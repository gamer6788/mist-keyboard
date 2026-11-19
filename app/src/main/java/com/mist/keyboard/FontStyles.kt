
package com.mist.keyboard

enum class FontFamily(val displayName: String) {
    CUPERTINO("Cupertino Noir"),
    DRAVEN("Draven Classic"),
    HELVETICA("Helvetica")
}

enum class FontVariation(val displayName: String) {
    REGULAR("Regular"),
    LIGHT("Light"),
    BOLD("Bold"),
    ITALIC("Italic"),
    BOLD_ITALIC("Bold Italic"),
    SMALL_CAPS("Small Caps"),
    GOTHIC("Gothic"),
    CAPITAL("Capital"),
    MONOSPACE("Monospace"),
    ROUNDED("Rounded")
}

object FontStyles {

    private const val NORMAL_UPPER =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    private const val NORMAL_LOWER =
        "abcdefghijklmnopqrstuvwxyz"

    private const val SANS_UPPER =
        "𝖠𝖡𝖢𝖣𝖤𝖥𝖦𝖧𝖨𝖩𝖪𝖫𝖬𝖭𝖮𝖯𝖰𝖱𝖲𝖳𝖴𝖵𝖶𝖷𝖸𝖹"

    private const val SANS_LOWER =
        "𝖺𝖻𝖼𝖽𝖾𝖿𝗀𝗁𝗂𝗃𝗄𝗅𝗆𝗇𝗈𝗉𝗊𝗋𝗌𝗍𝗎𝗏𝗐𝗑𝗒𝗓"

    private const val BOLD_UPPER =
        "𝗔𝗕𝗖𝗗𝗘𝗙𝗚𝗛𝗜𝗝𝗞𝗟𝗠𝗡𝗢𝗣𝗤𝗥𝗦𝗧𝗨𝗩𝗪𝗫𝗬𝗭"

    private const val BOLD_LOWER =
        "𝗮𝗯𝗰𝗱𝗲𝗳𝗴𝗵𝗶𝗷𝗸𝗹𝗺𝗻𝗼𝗽𝗾𝗿𝘀𝘁𝘂𝘃𝘄𝘅𝘆𝘇"

    private const val ITALIC_UPPER =
        "𝘈𝘉𝘊𝘋𝘌𝘍𝘎𝘏𝘐𝘑𝘒𝘓𝘔𝘕𝘖𝘗𝘘𝘙𝘚𝘛𝘜𝘝𝘞𝘟𝘠𝘡"

    private const val ITALIC_LOWER =
        "𝘢𝘣𝘤𝘥𝘦𝘧𝘨𝘩𝘪𝘫𝘬𝘭𝘮𝘯𝘰𝘱𝘲𝘳𝘴𝘵𝘶𝘷𝘸𝘹𝘺𝘻"

    private const val BOLD_ITALIC_UPPER =
        "𝘼𝘽𝘾𝘿𝙀𝙁𝙂𝙃𝙄𝙅𝙆𝙇𝙈𝙉𝙊𝙋𝙌𝙍𝙎𝙏𝙐𝙑𝙒𝙓𝙔𝙕"

    private const val BOLD_ITALIC_LOWER =
        "𝙖𝙗𝙘𝙙𝙚𝙛𝙜𝙝𝙞𝙟𝙠𝙡𝙢𝙣𝙤𝙥𝙦𝙧𝙨𝙩𝙪𝙫𝙬𝙭𝙮𝙯"

    private const val FRAKTUR_UPPER =
        "𝔄𝔅ℭ𝔇𝔈𝔉𝔊ℌℑ𝔍𝔎𝔏𝔐𝔑𝔒𝔓𝔔ℜ𝔖𝔗𝔘𝔙𝔚𝔛𝔜ℨ"

    private const val FRAKTUR_LOWER =
        "𝔞𝔟𝔠𝔡𝔢𝔣𝔤𝔥𝔦𝔧𝔨𝔩𝔪𝔫𝔬𝔭𝔮𝔯𝔰𝔱𝔲𝔳𝔴𝔵𝔶𝔷"

    private const val BOLD_FRAKTUR_UPPER =
        "𝕬𝕭𝕮𝕯𝕰𝕱𝕲𝕳𝕴𝕵𝕶𝕷𝕸𝕹𝕺𝕻𝕼𝕽𝕾𝕿𝖀𝖁𝖂𝖃𝖄𝖅"

