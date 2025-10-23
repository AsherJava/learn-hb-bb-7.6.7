/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam
 *  com.jiuqi.nr.bpm.de.dataflow.complete.ExecuteTask
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam
 *  com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil
 *  com.jiuqi.nr.bpm.exception.UserActionException
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneDim
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneExecutePara
 */
package com.jiuqi.nr.workflow2.converter.workflow.execute;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.complete.ExecuteTask;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import com.jiuqi.nr.workflow2.service.para.ProcessOneExecutePara;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ExecuteTaskConverter
extends ExecuteTask {
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public boolean IsMatch(String actionCode, String taskKey) {
        return !this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey);
    }

    public CompleteMsg executeTask(ExecuteParam executeParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return new CompleteMsg();
        }
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        String entityId = entityCaliber != null && !entityCaliber.isEmpty() ? entityCaliber : formSchemeDefine.getDw();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(formSchemeDefine.getTaskKey());
        DimensionValueSet dimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(executeParam.getDimSet(), formSchemeDefine);
        String unitKey = dimensionValueSet.getValue(this.dimensionUtil.getDwMainDimName(executeParam.getFormSchemeKey())).toString();
        String period = dimensionValueSet.getValue("DATATIME").toString();
        HashSet<ProcessOneDim> oneDims = new HashSet<ProcessOneDim>();
        ProcessOneDim unitOneDim = new ProcessOneDim();
        unitOneDim.setDimensionName(dimensionName);
        unitOneDim.setDimensionKey(entityId);
        unitOneDim.setDimensionValue(unitKey);
        oneDims.add(unitOneDim);
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            ProcessOneDim formOneDim = new ProcessOneDim();
            formOneDim.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionValue(executeParam.getFormKey());
            oneDims.add(formOneDim);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            ProcessOneDim formGroupOneDim = new ProcessOneDim();
            formGroupOneDim.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionValue(executeParam.getGroupKey());
            oneDims.add(formGroupOneDim);
        }
        ProcessOneExecutePara oneExecutePara = new ProcessOneExecutePara();
        oneExecutePara.setTaskKey(formSchemeDefine.getTaskKey());
        oneExecutePara.setPeriod(period);
        oneExecutePara.setTaskId(executeParam.getTaskId());
        oneExecutePara.setActionCode(executeParam.getActionId());
        oneExecutePara.setReportDimensions(oneDims);
        return null;
    }

    public CompleteMsg batchExecuteTask(BatchExecuteParam executeParam) throws UserActionException {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey());
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.batchExecuteTask(executeParam);
        }
        return null;
    }
}

