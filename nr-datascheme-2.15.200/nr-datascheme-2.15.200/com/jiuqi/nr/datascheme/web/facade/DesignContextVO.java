/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

import com.jiuqi.nr.datascheme.web.param.AuthQueryPM;

public class DesignContextVO {
    private boolean enableAccount = true;
    private boolean enableNoddl = false;
    private boolean enableI18n = false;
    private boolean deployed = false;
    private boolean entered = false;
    private AuthQueryPM.PrivilegeType auth;
    private String key;

    public boolean isEnableAccount() {
        return this.enableAccount;
    }

    public void setEnableAccount(boolean enableAccount) {
        this.enableAccount = enableAccount;
    }

    public boolean isEnableNoddl() {
        return this.enableNoddl;
    }

    public void setEnableNoddl(boolean enableNoddl) {
        this.enableNoddl = enableNoddl;
    }

    public boolean isEnableI18n() {
        return this.enableI18n;
    }

    public void setEnableI18n(boolean enableI18n) {
        this.enableI18n = enableI18n;
    }

    public boolean isDeployed() {
        return this.deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    public boolean isEntered() {
        return this.entered;
    }

    public void setEntered(boolean entered) {
        this.entered = entered;
    }

    public AuthQueryPM.PrivilegeType getAuth() {
        return this.auth;
    }

    public void setAuth(AuthQueryPM.PrivilegeType auth) {
        this.auth = auth;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

