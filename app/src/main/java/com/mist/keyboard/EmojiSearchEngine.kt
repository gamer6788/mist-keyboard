
package com.mist.keyboard

class EmojiSearchEngine {

    private val aliases = mapOf(
        "happy" to listOf("smile", "grin", "joy", "laugh"),
        "sad" to listOf("cry", "unhappy", "disappointed"),
        "love" to listOf("heart", "kiss", "romance"),
        "angry" to listOf("mad", "rage", "furious"),
        "laugh" to listOf("funny", "lol", "haha"),
        "cool" to listOf("sunglasses", "awesome"),
        "fire" to listOf("flame", "hot", "lit"),
        "food" to listOf("eat", "meal", "hungry"),
        "car" to listOf("vehicle", "automobile"),
        "money" to listOf("cash", "rich", "dollar"),
        "beautiful" to listOf("pretty", "gorgeous"),
        "sleep" to listOf("tired", "night", "dream"),
        "fear" to listOf("scared", "afraid", "terrified"),
        "music" to listOf("song", "sing", "sound"),
        "gaming" to listOf("game", "controller", "play"),
        "celebration" to listOf("party", "birthday", "congratulations"),
        "weather" to listOf("rain", "sun", "cloud", "snow"),
        "space" to listOf("star", "planet", "moon", "rocket"),
        "technology" to listOf("computer", "phone", "robot"),
        "nature" to listOf("tree", "flower", "animal"),
        "travel" to listOf("flight", "holiday", "vacation"),
        "warning" to listOf("alert", "danger", "caution"),
        "thinking" to listOf("think", "wonder", "confused")
    )

    fun search(query: String): List<EmojiItem> {
        val normalized = query
            .trim()
            .lowercase()
            .replace(Regex("\\s+"), " ")

        if (normalized.isBlank()) {
            return emptyList()
        }

        val words = normalized.split(" ")

        val expandedWords = words.flatMap { word ->
            listOf(word) + aliases[word].orEmpty()
        }.toSet()

        return EmojiData.all()
            .map { emoji ->
                emoji to calculateScore(emoji, normalized, expandedWords)
            }
            .filter { it.second > 0 }
            .sortedWith(
                compareByDescending<Pair<EmojiItem, Int>> { it.second }
                    .thenBy { it.first.emoji }
            )
            .map { it.first }
    }

    private fun calculateScore(
        item: EmojiItem,
        query: String,
        expandedWords: Set<String>
    ): Int {
        val keywords = item.keywords.map {
            it.lowercase()
        }

        var score = 0

        for (word in expandedWords) {
            if (keywords.any { it == word }) {
                score += 10
            } else if (keywords.any { it.startsWith(word) }) {
                score += 6
            } else if (keywords.any { it.contains(word) }) {
                score += 3
            }
        }

        if (keywords.any { it == query }) {
            score += 20
        }

        if (item.emoji == query) {
            score += 100
        }

        return score
    }

    fun suggestions(query: String, limit: Int = 8): List<EmojiItem> {
        return search(query).take(limit.coerceAtLeast(0))
    }

    fun hasResults(query: String): Boolean {
        return search(query).isNotEmpty()
    }
}
