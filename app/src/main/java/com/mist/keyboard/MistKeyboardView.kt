
package com.mist.keyboard

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.*
import kotlin.random.Random
import android.app.AlertDialog

class MistKeyboardView(
    private val service: MistKeyboardService,
    private val fontManager: FontManager
) : LinearLayout(service) {

    private var shifted = true
    private var symbols = false
    private var capsLocked = false

    private val normalRows = listOf(
        "qwertyuiop",
        "asdfghjkl",
        "⇧zxcvbnm⌫"
    )

    private val symbolRows = listOf(
        "1234567890",
        "@#$%&-+()",
        "⇧*/:;!?'⌫"
    )

    private val keyViews = mutableListOf<TextView>()
    private lateinit var keysContainer: LinearLayout
    private lateinit var fontLabel: TextView

    private val backgroundColor = Color.rgb(3, 8, 23)

    init {
        orientation = VERTICAL
        setPadding(5, 7, 5, 9)
        background = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(
                Color.rgb(2, 5, 18),
                Color.rgb(8, 19, 42),
                Color.rgb(3, 7, 20)
            )
        )

        val header = LinearLayout(service).apply {
            gravity = Gravity.CENTER_VERTICAL
            orientation = HORIZONTAL
        }

        val brand = TextView(service).apply {
            text = "✦ M I S T"
            textSize = 15f
            setTextColor(Color.rgb(151, 197, 255))
            setPadding(10, 8, 8, 8)
        }

        fontLabel = TextView(service).apply {
            textSize = 11f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(10, 8, 10, 8)
            background = keyBackground()
            setOnClickListener { showFontStudio() }
        }

        header.addView(brand, LayoutParams(0, 45, 1f))
        header.addView(fontLabel, LayoutParams(
            LayoutParams.WRAP_CONTENT, 38
        ))
        addView(header)

        val starLayer = StarField(service)
        addView(starLayer, LayoutParams(
            LayoutParams.MATCH_PARENT, 35
        ))

        keysContainer = LinearLayout(service).apply {
            orientation = VERTICAL
        }

        addView(keysContainer, LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        ))

        val bottom = LinearLayout(service).apply {
            orientation = HORIZONTAL
            gravity = Gravity.CENTER
        }

        addSpecialKey(bottom, "?123", 1.4f) {
            symbols = !symbols
            renderKeys()
        }

        addSpecialKey(bottom, ",", 0.8f) {
            commit(",")
        }

        addSpecialKey(bottom, "SPACE", 4f) {
            commit(" ")
        }

        addSpecialKey(bottom, ".", 0.8f) {
            commit(".")
        }

        addSpecialKey(bottom, "↵", 1.3f) {
            val connection = service.currentInputConnection
            connection?.let {
                if (!service.sendDefaultEditorAction(true)) {
                    it.commitText("\n", 1)
                }
            }
        }

        addView(bottom, LayoutParams(
            LayoutParams.MATCH_PARENT, 48
        ))

        updateFontLabel()
        renderKeys()
    }

    private fun keyBackground(): GradientDrawable {
        return GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(
                Color.rgb(20, 37, 65),
                Color.rgb(11, 22, 44)
            )
        ).apply {
            cornerRadius = 13f
            setStroke(1, Color.rgb(52, 88, 139))
        }
    }

    private fun renderKeys() {
        keysContainer.removeAllViews()
        keyViews.clear()

        val rows = if (symbols) symbolRows else normalRows

        rows.forEachIndexed { index, row ->
            val line = LinearLayout(service).apply {
                orientation = HORIZONTAL
                gravity = Gravity.CENTER
            }

            row.forEach { char ->
                val weight = if (
                    char == '⇧' || char == '⌫'
                ) 1.35f else 1f

                val label = when (char) {
                    '⇧' -> "⇧"
                    '⌫' -> "⌫"
                    else -> if (shifted) char.uppercaseChar().toString()
                        else char.toString()
                }

                val key = TextView(service).apply {
                    text = label
                    textSize = 18f
                    gravity = Gravity.CENTER
                    setTextColor(Color.WHITE)
                    background = keyBackground()
                    setPadding(1, 0, 1, 0)

                    setOnClickListener {
                        when (char) {
                            '⇧' -> {
                                if (shifted && !capsLocked) {
                                    capsLocked = true
                                } else if (capsLocked) {
                                    capsLocked = false
                                    shifted = false
                                } else {
                                    shifted = true
                                }
                                renderKeys()
                            }

                            '⌫' -> {
                                service.currentInputConnection
                                    ?.deleteSurroundingText(1, 0)
                            }

                            else -> {
                                val value = if (shifted) {
                                    char.uppercaseChar().toString()
                                } else {
                                    char.toString()
                                }
                                commit(value)

                                if (!capsLocked) {
                                    shifted = false
                                    renderKeys()
                                }
                            }
                        }
                    }
                }

                keyViews.add(key)

                val params = LayoutParams(0, 48, weight)
                params.setMargins(2, 3, 2, 3)
                line.addView(key, params)
            }

            val rowParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )

            keysContainer.addView(line, rowParams)
        }
    }

    private fun addSpecialKey(
        parent: LinearLayout,
        text: String,
        weight: Float,
        action: () -> Unit
    ) {
        val key = TextView(service).apply {
            this.text = text
            textSize = 14f
            gravity = Gravity.CENTER
            setTextColor(Color.WHITE)
            background = keyBackground()
            setOnClickListener { action() }
        }

        val params = LayoutParams(0, 43, weight)
        params.setMargins(2, 2, 2, 2)
        parent.addView(key, params)
    }

    private fun commit(text: String) {
        val converted = fontManager.transform(text)
        service.currentInputConnection?.commitText(converted, 1)
    }

    private fun updateFontLabel() {
        fontLabel.text =
            "${fontManager.family.displayName} ▾"
    }

    private fun showFontStudio() {
        val families = FontFamily.values()
        val familyNames = families.map { it.displayName }

        AlertDialog.Builder(service)
            .setTitle("✦ MIST FONT STUDIO")
            .setItems(familyNames.toTypedArray()) { _, familyIndex ->
                val family = families[familyIndex]
                showVariations(family)
            }
            .setNegativeButton("Close", null)
            .show()
    }

    private fun showVariations(family: FontFamily) {
        val variations = FontVariation.values()
        val names = variations.map { it.displayName }

        AlertDialog.Builder(service)
            .setTitle(family.displayName)
            .setItems(names.toTypedArray()) { _, index ->
                fontManager.select(family, variations[index])
                updateFontLabel()
            }
            .setNegativeButton("Back") { _, _ ->
                showFontStudio()
            }
            .show()
    }
}

private class StarField(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val stars = List(100) {
        Triple(
            Random.nextFloat(),
            Random.nextFloat(),
            Random.nextFloat() * 1.8f + 0.4f
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        stars.forEachIndexed { index, star ->
            paint.color = Color.argb(
                if (index % 5 == 0) 230 else 120,
                165, 197, 255
            )

            canvas.drawCircle(
                star.first * width,
                star.second * height,
                star.third,
                paint
            )
        }
    }
}
