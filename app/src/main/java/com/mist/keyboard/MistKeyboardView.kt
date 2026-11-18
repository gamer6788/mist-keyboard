
package com.mist.keyboard

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import android.widget.TextView

class MistKeyboardView(
    private val service: MistKeyboardService,
    private val fontManager: FontManager
) : LinearLayout(service) {

    private var isShifted = false
    private var isSymbols = false

    private lateinit var keysContainer: LinearLayout

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private val keyRows = listOf(
        listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
        listOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
        listOf("⇧", "z", "x", "c", "v", "b", "n", "m", "⌫"),
        listOf("?123", ",", "🌐", "space", ".", "↵")
    )

    private val symbolRows = listOf(
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
        listOf("@", "#", "$", "%", "&", "-", "+", "(", ")"),
        listOf("=", "*", "\"", "'", ":", ";", "!", "?", "⌫"),
        listOf("ABC", ",", "🌐", "space", ".", "↵")
    )

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        setPadding(
            dp(5), dp(7),
            dp(5), dp(9)
        )

        minimumHeight = dp(290)

        background = MistSpaceBackground()

        buildKeyboard()
    }

    private fun provideHapticFeedback(view: android.view.View) {
        view.isHapticFeedbackEnabled = true

        view.performHapticFeedback(
            HapticFeedbackConstants.VIRTUAL_KEY
        )
    }

    private fun buildKeyboard() {
        removeAllViews()

        buildToolbar()

        keysContainer = LinearLayout(service).apply {
            orientation = VERTICAL
            gravity = Gravity.CENTER
        }

        addView(
            keysContainer,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        val rows = if (isSymbols) symbolRows else keyRows

        rows.forEachIndexed { index, row ->
            addKeyRow(row, index)
        }
    }

    private fun buildToolbar() {
        val toolbar = LinearLayout(service).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(5), dp(2), dp(5), dp(5))
        }

        val title = TextView(service).apply {
            text = "✦ M I S T"
            textSize = 15f
            includeFontPadding = false
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
        }

        toolbar.addView(
            title,
            LayoutParams(0, dp(43), 1f)
        )

        toolbar.addView(
            createToolbarButton("Aa") {
                showFontStudio()
            },
            LayoutParams(dp(48), dp(42))
        )

        toolbar.addView(
            createToolbarButton("⇧") {
                isShifted = !isShifted
                buildKeyboard()
            },
            LayoutParams(dp(44), dp(42))
        )

        addView(
            toolbar,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(48)
            )
        )
    }

    private fun createToolbarButton(
        text: String,
        action: () -> Unit
    ): TextView {
        return TextView(service).apply {
            this.text = text
            textSize = 19f
            setTextColor(MistTheme.star)
            gravity = Gravity.CENTER

            background = roundedBackground(
                Color.argb(90, 35, 75, 125),
                12
            )

            isHapticFeedbackEnabled = true

            setOnClickListener {
                provideHapticFeedback(this)
                action()
            }
        }
    }

    private fun addKeyRow(
        keys: List<String>,
        rowIndex: Int
    ) {
        val row = LinearLayout(service).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER
        }

        keys.forEach { key ->

            // Keep keyboard labels in normal readable characters.
            val keyView = StarKeyView(service, key)

            val weight = when (key) {
                "⇧", "⌫", "?123", "ABC", "↵" -> 1.35f
                "space" -> 4.5f
                "🌐" -> 1.15f
                else -> 1f
            }

            row.addView(
                keyView,
                LayoutParams(
                    0,
                    dp(54),
                    weight
                ).apply {
                    setMargins(
                        dp(2),
                        dp(3),
                        dp(2),
                        dp(3)
                    )
                }
            )

            keyView.isHapticFeedbackEnabled = true

            keyView.setOnClickListener {
                provideHapticFeedback(keyView)
                handleKey(key)
            }
        }

        keysContainer.addView(
            row,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )
    }

    private fun handleKey(key: String) {
        val connection: InputConnection =
            service.currentInputConnection ?: return

        when (key) {

            "⇧" -> {
                isShifted = !isShifted
                buildKeyboard()
            }

            "⌫" -> {
                connection.deleteSurroundingText(1, 0)
            }

            "?123" -> {
                isSymbols = true
                buildKeyboard()
            }

            "ABC" -> {
                isSymbols = false
                buildKeyboard()
            }

            "space" -> {
                connection.commitText(" ", 1)
            }

            "↵" -> {
                connection.commitText("\n", 1)
            }

            "🌐" -> {
                service.switchToNextInputMethod(false)
            }

            "," -> {
                connection.commitText(",", 1)
            }

            "." -> {
                connection.commitText(".", 1)
            }

            else -> {
                val output = if (isShifted && !isSymbols) {
                    key.uppercase()
                } else {
                    key
                }

                connection.commitText(
                    fontManager.transform(output),
                    1
                )

                if (isShifted && !isSymbols) {
                    isShifted = false
                    buildKeyboard()
                }
            }
        }
    }

    private fun showFontStudio() {
        val families = FontFamily.values()

        val names = families.map {
            it.displayName
        }.toTypedArray()

        AlertDialog.Builder(service)
            .setTitle("✦ MIST FONT STUDIO")
            .setItems(names) { _, index ->
                showVariationSelector(families[index])
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun showVariationSelector(
        selectedFamily: FontFamily
    ) {
        val variations = FontVariation.values()

        val names = variations.map {
            it.displayName
        }.toTypedArray()

        AlertDialog.Builder(service)
            .setTitle("${selectedFamily.displayName} — Variation")
            .setItems(names) { _, index ->
                fontManager.select(
                    selectedFamily,
                    variations[index]
                )

                buildKeyboard()
            }
            .setNegativeButton("Back") { _, _ ->
                showFontStudio()
            }
            .show()
    }

    private fun roundedBackground(
        color: Int,
        radius: Int
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(radius).toFloat()

            setStroke(
                dp(1),
                Color.argb(65, 120, 175, 255)
            )
        }
    }
}
