
package com.mist.keyboard

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import kotlin.random.Random

class StarKeyView(
    context: Context,
    private val keyText: String
) : FrameLayout(context) {

    private val stars = List(13) {
        Triple(
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat() * 1.1f + 0.3f
        )
    }

    private val starPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val label = TextView(context).apply {
        text = keyText
        textSize = 18f
        gravity = android.view.Gravity.CENTER
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
            cornerRadius = 13f
            setStroke(1, MistTheme.border)
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
            starPaint.color = when {
                index % 5 == 0 -> MistTheme.blueGlow
                else -> MistTheme.star
            }

            starPaint.alpha = if (index % 3 == 0) 220 else 105

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
