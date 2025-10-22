/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.metadata.ISQLMetadata
 *  com.jiuqi.bi.database.metadata.LogicField
 *  com.jiuqi.bi.database.metadata.LogicIndex
 *  com.jiuqi.bi.database.metadata.LogicIndexField
 *  com.jiuqi.bi.database.metadata.LogicPrimaryKey
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.DesignDataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldGatherType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDesignDTO
 *  com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter
 *  com.jiuqi.nr.query.datascheme.extend.DataFieldAdaptItem
 *  com.jiuqi.nr.query.datascheme.extend.DataTableAdaptItem
 *  com.jiuqi.nr.query.datascheme.extend.DataTableInfo
 *  com.jiuqi.nr.query.datascheme.extend.DimFieldInfo
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource
 *  com.jiuqi.util.OrderGenerator
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.bql.extend;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.metadata.ISQLMetadata;
import com.jiuqi.bi.database.metadata.LogicField;
import com.jiuqi.bi.database.metadata.LogicIndex;
import com.jiuqi.bi.database.metadata.LogicIndexField;
import com.jiuqi.bi.database.metadata.LogicPrimaryKey;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.DesignDataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldGatherType;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDesignDTO;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter;
import com.jiuqi.nr.query.datascheme.extend.DataFieldAdaptItem;
import com.jiuqi.nr.query.datascheme.extend.DataTableAdaptItem;
import com.jiuqi.nr.query.datascheme.extend.DataTableInfo;
import com.jiuqi.nr.query.datascheme.extend.DimFieldInfo;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import com.jiuqi.util.OrderGenerator;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaDataSourceTableAdapter
extends AbstractDataTableAdapter {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private DynamicDataSource dynamicDataSource;
    private static final Set<String> UNVISIABLE_FIELDS = Stream.of("BIZKEYORDER", "BIZKEYORDER").collect(Collectors.toSet());

    public void createDataTable(DataTableInfo info) throws DataTableAdaptException {
        String[] strs = info.getSrcTableKey().split("\\.");
        if (strs.length != 2) {
            return;
        }
        String dataSourceKey = strs[0];
        String tableName = strs[1];
        try (Connection conn = this.getConn(dataSourceKey);){
            String dataSchemeKey = info.getDataSchemeKey();
            DesignDataTableDO table = new DesignDataTableDO();
            table.setKey(info.getKey());
            table.setCode(info.getCode());
            table.setTitle(info.getTitle());
            table.setDesc(info.getDescription());
            table.setExpression(info.getExpression());
            table.setDataGroupKey(info.getGroupKey());
            table.setDataSchemeKey(dataSchemeKey);
            table.setDataTableType(info.getDataTableType());
            table.setRepeatCode(Boolean.valueOf(info.getDataTableType() == DataTableType.DETAIL || info.getDataTableType() == DataTableType.SUB_TABLE));
            table.setOrder(OrderGenerator.newOrder());
            DataTableAdaptItem tableItem = new DataTableAdaptItem((DesignDataTable)table);
            DataTableMapDO dataTableMap = new DataTableMapDO(table.getKey(), table.getCode(), info.getSrcTableKey(), tableName, info.getSrcType());
            dataTableMap.setSchemeKey(table.getDataSchemeKey());
            tableItem.setDataTableMap(dataTableMap);
            List<LogicField> tableFields = this.getTableColumns(conn, tableName);
            DesignDataField bizkeyOrderField = null;
            for (LogicField logicField : tableFields) {
                DesignDataField field = this.createDataField((DesignDataTable)table, logicField);
                if (field.getCode().equals("BIZKEYORDER")) {
                    bizkeyOrderField = field;
                }
                DataFieldAdaptItem item = new DataFieldAdaptItem(field, (Object)logicField);
                item.setDeployInfo((DataFieldDeployInfo)this.createDeployInfo((DataField)field, logicField.getFieldName(), tableName));
                tableItem.addFieldItem(item);
            }
            ArrayList<String> bizKeys = new ArrayList<String>();
            if (table.getDataTableType() == DataTableType.SUB_TABLE) {
                List srcFieldCodes = info.getSrcFieldCodes();
                Map fieldCodeMap = tableItem.getFields().stream().collect(Collectors.toMap(Basic::getCode, Function.identity(), (oldValue, newValue) -> newValue));
                for (String fieldCode : srcFieldCodes) {
                    DesignDataField field = (DesignDataField)fieldCodeMap.get(fieldCode);
                    field.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
                    bizKeys.add(field.getKey());
                }
            } else {
                List dims = this.dataSchemeService.getDataSchemeDimension(dataSchemeKey);
                Map dimMap = dims.stream().collect(Collectors.toMap(DataDimension::getDimKey, Function.identity(), (oldValue, newValue) -> newValue));
                info.getDimFieldMap().forEach((entityId, columnCode) -> {
                    DesignDataDimension dim = (DesignDataDimension)dimMap.get(entityId);
                    DesignDataField field = tableItem.findBySourceKey(columnCode).getField();
                    field.setRefDataEntityKey(entityId);
                    if (dim.getDimensionType() == DimensionType.UNIT) {
                        field.setCode("MDCODE");
                    } else if (dim.getDimensionType() == DimensionType.PERIOD) {
                        field.setCode("DATATIME");
                    } else if (dim.getDimensionType() != DimensionType.UNIT_SCOPE) {
                        IEntityDefine entity = this.entityMetaService.queryEntity(entityId);
                        field.setCode(entity.getCode());
                    }
                    field.setDataFieldKind(DataFieldKind.PUBLIC_FIELD_DIM);
                    bizKeys.add(field.getKey());
                });
            }
            if (table.getDataTableType() == DataTableType.DETAIL || table.getDataTableType() == DataTableType.SUB_TABLE) {
                if (bizkeyOrderField == null) {
                    bizkeyOrderField = this.createBizkeyOrderField((DesignDataTable)table);
                    DataFieldAdaptItem item = new DataFieldAdaptItem(bizkeyOrderField, null);
                    DataFieldDeployInfoDO bizKeyDeployInfo = this.createDeployInfo((DataField)bizkeyOrderField, null, tableName);
                    bizKeyDeployInfo.setFieldName("VIRTUAL_BIZKEYORDER");
                    item.setDeployInfo((DataFieldDeployInfo)bizKeyDeployInfo);
                    tableItem.addFieldItem(item);
                }
                bizkeyOrderField.setDataFieldKind(DataFieldKind.BUILT_IN_FIELD);
            }
            if (table.isRepeatCode() && bizkeyOrderField != null) {
                bizKeys.add(bizkeyOrderField.getKey());
            }
            table.setBizKeys(bizKeys.toArray(new String[bizKeys.size()]));
            table.setOwner("NR");
            this.saveDataTable(tableItem);
        }
        catch (Exception e) {
            throw new DataTableAdaptException(e.getMessage(), e);
        }
    }

    protected DesignDataField createBizkeyOrderField(DesignDataTable dataTable) {
        DesignDataField field = this.createDataField(dataTable);
        field.setCode("BIZKEYORDER");
        field.setTitle("BIZKEYORDER");
        field.setDataFieldType(DataFieldType.STRING);
        field.setDataFieldApplyType(DataFieldApplyType.PERIODIC);
        field.setDataFieldGatherType(DataFieldGatherType.NONE);
        field.setDataFieldKind(DataFieldKind.BUILT_IN_FIELD);
        field.setPrecision(Integer.valueOf(50));
        field.setVisible(Boolean.valueOf(false));
        field.setOrder(OrderGenerator.newOrder());
        return field;
    }

    protected DesignDataField createDataField(DesignDataTable dataTable, LogicField logicField) {
        DataFieldKind fieldKind;
        DesignDataField field = this.createDataField(dataTable);
        field.setCode(logicField.getFieldName());
        String fieldTitle = logicField.getFieldTitle();
        if (StringUtils.isEmpty((CharSequence)fieldTitle)) {
            fieldTitle = logicField.getFieldName();
        }
        field.setTitle(fieldTitle);
        int dataType = logicField.getDataType();
        if (dataType == 3) {
            dataType = 10;
        }
        field.setDataFieldType(DataFieldType.valueOf((int)dataType));
        field.setDataFieldApplyType(DataFieldApplyType.PERIODIC);
        DataFieldGatherType gatherType = DataFieldGatherType.SUM;
        if (!DataTypes.isNumber((int)dataType)) {
            gatherType = DataFieldGatherType.NONE;
        }
        field.setDataFieldGatherType(gatherType);
        DataFieldKind dataFieldKind = fieldKind = dataTable.getDataTableType() == DataTableType.DETAIL || dataTable.getDataTableType() == DataTableType.SUB_TABLE ? DataFieldKind.FIELD : DataFieldKind.FIELD_ZB;
        if (UNVISIABLE_FIELDS.contains(logicField.getFieldName())) {
            fieldKind = DataFieldKind.BUILT_IN_FIELD;
        }
        field.setDataFieldKind(fieldKind);
        int scale = logicField.getScale();
        int size = logicField.getPrecision();
        if (dataType == 6) {
            size = logicField.getSize();
            scale = 0;
        } else if (dataType == 2) {
            scale = 0;
            size = 14;
        }
        field.setDecimal(Integer.valueOf(scale));
        field.setPrecision(Integer.valueOf(size));
        field.setVisible(Boolean.valueOf(true));
        field.setOrder(OrderGenerator.newOrder());
        return field;
    }

    protected DesignDataField createDataField(DesignDataTable dataTable) {
        DataFieldDesignDTO field = new DataFieldDesignDTO();
        field.setDataTableKey(dataTable.getKey());
        field.setKey(UUID.randomUUID().toString());
        field.setDataSchemeKey(dataTable.getDataSchemeKey());
        return field;
    }

    public Date getDataTableUpdateTime(String tableCode) throws DataTableAdaptException {
        return null;
    }

    public List<DimFieldInfo> getAllStrFields(String schemeKey, String srcTableKey, String srcTableCode) throws DataTableAdaptException {
        ArrayList<DimFieldInfo> dimFields = new ArrayList<DimFieldInfo>();
        try {
            String[] strs = srcTableKey.split("\\.");
            if (strs.length != 2) {
                return dimFields;
            }
            String dataSourceKey = strs[0];
            String tableName = strs[1];
            try (Connection conn = this.getConn(dataSourceKey);){
                Map dimMap = this.getDimMap(schemeKey);
                List<LogicField> logicFields = this.getTableFields(conn, tableName);
                for (LogicField field : logicFields) {
                    if (field.getDataType() != 6) continue;
                    this.addToDimFields(field, dimFields, dimMap);
                }
            }
            return dimFields;
        }
        catch (Exception e) {
            throw new DataTableAdaptException(e.getMessage(), e);
        }
    }

    private void addToDimFields(LogicField logicField, List<DimFieldInfo> dimFields, Map<String, DesignDataDimension> dimMap) {
        String fieldName = logicField.getFieldName();
        DimFieldInfo dimFieldInfo = new DimFieldInfo(fieldName, logicField.getFieldTitle());
        if (dimFields.contains(dimFieldInfo)) {
            return;
        }
        if (fieldName.equals("MDCODE") || fieldName.contains("ORG") || fieldName.contains("UNIT")) {
            DesignDataDimension dim = dimMap.get("MDCODE");
            if (dim != null) {
                dimFieldInfo.setMatchedDim((DataDimension)dim);
            }
        } else if (fieldName.equals("DATATIME") || fieldName.contains("PERIOD")) {
            DesignDataDimension dim = dimMap.get("DATATIME");
            if (dim != null) {
                dimFieldInfo.setMatchedDim((DataDimension)dim);
            }
        } else if (fieldName.startsWith("MD_")) {
            this.matchDimByEntityCode(dimMap, dimFieldInfo, fieldName);
        }
        dimFields.add(dimFieldInfo);
    }

    public List<LogicField> getTableFields(Connection conn, String tableName) throws Exception {
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        ISQLMetadata metadata = database.createMetadata(conn);
        return metadata.getFieldsByTableName(tableName);
    }

    /*
     * Exception decompiling
     */
    public List<LogicField> getTableColumns(Connection conn, String tableName) throws Exception {
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

    public List<String> getTableKeyFields(Connection conn, String tableName) throws Exception {
        IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(conn);
        ISQLMetadata metadata = database.createMetadata(conn);
        List indexes = metadata.getIndexesByTableName(tableName);
        for (Object index : indexes) {
            if (!index.isUnique()) continue;
            return index.getIndexFields().stream().map(LogicIndexField::getFieldName).collect(Collectors.toList());
        }
        LogicPrimaryKey primaryKey = metadata.getPrimaryKeyByTableName(tableName);
        if (primaryKey != null) {
            return primaryKey.getFieldNames();
        }
        for (LogicIndex index : indexes) {
            if (index.getIndexFields().size() <= 1) continue;
            return index.getIndexFields().stream().map(LogicIndexField::getFieldName).collect(Collectors.toList());
        }
        List<LogicField> allFields = this.getTableFields(conn, tableName);
        return allFields.stream().filter(field -> field.getDataType() == 6).map(LogicField::getFieldName).collect(Collectors.toList());
    }

    private boolean isIntegerType(int type, int scale, int precision) {
        if (type == -5 || type == 4 || type == 5 || type == -6) {
            return true;
        }
        return (type == 2 || type == 3) && scale == 0 && precision != 0;
    }

    private Connection getConn(String datasourceKey) throws SQLException {
        try {
            if (datasourceKey == null || "nrDataSource".equalsIgnoreCase(datasourceKey)) {
                return this.dataSource.getConnection();
            }
            DataSource dataSource = this.dynamicDataSource.getDataSource(datasourceKey);
            return dataSource.getConnection();
        }
        catch (SQLException ex) {
            throw new SQLException("\u6570\u636e\u6e90\u8fde\u63a5\u5931\u8d25, \u8fde\u63a5\u4e0d\u53ef\u7528:" + ex.getMessage(), ex);
        }
    }

    protected void analysisDataFields(DesignDataTable table, Map<String, DesignDataField> fieldNameMap, String tableName, String dataSourceKey, List<DesignDataField> insertFields, List<DesignDataField> updateFields, List<DesignDataField> deleteFields, Map<String, String> fieldNameByKey) throws DataTableAdaptException {
        try (Connection conn = this.getConn(dataSourceKey);){
            List<LogicField> tableFields = this.getTableColumns(conn, tableName);
            Map tableFieldMap = tableFields.stream().collect(Collectors.toMap(LogicField::getFieldName, Function.identity(), (oldValue, newValue) -> newValue));
            for (LogicField logicField : tableFields) {
                DesignDataField field = fieldNameMap.get(logicField.getFieldName());
                if (field != null) {
                    this.updateDataField(field, logicField);
                    updateFields.add(field);
                    continue;
                }
                field = this.createDataField(table, logicField);
                fieldNameByKey.put(field.getKey(), logicField.getFieldName());
                insertFields.add(field);
            }
            fieldNameMap.entrySet().forEach(entry -> {
                if (!tableFieldMap.containsKey(entry.getKey()) && !((DesignDataField)entry.getValue()).getCode().equals("BIZKEYORDER")) {
                    deleteFields.add((DesignDataField)entry.getValue());
                }
            });
        }
        catch (Exception e) {
            throw new DataTableAdaptException(e.getMessage(), e);
        }
    }

    private void updateDataField(DesignDataField field, LogicField logicField) {
        int dataType = logicField.getDataType();
        if (dataType == 3) {
            dataType = 10;
        }
        field.setDataFieldType(DataFieldType.valueOf((int)dataType));
        int scale = logicField.getScale();
        int size = logicField.getPrecision();
        if (dataType == 6) {
            size = logicField.getSize();
            scale = 0;
        } else if (dataType == 2) {
            scale = 0;
            size = 14;
        }
        field.setDecimal(Integer.valueOf(scale));
        field.setPrecision(Integer.valueOf(size));
        String fieldTitle = logicField.getFieldTitle();
        if (StringUtils.isNotEmpty((CharSequence)fieldTitle) && field.getCode().equals(field.getTitle())) {
            field.setTitle(fieldTitle);
        }
    }

    private static /* synthetic */ LogicField lambda$getTableColumns$4(LogicField v1, LogicField v2) {
        return v2;
    }

    private static /* synthetic */ LogicField lambda$getTableColumns$3(LogicField value) {
        return value;
    }
}

