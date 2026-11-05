package dev.droidtop.lib.input

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/** The tests droidtop (SdlControllerFamilyTest) and Enginehost (PadGlyphsTest) each kept for this code. */
class PadFamiliesTest {
    @Test
    fun `listed pads map to their family`() {
        assertEquals(PadFamily.XBOX, SdlControllerIds.familyOf(0x045e, 0x028e))
        assertEquals(PadFamily.PLAYSTATION, SdlControllerIds.familyOf(0x054c, 0x0268))
        assertEquals(PadFamily.NINTENDO, SdlControllerIds.familyOf(0x057e, 0x2006))
    }

    @Test
    fun `an id the table does not list has no family`() {
        assertNull(SdlControllerIds.familyOf(0, 0))
        assertNull(SdlControllerIds.familyOf(0x045e, 0xffff))
    }

    @Test
    fun `pads are sorted by what their buttons print`() {
        assertEquals(PadFamily.XBOX, PadFamilies.classify(0x045e, 0x028e, "Xbox 360 Controller"))
        assertEquals(PadFamily.PLAYSTATION, PadFamilies.classify(0x054c, 0x09cc, "Wireless Controller"))
        assertEquals(PadFamily.NINTENDO, PadFamilies.classify(0x057e, 0x2009, "Pro Controller"))
        assertEquals(PadFamily.XBOX, PadFamilies.classify(0x18d1, 0x9400, "Stadia Controller"))
        assertEquals(PadFamily.NINTENDO, PadFamilies.classify(0, 0, "Lic Pro Controller"))
        assertNull(PadFamilies.classify(0x057e, 0x2007, "NES Controller (R)"))
        assertNull(PadFamilies.classify(0x2022, 0x3001, "Retroid Pocket Controller"))
    }

    @Test
    fun `a stored family reads back by Enginehost's id and by droidtop's name`() {
        assertEquals(PadFamily.PLAYSTATION, PadFamily.fromId("playstation"))
        assertEquals(PadFamily.PLAYSTATION, PadFamily.fromId("PLAYSTATION"))
        assertNull(PadFamily.fromId("sega"))
        assertNull(PadFamily.fromId(null))
    }
}
