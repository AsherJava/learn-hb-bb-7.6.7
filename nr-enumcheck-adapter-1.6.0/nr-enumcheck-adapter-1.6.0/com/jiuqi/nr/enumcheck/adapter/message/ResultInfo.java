/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.enumcheck.common.EnumCheckResInfo
 *  com.jiuqi.nr.enumcheck.common.EnumCheckResultSaveInfo
 */
package com.jiuqi.nr.enumcheck.adapter.message;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.enumcheck.common.EnumCheckResInfo;
import com.jiuqi.nr.enumcheck.common.EnumCheckResultSaveInfo;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResultInfo {
    private String formSchemeKey;
    private String checkResultDesc;
    private boolean checkStatus;
    private String asyncTaskId;
    private EnumCheckResInfo enumCheckResInfo;
    private EnumCheckResultSaveInfo enumCheckResultSaveInfo;
    private String checkEndTime;
    private String entityId;
    private Map<String, DimensionValue> dimensionSet;
    private LinkedHashMap<String, LinkedHashMap<String, String>> dimRange;
    private Map<String, String> dimNameTitleMap;
    private Map<String, Boolean> dimNameIsShowMap;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getCheckResultDesc() {
        return this.checkResultDesc;
    }

    public void setCheckResultDesc(String checkResultDesc) {
        this.checkResultDesc = checkResultDesc;
    }

    public boolean isCheckStatus() {
        return this.checkStatus;
    }

    public void setCheckStatus(boolean checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getAsyncTaskId() {
        return this.asyncTaskId;
    }

    public void setAsyncTaskId(String asyncTaskId) {
        this.asyncTaskId = asyncTaskId;
    }

    public EnumCheckResInfo getEnumCheckResInfo() {
        return this.enumCheckResInfo;
    }

    public void setEnumCheckResInfo(EnumCheckResInfo enumCheckResInfo) {
        this.enumCheckResInfo = enumCheckResInfo;
    }

    public EnumCheckResultSaveInfo getEnumCheckResultSaveInfo() {
        return this.enumCheckResultSaveInfo;
    }

    public void setEnumCheckResultSaveInfo(EnumCheckResultSaveInfo enumCheckResultSaveInfo) {
        this.enumCheckResultSaveInfo = enumCheckResultSaveInfo;
    }

    public String getCheckEndTime() {
        return this.checkEndTime;
    }

    public void setCheckEndTime(String checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public LinkedHashMap<String, LinkedHashMap<String, String>> getDimRange() {
        return this.dimRange;
    }

    public void setDimRange(LinkedHashMap<String, LinkedHashMap<String, String>> dimRange) {
        this.dimRange = dimRange;
    }

    public Map<String, String> getDimNameTitleMap() {
        return this.dimNameTitleMap;
    }

    public void setDimNameTitleMap(Map<String, String> dimNameTitleMap) {
        this.dimNameTitleMap = dimNameTitleMap;
    }

    public Map<String, Boolean> getDimNameIsShowMap() {
        return this.dimNameIsShowMap;
    }

    public void setDimNameIsShowMap(Map<String, Boolean> dimNameIsShowMap) {
        this.dimNameIsShowMap = dimNameIsShowMap;
    }
}

