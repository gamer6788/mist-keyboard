
package com.mist.keyboard

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.inputmethod.InputConnection
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class MistKeyboardView(
    private val service: MistKeyboardService,
    private val fontManager: FontManager
) : LinearLayout(service) {

    private var isShifted = false
    private var isSymbols = false
    private var showingEmoji = false
    private var showingFavorites = false

    private lateinit var keysContainer: LinearLayout

    private val recentStore = EmojiRecentStore(service)
    private val skinToneManager = EmojiSkinToneManager(service)

    private val keyRows = listOf(
        listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
        listOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
        listOf("⇧", "z", "x", "c", "v", "b", "n", "m", "⌫"),
        listOf("123", ",", "🌐", "space", ".", "↵")
    )

    private val symbolRows = listOf(
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
        listOf("@", "#", "$", "%", "&", "-", "+", "(", ")"),
        listOf("=", "*", "\"", "'", ":", ";", "!", "?", "⌫"),
        listOf("ABC", ",", "🌐", "space", ".", "↵")
    )

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        setPadding(dp(5), dp(7), dp(5), dp(9))
        minimumHeight = dp(340)

        background = MistSpaceBackground()

        buildKeyboard()
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

        when {
            showingFavorites -> buildFavoritesPanel()
            showingEmoji -> buildEmojiPanel()
            else -> {
                val rows = if (isSymbols) symbolRows else keyRows

                rows.forEachIndexed { index, row ->
                    addKeyRow(row, index)
                }
            }
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
            createToolbarButton("😊") {
                showingEmoji = !showingEmoji
                showingFavorites = false
                buildKeyboard()
            },
            LayoutParams(dp(44), dp(42))
        )

        if (showingEmoji) {
            toolbar.addView(
                createToolbarButton("★") {
                    showingFavorites = !showingFavorites
                    buildKeyboard()
                },
                LayoutParams(dp(44), dp(42))
            )
        }

        toolbar.addView(
            createToolbarButton("Aa") {
                showFontStudio()
            },
            LayoutParams(dp(48), dp(42))
        )

        if (!showingEmoji) {
            toolbar.addView(
                createToolbarButton("⇧") {
                    isShifted = !isShifted
                    buildKeyboard()
                },
                LayoutParams(dp(44), dp(42))
            )
        }

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

            setOnClickListener {
                performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY
                )
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
            val keyView = StarKeyView(
                service,
                key
            )

            val weight = when (key) {
                "⇧", "⌫", "123", "ABC", "↵" -> 1.35f
                "space" -> 4.5f
                "🌐" -> 1.15f
                else -> 1f
            }

            row.addView(
                keyView,
                LayoutParams(
                    0,
                    dp(60),
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

            keyView.setOnClickListener {
                performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY
                )
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
        val connection =
            service.currentInputConnection ?: return

        when (key) {
            "⇧" -> {
                isShifted = !isShifted
                buildKeyboard()
            }

            "⌫" -> {
                deleteCharacter(connection)
            }

            "123" -> {
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

    private fun deleteCharacter(connection: InputConnection) {
        val before = connection
            .getTextBeforeCursor(100, 0)
            ?.toString()
            ?: return

        if (before.isEmpty()) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val iterator =
                android.icu.text.BreakIterator
                    .getCharacterInstance()

            iterator.setText(before)

            val start = iterator.preceding(before.length)

            if (start != android.icu.text.BreakIterator.DONE) {
                connection.deleteSurroundingText(
                    before.length - start,
                    0
                )
                return
            }
        }

        val lastCodePoint =
            before.codePointBefore(before.length)

        connection.deleteSurroundingText(
            Character.charCount(lastCodePoint),
            0
        )
    }

    private fun buildEmojiPanel() {
        val picker = EmojiPickerView(
            context = service,
            onEmojiSelected = { emoji ->
                insertEmoji(emoji)
            },
            onBackToKeyboard = {
                showingEmoji = false
                showingFavorites = false
                buildKeyboard()
            },
            recentEmojis = recentStore.getRecent()
        )

        keysContainer.addView(
            picker,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        )
    }

    private fun buildFavoritesPanel() {
        val header = TextView(service).apply {
            text = "✦ YOUR FAVOURITE EMOJIS"
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        keysContainer.addView(
            header,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(42)
            )
        )

        val favoritesView = EmojiFavoritesView(
            context = service,
            recentStore = recentStore,
            onEmojiSelected = { emoji ->
                insertEmoji(emoji)
            }
        )

        val scroll = ScrollView(service).apply {
            isFillViewport = true
        }

        scroll.addView(favoritesView)

        keysContainer.addView(
            scroll,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        val back = createEmojiAction("EMOJI") {
            showingFavorites = false
            buildKeyboard()
        }

        keysContainer.addView(
            back,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(44)
            )
        )
    }

    private fun insertEmoji(emoji: String) {
        val connection =
            service.currentInputConnection ?: return

        val finalEmoji = skinToneManager.applyTone(emoji)

        connection.commitText(finalEmoji, 1)
        recentStore.add(finalEmoji)

        performHapticFeedback(
            HapticFeedbackConstants.VIRTUAL_KEY
        )
    }

    private fun createEmojiAction(
        label: String,
        action: () -> Unit
    ): TextView {
        return TextView(service).apply {
            text = label
            textSize = 15f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)

            background = roundedBackground(
                Color.rgb(16, 34, 62),
                12
            )

            setOnClickListener {
                performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY
                )
                action()
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
            .setTitle(
                "${selectedFamily.displayName} — Variation"
            )
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
