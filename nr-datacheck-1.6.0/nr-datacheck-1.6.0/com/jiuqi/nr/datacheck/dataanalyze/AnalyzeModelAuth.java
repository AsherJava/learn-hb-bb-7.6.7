/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.dataanalyze;

import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeModel;

public class AnalyzeModelAuth
extends AnalyzeModel {
    private boolean auth;

    public AnalyzeModelAuth() {
    }

    public AnalyzeModelAuth(AnalyzeModel model, boolean auth) {
        this.setKey(model.getKey());
        this.setCode(model.getCode());
        this.setTitle(model.getTitle());
        this.setFml(model.getFml());
        this.setType(model.getType());
        this.setAuth(auth);
    }

    public boolean isAuth() {
        return this.auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
}

