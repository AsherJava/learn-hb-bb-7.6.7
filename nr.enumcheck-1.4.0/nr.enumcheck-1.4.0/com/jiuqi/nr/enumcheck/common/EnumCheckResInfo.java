/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.enumcheck.common;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.enumcheck.common.EnumCheckResultSaveInfo;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class EnumCheckResInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int checkResCount;
    private boolean hasTooManyError;
    private int selEntityCount;
    private EnumCheckResultSaveInfo saveInfo;
    private int selEnumDicCount;
    private Map<String, DimensionValue> dimensionSet;
    private LinkedHashMap<String, LinkedHashMap<String, String>> dimRange;
    private Map<String, String> dimNameTitleMap;
    private Map<String, Boolean> dimNameIsShowMap;
    private int formCount;
    private Set<String> errorEntityKeys;

    public int getCheckResCount() {
        return this.checkResCount;
    }

    public void setCheckResCount(int checkResCount) {
        this.checkResCount = checkResCount;
    }

    public boolean isHasTooManyError() {
        return this.hasTooManyError;
    }

    public void setHasTooManyError(boolean hasTooManyError) {
        this.hasTooManyError = hasTooManyError;
    }

    public int getSelEntityCount() {
        return this.selEntityCount;
    }

    public void setSelEntityCount(int selEntityCount) {
        this.selEntityCount = selEntityCount;
    }

    public EnumCheckResultSaveInfo getSaveInfo() {
        return this.saveInfo;
    }

    public void setSaveInfo(EnumCheckResultSaveInfo saveInfo) {
        this.saveInfo = saveInfo;
    }

    public int getSelEnumDicCount() {
        return this.selEnumDicCount;
    }

    public void setSelEnumDicCount(int selEnumDicCount) {
        this.selEnumDicCount = selEnumDicCount;
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

    public int getFormCount() {
        return this.formCount;
    }

    public void setFormCount(int formCount) {
        this.formCount = formCount;
    }

    public Set<String> getErrorEntityKeys() {
        return this.errorEntityKeys;
    }

    public void setErrorEntityKeys(Set<String> errorEntityKeys) {
        this.errorEntityKeys = errorEntityKeys;
    }
}

