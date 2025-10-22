/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.period.PeriodConsts
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.deploy.DeployTaskBeforeEvent
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.workflow2.todo.service.TodoManipulationService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.period.PeriodConsts;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyImpl;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.Workflow;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.service.HistoryService;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.utils.BpmQueryEntityData;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.DeployTaskBeforeEvent;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowDeleteEventImpl
implements DeployTaskBeforeEvent {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowDeleteEventImpl.class);
    @Autowired
    private IDesignTimeViewController iDesignTimeViewController;
    @Autowired
    private IRunTimeViewController iRunTimeViewController;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired
    private Workflow workflow;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private TodoManipulationService todoManipulationService;

    public void execute(String taskKey) {
        StringBuffer sb = new StringBuffer();
        DesignTaskDefine designTaskDefine = this.iDesignTimeViewController.queryTaskDefine(taskKey);
        if (designTaskDefine == null) {
            sb.append("\u8bbe\u8ba1\u671f\u4efb\u52a1\u5b9a\u4e49\u4e0d\u5b58\u5728, taskKey = ").append(taskKey).append(";");
            logger.info("\u8bbe\u8ba1\u671f\u4efb\u52a1\u5b9a\u4e49\u4e0d\u5b58\u5728, taskKey = " + taskKey);
            LogHelper.info((String)"\u6570\u636e\u5efa\u6a21", (String)"\u4efb\u52a1\u53d1\u5e03\u65f6-\u5220\u9664\u6d41\u7a0b\u6570\u636e", (String)sb.toString());
            return;
        }
        TaskDefine taskDefine = this.iRunTimeViewController.queryTaskDefine(taskKey);
        if (taskDefine == null) {
            sb.append("\u8fd0\u884c\u671f\u4efb\u52a1\u5b9a\u4e49\u4e0d\u5b58\u5728, taskKey = ").append(taskKey).append(";");
            logger.info("\u8fd0\u884c\u671f\u4efb\u52a1\u5b9a\u4e49\u4e0d\u5b58\u5728, taskKey = " + taskKey);
            LogHelper.info((String)"\u6570\u636e\u5efa\u6a21", (String)"\u4efb\u52a1\u53d1\u5e03\u65f6-\u5220\u9664\u6d41\u7a0b\u6570\u636e", (String)sb.toString());
            return;
        }
        boolean equal = this.isEqual(designTaskDefine, taskDefine, sb);
        sb.append("\u8bbe\u8ba1\u671f\u6d41\u7a0b\u5bf9\u8c61\u7c7b\u578b\u548c\u8fd0\u884c\u671f\u6d41\u7a0b\u5bf9\u8c61\u7c7b\u578b\u7684\u7ed3\u679c,").append(equal).append(";\n");
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        WorkFlowType wordFlowType = flowsSetting.getWordFlowType();
        if (!equal) {
            this.delete(taskDefine, sb, wordFlowType);
            this.deleteTodo(taskDefine, sb);
        }
        LogHelper.info((String)"\u6570\u636e\u5efa\u6a21", (String)"\u4efb\u52a1\u53d1\u5e03\u65f6-\u5220\u9664\u6d41\u7a0b\u6570\u636e", (String)sb.toString());
    }

    private boolean isEqual(DesignTaskDefine designTaskDefine, TaskDefine taskDefine, StringBuffer sb) {
        TaskFlowsDefine designFlowsSetting = designTaskDefine.getFlowsSetting();
        if (designFlowsSetting == null) {
            sb.append("\u8bbe\u8ba1\u671f\u6d41\u7a0b\u5b9a\u4e49\u4e0d\u5b58\u5728, \u4efb\u52a1\u6807\u8bc6:").append(designTaskDefine.getTaskCode()).append(";").append("\u4efb\u52a1\u540d\u79f0:").append(designTaskDefine.getTitle()).append("\n");
            logger.info("\u8bbe\u8ba1\u671f\u6d41\u7a0b\u5b9a\u4e49\u4e0d\u5b58\u5728, \u4efb\u52a1\u6807\u8bc6 = " + designTaskDefine.getTaskCode() + ";\u4efb\u52a1\u540d\u79f0" + designTaskDefine.getTitle());
            return false;
        }
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        if (flowsSetting == null) {
            sb.append("\u8fd0\u884c\u671f\u4efb\u52a1\u5b9a\u4e49\u4e0d\u5b58\u5728, \u4efb\u52a1\u6807\u8bc6:").append(taskDefine.getTaskCode()).append(";").append("\u4efb\u52a1\u540d\u79f0:").append(taskDefine.getTitle()).append("\n");
            logger.info("\u8fd0\u884c\u671f\u4efb\u52a1\u5b9a\u4e49\u4e0d\u5b58\u5728, \u4efb\u52a1\u6807\u8bc6 = " + taskDefine.getTaskCode() + ";\u4efb\u52a1\u540d\u79f0" + taskDefine.getTitle());
            return false;
        }
        WorkFlowType designWorkFlowType = designFlowsSetting.getWordFlowType();
        WorkFlowType wordFlowType = flowsSetting.getWordFlowType();
        return designWorkFlowType.equals((Object)wordFlowType);
    }

    public void delete(TaskDefine taskDefine, StringBuffer sb, WorkFlowType wordFlowType) {
        PeriodWrapper currPeriod = this.getCurrPeriod(taskDefine);
        try {
            List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
            if (formSchemeDefines != null && formSchemeDefines.size() > 0) {
                sb.append("\u5df2\u5220\u9664\u7684\u62a5\u8868\u65b9\u6848 \u3010");
                for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                    boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeDefine.getKey());
                    BusinessKeyImpl businessKey = new BusinessKeyImpl();
                    businessKey.setFormSchemeKey(formSchemeDefine.getKey());
                    try {
                        this.batchQueryUploadStateService.deleteStateData(formSchemeDefine);
                        this.batchQueryUploadStateService.deleteHistoryStateData(formSchemeDefine);
                        this.deleteInstances(formSchemeDefine, sb, defaultWorkflow, wordFlowType, currPeriod.toString());
                        sb.append(" \u62a5\u8868\u65b9\u6848\u6807\u8bc6\uff1a").append(formSchemeDefine.getFormSchemeCode()).append(", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a").append(formSchemeDefine.getTitle()).append("; ");
                    }
                    catch (Exception e) {
                        logger.error("\u72b6\u6001\u8868\u6570\u636e\u5220\u9664\u5931\u8d25", e);
                    }
                }
                sb.append("\u3011").append("\n");
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848\u96c6\u5408\u62a5\u9519", e);
        }
    }

    private void deleteInstances(FormSchemeDefine formSchemeDefine, StringBuffer sb, boolean defaultWorkflow, WorkFlowType startType, String period) {
        if (!defaultWorkflow) {
            Optional<ProcessEngine> processEngine = this.processEngineProvider.getProcessEngine(ProcessType.COMPLETED_ACTIVIT);
            RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
            List<BusinessKey> businessKeys = this.getBusinessKeys(formSchemeDefine, startType, period);
            sb.append("\u6d41\u7a0b\u5b9e\u4f8b\u603b\u6570\uff1a" + businessKeys.size()).append(";");
            for (BusinessKey businessKey : businessKeys) {
                Optional<ProcessInstance> instance = runTimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
                if (Optional.empty().equals(instance)) continue;
                runTimeService.deleteProcessInstanceById(instance.get().getId());
                HistoryService historyService = processEngine.map(engine -> engine.getHistoryService()).orElse(null);
                historyService.deleteHistoricProcessInstance(BusinessKeyFormatter.formatToString(businessKey), instance.get().getId());
            }
            sb.append("\u6d41\u7a0b\u5b9e\u4f8b\u5220\u9664\u5b8c\u6bd5").append("\n");
        }
    }

    private List<BusinessKey> getBusinessKeys(FormSchemeDefine formSchemeDefine, WorkFlowType startType, String period) {
        ArrayList<BusinessKey> businessKeys = new ArrayList<BusinessKey>();
        List<String> unitKeys = this.queryUnitKeys(formSchemeDefine);
        List<String> queryFormOrGroupKeys = this.queryFormOrGroupKeys(formSchemeDefine, startType);
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(formSchemeDefine.getKey());
        if (queryFormOrGroupKeys != null && queryFormOrGroupKeys.size() > 0) {
            for (String unitKey : unitKeys) {
                for (String key : queryFormOrGroupKeys) {
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    dimensionValueSet.setValue(dwMainDimName, (Object)unitKey);
                    dimensionValueSet.setValue("DATATIME", (Object)period);
                    BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeDefine.getKey(), dimensionValueSet, key, key);
                    businessKeys.add(businessKey);
                }
            }
        } else {
            for (String unitKey : unitKeys) {
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue(dwMainDimName, (Object)unitKey);
                dimensionValueSet.setValue("DATATIME", (Object)period);
                BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeDefine.getKey(), dimensionValueSet, null, null);
                businessKeys.add(businessKey);
            }
        }
        return businessKeys;
    }

    private List<String> queryUnitKeys(FormSchemeDefine formSchemeDefine) {
        ArrayList<String> unitIds = new ArrayList<String>();
        BpmQueryEntityData bpmQueryEntityData = new BpmQueryEntityData();
        List<IEntityRow> entityData = bpmQueryEntityData.getEntityData(formSchemeDefine.getKey(), formSchemeDefine.getDateTime());
        if (entityData != null && entityData.size() > 0) {
            for (IEntityRow iEntityRow : entityData) {
                unitIds.add(iEntityRow.getEntityKeyData());
            }
        }
        return unitIds;
    }

    private List<String> queryFormOrGroupKeys(FormSchemeDefine formSchemeDefine, WorkFlowType startType) {
        List allFormGroups;
        ArrayList<String> formOrGroupKeys = new ArrayList<String>();
        if (WorkFlowType.FORM.equals((Object)startType)) {
            List allFormKeys = this.iRunTimeViewController.queryAllFormKeysByFormScheme(formSchemeDefine.getKey());
            formOrGroupKeys.addAll(allFormKeys);
        } else if (WorkFlowType.GROUP.equals((Object)startType) && (allFormGroups = this.iRunTimeViewController.getAllFormGroupsInFormScheme(formSchemeDefine.getKey())) != null && allFormGroups.size() > 0) {
            for (FormGroupDefine formGroupDefine : allFormGroups) {
                formOrGroupKeys.add(formGroupDefine.getKey());
            }
        }
        return formOrGroupKeys;
    }

    public PeriodWrapper getCurrPeriod(TaskDefine taskDefine) {
        PeriodType periodType = taskDefine.getPeriodType();
        int periodOffset = taskDefine.getTaskPeriodOffset();
        String fromPeriod = taskDefine.getFromPeriod();
        String toPeriod = taskDefine.getToPeriod();
        if (StringUtils.isEmpty((String)fromPeriod) || StringUtils.isEmpty((String)toPeriod)) {
            char typeToCode = (char)PeriodConsts.typeToCode((int)periodType.type());
            fromPeriod = "1970" + typeToCode + "0001";
            toPeriod = "9999" + typeToCode + "0001";
        }
        return WorkflowDeleteEventImpl.getCurrPeriod(periodType.type(), periodOffset, fromPeriod, toPeriod);
    }

    private static PeriodWrapper getCurrPeriod(int periodType, int periodOffset, String fromPeriod, String toPeriod) {
        GregorianCalendar calendar = new GregorianCalendar();
        PeriodWrapper currentPeriod = PeriodUtil.currentPeriod((GregorianCalendar)calendar, (int)periodType, (int)periodOffset);
        return currentPeriod;
    }

    private void deleteTodo(TaskDefine taskDefine, StringBuffer sb) {
        try {
            List formSchemeDefines = this.iRunTimeViewController.queryFormSchemeByTask(taskDefine.getKey());
            if (formSchemeDefines != null && formSchemeDefines.size() > 0) {
                sb.append("\u5f85\u529e\u5df2\u5220\u9664\u7684\u62a5\u8868\u65b9\u6848\u96c6\u5408\u3010");
                for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                    try {
                        this.todoManipulationService.deleteTodoMessageByFormSchemeKey(formSchemeDefine.getKey());
                        sb.append(" \u62a5\u8868\u65b9\u6848\u6807\u8bc6\uff1a").append(formSchemeDefine.getFormSchemeCode()).append(", \u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a").append(formSchemeDefine.getTitle()).append("; ");
                    }
                    catch (Exception e) {
                        logger.error("\u5f85\u529e\u5220\u9664\u5931\u8d25\uff0c\u62a5\u8868\u65b9\u6848\u6807\u8bc6\uff1a" + formSchemeDefine.getFormSchemeCode() + ",\u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a" + formSchemeDefine.getTitle() + ",\u62a5\u8868\u65b9\u6848key:" + formSchemeDefine.getKey(), e);
                    }
                }
                sb.append("\u3011").append("\n");
            }
        }
        catch (Exception e) {
            logger.error("\u5f85\u529e\u5220\u9664\uff0c\u67e5\u8be2\u4efb\u52a1\u4e0b\u7684\u62a5\u8868\u65b9\u6848\u96c6\u5408\u62a5\u9519", e);
        }
    }
}

