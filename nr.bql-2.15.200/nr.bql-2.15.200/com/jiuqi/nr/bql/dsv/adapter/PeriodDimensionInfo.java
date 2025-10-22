/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.bql.dsv.adapter;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public class PeriodDimensionInfo {
    private TableModelDefine tableModel;
    private PeriodType periodType;
    private String periodEntityKey;

    public PeriodDimensionInfo(TableModelDefine tableModel, PeriodType periodType, String periodEntityKey) {
        this.tableModel = tableModel;
        this.periodType = periodType;
        this.periodEntityKey = periodEntityKey;
    }

    public TableModelDefine getTableModel() {
        return this.tableModel;
    }

    public void setTableModel(TableModelDefine tableModel) {
        this.tableModel = tableModel;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public String getPeriodEntityKey() {
        return this.periodEntityKey;
    }

    public void setPeriodEntityKey(String periodEntityKey) {
        this.periodEntityKey = periodEntityKey;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.tableModel == null ? 0 : this.tableModel.getCode().hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        PeriodDimensionInfo other = (PeriodDimensionInfo)obj;
        return !(this.tableModel == null ? other.tableModel != null : !this.tableModel.getCode().equals(other.tableModel.getCode()));
    }

    public String toString() {
        return "PeriodDimensionInfo [periodTableName=" + this.tableModel.getCode() + "]";
    }
}

