/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.encrypt;

public class EncryptedVO {
    private String sign;
    private String encryptedData;
    private Long timestamp;

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEncryptedData() {
        return this.encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

