/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.QueryConfigInfo;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class FloatRegionConfigVO {
    private String id;
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private String regionId;
    private String queryType;
    private QueryConfigInfo queryConfigInfo;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getRegionId() {
        return this.regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getQueryType() {
        return this.queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public QueryConfigInfo getQueryConfigInfo() {
        return this.queryConfigInfo;
    }

    public void setQueryConfigInfo(QueryConfigInfo queryConfigInfo) {
        this.queryConfigInfo = queryConfigInfo;
    }
}

