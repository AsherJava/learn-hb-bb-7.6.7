/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.script.bean;

import com.jiuqi.nr.single.core.script.common.TPSPasToken;

public class TRTab {
    private String name;
    private TPSPasToken token;

    public TRTab() {
    }

    public TRTab(String name, TPSPasToken token) {
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return this.name;
    }

    public TPSPasToken getToken() {
        return this.token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(TPSPasToken token) {
        this.token = token;
    }
}

