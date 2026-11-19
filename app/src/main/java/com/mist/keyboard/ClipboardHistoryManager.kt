
package com.mist.keyboard

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import org.json.JSONArray

class ClipboardHistoryManager(
    context: Context
) {

    private val appContext = context.applicationContext

    private val clipboardManager =
        appContext.getSystemService(
            Context.CLIPBOARD_SERVICE
        ) as ClipboardManager

    private val preferences =
        appContext.getSharedPreferences(
            "mist_clipboard_history",
            Context.MODE_PRIVATE
        )

    private val history = mutableListOf<String>()

    private val maxHistory = 20

    private var isListening = false

    private val clipboardListener =
        ClipboardManager.OnPrimaryClipChangedListener {
            readClipboard()
        }

    init {
        loadHistory()
    }

    fun start() {
        if (isListening) return

        clipboardManager.addPrimaryClipChangedListener(
            clipboardListener
        )

        isListening = true

        readClipboard()
    }

    fun stop() {
        if (!isListening) return

        clipboardManager.removePrimaryClipChangedListener(
            clipboardListener
        )

        isListening = false
    }

    private fun readClipboard() {
        try {
            val clip = clipboardManager.primaryClip
                ?: return

            for (index in 0 until clip.itemCount) {
                val item = clip.getItemAt(index)

                val text = item.coerceToText(appContext)
                    ?.toString()
                    ?.trim()
                    ?: continue

                if (text.isNotEmpty()) {
                    addText(text)
                }
            }
        } catch (_: SecurityException) {
            // Android may restrict clipboard access.
        } catch (_: Exception) {
            // Ignore invalid clipboard contents.
        }
    }

    @Synchronized
    fun addText(text: String) {
        val cleanText = text.trim()

        if (cleanText.isEmpty()) return

        history.remove(cleanText)

        history.add(0, cleanText)

        while (history.size > maxHistory) {
            history.removeAt(history.lastIndex)
        }

        saveHistory()
    }

    @Synchronized
    fun getHistory(): List<String> {
        return history.toList()
    }

    @Synchronized
    fun clearHistory() {
        history.clear()

        preferences.edit()
            .remove("history")
            .apply()
    }

    @Synchronized
    fun removeText(text: String) {
        history.remove(text)
        saveHistory()
    }

    private fun loadHistory() {
        try {
            val saved = preferences.getString(
                "history",
                "[]"
            ) ?: "[]"

            val json = JSONArray(saved)

            history.clear()

            for (index in 0 until json.length()) {
                val text = json.optString(index)

                if (text.isNotBlank() && !history.contains(text)) {
                    history.add(text)
                }

                if (history.size >= maxHistory) break
            }
        } catch (_: Exception) {
            history.clear()
        }
    }

    private fun saveHistory() {
        val json = JSONArray()

        history.forEach { text ->
            json.put(text)
        }

        preferences.edit()
            .putString("history", json.toString())
            .apply()
    }
}
