/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.multcheck2.bean.MCErrorDescription
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

import com.jiuqi.nr.multcheck2.bean.MCErrorDescription;

public class AnalyzeErrorInfoAddVO
extends MCErrorDescription {
    private String checkSchemeKey;
    private String formSchemeKey;
    private String orgEntity;

    public String getCheckSchemeKey() {
        return this.checkSchemeKey;
    }

    public void setCheckSchemeKey(String checkSchemeKey) {
        this.checkSchemeKey = checkSchemeKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getOrgEntity() {
        return this.orgEntity;
    }

    public void setOrgEntity(String orgEntity) {
        this.orgEntity = orgEntity;
    }
}

