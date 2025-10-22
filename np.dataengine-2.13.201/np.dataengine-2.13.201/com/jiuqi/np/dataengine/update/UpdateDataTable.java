/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.update;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.update.UpdateDataRecord;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateDataTable
implements Serializable,
Comparable<UpdateDataTable> {
    private static final long serialVersionUID = -6728240959184272500L;
    private String tableName;
    private TableModelRunInfo tableInfo;
    private Date dateVersionBegin;
    private List<UpdateDataRecord> deleteRecords = new ArrayList<UpdateDataRecord>();
    private List<UpdateDataRecord> insertRecords = new ArrayList<UpdateDataRecord>();
    private Map<DimensionValueSet, UpdateDataRecord> updateRecords = new HashMap<DimensionValueSet, UpdateDataRecord>();

    public UpdateDataTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public List<UpdateDataRecord> getDeleteRecords() {
        return this.deleteRecords;
    }

    public List<UpdateDataRecord> getInsertRecords() {
        return this.insertRecords;
    }

    public Map<DimensionValueSet, UpdateDataRecord> getUpdateRecords() {
        return this.updateRecords;
    }

    public Date getDateVersionBegin() {
        return this.dateVersionBegin;
    }

    public void setDateVersionBegin(Date dateVersionBegin) {
        this.dateVersionBegin = dateVersionBegin;
    }

    public String getTableModelCode() {
        if (this.tableInfo != null) {
            return this.tableInfo.getTableModelDefine().getCode();
        }
        return null;
    }

    public TableModelRunInfo getTableInfo() {
        return this.tableInfo;
    }

    public void setTableInfo(TableModelRunInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    @Override
    public int compareTo(UpdateDataTable o) {
        return this.tableName.compareTo(o.tableName);
    }

    public boolean equals(Object obj) {
        if (obj instanceof UpdateDataTable) {
            return this.tableName.equals(((UpdateDataTable)obj).tableName);
        }
        return false;
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("tableName:").append(this.tableName).append("\n");
        if (!this.deleteRecords.isEmpty()) {
            str.append("delRecords:\n");
            for (UpdateDataRecord record : this.deleteRecords) {
                str.append(record);
            }
            str.append("\n");
        }
        if (!this.insertRecords.isEmpty()) {
            str.append("insertRecords:\n");
            for (UpdateDataRecord record : this.insertRecords) {
                str.append(record);
            }
            str.append("\n");
        }
        if (!this.updateRecords.isEmpty()) {
            str.append("updateRecords:\n");
            for (UpdateDataRecord record : this.updateRecords.values()) {
                if (!record.isModified()) continue;
                str.append(record);
            }
            str.append("\n");
        }
        return str.toString();
    }
}

