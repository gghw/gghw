package com.ff.gghw.etc;

import org.junit.Test;
import static org.junit.Assert.*;

import com.ff.gghw.etc.Helpers;

public class HelpersTest {
    @Test
    public void testObjectsEqual() {
        String abc1 = new String("abc");
        String abc2 = new String("abc");
        String def = new String("def");
        assertTrue(Helpers.objectsEqual(null, null));
        assertFalse(Helpers.objectsEqual(abc1, null));
        assertFalse(Helpers.objectsEqual(null, abc1));
        assertTrue(Helpers.objectsEqual(abc1, abc1));
        assertTrue(Helpers.objectsEqual(abc1, abc2));
        assertFalse(Helpers.objectsEqual(abc1, def));
    }
    
    @Test
    public void testSafeParseInt() {
        assertEquals(0, Helpers.safeParseInt("x", 0));
        assertEquals(-1, Helpers.safeParseInt("x", -1));
        assertEquals(-1, Helpers.safeParseInt("", -1));
        assertEquals(1, Helpers.safeParseInt("1", 2));
        assertEquals(0, Helpers.safeParseInt("0", 2));
        assertEquals(-1, Helpers.safeParseInt("-1", 2));
    }
    
    @Test
    public void testForceCoverageHack() {
        assertNotNull(new Helpers());
    }
}

