/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener
 *  com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.CatalogModelService
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  javax.annotation.Resource
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.enumcheck.deploy;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener;
import com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.enumcheck.deploy.ColumnInfo;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.CatalogModelService;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@NpDefinitionObserver(type={MessageType.NRPUBLISHTASK})
public class EnumCheckObserver
implements Observer,
DeployPrepareEventListener,
IParamDeployFinishListener {
    private static final Logger logger = LoggerFactory.getLogger(EnumCheckObserver.class);
    @Autowired
    private IDesignTimeViewController nrDesignController;
    @Resource
    private DesignDataModelService designDataModelService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private CatalogModelService catalogModelService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DataBaseLimitModeProvider dataBaseLimitModeProvider;
    private final List<ColumnInfo> enumCheckColumnList = new ArrayList<ColumnInfo>();
    private static final List<String> AllSqls = new ArrayList<String>();

    public EnumCheckObserver() {
        this.enumCheckColumnList.add(new ColumnInfo("MJZD_KEY", "MJZD_KEY", ColumnModelType.STRING, 36, "", true));
        this.enumCheckColumnList.add(new ColumnInfo("MJZD_GROUPKEY", "MJZD_GROUPKEY", ColumnModelType.STRING, 50, ""));
        this.enumCheckColumnList.add(new ColumnInfo("MJZD_ASYNCTASKID", "MJZD_ASYNCTASKID", ColumnModelType.STRING, 50, ""));
        this.enumCheckColumnList.add(new ColumnInfo("MJZD_VIEWTYPE", "MJZD_VIEWTYPE", ColumnModelType.INTEGER, 10, ""));
        this.enumCheckColumnList.add(new ColumnInfo("MJZD_UNITORDER", "MJZD_UNITORDER", ColumnModelType.INTEGER, 10, ""));
        this.enumCheckColumnList.add(new ColumnInfo("MJZD_GROUPDETAIL", "MJZD_GROUPDETAIL", ColumnModelType.CLOB, 0, ""));
        this.enumCheckColumnList.add(new ColumnInfo("MJZD_OPERATOR", "MJZD_OPERATOR", ColumnModelType.STRING, 50, ""));
        this.enumCheckColumnList.add(new ColumnInfo("MJZD_UPTIME", "MJZD_UPTIME", ColumnModelType.DATETIME, 0, ""));
    }

    public boolean isAsyn() {
        return false;
    }

    public String getName() {
        return "\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u7ed3\u679c\u8868" + this.getClass().getName();
    }

    public void excute(Object[] objs) throws Exception {
        if (objs == null || objs.length == 0) {
            return;
        }
        for (Object obj : objs) {
            String taskId;
            DesignTaskDefine taskDefine;
            if (!(obj instanceof String) || "2.0".equals((taskDefine = this.nrDesignController.queryTaskDefine(taskId = (String)obj)).getVersion())) continue;
            this.getDeployTable(taskId);
        }
    }

    public List<String> getDeployTable(String taskId) throws Exception {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
        List schemes = this.nrDesignController.queryFormSchemeByTask(taskId);
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        if (schemes == null) {
            return Collections.emptyList();
        }
        AllSqls.clear();
        for (DesignFormSchemeDefine scheme : schemes) {
            this.deployEnumCheckResult(taskDefine, scheme, dataScheme);
        }
        return AllSqls;
    }

    /*
     * WARNING - void declaration
     */
    private void deployEnumCheckResult(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme, DataScheme dataScheme) throws Exception {
        boolean doInsert;
        String tableName = "NR_MJZDJCJG_" + scheme.getFormSchemeCode();
        DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(tableName);
        List oldColumns = null;
        Map<Object, Object> oldColumnMap = new HashMap();
        boolean bl = doInsert = tableModelDefine == null;
        if (doInsert) {
            tableModelDefine = this.initNewTableDefine(tableName, dataScheme);
        } else {
            tableModelDefine.setBizKeys("");
            tableModelDefine.setKeys("");
            oldColumns = this.designDataModelService.getColumnModelDefinesByTable(tableModelDefine.getID());
            oldColumnMap = oldColumns.stream().collect(Collectors.toMap(IModelDefineItem::getCode, t -> t));
        }
        tableModelDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868");
        tableModelDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868");
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> deleteFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<String> newColumCodes = new ArrayList<String>();
        String dimsStr = taskDefine.getDims();
        if (StringUtils.isNotEmpty((String)dimsStr)) {
            void var17_19;
            Object dims;
            Object object = dims = dimsStr.split(";");
            int n = ((Object)object).length;
            boolean bl2 = false;
            while (var17_19 < n) {
                Object dim = object[var17_19];
                IEntityDefine entity = this.entityMetaService.queryEntity((String)dim);
                if (null != entity) {
                    this.addColumn(tableModelDefine, oldColumnMap, new ColumnInfo(entity.getDimensionName(), entity.getDimensionName(), ColumnModelType.STRING, 50, this.queryEntityBizKeyByEntityId(entity.getId())), createFieldList, modifyFieldList);
                    newColumCodes.add(entity.getDimensionName());
                } else if ("ADJUST".equals(dim)) {
                    this.addColumn(tableModelDefine, oldColumnMap, new ColumnInfo("ADJUST", "ADJUST", ColumnModelType.STRING, 40, "", true), createFieldList, modifyFieldList);
                    newColumCodes.add("ADJUST");
                }
                ++var17_19;
            }
        }
        for (ColumnInfo column : this.enumCheckColumnList) {
            this.addColumn(tableModelDefine, oldColumnMap, column, createFieldList, modifyFieldList);
            newColumCodes.add(column.getCode());
        }
        Map<String, List<DesignColumnModelDefine>> indexNameColumnsMap = this.addIndex(tableName, tableModelDefine, oldColumns, doInsert, createFieldList);
        if (!oldColumnMap.isEmpty()) {
            Set<Object> codes = oldColumnMap.keySet();
            for (String string : codes) {
                DesignColumnModelDefine designColumnModelDefine = (DesignColumnModelDefine)oldColumnMap.get(string);
                if (newColumCodes.contains(designColumnModelDefine.getCode())) continue;
                deleteFieldList.add(designColumnModelDefine);
            }
        }
        this.doSaveAndDeploy(tableModelDefine, doInsert, createFieldList, modifyFieldList, deleteFieldList, indexNameColumnsMap);
    }

    private DesignTableModelDefine initNewTableDefine(String commentTableCode, DataScheme dataScheme) {
        DesignTableModelDefine tableModelDefine = this.designDataModelService.createTableModelDefine();
        tableModelDefine.setCode(commentTableCode);
        tableModelDefine.setName(commentTableCode);
        tableModelDefine.setOwner("NR");
        DesignCatalogModelDefine sysTableGroupByParent = this.catalogModelService.createChildCatalogModelDefine("10000000-1100-1110-1111-000000000000");
        tableModelDefine.setCatalogID(sysTableGroupByParent.getID());
        tableModelDefine.setSupportNrdb(true);
        tableModelDefine.setStorageName(dataScheme.getBizCode());
        return tableModelDefine;
    }

    private void addColumn(DesignTableModelDefine tableModelDefine, Map<String, DesignColumnModelDefine> oldColumnMap, ColumnInfo columnInfo, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) {
        DesignColumnModelDefine columnModelDefine = this.initField(tableModelDefine.getID(), columnInfo);
        DesignColumnModelDefine oldUserColumn = oldColumnMap.get(columnInfo.getCode());
        if (oldUserColumn == null) {
            createFieldList.add(columnModelDefine);
        } else {
            columnModelDefine.setID(oldUserColumn.getID());
            if (!this.checkColumnEquals(columnModelDefine, oldUserColumn)) {
                modifyFieldList.add(columnModelDefine);
            }
        }
        if (columnInfo.isKeyField()) {
            String keys = StringUtils.isEmpty((String)tableModelDefine.getBizKeys()) ? columnModelDefine.getID() : tableModelDefine.getBizKeys() + ";" + columnModelDefine.getID();
            tableModelDefine.setBizKeys(keys);
            tableModelDefine.setKeys(keys);
        }
    }

    private DesignColumnModelDefine initField(String tableKey, ColumnInfo columnInfo) {
        DesignColumnModelDefine fieldDefine = this.designDataModelService.createColumnModelDefine();
        fieldDefine.setCode(columnInfo.getCode());
        fieldDefine.setName(columnInfo.getCode());
        fieldDefine.setColumnType(columnInfo.getType());
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(columnInfo.getSize());
        fieldDefine.setReferColumnID(columnInfo.getReferFieldId());
        return fieldDefine;
    }

    private boolean checkColumnEquals(DesignColumnModelDefine o1, DesignColumnModelDefine o2) {
        return o1.getCode().equalsIgnoreCase(o2.getCode()) && o1.getColumnType() == o2.getColumnType() && (StringUtils.isEmpty((String)o1.getReferColumnID()) && StringUtils.isEmpty((String)o2.getReferColumnID()) || o1.getReferColumnID().equals(o2.getReferColumnID())) && o1.getPrecision() == o2.getPrecision();
    }

    private String queryEntityBizKeyByEntityId(String entityId) {
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        IEntityAttribute referFieldId = entityModel.getBizKeyField();
        if (referFieldId == null) {
            referFieldId = entityModel.getRecordKeyField();
        }
        return referFieldId.getID();
    }

    @NotNull
    private Map<String, List<DesignColumnModelDefine>> addIndex(String tableName, DesignTableModelDefine designTableModelDefine, List<DesignColumnModelDefine> oldColumns, boolean doInsert, List<DesignColumnModelDefine> createFieldList) {
        HashMap<String, List<DesignColumnModelDefine>> indexNameColumnsMap = new HashMap<String, List<DesignColumnModelDefine>>();
        String createTimeIndexName = tableName + "_IDX_TIME";
        boolean haveCreateTimeIndex = false;
        List indexModels = this.designDataModelService.getIndexsByTable(designTableModelDefine.getID());
        if (null != indexModels && !indexModels.isEmpty()) {
            for (DesignIndexModelDefine indexModel : indexModels) {
                if (!createTimeIndexName.equals(indexModel.getName())) continue;
                haveCreateTimeIndex = true;
            }
        }
        ArrayList<DesignColumnModelDefine> indexFields = new ArrayList<DesignColumnModelDefine>();
        if (!haveCreateTimeIndex) {
            List<DesignColumnModelDefine> allFieldList = doInsert ? createFieldList : oldColumns;
            for (DesignColumnModelDefine designColumnModelDefine : allFieldList) {
                if (!"MJZD_UPTIME".equals(designColumnModelDefine.getCode())) continue;
                indexFields.add(designColumnModelDefine);
                break;
            }
        }
        if (!indexFields.isEmpty()) {
            indexNameColumnsMap.put(createTimeIndexName, indexFields);
        }
        return indexNameColumnsMap;
    }

    private void doSaveAndDeploy(DesignTableModelDefine tableModelDefine, boolean doInsert, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, List<DesignColumnModelDefine> deleteFieldList, Map<String, List<DesignColumnModelDefine>> indexNameColumnsMap) throws Exception {
        if (doInsert) {
            this.designDataModelService.insertTableModelDefine(tableModelDefine);
        } else {
            this.designDataModelService.updateTableModelDefine(tableModelDefine);
        }
        if (createFieldList.size() > 0) {
            this.designDataModelService.insertColumnModelDefines(createFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (modifyFieldList.size() > 0) {
            this.designDataModelService.updateColumnModelDefines(modifyFieldList.toArray(new DesignColumnModelDefine[1]));
        }
        if (deleteFieldList.size() > 0) {
            String[] deleteIds = (String[])deleteFieldList.stream().map(IModelDefineItem::getID).toArray(String[]::new);
            this.designDataModelService.deleteColumnModelDefines(deleteIds);
        }
        if (!indexNameColumnsMap.isEmpty()) {
            for (String indexName : indexNameColumnsMap.keySet()) {
                List<DesignColumnModelDefine> designColumnModelDefines = indexNameColumnsMap.get(indexName);
                String[] fields = new String[designColumnModelDefines.size()];
                for (int i = 0; i < designColumnModelDefines.size(); ++i) {
                    fields[i] = designColumnModelDefines.get(i).getID();
                }
                this.designDataModelService.addIndexToTable(tableModelDefine.getID(), fields, indexName, IndexModelType.NORMAL);
            }
        }
        if (!(createFieldList.isEmpty() && modifyFieldList.isEmpty() && deleteFieldList.isEmpty() && indexNameColumnsMap.isEmpty())) {
            if (!this.dataBaseLimitModeProvider.databaseLimitMode()) {
                this.dataModelDeployService.deployTableUnCheck(tableModelDefine.getID());
            } else {
                AllSqls.addAll(this.dataModelDeployService.getDeployTableSqls(tableModelDefine.getID()));
            }
        }
    }

    public void onDeploy(DeployParams deployParams) {
        DeployItem formScheme = deployParams.getFormScheme();
        if (null == formScheme) {
            return;
        }
        Set runTimeKeys = formScheme.getRunTimeKeys();
        Set designTimeKeys = formScheme.getDesignTimeKeys();
        HashSet runKeysCopy = new HashSet(runTimeKeys);
        runKeysCopy.removeAll(designTimeKeys);
        if (runKeysCopy.isEmpty()) {
            return;
        }
        for (String runTimeKey : runKeysCopy) {
            FormSchemeDefine scheme = this.runTimeViewController.getFormScheme(runTimeKey);
            if (null == scheme) continue;
            String schemeCode = scheme.getFormSchemeCode();
            this.deployDeleteTableByCode("NR_MJZDJCJG_" + schemeCode);
        }
    }

    private void deployDeleteTableByCode(String tableCode) {
        try {
            DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
            if (tableModelDefine == null) {
                return;
            }
            this.designDataModelService.deleteTableModelDefine(tableModelDefine.getID());
            this.dataModelDeployService.deployTableUnCheck(tableModelDefine.getID());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void onAdd(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u53d1\u5e03\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u8868");
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(define.getTaskKey());
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        try {
            this.deployEnumCheckResult(taskDefine, designFormSchemeDefine, dataScheme);
            progressConsumer.accept("\u53d1\u5e03\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u8868\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            warningConsumer.accept("\u53d1\u5e03\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u8868\u5931\u8d25");
        }
    }

    public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        DesignFormSchemeDefine designFormScheme = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        FormSchemeDefine runTimeformScheme = this.runTimeViewController.getFormScheme(define.getKey());
        if (null == designFormScheme && null != runTimeformScheme) {
            progressConsumer.accept("\u6b63\u5728\u5220\u9664\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u8868");
            this.deployDeleteTableByCode("NR_MJZDJCJG_" + runTimeformScheme.getFormSchemeCode());
            progressConsumer.accept("\u5220\u9664\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u8868\u6210\u529f");
        }
    }

    public void onUpdate(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u66f4\u65b0\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u8868");
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(define.getTaskKey());
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        DataScheme dataScheme = this.runtimeDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        try {
            this.deployEnumCheckResult(taskDefine, designFormSchemeDefine, dataScheme);
            progressConsumer.accept("\u66f4\u65b0\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u8868\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            warningConsumer.accept("\u66f4\u65b0\u679a\u4e3e\u5b57\u5178\u68c0\u67e52.0\u8868\u5931\u8d25");
        }
    }
}