    private const val BOLD_FRAKTUR_LOWER =
        "𝖆𝖇𝖈𝖉𝖊𝖋𝖌𝖍𝖎𝖏𝖐𝖑𝖒𝖓𝖔𝖕𝖖𝖗𝖘𝖙𝖚𝖛𝖜𝖝𝖞𝖟"

    private const val SMALL_CAPS_LOWER =
        "ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ"

    private const val MONO_UPPER =
        "𝙰𝙱𝙲𝙳𝙴𝙵𝙶𝙷𝙸𝙹𝙺𝙻𝙼𝙽𝙾𝙿𝚀𝚁𝚂𝚃𝚄𝚅𝚆𝚇𝚈𝚉"

    private const val MONO_LOWER =
        "𝚊𝚋𝚌𝚍𝚎𝚏𝚐𝚑𝚒𝚓𝚔𝚕𝚖𝚗𝚘𝚙𝚚𝚛𝚜𝚝𝚞𝚟𝚠𝚡𝚢𝚣"

    private const val DOUBLE_UPPER =
        "𝔸𝔹ℂ𝔻𝔼𝔽𝔾ℍ𝕀𝕁𝕂𝕃𝕄ℕ𝕆ℙℚℝ𝕊𝕋𝕌𝕍𝕎𝕏𝕐ℤ"

    private const val DOUBLE_LOWER =
        "𝕒𝕓𝕔𝕕𝕖𝕗𝕘𝕙𝕚𝕛𝕜𝕝𝕞𝕟𝕠𝕡𝕢𝕣𝕤𝕥𝕦𝕧𝕨𝕩𝕪𝕫"

    private fun codePoints(value: String): IntArray {
        val result = ArrayList<Int>()
        var index = 0

        while (index < value.length) {
            val point = Character.codePointAt(value, index)
            result.add(point)
            index += Character.charCount(point)
        }

        return result.toIntArray()
    }

    private fun convert(
        input: String,
        upper: String,
        lower: String
    ): String {
        val upperPoints = codePoints(upper)
        val lowerPoints = codePoints(lower)

        val result = StringBuilder()
        var index = 0

        while (index < input.length) {
            val point = Character.codePointAt(input, index)

            val replacement = when (point) {
                in 65..90 -> upperPoints.getOrNull(point - 65) ?: point
                in 97..122 -> lowerPoints.getOrNull(point - 97) ?: point
                else -> point
            }

            result.append(
                String(Character.toChars(replacement))
            )

            index += Character.charCount(point)
        }

        return result.toString()
    }

    fun apply(
        input: String,
        family: FontFamily,
        variation: FontVariation
    ): String {

        val style = when (variation) {

            FontVariation.LIGHT -> {
                NORMAL_UPPER to NORMAL_LOWER
            }

            FontVariation.BOLD -> {
                if (family == FontFamily.DRAVEN) {
                    BOLD_FRAKTUR_UPPER to BOLD_FRAKTUR_LOWER
                } else {
                    BOLD_UPPER to BOLD_LOWER
                }
            }

            FontVariation.ITALIC -> {
                ITALIC_UPPER to ITALIC_LOWER
            }

            FontVariation.BOLD_ITALIC -> {
                BOLD_ITALIC_UPPER to BOLD_ITALIC_LOWER
            }

            FontVariation.SMALL_CAPS -> {
                NORMAL_UPPER to SMALL_CAPS_LOWER
            }

            FontVariation.GOTHIC -> {
                FRAKTUR_UPPER to FRAKTUR_LOWER
            }

            FontVariation.CAPITAL -> {
                return input.uppercase()
            }

            FontVariation.MONOSPACE -> {
                MONO_UPPER to MONO_LOWER
            }

            FontVariation.ROUNDED -> {
                DOUBLE_UPPER to DOUBLE_LOWER
            }

            FontVariation.REGULAR -> {
                when (family) {
                    FontFamily.CUPERTINO ->
                        SANS_UPPER to SANS_LOWER

                    FontFamily.DRAVEN ->
                        FRAKTUR_UPPER to FRAKTUR_LOWER

                    FontFamily.HELVETICA ->
                        NORMAL_UPPER to NORMAL_LOWER
                }
            }
        }

        return convert(input, style.first, style.second)
    }
}
