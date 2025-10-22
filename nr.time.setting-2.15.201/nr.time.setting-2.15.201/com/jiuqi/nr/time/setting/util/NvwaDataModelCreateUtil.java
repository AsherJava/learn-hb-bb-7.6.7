/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.exception.CreateSystemTableException
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.time.setting.util;

import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.CreateSystemTableException;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
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
    private IDesignDataSchemeService designDataSchemeService;
    public static final String SPLIT_CHAR = ";";
    public static final String DW_FIELD = "MDCODE";
    public static final String PERIOD_FIELD = "PERIOD";

    public String initEntityAndPeriodField(String tableKey, String fieldCode, ColumnModelType columnModelType, String referField, int size, List<DesignColumnModelDefine> isnertColumns, List<DesignColumnModelDefine> modifyColumns, boolean nullAble) {
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
        fieldDefine.setNullAble(nullAble);
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        return fieldDefine.getID();
    }

    public String initEntityAndPeriodField(String tableKey, String fieldCode, ColumnModelType columnModelType, String referField, int size, List<DesignColumnModelDefine> isnertColumns, List<DesignColumnModelDefine> modifyColumns, boolean nullAble, String defaultValue) {
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
        fieldDefine.setNullAble(nullAble);
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        if (null != defaultValue) {
            fieldDefine.setDefaultValue(defaultValue);
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

    public void updateFields(DesignColumnModelDefine[] fieldDefines) {
        try {
            this.designDataModelService.updateColumnModelDefines(fieldDefines);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("batch update fields error.", new Object[0]), (Throwable)e);
        }
    }

    public void initDwPeriodDimField(String tableId, StringBuffer tableEnityMasterKeys, DesignTaskDefine taskDefine, FormSchemeDefine scheme, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, Map<String, DesignColumnModelDefine> filedMap) {
        try {
            String referField = null;
            String dw = taskDefine.getDw();
            IEntityAttribute bizField = this.queryEntityTableBizKeyByView(dw);
            referField = bizField.getID();
            tableEnityMasterKeys.append(this.initEntityAndPeriodField(tableId, DW_FIELD, ColumnModelType.STRING, referField, 50, createFieldList, modifyFieldList, false)).append(SPLIT_CHAR);
            String dateTime = taskDefine.getDateTime();
            referField = this.queryPeriodBizKey(dateTime);
            tableEnityMasterKeys.append(this.initEntityAndPeriodField(tableId, PERIOD_FIELD, ColumnModelType.STRING, referField, 50, createFieldList, modifyFieldList, false)).append(SPLIT_CHAR);
            List reportDimension = this.designDataSchemeService.getReportDimension(taskDefine.getDataScheme());
            if (reportDimension != null && reportDimension.size() > 0) {
                for (DesignDataDimension dataDimension : reportDimension) {
                    if ("ADJUST".equals(dataDimension.getDimKey())) {
                        String adjust = "ADJUST";
                        String fieldKey = this.initEntityAndPeriodField(tableId, adjust, ColumnModelType.STRING, null, 50, createFieldList, modifyFieldList, false, dataDimension.getDefaultValue());
                        tableEnityMasterKeys.append(fieldKey).append(SPLIT_CHAR);
                        continue;
                    }
                    IEntityDefine entity = this.queryDimisionByView(dataDimension.getDimKey());
                    IEntityAttribute referFieldCol = this.queryEntityBizeKeyByEntityId(entity.getId());
                    if (referFieldCol != null) {
                        referField = referFieldCol.getID();
                    }
                    tableEnityMasterKeys.append(this.initEntityAndPeriodField(tableId, entity.getDimensionName(), ColumnModelType.STRING, referField, 50, createFieldList, modifyFieldList, false)).append(SPLIT_CHAR);
                }
            } else {
                String adjust = "ADJUST";
                if (filedMap.get(adjust) != null) {
                    this.initEntityAndPeriodField(tableId, adjust, ColumnModelType.STRING, null, 50, createFieldList, modifyFieldList, true);
                }
            }
        }
        catch (Exception e2) {
            logger.error("\u521d\u59cb\u5316\u4e3b\u952e\u5b57\u6bb5\u62a5\u9519,\u4efb\u52a1:" + taskDefine.getTaskCode() + "|" + taskDefine.getTitle(), e2);
            throw new RuntimeException("\u521d\u59cb\u5316\u4e3b\u952e\u62a5\u9519");
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

    public String initField_DATE(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.DATETIME, 0);
    }

    public String initField_String(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.STRING, size);
    }

    public String initField_Clob(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.BLOB, size);
    }

    public String initField_Boolean(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.BOOLEAN, 0);
    }

    public String initField_Time_Stamp(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.DATETIME, 6);
    }

    public String initField_Integer(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.INTEGER, 2);
    }

    private String initField(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, ColumnModelType fieldType, int size) throws Exception {
        DesignColumnModelDefine fieldDefine = this.queryFieldDefinesByCode(fieldCode, tableKey);
        if (fieldDefine == null) {
            fieldDefine = this.createField();
            createFieldList.add(fieldDefine);
        } else {
            modifyFieldList.add(fieldDefine);
        }
        fieldDefine.setName(fieldCode);
        fieldDefine.setCode(fieldCode);
        fieldDefine.setColumnType(fieldType);
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(size);
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        return fieldDefine.getID();
    }

    public Map<String, DesignColumnModelDefine> getFiledMap(String tableKey) {
        HashMap<String, DesignColumnModelDefine> fieldMap = new HashMap<String, DesignColumnModelDefine>();
        List<DesignColumnModelDefine> allFields = this.getAllFieldsInTable(tableKey);
        for (DesignColumnModelDefine designColumnModelDefine : allFields) {
            fieldMap.put(designColumnModelDefine.getCode(), designColumnModelDefine);
        }
        return fieldMap;
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
}

