package dev.droidtop.lib.input

/**
 * What is printed on a pad's face buttons, the one model droidtop and Enginehost draw glyphs by
 * (Droidtop/tracker#867 D4: "one shared controller model, with each app's own vocabulary on top").
 * [id] is the word a program stores; [fromId] reads it, and an enum name in any case too.
 */
enum class PadFamily(val id: String) {
    /** Bottom A, right B, left X, top Y; LB, RB, LT, RT; Menu and View. Also what an unknown pad is drawn as. */
    XBOX("xbox"),

    /** Bottom cross, right circle, left square, top triangle; L1, R1, L2, R2; Options and Share. */
    PLAYSTATION("playstation"),

    /** Bottom B, right A, left Y, top X; L, R, ZL, ZR; plus and minus. */
    NINTENDO("nintendo"),
    ;

    companion object {
        fun fromId(id: String?): PadFamily? = entries.firstOrNull { it.id == id || it.name.equals(id, ignoreCase = true) }
    }
}
