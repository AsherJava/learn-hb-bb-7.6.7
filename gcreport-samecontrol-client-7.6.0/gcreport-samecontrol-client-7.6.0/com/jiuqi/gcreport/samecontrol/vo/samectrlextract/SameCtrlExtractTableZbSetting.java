/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.samecontrol.vo.samectrlextract;

import java.util.List;
import java.util.Map;

public class SameCtrlExtractTableZbSetting {
    private String tableName;
    private String tableTitle;
    private Map<String, List<String>> attrTypeAndFieldNameMapping;
    private List<String> fieldNames;
    private boolean usingInFloatRegion;

    public Map<String, List<String>> getAttrTypeAndFieldNameMapping() {
        return this.attrTypeAndFieldNameMapping;
    }

    public void setAttrTypeAndFieldNameMapping(Map<String, List<String>> attrTypeAndFieldNameMapping) {
        this.attrTypeAndFieldNameMapping = attrTypeAndFieldNameMapping;
    }

    public boolean getUsingInFloatRegion() {
        return this.usingInFloatRegion;
    }

    public void setUsingInFloatRegion(boolean usingInFloatRegion) {
        this.usingInFloatRegion = usingInFloatRegion;
    }

    public List<String> getFieldNames() {
        return this.fieldNames;
    }

    public void setFieldNames(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableTitle() {
        return this.tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }
}

