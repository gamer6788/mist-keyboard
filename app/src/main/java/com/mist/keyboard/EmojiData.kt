
package com.mist.keyboard

/**
 * MIST Keyboard emoji data engine.
 *
 * Pure Kotlin implementation:
 * - No external dependencies
 * - No Android-specific APIs
 * - Compatible with the project's Kotlin 2.1.20 setup
 */
enum class EmojiCategory(
    val title: String,
    val icon: String
) {
    SMILEYS("Smileys", "😀"),
    PEOPLE("People", "👋"),
    ANIMALS("Animals", "🐻"),
    FOOD("Food", "🍕"),
    TRAVEL("Travel", "🚀"),
    ACTIVITIES("Activities", "⚽"),
    OBJECTS("Objects", "💡"),
    SYMBOLS("Symbols", "❤️"),
    FLAGS("Flags", "🏳️")
}

data class EmojiItem(
    val emoji: String,
    val category: EmojiCategory,
    val keywords: List<String> = emptyList()
)

object EmojiData {

    private fun item(
        emoji: String,
        category: EmojiCategory,
        vararg keywords: String
    ) = EmojiItem(
        emoji = emoji,
        category = category,
        keywords = keywords.toList()
    )

    private val emojiList = listOf(

        // SMILEYS
        item("😀", EmojiCategory.SMILEYS, "happy", "smile", "grin"),
        item("😃", EmojiCategory.SMILEYS, "happy", "smile"),
        item("😄", EmojiCategory.SMILEYS, "laugh", "happy"),
        item("😁", EmojiCategory.SMILEYS, "grin", "teeth"),
        item("😆", EmojiCategory.SMILEYS, "laugh", "funny"),
        item("😅", EmojiCategory.SMILEYS, "sweat", "nervous"),
        item("😂", EmojiCategory.SMILEYS, "joy", "tears", "laugh"),
        item("🤣", EmojiCategory.SMILEYS, "rolling", "laugh"),
        item("🥲", EmojiCategory.SMILEYS, "smile", "tear"),
        item("🥹", EmojiCategory.SMILEYS, "holding", "tears", "emotional"),
        item("☺️", EmojiCategory.SMILEYS, "smile", "happy"),
        item("😊", EmojiCategory.SMILEYS, "blush", "happy"),
        item("😇", EmojiCategory.SMILEYS, "angel", "halo"),
        item("🙂", EmojiCategory.SMILEYS, "smile"),
        item("🙃", EmojiCategory.SMILEYS, "upside", "silly"),
        item("😉", EmojiCategory.SMILEYS, "wink"),
        item("😌", EmojiCategory.SMILEYS, "relieved", "calm"),
        item("😍", EmojiCategory.SMILEYS, "love", "heart", "eyes"),
        item("🥰", EmojiCategory.SMILEYS, "love", "hearts"),
        item("😘", EmojiCategory.SMILEYS, "kiss", "love"),
        item("😗", EmojiCategory.SMILEYS, "kiss"),
        item("😙", EmojiCategory.SMILEYS, "kiss", "smile"),
        item("😚", EmojiCategory.SMILEYS, "kiss", "blush"),
        item("😋", EmojiCategory.SMILEYS, "yummy", "food"),
        item("😛", EmojiCategory.SMILEYS, "tongue", "silly"),
        item("😜", EmojiCategory.SMILEYS, "wink", "tongue"),
        item("🤪", EmojiCategory.SMILEYS, "crazy", "silly"),
        item("😝", EmojiCategory.SMILEYS, "tongue"),
        item("🤑", EmojiCategory.SMILEYS, "money", "rich"),
        item("🤗", EmojiCategory.SMILEYS, "hug"),
        item("🤭", EmojiCategory.SMILEYS, "giggle", "secret"),
        item("🫢", EmojiCategory.SMILEYS, "surprised", "shock"),
        item("🫣", EmojiCategory.SMILEYS, "peeking", "shy"),
        item("🤫", EmojiCategory.SMILEYS, "quiet", "secret"),
        item("🤔", EmojiCategory.SMILEYS, "think", "thinking"),
        item("🫡", EmojiCategory.SMILEYS, "salute", "respect"),
        item("🤐", EmojiCategory.SMILEYS, "silent", "zip"),
        item("🤨", EmojiCategory.SMILEYS, "suspicious"),
        item("😐", EmojiCategory.SMILEYS, "neutral"),
        item("😑", EmojiCategory.SMILEYS, "expressionless"),
        item("😶", EmojiCategory.SMILEYS, "silent", "speechless"),
        item("🫥", EmojiCategory.SMILEYS, "invisible", "hidden"),
        item("😏", EmojiCategory.SMILEYS, "smirk"),
        item("😒", EmojiCategory.SMILEYS, "unamused"),
        item("🙄", EmojiCategory.SMILEYS, "eye roll"),
        item("😬", EmojiCategory.SMILEYS, "grimace"),
        item("😮‍💨", EmojiCategory.SMILEYS, "sigh", "relief"),
        item("🤥", EmojiCategory.SMILEYS, "lie", "pinocchio"),
        item("😔", EmojiCategory.SMILEYS, "sad"),
        item("🥺", EmojiCategory.SMILEYS, "pleading", "puppy eyes"),
        item("😢", EmojiCategory.SMILEYS, "cry", "sad"),
        item("😭", EmojiCategory.SMILEYS, "cry", "sob"),
        item("😤", EmojiCategory.SMILEYS, "angry", "triumph"),
        item("😠", EmojiCategory.SMILEYS, "angry"),
        item("😡", EmojiCategory.SMILEYS, "rage", "angry"),
        item("🤬", EmojiCategory.SMILEYS, "swearing", "angry"),
        item("🤯", EmojiCategory.SMILEYS, "mind blown", "shock"),
        item("😳", EmojiCategory.SMILEYS, "flushed", "embarrassed"),
        item("🥵", EmojiCategory.SMILEYS, "hot", "heat"),
        item("🥶", EmojiCategory.SMILEYS, "cold", "freezing"),
        item("😱", EmojiCategory.SMILEYS, "scream", "fear"),
        item("😨", EmojiCategory.SMILEYS, "afraid", "scared"),
        item("😰", EmojiCategory.SMILEYS, "anxious", "sweat"),
        item("😥", EmojiCategory.SMILEYS, "sad", "disappointed"),
        item("😓", EmojiCategory.SMILEYS, "sweat", "sad"),
        item("🫠", EmojiCategory.SMILEYS, "melting"),
        item("🤤", EmojiCategory.SMILEYS, "drool"),
        item("😴", EmojiCategory.SMILEYS, "sleep", "tired"),
        item("🤤", EmojiCategory.SMILEYS, "sleepy", "drooling"),
        item("🥳", EmojiCategory.SMILEYS, "party", "celebrate"),
        item("🤩", EmojiCategory.SMILEYS, "star", "excited"),
        item("😎", EmojiCategory.SMILEYS, "cool", "sunglasses"),
        item("🤓", EmojiCategory.SMILEYS, "nerd", "glasses"),
        item("🧐", EmojiCategory.SMILEYS, "monocle", "curious"),
        item("😈", EmojiCategory.SMILEYS, "devil", "mischief"),
        item("👿", EmojiCategory.SMILEYS, "angry", "devil"),
        item("💀", EmojiCategory.SMILEYS, "skull", "dead"),
        item("☠️", EmojiCategory.SMILEYS, "skull", "danger"),
        item("👻", EmojiCategory.SMILEYS, "ghost", "spooky"),
        item("👽", EmojiCategory.SMILEYS, "alien"),
        item("🤖", EmojiCategory.SMILEYS, "robot"),
        item("💩", EmojiCategory.SMILEYS, "poop", "funny"),

        // PEOPLE AND HANDS
        item("👋", EmojiCategory.PEOPLE, "wave", "hello", "bye"),
        item("🤚", EmojiCategory.PEOPLE, "raised back hand"),
        item("🖐️", EmojiCategory.PEOPLE, "hand", "fingers"),
        item("✋", EmojiCategory.PEOPLE, "stop", "hand"),
        item("🖖", EmojiCategory.PEOPLE, "vulcan", "spock"),
        item("👌", EmojiCategory.PEOPLE, "okay", "perfect"),
        item("🤌", EmojiCategory.PEOPLE, "pinched fingers"),
        item("🤏", EmojiCategory.PEOPLE, "small", "tiny"),
        item("✌️", EmojiCategory.PEOPLE, "peace", "victory"),
        item("🤞", EmojiCategory.PEOPLE, "luck", "fingers crossed"),
        item("🫰", EmojiCategory.PEOPLE, "finger heart"),
        item("🤟", EmojiCategory.PEOPLE, "love you"),
        item("🤘", EmojiCategory.PEOPLE, "rock"),
        item("🤙", EmojiCategory.PEOPLE, "call", "shaka"),
        item("👈", EmojiCategory.PEOPLE, "left"),
        item("👉", EmojiCategory.PEOPLE, "right"),
        item("👆", EmojiCategory.PEOPLE, "up"),
        item("👇", EmojiCategory.PEOPLE, "down"),
        item("☝️", EmojiCategory.PEOPLE, "point up"),
        item("👍", EmojiCategory.PEOPLE, "thumbs up", "yes", "like"),
        item("👎", EmojiCategory.PEOPLE, "thumbs down", "no", "dislike"),
        item("✊", EmojiCategory.PEOPLE, "fist"),
        item("👊", EmojiCategory.PEOPLE, "punch", "fist"),
        item("👏", EmojiCategory.PEOPLE, "clap", "applause"),
        item("🙌", EmojiCategory.PEOPLE, "celebrate", "hands up"),
        item("👐", EmojiCategory.PEOPLE, "open hands"),
        item("🤝", EmojiCategory.PEOPLE, "handshake", "deal"),
        item("🙏", EmojiCategory.PEOPLE, "pray", "please", "thanks"),
        item("💪", EmojiCategory.PEOPLE, "strong", "muscle"),
        item("🫶", EmojiCategory.PEOPLE, "heart hands", "love"),
        item("💅", EmojiCategory.PEOPLE, "nails", "beauty"),
        item("🧠", EmojiCategory.PEOPLE, "brain", "smart"),
        item("👀", EmojiCategory.PEOPLE, "eyes", "look"),
        item("👁️", EmojiCategory.PEOPLE, "eye"),
        item("👄", EmojiCategory.PEOPLE, "mouth"),
        item("👶", EmojiCategory.PEOPLE, "baby"),
        item("🧒", EmojiCategory.PEOPLE, "child"),
        item("👦", EmojiCategory.PEOPLE, "boy"),
        item("👧", EmojiCategory.PEOPLE, "girl"),
        item("🧑", EmojiCategory.PEOPLE, "person"),
        item("👨", EmojiCategory.PEOPLE, "man"),
        item("👩", EmojiCategory.PEOPLE, "woman"),
        item("🧔", EmojiCategory.PEOPLE, "beard"),
        item("👴", EmojiCategory.PEOPLE, "old man"),
        item("👵", EmojiCategory.PEOPLE, "old woman"),
        item("🧑‍💻", EmojiCategory.PEOPLE, "technologist", "developer", "computer"),
        item("🧑‍🎓", EmojiCategory.PEOPLE, "student", "graduate"),
        item("🧑‍🚀", EmojiCategory.PEOPLE, "astronaut", "space"),
        item("🧑‍🍳", EmojiCategory.PEOPLE, "cook", "chef"),
        item("🕴️", EmojiCategory.PEOPLE, "suit", "business"),
        item("🕺", EmojiCategory.PEOPLE, "dance", "man"),
        item("💃", EmojiCategory.PEOPLE, "dance", "woman"),

        // ANIMALS AND NATURE
        item("🐶", EmojiCategory.ANIMALS, "dog", "puppy"),
        item("🐱", EmojiCategory.ANIMALS, "cat", "kitten"),
        item("🐭", EmojiCategory.ANIMALS, "mouse"),
        item("🐹", EmojiCategory.ANIMALS, "hamster"),
        item("🐰", EmojiCategory.ANIMALS, "rabbit", "bunny"),
        item("🦊", EmojiCategory.ANIMALS, "fox"),
        item("🐻", EmojiCategory.ANIMALS, "bear"),
        item("🐼", EmojiCategory.ANIMALS, "panda"),
        item("🐻‍❄️", EmojiCategory.ANIMALS, "polar bear"),
        item("🐨", EmojiCategory.ANIMALS, "koala"),
        item("🐯", EmojiCategory.ANIMALS, "tiger"),
        item("🦁", EmojiCategory.ANIMALS, "lion"),
        item("🐮", EmojiCategory.ANIMALS, "cow"),
        item("🐷", EmojiCategory.ANIMALS, "pig"),
        item("🐸", EmojiCategory.ANIMALS, "frog"),
        item("🐵", EmojiCategory.ANIMALS, "monkey"),
        item("🐔", EmojiCategory.ANIMALS, "chicken"),
        item("🐧", EmojiCategory.ANIMALS, "penguin"),
        item("🐦", EmojiCategory.ANIMALS, "bird"),
        item("🦆", EmojiCategory.ANIMALS, "duck"),
        item("🦅", EmojiCategory.ANIMALS, "eagle"),
        item("🦉", EmojiCategory.ANIMALS, "owl"),
        item("🦇", EmojiCategory.ANIMALS, "bat"),
        item("🐺", EmojiCategory.ANIMALS, "wolf"),
        item("🐴", EmojiCategory.ANIMALS, "horse"),
        item("🦄", EmojiCategory.ANIMALS, "unicorn"),
        item("🐝", EmojiCategory.ANIMALS, "bee"),
        item("🦋", EmojiCategory.ANIMALS, "butterfly"),
        item("🐌", EmojiCategory.ANIMALS, "snail"),
        item("🐞", EmojiCategory.ANIMALS, "ladybug"),
        item("🐢", EmojiCategory.ANIMALS, "turtle"),
        item("🐍", EmojiCategory.ANIMALS, "snake"),
        item("🦎", EmojiCategory.ANIMALS, "lizard"),
        item("🦖", EmojiCategory.ANIMALS, "dinosaur"),
        item("🐙", EmojiCategory.ANIMALS, "octopus"),
        item("🦀", EmojiCategory.ANIMALS, "crab"),
        item("🐠", EmojiCategory.ANIMALS, "fish"),
        item("🐬", EmojiCategory.ANIMALS, "dolphin"),
        item("🐳", EmojiCategory.ANIMALS, "whale"),
        item("🦈", EmojiCategory.ANIMALS, "shark"),
        item("🌸", EmojiCategory.ANIMALS, "flower", "cherry blossom"),
        item("🌹", EmojiCategory.ANIMALS, "rose", "flower"),
        item("🌻", EmojiCategory.ANIMALS, "sunflower"),
        item("🌲", EmojiCategory.ANIMALS, "tree"),
        item("🌴", EmojiCategory.ANIMALS, "palm tree"),
        item("🍀", EmojiCategory.ANIMALS, "clover", "luck"),
        item("🌵", EmojiCategory.ANIMALS, "cactus"),
        item("🌍", EmojiCategory.ANIMALS, "earth", "world"),
        item("🌙", EmojiCategory.ANIMALS, "moon"),
        item("⭐", EmojiCategory.ANIMALS, "star"),
        item("🌟", EmojiCategory.ANIMALS, "glowing star"),
        item("✨", EmojiCategory.ANIMALS, "sparkles", "magic"),
        item("🔥", EmojiCategory.ANIMALS, "fire", "hot"),
        item("🌈", EmojiCategory.ANIMALS, "rainbow"),
        item("⚡", EmojiCategory.ANIMALS, "lightning", "electric"),

        // FOOD AND DRINK
        item("🍏", EmojiCategory.FOOD, "green apple", "fruit"),
        item("🍎", EmojiCategory.FOOD, "apple", "fruit"),
        item("🍐", EmojiCategory.FOOD, "pear"),
        item("🍊", EmojiCategory.FOOD, "orange", "fruit"),
        item("🍋", EmojiCategory.FOOD, "lemon"),
        item("🍌", EmojiCategory.FOOD, "banana"),
        item("🍉", EmojiCategory.FOOD, "watermelon"),
        item("🍇", EmojiCategory.FOOD, "grapes"),
        item("🍓", EmojiCategory.FOOD, "strawberry"),
        item("🫐", EmojiCategory.FOOD, "blueberry"),
        item("🍒", EmojiCategory.FOOD, "cherry"),
        item("🍑", EmojiCategory.FOOD, "peach"),
        item("🥭", EmojiCategory.FOOD, "mango"),
        item("🍍", EmojiCategory.FOOD, "pineapple"),
        item("🥥", EmojiCategory.FOOD, "coconut"),
        item("🥝", EmojiCategory.FOOD, "kiwi"),
        item("🍅", EmojiCategory.FOOD, "tomato"),
        item("🥑", EmojiCategory.FOOD, "avocado"),
        item("🥕", EmojiCategory.FOOD, "carrot"),
        item("🌽", EmojiCategory.FOOD, "corn"),
        item("🌶️", EmojiCategory.FOOD, "chili", "pepper"),
        item("🥦", EmojiCategory.FOOD, "broccoli"),
        item("🥒", EmojiCategory.FOOD, "cucumber"),
        item("🍞", EmojiCategory.FOOD, "bread"),
        item("🥐", EmojiCategory.FOOD, "croissant"),
        item("🧀", EmojiCategory.FOOD, "cheese"),
        item("🥚", EmojiCategory.FOOD, "egg"),
        item("🍳", EmojiCategory.FOOD, "cooking", "egg"),
        item("🥞", EmojiCategory.FOOD, "pancakes"),
        item("🥓", EmojiCategory.FOOD, "bacon"),
        item("🍔", EmojiCategory.FOOD, "burger", "hamburger"),
        item("🍟", EmojiCategory.FOOD, "fries", "chips"),
        item("🍕", EmojiCategory.FOOD, "pizza"),
        item("🌭", EmojiCategory.FOOD, "hot dog"),
        item("🥪", EmojiCategory.FOOD, "sandwich"),
        item("🌮", EmojiCategory.FOOD, "taco"),
        item("🌯", EmojiCategory.FOOD, "burrito"),
        item("🍿", EmojiCategory.FOOD, "popcorn"),
        item("🍩", EmojiCategory.FOOD, "donut"),
        item("🍪", EmojiCategory.FOOD, "cookie"),
        item("🎂", EmojiCategory.FOOD, "birthday", "cake"),
        item("🍰", EmojiCategory.FOOD, "cake", "dessert"),
        item("🍫", EmojiCategory.FOOD, "chocolate"),
        item("🍬", EmojiCategory.FOOD, "candy"),
        item("🍭", EmojiCategory.FOOD, "lollipop"),
        item("🍦", EmojiCategory.FOOD, "ice cream"),
        item("☕", EmojiCategory.FOOD, "coffee"),
        item("🫖", EmojiCategory.FOOD, "teapot", "tea"),
        item("🧋", EmojiCategory.FOOD, "bubble tea", "boba"),
        item("🥤", EmojiCategory.FOOD, "drink", "cup"),
        item("🧃", EmojiCategory.FOOD, "juice"),

        // TRAVEL AND PLACES
        item("🚗", EmojiCategory.TRAVEL, "car"),
        item("🚕", EmojiCategory.TRAVEL, "taxi"),
        item("🚌", EmojiCategory.TRAVEL, "bus"),
        item("🚎", EmojiCategory.TRAVEL, "trolley"),
        item("🏎️", EmojiCategory.TRAVEL, "racing car"),
        item("🚓", EmojiCategory.TRAVEL, "police car"),
        item("🚑", EmojiCategory.TRAVEL, "ambulance"),
        item("🚒", EmojiCategory.TRAVEL, "fire truck"),
        item("🚲", EmojiCategory.TRAVEL, "bicycle", "bike"),
        item("🛵", EmojiCategory.TRAVEL, "scooter"),
        item("🏍️", EmojiCategory.TRAVEL, "motorcycle"),
        item("🚂", EmojiCategory.TRAVEL, "train"),
        item("✈️", EmojiCategory.TRAVEL, "airplane", "flight"),
        item("🛫", EmojiCategory.TRAVEL, "takeoff"),
        item("🚀", EmojiCategory.TRAVEL, "rocket", "space"),
        item("🛸", EmojiCategory.TRAVEL, "ufo"),
        item("🚁", EmojiCategory.TRAVEL, "helicopter"),
        item("⛵", EmojiCategory.TRAVEL, "sailboat"),
        item("🚢", EmojiCategory.TRAVEL, "ship"),
        item("⚓", EmojiCategory.TRAVEL, "anchor"),
        item("🏠", EmojiCategory.TRAVEL, "house", "home"),
        item("🏡", EmojiCategory.TRAVEL, "garden", "house"),
        item("🏢", EmojiCategory.TRAVEL, "office", "building"),
        item("🏰", EmojiCategory.TRAVEL, "castle"),
        item("🗼", EmojiCategory.TRAVEL, "tower"),
        item("🏖️", EmojiCategory.TRAVEL, "beach"),
        item("🏝️", EmojiCategory.TRAVEL, "island"),
        item("⛰️", EmojiCategory.TRAVEL, "mountain"),
        item("🌋", EmojiCategory.TRAVEL, "volcano"),
        item("🏕️", EmojiCategory.TRAVEL, "camping"),

        // ACTIVITIES
        item("⚽", EmojiCategory.ACTIVITIES, "football", "soccer"),
        item("🏀", EmojiCategory.ACTIVITIES, "basketball"),
        item("🏈", EmojiCategory.ACTIVITIES, "american football"),
        item("⚾", EmojiCategory.ACTIVITIES, "baseball"),
        item("🥎", EmojiCategory.ACTIVITIES, "softball"),
        item("🎾", EmojiCategory.ACTIVITIES, "tennis"),
        item("🏐", EmojiCategory.ACTIVITIES, "volleyball"),
        item("🏉", EmojiCategory.ACTIVITIES, "rugby"),
        item("🥏", EmojiCategory.ACTIVITIES, "frisbee"),
        item("🎱", EmojiCategory.ACTIVITIES, "billiards"),
        item("🏓", EmojiCategory.ACTIVITIES, "ping pong"),
        item("🏸", EmojiCategory.ACTIVITIES, "badminton"),
        item("🥊", EmojiCategory.ACTIVITIES, "boxing"),
        item("🥋", EmojiCategory.ACTIVITIES, "martial arts"),
        item("⛳", EmojiCategory.ACTIVITIES, "golf"),
        item("🎯", EmojiCategory.ACTIVITIES, "target"),
        item("🎮", EmojiCategory.ACTIVITIES, "gaming", "controller"),
        item("🎲", EmojiCategory.ACTIVITIES, "dice", "game"),
        item("🎹", EmojiCategory.ACTIVITIES, "piano", "music"),
        item("🎸", EmojiCategory.ACTIVITIES, "guitar", "music"),
        item("🎧", EmojiCategory.ACTIVITIES, "headphones", "music"),
        item("🎤", EmojiCategory.ACTIVITIES, "microphone", "singing"),
        item("🎬", EmojiCategory.ACTIVITIES, "movie", "film"),
        item("🎨", EmojiCategory.ACTIVITIES, "art", "painting"),
        item("🏆", EmojiCategory.ACTIVITIES, "trophy", "winner"),
        item("🥇", EmojiCategory.ACTIVITIES, "gold medal", "first"),
        item("🥈", EmojiCategory.ACTIVITIES, "silver medal", "second"),
        item("🥉", EmojiCategory.ACTIVITIES, "bronze medal", "third"),

        // OBJECTS
        item("💡", EmojiCategory.OBJECTS, "idea", "light bulb"),
        item("🔦", EmojiCategory.OBJECTS, "flashlight"),
        item("📱", EmojiCategory.OBJECTS, "phone", "mobile"),
        item("💻", EmojiCategory.OBJECTS, "laptop", "computer"),
        item("⌨️", EmojiCategory.OBJECTS, "keyboard"),
        item("🖥️", EmojiCategory.OBJECTS, "desktop", "computer"),
        item("🖱️", EmojiCategory.OBJECTS, "mouse"),
        item("🖨️", EmojiCategory.OBJECTS, "printer"),
        item("📷", EmojiCategory.OBJECTS, "camera", "photo"),
        item("📸", EmojiCategory.OBJECTS, "camera", "flash"),
        item("🔋", EmojiCategory.OBJECTS, "battery"),
        item("🔌", EmojiCategory.OBJECTS, "plug", "electric"),
        item("💾", EmojiCategory.OBJECTS, "save", "floppy"),
        item("💿", EmojiCategory.OBJECTS, "disc", "cd"),
        item("📀", EmojiCategory.OBJECTS, "dvd"),
        item("📚", EmojiCategory.OBJECTS, "books", "study"),
        item("📖", EmojiCategory.OBJECTS, "book", "reading"),
        item("✏️", EmojiCategory.OBJECTS, "pencil", "write"),
        item("🖊️", EmojiCategory.OBJECTS, "pen"),
        item("📝", EmojiCategory.OBJECTS, "note", "memo"),
        item("📌", EmojiCategory.OBJECTS, "pin"),
        item("📎", EmojiCategory.OBJECTS, "paperclip"),
        item("🔒", EmojiCategory.OBJECTS, "lock", "secure"),
        item("🔑", EmojiCategory.OBJECTS, "key"),
        item("🛠️", EmojiCategory.OBJECTS, "tools", "repair"),
        item("⚙️", EmojiCategory.OBJECTS, "settings", "gear"),
        item("🧭", EmojiCategory.OBJECTS, "compass"),
        item("⏰", EmojiCategory.OBJECTS, "alarm", "clock"),
        item("⌚", EmojiCategory.OBJECTS, "watch", "time"),
        item("💎", EmojiCategory.OBJECTS, "diamond", "gem"),
        
item("🎁", EmojiCategory.OBJECTS, "gift", "present"),
        item("🎈", EmojiCategory.OBJECTS, "balloon"),
        item("🕯️", EmojiCategory.OBJECTS, "candle"),
        item("🪞", EmojiCategory.OBJECTS, "mirror"),
        item("🧸", EmojiCategory.OBJECTS, "teddy bear", "toy"),

        // SYMBOLS
        item("❤️", EmojiCategory.SYMBOLS, "red heart", "love"),
        item("🧡", EmojiCategory.SYMBOLS, "orange heart"),
        item("💛", EmojiCategory.SYMBOLS, "yellow heart"),
        item("💚", EmojiCategory.SYMBOLS, "green heart"),
        item("💙", EmojiCategory.SYMBOLS, "blue heart"),
        item("💜", EmojiCategory.SYMBOLS, "purple heart"),
        item("🖤", EmojiCategory.SYMBOLS, "black heart"),
        item("🤍", EmojiCategory.SYMBOLS, "white heart"),
        item("🤎", EmojiCategory.SYMBOLS, "brown heart"),
        item("🩷", EmojiCategory.SYMBOLS, "pink heart"),
        item("🩵", EmojiCategory.SYMBOLS, "light blue heart"),
        item("🩶", EmojiCategory.SYMBOLS, "grey heart"),
        item("💔", EmojiCategory.SYMBOLS, "broken heart"),
        item("💕", EmojiCategory.SYMBOLS, "two hearts"),
        item("💞", EmojiCategory.SYMBOLS, "revolving hearts"),
        item("💓", EmojiCategory.SYMBOLS, "beating heart"),
        item("💗", EmojiCategory.SYMBOLS, "growing heart"),
        item("💖", EmojiCategory.SYMBOLS, "sparkling heart"),
        item("💘", EmojiCategory.SYMBOLS, "heart arrow"),
        item("💝", EmojiCategory.SYMBOLS, "heart gift"),
        item("💟", EmojiCategory.SYMBOLS, "heart decoration"),
        item("❣️", EmojiCategory.SYMBOLS, "heart exclamation"),
        item("❤️‍🔥", EmojiCategory.SYMBOLS, "heart on fire"),
        item("❤️‍🩹", EmojiCategory.SYMBOLS, "healing heart"),
        item("💯", EmojiCategory.SYMBOLS, "hundred", "perfect"),
        item("💢", EmojiCategory.SYMBOLS, "anger"),
        item("💥", EmojiCategory.SYMBOLS, "collision", "boom"),
        item("💫", EmojiCategory.SYMBOLS, "dizzy", "stars"),
        item("💦", EmojiCategory.SYMBOLS, "sweat", "water"),
        item("💨", EmojiCategory.SYMBOLS, "dash", "wind"),
        item("💬", EmojiCategory.SYMBOLS, "speech", "chat"),
        item("💭", EmojiCategory.SYMBOLS, "thought"),
        item("✔️", EmojiCategory.SYMBOLS, "check", "done"),
        item("❌", EmojiCategory.SYMBOLS, "cross", "wrong"),
        item("❗", EmojiCategory.SYMBOLS, "exclamation"),
        item("❓", EmojiCategory.SYMBOLS, "question"),
        item("⚠️", EmojiCategory.SYMBOLS, "warning"),
        item("♻️", EmojiCategory.SYMBOLS, "recycle"),
        item("🔴", EmojiCategory.SYMBOLS, "red circle"),
        item("🟠", EmojiCategory.SYMBOLS, "orange circle"),
        item("🟡", EmojiCategory.SYMBOLS, "yellow circle"),
        item("🟢", EmojiCategory.SYMBOLS, "green circle"),
        item("🔵", EmojiCategory.SYMBOLS, "blue circle"),
        item("🟣", EmojiCategory.SYMBOLS, "purple circle"),
        item("⚫", EmojiCategory.SYMBOLS, "black circle"),
        item("⚪", EmojiCategory.SYMBOLS, "white circle"),

        // FLAGS
        item("🏳️", EmojiCategory.FLAGS, "white flag"),
        item("🏴", EmojiCategory.FLAGS, "black flag"),
        item("🏁", EmojiCategory.FLAGS, "checkered flag", "finish"),
        item("🚩", EmojiCategory.FLAGS, "red flag"),
        item("🏳️‍🌈", EmojiCategory.FLAGS, "rainbow flag"),
        item("🏳️‍⚧️", EmojiCategory.FLAGS, "transgender flag"),
        item("🇳🇬", EmojiCategory.FLAGS, "nigeria", "nigerian"),
        item("🇬🇭", EmojiCategory.FLAGS, "ghana"),
        item("🇿🇦", EmojiCategory.FLAGS, "south africa"),
        item("🇺🇸", EmojiCategory.FLAGS, "united states", "usa"),
        item("🇬🇧", EmojiCategory.FLAGS, "united kingdom", "britain"),
        item("🇨🇦", EmojiCategory.FLAGS, "canada"),
        item("🇫🇷", EmojiCategory.FLAGS, "france"),
        item("🇩🇪", EmojiCategory.FLAGS, "germany"),
        item("🇯🇵", EmojiCategory.FLAGS, "japan"),
        item("🇨🇳", EmojiCategory.FLAGS, "china"),
        item("🇧🇷", EmojiCategory.FLAGS, "brazil"),
        item("🇮🇳", EmojiCategory.FLAGS, "india"),
        item("🇦🇺", EmojiCategory.FLAGS, "australia"),
        item("🇰🇷", EmojiCategory.FLAGS, "south korea")
    )

