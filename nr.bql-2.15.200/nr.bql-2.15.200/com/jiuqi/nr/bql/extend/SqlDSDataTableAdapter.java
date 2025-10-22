/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.model.field.DSField
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
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
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO
 *  com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter
 *  com.jiuqi.nr.query.datascheme.extend.DataFieldAdaptItem
 *  com.jiuqi.nr.query.datascheme.extend.DataTableAdaptItem
 *  com.jiuqi.nr.query.datascheme.extend.DataTableInfo
 *  com.jiuqi.nr.query.datascheme.extend.DimFieldInfo
 *  com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.bql.extend;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.model.field.DSField;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
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
import com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO;
import com.jiuqi.nr.datascheme.internal.entity.DesignDataTableDO;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter;
import com.jiuqi.nr.query.datascheme.extend.DataFieldAdaptItem;
import com.jiuqi.nr.query.datascheme.extend.DataTableAdaptItem;
import com.jiuqi.nr.query.datascheme.extend.DataTableInfo;
import com.jiuqi.nr.query.datascheme.extend.DimFieldInfo;
import com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqlDSDataTableAdapter
extends AbstractDataTableAdapter {
    @Autowired
    private DataSetStorageProvider dataSetStorageProvider;

    public void createDataTable(DataTableInfo info) throws DataTableAdaptException {
        try {
            DSModel sqlModel = this.dataSetStorageProvider.findModel(info.getSrcTableKey());
            if (sqlModel == null) {
                return;
            }
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
            DataTableMapDO dataTableMap = new DataTableMapDO(table.getKey(), table.getCode(), info.getSrcTableKey(), info.getSrcTableKey(), info.getSrcType());
            dataTableMap.setSchemeKey(table.getDataSchemeKey());
            tableItem.setDataTableMap(dataTableMap);
            List dsFields = sqlModel.getFields();
            DesignDataField bizkeyOrderField = null;
            for (DSField dsField : dsFields) {
                DesignDataField field = this.createDataField((DesignDataTable)table, dsField);
                if (field.getCode().equals("BIZKEYORDER")) {
                    bizkeyOrderField = field;
                }
                DataFieldAdaptItem item = new DataFieldAdaptItem(field, (Object)dsField);
                item.setDeployInfo((DataFieldDeployInfo)this.createDeployInfo((DataField)field, dsField.getName(), sqlModel.getName()));
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
                    item.setDeployInfo((DataFieldDeployInfo)this.createDeployInfo((DataField)bizkeyOrderField, null, sqlModel.getName()));
                    tableItem.addFieldItem(item);
                }
                bizkeyOrderField.setDataFieldKind(DataFieldKind.BUILT_IN_FIELD);
                List keyFields = sqlModel.getFields().stream().filter(o -> o.isDimention() && o.getName().equals(o.getKeyField())).collect(Collectors.toList());
                for (DSField dsField : keyFields) {
                    if (bizKeys.size() > 10) break;
                    DesignDataField field = tableItem.findBySourceKey(dsField.getName()).getField();
                    if (field.getDataFieldKind() != DataFieldKind.FIELD) continue;
                    field.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
                    bizKeys.add(field.getKey());
                }
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

    protected DesignDataField createDataField(DesignDataTable dataTable, DSField dsField) {
        DesignDataField field = this.createDataField(dataTable);
        field.setCode(dsField.getName());
        field.setTitle(dsField.getTitle());
        int dataType = dsField.getValType();
        if (dataType == 3) {
            dataType = 10;
        }
        field.setDataFieldType(DataFieldType.valueOf((int)dataType));
        field.setDataFieldApplyType(DataFieldApplyType.PERIODIC);
        DataFieldGatherType gatherType = DataFieldGatherType.SUM;
        boolean isNum = DataTypes.isNumber((int)dataType);
        if (!isNum) {
            gatherType = DataFieldGatherType.NONE;
        }
        field.setDataFieldGatherType(gatherType);
        field.setDataFieldKind(dataTable.getDataTableType() == DataTableType.DETAIL || dataTable.getDataTableType() == DataTableType.SUB_TABLE ? DataFieldKind.FIELD : DataFieldKind.FIELD_ZB);
        int precision = 200;
        int decimal = 0;
        if (isNum) {
            precision = 20;
            decimal = 6;
        } else if (dataType == 2) {
            precision = 14;
        }
        field.setPrecision(Integer.valueOf(precision));
        field.setDecimal(Integer.valueOf(decimal));
        FormatProperties formatProperties = new FormatProperties();
        formatProperties.setPattern(dsField.getShowPattern());
        formatProperties.setFormatType(Integer.valueOf(1));
        field.setFormatProperties(formatProperties);
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
        try {
            DSModel sqlModel = this.dataSetStorageProvider.findModel(srcTableCode);
            List dsFields = sqlModel.getFields();
            List bizKeyFields = dsFields.stream().filter(o -> o.getName().equals(o.getKeyField()) && o.getValType() == 6).collect(Collectors.toList());
            Map dimMap = this.getDimMap(schemeKey);
            ArrayList<DimFieldInfo> dimFields = new ArrayList<DimFieldInfo>();
            for (DSField dsField : bizKeyFields) {
                this.addToDimFields(dsField, dimFields, dimMap);
            }
            return dimFields;
        }
        catch (Exception e) {
            throw new DataTableAdaptException(e.getMessage(), e);
        }
    }

    private void addToDimFields(DSField dsField, List<DimFieldInfo> dimFields, Map<String, DesignDataDimension> dimMap) {
        DimFieldInfo dimFieldInfo = new DimFieldInfo(dsField.getName(), dsField.getTitle());
        if (dimFields.contains(dimFieldInfo)) {
            return;
        }
        if (dsField.getName().equals("MDCODE") || dsField.getName().contains("ORG") || dsField.getName().contains("UNIT")) {
            DesignDataDimension dim = dimMap.get("MDCODE");
            if (dim != null) {
                dimFieldInfo.setMatchedDim((DataDimension)dim);
            }
        } else if (dsField.getName().equals("DATATIME") || dsField.getName().contains("PERIOD")) {
            DesignDataDimension dim = dimMap.get("DATATIME");
            if (dim != null) {
                dimFieldInfo.setMatchedDim((DataDimension)dim);
            }
        } else if (dsField.getName().startsWith("MD_")) {
            this.matchDimByEntityCode(dimMap, dimFieldInfo, dsField.getName());
        }
        dimFields.add(dimFieldInfo);
    }

    protected void analysisDataFields(DesignDataTable table, Map<String, DesignDataField> fieldNameMap, String tableName, String dataSourceKey, List<DesignDataField> insertFields, List<DesignDataField> updateFields, List<DesignDataField> deleteFields, Map<String, String> fieldNameByKey) throws DataTableAdaptException {
        try {
            DSModel sqlModel = this.dataSetStorageProvider.findModel(tableName);
            List dsFields = sqlModel.getFields();
            Map tableFieldMap = dsFields.stream().collect(Collectors.toMap(DSField::getName, Function.identity(), (oldValue, newValue) -> newValue));
            for (DSField dsField : dsFields) {
                DesignDataField field = fieldNameMap.get(dsField.getName());
                if (field != null) {
                    this.updateDataField(field, dsField);
                    updateFields.add(field);
                    continue;
                }
                field = this.createDataField(table, dsField);
                fieldNameByKey.put(field.getKey(), dsField.getName());
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

    private void updateDataField(DesignDataField field, DSField dsField) {
        int dataType = dsField.getValType();
        if (dataType == 3) {
            dataType = 10;
        }
        field.setDataFieldType(DataFieldType.valueOf((int)dataType));
        FormatProperties formatProperties = new FormatProperties();
        formatProperties.setPattern(dsField.getShowPattern());
        formatProperties.setFormatType(Integer.valueOf(1));
        field.setFormatProperties(formatProperties);
    }
}

