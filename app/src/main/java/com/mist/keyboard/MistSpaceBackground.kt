
package com.mist.keyboard

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable
import kotlin.random.Random

class MistSpaceBackground : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val stars = List(240) {
        Triple(
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat() * 1.6f + 0.3f
        )
    }

    override fun draw(canvas: Canvas) {
        val bounds = bounds
        val w = bounds.width().toFloat()
        val h = bounds.height().toFloat()

        if (w <= 0f || h <= 0f) return

        paint.shader = RadialGradient(
            w * 0.5f,
            h * 0.45f,
            w * 0.75f,
            intArrayOf(
                Color.rgb(15, 37, 75),
                Color.rgb(5, 13, 32),
                Color.rgb(2, 5, 17)
            ),
            null,
            Shader.TileMode.CLAMP
        )

        canvas.drawRect(bounds, paint)
        paint.shader = null

        stars.forEachIndexed { index, star ->
            paint.color = if (index % 9 == 0) {
                MistTheme.blueGlow
            } else {
                MistTheme.star
            }

            paint.alpha = if (index % 7 == 0) 230 else 100

            canvas.drawCircle(
                bounds.left + star.first * w,
                bounds.top + star.second * h,
                star.third,
                paint
            )
        }
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: android.graphics.ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}
