/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.internal.service.DataEntryParamServiceImpl
 *  com.jiuqi.nr.dataentry.paramInfo.TaskData
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy
 */
package com.jiuqi.nr.workflow2.converter.dataentry;

import com.jiuqi.nr.dataentry.internal.service.DataEntryParamServiceImpl;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class DataEntryParamServiceConverter
extends DataEntryParamServiceImpl {
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private Workflow2EngineCompatibleCollector workflow2EngineCompatibleCollector;

    public TaskData getRuntimeTaskByKey(String taskKey) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey)) {
            return super.getRuntimeTaskByKey(taskKey);
        }
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        if (taskDefine != null) {
            String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(taskKey);
            Workflow2EngineCompatibleExtend extensionByEngine = this.workflow2EngineCompatibleCollector.getExtensionByEngine(workflowEngine);
            String uploadDescExtend = extensionByEngine.getUploadDesc(taskKey);
            FillInDescStrategy uploadDesc = uploadDescExtend == null ? null : FillInDescStrategy.valueOf((String)uploadDescExtend);
            String returnDescExtend = extensionByEngine.getReturnDesc(taskKey);
            FillInDescStrategy returnDesc = returnDescExtend == null ? null : FillInDescStrategy.valueOf((String)returnDescExtend);
            TaskData task = new TaskData();
            task.init(taskDefine);
            task.setSubmitExplain(uploadDesc != null);
            task.setForceSubmitExplain(uploadDesc != null && uploadDesc.equals((Object)FillInDescStrategy.REQUIRED));
            task.setBackDescriptionNeedWrite(returnDesc != null);
            return task;
        }
        return null;
    }
}

