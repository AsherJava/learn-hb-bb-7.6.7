/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.engine.IDataListener
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.memdb.api.DBCursor
 *  com.jiuqi.nvwa.memdb.api.DBTable
 *  com.jiuqi.nvwa.memdb.api.query.DBAggregation
 *  com.jiuqi.nvwa.memdb.api.query.DBNullsMode
 *  com.jiuqi.nvwa.memdb.api.query.DBQuery
 *  com.jiuqi.nvwa.memdb.api.query.DBQueryBuilder
 *  com.jiuqi.nvwa.memdb.api.query.DBSortMode
 *  com.jiuqi.nvwa.nrdb.NrdbStorageManager
 */
package com.jiuqi.np.dataengine.nrdb.query;

import com.jiuqi.bi.adhoc.engine.IDataListener;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.impl.ITableConditionProvider;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.PeriodConditionNode;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryInfo;
import com.jiuqi.np.dataengine.nrdb.query.DBQueryResultSet;
import com.jiuqi.np.dataengine.nrdb.query.GroupQueryInfo;
import com.jiuqi.np.dataengine.parse.LJSQLInfo;
import com.jiuqi.np.dataengine.query.MemorySteamLoader;
import com.jiuqi.np.dataengine.query.OrderByItem;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QueryTableColFilterValues;
import com.jiuqi.np.dataengine.reader.DataSetReader;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.memdb.api.DBCursor;
import com.jiuqi.nvwa.memdb.api.DBTable;
import com.jiuqi.nvwa.memdb.api.query.DBAggregation;
import com.jiuqi.nvwa.memdb.api.query.DBNullsMode;
import com.jiuqi.nvwa.memdb.api.query.DBQuery;
import com.jiuqi.nvwa.memdb.api.query.DBQueryBuilder;
import com.jiuqi.nvwa.memdb.api.query.DBSortMode;
import com.jiuqi.nvwa.nrdb.NrdbStorageManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class DBQueryExecutor {
    protected int fieldIndex = 1;
    protected int rowKeyFieldStartIndex;
    protected DBQueryInfo queryInfo;
    protected DBQueryBuilder dbQueryBuilder = new DBQueryBuilder();
    protected int bizkeyOrderFieldIndex;
    protected TableModelRunInfo tableInfo;
    protected boolean queryNothing = false;

    public void init(QueryContext qContext, DBQueryInfo queryInfo) throws Exception {
        this.queryInfo = queryInfo;
        String physicalTableName = qContext.getPhysicalTableName(queryInfo.primaryTable);
        if (queryInfo.primaryTable.getTableName().equals(physicalTableName)) {
            this.tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(physicalTableName);
        } else {
            this.tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(physicalTableName, false);
            TableModelRunInfo originalTableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(queryInfo.primaryTable.getTableName());
            this.tableInfo.setInnerDimensions(originalTableInfo.getInnerDimensions());
        }
        IQueryFieldDataReader dataReader = qContext.getDataReader();
        if (dataReader == null) {
            dataReader = new DataSetReader(qContext);
            qContext.setDataReader(dataReader);
        }
    }

    public boolean buildQuery(QueryContext qContext) throws Exception {
        this.fieldIndex = 1;
        this.appendQueryFields(qContext);
        this.appendRowKeyFields(qContext);
        boolean needMemoryFilter = this.buildFilterCondition(qContext);
        this.buildGroupBy(qContext);
        this.buildOrderBy(qContext);
        return needMemoryFilter;
    }

    public int readData(QueryContext qContext, IDataListener listener) throws Exception {
        return this.readData(qContext, listener, 0, 0);
    }

    public abstract int readData(QueryContext var1, IDataListener var2, int var3, int var4) throws Exception;

    public abstract void readToMemorySteamLoader(QueryContext var1, MemorySteamLoader var2) throws Exception;

    public DBQueryResultSet getDBQueryResultSet(QueryContext qContext) throws Exception {
        if (this.queryNothing) {
            return null;
        }
        DBTable dbTable = NrdbStorageManager.getInstance().openTable(this.tableInfo.getTableModelDefine());
        DBQuery dbQuery = this.dbQueryBuilder.build();
        if (DataEngineConsts.DATA_ENGINE_DEBUG) {
            qContext.getMonitor().debug("query " + dbTable.getName() + ":\n" + dbQuery, DataEngineConsts.DebugLogType.SQL);
        }
        DBCursor cursor = dbTable.query(dbQuery);
        return new DBQueryResultSet(dbTable, cursor);
    }

    public int runQuery(QueryContext qContext, MemoryDataSet<QueryField> result) throws Exception {
        return this.runQuery(qContext, result, 0, 0);
    }

    public abstract int runQuery(QueryContext var1, MemoryDataSet<QueryField> var2, int var3, int var4) throws Exception;

    private void appendQueryFields(QueryContext qContext) throws ParseException {
        DataModelDefinitionsCache dataModelDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
        for (QueryField queryField : this.queryInfo.QueryFields) {
            DBAggregation aggregation = this.getFieldAggregation(queryField);
            String fieldName = queryField.getFieldName();
            String fieldAlias = "c_".concat(String.valueOf(this.fieldIndex));
            this.dbQueryBuilder.select(fieldName, fieldAlias, aggregation);
            QueryFieldInfo fieldInfo = qContext.getDataReader().putIndex(dataModelDefinitionsCache, queryField, this.fieldIndex);
            fieldInfo.dimensionName = this.tableInfo.getDimensionName(queryField.getFieldCode());
            ++this.fieldIndex;
        }
    }

    private void appendRowKeyFields(QueryContext qContext) {
        ColumnModelDefine bizOrderField;
        int i;
        this.rowKeyFieldStartIndex = this.fieldIndex;
        QueryTable primaryTable = this.queryInfo.primaryTable;
        if (this.queryInfo.loopDimensions == null) {
            this.queryInfo.loopDimensions = new DimensionSet(primaryTable.getTableDimensions());
            for (i = 0; i < primaryTable.getTableDimensions().size(); ++i) {
                String dimName = primaryTable.getTableDimensions().get(i);
                if (primaryTable.getDimensionRestriction() != null && !primaryTable.getDimensionRestriction().hasValue(dimName)) {
                    this.queryInfo.loopDimensions.removeDimension(dimName);
                }
                if (primaryTable.getPeriodModifier() == null) continue;
                this.queryInfo.loopDimensions.removeDimension("DATATIME");
            }
        }
        for (i = 0; i < this.queryInfo.loopDimensions.size(); ++i) {
            String keyName = this.queryInfo.loopDimensions.get(i);
            ColumnModelDefine keyField = this.tableInfo.getDimensionField(keyName);
            String keyfieldName = keyField.getName();
            DBAggregation aggregation = null;
            if (this.isGroupingQuery() || primaryTable.getIsLj()) {
                aggregation = keyfieldName.equals("DATATIME") ? DBAggregation.MAX : DBAggregation.MIN;
            }
            String fieldAlias = "c_" + this.fieldIndex;
            this.dbQueryBuilder.select(keyfieldName, fieldAlias, aggregation);
            ++this.fieldIndex;
        }
        ColumnModelDefine inputOrderField = this.tableInfo.getOrderField();
        if (inputOrderField != null) {
            String fieldName = inputOrderField.getName();
            DBAggregation aggregation = this.isGroupingQuery() || primaryTable.getIsLj() ? DBAggregation.MIN : null;
            String fieldAlias = "c_" + this.fieldIndex;
            this.dbQueryBuilder.select(fieldName, fieldAlias, aggregation);
            ++this.fieldIndex;
        }
        if ((bizOrderField = this.tableInfo.getBizOrderField()) != null) {
            String fieldName = bizOrderField.getName();
            DBAggregation aggregation = this.isGroupingQuery() || primaryTable.getIsLj() ? DBAggregation.MIN : null;
            String fieldAlias = "c_" + this.fieldIndex;
            this.dbQueryBuilder.select(fieldName, fieldAlias, aggregation);
            this.bizkeyOrderFieldIndex = this.fieldIndex++;
        }
    }

    private boolean buildFilterCondition(QueryContext qContext) throws InterpretException {
        QueryTable primaryTable = this.queryInfo.primaryTable;
        DimensionValueSet masterkeys = new DimensionValueSet(qContext.getMasterKeys());
        DimensionValueSet tableRestriction = primaryTable.getDimensionRestriction();
        this.filterByMasterKeys(qContext, primaryTable, masterkeys, tableRestriction);
        this.filterByTableCondition(qContext);
        this.filterByTableRestriction(qContext, primaryTable, tableRestriction);
        this.filterByColValues(qContext);
        return this.filterByRowFilter(qContext);
    }

    private void filterByMasterKeys(QueryContext qContext, QueryTable primaryTable, DimensionValueSet masterkeys, DimensionValueSet tableRestriction) {
        ExecutorContext exeContext = qContext.getExeContext();
        IFmlExecEnvironment env = exeContext.getEnv();
        String linkAlias = qContext.getTableLinkAliaMap().get(primaryTable);
        HashSet<String> dimensionConditions = new HashSet<String>();
        for (int i = 0; i < masterkeys.size(); ++i) {
            boolean isUnitDimension;
            String keyName = masterkeys.getName(i);
            Object keyValue = masterkeys.getValue(i);
            if (dimensionConditions.contains(keyName)) continue;
            boolean bl = isUnitDimension = env != null && keyName.equals(env.getUnitDimesion(exeContext));
            if (tableRestriction != null && tableRestriction.hasValue(keyName)) continue;
            String dimensionFieldName = keyName;
            ColumnModelDefine dimensionField = this.tableInfo.getDimensionField(keyName);
            if (linkAlias != null && isUnitDimension) {
                String relatedUnitDimName = null;
                if (dimensionField == null) {
                    IDataModelLinkFinder dataLinkFinder = env.getDataModelLinkFinder();
                    relatedUnitDimName = dataLinkFinder.getRelatedUnitDimName(exeContext, linkAlias, keyName);
                    if (relatedUnitDimName != null) {
                        keyName = relatedUnitDimName;
                        dimensionField = this.tableInfo.getDimensionField(keyName);
                        keyValue = DataEngineUtil.processLinkedUnit(qContext, this.queryInfo.unitKeyMap, primaryTable, exeContext, keyName, keyValue);
                    }
                } else {
                    relatedUnitDimName = keyName;
                    keyValue = DataEngineUtil.processLinkedUnit(qContext, this.queryInfo.unitKeyMap, primaryTable, exeContext, keyName, keyValue);
                }
            }
            if (dimensionField == null) continue;
            dimensionFieldName = dimensionField.getName();
            int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
            if (StringUtils.isEmpty((String)dimensionFieldName)) continue;
            dimensionConditions.add(keyName);
            if (keyValue == null) {
                this.queryNothing = true;
                continue;
            }
            if (keyName.equals("DATATIME")) {
                keyValue = DataEngineUtil.getPeriodValue(qContext, this.tableInfo, primaryTable, env, linkAlias, keyValue);
                if (primaryTable.getIsLj()) {
                    StringBuilder ljFilterExp = new StringBuilder();
                    ljFilterExp.append(dimensionFieldName).append(" like '").append((String)keyValue, 0, 5).append("%'");
                    ljFilterExp.append(" and ");
                    ljFilterExp.append(dimensionFieldName).append("<='").append((String)keyValue).append("'");
                    this.dbQueryBuilder.filterByExpression(ljFilterExp.toString());
                    continue;
                }
                this.filterByKey(qContext, keyName, isUnitDimension, dimensionFieldName, keyValue, dimDataType, linkAlias);
                continue;
            }
            this.filterByKey(qContext, keyName, isUnitDimension, dimensionFieldName, keyValue, dimDataType, linkAlias);
        }
    }

    private void filterByTableRestriction(QueryContext qContext, QueryTable primaryTable, DimensionValueSet tableRestriction) {
        if (tableRestriction != null) {
            for (int i = 0; i < tableRestriction.size(); ++i) {
                ColumnModelDefine dimensionField;
                String keyName = tableRestriction.getName(i);
                Object keyValue = tableRestriction.getValue(i);
                if (keyValue == null) continue;
                if (!primaryTable.getIsLj() && keyValue instanceof PeriodConditionNode) {
                    String periodValue;
                    PeriodConditionNode conditionNode = (PeriodConditionNode)keyValue;
                    IASTNode node = conditionNode.getFilterNode();
                    try {
                        keyValue = node.evaluate((IContext)qContext);
                    }
                    catch (SyntaxException e) {
                        qContext.getMonitor().exception((Exception)((Object)e));
                    }
                    if (keyName.equals("DATATIME") && (periodValue = (String)keyValue).length() == 4) {
                        keyValue = String.valueOf(qContext.getPeriodWrapper().getYear()) + conditionNode.getTypeCode() + periodValue;
                    }
                }
                if ((dimensionField = this.tableInfo.getDimensionField(keyName)) == null) continue;
                String dimensionFieldName = dimensionField.getName();
                int dimDataType = DataTypesConvert.fieldTypeToDataType(dimensionField.getColumnType());
                if (StringUtils.isEmpty((String)dimensionFieldName)) continue;
                if (primaryTable.getIsLj() && keyValue instanceof PeriodConditionNode) {
                    PeriodConditionNode conditionNode = (PeriodConditionNode)keyValue;
                    StringBuilder filterExp = new StringBuilder();
                    PeriodConditionNode filterNode = conditionNode;
                    filterExp.append(dimensionFieldName).append(" like '").append(qContext.getPeriodWrapper().getYear()).append(filterNode.getTypeCode()).append("%'");
                    filterExp.append(" and ");
                    qContext.getCache().put("option.periodSqlField", dimensionFieldName);
                    try {
                        filterExp.append(filterNode.getFilterNode().interpret((IContext)qContext, Language.SQL, (Object)new LJSQLInfo()));
                    }
                    catch (Exception e) {
                        qContext.getMonitor().exception(e);
                    }
                    this.dbQueryBuilder.filterByExpression(filterExp.toString());
                    continue;
                }
                this.filterByValue(qContext, dimensionFieldName, keyValue, dimDataType);
            }
        }
    }

    private void filterByTableCondition(QueryContext qContext) {
        ITableConditionProvider conditionProvider = qContext.getTableConditionProvider();
        if (conditionProvider != null) {
            String realTableName = this.tableInfo.getTableModelDefine().getName();
            Map<String, String> argMap = conditionProvider.getTableCondition(qContext.getExeContext(), realTableName);
            if (argMap != null) {
                Set<Map.Entry<String, String>> entrySet = argMap.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    String fieldName = entry.getKey();
                    String value = entry.getValue();
                    this.filterByValue(qContext, fieldName, value, 6);
                }
            }
        }
    }

    private void filterByKey(QueryContext qContext, String keyName, boolean isUnitDimension, String keyFieldName, Object keyValue, int dimDataType, String linkAlias) {
        if (isUnitDimension) {
            List<String> dims = qContext.getDimsToBindRelation(this.tableInfo, this.queryInfo.primaryTable, keyName, keyFieldName);
            if (keyValue instanceof List) {
                List mainDimValues = (List)keyValue;
                Map<String, Map<String, List<String>>> allEffectiveRelationDimValues = qContext.getAllEffectiveRelationDimValues(dims, mainDimValues, linkAlias, this.queryInfo.unitKeyMap);
                if (allEffectiveRelationDimValues != null && allEffectiveRelationDimValues.size() > 0) {
                    for (Map.Entry<String, Map<String, List<String>>> entry : allEffectiveRelationDimValues.entrySet()) {
                        String dim = entry.getKey();
                        Map<String, List<String>> valueMap = entry.getValue();
                        ArrayList tuples = new ArrayList();
                        String dimensionFieldName = this.tableInfo.getDimensionField(dim).getName();
                        for (Map.Entry<String, List<String>> e : valueMap.entrySet()) {
                            String value1 = e.getKey();
                            for (String value2 : e.getValue()) {
                                tuples.add(Stream.of(value1, value2).collect(Collectors.toList()));
                            }
                        }
                        this.dbQueryBuilder.filterByTuples((Collection)Stream.of(keyFieldName, dimensionFieldName).collect(Collectors.toList()), tuples);
                    }
                }
                this.filterByValue(qContext, keyFieldName, keyValue, dimDataType);
            } else {
                Map<String, List<String>> relationDimValues = qContext.getEffectiveRelationDimValues(dims, keyValue.toString(), linkAlias, this.queryInfo.unitKeyMap);
                if (relationDimValues != null) {
                    for (Map.Entry<String, List<String>> entry : relationDimValues.entrySet()) {
                        String dimension = entry.getKey();
                        List<String> dimValues = entry.getValue();
                        String dimensionFieldName = this.tableInfo.getDimensionField(dimension).getName();
                        ArrayList tuples = new ArrayList();
                        String value1 = keyValue.toString();
                        for (String value2 : dimValues) {
                            tuples.add(Stream.of(value1, value2).collect(Collectors.toList()));
                        }
                        this.dbQueryBuilder.filterByTuples((Collection)Stream.of(keyFieldName, dimensionFieldName).collect(Collectors.toList()), tuples);
                    }
                }
                this.filterByValue(qContext, keyFieldName, keyValue, dimDataType);
            }
        } else {
            this.filterByValue(qContext, keyFieldName, keyValue, dimDataType);
        }
    }

    private void filterByValue(QueryContext qContext, String fieldName, Object value, int dataType) {
        if (value instanceof List) {
            List valueList = (List)value;
            if (valueList.size() == 0) {
                this.queryNothing = true;
            } else {
                this.filterByList(fieldName, dataType, valueList);
            }
        } else if (dataType == 6) {
            this.dbQueryBuilder.filterByValues(fieldName, new String[]{value.toString()});
        } else {
            this.dbQueryBuilder.filterByExpression(fieldName + "=" + value.toString());
        }
    }

    private void filterByColValues(QueryContext qContext) {
        QueryTableColFilterValues filterValues = this.queryInfo.getColFilterValues();
        if (filterValues != null) {
            for (QueryField queryField : this.queryInfo.QueryFields) {
                String fieldName = queryField.getFieldName();
                List<Object> valueList = filterValues.getColFilterValues(queryField);
                if (valueList == null) continue;
                if (valueList.size() <= 0) {
                    this.queryNothing = true;
                    continue;
                }
                String nullValueFilter = null;
                ArrayList<Object> notNullValueList = new ArrayList<Object>(valueList.size());
                for (Object value : valueList) {
                    if (value == null && nullValueFilter == null) {
                        nullValueFilter = fieldName + " is null ";
                        continue;
                    }
                    notNullValueList.add(value);
                }
                if (nullValueFilter != null) {
                    this.dbQueryBuilder.filterByExpression(nullValueFilter);
                    if (notNullValueList.isEmpty()) continue;
                    this.filterByList(fieldName, queryField.getDataType(), notNullValueList);
                    continue;
                }
                this.filterByList(fieldName, queryField.getDataType(), valueList);
            }
        }
    }

    private void filterByList(String fieldName, int dataType, List<Object> list) {
        if (dataType == 6) {
            this.dbQueryBuilder.filterByValues(fieldName, (Collection)list.stream().map(Object::toString).collect(Collectors.toList()));
        } else {
            StringBuilder buff = new StringBuilder();
            buff.append(fieldName).append(" in {");
            list.stream().forEach(o -> buff.append(o.toString()).append(","));
            buff.setLength(buff.length() - 1);
            buff.append("}");
            this.dbQueryBuilder.filterByExpression(buff.toString());
        }
    }

    private boolean filterByRowFilter(QueryContext qContext) throws InterpretException {
        boolean needMemoryFilter = this.queryInfo.needMemoryFilter;
        if (!needMemoryFilter && this.queryInfo.rowFilterNode != null) {
            if (this.queryInfo.rowFilterNode.support(Language.SQL)) {
                String rowFilter = this.queryInfo.rowFilterNode.interpret((IContext)qContext, Language.FORMULA, (Object)new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA, false));
                this.dbQueryBuilder.filterByExpression(rowFilter);
            } else {
                needMemoryFilter = true;
            }
        }
        return needMemoryFilter;
    }

    protected void buildOrderBy(QueryContext qContext) throws ParseException {
        List<ColumnModelDefine> dimFields;
        boolean needDefaultOrderby = false;
        if (this.queryInfo.orderByItems != null && !this.queryInfo.orderByItems.isEmpty()) {
            for (OrderByItem orderByItem : this.queryInfo.orderByItems) {
                QueryField queryField = this.getOrderQueryField(orderByItem);
                if (queryField == null) continue;
                if (orderByItem.specified) {
                    this.dbQueryBuilder.orderBy(queryField.getFieldName(), DBSortMode.ASC, DBNullsMode.DEFAULT, true);
                    continue;
                }
                this.dbQueryBuilder.orderBy(queryField.getFieldName(), orderByItem.descending ? DBSortMode.DESC : DBSortMode.ASC);
            }
        } else {
            needDefaultOrderby = true;
        }
        if (needDefaultOrderby && this.queryInfo.useDefaultOrderBy && !this.queryInfo.ignoreDefaultOrderBy && (dimFields = this.tableInfo.getDimFields()) != null && dimFields.size() > 0) {
            for (ColumnModelDefine dimField : dimFields) {
                if (this.tableInfo.isBizOrderField(dimField.getName())) continue;
                this.dbQueryBuilder.orderBy(new String[]{dimField.getName()});
            }
        }
        if (this.tableInfo.getOrderField() != null && !this.queryInfo.ignoreDefaultOrderBy) {
            this.dbQueryBuilder.orderBy(new String[]{this.tableInfo.getOrderField().getName()});
        }
        if (this.tableInfo.getBizOrderField() != null && !this.queryInfo.ignoreDefaultOrderBy) {
            this.dbQueryBuilder.orderBy(new String[]{this.tableInfo.getBizOrderField().getName()});
        }
    }

    private QueryField getOrderQueryField(OrderByItem orderByItem) throws ParseException {
        QueryField queryField = null;
        if (orderByItem.field != null) {
            queryField = orderByItem.field;
        } else if (orderByItem.expr != null) {
            if (orderByItem.expr instanceof DynamicDataNode) {
                DynamicDataNode dataNode = (DynamicDataNode)orderByItem.expr;
                queryField = dataNode.getQueryField();
            } else {
                throw new ParseException("\u4e0d\u652f\u6301\u8868\u8fbe\u5f0f\u6392\u5e8f");
            }
        }
        return queryField;
    }

    protected void buildGroupBy(QueryContext qContext) throws Exception {
        if (this.queryInfo.primaryTable.getIsLj()) {
            TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.queryInfo.primaryTable.getTableName());
            ArrayList<String> getGroupByFields = new ArrayList<String>();
            for (ColumnModelDefine dimensionField : tableInfo.getDimFields()) {
                if (dimensionField.getCode().equals("DATATIME")) continue;
                getGroupByFields.add(dimensionField.getName());
            }
            this.dbQueryBuilder.groupBy(getGroupByFields);
        } else {
            GroupQueryInfo groupInfo = this.queryInfo.groupQueryInfo;
            if (groupInfo == null || groupInfo.grpByColIndex2Node == null || groupInfo.grpByColIndex2Node.size() <= 0) {
                return;
            }
            ArrayList<String> getGroupByFields = new ArrayList<String>();
            for (Integer columnIndex : groupInfo.groupColumns) {
                ASTNode node = groupInfo.grpByColIndex2Node.get(columnIndex);
                if (node == null) {
                    throw new IncorrectQueryException("\u4e0d\u652f\u6301\u7684\u5206\u7ec4\u8868\u8fbe\u5f0f");
                }
                QueryField queryField = ExpressionUtils.extractQueryField((IASTNode)node);
                if (queryField != null) {
                    getGroupByFields.add(queryField.getFieldName());
                    continue;
                }
                throw new IncorrectQueryException("\u4e0d\u652f\u6301\u7684\u5206\u7ec4\u8868\u8fbe\u5f0f");
            }
            this.dbQueryBuilder.groupBy(getGroupByFields);
        }
    }

    protected boolean isGroupingQuery() {
        return this.queryInfo.groupQueryInfo != null;
    }

    protected DBAggregation getFieldAggregation(QueryField queryField) {
        if (queryField.getIsLj()) {
            return DBAggregation.SUM;
        }
        GroupQueryInfo groupInfo = this.queryInfo.groupQueryInfo;
        if (groupInfo == null) {
            return null;
        }
        FieldGatherType gatherType = groupInfo.uidGatherTypes.get(queryField.getUID());
        int dataType = queryField.getDataType();
        if (gatherType != null) {
            switch (gatherType) {
                case FIELD_GATHER_SUM: {
                    if (dataType == 4 || dataType == 3 || dataType == 10) {
                        return DBAggregation.SUM;
                    }
                    return DBAggregation.MIN;
                }
                case FIELD_GATHER_AVG: {
                    return DBAggregation.AVG;
                }
                case FIELD_GATHER_COUNT: {
                    return DBAggregation.COUNT;
                }
                case FIELD_GATHER_MIN: {
                    return DBAggregation.MIN;
                }
                case FIELD_GATHER_MAX: {
                    return DBAggregation.MAX;
                }
                case FIELD_GATHER_NONE: {
                    return null;
                }
            }
        }
        if (dataType == 4 || dataType == 3 || dataType == 10) {
            return DBAggregation.SUM;
        }
        return DBAggregation.MIN;
    }

    public DBQueryInfo getQueryInfo() {
        return this.queryInfo;
    }

    public int getRowKeyFieldStartIndex() {
        return this.rowKeyFieldStartIndex;
    }

    public int getBizkeyOrderFieldIndex() {
        return this.bizkeyOrderFieldIndex;
    }
}

