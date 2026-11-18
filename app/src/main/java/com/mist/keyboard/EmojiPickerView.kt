
package com.mist.keyboard

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.GridLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class EmojiPickerView(
    context: Context,
    private val onEmojiSelected: (String) -> Unit,
    private val onBackToKeyboard: () -> Unit,
    private val recentEmojis: List<String> = emptyList()
) : LinearLayout(context) {

    private var selectedCategory: EmojiCategory? = null
    private var currentSearch = ""

    private lateinit var searchInput: EditText
    private lateinit var emojiGrid: GridLayout
    private lateinit var categoryBar: LinearLayout
    private lateinit var contentScroll: ScrollView

    private var selectedSkinTone = ""

    private val backgroundColor = Color.rgb(4, 9, 24)
    private val panelColor = Color.rgb(15, 29, 53)
    private val accentColor = Color.rgb(91, 157, 235)
    private val textColor = Color.WHITE

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    init {
        orientation = VERTICAL
        setPadding(dp(5), dp(5), dp(5), dp(5))
        background = GradientDrawable().apply {
            setColor(backgroundColor)
            cornerRadius = dp(16).toFloat()
        }

        buildInterface()
        displayEmojis()
    }

    private fun buildInterface() {
        removeAllViews()

        addView(
            buildHeader(),
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(43)
            )
        )

        addView(
            buildSearchBar(),
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(46)
            ).apply {
                setMargins(0, dp(5), 0, dp(5))
            }
        )

        addView(
            buildCategoryBar(),
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(46)
            )
        )

        contentScroll = ScrollView(context).apply {
            isFillViewport = true
            clipToPadding = false
        }

        emojiGrid = GridLayout(context).apply {
            columnCount = 8
            setPadding(dp(2), dp(5), dp(2), dp(5))
            useDefaultMargins = false
        }

        contentScroll.addView(
            emojiGrid,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        )

        addView(
            contentScroll,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        addView(
            buildBottomBar(),
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(45)
            )
        )
    }

    private fun buildHeader(): View {
        val header = LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val title = TextView(context).apply {
            text = "✦ MIST EMOJI STUDIO"
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(textColor)
            gravity = Gravity.CENTER_VERTICAL
        }

        header.addView(
            title,
            LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        )

        val close = makeButton("⌨", 22f) {
            onBackToKeyboard()
        }

        header.addView(
            close,
            LayoutParams(dp(45), LayoutParams.MATCH_PARENT)
        )

        return header
    }

    private fun buildSearchBar(): View {
        val container = LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(dp(12), 0, dp(10), 0)
            background = roundedBackground(panelColor, 12)
        }

        val icon = TextView(context).apply {
            text = "⌕"
            textSize = 23f
            setTextColor(accentColor)
        }

        container.addView(icon)

        searchInput = EditText(context).apply {
            hint = "Search emojis..."
            textSize = 14f
            singleLine = true
            setTextColor(textColor)
            setHintTextColor(Color.LTGRAY)
            background = null
            setPadding(dp(8), 0, dp(5), 0)
            imeOptions = android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH
        }

        container.addView(
            searchInput,
            LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
        )

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                currentSearch = s?.toString().orEmpty()
                displayEmojis()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        searchInput.addTextChangedListener(watcher)

        return container
    }

    private fun buildCategoryBar(): View {
        val horizontal = HorizontalScrollView(context).apply {
            isHorizontalScrollBarEnabled = false
        }

        categoryBar = LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val recentButton = makeButton("🕘", 21f) {
            selectedCategory = null
            currentSearch = ""
            searchInput.setText("")
            displayRecent()
        }

        categoryBar.addView(
            recentButton,
            LayoutParams(dp(47), dp(42))
        )

        EmojiData.categories().forEach { category ->
            val button = makeButton(
                category.icon,
                21f
            ) {
                selectedCategory = category
                currentSearch = ""
                searchInput.setText("")
                displayEmojis()
                updateCategoryButtons()
            }

            button.tag = category.name

            categoryBar.addView(
                button,
                LayoutParams(dp(47), dp(42))
            )
        }

        horizontal.addView(categoryBar)

        return horizontal
    }

    private fun updateCategoryButtons() {
        for (index in 0 until categoryBar.childCount) {
            val child = categoryBar.getChildAt(index)
            val categoryName = child.tag as? String

            val selected = categoryName != null &&
                EmojiCategory.values().firstOrNull {
                    it.name == categoryName
                } == selectedCategory

            child.background = roundedBackground(
                if (selected) panelColor else Color.TRANSPARENT,
                10
            )
        }
    }

    private fun displayEmojis() {
        if (!::emojiGrid.isInitialized) return

        val results = when {
            currentSearch.isNotBlank() ->
                EmojiData.search(currentSearch)

            selectedCategory != null ->
                EmojiData.byCategory(selectedCategory!!)

            else ->
                EmojiData.all()
        }

        renderGrid(results.map { it.emoji })
    }

    private fun displayRecent() {
        renderGrid(
            EmojiData.recentlyUsed(recentEmojis).map {
                it.emoji
            }
        )
    }

    private fun renderGrid(emojis: List<String>) {
        emojiGrid.removeAllViews()

        emojis.forEach { emoji ->
            val item = TextView(context).apply {
                text = emoji
                textSize = 27f
                gravity = Gravity.CENTER
                setTextColor(textColor)

                setOnClickListener {
                    onEmojiSelected(
                        applySkinTone(emoji)
                    )
                }

                setOnLongClickListener {
                    showSkinToneOptions(emoji)
                    true
                }
            }

            emojiGrid.addView(
                item,
                GridLayout.LayoutParams().apply {
                    width = 0
                    height = dp(49)
                    columnSpec = GridLayout.spec(
                        GridLayout.UNDEFINED,
                        1f
                    )
                }
            )
        }

        if (emojis.isEmpty()) {
            val empty = TextView(context).apply {
                text = "No emojis found"
                textSize = 14f
                setTextColor(Color.LTGRAY)
                gravity = Gravity.CENTER
            }

            emojiGrid.addView(
                empty,
                GridLayout.LayoutParams().apply {
                    width = LayoutParams.MATCH_PARENT
                    height = dp(90)
                    columnSpec = GridLayout.spec(0, 8)
                }
            )
        }
    }

    private fun showSkinToneOptions(emoji: String) {
        val variants = EmojiData.skinToneVariants(emoji)

        if (variants.isEmpty()) return

        val choices = variants.toTypedArray()

        android.app.AlertDialog.Builder(context)
            .setTitle("Choose skin tone")
            .setItems(choices) { _, index ->
                onEmojiSelected(choices[index])
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun applySkinTone(emoji: String): String {
        return if (selectedSkinTone.isEmpty()) {
            emoji
        } else {
            val variants = EmojiData.skinToneVariants(emoji)
            variants.firstOrNull {
                it.contains(selectedSkinTone)
            } ?: emoji
        }
    }

    private fun buildBottomBar(): View {
        val bottom = LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER
        }

        val toneButton = makeButton("🎨", 19f) {
            showSkinTonePicker()
        }

        bottom.addView(
            toneButton,
            LayoutParams(dp(48), dp(42))
        )

        val space = View(context)

        bottom.addView(
            space,
            LayoutParams(0, 1, 1f)
        )

        val keyboardButton = makeButton("ABC", 16f) {
            onBackToKeyboard()
        }

        bottom.addView(
            keyboardButton,
            LayoutParams(dp(65), dp(42))
        )

        return bottom
    }

    private fun showSkinTonePicker() {
        val tones = listOf(
            "Default",
            "🏻 Light",
            "🏼 Medium-light",
            "🏽 Medium",
            "🏾 Medium-dark",
            "🏿 Dark"
        )

        val toneCodes = listOf(
            "",
            "\uD83C\uDFFB",
            "\uD83C\uDFFC",
            "\uD83C\uDFFD",
            "\uD83C\uDFFE",
            "\uD83C\uDFFF"
        )

        android.app.AlertDialog.Builder(context)
            .setTitle("Default skin tone")
            .setItems(tones.toTypedArray()) { _, index ->
                selectedSkinTone = toneCodes[index]
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun makeButton(
        label: String,
        size: Float,
        action: () -> Unit
    ): TextView {
        return TextView(context).apply {
            text = label
            textSize = size
            gravity = Gravity.CENTER
            setTextColor(textColor)

            background = roundedBackground(
                Color.TRANSPARENT,
                10
            )

            setOnClickListener {
                performHapticFeedback(
                    android.view.HapticFeedbackConstants.VIRTUAL_KEY
                )
                action()
            }
        }
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
                Color.argb(45, 100, 160, 240)
            )
        }
    }
}
