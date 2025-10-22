/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.exception.CreateSystemTableException
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.dataentry.deploy.util;

import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.CreateSystemTableException;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaDataModelDeployUtil {
    private static final Logger logger = LoggerFactory.getLogger(NvwaDataModelDeployUtil.class);
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
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private CatalogModelService catalogModelService;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    public static final String DW_FIELD = "MDCODE";
    public static final String PERIOD_FIELD = "PERIOD";

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

    public void initDwPeriodDimField(String tableId, StringBuffer tableEnityMasterKeys, String[] entitiesKeyArr, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) {
        boolean flag = true;
        String referField = null;
        for (String entityKeyStr : entitiesKeyArr) {
            if (this.isPeriodView(entityKeyStr)) {
                referField = this.queryPeriodBizKey(entityKeyStr);
                tableEnityMasterKeys.append(this.initEntityAndPeriodField(tableId, PERIOD_FIELD, ColumnModelType.STRING, referField, 50, createFieldList, modifyFieldList)).append(";");
                continue;
            }
            if (flag) {
                IEntityAttribute bizField = this.queryEntityTableBizKeyByView(entityKeyStr);
                referField = bizField.getID();
                tableEnityMasterKeys.append(this.initEntityAndPeriodField(tableId, DW_FIELD, ColumnModelType.STRING, referField, 50, createFieldList, modifyFieldList)).append(";");
                flag = false;
                continue;
            }
            IEntityDefine entity = this.queryDimisionByView(entityKeyStr);
            IEntityAttribute referFieldCol = this.queryEntityBizeKeyByEntityId(entity.getId());
            if (referFieldCol != null) {
                referField = referFieldCol.getID();
            }
            tableEnityMasterKeys.append(this.initEntityAndPeriodField(tableId, entity.getDimensionName(), ColumnModelType.STRING, referField, 50, createFieldList, modifyFieldList)).append(";");
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
            this.dataModelDeployService.deployTable(tableId);
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

    public void deleteIndexModelDefine(String tableID, String indexName) {
        try {
            this.designDataModelService.deleteIndexModelDefine(tableID, indexName);
        }
        catch (Exception e) {
            logger.error("\u5220\u9664\u7d22\u5f15".concat(indexName).concat("\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").concat(e.getMessage()), e);
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

