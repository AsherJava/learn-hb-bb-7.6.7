/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.organization.domain.OrgAuthDO;

public class OrgAuthVO
extends OrgAuthDO {
    private static final long serialVersionUID = 1L;

    @Override
    public String convertKey(String key) {
        return key;
    }

    public String getOrgtitle() {
        return (String)this.get("orgtitle");
    }

    public void setOrgtitle(String orgtitle) {
        this.put("orgtitle", (Object)orgtitle);
    }

    public void initAuth() {
        this.setAtmanage(this.getAtmanage());
        this.setAtaccess(this.getAtaccess());
        this.setAtwrite(this.getAtwrite());
        this.setAtedit(this.getAtedit());
        this.setAtreport(this.getAtreport());
        this.setAtsubmit(this.getAtsubmit());
        this.setAtapproval(this.getAtapproval());
    }
}

