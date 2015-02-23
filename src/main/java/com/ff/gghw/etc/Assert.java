package com.ff.gghw.etc;

import java.lang.IllegalArgumentException;

public class Assert {
    public static void arg(boolean condition) {
        if ( !condition ) {
            throw new IllegalArgumentException();
        }
    }
}

