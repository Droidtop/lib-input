package dev.droidtop.lib.input

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FaceLayoutTest {
    @Test
    fun `an Xbox pad confirms with the bottom button, printed A`() {
        val layout = FaceLayout.forFamily(PadFamily.XBOX, LayoutSource.FAMILY)
        assertEquals(FacePosition.BOTTOM, layout.positionOf(FaceRole.CONFIRM))
        assertEquals("A", layout.glyph(FaceRole.CONFIRM))
        assertFalse(layout.swapped)
    }

    @Test
    fun `a Nintendo pad confirms with the right button, printed A, and its keys trade meaning`() {
        val layout = FaceLayout.forFamily(PadFamily.NINTENDO, LayoutSource.FAMILY)
        assertEquals(FacePosition.RIGHT, layout.positionOf(FaceRole.CONFIRM))
        assertEquals("A", layout.glyph(FaceRole.CONFIRM))
        assertEquals("B", layout.glyph(FaceRole.CANCEL))
        assertTrue(layout.swapped)
    }

    @Test
    fun `a handheld toggled to swap its keys, on a Nintendo layout, cancels out`() {
        val layout = FaceLayout(PadFamily.NINTENDO, confirmOnRight = true, keysSwapped = true, source = LayoutSource.CONSOLE)
        assertFalse(layout.swapped)
        assertTrue(layout.known)
    }

    @Test
    fun `PlayStation prints its shapes, and the default is an unknown Xbox layout`() {
        assertEquals("✕", FaceLayout.printed(PadFamily.PLAYSTATION, FacePosition.BOTTOM))
        assertEquals("△", FaceLayout.printed(PadFamily.PLAYSTATION, FacePosition.TOP))
        assertFalse(FaceLayout.DEFAULT.known)
        assertEquals(PadFamily.XBOX, FaceLayout.DEFAULT.family)
    }

    @Test
    fun `a captured press of A that arrives as B swaps the keys`() {
        val layout = FaceLayout.captured(confirmKeyCodeIsB = true)
        assertTrue(layout.keysSwapped)
        assertTrue(layout.swapped)
        assertEquals(LayoutSource.CAPTURED, layout.source)
    }
}
