package dev.droidtop.lib.input

// droidtop's runtime-common FaceLayout.kt, moved here unchanged but for the family's type: PadFamily is what
// droidtop called GlyphFamily (Droidtop/tracker#867 D4: one shared controller model). Its SPEC references are
// droidtop's docs/SPEC.md.

enum class FacePosition { BOTTOM, RIGHT, LEFT, TOP }

/** The four things droidtop asks of the face buttons (docs/SPEC.md 6e): the logical actions A, B, X and Y. */
enum class FaceRole { CONFIRM, CANCEL, X, Y }

/** Where the answer came from, strongest first. [UNKNOWN] is the only one that has not been established. */
enum class LayoutSource { CAPTURED, CONSOLE, FAMILY, UNKNOWN }

/**
 * How the face buttons of the pad in the person's hands behave, in the two
 * independent facts that decide everything the shell draws and dispatches:
 *
 * - [confirmOnRight]: the layout the person is using confirms with the RIGHT
 *   face button (Nintendo convention) rather than the bottom one.
 * - [keysSwapped]: the system reports the pad's face keys swapped against
 *   their position, so `KEYCODE_BUTTON_A` comes from the right button and
 *   `KEYCODE_BUTTON_X` from the top one (Android's key codes are
 *   positional: A is the bottom button, B right, X left, Y top). This is what
 *   a handheld's own layout toggle does.
 *
 * Both being true cancel out: the right button confirms and the system
 * already calls it A. [swapped] is what the key map has to do about it.
 * The hint pills draw what is PRINTED on the button that does the job
 * ([glyph]), so on a Nintendo pad the confirm pill says A, and on an
 * Xbox-printed handheld toggled to the Nintendo layout it says B, which is
 * what is on the plastic.
 */
data class FaceLayout(
    val family: PadFamily,
    val confirmOnRight: Boolean,
    val keysSwapped: Boolean,
    val source: LayoutSource,
) {
    /** A and B trade meaning, and so do X and Y, between the Android key codes and the logical actions. */
    val swapped: Boolean get() = confirmOnRight != keysSwapped

    /** Established by detection or by the person; not the guess droidtop falls back to. */
    val known: Boolean get() = source != LayoutSource.UNKNOWN

    fun positionOf(role: FaceRole): FacePosition = when (role) {
        FaceRole.CONFIRM -> if (confirmOnRight) FacePosition.RIGHT else FacePosition.BOTTOM
        FaceRole.CANCEL -> if (confirmOnRight) FacePosition.BOTTOM else FacePosition.RIGHT
        FaceRole.X -> if (confirmOnRight) FacePosition.TOP else FacePosition.LEFT
        FaceRole.Y -> if (confirmOnRight) FacePosition.LEFT else FacePosition.TOP
    }

    /** The text printed on the button that does [role]. */
    fun glyph(role: FaceRole): String = printed(family, positionOf(role))

    companion object {
        /** Xbox-style, nothing swapped: what droidtop assumed for everything before detection. */
        val DEFAULT = FaceLayout(PadFamily.XBOX, confirmOnRight = false, keysSwapped = false, LayoutSource.UNKNOWN)

        /** The usual layout of a pad of [family] whose keys are reported by position. */
        fun forFamily(family: PadFamily, source: LayoutSource) =
            FaceLayout(family, confirmOnRight = family == PadFamily.NINTENDO, keysSwapped = false, source = source)

        /**
         * What the person told droidtop by pressing the button labelled A:
         * [confirmKeyCodeIsB] is whether that press arrived as
         * `KEYCODE_BUTTON_B`. Whatever the cause (a Nintendo-style pad, a
         * handheld's toggle), the button they call A is the confirm button
         * and the labels are the plain A, B, X, Y.
         */
        fun captured(confirmKeyCodeIsB: Boolean) =
            FaceLayout(PadFamily.XBOX, confirmOnRight = false, keysSwapped = confirmKeyCodeIsB, source = LayoutSource.CAPTURED)

        fun printed(family: PadFamily, position: FacePosition): String = when (family) {
            PadFamily.XBOX -> when (position) {
                FacePosition.BOTTOM -> "A"
                FacePosition.RIGHT -> "B"
                FacePosition.LEFT -> "X"
                FacePosition.TOP -> "Y"
            }
            PadFamily.NINTENDO -> when (position) {
                FacePosition.BOTTOM -> "B"
                FacePosition.RIGHT -> "A"
                FacePosition.LEFT -> "Y"
                FacePosition.TOP -> "X"
            }
            PadFamily.PLAYSTATION -> when (position) {
                FacePosition.BOTTOM -> "✕"
                FacePosition.RIGHT -> "○"
                FacePosition.LEFT -> "□"
                FacePosition.TOP -> "△"
            }
        }
    }
}
