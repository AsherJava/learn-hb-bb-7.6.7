/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.bi.util.StringUtils
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
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException
 *  com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter
 *  com.jiuqi.nr.query.datascheme.extend.DataFieldAdaptItem
 *  com.jiuqi.nr.query.datascheme.extend.DataTableAdaptItem
 *  com.jiuqi.nr.query.datascheme.extend.DataTableInfo
 *  com.jiuqi.nr.query.datascheme.extend.DimFieldInfo
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.util.OrderGenerator
 */
package com.jiuqi.nr.bql.extend;

import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.bi.util.StringUtils;
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
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.query.datascheme.exception.DataTableAdaptException;
import com.jiuqi.nr.query.datascheme.extend.AbstractDataTableAdapter;
import com.jiuqi.nr.query.datascheme.extend.DataFieldAdaptItem;
import com.jiuqi.nr.query.datascheme.extend.DataTableAdaptItem;
import com.jiuqi.nr.query.datascheme.extend.DataTableInfo;
import com.jiuqi.nr.query.datascheme.extend.DimFieldInfo;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.util.OrderGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaModelDataTableAdapter
extends AbstractDataTableAdapter {
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private PeriodEngineService periodEngineService;
    private static final Set<String> UNVISIABLE_FIELDS = Stream.of("BIZKEYORDER", "BIZKEYORDER").collect(Collectors.toSet());

    public void createDataTable(DataTableInfo info) throws DataTableAdaptException {
        try {
            DataFieldAdaptItem item;
            TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(info.getSrcTableKey());
            if (tableModel == null) {
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
            List columnModels = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
            Collections.sort(columnModels, new Comparator<ColumnModelDefine>(){

                @Override
                public int compare(ColumnModelDefine o1, ColumnModelDefine o2) {
                    double result = o1.getOrder() - o2.getOrder();
                    if (result > 0.0) {
                        return 1;
                    }
                    if (result < 0.0) {
                        return -1;
                    }
                    return 0;
                }
            });
            DesignDataField bizkeyOrderField = null;
            for (ColumnModelDefine columnModel : columnModels) {
                DesignDataField field = this.createDataField((DesignDataTable)table, columnModel);
                if (field.getCode().equals("BIZKEYORDER")) {
                    bizkeyOrderField = field;
                }
                DataFieldAdaptItem item2 = new DataFieldAdaptItem(field, (Object)columnModel);
                item2.setDeployInfo((DataFieldDeployInfo)this.createDeployInfo((DataField)field, columnModel.getName(), tableModel.getCode()));
                tableItem.addFieldItem(item2);
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
                    if (bizKeys.size() <= 10) {
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
                    }
                });
            }
            if (table.getDataTableType() == DataTableType.DETAIL) {
                if (bizkeyOrderField == null) {
                    bizkeyOrderField = this.createBizkeyOrderField((DesignDataTable)table);
                    item = new DataFieldAdaptItem(bizkeyOrderField, null);
                    item.setDeployInfo((DataFieldDeployInfo)this.createDeployInfo((DataField)bizkeyOrderField, null, tableModel.getCode()));
                    tableItem.addFieldItem(item);
                }
                bizkeyOrderField.setDataFieldKind(DataFieldKind.BUILT_IN_FIELD);
                String modelBizKeys = tableModel.getBizKeys();
                if (StringUtils.isNotEmpty((String)modelBizKeys)) {
                    String[] keyColumnIds;
                    for (String keyColumnId : keyColumnIds = modelBizKeys.split(";")) {
                        ColumnModelDefine keyColumn = this.dataModelService.getColumnModelDefineByID(keyColumnId);
                        DesignDataField field = tableItem.findBySourceKey(keyColumn.getCode()).getField();
                        if (field.getDataFieldKind() != DataFieldKind.FIELD) continue;
                        field.setDataFieldKind(DataFieldKind.TABLE_FIELD_DIM);
                        bizKeys.add(field.getKey());
                    }
                }
            } else if (table.getDataTableType() == DataTableType.SUB_TABLE) {
                if (bizkeyOrderField == null) {
                    bizkeyOrderField = this.createBizkeyOrderField((DesignDataTable)table);
                    item = new DataFieldAdaptItem(bizkeyOrderField, null);
                    item.setDeployInfo((DataFieldDeployInfo)this.createDeployInfo((DataField)bizkeyOrderField, null, tableModel.getCode()));
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

    public List<DimFieldInfo> getAllStrFields(String schemeKey, String srcTableKey, String srcTableCode) throws DataTableAdaptException {
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(srcTableCode);
        List columnModels = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        List<ColumnModelDefine> bizKeyFields = this.getBizkeyFields(tableModel);
        Map dimMap = this.getDimMap(schemeKey);
        ArrayList<DimFieldInfo> dimFields = new ArrayList<DimFieldInfo>();
        if (bizKeyFields != null) {
            for (ColumnModelDefine columnModel : bizKeyFields) {
                if (columnModel.getColumnType() != ColumnModelType.STRING) continue;
                this.addToDimFields(columnModel, dimFields, dimMap);
            }
        }
        for (ColumnModelDefine columnModel : columnModels) {
            if (columnModel.getColumnType() != ColumnModelType.STRING) continue;
            this.addToDimFields(columnModel, dimFields, dimMap);
        }
        return dimFields;
    }

    private List<ColumnModelDefine> getBizkeyFields(TableModelDefine tableModel) {
        ArrayList<ColumnModelDefine> bizKeyFields = null;
        String bizkeys = tableModel.getBizKeys();
        if (StringUtils.isNotEmpty((String)bizkeys)) {
            String[] tableModelBizkeys = bizkeys.split(";");
            bizKeyFields = new ArrayList<ColumnModelDefine>();
            for (String key : tableModelBizkeys) {
                ColumnModelDefine column = this.dataModelService.getColumnModelDefineByID(key);
                bizKeyFields.add(column);
            }
        }
        return bizKeyFields;
    }

    private void addToDimFields(ColumnModelDefine columnModel, List<DimFieldInfo> dimFields, Map<String, DesignDataDimension> dimMap) {
        DimFieldInfo dimFieldInfo = new DimFieldInfo(columnModel.getCode(), columnModel.getTitle());
        if (dimFields.contains(dimFieldInfo)) {
            return;
        }
        if (columnModel.getReferColumnID() != null) {
            ColumnModelDefine refColumn = this.dataModelService.getColumnModelDefineByID(columnModel.getReferColumnID());
            if (refColumn != null) {
                this.matchDimByRefTable(dimMap, refColumn, dimFieldInfo);
            }
        } else if (columnModel.getReferTableID() != null) {
            this.matchDimByRefTable(dimMap, columnModel, dimFieldInfo);
        }
        if (dimFieldInfo.getMatchedDim() == null) {
            DesignDataDimension dim;
            if (columnModel.getCode().equals("MDCODE") || columnModel.getCode().contains("ORG") || columnModel.getCode().contains("UNIT")) {
                dim = dimMap.get("MDCODE");
                if (dim != null) {
                    dimFieldInfo.setMatchedDim((DataDimension)dim);
                }
            } else if (columnModel.getCode().equals("DATATIME") || columnModel.getCode().contains("PERIOD")) {
                dim = dimMap.get("DATATIME");
                if (dim != null) {
                    dimFieldInfo.setMatchedDim((DataDimension)dim);
                }
            } else if (columnModel.getCode().startsWith("MD_")) {
                this.matchDimByEntityCode(dimMap, dimFieldInfo, columnModel.getCode());
            }
        }
        dimFields.add(dimFieldInfo);
    }

    private void matchDimByRefTable(Map<String, DesignDataDimension> dimMap, ColumnModelDefine columnModel, DimFieldInfo dimFieldInfo) {
        TableModelDefine refTableModel = this.dataModelService.getTableModelDefineByCode(columnModel.getReferTableID());
        if (refTableModel != null) {
            this.matchDimByEntityCode(dimMap, dimFieldInfo, refTableModel.getCode());
        }
    }

    public Date getDataTableUpdateTime(String tableCode) throws DataTableAdaptException {
        return null;
    }

    protected DesignDataField createDataField(DesignDataTable dataTable, ColumnModelDefine columnModel) {
        TableModelDefine refTableModel;
        DataFieldKind fieldKind;
        DesignDataField field = this.createDataField(dataTable);
        field.setCode(columnModel.getCode());
        field.setTitle(columnModel.getTitle() == null ? columnModel.getCode() : columnModel.getTitle());
        int dataType = columnModel.getColumnType().getValue();
        if (dataType == ColumnModelType.DOUBLE.getValue()) {
            dataType = ColumnModelType.BIGDECIMAL.getValue();
        }
        field.setDataFieldType(DataFieldType.valueOf((int)dataType));
        field.setDataFieldApplyType(DataFieldApplyType.PERIODIC);
        DataFieldGatherType gatherType = DataFieldGatherType.SUM;
        if (!DataTypes.isNumber((int)dataType)) {
            gatherType = DataFieldGatherType.NONE;
        }
        field.setDataFieldGatherType(gatherType);
        DataFieldKind dataFieldKind = fieldKind = dataTable.getDataTableType() == DataTableType.DETAIL || dataTable.getDataTableType() == DataTableType.SUB_TABLE ? DataFieldKind.FIELD : DataFieldKind.FIELD_ZB;
        if (UNVISIABLE_FIELDS.contains(columnModel.getCode())) {
            fieldKind = DataFieldKind.BUILT_IN_FIELD;
        }
        field.setDataFieldKind(fieldKind);
        field.setDecimal(Integer.valueOf(columnModel.getDecimal()));
        field.setPrecision(Integer.valueOf(columnModel.getPrecision()));
        if (StringUtils.isNotEmpty((String)columnModel.getShowFormat())) {
            FormatProperties formatProperties = new FormatProperties();
            formatProperties.setPattern(columnModel.getShowFormat());
            formatProperties.setFormatType(Integer.valueOf(1));
            field.setFormatProperties(formatProperties);
        }
        field.setOrder(OrderGenerator.newOrder());
        field.setVisible(Boolean.valueOf(true));
        if (columnModel.getReferTableID() != null && (refTableModel = this.dataModelService.getTableModelDefineById(columnModel.getReferTableID())) != null) {
            String refTableCode = refTableModel.getCode();
            if (refTableCode.startsWith(NrPeriodConst.PREFIX_CODE)) {
                IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(refTableCode);
                if (periodEntity != null) {
                    field.setRefDataEntityKey(periodEntity.getKey());
                }
            } else {
                IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(refTableCode);
                if (entityDefine != null) {
                    field.setRefDataEntityKey(entityDefine.getId());
                }
            }
        }
        return field;
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

    protected DesignDataField createDataField(DesignDataTable dataTable) {
        DataFieldDesignDTO field = new DataFieldDesignDTO();
        field.setDataTableKey(dataTable.getKey());
        field.setKey(UUID.randomUUID().toString());
        field.setDataSchemeKey(dataTable.getDataSchemeKey());
        return field;
    }

    protected void analysisDataFields(DesignDataTable table, Map<String, DesignDataField> fieldNameMap, String tableName, String dataSourceKey, List<DesignDataField> insertFields, List<DesignDataField> updateFields, List<DesignDataField> deleteFields, Map<String, String> fieldNameByKey) throws DataTableAdaptException {
        try {
            TableModelDefine tableModel = this.dataModelService.getTableModelDefineByCode(tableName);
            List columnModels = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
            Map tableFieldMap = columnModels.stream().collect(Collectors.toMap(ColumnModelDefine::getName, Function.identity(), (oldValue, newValue) -> newValue));
            for (ColumnModelDefine columnModel : columnModels) {
                DesignDataField field = fieldNameMap.get(columnModel.getName());
                if (field != null) {
                    this.updateDataField(field, columnModel);
                    updateFields.add(field);
                    continue;
                }
                field = this.createDataField(table, columnModel);
                fieldNameByKey.put(field.getKey(), columnModel.getName());
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

    private void updateDataField(DesignDataField field, ColumnModelDefine columnModel) {
        int dataType = columnModel.getColumnType().getValue();
        if (dataType == ColumnModelType.DOUBLE.getValue()) {
            dataType = ColumnModelType.BIGDECIMAL.getValue();
        }
        field.setDataFieldType(DataFieldType.valueOf((int)dataType));
        field.setDecimal(Integer.valueOf(columnModel.getDecimal()));
        field.setPrecision(Integer.valueOf(columnModel.getPrecision()));
        if (StringUtils.isNotEmpty((String)columnModel.getShowFormat())) {
            FormatProperties formatProperties = new FormatProperties();
            formatProperties.setPattern(columnModel.getShowFormat());
            formatProperties.setFormatType(Integer.valueOf(1));
            field.setFormatProperties(formatProperties);
        }
    }
}

