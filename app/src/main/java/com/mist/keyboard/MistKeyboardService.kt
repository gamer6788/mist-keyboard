
package com.mist.keyboard

import android.inputmethodservice.InputMethodService
import android.view.View

class MistKeyboardService : InputMethodService() {

    lateinit var fontManager: FontManager
        private set

    override fun onCreate() {
        super.onCreate()
        fontManager = FontManager(this)
    }

    override fun onCreateInputView(): View {
        return MistKeyboardView(this, fontManager)
    }
}
