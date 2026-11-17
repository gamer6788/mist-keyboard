
package com.mist.keyboard

import android.content.Context
import android.graphics.*
import android.view.View
import kotlin.random.Random

class MistSpaceBackground(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val stars = List(240) {
        Triple(
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat() * 1.6f + 0.3f
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.shader = RadialGradient(
            width * 0.5f,
            height * 0.45f,
            width * 0.65f,
            intArrayOf(
                Color.rgb(15, 37, 75),
                Color.rgb(5, 13, 32),
                Color.rgb(2, 5, 17)
            ),
            null,
            Shader.TileMode.CLAMP
        )

        canvas.drawRect(
            0f, 0f,
            width.toFloat(), height.toFloat(),
            paint
        )

        paint.shader = null

        stars.forEachIndexed { index, star ->
            paint.color = if (index % 9 == 0) {
                MistTheme.blueGlow
            } else {
                MistTheme.star
            }

            paint.alpha = if (index % 7 == 0) 230 else 100

            canvas.drawCircle(
                star.first * width,
                star.second * height,
                star.third,
                paint
            )
        }
    }
}
