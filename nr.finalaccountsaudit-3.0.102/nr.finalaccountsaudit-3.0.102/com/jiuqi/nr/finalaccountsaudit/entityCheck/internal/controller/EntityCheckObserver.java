/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.datascheme.api.DesignDataScheme
 *  com.jiuqi.nr.datascheme.api.service.IDataBaseLimitModeProvider
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datastatus.internal.model.DeployColumnCollection
 *  com.jiuqi.nr.datastatus.internal.util.NvwaDataModelUtil
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.service.DataModelDeployServiceImpl
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.finalaccountsaudit.entityCheck.internal.controller;

import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.datascheme.api.DesignDataScheme;
import com.jiuqi.nr.datascheme.api.service.IDataBaseLimitModeProvider;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datastatus.internal.model.DeployColumnCollection;
import com.jiuqi.nr.datastatus.internal.util.NvwaDataModelUtil;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener;
import com.jiuqi.nr.finalaccountsaudit.dao.ObserverDao;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
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
public class EntityCheckObserver
implements Observer,
DeployPrepareEventListener,
IParamDeployFinishListener {
    private static final Logger log = LoggerFactory.getLogger(EntityCheckObserver.class);
    @Autowired
    private IDesignTimeViewController nrDesignController;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private IDataBaseLimitModeProvider dataBaseLimitModeProvider;
    @Autowired
    private DataModelDeployServiceImpl dataModelDeployService;
    @Autowired
    private ObserverDao observerDao;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private NvwaDataModelUtil nvwaDataModelUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    private static final List<String> AllSqls = new ArrayList<String>();

    public boolean isAsyn() {
        return false;
    }

    public String getName() {
        return "\u5bf9\u5e94\u6570\u636e\u53d1\u5e03\u72b6\u6001\u8868:" + this.getClass().getName();
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
            this.deployEntityCheckResult(taskDefine, scheme);
        }
        return AllSqls;
    }

    private void deployEntityCheckResult(DesignTaskDefine taskDefine, DesignFormSchemeDefine scheme) throws Exception {
        String tableCode = "SYS_ENTITYCHECKUP_" + scheme.getFormSchemeCode();
        DesignTableModelDefine tableModelDefine = this.nvwaDataModelUtil.getDesTableByCode(tableCode);
        boolean doInsert = true;
        if (tableModelDefine == null) {
            tableModelDefine = this.nvwaDataModelUtil.createTable();
        } else {
            doInsert = false;
        }
        this.nvwaDataModelUtil.initTableModel((TaskDefine)taskDefine, (FormSchemeDefine)scheme, tableCode, tableModelDefine, "\u6237\u6570\u6838\u5bf9\u7ed3\u679c\u8868");
        String tableID = tableModelDefine.getID();
        DeployColumnCollection deployColumnCollection = new DeployColumnCollection(this.nvwaDataModelUtil.getAllDesColumnsMap(tableID));
        ColumnModelDefine formCol1 = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 50, "", tableID, "ECU_FC_KEY", null, deployColumnCollection);
        ColumnModelDefine formCol2 = this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 50, "", tableID, "ECU_UNIT_KEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 50, "", tableID, "ECU_TK_KEY", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 64, "", tableID, "ECU_UNITNAME", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 10, "", tableID, "ECU_CHECK", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 32, "", tableID, "ECU_JSYS", null, deployColumnCollection);
        this.nvwaDataModelUtil.initColumnModel(ColumnModelType.STRING, 80, "00000000-0000-0000-0000-000000000000", tableID, "VERSIONID", null, deployColumnCollection);
        String masterEntityKeys = this.nvwaDataModelUtil.initEntityColumns((TaskDefine)taskDefine, (FormSchemeDefine)scheme, tableID, deployColumnCollection);
        String keys = masterEntityKeys + formCol1.getID() + ";" + formCol2.getID() + ";";
        tableModelDefine.setKeys(keys);
        tableModelDefine.setBizKeys(keys);
        DesignDataScheme dataScheme = this.designDataSchemeService.getDataScheme(taskDefine.getDataScheme());
        if (dataScheme != null) {
            tableModelDefine.setSupportNrdb(true);
            tableModelDefine.setStorageName(dataScheme.getBizCode());
        }
        if (doInsert) {
            DesignCatalogModelDefine catalog = this.nvwaDataModelUtil.createCatalog();
            tableModelDefine.setCatalogID(catalog.getID());
            this.designDataModelService.insertTableModelDefine(tableModelDefine);
        } else {
            this.designDataModelService.updateTableModelDefine(tableModelDefine);
        }
        this.updateColumInfo(deployColumnCollection);
        if (!this.dataBaseLimitModeProvider.databaseLimitMode()) {
            this.nvwaDataModelUtil.deployTableUnCheck(tableModelDefine.getID());
        } else if (!this.observerDao.tableExists(tableCode)) {
            AllSqls.addAll(this.dataModelDeployService.getDeployTableSqls(tableModelDefine.getID()));
        }
    }

    private void updateColumInfo(DeployColumnCollection deployColumnCollection) {
        deployColumnCollection.finishSetting();
        if (deployColumnCollection.needCre()) {
            this.nvwaDataModelUtil.insertColumns(deployColumnCollection.getCreColumns());
        }
        if (deployColumnCollection.needMod()) {
            this.nvwaDataModelUtil.updateColumns(deployColumnCollection.getModColumns());
        }
        if (deployColumnCollection.needDel()) {
            this.nvwaDataModelUtil.deleteColumns(deployColumnCollection.getDelColumns());
        }
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

    public void onUpdate(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u66f4\u65b0\u7efc\u5408\u5ba1\u68381.0\u7ed3\u679c\u8868");
        try {
            this.doSaveAndDeploy(define);
        }
        catch (Exception e) {
            warningConsumer.accept("\u66f4\u65b0\u7efc\u5408\u5ba1\u68381.0\u7ed3\u679c\u8868\u5931\u8d25,\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
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

    private void doSaveAndDeploy(FormSchemeDefine scheme) throws Exception {
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(scheme.getTaskKey());
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(scheme.getKey());
        this.deployEntityCheckResult(taskDefine, designFormSchemeDefine);
    }

    private void doDeleteAndDeploy(FormSchemeDefine scheme) throws Exception {
        String schemeCode = scheme.getFormSchemeCode();
        DesignTableModelDefine tableDefine = this.designDataModelService.getTableModelDefineByCode("SYS_ENTITYCHECKUP_" + schemeCode);
        if (tableDefine == null) {
            return;
        }
        this.designDataModelService.deleteTableModelDefine(tableDefine.getID());
        this.dataModelDeployService.deployTableUnCheck(tableDefine.getID());
    }
}

