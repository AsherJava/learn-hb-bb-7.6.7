/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration;

public enum UploadLayerByLayerStrategy {
    UPLOAD_AFTER_SUBORDINATE_UPLOADED("\u4e0b\u7ea7\u5df2\u4e0a\u62a5\uff0c\u4e0a\u7ea7\u53ef\u4e0a\u62a5"),
    UPLOAD_AFTER_SUBORDINATE_CONFIRMED("\u4e0b\u7ea7\u5df2\u786e\u8ba4\uff0c\u4e0a\u7ea7\u53ef\u4e0a\u62a5");

    public final String title;

    private UploadLayerByLayerStrategy(String title) {
        this.title = title;
    }
}

