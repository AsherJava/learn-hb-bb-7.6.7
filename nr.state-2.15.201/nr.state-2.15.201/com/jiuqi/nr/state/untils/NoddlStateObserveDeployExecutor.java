/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  com.jiuqi.xlib.utils.CollectionUtils
 */
package com.jiuqi.nr.state.untils;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import com.jiuqi.xlib.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoddlStateObserveDeployExecutor
implements NODDLDeployExecutor {
    private static final Logger logger = LoggerFactory.getLogger(NoddlStateObserveDeployExecutor.class);
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private CatalogModelService catalogModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private DataModelRegisterService dataModelRegisterService;
    private static final String DW_FIELD = "MDCODE";
    private static final String PERIOD_FIELD = "PERIOD";

    public List<String> preDeploy(String taskKey) {
        ArrayList<String> result = new ArrayList<String>();
        List schemes = null;
        try {
            DesignTaskDefine taskDefine = this.nrDesignTimeController.queryTaskDefine(taskKey);
            schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
            for (DesignFormSchemeDefine scheme : schemes) {
                String tableKey = this.deployStateResult(taskDefine, scheme);
                List ddl = this.dataModelDeployService.getDeployTableSqls(tableKey);
                if (CollectionUtils.isEmpty((Collection)ddl)) continue;
                result.addAll(ddl);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public void doDeploy(String taskKey) {
        List schemes = null;
        try {
            schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskKey);
            for (DesignFormSchemeDefine scheme : schemes) {
                String tableCode = "SYS_STATE_" + scheme.getFormSchemeCode();
                DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
                if (tableDefine == null) continue;
                try {
                    this.dataModelRegisterService.registerTable(tableDefine.getID());
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public double getOrder() {
        return 0.0;
    }

    private String deployStateResult(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme) throws Exception {
        String tableCode = "SYS_STATE_" + scheme.getFormSchemeCode();
        DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
        boolean doInsert = true;
        if (tableDefine == null) {
            tableDefine = this.designDataModelService.createTableModelDefine();
        } else {
            doInsert = false;
        }
        tableDefine.setCode(tableCode);
        tableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u7ec8\u6b62\u586b\u62a5\u72b6\u6001\u8868");
        tableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u7ec8\u6b62\u586b\u62a5\u72b6\u6001\u8868");
        tableDefine.setName(tableCode);
        tableDefine.setType(TableModelType.DEFAULT);
        tableDefine.setKind(TableModelKind.DEFAULT);
        tableDefine.setOwner("NR");
        String tableKey = tableDefine.getID();
        StringBuffer tableEnityMasterKeys = new StringBuffer();
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        this.initField_String(tableKey, "S_USERID", null, createFieldList, modifyFieldList, 1000);
        this.initField_Time(tableKey, "CREATETETIME", null, createFieldList, modifyFieldList, 6);
        this.initField_Integer(tableKey, "S_STATE", null, createFieldList, modifyFieldList, 11);
        this.initEntityFields(taskDefine, (FormSchemeDefine)scheme, tableEnityMasterKeys, tableKey, createFieldList, modifyFieldList);
        tableDefine.setBizKeys(tableEnityMasterKeys.toString());
        tableDefine.setKeys(tableEnityMasterKeys.toString());
        if (doInsert) {
            DesignCatalogModelDefine sysTableGroup = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
            tableDefine.setCatalogID(sysTableGroup.getID());
            this.designDataModelService.insertTableModelDefine(tableDefine);
        } else {
            this.designDataModelService.updateTableModelDefine(tableDefine);
        }
        if (createFieldList.size() > 0) {
            this.designDataModelService.insertColumnModelDefines(createFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (modifyFieldList.size() > 0) {
            this.designDataModelService.updateColumnModelDefines(modifyFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        return tableDefine.getID();
    }

    private void initEntityFields(DesignTaskDefine taskDefine, FormSchemeDefine scheme, StringBuffer tableEnityMasterKeys, String tableKey, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        String entitiesKey = scheme.getMasterEntitiesKey();
        if (entitiesKey == null) {
            entitiesKey = taskDefine.getMasterEntitiesKey();
        }
        if (StringUtils.isEmpty((String)entitiesKey)) {
            throw new Exception("\u62a5\u8868\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u7ec8\u6b62\u72b6\u6001\u8868");
        }
        String[] entitiesKeyArr = entitiesKey.split(";");
        boolean flag = true;
        String referFieldId = null;
        List<String> flowEntitiesKey = this.getFlowEntitiesKey(scheme, taskDefine);
        for (String entityKeyStr : entitiesKeyArr) {
            if (this.isPeriodView(entityKeyStr)) {
                referFieldId = this.queryPeriodBizKey(entityKeyStr);
                String initField_Period = this.initField_Period(tableKey, PERIOD_FIELD, referFieldId, createFieldList, modifyFieldList);
                if (!flowEntitiesKey.contains(entityKeyStr)) continue;
                tableEnityMasterKeys.append(initField_Period).append(";");
                continue;
            }
            if (flag) {
                referFieldId = this.queryEntityTableBizKeyByView(entityKeyStr);
                String initField = this.initField(tableKey, DW_FIELD, referFieldId, createFieldList, modifyFieldList, ColumnModelType.STRING, 50, "");
                if (flowEntitiesKey.contains(entityKeyStr)) {
                    tableEnityMasterKeys.append(initField).append(";");
                }
                flag = false;
                continue;
            }
            IEntityDefine entity = this.queryDimisionByView(entityKeyStr);
            referFieldId = this.queryEntityBizeKeyByEntityId(entity.getId());
            String initField = this.initField(tableKey, entity.getDimensionName(), referFieldId, createFieldList, modifyFieldList, ColumnModelType.STRING, 50, "");
            if (!flowEntitiesKey.contains(entityKeyStr)) continue;
            tableEnityMasterKeys.append(initField).append(";");
        }
    }

    private List<String> getFlowEntitiesKey(FormSchemeDefine formScheme, DesignTaskDefine taskDefine) {
        String entitiesKey;
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        if (null == flowsSetting) {
            flowsSetting = taskDefine.getFlowsSetting();
        }
        if (StringUtils.isEmpty((String)(entitiesKey = flowsSetting.getDesignTableDefines()))) {
            entitiesKey = taskDefine.getFlowsSetting().getDesignTableDefines();
        }
        if (entitiesKey != null) {
            logger.error("\u6d41\u7a0b\u4e1a\u52a1\u4e3b\u952e\u4e3a\u7a7a\uff0c\u8bf7\u5728\u4efb\u52a1\u8bbe\u8ba1\u6838\u5b9e\u53c2\u6570");
        }
        return Arrays.asList(entitiesKey.split(";"));
    }

    private String initField_String(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.STRING, size, "");
    }

    private String initField_Time(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.DATETIME, size, "");
    }

    private String initField_Integer(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.INTEGER, size, "");
    }

    private String initField_Period(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        DesignColumnModelDefine fieldDefine = this.designDataModelService.getColumnModelDefineByCode(tableKey, fieldCode);
        if (fieldDefine == null) {
            fieldDefine = this.designDataModelService.createColumnModelDefine();
            createFieldList.add(fieldDefine);
        } else {
            modifyFieldList.add(fieldDefine);
        }
        fieldDefine.setCode(fieldCode);
        fieldDefine.setColumnType(ColumnModelType.STRING);
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(9);
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        return fieldDefine.getID();
    }

    private String initField(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, ColumnModelType fieldType, int size, String defaultValue) throws Exception {
        DesignColumnModelDefine fieldDefine = this.designDataModelService.getColumnModelDefineByCode(tableKey, fieldCode);
        if (fieldDefine == null) {
            fieldDefine = this.designDataModelService.createColumnModelDefine();
            createFieldList.add(fieldDefine);
        } else {
            modifyFieldList.add(fieldDefine);
        }
        fieldDefine.setCode(fieldCode);
        fieldDefine.setColumnType(fieldType);
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(size);
        if (ColumnModelType.DOUBLE == fieldType) {
            fieldDefine.setDecimal(2);
        }
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        if (StringUtils.isNotEmpty((String)defaultValue)) {
            fieldDefine.setDefaultValue(defaultValue);
        }
        return fieldDefine.getID();
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
            logger.error(e.getMessage(), e);
        }
        return entity;
    }

    public String queryEntityTableBizKeyByView(String entityViewKey) {
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
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String queryEntityBizeKeyByEntityId(String entityId) {
        try {
            IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityId);
            IEntityAttribute referFieldId = entityModel.getBizKeyField();
            if (referFieldId == null) {
                referFieldId = entityModel.getRecordKeyField();
            }
            return referFieldId.getID();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public IPeriodEntity queryPeriodEntity(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(viewKey);
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(entityView.getEntityId());
        return periodEntity;
    }

    public String queryPeriodBizKey(String viewKey) {
        IPeriodEntity periodEntity = this.queryPeriodEntity(viewKey);
        String referFieldId = this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(periodEntity.getKey()).getBizKeys();
        return referFieldId;
    }

    private boolean isPeriodView(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        return periodAdapter.isPeriodEntity(viewKey);
    }
}

