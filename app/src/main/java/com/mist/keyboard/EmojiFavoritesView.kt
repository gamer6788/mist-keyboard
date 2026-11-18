
package com.mist.keyboard

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView

class EmojiFavoritesView(
    context: Context,
    private val recentStore: EmojiRecentStore,
    private val onEmojiSelected: (String) -> Unit
) : LinearLayout(context) {

    private val backgroundColor = Color.rgb(4, 9, 24)
    private val panelColor = Color.rgb(15, 29, 53)
    private val accentColor = Color.rgb(91, 157, 235)

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    init {
        orientation = VERTICAL
        setPadding(dp(8), dp(8), dp(8), dp(8))

        background = GradientDrawable().apply {
            setColor(backgroundColor)
            cornerRadius = dp(14).toFloat()
        }

        buildView()
    }

    private fun buildView() {
        removeAllViews()

        val header = LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val title = TextView(context).apply {
            text = "✦ MY FAVOURITES"
            textSize = 14f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.WHITE)
        }

        header.addView(
            title,
            LayoutParams(0, dp(42), 1f)
        )

        val clearButton = TextView(context).apply {
            text = "CLEAR"
            textSize = 11f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(accentColor)
            gravity = Gravity.CENTER
            setPadding(dp(10), 0, dp(10), 0)

            setOnClickListener {
                recentStore.clearAll()
                buildView()
            }
        }

        header.addView(
            clearButton,
            LayoutParams(
                LayoutParams.WRAP_CONTENT,
                dp(42)
            )
        )

        addView(
            header,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(42)
            )
        )

        val emojis = recentStore.getPinned()

        if (emojis.isEmpty()) {
            val empty = TextView(context).apply {
                text = "No favourites yet.\nLong-press an emoji in the picker to explore options."
                textSize = 13f
                gravity = Gravity.CENTER
                setTextColor(Color.LTGRAY)
                setPadding(dp(12), dp(25), dp(12), dp(25))
            }

            addView(
                empty,
                LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                )
            )

            return
        }

        val grid = GridLayout(context).apply {
            columnCount = 6
            useDefaultMargins = false
        }

        emojis.forEach { emoji ->
            val cell = LinearLayout(context).apply {
                orientation = VERTICAL
                gravity = Gravity.CENTER
                background = GradientDrawable().apply {
                    setColor(panelColor)
                    cornerRadius = dp(10).toFloat()
                }
                setPadding(0, dp(4), 0, dp(4))
            }

            val emojiButton = TextView(context).apply {
                text = emoji
                textSize = 26f
                gravity = Gravity.CENTER
                setTextColor(Color.WHITE)

                setOnClickListener {
                    onEmojiSelected(emoji)
                }
            }

            val removeButton = TextView(context).apply {
                text = "×"
                textSize = 16f
                gravity = Gravity.CENTER
                setTextColor(Color.LTGRAY)

                setOnClickListener {
                    recentStore.unpin(emoji)
                    buildView()
                }
            }

            cell.addView(
                emojiButton,
                LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    dp(38)
                )
            )

            cell.addView(
                removeButton,
                LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    dp(22)
                )
            )

            grid.addView(
                cell,
                GridLayout.LayoutParams().apply {
                    width = 0
                    height = dp(64)
                    columnSpec = GridLayout.spec(
                        GridLayout.UNDEFINED,
                        1f
                    )
                    setMargins(dp(3), dp(3), dp(3), dp(3))
                }
            )
        }

        addView(
            grid,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        )
    }
}
