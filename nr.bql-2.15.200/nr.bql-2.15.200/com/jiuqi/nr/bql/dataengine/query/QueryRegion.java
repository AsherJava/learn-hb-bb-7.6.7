/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryFields
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.nr.bql.dataengine.query;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bql.dataengine.query.QuerySqlBuilder;
import com.jiuqi.nr.bql.dataengine.reader.MemoryDataSetReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryRegion
implements Iterable<Map.Entry<QueryTable, QueryFields>> {
    private static final Logger logger = LoggerFactory.getLogger(QueryRegion.class);
    public static final int TYPE_SQL_JOIN = 0;
    public static final int TYPE_MEMORY_JOIN = 1;
    private final Map<QueryTable, QueryFields> tableFields;
    private final Set<String> mightWriteTables;
    protected QuerySqlBuilder sqlBuilder;
    private List<QuerySqlBuilder> leftJoinBuilders = null;
    private List<QuerySqlBuilder> fullJoinBuilders = null;
    private MemoryDataSetReader dataReader = null;
    private DimensionSet loopDimensions;
    private int type = 0;
    private boolean isFloat = false;
    private boolean floatSumLeft = false;
    private DimensionSet dimensions;
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
        if (this.tableFields.size() > 0) {
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
        return " {loopDimensions=" + this.loopDimensions + ", isFloat=" + this.isFloat + ", floatSumLeft=" + this.floatSumLeft + ", fieldCount=" + this.fieldCount + "}";
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

    public final void combine(QueryRegion another) {
        for (QueryTable queryTable : another.tableFields.keySet()) {
            QueryFields queryFields = another.tableFields.get(queryTable);
            for (QueryField field : queryFields) {
                this.addQueryField(field);
            }
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

    public boolean justLeftJoin(QueryContext qContext) {
        int dataTableSize = 0;
        for (QueryTable table : this.tableFields.keySet()) {
            if (qContext.isRightJoinTable(table.getTableName())) {
                return false;
            }
            if (table.isDimensionTable() || table.getTableName().startsWith("NR_PERIOD") || this.sqlBuilder.isMdInfoTable(qContext, table.getTableName())) continue;
            ++dataTableSize;
        }
        return dataTableSize <= 1;
    }

    public MemoryDataSetReader runQuery(QueryContext qContext) throws Exception {
        this.createDataReader(qContext);
        if (this.isEmpty()) {
            return this.dataReader;
        }
        this.setQueryVersion(qContext);
        this.sqlBuilder.buildQuerySql(qContext);
        if (this.fullJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                builder.setMemoryStartIndex(this.dataReader.getColumnCount());
                builder.buildQuerySql(qContext);
            }
        }
        if (this.leftJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.leftJoinBuilders) {
                builder.setMemoryStartIndex(this.dataReader.getColumnCount());
                builder.buildQuerySql(qContext);
            }
        }
        this.dataReader.loadDatas(qContext, this.sqlBuilder);
        if (this.fullJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                this.dataReader.loadDatas(qContext, builder);
            }
        }
        return this.dataReader;
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
                this.dataReader.loadLeftJoinDatas(qContext, builder, asFullJoin);
            }
        }
    }

    public void createDataReader(QueryContext qContext) {
        this.dataReader = new MemoryDataSetReader(qContext);
        qContext.setDataReader((IQueryFieldDataReader)this.dataReader);
    }

    public void doInit(QueryContext context) throws ParseException {
        TableModelRunInfo tableInfo;
        this.loopDimensions = new DimensionSet(this.dimensions);
        this.sqlBuilder.doInit(context);
        if (this.getAllTableFields().size() > 1) {
            QueryTable primaryTable = this.getSqlBuilder().getPrimaryTable();
            boolean destUnique = true;
            boolean hasUnUnique = false;
            for (QueryTable queryTable : this.getAllTableFields().keySet()) {
                if (!queryTable.getTableDimensions().contains("RECORDKEY")) continue;
                if (queryTable.equals((Object)primaryTable)) {
                    destUnique = false;
                }
                hasUnUnique = true;
            }
            this.setFloatSumLeft(destUnique && hasUnUnique);
        }
        if (this.sqlBuilder.getPrimaryTable() != null && (tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.sqlBuilder.getPrimaryTable().getTableName())) != null && tableInfo.getOrderField() != null) {
            this.isFloat = true;
        }
        this.initLoopdimensions(context);
        this.initJoinBuilders(context);
        this.sqlBuilder.resetOrderItems(context);
    }

    private void initJoinBuilders(QueryContext context) throws ParseException {
        this.fullJoinBuilders = this.sqlBuilder.divideFullJoins(context);
        this.sqlBuilder.setLoopDimensions(context, this.loopDimensions);
        if (this.fullJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                builder.doInit(context);
                builder.setLoopDimensions(context, this.loopDimensions);
            }
        }
        this.leftJoinBuilders = this.sqlBuilder.divideLeftJoins(context);
        if (this.leftJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.leftJoinBuilders) {
                builder.doInit(context);
                builder.setLoopDimensions(context, this.loopDimensions);
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
            if (dimensionRestriction != null && !dimensionRestriction.isAllNull()) {
                for (int i = 0; i < dimensionRestriction.size(); ++i) {
                    if (dimensionRestriction.getValue(i) == null) continue;
                    this.loopDimensions.removeDimension(dimensionRestriction.getName(i));
                    context.getUpdateDatas().setNeedNewRowKey(true);
                }
            }
            if (table.getPeriodModifier() == null) continue;
            this.loopDimensions.removeDimension("DATATIME");
            context.getUpdateDatas().setNeedNewRowKey(true);
        }
        if (this.floatSumLeft) {
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

    public boolean isFloat() {
        return this.isFloat;
    }

    public DimensionSet getLoopDimensions() {
        return this.loopDimensions;
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
}

