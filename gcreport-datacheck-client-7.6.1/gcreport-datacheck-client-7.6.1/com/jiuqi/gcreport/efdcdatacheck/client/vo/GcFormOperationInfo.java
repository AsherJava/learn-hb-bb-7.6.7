/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.gcreport.efdcdatacheck.client.vo;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GcFormOperationInfo
extends JtableContext {
    private Map<String, Set<String>> reportZbDataMap;
    private List<String> formKeys = new ArrayList<String>();
    private DimensionValueSet dimensionValueSet;
    private Boolean includeUncharged;
    private String contextEntityId;

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public void setDimValue(String name, Object value) {
        if (this.dimensionValueSet == null) {
            this.dimensionValueSet = new DimensionValueSet();
        }
        this.dimensionValueSet.setValue(name, value);
    }

    public Map<String, Set<String>> getReportZbDataMap() {
        return this.reportZbDataMap;
    }

    public void setReportZbDataMap(Map<String, Set<String>> reportZbDataMap) {
        this.reportZbDataMap = reportZbDataMap;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public Boolean getIncludeUncharged() {
        return this.includeUncharged;
    }

    public void setIncludeUncharged(Boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }
}

