/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.np.dataengine.query.account;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.DuplicateRowKeysException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.exception.IncorrectRowKeysException;
import com.jiuqi.np.dataengine.exception.OperateRowException;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.TableChangesCommitter;
import com.jiuqi.np.dataengine.query.account.AccountModifyRowsSplit;
import com.jiuqi.np.dataengine.query.account.EmptyDimUtil;
import com.jiuqi.np.dataengine.query.account.IAccountColumnModelFinder;
import com.jiuqi.np.dataengine.setting.FieldsInfoImpl;
import com.jiuqi.np.dataengine.update.UpdateDataRecord;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountTableChangesCommitter
extends TableChangesCommitter {
    private static final Logger logger = LoggerFactory.getLogger(AccountTableChangesCommitter.class);
    public static final String ACCOUNT_PRIMARY_KEY = "BIZKEYORDER";
    private final DataModelService dataModelService = (DataModelService)SpringBeanUtils.getBean(DataModelService.class);
    private String accountTableName;
    private String accountHistoryTableName;
    private String accountReportTableName;
    private boolean trackHis;
    private final Map<String, TableModelRunInfo> tableModelRunInfoMap;
    private List<ColumnModelDefine> accDimCols;
    private HashMap<QueryField, List<Integer>> accQueryFields;
    private List<ColumnModelDefine> rptDimCols;
    private HashMap<QueryField, List<Integer>> rptQueryFields;
    private boolean manualCommit = false;
    private IAccountColumnModelFinder accountColumnModelFinder;

    private void initTableName() {
        this.accountTableName = this.getTableName();
        this.accountHistoryTableName = this.accountTableName + "_HIS";
        this.accountReportTableName = this.accountTableName + "_RPT";
    }

    private void initParam() {
        this.accQueryFields = new HashMap();
        this.rptQueryFields = new HashMap();
        for (Map.Entry queryItem : this.queryFields.entrySet()) {
            QueryField queryField = (QueryField)queryItem.getKey();
            List queryIndex = (List)queryItem.getValue();
            if (queryField.getTable().isAccountTable()) {
                this.accQueryFields.put(queryField, queryIndex);
                continue;
            }
            this.rptQueryFields.put(queryField, queryIndex);
        }
        TableModelRunInfo accTableInfo = this.tableModelRunInfoMap.get(this.accountTableName);
        if (accTableInfo != null) {
            this.initFinder();
            this.trackHis = this.accountColumnModelFinder.ifTrackHistory(accTableInfo.getTableModelDefine().getID());
            this.accDimCols = accTableInfo.getDimFields().stream().filter(o -> !ACCOUNT_PRIMARY_KEY.equals(o.getName()) && !"DATATIME".equals(o.getName())).collect(Collectors.toList());
        } else {
            this.accDimCols = Collections.emptyList();
        }
        TableModelRunInfo rptTableInfo = this.tableModelRunInfoMap.get(this.accountReportTableName);
        this.rptDimCols = rptTableInfo != null ? rptTableInfo.getDimFields().stream().filter(o -> !ACCOUNT_PRIMARY_KEY.equals(o.getName()) && !"DATATIME".equals(o.getName())).collect(Collectors.toList()) : Collections.emptyList();
    }

    private void initFinder() {
        if (this.accountColumnModelFinder == null) {
            this.accountColumnModelFinder = (IAccountColumnModelFinder)SpringBeanUtils.getBean(IAccountColumnModelFinder.class);
        }
    }

    private void initTransactionConfigure() throws SQLException {
        if (this.connection.getAutoCommit()) {
            this.connection.setAutoCommit(false);
            this.manualCommit = true;
        }
    }

    public AccountTableChangesCommitter(QueryContext qContext, QueryTable queryTable, Map<String, TableModelRunInfo> tableModelRunInfoMap, HashMap<QueryField, List<Integer>> queryFields, TableModelRunInfo tableRunInfo, FieldsInfoImpl fieldsInfoImpl, FieldsInfoImpl systemFields, Connection connection, boolean designTimeData, DimensionValueSet masterKeys, boolean needCheckKeys, QueryParam queryParam) {
        super(qContext, queryTable, queryFields, tableRunInfo, fieldsInfoImpl, systemFields, connection, designTimeData, masterKeys, needCheckKeys, queryParam);
        this.tableModelRunInfoMap = tableModelRunInfoMap;
        this.initTableName();
        this.initParam();
    }

    @Override
    public void deleteRows(DimensionValueSet masterKeys, String rowFilter, HashMap<QueryField, ArrayList<Object>> colFilterValues, IMonitor monitor, DimensionValueSet deleteKeys) throws Exception {
        String curDataTime = (String)masterKeys.getValue("DATATIME");
        this.initTransactionConfigure();
        if (this.accQueryFields.size() > 0) {
            this.doDeleteAllTeAccTable(masterKeys, rowFilter, colFilterValues, deleteKeys, curDataTime, monitor);
        }
        if (this.rptQueryFields.size() > 0) {
            this.doDeleteAllRptTable(masterKeys, rowFilter, colFilterValues, deleteKeys, curDataTime, monitor);
        }
        this.manualCommitIfNeed();
    }

    @Override
    public void deleteRows(List<DataRowImpl> deleteRows, Set<String> currentPeriodSet, IMonitor monitor) throws SQLException {
        String curDataTime = (String)this.masterKeys.getValue("DATATIME");
        boolean rptExist = this.rptQueryFields.size() > 0;
        TableModelRunInfo tableModelRunInfoAcc = this.tableModelRunInfoMap.get(this.accountTableName);
        TableModelRunInfo tableModelRunInfoRpt = this.tableModelRunInfoMap.get(this.accountReportTableName);
        AccountModifyRowsSplit rowsSplit = new AccountModifyRowsSplit(this.qContext.getExeContext(), tableModelRunInfoAcc, tableModelRunInfoRpt, this.accountTableName, this.accQueryFields, rptExist, deleteRows, curDataTime, this.connection, monitor, this.canModifyKey, this);
        this.initTransactionConfigure();
        if (this.accQueryFields.size() > 0) {
            this.doDeleTeAccTable(deleteRows, curDataTime, monitor, rowsSplit.getValidDataTimeMap());
        }
        if (rptExist) {
            this.doDeleteRptTable(deleteRows, curDataTime, monitor, rowsSplit.getSbidMap());
        }
        this.manualCommitIfNeed();
    }

    @Override
    public void updateRows(List<DataRowImpl> updateRows, IMonitor monitor) throws SQLException, IncorrectQueryException {
        this.checkDimEmpty(updateRows);
        String curDataTime = (String)this.masterKeys.getValue("DATATIME");
        this.checkAccDuplicateKeys(updateRows, false);
        boolean rptExist = this.rptQueryFields.size() > 0;
        TableModelRunInfo tableModelRunInfoAcc = this.tableModelRunInfoMap.get(this.accountTableName);
        TableModelRunInfo tableModelRunInfoRpt = this.tableModelRunInfoMap.get(this.accountReportTableName);
        AccountModifyRowsSplit rowsSplit = new AccountModifyRowsSplit(this.qContext.getExeContext(), tableModelRunInfoAcc, tableModelRunInfoRpt, this.accountTableName, this.accQueryFields, rptExist, updateRows, curDataTime, this.connection, monitor, this.canModifyKey, this);
        this.initTransactionConfigure();
        if (this.accQueryFields.size() > 0) {
            if (rowsSplit.versionSizeInAcc() > 0) {
                this.doBatchUpdateAccTable(rowsSplit.getVersionRows(), curDataTime, true, monitor, rowsSplit.getValidDataTimeMap());
            }
            if (rowsSplit.noVersionSizeInAcc() > 0) {
                this.doBatchUpdateAccTable(rowsSplit.getNoVersionRows(), curDataTime, false, monitor, rowsSplit.getValidDataTimeMap());
            }
        }
        if (rptExist) {
            if (rowsSplit.updSizeInRpt() > 0) {
                this.doBatchUpdateRptTable(rowsSplit.getRows4RptUpdate(), monitor, rowsSplit.getSbidMap(), curDataTime);
            }
            if (rowsSplit.insSizeInRpt() > 0) {
                this.insertIfNoRptRow(rowsSplit.getRows4RptInsert(), monitor, rowsSplit.getSbidMap());
            }
        }
        this.manualCommitIfNeed();
    }

    @Override
    public void updateRow(DataRowImpl updateRow, IMonitor monitor) throws SQLException, IncorrectQueryException {
        ArrayList<DataRowImpl> updateRows = new ArrayList<DataRowImpl>();
        updateRows.add(updateRow);
        this.checkDimEmpty(updateRows);
        String curDataTime = (String)this.masterKeys.getValue("DATATIME");
        this.checkAccDuplicateKeys(updateRows, false);
        boolean rptExist = this.rptQueryFields.size() > 0;
        TableModelRunInfo tableModelRunInfoAcc = this.tableModelRunInfoMap.get(this.accountTableName);
        TableModelRunInfo tableModelRunInfoRpt = this.tableModelRunInfoMap.get(this.accountReportTableName);
        AccountModifyRowsSplit rowsSplit = new AccountModifyRowsSplit(this.qContext.getExeContext(), tableModelRunInfoAcc, tableModelRunInfoRpt, this.accountTableName, this.accQueryFields, rptExist, updateRows, curDataTime, this.connection, monitor, this.canModifyKey, this);
        this.initTransactionConfigure();
        if (this.accQueryFields.size() > 0 && rowsSplit.accDataRowSize() > 0) {
            boolean versionWork = rowsSplit.versionSizeInAcc() > 0;
            this.doUpdateAccTable(updateRow, updateRows, curDataTime, versionWork, monitor, rowsSplit.getValidDataTimeMap());
        }
        if (rptExist) {
            if (rowsSplit.updSizeInRpt() > 0) {
                this.doUpdateRptTable(updateRow, updateRows, monitor, rowsSplit.getSbidMap(), curDataTime);
            } else if (rowsSplit.insSizeInRpt() > 0) {
                this.insertIfNoRptRow(updateRows, monitor, rowsSplit.getSbidMap());
            }
        }
        this.manualCommitIfNeed();
    }

    @Override
    public void insertRows(List<DataRowImpl> insertRows, IMonitor monitor) throws SQLException, IncorrectQueryException {
        this.checkDimEmpty(insertRows);
        this.checkAccDuplicateKeys(insertRows, true);
        String curDataTime = (String)this.masterKeys.getValue("DATATIME");
        this.initTransactionConfigure();
        if (this.accQueryFields.size() > 0) {
            Map<DataRowImpl, String> sbidMap = this.doInsertAccTable(insertRows, monitor, curDataTime);
            if (this.rptQueryFields.size() > 0) {
                this.doInsertRptTable(insertRows, monitor, curDataTime, sbidMap);
            }
        }
        this.manualCommitIfNeed();
    }

    private Map<DataRowImpl, String> doInsertAccTable(List<DataRowImpl> insertRows, IMonitor monitor, String curDataTime) throws SQLException, IncorrectQueryException {
        ColumnModelDefine recField;
        StringBuilder insertSqlAcc = this.initInsertSql(this.accountTableName);
        StringBuilder valueSqlAcc = new StringBuilder();
        HashMap<DataRowImpl, String> map = new HashMap<DataRowImpl, String>();
        int valueCountAcc = 0;
        boolean addDotAcc = false;
        for (Map.Entry<QueryField, List<Integer>> queryItem : this.accQueryFields.entrySet()) {
            QueryField queryField = queryItem.getKey();
            String colName = queryField.getFieldName();
            this.processInsertColumn(insertSqlAcc, valueSqlAcc, colName, addDotAcc);
            addDotAcc = true;
            ++valueCountAcc;
        }
        DimensionSet dimensionSet = this.tableModelRunInfoMap.get(this.accountTableName).getDimensions();
        int dimCountAcc = 0;
        if (dimensionSet != null) {
            for (ColumnModelDefine columnModelDefine : this.accDimCols) {
                String colName = columnModelDefine.getName();
                this.processInsertColumn(insertSqlAcc, valueSqlAcc, colName, addDotAcc);
                addDotAcc = true;
                ++dimCountAcc;
            }
            if (StringUtils.isNotEmpty((String)curDataTime)) {
                this.processInsertColumn(insertSqlAcc, valueSqlAcc, "VALIDDATATIME", addDotAcc);
                addDotAcc = true;
                ++dimCountAcc;
            }
            if (!dimensionSet.contains("RECORDKEY")) {
                this.processInsertColumn(insertSqlAcc, valueSqlAcc, "SBID", addDotAcc);
                addDotAcc = true;
                ++dimCountAcc;
            }
        }
        boolean addRec = (recField = this.tableRunInfo.getRecField()) != null && !this.recFieldIsDim;
        int recIndex = this.buildOrderField(insertSqlAcc, valueSqlAcc, addDotAcc, recField, addRec);
        ColumnModelDefine bizOrderField = this.tableRunInfo.getBizOrderField();
        boolean addBizOrder = bizOrderField != null && !this.bizOrderIsDim;
        int bizOrderIndex = this.buildOrderField(insertSqlAcc, valueSqlAcc, addDotAcc, bizOrderField, addBizOrder);
        insertSqlAcc.append(",").append("MODIFYTIME");
        valueSqlAcc.append(",").append("?");
        Timestamp now = Timestamp.from(Instant.now());
        insertSqlAcc.append(") values (").append((CharSequence)valueSqlAcc).append(")");
        int arrayCountAcc = dimCountAcc + valueCountAcc + (addRec ? 1 : 0) + (addBizOrder ? 1 : 0) + 1;
        ArrayList<Object[]> batchValuesAcc = new ArrayList<Object[]>();
        for (DataRowImpl insertRow : insertRows) {
            UpdateDataRecord insertRecord = null;
            DimensionValueSet rowKeys = null;
            if (this.updateDataTable != null) {
                insertRecord = new UpdateDataRecord();
                rowKeys = new DimensionValueSet();
                insertRecord.setRowkeys(rowKeys);
            }
            Object[] dataValuesAcc = new Object[arrayCountAcc];
            int arrayIndexAcc = 0;
            for (Map.Entry<QueryField, List<Integer>> queryItem : this.accQueryFields.entrySet()) {
                QueryField item = queryItem.getKey();
                List<Integer> columnIndexs = queryItem.getValue();
                Object dataValue = insertRow.internalGetValue(columnIndexs);
                dataValue = this.formatDataForDB(item, dataValue);
                dataValuesAcc[arrayIndexAcc] = item.getDataType() == 33 ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
                ++arrayIndexAcc;
                if (insertRecord == null) continue;
                insertRecord.addData(item.getFieldName(), item.getDataType(), dataValue, null);
            }
            if (insertRecord != null && StringUtils.isNotEmpty((String)curDataTime)) {
                insertRecord.addData("VALIDDATATIME", ColumnModelType.STRING.getValue(), curDataTime, null);
            }
            if (dimensionSet != null) {
                this.initFinder();
                for (ColumnModelDefine dimCol : this.accDimCols) {
                    Object keyValue;
                    String dimension = this.tableRunInfo.getDimensionName(dimCol.getCode());
                    if ("RECORDKEY".equals(dimension)) {
                        keyValue = UUID.randomUUID().toString();
                        map.put(insertRow, (String)keyValue);
                    } else {
                        keyValue = this.getKeyValue(insertRow, dimension);
                    }
                    if (this.accountColumnModelFinder.isBNWDColumn(dimCol.getID())) {
                        keyValue = EmptyDimUtil.validateKeyValue(keyValue, dimCol.getColumnType());
                    }
                    if (keyValue == null || StringUtils.isEmpty((String)keyValue.toString())) {
                        logger.error(String.format("\u7ef4\u5ea6%s\u503c\u4e3a\u7a7a\uff0c\u6267\u884cSQL\uff1a%s", dimension, insertSqlAcc));
                        throw new IncorrectQueryException(String.format("\u63d2\u5165\u6570\u636e\u884c\u65f6\uff0c\u4e1a\u52a1\u4e3b\u952e\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u4e1a\u52a1\u4e3b\u952e\u4e3a%s\uff0c\u7ef4\u5ea6%s\u503c\u4e3a\u7a7a", insertRow.getRowKeys(), dimension));
                    }
                    dataValuesAcc[arrayIndexAcc] = keyValue;
                    ++arrayIndexAcc;
                    if (rowKeys == null) continue;
                    rowKeys.setValue(dimension, keyValue);
                }
                if (StringUtils.isNotEmpty((String)curDataTime)) {
                    dataValuesAcc[arrayIndexAcc++] = curDataTime;
                }
                if (!dimensionSet.contains("RECORDKEY")) {
                    String keyValue = UUID.randomUUID().toString();
                    dataValuesAcc[arrayIndexAcc++] = keyValue;
                    if (rowKeys != null) {
                        rowKeys.setValue("SBID", keyValue);
                    }
                    map.put(insertRow, keyValue);
                }
            }
            int colIndexAcc = dimCountAcc + valueCountAcc;
            if (addRec) {
                this.setOrderValue(colIndexAcc, recField, recIndex, insertRow, dataValuesAcc);
                ++colIndexAcc;
            }
            if (addBizOrder) {
                this.setOrderValue(colIndexAcc, bizOrderField, bizOrderIndex, insertRow, dataValuesAcc);
            }
            if (this.updateDataTable != null) {
                this.updateDataTable.getInsertRecords().add(insertRecord);
            }
            dataValuesAcc[dataValuesAcc.length - 1] = now;
            batchValuesAcc.add(dataValuesAcc);
        }
        if (batchValuesAcc.size() > 0) {
            DataEngineUtil.batchUpdate(this.connection, insertSqlAcc.toString(), batchValuesAcc, monitor);
        }
        return map;
    }

    private void doInsertRptTable(List<DataRowImpl> insertRows, IMonitor monitor, String curDataTime, Map<DataRowImpl, String> sbidMap) throws SQLException, IncorrectQueryException {
        TableModelRunInfo tableRunInfo;
        ColumnModelDefine recField;
        StringBuilder insertSqlRpt = this.initInsertSql(this.accountReportTableName);
        StringBuilder valueSqlRpt = new StringBuilder();
        int valueCountRpt = 0;
        boolean addDotRpt = false;
        for (Map.Entry<QueryField, List<Integer>> queryItem : this.rptQueryFields.entrySet()) {
            QueryField queryField = queryItem.getKey();
            String colName = queryField.getFieldName();
            this.processInsertColumn(insertSqlRpt, valueSqlRpt, colName, addDotRpt);
            addDotRpt = true;
            ++valueCountRpt;
        }
        DimensionSet dimensionSet = this.tableModelRunInfoMap.get(this.accountReportTableName).getDimensions();
        int dimCountRpt = 0;
        if (dimensionSet != null) {
            for (ColumnModelDefine columnModelDefine : this.rptDimCols) {
                String colName = columnModelDefine.getName();
                this.processInsertColumn(insertSqlRpt, valueSqlRpt, colName, addDotRpt);
                addDotRpt = true;
                ++dimCountRpt;
            }
            if (StringUtils.isNotEmpty((String)curDataTime)) {
                this.processInsertColumn(insertSqlRpt, valueSqlRpt, "DATATIME", addDotRpt);
                addDotRpt = true;
                ++dimCountRpt;
            }
            if (!dimensionSet.contains("RECORDKEY")) {
                this.processInsertColumn(insertSqlRpt, valueSqlRpt, "SBID", addDotRpt);
                addDotRpt = true;
                ++dimCountRpt;
            }
        }
        boolean addRec = (recField = (tableRunInfo = this.tableModelRunInfoMap.get(this.accountReportTableName)).getRecField()) != null && !this.recFieldIsDim;
        int recIndex = this.buildOrderField(insertSqlRpt, valueSqlRpt, addDotRpt, recField, addRec);
        ColumnModelDefine bizOrderField = tableRunInfo.getBizOrderField();
        boolean addBizOrder = bizOrderField != null && !this.bizOrderIsDim;
        int bizOrderIndex = this.buildOrderField(insertSqlRpt, valueSqlRpt, addDotRpt, bizOrderField, addBizOrder);
        insertSqlRpt.append(") values (").append((CharSequence)valueSqlRpt).append(")");
        int arrayCountRpt = dimCountRpt + valueCountRpt + (addRec ? 1 : 0) + (addBizOrder ? 1 : 0);
        ArrayList<Object[]> batchValuesRpt = new ArrayList<Object[]>();
        int rowIndex = 0;
        for (DataRowImpl insertRow : insertRows) {
            UpdateDataRecord insertRecord = null;
            DimensionValueSet rowKeys = null;
            if (this.updateDataTable != null) {
                insertRecord = new UpdateDataRecord();
                rowKeys = new DimensionValueSet();
                insertRecord.setRowkeys(rowKeys);
            }
            Object[] dataValuesRpt = new Object[arrayCountRpt];
            int arrayIndexRpt = 0;
            for (Map.Entry<QueryField, List<Integer>> queryItem : this.rptQueryFields.entrySet()) {
                QueryField item = queryItem.getKey();
                List<Integer> columnIndexs = queryItem.getValue();
                Object dataValue = insertRow.internalGetValue(columnIndexs);
                dataValue = this.formatDataForDB(item, dataValue);
                dataValuesRpt[arrayIndexRpt] = item.getDataType() == 33 ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
                ++arrayIndexRpt;
                if (insertRecord == null) continue;
                insertRecord.addData(item.getFieldName(), item.getDataType(), dataValue, null);
            }
            if (dimensionSet != null) {
                for (ColumnModelDefine dimCol : this.rptDimCols) {
                    String dimension = tableRunInfo.getDimensionName(dimCol.getCode());
                    Object keyValue = "RECORDKEY".equals(dimension) ? sbidMap.get(insertRow) : this.getKeyValue(insertRow, dimension);
                    if (keyValue == null) {
                        logger.error(String.format("\u7ef4\u5ea6%s\u503c\u4e3a\u7a7a\uff0c\u6267\u884cSQL\uff1a%s", dimension, insertSqlRpt));
                        throw new IncorrectQueryException(String.format("\u63d2\u5165\u6570\u636e\u884c\u65f6\uff0c\u4e1a\u52a1\u4e3b\u952e\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u4e1a\u52a1\u4e3b\u952e\u4e3a%s\uff0c\u7ef4\u5ea6%s\u503c\u4e3a\u7a7a", insertRow.getRowKeys(), dimension));
                    }
                    dataValuesRpt[arrayIndexRpt] = keyValue;
                    ++arrayIndexRpt;
                    if (rowKeys == null) continue;
                    rowKeys.setValue(dimension, keyValue);
                }
                if (StringUtils.isNotEmpty((String)curDataTime)) {
                    dataValuesRpt[arrayIndexRpt++] = curDataTime;
                    if (rowKeys != null) {
                        rowKeys.setValue("DATATIME", curDataTime);
                    }
                }
                if (!dimensionSet.contains("RECORDKEY")) {
                    String keyValue = sbidMap.get(insertRow);
                    dataValuesRpt[arrayIndexRpt++] = keyValue;
                    if (rowKeys != null) {
                        rowKeys.setValue("SBID", keyValue);
                    }
                }
            }
            int colIndexRpt = dimCountRpt + valueCountRpt;
            if (addRec) {
                this.setOrderValue(colIndexRpt, recField, recIndex, insertRow, dataValuesRpt);
                ++colIndexRpt;
            }
            if (addBizOrder) {
                this.setOrderValue(colIndexRpt, bizOrderField, bizOrderIndex, insertRow, dataValuesRpt);
            }
            if (this.updateDataTable != null) {
                this.updateDataTable.getInsertRecords().add(insertRecord);
            }
            batchValuesRpt.add(dataValuesRpt);
            ++rowIndex;
        }
        if (batchValuesRpt.size() > 0) {
            DataEngineUtil.batchUpdate(this.connection, insertSqlRpt.toString(), batchValuesRpt, monitor);
        }
    }

    private StringBuilder initInsertSql(String tableName) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("insert into ");
        insertSql.append(tableName);
        insertSql.append(" (");
        return insertSql;
    }

    private StringBuilder initDeleteSql(String tableName) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from ");
        deleteSql.append(tableName);
        return deleteSql;
    }

    private StringBuilder initUpdateSql(String tableName) {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("update ");
        updateSql.append(tableName);
        updateSql.append(" set ");
        return updateSql;
    }

    private void processInsertColumn(StringBuilder insertSql, StringBuilder valueSql, String colName, boolean addDot) {
        if (addDot) {
            insertSql.append(",");
            valueSql.append(",");
        }
        insertSql.append(colName);
        valueSql.append("?");
    }

    private ColumnModelDefine findAccountColumnByCode(String code) {
        TableModelDefine table = this.dataModelService.getTableModelDefineByName(this.accountTableName);
        if (table != null) {
            return this.dataModelService.getColumnModelDefineByCode(table.getID(), code);
        }
        return null;
    }

    private ColumnModelDefine findAccountRpColumnByCode(String code) {
        TableModelDefine table = this.dataModelService.getTableModelDefineByName(this.accountReportTableName);
        if (table != null) {
            return this.dataModelService.getColumnModelDefineByCode(table.getID(), code);
        }
        return null;
    }

    /*
     * WARNING - void declaration
     */
    private void doDeleteAllTeAccTable(DimensionValueSet masterKeys, String rowFilter, HashMap<QueryField, ArrayList<Object>> colFilterValues, DimensionValueSet deleteKeys, String curDataTime, IMonitor monitor) throws Exception {
        ArrayList<void> arrayList;
        StringBuilder conditionSqlForHis = new StringBuilder();
        StringBuilder deleteSql = this.initDeleteSql(this.accountTableName);
        int whereCount = 0;
        DimensionSet openDimensions = this.queryTable.getOpenDimensions();
        DimensionValueSet dimensionRestriction = this.queryTable.getDimensionRestriction();
        DimensionValueSet delMasterkeys = new DimensionValueSet();
        ArrayList<Object> argValues = new ArrayList<Object>();
        ArrayList<Integer> dataTypes = new ArrayList<Integer>();
        TableModelRunInfo tableModelRunInfo = this.tableModelRunInfoMap.get(this.accountTableName);
        this.initFinder();
        for (ColumnModelDefine accDimCol : this.accDimCols) {
            void var19_20;
            Object rv;
            void var19_23;
            if (this.accountColumnModelFinder.isBNWDColumn(accDimCol.getID())) continue;
            String dimension = tableModelRunInfo.getDimensionName(accDimCol.getCode());
            Object object = masterKeys.getValue(dimension);
            if (object == null && deleteKeys.hasValue(dimension)) {
                Object object2 = deleteKeys.getValue(dimension);
            }
            if (openDimensions.contains(dimension) && var19_23 == null) {
                String fieldCode = accDimCol.getCode();
                if (!fieldCode.equals("MDCODE") && !fieldCode.equals("DATATIME") && !fieldCode.equals("DW")) continue;
                throw new OperateRowException("\u7f3a\u5931\u4e86\u4e3b\u7ef4\u5ea6\u6216\u65f6\u671f\u6761\u4ef6,\u4e0d\u5141\u8bb8\u6267\u884c\u5220\u9664\uff01");
            }
            whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
            if (dimensionRestriction != null && (rv = dimensionRestriction.getValue(dimension)) != null) {
                Object object3 = rv;
            }
            if (this.updateDataTable != null) {
                delMasterkeys.setValue(dimension, var19_20);
            }
            deleteSql.append(accDimCol.getName()).append("=?");
            conditionSqlForHis.append(" and ").append(accDimCol.getName()).append("=?");
            int dataType = DataTypesConvert.fieldTypeToDataType(accDimCol.getColumnType());
            dataTypes.add(dataType);
            if (!(var19_20 instanceof List)) {
                arrayList = new ArrayList<void>();
                arrayList.add(var19_20);
                argValues.add(arrayList);
                continue;
            }
            argValues.add(var19_20);
        }
        long descartesCount = this.getDescartesCount(argValues);
        if (descartesCount > 10000L) {
            this.deleteRowsByTempTable(true, curDataTime, delMasterkeys, rowFilter, colFilterValues, monitor, deleteKeys);
        } else {
            if (!StringUtils.isEmpty((String)rowFilter)) {
                whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                deleteSql.append("(").append(rowFilter).append(")");
                conditionSqlForHis.append(" and ").append("(").append(rowFilter).append(")");
            }
            if (colFilterValues != null && colFilterValues.size() > 0) {
                for (Map.Entry entry : colFilterValues.entrySet()) {
                    QueryField queryField = (QueryField)entry.getKey();
                    arrayList = (ArrayList<void>)entry.getValue();
                    if (!this.accQueryFields.containsKey(queryField) || arrayList.size() <= 0) continue;
                    whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                    deleteSql.append(queryField.getFieldName()).append("=?");
                    conditionSqlForHis.append(" and ").append(queryField.getFieldName()).append("=?");
                    dataTypes.add(queryField.getDataType());
                    argValues.add(arrayList);
                }
            }
            if (StringUtils.isNotEmpty((String)curDataTime)) {
                whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                deleteSql.append("VALIDDATATIME<=").append("'").append(curDataTime).append("' ");
            }
            if (whereCount <= 0) {
                if (this.tableRunInfo.getVersionField() == null && masterKeys.hasValue("VERSIONID")) {
                    return;
                }
                logger.warn("\u6267\u884c\u6e05\u7a7a\u5168\u8868\uff0cSQL\uff1a" + deleteSql);
            }
            if (delMasterkeys.size() > 0) {
                UpdateDataRecord delRecord = new UpdateDataRecord();
                delRecord.setRowkeys(delMasterkeys);
                this.updateDataTable.getDeleteRecords().add(delRecord);
            }
            ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
            this.descartes(argValues, batchArgs, 0, new ArrayList<Object>(), dataTypes);
            if (batchArgs.size() > 0) {
                if (batchArgs.size() == 1) {
                    this.doCopyData2HisTable(false, curDataTime, conditionSqlForHis, (Object[])batchArgs.get(0), null, monitor);
                    DataEngineUtil.executeUpdate(this.connection, deleteSql.toString(), (Object[])batchArgs.get(0), monitor);
                } else {
                    this.doCopyData2HisTable(true, curDataTime, conditionSqlForHis, new Object[0], batchArgs, monitor);
                    DataEngineUtil.batchUpdate(this.connection, deleteSql.toString(), batchArgs, monitor);
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private void doDeleteAllRptTable(DimensionValueSet masterKeys, String rowFilter, HashMap<QueryField, ArrayList<Object>> colFilterValues, DimensionValueSet deleteKeys, String curDataTime, IMonitor monitor) throws Exception {
        ArrayList<void> arrayList;
        StringBuilder deleteSql = this.initDeleteSql(this.accountReportTableName);
        int whereCount = 0;
        DimensionSet openDimensions = this.queryTable.getOpenDimensions();
        DimensionValueSet dimensionRestriction = this.queryTable.getDimensionRestriction();
        DimensionValueSet delMasterkeys = new DimensionValueSet();
        ArrayList<Object> argValues = new ArrayList<Object>();
        ArrayList<Integer> dataTypes = new ArrayList<Integer>();
        TableModelRunInfo tableModelRunInfo = this.tableModelRunInfoMap.get(this.accountReportTableName);
        for (ColumnModelDefine rptDimCol : this.rptDimCols) {
            void var18_19;
            Object rv;
            void var18_22;
            String dimension = tableModelRunInfo.getDimensionName(rptDimCol.getCode());
            if (!masterKeys.hasValue(dimension)) continue;
            Object object = masterKeys.getValue(dimension);
            if (object == null && deleteKeys.hasValue(dimension)) {
                Object object2 = deleteKeys.getValue(dimension);
            }
            if (openDimensions.contains(dimension) && var18_22 == null) {
                String fieldCode = rptDimCol.getCode();
                if (!fieldCode.equals("MDCODE") && !fieldCode.equals("DATATIME") && !fieldCode.equals("DW")) continue;
                throw new OperateRowException("\u7f3a\u5931\u4e86\u4e3b\u7ef4\u5ea6\u6216\u65f6\u671f\u6761\u4ef6,\u4e0d\u5141\u8bb8\u6267\u884c\u5220\u9664\uff01");
            }
            whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
            if (dimensionRestriction != null && (rv = dimensionRestriction.getValue(dimension)) != null) {
                Object object3 = rv;
            }
            if (this.updateDataTable != null) {
                delMasterkeys.setValue(dimension, var18_19);
            }
            deleteSql.append(rptDimCol.getName()).append("=?");
            int dataType = DataTypesConvert.fieldTypeToDataType(rptDimCol.getColumnType());
            dataTypes.add(dataType);
            if (!(var18_19 instanceof List)) {
                arrayList = new ArrayList<void>();
                arrayList.add(var18_19);
                argValues.add(arrayList);
                continue;
            }
            argValues.add(var18_19);
        }
        long descartCount = this.getDescartesCount(argValues);
        if (descartCount > 10000L) {
            this.deleteRowsByTempTable(false, curDataTime, delMasterkeys, rowFilter, colFilterValues, monitor, deleteKeys);
        } else {
            if (!StringUtils.isEmpty((String)rowFilter)) {
                whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                deleteSql.append("(").append(rowFilter).append(")");
            }
            if (colFilterValues != null && colFilterValues.size() > 0) {
                for (Map.Entry entry : colFilterValues.entrySet()) {
                    QueryField queryField = (QueryField)entry.getKey();
                    arrayList = (ArrayList<void>)entry.getValue();
                    if (!this.rptQueryFields.containsKey(queryField) || arrayList.size() <= 0) continue;
                    whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                    deleteSql.append(queryField.getFieldName()).append("=?");
                    dataTypes.add(queryField.getDataType());
                    argValues.add(arrayList);
                }
            }
            if (StringUtils.isNotEmpty((String)curDataTime)) {
                whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                deleteSql.append("DATATIME=").append("'").append(curDataTime).append("' ");
            }
            if (whereCount <= 0) {
                if (this.tableRunInfo.getVersionField() == null && masterKeys.hasValue("VERSIONID")) {
                    return;
                }
                logger.warn("\u6267\u884c\u6e05\u7a7a\u5168\u8868\uff0cSQL\uff1a" + deleteSql);
            }
            if (delMasterkeys.size() > 0) {
                UpdateDataRecord delRecord = new UpdateDataRecord();
                delRecord.setRowkeys(delMasterkeys);
                this.updateDataTable.getDeleteRecords().add(delRecord);
            }
            ArrayList<Object[]> batchArgs = new ArrayList<Object[]>();
            this.descartes(argValues, batchArgs, 0, new ArrayList<Object>(), dataTypes);
            if (batchArgs.size() > 0) {
                if (batchArgs.size() == 1) {
                    DataEngineUtil.executeUpdate(this.connection, deleteSql.toString(), (Object[])batchArgs.get(0), monitor);
                } else {
                    DataEngineUtil.batchUpdate(this.connection, deleteSql.toString(), batchArgs, monitor);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void deleteRowsByTempTable(boolean acc, String curDataTime, DimensionValueSet masterKeys, String rowFilter, HashMap<QueryField, ArrayList<Object>> colFilterValues, IMonitor monitor, DimensionValueSet deleteKeys) throws Exception {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from ");
        deleteSql.append(acc ? this.accountTableName : this.accountReportTableName);
        StringBuilder conditionSqlForHis = new StringBuilder();
        int whereCount = 0;
        DimensionSet openDimensions = this.queryTable.getOpenDimensions();
        DimensionValueSet dimensionRestriction = this.queryTable.getDimensionRestriction();
        DimensionValueSet delMasterkeys = new DimensionValueSet();
        ArrayList<Object> argValues = new ArrayList<Object>();
        TableModelRunInfo tableModelRunInfo = acc ? this.tableModelRunInfoMap.get(this.accountTableName) : this.tableModelRunInfoMap.get(this.accountReportTableName);
        List<ColumnModelDefine> dimFields = acc ? this.accDimCols : this.rptDimCols;
        ArrayList<String> tempAssistantTables = new ArrayList<String>();
        this.initFinder();
        try {
            for (ColumnModelDefine columnModelDefine : dimFields) {
                ArrayList<Object> listValue;
                Object rv;
                if (this.accountColumnModelFinder.isBNWDColumn(columnModelDefine.getID())) continue;
                String dimension = tableModelRunInfo.getDimensionName(columnModelDefine.getCode());
                Object argValue = masterKeys.getValue(dimension);
                if (argValue == null && deleteKeys.hasValue(dimension)) {
                    argValue = deleteKeys.getValue(dimension);
                }
                if (openDimensions.contains(dimension) && argValue == null) {
                    String fieldCode = columnModelDefine.getCode();
                    if (!fieldCode.equals("MDCODE") && !fieldCode.equals("DATATIME") && !fieldCode.equals("DW")) continue;
                    throw new OperateRowException("\u7f3a\u5931\u4e86\u4e3b\u7ef4\u5ea6\u6216\u65f6\u671f\u6761\u4ef6,\u4e0d\u5141\u8bb8\u6267\u884c\u5220\u9664\uff01");
                }
                TempAssistantTable tempAssistantTable = null;
                if (dimensionRestriction != null && (rv = dimensionRestriction.getValue(dimension)) != null) {
                    argValue = rv;
                }
                if (this.updateDataTable != null) {
                    delMasterkeys.setValue(dimension, argValue);
                }
                int dataType = DataTypesConvert.fieldTypeToDataType(columnModelDefine.getColumnType());
                if (!(argValue instanceof List)) {
                    ArrayList<Object> arrayList = new ArrayList<Object>();
                    arrayList.add(argValue);
                    listValue = arrayList;
                } else {
                    listValue = (ArrayList<Object>)argValue;
                    tempAssistantTable = this.qContext.getTempAssistantTable(dimension, listValue, dataType);
                    if (tempAssistantTable != null) {
                        tempAssistantTables.add(dimension);
                    }
                }
                whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                StringBuilder conditionSql = new StringBuilder();
                this.appendWhereCondition(conditionSql, listValue, argValues, columnModelDefine.getName(), dataType, tempAssistantTable);
                deleteSql.append((CharSequence)conditionSql);
                conditionSql.append(" and ").append((CharSequence)conditionSql);
            }
            if (!StringUtils.isEmpty((String)rowFilter)) {
                whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                deleteSql.append("(").append(rowFilter).append(")");
                conditionSqlForHis.append(" and ").append("(").append(rowFilter).append(")");
            }
            if (colFilterValues != null && colFilterValues.size() > 0) {
                for (Map.Entry entry : colFilterValues.entrySet()) {
                    QueryField queryField = (QueryField)entry.getKey();
                    ArrayList arrayList = (ArrayList)entry.getValue();
                    if (!this.queryFields.containsKey(queryField) || arrayList.size() <= 0) continue;
                    whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                    StringBuilder conditionSql = new StringBuilder();
                    this.appendWhereCondition(conditionSql, arrayList, argValues, queryField.getFieldName(), queryField.getDataType(), null);
                    deleteSql.append((CharSequence)conditionSql);
                    conditionSql.append(" and ").append((CharSequence)conditionSql);
                }
            }
            if (StringUtils.isNotEmpty((String)curDataTime)) {
                whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                if (acc) {
                    deleteSql.append("VALIDDATATIME<=").append("'").append(curDataTime).append("' ");
                } else {
                    deleteSql.append("DATATIME=").append("'").append(curDataTime).append("' ");
                }
            }
            if (whereCount <= 0) {
                if (this.tableRunInfo.getVersionField() == null && masterKeys.hasValue("VERSIONID")) {
                    return;
                }
                logger.warn("\u6267\u884c\u6e05\u7a7a\u5168\u8868\uff0cSQL\uff1a" + deleteSql);
            }
            if (delMasterkeys.size() > 0) {
                UpdateDataRecord delRecord = new UpdateDataRecord();
                delRecord.setRowkeys(delMasterkeys);
                this.updateDataTable.getDeleteRecords().add(delRecord);
            }
            Connection connection = this.getConnection();
            if (acc) {
                this.doCopyData2HisTable(false, curDataTime, conditionSqlForHis, argValues.toArray(), null, monitor);
            }
            DataEngineUtil.executeUpdate(connection, deleteSql.toString(), argValues.toArray(), monitor);
        }
        finally {
            for (String string : tempAssistantTables) {
                this.qContext.getTempResource().dropTempTable(string);
            }
        }
    }

    private void appendWhereCondition(StringBuilder sqlBuilder, List<?> paramValue, List<Object> argValues, String fieldName, int dataType, TempAssistantTable tempAssistantTable) {
        int maxInSize = DataEngineUtil.getMaxInSize(this.qContext.getQueryParam().getDatabase());
        if (paramValue.size() > 1) {
            if (paramValue.size() >= maxInSize) {
                if (tempAssistantTable != null) {
                    sqlBuilder.append(fieldName).append(" in ").append("(").append(tempAssistantTable.getSelectSql()).append(")");
                } else {
                    this.printSplitedInSQL(sqlBuilder, fieldName, paramValue, argValues, dataType);
                }
            } else {
                this.printInSQL(sqlBuilder, fieldName, paramValue, argValues, dataType);
            }
        } else {
            sqlBuilder.append(fieldName);
            sqlBuilder.append("=");
            this.appendValue(sqlBuilder, paramValue.get(0), argValues, dataType);
        }
    }

    private void printSplitedInSQL(StringBuilder buffer, String fieldName, List<?> valueList, List<Object> argValues, int dataType) {
        int maxInSize = 1000;
        ArrayList subValues = new ArrayList(maxInSize);
        buffer.append('(');
        boolean started = false;
        for (Object val : valueList) {
            subValues.add(val);
            if (subValues.size() < maxInSize) continue;
            if (started) {
                buffer.append(" OR ");
            } else {
                started = true;
            }
            this.printInSQL(buffer, fieldName, subValues, argValues, dataType);
            subValues.clear();
        }
        if (!subValues.isEmpty()) {
            if (started) {
                buffer.append(" OR ");
            } else {
                started = true;
            }
            this.printInSQL(buffer, fieldName, subValues, argValues, dataType);
            subValues.clear();
        }
        buffer.append(')');
    }

    private void printInSQL(StringBuilder buffer, String fieldName, List<?> valueList, List<Object> argValues, int dataType) {
        buffer.append(fieldName);
        buffer.append(" in (");
        for (Object value : valueList) {
            this.appendValue(buffer, value, argValues, dataType);
            buffer.append(",");
        }
        buffer.setLength(buffer.length() - 1);
        buffer.append(")");
    }

    @Override
    protected void appendValue(StringBuilder sql, Object value, List<Object> argValues, int dataType) {
        Object argValue = value;
        if (dataType == 6) {
            argValue = value.toString();
        } else if (dataType == 33) {
            argValue = Convert.toUUID((Object)value);
        } else if (dataType == 5 || dataType == 2) {
            argValue = Convert.toDate((Object)value);
        } else if (dataType == 4) {
            argValue = Convert.toInt((Object)value);
        }
        sql.append("?");
        argValues.add(argValue);
    }

    private void doDeleTeAccTable(List<DataRowImpl> deleteRows, String curDataTime, IMonitor monitor, Map<DataRowImpl, String> validDataTimeMap) throws SQLException {
        boolean hasRecField;
        StringBuilder conditionSqlForHis = new StringBuilder();
        StringBuilder deleteSql = this.initDeleteSql(this.accountTableName);
        deleteSql.append(" where ");
        int whereCount = 0;
        int recIndex = -1;
        ColumnModelDefine recField = this.tableRunInfo.getRecField();
        if (Objects.nonNull(recField)) {
            FieldDefine field = this.getFieldDefine(recField);
            recIndex = !this.recFieldIsDim && field != null ? this.fieldsInfoImpl.indexOf(field) : -1;
        }
        boolean bl = hasRecField = recField != null && recIndex >= 0;
        if (hasRecField) {
            deleteSql.append(recField.getName());
            deleteSql.append("=");
            deleteSql.append("?");
            ++whereCount;
            conditionSqlForHis.append(" and ").append("t.").append(recField.getName()).append("=").append("?");
        } else {
            for (ColumnModelDefine fieldDefine : this.accDimCols) {
                String columnName = fieldDefine.getName();
                if (whereCount > 0) {
                    deleteSql.append(" and ");
                }
                if (ACCOUNT_PRIMARY_KEY.equals(columnName)) {
                    columnName = "SBID";
                }
                deleteSql.append(columnName);
                deleteSql.append("=");
                deleteSql.append("?");
                ++whereCount;
                conditionSqlForHis.append(" and ").append("t.").append(columnName).append("=").append("?");
            }
        }
        if (whereCount > 0) {
            this.initFinder();
            deleteSql.append(" and VALIDDATATIME<=").append("'").append(curDataTime).append("'");
            int dimCount = this.accDimCols.size();
            ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
            for (DataRowImpl deleteRow : deleteRows) {
                UpdateDataRecord deleteRecord = null;
                DimensionValueSet rowkeys = null;
                if (this.updateDataTable != null) {
                    deleteRecord = new UpdateDataRecord();
                    rowkeys = new DimensionValueSet();
                    deleteRecord.setRowkeys(rowkeys);
                }
                Object[] keyValues = new Object[hasRecField ? 1 : dimCount];
                int arrayIndex = 0;
                if (recField != null && recIndex >= 0) {
                    Object dataValue = deleteRow.internalGetValue(recIndex);
                    keyValues[arrayIndex] = recField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
                } else {
                    for (ColumnModelDefine columnModelDefine : this.accDimCols) {
                        String dimension = this.tableRunInfo.getDimensionName(columnModelDefine.getCode());
                        Object keyValue = this.getOldKeyValue(deleteRow, dimension);
                        if (this.accountColumnModelFinder.isBNWDColumn(columnModelDefine.getID())) {
                            keyValue = EmptyDimUtil.validateKeyValue(keyValue, columnModelDefine.getColumnType());
                        }
                        keyValues[arrayIndex] = columnModelDefine.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                        if (rowkeys != null) {
                            rowkeys.setValue(dimension, keyValue);
                        }
                        ++arrayIndex;
                    }
                }
                if (deleteRecord != null) {
                    for (Map.Entry entry : this.accQueryFields.entrySet()) {
                        QueryField item = (QueryField)entry.getKey();
                        List columnIndexs = (List)entry.getValue();
                        Object dataValue = deleteRow.internalGetValue(columnIndexs);
                        deleteRecord.addData(item.getFieldName(), item.getDataType(), null, dataValue);
                    }
                    deleteRecord.addData("VALIDDATATIME", ColumnModelType.STRING.getValue(), null, validDataTimeMap.get(deleteRow));
                }
                if (this.updateDataTable != null) {
                    this.updateDataTable.getDeleteRecords().add(deleteRecord);
                }
                batchValues.add(keyValues);
            }
            if (batchValues.size() > 0) {
                this.doCopyData2HisTable(true, curDataTime, conditionSqlForHis, new Object[0], batchValues, monitor);
                DataEngineUtil.batchUpdate(this.connection, deleteSql.toString(), batchValues, monitor);
            }
        }
    }

    private void doDeleteRptTable(List<DataRowImpl> deleteRows, String curDataTime, IMonitor monitor, Map<DataRowImpl, String> sbidMap) throws SQLException {
        StringBuilder deleteSql = this.initDeleteSql(this.accountReportTableName);
        deleteSql.append(" where ");
        boolean whereCount = false;
        deleteSql.append("SBID");
        deleteSql.append("=");
        deleteSql.append("?");
        deleteSql.append(" and DATATIME=").append("'").append(curDataTime).append("' ");
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (DataRowImpl deleteRow : deleteRows) {
            UpdateDataRecord deleteRecord = null;
            DimensionValueSet rowkeys = null;
            if (this.updateDataTable != null) {
                deleteRecord = new UpdateDataRecord();
                rowkeys = new DimensionValueSet();
                deleteRecord.setRowkeys(rowkeys);
            }
            Object[] keyValues = new Object[]{sbidMap.get(deleteRow)};
            if (deleteRecord != null) {
                for (Map.Entry queryItem : this.queryFields.entrySet()) {
                    QueryField item = (QueryField)queryItem.getKey();
                    List columnIndexs = (List)queryItem.getValue();
                    Object dataValue = deleteRow.internalGetValue(columnIndexs);
                    deleteRecord.addData(item.getFieldName(), item.getDataType(), null, dataValue);
                }
            }
            if (this.updateDataTable != null) {
                this.updateDataTable.getDeleteRecords().add(deleteRecord);
            }
            batchValues.add(keyValues);
        }
        if (batchValues.size() > 0) {
            DataEngineUtil.batchUpdate(this.connection, deleteSql.toString(), batchValues, monitor);
        }
    }

    private void doBatchUpdateAccTable(List<DataRowImpl> updateRows, String curDataTime, boolean versionWork, IMonitor monitor, Map<DataRowImpl, String> validDataTimeMap) throws SQLException {
        StringBuilder conditionSqlForHis = new StringBuilder();
        ArrayList<String> setFields = new ArrayList<String>();
        for (Map.Entry<QueryField, List<Integer>> queryItem : this.accQueryFields.entrySet()) {
            QueryField queryField = queryItem.getKey();
            setFields.add(queryField.getFieldName() + "=?");
        }
        if (this.canModifyKey) {
            for (ColumnModelDefine accDimCol : this.accDimCols) {
                setFields.add(accDimCol.getName() + "=?");
            }
        }
        if (versionWork) {
            setFields.add("VALIDDATATIME=?");
        }
        setFields.add("MODIFYTIME=?");
        int recIndex = -1;
        ColumnModelDefine recField = this.tableRunInfo.getRecField();
        if (Objects.nonNull(recField)) {
            FieldDefine field = this.getFieldDefine(recField);
            recIndex = !this.recFieldIsDim && field != null ? this.fieldsInfoImpl.indexOf(field) : -1;
        }
        boolean hasRecField = recField != null && recIndex >= 0;
        ArrayList<String> whereFields = new ArrayList<String>();
        if (hasRecField) {
            whereFields.add(recField.getName() + "=?");
            conditionSqlForHis.append(" and ").append(recField.getName()).append("=").append("?");
        } else {
            for (ColumnModelDefine fieldDefine : this.accDimCols) {
                whereFields.add(fieldDefine.getName() + "=?");
                conditionSqlForHis.append(" and ").append(fieldDefine.getName()).append("=").append("?");
            }
        }
        if (!whereFields.isEmpty()) {
            this.initFinder();
            StringBuilder updateSql = this.initUpdateSql(this.accountTableName);
            updateSql.append(String.join((CharSequence)",", setFields));
            updateSql.append(" where ");
            whereFields.add("VALIDDATATIME<=?");
            updateSql.append(String.join((CharSequence)" and ", whereFields));
            int arrayCount = setFields.size() + whereFields.size();
            Timestamp now = Timestamp.from(Instant.now());
            ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
            ArrayList<Object[]> conBatchValues = new ArrayList<Object[]>();
            for (DataRowImpl updateRow : updateRows) {
                Object keyValue;
                String dimension;
                UpdateDataRecord updateRecord = null;
                DimensionValueSet rowkeys = null;
                if (this.updateDataTable != null) {
                    updateRecord = new UpdateDataRecord();
                    rowkeys = new DimensionValueSet();
                    updateRecord.setRowkeys(rowkeys);
                }
                Object[] dataValues = new Object[arrayCount];
                Object[] conValues = new Object[whereFields.size() - 1];
                int arrayIndex = 0;
                int conIndex = 0;
                for (Map.Entry<QueryField, List<Integer>> queryItem : this.accQueryFields.entrySet()) {
                    QueryField item = queryItem.getKey();
                    List<Integer> columnIndexs = queryItem.getValue();
                    Object dataValue = updateRow.internalGetValue(columnIndexs);
                    dataValue = this.formatDataForDB(item, dataValue);
                    dataValues[arrayIndex++] = item.getDataType() == 33 ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
                    if (updateRecord == null) continue;
                    updateRecord.addData(item.getFieldName(), item.getDataType(), dataValue, updateRow.internalGetOldValue(columnIndexs));
                }
                for (ColumnModelDefine accDimCol : this.accDimCols) {
                    dimension = this.tableRunInfo.getDimensionName(accDimCol.getCode());
                    keyValue = this.getKeyValue(updateRow, dimension);
                    if (this.accountColumnModelFinder.isBNWDColumn(accDimCol.getID())) {
                        keyValue = EmptyDimUtil.validateKeyValue(keyValue, accDimCol.getColumnType());
                    }
                    if (this.canModifyKey) {
                        dataValues[arrayIndex++] = accDimCol.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                        if (updateRecord != null) {
                            updateRecord.addData(accDimCol.getName(), ColumnModelType.STRING.getValue(), keyValue, null);
                        }
                    }
                    if (rowkeys == null) continue;
                    rowkeys.setValue(dimension, keyValue);
                }
                if (versionWork) {
                    dataValues[arrayIndex++] = curDataTime;
                }
                dataValues[arrayIndex++] = now;
                if (updateRecord != null) {
                    String oldValue = validDataTimeMap.get(updateRow);
                    if (versionWork) {
                        updateRecord.addData("VALIDDATATIME", ColumnModelType.STRING.getValue(), curDataTime, oldValue);
                    } else {
                        updateRecord.addData("VALIDDATATIME", ColumnModelType.STRING.getValue(), oldValue, oldValue);
                    }
                }
                if (hasRecField) {
                    Object dataValue = updateRow.internalGetValue(recIndex);
                    if (recField.getColumnType() == ColumnModelType.UUID) {
                        dataValue = DataEngineConsts.toBytes(dataValue);
                    }
                    conValues[conIndex] = dataValue;
                    dataValues[arrayIndex++] = dataValue;
                } else {
                    for (ColumnModelDefine dimField : this.accDimCols) {
                        dimension = this.tableRunInfo.getDimensionName(dimField.getCode());
                        keyValue = this.getOldKeyValue(updateRow, dimension);
                        if (this.accountColumnModelFinder.isBNWDColumn(dimField.getID())) {
                            keyValue = EmptyDimUtil.validateKeyValue(keyValue, dimField.getColumnType());
                        }
                        if (dimField.getColumnType() == ColumnModelType.UUID) {
                            keyValue = DataEngineConsts.toBytes(keyValue);
                        }
                        dataValues[arrayIndex++] = keyValue;
                        if (rowkeys != null) {
                            rowkeys.setValue(dimension, keyValue);
                        }
                        conValues[conIndex++] = keyValue;
                    }
                }
                if (this.updateDataTable != null) {
                    this.updateDataTable.getUpdateRecords().put(rowkeys, updateRecord);
                }
                dataValues[arrayIndex] = curDataTime;
                batchValues.add(dataValues);
                conBatchValues.add(conValues);
            }
            if (!batchValues.isEmpty()) {
                if (versionWork) {
                    this.doCopyData2HisTable(true, curDataTime, conditionSqlForHis, new Object[0], conBatchValues, monitor);
                }
                DataEngineUtil.batchUpdate(this.connection, updateSql.toString(), batchValues, monitor);
            }
        }
    }

    private void doBatchUpdateRptTable(List<DataRowImpl> updateRows, IMonitor monitor, Map<DataRowImpl, String> sbidMap, String curDataTime) throws SQLException {
        StringBuilder updateSql = this.initUpdateSql(this.accountReportTableName);
        boolean addDot = false;
        int valueCount = this.rptQueryFields.size();
        for (Map.Entry<QueryField, List<Integer>> queryItem : this.rptQueryFields.entrySet()) {
            QueryField queryField = queryItem.getKey();
            if (addDot) {
                updateSql.append(",");
            }
            addDot = true;
            updateSql.append(queryField.getFieldName()).append("=").append("?");
        }
        if (this.canModifyKey) {
            for (ColumnModelDefine fieldDefine : this.rptDimCols) {
                if (addDot) {
                    updateSql.append(",");
                }
                addDot = true;
                updateSql.append(fieldDefine.getName()).append("=").append("?");
            }
        }
        int recIndex = -1;
        ColumnModelDefine recField = this.tableRunInfo.getRecField();
        if (Objects.nonNull(recField)) {
            FieldDefine field = this.getFieldDefine(recField);
            recIndex = !this.recFieldIsDim && field != null ? this.fieldsInfoImpl.indexOf(field) : -1;
        }
        boolean hasRecField = recField != null && recIndex >= 0;
        updateSql.append(" where ");
        if (hasRecField) {
            updateSql.append(recField.getName());
            updateSql.append("=");
            updateSql.append("?");
        } else {
            updateSql.append("SBID").append("=").append("?");
        }
        updateSql.append(" and ").append("DATATIME").append("=").append("'").append(curDataTime).append("'");
        int dimCount = this.rptDimCols.size();
        int arrayCount = (this.canModifyKey ? dimCount : 0) + valueCount + 1;
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (DataRowImpl updateRow : updateRows) {
            UpdateDataRecord updateRecord = null;
            DimensionValueSet rowkeys = null;
            if (this.updateDataTable != null) {
                updateRecord = new UpdateDataRecord();
                rowkeys = new DimensionValueSet();
                updateRecord.setRowkeys(rowkeys);
            }
            Object[] dataValues = new Object[arrayCount];
            int arrayIndex = 0;
            for (Map.Entry<QueryField, List<Integer>> entry : this.rptQueryFields.entrySet()) {
                QueryField item = entry.getKey();
                List<Integer> columnIndexs = entry.getValue();
                Object dataValue = updateRow.internalGetValue(columnIndexs);
                dataValue = this.formatDataForDB(item, dataValue);
                dataValues[arrayIndex] = item.getDataType() == 33 ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
                if (updateRecord != null) {
                    updateRecord.addData(item.getFieldName(), item.getDataType(), dataValue, updateRow.internalGetOldValue(columnIndexs));
                }
                ++arrayIndex;
            }
            TableModelRunInfo rptTableInfo = this.tableModelRunInfoMap.get(this.accountReportTableName);
            for (ColumnModelDefine dimField : this.rptDimCols) {
                Object keyValue;
                String dimension = rptTableInfo.getDimensionName(dimField.getCode());
                if ("SBID".equals(dimField.getName())) {
                    keyValue = sbidMap.get(updateRow);
                    if (dimension == null) {
                        dimension = "SBID";
                    }
                } else {
                    keyValue = this.getKeyValue(updateRow, dimension);
                }
                if (this.canModifyKey) {
                    dataValues[arrayIndex] = dimField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                    ++arrayIndex;
                }
                if (rowkeys == null) continue;
                rowkeys.setValue(dimension, keyValue);
            }
            if (hasRecField) {
                Object object = updateRow.internalGetValue(recIndex);
                dataValues[arrayIndex] = recField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(object) : object;
            } else {
                String string = sbidMap.get(updateRow);
                dataValues[arrayIndex] = string;
                if (rowkeys != null) {
                    rowkeys.setValue("SBID", string);
                }
            }
            if (this.updateDataTable != null) {
                this.updateDataTable.getUpdateRecords().put(rowkeys, updateRecord);
            }
            batchValues.add(dataValues);
        }
        if (batchValues.size() <= 0) {
            return;
        }
        DataEngineUtil.batchUpdate(this.connection, updateSql.toString(), batchValues, monitor);
    }

    private void doUpdateAccTable(DataRowImpl updateRow, List<DataRowImpl> updateRows, String curDataTime, boolean versionWork, IMonitor monitor, Map<DataRowImpl, String> validDataTimeMap) throws SQLException, IncorrectQueryException {
        StringBuilder conditionSqlForHis = new StringBuilder();
        ArrayList<String> setFields = new ArrayList<String>();
        for (Map.Entry<QueryField, List<Integer>> queryItem : this.accQueryFields.entrySet()) {
            QueryField queryField = queryItem.getKey();
            setFields.add(queryField.getFieldName() + "=?");
        }
        if (this.canModifyKey) {
            for (ColumnModelDefine accDimCol : this.accDimCols) {
                setFields.add(accDimCol.getName() + "=?");
            }
        }
        if (versionWork) {
            setFields.add("VALIDDATATIME=?");
        }
        setFields.add("MODIFYTIME=?");
        int recIndex = -1;
        ColumnModelDefine recField = this.tableRunInfo.getRecField();
        if (Objects.nonNull(recField)) {
            FieldDefine field = this.getFieldDefine(recField);
            recIndex = !this.recFieldIsDim && field != null ? this.fieldsInfoImpl.indexOf(field) : -1;
        }
        boolean hasRecField = recField != null && recIndex >= 0;
        ArrayList<String> whereFields = new ArrayList<String>();
        if (hasRecField) {
            whereFields.add(recField.getName() + "=?");
            conditionSqlForHis.append(" and ").append(recField.getName()).append("=").append("?");
        } else {
            for (ColumnModelDefine accDimCol : this.accDimCols) {
                whereFields.add(accDimCol.getName() + "=?");
                conditionSqlForHis.append(" and ").append(accDimCol.getName()).append("=").append("?");
            }
        }
        if (!whereFields.isEmpty()) {
            Object keyValue;
            String dimension;
            this.initFinder();
            StringBuilder updateSql = this.initUpdateSql(this.accountTableName);
            updateSql.append(String.join((CharSequence)",", setFields));
            updateSql.append(" where ");
            whereFields.add("VALIDDATATIME<=?");
            updateSql.append(String.join((CharSequence)" and ", whereFields));
            int arrayCount = setFields.size() + whereFields.size();
            UpdateDataRecord updateRecord = null;
            DimensionValueSet rowKeys = null;
            if (this.updateDataTable != null) {
                updateRecord = new UpdateDataRecord();
                rowKeys = new DimensionValueSet();
                updateRecord.setRowkeys(rowKeys);
            }
            Object[] dataValues = new Object[arrayCount];
            Object[] conValues = new Object[whereFields.size() - 1];
            int arrayIndex = 0;
            int conIndex = 0;
            for (Map.Entry<QueryField, List<Integer>> queryItem : this.accQueryFields.entrySet()) {
                QueryField item = queryItem.getKey();
                List<Integer> columnIndexs = queryItem.getValue();
                Object dataValue = updateRow.internalGetValue(columnIndexs);
                dataValue = this.formatDataForDB(item, dataValue);
                dataValues[arrayIndex] = item.getDataType() == 33 ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
                if (updateRecord != null) {
                    updateRecord.addData(item.getFieldName(), item.getDataType(), dataValue, updateRow.internalGetOldValue(columnIndexs));
                }
                ++arrayIndex;
            }
            for (ColumnModelDefine accDimCol : this.accDimCols) {
                dimension = this.tableRunInfo.getDimensionName(accDimCol.getCode());
                keyValue = this.getKeyValue(updateRow, dimension);
                if (this.accountColumnModelFinder.isBNWDColumn(accDimCol.getID())) {
                    keyValue = EmptyDimUtil.validateKeyValue(keyValue, accDimCol.getColumnType());
                }
                if (this.canModifyKey) {
                    dataValues[arrayIndex] = accDimCol.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                    ++arrayIndex;
                    if (updateRecord != null) {
                        updateRecord.addData(accDimCol.getName(), ColumnModelType.STRING.getValue(), keyValue, null);
                    }
                }
                if (rowKeys == null) continue;
                rowKeys.setValue(dimension, keyValue);
            }
            if (versionWork) {
                dataValues[arrayIndex++] = curDataTime;
            }
            dataValues[arrayIndex++] = Timestamp.from(Instant.now());
            if (updateRecord != null) {
                String oldValue = validDataTimeMap.get(updateRow);
                if (versionWork) {
                    updateRecord.addData("VALIDDATATIME", ColumnModelType.STRING.getValue(), curDataTime, oldValue);
                } else {
                    updateRecord.addData("VALIDDATATIME", ColumnModelType.STRING.getValue(), oldValue, oldValue);
                }
            }
            if (recField != null && recIndex >= 0) {
                Object dataValue = updateRow.internalGetValue(recIndex);
                if (recField.getColumnType() == ColumnModelType.UUID) {
                    dataValue = DataEngineConsts.toBytes(dataValue);
                }
                conValues[conIndex] = dataValue;
                dataValues[arrayIndex++] = dataValue;
            } else {
                for (ColumnModelDefine dimField : this.accDimCols) {
                    dimension = this.tableRunInfo.getDimensionName(dimField.getCode());
                    keyValue = this.getOldKeyValue(updateRow, dimension);
                    if (this.accountColumnModelFinder.isBNWDColumn(dimField.getID())) {
                        keyValue = EmptyDimUtil.validateKeyValue(keyValue, dimField.getColumnType());
                    }
                    if (dimField.getColumnType() == ColumnModelType.UUID) {
                        keyValue = DataEngineConsts.toBytes(keyValue);
                    }
                    dataValues[arrayIndex++] = keyValue;
                    if (rowKeys != null) {
                        rowKeys.setValue(dimension, keyValue);
                    }
                    conValues[conIndex++] = keyValue;
                }
            }
            if (versionWork) {
                this.doCopyData2HisTable(false, curDataTime, conditionSqlForHis, conValues, null, monitor);
            }
            dataValues[arrayIndex] = curDataTime;
            int resultValue = DataEngineUtil.executeUpdate(this.connection, updateSql.toString(), dataValues, monitor);
            if (resultValue > 0) {
                if (this.updateDataTable != null) {
                    this.updateDataTable.getUpdateRecords().put(rowKeys, updateRecord);
                }
            } else {
                this.insertRows(updateRows, monitor);
            }
        }
    }

    private void doUpdateRptTable(DataRowImpl updateRow, List<DataRowImpl> updateRows, IMonitor monitor, Map<DataRowImpl, String> sbidMap, String curDataTime) throws SQLException, IncorrectQueryException {
        StringBuilder updateSql = this.initUpdateSql(this.accountReportTableName);
        boolean addDot = false;
        int valueCount = this.rptQueryFields.size();
        for (Map.Entry<QueryField, List<Integer>> queryItem : this.rptQueryFields.entrySet()) {
            QueryField queryField = queryItem.getKey();
            if (addDot) {
                updateSql.append(",");
            }
            addDot = true;
            updateSql.append(queryField.getFieldName()).append("=").append("?");
        }
        if (this.canModifyKey) {
            for (ColumnModelDefine rptDimCol : this.rptDimCols) {
                if (addDot) {
                    updateSql.append(",");
                }
                addDot = true;
                updateSql.append(rptDimCol.getName()).append("=").append("?");
            }
        }
        int recIndex = -1;
        ColumnModelDefine recField = this.tableRunInfo.getRecField();
        if (Objects.nonNull(recField)) {
            FieldDefine field = this.getFieldDefine(recField);
            recIndex = !this.recFieldIsDim && field != null ? this.fieldsInfoImpl.indexOf(field) : -1;
        }
        boolean hasRecField = recField != null && recIndex >= 0;
        updateSql.append(" where ");
        if (hasRecField) {
            updateSql.append(recField.getName());
            updateSql.append("=");
            updateSql.append("?");
        } else {
            updateSql.append("SBID").append("=").append("?");
        }
        updateSql.append(" and ").append("DATATIME").append("=").append("'").append(curDataTime).append("'");
        int dimCount = this.rptDimCols.size();
        int arrayCount = (this.canModifyKey ? dimCount : 0) + valueCount + 1;
        UpdateDataRecord updateRecord = null;
        DimensionValueSet rowkeys = null;
        if (this.updateDataTable != null) {
            updateRecord = new UpdateDataRecord();
            rowkeys = new DimensionValueSet();
            updateRecord.setRowkeys(rowkeys);
        }
        Object[] dataValues = new Object[arrayCount];
        int arrayIndex = 0;
        for (Map.Entry<QueryField, List<Integer>> entry : this.rptQueryFields.entrySet()) {
            QueryField item = entry.getKey();
            List<Integer> columnIndexs = entry.getValue();
            Object dataValue = updateRow.internalGetValue(columnIndexs);
            dataValue = this.formatDataForDB(item, dataValue);
            dataValues[arrayIndex] = item.getDataType() == 33 ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
            if (updateRecord != null) {
                updateRecord.addData(item.getFieldName(), item.getDataType(), dataValue, updateRow.internalGetOldValue(columnIndexs));
            }
            ++arrayIndex;
        }
        TableModelRunInfo rptTableInfo = this.tableModelRunInfoMap.get(this.accountReportTableName);
        for (ColumnModelDefine rptDimCol : this.rptDimCols) {
            Object keyValue;
            String dimension = rptTableInfo.getDimensionName(rptDimCol.getCode());
            if ("SBID".equals(rptDimCol.getName())) {
                keyValue = sbidMap.get(updateRow);
                if (dimension == null) {
                    dimension = "SBID";
                }
            } else {
                keyValue = this.getKeyValue(updateRow, dimension);
            }
            if (this.canModifyKey) {
                dataValues[arrayIndex] = rptDimCol.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                ++arrayIndex;
            }
            if (rowkeys == null) continue;
            rowkeys.setValue(dimension, keyValue);
        }
        if (hasRecField) {
            Object object = updateRow.internalGetValue(recIndex);
            dataValues[arrayIndex] = recField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(object) : object;
        } else {
            String string = sbidMap.get(updateRow);
            dataValues[arrayIndex] = string;
            if (rowkeys != null) {
                rowkeys.setValue("SBID", string);
            }
        }
        int n = DataEngineUtil.executeUpdate(this.connection, updateSql.toString(), dataValues, monitor);
        if (n > 0) {
            if (this.updateDataTable != null) {
                this.updateDataTable.getUpdateRecords().put(rowkeys, updateRecord);
            }
        } else {
            this.insertRows(updateRows, monitor);
        }
    }

    private void doCopyData2HisTable(boolean ifBatch, String curDataTime, StringBuilder conditionSql, Object[] values, List<Object[]> batchValues, IMonitor monitor) throws SQLException {
        String id = this.dataModelService.getTableModelDefineByName(this.accountTableName).getID();
        List columns = this.dataModelService.getColumnModelDefinesByTable(id);
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ");
        sql.append(this.accountHistoryTableName);
        sql.append(" (INVALIDDATATIME,");
        StringBuilder valSql = new StringBuilder();
        valSql.append(" select ");
        valSql.append("'");
        valSql.append(curDataTime);
        valSql.append("'");
        valSql.append(",");
        for (ColumnModelDefine column : columns) {
            String columnName = column.getName();
            if ("MODIFYTIME".equals(columnName)) continue;
            sql.append(columnName);
            sql.append(",");
            if (ACCOUNT_PRIMARY_KEY.equals(columnName)) {
                String uuidSql = DataEngineUtil.buildcreateUUIDSql(this.queryParam.getDatabase(), true);
                valSql.append(uuidSql);
                valSql.append(",");
                continue;
            }
            valSql.append("t.");
            valSql.append(columnName);
            valSql.append(",");
        }
        sql.setLength(sql.length() - 1);
        sql.append(")");
        valSql.setLength(valSql.length() - 1);
        valSql.append(" from ");
        valSql.append(this.accountTableName);
        valSql.append(" t where t.VALIDDATATIME<");
        valSql.append("'");
        valSql.append(curDataTime);
        valSql.append("'");
        valSql.append((CharSequence)conditionSql);
        String finalSql = sql.append((CharSequence)valSql).toString();
        if (ifBatch) {
            DataEngineUtil.batchUpdate(this.connection, finalSql, batchValues, monitor);
        } else {
            DataEngineUtil.executeUpdate(this.connection, finalSql, values, monitor);
        }
    }

    private FieldDefine getFieldDefine(ColumnModelDefine columnModelDefine) {
        DataModelDefinitionsCache tableCache = null;
        try {
            tableCache = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache();
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return tableCache == null ? null : tableCache.getFieldDefine(columnModelDefine);
    }

    private void insertIfNoRptRow(List<DataRowImpl> insertRows, IMonitor monitor, Map<DataRowImpl, String> sbidMap) throws SQLException, IncorrectQueryException {
        ColumnModelDefine recField;
        List<String> recKeys = DataEngineConsts.getRecKeys(insertRows.size());
        String curDataTime = (String)this.masterKeys.getValue("DATATIME");
        StringBuilder insertSqlRpt = this.initInsertSql(this.accountReportTableName);
        StringBuilder valueSqlRpt = new StringBuilder();
        int valueCountRpt = 0;
        boolean addDotRpt = false;
        for (Map.Entry queryItem : this.queryFields.entrySet()) {
            QueryField queryField = (QueryField)queryItem.getKey();
            String colName = queryField.getFieldName();
            String tableName = queryField.getTableName();
            if (!tableName.equals(this.accountReportTableName)) continue;
            this.processInsertColumn(insertSqlRpt, valueSqlRpt, colName, addDotRpt);
            addDotRpt = true;
            ++valueCountRpt;
        }
        TableModelRunInfo tableRunInfo = this.tableModelRunInfoMap.get(this.accountReportTableName);
        DimensionSet dimensionSet = tableRunInfo.getDimensions();
        int dimCountRpt = 0;
        if (dimensionSet != null) {
            for (int i = 0; i < dimensionSet.size(); ++i) {
                String dimension = dimensionSet.get(i);
                if ("DATATIME".equals(dimension)) continue;
                ColumnModelDefine columnModelDefine = tableRunInfo.getDimensionField(dimension);
                String colName = columnModelDefine.getName();
                this.processInsertColumn(insertSqlRpt, valueSqlRpt, colName, addDotRpt);
                addDotRpt = true;
                ++dimCountRpt;
            }
            if (StringUtils.isNotEmpty((String)curDataTime)) {
                this.processInsertColumn(insertSqlRpt, valueSqlRpt, "DATATIME", addDotRpt);
                addDotRpt = true;
                ++dimCountRpt;
            }
            if (!dimensionSet.contains("RECORDKEY")) {
                this.processInsertColumn(insertSqlRpt, valueSqlRpt, "SBID", addDotRpt);
                addDotRpt = true;
                ++dimCountRpt;
            }
        }
        boolean addRec = (recField = tableRunInfo.getRecField()) != null && !this.recFieldIsDim;
        int recIndex = this.buildOrderField(insertSqlRpt, valueSqlRpt, addDotRpt, recField, addRec);
        ColumnModelDefine bizOrderField = tableRunInfo.getBizOrderField();
        boolean addBizOrder = bizOrderField != null && !this.bizOrderIsDim;
        int bizOrderIndex = this.buildOrderField(insertSqlRpt, valueSqlRpt, addDotRpt, bizOrderField, addBizOrder);
        insertSqlRpt.append(") values (").append((CharSequence)valueSqlRpt).append(")");
        int arrayCountRpt = dimCountRpt + valueCountRpt + (addRec ? 1 : 0) + (addBizOrder ? 1 : 0);
        ArrayList<Object[]> batchValuesRpt = new ArrayList<Object[]>();
        int rowIndex = 0;
        for (DataRowImpl insertRow : insertRows) {
            UpdateDataRecord insertRecord = null;
            DimensionValueSet rowKeys = null;
            if (this.updateDataTable != null) {
                insertRecord = new UpdateDataRecord();
                rowKeys = new DimensionValueSet();
                insertRecord.setRowkeys(rowKeys);
            }
            Object[] dataValuesRpt = new Object[arrayCountRpt];
            int arrayIndexRpt = 0;
            for (Map.Entry queryItem : this.queryFields.entrySet()) {
                QueryField item = (QueryField)queryItem.getKey();
                List columnIndexs = (List)queryItem.getValue();
                Object dataValue = insertRow.internalGetValue(columnIndexs);
                dataValue = this.formatDataForDB(item, dataValue);
                String tableName = item.getTableName();
                if (tableName.equals(this.accountReportTableName)) {
                    dataValuesRpt[arrayIndexRpt] = item.getDataType() == 33 ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
                    ++arrayIndexRpt;
                }
                if (insertRecord == null) continue;
                insertRecord.addData(item.getFieldName(), item.getDataType(), dataValue, null);
            }
            if (dimensionSet != null) {
                for (int index = 0; index < dimensionSet.size(); ++index) {
                    Object keyValue;
                    String dimension = dimensionSet.get(index);
                    if ("DATATIME".equals(dimension)) continue;
                    if ("RECORDKEY".equals(dimension)) {
                        keyValue = sbidMap.get(insertRow);
                    } else {
                        if (ACCOUNT_PRIMARY_KEY.equals(tableRunInfo.getDimensionField(dimension).getName())) {
                            String rptKeyValue = UUID.randomUUID().toString();
                            dataValuesRpt[arrayIndexRpt] = rptKeyValue;
                            ++arrayIndexRpt;
                            continue;
                        }
                        keyValue = this.getKeyValue(insertRow, dimension);
                    }
                    if (keyValue == null) {
                        if ("RECORDKEY".equals(dimension)) {
                            keyValue = recKeys.get(rowIndex);
                        } else {
                            logger.error(String.format("\u7ef4\u5ea6%s\u503c\u4e3a\u7a7a\uff0c\u6267\u884cSQL\uff1a%s", dimension, insertSqlRpt));
                            throw new IncorrectQueryException(String.format("\u63d2\u5165\u6570\u636e\u884c\u65f6\uff0c\u4e1a\u52a1\u4e3b\u952e\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u4e1a\u52a1\u4e3b\u952e\u4e3a%s\uff0c\u7ef4\u5ea6%s\u503c\u4e3a\u7a7a", insertRow.getRowKeys(), dimension));
                        }
                    }
                    dataValuesRpt[arrayIndexRpt] = keyValue;
                    ++arrayIndexRpt;
                    if (rowKeys == null) continue;
                    rowKeys.setValue(dimension, keyValue);
                }
                if (StringUtils.isNotEmpty((String)curDataTime)) {
                    dataValuesRpt[arrayIndexRpt++] = curDataTime;
                    if (rowKeys != null) {
                        rowKeys.setValue("DATATIME", curDataTime);
                    }
                }
                if (!dimensionSet.contains("RECORDKEY")) {
                    String keyValue = sbidMap.get(insertRow);
                    dataValuesRpt[arrayIndexRpt] = keyValue;
                    if (rowKeys != null) {
                        rowKeys.setValue("SBID", keyValue);
                    }
                }
            }
            int colIndexRpt = dimCountRpt + valueCountRpt;
            if (addRec) {
                this.setOrderValue(colIndexRpt, recField, recIndex, insertRow, dataValuesRpt);
                ++colIndexRpt;
            }
            if (addBizOrder) {
                this.setOrderValue(colIndexRpt, bizOrderField, bizOrderIndex, insertRow, dataValuesRpt);
            }
            if (this.updateDataTable != null) {
                this.updateDataTable.getInsertRecords().add(insertRecord);
            }
            batchValuesRpt.add(dataValuesRpt);
            ++rowIndex;
        }
        if (batchValuesRpt.size() > 0) {
            DataEngineUtil.batchUpdate(this.connection, insertSqlRpt.toString(), batchValuesRpt, monitor);
        }
    }

    private void checkAccDuplicateKeys(List<DataRowImpl> dataRows, boolean isInsert) throws IncorrectQueryException, SQLException {
        List<DataRowImpl> changeRows;
        if (!this.needCheckKeys || dataRows.isEmpty()) {
            return;
        }
        this.initFinder();
        TableModelRunInfo tableModelRunInfo = this.tableModelRunInfoMap.get(this.accountTableName);
        DimensionSet rowDims = tableModelRunInfo.getDimensions();
        DimensionSet masterDims = this.masterKeys == null ? new DimensionSet() : this.masterKeys.getDimensionSet();
        DimensionValueSet rowKeys = new DimensionValueSet(dataRows.get(0).getRowKeys());
        DimensionSet rowDimensions = new DimensionSet(rowKeys.getDimensionSet());
        rowDimensions.removeAll(masterDims);
        if (rowDimensions.size() <= 0 || rowDimensions.contains("RECORDKEY") || rowDims.size() <= 1) {
            return;
        }
        List<DataRowImpl> list = changeRows = isInsert ? dataRows : this.getChangeRowKeys(dataRows);
        if (changeRows.size() <= 0) {
            return;
        }
        StringBuilder excuteTemplateSql = new StringBuilder();
        excuteTemplateSql.append("select %s as rowIndex from ").append(tableModelRunInfo.getTableModelDefine().getName());
        excuteTemplateSql.append(" where ");
        boolean addAnd = false;
        List<ColumnModelDefine> dimFields = tableModelRunInfo.getDimFields();
        for (ColumnModelDefine fieldDefine : dimFields) {
            if ("SBID".equals(fieldDefine.getName())) continue;
            if (addAnd) {
                excuteTemplateSql.append(" and ");
            }
            addAnd = true;
            excuteTemplateSql.append(fieldDefine.getName()).append("=?");
        }
        String templateSql = excuteTemplateSql.toString();
        StringBuilder excuteSql = new StringBuilder();
        ArrayList<Object> paramValues = new ArrayList<Object>();
        Iterator<DataRowImpl> iterator = changeRows.iterator();
        int rowIndex = 0;
        ArrayList<DimensionValueSet> duplicateKeys = new ArrayList<DimensionValueSet>();
        while (iterator.hasNext()) {
            DataRowImpl dataRowImpl = iterator.next();
            if (excuteSql.length() > 0) {
                excuteSql.append(" union all ");
            }
            excuteSql.append(String.format(templateSql, rowIndex));
            for (ColumnModelDefine fieldDefine : dimFields) {
                if ("SBID".equals(fieldDefine.getName())) continue;
                String dimName = tableModelRunInfo.getDimensionName(fieldDefine.getCode());
                Object keyValue = this.getKeyValue(dataRowImpl, dimName);
                if (this.accountColumnModelFinder.isBNWDColumn(fieldDefine.getID())) {
                    keyValue = EmptyDimUtil.validateKeyValue(keyValue, fieldDefine.getColumnType());
                }
                if (keyValue == null) {
                    throw new IncorrectRowKeysException(dataRowImpl.getRowKeys(), isInsert, dimName);
                }
                if (fieldDefine.getColumnType() == ColumnModelType.UUID) {
                    keyValue = DataEngineConsts.toBytes(keyValue);
                }
                paramValues.add(keyValue);
            }
            if (rowIndex > 0 && rowIndex % 50 == 0) {
                this.checkBatchDuplicateKeys(isInsert, changeRows, excuteSql, paramValues, duplicateKeys);
                excuteSql.setLength(0);
                paramValues.clear();
            }
            ++rowIndex;
        }
        if (paramValues.size() > 0) {
            this.checkBatchDuplicateKeys(isInsert, changeRows, excuteSql, paramValues, duplicateKeys);
        }
        if (duplicateKeys.size() > 0) {
            throw new DuplicateRowKeysException(duplicateKeys);
        }
    }

    private void checkDimEmpty(List<DataRowImpl> dataRows) throws IncorrectQueryException {
        this.initFinder();
        for (DataRowImpl dataRow : dataRows) {
            int bnwdColCount = 0;
            int emptyDimCount = 0;
            for (ColumnModelDefine accDimCol : this.accDimCols) {
                if (!this.accountColumnModelFinder.isBNWDColumn(accDimCol.getID())) continue;
                ++bnwdColCount;
                String dimension = this.tableModelRunInfoMap.get(this.accountTableName).getDimensionName(accDimCol.getCode());
                Object keyValue = this.getKeyValue(dataRow, dimension);
                if (keyValue != null && !StringUtils.isEmpty((String)keyValue.toString()) && !"-".equals(keyValue.toString()) && !"9999R0001".equals(keyValue.toString())) continue;
                ++emptyDimCount;
            }
            if (emptyDimCount != bnwdColCount) continue;
            throw new IncorrectQueryException("\u53f0\u8d26\u8868\u4fdd\u5b58\u6570\u636e\u884c\u65f6\uff0c\u4e1a\u52a1\u4e3b\u952e\u503c\u4e0d\u80fd\u5168\u4e3a\u7a7a");
        }
    }

    @Override
    public Object getKeyValue(DataRowImpl dataRow, String dimension) {
        PeriodModifier periodModifier;
        Object keyValue;
        Object dimValue;
        DimensionValueSet dimensionRestriction = this.queryTable.getDimensionRestriction();
        if (dimensionRestriction != null && dimensionRestriction.hasValue(dimension) && (dimValue = dimensionRestriction.getValue(dimension)) != null) {
            return dimValue;
        }
        ColumnModelDefine keyField = this.tableRunInfo.getDimensionField(dimension);
        this.initFinder();
        boolean bnwdColumn = this.accountColumnModelFinder.isBNWDColumn(keyField.getID());
        FieldDefine field = null;
        try {
            field = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(keyField);
        }
        catch (ParseException e) {
            logger.error("\u67e5\u8be2\u6307\u6807\u5931\u8d25\uff01");
        }
        if (bnwdColumn) {
            keyValue = dataRow.getLatestKeyValue(field);
            if (keyValue == null && dataRow.getTableImpl().fieldsInfoImpl.indexOf(field) < 0) {
                keyValue = dataRow.getKeyValue(field);
            }
        } else {
            keyValue = dataRow.getKeyValue(field);
        }
        if ((periodModifier = this.queryTable.getPeriodModifier()) != null && dimension.equalsIgnoreCase("DATATIME")) {
            keyValue = this.qContext.getExeContext().getPeriodAdapter().modify(keyValue.toString(), periodModifier);
        }
        return keyValue;
    }

    @Override
    protected List<DataRowImpl> getChangeRowKeys(List<DataRowImpl> updateRows) {
        DimensionSet rowDims = this.tableRunInfo.getDimensions();
        ArrayList<DataRowImpl> changeRowKeys = new ArrayList<DataRowImpl>();
        if (rowDims == null) {
            return changeRowKeys;
        }
        block0: for (DataRowImpl dataRowImpl : updateRows) {
            for (int index = 0; index < rowDims.size(); ++index) {
                String dimName = rowDims.get(index);
                ColumnModelDefine dimField = this.tableRunInfo.getDimensionField(dimName);
                Object oldValue = this.getOldKeyValue(dataRowImpl, dimName);
                oldValue = DataEngineConsts.formatData(dimField, oldValue, null);
                Object newValue = this.getKeyValue(dataRowImpl, dimName);
                newValue = DataEngineConsts.formatData(dimField, newValue, null);
                if ((oldValue = EmptyDimUtil.getStringEmptyKeyValue(oldValue, dimField.getColumnType())).equals(newValue = EmptyDimUtil.getStringEmptyKeyValue(newValue, dimField.getColumnType()))) continue;
                changeRowKeys.add(dataRowImpl);
                continue block0;
            }
        }
        return changeRowKeys;
    }

    public boolean isTrackHis() {
        return this.trackHis;
    }

    private void checkBatchDuplicateKeys(boolean isInsert, List<DataRowImpl> changeRows, StringBuilder excuteSql, List<Object> paramValues, List<DimensionValueSet> duplicateKeys) throws SQLException {
        try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
             ResultSet rs = sqlHelper.executeQuery(this.connection, excuteSql.toString(), paramValues.toArray());){
            while (rs.next()) {
                int queryRowIndex = rs.getInt(1);
                DataRowImpl rowImpl = changeRows.get(queryRowIndex);
                if (isInsert) {
                    duplicateKeys.add(rowImpl.getRowKeys());
                    continue;
                }
                rowImpl.resetRowKeys();
                duplicateKeys.add(rowImpl.getRowKeys());
            }
        }
        catch (Exception e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    private void manualCommitIfNeed() throws SQLException {
        if (this.manualCommit) {
            try {
                this.connection.commit();
            }
            catch (SQLException e) {
                logger.error("\u53f0\u8d26\u6570\u636e\u63d0\u4ea4\u5f02\u5e38\uff1a" + e.getMessage(), e);
                this.connection.rollback();
                throw e;
            }
        }
    }
}

