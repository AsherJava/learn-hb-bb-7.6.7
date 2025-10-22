/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.exception.CreateSystemTableException
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.common.utils.NrPeriodConst
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.data.access.util;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.data.access.common.TableConsts;
import com.jiuqi.nr.data.access.exception.SystemStateTableException;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.datascheme.internal.dto.DataSchemeDTO;
import com.jiuqi.nr.datascheme.internal.service.DataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.CreateSystemTableException;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.common.utils.NrPeriodConst;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataAccesslUtil {
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private DataSchemeService dataSchemeService;
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
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IFormSchemeService formSchemeService;
    public static final String SPLIT_CHAR = ";";

    public DesignTableModelDefine initTableModelDefine(String tableCode, String tableTitle) {
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
        tableDefine = this.designDataModelService.createTableModelDefine();
        String ownerGroupID = null;
        try {
            ownerGroupID = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000").getID();
        }
        catch (Exception e) {
            throw new SystemStateTableException("\u521b\u5efa\u76ee\u5f55\u5f02\u5e38 ", e);
        }
        tableDefine.setCode(tableCode);
        tableDefine.setCatalogID(ownerGroupID);
        tableDefine.setTitle(tableTitle);
        tableDefine.setName(tableCode);
        tableDefine.setType(TableModelType.DEFAULT);
        tableDefine.setKind(TableModelKind.DEFAULT);
        tableDefine.setOwner("NR");
        return tableDefine;
    }

    public StringBuffer initBizkField(String dwField, String periodField, DesignTableModelDefine tableDefine, String dataSchemeKey, boolean nullAble) {
        StringBuffer tableEnityMasterKeys = new StringBuffer();
        List dimesion = this.designDataSchemeService.getDataSchemeDimension(dataSchemeKey);
        for (DesignDataDimension dim : dimesion) {
            DimensionType dimType = dim.getDimensionType();
            String entityKey = dim.getDimKey();
            if (dimType.equals((Object)DimensionType.UNIT)) {
                IEntityAttribute referValue = this.queryEntityBizeKeyByEntityId(entityKey);
                String fieldKey = this.initField(tableDefine.getID(), "MDCODE", referValue.getID(), ColumnModelType.STRING, null, 40, false);
                tableEnityMasterKeys.append(fieldKey).append(SPLIT_CHAR);
                continue;
            }
            if (dimType.equals((Object)DimensionType.PERIOD)) {
                String referKey = this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(entityKey).getBizKeys();
                String field = this.initField(tableDefine.getID(), "DATATIME", referKey, ColumnModelType.STRING, null, 10, false);
                tableEnityMasterKeys.append(field).append(SPLIT_CHAR);
                continue;
            }
            IEntityDefine otherDim = this.iEntityMetaService.queryEntity(entityKey);
            IEntityAttribute referValue = this.queryEntityBizeKeyByEntityId(entityKey);
            String dimField = this.initField(tableDefine.getID(), otherDim.getDimensionName(), referValue.getID(), ColumnModelType.STRING, null, 40, nullAble);
            if (nullAble) continue;
            tableEnityMasterKeys.append(dimField).append(SPLIT_CHAR);
        }
        return tableEnityMasterKeys;
    }

    public String queryFiledDefine(String tableId, String fieldCode) {
        DesignColumnModelDefine fieldDefineByCode = this.designDataModelService.getColumnModelDefineByCode(tableId, fieldCode);
        return Objects.nonNull(fieldDefineByCode) ? fieldDefineByCode.getID() : null;
    }

    public String initField(String tableId, String fieldCode, String referField, ColumnModelType columnModelType, String defaultValue, int size, boolean nullable) {
        DesignColumnModelDefine fieldDefine = null;
        boolean doInsert = true;
        DesignColumnModelDefine fieldDefineByCode = this.designDataModelService.getColumnModelDefineByCode(tableId, fieldCode);
        if (fieldDefineByCode != null) {
            doInsert = false;
            fieldDefine = fieldDefineByCode;
        }
        if (fieldDefineByCode == null) {
            fieldDefine = this.designDataModelService.createColumnModelDefine();
        }
        fieldDefine.setCode(fieldCode);
        fieldDefine.setColumnType(columnModelType);
        fieldDefine.setTableID(tableId);
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
                throw new SystemStateTableException(String.format("\u65b0\u589e\u6307\u6807\u5f02\u5e38-", fieldCode), e);
            }
        } else {
            this.designDataModelService.updateColumnModelDefine(fieldDefine);
        }
        return fieldDefine.getID();
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

    public DesignTableModelDefine getTableModelDefine(String tableId) {
        DesignTableModelDefine table = this.designDataModelService.getTableModelDefine(tableId);
        return table;
    }

    public void updateModelDefine(DesignTableModelDefine designTableModelDefine) {
        try {
            this.designDataModelService.updateTableModelDefine(designTableModelDefine);
        }
        catch (ModelValidateException e) {
            throw new SystemStateTableException("\u66f4\u65b0\u72b6\u6001\u8868\u5f02\u5e38", e);
        }
    }

    public void insertModelDefine(DesignTableModelDefine designTableModelDefine) {
        try {
            this.designDataModelService.insertTableModelDefine(designTableModelDefine);
        }
        catch (ModelValidateException e) {
            throw new SystemStateTableException("\u65b0\u589e\u72b6\u6001\u8868\u5f02\u5e38", e);
        }
    }

    public void delModelDefineAndDeploy(String tableName) {
        DesignTableModelDefine table = this.designDataModelService.getTableModelDefineByCode(tableName);
        if (table != null) {
            this.designDataModelService.delteTableModel(table.getID());
            this.deployDataModel(table.getID());
        }
    }

    public void deployDataModel(String tableId) {
        try {
            this.dataModelDeployService.deployTable(tableId);
        }
        catch (Exception e) {
            throw new SystemStateTableException("\u53d1\u5e03\u72b6\u6001\u8868\u5f02\u5e38", e);
        }
    }

    public void addIndeToTable(String tableKey, String[] fields, String indexName, IndexModelType type) {
        this.designDataModelService.addIndexToTable(tableKey, fields, indexName, type);
    }

    public String getTableName(FormSchemeDefine formSchemeDefine, String tablePrefix) {
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formSchemeDefine.getTaskKey());
        DataSchemeDTO dataScheme = this.dataSchemeService.getDataScheme(taskDefine.getDataScheme());
        String tableCode = TableConsts.getSysTableName(tablePrefix, dataScheme.getBizCode());
        return tableCode;
    }

    public EntityViewDefine queryPeriodViewDefine(TaskDefine taskDefine) {
        return this.entityViewRunTimeController.buildEntityView(taskDefine.getDateTime());
    }

    public EntityViewDefine queryDWEntityViewDefine(FormSchemeDefine formScheme, TaskDefine taskDefine) {
        String dw = formScheme.getDw();
        if (com.jiuqi.bi.util.StringUtils.isEmpty((String)dw)) {
            dw = taskDefine.getDw();
        }
        dw = this.contextEntityId(dw);
        EntityViewDefine viewByFormSchemeKey = this.runtimeView.getViewByFormSchemeKey(formScheme.getKey());
        return this.entityViewRunTimeController.buildEntityView(dw, viewByFormSchemeKey.getRowFilterExpression());
    }

    public FormSchemeDefine queryFormScheme(String formSchemeKey) {
        return this.runtimeView.getFormScheme(formSchemeKey);
    }

    public String getDwDimensionName(String entityId) {
        entityId = this.contextEntityId(entityId);
        IEntityDefine entity = this.iEntityMetaService.queryEntity(entityId);
        return entity.getDimensionName();
    }

    public String getPeriodDimensionName(String entityId) {
        return this.periodEngineService.getPeriodAdapter().getPeriodDimensionName(entityId);
    }

    public EntityDimData getDwEntityDimData(String formSchemeKey) {
        FormSchemeDefine formScheme = this.queryFormScheme(formSchemeKey);
        String entityId = formScheme.getDw();
        entityId = this.contextEntityId(entityId);
        IEntityDefine entity = this.iEntityMetaService.queryEntity(entityId);
        EntityDimData entityDimData = new EntityDimData(entity.getDimensionName(), entityId);
        return entityDimData;
    }

    public EntityDimData getPeriodEntityDimData(String formSchemeKey) {
        FormSchemeDefine formScheme = this.queryFormScheme(formSchemeKey);
        String entityId = formScheme.getDateTime();
        String dimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName();
        EntityDimData entityDimData = new EntityDimData(dimensionName, entityId);
        return entityDimData;
    }

    public EntityDimData getDwEntityDimDataByTaskKey(String taskKey) {
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        String entityId = taskDefine.getDw();
        entityId = this.contextEntityId(entityId);
        IEntityDefine entity = this.iEntityMetaService.queryEntity(entityId);
        return new EntityDimData(entity.getDimensionName(), entityId);
    }

    public EntityDimData getSrcDwEntityDimDataByTaskKey(String taskKey) {
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        String entityId = taskDefine.getDw();
        IEntityDefine entity = this.iEntityMetaService.queryEntity(entityId);
        return new EntityDimData(entity.getDimensionName(), entityId);
    }

    public EntityDimData getPeriodEntityDimDataByTaskKey(String taskKey) {
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        String entityId = taskDefine.getDateTime();
        String dimensionName = this.periodEngineService.getPeriodAdapter().getPeriodDimensionName();
        return new EntityDimData(dimensionName, entityId);
    }

    public String contextEntityId(String entityId) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String contextId = dsContext.getContextEntityId();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)contextId)) {
            return contextId;
        }
        return entityId;
    }

    public List<EntityDimData> getDimEntityDimDataByTaskKey(String taskKey) {
        ArrayList<EntityDimData> result = new ArrayList<EntityDimData>();
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        String dims = taskDefine.getDims();
        boolean enableAdjust = this.formSchemeService.isTaskEnableAdjustPeriod(taskKey);
        ArrayList allDims = new ArrayList();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)dims)) {
            List<String> dimsList = Arrays.asList(dims.split(SPLIT_CHAR));
            allDims.addAll(dimsList);
        }
        for (String entityId : allDims) {
            IEntityDefine entity = this.iEntityMetaService.queryEntity(entityId);
            String dimName = entity.getDimensionName();
            EntityDimData entityDimData = new EntityDimData(dimName, entityId);
            result.add(entityDimData);
        }
        if (enableAdjust) {
            EntityDimData entityDimData = new EntityDimData("ADJUST", "ADJUST");
            result.add(entityDimData);
        }
        return result;
    }

    public List<EntityDimData> getDimEntityDimData(String formSchemeKey) {
        return this.getDimEntityDimData(formSchemeKey, true);
    }

    public List<EntityDimData> getDimEntityDimData(String formSchemeKey, boolean needAdjust) {
        ArrayList<EntityDimData> result = new ArrayList<EntityDimData>();
        FormSchemeDefine formScheme = this.queryFormScheme(formSchemeKey);
        String dims = formScheme.getDims();
        boolean enableAdjust = this.formSchemeService.enableAdjustPeriod(formSchemeKey);
        ArrayList allDims = new ArrayList();
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)dims)) {
            List<String> dimsList = Arrays.asList(dims.split(SPLIT_CHAR));
            allDims.addAll(dimsList);
        }
        for (String entityId : allDims) {
            IEntityDefine entity = this.iEntityMetaService.queryEntity(entityId);
            String dimName = entity.getDimensionName();
            EntityDimData entityDimData = new EntityDimData(dimName, entityId);
            result.add(entityDimData);
        }
        if (needAdjust && enableAdjust) {
            EntityDimData entityDimData = new EntityDimData("ADJUST", "ADJUST");
            result.add(entityDimData);
        }
        return result;
    }

    public DimensionValueSet filterReportDims(FormSchemeDefine formScheme, DimensionValueSet newMasterKey) {
        String dims = formScheme.getDims();
        if (!StringUtils.hasLength(dims)) {
            return newMasterKey;
        }
        List reportDims = this.formSchemeService.getReportDimensionKey(formScheme.getKey());
        List allDims = Stream.of(dims.split(SPLIT_CHAR)).collect(Collectors.toList());
        allDims.removeAll(reportDims);
        for (String dim : allDims) {
            IEntityDefine entity = this.iEntityMetaService.queryEntity(dim);
            String dimensionName = entity.getDimensionName();
            newMasterKey.clearValue(dimensionName);
        }
        return newMasterKey;
    }

    public Map<String, String> getAllEntityIdDimensionMap(String formSchemeKey) {
        FormSchemeDefine formScheme = this.queryFormScheme(formSchemeKey);
        return this.getEntityIdDimensionMapByTaskKey(formScheme.getTaskKey());
    }

    public Map<String, String> getEntityIdDimensionMapByTaskKey(String taskKey) {
        HashMap<String, String> result = new HashMap<String, String>();
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        String dims = taskDefine.getDims();
        ArrayList<String> allDims = new ArrayList<String>();
        allDims.add(this.contextEntityId(taskDefine.getDw()));
        boolean enableAdjust = this.formSchemeService.isTaskEnableAdjustPeriod(taskKey);
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)dims)) {
            List<String> dimsList = Arrays.asList(dims.split(SPLIT_CHAR));
            allDims.addAll(dimsList);
        }
        for (String entityId : allDims) {
            IEntityDefine entity = this.iEntityMetaService.queryEntity(entityId);
            String dimName = entity.getDimensionName();
            result.put(dimName, entityId);
        }
        result.put(NrPeriodConst.DATETIME, taskDefine.getDateTime());
        if (enableAdjust) {
            result.put("ADJUST", "ADJUST");
        }
        return result;
    }
}

