/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig
 *  com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine
 *  com.jiuqi.nr.bpm.setting.service.WorkflowSettingService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.upgrade.face.ITaskVersionUpgrade
 *  com.jiuqi.nr.definition.upgrade.face.UpgradeType
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationService
 *  com.jiuqi.nr.workflow2.settings.utils.WorkflowSettingsUtil
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.converter.upgrade.version;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.upgrade.face.ITaskVersionUpgrade;
import com.jiuqi.nr.definition.upgrade.face.UpgradeType;
import com.jiuqi.nr.workflow2.converter.upgrade.WorkflowDataUpgrade;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationService;
import com.jiuqi.nr.workflow2.settings.utils.WorkflowSettingsUtil;
import java.util.Comparator;
import java.util.Optional;
import java.util.UUID;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowSettingsUpgrade
implements ITaskVersionUpgrade {
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsManipulationService workflowSettingsManipulationService;
    @Autowired
    private DefaultProcessDesignService defaultProcessDesignService;
    @Autowired
    private WorkflowSettingService workflowSettingService_1_0;

    public String getModuleName() {
        return "\u6d41\u7a0b2.0-\u586b\u62a5\u8ba1\u5212\u914d\u7f6e\u5347\u7ea7";
    }

    public boolean apply(String taskKey, UpgradeType upgradeType) {
        return upgradeType.equals((Object)UpgradeType.BEFORE);
    }

    public void doUpgrade(String taskKey, UpgradeType upgradeType, AsyncTaskMonitor monitor) {
        if (upgradeType.equals((Object)UpgradeType.BEFORE)) {
            DesignTaskDefine designTaskDefine = this.designTimeViewController.getTask(taskKey);
            TaskFlowsDefine flowsSetting = designTaskDefine.getFlowsSetting();
            FlowsType flowsType = flowsSetting.getFlowsType();
            WorkFlowType workFlowType = flowsSetting.getWordFlowType();
            boolean allowFormBack = flowsSetting.isAllowFormBack();
            WorkflowObjectType workflowObjectType = null;
            switch (workFlowType) {
                case ENTITY: {
                    workflowObjectType = allowFormBack ? WorkflowObjectType.MD_WITH_SFR : WorkflowObjectType.MAIN_DIMENSION;
                    break;
                }
                case FORM: {
                    workflowObjectType = WorkflowObjectType.FORM;
                    break;
                }
                case GROUP: {
                    workflowObjectType = WorkflowObjectType.FORM_GROUP;
                }
            }
            if (flowsType.equals((Object)FlowsType.NOSTARTUP) || flowsType.equals((Object)FlowsType.EXTEND)) {
                WorkflowSettingsUtil.initTaskWorkflowSetting((String)designTaskDefine.getKey(), (boolean)false);
            } else {
                WorkflowSettingsDO workflowSettingsDO;
                WorkflowDataUpgrade workflowDataUpgrade = new WorkflowDataUpgrade();
                UUID workflowDefineUUID = UUID.randomUUID();
                WorkflowSettingsDTO settingsDTO = WorkflowSettingsUtil.generateDefaultWorkflowSettingsDTO((String)designTaskDefine.getKey(), (String)workflowDefineUUID.toString());
                settingsDTO.setTodoEnable(SendMessageTaskConfig.canSendMessage());
                settingsDTO.setWorkflowObjectType(workflowObjectType);
                settingsDTO.setOtherConfig(workflowDataUpgrade.generateOtherConfig((TaskDefine)designTaskDefine).toString());
                settingsDTO.setWorkflowEngine("jiuqi.nr.default-1.0");
                JSONObject workflow = WorkflowSettingsUtil.generateStandardWorkflow();
                WorkflowSettingDefine targetSettingDefine = this.queryWorkflowSettingDefineByLatestFormScheme(taskKey);
                if (targetSettingDefine != null) {
                    settingsDTO.setWorkflowEngine("jiuqi.nr.customprocessengine");
                    settingsDTO.setWorkflowDefine(targetSettingDefine.getWorkflowId());
                }
                if ((workflowSettingsDO = this.workflowSettingsService.queryWorkflowSettings(taskKey)) == null) {
                    this.workflowSettingsManipulationService.addWorkflowSettings(settingsDTO);
                    this.defaultProcessDesignService.addDefaultProcessConfig(workflowDefineUUID.toString(), workflow.toString());
                } else {
                    String previousWorkflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
                    this.defaultProcessDesignService.deleteDefaultProcessConfig(previousWorkflowDefine);
                    this.workflowSettingsManipulationService.updateWorkflowSettings(settingsDTO);
                }
            }
        }
    }

    public WorkflowSettingDefine queryWorkflowSettingDefineByLatestFormScheme(String taskKey) {
        Optional<SchemePeriodLinkDefine> target = this.runTimeViewController.listSchemePeriodLinkByTask(taskKey).stream().max(Comparator.comparing(SchemePeriodLinkDefine::getPeriodKey));
        if (target.isPresent()) {
            SchemePeriodLinkDefine schemePeriodLinkDefine = target.get();
            String formSchemeKey = schemePeriodLinkDefine.getSchemeKey();
            return this.workflowSettingService_1_0.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        }
        return null;
    }
}

