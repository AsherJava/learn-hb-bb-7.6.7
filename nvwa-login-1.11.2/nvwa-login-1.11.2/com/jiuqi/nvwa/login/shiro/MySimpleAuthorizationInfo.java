/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.authz.SimpleAuthorizationInfo
 */
package com.jiuqi.nvwa.login.shiro;

import org.apache.shiro.authz.SimpleAuthorizationInfo;

public class MySimpleAuthorizationInfo
extends SimpleAuthorizationInfo {
    private static final long serialVersionUID = 1L;
    private String mgrFlag = "normal";

    public String getMgrFlag() {
        return this.mgrFlag;
    }

    public void setMgrFlag(String mgrFlag) {
        this.mgrFlag = mgrFlag;
    }
}

