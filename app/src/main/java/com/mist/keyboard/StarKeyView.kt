
package com.mist.keyboard

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import kotlin.random.Random

class StarKeyView(
    context: Context,
    private val keyText: String
) : FrameLayout(context) {

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private val stars = List(11) {
        Triple(
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat() * 1.2f + 0.4f
        )
    }

    private val starPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val label = TextView(context).apply {
        text = keyText
        textSize = if (keyText.length > 2) 13f else 20f
        gravity = Gravity.CENTER
        includeFontPadding = false
        setTextColor(Color.WHITE)

        typeface = Typeface.create(
            "sans-serif-medium",
            Typeface.NORMAL
        )
    }

    init {
        background = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(
                MistTheme.keyTop,
                MistTheme.keyBottom
            )
        ).apply {
            cornerRadius = dp(13).toFloat()
            setStroke(dp(1), MistTheme.border)
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
            starPaint.color = if (index % 5 == 0) {
                MistTheme.blueGlow
            } else {
                MistTheme.star
            }

            starPaint.alpha = if (index % 3 == 0) 210 else 85

            canvas.drawCircle(
                width * star.first,
                height * star.second,
                star.third,
                starPaint
            )
        }
    }

    fun setLabel(value: String) {
        label.text = value
    }
}
