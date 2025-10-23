/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.dao.DesignIndexModelDao
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.workflow2.form.reject.listener;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.form.reject.model.FROperateDesTableModelDefine;
import com.jiuqi.nr.workflow2.form.reject.model.FROperateTable;
import com.jiuqi.nr.workflow2.form.reject.model.FRStatusDesTableModelDefine;
import com.jiuqi.nr.workflow2.form.reject.model.FRStatusTable;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.dao.DesignIndexModelDao;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WorkflowSettingChangeListener
implements IParamDeployFinishListener {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private DesignDataModelService designDataModelService;
    @Autowired
    private DataModelDeployService dataModelDeployService;
    @Autowired
    private DesignIndexModelDao designIndexModelDao;

    @EventListener
    public void workflowSettingsSaveEventHandler(WorkflowSettingsSaveEvent event) {
        String taskKey = event.getTaskId();
        WorkflowObjectType flowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(taskKey);
        if (WorkflowObjectType.MD_WITH_SFR == flowObjectType) {
            List formSchemeDefines = this.runTimeViewController.listFormSchemeByTask(taskKey);
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                this.checkFormRejectStatusTableModelDefine(formSchemeDefine);
                this.checkFormRejectOperateTableModelDefine(formSchemeDefine);
            }
        }
    }

    private void checkFormRejectStatusTableModelDefine(FormSchemeDefine formSchemeDefine) {
        FRStatusTable statusTable = new FRStatusTable(formSchemeDefine);
        DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(statusTable.getTableName());
        if (tableModelDefine != null) {
            this.dropTableModelDefine(tableModelDefine, true);
        }
        FRStatusDesTableModelDefine oriTableModelDefine = new FRStatusDesTableModelDefine(statusTable, this.designDataModelService);
        this.createFormRejectStatusTableModelDefine(oriTableModelDefine);
    }

    private void checkFormRejectOperateTableModelDefine(FormSchemeDefine formSchemeDefine) {
        FROperateTable operateTable = new FROperateTable(formSchemeDefine);
        DesignTableModelDefine tableModelDefine = this.designDataModelService.getTableModelDefineByCode(operateTable.getTableName());
        if (tableModelDefine != null) {
            this.dropTableModelDefine(tableModelDefine, true);
        }
        FROperateDesTableModelDefine oriTableModelDefine = new FROperateDesTableModelDefine(operateTable, this.designDataModelService);
        this.createFormRejectOperateTableModelDefine(oriTableModelDefine);
    }

    private void createFormRejectStatusTableModelDefine(FRStatusDesTableModelDefine statusDesTableModelDefine) {
        try {
            this.designIndexModelDao.insert((Object)statusDesTableModelDefine.getIndexModelDefine());
            this.designDataModelService.insertTableModelDefine(statusDesTableModelDefine.getTableModelDefine());
            this.designDataModelService.insertColumnModelDefines(statusDesTableModelDefine.getColumnModelDefines().toArray(new DesignColumnModelDefine[0]));
            this.dataModelDeployService.deployTable(statusDesTableModelDefine.getTableModelDefine().getID());
        }
        catch (Exception e) {
            LoggerFactory.getLogger(WorkflowSettingChangeListener.class).error("createFormRejectTableModelDefine", e);
        }
    }

    private void updateFormRejectStatusTableModelDefine(FRStatusDesTableModelDefine statusDesTableModelDefine, DesignTableModelDefine tableModelDefine) {
    }

    private void createFormRejectOperateTableModelDefine(FROperateDesTableModelDefine operateDesTableModelDefine) {
        try {
            this.designDataModelService.insertTableModelDefine(operateDesTableModelDefine.getTableModelDefine());
            this.designDataModelService.insertColumnModelDefines(operateDesTableModelDefine.getColumnModelDefines().toArray(new DesignColumnModelDefine[0]));
            this.dataModelDeployService.deployTable(operateDesTableModelDefine.getTableModelDefine().getID());
        }
        catch (Exception e) {
            LoggerFactory.getLogger(WorkflowSettingChangeListener.class).error("createFormRejectTableModelDefine", e);
        }
    }

    private void updateFormRejectOperateTableModelDefine(FROperateDesTableModelDefine operateDesTableModelDefine, DesignTableModelDefine tableModelDefine) {
    }

    private void dropTableModelDefine(DesignTableModelDefine tableModelDefine, boolean forceDelete) {
        try {
            this.designDataModelService.deleteColumnModelDefineByTable(tableModelDefine.getID());
            this.designDataModelService.deleteIndexsByTable(tableModelDefine.getID());
            this.designDataModelService.deleteTableModelDefine(tableModelDefine.getID());
            if (forceDelete) {
                this.dataModelDeployService.deployTableUnCheck(tableModelDefine.getID());
            } else {
                this.dataModelDeployService.deployTable(tableModelDefine.getID());
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(WorkflowSettingChangeListener.class).error("clearFormRejectTableModelDefine", e);
        }
    }
}

