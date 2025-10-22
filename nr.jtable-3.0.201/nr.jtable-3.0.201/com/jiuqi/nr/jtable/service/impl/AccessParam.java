/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.nr.jtable.service.IAccessParam;

public class AccessParam
implements IAccessParam {
    private String accessCode;

    @Override
    public String getAccessCode() {
        return this.accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}

