package com.ff.gghw.etc;

import static org.junit.Assert.*;
import org.junit.Test;
import java.lang.IllegalArgumentException;

import com.ff.gghw.etc.Assert;

public class AssertTest {
    @Test
    public void testAssertArgNoThrow() {
        Assert.arg(true);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testAssertArgThrow() {
        Assert.arg(false);
    }
    
    @Test
    public void testForceCoverageHack() {
        assertNotNull(new Assert());
    }
}

