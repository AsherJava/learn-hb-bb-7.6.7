/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.ext.model.DSV
 *  com.jiuqi.bi.adhoc.model.FieldInfo
 *  com.jiuqi.bi.adhoc.model.FieldType
 *  com.jiuqi.bi.adhoc.model.HierarchyInfo
 *  com.jiuqi.bi.adhoc.model.KeyInfo
 *  com.jiuqi.bi.adhoc.model.RelationInfo
 *  com.jiuqi.bi.adhoc.model.RelationMode
 *  com.jiuqi.bi.adhoc.model.TableInfo
 *  com.jiuqi.bi.adhoc.model.TableType
 *  com.jiuqi.bi.adhoc.model.TimeGranularity
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.query.filter.HierarchyDataMode
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.bi.util.time.TimeHelper
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.BqlTimeDimUtils
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nr.period.common.utils.TimeDimField
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.nr.bql.dsv.adapter;

import com.jiuqi.bi.adhoc.ext.model.DSV;
import com.jiuqi.bi.adhoc.model.FieldInfo;
import com.jiuqi.bi.adhoc.model.FieldType;
import com.jiuqi.bi.adhoc.model.HierarchyInfo;
import com.jiuqi.bi.adhoc.model.KeyInfo;
import com.jiuqi.bi.adhoc.model.RelationInfo;
import com.jiuqi.bi.adhoc.model.RelationMode;
import com.jiuqi.bi.adhoc.model.TableInfo;
import com.jiuqi.bi.adhoc.model.TableType;
import com.jiuqi.bi.adhoc.model.TimeGranularity;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.query.filter.HierarchyDataMode;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeHelper;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.bql.dsv.adapter.DSVAdapter;
import com.jiuqi.nr.bql.dsv.adapter.DSVContext;
import com.jiuqi.nr.bql.dsv.adapter.FieldInfoAdapter;
import com.jiuqi.nr.bql.intf.CalibreDimTable;
import com.jiuqi.nr.bql.intf.ICalibreDimTableProvider;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.BqlTimeDimUtils;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nr.period.common.utils.TimeDimField;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DimensionTableAdapter {
    private static final ILogger logger = DSVAdapter.getLogger();
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private FieldInfoAdapter fieldInfoAdapter;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired(required=false)
    private ICalibreDimTableProvider calibreDimTableProvider;

    public void bindDimensionTables(DSVContext context, DSV dsv, TableInfo tableInfo, DataTable dataTable, Map<String, TableInfo> dimensionTableMap) {
        String[] bizkeys;
        for (String keyFieldId : bizkeys = dataTable.getBizKeys()) {
            DataField keyField = context.getDataField(keyFieldId);
            String entityId = keyField.getRefDataEntityKey();
            IEntityDefine entityDefine = null;
            IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
            if (!StringUtils.isNotEmpty((CharSequence)entityId)) continue;
            try {
                boolean succ = true;
                TableInfo dimTableInfo = null;
                if (entityId.equals("ADJUST")) {
                    dimTableInfo = dimensionTableMap.get(entityId);
                    if (dimTableInfo == null) {
                        dimTableInfo = this.createAdjustDimTable(context, dsv, tableInfo);
                        dimensionTableMap.put("ADJUST", dimTableInfo);
                    }
                } else if (periodAdapter.isPeriodEntity(entityId)) {
                    dimTableInfo = dimensionTableMap.get(entityId);
                    if (dimTableInfo == null) {
                        dimTableInfo = this.createDimTableInfo(tableInfo);
                        succ = !context.isCustomPeriod() ? this.adaptPeriodDimension(context, dsv, tableInfo, entityId, periodAdapter, dimTableInfo) : this.adaptCustomPeriodDimension(context, dsv, tableInfo, entityId, periodAdapter, dimTableInfo);
                        if (!succ) continue;
                        dimensionTableMap.put(entityId, dimTableInfo);
                    }
                } else {
                    entityDefine = this.entityMetaService.queryEntity(entityId);
                    if (entityDefine == null) {
                        succ = false;
                        continue;
                    }
                    String dimTableName = entityDefine.getDimensionName();
                    String dimTableTitle = entityDefine.getTitle();
                    String fieldDimName = context.getFieldDimNameMap().get(keyField.getKey());
                    if (!dimTableName.equals(fieldDimName)) {
                        dimTableName = keyField.getCode() + "_" + entityDefine.getDimensionName();
                        dimTableTitle = keyField.getTitle() + "_" + entityDefine.getTitle();
                    }
                    if ((dimTableInfo = dimensionTableMap.get(dimTableName)) == null) {
                        dimTableInfo = this.createDimTableInfo(tableInfo);
                        dimTableInfo.setName(dimTableName);
                        dimTableInfo.setTitle(dimTableTitle);
                        succ = this.adaptEntityDimension(context, dsv, tableInfo, entityId, dimTableInfo, entityDefine, keyField);
                        if (!succ) continue;
                        dimensionTableMap.put(dimTableName, dimTableInfo);
                    }
                    if (entityId.equals(context.getMainDimensionEntityId())) {
                        context.setMdTable(dimTableInfo);
                        dimTableInfo.getPropMap().put("isUnitDim", true);
                    }
                }
                String fieldName = keyField.getCode();
                if (dimTableInfo != null && periodAdapter.isPeriodEntity(entityId) && !context.isCustomPeriod()) {
                    FieldInfo timeKeyField = dimTableInfo.getKeyField();
                    FieldInfo fieldInfo = tableInfo.findField(PeriodTableColumn.TIMEKEY.name());
                    if (fieldInfo != null && timeKeyField != null) {
                        fieldInfo.setFieldType(FieldType.TIME_DIM);
                        fieldInfo.setTimeKey(true);
                        fieldInfo.setPhysicalName(dimTableInfo.getName() + "[" + timeKeyField.getName() + "]");
                        fieldInfo.setTimegranularity(timeKeyField.getTimegranularity());
                        fieldName = fieldInfo.getName();
                    }
                }
                this.buildRelation(context, tableInfo, dimTableInfo, fieldName, keyField, entityDefine);
            }
            catch (Exception e) {
                logger.error("\u6570\u636e\u65b9\u6848\uff1a" + context.getDataScheme().getCode() + "    \u6307\u6807/\u5b57\u6bb5 \u3010 " + dataTable.getCode() + "." + keyField.getCode() + "\u3011 \u5173\u8054\u7ef4\u5ea6\u8868\u3010" + entityId + "\u3011\u51fa\u9519\uff01", (Throwable)e);
            }
        }
    }

    protected TableInfo createAdjustDimTable(DSVContext context, DSV dsv, TableInfo tableInfo) {
        TableInfo dimTableInfo = this.createDimTableInfo(tableInfo);
        dimTableInfo.setName("ADJUST");
        dimTableInfo.setGuid(dimTableInfo.getName());
        dimTableInfo.setPhysicalTableName(dimTableInfo.getName());
        dimTableInfo.setPhysicalSchemaName(tableInfo.getDsvName());
        dimTableInfo.setTitle("\u8c03\u6574\u671f");
        FieldInfo codeField = this.fieldInfoAdapter.getDimFieldInfo(context, dsv, "CODE", "\u4ee3\u7801", dimTableInfo.getName());
        FieldInfo titleField = this.fieldInfoAdapter.getDimFieldInfo(context, dsv, "TITLE", "\u6807\u9898", dimTableInfo.getName());
        codeField.setKey(true);
        codeField.setKeyField(codeField.getName());
        codeField.setNameField(titleField.getName());
        titleField.setKeyField(codeField.getName());
        titleField.setNameField(titleField.getName());
        dimTableInfo.getFields().add(codeField);
        dimTableInfo.getFields().add(titleField);
        return dimTableInfo;
    }

    protected TableInfo createDimTableInfo(TableInfo tableInfo) {
        TableInfo dimTableInfo = new TableInfo();
        dimTableInfo.setType(TableType.DIM);
        dimTableInfo.setConnName(tableInfo.getConnName());
        dimTableInfo.setDsvName(tableInfo.getDsvName());
        return dimTableInfo;
    }

    private void buildRelation(DSVContext context, TableInfo tableInfo, TableInfo dimTableInfo, String dataKeyFieldCode, DataField dataKeyField, IEntityDefine entityDefine) {
        RelationInfo relationInfo = new RelationInfo();
        relationInfo.setMode(RelationMode.DIM_REFERENCE);
        relationInfo.setSrcTable(tableInfo.getName());
        relationInfo.setTargetTable(dimTableInfo.getName());
        relationInfo.getFieldMaps().put(dataKeyFieldCode, dimTableInfo.getKeyField().getName());
        if (entityDefine != null && entityDefine.isTree().booleanValue() && (!context.getMainDimensionEntityId().equals(entityDefine.getId()) || dataKeyField.isOnlyLeaf())) {
            relationInfo.setHierarchyDataMode(HierarchyDataMode.DETAIL_ONLY);
        }
        tableInfo.getRelations().add(relationInfo);
    }

    private boolean adaptPeriodDimension(DSVContext context, DSV dsv, TableInfo tableInfo, String entityId, IPeriodEntityAdapter periodAdapter, TableInfo dimTableInfo) throws TimeCalcException {
        Date updateTime;
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(entityId);
        if (periodEntity == null) {
            return false;
        }
        String dimensionName = this.getPeriodDimName(periodEntity);
        TableModelDefine periodEntityTableModel = periodAdapter.getPeriodEntityTableModel(entityId);
        dimTableInfo.setGuid(entityId);
        dimTableInfo.setName(dimensionName);
        dimTableInfo.setTitle(periodEntity.getTitle());
        dimTableInfo.setPhysicalTableName(periodEntityTableModel.getCode());
        dimTableInfo.setPhysicalSchemaName(tableInfo.getDsvName());
        if (periodEntity.getType() == PeriodType.MONTH) {
            dimTableInfo.setMinFiscalMonth(periodEntity.getMinFiscalMonth());
            dimTableInfo.setMaxFiscalMonth(periodEntity.getMaxFiscalMonth());
        }
        if ((updateTime = periodEntityTableModel.getUpdateTime()) != null) {
            dimTableInfo.setTimeStamp(updateTime.getTime());
        }
        this.addPeriodFieldInfos(context, dsv, dimTableInfo, periodEntity, periodEntityTableModel);
        FieldInfo keyField = dimTableInfo.getKeyField();
        if (keyField != null) {
            KeyInfo keyInfo = new KeyInfo();
            keyInfo.getFields().add(keyField.getName());
            keyInfo.setName(dimTableInfo.getName() + "_key");
            dimTableInfo.getKeys().add(keyInfo);
        }
        return true;
    }

    private boolean adaptCustomPeriodDimension(DSVContext context, DSV dsv, TableInfo tableInfo, String entityId, IPeriodEntityAdapter periodAdapter, TableInfo dimTableInfo) {
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(entityId);
        if (periodEntity == null) {
            return false;
        }
        String dimensionName = this.getPeriodDimName(periodEntity);
        TableModelDefine periodEntityTableModel = context.getPeriodDimensionInfo().getTableModel();
        String tableId = periodEntityTableModel.getID();
        dimTableInfo.setGuid(entityId);
        dimTableInfo.setName(dimensionName);
        dimTableInfo.setTitle(periodEntity.getTitle());
        dimTableInfo.setPhysicalTableName(periodEntityTableModel.getCode());
        dimTableInfo.setPhysicalSchemaName(tableInfo.getDsvName());
        Date updateTime = periodEntityTableModel.getUpdateTime();
        if (updateTime != null) {
            dimTableInfo.setTimeStamp(updateTime.getTime());
        }
        ColumnModelDefine codeColumn = this.dataModelService.getColumnModelDefineByCode(tableId, PeriodTableColumn.CODE.getCode());
        ColumnModelDefine titleColumn = this.dataModelService.getColumnModelDefineByCode(tableId, PeriodTableColumn.TITLE.getCode());
        FieldInfo timeCodeFieldInfo = this.fieldInfoAdapter.getFieldInfoByColumnModel(context, dsv, codeColumn, periodEntityTableModel.getCode(), false, context.isCustomPeriod());
        FieldInfo timeTitleFieldInfo = this.fieldInfoAdapter.getFieldInfoByColumnModel(context, dsv, titleColumn, periodEntityTableModel.getCode(), false, context.isCustomPeriod());
        timeCodeFieldInfo.setTitle("\u65f6\u671f\u4ee3\u7801");
        timeCodeFieldInfo.setKey(true);
        timeCodeFieldInfo.setKeyField(timeCodeFieldInfo.getName());
        timeCodeFieldInfo.setNameField(timeTitleFieldInfo.getName());
        timeCodeFieldInfo.setFieldType(FieldType.GENERAL_DIM);
        dimTableInfo.getFields().add(timeCodeFieldInfo);
        timeTitleFieldInfo.setTitle("\u65f6\u671f");
        timeTitleFieldInfo.setKeyField(timeCodeFieldInfo.getName());
        timeTitleFieldInfo.setNameField(timeTitleFieldInfo.getName());
        timeTitleFieldInfo.setFieldType(FieldType.GENERAL_DIM);
        dimTableInfo.getFields().add(timeTitleFieldInfo);
        KeyInfo keyInfo = new KeyInfo();
        keyInfo.getFields().add(timeCodeFieldInfo.getName());
        keyInfo.setName(dimTableInfo.getName() + "_key");
        dimTableInfo.getKeys().add(keyInfo);
        return true;
    }

    protected void addPeriodFieldInfos(DSVContext context, DSV dsv, TableInfo dimTableInfo, IPeriodEntity periodEntity, TableModelDefine periodEntityTableModel) throws TimeCalcException {
        ColumnModelDefine columnModel;
        String tableId = periodEntityTableModel.getID();
        String tableModelCode = periodEntityTableModel.getCode();
        List timeDimFields = BqlTimeDimUtils.getTimeDimFields((String)periodEntity.getKey(), (PeriodType)periodEntity.getPeriodType());
        for (TimeDimField timeDimField : timeDimFields) {
            columnModel = this.dataModelService.getColumnModelDefineByCode(tableId, timeDimField.getName());
            FieldInfo timeFieldInfo = this.fieldInfoAdapter.getFieldInfoByColumnModel(context, dsv, columnModel, tableModelCode, false, context.isCustomPeriod());
            if (timeDimField.isTimeKey()) {
                timeFieldInfo.setTimeKey(true);
                timeFieldInfo.setFieldType(FieldType.TIME_DIM);
                timeFieldInfo.setTimegranularity(BqlTimeDimUtils.adaptTimeGranularity((PeriodType)periodEntity.getPeriodType()));
                timeFieldInfo.setTitle(timeDimField.getTitle());
                timeFieldInfo.setKey(true);
                timeFieldInfo.setDataPattern("yyyyMMdd");
                if (timeFieldInfo.getTimegranularity() != null) {
                    timeFieldInfo.setShowPattern(BqlTimeDimUtils.getTimeKeyFieldShowPattern((TimeGranularity)timeFieldInfo.getTimegranularity(), (String)periodEntity.getKey()));
                }
            } else {
                timeFieldInfo.setFieldType(FieldType.TIME_DIM);
                timeFieldInfo.setTimegranularity(timeDimField.getTimeGranularity());
                timeFieldInfo.setTitle(timeDimField.getTitle());
                timeFieldInfo.setShowPattern(TimeHelper.getDefaultShowPattern((int)timeDimField.getTimeGranularity().value(), (boolean)false));
            }
            dimTableInfo.getFields().add(timeFieldInfo);
        }
        columnModel = this.dataModelService.getColumnModelDefineByCode(tableId, PeriodTableColumn.DAYS.getCode());
        FieldInfo daysFieldInfo = this.fieldInfoAdapter.getFieldInfoByColumnModel(context, dsv, columnModel, tableModelCode, false, context.isCustomPeriod());
        daysFieldInfo.setFieldType(FieldType.DESCRIPTION);
        daysFieldInfo.setName("SYS_DAYS");
        daysFieldInfo.setVisible(false);
        dimTableInfo.getFields().add(daysFieldInfo);
        columnModel = this.dataModelService.getColumnModelDefineByCode(tableId, PeriodTableColumn.ENDDATE.getCode());
        FieldInfo endDateFieldInfo = this.fieldInfoAdapter.getFieldInfoByColumnModel(context, dsv, columnModel, tableModelCode, false, context.isCustomPeriod());
        endDateFieldInfo.setFieldType(FieldType.DESCRIPTION);
        endDateFieldInfo.setName("SYS_LASTDAY");
        endDateFieldInfo.setVisible(false);
        endDateFieldInfo.setDataType(6);
        dimTableInfo.getFields().add(endDateFieldInfo);
        columnModel = this.dataModelService.getColumnModelDefineByCode(tableId, PeriodTableColumn.CODE.getCode());
        FieldInfo codeFieldInfo = this.fieldInfoAdapter.getFieldInfoByColumnModel(context, dsv, columnModel, tableModelCode, false, context.isCustomPeriod());
        codeFieldInfo.setFieldType(FieldType.DESCRIPTION);
        dimTableInfo.getFields().add(codeFieldInfo);
        for (FieldInfo fieldInfo : dimTableInfo.getFields()) {
            if (fieldInfo.getFieldType() != FieldType.TIME_DIM) continue;
            fieldInfo.setKeyField(fieldInfo.getName());
            fieldInfo.setNameField(fieldInfo.getName());
        }
    }

    private String getPeriodDimName(IPeriodEntity periodEntity) {
        return BqlTimeDimUtils.getBqlTimeDimTable((String)periodEntity.getCode());
    }

    private boolean adaptEntityDimension(DSVContext context, DSV dsv, TableInfo tableInfo, String entityId, TableInfo dimTableInfo, IEntityDefine entityDefine, DataField keyField) throws JQException {
        KeyInfo keyInfo;
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        if (entityModel == null) {
            return false;
        }
        TableModelDefine entityTableModel = this.entityMetaService.getTableModel(entityId);
        if (entityTableModel == null) {
            return false;
        }
        dimTableInfo.setGuid(dimTableInfo.getName());
        dimTableInfo.setPhysicalTableName(entityDefine.getCode());
        dimTableInfo.setPhysicalSchemaName(tableInfo.getDsvName());
        Date updateTime = entityTableModel.getUpdateTime();
        if (updateTime != null) {
            dimTableInfo.setTimeStamp(updateTime.getTime());
        }
        boolean isMainDim = entityModel.getEntityId().equals(context.getMainDimensionEntityId());
        ArrayList<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();
        if (isMainDim && context.hasUnitScope()) {
            HashSet<String> nameSet = new HashSet<String>();
            for (String scopeEntityKey : context.getUnitScopeEntityKeys()) {
                TableModelDefine scopeEntityTableModel;
                IEntityDefine scopeEntityDefine;
                IEntityModel scopeEntityModel = this.entityMetaService.getEntityModel(scopeEntityKey);
                if (scopeEntityModel == null || (scopeEntityDefine = this.entityMetaService.queryEntity(scopeEntityKey)) == null || (scopeEntityTableModel = this.entityMetaService.getTableModel(scopeEntityKey)) == null) continue;
                this.fieldInfoAdapter.getDimFieldInfosByTableModel(context, dsv, entityTableModel.getCode(), scopeEntityTableModel, scopeEntityModel, fieldInfos, nameSet);
            }
        } else {
            this.fieldInfoAdapter.getDimFieldInfosByTableModel(context, dsv, entityTableModel.getCode(), entityTableModel, entityModel, fieldInfos, null);
        }
        dimTableInfo.getFields().addAll(fieldInfos);
        if (entityDefine.isTree().booleanValue() && entityModel.getParentField() != null && !keyField.isOnlyLeaf()) {
            HierarchyInfo hierarchy = new HierarchyInfo();
            hierarchy.setKeyField(dimTableInfo.getKeyField().getName());
            hierarchy.setName(dimTableInfo.getName() + "_TREE");
            hierarchy.setTitle(entityDefine.getTitle() + "_\u6811\u578b");
            hierarchy.setParentField(entityModel.getParentField().getCode());
            dimTableInfo.getHierarchies().add(hierarchy);
        }
        if (entityTableModel.getDictType() == TableDictType.ZIPPER) {
            keyInfo = new KeyInfo();
            keyInfo.getFields().add("SID");
            keyInfo.setName(dimTableInfo.getName() + "_" + "SID");
            dimTableInfo.getKeys().add(keyInfo);
        }
        keyInfo = new KeyInfo();
        keyInfo.getFields().add(entityModel.getBizKeyField().getCode());
        keyInfo.setName(dimTableInfo.getName() + "_key");
        dimTableInfo.getKeys().add(keyInfo);
        if (this.calibreDimTableProvider != null && entityId.equals(context.getMainDimensionEntityId())) {
            try {
                List<CalibreDimTable> calibreDimTableList;
                FieldInfo objectFieldInfo = dimTableInfo.findField("OBJECTCODE");
                if (objectFieldInfo == null) {
                    FieldInfo dimKeyField = dimTableInfo.getKeyField();
                    objectFieldInfo = dimKeyField.clone();
                    objectFieldInfo.setName("OBJECTCODE");
                    objectFieldInfo.setKey(false);
                    objectFieldInfo.setPhysicalName(dimKeyField.getName());
                    dimTableInfo.getFields().add(objectFieldInfo);
                }
                if ((calibreDimTableList = this.calibreDimTableProvider.getCalibreDimTableList(context.getDataScheme())) != null) {
                    for (CalibreDimTable calibreDimTable : calibreDimTableList) {
                        try {
                            this.addMappingDimTable(context, dsv, dimTableInfo, entityModel, calibreDimTable);
                        }
                        catch (Exception e) {
                            logger.error("\u6570\u636e\u65b9\u6848\uff1a" + context.getDataScheme().getCode() + "    \u6620\u5c04\u7ef4\u5ea6\u8868 \u3010 " + calibreDimTable.getTableCode() + " to " + dimTableInfo.getName() + " \u51fa\u9519\uff01" + e.getMessage(), (Throwable)e);
                        }
                    }
                }
            }
            catch (Exception ee) {
                logger.error("\u6570\u636e\u65b9\u6848\uff1a" + context.getDataScheme().getCode() + "    \u6620\u5c04\u7ef4\u5ea6\u8868\u51fa\u9519\uff01" + ee.getMessage(), (Throwable)ee);
            }
        }
        return true;
    }

    private boolean addMappingDimTable(DSVContext context, DSV dsv, TableInfo dimTableInfo, IEntityModel dimEntityModel, CalibreDimTable calibreDimTable) throws JQException {
        String mappingTableCode = calibreDimTable.getTableCode();
        TableInfo mappingTableInfo = new TableInfo();
        mappingTableInfo.setType(TableType.DIM);
        mappingTableInfo.setConnName(dimTableInfo.getConnName());
        mappingTableInfo.setDsvName(dimTableInfo.getDsvName());
        mappingTableInfo.setGuid(mappingTableCode);
        mappingTableInfo.setName(mappingTableCode);
        mappingTableInfo.setTitle(mappingTableCode);
        mappingTableInfo.setPhysicalTableName(mappingTableCode);
        mappingTableInfo.setPhysicalSchemaName(dsv.getName());
        mappingTableInfo.setTimeStamp(dimTableInfo.getTimeStamp());
        String keyFieldName = calibreDimTable.getType() == 1 ? "OBJECTCODE" : "CODE";
        FieldInfo mappingFieldInfo = this.createMappingFieldInfo(dimTableInfo, mappingTableCode, keyFieldName, keyFieldName);
        mappingTableInfo.getFields().add(mappingFieldInfo);
        mappingFieldInfo = this.createMappingFieldInfo(dimTableInfo, mappingTableCode, "NAME", keyFieldName);
        mappingTableInfo.getFields().add(mappingFieldInfo);
        FieldInfo parentsFieldInfo = this.createMappingFieldInfo(dimTableInfo, mappingTableCode, "PARENTCODE", keyFieldName);
        if (parentsFieldInfo != null) {
            mappingTableInfo.getFields().add(mappingFieldInfo);
            HierarchyInfo hierarchy = new HierarchyInfo();
            hierarchy.setKeyField(keyFieldName);
            hierarchy.setName(mappingTableCode + "_TREE");
            hierarchy.setTitle(mappingTableCode + "_\u6811\u578b");
            hierarchy.setParentField(parentsFieldInfo.getName());
            mappingTableInfo.getHierarchies().add(hierarchy);
        }
        KeyInfo keyInfo = new KeyInfo();
        keyInfo.getFields().add(keyFieldName);
        keyInfo.setName(mappingTableCode + "_key");
        mappingTableInfo.getKeys().add(keyInfo);
        dsv.getTables().add(mappingTableInfo);
        dsv.getTableMappings().put(mappingTableInfo.getName(), dimTableInfo.getName());
        return true;
    }

    protected FieldInfo createMappingFieldInfo(TableInfo dimTableInfo, String mappingTableCode, String fieldName, String keyFieldName) {
        FieldInfo dimFieldInfo = dimTableInfo.findField(fieldName);
        FieldInfo mappingFieldInfo = dimFieldInfo.clone();
        mappingFieldInfo.setTableName(mappingTableCode);
        mappingFieldInfo.setKeyField(keyFieldName);
        mappingFieldInfo.setNameField("NAME");
        return mappingFieldInfo;
    }
}

