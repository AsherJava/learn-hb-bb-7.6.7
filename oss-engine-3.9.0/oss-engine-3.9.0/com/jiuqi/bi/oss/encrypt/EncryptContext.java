/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.encrypt;

public class EncryptContext {
    private String encryptKey;

    public EncryptContext() {
    }

    public EncryptContext(String key) {
        this.encryptKey = key;
    }

    public String getEncryptKey() {
        return this.encryptKey;
    }
}

