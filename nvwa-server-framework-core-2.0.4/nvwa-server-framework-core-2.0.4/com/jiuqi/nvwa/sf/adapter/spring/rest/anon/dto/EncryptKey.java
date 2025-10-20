/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon.dto;

import java.io.Serializable;

public class EncryptKey
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String alias;
    private String text;
    private int hc;

    public EncryptKey(String alias, String text, int hc) {
        this.alias = alias;
        this.text = text;
        this.hc = hc;
    }

    public EncryptKey(String alias, String text) {
        this.alias = alias;
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getHc() {
        return this.hc;
    }

    public void setHc(int hc) {
        this.hc = hc;
    }
}

