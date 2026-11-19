
package com.mist.keyboard

import android.content.Context

class FontManager(context: Context) {

    private val preferences = context.applicationContext
        .getSharedPreferences(
            "mist_font_preferences",
            Context.MODE_PRIVATE
        )

    var family: FontFamily
        get() {
            val saved = preferences.getString(
                "family",
                FontFamily.CUPERTINO.name
            )

            return runCatching {
                FontFamily.valueOf(
                    saved ?: FontFamily.CUPERTINO.name
                )
            }.getOrDefault(FontFamily.CUPERTINO)
        }
        private set(value) {
            preferences.edit()
                .putString("family", value.name)
                .apply()
        }

    var variation: FontVariation
        get() {
            val saved = preferences.getString(
                "variation",
                FontVariation.REGULAR.name
            )

            return runCatching {
                FontVariation.valueOf(
                    saved ?: FontVariation.REGULAR.name
                )
            }.getOrDefault(FontVariation.REGULAR)
        }
        private set(value) {
            preferences.edit()
                .putString("variation", value.name)
                .apply()
        }

    fun transform(text: String): String {
        return runCatching {
            FontStyles.apply(text, family, variation)
        }.getOrDefault(text)
    }

    fun select(
        newFamily: FontFamily,
        newVariation: FontVariation
    ) {
        preferences.edit()
            .putString("family", newFamily.name)
            .putString("variation", newVariation.name)
            .apply()
    }

    fun reset() {
        select(
            FontFamily.CUPERTINO,
            FontVariation.REGULAR
        )
    }
}
