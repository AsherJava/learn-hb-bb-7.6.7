/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.data.engine.summary.exe.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.summary.define.DimRelationInfo;
import com.jiuqi.nr.data.engine.summary.define.IRelationInfoProvider;
import com.jiuqi.nr.data.engine.summary.define.RelationInfo;
import com.jiuqi.nr.data.engine.summary.exe.impl.SumQueryTable;
import com.jiuqi.nr.data.engine.summary.parse.SumNode;
import com.jiuqi.nr.data.engine.summary.parse.SumTable;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.xlib.utils.StringUtil;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SumDataSetBuilder {
    private static final Logger logger = LoggerFactory.getLogger(SumDataSetBuilder.class);
    protected String tableName;
    protected String mainTableName;
    protected String periodOffset;
    protected final Map<String, SumTable> nodeMap = new HashMap<String, SumTable>();
    protected String filter;
    protected IRelationInfoProvider relationInfoProvider;
    protected DimensionValueSet srcDimension;
    protected ExecutorContext executorContext;
    protected ISQLInfo dbInfo;
    protected Map<String, TempAssistantTable> tempAssistantTables;
    protected Connection connection;
    protected SumQueryTable sumQueryTable;
    protected Date queryVersionDate = null;

    public SumDataSetBuilder(ExecutorContext executorContext, ISQLInfo dbInfo, DimensionValueSet srcDimension, SumQueryTable sumQueryTable, IRelationInfoProvider relationInfoProvider, Connection connection) {
        this.tableName = sumQueryTable.getTableName();
        this.periodOffset = sumQueryTable.getPeriodOffSet();
        this.sumQueryTable = sumQueryTable;
        this.srcDimension = srcDimension;
        this.relationInfoProvider = relationInfoProvider;
        this.executorContext = executorContext;
        this.dbInfo = dbInfo;
        this.connection = connection;
        this.initMainTableName(executorContext, relationInfoProvider);
        this.initQueryVersionDate(executorContext, srcDimension, sumQueryTable.getPeriodOffSet());
    }

    public String buildDataSetSql() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        StringBuilder fromPart = new StringBuilder();
        fromPart.append(this.tableName).append(" ").append(this.tableName);
        for (String tableName : this.nodeMap.keySet()) {
            HashSet<String> colSet = new HashSet<String>();
            List<SumNode> nodeList = this.nodeMap.get(tableName).getNodes();
            for (SumNode node : nodeList) {
                this.appendNodeToColumn(sql, tableName, node, colSet);
            }
        }
        sql.setLength(sql.length() - 1);
        StringBuilder whereCondition = new StringBuilder();
        this.buildFromAndWhere(fromPart, whereCondition);
        sql.append(" from ").append((CharSequence)fromPart);
        if (whereCondition.length() > 0) {
            sql.append(" where ").append((CharSequence)whereCondition);
        }
        return sql.toString();
    }

    protected void appendNodeToColumn(StringBuilder sql, String nodeTableName, SumNode node, HashSet<String> colSet) {
        String zbAlias = node.getZBAlias();
        if (!colSet.contains(zbAlias)) {
            ColumnModelDefine columnModel = node.getColumnModel();
            sql.append(nodeTableName).append(".").append(columnModel.getName()).append(" as ").append(zbAlias).append(",");
            colSet.add(zbAlias);
        }
    }

    protected void buildFromAndWhere(StringBuilder fromPart, StringBuilder whereCondition) throws Exception {
        RelationInfo r;
        if (!this.tableName.equals(this.mainTableName) && (r = this.relationInfoProvider.findRelationInfo(this.executorContext, this.tableName, this.mainTableName)) != null) {
            this.appendTablesByRellationInfo(fromPart, r);
        }
        this.appendOtherTables(fromPart, this.tableName);
        this.appendConditions(whereCondition);
    }

    protected void appendConditions(StringBuilder whereCondition) throws ParseException {
        this.appendCondition(whereCondition);
        this.appendConditionByFilter(whereCondition);
    }

    protected boolean appendTablesByRellationInfo(StringBuilder sql, RelationInfo r) throws ParseException {
        sql.append(" left join ").append(r.getDestTableName()).append(" ").append(r.getDestTableName());
        sql.append(" on ");
        boolean needAnd = false;
        for (String srcFieldName : r.getRelationFieldMap().keySet()) {
            String destFieldName = r.getRelationFieldMap().get(srcFieldName);
            if (needAnd) {
                sql.append(" and ");
            }
            sql.append(r.getSrcTableName()).append(".").append(srcFieldName).append("=");
            sql.append(r.getDestTableName()).append(".").append(destFieldName);
            needAnd = true;
        }
        return needAnd;
    }

    protected void appendConditionByFilter(StringBuilder whereCondition) {
        if (StringUtils.isNotEmpty((String)this.filter)) {
            if (whereCondition.length() > 0) {
                whereCondition.append(" and ");
            }
            whereCondition.append(" (").append(this.filter).append(")");
        }
    }

    protected void appendCondition(StringBuilder whereCondition) throws ParseException {
        TableModelRunInfo tableInfo = this.executorContext.getCache().getDataModelDefinitionsCache().getTableInfo(this.mainTableName);
        boolean needAnd = false;
        for (int i = 0; i < this.srcDimension.size(); ++i) {
            String keyName = this.srcDimension.getName(i);
            Object keyValue = this.srcDimension.getValue(i);
            if (StringUtils.isNotEmpty((String)this.periodOffset) && keyName.equals("DATATIME") && keyValue instanceof String) {
                String period = (String)keyValue;
                PeriodModifier pm = PeriodModifier.parse((String)this.periodOffset);
                if (pm != null) {
                    keyValue = pm.modify(period);
                }
            }
            String dimensionFieldName = keyName;
            ColumnModelDefine dimensionField = tableInfo.getDimensionField(keyName);
            if (dimensionField == null) continue;
            dimensionFieldName = dimensionField.getName();
            int dimDataType = DataTypesConvert.fieldTypeToDataType((ColumnModelType)dimensionField.getColumnType());
            this.appendToCondition(whereCondition, this.mainTableName, dimensionFieldName, keyName, keyValue, dimDataType, needAnd, this.tempAssistantTables);
            if (needAnd) continue;
            needAnd = true;
        }
    }

    protected void appendToCondition(StringBuilder sql, String tableAlias, String name, String keyName, Object value, int dataType, boolean needAnd, Map<String, TempAssistantTable> tempAssistantTables) {
        if (needAnd) {
            sql.append(" and ");
        }
        if (value instanceof List) {
            List valueList = (List)value;
            if (valueList.size() <= 0) {
                sql.append(" 1=0 ");
            } else {
                String fieldName = tableAlias + "." + name;
                if (valueList.size() >= DataEngineUtil.getMaxInSize((IDatabase)this.dbInfo.getDatabase()) && tempAssistantTables != null) {
                    TempAssistantTable tempAssistantTable = tempAssistantTables.get(keyName);
                    if (tempAssistantTable != null) {
                        sql.append(" exists ").append(tempAssistantTable.getExistsSelectSql(fieldName));
                    }
                } else {
                    sql.append(fieldName);
                    sql.append(" in (");
                    for (Object currentValue : valueList) {
                        this.appendValue(sql, currentValue, dataType);
                        sql.append(",");
                    }
                    sql.setLength(sql.length() - 1);
                    sql.append(")");
                }
            }
        } else {
            sql.append(tableAlias).append(".").append(name);
            sql.append("=");
            this.appendValue(sql, value, dataType);
        }
    }

    private void appendValue(StringBuilder sql, Object value, int dataType) {
        if (dataType == 6) {
            sql.append("'").append(value).append("'");
        } else if (dataType == 33) {
            UUID uuid = Convert.toUUID((Object)value);
            sql.append(DataEngineUtil.buildGUIDValueSql((IDatabase)this.dbInfo.getDatabase(), (UUID)uuid));
        } else if (dataType == 5 || dataType == 2) {
            sql.append(DataEngineUtil.buildDateValueSql((IDatabase)this.dbInfo.getDatabase(), (Object)value, (Connection)this.connection));
        } else {
            sql.append(value);
        }
    }

    protected void appendOtherTables(StringBuilder fromPart, String tableName) throws Exception {
        for (String otherTable : this.nodeMap.keySet()) {
            DimRelationInfo dimRefR;
            if (otherTable.equals(tableName) || otherTable.equals(this.mainTableName)) continue;
            RelationInfo r = this.relationInfoProvider.findRelationInfo(this.executorContext, tableName, otherTable);
            if (r != null) {
                this.appendTablesByRellationInfo(fromPart, r);
                continue;
            }
            DimRelationInfo dimR = this.relationInfoProvider.findDimRelationInfo(this.executorContext, this.mainTableName);
            boolean needAnd = this.appendTablesByRellationInfo(fromPart, dimR);
            this.appendVersionFilter(dimR, fromPart, needAnd);
            if (otherTable.equals(dimR.getDestTableName()) || (dimRefR = dimR.getRelationInfo(otherTable)) == null) continue;
            boolean needAnd1 = this.appendTablesByRellationInfo(fromPart, dimRefR);
            this.appendVersionFilter(dimRefR, fromPart, needAnd1);
        }
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public Map<String, SumTable> getNodeMap() {
        return this.nodeMap;
    }

    public void setTempAssistantTables(Map<String, TempAssistantTable> tempAssistantTables) {
        this.tempAssistantTables = tempAssistantTables;
    }

    private void appendVersionFilter(DimRelationInfo dimR, StringBuilder condition, boolean needAnd) {
        if (!dimR.isSupportZipperVersion()) {
            return;
        }
        if (needAnd) {
            condition.append(" and ");
        }
        condition.append(this.getDateCompareSql(dimR.getDestTableName(), dimR.getStartDateFieldName(), "<=", this.queryVersionDate));
        condition.append(" and ");
        condition.append(this.getDateCompareSql(dimR.getDestTableName(), dimR.getEndDateFieldName(), ">", this.queryVersionDate));
    }

    private String getDateCompareSql(String alias, String fieldName, String compareOper, Date date) {
        StringBuilder sql = new StringBuilder();
        try {
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(this.connection);
            if (StringUtils.isNotEmpty((String)alias)) {
                sql.append(alias).append(".");
            }
            sql.append(fieldName);
            sql.append(compareOper);
            sql.append(database.createSQLInterpretor(this.connection).formatSQLDate(date));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return sql.toString();
    }

    private void initMainTableName(ExecutorContext executorContext, IRelationInfoProvider relationInfoProvider) {
        try {
            this.mainTableName = relationInfoProvider.findMainTableName(executorContext, this.tableName);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void initQueryVersionDate(ExecutorContext executorContext, DimensionValueSet srcDimension, String periodOffset) {
        try {
            Object periodValue = srcDimension.getValue("DATATIME");
            String period = null;
            if (periodValue != null) {
                Date[] region;
                if (periodValue instanceof List) {
                    List periods = (List)periodValue;
                    period = periods.get(0).toString();
                } else {
                    period = periodValue.toString();
                }
                PeriodWrapper periodWrapper = new PeriodWrapper(period);
                if (StringUtil.isNotEmpty((String)periodOffset)) {
                    PeriodModifier pm = PeriodModifier.parse((String)periodOffset);
                    pm.modify(periodWrapper);
                }
                if ((region = executorContext.getPeriodAdapter().getPeriodDateRegion(periodWrapper)).length == 2) {
                    this.queryVersionDate = region[1];
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private ColumnModelDefine getColumnModel(FieldDefine fieldDefine) {
        try {
            ColumnModelDefine column = this.executorContext.getCache().getDataModelDefinitionsCache().getColumnModel(fieldDefine);
            return column;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