    private val uniqueEmojiList: List<EmojiItem> by lazy {
        emojiList.distinctBy { it.emoji }
    }

    fun all(): List<EmojiItem> = uniqueEmojiList

    fun byCategory(category: EmojiCategory): List<EmojiItem> {
        return uniqueEmojiList.filter {
            it.category == category
        }
    }

    fun categories(): List<EmojiCategory> {
        return EmojiCategory.values().toList()
    }

    fun search(query: String): List<EmojiItem> {
        val normalized = query.trim().lowercase()

        if (normalized.isEmpty()) return all()

        return uniqueEmojiList.filter { item ->
            item.emoji.contains(normalized) ||
                item.keywords.any { keyword ->
                    keyword.lowercase().contains(normalized)
                }
        }
    }

    fun find(emoji: String): EmojiItem? {
        return uniqueEmojiList.firstOrNull {
            it.emoji == emoji
        }
    }

    fun recentlyUsed(recent: List<String>): List<EmojiItem> {
        val lookup = uniqueEmojiList.associateBy {
            it.emoji
        }

        return recent.distinct().mapNotNull {
            lookup[it]
        }
    }

    private val skinTones = listOf(
        "\uD83C\uDFFB",
        "\uD83C\uDFFC",
        "\uD83C\uDFFD",
        "\uD83C\uDFFE",
        "\uD83C\uDFFF"
    )

