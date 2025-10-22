/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine.query;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.bql.intf.TableRelation;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainSubQueryAdapter {
    private QueryContext qContext;
    private TableRelation parentTableRelation;
    private TableModelRunInfo primaryTableInfo;
    private TableModelRunInfo parentTableInfo;
    private Map<String, String> primaryColumnMap = new LinkedHashMap<String, String>();
    private Map<String, String> parentColumnMap = new LinkedHashMap<String, String>();
    private String primaryTableAlias;
    private String parentTableAlias;

    public MainSubQueryAdapter(QueryContext qContext, TableRelation parentTableRelation, QueryTable primaryTable) throws ParseException {
        this.qContext = qContext;
        this.parentTableRelation = parentTableRelation;
        this.primaryTableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(primaryTable.getTableName());
        this.parentTableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(parentTableRelation.getDestTableName());
    }

    public void joinParentTable(StringBuilder subQuerySelect, StringBuilder subQueryFrom) {
        String parentTableName = this.parentTableInfo.getTableModelDefine().getName();
        for (Map.Entry<String, String> entry : this.parentColumnMap.entrySet()) {
            subQuerySelect.append(this.parentTableAlias).append(".").append(entry.getKey()).append(" as ").append(entry.getValue()).append(",");
        }
        for (Map.Entry<String, String> entry : this.primaryColumnMap.entrySet()) {
            subQuerySelect.append(this.primaryTableAlias).append(".").append(entry.getKey()).append(" as ").append(entry.getValue()).append(",");
        }
        subQuerySelect.setLength(subQuerySelect.length() - 1);
        subQueryFrom.append(" join ").append(parentTableName).append(" ").append(this.parentTableAlias);
        subQueryFrom.append(" on ");
        boolean and = false;
        for (Map.Entry<String, String> entry : this.parentTableRelation.getFieldMap().entrySet()) {
            if (and) {
                subQueryFrom.append(" and ");
            }
            subQueryFrom.append(this.primaryTableAlias).append(".").append(entry.getKey()).append("=").append(this.parentTableAlias).append(".").append(entry.getValue());
        }
    }

    public boolean isParentTable(QueryTable table) {
        return this.parentTableInfo.getTableModelDefine().getName().equals(table.getTableName());
    }

    public String getColumnName(String columnName, boolean isParent) {
        if (isParent) {
            return this.parentColumnMap.get(columnName);
        }
        return this.primaryColumnMap.get(columnName);
    }

    public String getColumnName(String columnName, QueryField queryField) {
        if (queryField.getTable().getTableName().equals(this.parentTableInfo.getTableModelDefine().getName())) {
            return this.parentColumnMap.get(columnName);
        }
        if (queryField.getTable().getTableName().equals(this.primaryTableInfo.getTableModelDefine().getName())) {
            return this.primaryColumnMap.get(columnName);
        }
        return queryField.getFieldName();
    }

    public String renameColumn(String columnName, String tableName) {
        String reName = columnName;
        if (this.parentTableInfo.getTableModelDefine().getName().equals(tableName)) {
            reName = columnName + "_p";
            this.parentColumnMap.put(columnName, reName);
        } else if (this.primaryTableInfo.getTableModelDefine().getName().equals(tableName)) {
            reName = columnName + "_c";
            this.primaryColumnMap.put(columnName, reName);
        }
        return reName;
    }

    public DimensionSet getQueryDimension() {
        DimensionSet dims = new DimensionSet();
        dims.addAll(this.parentTableInfo.getDimensions());
        dims.addAll(this.primaryTableInfo.getDimensions());
        return dims;
    }

    public ColumnModelDefine getParentDimensionField(String dimension) {
        return this.parentTableInfo.getDimensionField(dimension);
    }

    public String getPrimaryTableAlias() {
        return this.primaryTableAlias;
    }

    public String getParentTableAlias() {
        return this.parentTableAlias;
    }

    public void setPrimaryTableAlias(String primaryTableAlias) {
        this.primaryTableAlias = primaryTableAlias;
        this.parentTableAlias = primaryTableAlias + "_p";
    }

    public TableModelRunInfo getParentTableInfo() {
        return this.parentTableInfo;
    }
}

