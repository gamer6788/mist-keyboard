
package com.mist.keyboard

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class ClipboardPanelView(
    context: Context,
    private val clipboardManager: ClipboardHistoryManager,
    private val onTextSelected: (String) -> Unit,
    private val onBack: () -> Unit
) : LinearLayout(context) {

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    init {
        orientation = VERTICAL
        setPadding(dp(8), dp(5), dp(8), dp(5))
        buildPanel()
    }

    private fun buildPanel() {
        removeAllViews()

        val header = LinearLayout(context).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        val title = TextView(context).apply {
            text = "✦ MIST CLIPBOARD"
            textSize = 16f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER_VERTICAL
        }

        header.addView(
            title,
            LayoutParams(0, dp(45), 1f)
        )

        header.addView(
            createButton("CLEAR") {
                clipboardManager.clearHistory()
                buildPanel()
            },
            LayoutParams(dp(65), dp(38))
        )

        addView(
            header,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(45)
            )
        )

        val entries = clipboardManager.getHistory()

        if (entries.isEmpty()) {
            val empty = TextView(context).apply {
                text = "Your copied texts will appear here.\n\nCopy some text, then open MIST Clipboard."
                textSize = 14f
                setTextColor(Color.LTGRAY)
                gravity = Gravity.CENTER
                setPadding(dp(20), dp(20), dp(20), dp(20))
            }

            addView(
                empty,
                LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    0,
                    1f
                )
            )

            addView(
                createButton("BACK TO KEYBOARD", onBack),
                LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    dp(43)
                )
            )

            return
        }

        val scroll = ScrollView(context).apply {
            isFillViewport = true
        }

        val list = LinearLayout(context).apply {
            orientation = VERTICAL
            setPadding(0, dp(5), 0, dp(5))
        }

        entries.forEach { clipboardText ->

            val entry = LinearLayout(context).apply {
                orientation = HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL

                background = roundedBackground(
                    Color.argb(105, 20, 43, 75)
                )

                setPadding(
                    dp(10),
                    dp(5),
                    dp(5),
                    dp(5)
                )
            }

            val content = TextView(context).apply {
                text = clipboardText
                textSize = 13f
                setTextColor(Color.WHITE)
                maxLines = 3
                gravity = Gravity.CENTER_VERTICAL

                setPadding(
                    dp(3),
                    dp(5),
                    dp(5),
                    dp(5)
                )

                setOnClickListener {
                    onTextSelected(clipboardText)
                }
            }

            entry.addView(
                content,
                LayoutParams(
                    0,
                    LayoutParams.WRAP_CONTENT,
                    1f
                )
            )

            val remove = TextView(context).apply {
                this.text = "×"
                textSize = 23f
                setTextColor(Color.LTGRAY)
                gravity = Gravity.CENTER

                setOnClickListener {
                    clipboardManager.removeText(clipboardText)
                    buildPanel()
                }
            }

            entry.addView(
                remove,
                LayoutParams(dp(38), dp(42))
            )

            list.addView(
                entry,
                LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(
                        0,
                        dp(3),
                        0,
                        dp(3)
                    )
                }
            )
        }

        scroll.addView(list)

        addView(
            scroll,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )

        addView(
            createButton("BACK TO KEYBOARD", onBack),
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                dp(43)
            )
        )
    }

    private fun createButton(
        label: String,
        action: () -> Unit
    ): TextView {
        return TextView(context).apply {
            text = label
            textSize = 12f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER

            background = roundedBackground(
                Color.rgb(19, 48, 82)
            )

            setOnClickListener {
                performHapticFeedback(
                    HapticFeedbackConstants.VIRTUAL_KEY
                )
                action()
            }
        }
    }

    private fun roundedBackground(
        color: Int
    ): GradientDrawable {
        return GradientDrawable().apply {
            setColor(color)
            cornerRadius = dp(12).toFloat()

            setStroke(
                dp(1),
                Color.argb(100, 90, 155, 230)
            )
        }
    }
}
