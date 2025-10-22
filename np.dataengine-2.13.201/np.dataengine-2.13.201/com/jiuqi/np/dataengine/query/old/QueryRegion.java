/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.np.dataengine.query.old;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.common.LookupItem;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.intf.IQuerySqlUpdater;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.UpdateDatas;
import com.jiuqi.np.dataengine.query.old.MemoryDataSetReader;
import com.jiuqi.np.dataengine.query.old.QuerySqlBuilder;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QueryRegion
implements Iterable<Map.Entry<QueryTable, QueryFields>> {
    public static final int TYPE_SQL_JOIN = 0;
    public static final int TYPE_MEMORY_JOIN = 1;
    private final Map<QueryTable, QueryFields> tableFields;
    private List<LookupItem> lookupItems;
    private final Set<String> mightWriteTables;
    protected QuerySqlBuilder sqlBuilder;
    private List<QuerySqlBuilder> leftJoinBuilders = null;
    private List<QuerySqlBuilder> fullJoinBuilders = null;
    private MemoryDataSetReader dataReader = null;
    private DimensionSet loopDimensions;
    private boolean needOrderJoin;
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

    public MemoryDataSetReader runQuery(QueryContext qContext, IQuerySqlUpdater sqlUpdater) throws Exception {
        this.createDataReader(qContext);
        if (this.isEmpty()) {
            return this.dataReader;
        }
        this.setQueryVersion(qContext);
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
        this.dataReader.loadDatas(qContext, this.sqlBuilder, this.needOrderJoin, false);
        if (this.fullJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                this.dataReader.loadDatas(qContext, builder, this.needOrderJoin, this.floatSumLeft);
            }
        }
        if (this.needOrderJoin) {
            this.dataReader.resetBizOrderValue();
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
        qContext.setDataReader(this.dataReader);
        this.dataReader.setLoopDimensions(this.loopDimensions);
    }

    public void doInit(QueryContext context) throws ParseException {
        TableModelRunInfo tableInfo;
        this.loopDimensions = new DimensionSet(this.dimensions);
        this.sqlBuilder.setUseDNASql(context.getExeContext().isUseDnaSql());
        this.sqlBuilder.doInit(context);
        if (this.getAllTableFields().size() > 1) {
            QueryTable primaryTable = this.getSqlBuilder().getPrimaryTable();
            boolean destUnique = true;
            boolean hasUnUnique = false;
            for (QueryTable queryTable : this.getAllTableFields().keySet()) {
                if (!queryTable.getTableDimensions().contains("RECORDKEY")) continue;
                if (queryTable.equals(primaryTable)) {
                    destUnique = false;
                }
                hasUnUnique = true;
            }
            boolean needOrderJoin = !destUnique;
            this.setFloatSumLeft(destUnique && hasUnUnique);
            this.setNeedOrderJoin(needOrderJoin);
        }
        if (this.sqlBuilder.getPrimaryTable() != null && (tableInfo = context.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(this.sqlBuilder.getPrimaryTable().getTableName())) != null && tableInfo.getOrderField() != null) {
            this.isFloat = true;
        }
        this.initLoopdimensions(context);
        this.initJoinBuilders(context);
        this.sqlBuilder.resetOrderItems(context);
    }

    private void initJoinBuilders(QueryContext context) throws ParseException {
        boolean ignoreDataVersion = this.sqlBuilder.isIgnoreDataVersion();
        this.fullJoinBuilders = this.sqlBuilder.divideFullJoins(context);
        this.sqlBuilder.setLoopDimensions(context, this.loopDimensions);
        if (this.fullJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.fullJoinBuilders) {
                builder.setIgnoreDataVersion(ignoreDataVersion);
                builder.doInit(context);
                builder.setLoopDimensions(context, this.loopDimensions);
            }
        }
        this.leftJoinBuilders = this.sqlBuilder.divideLeftJoins(context);
        if (this.leftJoinBuilders != null) {
            for (QuerySqlBuilder builder : this.leftJoinBuilders) {
                builder.setIgnoreDataVersion(ignoreDataVersion);
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
            e.printStackTrace();
            return false;
        }
    }
}

