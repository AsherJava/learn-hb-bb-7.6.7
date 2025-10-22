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
 *  com.jiuqi.np.dataengine.definitions.DataDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableRunInfo
 *  com.jiuqi.np.dataengine.reader.IQueryFieldDataReader
 */
package com.jiuqi.nr.data.engine.analysis.exe.query;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.definitions.DataDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableRunInfo;
import com.jiuqi.np.dataengine.reader.IQueryFieldDataReader;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisMemoryDataSetReader;
import com.jiuqi.nr.data.engine.analysis.exe.query.AnalysisQuerySqlBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisQueryRegion
implements Iterable<Map.Entry<QueryTable, QueryFields>> {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisQueryRegion.class);
    private Map<QueryTable, QueryFields> tableFields;
    private AnalysisQuerySqlBuilder sqlBuilder;
    private List<AnalysisQuerySqlBuilder> leftJoinBuilders = null;
    private List<AnalysisQuerySqlBuilder> fullJoinBuilders = null;
    private AnalysisMemoryDataSetReader dataReader = null;
    private DimensionSet loopDimensions;
    private boolean isFloat = false;
    private DimensionSet dimensions;
    private int fieldCount;
    private ENameSet tableAliases = null;
    private String singleTableAlias;

    public AnalysisQueryRegion(DimensionSet dimensions, AnalysisQuerySqlBuilder sqlBuilder) {
        this.setDimensions(dimensions);
        this.sqlBuilder = sqlBuilder;
        if (sqlBuilder != null) {
            sqlBuilder.setQueryRegion(this);
        }
        this.tableFields = new HashMap<QueryTable, QueryFields>();
    }

    public AnalysisQueryRegion(DimensionSet dimensions) {
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
        if (queryFields == null) {
            return;
        }
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

    public final void combine(AnalysisQueryRegion another) {
        for (QueryTable queryTable : another.tableFields.keySet()) {
            QueryFields queryFields = another.tableFields.get(queryTable);
            for (QueryField field : queryFields) {
                this.addQueryField(field);
            }
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

    public final QueryFields getTableFields(QueryTable queryTable) {
        QueryFields fields = this.tableFields.get(queryTable);
        return fields;
    }

    public Map<QueryTable, QueryFields> getAllTableFields() {
        return this.tableFields;
    }

    public AnalysisMemoryDataSetReader runQuery(AnalysisContext qContext, Set<DimensionValueSet> statMasterKeySet) throws Exception {
        this.createDataReader(qContext);
        if (this.isEmpty()) {
            return this.dataReader;
        }
        this.sqlBuilder.buildQuerySql(qContext, null);
        if (this.fullJoinBuilders != null) {
            for (AnalysisQuerySqlBuilder builder : this.fullJoinBuilders) {
                builder.setMemoryStartIndex(this.dataReader.getColumnCount());
                builder.buildQuerySql(qContext, null);
            }
        }
        if (this.leftJoinBuilders != null) {
            for (AnalysisQuerySqlBuilder builder : this.leftJoinBuilders) {
                builder.setMemoryStartIndex(this.dataReader.getColumnCount());
                builder.buildQuerySql(qContext, null);
            }
        }
        this.dataReader.expandByDims(qContext, statMasterKeySet);
        this.dataReader.loadDatas(qContext, this.sqlBuilder);
        if (this.fullJoinBuilders != null) {
            for (AnalysisQuerySqlBuilder builder : this.fullJoinBuilders) {
                this.dataReader.loadDatas(qContext, builder);
            }
        }
        return this.dataReader;
    }

    public void loadLeftJoinDatas(AnalysisContext qContext, boolean asFullJoin) throws Exception {
        if (this.leftJoinBuilders != null) {
            for (AnalysisQuerySqlBuilder builder : this.leftJoinBuilders) {
                this.dataReader.loadLeftJoinDatas(qContext, builder, asFullJoin);
            }
        }
    }

    public void createDataReader(AnalysisContext qContext) {
        this.dataReader = new AnalysisMemoryDataSetReader(qContext);
        qContext.setDataReader((IQueryFieldDataReader)this.dataReader);
    }

    public void doInit(AnalysisContext context) throws ParseException {
        TableRunInfo tableInfo;
        this.loopDimensions = new DimensionSet(this.dimensions);
        this.sqlBuilder.doInit(context);
        if (this.sqlBuilder.getPrimaryTable() != null && (tableInfo = context.getExeContext().getCache().getDataDefinitionsCache().getTableInfo(this.sqlBuilder.getPrimaryTable().getTableName())) != null && tableInfo.getOrderField() != null) {
            this.isFloat = true;
        }
        this.initLoopdimensions(context);
        this.initJoinBuilders(context);
    }

    private void initJoinBuilders(AnalysisContext context) throws ParseException {
        boolean ignoreDataVersion = this.sqlBuilder.isIgnoreDataVersion();
        this.fullJoinBuilders = this.sqlBuilder.divideFullJoins(context);
        this.sqlBuilder.setLoopDimensions(context, this.loopDimensions);
        if (this.fullJoinBuilders != null) {
            for (AnalysisQuerySqlBuilder builder : this.fullJoinBuilders) {
                builder.setIgnoreDataVersion(ignoreDataVersion);
                builder.doInit(context);
                builder.setLoopDimensions(context, this.loopDimensions);
            }
        }
        this.leftJoinBuilders = this.sqlBuilder.divideLeftJoins(context);
        if (this.leftJoinBuilders != null) {
            for (AnalysisQuerySqlBuilder builder : this.leftJoinBuilders) {
                builder.setIgnoreDataVersion(ignoreDataVersion);
                builder.doInit(context);
                builder.setLoopDimensions(context, this.loopDimensions);
            }
            if (this.isFloat) {
                for (int i = this.leftJoinBuilders.size() - 1; i >= 0; --i) {
                    AnalysisQuerySqlBuilder builder;
                    builder = this.leftJoinBuilders.get(i);
                    TableRunInfo tableInfo = context.getExeContext().getCache().getDataDefinitionsCache().getTableInfo(builder.getPrimaryTable().getTableName());
                    if (tableInfo.getOrderField() == null) continue;
                    if (this.fullJoinBuilders == null) {
                        this.fullJoinBuilders = new ArrayList<AnalysisQuerySqlBuilder>();
                    }
                    this.fullJoinBuilders.add(builder);
                    this.leftJoinBuilders.remove(i);
                }
            }
        }
    }

    public void initLoopdimensions(AnalysisContext context) {
        for (QueryTable table : this.tableFields.keySet()) {
            DimensionValueSet dimensionRestriction = table.getDimensionRestriction();
            if (dimensionRestriction != null && !dimensionRestriction.isAllNull()) {
                for (int i = 0; i < dimensionRestriction.size(); ++i) {
                    if (dimensionRestriction.getValue(i) == null) continue;
                    this.loopDimensions.removeDimension(dimensionRestriction.getName(i));
                }
            }
            if (table.getPeriodModifier() == null) continue;
            this.loopDimensions.removeDimension("DATATIME");
        }
    }

    @Override
    public Iterator<Map.Entry<QueryTable, QueryFields>> iterator() {
        return this.tableFields.entrySet().iterator();
    }

    public AnalysisQuerySqlBuilder getSqlBuilder() {
        return this.sqlBuilder;
    }

    public void setSqlBuilder(AnalysisQuerySqlBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
        sqlBuilder.setQueryRegion(this);
    }

    public List<AnalysisQuerySqlBuilder> getSubSqlBuilders() {
        if (this.leftJoinBuilders == null) {
            this.leftJoinBuilders = new ArrayList<AnalysisQuerySqlBuilder>();
        }
        return this.leftJoinBuilders;
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
            for (AnalysisQuerySqlBuilder builder : this.fullJoinBuilders) {
                allQueryTables.addAll(builder.getQueryRegion().getAllTableFields().keySet());
            }
        }
        if (this.leftJoinBuilders != null) {
            for (AnalysisQuerySqlBuilder builder : this.leftJoinBuilders) {
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

    public boolean hasMultiFloat(AnalysisContext qContext) {
        if (this.tableFields.size() < 2) {
            return false;
        }
        try {
            DataDefinitionsCache dataDefinitionsCache = qContext.getExeContext().getCache().getDataDefinitionsCache();
            int floatTableCount = 0;
            for (QueryTable table : this.tableFields.keySet()) {
                TableRunInfo tableInfo = dataDefinitionsCache.getTableInfo(table.getTableName());
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

