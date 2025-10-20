/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.tool.web;

public class DebugParam {
    static boolean enableDebug = false;
    static boolean enableCopyInitOffset = true;

    public static boolean isEnableCopyInitOffset() {
        return !enableDebug || enableCopyInitOffset;
    }
}

