
package com.mist.keyboard

import android.inputmethodservice.InputMethodService
import android.view.View

class MistKeyboardService : InputMethodService() {

    lateinit var fontManager: FontManager
        private set

    lateinit var clipboardHistoryManager: ClipboardHistoryManager
        private set

    override fun onCreate() {
        super.onCreate()

        fontManager = FontManager(this)

        clipboardHistoryManager = ClipboardHistoryManager(this)
        clipboardHistoryManager.start()
    }

    override fun onCreateInputView(): View {
        return MistKeyboardView(this, fontManager)
    }

    override fun onDestroy() {
        if (::clipboardHistoryManager.isInitialized) {
            clipboardHistoryManager.stop()
        }

        super.onDestroy()
    }
}
