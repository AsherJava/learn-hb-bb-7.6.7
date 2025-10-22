/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicIndex
 *  com.jiuqi.bi.database.metadata.LogicIndexField
 *  com.jiuqi.bi.database.metadata.LogicPrimaryKey
 *  com.jiuqi.bi.database.metadata.LogicTable
 *  com.jiuqi.bi.database.operator.ITableExecutableOperation
 *  com.jiuqi.bi.database.operator.ITableOperation
 *  com.jiuqi.bi.database.operator.ITableRefactor
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.datasource.DataSourceUtils
 */
package com.jiuqi.nr.io.tz.service.impl;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicIndexField;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.database.metadata.LogicTable;
import com.jiuqi.bi.database.operator.ITableExecutableOperation;
import com.jiuqi.bi.database.operator.ITableOperation;
import com.jiuqi.bi.database.operator.ITableRefactor;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.io.tz.TzConstants;
import com.jiuqi.nr.io.tz.bean.DataSchemeTmpTable;
import com.jiuqi.nr.io.tz.bean.TempIndex;
import com.jiuqi.nr.io.tz.exception.ParamCheckException;
import com.jiuqi.nr.io.tz.exception.TzCreateTmpTableException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.CollectionUtils;

public abstract class BaseTmpTableCreateDao {
    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    protected IRuntimeDataSchemeService dataSchemeService;
    private static final Logger logger = LoggerFactory.getLogger(BaseTmpTableCreateDao.class);

    protected abstract Set<TempIndex> getIndexFields(DataSchemeTmpTable var1);

    protected abstract List<String> pkFieldName(DataSchemeTmpTable var1);

    protected abstract List<LogicField> buildExtTmpFields(DataSchemeTmpTable var1);

