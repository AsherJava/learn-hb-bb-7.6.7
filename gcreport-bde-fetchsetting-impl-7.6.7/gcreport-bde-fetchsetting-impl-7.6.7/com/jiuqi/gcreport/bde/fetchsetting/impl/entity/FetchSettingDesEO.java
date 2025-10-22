/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.MD5Util
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.entity;

import com.jiuqi.va.domain.common.MD5Util;
import java.util.List;

public class FetchSettingDesEO {
    public static final String TABLENAME = "BDE_FETCHSETTING_DES";
    private static final long serialVersionUID = 8752378478787217679L;
    private String id;
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private String regionId;
    private String dataLinkId;
    private String fieldDefineId;
    private String regionType;
    private String fixedSettingData;
    private List<String> dimComb;
    private Integer stopFlag;
    private Integer sortOrder;

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

    public String getDataLinkId() {
        return this.dataLinkId;
    }

    public void setDataLinkId(String dataLinkId) {
        this.dataLinkId = dataLinkId;
    }

    public String getFieldDefineId() {
        return this.fieldDefineId;
    }

    public void setFieldDefineId(String fieldDefineId) {
        this.fieldDefineId = fieldDefineId;
    }

    public String getRegionType() {
        return this.regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public String getEqualsString() {
        String equalsString = "FetchSetting{fetchSchemeId='" + this.fetchSchemeId + '\'' + ", formSchemeId='" + this.formSchemeId + '\'' + ", formId='" + this.formId + '\'' + ", regionId='" + this.regionId + '\'' + ", dataLinkId='" + this.dataLinkId + '\'' + ", fieldDefineId='" + this.fieldDefineId + '\'' + ", regionType='" + this.regionType + '\'' + ", fixedSettingData = '" + this.fixedSettingData + "', stopFlag = " + this.stopFlag + "'" + '}';
        return MD5Util.encrypt((String)equalsString);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<String> getDimComb() {
        return this.dimComb;
    }

    public void setDimComb(List<String> dimComb) {
        this.dimComb = dimComb;
    }

    public String getFixedSettingData() {
        return this.fixedSettingData;
    }

    public void setFixedSettingData(String fixedSettingData) {
        this.fixedSettingData = fixedSettingData;
    }

    public Integer getStopFlag() {
        return this.stopFlag;
    }

    public void setStopFlag(Integer stopFlag) {
        this.stopFlag = stopFlag;
    }
}

