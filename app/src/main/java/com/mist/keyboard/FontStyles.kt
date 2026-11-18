
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

    private fun convert(
        input: String,
        upper: String,
        lower: String,
        digits: String = "0123456789"
    ): String {

        // Read complete Unicode code points, not individual UTF-16 units.
        val upperPoints = upper.codePoints().toArray()
        val lowerPoints = lower.codePoints().toArray()
        val digitPoints = digits.codePoints().toArray()

        val result = StringBuilder()

        input.codePoints().forEach { cp ->

            val index = when (cp) {
                in 65..90 -> cp - 65
                in 97..122 -> cp - 97
                in 48..57 -> cp - 48
                else -> -1
            }

            val replacement = when {
                cp in 65..90 && index < upperPoints.size ->
                    upperPoints[index]

                cp in 97..122 && index < lowerPoints.size ->
                    lowerPoints[index]

                cp in 48..57 && index < digitPoints.size ->
                    digitPoints[index]

                else -> cp
            }

            result.append(String(Character.toChars(replacement)))
        }

        return result.toString()
    }

    fun apply(
        input: String,
        family: FontFamily,
        variation: FontVariation
    ): String {

        if (
            family == FontFamily.HELVETICA &&
            (variation == FontVariation.REGULAR ||
             variation == FontVariation.LIGHT)
        ) {
            return input
        }

        val upper: String
        val lower: String

        when (family) {

            FontFamily.CUPERTINO -> {
                when (variation) {

                    FontVariation.BOLD ->
                        return convert(
                            input,
                            "𝗔𝗕𝗖𝗗𝗘𝗙𝗚𝗛𝗜𝗝𝗞𝗟𝗠𝗡𝗢𝗣𝗤𝗥𝗦𝗧𝗨𝗩𝗪𝗫𝗬𝗭",
                            "𝗮𝗯𝗰𝗱𝗲𝗳𝗴𝗵𝗶𝗷𝗸𝗹𝗺𝗻𝗼𝗽𝗾𝗿𝘀𝘁𝘂𝘃𝘄𝘅𝘆𝘇"
                        )

                    FontVariation.ITALIC ->
                        return convert(
                            input,
                            "𝘈𝘉𝘊𝘋𝘌𝘍𝘎𝘏𝘐𝘑𝘒𝘓𝘔𝘕𝘖𝘗𝘘𝘙𝘚𝘛𝘜𝘝𝘞𝘟𝘠𝘡",
                            "𝘢𝘣𝘤𝘥𝘦𝘧𝘨𝘩𝘪𝘫𝘬𝘭𝘮𝘯𝘰𝘱𝘲𝘳𝘴𝘵𝘶𝘷𝘸𝘹𝘺𝘻"
                        )

                    FontVariation.BOLD_ITALIC ->
                        return convert(
                            input,
                            "𝘼𝘽𝘾𝘿𝙀𝙁𝙂𝙃𝙄𝙅𝙆𝙇𝙈𝙉𝙊𝙋𝙌𝙍𝙎𝙏𝙐𝙑𝙒𝙓𝙔𝙕",
                            "𝙖𝙗𝙘𝙙𝙚𝙛𝙜𝙝𝙞𝙟𝙠𝙡𝙢𝙣𝙤𝙥𝙦𝙧𝙨𝙩𝙪𝙫𝙬𝙭𝙮𝙯"
                        )

                    FontVariation.SMALL_CAPS ->
                        return convert(
                            input,
                            NORMAL_UPPER,
                            "ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ"
                        )

                    else -> {
                        upper =
                            "𝖠𝖡𝖢𝖣𝖤𝖥𝖦𝖧𝖨𝖩𝖪𝖫𝖬𝖭𝖮𝖯𝖰𝖱𝖲𝖳𝖴𝖵𝖶𝖷𝖸𝖹"

                        lower =
                            "𝖺𝖻𝖼𝖽𝖾𝖿𝗀𝗁𝗂𝗃𝗄𝗅𝗆𝗇𝗈𝗉𝗊𝗋𝗌𝗍𝗎𝗏𝗐𝗑𝗒𝗓"
                    }
                }
            }

            FontFamily.DRAVEN -> {
                when (variation) {

                    FontVariation.BOLD ->
                        return convert(
                            input,
                            "𝕬𝕭𝕮𝕯𝕰𝕱𝕲𝕳𝕴𝕵𝕶𝕷𝕸𝕹𝕺𝕻𝕼𝕽𝕾𝕿𝖀𝖁𝖂𝖃𝖄𝖅",
                            "𝖆𝖇𝖈𝖉𝖊𝖋𝖌𝖍𝖎𝖏𝖐𝖑𝖒𝖓𝖔𝖕𝖖𝖗𝖘𝖙𝖚𝖛𝖜𝖝𝖞𝖟"
                        )

                    FontVariation.CAPITAL ->
                        return input.uppercase()

                    FontVariation.SMALL_CAPS ->
                        return convert(
                            input,
                            NORMAL_UPPER,
                            "ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ"
                        )

                    else -> {
                        upper =
                            "𝔄𝔅ℭ𝔇𝔈𝔉𝔊ℌℑ𝔍𝔎𝔏𝔐𝔑𝔒𝔓𝔔ℜ𝔖𝔗𝔘𝔙𝔚𝔛𝔜ℨ"

                        lower =
                            "𝔞𝔟𝔠𝔡𝔢𝔣𝔤𝔥𝔦𝔧𝔨𝔩𝔪𝔫𝔬𝔭𝔮𝔯𝔰𝔱𝔲𝔳𝔴𝔵𝔶𝔷"
                    }
                }
            }

            FontFamily.HELVETICA -> {
                when (variation) {

                    FontVariation.BOLD ->
                        return convert(
                            input,
                            "𝗔𝗕𝗖𝗗𝗘𝗙𝗚𝗛𝗜𝗝𝗞𝗟𝗠𝗡𝗢𝗣𝗤𝗥𝗦𝗧𝗨𝗩𝗪𝗫𝗬𝗭",
                            "𝗮𝗯𝗰𝗱𝗲𝗳𝗴𝗵𝗶𝗷𝗸𝗹𝗺𝗻𝗼𝗽𝗾𝗿𝘀𝘁𝘂𝘃𝘄𝘅𝘆𝘇"
                        )

                    FontVariation.ITALIC ->
                        return convert(
                            input,
                            "𝘈𝘉𝘊𝘋𝘌𝘍𝘎𝘏𝘐𝘑𝘒𝘓𝘔𝘕𝘖𝘗𝘘𝘙𝘚𝘛𝘜𝘝𝘞𝘟𝘠𝘡",
                            "𝘢𝘣𝘤𝘥𝘦𝘧𝘨𝘩𝘪𝘫𝘬𝘭𝘮𝘯𝘰𝘱𝘲𝘳𝘴𝘵𝘶𝘷𝘸𝘹𝘺𝘻"
                        )

                    FontVariation.BOLD_ITALIC ->
                        return convert(
                            input,
                            "𝘼𝘽𝘾𝘿𝙀𝙁𝙂𝙃𝙄𝙅𝙆𝙇𝙈𝙉𝙊𝙋𝙌𝙍𝙎𝙏𝙐𝙑𝙒𝙓𝙔𝙕",
                            "𝙖𝙗𝙘𝙙𝙚𝙛𝙜𝙝𝙞𝙟𝙠𝙡𝙢𝙣𝙤𝙥𝙦𝙧𝙨𝙩𝙪𝙫𝙬𝙭𝙮𝙯"
                        )

                    FontVariation.MONOSPACE ->
                        return convert(
                            input,
                            "𝙰𝙱𝙲𝙳𝙴𝙵𝙶𝙷𝙸𝙹𝙺𝙻𝙼𝙽𝙾𝙿𝚀𝚁𝚂𝚃𝚄𝚅𝚆𝚇𝚈𝚉",
                            "𝚊𝚋𝚌𝚍𝚎𝚏𝚐𝚑𝚒𝚓𝚔𝚕𝚖𝚗𝚘𝚙𝚚𝚛𝚜𝚝𝚞𝚟𝚠𝚡𝚢𝚣"
                        )

                    FontVariation.ROUNDED ->
                        return convert(
                            input,
                            "𝔸𝔹ℂ𝔻𝔼𝔽𝔾ℍ𝕀𝕁𝕂𝕃𝕄ℕ𝕆ℙℚℝ𝕊𝕋𝕌𝕍𝕎𝕏𝕐ℤ",
                            "𝕒𝕓𝕔𝕕𝕖𝕗𝕘𝕙𝕚𝕛𝕜𝕝𝕞𝕟𝕠𝕡𝕢𝕣𝕤𝕥𝕦𝕧𝕨𝕩𝕪𝕫"
                        )

                    else -> {
                        upper = NORMAL_UPPER
                        lower = NORMAL_LOWER
                    }
                }
            }
        }

        return convert(input, upper, lower)
    }
}
