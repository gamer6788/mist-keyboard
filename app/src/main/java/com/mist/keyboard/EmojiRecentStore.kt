
package com.mist.keyboard

import android.content.Context

class EmojiRecentStore(context: Context) {

    private val preferences = context.getSharedPreferences(
        "mist_emoji_history",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_RECENTS = "recent_emojis"
        private const val KEY_PINNED = "pinned_emojis"
        private const val MAX_RECENTS = 40
    }

    fun getRecent(): List<String> {
        return preferences
            .getString(KEY_RECENTS, "")
            .orEmpty()
            .split("|")
            .filter { it.isNotBlank() }
            .distinct()
            .take(MAX_RECENTS)
    }

    fun add(emoji: String) {
        if (emoji.isBlank()) return

        val updated = listOf(emoji) +
            getRecent().filter { it != emoji }

        preferences.edit()
            .putString(
                KEY_RECENTS,
                updated.take(MAX_RECENTS).joinToString("|")
            )
            .apply()
    }

    fun getPinned(): List<String> {
        return preferences
            .getString(KEY_PINNED, "")
            .orEmpty()
            .split("|")
            .filter { it.isNotBlank() }
            .distinct()
    }

    fun pin(emoji: String) {
        if (emoji.isBlank()) return

        val updated = getPinned().toMutableList()

        if (emoji !in updated) {
            updated.add(0, emoji)
        }

        preferences.edit()
            .putString(KEY_PINNED, updated.joinToString("|"))
            .apply()
    }

    fun unpin(emoji: String) {
        val updated = getPinned().filterNot { it == emoji }

        preferences.edit()
            .putString(KEY_PINNED, updated.joinToString("|"))
            .apply()
    }

    fun clearRecent() {
        preferences.edit()
            .remove(KEY_RECENTS)
            .apply()
    }

    fun clearAll() {
        preferences.edit()
            .remove(KEY_RECENTS)
            .remove(KEY_PINNED)
            .apply()
    }

    fun isPinned(emoji: String): Boolean {
        return emoji in getPinned()
    }
}
