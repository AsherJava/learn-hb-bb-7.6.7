/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 */
package com.jiuqi.nr.workflow2.settings.utils;

import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.settings.exception.RuntimeTaskDefineNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultEngineVersionJudgeImpl
implements DefaultEngineVersionJudge {
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    public boolean isTaskAndEngineVersion_1_0(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        if (taskDefine == null) {
            throw new RuntimeTaskDefineNotFoundException("\u5f53\u524d\u4efb\u52a1\u4e0d\u5b58\u5728\u8fd0\u884c\u671f\u4efb\u52a1\u5b9a\u4e49\uff01");
        }
        boolean isTaskVersion_1_0 = "1.0".equals(taskDefine.getVersion());
        if (isTaskVersion_1_0) {
            return true;
        }
        String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(taskKey);
        if (workflowEngine == null) {
            throw new RuntimeException("\u5f53\u524d\u4efb\u52a1\u4e0b\u586b\u62a5\u8ba1\u5212\u8bbe\u7f6e\u4e0d\u5b58\u5728\uff01");
        }
        return workflowEngine.equals("jiuqi.nr.default-1.0") || workflowEngine.equals("jiuqi.nr.customprocessengine");
    }

    public boolean isDefaultEngineVersion_2_0(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        if (taskDefine == null) {
            throw new RuntimeTaskDefineNotFoundException("\u5f53\u524d\u4efb\u52a1\u4e0d\u5b58\u5728\u8fd0\u884c\u671f\u4efb\u52a1\u5b9a\u4e49\uff01");
        }
        boolean isTaskVersion_1_0 = "1.0".equals(taskDefine.getVersion());
        if (isTaskVersion_1_0) {
            return false;
        }
        String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(taskKey);
        if (workflowEngine == null) {
            throw new RuntimeException("\u5f53\u524d\u4efb\u52a1\u4e0b\u586b\u62a5\u8ba1\u5212\u8bbe\u7f6e\u4e0d\u5b58\u5728\uff01");
        }
        return workflowEngine.equals("jiuqi.nr.default");
    }

    public boolean isTaskVersion_1_0(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        if (taskDefine == null) {
            throw new RuntimeTaskDefineNotFoundException("\u5f53\u524d\u4efb\u52a1\u4e0d\u5b58\u5728\u8fd0\u884c\u671f\u4efb\u52a1\u5b9a\u4e49\uff01");
        }
        return taskDefine.getVersion().equals("1.0");
    }

    public boolean isTaskVersion_2_0(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        if (taskDefine == null) {
            throw new RuntimeTaskDefineNotFoundException("\u5f53\u524d\u4efb\u52a1\u4e0d\u5b58\u5728\u8fd0\u884c\u671f\u4efb\u52a1\u5b9a\u4e49\uff01");
        }
        return taskDefine.getVersion().equals("2.0");
    }
}

