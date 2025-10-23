/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.exception;

public class FormSchemeNotFoundException
extends RuntimeException {
    private static final long serialVersionUID = -5371457382930010970L;

    public FormSchemeNotFoundException(String taskKey, String period) {
        super("\u672a\u627e\u5230\u4e0e taskKey:" + taskKey + " period:" + period + " \u5bf9\u5e94\u7684\u62a5\u8868\u65b9\u6848");
    }
}

