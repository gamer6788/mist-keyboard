
package com.mist.keyboard

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import android.widget.TextView

class MistKeyboardView(
    private val service: MistKeyboardService,
    private val fontManager: FontManager
) : LinearLayout(service) {

    private var shifted = true
    private var symbols = false
    private var capsLocked = false

    private val normalRows = listOf(
        "qwertyuiop",
        "asdfghjkl",
        "⇧zxcvbnm⌫"
    )

    private val symbolRows = listOf(
        "1234567890",
        "@#$%&-+()",
        "⇧*/:;!?'⌫"
    )

    private lateinit var keysContainer: LinearLayout
    private lateinit var fontLabel: TextView

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setPadding(5, 7, 5, 9)

        background = MistSpaceBackground(service)

        createToolbar()

        keysContainer = LinearLayout(service).apply {
            orientation = VERTICAL
        }

        addView(
            keysContainer,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT,
                1f
            )
        )

        createBottomRow()

        updateFontLabel()
        renderKeys()
    }

    private fun createToolbar() {
        val header = LinearLayout(service).apply {
            gravity = Gravity.CENTER_VERTICAL
            orientation = HORIZONTAL
        }

        val brand = TextView(service).apply {
            text = "✦ M I S T"
            textSize = 16f
            setTextColor(Color.rgb(151, 197, 255))
            setPadding(10, 8, 8, 8)
        }

        fontLabel = TextView(service).apply {
            textSize = 11f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(12, 9, 12, 9)
            background = toolbarBackground()

            setOnClickListener {
                showFontStudio()
            }
        }

        header.addView(
            brand,
            LayoutParams(0, 45, 1f)
        )

        header.addView(
            fontLabel,
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                38
            )
        )

        addView(
            header,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        )
    }

    private fun createBottomRow() {
        val bottom = LinearLayout(service).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER
        }

        addSpecialKey(bottom, "?123", 1.4f) {
            symbols = !symbols
            renderKeys()
        }

        addSpecialKey(bottom, ",", 0.8f) {
            commit(",")
        }

        addSpecialKey(bottom, "SPACE", 4f) {
            commit(" ")
        }

        addSpecialKey(bottom, ".", 0.8f) {
            commit(".")
        }

        addSpecialKey(bottom, "↵", 1.3f) {
            val connection = service.currentInputConnection

            if (connection != null) {
                if (!service.sendDefaultEditorAction(true)) {
                    connection.commitText("\n", 1)
                }
            }
        }

        addView(
            bottom,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                48
            )
        )
    }

    private fun renderKeys() {
        keysContainer.removeAllViews()

        val rows = if (symbols) symbolRows else normalRows

        rows.forEach { row ->
            val line = LinearLayout(service).apply {
                orientation = HORIZONTAL
                gravity = Gravity.CENTER
            }

            row.forEach { char ->

                val weight = when (char) {
                    '⇧', '⌫' -> 1.35f
                    else -> 1f
                }

                val label = when (char) {
                    '⇧' -> "⇧"
                    '⌫' -> "⌫"
                    else -> {
                        if (shifted) {
                            char.uppercaseChar().toString()
                        } else {
                            char.toString()
                        }
                    }
                }

                val key = StarKeyView(service, label)

                key.setOnClickListener {
                    handleKey(char)
                }

                val params = LayoutParams(
                    0,
                    48,
                    weight
                )

                params.setMargins(2, 3, 2, 3)

                line.addView(key, params)
            }

            keysContainer.addView(
                line,
                LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                )
            )
        }
    }

    private fun handleKey(char: Char) {
        when (char) {

            '⇧' -> {
                if (shifted && !capsLocked) {
                    capsLocked = true
                } else if (capsLocked) {
                    capsLocked = false
                    shifted = false
                } else {
                    shifted = true
                }

                renderKeys()
            }

            '⌫' -> {
                val connection = service.currentInputConnection

                connection?.deleteSurroundingText(1, 0)
            }

            else -> {
                val value = if (shifted) {
                    char.uppercaseChar().toString()
                } else {
                    char.toString()
                }

                commit(value)

                if (!capsLocked) {
                    shifted = false
                    renderKeys()
                }
            }
        }
    }

    private fun commit(text: String) {
        val connection: InputConnection =
            service.currentInputConnection ?: return

        val converted = fontManager.transform(text)

        connection.commitText(converted, 1)
    }

    private fun addSpecialKey(
        parent: LinearLayout,
        text: String,
        weight: Float,
        action: () -> Unit
    ) {
        val key = TextView(service).apply {
            this.text = text
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            background = toolbarBackground()

            setOnClickListener {
                action()
            }
        }

        val params = LayoutParams(
            0,
            43,
            weight
        )

        params.setMargins(2, 2, 2, 2)

        parent.addView(key, params)
    }

    private fun toolbarBackground(): GradientDrawable {
        return GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(
                Color.rgb(20, 37, 65),
                Color.rgb(8, 17, 36)
            )
        ).apply {
            cornerRadius = 13f
            setStroke(1, MistTheme.border)
        }
    }

    private fun updateFontLabel() {
        fontLabel.text =
            "${fontManager.family.displayName} ▾"
    }

    private fun showFontStudio() {
        val families = FontFamily.values()

        AlertDialog.Builder(service)
            .setTitle("✦ MIST FONT STUDIO")
            .setItems(
                families.map { it.displayName }.toTypedArray()
            ) { _, index ->
                showVariations(families[index])
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun showVariations(family: FontFamily) {
        val variations = FontVariation.values()

        AlertDialog.Builder(service)
            .setTitle(family.displayName)
            .setItems(
                variations.map { it.displayName }.toTypedArray()
            ) { _, index ->

                fontManager.select(
                    family,
                    variations[index]
                )

                updateFontLabel()
            }
            .setNegativeButton("Back") { _, _ ->
                showFontStudio()
            }
            .show()
    }
}
