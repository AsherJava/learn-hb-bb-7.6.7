/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.function.func.floatcopy;

import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.definition.facade.FieldDefine;
import java.util.ArrayList;
import java.util.List;

public class FloatCopyQueryInfo {
    private List<String> queryColumns;
    private List<String> parsedAssginExps;
    private List<Integer> keyColumns;
    private List<String> keyColumnExps = new ArrayList<String>();
    private TableModelRunInfo tableInfo;
    private String reportName;
    private String reportTitle;
    private FieldDefine floatOrderField;
    private String RegionKey;

    public FloatCopyQueryInfo() {
        this.queryColumns = new ArrayList<String>();
        this.keyColumns = new ArrayList<Integer>();
        this.parsedAssginExps = new ArrayList<String>();
    }

    public void addQueryColumns(String queryFieldExp) {
        this.queryColumns.add(queryFieldExp);
        this.parsedAssginExps.add(queryFieldExp);
    }

    public List<String> getQueryColumns() {
        return this.queryColumns;
    }

    public List<Integer> getKeyColumns() {
        return this.keyColumns;
    }

    public void setKeyColumns(String[] keyFieldExps) {
        if (keyFieldExps == null) {
            return;
        }
        for (String field : keyFieldExps) {
            this.keyColumnExps.add(field);
            int atIndex = field.indexOf("@");
            if (atIndex >= 0) {
                field = field.substring(0, atIndex);
            }
            this.queryColumns.add(field);
            this.keyColumns.add(this.queryColumns.size() - 1);
        }
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public FieldDefine getFloatOrderField() {
        return this.floatOrderField;
    }

    public void setFloatOrderField(FieldDefine floatOrderField) {
        this.floatOrderField = floatOrderField;
    }

    public String getRegionKey() {
        return this.RegionKey;
    }

    public void setRegionKey(String regionKey) {
        this.RegionKey = regionKey;
    }

    public String getReportTitle() {
        return this.reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public List<String> getKeyColumnExps() {
        return this.keyColumnExps;
    }

    public TableModelRunInfo getTableInfo() {
        return this.tableInfo;
    }

    public void setTableInfo(TableModelRunInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    public List<String> getParsedAssginExps() {
        return this.parsedAssginExps;
    }
}

