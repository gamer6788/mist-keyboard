
package com.mist.keyboard

import android.content.Context

class EmojiPanelPreferences(context: Context) {

    private val preferences = context.getSharedPreferences(
        "mist_emoji_panel_preferences",
        Context.MODE_PRIVATE
    )

    fun saveCategory(category: EmojiCategory?) {
        preferences.edit()
            .putString("selected_category", category?.name ?: "")
            .apply()
    }

    fun getCategory(): EmojiCategory? {
        val name = preferences.getString(
            "selected_category",
            ""
        ).orEmpty()

        return EmojiCategory.values().firstOrNull {
            it.name == name
        }
    }

    fun saveTab(tab: EmojiPanelTab) {
        preferences.edit()
            .putString("selected_tab", tab.name)
            .apply()
    }

    fun getTab(): EmojiPanelTab {
        val name = preferences.getString(
            "selected_tab",
            EmojiPanelTab.EMOJI.name
        )

        return runCatching {
            EmojiPanelTab.valueOf(name ?: EmojiPanelTab.EMOJI.name)
        }.getOrDefault(EmojiPanelTab.EMOJI)
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
}
