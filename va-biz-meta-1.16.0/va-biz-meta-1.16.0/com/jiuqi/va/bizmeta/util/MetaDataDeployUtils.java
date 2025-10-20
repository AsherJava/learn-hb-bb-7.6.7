/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.util;

public class MetaDataDeployUtils {
    public static String readMetaStr(String metaPath) {
        char c;
        StringBuilder metaDataStr = new StringBuilder();
        for (int i = metaPath.length() - 1; i >= 0 && (c = metaPath.charAt(i)) != '/'; --i) {
            metaDataStr.append(metaPath.charAt(i));
        }
        return metaDataStr.reverse().toString();
    }
}

