/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.exception.CreateSystemTableException
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.CreateSystemTableException;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaDataModelCreateUtil {
    private static final Logger logger = LoggerFactory.getLogger(NvwaDataModelCreateUtil.class);
    @Autowired
    IDesignTimeViewController designTimeViewController;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private CatalogModelService catalogModelService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    public static final String SPLIT_CHAR = ";";
    public static final String DW_FIELD = "MDCODE";
    public static final String PERIOD_FIELD = "PERIOD";

    public String initField(String tableKey, String fieldCode, String referField, ColumnModelType columnModelType, int size, boolean nullable, Map<String, DesignColumnModelDefine> fieldMap, String referTableId) {
        return this.initField(tableKey, fieldCode, referField, columnModelType, size, nullable, fieldMap, referTableId, null);
    }

    public String initField(String tableKey, String fieldCode, String referField, ColumnModelType columnModelType, int size, boolean nullable, Map<String, DesignColumnModelDefine> fieldMap, String referTableId, String defaultValue) {
        DesignColumnModelDefine fieldDefine = null;
        boolean doInsert = true;
        DesignColumnModelDefine fieldDefineByCode = fieldMap.get(fieldCode);
        if (fieldDefineByCode != null) {
            doInsert = false;
            fieldDefine = fieldDefineByCode;
        }
        if (fieldDefineByCode == null) {
            fieldDefine = this.createField();
        }
        fieldDefine.setCode(fieldCode);
        fieldDefine.setName(fieldCode);
        fieldDefine.setColumnType(columnModelType);
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(size);
        fieldDefine.setNullAble(nullable);
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        if (referTableId != null) {
            fieldDefine.setReferTableID(referTableId);
        }
        if (null != defaultValue) {
            fieldDefine.setDefaultValue(defaultValue);
        }
        if (doInsert) {
            try {
                this.designDataModelService.insertColumnModelDefine(fieldDefine);
            }
            catch (Exception e) {
                throw new CreateSystemTableException(String.format("insert field %s error.", fieldCode), (Throwable)e);
            }
        } else if (!PERIOD_FIELD.equals(fieldDefine.getCode())) {
            this.updateField(fieldDefine);
        }
        return fieldDefine.getID();
    }

    public String initEntityAndPeriodField(String tableKey, String fieldCode, ColumnModelType columnModelType, String referField, int size, List<DesignColumnModelDefine> isnertColumns, List<DesignColumnModelDefine> modifyColumns) {
        DesignColumnModelDefine fieldDefine = null;
        DesignColumnModelDefine fieldDefineByCode = this.queryFieldDefinesByCode(fieldCode, tableKey);
        if (fieldDefineByCode != null) {
            fieldDefine = fieldDefineByCode;
            modifyColumns.add(fieldDefine);
        }
        if (fieldDefineByCode == null) {
            fieldDefine = this.createField();
            isnertColumns.add(fieldDefine);
        }
        fieldDefine.setCode(fieldCode);
        fieldDefine.setName(fieldCode);
        fieldDefine.setColumnType(columnModelType);
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(size);
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        return fieldDefine.getID();
    }

    public void insertField(DesignColumnModelDefine fieldDefine) {
        try {
            this.designDataModelService.insertColumnModelDefine(fieldDefine);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("insert field %s error.", fieldDefine.getCode()), (Throwable)e);
        }
    }

    public void insertFields(DesignColumnModelDefine[] fieldDefine) {
        try {
            this.designDataModelService.insertColumnModelDefines(fieldDefine);
        }
        catch (Exception e) {
            throw new CreateSystemTableException("batch insert field  error.", (Throwable)e);
        }
    }

    public void updateField(DesignColumnModelDefine fieldDefine) {
        try {
            this.designDataModelService.updateColumnModelDefine(fieldDefine);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("update field %s error.", fieldDefine.getCode()), (Throwable)e);
        }
    }

    public void updateFields(DesignColumnModelDefine[] fieldDefines) {
        try {
            this.designDataModelService.updateColumnModelDefines(fieldDefines);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("batch update fields error.", new Object[0]), (Throwable)e);
        }
    }

    public StringBuffer createBizkField(FormSchemeDefine formScheme, DesignTaskDefine taskDefine, DesignTableModelDefine tableDefine, Map<String, DesignColumnModelDefine> filedMap) {
        StringBuffer tableEnityMasterKeys = new StringBuffer();
        String referFieldId = null;
        String tableID = null;
        ColumnModelType columnModelType = ColumnModelType.STRING;
        String dateTime = taskDefine.getDateTime();
        referFieldId = this.queryPeriodBizKey(dateTime);
        columnModelType = ColumnModelType.STRING;
        String periodField = this.initField(tableDefine.getID(), PERIOD_FIELD, referFieldId, columnModelType, 9, false, filedMap, null);
        tableEnityMasterKeys.append(periodField).append(SPLIT_CHAR);
        String dw = taskDefine.getDw();
        IEntityDefine entityDefine = this.queryDimisionByView(dw);
        IEntityAttribute entityAttribute = this.queryEntityBizeKeyByEntityId(entityDefine.getId());
        referFieldId = entityAttribute.getID();
        columnModelType = entityAttribute.getColumnType();
        tableID = entityAttribute.getTableID();
        String dwFieldKey = this.initField(tableDefine.getID(), DW_FIELD, referFieldId, columnModelType, entityAttribute.getPrecision(), false, filedMap, tableID);
        tableEnityMasterKeys.append(dwFieldKey).append(SPLIT_CHAR);
        List reportDimensions = this.taskService.getReportDimension(formScheme.getTaskKey());
        IEntityModel dwEntityModel = this.iEntityMetaService.getEntityModel(taskDefine.getDw());
        if (reportDimensions != null && reportDimensions.size() > 0) {
            for (DataDimension dataDimension : reportDimensions) {
                DimensionType dimensionType = dataDimension.getDimensionType();
                String entityKey = dataDimension.getDimKey();
                if ("ADJUST".equals(entityKey)) {
                    String adjust = "ADJUST";
                    String fieldKey = this.initField(tableDefine.getID(), adjust, null, ColumnModelType.STRING, 50, false, filedMap, null, dataDimension.getDefaultValue());
                    tableEnityMasterKeys.append(fieldKey).append(SPLIT_CHAR);
                    continue;
                }
                if (!this.workFlowDimensionBuilder.isCorporate((TaskDefine)taskDefine, dataDimension, dwEntityModel)) continue;
                IEntityDefine entity = this.queryDimisionByView(entityKey);
                IEntityAttribute referField = this.queryEntityBizeKeyByEntityId(entity.getId());
                referFieldId = referField.getID();
                columnModelType = referField.getColumnType();
                tableID = referField.getTableID();
                String fieldKey = this.initField(tableDefine.getID(), entity.getDimensionName(), referFieldId, columnModelType, referField.getPrecision(), false, filedMap, tableID);
                tableEnityMasterKeys.append(fieldKey).append(SPLIT_CHAR);
            }
        } else if (filedMap.get("ADJUST") != null) {
            DesignColumnModelDefine designColumnModelDefine = filedMap.get("ADJUST");
            this.deleteFieldDefine(designColumnModelDefine.getID());
        }
        return tableEnityMasterKeys;
    }

    public void initDwPeriodDimField(String tableId, StringBuffer tableEnityMasterKeys, String[] entitiesKeyArr, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) {
        boolean flag = true;
        String referField = null;
        for (String entityKeyStr : entitiesKeyArr) {
            if (this.isPeriodView(entityKeyStr)) {
                referField = this.queryPeriodBizKey(entityKeyStr);
                tableEnityMasterKeys.append(this.initEntityAndPeriodField(tableId, PERIOD_FIELD, ColumnModelType.STRING, referField, 50, createFieldList, modifyFieldList)).append(SPLIT_CHAR);
                continue;
            }
            if (flag) {
                IEntityAttribute bizField = this.queryEntityTableBizKeyByView(entityKeyStr);
                referField = bizField.getID();
                tableEnityMasterKeys.append(this.initEntityAndPeriodField(tableId, DW_FIELD, ColumnModelType.STRING, referField, 50, createFieldList, modifyFieldList)).append(SPLIT_CHAR);
                flag = false;
                continue;
            }
            IEntityDefine entity = this.queryDimisionByView(entityKeyStr);
            IEntityAttribute referFieldCol = this.queryEntityBizeKeyByEntityId(entity.getId());
            if (referFieldCol != null) {
                referField = referFieldCol.getID();
            }
            tableEnityMasterKeys.append(this.initEntityAndPeriodField(tableId, entity.getDimensionName(), ColumnModelType.STRING, referField, 50, createFieldList, modifyFieldList)).append(SPLIT_CHAR);
        }
    }

    private IEntityDefine queryDimisionByView(String entityViewKey) {
        IEntityDefine entity = null;
        try {
            EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(entityViewKey);
            if (entityView != null && entityView.getEntityId() != null) {
                entity = this.iEntityMetaService.queryEntity(entityView.getEntityId());
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u7ef4\u5ea6\u51fa\u9519", e);
        }
        return entity;
    }

    public TableModelDefine queryTableModel(String entiviewKey) {
        IEntityDefine entity = this.queryDimisionByView(entiviewKey);
        return this.iEntityMetaService.getTableModel(entity.getId());
    }

    private IEntityAttribute queryEntityTableBizKeyByView(String entityViewKey) {
        IEntityDefine entity = null;
        try {
            EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(entityViewKey);
            if (entityView != null && entityView.getEntityId() != null) {
                entity = this.iEntityMetaService.queryEntity(entityView.getEntityId());
            }
            if (entity != null) {
                return this.queryEntityBizeKeyByEntityId(entity.getId());
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5b9e\u4f53\u5b9a\u4e49\u5bf9\u8c61\u51fa\u9519", e);
        }
        return null;
    }

    private IEntityAttribute queryEntityBizeKeyByEntityId(String entityId) {
        IEntityAttribute referFieldId = null;
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityId);
        referFieldId = entityModel.getBizKeyField();
        if (referFieldId == null) {
            referFieldId = entityModel.getRecordKeyField();
        }
        return referFieldId;
    }

    private IPeriodEntity queryPeriodEntity(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        EntityViewDefine viewDefine = this.iEntityViewRunTimeController.buildEntityView(viewKey);
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(viewDefine.getEntityId());
        return periodEntity;
    }

    private String queryPeriodBizKey(String viewKey) {
        IPeriodEntity periodEntity = this.queryPeriodEntity(viewKey);
        String referFieldId = this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(periodEntity.getKey()).getBizKeys();
        return referFieldId;
    }

    public boolean isPeriodView(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        return periodAdapter.isPeriodEntity(viewKey);
    }

    public DesignTableModelDefine initTableBase(String tableCode, String tableTitle) {
        DesignTableModelDefine tableDefine = null;
        try {
            tableDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query table %s error.", tableCode), (Throwable)e);
        }
        if (tableDefine != null) {
            return tableDefine;
        }
        tableDefine = this.createTableDefine();
        String ownerGroupID = null;
        try {
            ownerGroupID = this.createCatlogModelDefine().getID();
        }
        catch (Exception e) {
            throw new CreateSystemTableException("create tableGroup error ", (Throwable)e);
        }
        tableDefine.setCode(tableCode);
        tableDefine.setName(tableCode);
        tableDefine.setType(TableModelType.DEFAULT);
        tableDefine.setKind(TableModelKind.DEFAULT);
        tableDefine.setOwner("NR");
        tableDefine.setCatalogID(ownerGroupID);
        tableDefine.setTitle(tableTitle);
        return tableDefine;
    }

    public DesignColumnModelDefine queryFieldDefinesByCode(String fieldCode, String tableKey) {
        DesignColumnModelDefine fieldDefine = null;
        try {
            fieldDefine = this.designDataModelService.getColumnModelDefineByCode(tableKey, fieldCode);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query field by code %s error.", fieldCode), (Throwable)e);
        }
        return fieldDefine;
    }

    public ColumnModelDefine getRunColumnDefinesByCode(String fieldCode, String tableKey) {
        ColumnModelDefine fieldDefine = null;
        try {
            fieldDefine = this.dataModelService.getColumnModelDefineByCode(tableKey, fieldCode);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query field by code %s error.", fieldCode), (Throwable)e);
        }
        return fieldDefine;
    }

    public List<DesignColumnModelDefine> getAllFieldsInTable(String key) {
        ArrayList<DesignColumnModelDefine> fields = new ArrayList();
        try {
            fields = this.designDataModelService.getColumnModelDefinesByTable(key);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query fields %s error.", key), (Throwable)e);
        }
        return fields;
    }

    public Map<String, DesignColumnModelDefine> getFiledMap(String tableKey) {
        HashMap<String, DesignColumnModelDefine> fieldMap = new HashMap<String, DesignColumnModelDefine>();
        List<DesignColumnModelDefine> allFields = this.getAllFieldsInTable(tableKey);
        for (DesignColumnModelDefine designColumnModelDefine : allFields) {
            fieldMap.put(designColumnModelDefine.getCode(), designColumnModelDefine);
        }
        return fieldMap;
    }

    public DesignColumnModelDefine queryFieldDefine(String fieldId) {
        DesignColumnModelDefine field = null;
        try {
            field = this.designDataModelService.getColumnModelDefine(fieldId);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query field %s error.", fieldId), (Throwable)e);
        }
        return field;
    }

    public void deleteFieldDefine(String fieldKey) {
        try {
            this.designDataModelService.deleteColumnModelDefine(fieldKey);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("delete field %s error.", fieldKey), (Throwable)e);
        }
    }

    public List<FormSchemeDefine> getFormSchemeList(TaskDefine task) {
        ArrayList<FormSchemeDefine> formSchemeList = new ArrayList();
        try {
            formSchemeList = this.runTimeViewController.queryFormSchemeByTask(task.getKey());
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query formSchemes by task %s error.", task.getKey()), (Throwable)e);
        }
        return formSchemeList;
    }

    public List<DesignFormSchemeDefine> getDesFormSchemeList(TaskDefine task) {
        ArrayList<DesignFormSchemeDefine> formSchemeList = new ArrayList();
        try {
            formSchemeList = this.designTimeViewController.queryFormSchemeByTask(task.getKey());
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query formSchemes by task %s error.", task.getKey()), (Throwable)e);
        }
        return formSchemeList;
    }

    public FormSchemeDefine getFormScheme(String formSchemeKey) {
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query formScheme %s error.", formSchemeKey), (Throwable)e);
        }
        return formScheme;
    }

    public DesignColumnModelDefine createField() {
        DesignColumnModelDefine fieldDefine = null;
        try {
            fieldDefine = this.designDataModelService.createColumnModelDefine();
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("create field error.", new Object[0]), (Throwable)e);
        }
        return fieldDefine;
    }

    public DesignTableModelDefine createTableDefine() {
        DesignTableModelDefine tableDefine = null;
        try {
            tableDefine = this.designDataModelService.createTableModelDefine();
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("create table error.", new Object[0]), (Throwable)e);
        }
        return tableDefine;
    }

    public DesignTableModelDefine getTable(String entityTableKey) {
        DesignTableModelDefine designTableDefine = null;
        try {
            designTableDefine = this.designDataModelService.getTableModelDefine(entityTableKey);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query table %s error.", entityTableKey), (Throwable)e);
        }
        return designTableDefine;
    }

    public DesignTableModelDefine getDesTableByCode(String code) {
        DesignTableModelDefine table = null;
        try {
            table = this.designDataModelService.getTableModelDefineByCode(code);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query table %s error.", code), (Throwable)e);
        }
        return table;
    }

    public TableModelDefine getRunTableByCode(String code) {
        TableModelDefine table = null;
        try {
            table = this.dataModelService.getTableModelDefineByCode(code);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query table %s error.", code), (Throwable)e);
        }
        return table;
    }

    public String initField(String tableKey, String fieldCode, String referField, String title, String defaultValue, ColumnModelType fieldType, int size, boolean nullable, Map<String, DesignColumnModelDefine> fieldMap) {
        DesignColumnModelDefine fieldDefine = null;
        boolean doInsert = true;
        DesignColumnModelDefine fieldDefineByCode = fieldMap.get(fieldCode);
        if (fieldDefineByCode != null) {
            doInsert = false;
            fieldDefine = fieldDefineByCode;
        }
        if (fieldDefineByCode == null) {
            fieldDefine = this.createField();
        }
        fieldDefine.setCode(fieldCode);
        fieldDefine.setName(fieldCode);
        fieldDefine.setTitle(title);
        fieldDefine.setColumnType(fieldType);
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(size);
        fieldDefine.setNullAble(nullable);
        fieldDefine.setDefaultValue(defaultValue);
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        if (doInsert) {
            try {
                this.designDataModelService.insertColumnModelDefine(fieldDefine);
            }
            catch (Exception e) {
                throw new CreateSystemTableException(String.format("insert field %s error.", fieldCode), (Throwable)e);
            }
        } else {
            this.updateField(fieldDefine);
        }
        return fieldDefine.getID();
    }

    public void insertTableModelDefine(DesignTableModelDefine designTableModelDefine) {
        try {
            this.designDataModelService.insertTableModelDefine(designTableModelDefine);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("insert table error.", new Object[0]), (Throwable)e);
        }
    }

    public void updateTableModelDefine(DesignTableModelDefine designTableModelDefine) {
        try {
            this.designDataModelService.updateTableModelDefine(designTableModelDefine);
        }
        catch (ModelValidateException e) {
            throw new CreateSystemTableException(String.format("update table %s error.", designTableModelDefine.getCode()), (Throwable)e);
        }
    }

    public void deployTable(String tableId) {
        try {
            this.dataModelDeployService.deployTableUnCheck(tableId);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("deploy table error,id: %s ", tableId), (Throwable)e);
        }
    }

    public void deployDeleteTableByCode(String tableCode) {
        try {
            DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
            if (tableDefine == null) {
                return;
            }
            this.designDataModelService.deleteTableModelDefine(tableDefine.getID());
            this.dataModelDeployService.deployTableUnCheck(tableDefine.getID());
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u5b58\u50a8\u8868".concat(tableCode).concat("\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").concat(e.getMessage()), e);
        }
    }

    public void addIndexToTable(String tableKey, String[] fields, String indexName, IndexModelType type) {
        try {
            this.designDataModelService.addIndexToTable(tableKey, fields, indexName, type);
        }
        catch (Exception e) {
            logger.error("\u6dfb\u52a0\u7d22\u5f15".concat(indexName).concat("\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").concat(e.getMessage()), e);
        }
    }

    public void deleteIndexModelDefine(String indexName) {
        try {
            this.designDataModelService.deleteIndexModelDefine(indexName);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u7d22\u5f15".concat(indexName).concat("\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").concat(e.getMessage()), e);
        }
    }

    public DesignCatalogModelDefine getCatlogModelDefine(String id) {
        try {
            DesignCatalogModelDefine catLog = this.catalogModelService.getCatalogModelDefine(id);
            return catLog;
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u76ee\u5f55\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a".concat(e.getMessage()), e);
            return null;
        }
    }

    public DesignCatalogModelDefine createCatlogModelDefine() {
        try {
            DesignCatalogModelDefine catLog = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
            return catLog;
        }
        catch (Exception e) {
            logger.error("\u521b\u5efa\u76ee\u5f55\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a".concat(e.getMessage()), e);
            return null;
        }
    }
}

