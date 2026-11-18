
package com.mist.keyboard

import android.content.Context

class EmojiSkinToneManager(context: Context) {

    companion object {
        private const val PREFS_NAME = "mist_emoji_skin_tone"
        private const val KEY_DEFAULT_TONE = "default_tone"

        const val DEFAULT = ""
        const val LIGHT = "\uD83C\uDFFB"
        const val MEDIUM_LIGHT = "\uD83C\uDFFC"
        const val MEDIUM = "\uD83C\uDFFD"
        const val MEDIUM_DARK = "\uD83C\uDFFE"
        const val DARK = "\uD83C\uDFFF"
    }

    private val preferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    var selectedTone: String
        get() = preferences.getString(KEY_DEFAULT_TONE, DEFAULT) ?: DEFAULT
        set(value) {
            if (value in supportedTones) {
                preferences.edit()
                    .putString(KEY_DEFAULT_TONE, value)
                    .apply()
            }
        }

    private val supportedTones = setOf(
        DEFAULT,
        LIGHT,
        MEDIUM_LIGHT,
        MEDIUM,
        MEDIUM_DARK,
        DARK
    )

    fun applyTone(emoji: String): String {
        if (emoji.isBlank() || selectedTone.isEmpty()) {
            return emoji
        }

        val variants = EmojiData.skinToneVariants(emoji)

        return variants.firstOrNull { variant ->
            variant.contains(selectedTone)
        } ?: emoji
    }

    fun clearTone() {
        selectedTone = DEFAULT
    }

    fun availableTones(): List<Pair<String, String>> {
        return listOf(
            "Default" to DEFAULT,
            "Light" to LIGHT,
            "Medium-light" to MEDIUM_LIGHT,
            "Medium" to MEDIUM,
            "Medium-dark" to MEDIUM_DARK,
            "Dark" to DARK
        )
    }
}
