package com.ff.gghw.etc;

public class Helpers {
    public static boolean objectsEqual(Object a, Object b) {
        if ( a == b ) return true;
        if ( a == null || b == null ) return false;
        return a.equals(b);
    }

    public static int safeParseInt(String string, int failValue) {
        try {
            return Integer.parseInt(string);
        }
        catch ( Exception e ) {
            return failValue;
        }
    }
}

