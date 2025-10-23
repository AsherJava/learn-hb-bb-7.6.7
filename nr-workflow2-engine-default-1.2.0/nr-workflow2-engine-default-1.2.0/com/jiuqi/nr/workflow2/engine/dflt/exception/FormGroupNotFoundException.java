/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.exception;

public class FormGroupNotFoundException
extends RuntimeException {
    public FormGroupNotFoundException(String formKey) {
        super("\u672a\u627e\u5230\u62a5\u8868\u5b9a\u4e49\u4e3b\u952ekey\uff1a" + formKey + "\u5bf9\u5e94\u7684\u62a5\u8868\u5206\u7ec4\u5b9a\u4e49\uff01");
    }
}

