/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.bean;

public class JIOConfig {
    private String key;
    private String msKey;
    private String file;
    private byte[] config;
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

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public byte[] getConfig() {
        return this.config;
    }

    public void setConfig(byte[] config) {
        this.config = config;
    }

    public byte[] getContent() {
        return this.content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}

