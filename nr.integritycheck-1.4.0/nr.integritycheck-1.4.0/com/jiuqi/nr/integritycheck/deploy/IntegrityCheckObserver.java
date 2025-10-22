/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.integritycheck.deploy;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployEvent;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.integritycheck.deploy.ColumnInfo;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DataModelRegisterService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class IntegrityCheckObserver
implements ApplicationListener<DataSchemeDeployEvent> {
    private static final Logger logger = LoggerFactory.getLogger(IntegrityCheckObserver.class);
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private CatalogModelService catalogModelService;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private DataBaseLimitModeProvider dataBaseLimitModeProvider;
    @Autowired
    private NRDesignTimeController nrDesignController;
    @Autowired
    private DataModelRegisterService dataModelRegisterService;
    private final List<ColumnInfo> icrTableColumnList = new ArrayList<ColumnInfo>();
    private final List<ColumnInfo> icdTableColumnList = new ArrayList<ColumnInfo>();

    IntegrityCheckObserver() {
        this.icrTableColumnList.add(new ColumnInfo("ID", "ID", ColumnModelType.STRING, 40, "", true));
        this.icrTableColumnList.add(new ColumnInfo("TASK_KEY", "TASK_KEY", ColumnModelType.STRING, 40, ""));
        this.icrTableColumnList.add(new ColumnInfo("BBLX", "BBLX", ColumnModelType.INTEGER, 10, ""));
        this.icrTableColumnList.add(new ColumnInfo("FORM_KEY", "FORM_KEY", ColumnModelType.STRING, 40, ""));
        this.icrTableColumnList.add(new ColumnInfo("ICR_RESULT", "ICR_RESULT", ColumnModelType.INTEGER, 10, ""));
        this.icrTableColumnList.add(new ColumnInfo("RECID", "RECID", ColumnModelType.STRING, 50, ""));
        this.icdTableColumnList.add(new ColumnInfo("RECID", "RECID", ColumnModelType.STRING, 40, "", true));
        this.icdTableColumnList.add(new ColumnInfo("DESCRIPTION", "DESCRIPTION", ColumnModelType.STRING, 2000, ""));
        this.icdTableColumnList.add(new ColumnInfo("CREATETIME", "CREATETIME", ColumnModelType.DATETIME, 0, ""));
        this.icdTableColumnList.add(new ColumnInfo("CREATOR", "CREATOR", ColumnModelType.STRING, 50, ""));
        this.icdTableColumnList.add(new ColumnInfo("UPDATETIME", "UPDATETIME", ColumnModelType.DATETIME, 0, ""));
        this.icdTableColumnList.add(new ColumnInfo("UPDATER", "UPDATER", ColumnModelType.STRING, 50, ""));
    }

    private List<String> deployICRTable(DesignDataScheme dataScheme) throws Exception {
        boolean doInsert;
        if (null == dataScheme) {
            return Collections.emptyList();
        }
        String icrTableName = "NR_ICR_" + dataScheme.getBizCode();
        DesignTableModelDefine icrTableDefine = this.designDataModelService.getTableModelDefineByCode(icrTableName);
        HashMap<String, DesignColumnModelDefine> oldColumnMap = new HashMap();
        boolean bl = doInsert = icrTableDefine == null;
        if (doInsert) {
            icrTableDefine = this.initNewTableDefine(icrTableName, dataScheme);
        } else {
            icrTableDefine.setBizKeys("");
            icrTableDefine.setKeys("");
            List oldColumns = this.designDataModelService.getColumnModelDefinesByTable(icrTableDefine.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, t -> t));
        }
        icrTableDefine.setDesc("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011\u5bf9\u5e94\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868");
        icrTableDefine.setTitle("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011\u5bf9\u5e94\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> deleteFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<String> newColumCodes = new ArrayList<String>();
        List dataSchemeDimension = this.designDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
        if (dataSchemeDimension.isEmpty()) {
            throw new Exception("\u62a5\u8868\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868");
        }
        this.addColumn(icrTableDefine, oldColumnMap, new ColumnInfo("BATCHID", "BATCHID", ColumnModelType.STRING, 40, "", true), createFieldList, modifyFieldList);
        newColumCodes.add("BATCHID");
        String dw = "";
        String dateTime = "";
        ArrayList<String> dims = new ArrayList<String>();
        for (DesignDataDimension designDataDimension : dataSchemeDimension) {
            if (DimensionType.UNIT.equals((Object)designDataDimension.getDimensionType())) {
                dw = designDataDimension.getDimKey();
                continue;
            }
            if (DimensionType.PERIOD.equals((Object)designDataDimension.getDimensionType())) {
                dateTime = designDataDimension.getDimKey();
                continue;
            }
            if (!DimensionType.DIMENSION.equals((Object)designDataDimension.getDimensionType())) continue;
            dims.add(designDataDimension.getDimKey());
        }
        this.addColumn(icrTableDefine, oldColumnMap, new ColumnInfo("MDCODE", "MDCODE", ColumnModelType.STRING, 40, this.queryEntityTableBizKeyByEntityID(dw), true), createFieldList, modifyFieldList);
        newColumCodes.add("MDCODE");
        this.addColumn(icrTableDefine, oldColumnMap, new ColumnInfo("DATATIME", "DATATIME", ColumnModelType.STRING, 9, this.queryPeriodBizKey(dateTime), true), createFieldList, modifyFieldList);
        newColumCodes.add("DATATIME");
        if (!dims.isEmpty()) {
            for (String dim : dims) {
                IEntityDefine entity = this.iEntityMetaService.queryEntity(dim);
                if (null != entity) {
                    this.addColumn(icrTableDefine, oldColumnMap, new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId()), true), createFieldList, modifyFieldList);
                    newColumCodes.add(entity.getDimensionName());
                    continue;
                }
                if (!"ADJUST".equals(dim)) continue;
                this.addColumn(icrTableDefine, oldColumnMap, new ColumnInfo("ADJUST", "ADJUST", ColumnModelType.STRING, 40, "", true), createFieldList, modifyFieldList);
                newColumCodes.add("ADJUST");
            }
        }
        for (ColumnInfo column : this.icrTableColumnList) {
            this.addColumn(icrTableDefine, oldColumnMap, column, createFieldList, modifyFieldList);
            newColumCodes.add(column.getCode());
        }
        if (!oldColumnMap.isEmpty()) {
            Set codes = oldColumnMap.keySet();
            for (String code : codes) {
                DesignColumnModelDefine designColumnModelDefine = (DesignColumnModelDefine)oldColumnMap.get(code);
                if (newColumCodes.contains(designColumnModelDefine.getCode())) continue;
                deleteFieldList.add(designColumnModelDefine);
            }
        }
        return this.doSaveAndDeploy(icrTableDefine, doInsert, createFieldList, modifyFieldList, deleteFieldList, null);
    }

    private List<String> deployICDTable(DesignDataScheme dataScheme) throws Exception {
        boolean doInsert;
        if (null == dataScheme) {
            return Collections.emptyList();
        }
        String icdTableName = "NR_ICD_" + dataScheme.getBizCode();
        DesignTableModelDefine icdTableDefine = this.designDataModelService.getTableModelDefineByCode(icdTableName);
        HashMap<String, DesignColumnModelDefine> oldColumnMap = new HashMap();
        boolean bl = doInsert = icdTableDefine == null;
        if (doInsert) {
            icdTableDefine = this.initNewTableDefine(icdTableName, dataScheme);
        } else {
            icdTableDefine.setBizKeys("");
            icdTableDefine.setKeys("");
            List oldColumns = this.designDataModelService.getColumnModelDefinesByTable(icdTableDefine.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, t -> t));
        }
        icdTableDefine.setDesc("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011\u5bf9\u5e94\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868");
        icdTableDefine.setTitle("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011\u5bf9\u5e94\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u9519\u8bef\u8bf4\u660e\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> deleteFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<String> newColumCodes = new ArrayList<String>();
        List dataSchemeDimension = this.designDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
        if (dataSchemeDimension.isEmpty()) {
            throw new Exception("\u62a5\u8868\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u8868\u5b8c\u6574\u6027\u68c0\u67e5\u7ed3\u679c\u8868");
        }
        String dw = "";
        String dateTime = "";
        ArrayList<String> dims = new ArrayList<String>();
        for (Object designDataDimension : dataSchemeDimension) {
            if (DimensionType.UNIT.equals((Object)designDataDimension.getDimensionType())) {
                dw = designDataDimension.getDimKey();
                continue;
            }
            if (DimensionType.PERIOD.equals((Object)designDataDimension.getDimensionType())) {
                dateTime = designDataDimension.getDimKey();
                continue;
            }
            if (!DimensionType.DIMENSION.equals((Object)designDataDimension.getDimensionType())) continue;
            dims.add(designDataDimension.getDimKey());
        }
        ArrayList<String> dimCodes = new ArrayList<String>();
        this.addColumn(icdTableDefine, oldColumnMap, new ColumnInfo("MDCODE", "MDCODE", ColumnModelType.STRING, 40, this.queryEntityTableBizKeyByEntityID(dw)), createFieldList, modifyFieldList);
        newColumCodes.add("MDCODE");
        dimCodes.add("MDCODE");
        this.addColumn(icdTableDefine, oldColumnMap, new ColumnInfo("DATATIME", "DATATIME", ColumnModelType.STRING, 9, this.queryPeriodBizKey(dateTime)), createFieldList, modifyFieldList);
        newColumCodes.add("DATATIME");
        dimCodes.add("DATATIME");
        if (!dims.isEmpty()) {
            for (String dim : dims) {
                IEntityDefine entity = this.iEntityMetaService.queryEntity(dim);
                if (null != entity) {
                    this.addColumn(icdTableDefine, oldColumnMap, new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId())), createFieldList, modifyFieldList);
                    newColumCodes.add(entity.getDimensionName());
                    dimCodes.add(entity.getDimensionName());
                    continue;
                }
                if (!"ADJUST".equals(dim)) continue;
                this.addColumn(icdTableDefine, oldColumnMap, new ColumnInfo("ADJUST", "ADJUST", ColumnModelType.STRING, 40, ""), createFieldList, modifyFieldList);
                newColumCodes.add("ADJUST");
                dimCodes.add("ADJUST");
            }
        }
        for (ColumnInfo column : this.icdTableColumnList) {
            this.addColumn(icdTableDefine, oldColumnMap, column, createFieldList, modifyFieldList);
            newColumCodes.add(column.getCode());
        }
        ArrayList<DesignColumnModelDefine> indexFields = new ArrayList<DesignColumnModelDefine>();
        String dimIndexName = icdTableName + "_IDX_DIM";
        HashMap<String, List<DesignColumnModelDefine>> indexNameColumnsMap = new HashMap<String, List<DesignColumnModelDefine>>();
        List indexModels = this.designDataModelService.getIndexsByTable(icdTableDefine.getID());
        boolean haveGroupIndex = false;
        if (null != indexModels && !indexModels.isEmpty()) {
            for (DesignIndexModelDefine indexModel : indexModels) {
                if (!dimIndexName.equals(indexModel.getName())) continue;
                haveGroupIndex = true;
            }
        }
        if (!haveGroupIndex && !createFieldList.isEmpty()) {
            block4: for (DesignColumnModelDefine designColumnModelDefine : createFieldList) {
                for (String dimCode : dimCodes) {
                    if (!dimCode.equals(designColumnModelDefine.getCode())) continue;
                    indexFields.add(designColumnModelDefine);
                    continue block4;
                }
            }
        }
        if (!indexFields.isEmpty()) {
            indexNameColumnsMap.put(dimIndexName, indexFields);
        }
        if (!oldColumnMap.isEmpty()) {
            Set codes = oldColumnMap.keySet();
            for (String code : codes) {
                DesignColumnModelDefine designColumnModelDefine = (DesignColumnModelDefine)oldColumnMap.get(code);
                if (newColumCodes.contains(designColumnModelDefine.getCode())) continue;
                deleteFieldList.add(designColumnModelDefine);
            }
        }
        return this.doSaveAndDeploy(icdTableDefine, doInsert, createFieldList, modifyFieldList, deleteFieldList, indexNameColumnsMap);
    }

    private DesignTableModelDefine initNewTableDefine(String tableCode, DesignDataScheme dataScheme) {
        DesignTableModelDefine tableDefine = this.designDataModelService.createTableModelDefine();
        tableDefine.setCode(tableCode);
        tableDefine.setName(tableCode);
        tableDefine.setOwner("NR");
        DesignCatalogModelDefine sysTableGroupByParent = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
        tableDefine.setCatalogID(sysTableGroupByParent.getID());
        tableDefine.setSupportNrdb(true);
        tableDefine.setStorageName(dataScheme.getBizCode());
        return tableDefine;
    }

    private List<String> doSaveAndDeploy(DesignTableModelDefine tableDefine, boolean doInsert, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, List<DesignColumnModelDefine> deleteFieldList, Map<String, List<DesignColumnModelDefine>> indexNameColumnsMap) throws Exception {
        if (doInsert) {
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
        if (deleteFieldList.size() > 0) {
            List<String> ids = deleteFieldList.stream().map(t -> t.getID()).collect(Collectors.toList());
            this.designDataModelService.deleteColumnModelDefines(ids.toArray(new String[ids.size()]));
        }
        if (null != indexNameColumnsMap && !indexNameColumnsMap.isEmpty()) {
            for (String indexName : indexNameColumnsMap.keySet()) {
                List<DesignColumnModelDefine> designColumnModelDefines = indexNameColumnsMap.get(indexName);
                String[] fields = new String[designColumnModelDefines.size()];
                for (int i = 0; i < designColumnModelDefines.size(); ++i) {
                    fields[i] = designColumnModelDefines.get(i).getID();
                }
                this.designDataModelService.addIndexToTable(tableDefine.getID(), fields, indexName, IndexModelType.NORMAL);
            }
        }
        if (!this.dataBaseLimitModeProvider.databaseLimitMode()) {
            if (createFieldList.size() > 0 || modifyFieldList.size() > 0 || deleteFieldList.size() > 0) {
                this.dataModelDeployService.deployTableUnCheck(tableDefine.getID());
            }
            return Collections.emptyList();
        }
        return this.dataModelDeployService.getDeployTableSqls(tableDefine.getID());
    }

    private void addColumn(DesignTableModelDefine designTableModelDefine, Map<String, DesignColumnModelDefine> oldColumnMap, ColumnInfo columnInfo, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) {
        DesignColumnModelDefine userColumn = this.initField(designTableModelDefine.getID(), columnInfo.getReferFieldID(), columnInfo);
        DesignColumnModelDefine oldUserColumn = oldColumnMap.get(columnInfo.getCode());
        if (oldUserColumn == null) {
            createFieldList.add(userColumn);
        } else {
            userColumn.setID(oldUserColumn.getID());
            if (!this.checkColumnEquals(userColumn, oldUserColumn)) {
                modifyFieldList.add(userColumn);
            }
        }
        if (columnInfo.isKeyField()) {
            String keys = StringUtils.isEmpty((String)designTableModelDefine.getBizKeys()) ? userColumn.getID() : designTableModelDefine.getBizKeys() + ";" + userColumn.getID();
            designTableModelDefine.setBizKeys(keys);
            designTableModelDefine.setKeys(keys);
        }
    }

    private DesignColumnModelDefine initField(String tableKey, String referField, ColumnInfo columnInfo) {
        DesignColumnModelDefine fieldDefine = this.designDataModelService.createColumnModelDefine();
        fieldDefine.setCode(columnInfo.getCode());
        fieldDefine.setName(columnInfo.getCode());
        fieldDefine.setColumnType(columnInfo.getType());
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(columnInfo.getSize());
        fieldDefine.setReferColumnID(referField);
        if ("ADJUST".equals(columnInfo.getCode())) {
            fieldDefine.setDefaultValue("0");
        }
        return fieldDefine;
    }

    private boolean checkColumnEquals(DesignColumnModelDefine o1, DesignColumnModelDefine o2) {
        return o1.getCode().equalsIgnoreCase(o2.getCode()) && o1.getColumnType() == o2.getColumnType() && (StringUtils.isEmpty((String)o1.getReferColumnID()) && StringUtils.isEmpty((String)o2.getReferColumnID()) || o1.getReferColumnID().equals(o2.getReferColumnID())) && o1.getPrecision() == o2.getPrecision();
    }

    private String queryEntityTableBizKeyByEntityID(String entityID) {
        IEntityDefine entity;
        if (StringUtils.isNotEmpty((String)entityID) && (entity = this.iEntityMetaService.queryEntity(entityID)) != null) {
            return this.queryEntityBizKeyByEntityId(entity.getId());
        }
        return null;
    }

    private String queryEntityBizKeyByEntityId(String entityId) {
        IEntityModel entityModel = this.iEntityMetaService.getEntityModel(entityId);
        IEntityAttribute referFieldId = entityModel.getBizKeyField();
        if (referFieldId == null) {
            referFieldId = entityModel.getRecordKeyField();
        }
        return referFieldId.getID();
    }

    private String queryPeriodBizKey(String viewKey) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(viewKey);
        return this.periodEngineService.getPeriodAdapter().getPeriodEntityTableModel(periodEntity.getKey()).getBizKeys();
    }

    public List<String> getICRTableDeployTable(String taskId) throws Exception {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        if (dataScheme == null) {
            return new ArrayList<String>();
        }
        return this.deployICRTable(dataScheme);
    }

    public List<String> getICDTableDeployTable(String taskId) throws Exception {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        if (dataScheme == null) {
            return new ArrayList<String>();
        }
        return this.deployICDTable(dataScheme);
    }

    public void registerICRTable(String taskId) {
        try {
            DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String icrTableName = "NR_ICR_" + dataScheme.getBizCode();
            DesignTableModelDefine icrTableDefine = this.designDataModelService.getTableModelDefineByCode(icrTableName);
            if (icrTableDefine == null) {
                icrTableDefine = this.designDataModelService.createTableModelDefine();
            }
            this.dataModelRegisterService.registerTable(icrTableDefine.getID());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void registerICDTable(String taskId) {
        try {
            DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String icdTableName = "NR_ICD_" + dataScheme.getBizCode();
            DesignTableModelDefine icdTableDefine = this.designDataModelService.getTableModelDefineByCode(icdTableName);
            if (icdTableDefine == null) {
                icdTableDefine = this.designDataModelService.createTableModelDefine();
            }
            this.dataModelRegisterService.registerTable(icdTableDefine.getID());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void deleteTable(DataScheme dataScheme) throws Exception {
        String icrTableName = "NR_ICR_" + dataScheme.getBizCode();
        DesignTableModelDefine icrTableDefine = this.designDataModelService.getTableModelDefineByCode(icrTableName);
        this.designDataModelService.deleteTableModelDefine(icrTableDefine.getID());
        this.dataModelDeployService.deployTableUnCheck(icrTableDefine.getID());
        String icdTableName = "NR_ICD_" + dataScheme.getBizCode();
        DesignTableModelDefine icdTableDefine = this.designDataModelService.getTableModelDefineByCode(icdTableName);
        this.designDataModelService.deleteTableModelDefine(icdTableDefine.getID());
        this.dataModelDeployService.deployTableUnCheck(icdTableDefine.getID());
    }

    public void publishTable(DataScheme dataScheme) {
        try {
            DesignDataScheme designDataScheme = this.designDataSchemeService.getDataScheme(dataScheme.getKey());
            if (null == designDataScheme) {
                return;
            }
            this.deployICRTable(designDataScheme);
            this.deployICDTable(designDataScheme);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void onApplicationEvent(DataSchemeDeployEvent event) {
        if (this.dataBaseLimitModeProvider.databaseLimitMode()) {
            return;
        }
        try {
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(event.getSource().getDataSchemeKey());
            DataScheme runTimeDataScheme = event.getSource().getDataScheme();
            if (null == dataScheme && null == runTimeDataScheme) {
                return;
            }
            if (null == dataScheme && null != runTimeDataScheme) {
                this.deleteTable(runTimeDataScheme);
                return;
            }
            this.deployICRTable(dataScheme);
            this.deployICDTable(dataScheme);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

