
package com.mist.keyboard

import android.content.Context
import android.view.View
import android.view.inputmethod.InputConnection

class EmojiKeyboardIntegration(
    context: Context,
    private val getInputConnection: () -> InputConnection?,
    private val onKeyboardChanged: (View) -> Unit,
    private val onEmojiPanelClosed: () -> Unit
) {

    private val recentStore = EmojiRecentStore(context)
    private val skinToneManager = EmojiSkinToneManager(context)

    private var emojiPanel: EmojiPanelController? = null
    private var isEmojiPanelVisible = false

    fun openEmojiPanel() {
        if (isEmojiPanelVisible) return

        val controller = EmojiPanelController(
            context = context,
            onEmojiSelected = { emoji ->
                insertEmoji(emoji)
            },
            onBackToKeyboard = {
                closeEmojiPanel()
            }
        )

        emojiPanel = controller

        val view = controller.createPanel()

        isEmojiPanelVisible = true
        onKeyboardChanged(view)
    }

    fun closeEmojiPanel() {
        if (!isEmojiPanelVisible) return

        emojiPanel?.destroy()
        emojiPanel = null
        isEmojiPanelVisible = false

        onEmojiPanelClosed()
    }

    fun toggleEmojiPanel() {
        if (isEmojiPanelVisible) {
            closeEmojiPanel()
        } else {
            openEmojiPanel()
        }
    }

    fun insertEmoji(emoji: String) {
        val connection = getInputConnection() ?: return

        val finalEmoji = skinToneManager.applyTone(emoji)

        connection.commitText(finalEmoji, 1)

        recentStore.add(finalEmoji)
    }

    fun deleteEmoji() {
        val connection = getInputConnection() ?: return

        connection.deleteSurroundingText(2, 0)
    }

    fun addFavourite(emoji: String) {
        recentStore.pin(emoji)
    }

    fun removeFavourite(emoji: String) {
        recentStore.unpin(emoji)
    }

    fun getRecentEmojis(): List<String> {
        return recentStore.getRecent()
    }

    fun getFavouriteEmojis(): List<String> {
        return recentStore.getPinned()
    }

    fun setSkinTone(tone: String) {
        skinToneManager.selectedTone = tone
    }

    fun isVisible(): Boolean {
        return isEmojiPanelVisible
    }

    fun destroy() {
        emojiPanel?.destroy()
        emojiPanel = null
        isEmojiPanelVisible = false
    }
}
