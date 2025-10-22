/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.db;

public class DbConsts {
    public static final int FA_READ_ONLY_BITMASK = 1;
    public static final int FA_EXCLUSIVE_BITMASK = 2;
    public static final int FA_TEMPORARY_BITMASK = 4;
    public static final int FA_AUTO_DELETE_BITMASK = 8;
    public static final int FA_TRUC_EXISTING_BITMASK = 16;
    public static final int FA_RANDOM_ACCESS_BITMASK = 32;
    public static final int FA_SEQUENTIAL_SCAN_BITMASK = 64;
    public static final int MIGHT_ERROR_RETURN = -1;

    public static void lastWin32CallCheck(String srrorMsg) throws Exception {
        throw new Exception(srrorMsg);
    }
}

