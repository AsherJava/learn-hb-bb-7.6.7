/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.dataentry.deploy.util.NvwaDataModelDeployUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
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
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.internal.controller;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.dataentry.deploy.util.NvwaDataModelDeployUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@NpDefinitionObserver(type={MessageType.NRPUBLISHTASK})
public class MultCheckObserver
implements Observer,
DeployPrepareEventListener,
IParamDeployFinishListener {
    private static final Logger log = LoggerFactory.getLogger(MultCheckObserver.class);
    @Autowired
    private NRDesignTimeController nrDesignController;
    @Autowired
    private NvwaDataModelDeployUtil nvwaDataModelDeployUtil;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    DataBaseLimitModeProvider dataBaseLimitModeProvider;
    @Autowired
    DataModelDeployServiceImpl dataModelDeployService;
    @Autowired
    ObserverDao observerDao;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private static final List<String> AllSqls = new ArrayList<String>();

    public boolean isAsyn() {
        return false;
    }

    public String getName() {
        return "\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868" + this.getClass().getName();
    }

    public void excute(Object[] objs) throws Exception {
        if (objs == null) {
            return;
        }
        for (Object obj : objs) {
            String taskID;
            TaskDefine taskDefine;
            if (!(obj instanceof String) || "2.0".equals((taskDefine = this.runTimeViewController.queryTaskDefine(taskID = (String)obj)).getVersion())) continue;
            this.getDeployTable(taskID);
        }
    }

    public List<String> getDeployTable(String taskId) throws Exception {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(taskId);
        List schemes = this.nrDesignController.queryFormSchemeByTask(taskId);
        if (schemes == null) {
            return new ArrayList<String>();
        }
        AllSqls.clear();
        for (DesignFormSchemeDefine scheme : schemes) {
            this.deployMultCheckResult(taskDefine, scheme);
        }
        return AllSqls;
    }

    private void deployMultCheckResult(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme) throws Exception {
        String tableCode = "SYS_MULTCHECK_" + scheme.getFormSchemeCode();
        DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
        boolean doInsert = true;
        if (tableDefine == null) {
            tableDefine = this.designDataModelService.createTableModelDefine();
        } else {
            doInsert = false;
        }
        tableDefine.setCode(tableCode);
        tableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868");
        tableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u7efc\u5408\u5ba1\u6838\u7ed3\u679c\u8868");
        tableDefine.setName(tableCode);
        tableDefine.setType(TableModelType.DEFAULT);
        tableDefine.setKind(TableModelKind.DEFAULT);
        tableDefine.setOwner("NR");
        String tableKey = tableDefine.getID();
        StringBuffer tableEnityMasterKeys = new StringBuffer();
        ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
        ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
        tableEnityMasterKeys.append(this.initField_String_Key(tableKey, "ZHSH_KEY", null, createFieldList, modifyFieldList)).append(";");
        this.initField_String(tableKey, "ZHSH_CHECKITEMKEY", null, createFieldList, modifyFieldList, 50);
        this.initField_String(tableKey, "ZHSH_CHECKITEMNAME", null, createFieldList, modifyFieldList, 50);
        this.initField_String(tableKey, "ZHSH_CHECKTYPE", null, createFieldList, modifyFieldList, 50);
        this.initField_String(tableKey, "ZHSH_FORMSCHEMEKEY", null, createFieldList, modifyFieldList, 50);
        this.initField_String(tableKey, "ZHSH_ASYNCTASKID", null, createFieldList, modifyFieldList, 50);
        this.initField_String(tableKey, "ZHSH_CHECKITEMASYNCTASKID", null, createFieldList, modifyFieldList, 50);
        this.initField_String(tableKey, "ZHSH_CURRENTENTITY", null, createFieldList, modifyFieldList, 50);
        this.initField(tableKey, "ZHSH_ISPASS", null, createFieldList, modifyFieldList, ColumnModelType.INTEGER, 10, "0");
        this.initField(tableKey, "ZHSH_ORDER", null, createFieldList, modifyFieldList, ColumnModelType.INTEGER, 10, "0");
        this.initField_TEXT(tableKey, "ZHSH_CHECKRESULT", null, createFieldList, modifyFieldList);
        this.initField_TEXT(tableKey, "ZHSH_CHECKPARAMS", null, createFieldList, modifyFieldList);
        this.initField_TEXT(tableKey, "ZHSH_CHECKDETAIL", null, createFieldList, modifyFieldList);
        this.initField_DATE(tableKey, "ZHSH_UPDATETIME", null, createFieldList, modifyFieldList);
        this.initField_String(tableKey, "ZHSH_OPERATOR", null, createFieldList, modifyFieldList, 50);
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

    private String initField_String_Key(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        DesignColumnModelDefine fieldDefine = this.designDataModelService.getColumnModelDefineByCode(tableKey, fieldCode);
        if (fieldDefine != null) {
            return fieldDefine.getID();
        }
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.STRING, 36, "");
    }

    private String initField_DATE(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.DATETIME, 0, "");
    }

    private void initField_TEXT(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList) throws Exception {
        this.initField(tableKey, fieldCode, null, createFieldList, modifyFieldList, ColumnModelType.CLOB, 0, "");
    }

    private String initField_String(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, int size) throws Exception {
        return this.initField(tableKey, fieldCode, referField, createFieldList, modifyFieldList, ColumnModelType.STRING, size, "");
    }

    private String initField(String tableKey, String fieldCode, String referField, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, ColumnModelType fieldType, int size, String defaultValue) {
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
        DeployItem formSchemeDeploy = deployParams.getFormScheme();
        if (formSchemeDeploy == null) {
            return;
        }
        Set runTimeKeys = formSchemeDeploy.getRunTimeKeys();
        Set designTimeKeys = formSchemeDeploy.getDesignTimeKeys();
        HashSet runKeysCopy = new HashSet(runTimeKeys);
        runKeysCopy.removeAll(designTimeKeys);
        if (CollectionUtils.isEmpty(runKeysCopy)) {
            return;
        }
        for (String schemeKey : runKeysCopy) {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(schemeKey);
            if (formScheme == null) continue;
            try {
                this.doDeleteAndDeploy(formScheme);
            }
            catch (Exception e) {
                log.error("\u5220\u9664\u7efc\u5408\u5ba1\u68381.0\u7ed3\u679c\u8868\u5931\u8d25", e);
            }
        }
    }

    public void onAdd(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        this.onUpdate(define, warningConsumer, progressConsumer);
    }

    public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u8fd9\u662f\u53d1\u5e03\u8fdb\u5ea6\uff0c\u6b63\u5728\u5220\u9664\u5ba1\u6838\u8868");
        try {
            this.doDeleteAndDeploy(define);
        }
        catch (Exception e) {
            warningConsumer.accept("\u66f4\u65b0\u7efc\u5408\u5ba1\u68381.0\u7ed3\u679c\u8868\u5931\u8d25,\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
    }

    public void onUpdate(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u66f4\u65b0\u7efc\u5408\u5ba1\u68381.0\u7ed3\u679c\u8868");
        try {
            this.doSaveAndDeploy(define);
        }
        catch (Exception e) {
            warningConsumer.accept("\u66f4\u65b0\u7efc\u5408\u5ba1\u68381.0\u7ed3\u679c\u8868\u5931\u8d25,\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
    }

    private void doSaveAndDeploy(FormSchemeDefine scheme) throws Exception {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(scheme.getTaskKey());
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(scheme.getKey());
        this.deployMultCheckResult(taskDefine, designFormSchemeDefine);
    }

    private void doDeleteAndDeploy(FormSchemeDefine scheme) throws Exception {
        String tableCode = "SYS_MULTCHECK_" + scheme.getFormSchemeCode();
        DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode(tableCode);
        if (tableDefine == null) {
            return;
        }
        this.designDataModelService.deleteTableModelDefine(tableDefine.getID());
        this.dataModelDeployService.deployTableUnCheck(tableDefine.getID());
    }
}

