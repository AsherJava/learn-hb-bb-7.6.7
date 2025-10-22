/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$QueryTableType
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IUnitLeafFinder
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine.query;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.bql.dataengine.query.DimQueryInfo;
import com.jiuqi.nr.bql.dataengine.query.MainSubQueryAdapter;
import com.jiuqi.nr.bql.dataengine.query.OrderTempAssistantTable;
import com.jiuqi.nr.bql.dataengine.query.QuerySqlBuilder;
import com.jiuqi.nr.bql.intf.ISqlConditionProcesser;
import com.jiuqi.nr.bql.util.TempAssistantTableUtils;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SubQueryBuilder {
    private QueryContext context;
    private QuerySqlBuilder querySqlBuilder;
    private TableModelRunInfo tableInfo;
    private QueryTable queryTable;
    private boolean needAnd = false;
    private String tableName;
    private String tableAlias;
    private String subQueryAlias;
    private MainSubQueryAdapter mainSubQueryAdapter;

    public SubQueryBuilder(QueryContext context, QuerySqlBuilder querySqlBuilder, TableModelRunInfo tableInfo, QueryTable queryTable) {
        this.context = context;
        this.querySqlBuilder = querySqlBuilder;
        this.tableInfo = tableInfo;
        this.queryTable = queryTable;
        this.tableName = querySqlBuilder.getTableName(context, queryTable);
        this.tableAlias = querySqlBuilder.getTableAlias(context, queryTable);
        this.subQueryAlias = "s" + this.tableAlias;
    }

    public void appendSubQueryTable(StringBuilder sql) throws SQLException {
        StringBuilder subQuerySelect = new StringBuilder(" select ");
        StringBuilder subQueryFrom = new StringBuilder(" from ");
        subQueryFrom.append(this.tableName).append(" ").append(this.subQueryAlias);
        if (this.mainSubQueryAdapter == null) {
            subQuerySelect.append(this.subQueryAlias).append(".*");
        } else {
            this.mainSubQueryAdapter.joinParentTable(subQuerySelect, subQueryFrom);
        }
        StringBuilder subQueryWhere = this.buildWhereCondition(this.subQueryAlias, subQuerySelect, subQueryFrom);
        sql.append("(").append((CharSequence)subQuerySelect).append((CharSequence)subQueryFrom);
        if (subQueryWhere.length() > 0) {
            sql.append(" where ").append((CharSequence)subQueryWhere);
        }
        sql.append(") ").append(this.tableAlias);
    }

    public void joinTable(StringBuilder sql, TableModelRunInfo primaryTableInfo, String primaryTableAlias, String type, List<DimQueryInfo> refDimQueryInfos) throws SQLException, ParseException {
        sql.append(" ").append(type).append(" join ");
        this.appendSubQueryTable(sql);
        sql.append(" on ");
        boolean appendAnd = false;
        for (int i = 0; i < this.queryTable.getTableDimensions().size(); ++i) {
            String dimension = this.queryTable.getTableDimensions().get(i);
            ColumnModelDefine dimensionField = this.tableInfo.getDimensionField(dimension);
            if (dimensionField == null || !this.querySqlBuilder.getPrimaryTable().getTableDimensions().contains(dimension)) continue;
            if (appendAnd) {
                sql.append(" and ");
            }
            String keyfieldName = dimension;
            ColumnModelDefine primaryDimensionField = primaryTableInfo.getDimensionField(dimension);
            keyfieldName = primaryDimensionField.getName();
            sql.append(primaryTableAlias).append(".");
            sql.append(keyfieldName);
            sql.append("=");
            sql.append(this.tableAlias).append(".");
            String dimensionFieldName = DataEngineUtil.getDimensionFieldName((TableModelRunInfo)this.tableInfo, (String)dimension);
            sql.append(dimensionFieldName);
            appendAnd = true;
        }
    }

    private StringBuilder buildWhereCondition(String subQueryAlias, StringBuilder subQuerySelect, StringBuilder subQueryFrom) throws SQLException {
        String splitTableName;
        Map argMap;
        StringBuilder subQueryWhere = new StringBuilder();
        DimensionValueSet masterkeys = new DimensionValueSet(this.context.getMasterKeys());
        ExecutorContext exeContext = this.context.getExeContext();
        for (int i = 0; i < masterkeys.size(); ++i) {
            OrderTempAssistantTable orderTempAssistantTable;
            IUnitLeafFinder unitLeafFinder;
            ISqlConditionProcesser sqlConditionProcesser;
            String keyTableAlias = subQueryAlias;
            String keyName = masterkeys.getName(i);
            Object keyValue = masterkeys.getValue(i);
            String dimensionFieldName = keyName;
            ColumnModelDefine dimensionField = this.tableInfo.getDimensionField(keyName);
            if (dimensionField == null && this.mainSubQueryAdapter != null && (dimensionField = this.mainSubQueryAdapter.getParentDimensionField(keyName)) != null) {
                keyTableAlias = this.mainSubQueryAdapter.getParentTableAlias();
            }
            if ((sqlConditionProcesser = this.querySqlBuilder.getSqlConditionProcesser()) != null && !sqlConditionProcesser.acceptFieldCondition(dimensionField) || dimensionField == null) continue;
            if (keyName.equals(this.context.getExeContext().getUnitDimension()) && this.tableInfo.getBizOrderField() == null && (unitLeafFinder = this.context.getUnitLeafFinder()) != null) {
                keyValue = this.context.getStatLeafHelper().processUnitLeafs(this.context, unitLeafFinder, keyValue);
            }
            dimensionFieldName = dimensionField.getName();
            int dimDataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType());
            if (dimDataType == 2 && keyName.equals("DATATIME")) {
                keyValue = this.querySqlBuilder.convertPeriodToDate(keyValue);
            }
            if ((orderTempAssistantTable = TempAssistantTableUtils.getContextTempAssistantTables(this.context).get(keyName)) != null) {
                subQuerySelect.append(",").append(orderTempAssistantTable.getTableName()).append(".").append("TEMP_ORDER").append(" as ").append(keyName).append("_").append("TEMP_ORDER");
                subQueryFrom.append(orderTempAssistantTable.getJoinSql(keyTableAlias + "." + dimensionFieldName));
                continue;
            }
            this.querySqlBuilder.appendToCondition(this.context, null, subQueryWhere, keyTableAlias, dimensionFieldName, keyValue, dimDataType, this.needAnd);
            this.needAnd = true;
        }
        SplitTableHelper splitTableHelper = this.context.getQueryParam().getSplitTableHelper();
        if (splitTableHelper != null && this.queryTable.getTableType() != DataEngineConsts.QueryTableType.PERIOD && (argMap = splitTableHelper.getSumSchemeKey(exeContext, splitTableName = splitTableHelper.getCurrentSplitTableName(exeContext, this.queryTable.getTableName()))) != null) {
            Set entrySet = argMap.entrySet();
            for (Map.Entry entry : entrySet) {
                String fieldName = (String)entry.getKey();
                String value = (String)entry.getValue();
                this.querySqlBuilder.appendToCondition(this.context, null, subQueryWhere, subQueryAlias, fieldName, value, 6, this.needAnd);
                this.needAnd = true;
            }
        }
        this.needAnd = this.appendColFilter(subQueryWhere, subQueryAlias, this.needAnd);
        this.needAnd = QuerySqlBuilder.appendVersionFilter(this.context.getQueryParam(), subQueryAlias, this.tableInfo, subQueryWhere, this.querySqlBuilder.getQueryVersionDate(), this.querySqlBuilder.getQueryVersionStartDate(), this.needAnd);
        return subQueryWhere;
    }

    private boolean appendColFilter(StringBuilder condition, String subQueryAlias, boolean needAnd) {
        needAnd = this.querySqlBuilder.appendDimensionFilterByTable(this.context, subQueryAlias, this.queryTable, this.tableInfo, condition, needAnd);
        needAnd = this.querySqlBuilder.appendColFilterByTable(this.context, subQueryAlias, this.queryTable, this.tableInfo, condition, needAnd);
        return needAnd;
    }

    public void setMainSubQueryAdapter(MainSubQueryAdapter mainSubQueryAdapter) {
        this.mainSubQueryAdapter = mainSubQueryAdapter;
        if (mainSubQueryAdapter != null) {
            this.mainSubQueryAdapter.setPrimaryTableAlias(this.subQueryAlias);
        }
    }
}

