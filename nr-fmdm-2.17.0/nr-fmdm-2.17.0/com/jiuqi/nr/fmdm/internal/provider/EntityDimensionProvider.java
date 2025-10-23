/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.update.UpdateDataColumn
 *  com.jiuqi.np.dataengine.update.UpdateDataRecord
 *  com.jiuqi.np.dataengine.update.UpdateDataTable
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.provider.DimensionColumn
 *  com.jiuqi.np.definition.provider.DimensionMetaData
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.fmdm.internal.provider;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.update.UpdateDataColumn;
import com.jiuqi.np.dataengine.update.UpdateDataRecord;
import com.jiuqi.np.dataengine.update.UpdateDataTable;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.provider.DimensionColumn;
import com.jiuqi.np.definition.provider.DimensionMetaData;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.fmdm.BatchFMDMDTO;
import com.jiuqi.nr.fmdm.FMDMAttributeDTO;
import com.jiuqi.nr.fmdm.FMDMDataDTO;
import com.jiuqi.nr.fmdm.IFMDMAttribute;
import com.jiuqi.nr.fmdm.IFMDMAttributeService;
import com.jiuqi.nr.fmdm.IFMDMData;
import com.jiuqi.nr.fmdm.IFMDMDataService;
import com.jiuqi.nr.fmdm.common.Utils;
import com.jiuqi.nr.fmdm.exception.FMDMQueryException;
import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;
import com.jiuqi.nr.fmdm.internal.provider.fmdm.EntityTable;
import com.jiuqi.nr.fmdm.internal.provider.fmdm.FMDMRow;
import com.jiuqi.nr.fmdm.internal.provider.fmdm.FMDMTable;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Primary
public class EntityDimensionProvider
implements IDimensionProvider {
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFMDMDataService fmdmDataService;
    @Autowired
    private IFMDMAttributeService fmdmAttributeService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DataModelService dataModelService;
    private static final Logger logger = LoggerFactory.getLogger(EntityDimensionProvider.class);
    private static final String ISOLATECONDITION_VARNAME = "ISOLATECONDITION";

    public String getFieldDimensionName(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, FieldDefine fieldDefine) {
        String entityKey = fieldDefine.getEntityKey();
        if (StringUtils.hasText(entityKey)) {
            if (!entityKey.startsWith("MD_")) {
                return "DATATIME";
            }
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityKey);
            return entityDefine != null ? this.getDimensionNameByEntityTableCode(executorContext, entityDefine.getCode()) : null;
        }
        return this.entityMetaService.getDimensionName(fieldDefine.getEntityKey());
    }

    public String getFieldDimensionName(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, ColumnModelDefine columnModelDefine) {
        String referTable = columnModelDefine.getReferTableID();
        if (!StringUtils.hasText(referTable)) {
            return null;
        }
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineById(referTable);
        return tableModelDefine != null ? this.getDimensionNameByEntityTableCode(executorContext, tableModelDefine.getCode()) : null;
    }

    public String getDimensionNameByEntityId(String entityId) {
        String dimensionName;
        if ("ADJUST".equals(entityId)) {
            return entityId;
        }
        if (this.periodEntityAdapter.isPeriodEntity(entityId)) {
            IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(entityId);
            dimensionName = periodEntity.getDimensionName();
        } else {
            dimensionName = this.entityMetaService.getDimensionName(entityId);
        }
        return dimensionName;
    }

    public String getDimensionNameByEntityTableCode(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, String entityTableCode) {
        if (!entityTableCode.startsWith("MD_")) {
            return "DATATIME";
        }
        return this.entityMetaService.getDimensionNameByCode(entityTableCode);
    }

    public String getDimensionTableName(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, String dimensionName) {
        String tableName;
        FormSchemeDefine formSchemeDefine;
        if ("ADJUST".equals(dimensionName)) {
            return "NR_ADJUST_PERIOD";
        }
        IEntityDefine entityDefine = this.entityMetaService.queryEntityByCode(dimensionName);
        if (entityDefine.getIncludeSubTreeEntity() == 1 && (formSchemeDefine = this.getFormSchemeDefine(executorContext)) != null) {
            String dw = Utils.getEntityId(formSchemeDefine);
            if (!StringUtils.hasText(dw)) {
                TaskDefine taskDefine = this.getTaskDefine(executorContext);
                if (taskDefine == null) {
                    throw new RuntimeException("\u627e\u4e0d\u5230\u7684\u4efb\u52a1");
                }
                dw = taskDefine.getDw();
            }
            entityDefine = this.entityMetaService.queryEntity(dw);
        }
        if (dimensionName.startsWith("MD_ORG_") || dimensionName.startsWith("MD_")) {
            tableName = entityDefine.getCode();
        } else {
            TableModelDefine tableModel = this.entityMetaService.getTableModel(entityDefine.getId());
            tableName = tableModel.getName();
        }
        return tableName;
    }

    public DimensionTable getDimensionTableByEntityId(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, String entityId, PeriodWrapper period, Object dimValue) {
        IEntityDefine entityDefine;
        String orgEntityId;
        String queryEntityId = entityId;
        String contextDim = executorContext.getUnitDimension();
        String entityDim = this.entityMetaService.getDimensionName(entityId);
        if (StringUtils.hasText(contextDim) && StringUtils.hasText(entityDim) && contextDim.equals(entityDim) && StringUtils.hasText(orgEntityId = executorContext.getOrgEntityId())) {
            queryEntityId = orgEntityId;
        }
        if ((entityDefine = this.entityMetaService.queryEntity(queryEntityId)) == null) {
            return null;
        }
        return this.getEntityData(queryEntityId, executorContext, period, dimValue);
    }

    public DimensionTable getDimensionTableByEntityId(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, String entityId, PeriodWrapper period, Object dimValue, String linkAlias) {
        if (StringUtils.hasText(linkAlias)) {
            return this.getEntityData(entityId, executorContext, period, dimValue);
        }
        return this.getDimensionTableByEntityId(executorContext, entityId, period, dimValue);
    }

    private FormSchemeDefine getFormSchemeDefine(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext) {
        IFmlExecEnvironment env = executorContext.getEnv();
        ReportFmlExecEnvironment reportEnv = null;
        if (env instanceof ReportFmlExecEnvironment) {
            reportEnv = (ReportFmlExecEnvironment)env;
        }
        FormSchemeDefine formSchemeDefine = null;
        if (reportEnv != null) {
            try {
                formSchemeDefine = reportEnv.getFormSchemeDefine();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return formSchemeDefine;
    }

    private TaskDefine getTaskDefine(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext) {
        IFmlExecEnvironment env = executorContext.getEnv();
        ReportFmlExecEnvironment reportEnv = null;
        if (env instanceof ReportFmlExecEnvironment) {
            reportEnv = (ReportFmlExecEnvironment)env;
        }
        TaskDefine taskDefine = null;
        if (reportEnv != null) {
            try {
                taskDefine = reportEnv.getTaskDefine();
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return taskDefine;
    }

    public void writeDimensionTable(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, UpdateDataTable updateDataTable, PeriodWrapper period) {
        TableModelDefine tableModel;
        String tableName;
        FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(executorContext);
        if (formSchemeDefine == null) {
            return;
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formSchemeDefine.getTaskKey());
        String entityId = executorContext.getOrgEntityId();
        if (!StringUtils.hasText(entityId)) {
            entityId = taskDefine.getDw();
        }
        if ((tableName = updateDataTable.getTableName()).equalsIgnoreCase((tableModel = this.entityMetaService.getTableModel(entityId)).getName())) {
            this.executeWrite(formSchemeDefine.getKey(), updateDataTable, period);
        }
    }

    private void executeWrite(String formSchemeKey, UpdateDataTable updateDataTable, PeriodWrapper period) {
        List insertRecords;
        Map updateRecords;
        List deleteRecords = updateDataTable.getDeleteRecords();
        if (!CollectionUtils.isEmpty(deleteRecords)) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            DimensionValueSet dimensionValueSet = this.getDimensionKeys(deleteRecords, Utils.getEntityId(formScheme), period.toString());
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
            FMDMDataDTO dto = new FMDMDataDTO();
            dto.setFormSchemeKey(formSchemeKey);
            dto.setDimensionCombination(dimensionCombinationBuilder.getCombination());
            dto.setDataMasking(false);
            try {
                this.fmdmDataService.delete(dto);
            }
            catch (FMDMUpdateException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (!CollectionUtils.isEmpty(updateRecords = updateDataTable.getUpdateRecords())) {
            BatchFMDMDTO batchUpdate = this.setUpdateData(formSchemeKey, period, new ArrayList<UpdateDataRecord>(updateRecords.values()));
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            DimensionValueSet dimensionValueSet = this.getDimensionKeys(new ArrayList<UpdateDataRecord>(updateRecords.values()), Utils.getEntityId(formScheme), period.toString());
            batchUpdate.setDimensionValueSet(dimensionValueSet);
            try {
                if (!CollectionUtils.isEmpty(batchUpdate.getFmdmList())) {
                    this.fmdmDataService.batchUpdateFMDM(batchUpdate);
                }
            }
            catch (FMDMUpdateException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (!CollectionUtils.isEmpty(insertRecords = updateDataTable.getInsertRecords())) {
            BatchFMDMDTO batchInsert = this.setUpdateData(formSchemeKey, period, insertRecords);
            try {
                this.fmdmDataService.batchAddFMDM(batchInsert);
            }
            catch (FMDMUpdateException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private BatchFMDMDTO setUpdateData(String formSchemeKey, PeriodWrapper period, List<UpdateDataRecord> updateDataRecords) {
        BatchFMDMDTO batchFMDMDTO = new BatchFMDMDTO();
        for (UpdateDataRecord updateDataRecord : updateDataRecords) {
            if (!updateDataRecord.isModified()) continue;
            FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
            fmdmDataDTO.setFormulaSchemeKey(formSchemeKey);
            DimensionValueSet rowkeys = updateDataRecord.getRowkeys();
            rowkeys.setValue("DATATIME", (Object)period.toString());
            DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(rowkeys);
            fmdmDataDTO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
            Collection updateColumns = updateDataRecord.getUpdateColumns();
            for (UpdateDataColumn updateColumn : updateColumns) {
                if (!updateColumn.isModified()) continue;
                fmdmDataDTO.setValue(updateColumn.getName(), updateColumn.getValue());
            }
            batchFMDMDTO.addFmdmUpdateDTO(fmdmDataDTO);
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period.toString());
        batchFMDMDTO.setDimensionValueSet(dimensionValueSet);
        batchFMDMDTO.setFormSchemeKey(formSchemeKey);
        batchFMDMDTO.setDataMasking(false);
        return batchFMDMDTO;
    }

    private DimensionValueSet getDimensionKeys(List<UpdateDataRecord> updateDataRecords, String entityId, String period) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        String dimensionName = this.entityMetaService.getDimensionName(entityId);
        ArrayList<String> mainKeys = new ArrayList<String>();
        for (UpdateDataRecord updateDataRecord : updateDataRecords) {
            Object value;
            if (updateDataRecord.getRowkeys() == null || (value = updateDataRecord.getRowkeys().getValue(dimensionName)) == null || "".equals(value.toString())) continue;
            mainKeys.add(value.toString());
        }
        dimensionValueSet.setValue(dimensionName, mainKeys);
        return dimensionValueSet;
    }

    private List<IEntityRow> getEntityRows(String entityId, com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, DimensionValueSet masterKeys) {
        List<Object> allRows = new ArrayList<IEntityRow>();
        try {
            IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
            iEntityQuery.setMasterKeys(masterKeys);
            iEntityQuery.setEntityView(this.iEntityViewRunTimeController.buildEntityView(entityId));
            Variable variable = executorContext.getVariableManager().find(ISOLATECONDITION_VARNAME);
            if (variable != null) {
                iEntityQuery.setIsolateCondition((String)variable.getVarValue((IContext)executorContext));
            }
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            String periodView = executorContext.getPeriodView();
            context.setPeriodView(periodView);
            VariableManager variableManager = context.getVariableManager();
            for (Variable var : executorContext.getVariableManager().getAllVars()) {
                variableManager.add(var);
            }
            IEntityTable entityTable = iEntityQuery.executeReader((IContext)context);
            allRows = entityTable.getAllRows();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return allRows;
    }

    private List<IFMDMData> getFMDMRows(String fromSchemeKey, DimensionValueSet dimensionValueSet) {
        DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(dimensionValueSet);
        FMDMDataDTO fmdmDataDTO = new FMDMDataDTO();
        fmdmDataDTO.setFormSchemeKey(fromSchemeKey);
        fmdmDataDTO.setDimensionCombination(dimensionCombinationBuilder.getCombination());
        fmdmDataDTO.setDataMasking(false);
        List<IFMDMData> list = null;
        try {
            list = this.fmdmDataService.list(fmdmDataDTO);
        }
        catch (FMDMQueryException e) {
            logger.error(e.getMessage(), e);
        }
        return list;
    }

    public String getEntityIdByEntityTableCode(com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, String entityTableCode) {
        return this.entityMetaService.getEntityIdByCode(entityTableCode);
    }

    public DimensionTable getFMDMData(String formSchemeKey, PeriodWrapper period, Object dimValue) {
        FMDMAttributeDTO fmdmAttributeDTO = new FMDMAttributeDTO();
        fmdmAttributeDTO.setFormSchemeKey(formSchemeKey);
        List<IFMDMAttribute> attributes = null;
        try {
            attributes = this.fmdmAttributeService.list(fmdmAttributeDTO);
        }
        catch (FMDMQueryException e) {
            logger.error(e.getMessage(), e);
        }
        DimensionMetaData dimensionMetaData = new DimensionMetaData();
        HashMap<String, Integer> columnMap = new HashMap<String, Integer>();
        for (IFMDMAttribute attribute : attributes) {
            DimensionColumn column = dimensionMetaData.addColumn(attribute.getCode(), attribute.getColumnType().getValue());
            columnMap.put(attribute.getCode(), column.getIndex());
        }
        String entityId = null;
        String dimensionName = this.entityMetaService.getDimensionName(entityId);
        FMDMTable dimensionTable = new FMDMTable(dimensionName, dimensionMetaData);
        DimensionValueSet masterKeys = this.getDimension(period, dimValue, dimensionName);
        List<IFMDMData> fmdmRows = this.getFMDMRows(formSchemeKey, masterKeys);
        if (!CollectionUtils.isEmpty(fmdmRows)) {
            IFMDMAttribute keyField = null;
            IFMDMAttribute codeField = null;
            IFMDMAttribute titleField = null;
            IFMDMAttribute parentField = null;
            try {
                keyField = this.fmdmAttributeService.getFMDMBizField(fmdmAttributeDTO);
                codeField = this.fmdmAttributeService.getFMDMCodeField(fmdmAttributeDTO);
                titleField = this.fmdmAttributeService.getFMDMTitleField(fmdmAttributeDTO);
                parentField = this.fmdmAttributeService.getFMDMParentField(fmdmAttributeDTO);
            }
            catch (FMDMQueryException e) {
                logger.error(e.getMessage(), e);
            }
            for (IFMDMData fmdmRow : fmdmRows) {
                if (keyField == null || codeField == null || titleField == null || parentField == null) continue;
                AbstractData keyValue = fmdmRow.getValue(keyField.getCode());
                if (keyValue.isNull) continue;
                AbstractData codeValue = fmdmRow.getValue(codeField.getCode());
                if (codeValue.isNull) continue;
                AbstractData titleValue = fmdmRow.getValue(titleField.getCode());
                AbstractData parentValue = fmdmRow.getValue(parentField.getCode());
                FMDMRow dimensionRow = dimensionTable.addRow(keyValue.getAsString(), codeValue.getAsString(), fmdmRow);
                dimensionRow.setTitle(titleValue.isNull ? "" : titleValue.getAsString());
                dimensionRow.setParentKey(parentValue.isNull ? "" : titleValue.getAsString());
            }
        }
        dimensionTable.setTableKey(entityId);
        dimensionTable.setTableCode(this.entityMetaService.getEntityCode(entityId));
        return dimensionTable;
    }

    private DimensionValueSet getDimension(PeriodWrapper period, Object dimValue, String dimName) {
        DimensionValueSet masterKeys = new DimensionValueSet();
        if (period != null) {
            masterKeys.setValue("DATATIME", (Object)period.toString());
        }
        if (dimValue != null) {
            masterKeys.setValue(dimName, dimValue);
        }
        return masterKeys;
    }

    public DimensionTable getEntityData(String entityId, com.jiuqi.np.dataengine.executors.ExecutorContext executorContext, PeriodWrapper period, Object dimValue) {
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        DimensionMetaData dimensionMetaData = new DimensionMetaData();
        HashMap<Integer, Integer> columnMap = new HashMap<Integer, Integer>();
        if (entityModel != null) {
            int attributeIndex = 0;
            Iterator attributes = entityModel.getAttributes();
            while (attributes.hasNext()) {
                IEntityAttribute next = (IEntityAttribute)attributes.next();
                DimensionColumn column = dimensionMetaData.addColumn(next.getCode(), next.getColumnType().getValue());
                columnMap.put(attributeIndex, column.getIndex());
                ++attributeIndex;
            }
        }
        String dimensionName = this.entityMetaService.getDimensionName(entityId);
        EntityTable entityTable = new EntityTable(dimensionName, dimensionMetaData);
        DimensionValueSet masterKeys = this.getDimension(period, dimValue, dimensionName);
        List<IEntityRow> entityRows = this.getEntityRows(entityId, executorContext, masterKeys);
        if (!CollectionUtils.isEmpty(entityRows)) {
            for (IEntityRow entityRow : entityRows) {
                entityTable.addRow(entityRow.getEntityKeyData(), entityRow.getCode(), entityRow);
            }
        }
        entityTable.setTableKey(entityId);
        entityTable.setTableCode(this.entityMetaService.getEntityCode(entityId));
        return entityTable;
    }
}

