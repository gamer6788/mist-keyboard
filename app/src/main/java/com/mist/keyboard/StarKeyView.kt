
package com.mist.keyboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import kotlin.random.Random

class StarKeyView(
    context: Context,
    private val keyText: String
) : FrameLayout(context) {

    private fun dp(value: Float): Float =
        value * resources.displayMetrics.density

    private fun dp(value: Int): Int =
        dp(value.toFloat()).toInt()

    private val stars = List(8) {
        Triple(
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat() * 1.2f + 0.4f
        )
    }

    private val starPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(1.5f)
        color = MistTheme.blueGlow
    }

    private val label = TextView(context).apply {
        text = keyText

        textSize = when {
            keyText == "space" -> 14f
            keyText.length > 2 -> 13f
            else -> 20f
        }

        gravity = Gravity.CENTER
        includeFontPadding = false
        setTextColor(Color.WHITE)

        typeface = Typeface.create(
            "sans-serif-medium",
            Typeface.NORMAL
        )

        setPadding(
            dp(2),
            dp(3),
            dp(2),
            dp(3)
        )
    }

    init {
        isClickable = true
        isFocusable = true

        background = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(
                MistTheme.keyTop,
                MistTheme.keyBottom
            )
        ).apply {
            cornerRadius = dp(12).toFloat()

            setStroke(
                dp(2),
                MistTheme.border
            )
        }

        addView(
            label,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        )

        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        stars.forEachIndexed { index, star ->

            starPaint.color = if (index % 3 == 0) {
                MistTheme.blueGlow
            } else {
                MistTheme.star
            }

            starPaint.alpha = if (index % 2 == 0) {
                150
            } else {
                75
            }

            canvas.drawCircle(
                width * star.first,
                height * star.second,
                star.third,
                starPaint
            )
        }

        val inset = dp(1.5f)

        canvas.drawRoundRect(
            inset,
            inset,
            width.toFloat() - inset,
            height.toFloat() - inset,
            dp(12f),
            dp(12f),
            borderPaint
        )
    }

    fun setLabel(value: String) {
        label.text = value
    }
}
