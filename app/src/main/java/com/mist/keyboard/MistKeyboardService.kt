
package com.mist.keyboard

import android.inputmethodservice.InputMethodService
import android.view.View

class MistKeyboardService : InputMethodService() {

    override fun onCreateInputView(): View {
        return MistKeyboardView(this)
    }
}
