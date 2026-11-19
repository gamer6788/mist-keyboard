
package com.mist.keyboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
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

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    private val cornerRadius = dp(13).toFloat()

    private val stars = List(8) {
        Triple(
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat() * 1.1f + 0.4f
        )
    }

    private val starPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(1.5f.toInt()).toFloat()
        color = MistTheme.border
    }

    private val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = dp(3).toFloat()
        color = Color.argb(35, 85, 165, 255)
    }

    private val label = TextView(context).apply {
        text = keyText

        textSize = when {
            keyText == "space" -> 13f
            keyText.length > 2 -> 13f
            keyText == "⌫" || keyText == "⇧" -> 21f
            else -> 21f
        }

        gravity = Gravity.CENTER
        includeFontPadding = false
        setTextColor(Color.WHITE)

        typeface = Typeface.create(
            "sans-serif-medium",
            Typeface.NORMAL
        )

        setPadding(dp(2), dp(2), dp(2), dp(2))
    }

    init {
        background = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(
                Color.rgb(24, 48, 82),
                Color.rgb(9, 19, 39),
                Color.rgb(13, 29, 54)
            )
        ).apply {
            cornerRadius = this@StarKeyView.cornerRadius

            setStroke(
                dp(2),
                MistTheme.border
            )
        }

        elevation = dp(2).toFloat()

        addView(
            label,
            LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
        )

        setWillNotDraw(false)

        isClickable = true
        isFocusable = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val rect = RectF(
            dp(2).toFloat(),
            dp(2).toFloat(),
            width - dp(2).toFloat(),
            height - dp(2).toFloat()
        )

        canvas.drawRoundRect(
            rect,
            cornerRadius,
            cornerRadius,
            glowPaint
        )

        stars.forEachIndexed { index, star ->
            starPaint.color = if (index % 4 == 0) {
                MistTheme.blueGlow
            } else {
                MistTheme.star
            }

            starPaint.alpha = if (index % 3 == 0) 190 else 75

            canvas.drawCircle(
                width * star.first,
                height * star.second,
                star.third,
                starPaint
            )
        }

        val borderRect = RectF(
            dp(1).toFloat(),
            dp(1).toFloat(),
            width - dp(1).toFloat(),
            height - dp(1).toFloat()
        )

        canvas.drawRoundRect(
            borderRect,
            cornerRadius,
            cornerRadius,
            borderPaint
        )
    }

    fun setLabel(value: String) {
        label.text = value
    }
}
