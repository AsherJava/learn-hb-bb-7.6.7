/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.org.ZB
 */
package com.jiuqi.nr.formtype.web.vo;

import com.jiuqi.va.domain.org.ZB;
import java.util.UUID;

public class OrgFormTypeZbVO {
    private UUID id;
    private String name;
    private String title;
    private String reltablename;
    private String orgName;
    private String defaultValue;
    private boolean existedData;

    public OrgFormTypeZbVO() {
    }

    public OrgFormTypeZbVO(String orgName, boolean existedData, ZB zb) {
        this.orgName = orgName;
        this.existedData = existedData;
        if (null != zb) {
            this.id = zb.getId();
            this.name = zb.getName();
            this.title = zb.getTitle();
            this.reltablename = zb.getReltablename();
            this.defaultValue = zb.getDefaultVal();
        }
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReltablename() {
        return this.reltablename;
    }

    public void setReltablename(String reltablename) {
        this.reltablename = reltablename;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public boolean isExistedData() {
        return this.existedData;
    }

    public void setExistedData(boolean existedData) {
        this.existedData = existedData;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}

