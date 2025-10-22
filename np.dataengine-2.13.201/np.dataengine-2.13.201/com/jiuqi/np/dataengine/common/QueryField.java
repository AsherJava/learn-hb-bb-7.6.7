/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.IQueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.io.Serializable;

public final class QueryField
implements IQueryField,
Serializable {
    private static final long serialVersionUID = 6281078432347504137L;
    private String uuid;
    private String fieldCode;
    private String fieldName;
    private int dataType;
    private QueryTable queryTable;
    private int hashCode = -1;
    private ColumnModelDefine columnModel;
    private boolean needAccountVersion = false;

    public QueryField(ColumnModelDefine columnModel, QueryTable queryTable) {
        this.columnModel = columnModel;
        this.queryTable = queryTable;
    }

    public QueryField(String uuid, String fieldCode, String fieldName, QueryTable queryTable) {
        this.uuid = uuid;
        this.fieldName = fieldName;
        this.queryTable = queryTable;
        this.fieldCode = fieldCode;
    }

    public QueryField(String uuid, String fieldName, QueryTable queryTable) {
        this.uuid = uuid;
        this.fieldName = fieldName;
        this.queryTable = queryTable;
        this.fieldCode = fieldName;
    }

    public String getUID() {
        if (this.columnModel != null) {
            return this.columnModel.getID();
        }
        return this.uuid;
    }

    public QueryTable getTable() {
        return this.queryTable;
    }

    @Override
    public String getFieldName() {
        if (this.columnModel != null) {
            return this.columnModel.getName();
        }
        return this.fieldName;
    }

    @Override
    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int value) {
        this.dataType = value;
    }

    @Override
    public String getTableName() {
        return this.queryTable.getTableName();
    }

    @Override
    public int getFractionDigits() {
        if (this.columnModel != null) {
            return this.columnModel.getDecimal();
        }
        return 0;
    }

    @Override
    public int getFieldSize() {
        if (this.columnModel != null) {
            return this.columnModel.getPrecision();
        }
        return 0;
    }

    @Override
    public DimensionSet getTableDimensions() {
        return this.queryTable.getTableDimensions();
    }

    @Override
    public PeriodModifier getPeriodModifier() {
        return this.queryTable.getPeriodModifier();
    }

    @Override
    public DimensionValueSet getDimensionRestriction() {
        return this.queryTable.getDimensionRestriction();
    }

    @Override
    public boolean getIsLj() {
        return this.queryTable.getIsLj();
    }

    public void setIsLj(boolean value) {
        this.queryTable.setIsLj(value);
    }

    public boolean hasSumMerge() {
        return this.queryTable.hasSumMerge();
    }

    public String getRegion() {
        if (this.queryTable.getRegion() == null || !this.queryTable.getIsSimple()) {
            return this.queryTable.getAlias();
        }
        return this.queryTable.getRegion();
    }

    public void setRegion(String region) {
        this.queryTable.setRegion(region);
    }

    public void resetHashCode() {
        this.queryTable.resetHashCode();
        this.hashCode = -1;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.columnModel == null ? 0 : this.columnModel.getID().hashCode());
        result = 31 * result + (this.fieldName == null ? 0 : this.fieldName.hashCode());
        result = 31 * result + (this.queryTable == null ? 0 : this.queryTable.hashCode());
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
        QueryField other = (QueryField)obj;
        if (this.columnModel == null) {
            if (other.columnModel != null) {
                return false;
            }
        } else {
            if (other.columnModel == null) {
                return false;
            }
            if (!this.columnModel.getID().equals(other.columnModel.getID())) {
                return false;
            }
        }
        if (this.fieldName == null ? other.fieldName != null : !this.fieldName.equals(other.fieldName)) {
            return false;
        }
        return !(this.queryTable == null ? other.queryTable != null : !this.queryTable.equals(other.queryTable));
    }

    public String dimensionValueSetToString(DimensionValueSet dimensionValueSet) {
        StringBuilder result = new StringBuilder(64);
        int count = dimensionValueSet.size();
        for (int i = 0; i < count; ++i) {
            if (dimensionValueSet.getValue(i) == null) continue;
            result.append(dimensionValueSet.getName(i));
            result.append('=');
            result.append("\\");
            result.append("\"");
            result.append(dimensionValueSet.getValue(i));
            result.append("\\");
            result.append("\"");
            result.append(",");
        }
        return result.toString().substring(0, result.toString().length() - 1);
    }

    public String getReferFieldKey() {
        if (this.columnModel != null) {
            return this.columnModel.getReferColumnID();
        }
        return null;
    }

    public String getFieldCode() {
        if (this.columnModel != null) {
            return this.columnModel.getCode();
        }
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public boolean isNeedAccountVersion() {
        return this.needAccountVersion;
    }

    public void setNeedAccountVersion(boolean needAccountVersion) {
        this.needAccountVersion = needAccountVersion;
    }

    public boolean isAccountField() {
        return this.queryTable.isAccountTable();
    }

    public boolean isMultival() {
        if (this.columnModel != null) {
            return this.columnModel.isMultival();
        }
        return false;
    }

    public String getSceneId() {
        if (this.columnModel != null) {
            return this.columnModel.getSceneId();
        }
        return null;
    }

    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append(this.queryTable.getTableName()).append("[");
        buff.append(this.getFieldName());
        if (this.getPeriodModifier() != null) {
            buff.append(",").append(this.getPeriodModifier());
        }
        if (this.getDimensionRestriction() != null) {
            buff.append(",").append(this.getDimensionRestriction());
        }
        if (this.getIsLj()) {
            buff.append(",LJ");
        }
        buff.append("]");
        return buff.toString();
    }
}

