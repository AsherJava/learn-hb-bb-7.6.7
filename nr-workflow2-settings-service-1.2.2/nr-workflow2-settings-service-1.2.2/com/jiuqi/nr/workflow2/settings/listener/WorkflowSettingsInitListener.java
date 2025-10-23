/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.event.ParamChangeEvent$ChangeType
 *  com.jiuqi.nr.definition.event.TaskChangeEvent
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.settings.listener;

import com.jiuqi.nr.definition.event.ParamChangeEvent;
import com.jiuqi.nr.definition.event.TaskChangeEvent;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationService;
import com.jiuqi.nr.workflow2.settings.utils.WorkflowSettingsUtil;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.ApplicationListener;

public class WorkflowSettingsInitListener
implements ApplicationListener<TaskChangeEvent> {
    @Resource
    private WorkflowSettingsService workflowSettingsService;
    @Resource
    private WorkflowSettingsManipulationService workflowSettingsManipulationService;
    @Resource
    private DefaultProcessDesignService defaultProcessDesignService;

    @Override
    public void onApplicationEvent(TaskChangeEvent event) {
        block3: {
            List taskIds;
            ParamChangeEvent.ChangeType type;
            block2: {
                type = event.getType();
                taskIds = event.getKeys();
                if (!type.equals((Object)ParamChangeEvent.ChangeType.ADD) || taskIds == null || taskIds.isEmpty()) break block2;
                for (String taskId : taskIds) {
                    WorkflowSettingsUtil.initTaskDefault_1_0_WorkflowSetting(taskId, true);
                }
                break block3;
            }
            if (!type.equals((Object)ParamChangeEvent.ChangeType.DELETE) || taskIds == null || taskIds.isEmpty()) break block3;
            for (String taskId : taskIds) {
                String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskId);
                this.workflowSettingsManipulationService.deleteWorkflowSettings(taskId);
                if (workflowDefine == null || workflowDefine.isEmpty()) continue;
                this.defaultProcessDesignService.deleteDefaultProcessConfig(workflowDefine);
            }
        }
    }
}

