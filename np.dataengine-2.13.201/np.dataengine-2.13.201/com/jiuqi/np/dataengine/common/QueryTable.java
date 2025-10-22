/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryFieldBuilder;
import com.jiuqi.np.dataengine.common.SpecialValue;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import java.io.Serializable;

public final class QueryTable
implements Serializable,
Comparable<QueryTable> {
    private static final long serialVersionUID = 8678106363066538905L;
    private int hashCode = -1;
    private DataEngineConsts.QueryTableType tableType = DataEngineConsts.QueryTableType.COMMON;
    private PeriodType tablePeriodType;
    private String region;
    private String tableAlias;
    private String tableName;
    private DimensionSet tableDimensions;
    private DimensionSet openDimensions;
    private PeriodModifier periodModifier;
    private DimensionValueSet dimensionRestriction;
    private boolean isLj;

    public QueryTable(String alias, String tableName) {
        this.setAlias(alias);
        this.setTableName(tableName);
    }

    public QueryTable(QueryFieldBuilder queryBuilder) {
        this.setTableName(queryBuilder.getTableName());
        this.setTableDimensions(queryBuilder.getTableDimensions());
        this.setOpenDimensions(queryBuilder.getOpenDimensions());
        this.setPeriodModifier(queryBuilder.getPeriodModifier());
        this.setIsLj(queryBuilder.getIsLj());
        this.setDimensionRestriction(queryBuilder.getDimensionRestriction());
        this.setAlias(this.createAlias());
    }

    public QueryTable(String tableName, DimensionSet tableDimensions) {
        this.setTableName(tableName);
        this.setTableDimensions(tableDimensions);
        this.setOpenDimensions(tableDimensions);
        this.setPeriodModifier(null);
        this.setDimensionRestriction(null);
        this.setAlias(this.createAlias());
    }

    public QueryTable(String tableAlias, QueryFieldBuilder queryBuilder) {
        this.setAlias(tableAlias);
        this.setTableName(queryBuilder.getTableName());
        this.setTableDimensions(queryBuilder.getTableDimensions());
        this.setOpenDimensions(queryBuilder.getOpenDimensions());
        this.setPeriodModifier(queryBuilder.getPeriodModifier());
        this.setIsLj(queryBuilder.getIsLj());
        this.setDimensionRestriction(queryBuilder.getDimensionRestriction());
    }

    public String getAlias() {
        return this.tableAlias;
    }

    public void setAlias(String value) {
        this.tableAlias = value;
    }

    private String createAlias() {
        if (this.getIsSimple()) {
            return this.tableName;
        }
        return this.tableName + this.hashCode();
    }

    public String getTableName() {
        return this.tableName;
    }

    private void setTableName(String value) {
        this.tableName = value;
    }

    public DimensionSet getTableDimensions() {
        return this.tableDimensions;
    }

    private void setTableDimensions(DimensionSet value) {
        this.tableDimensions = value;
    }

    public DimensionSet getOpenDimensions() {
        return this.openDimensions;
    }

    private void setOpenDimensions(DimensionSet value) {
        this.openDimensions = value;
    }

    public PeriodModifier getPeriodModifier() {
        return this.periodModifier;
    }

    public void setPeriodModifier(PeriodModifier value) {
        this.periodModifier = value;
    }

    public DimensionValueSet getDimensionRestriction() {
        return this.dimensionRestriction;
    }

    public void setDimensionRestriction(DimensionValueSet value) {
        this.dimensionRestriction = value;
    }

    public boolean getIsLj() {
        return this.isLj;
    }

    public void setIsLj(boolean value) {
        this.isLj = value;
    }

    public boolean isTableDimension(String dimension) {
        return this.getTableDimensions().indexOf(dimension) >= 0;
    }

    public boolean getIsSimple() {
        return this.getPeriodModifier() == null && this.getDimensionRestriction() == null && !this.isLj;
    }

    public boolean hasSumMerge() {
        return this.getDimensionRestriction() != null && this.getDimensionRestriction().hasSumMerge();
    }

    public boolean hasDimensionTranslate() {
        if (this.getDimensionRestriction() == null) {
            return false;
        }
        int c = this.getDimensionRestriction().getDimensionSet().size();
        for (int i = 0; i < c; ++i) {
            Object rv = this.getDimensionRestriction().getValue(i);
            if (!(rv instanceof SpecialValue) || rv == DimensionValueSet.SumMergeDimValue || rv == DimensionValueSet.NoStrictDimValue) continue;
            return true;
        }
        return false;
    }

    public boolean isCommonDataTable() {
        return this.tableType == DataEngineConsts.QueryTableType.COMMON;
    }

    public boolean isDimensionTable() {
        return this.tableType == DataEngineConsts.QueryTableType.DIMENSION;
    }

    public boolean isPeriodTable() {
        return this.tableType == DataEngineConsts.QueryTableType.PERIOD;
    }

    public boolean isAccountTable() {
        return this.tableType == DataEngineConsts.QueryTableType.ACCOUNT;
    }

    public boolean isAccountHisTable() {
        return this.tableType == DataEngineConsts.QueryTableType.ACCOUNT_HIS;
    }

    public boolean isAccountRptTable() {
        return this.tableType == DataEngineConsts.QueryTableType.ACCOUNT_RPT;
    }

    public DataEngineConsts.QueryTableType getTableType() {
        return this.tableType;
    }

    public void setTableType(DataEngineConsts.QueryTableType tableType) {
        this.tableType = tableType;
    }

    public int hashCode() {
        if (this.hashCode == -1) {
            int prime = 31;
            int result = 1;
            result = 31 * result + (this.dimensionRestriction == null ? 0 : this.dimensionRestriction.hashCode());
            result = 31 * result + (this.isLj ? 1231 : 1237);
            result = 31 * result + (this.periodModifier == null ? 0 : this.periodModifier.hashCode());
            result = 31 * result + (this.region == null ? 0 : this.region.hashCode());
            this.hashCode = result = 31 * result + (this.tableName == null ? 0 : this.tableName.hashCode());
        }
        return this.hashCode;
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
        QueryTable other = (QueryTable)obj;
        if (this.tableName == null ? other.tableName != null : !this.tableName.equals(other.tableName)) {
            return false;
        }
        if (this.region == null ? other.region != null : !this.region.equals(other.region)) {
            return false;
        }
        if (this.periodModifier == null ? other.periodModifier != null : !this.periodModifier.equals((Object)other.periodModifier)) {
            return false;
        }
        if (this.dimensionRestriction == null ? other.dimensionRestriction != null : !this.dimensionRestriction.equals(other.dimensionRestriction)) {
            return false;
        }
        return this.isLj == other.isLj;
    }

    @Override
    public int compareTo(QueryTable o) {
        return this.hashCode() - o.hashCode();
    }

    public String toString() {
        StringBuilder str = new StringBuilder(this.tableName);
        if (this.getPeriodModifier() != null) {
            str.append(",").append(this.getPeriodModifier());
        }
        if (this.getDimensionRestriction() != null) {
            str.append(",").append(this.getDimensionRestriction());
        }
        if (this.getIsLj()) {
            str.append(".LJ");
        }
        return str.toString();
    }

    public void resetHashCode() {
        this.hashCode = -1;
    }

    public PeriodType getTablePeriodType() {
        return this.tablePeriodType;
    }

    public void setTablePeriodType(PeriodType tablePeriodType) {
        this.tablePeriodType = tablePeriodType;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
        this.resetHashCode();
    }
}

