/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAnyGetter
 */
package com.jiuqi.gcreport.investworkpaper.vo;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import java.util.HashMap;
import java.util.Map;

public class InvestWorkPaperRowData {
    private String dataSource;
    private String dataSourceTitle;
    private String orientTitle;
    private String zbTitle;
    private String zbCode;
    private String zbTable;
    private String ruleId;
    private final Map<String, Object> dynamicFields = new HashMap<String, Object>();

    public String getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataSourceTitle() {
        return this.dataSourceTitle;
    }

    public void setDataSourceTitle(String dataSourceTitle) {
        this.dataSourceTitle = dataSourceTitle;
    }

    public String getOrientTitle() {
        return this.orientTitle;
    }

    public void setOrientTitle(String orientTitle) {
        this.orientTitle = orientTitle;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getZbTable() {
        return this.zbTable;
    }

    public void setZbTable(String zbTable) {
        this.zbTable = zbTable;
    }

    public String getZbTitle() {
        return this.zbTitle;
    }

    public void setZbTitle(String zbTitle) {
        this.zbTitle = zbTitle;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public void addDynamicField(String key, Object value) {
        this.dynamicFields.put(key, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getDynamicFields() {
        return this.dynamicFields;
    }
}

