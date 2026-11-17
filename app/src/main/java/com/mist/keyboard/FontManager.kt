
package com.mist.keyboard

import android.content.Context

class FontManager(context: Context) {

    private val preferences = context.getSharedPreferences(
        "mist_font_preferences",
        Context.MODE_PRIVATE
    )

    var family: FontFamily
        get() = runCatching {
            FontFamily.valueOf(
                preferences.getString("family", "CUPERTINO")!!
            )
        }.getOrDefault(FontFamily.CUPERTINO)
        set(value) {
            preferences.edit()
                .putString("family", value.name)
                .apply()
        }

    var variation: FontVariation
        get() = runCatching {
            FontVariation.valueOf(
                preferences.getString("variation", "REGULAR")!!
            )
        }.getOrDefault(FontVariation.REGULAR)
        set(value) {
            preferences.edit()
                .putString("variation", value.name)
                .apply()
        }

    fun transform(text: String): String {
        return FontStyles.apply(text, family, variation)
    }

    fun select(
        newFamily: FontFamily,
        newVariation: FontVariation
    ) {
        family = newFamily
        variation = newVariation
    }
}
