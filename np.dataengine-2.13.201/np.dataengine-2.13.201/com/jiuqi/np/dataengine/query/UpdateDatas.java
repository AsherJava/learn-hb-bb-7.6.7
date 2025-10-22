/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.memdb.api.DBCursor
 *  com.jiuqi.nvwa.memdb.api.DBRecord
 *  com.jiuqi.nvwa.memdb.api.DBTable
 *  com.jiuqi.nvwa.memdb.api.DBTransaction
 *  com.jiuqi.nvwa.memdb.api.query.DBAggregation
 *  com.jiuqi.nvwa.memdb.api.query.DBQueryBuilder
 *  com.jiuqi.nvwa.memdb.api.record.ArrayRecord
 *  com.jiuqi.nvwa.nrdb.NrdbStorageManager
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.collector.FmlExecuteCollector;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DataValidateException;
import com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser;
import com.jiuqi.np.dataengine.intf.IFieldValueUpdateProcessor;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.ITableConditionProvider;
import com.jiuqi.np.dataengine.log.LogRow;
import com.jiuqi.np.dataengine.log.LogType;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.update.UpdateDataColumn;
import com.jiuqi.np.dataengine.update.UpdateDataRecord;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.np.dataengine.update.UpdateDataTable;
import com.jiuqi.np.dataengine.util.FieldSqlConditionUtil;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.memdb.api.DBCursor;
import com.jiuqi.nvwa.memdb.api.DBRecord;
import com.jiuqi.nvwa.memdb.api.DBTable;
import com.jiuqi.nvwa.memdb.api.DBTransaction;
import com.jiuqi.nvwa.memdb.api.query.DBAggregation;
import com.jiuqi.nvwa.memdb.api.query.DBQueryBuilder;
import com.jiuqi.nvwa.memdb.api.record.ArrayRecord;
import com.jiuqi.nvwa.nrdb.NrdbStorageManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateDatas {
    private static final Logger logger = LoggerFactory.getLogger(UpdateDatas.class);
    private UpdateDataSet updateDataSet = null;
    private boolean needNewRowKey;
    private HashMap<String, IExpression> dimDefaultValueExps;
    private boolean sqlSoftParse = true;

    public void addValue(QueryContext qContext, QueryField field, Object value, Object oldValue) {
        UpdateDataRecord record;
        DimensionValueSet rowKey;
        String physicalTableName = qContext.getPhysicalTableName(field.getTable());
        UpdateDataTable table = this.getUpdateDataSet().getTable(physicalTableName);
        if (table.getTableModelCode() == null) {
            try {
                TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(physicalTableName);
                table.setTableInfo(tableInfo);
            }
            catch (ParseException e) {
                qContext.getMonitor().exception((Exception)((Object)e));
            }
        }
        DimensionValueSet newRowkey = rowKey = qContext.getRowKey();
        if (this.needNewRowKey) {
            newRowkey = new DimensionValueSet(rowKey);
            DimensionValueSet currentMasterKey = qContext.getCurrentMasterKey();
            for (int i = 0; i < currentMasterKey.size(); ++i) {
                if (newRowkey.getValue(currentMasterKey.getName(i)) != null) continue;
                newRowkey.setValue(currentMasterKey.getName(i), currentMasterKey.getValue(i));
            }
            PeriodModifier modifier = field.getTable().getPeriodModifier();
            if (modifier != null) {
                String period = (String)rowKey.getValue("DATATIME");
                if (period == null) {
                    period = (String)qContext.getMasterKeys().getValue("DATATIME");
                }
                newRowkey.setValue("DATATIME", qContext.getExeContext().getPeriodAdapter().modify(period, modifier));
            }
            if (field.getTable().getDimensionRestriction() != null) {
                DimensionValueSet restriction = field.getTable().getDimensionRestriction();
                for (int i = 0; i < restriction.size(); ++i) {
                    if (restriction.getValue(i) == null) continue;
                    newRowkey.setValue(restriction.getName(i), restriction.getValue(i));
                }
            }
        }
        if ((record = table.getUpdateRecords().get(newRowkey)) == null) {
            record = new UpdateDataRecord();
            record.setRowkeys(newRowkey);
            table.getUpdateRecords().put(newRowkey, record);
        }
        record.addData(field.getFieldName(), field.getDataType(), field.getFractionDigits(), value, oldValue);
    }

    public void reset() {
        this.updateDataSet = null;
    }

    public int getUpdateRecordSize() {
        if (this.updateDataSet == null || this.updateDataSet.getTables().size() != 1) {
            return 0;
        }
        return this.updateDataSet.getTables().values().stream().findFirst().get().getUpdateRecords().size();
    }

    public void commitData(QueryContext qContext, IMonitor monitor, QueryParam queryParam) throws ParseException, SQLException {
        if (this.updateDataSet == null) {
            return;
        }
        this.sqlSoftParse = !qContext.isDebug() && !qContext.outFMLPlan();
        Connection connection = queryParam.getConnection();
        AbstractMonitor abstractMonitor = (AbstractMonitor)monitor;
        Map<String, UpdateDataTable> tables = this.getUpdateDataSet().getTables();
        ArrayList<UpdateDataTable> tableList = new ArrayList<UpdateDataTable>(tables.values());
        Collections.sort(tableList);
        DataModelDefinitionsCache dataDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
        for (UpdateDataTable table : tableList) {
            TableModelRunInfo tableInfo = table.getTableInfo();
            if (tableInfo.getTableModelDefine().getType() == TableModelType.ENUM) {
                dataDefinitionsCache.getDimensionProvider().writeDimensionTable(qContext.getExeContext(), table, qContext.getPeriodWrapper());
                continue;
            }
            double maxFloatOrder = 0.0;
            boolean updateByNrdb = qContext.isEnableNrdb() && tableInfo.getTableModelDefine().isSupportNrdb();
            maxFloatOrder = this.commitTableData(qContext, monitor, connection, abstractMonitor, tables, table, tableInfo, maxFloatOrder, updateByNrdb);
        }
        if (!tables.isEmpty()) {
            monitor.onDataChange(this.getUpdateDataSet());
        }
    }

    private double commitTableData(QueryContext qContext, IMonitor monitor, Connection connection, AbstractMonitor abstractMonitor, Map<String, UpdateDataTable> tables, UpdateDataTable table, TableModelRunInfo tableInfo, double maxFloatOrder, boolean updateByNrdb) {
        Iterator<Map.Entry<DimensionValueSet, UpdateDataRecord>> recordsIterator = table.getUpdateRecords().entrySet().iterator();
        FmlExecuteCollector collector = abstractMonitor.getCollector();
        while (recordsIterator.hasNext()) {
            Map.Entry<DimensionValueSet, UpdateDataRecord> recordEntry = recordsIterator.next();
            UpdateDataRecord record = recordEntry.getValue();
            try {
                if (!this.checkRowKey(qContext, tableInfo, record.getRowkeys())) {
                    recordsIterator.remove();
                    continue;
                }
                if (!updateByNrdb) {
                    int result = this.updateByJdbcSql(qContext, table.getTableName(), tableInfo, record, connection, collector);
                    if (result <= 0 && !qContext.isZbCalcMode()) {
                        recordsIterator.remove();
                        maxFloatOrder = this.insertByJdbcSql(qContext, table.getTableName(), tableInfo, record, connection, maxFloatOrder, collector);
                        table.getInsertRecords().add(record);
                    }
                } else {
                    boolean succ = this.updateByNrdb(qContext, table.getTableName(), tableInfo, record, connection, collector);
                    if (!succ && !qContext.isZbCalcMode()) {
                        recordsIterator.remove();
                        maxFloatOrder = this.insertByNrdb(qContext, table.getTableName(), tableInfo, record, connection, maxFloatOrder, collector);
                        table.getInsertRecords().add(record);
                    }
                }
                if (record.getUpdateColumns().isEmpty()) {
                    recordsIterator.remove();
                    continue;
                }
                abstractMonitor.addUpdateRecordCount(1);
            }
            catch (Exception e) {
                monitor.exception(e);
            }
        }
        if (table.getUpdateRecords().isEmpty() && table.getInsertRecords().isEmpty()) {
            tables.remove(table.getTableName());
        }
        return maxFloatOrder;
    }

    private boolean checkRowKey(QueryContext qContext, TableModelRunInfo tableInfo, DimensionValueSet rowKey) {
        DimensionSet tableDimensions = tableInfo.getDimensions();
        for (int i = 0; i < tableDimensions.size(); ++i) {
            String dimesionName = tableDimensions.get(i);
            if (rowKey.hasValue(dimesionName)) continue;
            Object defaultValue = this.getDimensionDefaultValue(qContext, tableInfo, dimesionName);
            if (defaultValue != null) {
                rowKey.setValue(dimesionName, defaultValue);
                continue;
            }
            if (qContext.getDimensionRelationProvider().is1v1RelationDim(qContext.getExeContext(), dimesionName, null)) {
                String mainDimValue = (String)rowKey.getValue(qContext.getExeContext().getUnitDimension());
                String peiod = (String)rowKey.getValue("DATATIME");
                List<String> relationValuesByDim = qContext.getDimensionRelationProvider().getRelationValuesByDim(qContext.getExeContext(), dimesionName, mainDimValue, peiod, null);
                if (relationValuesByDim.size() != 1) continue;
                rowKey.setValue(dimesionName, relationValuesByDim.get(0));
                continue;
            }
            StringBuilder msg = new StringBuilder();
            msg.append("\u6267\u884c\u66f4\u65b0\u5931\u8d25\uff0c\u884c\u7ef4\u5ea6\u503c\u7f3a\u5931:rowKey=").append(rowKey).append(",\u7f3a\u5931\u4e86").append(dimesionName);
            qContext.getMonitor().exception(new DataValidateException(msg.toString()));
            return false;
        }
        return true;
    }

    /*
     * WARNING - void declaration
     */
    private double insertByJdbcSql(QueryContext qContext, String physicalTableName, TableModelRunInfo tableInfo, UpdateDataRecord record, Connection connection, double floatOrder, FmlExecuteCollector collector) throws Exception {
        Map<String, String> argMap = this.getConditionArgMap(qContext, physicalTableName);
        ColumnModelDefine bizkeyOrderField = tableInfo.getBizOrderField();
        StringBuilder insertSql = new StringBuilder();
        StringBuilder values = new StringBuilder();
        HashSet<String> columnSet = new HashSet<String>();
        ArrayList<Object> argList = this.sqlSoftParse ? new ArrayList<Object>() : null;
        insertSql.append(" insert into ").append(physicalTableName);
        insertSql.append(" (");
        DimensionValueSet rowKey = record.getRowkeys();
        for (int i = 0; i < rowKey.size(); ++i) {
            String dimension = rowKey.getName(i);
            if (!tableInfo.getDimensions().contains(dimension)) continue;
            Object dimValue = rowKey.getValue(i);
            String dimensionFieldName = dimension;
            ColumnModelDefine columnModelDefine = tableInfo.getDimensionField(dimension);
            if (columnModelDefine != null) {
                dimensionFieldName = columnModelDefine.getName();
            }
            columnSet.add(dimensionFieldName);
            if (bizkeyOrderField != null && dimensionFieldName.equals(bizkeyOrderField.getName())) {
                dimValue = UUID.randomUUID().toString();
            }
            if (columnModelDefine.getColumnType() == ColumnModelType.UUID) {
                dimValue = Convert.toUUID(dimValue);
            } else if (columnModelDefine.getColumnType() == ColumnModelType.DATETIME && dimValue instanceof String) {
                dimValue = DataTypesConvert.periodToDate(new PeriodWrapper((String)dimValue));
            }
            insertSql.append(dimensionFieldName).append(",");
            FieldSqlConditionUtil.appendValue(qContext.getQueryParam().getDatabase(), connection, values, columnModelDefine.getColumnType().getValue(), dimValue, argList);
            values.append(",");
        }
        IEncryptDecryptProcesser encryptDecryptProcesser = qContext.getQueryParam().getEncryptDecryptProcesser();
        IFieldValueUpdateProcessor fieldValueUpdateProcessor = qContext.getExeContext().getEnv().getFieldValueUpdateProcessor();
        for (UpdateDataColumn column : record.getUpdateColumns()) {
            void var20_25;
            if (columnSet.contains(column.getName())) continue;
            Object object = this.getColumnValue(qContext, tableInfo, encryptDecryptProcesser, fieldValueUpdateProcessor, column);
            if (column.getType() == 1 && object instanceof Boolean) {
                boolean booleanValue = (Boolean)object;
                Integer n = booleanValue ? 1 : 0;
            }
            columnSet.add(column.getName());
            insertSql.append(column.getName()).append(",");
            FieldSqlConditionUtil.appendValue(qContext.getQueryParam().getDatabase(), connection, values, column.getType(), var20_25, argList);
            values.append(",");
        }
        if (bizkeyOrderField != null && !tableInfo.getDimensions().contains("RECORDKEY")) {
            String bizkeyOrderFieldName = bizkeyOrderField.getName();
            String bizkeyOrderValue = UUID.randomUUID().toString();
            record.addData(bizkeyOrderFieldName, bizkeyOrderField.getColumnType().getValue(), tableInfo.getOrderField().getDecimal(), bizkeyOrderValue, null);
            insertSql.append(bizkeyOrderFieldName).append(",");
            FieldSqlConditionUtil.appendValue(qContext.getQueryParam().getDatabase(), connection, values, 6, bizkeyOrderValue, argList);
            values.append(",");
        }
        if (tableInfo.getOrderField() != null) {
            String orderFieldName = tableInfo.getOrderField().getName();
            if (floatOrder == 0.0) {
                StringBuilder querySql = new StringBuilder();
                ArrayList<Object> arrayList = new ArrayList<Object>();
                querySql.append(" select max(").append(orderFieldName).append(")");
                querySql.append(" from ").append(physicalTableName).append(" ").append(physicalTableName);
                querySql.append(" where ");
                this.appendWhereCondition(qContext, connection, physicalTableName, tableInfo, querySql, arrayList, qContext.getCurrentMasterKey());
                try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
                     ResultSet rs = sqlHelper.executeQuery(connection, querySql.toString(), arrayList.toArray(), qContext.getMonitor());){
                    if (rs.next()) {
                        floatOrder = rs.getDouble(1);
                    }
                }
            }
            record.addData(orderFieldName, tableInfo.getOrderField().getColumnType().getValue(), tableInfo.getOrderField().getDecimal(), DataEngineConsts.formatData(tableInfo.getOrderField(), (Object)(floatOrder += 1000.0), null), null);
            insertSql.append(orderFieldName);
            FieldSqlConditionUtil.appendValue(qContext.getQueryParam().getDatabase(), connection, values, 3, floatOrder, argList);
        } else {
            insertSql.setLength(insertSql.length() - 1);
            values.setLength(values.length() - 1);
        }
        if (argMap != null) {
            Set<Map.Entry<String, String>> entrySet = argMap.entrySet();
            for (Map.Entry entry : entrySet) {
                String fieldName = (String)entry.getKey();
                String value = (String)entry.getValue();
                insertSql.append(",").append(fieldName);
                values.append(",");
                FieldSqlConditionUtil.appendValue(qContext.getQueryParam().getDatabase(), connection, values, 6, value, argList);
            }
        }
        insertSql.append(")");
        insertSql.append(" values (").append((CharSequence)values).append(")");
        String sql = insertSql.toString();
        if (qContext.isDebug()) {
            long start = System.currentTimeMillis();
            DataEngineUtil.executeUpdate(connection, sql, argList == null ? null : argList.toArray(), qContext.getMonitor());
            long exeCost = System.currentTimeMillis() - start;
            LogRow logRow = new LogRow(LogType.INSERT, exeCost, exeCost, record.getUpdateColumns().size(), 1, physicalTableName, sql, qContext.getDateFormat());
            qContext.getMonitor().message(logRow.toString(), null);
            if (collector != null) {
                collector.addSqlLogRow(logRow);
            }
        } else {
            DataEngineUtil.executeUpdate(connection, sql, argList == null ? null : argList.toArray(), qContext.getMonitor());
        }
        if (collector != null) {
            collector.getGlobalInfo().addInsertRecordCount();
        }
        return floatOrder;
    }

    private int updateByJdbcSql(QueryContext qContext, String physicalTableName, TableModelRunInfo tableInfo, UpdateDataRecord record, Connection connection, FmlExecuteCollector collector) throws SQLException {
        StringBuilder updateSql = new StringBuilder();
        DimensionValueSet rowKey = record.getRowkeys();
        Map<String, String> argMap = this.getConditionArgMap(qContext, physicalTableName);
        updateSql.append("update ").append(physicalTableName).append(" ").append(physicalTableName);
        updateSql.append(" set ");
        ArrayList<Object> argList = null;
        Iterator<UpdateDataColumn> columnsIterator = record.getUpdateColumns().iterator();
        IEncryptDecryptProcesser encryptDecryptProcesser = qContext.getQueryParam().getEncryptDecryptProcesser();
        IFieldValueUpdateProcessor fieldValueUpdateProcessor = qContext.getExeContext().getEnv().getFieldValueUpdateProcessor();
        while (columnsIterator.hasNext()) {
            UpdateDataColumn column = columnsIterator.next();
            if (column.isModified()) {
                if (argList == null) {
                    argList = new ArrayList<Object>();
                }
                Object value = this.getColumnValue(qContext, tableInfo, encryptDecryptProcesser, fieldValueUpdateProcessor, column);
                if (column.getType() == 1 && value instanceof Boolean) {
                    boolean bl = (Boolean)value;
                    value = bl ? 1 : 0;
                }
                if (this.sqlSoftParse) {
                    argList.add(value);
                    updateSql.append(column.getName()).append("=?").append(",");
                    continue;
                }
                updateSql.append(column.getName()).append("=");
                FieldSqlConditionUtil.appendConstValue(qContext.getQueryParam().getDatabase(), connection, updateSql, column.getType(), value);
                updateSql.append(",");
                continue;
            }
            columnsIterator.remove();
        }
        if (record.getUpdateColumns().isEmpty()) {
            return 1;
        }
        updateSql.setLength(updateSql.length() - 1);
        updateSql.append(" where ");
        this.appendWhereCondition(qContext, connection, physicalTableName, tableInfo, updateSql, argList, rowKey);
        if (argMap != null) {
            Set<Map.Entry<String, String>> entrySet = argMap.entrySet();
            for (Map.Entry entry : entrySet) {
                String fieldName = (String)entry.getKey();
                String value = (String)entry.getValue();
                updateSql.append(" and ").append(physicalTableName).append(".").append(fieldName);
                if (this.sqlSoftParse) {
                    updateSql.append("=?");
                    argList.add(value);
                    continue;
                }
                updateSql.append("=");
                FieldSqlConditionUtil.appendConstValue(qContext.getQueryParam().getDatabase(), connection, updateSql, 6, value);
            }
        }
        int result = 0;
        String sql = updateSql.toString();
        if (qContext.isDebug()) {
            long l = System.currentTimeMillis();
            result = DataEngineUtil.executeUpdate(connection, sql, argList.toArray(), qContext.getMonitor());
            long exeCost = System.currentTimeMillis() - l;
            LogRow logRow = new LogRow(LogType.UPDATE, exeCost, exeCost, record.getUpdateColumns().size(), 1, physicalTableName, sql, qContext.getDateFormat());
            qContext.getMonitor().message(logRow.toString(), null);
            if (collector != null) {
                collector.addSqlLogRow(logRow);
            }
        } else {
            result = DataEngineUtil.executeUpdate(connection, sql, argList.toArray(), qContext.getMonitor());
        }
        if (result > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\u8fd0\u7b97\u7ed3\u679c\u66f4\u65b0sql\u66f4\u65b0\u4e86\u591a\u6761\u8bb0\u5f55,\u8bf7\u68c0\u67e5\u5b58\u50a8\u8868\u7ef4\u5ea6\u5b8c\u6574\u6027\u53ca\u662f\u5426\u6709\u5783\u573e\u6570\u636e,sql:\n").append((CharSequence)updateSql);
            qContext.getMonitor().exception(new DataValidateException(stringBuilder.toString()));
        }
        if (result > 0 && collector != null) {
            collector.getGlobalInfo().addUpdateRecordCount();
        }
        return result;
    }

    public void appendWhereCondition(QueryContext qContext, Connection connection, String tableName, TableModelRunInfo tableInfo, StringBuilder updateSql, List<Object> argList, DimensionValueSet dimensionValueSet) {
        boolean needAnd = false;
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String dimension = dimensionValueSet.getName(i);
            if (!tableInfo.getDimensions().contains(dimension)) continue;
            if (needAnd) {
                updateSql.append(" and ");
            }
            String dimensionFieldName = dimension;
            ColumnModelDefine dimensionField = tableInfo.getDimensionField(dimension);
            Object dimValue = dimensionValueSet.getValue(i);
            if (dimensionField.getColumnType() == ColumnModelType.UUID) {
                dimValue = Convert.toUUID((Object)dimValue);
            } else if (dimensionField.getColumnType() == ColumnModelType.DATETIME && dimValue instanceof String) {
                dimValue = DataTypesConvert.periodToDate(new PeriodWrapper((String)dimValue));
            }
            if (dimensionField != null) {
                dimensionFieldName = dimensionField.getName();
            }
            updateSql.append(tableName).append(".").append(dimensionFieldName);
            if (this.sqlSoftParse) {
                updateSql.append("=?");
                argList.add(dimValue);
            } else {
                updateSql.append("=");
                FieldSqlConditionUtil.appendConstValue(qContext.getQueryParam().getDatabase(), connection, updateSql, dimensionField.getColumnType().getValue(), dimValue);
            }
            needAnd = true;
        }
    }

    private UpdateDataSet getUpdateDataSet() {
        if (this.updateDataSet == null) {
            this.updateDataSet = new UpdateDataSet(false);
        }
        return this.updateDataSet;
    }

    public boolean isNeedNewRowKey() {
        return this.needNewRowKey;
    }

    public void setNeedNewRowKey(boolean needNewRowKey) {
        this.needNewRowKey = needNewRowKey;
    }

    private Object getDimensionDefaultValue(QueryContext qContext, TableModelRunInfo tableInfo, String dimensionName) {
        Object defaultValue;
        block7: {
            if (this.dimDefaultValueExps == null) {
                this.dimDefaultValueExps = new HashMap();
            }
            defaultValue = null;
            String defaultValueStr = null;
            ColumnModelDefine dimField = null;
            try {
                if (this.dimDefaultValueExps.containsKey(dimensionName)) {
                    IExpression defaultValueExp = this.dimDefaultValueExps.get(dimensionName);
                    if (defaultValueExp != null) {
                        defaultValue = defaultValueExp.evaluate((IContext)qContext);
                    }
                } else {
                    ReportFormulaParser formulaParser;
                    IExpression defaultValueExp;
                    dimField = tableInfo.getDimensionField(dimensionName);
                    defaultValueStr = dimField.getDefaultValue();
                    this.dimDefaultValueExps.put(dimensionName, null);
                    if (StringUtils.isNotEmpty((String)defaultValueStr) && (defaultValueExp = (formulaParser = qContext.getExeContext().getCache().getFormulaParser(true)).parseEval(defaultValueStr, (IContext)qContext)) != null) {
                        this.dimDefaultValueExps.put(dimensionName, defaultValueExp);
                        defaultValue = defaultValueExp.evaluate((IContext)qContext);
                    }
                }
            }
            catch (Exception e) {
                if (dimField == null || dimField.getColumnType() != ColumnModelType.STRING || !StringUtils.isNotEmpty(defaultValueStr)) break block7;
                defaultValue = defaultValueStr;
            }
        }
        return defaultValue;
    }

    private double insertByNrdb(QueryContext qContext, String physicalTableName, TableModelRunInfo tableInfo, UpdateDataRecord record, Connection connection, double maxFloatOrder, FmlExecuteCollector collector) throws Exception {
        Throwable throwable;
        ColumnModelDefine bizkeyOrderField = tableInfo.getBizOrderField();
        ArrayList<String> columnNames = new ArrayList<String>();
        ArrayList<Object> argList = new ArrayList<Object>();
        DimensionValueSet rowKey = record.getRowkeys();
        for (int i = 0; i < rowKey.size(); ++i) {
            Object dimensionField;
            String dimension = rowKey.getName(i);
            if (!tableInfo.getDimensions().contains(dimension) || (dimensionField = tableInfo.getDimensionField(dimension)) == null) continue;
            String dimensionFieldName = dimensionField.getName();
            Object dimValue = rowKey.getValue(i);
            if (bizkeyOrderField != null && dimensionFieldName.equals(bizkeyOrderField.getName())) {
                dimValue = UUID.randomUUID().toString();
            }
            if (dimensionField.getColumnType() == ColumnModelType.UUID) {
                dimValue = Convert.toUUID((Object)dimValue);
            } else if (dimensionField.getColumnType() == ColumnModelType.DATETIME && dimValue instanceof String) {
                dimValue = DataTypesConvert.periodToDate(new PeriodWrapper((String)dimValue));
            }
            columnNames.add(dimensionFieldName);
            argList.add(dimValue);
        }
        if (bizkeyOrderField != null && !tableInfo.getDimensions().contains("RECORDKEY")) {
            String bizkeyOrderFieldName = bizkeyOrderField.getName();
            String bizkeyOrderValue = UUID.randomUUID().toString();
            record.addData(bizkeyOrderFieldName, bizkeyOrderField.getColumnType().getValue(), bizkeyOrderField.getDecimal(), bizkeyOrderValue, null);
            columnNames.add(bizkeyOrderFieldName);
            argList.add(bizkeyOrderValue);
        }
        IEncryptDecryptProcesser encryptDecryptProcesser = qContext.getQueryParam().getEncryptDecryptProcesser();
        IFieldValueUpdateProcessor fieldValueUpdateProcessor = qContext.getExeContext().getEnv().getFieldValueUpdateProcessor();
        for (UpdateDataColumn column : record.getUpdateColumns()) {
            if (columnNames.contains(column.getName())) continue;
            Object value = this.getColumnValue(qContext, tableInfo, encryptDecryptProcesser, fieldValueUpdateProcessor, column);
            columnNames.add(column.getName());
            argList.add(value);
        }
        if (tableInfo.getOrderField() != null) {
            String orderFieldName = tableInfo.getOrderField().getName();
            if (maxFloatOrder == 0.0) {
                DBQueryBuilder dbQueryBuilder = new DBQueryBuilder();
                dbQueryBuilder.select(orderFieldName, DBAggregation.MAX);
                for (int i = 0; i < rowKey.size(); ++i) {
                    ColumnModelDefine dimensionField;
                    String dimension = rowKey.getName(i);
                    if (!tableInfo.getDimensions().contains(dimension) || (dimensionField = tableInfo.getDimensionField(dimension)) == null) continue;
                    String dimensionFieldName = dimensionField.getName();
                    Object dimValue = rowKey.getValue(i);
                    if (dimensionField.getColumnType() == ColumnModelType.DATETIME && dimValue instanceof String) {
                        dimValue = DataTypesConvert.periodToDate(new PeriodWrapper((String)dimValue));
                    }
                    dbQueryBuilder.filterByValues(dimensionFieldName, new String[]{dimValue.toString()});
                }
                throwable = null;
                try (DBTable dbTable = NrdbStorageManager.getInstance().openTable(tableInfo.getTableModelDefine());
                     DBCursor cursor = dbTable.query(dbQueryBuilder.build());){
                    if (cursor.hasNext()) {
                        maxFloatOrder = ((DBRecord)cursor.next()).getDouble(0);
                    }
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
            }
            record.addData(orderFieldName, tableInfo.getOrderField().getColumnType().getValue(), tableInfo.getOrderField().getDecimal(), DataEngineConsts.formatData(tableInfo.getOrderField(), (Object)(maxFloatOrder += 1000.0), null), null);
            columnNames.add(orderFieldName);
            argList.add(maxFloatOrder);
        }
        this.appendArgMapToNrdbRecord(qContext, physicalTableName, argList, columnNames);
        try (DBTable dbTable = NrdbStorageManager.getInstance().openTable(tableInfo.getTableModelDefine());){
            throwable = null;
            try (DBTransaction transaction = dbTable.newTransaction(columnNames);){
                transaction.insert((DBRecord)new ArrayRecord(argList));
                if (collector != null) {
                    collector.getGlobalInfo().addInsertRecordCount();
                }
                transaction.commit();
            }
            catch (Throwable throwable3) {
                throwable = throwable3;
                throw throwable3;
            }
        }
        return maxFloatOrder;
    }

    /*
     * Exception decompiling
     */
    private boolean updateByNrdb(QueryContext qContext, String physicalTableName, TableModelRunInfo tableInfo, UpdateDataRecord record, Connection connection, FmlExecuteCollector collector) throws Exception {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private void appendArgMapToNrdbRecord(QueryContext qContext, String physicalTableName, List<Object> argList, List<String> columnNames) {
        Map<String, String> argMap = this.getConditionArgMap(qContext, physicalTableName);
        if (argMap != null) {
            Set<Map.Entry<String, String>> entrySet = argMap.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String fieldName = entry.getKey();
                String value = entry.getValue();
                columnNames.add(fieldName);
                argList.add(value);
            }
        }
    }

    private Object getColumnValue(QueryContext qContext, TableModelRunInfo tableInfo, IEncryptDecryptProcesser encryptDecryptProcesser, IFieldValueUpdateProcessor fieldValueUpdateProcessor, UpdateDataColumn column) {
        Object value = column.getValue();
        try {
            FieldDefine fieldDefine;
            ColumnModelDefine field = null;
            if (fieldValueUpdateProcessor != null) {
                field = tableInfo.getFieldByName(column.getName());
                fieldDefine = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(field);
                value = fieldValueUpdateProcessor.processValue(qContext, fieldDefine, value);
            }
            if (value != null && column.getType() == 6) {
                if (field == null) {
                    field = tableInfo.getFieldByName(column.getName());
                }
                if (encryptDecryptProcesser != null) {
                    fieldDefine = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(field);
                    value = encryptDecryptProcesser.encrypt(qContext, fieldDefine, value.toString());
                }
                value = qContext.encrypt(field.getSceneId(), value.toString());
            }
            column.setValue(value);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    private Map<String, String> getConditionArgMap(QueryContext qContext, String physicalTableName) {
        ITableConditionProvider conditionProvider = qContext.getTableConditionProvider();
        Map<String, String> argMap = null;
        if (conditionProvider != null) {
            argMap = conditionProvider.getTableCondition(qContext.getExeContext(), physicalTableName);
        }
        return argMap;
    }

    public String toString() {
        return this.updateDataSet.toString();
    }
}