    public void restructureTempTable(DataSchemeTmpTable tmpTable) {
        List<String> pkFieldNames;
        LogicTable logicTable = this.buildTmpTable(tmpTable);
        List<LogicField> fields = this.buildTmpFields(tmpTable);
        List<LogicField> extFields = this.buildExtTmpFields(tmpTable);
        if (extFields != null) {
            fields.addAll(extFields);
        }
        if (CollectionUtils.isEmpty(pkFieldNames = this.pkFieldName(tmpTable))) {
            throw new RuntimeException("\u672a\u627e\u5230\u4e3b\u952e\u5b9a\u4e49,\u65e0\u6cd5\u751f\u4ea7\u6570\u636e\u8868");
        }
        LogicPrimaryKey primaryKey = this.getLogicPrimaryKey(logicTable, pkFieldNames);
        Set<TempIndex> indexField = this.getIndexFields(tmpTable);
        List<LogicIndex> allIndex = this.getIndex(logicTable, indexField);
        Connection connection = null;
        try {
            connection = Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            ITableRefactor tableOperator = database.createTableOperator(connection);
            List restructure = tableOperator.restructurePreview(logicTable, fields, primaryKey, allIndex, null);
            for (ITableOperation info : restructure) {
                logger.info(info.getOperationName() + ":\r\n" + info.getOperationDescription());
                if (!(info instanceof ITableExecutableOperation)) continue;
                ((ITableExecutableOperation)info).execute(connection);
            }
        }
        catch (Exception e) {
            this.destroyTempTable(logicTable.getName());
            throw new TzCreateTmpTableException(e);
        }
        finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection((Connection)connection, (DataSource)this.jdbcTemplate.getDataSource());
            }
        }
    }

    public DataSchemeTmpTable restructureTempTable(String dataTableKey) {
        DataSchemeTmpTable tmpTable = this.dataFieldInit(dataTableKey);
        this.restructureTempTable(tmpTable);
        return tmpTable;
    }

    protected List<LogicIndex> getIndex(LogicTable logicTable, Set<TempIndex> indexFieldNames) {
        if (CollectionUtils.isEmpty(indexFieldNames)) {
            return Collections.emptyList();
        }
        ArrayList<LogicIndex> allIndex = new ArrayList<LogicIndex>();
        int num = 0;
        for (TempIndex tempIndex : indexFieldNames) {
            ++num;
            List<String> filedNames = tempIndex.getFiledNames();
            if (CollectionUtils.isEmpty(filedNames)) continue;
            LogicIndex index = new LogicIndex();
            index.setTableName(logicTable.getName());
            index.setIndexName(logicTable.getName() + "_" + num);
            ArrayList<LogicIndexField> indexFields = new ArrayList<LogicIndexField>();
            for (String name : filedNames) {
                LogicIndexField indexField = new LogicIndexField();
                indexField.setFieldName(name);
                indexFields.add(indexField);
            }
            index.setIndexFields(indexFields);
            index.setUnique(tempIndex.isUnique());
            allIndex.add(index);
        }
        return allIndex;
    }

    protected LogicPrimaryKey getLogicPrimaryKey(LogicTable logicTable, List<String> pkFieldNames) {
        LogicPrimaryKey primaryKey = new LogicPrimaryKey();
        primaryKey.setTableName(logicTable.getName());
        primaryKey.setPkName(logicTable.getName() + "_PK");
        primaryKey.setFieldNames(pkFieldNames);
        return primaryKey;
    }

    public DataSchemeTmpTable dataFieldInit(String dataTableKey) {
        DataSchemeTmpTable tmpTable = new DataSchemeTmpTable();
        DataTable dataTable = this.dataSchemeService.getDataTable(dataTableKey);
        if (dataTable == null) {
            throw new ParamCheckException("\u6ca1\u6709\u53c2\u6570\u8868\u5b9a\u4e49");
        }
        tmpTable.setTable(dataTable);
        List fields = this.dataSchemeService.getDataFieldByTable(dataTableKey);
        for (DataField field : fields) {
            tmpTable.getAllFields().add(field);
            tmpTable.getFieldMap().put(field.getKey(), field);
            DataFieldKind dataFieldKind = field.getDataFieldKind();
            switch (dataFieldKind) {
                case FIELD: 
                case FIELD_ZB: {
                    if (field.isChangeWithPeriod()) {
                        tmpTable.getPeriodicFields().add(field);
                        break;
                    }
                    tmpTable.getTimePointFields().add(field);
                    break;
                }
                case PUBLIC_FIELD_DIM: {
                    if ("DATATIME".equals(field.getCode())) {
                        tmpTable.setPeriod(field);
                        break;
                    }
                    if ("MDCODE".equals(field.getCode())) {
                        tmpTable.setMdCode(field);
                        break;
                    }
                    tmpTable.getDimFields().add(field);
                    break;
                }
                case TABLE_FIELD_DIM: {
                    tmpTable.getTableDimFields().add(field);
                    break;
                }
            }
        }
        List dwFields = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{tmpTable.getMdCode().getKey()});
        if (dwFields.isEmpty()) {
            throw new ParamCheckException("\u672a\u627e\u5230\u53f0\u8d26\u8868\u53d1\u5e03\u4fe1\u606f");
        }
        List dwTableName = dwFields.stream().map(DataFieldDeployInfo::getTableName).collect(Collectors.toList());
        String tableName = null;
        for (String name : dwTableName) {
            if (tableName != null) {
                if (tableName.length() <= name.length()) continue;
                tableName = name;
                continue;
            }
            tableName = name;
        }
        tmpTable.setTzTableName(tableName);
        String[] fieldKeys = (String[])tmpTable.getDimFields().stream().map(Basic::getKey).toArray(String[]::new);
        List dimDep = this.dataSchemeService.getDeployInfoByDataFieldKeys(fieldKeys);
        ArrayList<DataFieldDeployInfo> dimDistinct = new ArrayList<DataFieldDeployInfo>();
        HashSet<String> disCode = new HashSet<String>();
        for (DataFieldDeployInfo dataFieldDeployInfo : dimDep) {
            if (disCode.contains(dataFieldDeployInfo.getFieldName())) continue;
            dimDistinct.add(dataFieldDeployInfo);
            disCode.add(dataFieldDeployInfo.getFieldName());
        }
        tmpTable.setDimDeploys(dimDistinct);
        fieldKeys = (String[])tmpTable.getTableDimFields().stream().map(Basic::getKey).toArray(String[]::new);
        if (fieldKeys.length == 0) {
            throw new ParamCheckException("\u53f0\u8d26\u8868\u5185\u7ef4\u5ea6\u53c2\u6570\u4e0d\u5b8c\u6574");
        }
        tmpTable.setTableDimDeploys(this.dataSchemeService.getDeployInfoByDataFieldKeys(fieldKeys));
        fieldKeys = (String[])tmpTable.getTimePointFields().stream().map(Basic::getKey).toArray(String[]::new);
        if (fieldKeys.length == 0) {
            throw new ParamCheckException("\u53f0\u8d26\u8868\u65e0\u65f6\u70b9\u6307\u6807\u53c2\u6570");
        }
        tmpTable.setTimePointDeploys(this.dataSchemeService.getDeployInfoByDataFieldKeys(fieldKeys));
        fieldKeys = (String[])tmpTable.getPeriodicFields().stream().map(Basic::getKey).toArray(String[]::new);
        tmpTable.setPeriodicDeploys(this.dataSchemeService.getDeployInfoByDataFieldKeys(fieldKeys));
        Map<String, DataFieldDeployInfo> infoMap = tmpTable.getDeployInfoMap();
        Stream.of(tmpTable.getDimDeploys(), tmpTable.getTableDimDeploys(), tmpTable.getTimePointDeploys(), tmpTable.getPeriodicDeploys()).flatMap(Collection::stream).forEach(r -> infoMap.put(r.getDataFieldKey(), (DataFieldDeployInfo)r));
        return tmpTable;
    }

    protected LogicTable buildTmpTable(DataSchemeTmpTable tmpTableInfo) {
        LogicTable tmpTable = new LogicTable();
        if (tmpTableInfo.getTempTableName() == null) {
            tmpTableInfo.setTempTableName(TzConstants.createName());
        }
        tmpTable.setName(tmpTableInfo.getTempTableName());
        return tmpTable;
    }

    protected List<LogicField> buildTmpFields(DataSchemeTmpTable params) {
        DataField dataField;
        LogicField dimField;
        ArrayList<LogicField> tmpFields = new ArrayList<LogicField>();
        LogicField code = new LogicField();
        code.setFieldName("MDCODE");
        code.setSize(params.getMdCode().getPrecision().intValue());
        code.setDataType(6);
        tmpFields.add(code);
        for (DataFieldDeployInfo dimDeploy : params.getDimDeploys()) {
            dimField = new LogicField();
            dimField.setFieldName(dimDeploy.getFieldName());
            dataField = params.getFieldMap().get(dimDeploy.getDataFieldKey());
            this.parseFieldType(dataField, dimField);
            dimField.setNullable(false);
            tmpFields.add(dimField);
        }
        for (DataFieldDeployInfo tableDimField : params.getTableDimDeploys()) {
            dimField = new LogicField();
            dimField.setFieldName(tableDimField.getFieldName());
            dataField = params.getFieldMap().get(tableDimField.getDataFieldKey());
            this.parseFieldType(dataField, dimField);
            tmpFields.add(dimField);
        }
        Stream.of(params.getTimePointDeploys(), params.getPeriodicDeploys()).filter(Objects::nonNull).flatMap(Collection::stream).map(f -> {
            LogicField field = new LogicField();
            field.setFieldName(f.getFieldName());
            DataField dataField = params.getFieldMap().get(f.getDataFieldKey());
            this.parseFieldType(dataField, field);
            field.setNullable(true);
            return field;
        }).collect(Collectors.toCollection(() -> tmpFields));
        LogicField modifyTime = new LogicField();
        modifyTime.setFieldName("MODIFYTIME");
        modifyTime.setDataType(2);
        modifyTime.setNullable(true);
        modifyTime.setDataTypeName("timestamp");
        tmpFields.add(modifyTime);
        return tmpFields;
    }

    public void parseFieldType(DataField tableDimField, LogicField dimField) {
        switch (tableDimField.getDataFieldType()) {
            case BIGDECIMAL: {
                dimField.setDataType(3);
                if (tableDimField.getPrecision() != null) {
                    dimField.setPrecision(tableDimField.getPrecision().intValue());
                }
                if (tableDimField.getDecimal() == null) break;
                dimField.setScale(tableDimField.getDecimal().intValue());
                break;
            }
            case STRING: 
            case PICTURE: 
            case FILE: {
                dimField.setDataType(6);
                if (tableDimField.getPrecision() != null) {
                    dimField.setSize(tableDimField.getPrecision().intValue());
                    dimField.setRawType(-9);
                    break;
                }
                dimField.setSize(255);
                break;
            }
            default: {
                if (tableDimField.getPrecision() != null) {
                    dimField.setSize(tableDimField.getPrecision().intValue());
                } else {
                    dimField.setSize(255);
                }
                dimField.setDataType(tableDimField.getDataFieldType().getValue());
            }
        }
    }

    public void destroyTempTable(DataSchemeTmpTable tmpTable) {
        if (tmpTable == null) {
            return;
        }
        this.destroyTempTable(tmpTable.getTempTableName());
    }

    public void destroyTempTable(String tempTableName) {
        if (tempTableName == null) {
            return;
        }
        try {
            String sql = "DROP TABLE " + tempTableName;
            logger.info("\u6267\u884cSQL\uff1a{}", (Object)sql);
            this.jdbcTemplate.execute(sql);
        }
        catch (Exception ignored) {
            logger.warn("\u5220\u9664\u4e34\u65f6\u8868\u5931\u8d25\uff0c\u8bf7\u624b\u52a8\u5220\u9664\uff1a{}", (Object)tempTableName);
        }
    }
}

