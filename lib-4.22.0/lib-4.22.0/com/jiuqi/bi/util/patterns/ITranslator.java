/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.patterns;

@FunctionalInterface
public interface ITranslator {
    public String[] translate(String var1, int var2);

    default public String[] translate(String pattern, int length, String prevText) {
        return this.translate(pattern, length);
    }
}

