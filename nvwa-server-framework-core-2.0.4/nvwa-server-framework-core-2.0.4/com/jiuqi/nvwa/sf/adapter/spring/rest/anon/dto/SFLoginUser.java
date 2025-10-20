/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.rest.anon.dto;

import java.io.Serializable;

public class SFLoginUser
implements Serializable {
    private String name;
    private String encoder;
    private String password;

    public String getEncoder() {
        return this.encoder;
    }

    public void setEncoder(String encoder) {
        this.encoder = encoder;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

