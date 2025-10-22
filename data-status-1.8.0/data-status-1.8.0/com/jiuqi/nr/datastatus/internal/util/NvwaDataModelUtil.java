/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.data.logic.internal.util.NvwaDataModelUtil
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.exception.CreateSystemTableException
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.service.impl.DesignFormSchemeService
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
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.datastatus.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datastatus.internal.model.DeployColumnCollection;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.exception.CreateSystemTableException;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.service.impl.DesignFormSchemeService;
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
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NvwaDataModelUtil {
    private static final Logger logger = LoggerFactory.getLogger(com.jiuqi.nr.data.logic.internal.util.NvwaDataModelUtil.class);
    @Autowired
    IDesignTimeViewController designTimeViewController;
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
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
    @Autowired
    private DesignFormSchemeService formSchemeService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;

    public DesignTableModelDefine createTable() {
        DesignTableModelDefine table;
        try {
            table = this.designDataModelService.createTableModelDefine();
        }
        catch (Exception e) {
            throw new CreateSystemTableException("create table error.", (Throwable)e);
        }
        return table;
    }

    public void initTableModel(TaskDefine taskDefine, FormSchemeDefine scheme, String tableCode, DesignTableModelDefine tableModelDefine, String tableDesc) {
        tableModelDefine.setCode(tableCode);
        tableModelDefine.setName(tableCode);
        tableModelDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94" + tableDesc);
        tableModelDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94" + tableDesc);
        tableModelDefine.setType(TableModelType.DEFAULT);
        tableModelDefine.setKind(TableModelKind.DEFAULT);
        tableModelDefine.setOwner("NR");
        tableModelDefine.setSupportNrdb(true);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        tableModelDefine.setStorageName(dataScheme.getBizCode());
    }

    public DesignColumnModelDefine createColumn() {
        DesignColumnModelDefine column;
        try {
            column = this.designDataModelService.createColumnModelDefine();
        }
        catch (Exception e) {
            throw new CreateSystemTableException("create field error.", (Throwable)e);
        }
        return column;
    }

    public ColumnModelDefine initColumnModel(ColumnModelType columnType, int size, String defaultValue, String tableID, String columnCode, String referColumnID, DeployColumnCollection deployColumnCollection) {
        DesignColumnModelDefine columnModelDefine = deployColumnCollection.getColBefore(columnCode);
        if (columnModelDefine == null) {
            columnModelDefine = this.createColumn();
            deployColumnCollection.appendCreCol(columnModelDefine);
        } else {
            deployColumnCollection.appendModCol(columnModelDefine);
        }
        columnModelDefine.setName(columnCode);
        columnModelDefine.setCode(columnCode);
        columnModelDefine.setColumnType(columnType);
        columnModelDefine.setTableID(tableID);
        columnModelDefine.setPrecision(size);
        if (ColumnModelType.DOUBLE == columnType) {
            columnModelDefine.setDecimal(2);
        } else if (ColumnModelType.BIGDECIMAL == columnType) {
            columnModelDefine.setDecimal(6);
        }
        if (referColumnID != null) {
            columnModelDefine.setReferColumnID(referColumnID);
        }
        if (StringUtils.isNotEmpty((String)defaultValue)) {
            columnModelDefine.setDefaultValue(defaultValue);
        }
        return columnModelDefine;
    }

    public DesignColumnModelDefine getDesColumnByCode(String columnCode, String tableID) {
        DesignColumnModelDefine column;
        try {
            column = this.designDataModelService.getColumnModelDefineByCode(tableID, columnCode);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query column by code %s error.", columnCode), (Throwable)e);
        }
        return column;
    }

    public Map<String, DesignColumnModelDefine> getAllDesColumnsMap(String tableID) {
        Map<String, DesignColumnModelDefine> result;
        try {
            result = this.designDataModelService.getColumnModelDefinesByTable(tableID).stream().collect(Collectors.toMap(IModelDefineItem::getCode, Function.identity()));
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query columns by tableId %s error.", tableID), (Throwable)e);
        }
        return result;
    }

    public DesignTableModelDefine getDesTableByCode(String tableCode) {
        DesignTableModelDefine table;
        try {
            table = this.designDataModelService.getTableModelDefineByCode(tableCode);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("query table %s error.", tableCode), (Throwable)e);
        }
        return table;
    }

    public DesignCatalogModelDefine createCatalog() {
        try {
            return this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
        }
        catch (Exception e) {
            logger.error("\u521b\u5efa\u76ee\u5f55\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a".concat(e.getMessage()), e);
            return null;
        }
    }

    public void insertTableModelDefine(DesignTableModelDefine designTableModelDefine) {
        try {
            this.designDataModelService.insertTableModelDefine(designTableModelDefine);
        }
        catch (Exception e) {
            throw new CreateSystemTableException("insert table error.", (Throwable)e);
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

    public void addIndexToTable(String tableID, String[] fields, String indexName, IndexModelType type) {
        try {
            this.designDataModelService.addIndexToTable(tableID, fields, indexName, type);
        }
        catch (Exception e) {
            logger.error("\u6dfb\u52a0\u7d22\u5f15".concat(indexName).concat("\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").concat(e.getMessage()), e);
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

    public void insertColumns(DesignColumnModelDefine[] columns) {
        try {
            this.designDataModelService.insertColumnModelDefines(columns);
        }
        catch (Exception e) {
            throw new CreateSystemTableException("batch insert columns  error.", (Throwable)e);
        }
    }

    public void updateColumns(DesignColumnModelDefine[] columns) {
        try {
            this.designDataModelService.updateColumnModelDefines(columns);
        }
        catch (Exception e) {
            throw new CreateSystemTableException("batch update columns error.", (Throwable)e);
        }
    }

    public void deleteColumns(String[] columnIds) {
        try {
            this.designDataModelService.deleteColumnModelDefines(columnIds);
        }
        catch (Exception e) {
            throw new CreateSystemTableException("batch delete columns error.", (Throwable)e);
        }
    }

    public String initEntityColumns(TaskDefine taskDefine, FormSchemeDefine scheme, String tableID, DeployColumnCollection deployColumnCollection) throws Exception {
        StringBuilder masterEntityKeys = new StringBuilder();
        String entitiesKey = this.nrDesignTimeController.getFormSchemeEntity(scheme.getKey());
        if (StringUtils.isEmpty((String)entitiesKey)) {
            entitiesKey = taskDefine.getMasterEntitiesKey();
        }
        if (StringUtils.isEmpty((String)entitiesKey)) {
            throw new Exception("\u62a5\u8868\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u5ba1\u6838\u76f8\u5173\u8868");
        }
        String[] entitiesKeyArr = entitiesKey.split(";");
        boolean flag = true;
        String periodEntityKey = "";
        for (String entityKeyStr : entitiesKeyArr) {
            String referColumnID = null;
            if (this.isPeriodEntity(entityKeyStr)) {
                IPeriodEntity periodEntity = this.queryPeriodEntity(entityKeyStr);
                referColumnID = this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(periodEntity.getKey()).getBizKeys();
                periodEntityKey = this.initColumnModel(ColumnModelType.STRING, 50, "-", tableID, "PERIOD", referColumnID, deployColumnCollection).getID();
                continue;
            }
            if (flag) {
                IEntityAttribute bizField = this.queryEntityBizKeyByView(entityKeyStr);
                if (bizField != null) {
                    referColumnID = bizField.getID();
                }
                masterEntityKeys.append(this.initColumnModel(ColumnModelType.STRING, 50, "-", tableID, "MDCODE", referColumnID, deployColumnCollection).getID()).append(";");
                flag = false;
                continue;
            }
            IEntityDefine entity = this.queryDimensionByView(entityKeyStr);
            if (entity == null) continue;
            IEntityAttribute referFieldCol = this.queryEntityBizKeyByEntityId(entity.getId());
            if (referFieldCol != null) {
                referColumnID = referFieldCol.getID();
            }
            masterEntityKeys.append(this.initColumnModel(ColumnModelType.STRING, 50, "-", tableID, entity.getDimensionName(), referColumnID, deployColumnCollection).getID()).append(";");
        }
        if (this.formSchemeService.enableAdjustPeriod(scheme.getKey())) {
            masterEntityKeys.append(this.initColumnModel(ColumnModelType.STRING, 50, "0", tableID, "ADJUST", null, deployColumnCollection).getID()).append(";");
        }
        if (StringUtils.isNotEmpty((String)periodEntityKey)) {
            masterEntityKeys.append(periodEntityKey).append(";");
        }
        return masterEntityKeys.toString();
    }

    public boolean isPeriodEntity(String entityId) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        return periodAdapter.isPeriodEntity(entityId);
    }

    private IPeriodEntity queryPeriodEntity(String entityId) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        EntityViewDefine viewDefine = this.iEntityViewRunTimeController.buildEntityView(entityId);
        return periodAdapter.getPeriodEntity(viewDefine.getEntityId());
    }

    private IEntityDefine queryDimensionByView(String entityViewKey) {
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

    private IEntityAttribute queryEntityBizKeyByView(String entityViewKey) {
        IEntityDefine entity = null;
        try {
            EntityViewDefine entityView = this.iEntityViewRunTimeController.buildEntityView(entityViewKey);
            if (entityView != null && entityView.getEntityId() != null) {
                entity = this.iEntityMetaService.queryEntity(entityView.getEntityId());
            }
            if (entity != null) {
                return this.queryEntityBizKeyByEntityId(entity.getId());
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u5b9e\u4f53\u5b9a\u4e49\u5bf9\u8c61\u51fa\u9519", e);
        }
        return null;
    }

    private IEntityAttribute queryEntityBizKeyByEntityId(String entityId) {
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityId);
        IEntityAttribute entityAttribute = entityModel.getBizKeyField();
        if (entityAttribute == null) {
            entityAttribute = entityModel.getRecordKeyField();
        }
        return entityAttribute;
    }

    public void deployTable(String tableID) {
        try {
            this.dataModelDeployService.deployTable(tableID);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("deploy table error,id: %s ", tableID), (Throwable)e);
        }
    }

    public void deployTableUnCheck(String tableID) {
        try {
            this.dataModelDeployService.deployTableUnCheck(tableID);
        }
        catch (Exception e) {
            throw new CreateSystemTableException(String.format("deploy table error,id: %s ", tableID), (Throwable)e);
        }
    }

    public void deployDeleteTableByCode(String tableCode) {
        try {
            DesignTableModelDefine table = this.designDataModelService.getTableModelDefineByCode(tableCode);
            if (table == null) {
                return;
            }
            this.designDataModelService.deleteTableModelDefine(table.getID());
            this.dataModelDeployService.deployTableUnCheck(table.getID());
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u6570\u636e\u8868".concat(tableCode).concat("\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").concat(e.getMessage()), e);
        }
    }
}

