/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener
 *  com.jiuqi.nvwa.definition.common.TableModelKind
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.time.setting.util;

import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener;
import com.jiuqi.nr.time.setting.util.NvwaDataModelCreateUtil;
import com.jiuqi.nvwa.definition.common.TableModelKind;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.design.DesignCatalogModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@NpDefinitionObserver(type={MessageType.NRPUBLISHTASK})
public class TimeSettingObserver
implements Observer,
DeployPrepareEventListener,
IParamDeployFinishListener {
    private static final Logger logger = LoggerFactory.getLogger(TimeSettingObserver.class);
    @Autowired
    IDesignTimeViewController nrDesignController;
    @Autowired
    NvwaDataModelCreateUtil nvwaDataModelCreateUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;

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
            List schemes = this.nrDesignController.queryFormSchemeByTask(taskID);
            if (schemes == null) {
                return;
            }
            for (DesignFormSchemeDefine scheme : schemes) {
                this.deployTable(taskDefine, (FormSchemeDefine)scheme);
            }
        }
    }

    public String getName() {
        return "\u586b\u62a5\u65f6\u95f4\u8868" + this.getClass().getName();
    }

    public void deployTable(DesignTaskDefine taskDefine, FormSchemeDefine scheme) throws Exception {
        try {
            String tableCode = "NR_TIME_SETTING_" + scheme.getFormSchemeCode();
            DesignTableModelDefine tableDefine = this.nvwaDataModelCreateUtil.getDesTableByCode(tableCode);
            boolean doInsert = true;
            if (tableDefine == null) {
                tableDefine = this.nvwaDataModelCreateUtil.createTableDefine();
            } else {
                doInsert = false;
            }
            tableDefine.setCode(tableCode);
            tableDefine.setDesc("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u7684\u586b\u62a5\u65f6\u95f4\u8868");
            tableDefine.setTitle("\u4efb\u52a1\u3010" + taskDefine.getTitle() + "\u3011\u62a5\u8868\u65b9\u6848\u3010" + scheme.getTitle() + "\u3011\u5bf9\u5e94\u7684\u586b\u62a5\u65f6\u95f4\u8868");
            tableDefine.setName(tableCode);
            tableDefine.setType(TableModelType.DEFAULT);
            tableDefine.setKind(TableModelKind.DEFAULT);
            tableDefine.setOwner("NR");
            String tableKey = tableDefine.getID();
            StringBuffer tableEnityMasterKeys = new StringBuffer();
            ArrayList<DesignColumnModelDefine> createFieldList = new ArrayList<DesignColumnModelDefine>();
            ArrayList<DesignColumnModelDefine> modifyFieldList = new ArrayList<DesignColumnModelDefine>();
            Map<String, DesignColumnModelDefine> filedMap = this.nvwaDataModelCreateUtil.getFiledMap(tableKey);
            this.nvwaDataModelCreateUtil.initDwPeriodDimField(tableKey, tableEnityMasterKeys, taskDefine, scheme, createFieldList, modifyFieldList, filedMap);
            this.initOtherFiled(tableKey, createFieldList, modifyFieldList, tableEnityMasterKeys);
            tableDefine.setBizKeys(tableEnityMasterKeys.toString());
            tableDefine.setKeys(tableEnityMasterKeys.toString());
            if (doInsert) {
                DesignCatalogModelDefine sysTableGroupByParent = this.nvwaDataModelCreateUtil.createCatlogModelDefine();
                tableDefine.setCatalogID(sysTableGroupByParent.getID());
                this.nvwaDataModelCreateUtil.insertTableModelDefine(tableDefine);
            } else {
                this.nvwaDataModelCreateUtil.updateTableModelDefine(tableDefine);
            }
            if (createFieldList.size() > 0) {
                this.nvwaDataModelCreateUtil.insertFields(createFieldList.toArray(new DesignColumnModelDefine[1]));
            }
            if (modifyFieldList.size() > 0) {
                this.nvwaDataModelCreateUtil.updateFields(modifyFieldList.toArray(new DesignColumnModelDefine[1]));
            }
            this.nvwaDataModelCreateUtil.deployTable(tableDefine.getID());
        }
        catch (Exception e) {
            logger.error("\u521b\u5efa\u586b\u62a5\u65f6\u95f4\u8868\u5931\u8d25,\u4efb\u52a1:" + taskDefine.getTaskCode() + "|" + taskDefine.getTitle(), e);
        }
    }

    private void initOtherFiled(String tableKey, List<DesignColumnModelDefine> createFieldList, List<DesignColumnModelDefine> modifyFieldList, StringBuffer tableEnityMasterKeys) {
        try {
            this.nvwaDataModelCreateUtil.initField_String(tableKey, "formscheme_id", null, createFieldList, modifyFieldList, 50);
            this.nvwaDataModelCreateUtil.initField_Time_Stamp(tableKey, "submit_starttime", null, createFieldList, modifyFieldList);
            this.nvwaDataModelCreateUtil.initField_Time_Stamp(tableKey, "submit_deadlinetime", null, createFieldList, modifyFieldList);
            this.nvwaDataModelCreateUtil.initField_Time_Stamp(tableKey, "repay_daealine", null, createFieldList, modifyFieldList);
            this.nvwaDataModelCreateUtil.initField_String(tableKey, "operator", null, createFieldList, modifyFieldList, 50);
            this.nvwaDataModelCreateUtil.initField_String(tableKey, "operator_unitid", null, createFieldList, modifyFieldList, 1000);
            this.nvwaDataModelCreateUtil.initField_Time_Stamp(tableKey, "createtime", null, createFieldList, modifyFieldList);
            this.nvwaDataModelCreateUtil.initField_Time_Stamp(tableKey, "updatetime", null, createFieldList, modifyFieldList);
            this.nvwaDataModelCreateUtil.initField_Integer(tableKey, "unit_level", null, createFieldList, modifyFieldList);
        }
        catch (Exception e) {
            e.printStackTrace();
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
            DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(runTimeKey);
            if (null == designFormSchemeDefine) continue;
            this.nvwaDataModelCreateUtil.deployDeleteTableByCode("NR_TIME_SETTING_" + designFormSchemeDefine.getFormSchemeCode());
        }
    }

    public void onAdd(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u53d1\u5e03\u586b\u62a5\u65f6\u95f4\u8868");
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(define.getTaskKey());
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        try {
            this.deployTable(taskDefine, (FormSchemeDefine)designFormSchemeDefine);
            progressConsumer.accept("\u53d1\u5e03\u586b\u62a5\u65f6\u95f4\u8868\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            warningConsumer.accept("\u53d1\u5e03\u586b\u62a5\u65f6\u95f4\u8868\u5931\u8d25");
        }
    }

    public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        DesignFormSchemeDefine designFormScheme = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        FormSchemeDefine runTimeformScheme = this.runTimeViewController.getFormScheme(define.getKey());
        if (null == designFormScheme && null != runTimeformScheme) {
            progressConsumer.accept("\u6b63\u5728\u5220\u9664\u586b\u62a5\u65f6\u95f4\u8868");
            this.nvwaDataModelCreateUtil.deployDeleteTableByCode("NR_TIME_SETTING_" + designFormScheme.getFormSchemeCode());
            progressConsumer.accept("\u5220\u9664\u586b\u62a5\u65f6\u95f4\u8868\u6210\u529f");
        }
    }

    public void onUpdate(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u66f4\u65b0\u586b\u62a5\u65f6\u95f4\u8868");
        DesignTaskDefine taskDefine = this.nrDesignController.queryTaskDefine(define.getTaskKey());
        DesignFormSchemeDefine designFormSchemeDefine = this.nrDesignController.queryFormSchemeDefine(define.getKey());
        try {
            this.deployTable(taskDefine, (FormSchemeDefine)designFormSchemeDefine);
            progressConsumer.accept("\u66f4\u65b0\u586b\u62a5\u65f6\u95f4\u8868\u6210\u529f");
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            warningConsumer.accept("\u66f4\u65b0\u586b\u62a5\u65f6\u95f4\u8868\u5931\u8d25");
        }
    }
}

