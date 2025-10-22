/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.midstore2.batch.web.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MidstoreRunVO
implements Serializable {
    private List<String> midstoreSchemeKeys;
    private List<String> orgCodes;
    private String periods;
    private Map<String, DimensionValue> dimSetMap;
    private boolean isDeleteEmpty;
    private String exchangeMode;
    private String taskKey;

    public List<String> getMidstoreSchemeKeys() {
        return this.midstoreSchemeKeys;
    }

    public void setMidstoreSchemeKeys(List<String> midstoreSchemeKeys) {
        this.midstoreSchemeKeys = midstoreSchemeKeys;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public String getPeriods() {
        return this.periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public Map<String, DimensionValue> getDimSetMap() {
        return this.dimSetMap;
    }

    public void setDimSetMap(Map<String, DimensionValue> dimSetMap) {
        this.dimSetMap = dimSetMap;
    }

    @JsonProperty(value="isDeleteEmpty")
    public boolean isDeleteEmpty() {
        return this.isDeleteEmpty;
    }

    @JsonProperty(value="isDeleteEmpty")
    public void setDeleteEmpty(boolean deleteEmpty) {
        this.isDeleteEmpty = deleteEmpty;
    }

    public String getExchangeMode() {
        return this.exchangeMode;
    }

    public void setExchangeMode(String exchangeMode) {
        this.exchangeMode = exchangeMode;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

