/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataDimension
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
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
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DataModelRegisterService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.snapshot.deploy;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataDimension;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.snapshot.deploy.ColumnInfo;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
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
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnapshotTableUtils {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotTableUtils.class);
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private DesignDataModelService designDataModelService;
    private CatalogModelService catalogModelService;
    private DataModelDeployService dataModelDeployService;
    private IEntityMetaService iEntityMetaService;
    private PeriodEngineService periodEngineService;
    private NRDesignTimeController nrDesignController;
    private DataModelRegisterService dataModelRegisterService;
    private IDesignDataSchemeService designDataSchemeService;
    private DataBaseLimitModeProvider dataBaseLimitModeProvider;
    private final List<ColumnInfo> snapshotTableColumnList = new ArrayList<ColumnInfo>();
    private final List<ColumnInfo> snapshotRelTableColumnList = new ArrayList<ColumnInfo>();

    public SnapshotTableUtils() {
        this.runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        this.designDataModelService = (DesignDataModelService)SpringBeanUtils.getBean(DesignDataModelService.class);
        this.catalogModelService = (CatalogModelService)SpringBeanUtils.getBean(CatalogModelService.class);
        this.dataModelDeployService = (DataModelDeployService)SpringBeanUtils.getBean(DataModelDeployService.class);
        this.iEntityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        this.periodEngineService = (PeriodEngineService)SpringBeanUtils.getBean(PeriodEngineService.class);
        this.nrDesignController = (NRDesignTimeController)SpringBeanUtils.getBean(NRDesignTimeController.class);
        this.dataModelRegisterService = (DataModelRegisterService)SpringBeanUtils.getBean(DataModelRegisterService.class);
        this.designDataSchemeService = (IDesignDataSchemeService)SpringBeanUtils.getBean(IDesignDataSchemeService.class);
        this.dataBaseLimitModeProvider = (DataBaseLimitModeProvider)SpringBeanUtils.getBean(DataBaseLimitModeProvider.class);
        this.snapshotTableColumnList.add(new ColumnInfo("ID", "ID", ColumnModelType.STRING, 50, "", true));
        this.snapshotTableColumnList.add(new ColumnInfo("TITLE", "TITLE", ColumnModelType.STRING, 50, ""));
        this.snapshotTableColumnList.add(new ColumnInfo("DESCRIBTION", "DESCRIBTION", ColumnModelType.STRING, 200, ""));
        this.snapshotTableColumnList.add(new ColumnInfo("CREATTIME", "CREATTIME", ColumnModelType.DATETIME, 6, ""));
        this.snapshotTableColumnList.add(new ColumnInfo("CREATUSERID", "CREATUSERID", ColumnModelType.STRING, 50, ""));
        this.snapshotTableColumnList.add(new ColumnInfo("CREATUSERNAME", "CREATUSERNAME", ColumnModelType.STRING, 200, ""));
        this.snapshotTableColumnList.add(new ColumnInfo("TASKKEY", "TASKKEY", ColumnModelType.STRING, 50, ""));
        this.snapshotTableColumnList.add(new ColumnInfo("FORMSCHEMEKEY", "FORMSCHEMEKEY", ColumnModelType.STRING, 50, ""));
        this.snapshotTableColumnList.add(new ColumnInfo("ISAUTOCREATED", "ISAUTOCREATED", ColumnModelType.STRING, 1, ""));
        this.snapshotRelTableColumnList.add(new ColumnInfo("SNAPSHOTID", "SNAPSHOTID", ColumnModelType.STRING, 50, "", true));
        this.snapshotRelTableColumnList.add(new ColumnInfo("SSFILEKEY", "SSFILEKEY", ColumnModelType.STRING, 50, ""));
    }

    public void doObserver(DataScheme dataScheme) {
        try {
            if (null == dataScheme) {
                return;
            }
            this.deploySnapshotTable(dataScheme);
            this.deploySnapshotRelTable(dataScheme, false);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public List<String> getSnapshotDeployTable(String taskId) throws Exception {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        if (dataScheme == null) {
            return new ArrayList<String>();
        }
        return this.deploySnapshotTable((DataScheme)dataScheme);
    }

    public List<String> getSnapshotRelDeployTable(String taskId) throws Exception {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        if (dataScheme == null) {
            return new ArrayList<String>();
        }
        return this.deploySnapshotRelTable((DataScheme)dataScheme, true);
    }

    public void registerSnapshotTable(String taskId) {
        try {
            DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String snapshotTableName = "NR_SNAPSHOT_" + dataScheme.getBizCode();
            DesignTableModelDefine snapshotTable = this.designDataModelService.getTableModelDefineByCode(snapshotTableName);
            if (snapshotTable == null) {
                snapshotTable = this.designDataModelService.createTableModelDefine();
            }
            this.dataModelRegisterService.registerTable(snapshotTable.getID());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void registerSnapshotRelTable(String taskId) {
        try {
            DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String snapshotRelTableName = "NR_SNAPSHOT_REL_" + dataScheme.getBizCode();
            DesignTableModelDefine snapshotRelTable = this.designDataModelService.getTableModelDefineByCode(snapshotRelTableName);
            if (snapshotRelTable == null) {
                snapshotRelTable = this.designDataModelService.createTableModelDefine();
            }
            this.dataModelRegisterService.registerTable(snapshotRelTable.getID());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void insertCol(DataScheme dataScheme) throws Exception {
        if (null == dataScheme) {
            return;
        }
        String snapshotTableName = "NR_SNAPSHOT_" + dataScheme.getBizCode();
        this.doObserver(dataScheme);
        DesignTableModelDefine snapshotTable = this.designDataModelService.getTableModelDefineByCode(snapshotTableName);
        List columnModelDefines = this.designDataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
        for (DesignColumnModelDefine columnModelDefine : columnModelDefines) {
            if (!columnModelDefine.getCode().equals("ISAUTOCREATED")) continue;
            return;
        }
        ColumnInfo columnInfo = new ColumnInfo("ISAUTOCREATED", "ISAUTOCREATED", ColumnModelType.STRING, 1, "");
        DesignColumnModelDefine fieldDefine = this.designDataModelService.createColumnModelDefine();
        fieldDefine.setCode(columnInfo.getCode());
        fieldDefine.setName(columnInfo.getCode());
        fieldDefine.setColumnType(columnInfo.getType());
        fieldDefine.setTableID(snapshotTable.getID());
        fieldDefine.setPrecision(columnInfo.getSize());
        fieldDefine.setReferColumnID(columnInfo.getReferFieldID());
        fieldDefine.setDefaultValue("0");
        this.designDataModelService.insertColumnModelDefine(fieldDefine);
        this.designDataModelService.updateTableModelDefine(snapshotTable);
        this.dataModelDeployService.deployTableUnCheck(snapshotTable.getID());
    }

    private List<String> deploySnapshotTable(DataScheme dataScheme) throws Exception {
        boolean doInsert;
        if (null == dataScheme) {
            return Collections.emptyList();
        }
        String snapshotTableName = "NR_SNAPSHOT_" + dataScheme.getBizCode();
        DesignTableModelDefine snapshotTable = this.designDataModelService.getTableModelDefineByCode(snapshotTableName);
        HashMap<String, DesignColumnModelDefine> oldColumnMap = new HashMap();
        boolean bl = doInsert = snapshotTable == null;
        if (doInsert) {
            snapshotTable = this.initNewTableDefine(snapshotTableName, dataScheme);
        } else {
            snapshotTable.setBizKeys("");
            snapshotTable.setKeys("");
            List oldColumns = this.designDataModelService.getColumnModelDefinesByTable(snapshotTable.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, t -> t));
        }
        snapshotTable.setDesc("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011\u5bf9\u5e94\u5feb\u7167\u4fe1\u606f\u8868");
        snapshotTable.setTitle("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011\u5bf9\u5e94\u5feb\u7167\u4fe1\u606f\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        for (ColumnInfo column : this.snapshotTableColumnList) {
            this.addColumn(snapshotTable, oldColumnMap, column, createFieldList, modifyFieldList);
        }
        return this.doSaveAndDeploy(snapshotTable, doInsert, createFieldList, modifyFieldList);
    }

    private List<String> deploySnapshotRelTable(DataScheme dataScheme, boolean isNODDL) throws Exception {
        IEntityDefine entity;
        List reportDimension;
        List dataSchemeDimension;
        boolean doInsert;
        if (null == dataScheme) {
            return Collections.emptyList();
        }
        String snapshotRelTableName = "NR_SNAPSHOT_REL_" + dataScheme.getBizCode();
        DesignTableModelDefine snapshotRelTable = this.designDataModelService.getTableModelDefineByCode(snapshotRelTableName);
        HashMap<String, DesignColumnModelDefine> oldColumnMap = new HashMap();
        boolean bl = doInsert = snapshotRelTable == null;
        if (doInsert) {
            snapshotRelTable = this.initNewTableDefine(snapshotRelTableName, dataScheme);
        } else {
            snapshotRelTable.setBizKeys("");
            snapshotRelTable.setKeys("");
            List oldColumns = this.designDataModelService.getColumnModelDefinesByTable(snapshotRelTable.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, t -> t));
        }
        snapshotRelTable.setDesc("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011\u5bf9\u5e94\u5feb\u7167\u5173\u8054\u8868");
        snapshotRelTable.setTitle("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011\u5bf9\u5e94\u5feb\u7167\u5173\u8054\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        String dw = "";
        String dateTime = "";
        if (isNODDL) {
            dataSchemeDimension = this.designDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
            if (dataSchemeDimension.isEmpty()) {
                throw new Exception("\u6570\u636e\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u5feb\u7167\u5173\u8054\u8868");
            }
            for (DesignDataDimension designDataDimension : dataSchemeDimension) {
                if (DimensionType.UNIT.equals((Object)designDataDimension.getDimensionType())) {
                    dw = designDataDimension.getDimKey();
                    continue;
                }
                if (!DimensionType.PERIOD.equals((Object)designDataDimension.getDimensionType())) continue;
                dateTime = designDataDimension.getDimKey();
            }
        } else {
            dataSchemeDimension = this.runtimeDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
            if (dataSchemeDimension.isEmpty()) {
                throw new Exception("\u6570\u636e\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u5feb\u7167\u5173\u8054\u8868");
            }
            for (DataDimension dataDimension : dataSchemeDimension) {
                if (DimensionType.UNIT.equals((Object)dataDimension.getDimensionType())) {
                    dw = dataDimension.getDimKey();
                    continue;
                }
                if (!DimensionType.PERIOD.equals((Object)dataDimension.getDimensionType())) continue;
                dateTime = dataDimension.getDimKey();
            }
        }
        this.addColumn(snapshotRelTable, oldColumnMap, new ColumnInfo("MDCODE", "MDCODE", ColumnModelType.STRING, 60, this.queryEntityTableBizKeyByEntityID(dw), true), createFieldList, modifyFieldList);
        this.addColumn(snapshotRelTable, oldColumnMap, new ColumnInfo("DATATIME", "DATATIME", ColumnModelType.STRING, 9, this.queryPeriodBizKey(dateTime), true), createFieldList, modifyFieldList);
        if (isNODDL) {
            reportDimension = this.designDataSchemeService.getReportDimension(dataScheme.getKey());
            if (null != reportDimension && !reportDimension.isEmpty()) {
                for (DataDimension dataDimension : reportDimension) {
                    entity = this.iEntityMetaService.queryEntity(dataDimension.getDimKey());
                    if (null != entity) {
                        this.addColumn(snapshotRelTable, oldColumnMap, new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId()), true), createFieldList, modifyFieldList);
                        continue;
                    }
                    if (!"ADJUST".equals(dataDimension.getDimKey())) continue;
                    this.addColumn(snapshotRelTable, oldColumnMap, new ColumnInfo("ADJUST", "ADJUST", ColumnModelType.STRING, 40, "", true), createFieldList, modifyFieldList);
                }
            }
        } else {
            reportDimension = this.runtimeDataSchemeService.getReportDimension(dataScheme.getKey());
            if (null != reportDimension && !reportDimension.isEmpty()) {
                for (DataDimension dataDimension : reportDimension) {
                    entity = this.iEntityMetaService.queryEntity(dataDimension.getDimKey());
                    if (null != entity) {
                        this.addColumn(snapshotRelTable, oldColumnMap, new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId()), true), createFieldList, modifyFieldList);
                        continue;
                    }
                    if (!"ADJUST".equals(dataDimension.getDimKey())) continue;
                    this.addColumn(snapshotRelTable, oldColumnMap, new ColumnInfo("ADJUST", "ADJUST", ColumnModelType.STRING, 40, "", true), createFieldList, modifyFieldList);
                }
            }
        }
        for (ColumnInfo column : this.snapshotRelTableColumnList) {
            this.addColumn(snapshotRelTable, oldColumnMap, column, createFieldList, modifyFieldList);
        }
        return this.doSaveAndDeploy(snapshotRelTable, doInsert, createFieldList, modifyFieldList);
    }

    private DesignTableModelDefine initNewTableDefine(String tableCode, DataScheme dataScheme) {
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

    private void addColumn(DesignTableModelDefine tableDefine, Map<String, DesignColumnModelDefine> oldColumnMap, ColumnInfo columnInfo, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) {
        DesignColumnModelDefine userColumn = this.initField(tableDefine.getID(), columnInfo.getReferFieldID(), columnInfo);
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
            String keys = StringUtils.isEmpty((String)tableDefine.getBizKeys()) ? userColumn.getID() : tableDefine.getBizKeys() + ";" + userColumn.getID();
            tableDefine.setBizKeys(keys);
            tableDefine.setKeys(keys);
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

    private List<String> doSaveAndDeploy(DesignTableModelDefine tableDefine, boolean doInsert, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
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
        if (!this.dataBaseLimitModeProvider.databaseLimitMode()) {
            if (createFieldList.size() > 0 || modifyFieldList.size() > 0) {
                this.dataModelDeployService.deployTableUnCheck(tableDefine.getID());
            }
            return Collections.emptyList();
        }
        return this.dataModelDeployService.getDeployTableSqls(tableDefine.getID());
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

    public void deleteTable(DataScheme dataScheme) throws Exception {
        String snapshotTableName = "NR_SNAPSHOT_" + dataScheme.getBizCode();
        DesignTableModelDefine snapshotTable = this.designDataModelService.getTableModelDefineByCode(snapshotTableName);
        this.designDataModelService.deleteTableModelDefine(snapshotTable.getID());
        this.dataModelDeployService.deployTableUnCheck(snapshotTable.getID());
        String snapshotRelTableName = "NR_SNAPSHOT_REL_" + dataScheme.getBizCode();
        DesignTableModelDefine snapshotRelTable = this.designDataModelService.getTableModelDefineByCode(snapshotRelTableName);
        this.designDataModelService.deleteTableModelDefine(snapshotRelTable.getID());
        this.dataModelDeployService.deployTableUnCheck(snapshotRelTable.getID());
    }
}

