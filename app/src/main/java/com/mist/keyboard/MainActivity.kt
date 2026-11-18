
package com.mist.keyboard

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.provider.Settings
import android.view.Gravity
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.widget.*

class MainActivity : Activity() {

    private val bg = Color.rgb(3, 7, 20)

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = bg
        window.navigationBarColor = bg

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(28), dp(24), dp(28), dp(24))

            background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(
                    Color.rgb(2, 4, 15),
                    Color.rgb(8, 19, 43),
                    Color.rgb(2, 4, 15)
                )
            )
        }

        val logo = ImageView(this).apply {
            setImageResource(R.drawable.ic_mist)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }

        root.addView(
            logo,
            LinearLayout.LayoutParams(dp(145), dp(145))
        )

        val title = TextView(this).apply {
            text = "M I S T"
            textSize = 30f
            letterSpacing = 0.18f
            gravity = Gravity.CENTER
            includeFontPadding = false
            setTextColor(Color.WHITE)

            typeface = Typeface.create(
                "sans-serif-light",
                Typeface.NORMAL
            )
        }

        root.addView(title)

        val subtitle = TextView(this).apply {
            text = "BEYOND ORDINARY TYPING"
            textSize = 10f
            letterSpacing = 0.22f
            gravity = Gravity.CENTER
            includeFontPadding = false
            setTextColor(Color.rgb(130, 177, 239))
            setPadding(0, dp(8), 0, dp(30))
        }

        root.addView(subtitle)

        addAction(root, "ENABLE MIST KEYBOARD") {
            startActivity(
                Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
            )
        }

        addAction(root, "SELECT MIST KEYBOARD") {
            val imm = getSystemService(
                android.view.inputmethod.InputMethodManager::class.java
            )
            imm.showInputMethodPicker()
        }

        addAction(root, "FONT STUDIO") {
            Toast.makeText(
                this,
                "Open Font Studio from the keyboard toolbar.",
                Toast.LENGTH_LONG
            ).show()
        }

        setContentView(root)
    }

    private fun addAction(
        root: LinearLayout,
        label: String,
        action: () -> Unit
    ) {
        val button = Button(this).apply {
            text = label
            textSize = 12f
            isAllCaps = false
            gravity = Gravity.CENTER
            includeFontPadding = false

            minHeight = dp(54)
            minimumHeight = dp(54)

            setPadding(dp(8), 0, dp(8), 0)
            setTextColor(Color.WHITE)

            background = GradientDrawable().apply {
                setColor(Color.rgb(16, 34, 62))
                cornerRadius = dp(18).toFloat()
                setStroke(
                    dp(1),
                    Color.rgb(65, 113, 174)
                )
            }

            setOnClickListener { action() }
        }

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dp(58)
        )

        params.setMargins(0, dp(7), 0, dp(7))

        root.addView(button, params)
    }
}
