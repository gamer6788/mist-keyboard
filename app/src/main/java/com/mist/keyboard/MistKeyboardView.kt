
package com.mist.keyboard

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
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
    private var showingNumberRow = true

    private var showingEmoji = false
    private var showingFavorites = false
    private var showingFontStudio = false
    private var showingClipboard = false

    private var selectedFontFamily: FontFamily? = null

    private lateinit var keysContainer: LinearLayout

    private val recentStore = EmojiRecentStore(service)
    private val skinToneManager = EmojiSkinToneManager(service)

    private val deleteHandler = Handler(Looper.getMainLooper())
    private var deleteHasRepeated = false

    private val deleteRunnable = object : Runnable {
        override fun run() {
            val connection =
                service.currentInputConnection ?: return

            deleteCharacter(connection)
            deleteHasRepeated = true

            deleteHandler.postDelayed(this, 65)
        }
    }

    private val numberRow = listOf(
        "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "0"
    )

    private val keyRows = listOf(
        listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
        listOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
        listOf("⇧", "z", "x", "c", "v", "b", "n", "m", "⌫"),
        listOf("123", ",", "😊", "🌐", "space", ".", "↵")
    )

    private val symbolRows = listOf(
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
        listOf("@", "#", "$", "%", "&", "-", "+", "(", ")"),
        listOf("=", "*", "\"", "'", ":", ";", "!", "?", "⌫"),
        listOf("ABC", ",", "😊", "🌐", "space", ".", "↵")
    )

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        setPadding(dp(4), dp(5), dp(4), dp(7))
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
            showingFontStudio -> buildFontStudioPanel()
            showingClipboard -> buildClipboardPanel()
            showingFavorites -> buildFavoritesPanel()
            showingEmoji -> buildEmojiPanel()

            else -> {
                if (!isSymbols && showingNumberRow) {
                    addNumberRow()
                }

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
            setPadding(dp(3), dp(1), dp(3), dp(5))
        }

        val title = TextView(service).apply {
            text = "✦ M I S T"
            textSize = 15f
            includeFontPadding = false
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(8), 0, 0, 0)
        }

        toolbar.addView(
            title,
            LayoutParams(0, dp(40), 1f)
        )

        if (showingEmoji || showingFavorites) {
            toolbar.addView(
                createToolbarButton("★") {
                    showingFavorites = !showingFavorites
                    showingEmoji = !showingFavorites
                    showingClipboard = false
                    showingFontStudio = false
                    buildKeyboard()
                },
                LayoutParams(dp(42), dp(39))
            )
        }

        toolbar.addView(
            createToolbarButton("▤") {
                showingEmoji = false
                showingFavorites = false
                showingFontStudio = false
                showingClipboard = true
                selectedFontFamily = null
                buildKeyboard()
            },
            LayoutParams(dp(42), dp(39))
        )

        toolbar.addView(
            createToolbarButton("Aa") {
                showingEmoji = false
                showingFavorites = false
                showingClipboard = false
                showingFontStudio = true
                selectedFontFamily = null
                buildKeyboard()
            },
            LayoutParams(dp(46), dp(39))
        )

        addView(
            toolbar,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(43)
            )
        )
    }

    private fun createToolbarButton(
        text: String,
        action: () -> Unit
    ): TextView {
        return TextView(service).apply {
            this.text = text
            textSize = 18f
            setTextColor(MistTheme.star)
            gravity = Gravity.CENTER

            background = roundedBackground(
                Color.argb(90, 35, 75, 125),
                11
            )

            setOnClickListener {
                tapFeedback()
                action()
            }
        }
    }

    private fun addNumberRow() {
        val row = LinearLayout(service).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER
        }

        numberRow.forEach { number ->
            val keyView = StarKeyView(service, number)

            row.addView(
                keyView,
                LayoutParams(
                    0,
                    LayoutParams.MATCH_PARENT,
                    1f
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
                tapFeedback()
                service.currentInputConnection
                    ?.commitText(number, 1)
            }
        }

        keysContainer.addView(
            row,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                0.75f
            )
        )
    }

    private fun addKeyRow(
        keys: List<String>,
        rowIndex: Int
    ) {
        val row = LinearLayout(service).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER

            if (rowIndex == 1) {
                setPadding(dp(9), 0, dp(9), 0)
            }
        }

        keys.forEach { key ->
            val keyView = StarKeyView(service, key)

            val weight = when (key) {
                "⇧", "⌫", "123", "ABC", "↵" -> 1.4f
                "space" -> 3.8f
                "🌐", "😊" -> 1.1f
                ",", "." -> 0.85f
                else -> 1f
            }

            row.addView(
                keyView,
                LayoutParams(
                    0,
                    LayoutParams.MATCH_PARENT,
                    weight
                ).apply {
                    setMargins(
                        dp(2),
                        dp(4),
                        dp(2),
                        dp(4)
                    )
                }
            )

            if (key == "⌫") {
                attachHoldToDelete(keyView)
            } else {
                keyView.setOnClickListener {
                    tapFeedback()
                    handleKey(key)
                }
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

    // HOLD TO DELETE

    private fun attachHoldToDelete(view: StarKeyView) {
        view.setOnTouchListener { _, event ->

            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    deleteHasRepeated = false
                    view.isPressed = true

                    tapFeedback()

                    deleteHandler.postDelayed(
                        deleteRunnable,
                        400
                    )

                    true
                }

                MotionEvent.ACTION_UP -> {
                    deleteHandler.removeCallbacks(deleteRunnable)

                    view.isPressed = false

                    if (!deleteHasRepeated) {
                        service.currentInputConnection?.let {
                            deleteCharacter(it)
                        }
                    }

                    true
                }

                MotionEvent.ACTION_CANCEL -> {
                    deleteHandler.removeCallbacks(deleteRunnable)
                    view.isPressed = false
                    true
                }

                else -> true
            }
        }
    }

    private fun tapFeedback() {
        performHapticFeedback(
            HapticFeedbackConstants.VIRTUAL_KEY
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

            "⌫" -> deleteCharacter(connection)

            "123" -> {
                isSymbols = true
                buildKeyboard()
            }

            "ABC" -> {
                isSymbols = false
                buildKeyboard()
            }

            "😊" -> {
                showingEmoji = true
                showingFavorites = false
                showingFontStudio = false
                showingClipboard = false
                buildKeyboard()
            }

            "space" -> connection.commitText(" ", 1)

            "↵" -> connection.commitText("\n", 1)

            "🌐" -> service.switchToNextInputMethod(false)

            "," -> connection.commitText(",", 1)

            "." -> connection.commitText(".", 1)

            else -> {
                val output = if (isShifted && !isSymbols) {
                    key.uppercase()
                } else {
                    key
                }

                val transformed = runCatching {
                    fontManager.transform(output)
                }.getOrDefault(output)

                connection.commitText(transformed, 1)

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

    // EMOJI

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

        keysContainer.addView(
            createEmojiAction("EMOJI") {
                showingFavorites = false
                showingEmoji = true
                buildKeyboard()
            },
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(42)
            )
        )
    }

    private fun insertEmoji(emoji: String) {
        val connection =
            service.currentInputConnection ?: return

        val finalEmoji = skinToneManager.applyTone(emoji)

        connection.commitText(finalEmoji, 1)
        recentStore.add(finalEmoji)

        tapFeedback()
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
                tapFeedback()
                action()
            }
        }
    }

    // FONT STUDIO

    private fun buildFontStudioPanel() {
        val heading = TextView(service).apply {
            text = if (selectedFontFamily == null) {
                "✦ MIST FONT STUDIO"
            } else {
                "✦ ${selectedFontFamily!!.displayName}"
            }

            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        keysContainer.addView(
            heading,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(48)
            )
        )

        val scroll = ScrollView(service).apply {
            isFillViewport = true
        }

        val options = LinearLayout(service).apply {
            orientation = VERTICAL
            setPadding(dp(8), dp(4), dp(8), dp(8))
        }

        if (selectedFontFamily == null) {
            FontFamily.values().forEach { family ->
                addFontOption(
                    options,
                    family.displayName,
                    "Choose font family"
                ) {
                    selectedFontFamily = family
                    buildKeyboard()
                }
            }
        } else {
            FontVariation.values().forEach { variation ->
                addFontOption(
                    options,
                    variation.displayName,
                    "Select this style"
                ) {
                    runCatching {
                        fontManager.select(
                            selectedFontFamily!!,
                            variation
                        )
                    }.onSuccess {
                        showingFontStudio = false
                        selectedFontFamily = null
                        buildKeyboard()
                    }
                }
            }
        }

        scroll.addView(options)

        keysContainer.addView(
            scroll,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        val backLabel =
            if (selectedFontFamily == null) "BACK TO KEYBOARD"
            else "BACK TO FONTS"

        keysContainer.addView(
            createEmojiAction(backLabel) {
                if (selectedFontFamily != null) {
                    selectedFontFamily = null
                    buildKeyboard()
                } else {
                    showingFontStudio = false
                    buildKeyboard()
                }
            },
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(44)
            )
        )
    }

    private fun addFontOption(
        parent: LinearLayout,
        name: String,
        description: String,
        action: () -> Unit
    ) {
        val option = LinearLayout(service).apply {
            orientation = VERTICAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(15), dp(8), dp(12), dp(8))

            background = roundedBackground(
                Color.argb(100, 22, 48, 83),
                12
            )

            setOnClickListener {
                tapFeedback()
                action()
            }
        }

        val title = TextView(service).apply {
            text = name
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.WHITE)
        }

        val subtitle = TextView(service).apply {
            text = description
            textSize = 11f
            setTextColor(Color.LTGRAY)
        }

        option.addView(title)
        option.addView(subtitle)

        parent.addView(
            option,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(55)
            ).apply {
                setMargins(0, dp(3), 0, dp(3))
            }
        )
    }

    // CLIPBOARD

    private fun buildClipboardPanel() {
        val panel = ClipboardPanelView(
            context = service,
            clipboardManager = service.clipboardHistoryManager,
            onTextSelected = { text ->
                service.currentInputConnection?.commitText(
                    text,
                    1
                )

                showingClipboard = false
                buildKeyboard()
            },
            onBack = {
                showingClipboard = false
                buildKeyboard()
            }
        )

        keysContainer.addView(
            panel,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        )
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

    override fun onDetachedFromWindow() {
        deleteHandler.removeCallbacks(deleteRunnable)
        super.onDetachedFromWindow()
    }
}
