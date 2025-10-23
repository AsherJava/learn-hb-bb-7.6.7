/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration;

public enum TodoReceiverStrategy {
    IDENTICAL_TO_EXECUTOR("\u540c\u52a8\u4f5c\u6267\u884c\u4eba"),
    CUSTOM("\u81ea\u5b9a\u4e49");

    public final String title;

    private TodoReceiverStrategy(String title) {
        this.title = title;
    }
}

