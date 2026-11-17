
package com.mist.keyboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.random.Random

class MistKeyboardView(context: Context) : LinearLayout(context) {

    private val rows = listOf(
        listOf("Q","W","E","R","T","Y","U","I","O","P"),
        listOf("A","S","D","F","G","H","J","K","L"),
        listOf("⇧","Z","X","C","V","B","N","M","⌫"),
        listOf("?123",",","🌌","SPACE",".","↵")
    )

    private var upperCase = true
    private var symbols = false

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        setPadding(5, 12, 5, 12)
        background = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(Color.rgb(3, 7, 22), Color.rgb(12, 23, 48))
        )

        val stars = StarField(context)
        addView(stars, LayoutParams(
            LayoutParams.MATCH_PARENT, 0, 1f
        ))

        rows.forEachIndexed { index, row ->
            val line = LinearLayout(context)
            line.orientation = HORIZONTAL
            line.gravity = Gravity.CENTER
            line.setPadding(0, 3, 0, 3)

            row.forEach { key ->
                val button = TextView(context).apply {
                    text = key
                    textSize = if (key == "SPACE") 12f else 17f
                    gravity = Gravity.CENTER
                    setTextColor(Color.WHITE)
                    typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                    setPadding(1, 0, 1, 0)

                    background = GradientDrawable().apply {
                        setColor(Color.rgb(17, 32, 58))
                        cornerRadius = 13f
                        setStroke(1, Color.rgb(52, 91, 139))
                    }

                    setOnClickListener {
                        handleKey(key)
                    }
                }

                val weight = when (key) {
                    "SPACE" -> 4f
                    "⇧", "⌫", "?123", "↵" -> 1.4f
                    else -> 1f
                }

                val params = LayoutParams(0, 51, weight)
                params.setMargins(2, 3, 2, 3)
                line.addView(button, params)
            }

            addView(line, LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT
            ))
        }
    }

    private fun handleKey(key: String) {
        val connection: InputConnection =
            (context as? android.inputmethodservice.InputMethodService)
                ?.currentInputConnection ?: return

        when (key) {
            "⇧" -> {
                upperCase = !upperCase
                return
            }

            "?123" -> {
                symbols = !symbols
                return
            }

            "⌫" -> connection.deleteSurroundingText(1, 0)

            "SPACE" -> connection.commitText(" ", 1)

            "↵" -> connection.commitText("\n", 1)

            "🌌" -> connection.commitText("✦ ", 1)

            else -> {
                val character = if (upperCase) key else key.lowercase()
                connection.commitText(character, 1)
            }
        }
    }
}

private class StarField(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val random = Random(42)

    private val stars = List(90) {
        Triple(random.nextFloat(), random.nextFloat(),
            random.nextFloat() * 2f + 0.5f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        stars.forEach { (x, y, radius) ->
            paint.color = Color.argb(
                random.nextInt(90, 230), 170, 205, 255
            )
            canvas.drawCircle(
                x * width,
                y * height,
                radius,
                paint
            )
        }
    }
}
