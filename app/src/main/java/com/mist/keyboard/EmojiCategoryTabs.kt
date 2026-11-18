
package com.mist.keyboard

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView

class EmojiCategoryTabs(
    context: Context,
    private val onCategorySelected: (EmojiCategory?) -> Unit
) : HorizontalScrollView(context) {

    private val density = resources.displayMetrics.density

    private fun dp(value: Int): Int =
        (value * density).toInt()

    private val tabs = mutableListOf<TextView>()
    private var selectedCategory: EmojiCategory? = null

    init {
        isHorizontalScrollBarEnabled = false
        overScrollMode = OVER_SCROLL_NEVER

        buildTabs()
    }

    private fun buildTabs() {
        val container = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        addView(
            container,
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT
            )
        )

        addTab(
            container,
            "🕘",
            null
        )

        EmojiData.categories().forEach { category ->
            addTab(
                container,
                category.icon,
                category
            )
        }

        updateSelection()
    }

    private fun addTab(
        container: LinearLayout,
        icon: String,
        category: EmojiCategory?
    ) {
        val tab = TextView(context).apply {
            text = icon
            textSize = 21f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            tag = category

            setOnClickListener {
                selectedCategory = category
                updateSelection()
                onCategorySelected(category)
            }
        }

        container.addView(
            tab,
            LinearLayout.LayoutParams(
                dp(45),
                dp(42)
            ).apply {
                setMargins(dp(2), dp(2), dp(2), dp(2))
            }
        )

        tabs.add(tab)
    }

    private fun updateSelection() {
        tabs.forEach { tab ->
            val category = tab.tag as? EmojiCategory
            val isSelected = category == selectedCategory

            tab.background = GradientDrawable().apply {
                setColor(
                    if (isSelected) {
                        Color.rgb(24, 54, 91)
                    } else {
                        Color.TRANSPARENT
                    }
                )

                cornerRadius = dp(10).toFloat()

                setStroke(
                    dp(1),
                    if (isSelected) {
                        Color.rgb(91, 157, 235)
                    } else {
                        Color.TRANSPARENT
                    }
                )
            }
        }
    }

    fun selectCategory(category: EmojiCategory?) {
        selectedCategory = category
        updateSelection()
        onCategorySelected(category)
    }
}
