/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.setting.dao.impl.WorkflowSettingDao
 *  com.jiuqi.nr.bpm.setting.pojo.WorkflowSettingPojo
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  javax.annotation.Resource
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.workflow2.converter.listener;

import com.jiuqi.nr.bpm.setting.dao.impl.WorkflowSettingDao;
import com.jiuqi.nr.bpm.setting.pojo.WorkflowSettingPojo;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.applicationevent.WorkflowSettingsSaveEvent;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import java.util.List;
import javax.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WorkflowSettingsSaveListener
implements ApplicationListener<WorkflowSettingsSaveEvent> {
    @Resource
    private IDesignTimeViewController designTimeViewController;
    @Resource
    private WorkflowSettingDao workflowSettingDao;
    @Resource
    private WorkflowSettingsService workflowSettingsServiceVersion2_0;

    @Override
    public void onApplicationEvent(@NotNull WorkflowSettingsSaveEvent event) {
        try {
            List formSchemeDefines = this.designTimeViewController.listFormSchemeByTask(event.getTaskId());
            String workflowEngine = this.workflowSettingsServiceVersion2_0.queryTaskWorkflowEngine(event.getTaskId());
            if (workflowEngine != null && !workflowEngine.equals("jiuqi.nr.customprocessengine")) {
                for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
                    this.workflowSettingDao.delWorkFlowSettingBySchemeKey(formSchemeDefine.getKey());
                }
                return;
            }
            String workflowDefine = this.workflowSettingsServiceVersion2_0.queryTaskWorkflowDefine(event.getTaskId());
            for (DesignFormSchemeDefine formSchemeDefine : formSchemeDefines) {
                this.workflowSettingDao.delWorkFlowSettingBySchemeKey(formSchemeDefine.getKey());
                WorkflowSettingPojo workflowSettingPojo = new WorkflowSettingPojo();
                workflowSettingPojo.setDataId(formSchemeDefine.getKey());
                workflowSettingPojo.setWorkflowId(workflowDefine);
                this.workflowSettingDao.insertData(workflowSettingPojo);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

