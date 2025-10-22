/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.nr.common.temptable.ITempTable
 *  com.jiuqi.nr.common.temptable.ITempTableManager
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO
 *  com.jiuqi.nvwa.dataengine.util.DataEngineUtil
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.nr.common.temptable.ITempTable;
import com.jiuqi.nr.common.temptable.ITempTableManager;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataFieldDO;
import com.jiuqi.nr.io.tz.TempTable;
import com.jiuqi.nr.io.tz.TzParams;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.bean.FlagState;
import com.jiuqi.nr.io.tz.listener.ChangeInfo;
import com.jiuqi.nr.io.tz.listener.ColumnData;
import com.jiuqi.nr.io.tz.listener.DataRecord;
import com.jiuqi.nr.io.tz.listener.TzDataChangeListener;
import com.jiuqi.nr.io.tz.service.TzChangeService;
import com.jiuqi.nvwa.dataengine.util.DataEngineUtil;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

@Service
public class TzChangeServiceImpl
implements TzChangeService {
    private static final Logger logger = LoggerFactory.getLogger(TzChangeServiceImpl.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired(required=false)
    private List<TzDataChangeListener> listeners;
    @Autowired
    private ITempTableManager tempTableManager;

    @Override
    public void flagAfter(TzParams params, FlagState flagState, AsyncTaskMonitor monitor) {
        if (this.listeners == null) {
            return;
        }
        if (flagState.isChange()) {
            ChangeInfo changeInfo;
            block19: {
                changeInfo = this.initChangeInfo(params);
                String stateTableName = params.getTempTableName();
                DataSchemeTmpTable tmpTable = params.getTmpTable();
                String tzTableName = tmpTable.getTzTableName();
                Map<String, DataField> allColInfoInTZ = this.getAllColInTZ(tmpTable);
                Map<String, String> codeNameMap = this.getCodeNameMap(allColInfoInTZ);
                ArrayList<String> allColInTZ = new ArrayList<String>(allColInfoInTZ.keySet());
                StringBuilder stateTableSql = this.buildQuery(allColInTZ, stateTableName, true);
                this.appendConditionState(stateTableSql);
                Map<DataRecord, String> recordMap = this.processStateResult(changeInfo, this.jdbcTemplate.queryForList(stateTableSql.toString()), allColInfoInTZ);
                if (!recordMap.isEmpty()) {
                    ArrayList<String> sbidList = new ArrayList<String>(recordMap.values());
                    StringBuilder tzTableSql = this.buildQuery(allColInTZ, tzTableName, false);
                    if (sbidList.size() >= this.getMaxInSize()) {
                        try (ITempTable tempTable = this.tempTableManager.getOneKeyTempTable();){
                            ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
                            for (String fk : sbidList) {
                                Object[] values = new Object[]{fk};
                                batchValues.add(values);
                            }
                            tempTable.insertRecords(batchValues);
                            this.appendCondition(tzTableSql, true, tempTable.getTableName(), new ArrayList<String>());
                            this.processTzResult(changeInfo, this.jdbcTemplate.queryForList(tzTableSql.toString()), recordMap, codeNameMap);
                            break block19;
                        }
                        catch (IOException | SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    this.appendCondition(tzTableSql, false, null, sbidList);
                    this.processTzResult(changeInfo, this.jdbcTemplate.queryForList(tzTableSql.toString()), recordMap, codeNameMap);
                }
            }
            params.setChangeInfo(changeInfo);
        }
    }

    @Override
    public void saveAfter(TzParams params, FlagState flagState, AsyncTaskMonitor monitor) {
        ChangeInfo changeInfo;
        if (this.listeners != null && (changeInfo = params.getChangeInfo()) != null) {
            for (TzDataChangeListener listener : this.listeners) {
                listener.onDataChange(changeInfo);
            }
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection((DataSource)this.dataSource);
    }

    private int getMaxInSize() {
        IDatabase database = null;
        try (Connection connection = this.getConnection();){
            database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
        }
        catch (SQLException e) {
            logger.debug("\u83b7\u53d6\u5f53\u524d\u6570\u636e\u5e93MaxInSize\u5f02\u5e38" + e.getMessage(), e);
        }
        return database == null ? 0 : DataEngineUtil.getMaxInSize((IDatabase)database);
    }

    private Map<String, DataField> getAllColInTZ(DataSchemeTmpTable tmpTable) {
        Map<String, DataFieldDeployInfo> deployInfoMap = tmpTable.getDeployInfoMap();
        HashMap<String, DataField> colInfo = new HashMap<String, DataField>();
        DesignDataFieldDO sbidField = new DesignDataFieldDO();
        sbidField.setTitle("SBID");
        sbidField.setCode("SBID");
        sbidField.setDataFieldType(DataFieldType.STRING);
        colInfo.put("SBID", (DataField)sbidField);
        DataField mdCode = tmpTable.getMdCode();
        colInfo.put("MDCODE", mdCode);
        for (DataField dimField : tmpTable.getDimFields()) {
            colInfo.put(deployInfoMap.get(dimField.getKey()).getFieldName(), dimField);
        }
        for (DataField tableDimField : tmpTable.getTableDimFields()) {
            colInfo.put(deployInfoMap.get(tableDimField.getKey()).getFieldName(), tableDimField);
        }
        for (DataField timePointField : tmpTable.getTimePointFields()) {
            colInfo.put(deployInfoMap.get(timePointField.getKey()).getFieldName(), timePointField);
        }
        return colInfo;
    }

    private Map<String, String> getCodeNameMap(Map<String, DataField> allColInfoInTZ) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (Map.Entry<String, DataField> entry : allColInfoInTZ.entrySet()) {
            String code = entry.getValue().getCode();
            String name = entry.getKey();
            map.put(code, name);
        }
        return map;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    private TempTable createTemp(List<?> filterValues, int dataType) {
        try (Connection connection = this.getConnection();){
            TempTable tempTable2 = new TempTable(filterValues, dataType);
            tempTable2.createTempTable(connection);
            tempTable2.insertIntoTempTable(connection);
            TempTable tempTable = tempTable2;
            return tempTable;
        }
        catch (Exception e) {
            logger.debug("\u7ec4\u88c5ChangeInfo\u65f6\u521b\u5efa\u4e34\u65f6\u8868\u5f02\u5e38\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    @Deprecated
    private void dropTempTable(TempTable tempTable) {
        if (tempTable != null) {
            try (Connection connection = this.getConnection();){
                tempTable.dropTempTable(connection);
            }
            catch (SQLException e) {
                logger.debug("\u7ec4\u88c5ChangeInfo\u65f6\u5220\u9664\u4e34\u65f6\u8868" + tempTable.getTempTable() + "\u5f02\u5e38\uff1a" + e.getMessage(), e);
            }
        }
    }

    private StringBuilder buildQuery(List<String> colName, String tableName, boolean queryTmp) {
        if (!colName.isEmpty() && StringUtils.isNotEmpty((String)tableName)) {
            StringBuilder sql = new StringBuilder();
            sql.append("select ");
            if (queryTmp) {
                sql.append("OPT");
                sql.append(",");
            } else {
                sql.append("VALIDDATATIME");
                sql.append(",");
                sql.append("BIZKEYORDER");
                sql.append(",");
            }
            for (String s : colName) {
                sql.append(s);
                sql.append(",");
            }
            sql.setLength(sql.length() - 1);
            sql.append(" from ");
            sql.append(tableName);
            sql.append(" R ");
            return sql;
        }
        return new StringBuilder();
    }

    private void appendConditionState(StringBuilder sql) {
        sql.append(" where ");
        sql.append("R.");
        sql.append("OPT");
        sql.append("=");
        sql.append(-1);
        sql.append(" or ");
        sql.append("(");
        sql.append("R.");
        sql.append("OPT");
        sql.append(">=");
        sql.append(2);
        sql.append(" and ");
        sql.append("R.");
        sql.append("OPT");
        sql.append("<=");
        sql.append(4);
        sql.append(")");
    }

    private void appendCondition(StringBuilder sql, boolean useTemp, String tempTableName, List<String> sbidList) {
        if (useTemp) {
            sql.append(" join ");
            sql.append(tempTableName);
            sql.append(" T ");
            sql.append(" on R.");
            sql.append("SBID");
            sql.append("=T.");
            sql.append("TEMP_KEY");
        } else {
            sql.append(" where ");
            sql.append("R.");
            sql.append("SBID");
            sql.append(" in(");
            for (String s : sbidList) {
                sql.append("'");
                sql.append(s);
                sql.append("'");
                sql.append(",");
            }
            sql.setLength(sql.length() - 1);
            sql.append(")");
        }
    }

    private ChangeInfo initChangeInfo(TzParams tzParams) {
        ChangeInfo info = new ChangeInfo();
        DataSchemeTmpTable tmpTable = tzParams.getTmpTable();
        info.setTable(tmpTable.getTable());
        info.setMdCode(tmpTable.getMdCode());
        info.setPeriod(tmpTable.getPeriod());
        info.setDimFields(tmpTable.getDimFields());
        info.setTableDimFields(tmpTable.getTableDimFields());
        info.setTimePointFields(tmpTable.getTimePointFields());
        info.setPeriodicFields(tmpTable.getPeriodicFields());
        info.setAllFields(tmpTable.getAllFields());
        info.setFieldMap(tmpTable.getFieldMap());
        info.setDatatime(tzParams.getDatatime());
        return info;
    }

    private Map<DataRecord, String> processStateResult(ChangeInfo changeInfo, List<Map<String, Object>> result, Map<String, DataField> allColInfoInTZ) {
        ArrayList<DataRecord> insertRecords = new ArrayList<DataRecord>();
        ArrayList<DataRecord> deleteRecords = new ArrayList<DataRecord>();
        ArrayList<DataRecord> updateRecords = new ArrayList<DataRecord>();
        HashMap<DataRecord, String> recordMap = new HashMap<DataRecord, String>();
        for (Map<String, Object> dataMap : result) {
            String sbid = (String)dataMap.get("SBID");
            DataRecord dataRecord = new DataRecord();
            HashMap<String, ColumnData> columnDataMap = new HashMap<String, ColumnData>();
            ColumnData bizKey = new ColumnData();
            bizKey.setType(DataFieldType.STRING.getValue());
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                String colName = entry.getKey().toUpperCase();
                Object value = entry.getValue();
                if ("OPT".equals(colName)) {
                    int opt = AbstractData.valueOf((Object)value, (int)4).getAsInt();
                    if (-1 == opt) {
                        deleteRecords.add(dataRecord);
                        recordMap.put(dataRecord, sbid);
                        continue;
                    }
                    if (2 == opt || 3 == opt) {
                        updateRecords.add(dataRecord);
                        recordMap.put(dataRecord, sbid);
                        continue;
                    }
                    if (4 != opt) continue;
                    insertRecords.add(dataRecord);
                    bizKey.setValue(sbid);
                    continue;
                }
                DataField dataField = allColInfoInTZ.get(colName);
                ColumnData columnData = new ColumnData();
                DataFieldType type = dataField.getDataFieldType();
                columnData.setType(DataFieldType.PICTURE == type || DataFieldType.FILE == type ? DataFieldType.STRING.getValue() : type.getValue());
                columnData.setValue(value);
                columnDataMap.put(dataField.getCode(), columnData);
            }
            ColumnData columnData = new ColumnData();
            columnData.setType(DataFieldType.STRING.getValue());
            columnData.setValue(changeInfo.getDatatime());
            columnDataMap.put("VALIDDATATIME", columnData);
            columnDataMap.put("BIZKEYORDER", bizKey);
            dataRecord.setColumnData(columnDataMap);
        }
        changeInfo.setInsertRecords(insertRecords);
        changeInfo.setDeleteRecords(deleteRecords);
        changeInfo.setUpdateRecords(updateRecords);
        return recordMap;
    }

    private void processTzResult(ChangeInfo changeInfo, List<Map<String, Object>> result, Map<DataRecord, String> recordMap, Map<String, String> codeNameMap) {
        List<DataRecord> deleteRecords = changeInfo.getDeleteRecords();
        List<DataRecord> updateRecords = changeInfo.getUpdateRecords();
        Map<String, Map<String, Object>> sbidResultMap = this.mapSbid(result);
        this.fillOldValue(deleteRecords, recordMap, sbidResultMap, codeNameMap, false);
        this.fillOldValue(updateRecords, recordMap, sbidResultMap, codeNameMap, true);
    }

    private Map<String, Map<String, Object>> mapSbid(List<Map<String, Object>> result) {
        HashMap<String, Map<String, Object>> mapSbid = new HashMap<String, Map<String, Object>>();
        for (Map<String, Object> dataMap : result) {
            String sbid = (String)dataMap.get("SBID");
            if (sbid == null) {
                sbid = (String)dataMap.get("SBID".toLowerCase());
            }
            mapSbid.put(sbid, dataMap);
        }
        return mapSbid;
    }

    private void fillOldValue(List<DataRecord> dataRecords, Map<DataRecord, String> recordMap, Map<String, Map<String, Object>> sbidResultMap, Map<String, String> codeNameMap, boolean update) {
        for (DataRecord record : dataRecords) {
            String sbid = recordMap.get(record);
            Map<String, ColumnData> columnData = record.getColumnData();
            Map<String, Object> dataRow = sbidResultMap.get(sbid);
            if (dataRow == null) continue;
            for (Map.Entry<String, ColumnData> entry : columnData.entrySet()) {
                String code = entry.getKey();
                ColumnData colValue = entry.getValue();
                String name = codeNameMap.get(code);
                String colName = name == null ? code : name;
                if (colName == null) continue;
                Object dataValue = dataRow.get(colName);
                colValue.setOldValue(dataValue);
                if (!"BIZKEYORDER".equals(colName) || !update) continue;
                colValue.setValue(dataValue);
            }
        }
    }
}

