/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.bean;

public class JIOConfig {
    private String key;
    private String msKey;
    private byte[] config;
    private byte[] file;
    private byte[] content;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsKey() {
        return this.msKey;
    }

    public void setMsKey(String msKey) {
        this.msKey = msKey;
    }

    public byte[] getConfig() {
        return this.config;
    }

    public void setConfig(byte[] config) {
        this.config = config;
    }

    public byte[] getFile() {
        return this.file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}

