/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.todo.service.TodoManipulationService
 *  org.activiti.engine.delegate.DelegateExecution
 */
package com.jiuqi.nr.bpm.impl.countersign.impl;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.de.dataflow.common.TodoSignExecuteEvent;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.countersign.CounterSignParam;
import com.jiuqi.nr.bpm.impl.countersign.MulitiInstanceCompleteTaskListener;
import com.jiuqi.nr.bpm.impl.countersign.impl.DefaultMulitiInstanceCompleteTask;
import com.jiuqi.nr.bpm.impl.countersign.impl.DefaultMulitiInstanceCompleteTaskDianxin;
import com.jiuqi.nr.bpm.impl.upload.modeling.ProcessBuilderUtils;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationService;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component(value="ActivitiMulitiInstanceListenerBean")
public class DefaultMulitiInstanceCompleteTaskListener
implements MulitiInstanceCompleteTaskListener {
    private static final String COMPLETE_INSTANCE = "nrOfCompletedInstances";
    private static final String INSTANCES = "nrOfInstances";
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TodoManipulationService todoManipulationService;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DeEntityHelper entityHelper;
    @Autowired
    private PeriodEngineService periodEngineService;

    @Override
    public boolean completeTask(DelegateExecution execution) {
        String taskId = execution.getId();
        Object param = execution.getVariable(this.nrParameterUtils.getCountersignObjKey());
        if (param == null) {
            return false;
        }
        CounterSignParam counterSignParam = (CounterSignParam)param;
        boolean signStartMode = counterSignParam.isSignStartMode();
        if (signStartMode) {
            DefaultMulitiInstanceCompleteTaskDianxin dianxin = new DefaultMulitiInstanceCompleteTaskDianxin();
            return dianxin.completeTask(execution, counterSignParam);
        }
        DefaultMulitiInstanceCompleteTask defaultMulitiInstanceCompleteTask = new DefaultMulitiInstanceCompleteTask();
        return defaultMulitiInstanceCompleteTask.completeTask(execution, counterSignParam);
    }

    private void clearMessage(CounterSignParam counterSignParam, String taskId) {
        String taskCode = counterSignParam.getTaskCode();
        BusinessKey businessKey = counterSignParam.getBusinessKey();
        String formOrGroupKey = businessKey.getFormKey();
        String formSchemeKey = businessKey.getFormSchemeKey();
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(formSchemeKey);
        DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(businessKey);
        String period = dimensionValueSet.getValue("DATATIME").toString();
        String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        String unitKey = dimensionValueSet.getValue(mainDimName).toString();
        Object adjustObj = dimensionValueSet.getValue("ADJUST");
        String adjust = adjustObj == null ? "" : adjustObj.toString();
        String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), dimensionValueSet);
        WorkFlowType workFlowType = this.workflow.queryStartType(businessKey.getFormSchemeKey());
        String msgId = this.workflow.getMessageId(formSchemeKey, period, unitKey, adjust, formOrGroupKey, formOrGroupKey, workFlowType, taskCode, corporateValue);
        this.todoManipulationService.deleteTodoMessageByCurrentUser(ProcessBuilderUtils.produceUUIDKey(msgId));
    }

    private void publishEvent(BusinessKey businessKey, DimensionValueSet dimensionValueSet, String taskId, String taskCode, String unitKey, String period, String corporateValue, String formOrGroupKey, String messageId, String adjust, String actionCode) {
        FormSchemeDefine formScheme;
        TaskDefine queryTaskDefine;
        List<IEntityRow> entityData;
        String formSchemeKey = businessKey.getFormSchemeKey();
        TodoSignExecuteEvent todoSignCompleteEvent = new TodoSignExecuteEvent();
        todoSignCompleteEvent.setMessageId(messageId);
        HashMap<String, String> variables = new HashMap<String, String>();
        variables.put("formSchemeId", formSchemeKey);
        variables.put("unitId", unitKey);
        variables.put("11111111-1111-1111-1111-111111111111", formOrGroupKey);
        variables.put("taskId", taskId);
        variables.put("taskNodeId", taskCode);
        variables.put("actionId", actionCode);
        variables.put("adjust", adjust);
        variables.put("corporateValue", corporateValue);
        variables.put("period", period);
        RunTimeService runTimeService = this.nrParameterUtils.getProcessEngine(formSchemeKey).get().getRunTimeService();
        Optional<Task> taskById = runTimeService.getTaskById(taskId, businessKey);
        if (taskById.isPresent()) {
            Task task = taskById.get();
            String name = task.getName();
            variables.put("nodeName", name);
        }
        if ((entityData = this.entityHelper.getEntityRow(formSchemeKey, dimensionValueSet)).size() > 0) {
            for (IEntityRow iEntityRow : entityData) {
                String entityKeyData = iEntityRow.getEntityKeyData();
                String title = iEntityRow.getTitle();
                variables.put("unitName", title);
                variables.put("unitId", entityKeyData);
            }
        }
        if ((queryTaskDefine = this.nrParameterUtils.getTaskDefine((formScheme = this.nrParameterUtils.getFormScheme(formSchemeKey)).getTaskKey())) != null) {
            String taskTitle = queryTaskDefine.getTitle();
            variables.put("taskTitle", taskTitle);
        }
        String periodTitle = this.date(formScheme, period);
        variables.put("periodTitle", periodTitle);
        boolean mulitiInstanceTask = this.nrParameterUtils.isMulitiInstanceTask(taskCode, formSchemeKey);
        variables.put("signNode", String.valueOf(mulitiInstanceTask));
        todoSignCompleteEvent.setVariables(variables);
        this.applicationContext.publishEvent(todoSignCompleteEvent);
    }

    public String date(FormSchemeDefine formScheme, String periodStr) {
        String period = null;
        try {
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime());
            PeriodWrapper periodWrapper = new PeriodWrapper(periodStr);
            return periodProvider.getPeriodTitle(periodWrapper);
        }
        catch (Exception exception) {
            return period;
        }
    }
}

