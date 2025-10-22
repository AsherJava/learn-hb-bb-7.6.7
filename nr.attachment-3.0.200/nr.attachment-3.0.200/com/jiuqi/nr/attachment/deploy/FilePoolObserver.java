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
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.attachment.deploy;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.attachment.deploy.ColumnInfo;
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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class FilePoolObserver
implements ApplicationListener<DataSchemeDeployEvent> {
    private static final Logger logger = LoggerFactory.getLogger(FilePoolObserver.class);
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
    private final List<ColumnInfo> fpTableColumnList = new ArrayList<ColumnInfo>();

    FilePoolObserver() {
        this.fpTableColumnList.add(new ColumnInfo("ID", "ID", ColumnModelType.STRING, 40, "", true));
        this.fpTableColumnList.add(new ColumnInfo("GROUPKEY", "GROUPKEY", ColumnModelType.STRING, 40, ""));
        this.fpTableColumnList.add(new ColumnInfo("FILEKEY", "FILEKEY", ColumnModelType.STRING, 40, ""));
        this.fpTableColumnList.add(new ColumnInfo("FIELD_KEY", "FIELD_KEY", ColumnModelType.STRING, 40, ""));
        this.fpTableColumnList.add(new ColumnInfo("ISDELETE", "ISDELETE", ColumnModelType.STRING, 1, ""));
    }

    private List<String> deployFilePoolTable(DesignDataScheme dataScheme) throws Exception {
        boolean doInsert;
        if (null == dataScheme) {
            return Collections.emptyList();
        }
        String filePoolTableName = "NR_FILE_" + dataScheme.getBizCode();
        DesignTableModelDefine filePoolTable = this.designDataModelService.getTableModelDefineByCode(filePoolTableName);
        List oldColumns = null;
        HashMap<String, DesignColumnModelDefine> oldColumnMap = new HashMap();
        boolean bl = doInsert = filePoolTable == null;
        if (doInsert) {
            filePoolTable = this.initNewTableDefine(filePoolTableName, dataScheme);
        } else {
            filePoolTable.setBizKeys("");
            filePoolTable.setKeys("");
            oldColumns = this.designDataModelService.getColumnModelDefinesByTable(filePoolTable.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, t -> t));
        }
        filePoolTable.setDesc("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011\u5bf9\u5e94\u9644\u4ef6\u6c60\u5173\u8054\u8868");
        filePoolTable.setTitle("\u6570\u636e\u65b9\u6848\u3010" + dataScheme.getTitle() + "\u3011\u5bf9\u5e94\u9644\u4ef6\u6c60\u5173\u8054\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> deleteFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<String> newColumCodes = new ArrayList<String>();
        List dataSchemeDimension = this.designDataSchemeService.getDataSchemeDimension(dataScheme.getKey());
        if (dataSchemeDimension.isEmpty()) {
            throw new Exception("\u62a5\u8868\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u9644\u4ef6\u6c60\u5173\u8054\u8868");
        }
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
        this.addColumn(filePoolTable, oldColumnMap, new ColumnInfo("MDCODE", "MDCODE", ColumnModelType.STRING, 40, this.queryEntityTableBizKeyByEntityID(dw), true), createFieldList, modifyFieldList);
        newColumCodes.add("MDCODE");
        this.addDimCol(filePoolTable, oldColumnMap, createFieldList, modifyFieldList, newColumCodes, dims);
        this.addColumn(filePoolTable, oldColumnMap, new ColumnInfo("PERIOD", "PERIOD", ColumnModelType.STRING, 9, this.queryPeriodBizKey(dateTime), true), createFieldList, modifyFieldList);
        newColumCodes.add("PERIOD");
        for (ColumnInfo column : this.fpTableColumnList) {
            this.addColumn(filePoolTable, oldColumnMap, column, createFieldList, modifyFieldList);
            newColumCodes.add(column.getCode());
        }
        Map<String, List<DesignColumnModelDefine>> indexNameColumnsMap = this.addIndex(filePoolTableName, filePoolTable, oldColumns, doInsert, createFieldList);
        if (!oldColumnMap.isEmpty()) {
            Set codes = oldColumnMap.keySet();
            for (String code : codes) {
                DesignColumnModelDefine designColumnModelDefine = (DesignColumnModelDefine)oldColumnMap.get(code);
                if (newColumCodes.contains(designColumnModelDefine.getCode())) continue;
                deleteFieldList.add(designColumnModelDefine);
            }
        }
        return this.doSaveAndDeploy(filePoolTable, doInsert, createFieldList, modifyFieldList, deleteFieldList, indexNameColumnsMap);
    }

    @NotNull
    private Map<String, List<DesignColumnModelDefine>> addIndex(String filePoolTableName, DesignTableModelDefine filePoolTable, List<DesignColumnModelDefine> oldColumns, boolean doInsert, List<DesignColumnModelDefine> createFieldList) {
        HashMap<String, List<DesignColumnModelDefine>> indexNameColumnsMap = new HashMap<String, List<DesignColumnModelDefine>>();
        String groupIndexName = filePoolTableName + "_IDX_GROUP";
        boolean haveGroupIndex = false;
        String fileIndexName = filePoolTableName + "_IDX_FILE";
        boolean haveFileIndex = false;
        String dimFieldIndexName = filePoolTableName + "_IDX_DIMF";
        boolean haveDimFieldIndex = false;
        List indexModels = this.designDataModelService.getIndexsByTable(filePoolTable.getID());
        if (null != indexModels && !indexModels.isEmpty()) {
            for (DesignIndexModelDefine indexModel : indexModels) {
                if (groupIndexName.equals(indexModel.getName())) {
                    haveGroupIndex = true;
                    continue;
                }
                if (fileIndexName.equals(indexModel.getName())) {
                    haveFileIndex = true;
                    continue;
                }
                if (!dimFieldIndexName.equals(indexModel.getName())) continue;
                haveDimFieldIndex = true;
            }
        }
        List<DesignColumnModelDefine> allFieldList = null;
        allFieldList = doInsert ? createFieldList : oldColumns;
        this.addSingleIndex(indexNameColumnsMap, groupIndexName, haveGroupIndex, allFieldList, "GROUPKEY");
        this.addSingleIndex(indexNameColumnsMap, fileIndexName, haveFileIndex, allFieldList, "FILEKEY");
        this.addUnionIndex(indexNameColumnsMap, dimFieldIndexName, haveDimFieldIndex, allFieldList);
        return indexNameColumnsMap;
    }

    private void addUnionIndex(Map<String, List<DesignColumnModelDefine>> indexNameColumnsMap, String indexName, boolean haveIndex, List<DesignColumnModelDefine> allFieldList) {
        ArrayList<DesignColumnModelDefine> dimFieldIndexFields = new ArrayList<DesignColumnModelDefine>();
        if (!haveIndex) {
            for (DesignColumnModelDefine designColumnModelDefine : allFieldList) {
                if ("ID".equals(designColumnModelDefine.getCode()) || "GROUPKEY".equals(designColumnModelDefine.getCode()) || "FILEKEY".equals(designColumnModelDefine.getCode()) || "FIELD_KEY".equals(designColumnModelDefine.getCode()) || "ISDELETE".equals(designColumnModelDefine.getCode())) continue;
                dimFieldIndexFields.add(designColumnModelDefine);
            }
            for (DesignColumnModelDefine designColumnModelDefine : allFieldList) {
                if (!"FIELD_KEY".equals(designColumnModelDefine.getCode())) continue;
                dimFieldIndexFields.add(designColumnModelDefine);
                break;
            }
        }
        if (!dimFieldIndexFields.isEmpty()) {
            indexNameColumnsMap.put(indexName, dimFieldIndexFields);
        }
    }

    private void addSingleIndex(Map<String, List<DesignColumnModelDefine>> indexNameColumnsMap, String indexName, boolean haveIndex, List<DesignColumnModelDefine> allFieldList, String colCode) {
        ArrayList<DesignColumnModelDefine> indexFields = new ArrayList<DesignColumnModelDefine>();
        if (!haveIndex) {
            for (DesignColumnModelDefine designColumnModelDefine : allFieldList) {
                if (!colCode.equals(designColumnModelDefine.getCode())) continue;
                indexFields.add(designColumnModelDefine);
                break;
            }
        }
        if (!indexFields.isEmpty()) {
            indexNameColumnsMap.put(indexName, indexFields);
        }
    }

    private void addDimCol(DesignTableModelDefine filePoolTable, Map<String, DesignColumnModelDefine> oldColumnMap, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, List<String> newColumCodes, List<String> dims) {
        if (!dims.isEmpty()) {
            for (String dim : dims) {
                IEntityDefine entity = this.iEntityMetaService.queryEntity(dim);
                if (null != entity) {
                    this.addColumn(filePoolTable, oldColumnMap, new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId()), true), createFieldList, modifyFieldList);
                    newColumCodes.add(entity.getDimensionName());
                    continue;
                }
                if (!"ADJUST".equals(dim)) continue;
                this.addColumn(filePoolTable, oldColumnMap, new ColumnInfo("ADJUST", "ADJUST", ColumnModelType.STRING, 40, "", true), createFieldList, modifyFieldList);
                newColumCodes.add("ADJUST");
            }
        }
    }

    private DesignTableModelDefine initNewTableDefine(String filePoolTableCode, DesignDataScheme dataScheme) {
        DesignTableModelDefine filePoolTableDefine = this.designDataModelService.createTableModelDefine();
        filePoolTableDefine.setCode(filePoolTableCode);
        filePoolTableDefine.setName(filePoolTableCode);
        filePoolTableDefine.setOwner("NR");
        DesignCatalogModelDefine sysTableGroupByParent = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
        filePoolTableDefine.setCatalogID(sysTableGroupByParent.getID());
        filePoolTableDefine.setSupportNrdb(true);
        filePoolTableDefine.setStorageName(dataScheme.getBizCode());
        return filePoolTableDefine;
    }

    private List<String> doSaveAndDeploy(DesignTableModelDefine filePoolTableDefine, boolean doInsert, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, List<DesignColumnModelDefine> deleteFieldList, Map<String, List<DesignColumnModelDefine>> indexNameColumnsMap) throws Exception {
        if (doInsert) {
            this.designDataModelService.insertTableModelDefine(filePoolTableDefine);
        } else {
            this.designDataModelService.updateTableModelDefine(filePoolTableDefine);
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
        if (!indexNameColumnsMap.isEmpty()) {
            for (String indexName : indexNameColumnsMap.keySet()) {
                List<DesignColumnModelDefine> designColumnModelDefines = indexNameColumnsMap.get(indexName);
                String[] fields = new String[designColumnModelDefines.size()];
                for (int i = 0; i < designColumnModelDefines.size(); ++i) {
                    fields[i] = designColumnModelDefines.get(i).getID();
                }
                this.designDataModelService.addIndexToTable(filePoolTableDefine.getID(), fields, indexName, IndexModelType.NORMAL);
            }
        }
        if (!this.dataBaseLimitModeProvider.databaseLimitMode()) {
            if (createFieldList.size() > 0 || modifyFieldList.size() > 0 || deleteFieldList.size() > 0 || !indexNameColumnsMap.isEmpty()) {
                this.dataModelDeployService.deployTableUnCheck(filePoolTableDefine.getID());
            }
            return Collections.emptyList();
        }
        return this.dataModelDeployService.getDeployTableSqls(filePoolTableDefine.getID());
    }

    private void addColumn(DesignTableModelDefine filePoolTableDefine, Map<String, DesignColumnModelDefine> oldColumnMap, ColumnInfo columnInfo, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) {
        DesignColumnModelDefine userColumn = this.initField(filePoolTableDefine.getID(), columnInfo.getReferFieldID(), columnInfo);
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
            String keys = StringUtils.isEmpty((String)filePoolTableDefine.getBizKeys()) ? userColumn.getID() : filePoolTableDefine.getBizKeys() + ";" + userColumn.getID();
            filePoolTableDefine.setBizKeys(keys);
            filePoolTableDefine.setKeys(keys);
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

    public List<String> getDeployTable(String taskId) throws Exception {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        if (dataScheme == null) {
            return new ArrayList<String>();
        }
        return this.deployFilePoolTable(dataScheme);
    }

    public void getDeployTableBydataSchemeKey(String dataSchemeKey) throws Exception {
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(dataSchemeKey);
        if (dataScheme == null) {
            return;
        }
        this.deployFilePoolTable(dataScheme);
    }

    public void registerTable(String taskId) {
        try {
            DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
            DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
            String filePoolTableName = "NR_FILE_" + dataScheme.getBizCode();
            DesignTableModelDefine filePoolTable = this.designDataModelService.getTableModelDefineByCode(filePoolTableName);
            if (filePoolTable == null) {
                filePoolTable = this.designDataModelService.createTableModelDefine();
            }
            this.dataModelRegisterService.registerTable(filePoolTable.getID());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void deleteTable(DataScheme dataScheme) throws Exception {
        String filePoolTableName = "NR_FILE_" + dataScheme.getBizCode();
        DesignTableModelDefine filePoolTable = this.designDataModelService.getTableModelDefineByCode(filePoolTableName);
        this.designDataModelService.deleteTableModelDefine(filePoolTable.getID());
        this.dataModelDeployService.deployTableUnCheck(filePoolTable.getID());
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
            if (null == dataScheme) {
                this.deleteTable(runTimeDataScheme);
                return;
            }
            this.deployFilePoolTable(dataScheme);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}

