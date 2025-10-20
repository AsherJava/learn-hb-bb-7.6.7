/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.params;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GcFormLockParam
implements Serializable {
    private FormSchemeDefine formScheme;
    private Set<String> formKeys = new HashSet<String>();
    private List<Map<String, DimensionValue>> dimensionValueMaps = new ArrayList<Map<String, DimensionValue>>();
    private Map<String, DimensionValue> dimensionValueMap;
    private boolean isLock;
    private boolean forceUnLock = false;

    public FormSchemeDefine getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(FormSchemeDefine formScheme) {
        this.formScheme = formScheme;
    }

    public Set<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(Set<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<Map<String, DimensionValue>> getDimensionValueMaps() {
        return this.dimensionValueMaps;
    }

    public void setDimensionValueMaps(List<Map<String, DimensionValue>> dimensionValueMaps) {
        this.dimensionValueMaps = dimensionValueMaps;
    }

    public boolean isLock() {
        return this.isLock;
    }

    public void setLock(boolean lock) {
        this.isLock = lock;
    }

    public Map<String, DimensionValue> getDimensionValueMap() {
        return this.dimensionValueMap;
    }

    public void setDimensionValueMap(Map<String, DimensionValue> dimensionValueMap) {
        this.dimensionValueMap = dimensionValueMap;
    }

    public boolean isForceUnLock() {
        return this.forceUnLock;
    }

    public void setForceUnLock(boolean forceUnLock) {
        this.forceUnLock = forceUnLock;
    }
}

