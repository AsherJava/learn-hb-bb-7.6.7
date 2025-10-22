/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.operator.And
 *  com.jiuqi.bi.syntax.operator.Equal
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.operator.And;
import com.jiuqi.bi.syntax.operator.Equal;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.common.LookupItem;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.MemorySteamLoader;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import com.jiuqi.np.dataengine.query.UpdateDatas;
import com.jiuqi.np.dataengine.reader.MemoryDataSetReader;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryRegion
implements Iterable<Map.Entry<QueryTable, QueryFields>> {
    private static final Logger logger = LoggerFactory.getLogger(QueryRegion.class);
    public static final int TYPE_SQL_JOIN = 0;
    public static final int TYPE_MEMORY_JOIN = 1;
    public static final int TYPE_STREAM_JOIN = 2;
    protected final Map<QueryTable, QueryFields> tableFields;
    private List<LookupItem> lookupItems;
    private final Set<String> mightWriteTables;
    protected QuerySqlBuilder sqlBuilder;
    private List<QuerySqlBuilder> leftJoinBuilders = null;
    private List<QuerySqlBuilder> fullJoinBuilders = null;
    protected MemoryDataSetReader dataReader = null;
    protected DimensionSet loopDimensions;
    private boolean needOrderJoin;
    private int type = 0;
    protected boolean isFloat = false;
    private boolean floatSumLeft = false;
    private boolean floatStreamCalc = false;
    protected boolean isStatQuery = false;
    protected DimensionSet dimensions;
    private int fieldCount;
    private ENameSet tableAliases = null;
    private String singleTableAlias;

    public QueryRegion(DimensionSet dimensions, QuerySqlBuilder sqlBuilder) {
        this.setDimensions(dimensions);
        this.sqlBuilder = sqlBuilder;
        if (sqlBuilder != null) {
            sqlBuilder.setQueryRegion(this);
        }
        this.tableFields = new HashMap<QueryTable, QueryFields>();
        this.mightWriteTables = new HashSet<String>();
        this.lookupItems = new ArrayList<LookupItem>();
    }

    public QueryRegion(DimensionSet dimensions) {
        this(dimensions, null);
    }

    public final DimensionSet getDimensions() {
        return this.dimensions;
    }

    private void setDimensions(DimensionSet value) {
        this.dimensions = value;
    }

    public final int getFieldCount() {
        return this.fieldCount;
    }

    private void setFieldCount(int value) {
        this.fieldCount = value;
    }

    public final ENameSet getTableAliases() {
        if (this.tableAliases == null) {
            this.tableAliases = new ENameSet();
            for (QueryTable table : this.tableFields.keySet()) {
                this.tableAliases.add(table.getAlias());
            }
        }
        return this.tableAliases;
    }

    public boolean hasDimensionTable() {
        for (QueryTable queryTable : this.tableFields.keySet()) {
            if (!queryTable.isDimensionTable()) continue;
            return true;
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        List<QueryTable> allQueryTables = this.getAllQueryTables();
        sb.append("QueryRegion[");
        for (QueryTable table : allQueryTables) {
            sb.append(table);
            sb.append(',');
        }
        sb.setLength(sb.length() - 1);
        sb.append(']');
        sb.append(this.getDimensions().toString());
        sb.append('(');
        for (QueryTable table : allQueryTables) {
            sb.append(table.getAlias());
            sb.append(',');
        }
        sb.setLength(sb.length() - 1);
        sb.append(')');
        return sb.toString();
    }

    public void printQueryRegion(QueryContext qContext, StringBuilder msg) {
        msg.append(this.toString()).append(this.getInfoStr()).append("\n");
        if (this.tableFields.size() > 0 && this.sqlBuilder != null && this.sqlBuilder.getPrimaryTable() != null) {
            this.printQueryTable(msg, this.sqlBuilder, null);
            if (this.leftJoinBuilders != null) {
                for (QuerySqlBuilder builder : this.leftJoinBuilders) {
                    this.printQueryTable(msg, builder, "left join");
                }
            }
            if (this.fullJoinBuilders != null) {
                for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                    this.printQueryTable(msg, builder, "full join");
                }
            }
        }
    }

    private String getInfoStr() {
        return " {loopDimensions=" + this.loopDimensions + ", needOrderJoin=" + this.needOrderJoin + ", isFloat=" + this.isFloat + ", floatSumLeft=" + this.floatSumLeft + ", fieldCount=" + this.fieldCount + "}";
    }

    private void printQueryTable(StringBuilder msg, QuerySqlBuilder builder, String joinType) {
        if (joinType != null) {
            msg.append("    ").append(joinType).append(" ");
        }
        msg.append(builder.getPrimaryTable());
        msg.append("{");
        for (QueryField queryField : builder.getQueryRegion().getTableFields(builder.getPrimaryTable())) {
            msg.append(queryField.getFieldCode()).append(",");
        }
        msg.setLength(msg.length() - 1);
        msg.append("}\n");
    }

    public final void addQueryField(QueryField queryField) {
        QueryTable queryTable = queryField.getTable();
        QueryFields fields = this.tableFields.get(queryTable);
        if (fields == null) {
            fields = new QueryFields();
            this.tableFields.put(queryTable, fields);
            if (this.tableAliases != null) {
                this.tableAliases.add(queryTable.getAlias());
            }
            if (this.tableFields.size() == 1) {
                this.setSingleTableAlias(queryTable.getAlias());
            } else {
                this.setSingleTableAlias(null);
            }
        }
        if (fields.add(queryField)) {
            this.setFieldCount(this.getFieldCount() + 1);
        }
    }

    public final void addQueryFields(QueryFields queryFields) {
        boolean sameSingleTable;
        boolean bl = sameSingleTable = this.tableFields.size() == 0;
        if (!sameSingleTable) {
            String singleAlias = this.getSingleTableAlias();
            String queryAlias = queryFields.getSingleTableAlias();
            if (singleAlias == null && queryAlias == null || singleAlias != null && queryAlias != null && singleAlias.equals(queryAlias)) {
                sameSingleTable = true;
            }
        }
        for (QueryField field : queryFields) {
            this.addQueryField(field);
        }
        if (sameSingleTable) {
            this.setSingleTableAlias(queryFields.getSingleTableAlias());
        }
    }

    public void addLookupItem(LookupItem lookupItem) {
        this.lookupItems.add(lookupItem);
    }

    public List<LookupItem> getLookupItems() {
        return this.lookupItems;
    }

    public void setLookupItems(List<LookupItem> value) {
        this.lookupItems = value;
    }

    public final void combine(QueryRegion another) {
        for (QueryTable queryTable : another.tableFields.keySet()) {
            QueryFields queryFields = another.tableFields.get(queryTable);
            for (QueryField field : queryFields) {
                this.addQueryField(field);
            }
        }
        for (LookupItem lookupItem : another.getLookupItems()) {
            this.addLookupItem(lookupItem);
        }
        Iterator<String> it = another.mightWriteTables.iterator();
        while (it.hasNext()) {
            this.mightWriteTables.add(it.next());
        }
    }

    public final boolean isEmpty() {
        return this.tableFields.isEmpty();
    }

    public final String getSingleTableAlias() {
        return this.singleTableAlias;
    }

    public final void setSingleTableAlias(String value) {
        this.singleTableAlias = value;
    }

    public final boolean isTableWritable(String tableAlias) {
        return this.mightWriteTables.contains(tableAlias);
    }

    public final void setTableWritable(String tableAlias) {
        this.mightWriteTables.add(tableAlias);
    }

    public final QueryFields getTableFields(QueryTable queryTable) {
        QueryFields fields = this.tableFields.get(queryTable);
        return fields;
    }

    public Map<QueryTable, QueryFields> getAllTableFields() {
        return this.tableFields;
    }

    public int getAllFieldsCount() {
        int count = 0;
        if (this.tableFields != null) {
            for (QueryFields queryFields : this.tableFields.values()) {
                count += queryFields.getCount();
            }
        }
        return count;
    }

    public boolean justLeftJoin(QueryContext qContext) {
        int dataTableSize = 0;
        for (QueryTable table : this.tableFields.keySet()) {
            if (qContext.isRightJoinTable(table.getTableName())) {
                return false;
            }
            if (table.isDimensionTable() || table.getTableName().startsWith("NR_PERIOD")) continue;
            ++dataTableSize;
        }
        return dataTableSize == 1;
    }

    public MemoryDataSetReader runQuery(QueryContext qContext, IQuerySqlUpdater sqlUpdater) throws Exception {
        this.createDataReader(qContext);
        if (this.isEmpty()) {
            return this.dataReader;
        }
        this.setQueryVersion(qContext);
        this.sqlBuilder.setMemoryStartIndex(0);
        this.sqlBuilder.buildQuerySql(qContext, sqlUpdater);
        if (this.fullJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                builder.setMemoryStartIndex(this.dataReader.getColumnCount());
                builder.buildQuerySql(qContext, sqlUpdater);
            }
        }
        if (this.leftJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.leftJoinBuilders) {
                builder.setMemoryStartIndex(this.dataReader.getColumnCount());
                builder.buildQuerySql(qContext, sqlUpdater);
            }
        }
        this.dataReader.loadDatas(qContext, this.sqlBuilder, this.needOrderJoin, this.sqlBuilder.sumDatas());
        if (this.fullJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                this.dataReader.loadDatas(qContext, builder, this.needOrderJoin, this.floatSumLeft || builder.sumDatas());
            }
        }
        if (this.needOrderJoin) {
            this.dataReader.resetBizOrderValue();
        }
        return this.dataReader;
    }

    public MemorySteamLoader queryMemorySteamLoader(QueryContext qContext, IQuerySqlUpdater sqlUpdater) throws Exception {
        this.setQueryVersion(qContext);
        this.sqlBuilder.setMemoryStartIndex(0);
        this.sqlBuilder.buildQuerySql(qContext, sqlUpdater);
        return this.sqlBuilder.queryMemorySteamLoader(qContext);
    }

    private void setQueryVersion(QueryContext qContext) {
        DimensionValueSet masterKeys = qContext.getMasterKeys();
        if (masterKeys != null && masterKeys.hasValue("DATATIME")) {
            String periodValue = masterKeys.getValue("DATATIME").toString();
            try {
                PeriodWrapper periodWrapper = new PeriodWrapper(periodValue);
                Date[] dateRegion = qContext.getExeContext().getPeriodAdapter().getPeriodDateRegion(periodWrapper);
                if (dateRegion == null) {
                    return;
                }
                Date beginDate = dateRegion[0];
                Date endDate = dateRegion[1];
                this.sqlBuilder.setQueryVersionStartDate(beginDate);
                this.sqlBuilder.setQueryVersionDate(endDate);
                if (this.fullJoinBuilders != null) {
                    for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                        builder.setQueryVersionStartDate(beginDate);
                        builder.setQueryVersionDate(endDate);
                    }
                }
                if (this.leftJoinBuilders != null) {
                    for (QuerySqlBuilder builder : this.leftJoinBuilders) {
                        builder.setQueryVersionStartDate(beginDate);
                        builder.setQueryVersionDate(endDate);
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public void loadLeftJoinDatas(QueryContext qContext, boolean asFullJoin) throws Exception {
        if (this.leftJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.leftJoinBuilders) {
                if (!asFullJoin && qContext.isRightJoinTable(builder.getPrimaryTable().getTableName())) {
                    this.dataReader.loadLeftJoinDatas(qContext, builder, true);
                    continue;
                }
                this.dataReader.loadLeftJoinDatas(qContext, builder, asFullJoin);
            }
        }
    }

    public void createDataReader(QueryContext qContext) {
        this.dataReader = new MemoryDataSetReader(qContext);
        qContext.setDataReader(this.dataReader);
        this.dataReader.setLoopDimensions(this.loopDimensions);
    }

    /*
     * WARNING - void declaration
     */
    public void doInit(QueryContext context) throws ParseException {
        QueryTable primaryTable;
        boolean isCheck;
        this.loopDimensions = new DimensionSet(this.dimensions);
        ExecutorContext exeContext = context.getExeContext();
        this.sqlBuilder.setUseDNASql(exeContext.isUseDnaSql());
        this.sqlBuilder.doInit(context);
        boolean bl = isCheck = context.getRunnerType() == DataEngineConsts.DataEngineRunType.CHECK || context.getRunnerType() == DataEngineConsts.DataEngineRunType.BATCH_CHECK;
        if (this.getAllTableFields().size() > 1) {
            primaryTable = this.getSqlBuilder().getPrimaryTable();
            boolean destUnique = true;
            boolean hasUnUnique = false;
            for (QueryTable queryTable : this.getAllTableFields().keySet()) {
                if (!queryTable.getTableDimensions().contains("RECORDKEY")) continue;
                if (queryTable.equals(primaryTable)) {
                    destUnique = false;
                }
                hasUnUnique = true;
            }
            boolean needOrderJoin = !destUnique && !context.isQueryModule() && (context.getRunnerType() == DataEngineConsts.DataEngineRunType.CALCULATE || context.getRunnerType() == DataEngineConsts.DataEngineRunType.BATCH_CALCULATE);
            this.setFloatSumLeft(destUnique && hasUnUnique);
            this.setNeedOrderJoin(needOrderJoin);
        }
        if ((primaryTable = this.sqlBuilder.getPrimaryTable()) != null) {
            TableModelRunInfo tableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(primaryTable.getTableName());
            if (tableInfo != null && tableInfo.getOrderField() != null) {
                this.isFloat = true;
            }
            if (this.isFloat && context.isFormulaRun()) {
                if (!context.isEnableNrdb()) {
                    int floatTableCount = 0;
                    boolean simpleQuery = true;
                    for (QueryTable queryTable : this.getAllTableFields().keySet()) {
                        if (!queryTable.getIsSimple() || context.getTableLinkAliaMap().containsKey(primaryTable)) {
                            simpleQuery = false;
                            break;
                        }
                        TableModelRunInfo tableRunInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(queryTable.getTableName());
                        if (tableRunInfo.getBizOrderField() == null) continue;
                        ++floatTableCount;
                    }
                    this.floatStreamCalc = simpleQuery && floatTableCount == 1;
                }
                IDataModelLinkFinder dataModelLinkFinder = exeContext.getEnv().getDataModelLinkFinder();
                String region = primaryTable.getRegion();
                if (region != null && dataModelLinkFinder != null && dataModelLinkFinder.hasRegionCondition(exeContext, region)) {
                    void var8_19;
                    void var8_17;
                    Object var8_15 = null;
                    String condition = dataModelLinkFinder.getRegionCondition(exeContext, region);
                    try {
                        IExpression iExpression = exeContext.getCache().getFormulaParser(exeContext).parseCond(condition, (IContext)context);
                        for (IASTNode node : iExpression) {
                            if (!(node instanceof DynamicDataNode)) continue;
                            DynamicDataNode dataNode = (DynamicDataNode)node;
                            dataNode.setTableAlias("t0");
                            QueryFields queryFields = this.tableFields.get(primaryTable);
                            queryFields.add(dataNode.getQueryField());
                        }
                    }
                    catch (Exception e) {
                        context.getMonitor().exception(e);
                    }
                    if (var8_17 == null) {
                        Equal equal = new Equal(null, (IASTNode)new DataNode(null, 1), (IASTNode)new DataNode(null, 0));
                        context.getMonitor().exception((Exception)((Object)new ParseException("\u533a\u57df\u8fc7\u6ee4\u6761\u4ef6 " + condition + " \u89e3\u6790\u5931\u8d25\u6216\u8005\u4e0d\u652f\u6301SQL\u7ffb\u8bd1\uff0c\u8fc7\u6ee4\u6761\u4ef6\u63091=0\u5904\u7406")));
                    }
                    this.sqlBuilder.setRowFilterNode((IASTNode)var8_19);
                    this.sqlBuilder.setNeedMemoryFilter(true);
                }
            }
        }
        this.sqlBuilder.setMultiFloatCheck(this.isFloat && this.hasMultiFloat(context) && isCheck);
        this.initLoopdimensions(context);
        this.initJoinBuilders(context);
        this.sqlBuilder.resetOrderItems(context);
    }

    private void fixDimRestrictByDefault(QueryContext context, QuerySqlBuilder sqlBuilder) throws ParseException {
        QueryTable primaryTable = sqlBuilder.getPrimaryTable();
        if (context.isFormulaRun() && !this.isStatQuery && primaryTable != null && primaryTable.getDimensionRestriction() != null) {
            ExecutorContext exeContext = context.getExeContext();
            TableModelRunInfo tableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(primaryTable.getTableName());
            boolean hasDimRestriction = false;
            DimensionValueSet restriction = primaryTable.getDimensionRestriction();
            for (int i = 0; i < restriction.size(); ++i) {
                String dimName = restriction.getName(i);
                if (dimName.equals("DATATIME")) continue;
                hasDimRestriction = true;
                break;
            }
            if (hasDimRestriction) {
                DimensionSet tableDims = primaryTable.getTableDimensions();
                Object filterNode = sqlBuilder.getRowFilterNode();
                for (int i = 0; i < tableDims.size(); ++i) {
                    String dim = tableDims.get(i);
                    try {
                        if (context.getMasterKeys().hasValue(dim) || restriction.hasValue(dim) || context.is1v1RelationDim(dim, context.getTableLinkAliaMap().get(primaryTable))) continue;
                        ColumnModelDefine dimensionField = tableInfo.getDimensionField(dim);
                        FieldDefine dimFieldDefine = exeContext.getCache().getDataModelDefinitionsCache().getFieldDefine(dimensionField);
                        if (dimensionField == null || !StringUtils.isNotEmpty((CharSequence)dimFieldDefine.getDefaultValue())) continue;
                        String dimFilter = tableInfo.getTableModelDefine().getCode() + "[" + dimFieldDefine.getCode() + "]=" + dimFieldDefine.getDefaultValue();
                        IExpression dimFilterNode = context.getFormulaParser().parseCond(dimFilter, (IContext)context);
                        String tableAlias = context.getQueryTableAliaMap().get(primaryTable);
                        for (IASTNode node : dimFilterNode) {
                            if (!(node instanceof DynamicDataNode)) continue;
                            DynamicDataNode dataNode = (DynamicDataNode)node;
                            dataNode.setTableAlias(tableAlias);
                            QueryFields queryFields = sqlBuilder.getQueryRegion().getTableFields(primaryTable);
                            queryFields.add(dataNode.getQueryField());
                        }
                        filterNode = filterNode == null ? dimFilterNode : new And(null, filterNode, (IASTNode)dimFilterNode);
                        sqlBuilder.setRowFilterNode((IASTNode)filterNode);
                        sqlBuilder.getLoopDimensions().removeDimension(dim);
                        continue;
                    }
                    catch (Exception e) {
                        context.getMonitor().exception(e);
                    }
                }
            }
        }
    }

    private void initJoinBuilders(QueryContext context) throws ParseException {
        if (this.floatStreamCalc) {
            return;
        }
        boolean ignoreDataVersion = this.sqlBuilder.isIgnoreDataVersion();
        this.fullJoinBuilders = this.sqlBuilder.divideFullJoins(context);
        this.sqlBuilder.setLoopDimensions(context, this.loopDimensions);
        this.fixDimRestrictByDefault(context, this.sqlBuilder);
        if (this.fullJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                builder.setIgnoreDefaultOrderBy(true);
                builder.setIgnoreDataVersion(ignoreDataVersion);
                builder.setRowFilterNode(null);
                builder.doInit(context);
                builder.setLoopDimensions(context, this.loopDimensions);
                builder.setMultiFloatCheck(this.sqlBuilder.isMultiFloatCheck());
                this.fixDimRestrictByDefault(context, builder);
            }
        }
        this.leftJoinBuilders = this.sqlBuilder.divideLeftJoins(context);
        if (this.leftJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.leftJoinBuilders) {
                builder.setIgnoreDefaultOrderBy(true);
                builder.setIgnoreDataVersion(ignoreDataVersion);
                builder.setRowFilterNode(null);
                builder.doInit(context);
                builder.setLoopDimensions(context, this.loopDimensions);
                builder.setMultiFloatCheck(this.sqlBuilder.isMultiFloatCheck());
                this.fixDimRestrictByDefault(context, builder);
            }
            if (this.isFloat) {
                for (int i = this.leftJoinBuilders.size() - 1; i >= 0; --i) {
                    QuerySqlBuilder builder;
                    builder = this.leftJoinBuilders.get(i);
                    TableModelRunInfo tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(builder.getPrimaryTable().getTableName());
                    if (tableInfo.getOrderField() == null) continue;
                    if (this.fullJoinBuilders == null) {
                        this.fullJoinBuilders = new ArrayList<QuerySqlBuilder>();
                    }
                    this.fullJoinBuilders.add(builder);
                    this.leftJoinBuilders.remove(i);
                }
            }
        }
    }

    public void initLoopdimensions(QueryContext context) {
        for (QueryTable table : this.tableFields.keySet()) {
            DimensionValueSet dimensionRestriction = table.getDimensionRestriction();
            UpdateDatas updateDatas = context.getUpdateDatas();
            if (dimensionRestriction != null && !dimensionRestriction.isAllNull()) {
                for (int i = 0; i < dimensionRestriction.size(); ++i) {
                    if (dimensionRestriction.getValue(i) == null) continue;
                    this.loopDimensions.removeDimension(dimensionRestriction.getName(i));
                    if (updateDatas == null) continue;
                    updateDatas.setNeedNewRowKey(true);
                }
            }
            if (table.getPeriodModifier() == null) continue;
            this.loopDimensions.removeDimension("DATATIME");
            if (updateDatas == null) continue;
            updateDatas.setNeedNewRowKey(true);
        }
        if (this.floatSumLeft || this.sqlBuilder.sumDatas()) {
            this.loopDimensions.removeDimension("RECORDKEY");
        }
    }

    @Override
    public Iterator<Map.Entry<QueryTable, QueryFields>> iterator() {
        return this.tableFields.entrySet().iterator();
    }

    public QuerySqlBuilder getSqlBuilder() {
        return this.sqlBuilder;
    }

    public void setSqlBuilder(QuerySqlBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
        sqlBuilder.setQueryRegion(this);
    }

    public List<QuerySqlBuilder> getSubSqlBuilders() {
        if (this.leftJoinBuilders == null) {
            this.leftJoinBuilders = new ArrayList<QuerySqlBuilder>();
        }
        return this.leftJoinBuilders;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSimple() {
        boolean isSimple = true;
        for (QueryTable table : this.tableFields.keySet()) {
            if (table.getIsSimple()) continue;
            isSimple = false;
        }
        return isSimple;
    }

    private List<QueryTable> getAllQueryTables() {
        ArrayList<QueryTable> allQueryTables = new ArrayList<QueryTable>(this.tableFields.keySet());
        if (this.fullJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                allQueryTables.addAll(builder.getQueryRegion().getAllTableFields().keySet());
            }
        }
        if (this.leftJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.leftJoinBuilders) {
                allQueryTables.addAll(builder.getQueryRegion().getAllTableFields().keySet());
            }
        }
        return allQueryTables;
    }

    public List<QuerySqlBuilder> getAllFullJoinSqlBuilders() {
        ArrayList<QuerySqlBuilder> allBuilders = new ArrayList<QuerySqlBuilder>();
        allBuilders.add(this.sqlBuilder);
        if (this.fullJoinBuilders != null) {
            allBuilders.addAll(this.fullJoinBuilders);
        }
        return allBuilders;
    }

    public boolean isFloat() {
        return this.isFloat;
    }

    public DimensionSet getLoopDimensions() {
        return this.loopDimensions;
    }

    public boolean isNeedOrderJoin() {
        return this.needOrderJoin;
    }

    public void setNeedOrderJoin(boolean needOrderJoin) {
        this.needOrderJoin = needOrderJoin;
    }

    public boolean isFloatSumLeft() {
        return this.floatSumLeft;
    }

    public void setFloatSumLeft(boolean floatSumLeft) {
        this.floatSumLeft = floatSumLeft;
    }

    public boolean hasMultiFloat(QueryContext qContext) {
        if (this.tableFields.size() < 2) {
            return false;
        }
        try {
            DataModelDefinitionsCache dataDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
            int floatTableCount = 0;
            for (QueryTable table : this.tableFields.keySet()) {
                TableModelRunInfo tableInfo = dataDefinitionsCache.getTableInfo(table.getTableName());
                if (tableInfo.getOrderField() == null) continue;
                ++floatTableCount;
            }
            return floatTableCount > 1;
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public boolean isNeedMemoryFilter() {
        return this.sqlBuilder != null && this.sqlBuilder.isNeedMemoryFilter() && this.sqlBuilder.getRowFilterNode() != null;
    }

    public boolean judge(QueryContext qContext) {
        try {
            return this.sqlBuilder.getRowFilterNode().judge((IContext)qContext);
        }
        catch (SyntaxException e) {
            return false;
        }
    }

    public boolean isFloatStreamCalc() {
        return this.floatStreamCalc;
    }

    public boolean isStatQuery() {
        return this.isStatQuery;
    }

    public void setStatQuery(boolean isStatQuery) {
        this.isStatQuery = isStatQuery;
    }
}

