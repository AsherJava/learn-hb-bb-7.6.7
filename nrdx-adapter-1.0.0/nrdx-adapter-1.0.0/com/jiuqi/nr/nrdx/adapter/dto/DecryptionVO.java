/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.nrdx.adapter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DecryptionVO {
    private String key;
    private String crypto;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCrypto() {
        return this.crypto;
    }

    public void setCrypto(String crypto) {
        this.crypto = crypto;
    }
}

