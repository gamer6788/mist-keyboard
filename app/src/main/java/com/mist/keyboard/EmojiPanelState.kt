
package com.mist.keyboard

enum class EmojiPanelTab {
    EMOJI,
    FAVORITES,
    RECENTS
}

data class EmojiPanelState(
    val selectedCategory: EmojiCategory? = null,
    val selectedTab: EmojiPanelTab = EmojiPanelTab.EMOJI,
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
    val isVisible: Boolean = false
)

class EmojiPanelStateManager {

    var state: EmojiPanelState = EmojiPanelState()
        private set

    fun open() {
        state = state.copy(isVisible = true)
    }

    fun close() {
        state = state.copy(
            isVisible = false,
            searchQuery = "",
            isSearchActive = false
        )
    }

    fun selectCategory(category: EmojiCategory?) {
        state = state.copy(
            selectedCategory = category,
            selectedTab = EmojiPanelTab.EMOJI,
            searchQuery = "",
            isSearchActive = false
        )
    }

    fun selectTab(tab: EmojiPanelTab) {
        state = state.copy(
            selectedTab = tab,
            searchQuery = "",
            isSearchActive = false
        )
    }

    fun updateSearch(query: String) {
        state = state.copy(
            searchQuery = query,
            isSearchActive = query.isNotBlank(),
            selectedTab = EmojiPanelTab.EMOJI
        )
    }

    fun reset() {
        state = EmojiPanelState()
    }
}
