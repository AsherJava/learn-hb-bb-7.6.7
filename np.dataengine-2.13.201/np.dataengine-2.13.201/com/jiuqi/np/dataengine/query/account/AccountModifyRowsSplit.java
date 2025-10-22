/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.temp.ITempTableMeta
 *  com.jiuqi.bi.database.temp.TempTable
 *  com.jiuqi.bi.database.temp.TempTableProviderFactory
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.TempTableActuator
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.query.account;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.temp.ITempTableMeta;
import com.jiuqi.bi.database.temp.TempTable;
import com.jiuqi.bi.database.temp.TempTableProviderFactory;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.query.account.AccountTableChangesCommitter;
import com.jiuqi.np.dataengine.query.account.AccountTempTable;
import com.jiuqi.np.dataengine.query.account.EmptyDimUtil;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.TempTableActuator;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountModifyRowsSplit {
    private static final Logger logger = LoggerFactory.getLogger(AccountModifyRowsSplit.class);
    private final AccountTableChangesCommitter committer;
    private final TableModelRunInfo tableModelRunInfoAcc;
    private final TableModelRunInfo tableModelRunInfoRpt;
    private final String curDataTime;
    private final ExecutorContext context;
    private final HashMap<QueryField, List<Integer>> accQueryFields;
    private final boolean canModifyKey;
    private final List<DataRowImpl> dataRows;
    private final Map<DimensionValueSet, DataRowImpl> map = new HashMap<DimensionValueSet, DataRowImpl>();
    private final Map<DataRowImpl, String> sbidMap = new HashMap<DataRowImpl, String>();
    private final Map<String, DataRowImpl> sbidMap2 = new HashMap<String, DataRowImpl>();
    private final Map<DataRowImpl, String> validDataTimeMap = new HashMap<DataRowImpl, String>();
    private String querySqlRpt;
    private final String tableName;
    private final String tableNameRpt;
    private final String tableNameHis;
    private final Connection connection;
    private final IMonitor monitor;
    private final List<DataRowImpl> versionRows = new ArrayList<DataRowImpl>();
    private final List<DataRowImpl> noVersionRows = new ArrayList<DataRowImpl>();
    private final List<DataRowImpl> rows4RptUpdate = new ArrayList<DataRowImpl>();
    private final List<DataRowImpl> rows4RptInsert = new ArrayList<DataRowImpl>();
    private boolean containsSbid = false;
    private String sbidDimension;
    private int sbidIndex = -1;
    private final List<ColumnModelDefine> rowKeyCols = new ArrayList<ColumnModelDefine>();
    private static final int TEMP_ROW_NUM = 100;
    private boolean useTemp;

    public AccountModifyRowsSplit(ExecutorContext context, TableModelRunInfo tableRunInfoAcc, TableModelRunInfo tableRunInfoRpt, String tableName, HashMap<QueryField, List<Integer>> accQueryFields, boolean rptExist, List<DataRowImpl> dataRows, String curDateTime, Connection connection, IMonitor monitor, boolean canModifyKey, AccountTableChangesCommitter committer) {
        this.tableModelRunInfoAcc = tableRunInfoAcc;
        this.tableModelRunInfoRpt = tableRunInfoRpt;
        this.accQueryFields = accQueryFields;
        this.curDataTime = curDateTime;
        this.dataRows = dataRows;
        this.tableName = tableName;
        this.tableNameRpt = tableName + "_RPT";
        this.tableNameHis = tableName + "_HIS";
        this.connection = connection;
        this.monitor = monitor;
        this.context = context;
        this.canModifyKey = canModifyKey;
        this.committer = committer;
        this.mapDataRowsAndCombineRowKeys();
        this.analysisRowKeys();
        this.doQuery();
        if (rptExist) {
            this.doBuildRptSql();
            this.doQueryRpt();
        }
    }

    private void analysisRowKeys() {
        DimensionValueSet rowKeys = this.dataRows.get(0).getRowKeys();
        for (int i = 0; i < rowKeys.size(); ++i) {
            ColumnModelDefine dimensionField = this.tableModelRunInfoAcc.getDimensionField(rowKeys.getName(i));
            String name = dimensionField.getName();
            if ("SBID".equals(name)) {
                this.containsSbid = true;
                this.sbidDimension = rowKeys.getName(i);
                this.sbidIndex = i + 1;
            }
            this.rowKeyCols.add(dimensionField);
        }
        IDatabase database = DatabaseInstance.getDatabase();
        int maxInSize = DataEngineUtil.getMaxInSize(database);
        this.useTemp = this.containsSbid ? this.dataRows.size() >= maxInSize : this.dataRows.size() * rowKeys.size() >= maxInSize;
    }

    private void doSplitRpt(ResultSet resultSetRpt) {
        if (resultSetRpt != null) {
            try {
                while (resultSetRpt.next()) {
                    DataRowImpl dataRow;
                    String s = resultSetRpt.getString(1);
                    if (!StringUtils.isNotEmpty((String)s) || (dataRow = this.sbidMap2.get(s)) == null) continue;
                    this.rows4RptUpdate.add(dataRow);
                }
                for (DataRowImpl dataRow : this.dataRows) {
                    if (this.rows4RptUpdate.contains(dataRow)) continue;
                    this.rows4RptInsert.add(dataRow);
                }
            }
            catch (Exception e) {
                throw new RuntimeException("\u53f0\u8d26\u62a5\u8868[" + this.tableNameRpt + "]\u66f4\u65b0\u65f6\u67e5\u8be2\u51fa\u9519", e);
            }
        }
    }

    private DataRowImpl getDataRowRpt(DimensionValueSet dimensionValueSet) {
        for (Map.Entry<DimensionValueSet, DataRowImpl> entry : this.map.entrySet()) {
            DimensionValueSet rowKeys = entry.getKey();
            DimensionValueSet rptRowKeys = new DimensionValueSet();
            for (int i = 0; i < rowKeys.size(); ++i) {
                if (this.tableModelRunInfoRpt.getDimensionField(rowKeys.getName(i)) == null) continue;
                rptRowKeys.setValue(rowKeys.getName(i), rowKeys.getValue(i));
            }
            if (!rptRowKeys.equals(dimensionValueSet)) continue;
            return entry.getValue();
        }
        return null;
    }

    private void doQueryRpt() {
        if (this.sbidMap2.size() > 0) {
            Object[] args = new Object[this.sbidMap2.size() + 1];
            int index = 0;
            args[index++] = this.curDataTime;
            for (String s : this.sbidMap2.keySet()) {
                args[index++] = s;
            }
            try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
                 ResultSet rs = sqlHelper.executeQuery(this.connection, this.querySqlRpt, args, this.monitor);){
                this.doSplitRpt(rs);
            }
            catch (Exception e) {
                throw new RuntimeException("\u53f0\u8d26\u62a5\u8868[" + this.tableName + "]\u66f4\u65b0\u65f6\u67e5\u8be2\u51fa\u9519", e);
            }
        }
    }

    private void doSplitAcc(ResultSet resultSet) {
        if (resultSet != null) {
            int sbIndex = this.containsSbid ? this.sbidIndex : this.rowKeyCols.size() + this.accQueryFields.size() + 1;
            int validTimeIndex = this.containsSbid ? this.rowKeyCols.size() + this.accQueryFields.size() + 1 : this.rowKeyCols.size() + this.accQueryFields.size() + 2;
            try {
                DimensionValueSet rowKeys = this.dataRows.get(0).getRowKeys();
                while (resultSet.next()) {
                    DimensionValueSet rowDim = new DimensionValueSet();
                    for (int i = 0; i < this.rowKeyCols.size(); ++i) {
                        ColumnModelType type = this.rowKeyCols.get(i).getColumnType();
                        if (ColumnModelType.DATETIME == type) {
                            Date date = resultSet.getDate(i + 1);
                            rowDim.setValue(rowKeys.getName(i), date);
                            continue;
                        }
                        String s = resultSet.getString(i + 1);
                        rowDim.setValue(rowKeys.getName(i), s);
                    }
                    DataRowImpl dataRow = this.getDataRow(rowDim);
                    if (dataRow == null) continue;
                    String sbid = resultSet.getString(sbIndex);
                    String validDataTime = resultSet.getString(validTimeIndex);
                    this.validDataTimeMap.put(dataRow, validDataTime);
                    this.sbidMap.put(dataRow, sbid);
                    this.sbidMap2.put(sbid, dataRow);
                    int index = this.rowKeyCols.size() + 1;
                    if (this.committer.isTrackHis()) {
                        boolean version = false;
                        for (Map.Entry<QueryField, List<Integer>> entry : this.accQueryFields.entrySet()) {
                            QueryField queryField = entry.getKey();
                            if (queryField.isNeedAccountVersion()) {
                                Object originalValue;
                                List<Integer> columnIndexs = entry.getValue();
                                Object dataValue = dataRow.internalGetValue(columnIndexs);
                                try {
                                    originalValue = resultSet.getObject(index);
                                }
                                catch (SQLException e) {
                                    originalValue = null;
                                }
                                FieldDefine fieldDefine = dataRow.getSystemFields().getFieldDefine(columnIndexs.get(0));
                                Object formatData = this.formatData(fieldDefine, originalValue, dataRow.getTableImpl());
                                if (dataValue instanceof java.util.Date) {
                                    String originalDate = formatData == null ? "" : formatData.toString().substring(0, 19);
                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    java.util.Date date = new java.util.Date(((java.util.Date)dataValue).getTime());
                                    String dateStr = format.format(date);
                                    if (!dateStr.equals(originalDate)) {
                                        this.versionRows.add(dataRow);
                                        version = true;
                                        break;
                                    }
                                } else if (!dataRow.isEqual(formatData, dataValue)) {
                                    this.versionRows.add(dataRow);
                                    version = true;
                                    break;
                                }
                            }
                            ++index;
                        }
                        if (version) continue;
                        this.noVersionRows.add(dataRow);
                        continue;
                    }
                    this.noVersionRows.add(dataRow);
                }
                if (this.canModifyKey && this.committer.isTrackHis()) {
                    for (DataRowImpl row : this.dataRows) {
                        DimensionValueSet originalRowKeys = row.getRowKeys();
                        for (int i = 0; i < originalRowKeys.size(); ++i) {
                            String dimension = originalRowKeys.getName(i);
                            Object originalKeyValue = originalRowKeys.getValue(i);
                            Object keyValue = this.committer.getKeyValue(row, dimension);
                            ColumnModelType type = this.tableModelRunInfoAcc.getDimensionField(originalRowKeys.getName(i)).getColumnType();
                            keyValue = EmptyDimUtil.getStringEmptyKeyValue(keyValue, type);
                            if (this.versionRows.contains(row) || row.isEqual(originalKeyValue, keyValue)) continue;
                            this.versionRows.add(row);
                            this.noVersionRows.remove(row);
                        }
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException("\u53f0\u8d26\u8868[" + this.tableName + "]\u66f4\u65b0\u65f6\u67e5\u8be2\u51fa\u9519", e);
            }
        }
    }

    private Object formatData(FieldDefine fieldDefine, Object originalValue, ReadonlyTableImpl tableImpl) {
        if (fieldDefine.getType() == FieldType.FIELD_TYPE_DATE && originalValue instanceof LocalDateTime) {
            Instant instant = ((LocalDateTime)originalValue).atZone(ZoneId.systemDefault()).toInstant();
            return java.util.Date.from(instant);
        }
        return DataEngineConsts.formatData(fieldDefine, originalValue, tableImpl);
    }

    private DataRowImpl getDataRow(DimensionValueSet rowDim) {
        return this.map.get(rowDim);
    }

    private void doQuery() {
        ArrayList<Object[]> sbids = new ArrayList<Object[]>();
        ArrayList<Object> sbidList = new ArrayList<Object>();
        if (this.containsSbid) {
            for (DataRowImpl dataRow : this.dataRows) {
                Object[] arr = new Object[1];
                Object value = dataRow.getRowKeys().getValue(this.sbidDimension);
                ColumnModelType columnType = this.tableModelRunInfoAcc.getDimensionField(this.sbidDimension).getColumnType();
                arr[0] = value = EmptyDimUtil.validateKeyValue(value, columnType);
                sbidList.add(value);
                sbids.add(arr);
            }
        }
        if (this.useTemp) {
            if (this.containsSbid) {
                this.SBIDTempQuery(sbids);
            } else {
                this.noSBIDTempQuery();
            }
        } else if (this.containsSbid) {
            this.SBIDOrQuery(sbidList);
        } else {
            this.noSBIDOrQuery();
        }
    }

    private void noSBIDTempQuery() {
        AccountTempTable accountTempTable = new AccountTempTable(this.rowKeyCols);
        try (ITempTable tableByMeta = TempTableActuator.getTempTableByMeta((Connection)this.connection, (ITempTableMeta)accountTempTable);){
            String tempTable = tableByMeta.getTableName();
            StringBuilder tmpSql = new StringBuilder("insert into %s (");
            StringBuilder tmpSql2 = new StringBuilder();
            int rowKeySize = this.dataRows.get(0).getRowKeys().size();
            for (ColumnModelDefine columnModelDefine : this.rowKeyCols) {
                String realColName = tableByMeta.getRealColName(columnModelDefine.getName());
                tmpSql.append(realColName).append(",");
                tmpSql2.append("?,");
            }
            tmpSql.setLength(tmpSql.length() - 1);
            tmpSql2.setLength(tmpSql2.length() - 1);
            tmpSql.append(") values (").append((CharSequence)tmpSql2).append(")");
            ArrayList<Object[]> tmpBatchValues = new ArrayList<Object[]>();
            for (DataRowImpl dataRow : this.dataRows) {
                Object[] values = new Object[rowKeySize];
                int n = 0;
                for (int i = 0; i < rowKeySize; ++i) {
                    Object value = dataRow.getRowKeys().getValue(i);
                    ColumnModelType type = this.tableModelRunInfoAcc.getDimensionField(dataRow.getRowKeys().getName(i)).getColumnType();
                    value = EmptyDimUtil.validateKeyValue(value, type);
                    values[n++] = value;
                }
                tmpBatchValues.add(values);
            }
            DataEngineUtil.batchUpdate(this.connection, String.format(tmpSql.toString(), tempTable), tmpBatchValues, this.monitor);
            StringBuilder stringBuilder = new StringBuilder();
            ArrayList<String> args = new ArrayList<String>();
            stringBuilder.append("select ");
            for (ColumnModelDefine columnModelDefine : this.rowKeyCols) {
                stringBuilder.append("t1.").append(columnModelDefine.getName()).append(",");
            }
            for (Map.Entry entry : this.accQueryFields.entrySet()) {
                stringBuilder.append("t1.").append(((QueryField)entry.getKey()).getFieldName()).append(",");
            }
            stringBuilder.append("SBID").append(",");
            stringBuilder.append("VALIDDATATIME");
            stringBuilder.append(" from ").append(this.tableName).append(" as t1 ");
            stringBuilder.append(" inner join ").append(tempTable).append(" as t2 ");
            stringBuilder.append(" on ");
            boolean addAnd = false;
            for (ColumnModelDefine col : this.rowKeyCols) {
                if (addAnd) {
                    stringBuilder.append(" and ");
                }
                stringBuilder.append("t1.").append(col.getName()).append("=").append("t2.").append(tableByMeta.getRealColName(col.getName()));
                addAnd = true;
            }
            stringBuilder.append(" where t1.").append("VALIDDATATIME").append("<=? ");
            args.add(this.curDataTime);
            try (SqlQueryHelper sqlQueryHelper = DataEngineUtil.createSqlQueryHelper();
                 ResultSet rs = sqlQueryHelper.executeQuery(this.connection, stringBuilder.toString(), args.toArray(new Object[0]), this.monitor);){
                this.doSplitAcc(rs);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u53f0\u8d26\u8868[" + this.tableName + "]\u66f4\u65b0\u65f6\u67e5\u8be2\u51fa\u9519:" + e.getMessage(), e);
        }
        finally {
            this.handleConCom();
        }
    }

    private void noSBIDOrQuery() {
        try {
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> args = new ArrayList<Object>();
            sql.append("select ");
            for (ColumnModelDefine columnModelDefine : this.rowKeyCols) {
                sql.append(columnModelDefine.getName()).append(",");
            }
            for (Map.Entry entry : this.accQueryFields.entrySet()) {
                sql.append(((QueryField)entry.getKey()).getFieldName()).append(",");
            }
            sql.append("SBID").append(",");
            sql.append("VALIDDATATIME");
            sql.append(" from ").append(this.tableName).append(" where ").append("(");
            boolean addOr = false;
            for (DataRowImpl dataRow : this.dataRows) {
                if (addOr) {
                    sql.append(" or ");
                }
                sql.append("(");
                boolean addAdd = false;
                for (int i = 0; i < dataRow.getRowKeys().size(); ++i) {
                    if (addAdd) {
                        sql.append(" and ");
                    }
                    ColumnModelDefine dimensionField = this.tableModelRunInfoAcc.getDimensionField(dataRow.getRowKeys().getName(i));
                    sql.append(dimensionField.getName()).append("=?");
                    Object keyValue = dataRow.getRowKeys().getValue(i);
                    ColumnModelType type = dimensionField.getColumnType();
                    keyValue = EmptyDimUtil.validateKeyValue(keyValue, type);
                    args.add(keyValue);
                    addAdd = true;
                }
                sql.append(")");
                addOr = true;
            }
            sql.append(")").append(" and ").append("VALIDDATATIME").append("<=? ");
            args.add(this.curDataTime);
            try (SqlQueryHelper sqlQueryHelper = DataEngineUtil.createSqlQueryHelper();
                 ResultSet rs = sqlQueryHelper.executeQuery(this.connection, sql.toString(), args.toArray(new Object[0]), this.monitor);){
                this.doSplitAcc(rs);
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u53f0\u8d26\u8868[" + this.tableName + "]\u66f4\u65b0\u65f6\u67e5\u8be2\u51fa\u9519:" + e.getMessage(), e);
        }
    }

    private void SBIDOrQuery(List<Object> sbidList) {
        try {
            StringBuilder sql = new StringBuilder();
            ArrayList<Object> args = new ArrayList<Object>();
            sql.append("select ");
            for (ColumnModelDefine columnModelDefine : this.rowKeyCols) {
                sql.append(columnModelDefine.getName()).append(",");
            }
            for (Map.Entry entry : this.accQueryFields.entrySet()) {
                sql.append(((QueryField)entry.getKey()).getFieldName()).append(",");
            }
            sql.append("VALIDDATATIME");
            sql.append(" from ").append(this.tableName).append(" where ").append("SBID").append(" in(");
            for (Object object : sbidList) {
                sql.append("?,");
                args.add(object);
            }
            sql.setLength(sql.length() - 1);
            sql.append(")").append(" and ").append("VALIDDATATIME").append("<=? ");
            args.add(this.curDataTime);
            Throwable throwable = null;
            try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
                 ResultSet rs = sqlHelper.executeQuery(this.connection, sql.toString(), args.toArray(new Object[0]), this.monitor);){
                this.doSplitAcc(rs);
            }
            catch (Throwable throwable2) {
                Throwable throwable3 = throwable2;
                throw throwable2;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u53f0\u8d26\u8868[" + this.tableName + "]\u66f4\u65b0\u65f6\u67e5\u8be2\u51fa\u9519:" + e.getMessage(), e);
        }
    }

    private void SBIDTempQuery(List<Object[]> sbids) {
        try (TempTable oneKeyTempTable = TempTableProviderFactory.getInstance().getTempTableProvider().getOneKeyTempTable(this.connection);){
            oneKeyTempTable.insertRecords(sbids);
            StringBuilder sql = new StringBuilder();
            ArrayList<String> args = new ArrayList<String>();
            sql.append("select ");
            for (ColumnModelDefine columnModelDefine : this.rowKeyCols) {
                sql.append("t1.").append(columnModelDefine.getName()).append(",");
            }
            for (Map.Entry entry : this.accQueryFields.entrySet()) {
                sql.append("t1.").append(((QueryField)entry.getKey()).getFieldName()).append(",");
            }
            sql.append("VALIDDATATIME");
            sql.append(" from ").append(this.tableName).append(" as t1 ");
            sql.append(" inner join ").append(oneKeyTempTable.getTableName()).append(" as t2 ");
            sql.append(" on t1.").append("SBID").append("=t2.").append("TEMP_KEY");
            sql.append(" where t1.").append("VALIDDATATIME").append("<=? ");
            args.add(this.curDataTime);
            Throwable throwable = null;
            try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
                 ResultSet rs = sqlHelper.executeQuery(this.connection, sql.toString(), args.toArray(new Object[0]), this.monitor);){
                this.doSplitAcc(rs);
            }
            catch (Throwable throwable2) {
                Throwable throwable3 = throwable2;
                throw throwable2;
            }
        }
        catch (Exception e) {
            throw new RuntimeException("\u53f0\u8d26\u8868[" + this.tableName + "]\u66f4\u65b0\u65f6\u67e5\u8be2\u51fa\u9519:" + e.getMessage(), e);
        }
        finally {
            this.handleConCom();
        }
    }

    private void handleConCom() {
        try {
            if (!this.connection.getAutoCommit()) {
                this.connection.commit();
            }
        }
        catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void doBuildRptSql() {
        StringBuilder sql = new StringBuilder();
        sql.append(" select ");
        sql.append("SBID");
        sql.append(" from ");
        sql.append(this.tableNameRpt);
        sql.append(" where ");
        sql.append("DATATIME");
        sql.append("=?");
        sql.append(" and ");
        sql.append("SBID");
        sql.append(" in ");
        sql.append("(");
        for (int i = 0; i < this.sbidMap2.size(); ++i) {
            sql.append("?,");
        }
        sql.setLength(sql.length() - 1);
        sql.append(")");
        this.querySqlRpt = sql.toString();
    }

    private void mapDataRowsAndCombineRowKeys() {
        for (DataRowImpl dataRow : this.dataRows) {
            dataRow.getRowKeys().clearValue("DATATIME");
            this.map.put(dataRow.getRowKeys(), dataRow);
        }
    }

    public List<DataRowImpl> getVersionRows() {
        return this.versionRows;
    }

    public int versionSizeInAcc() {
        return this.versionRows.size();
    }

    public List<DataRowImpl> getNoVersionRows() {
        return this.noVersionRows;
    }

    public int noVersionSizeInAcc() {
        return this.noVersionRows.size();
    }

    public List<DataRowImpl> getRows4RptUpdate() {
        return this.rows4RptUpdate;
    }

    public int updSizeInRpt() {
        return this.rows4RptUpdate.size();
    }

    public List<DataRowImpl> getRows4RptInsert() {
        return this.rows4RptInsert;
    }

    public int insSizeInRpt() {
        return this.rows4RptInsert.size();
    }

    public int accDataRowSize() {
        return this.versionSizeInAcc() + this.noVersionSizeInAcc();
    }

    public int rptDataRowSize() {
        return this.updSizeInRpt() + this.insSizeInRpt();
    }

    public Map<DataRowImpl, String> getSbidMap() {
        return this.sbidMap;
    }

    public Map<DataRowImpl, String> getValidDataTimeMap() {
        return this.validDataTimeMap;
    }
}

