/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.io.tz.bean;

import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSchemeTmpTable {
    private boolean full = true;
    private String tempTableName;
    private DataTable table;
    private String tzTableName;
    private DataField mdCode;
    private DataField period;
    private DataField sbId;
    private List<DataField> dimFields = new ArrayList<DataField>();
    private List<DataFieldDeployInfo> dimDeploys;
    private final List<DataField> tableDimFields = new ArrayList<DataField>();
    private List<DataFieldDeployInfo> tableDimDeploys;
    private final List<DataField> timePointFields = new ArrayList<DataField>();
    private final List<DataField> versionTimePointFields = new ArrayList<DataField>();
    private List<DataFieldDeployInfo> timePointDeploys;
    private List<DataFieldDeployInfo> versionTimePointDeploys;
    private final List<DataField> periodicFields = new ArrayList<DataField>();
    private final List<DataField> missingFields = new ArrayList<DataField>();
    private List<DataFieldDeployInfo> periodicDeploys;
    private final List<DataField> allFields = new ArrayList<DataField>();
    private final Map<String, DataField> fieldMap = new HashMap<String, DataField>();
    private final Map<String, DataFieldDeployInfo> deployInfoMap = new HashMap<String, DataFieldDeployInfo>();

    public boolean isFull() {
        return this.full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public String getTempTableName() {
        return this.tempTableName;
    }

    public void setTempTableName(String tempTableName) {
        this.tempTableName = tempTableName;
    }

    public DataField getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(DataField mdCode) {
        this.mdCode = mdCode;
    }

    public List<DataField> getAllFields() {
        return this.allFields;
    }

    public List<DataField> getTableDimFields() {
        return this.tableDimFields;
    }

    public List<DataField> getTimePointFields() {
        return this.timePointFields;
    }

    public List<DataField> getPeriodicFields() {
        return this.periodicFields;
    }

    public DataTable getTable() {
        return this.table;
    }

    public void setTable(DataTable table) {
        this.table = table;
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

    public List<DataField> getMissingFields() {
        return this.missingFields;
    }

    public List<DataFieldDeployInfo> getDimDeploys() {
        if (this.dimDeploys == null) {
            return Collections.emptyList();
        }
        return this.dimDeploys;
    }

    public void setDimDeploys(List<DataFieldDeployInfo> dimDeploys) {
        this.dimDeploys = dimDeploys;
    }

    public List<DataFieldDeployInfo> getTableDimDeploys() {
        if (this.tableDimDeploys == null) {
            return Collections.emptyList();
        }
        return this.tableDimDeploys;
    }

    public void setTableDimDeploys(List<DataFieldDeployInfo> tableDimDeploys) {
        this.tableDimDeploys = tableDimDeploys;
    }

    public List<DataFieldDeployInfo> getTimePointDeploys() {
        if (this.timePointDeploys == null) {
            return Collections.emptyList();
        }
        return this.timePointDeploys;
    }

    public void setTimePointDeploys(List<DataFieldDeployInfo> timePointDeploys) {
        this.timePointDeploys = timePointDeploys;
    }

    public List<DataFieldDeployInfo> getPeriodicDeploys() {
        if (this.periodicDeploys == null) {
            return Collections.emptyList();
        }
        return this.periodicDeploys;
    }

    public void setPeriodicDeploys(List<DataFieldDeployInfo> periodicDeploys) {
        this.periodicDeploys = periodicDeploys;
    }

    public List<DataField> getVersionTimePointFields() {
        return this.versionTimePointFields;
    }

    public List<DataFieldDeployInfo> getVersionTimePointDeploys() {
        if (this.versionTimePointDeploys == null) {
            return Collections.emptyList();
        }
        return this.versionTimePointDeploys;
    }

    public void setVersionTimePointDeploys(List<DataFieldDeployInfo> versionTimePointDeploys) {
        this.versionTimePointDeploys = versionTimePointDeploys;
    }

    public String getTzTableName() {
        return this.tzTableName;
    }

    public void setTzTableName(String tzTableName) {
        this.tzTableName = tzTableName;
    }

    public Map<String, DataField> getFieldMap() {
        return this.fieldMap;
    }

    public Map<String, DataFieldDeployInfo> getDeployInfoMap() {
        return this.deployInfoMap;
    }

    public String toString() {
        return "\u4e34\u65f6\u8868\u6a21\u578b\u4fe1\u606f:\u8868\u540d" + this.tempTableName + "\n\u6570\u636e\u8868\u5b9a\u4e49:" + this.table + "\n\u5bfc\u5165\u53f0\u8d26\u8868\u540d:" + this.tzTableName + "\n\u60c5\u666f\u6a21\u578b:" + (this.dimFields == null ? "[]" : Arrays.toString(this.dimFields.toArray())) + "\n\u8868\u5185\u7ef4\u5ea6\u6a21\u578b:" + Arrays.toString(this.tableDimFields.toArray()) + "\n\u65f6\u70b9\u6307\u6807:" + Arrays.toString(this.timePointFields.toArray()) + "\n\u65f6\u671f\u6307\u6807:" + Arrays.toString(this.periodicFields.toArray()) + "\n\u7f3a\u5931\u6307\u6807\u6a21\u578b:" + Arrays.toString(this.missingFields.toArray()) + "\n";
    }

    public void setSbId(DataField field) {
        this.sbId = field;
    }

    public DataField getSbId() {
        return this.sbId;
    }
}

