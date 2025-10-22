/*
 * Decompiled with CFR 0.152.
 */
package nr.single.map.configurations.common;

public class Utils {
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; ++i) {
            if (Character.isDigit(str.charAt(i))) continue;
            return false;
        }
        return true;
    }
}

