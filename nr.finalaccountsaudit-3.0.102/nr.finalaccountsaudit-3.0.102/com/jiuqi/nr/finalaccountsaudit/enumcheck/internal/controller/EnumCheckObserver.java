/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.dataentry.deploy.util.NvwaDataModelDeployUtil
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener
 *  com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.service.DataModelDeployServiceImpl
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.finalaccountsaudit.enumcheck.internal.controller;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.dataentry.deploy.util.NvwaDataModelDeployUtil;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener;
import com.jiuqi.nr.definition.paramcheck.DataBaseLimitModeProvider;
import com.jiuqi.nr.finalaccountsaudit.dao.ObserverDao;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.service.DataModelDeployServiceImpl;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Resource;
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
    IDesignTimeViewController nrDesignController;
    @Autowired
    IDataDefinitionDesignTimeController npDesignController;
    @Autowired
    IEntityViewRunTimeController entityController;
    @Resource
    private DesignDataModelService designDataModelService;
    @Autowired
    private NvwaDataModelDeployUtil nvwaDataModelDeployUtil;
    @Autowired
    DataBaseLimitModeProvider dataBaseLimitModeProvider;
    @Resource
    DataModelDeployServiceImpl dataModelDeployService;
    @Autowired
    ObserverDao observerDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private static final List<String> AllSqls = new ArrayList<String>();

    public boolean isAsyn() {
        return false;
    }

    public void excute(Object[] objs) throws Exception {
        if (objs == null || objs.length == 0) {
            return;
        }
        for (Object obj : objs) {
            String taskID;
            DesignTaskDefine taskDefine;
            if (!(obj instanceof String) || "2.0".equals((taskDefine = this.nrDesignController.queryTaskDefine(taskID = (String)obj)).getVersion())) continue;
            this.getDeployTable(taskID);
        }
    }

    public String getName() {
        return "\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u7ed3\u679c\u8868" + this.getClass().getName();
    }

    public List<String> getDeployTable(String taskId) throws Exception {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
        List schemes = this.nrDesignController.queryFormSchemeByTask(taskId);
        if (schemes == null) {
            return new ArrayList<String>();
        }
        AllSqls.clear();
        for (DesignFormSchemeDefine scheme : schemes) {
            this.deployEnumCheckResult(taskDefine, scheme);
        }
        return AllSqls;
    }

    private void deployEnumCheckResult(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme) throws Exception {
        String tableCode = "SYS_MJZDJCJG_" + scheme.getFormSchemeCode();
        DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
        boolean doInsert = true;
        if (tableDefine == null) {
            tableDefine = this.designDataModelService.createTableModelDefine();
        } else {
            doInsert = false;
        }
        tableDefine.setCode(tableCode);
        tableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u5ba1\u6838\u7ed3\u679c\u8868");
        tableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u5ba1\u6838\u7ed3\u679c\u8868");
        tableDefine.setName(tableCode);
        tableDefine.setType(TableModelType.DEFAULT);
        tableDefine.setKind(TableModelKind.DEFAULT);
        tableDefine.setOwner("NR");
        String tableKey = tableDefine.getID();
        StringBuffer tableEnityMasterKeys = new StringBuffer();
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        tableEnityMasterKeys.append(this.initField_String_Key(tableKey, "MJZD_KEY", null, createFieldList, modifyFieldList)).append(";");
        this.initField_String(tableKey, "MJZD_ASYNCTASKID", null, createFieldList, modifyFieldList, 50);
        this.initField_String(tableKey, "MJZD_GROUPKEY", null, createFieldList, modifyFieldList, 50);
        this.initField_TEXT(tableKey, "MJZD_GROUPDETAIL", null, createFieldList, modifyFieldList);
        this.initField(tableKey, "MJZD_VIEWTYPE", null, createFieldList, modifyFieldList, ColumnModelType.INTEGER, 10, "0");
        this.initField(tableKey, "MJZD_UNITORDER", null, createFieldList, modifyFieldList, ColumnModelType.INTEGER, 10, "0");
        this.initField_DATE(tableKey, "MJZD_UPTIME", null, createFieldList, modifyFieldList);
        this.initField_String(tableKey, "MJZD_OPERATOR", null, createFieldList, modifyFieldList, 50);
        tableDefine.setBizKeys(tableEnityMasterKeys.toString());
        tableDefine.setKeys(tableEnityMasterKeys.toString());
        if (doInsert) {
            DesignCatalogModelDefine sysTableGroup = this.nvwaDataModelDeployUtil.createCatlogModelDefine();
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
        if (!this.dataBaseLimitModeProvider.databaseLimitMode()) {
            this.nvwaDataModelDeployUtil.deployTable(tableDefine.getID());
        } else if (!this.observerDao.tableExists(tableCode)) {
            AllSqls.addAll(this.dataModelDeployService.getDeployTableSqls(tableDefine.getID()));
        }
    }

    private void initEntityFields(DesignTaskDefine taskDefine, FormSchemeDefine scheme, StringBuffer tableEnityMasterKeys, String tableKey, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        String entitiesKey = this.nrDesignController.getFormSchemeEntity(scheme.getKey());
        if (StringUtils.isEmpty((String)entitiesKey)) {
            entitiesKey = taskDefine.getMasterEntitiesKey();
        }
        if (StringUtils.isEmpty((String)entitiesKey)) {
            throw new Exception("\u62a5\u8868\u65b9\u6848\u3001\u4efb\u52a1\u6ca1\u6709\u8bbe\u7f6e\u4e3b\u952e\u65e0\u6cd5\u521b\u5efa\u5ba1\u6838\u76f8\u5173\u8868");
        }
        String[] entitiesKeyArr = entitiesKey.split(";");
        this.nvwaDataModelDeployUtil.initDwPeriodDimField(tableKey, tableEnityMasterKeys, entitiesKeyArr, createFieldList, modifyFieldList);
    }

    private String initField_DATE(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.DATETIME, 0, "");
    }

    private String initField_Period(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        DesignColumnModelDefine fieldDefine = this.nvwaDataModelDeployUtil.queryFieldDefinesByCode(fieldCode, tableKey);
        if (fieldDefine == null) {
            fieldDefine = this.nvwaDataModelDeployUtil.createField();
            createFieldList.add(fieldDefine);
        } else {
            modifyFieldList.add(fieldDefine);
        }
        fieldDefine.setCode(fieldCode);
        fieldDefine.setName(fieldCode);
        fieldDefine.setColumnType(ColumnModelType.STRING);
        fieldDefine.setTableID(tableKey);
        fieldDefine.setPrecision(9);
        if (referField != null) {
            fieldDefine.setReferColumnID(referField);
        }
        return fieldDefine.getID();
    }

    private void initField_TEXT(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        this.initField(tableKey, fieldCode, null, createFieldList, modifyFieldList, ColumnModelType.CLOB, 0, "");
    }

    private String initField_String_Key(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        DesignColumnModelDefine fieldDefine = this.designDataModelService.getColumnModelDefineByCode(tableKey, fieldCode);
        if (fieldDefine != null) {
            return fieldDefine.getID();
        }
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.STRING, 36, "");
    }

    private String initField_String(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.STRING, size, "");
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
        fieldDefine.setName(fieldCode);
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
            this.deployDeleteTableByCode("SYS_MJZDJCJG_" + schemeCode);
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
        progressConsumer.accept("\u6b63\u5728\u53d1\u5e03\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u8868");
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(define.getTaskKey());
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        try {
            this.deployEnumCheckResult(taskDefine, designFormSchemeDefine);
            progressConsumer.accept("\u53d1\u5e03\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u8868\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            warningConsumer.accept("\u53d1\u5e03\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u8868\u5931\u8d25");
        }
    }

    public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        DesignFormSchemeDefine designFormScheme = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        FormSchemeDefine runTimeformScheme = this.runTimeViewController.getFormScheme(define.getKey());
        if (null == designFormScheme && null != runTimeformScheme) {
            progressConsumer.accept("\u6b63\u5728\u5220\u9664\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u8868");
            this.deployDeleteTableByCode("SYS_MJZDJCJG_" + runTimeformScheme.getFormSchemeCode());
            progressConsumer.accept("\u5220\u9664\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u8868\u6210\u529f");
        }
    }

    public void onUpdate(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u66f4\u65b0\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u8868");
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(define.getTaskKey());
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        try {
            this.deployEnumCheckResult(taskDefine, designFormSchemeDefine);
            progressConsumer.accept("\u66f4\u65b0\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u8868\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            warningConsumer.accept("\u66f4\u65b0\u679a\u4e3e\u5b57\u5178\u68c0\u67e5\u8868\u5931\u8d25");
        }
    }
}

