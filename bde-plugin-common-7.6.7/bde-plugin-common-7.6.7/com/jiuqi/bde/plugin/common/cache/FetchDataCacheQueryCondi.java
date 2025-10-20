/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.plugin.common.cache;

import java.util.List;

public class FetchDataCacheQueryCondi {
    private List<String> selectFields;
    private List<String> groupFields;
    private String orgCode;
    private String orgType;
    private String orgVer;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgVer() {
        return this.orgVer;
    }

    public void setOrgVer(String orgVer) {
        this.orgVer = orgVer;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public FetchDataCacheQueryCondi() {
    }

    public FetchDataCacheQueryCondi(List<String> selectFields, List<String> groupFields) {
        this.selectFields = selectFields;
        this.groupFields = groupFields;
    }

    public FetchDataCacheQueryCondi(List<String> selectFields, List<String> groupFields, String orgCode) {
        this.selectFields = selectFields;
        this.groupFields = groupFields;
        this.orgCode = orgCode;
    }

    public List<String> getSelectFields() {
        return this.selectFields;
    }

    public void setSelectFields(List<String> selectFields) {
        this.selectFields = selectFields;
    }

    public List<String> getGroupFields() {
        return this.groupFields;
    }

    public void setGroupFields(List<String> groupFields) {
        this.groupFields = groupFields;
    }
}

