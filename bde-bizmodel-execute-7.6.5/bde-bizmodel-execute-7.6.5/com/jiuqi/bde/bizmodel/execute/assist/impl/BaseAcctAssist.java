/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.assist.impl;

import com.jiuqi.bde.bizmodel.execute.assist.IAcctAssist;

public class BaseAcctAssist
implements IAcctAssist {
    private String code;
    private String name;

    public BaseAcctAssist() {
    }

    public BaseAcctAssist(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getOrder() {
        return this.code;
    }
}

