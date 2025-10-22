/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.sql.SummarizingMethod
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.BitMaskAndNullValue;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.intf.impl.GroupingQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.GroupingTableImpl;
import com.jiuqi.np.dataengine.query.DataQueryBuilder;
import com.jiuqi.np.dataengine.query.GroupingDetailQuerySqlBuilder;
import com.jiuqi.np.dataengine.query.GroupingDetailQuerySqlBuilder_new;
import com.jiuqi.np.dataengine.query.GroupingQuerySqlBuilder;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GroupingQueryBuilder
extends DataQueryBuilder {
    private static final long serialVersionUID = -2103794069808864529L;
    private boolean wantDetail;
    private boolean sortGroupingAndDetailRows;
    private SummarizingMethod summarizingMethod;
    private HashMap<String, FieldGatherType> uidGatherTypes;
    private ArrayList<Integer> noneGatherCols;
    private List<Integer> groupColumns;
    private HashMap<Integer, ASTNode> grpByColIndex2Node;
    private ArrayList<Integer> grpByColIndexEffectiveInGroupingId;

    public void buildGroupingQuery(QueryContext qContext, GroupingQueryImpl groupingQueryImpl) throws ExpressionException, SyntaxException, SQLException {
        this.sqlUpdater = groupingQueryImpl.getQuerySqlUpdater();
        this.table = new GroupingTableImpl(qContext, groupingQueryImpl.getMasterKeys(), groupingQueryImpl.getColumns().size());
        this.setTableParams(qContext.getExeContext().getCache(), groupingQueryImpl);
        this.doParseQuery(qContext, groupingQueryImpl);
        this.checkFieldsGatherType(qContext, groupingQueryImpl);
        this.initSystemFields();
        this.buildQueryRegion(qContext);
        this.applyRowAuthority(qContext, groupingQueryImpl);
        this.queryRegion.getSqlBuilder().setSqlJoinProvider(groupingQueryImpl.getSqlJoinProvider());
        this.buildQuerySql(qContext);
    }

    @Override
    public QuerySqlBuilder getSqlBuilder(QueryContext qContext) {
        if (this.querySqlBuilder == null) {
            if (this.queryModule && !this.queryRegion.justLeftJoin(qContext) && this.queryRegion.getAllTableFields().size() > 1 && this.queryRegion.getLookupItems().size() == 0) {
                IDatabase database;
                IDatabase iDatabase = database = qContext == null ? null : qContext.getQueryParam().getDatabase();
                this.querySqlBuilder = database != null && !database.getDescriptor().supportFullJoin() ? new GroupingDetailQuerySqlBuilder_new() : (this.queryRegion.getAllTableFields().size() > 2 ? new GroupingQuerySqlBuilder() : new GroupingDetailQuerySqlBuilder());
            } else {
                this.querySqlBuilder = new GroupingQuerySqlBuilder();
            }
        }
        return this.querySqlBuilder;
    }

    @Override
    protected void buildQuerySql(QueryContext qContext) {
        GroupingQuerySqlBuilder mainSqlBuilder = (GroupingQuerySqlBuilder)this.querySqlBuilder;
        mainSqlBuilder.setQueryParam(this.queryParam);
        mainSqlBuilder.setQueryRegion(this.queryRegion);
        mainSqlBuilder.setMasterDimensions(this.table.getMasterDimensions());
        mainSqlBuilder.setWantDetail(this.wantDetail);
        mainSqlBuilder.setSortGroupingAndDetailRows(this.sortGroupingAndDetailRows);
        mainSqlBuilder.setIgnoreDefaultOrderBy(this.ignoreDefaultOrderBy);
        mainSqlBuilder.setSummarizingMethod(this.summarizingMethod);
        mainSqlBuilder.setGroupColumns(this.groupColumns);
        mainSqlBuilder.setUIDGatherTypes(this.uidGatherTypes);
        mainSqlBuilder.setGrpByColIndex2Node(this.grpByColIndex2Node);
        mainSqlBuilder.setGrpByColIndexEffectiveInGroupingId(this.grpByColIndexEffectiveInGroupingId);
        mainSqlBuilder.setQueryVersionStartDate(this.table.getQueryVersionStartDate());
        mainSqlBuilder.setQueryVersionDate(this.table.getQueryVersionDate());
        if (this.mainTableName == null) {
            QueryTable mainTable = null;
            for (QueryTable queryTable : this.queryRegion.getAllTableFields().keySet()) {
                if (mainTable != null && !queryTable.getTableDimensions().containsAll(mainTable.getTableDimensions())) continue;
                mainTable = queryTable;
            }
            this.mainTableName = mainTable.getTableName();
        }
        mainSqlBuilder.setPrimaryTableName(this.mainTableName);
    }

    private void checkFieldsGatherType(QueryContext qContext, GroupingQueryImpl queryInfo) throws SyntaxException, ExpressionException {
        this.wantDetail = queryInfo.getWantDetail();
        this.queryModule = queryInfo.isQueryModule();
        this.sortGroupingAndDetailRows = queryInfo.getSortGroupingAndDetailRows();
        this.groupColumns = queryInfo.getGroupColumns();
        this.summarizingMethod = queryInfo.getSummarizingMethod();
        this.uidGatherTypes = new HashMap();
        this.grpByColIndex2Node = new HashMap();
        this.noneGatherCols = new ArrayList();
        ArrayList<FieldGatherType> gatherTypes = queryInfo.getGatherTypes();
        HashMap<Integer, Boolean> setTypes = queryInfo.getSetTypes();
        HashMap<Integer, BitMaskAndNullValue> grpByColsEffectiveInGroupingId = new HashMap<Integer, BitMaskAndNullValue>();
        int countOfColumnsEffectiveInGroupingId = this.groupColumns.size();
        int c = this.table.fieldsInfoImpl.getFieldCount();
        for (int columnIndex = 0; columnIndex < c; ++columnIndex) {
            FieldDefine fieldDefine = this.table.fieldsInfoImpl.getFieldDefine(columnIndex);
            if (fieldDefine == null) {
                if (!setTypes.containsKey(columnIndex) || !setTypes.get(columnIndex).booleanValue()) continue;
                this.noneGatherCols.add(columnIndex);
                continue;
            }
            ASTNode queryNode = (ASTNode)this.queryNodes.get(columnIndex);
            if (this.groupColumns.contains(columnIndex)) {
                if (fieldDefine != null && fieldDefine.getType() != FieldType.FIELD_TYPE_STRING && fieldDefine.getType() != FieldType.FIELD_TYPE_INTEGER && fieldDefine.getType() != FieldType.FIELD_TYPE_DATE && fieldDefine.getType() != FieldType.FIELD_TYPE_LOGIC && fieldDefine.getType() != FieldType.FIELD_TYPE_GENERAL && fieldDefine.getType() != FieldType.FIELD_TYPE_DATE_TIME && fieldDefine.getType() != FieldType.FIELD_TYPE_TIME && fieldDefine.getType() != FieldType.FIELD_TYPE_UUID && fieldDefine.getType() != FieldType.FIELD_TYPE_DECIMAL && fieldDefine.getType() != FieldType.FIELD_TYPE_FLOAT) {
                    throw new ExpressionException("\u6570\u636e\u7c7b\u578b\u4e0d\u652f\u6301\u5206\u7ec4\u67e5\u8be2");
                }
                gatherTypes.set(columnIndex, FieldGatherType.FIELD_GATHER_NONE);
                this.grpByColIndex2Node.put(columnIndex, queryNode);
                int groupIndex = this.groupColumns.indexOf(columnIndex);
                int bitMask = 1 << countOfColumnsEffectiveInGroupingId - groupIndex - 1;
                AbstractData nullValue = DataTypes.getNullValue(queryNode.getType((IContext)qContext));
                grpByColsEffectiveInGroupingId.put(columnIndex, new BitMaskAndNullValue(bitMask, nullValue));
                continue;
            }
            ColumnModelDefine columnModel = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getColumnModel(fieldDefine);
            if (setTypes.containsKey(columnIndex) && setTypes.get(columnIndex).booleanValue()) {
                this.uidGatherTypes.put(columnModel.getID(), FieldGatherType.FIELD_GATHER_NONE);
                this.noneGatherCols.add(columnIndex);
                continue;
            }
            FieldGatherType fieldGatherType = this.getFieldGatherType(fieldDefine, gatherTypes, columnIndex);
            this.uidGatherTypes.put(columnModel.getID(), fieldGatherType);
            if (fieldGatherType != FieldGatherType.FIELD_GATHER_NONE) continue;
            this.noneGatherCols.add(columnIndex);
        }
        this.table.setGrpByColsEffectiveInGroupingId(grpByColsEffectiveInGroupingId);
        this.table.setNoneGatherCols(this.noneGatherCols);
        this.grpByColIndexEffectiveInGroupingId = new ArrayList<Integer>(grpByColsEffectiveInGroupingId.keySet());
    }

    private FieldGatherType getFieldGatherType(FieldDefine fieldDefine, ArrayList<FieldGatherType> gatherTypes, int columnIndex) {
        FieldGatherType gatherType = gatherTypes.get(columnIndex);
        if (gatherType != FieldGatherType.FIELD_GATHER_NONE) {
            return gatherType;
        }
        return fieldDefine.getGatherType();
    }

    @Override
    public boolean canUseMemoryJoin(int rowCount) {
        return this.queryRegion.getFieldCount() >= 1000 || this.supportFullJoin && this.queryRegion.getSqlBuilder().fullJoinTables.size() > 1 || !this.queryModule && this.queryRegion.getAllTableFields().size() > 1 && !this.supportFullJoin && this.queryRegion.getSqlBuilder().fullJoinTables.size() > 0;
    }
}

