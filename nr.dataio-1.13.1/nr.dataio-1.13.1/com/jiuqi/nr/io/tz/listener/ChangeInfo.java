/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 */
package com.jiuqi.nr.io.tz.listener;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.io.tz.listener.DataRecord;
import java.util.List;
import java.util.Map;

public class ChangeInfo {
    private DataTable table;
    private DataField mdCode;
    private DataField period;
    private List<DataField> dimFields;
    private List<DataField> tableDimFields;
    private List<DataField> timePointFields;
    private List<DataField> periodicFields;
    private List<DataField> allFields;
    private Map<String, DataField> fieldMap;
    private List<DataRecord> deleteRecords;
    private List<DataRecord> insertRecords;
    private List<DataRecord> updateRecords;
    public String datatime;

    public DataTable getTable() {
        return this.table;
    }

    public void setTable(DataTable table) {
        this.table = table;
    }

    public DataField getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(DataField mdCode) {
        this.mdCode = mdCode;
    }

    public DataField getPeriod() {
        return this.period;
    }

    public void setPeriod(DataField period) {
        this.period = period;
    }

    public List<DataField> getDimFields() {
        return this.dimFields;
    }

    public void setDimFields(List<DataField> dimFields) {
        this.dimFields = dimFields;
    }

    public List<DataField> getTableDimFields() {
        return this.tableDimFields;
    }

    public void setTableDimFields(List<DataField> tableDimFields) {
        this.tableDimFields = tableDimFields;
    }

    public List<DataField> getTimePointFields() {
        return this.timePointFields;
    }

    public void setTimePointFields(List<DataField> timePointFields) {
        this.timePointFields = timePointFields;
    }

    public List<DataField> getPeriodicFields() {
        return this.periodicFields;
    }

    public void setPeriodicFields(List<DataField> periodicFields) {
        this.periodicFields = periodicFields;
    }

    public List<DataField> getAllFields() {
        return this.allFields;
    }

    public void setAllFields(List<DataField> allFields) {
        this.allFields = allFields;
    }

    public Map<String, DataField> getFieldMap() {
        return this.fieldMap;
    }

    public void setFieldMap(Map<String, DataField> fieldMap) {
        this.fieldMap = fieldMap;
    }

    public List<DataRecord> getDeleteRecords() {
        return this.deleteRecords;
    }

    public void setDeleteRecords(List<DataRecord> deleteRecords) {
        this.deleteRecords = deleteRecords;
    }

    public List<DataRecord> getInsertRecords() {
        return this.insertRecords;
    }

    public void setInsertRecords(List<DataRecord> insertRecords) {
        this.insertRecords = insertRecords;
    }

    public List<DataRecord> getUpdateRecords() {
        return this.updateRecords;
    }

    public void setUpdateRecords(List<DataRecord> updateRecords) {
        this.updateRecords = updateRecords;
    }

    public String getDatatime() {
        return this.datatime;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }
}

