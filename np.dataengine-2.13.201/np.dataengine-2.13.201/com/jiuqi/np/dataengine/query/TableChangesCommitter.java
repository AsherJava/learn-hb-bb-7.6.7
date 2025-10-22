/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.encryption.common.EncryptionException
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.exception.UnsafeOperationException;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.ITableNameFinder;
import com.jiuqi.np.dataengine.intf.impl.DataRowImpl;
import com.jiuqi.np.dataengine.log.LogRow;
import com.jiuqi.np.dataengine.log.LogType;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.setting.FieldsInfoImpl;
import com.jiuqi.np.dataengine.update.UpdateDataRecord;
import com.jiuqi.np.dataengine.update.UpdateDataSet;
import com.jiuqi.np.dataengine.update.UpdateDataTable;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.encryption.common.EncryptionException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableChangesCommitter {
    protected static final Logger logger = LoggerFactory.getLogger(TableChangesCommitter.class);
    protected QueryTable queryTable;
    protected HashMap<QueryField, List<Integer>> queryFields;
    protected TableModelRunInfo tableRunInfo;
    protected FieldsInfoImpl fieldsInfoImpl;
    protected FieldsInfoImpl systemFields;
    protected Connection connection;
    protected boolean designTimeData;
    protected boolean recFieldIsDim = false;
    protected boolean bizOrderIsDim = false;
    protected DimensionValueSet masterKeys;
    protected boolean needCheckKeys;
    protected Map<String, Integer> dimFieldIndexes = new HashMap<String, Integer>();
    protected UpdateDataTable updateDataTable;
    protected QueryParam queryParam;
    protected final DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected Date queryVersionDate = Consts.DATE_VERSION_FOR_ALL;
    protected Date queryVersionStartDate = Consts.DATE_VERSION_INVALID_VALUE;
    protected boolean onlySaveData = false;
    protected boolean canModifyKey = true;
    protected QueryContext qContext;

    public TableChangesCommitter(QueryContext qContext, QueryTable queryTable, HashMap<QueryField, List<Integer>> queryFields, TableModelRunInfo tableRunInfo, FieldsInfoImpl fieldsInfoImpl, FieldsInfoImpl systemFields, Connection connection, boolean designTimeData, DimensionValueSet masterKeys, boolean needCheckKeys, QueryParam queryParam) {
        this.qContext = qContext;
        this.queryTable = queryTable;
        this.queryFields = queryFields;
        this.tableRunInfo = tableRunInfo;
        this.fieldsInfoImpl = fieldsInfoImpl;
        this.systemFields = systemFields;
        this.connection = connection;
        this.designTimeData = designTimeData;
        this.masterKeys = masterKeys;
        this.needCheckKeys = needCheckKeys;
        this.adjustQueryFields();
        this.checkRecField();
        this.checkBizOrderField();
        this.queryParam = queryParam;
        this.canModifyKey = queryParam.canModifyKey();
    }

    protected void checkRecField() {
        ColumnModelDefine recField = this.tableRunInfo.getRecField();
        if (recField == null) {
            return;
        }
        List<ColumnModelDefine> dimFields = this.tableRunInfo.getDimFields();
        for (ColumnModelDefine fieldDefine : dimFields) {
            if (!fieldDefine.getID().equals(recField.getID())) continue;
            this.recFieldIsDim = true;
            break;
        }
    }

    protected void checkBizOrderField() {
        ColumnModelDefine bizOrderField = this.tableRunInfo.getBizOrderField();
        if (bizOrderField == null) {
            return;
        }
        List<ColumnModelDefine> dimFields = this.tableRunInfo.getDimFields();
        for (ColumnModelDefine fieldDefine : dimFields) {
            if (!fieldDefine.getID().equals(bizOrderField.getID())) continue;
            this.bizOrderIsDim = true;
            break;
        }
    }

    protected void adjustQueryFields() {
        HashMap<QueryField, List<Integer>> resultFields = new HashMap<QueryField, List<Integer>>();
        for (Map.Entry<QueryField, List<Integer>> queryField : this.queryFields.entrySet()) {
            String fieldName = queryField.getKey().getFieldCode();
            if (this.tableRunInfo.isKeyField(fieldName) || this.tableRunInfo.isRecField(fieldName) || this.tableRunInfo.isBizOrderField(fieldName)) continue;
            resultFields.put(queryField.getKey(), queryField.getValue());
        }
        this.queryFields = resultFields;
    }

    protected String getTableName() {
        if (this.designTimeData) {
            return String.format("%s%s", "DES_", this.tableRunInfo.getTableModelDefine().getName());
        }
        return this.qContext.getPhysicalTableName(this.queryTable);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * WARNING - void declaration
     */
    public void deleteRows(DimensionValueSet masterKeys, String rowFilter, HashMap<QueryField, ArrayList<Object>> colFilterValues, IMonitor monitor, DimensionValueSet deleteKeys) throws Exception {
        Map<String, String> map;
        ITableNameFinder tableNameFinder;
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from ");
        String physicalTableName = this.getTableName();
        deleteSql.append(physicalTableName);
        int whereCount = 0;
        DimensionSet openDimensions = this.queryTable.getOpenDimensions();
        DimensionValueSet dimensionRestriction = this.queryTable.getDimensionRestriction();
        ArrayList<Object> argValues = new ArrayList<Object>();
        ArrayList<Integer> dataTypes = new ArrayList<Integer>();
        List<ColumnModelDefine> dimFields = this.tableRunInfo.getDimFields();
        ArrayList<TempAssistantTable> tempAssistantTables = new ArrayList<TempAssistantTable>();
        for (ColumnModelDefine fieldDefine : dimFields) {
            void var18_19;
            Object rv;
            void var18_22;
            String dimension = this.tableRunInfo.getDimensionName(fieldDefine.getCode());
            Object object = masterKeys.getValue(dimension);
            if (object == null && deleteKeys.hasValue(dimension)) {
                Object object2 = deleteKeys.getValue(dimension);
            }
            if (openDimensions.contains(dimension) && var18_22 == null) {
                String fieldCode = fieldDefine.getCode();
                if (!fieldCode.equals("MDCODE") && !fieldCode.equals("DATATIME") && !fieldCode.equals("DW")) continue;
                throw new UnsafeOperationException("\u7f3a\u5931\u4e86\u4e3b\u7ef4\u5ea6\u6216\u65f6\u671f\u6761\u4ef6,\u4e0d\u5141\u8bb8\u6267\u884c\u5220\u9664\uff01");
            }
            whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
            if (dimensionRestriction != null && (rv = dimensionRestriction.getValue(dimension)) != null) {
                Object object3 = rv;
            }
            deleteSql.append(fieldDefine.getName()).append("=?");
            int dataType = DataTypesConvert.fieldTypeToDataType(fieldDefine.getColumnType());
            dataTypes.add(dataType);
            if (!(var18_19 instanceof List)) {
                ArrayList<void> arrayList = new ArrayList<void>();
                arrayList.add(var18_19);
                argValues.add(arrayList);
                continue;
            }
            argValues.add(var18_19);
        }
        long descartCount = this.getDescartesCount(argValues);
        if (descartCount > 10000L) {
            this.deleteRowsBySql(masterKeys, rowFilter, colFilterValues, monitor, deleteKeys);
            return;
        }
        if (!StringUtils.isEmpty((String)rowFilter)) {
            whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
            deleteSql.append("(").append(rowFilter).append(")");
        }
        if (colFilterValues != null && colFilterValues.size() > 0) {
            for (Map.Entry entry : colFilterValues.entrySet()) {
                QueryField queryField = (QueryField)entry.getKey();
                ArrayList arrayList = (ArrayList)entry.getValue();
                if (!this.queryFields.containsKey(queryField) || arrayList.size() <= 0) continue;
                whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                deleteSql.append(queryField.getFieldName()).append("=?");
                dataTypes.add(queryField.getDataType());
                argValues.add(arrayList);
            }
        }
        if ((tableNameFinder = this.qContext.getTableNameFinder()) != null && (map = tableNameFinder.getTableCondition(this.qContext.getExeContext(), physicalTableName)) != null) {
            for (Map.Entry entry : map.entrySet()) {
                this.appendWhereOrAnd(deleteSql, whereCount);
                deleteSql.append((String)entry.getKey());
                deleteSql.append("=");
                deleteSql.append("?");
                dataTypes.add(6);
                argValues.add(entry.getValue());
            }
        }
        if (whereCount <= 0) {
            if (this.tableRunInfo.getVersionField() == null && masterKeys.hasValue("VERSIONID")) {
                return;
            }
            logger.warn("\u6267\u884c\u6e05\u7a7a\u5168\u8868\uff0cSQL\uff1a" + deleteSql);
        }
        ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
        this.descartes(argValues, arrayList, 0, new ArrayList<Object>(), dataTypes);
        Connection connection = this.getConnection();
        try {
            if (arrayList.size() > 0) {
                if (arrayList.size() == 1) {
                    this.executeUpdate(LogType.DELETE, deleteSql.toString(), (Object[])arrayList.get(0), monitor);
                    return;
                }
                this.batchUpdate(LogType.DELETE, deleteSql.toString(), arrayList, monitor);
                return;
            }
            this.createTempTables(connection, tempAssistantTables);
            this.executeUpdate(LogType.DELETE, deleteSql.toString(), (Object[])arrayList.get(0), monitor);
        }
        finally {
            this.dropTempTables(connection, tempAssistantTables);
        }
    }

    protected long getDescartesCount(List<Object> argValues) {
        long argCount = 1L;
        for (Object argValue : argValues) {
            List argList = (List)argValue;
            argCount *= (long)argList.size();
        }
        return argCount;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void deleteRowsBySql(DimensionValueSet masterKeys, String rowFilter, HashMap<QueryField, ArrayList<Object>> colFilterValues, IMonitor monitor, DimensionValueSet deleteKeys) throws Exception {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from ");
        deleteSql.append(this.getTableName());
        int whereCount = 0;
        DimensionSet openDimensions = this.queryTable.getOpenDimensions();
        DimensionValueSet dimensionRestriction = this.queryTable.getDimensionRestriction();
        DimensionValueSet delMasterkeys = new DimensionValueSet();
        ArrayList<Object> argValues = new ArrayList<Object>();
        List<ColumnModelDefine> dimFields = this.tableRunInfo.getDimFields();
        ArrayList<String> tempAssistantTables = new ArrayList<String>();
        try {
            for (ColumnModelDefine columnModelDefine : dimFields) {
                ArrayList<Object> listValue;
                Object rv;
                String dimension = this.tableRunInfo.getDimensionName(columnModelDefine.getCode());
                Object argValue = masterKeys.getValue(dimension);
                if (argValue == null && deleteKeys.hasValue(dimension)) {
                    argValue = deleteKeys.getValue(dimension);
                }
                if (openDimensions.contains(dimension) && argValue == null) {
                    String fieldCode = columnModelDefine.getCode();
                    if (!fieldCode.equals("MDCODE") && !fieldCode.equals("DATATIME") && !fieldCode.equals("DW")) continue;
                    throw new UnsafeOperationException("\u7f3a\u5931\u4e86\u4e3b\u7ef4\u5ea6\u6216\u65f6\u671f\u6761\u4ef6,\u4e0d\u5141\u8bb8\u6267\u884c\u5220\u9664\uff01");
                }
                whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                if (dimensionRestriction != null && (rv = dimensionRestriction.getValue(dimension)) != null) {
                    argValue = rv;
                }
                if (this.updateDataTable != null) {
                    delMasterkeys.setValue(dimension, argValue);
                }
                TempAssistantTable tempAssistantTable = null;
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
                this.appendWhereCondition(deleteSql, listValue, argValues, columnModelDefine.getName(), dataType, tempAssistantTable);
            }
            if (!StringUtils.isEmpty((String)rowFilter)) {
                whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                deleteSql.append("(").append(rowFilter).append(")");
            }
            if (colFilterValues != null && colFilterValues.size() > 0) {
                for (Map.Entry entry : colFilterValues.entrySet()) {
                    QueryField queryField = (QueryField)entry.getKey();
                    ArrayList arrayList = (ArrayList)entry.getValue();
                    if (!this.queryFields.containsKey(queryField) || arrayList.size() <= 0) continue;
                    whereCount = this.appendWhereOrAnd(deleteSql, whereCount);
                    this.appendWhereCondition(deleteSql, arrayList, argValues, queryField.getFieldName(), queryField.getDataType(), null);
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
            this.executeUpdate(LogType.DELETE, deleteSql.toString(), argValues.toArray(), monitor);
        }
        finally {
            for (String string : tempAssistantTables) {
                this.qContext.getTempResource().dropTempTable(string);
            }
        }
    }

    protected void descartes(List<Object> argValues, List<Object[]> batchArgs, int layer, List<Object> curList, List<Integer> dataTypes) {
        block7: {
            block6: {
                if (layer >= argValues.size() - 1) break block6;
                List argList = (List)argValues.get(layer);
                if (argList.size() == 0) {
                    this.descartes(argValues, batchArgs, layer + 1, curList, dataTypes);
                } else {
                    for (int i = 0; i < argList.size(); ++i) {
                        ArrayList<Object> list = new ArrayList<Object>(curList);
                        Object formatValue = this.formatValue(argList.get(i), dataTypes.get(layer));
                        list.add(formatValue);
                        this.descartes(argValues, batchArgs, layer + 1, list, dataTypes);
                    }
                }
                break block7;
            }
            if (layer != argValues.size() - 1) break block7;
            List argList = (List)argValues.get(layer);
            if (argList.size() == 0) {
                batchArgs.add(curList.toArray());
            } else {
                for (int i = 0; i < argList.size(); ++i) {
                    ArrayList<Object> list = new ArrayList<Object>(curList);
                    Object formatValue = this.formatValue(argList.get(i), dataTypes.get(layer));
                    list.add(formatValue);
                    batchArgs.add(list.toArray());
                }
            }
        }
    }

    protected Object formatValue(Object value, Integer dataType) {
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
        return argValue;
    }

    protected int appendWhereOrAnd(StringBuilder deleteSql, int whereCount) {
        if (whereCount == 0) {
            deleteSql.append(" where ");
        } else if (whereCount > 0) {
            deleteSql.append(" and ");
        }
        return ++whereCount;
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

    public void deleteRows(List<DataRowImpl> deleteRows, Set<String> currentPeriodSet, IMonitor monitor) throws Exception {
        Map<String, String> tableCondition;
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append("delete from ");
        String physicalTableName = this.getTableName();
        deleteSql.append(physicalTableName);
        deleteSql.append(" where ");
        int whereCount = 0;
        int recIndex = -1;
        List<ColumnModelDefine> dimFields = this.tableRunInfo.getDimFields();
        ColumnModelDefine recField = this.tableRunInfo.getRecField();
        if (Objects.nonNull(recField)) {
            FieldDefine field = this.getFieldDefine(recField);
            recIndex = recField != null && !this.recFieldIsDim ? this.fieldsInfoImpl.indexOf(field) : -1;
        }
        boolean hasRecField = recField != null && recIndex >= 0;
        boolean supportVersion = false;
        QueryField versionField = this.getQueryFieldByName("VALIDTIME");
        int versionIndex = 0;
        if (hasRecField) {
            deleteSql.append(recField.getName());
            deleteSql.append("=");
            deleteSql.append("?");
            ++whereCount;
        } else {
            for (ColumnModelDefine fieldDefine : dimFields) {
                if (whereCount > 0) {
                    deleteSql.append(" and ");
                }
                deleteSql.append(fieldDefine.getName());
                deleteSql.append("=");
                deleteSql.append("?");
                ++whereCount;
            }
            if (supportVersion && versionField != null && !this.onlySaveData) {
                if (whereCount > 0) {
                    deleteSql.append(" and ");
                }
                deleteSql.append("VALIDTIME");
                deleteSql.append("=");
                deleteSql.append("?");
                ++whereCount;
                ++versionIndex;
            }
        }
        ArrayList<String> tableConditionValues = null;
        ITableNameFinder tableNameFinder = this.qContext.getTableNameFinder();
        if (tableNameFinder != null && (tableCondition = tableNameFinder.getTableCondition(this.qContext.getExeContext(), physicalTableName)) != null) {
            tableConditionValues = new ArrayList<String>();
            for (Map.Entry<String, String> entry : tableCondition.entrySet()) {
                this.appendWhereOrAnd(deleteSql, whereCount);
                deleteSql.append(entry.getKey());
                deleteSql.append("=");
                deleteSql.append("?");
                tableConditionValues.add(entry.getValue());
            }
        }
        if (whereCount == 0) {
            return;
        }
        int dimCount = dimFields.size();
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (DataRowImpl deleteRow : deleteRows) {
            Object dataValue;
            UpdateDataRecord deleteRecord = null;
            DimensionValueSet rowkeys = null;
            if (this.updateDataTable != null) {
                deleteRecord = new UpdateDataRecord();
                rowkeys = new DimensionValueSet();
                deleteRecord.setRowkeys(rowkeys);
            }
            int arrayCount = hasRecField ? 1 : dimCount + (this.onlySaveData ? 0 : versionIndex) + (tableConditionValues == null ? 0 : tableConditionValues.size());
            Object[] keyValues = new Object[arrayCount];
            int arrayIndex = 0;
            if (recField != null && recIndex >= 0) {
                dataValue = deleteRow.internalGetValue(recIndex);
                keyValues[arrayIndex] = recField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
            } else {
                for (ColumnModelDefine columnModelDefine : dimFields) {
                    String dimension = this.tableRunInfo.getDimensionName(columnModelDefine.getCode());
                    Object keyValue = this.getKeyValue(deleteRow, dimension);
                    keyValues[arrayIndex] = columnModelDefine.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                    if (rowkeys != null) {
                        rowkeys.setValue(dimension, keyValue);
                    }
                    ++arrayIndex;
                }
                if (supportVersion && versionField != null && !this.onlySaveData) {
                    dataValue = deleteRow.internalGetValue(this.queryFields.get(versionField));
                    keyValues[arrayIndex++] = dataValue;
                }
                if (tableConditionValues != null) {
                    for (String string : tableConditionValues) {
                        keyValues[arrayIndex] = string;
                        ++arrayIndex;
                    }
                }
            }
            if (deleteRecord != null) {
                for (Map.Entry entry : this.queryFields.entrySet()) {
                    QueryField item = (QueryField)entry.getKey();
                    List columnIndexs = (List)entry.getValue();
                    Object dataValue2 = deleteRow.internalGetValue(columnIndexs);
                    deleteRecord.addData(item.getFieldName(), item.getDataType(), item.getFractionDigits(), null, dataValue2);
                }
            }
            if (this.updateDataTable != null) {
                this.updateDataTable.getDeleteRecords().add(deleteRecord);
            }
            batchValues.add(keyValues);
        }
        if (batchValues.size() <= 0) {
            return;
        }
        this.batchUpdate(LogType.DELETE, deleteSql.toString(), batchValues, monitor);
    }

    protected QueryField getQueryFieldByName(String fieldName) {
        QueryField queryField = null;
        for (Map.Entry<QueryField, List<Integer>> fieldItem : this.queryFields.entrySet()) {
            if (!fieldItem.getKey().getFieldName().equalsIgnoreCase(fieldName)) continue;
            queryField = fieldItem.getKey();
            break;
        }
        return queryField;
    }

    /*
     * WARNING - void declaration
     */
    public void updateRow(DataRowImpl updateRow, IMonitor monitor) throws Exception {
        Object keyValue;
        String dimension;
        void var9_16;
        Map<String, String> tableCondition;
        ArrayList<DataRowImpl> updateRows = new ArrayList<DataRowImpl>();
        updateRows.add(updateRow);
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("update ");
        String physicalTableName = this.getTableName();
        updateSql.append(physicalTableName);
        updateSql.append(" set ");
        boolean addDot = false;
        int valueCount = this.queryFields.size();
        for (Map.Entry<QueryField, List<Integer>> entry : this.queryFields.entrySet()) {
            QueryField queryField = entry.getKey();
            if (addDot) {
                updateSql.append(",");
            }
            addDot = true;
            updateSql.append(queryField.getFieldName()).append("=").append("?");
        }
        List<ColumnModelDefine> dimFields = this.tableRunInfo.getDimFields();
        if (this.canModifyKey) {
            for (ColumnModelDefine fieldDefine : dimFields) {
                if (addDot) {
                    updateSql.append(",");
                }
                addDot = true;
                updateSql.append(fieldDefine.getName()).append("=").append("?");
            }
        }
        boolean bl = false;
        int recIndex = -1;
        ColumnModelDefine recField = this.tableRunInfo.getRecField();
        if (Objects.nonNull(recField)) {
            FieldDefine field = this.getFieldDefine(recField);
            recIndex = recField != null && !this.recFieldIsDim ? this.fieldsInfoImpl.indexOf(field) : -1;
        }
        boolean hasRecField = recField != null && recIndex >= 0;
        int versionIndex = 0;
        boolean supportVersion = false;
        QueryField versionField = this.getQueryFieldByName("VALIDTIME");
        if (hasRecField) {
            void var9_13;
            updateSql.append(" where ");
            updateSql.append(recField.getName());
            updateSql.append("=");
            updateSql.append("?");
            ++var9_13;
        } else {
            void var9_14;
            updateSql.append(" where ");
            for (ColumnModelDefine fieldDefine : dimFields) {
                if (var9_14 > 0) {
                    updateSql.append(" and ");
                }
                addDot = true;
                updateSql.append(fieldDefine.getName()).append("=").append("?");
                ++var9_14;
            }
            if (supportVersion && versionField != null) {
                void var9_15;
                if (var9_14 > 0) {
                    updateSql.append(" and ");
                }
                updateSql.append("VALIDTIME");
                updateSql.append("=");
                updateSql.append("?");
                ++var9_15;
                ++versionIndex;
            }
        }
        ArrayList<String> tableConditionValues = null;
        ITableNameFinder tableNameFinder = this.qContext.getTableNameFinder();
        if (tableNameFinder != null && (tableCondition = tableNameFinder.getTableCondition(this.qContext.getExeContext(), physicalTableName)) != null) {
            tableConditionValues = new ArrayList<String>();
            for (Map.Entry<String, String> entry : tableCondition.entrySet()) {
                this.appendWhereOrAnd(updateSql, (int)var9_16);
                updateSql.append(entry.getKey());
                updateSql.append("=");
                updateSql.append("?");
                tableConditionValues.add(entry.getValue());
            }
        }
        if (var9_16 == false) {
            return;
        }
        int dimCount = dimFields.size();
        int arrayCount = (this.canModifyKey ? dimCount : 0) + valueCount + (hasRecField ? 1 : dimCount) + versionIndex + (tableConditionValues == null ? 0 : tableConditionValues.size());
        UpdateDataRecord updateRecord = null;
        DimensionValueSet rowkeys = null;
        if (this.updateDataTable != null) {
            updateRecord = new UpdateDataRecord();
            rowkeys = new DimensionValueSet();
            updateRecord.setRowkeys(rowkeys);
        }
        Object[] dataValues = new Object[arrayCount];
        int arrayIndex = 0;
        for (Map.Entry<QueryField, List<Integer>> queryItem : this.queryFields.entrySet()) {
            QueryField item = queryItem.getKey();
            List<Integer> columnIndexs = queryItem.getValue();
            int dataType = item.getDataType();
            Object dataValue = updateRow.internalGetValue(columnIndexs);
            dataValues[arrayIndex] = dataValue = this.formatDataForDB(item, dataValue);
            if (updateRecord != null) {
                updateRecord.addData(item.getFieldName(), item.getDataType(), item.getFractionDigits(), dataValue, updateRow.internalGetOldValue(columnIndexs));
            }
            ++arrayIndex;
        }
        for (ColumnModelDefine dimField : dimFields) {
            dimension = this.tableRunInfo.getDimensionName(dimField.getCode());
            keyValue = this.getKeyValue(updateRow, dimension);
            if (this.canModifyKey) {
                dataValues[arrayIndex] = dimField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                ++arrayIndex;
            }
            if (rowkeys == null) continue;
            rowkeys.setValue(dimension, keyValue);
        }
        if (recField != null && recIndex >= 0) {
            Object dataValue = updateRow.internalGetValue(recIndex);
            dataValues[arrayIndex++] = recField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
        } else {
            for (ColumnModelDefine dimField : dimFields) {
                dimension = this.tableRunInfo.getDimensionName(dimField.getCode());
                keyValue = this.getOldKeyValue(updateRow, dimension);
                dataValues[arrayIndex] = dimField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                if (rowkeys != null) {
                    rowkeys.setValue(dimension, keyValue);
                }
                ++arrayIndex;
            }
            if (supportVersion && versionField != null) {
                List<Integer> columnIndexs = this.queryFields.get(versionField);
                Object dataValue = updateRow.internalGetValue(columnIndexs);
                dataValues[arrayIndex++] = dataValue;
                if (this.updateDataTable != null && this.updateDataTable.getDateVersionBegin() == null) {
                    this.updateDataTable.setDateVersionBegin((Date)dataValue);
                }
            }
            if (tableConditionValues != null) {
                for (String value : tableConditionValues) {
                    dataValues[arrayIndex] = value;
                    ++arrayIndex;
                }
            }
        }
        int resultValue = this.executeUpdate(LogType.UPDATE, updateSql.toString(), dataValues, monitor);
        if (resultValue > 0) {
            if (this.updateDataTable != null) {
                this.updateDataTable.getUpdateRecords().put(rowkeys, updateRecord);
            }
        } else {
            this.insertRows(updateRows, monitor);
        }
    }

    /*
     * WARNING - void declaration
     */
    public void updateRows(List<DataRowImpl> updateRows, IMonitor monitor) throws Exception {
        void var8_15;
        Map<String, String> tableCondition;
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("update ");
        String physicalTableName = this.getTableName();
        updateSql.append(physicalTableName);
        updateSql.append(" set ");
        boolean addDot = false;
        int valueCount = this.queryFields.size();
        for (Map.Entry<QueryField, List<Integer>> entry : this.queryFields.entrySet()) {
            QueryField queryField = entry.getKey();
            if (addDot) {
                updateSql.append(",");
            }
            addDot = true;
            updateSql.append(queryField.getFieldName()).append("=").append("?");
        }
        List<ColumnModelDefine> dimFields = this.tableRunInfo.getDimFields();
        if (this.canModifyKey) {
            for (ColumnModelDefine fieldDefine : dimFields) {
                if (addDot) {
                    updateSql.append(",");
                }
                addDot = true;
                updateSql.append(fieldDefine.getName()).append("=").append("?");
            }
        }
        boolean bl = false;
        int recIndex = -1;
        ColumnModelDefine recField = this.tableRunInfo.getRecField();
        if (Objects.nonNull(recField)) {
            FieldDefine field = this.getFieldDefine(recField);
            recIndex = recField != null && !this.recFieldIsDim ? this.fieldsInfoImpl.indexOf(field) : -1;
        }
        boolean hasRecField = recField != null && recIndex >= 0;
        int versionIndex = 0;
        boolean supportVersion = false;
        QueryField versionField = this.getQueryFieldByName("VALIDTIME");
        if (hasRecField) {
            void var8_12;
            updateSql.append(" where ");
            updateSql.append(recField.getName());
            updateSql.append("=");
            updateSql.append("?");
            ++var8_12;
        } else {
            void var8_13;
            updateSql.append(" where ");
            for (ColumnModelDefine fieldDefine : dimFields) {
                if (var8_13 > 0) {
                    updateSql.append(" and ");
                }
                addDot = true;
                updateSql.append(fieldDefine.getName()).append("=").append("?");
                ++var8_13;
            }
            if (supportVersion && versionField != null) {
                void var8_14;
                if (var8_13 > 0) {
                    updateSql.append(" and ");
                }
                updateSql.append("VALIDTIME");
                updateSql.append("=");
                updateSql.append("?");
                ++var8_14;
                ++versionIndex;
            }
        }
        ArrayList<String> tableConditionValues = null;
        ITableNameFinder tableNameFinder = this.qContext.getTableNameFinder();
        if (tableNameFinder != null && (tableCondition = tableNameFinder.getTableCondition(this.qContext.getExeContext(), physicalTableName)) != null) {
            tableConditionValues = new ArrayList<String>();
            for (Map.Entry<String, String> entry : tableCondition.entrySet()) {
                this.appendWhereOrAnd(updateSql, (int)var8_15);
                updateSql.append(entry.getKey());
                updateSql.append("=");
                updateSql.append("?");
                tableConditionValues.add(entry.getValue());
            }
        }
        if (var8_15 == false) {
            return;
        }
        int dimCount = dimFields.size();
        int arrayCount = (this.canModifyKey ? dimCount : 0) + valueCount + (hasRecField ? 1 : dimCount) + versionIndex + (tableConditionValues == null ? 0 : tableConditionValues.size());
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
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
            int arrayIndex = 0;
            for (Map.Entry<QueryField, List<Integer>> queryItem : this.queryFields.entrySet()) {
                QueryField item = queryItem.getKey();
                List<Integer> columnIndexs = queryItem.getValue();
                int dataType = item.getDataType();
                Object dataValue = updateRow.internalGetValue(columnIndexs);
                dataValues[arrayIndex] = dataValue = this.formatDataForDB(item, dataValue);
                if (updateRecord != null) {
                    updateRecord.addData(item.getFieldName(), item.getDataType(), item.getFractionDigits(), dataValue, updateRow.internalGetOldValue(columnIndexs));
                }
                ++arrayIndex;
            }
            for (ColumnModelDefine dimField : dimFields) {
                dimension = this.tableRunInfo.getDimensionName(dimField.getCode());
                keyValue = this.getKeyValue(updateRow, dimension);
                if (this.canModifyKey) {
                    dataValues[arrayIndex] = dimField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                    ++arrayIndex;
                }
                if (rowkeys == null) continue;
                rowkeys.setValue(dimension, keyValue);
            }
            if (recField != null && recIndex >= 0) {
                Object dataValue = updateRow.internalGetValue(recIndex);
                dataValues[arrayIndex++] = recField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
            } else {
                for (ColumnModelDefine dimField : dimFields) {
                    dimension = this.tableRunInfo.getDimensionName(dimField.getCode());
                    keyValue = this.getOldKeyValue(updateRow, dimension);
                    dataValues[arrayIndex] = dimField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                    if (rowkeys != null) {
                        rowkeys.setValue(dimension, keyValue);
                    }
                    ++arrayIndex;
                }
                if (supportVersion && versionField != null) {
                    List<Integer> columnIndexs = this.queryFields.get(versionField);
                    Object dataValue = updateRow.internalGetValue(columnIndexs);
                    dataValues[arrayIndex++] = dataValue;
                    if (this.updateDataTable != null && this.updateDataTable.getDateVersionBegin() == null) {
                        this.updateDataTable.setDateVersionBegin((Date)dataValue);
                    }
                }
                if (tableConditionValues != null) {
                    for (String value : tableConditionValues) {
                        dataValues[arrayIndex] = value;
                        ++arrayIndex;
                    }
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
        this.batchUpdate(LogType.UPDATE, updateSql.toString(), batchValues, monitor);
    }

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
                if (!Objects.nonNull(oldValue) || oldValue.equals(newValue)) continue;
                changeRowKeys.add(dataRowImpl);
                continue block0;
            }
        }
        return changeRowKeys;
    }

    public void insertRows(List<DataRowImpl> insertRows, IMonitor monitor) throws Exception {
        Map<String, String> tableCondition;
        ColumnModelDefine recField;
        StringBuilder insertSql = new StringBuilder();
        StringBuilder valueSql = new StringBuilder();
        insertSql.append("insert into ");
        String physicalTableName = this.getTableName();
        insertSql.append(physicalTableName);
        insertSql.append(" (");
        int valueCount = this.queryFields.size();
        boolean addDot = false;
        ArrayList<Integer> keyUidIndexs = new ArrayList<Integer>();
        for (Map.Entry<QueryField, List<Integer>> queryItem : this.queryFields.entrySet()) {
            QueryField queryField = queryItem.getKey();
            if (addDot) {
                insertSql.append(",");
                valueSql.append(",");
            }
            addDot = true;
            insertSql.append(queryField.getFieldName());
            valueSql.append("?");
        }
        DimensionSet dimensionSet = this.tableRunInfo.getDimensions();
        if (dimensionSet != null) {
            for (int index = 0; index < dimensionSet.size(); ++index) {
                String dimension = dimensionSet.get(index);
                ColumnModelDefine fieldDefine = this.tableRunInfo.getDimensionField(dimension);
                if (addDot) {
                    insertSql.append(",");
                    valueSql.append(",");
                }
                addDot = true;
                if (fieldDefine.getColumnType() == ColumnModelType.UUID) {
                    keyUidIndexs.add(index);
                }
                insertSql.append(fieldDefine.getName());
                valueSql.append("?");
            }
        }
        boolean addRec = (recField = this.tableRunInfo.getRecField()) != null && !this.recFieldIsDim;
        int recIndex = this.buildOrderField(insertSql, valueSql, addDot, recField, addRec);
        ColumnModelDefine bizOrderField = this.tableRunInfo.getBizOrderField();
        boolean addBizOrder = bizOrderField != null && !this.bizOrderIsDim;
        int bizOrderIndex = this.buildOrderField(insertSql, valueSql, addDot, bizOrderField, addBizOrder);
        ArrayList<String> tableConditionValues = null;
        ITableNameFinder tableNameFinder = this.qContext.getTableNameFinder();
        if (tableNameFinder != null && (tableCondition = tableNameFinder.getTableCondition(this.qContext.getExeContext(), physicalTableName)) != null) {
            tableConditionValues = new ArrayList<String>();
            for (Map.Entry<String, String> entry : tableCondition.entrySet()) {
                insertSql.append(",").append(entry.getKey());
                valueSql.append(",?");
                tableConditionValues.add(entry.getValue());
            }
        }
        insertSql.append(") values (").append((CharSequence)valueSql).append(")");
        int dimCount = dimensionSet.size();
        int arrayCount = dimCount + valueCount + (addRec ? 1 : 0) + (addBizOrder ? 1 : 0) + (tableConditionValues == null ? 0 : tableConditionValues.size());
        ArrayList<Object[]> batchValues = new ArrayList<Object[]>();
        for (DataRowImpl insertRow : insertRows) {
            UpdateDataRecord insertRecord = null;
            DimensionValueSet rowkeys = null;
            if (this.updateDataTable != null) {
                insertRecord = new UpdateDataRecord();
                rowkeys = new DimensionValueSet();
                insertRecord.setRowkeys(rowkeys);
            }
            Object[] dataValues = new Object[arrayCount];
            int arrayIndex = 0;
            for (Map.Entry<QueryField, List<Integer>> queryItem : this.queryFields.entrySet()) {
                QueryField item = queryItem.getKey();
                List<Integer> columnIndexs = queryItem.getValue();
                int dataType = item.getDataType();
                Object dataValue = insertRow.internalGetValue(columnIndexs);
                dataValues[arrayIndex] = dataValue = this.formatDataForDB(item, dataValue);
                if (insertRecord != null) {
                    insertRecord.addData(item.getFieldName(), dataType, item.getFractionDigits(), dataValue, null);
                }
                ++arrayIndex;
            }
            for (int index = 0; index < dimCount; ++index) {
                String dimension = dimensionSet.get(index);
                Object keyValue = this.getKeyValue(insertRow, dimension);
                if (dimension.equals("RECORDKEY") && keyValue == null) {
                    keyValue = UUID.randomUUID().toString();
                }
                if (keyValue == null) {
                    logger.error(String.format("\u7ef4\u5ea6%s\u503c\u4e3a\u7a7a\uff0c\u6267\u884cSQL\uff1a%s", dimension, insertSql.toString()));
                    throw new IncorrectQueryException(String.format("\u63d2\u5165\u6570\u636e\u884c\u65f6\uff0c\u4e1a\u52a1\u4e3b\u952e\u503c\u4e0d\u80fd\u4e3a\u7a7a\uff0c\u4e1a\u52a1\u4e3b\u952e\u4e3a%s\uff0c\u7ef4\u5ea6%s\u503c\u4e3a\u7a7a", insertRow.getRowKeys(), dimension));
                }
                dataValues[arrayIndex] = keyUidIndexs.contains(index) ? (Object)DataEngineConsts.toBytes(keyValue) : keyValue;
                if (rowkeys != null) {
                    rowkeys.setValue(dimension, keyValue);
                }
                ++arrayIndex;
            }
            int colIndex = dimCount + valueCount;
            if (addRec) {
                this.setOrderValue(colIndex, recField, recIndex, insertRow, dataValues);
                ++colIndex;
            }
            if (addBizOrder) {
                this.setOrderValue(colIndex, bizOrderField, bizOrderIndex, insertRow, dataValues);
                ++colIndex;
            }
            if (tableConditionValues != null) {
                for (String value : tableConditionValues) {
                    dataValues[colIndex] = value;
                    ++colIndex;
                }
            }
            if (this.updateDataTable != null) {
                this.updateDataTable.getInsertRecords().add(insertRecord);
            }
            batchValues.add(dataValues);
        }
        if (batchValues.size() <= 0) {
            return;
        }
        this.batchUpdate(LogType.INSERT, insertSql.toString(), batchValues, monitor);
    }

    protected Object formatDataForDB(QueryField field, Object dataValue) {
        int dataType = field.getDataType();
        if (dataType == 33) {
            dataValue = DataEngineConsts.toBytes(dataValue);
        } else if (dataType == 1 && dataValue instanceof Boolean) {
            boolean booleanValue = (Boolean)dataValue;
            dataValue = booleanValue ? 1 : 0;
        } else if (dataType == 6 && dataValue != null) {
            try {
                dataValue = this.qContext.encrypt(field.getSceneId(), dataValue.toString());
            }
            catch (EncryptionException e) {
                this.qContext.getMonitor().exception((Exception)((Object)e));
            }
        }
        return dataValue;
    }

    protected void setOrderValue(int colIndex, ColumnModelDefine recField, int orderIndex, DataRowImpl insertRow, Object[] dataValues) {
        Object dataValue;
        Object object = dataValue = orderIndex >= 0 ? insertRow.internalGetValue(orderIndex) : UUID.randomUUID().toString();
        if (dataValue == null) {
            dataValue = UUID.randomUUID().toString();
        }
        dataValues[colIndex] = recField.getColumnType() == ColumnModelType.UUID ? (Object)DataEngineConsts.toBytes(dataValue) : dataValue;
    }

    protected int buildOrderField(StringBuilder insertSql, StringBuilder valueSql, boolean addDot, ColumnModelDefine recField, boolean addRec) {
        if (!addRec) {
            return -1;
        }
        FieldDefine field = this.getFieldDefine(recField);
        int recIndex = this.fieldsInfoImpl.indexOf(field);
        if (recField != null) {
            if (addDot) {
                insertSql.append(",");
                valueSql.append(",");
            }
            insertSql.append(recField.getName());
            valueSql.append("?");
        }
        return recIndex;
    }

    public Object getKeyValue(DataRowImpl dataRow, String dimension) {
        Object dimValue;
        DimensionValueSet dimensionRestriction = this.queryTable.getDimensionRestriction();
        if (dimensionRestriction != null && dimensionRestriction.hasValue(dimension) && (dimValue = dimensionRestriction.getValue(dimension)) != null) {
            return dimValue;
        }
        Integer fieldIndex = this.dimFieldIndexes.get(dimension);
        ColumnModelDefine keyField = this.tableRunInfo.getDimensionField(dimension);
        if (fieldIndex == null) {
            try {
                FieldDefine field = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(keyField);
                if (field != null) {
                    fieldIndex = dataRow.getFieldsInfo().indexOf(field);
                    this.dimFieldIndexes.put(dimension, fieldIndex);
                }
            }
            catch (ParseException e) {
                logger.error("\u67e5\u8be2\u6307\u6807\u5931\u8d25\uff01");
            }
        }
        Object keyValue = dataRow.getKeyValue(fieldIndex, dimension);
        PeriodModifier periodModifier = this.queryTable.getPeriodModifier();
        if (periodModifier != null && dimension.equalsIgnoreCase("DATATIME")) {
            keyValue = this.qContext.getExeContext().getPeriodAdapter().modify(keyValue.toString(), periodModifier);
        }
        if (keyField.getColumnType() == ColumnModelType.DATETIME && keyValue instanceof String) {
            keyValue = DataTypesConvert.periodToDate(new PeriodWrapper((String)keyValue));
        }
        return keyValue;
    }

    public Object getOldKeyValue(DataRowImpl dataRow, String dimension) {
        Object dimValue;
        DimensionValueSet dimensionRestriction = this.queryTable.getDimensionRestriction();
        if (dimensionRestriction != null && dimensionRestriction.hasValue(dimension) && (dimValue = dimensionRestriction.getValue(dimension)) != null) {
            return dimValue;
        }
        ColumnModelDefine keyField = this.tableRunInfo.getDimensionField(dimension);
        FieldDefine fieldDefine = null;
        try {
            fieldDefine = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(keyField);
        }
        catch (ParseException e) {
            logger.error("\u67e5\u8be2\u6307\u6807\u5931\u8d25\uff01");
        }
        Object keyValue = dataRow.getOldKeyValue(fieldDefine);
        PeriodModifier periodModifier = this.queryTable.getPeriodModifier();
        if (periodModifier != null && dimension.equalsIgnoreCase("DATATIME")) {
            keyValue = this.qContext.getExeContext().getPeriodAdapter().modify(keyValue.toString(), periodModifier);
        }
        if (keyField.getColumnType() == ColumnModelType.DATETIME && keyValue instanceof String) {
            keyValue = DataTypesConvert.periodToDate(new PeriodWrapper((String)keyValue));
        }
        return keyValue;
    }

    protected Connection getConnection() {
        return this.connection;
    }

    public void setUpdateDataTable(UpdateDataSet updateDataSet) {
        if (updateDataSet != null) {
            this.updateDataTable = updateDataSet.getTable(this.getTableName());
            this.updateDataTable.setTableInfo(this.tableRunInfo);
        }
    }

    public TableModelRunInfo getTableRunInfo() {
        return this.tableRunInfo;
    }

    public DimensionValueSet getMasterKeys() {
        return this.masterKeys;
    }

    public void createTempTables(Connection connection, List<TempAssistantTable> tempAssistantTables) throws Exception {
        if (tempAssistantTables == null || tempAssistantTables.size() <= 0) {
            return;
        }
        for (TempAssistantTable tempAssistantTable : tempAssistantTables) {
            tempAssistantTable.createTempTable(connection);
            tempAssistantTable.insertIntoTempTable(connection);
        }
    }

    public void dropTempTables(Connection connection, List<TempAssistantTable> tempAssistantTables) {
        if (tempAssistantTables == null || tempAssistantTables.size() <= 0) {
            return;
        }
        for (TempAssistantTable tempAssistantTable : tempAssistantTables) {
            try {
                tempAssistantTable.dropTempTable(connection);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void revokeRows(List<DataRowImpl> revokeRows, IMonitor monitor) throws SQLException, IncorrectQueryException {
    }

    public void setOnlySaveData(boolean onlySaveData) {
        this.onlySaveData = onlySaveData;
    }

    private FieldDefine getFieldDefine(ColumnModelDefine columnModelDefine) {
        DataModelDefinitionsCache tableCache = null;
        try {
            tableCache = this.qContext.getExeContext().getCache().getDataModelDefinitionsCache();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        FieldDefine field = tableCache.getFieldDefine(columnModelDefine);
        return field;
    }

    private int executeUpdate(LogType type, String sql, Object[] args, IMonitor monitor) throws SQLException {
        if (this.qContext.isSqlLog()) {
            long start = System.currentTimeMillis();
            int rowCount = DataEngineUtil.executeUpdate(this.getConnection(), sql, args, monitor);
            long exeCost = System.currentTimeMillis() - start;
            LogRow logRow = new LogRow(type, exeCost, exeCost, args.length, rowCount, this.getTableName(), sql, this.dFormat);
            this.qContext.getMonitor().message(logRow.toString(), this);
            return rowCount;
        }
        return DataEngineUtil.executeUpdate(this.getConnection(), sql, args, monitor);
    }

    private void batchUpdate(LogType type, String sql, List<Object[]> batchArgs, IMonitor monitor) throws SQLException {
        if (batchArgs.isEmpty()) {
            return;
        }
        if (this.qContext.isSqlLog()) {
            long start = System.currentTimeMillis();
            DataEngineUtil.batchUpdate(this.getConnection(), sql, batchArgs, monitor);
            long exeCost = System.currentTimeMillis() - start;
            LogRow logRow = new LogRow(type, exeCost, exeCost, batchArgs.get(0).length, batchArgs.size(), this.getTableName(), sql, this.dFormat);
            this.qContext.getMonitor().message(logRow.toString(), this);
        } else {
            DataEngineUtil.batchUpdate(this.getConnection(), sql, batchArgs, monitor);
        }
    }

    public void upsertRows(List<DataRowImpl> updateRows, IMonitor monitor) throws Exception {
        for (DataRowImpl updateRow : updateRows) {
            this.updateRow(updateRow, monitor);
        }
    }
}