    private val skinToneBases = setOf(
        0x1F44B, 0x1F44D, 0x1F44E, 0x1F44A,
        0x270A, 0x270B, 0x270C, 0x1F91E,
        0x1F91F, 0x1F918, 0x1F919, 0x1F91A,
        0x1F590, 0x1F596, 0x1F64C, 0x1F64F,
        0x1F90C, 0x1F90F, 0x1F90B, 0x1F90D,
        0x1F485, 0x1F4AA, 0x1F9B5, 0x1F9B6,
        0x1F9B4, 0x1F9B7, 0x1F9B8, 0x1F9B9,
        0x1F9BB, 0x1F9CD, 0x1F9CE, 0x1F9CF,
        0x1F3C3, 0x1F3CA, 0x1F3CB, 0x1F3CC,
        0x1F46E, 0x1F477, 0x1F482, 0x1F575,
        0x1F645, 0x1F646, 0x1F647, 0x1F64B,
        0x1F64D, 0x1F64E, 0x1F6A3, 0x1F6B4,
        0x1F6B5, 0x1F6B6, 0x1F926, 0x1F937,
        0x1F939, 0x1F93D, 0x1F93E, 0x1F9D1,
        0x1F9D2, 0x1F9D3, 0x1F9D4, 0x1F9D5,
        0x1F9D6, 0x1F9D7, 0x1F9D8, 0x1F9D9,
        0x1F9DA, 0x1F9DB, 0x1F9DC, 0x1F9DD,
        0x1F9DE, 0x1F9DF, 0x1F9B0, 0x1F9B1,
        0x1F9B2, 0x1F9B3, 0x1F385, 0x1F3C2,
        0x1F3C4, 0x1F3C7, 0x1F3CA, 0x1F3CB,
        0x1F3CC, 0x1F574, 0x1F57A, 0x1F6C0
    )

    fun skinToneVariants(emoji: String): List<String> {
        if (emoji.isEmpty()) return emptyList()

        val firstCodePoint = emoji.codePointAt(0)

        if (firstCodePoint !in skinToneBases) {
            return emptyList()
        }

        val firstLength = Character.charCount(firstCodePoint)
        var insertionIndex = firstLength

        if (
            insertionIndex < emoji.length &&
            emoji.codePointAt(insertionIndex) == 0xFE0F
        ) {
            insertionIndex += Character.charCount(0xFE0F)
        }

        val prefix = emoji.substring(0, insertionIndex)
        val suffix = emoji.substring(insertionIndex)

        return skinTones.map { tone ->
            prefix + tone + suffix
        }
    }
}
