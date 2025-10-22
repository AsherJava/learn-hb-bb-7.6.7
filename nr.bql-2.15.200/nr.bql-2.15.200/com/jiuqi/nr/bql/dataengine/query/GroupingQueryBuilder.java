/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.BitMaskAndNullValue
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.data.DataTypes
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.sql.SummarizingMethod
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.dataengine.query;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.BitMaskAndNullValue;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.data.DataTypes;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.sql.SummarizingMethod;
import com.jiuqi.nr.bql.dataengine.impl.GroupingQueryImpl;
import com.jiuqi.nr.bql.dataengine.impl.ReadonlyTableImpl;
import com.jiuqi.nr.bql.dataengine.query.DataQueryBuilder;
import com.jiuqi.nr.bql.dataengine.query.GroupingQuerySqlBuilder;
import com.jiuqi.nr.bql.dataengine.query.QuerySqlBuilder;
import com.jiuqi.nr.bql.datasource.QueryDataReader;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
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
    private HashMap<String, AggrType> uidGatherTypes;
    private ArrayList<Integer> noneGatherCols;
    private List<Integer> groupColumns;
    private HashMap<Integer, ASTNode> grpByColIndex2Node;
    private ArrayList<Integer> grpByColIndexEffectiveInGroupingId;
    private DimensionSet groupDims = new DimensionSet();

    public GroupingQueryBuilder(QueryDataReader reader) {
        super(reader);
    }

    public void buildGroupingQuery(QueryContext qContext, GroupingQueryImpl groupingQueryImpl) throws ExpressionException, SyntaxException, SQLException {
        this.queryVersionDate = groupingQueryImpl.getQueryVersionDate();
        this.table = new ReadonlyTableImpl(qContext, groupingQueryImpl.getMasterKeys(), groupingQueryImpl.getColumns().size());
        this.setTableParams(qContext.getExeContext().getCache(), groupingQueryImpl);
        this.doParseQuery(qContext, groupingQueryImpl);
        this.checkFieldsGatherType(qContext, groupingQueryImpl);
        this.buildQueryRegion(qContext);
        this.queryRegion.sqlBuilder.setSqlConditionProcesser(groupingQueryImpl.getSqlConditionProcesser());
        this.buildQuerySql(qContext);
    }

    @Override
    public QuerySqlBuilder getSqlBuilder(QueryContext qContext) {
        if (this.querySqlBuilder == null) {
            this.querySqlBuilder = new GroupingQuerySqlBuilder();
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
        mainSqlBuilder.setGroupDims(this.groupDims);
        mainSqlBuilder.setQueryVersionDate(this.queryVersionDate);
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
        this.sortGroupingAndDetailRows = queryInfo.getSortGroupingAndDetailRows();
        this.groupColumns = queryInfo.getGroupColumns();
        this.uidGatherTypes = new HashMap();
        this.grpByColIndex2Node = new HashMap();
        this.noneGatherCols = new ArrayList();
        ArrayList<AggrType> gatherTypes = queryInfo.getGatherTypes();
        HashMap<Integer, Boolean> setTypes = queryInfo.getSetTypes();
        HashMap<Integer, BitMaskAndNullValue> grpByColsEffectiveInGroupingId = new HashMap<Integer, BitMaskAndNullValue>();
        int countOfColumnsEffectiveInGroupingId = this.groupColumns.size();
        int c = this.table.fieldsInfoImpl.getFieldCount();
        for (int columnIndex = 0; columnIndex < c; ++columnIndex) {
            ColumnModelDefine columnModel = this.table.fieldsInfoImpl.getFieldDefine(columnIndex);
            if (columnModel == null) {
                if (!setTypes.containsKey(columnIndex) || !setTypes.get(columnIndex).booleanValue()) continue;
                this.noneGatherCols.add(columnIndex);
                continue;
            }
            ASTNode queryNode = (ASTNode)this.queryNodes.get(columnIndex);
            if (this.groupColumns.contains(columnIndex)) {
                if (columnModel != null && columnModel.getColumnType() == ColumnModelType.BLOB) {
                    throw new ExpressionException("\u6570\u636e\u7c7b\u578b\u4e0d\u652f\u6301\u5206\u7ec4\u67e5\u8be2");
                }
                ExecutorContext exeContext = qContext.getExeContext();
                String dimension = exeContext.getCache().getDataModelDefinitionsCache().getDimensionProvider().getFieldDimensionName(exeContext, columnModel);
                if (dimension != null) {
                    this.groupDims.addDimension(dimension);
                }
                gatherTypes.set(columnIndex, AggrType.NONE);
                this.grpByColIndex2Node.put(columnIndex, queryNode);
                int groupIndex = this.groupColumns.indexOf(columnIndex);
                int bitMask = 1 << countOfColumnsEffectiveInGroupingId - groupIndex - 1;
                AbstractData nullValue = DataTypes.getNullValue((int)queryNode.getType((IContext)qContext));
                grpByColsEffectiveInGroupingId.put(columnIndex, new BitMaskAndNullValue(bitMask, nullValue));
                continue;
            }
            if (setTypes.containsKey(columnIndex) && setTypes.get(columnIndex).booleanValue()) {
                this.uidGatherTypes.put(columnModel.getID(), AggrType.NONE);
                this.noneGatherCols.add(columnIndex);
                continue;
            }
            AggrType fieldGatherType = this.getFieldGatherType(columnModel, gatherTypes, columnIndex);
            String statId = columnModel.getID();
            this.uidGatherTypes.put(statId, fieldGatherType);
            if (fieldGatherType != AggrType.NONE) continue;
            this.noneGatherCols.add(columnIndex);
        }
        this.table.setNoneGatherCols(this.noneGatherCols);
        this.grpByColIndexEffectiveInGroupingId = new ArrayList(grpByColsEffectiveInGroupingId.keySet());
    }

    private AggrType getFieldGatherType(ColumnModelDefine columnModel, ArrayList<AggrType> gatherTypes, int columnIndex) {
        AggrType gatherType = gatherTypes.get(columnIndex);
        if (gatherType != AggrType.NONE) {
            return gatherType;
        }
        return columnModel.getAggrType();
    }

    @Override
    public boolean canUseMemoryJoin(int rowCount) {
        return this.queryRegion.getFieldCount() >= 1000 || this.supportFullJoin && this.queryRegion.getSqlBuilder().fullJoinTables.size() > 1;
    }
}

