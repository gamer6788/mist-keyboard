
package com.mist.keyboard

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.GridLayout
import android.widget.TextView

class EmojiPanelAdapter(
    private val context: Context,
    private val onEmojiSelected: (String) -> Unit,
    private val onEmojiLongPressed: (String) -> Unit
) {

    private val density = context.resources.displayMetrics.density

    private fun dp(value: Int): Int =
        (value * density).toInt()

    fun render(
        grid: GridLayout,
        items: List<EmojiItem>,
        columns: Int = 8
    ) {
        grid.removeAllViews()
        grid.columnCount = columns.coerceAtLeast(1)

        items.forEach { item ->
            val emojiView = TextView(context).apply {
                text = item.emoji
                textSize = 27f
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)

                background = GradientDrawable().apply {
                    setColor(Color.TRANSPARENT)
                    cornerRadius = dp(10).toFloat()
                }

                setOnClickListener {
                    performHapticFeedback(
                        android.view.HapticFeedbackConstants.VIRTUAL_KEY
                    )
                    onEmojiSelected(item.emoji)
                }

                setOnLongClickListener {
                    onEmojiLongPressed(item.emoji)
                    true
                }
            }

            grid.addView(
                emojiView,
                GridLayout.LayoutParams().apply {
                    width = 0
                    height = dp(49)
                    columnSpec = GridLayout.spec(
                        GridLayout.UNDEFINED,
                        1f
                    )
                    setMargins(dp(2), dp(2), dp(2), dp(2))
                }
            )
        }

        if (items.isEmpty()) {
            val emptyView = TextView(context).apply {
                text = "No emojis found"
                textSize = 14f
                gravity = Gravity.CENTER
                setTextColor(Color.LTGRAY)
            }

            grid.addView(
                emptyView,
                GridLayout.LayoutParams().apply {
                    width = android.view.ViewGroup.LayoutParams.MATCH_PARENT
                    height = dp(80)
                    columnSpec = GridLayout.spec(0, columns)
                }
            )
        }
    }
}
