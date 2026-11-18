
package com.mist.keyboard

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

class EmojiPanelController(
    private val context: Context,
    private val onEmojiSelected: (String) -> Unit,
    private val onBackToKeyboard: () -> Unit
) {

    private val recentStore = EmojiRecentStore(context)
    private val skinToneManager = EmojiSkinToneManager(context)
    private val searchEngine = EmojiSearchEngine()

    private var panel: EmojiPickerView? = null

    fun createPanel(): View {
        val emojiPanel = EmojiPickerView(
            context = context,
            onEmojiSelected = { emoji ->
                recentStore.add(emoji)
                onEmojiSelected(
                    skinToneManager.applyTone(emoji)
                )
            },
            onBackToKeyboard = onBackToKeyboard,
            recentEmojis = recentStore.getRecent()
        )

        panel = emojiPanel
        return emojiPanel
    }

    fun getRecentEmojis(): List<String> {
        return recentStore.getRecent()
    }

    fun getPinnedEmojis(): List<String> {
        return recentStore.getPinned()
    }

    fun pinEmoji(emoji: String) {
        recentStore.pin(emoji)
    }

    fun unpinEmoji(emoji: String) {
        recentStore.unpin(emoji)
    }

    fun clearRecentEmojis() {
        recentStore.clearRecent()
    }

    fun setSkinTone(tone: String) {
        skinToneManager.selectedTone = tone
    }

    fun getSelectedSkinTone(): String {
        return skinToneManager.selectedTone
    }

    fun searchEmojis(query: String): List<EmojiItem> {
        return searchEngine.search(query)
    }

    fun refresh() {
        panel?.let { view ->
            (view.parent as? ViewGroup)?.removeView(view)
            panel = null
        }
    }

    fun destroy() {
        refresh()
    }
}
