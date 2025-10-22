/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserver
 *  com.jiuqi.np.definition.observer.Observer
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployParams
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.controller.NRDesignTimeController
 *  com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener
 */
package com.jiuqi.nr.data.logic.internal.deploy;

import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserver;
import com.jiuqi.np.definition.observer.Observer;
import com.jiuqi.nr.data.logic.internal.deploy.TableDeployService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.controller.NRDesignTimeController;
import com.jiuqi.nr.definition.internal.runtime.service.DeployPrepareEventListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@NpDefinitionObserver(type={MessageType.NRPUBLISHTASK})
public class CheckObserver
implements Observer,
DeployPrepareEventListener,
IParamDeployFinishListener {
    @Autowired
    private NRDesignTimeController nrDesignTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    TableDeployService tableDeployService;
    private static final Logger logger = LoggerFactory.getLogger(CheckObserver.class);

    public boolean isAsyn() {
        return false;
    }

    public String getName() {
        return "TABLE{ALLCKR;CKR;CKD}-" + this.getClass().getName();
    }

    public void excute(Object[] objs) throws Exception {
        if (objs == null) {
            return;
        }
        for (Object obj : objs) {
            String taskID;
            DesignTaskDefine taskDefine;
            if (!(obj instanceof String) || "2.0".equals((taskDefine = this.nrDesignTimeController.queryTaskDefine(taskID = (String)obj)).getVersion())) continue;
            List schemes = this.nrDesignTimeController.queryFormSchemeByTask(taskID);
            if (schemes == null) {
                return;
            }
            for (DesignFormSchemeDefine scheme : schemes) {
                this.tableDeployService.deployTables((TaskDefine)taskDefine, (FormSchemeDefine)scheme);
                try {
                    this.tableDeployService.publishSuccessEvent((TaskDefine)taskDefine, (FormSchemeDefine)scheme);
                }
                catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
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
            String schemeCode = formScheme.getFormSchemeCode();
            this.tableDeployService.deleteTables(schemeCode);
        }
    }

    public void onAdd(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        this.onUpdate(define, warningConsumer, progressConsumer);
    }

    public void onDelete(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u5220\u9664\u5ba1\u6838\u76f8\u5173\u7cfb\u7edf\u8868");
        if (define == null) {
            return;
        }
        this.tableDeployService.deleteTables(define.getFormSchemeCode());
    }

    public void onUpdate(FormSchemeDefine define, Consumer<String> warningConsumer, Consumer<String> progressConsumer) {
        progressConsumer.accept("\u6b63\u5728\u53d1\u5e03\u5ba1\u6838\u76f8\u5173\u7cfb\u7edf\u8868");
        if (define == null) {
            return;
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(define.getTaskKey());
        if (taskDefine == null) {
            return;
        }
        try {
            this.tableDeployService.deployTables(taskDefine, define);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            warningConsumer.accept("\u5ba1\u6838\u76f8\u5173\u7cfb\u7edf\u8868\u53d1\u5e03\u5f02\u5e38\uff1a" + e.getMessage());
        }
        progressConsumer.accept("\u6b63\u5728\u6267\u884c\u5ba1\u6838\u8868\u53d1\u5e03\u540e\u4e8b\u4ef6");
        try {
            this.tableDeployService.publishSuccessEvent(taskDefine, define);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            warningConsumer.accept("\u5ba1\u6838\u8868\u53d1\u5e03\u540e\u4e8b\u4ef6\u6267\u884c\u5f02\u5e38\uff1a" + e.getMessage());
        }
    }
}

