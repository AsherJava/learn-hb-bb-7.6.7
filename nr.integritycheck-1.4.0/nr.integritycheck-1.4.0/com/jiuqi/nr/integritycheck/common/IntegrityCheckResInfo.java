/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IntegrityCheckResInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, DimensionValue> dimensionSet;
    private Map<String, Map<String, Boolean>> dimNameEntityIdExistCurrencyAttributeMap;
    private LinkedHashMap<String, LinkedHashMap<String, String>> dimRange;
    private Map<String, String> dimNameTitleMap;
    private Map<String, Boolean> dimNameIsShowMap;
    private List<String> formKeys;
    private String contextEntityId;

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public Map<String, Map<String, Boolean>> getDimNameEntityIdExistCurrencyAttributeMap() {
        return this.dimNameEntityIdExistCurrencyAttributeMap;
    }

    public void setDimNameEntityIdExistCurrencyAttributeMap(Map<String, Map<String, Boolean>> dimNameEntityIdExistCurrencyAttributeMap) {
        this.dimNameEntityIdExistCurrencyAttributeMap = dimNameEntityIdExistCurrencyAttributeMap;
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

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }
}

