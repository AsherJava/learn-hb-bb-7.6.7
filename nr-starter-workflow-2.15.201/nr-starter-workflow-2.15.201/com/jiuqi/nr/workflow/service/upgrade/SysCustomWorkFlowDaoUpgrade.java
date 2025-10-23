/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.sql.CustomClassExecutor
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowAction
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine
 *  com.jiuqi.nr.bpm.custom.dao.impl.DesignCustomWorkflowDaoImpl
 *  com.jiuqi.nr.bpm.custom.dao.impl.RunTimeCustomWorkflowDaoImpl
 *  com.jiuqi.nr.bpm.custom.service.impl.WorkflowCustomServiceImpl
 *  com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine
 *  com.jiuqi.nr.bpm.setting.dao.impl.WorkflowSettingDao
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow.service.upgrade;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.sql.CustomClassExecutor;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.dao.impl.DesignCustomWorkflowDaoImpl;
import com.jiuqi.nr.bpm.custom.dao.impl.RunTimeCustomWorkflowDaoImpl;
import com.jiuqi.nr.bpm.custom.service.impl.WorkflowCustomServiceImpl;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.dao.impl.WorkflowSettingDao;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.sql.DataSource;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

public class SysCustomWorkFlowDaoUpgrade
implements CustomClassExecutor {
    WorkflowSettingDao wfSettingDao = (WorkflowSettingDao)SpringBeanUtils.getBean(WorkflowSettingDao.class);
    IRunTimeViewController rtvController = (IRunTimeViewController)SpringBeanUtils.getBean(IRunTimeViewController.class);
    DesignCustomWorkflowDaoImpl designTimeWorkFlowDao;
    RunTimeCustomWorkflowDaoImpl runTimeWorkFlowDao = (RunTimeCustomWorkflowDaoImpl)SpringBeanUtils.getBean(RunTimeCustomWorkflowDaoImpl.class);
    WorkflowCustomServiceImpl customWorkFlowService;
    IFormulaRunTimeController formulaRunTimeService;

    public SysCustomWorkFlowDaoUpgrade() {
        this.designTimeWorkFlowDao = (DesignCustomWorkflowDaoImpl)SpringBeanUtils.getBean(DesignCustomWorkflowDaoImpl.class);
        this.customWorkFlowService = (WorkflowCustomServiceImpl)SpringBeanUtils.getBean(WorkflowCustomServiceImpl.class);
        this.formulaRunTimeService = (IFormulaRunTimeController)SpringBeanUtils.getBean(IFormulaRunTimeController.class);
    }

    public void execute(DataSource dataSource) {
        List settingDefineList = this.wfSettingDao.getWorkflowSettingDefineList();
        if (settingDefineList == null) {
            return;
        }
        List formSchemeDefines = this.rtvController.listAllFormScheme();
        Map<String, Set<String>> workflowDefineMapToTask = this.workflowDefineMapToTask(formSchemeDefines, settingDefineList);
        for (WorkflowSettingDefine settingDefine : settingDefineList) {
            WorkFlowDefine workFlowDefine;
            TaskDefine taskDefine;
            FormSchemeDefine formScheme = formSchemeDefines.stream().filter(e -> e.getKey().equals(settingDefine.getDataId())).findFirst().orElse(null);
            if (formScheme == null || (taskDefine = this.rtvController.getTask(formScheme.getTaskKey())) == null || (workFlowDefine = this.runTimeWorkFlowDao.getWorkFlowDefineByID(settingDefine.getWorkflowId(), 1)) == null) continue;
            try {
                String workflowDefineId = this.isDifferentTaskRefSameDefine(workflowDefineMapToTask, settingDefine) ? this.differentTaskRefSameDefine(taskDefine, workFlowDefine, settingDefine) : this.taskRefWorkflowDefine(taskDefine, workFlowDefine, workFlowDefine.getId());
                this.customWorkFlowService.releaseWorkFlowDefine(workflowDefineId, 0);
            }
            catch (Exception e2) {
                LoggerFactory.getLogger(this.getClass()).error(e2.getMessage(), e2.getCause());
            }
        }
    }

    private Map<String, Set<String>> workflowDefineMapToTask(List<FormSchemeDefine> formSchemeDefines, List<WorkflowSettingDefine> settingDefineList) {
        HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();
        for (WorkflowSettingDefine settingDefine : settingDefineList) {
            String workflowId = settingDefine.getWorkflowId();
            map.computeIfAbsent(workflowId, k -> new HashSet());
            FormSchemeDefine formScheme = formSchemeDefines.stream().filter(e -> e.getKey().equals(settingDefine.getDataId())).findFirst().orElse(null);
            if (formScheme == null) continue;
            ((Set)map.get(workflowId)).add(formScheme.getTaskKey());
        }
        return map;
    }

    private boolean isDifferentTaskRefSameDefine(Map<String, Set<String>> workflowDefineMapToTask, WorkflowSettingDefine settingDefine) {
        return workflowDefineMapToTask.get(settingDefine.getWorkflowId()).size() > 1;
    }

    private String differentTaskRefSameDefine(TaskDefine taskDefine, WorkFlowDefine workFlowDefine, WorkflowSettingDefine settingDefine) {
        String copyWorkFlowDefineId = this.customWorkFlowService.copyWorkFlowDefine(workFlowDefine.getId());
        WorkFlowDefine copyWorkFlowDefine = this.designTimeWorkFlowDao.getWorkFlowDefineByID(copyWorkFlowDefineId, 0);
        this.taskRefWorkflowDefine(taskDefine, copyWorkFlowDefine, workFlowDefine.getId());
        this.wfSettingDao.updateWorkflowSettingDefineById(settingDefine.getId(), copyWorkFlowDefine.getId());
        return copyWorkFlowDefine.getId();
    }

    private String taskRefWorkflowDefine(TaskDefine taskDefine, WorkFlowDefine workFlowDefine, String oldDefineId) {
        this.designTimeWorkFlowDao.updateWorkflowDefine(workFlowDefine.getId(), oldDefineId, taskDefine.getKey());
        List nodeActions = this.designTimeWorkFlowDao.getWorkFlowActionsByLinkID(workFlowDefine.getLinkid());
        if (nodeActions == null) {
            return workFlowDefine.getId();
        }
        for (WorkFlowAction action : nodeActions) {
            try {
                this.updateWorkflowAction(taskDefine, action);
            }
            catch (Exception e) {
                LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            }
        }
        return workFlowDefine.getId();
    }

    private void updateWorkflowAction(TaskDefine taskDefine, WorkFlowAction action) throws Exception {
        if (!"cus_submit".equals(action.getActionCode()) && !"cus_upload".equals(action.getActionCode())) {
            return;
        }
        JSONObject actionConfig = new JSONObject(action.getExset());
        if (taskDefine.getFlowsSetting().getReportBeforeAudit()) {
            actionConfig.put("needAutoCheck", true);
        }
        JSONObject needAutoCheckConf = this.getNeedAutoCheckConf(taskDefine, actionConfig);
        actionConfig.put("needAutoCheckConf", needAutoCheckConf.toMap());
        if (taskDefine.getFlowsSetting().getReportBeforeOperation()) {
            actionConfig.put("needAutoCalculate", true);
        }
        JSONObject needAutoCalculateConf = this.getNeedAutoCalculateConf(taskDefine, actionConfig);
        actionConfig.put("needAutoCalculateConf", needAutoCalculateConf.toMap());
        if (taskDefine.getFlowsSetting().isCheckBeforeReporting()) {
            actionConfig.put("nodeCheck", true);
        }
        JSONObject nodeCheckConf = this.getNodeCheckConf(taskDefine);
        actionConfig.put("nodeCheckConf", nodeCheckConf.toMap());
        action.setExset(actionConfig.toString());
        this.designTimeWorkFlowDao.updateWorkflowAction(action);
    }

    private JSONObject getNeedAutoCheckConf(TaskDefine taskDefine, JSONObject actionConfig) throws Exception {
        JSONObject needAutoCheckConf;
        block0: {
            Iterator<Map.Entry<String, List<String>>> iterator;
            TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
            needAutoCheckConf = new JSONObject();
            Map<String, List<String>> formulaSchemes = this.formSchemeToFormulaSchemes(taskDefine, flowsSetting.getReportBeforeAuditValue());
            HashMap<String, Object> currencyConf = new HashMap<String, Object>();
            ReportAuditType checkBZType = flowsSetting.getReportBeforeAuditType();
            currencyConf.put("type", checkBZType.getValue());
            String checkBZValues = flowsSetting.getReportBeforeAuditCustom();
            currencyConf.put("cusValue", checkBZValues);
            needAutoCheckConf.put("dimFilterCondition", (Object)flowsSetting.getFilterCondition());
            needAutoCheckConf.put("formulaSchemes", formulaSchemes);
            needAutoCheckConf.put("currencyConf", currencyConf);
            if (formulaSchemes.isEmpty() || !(iterator = formulaSchemes.entrySet().iterator()).hasNext()) break block0;
            Map.Entry<String, List<String>> entry = iterator.next();
            actionConfig.put("needAutoCheckFormSchemeKey", (Object)entry.getKey());
        }
        return needAutoCheckConf;
    }

    private JSONObject getNeedAutoCalculateConf(TaskDefine taskDefine, JSONObject actionConfig) throws Exception {
        JSONObject needAutoCalculateConf;
        block0: {
            Iterator<Map.Entry<String, List<String>>> iterator;
            TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
            needAutoCalculateConf = new JSONObject();
            Map<String, List<String>> formulaSchemes = this.formSchemeToFormulaSchemes(taskDefine, flowsSetting.getReportBeforeOperationValue());
            needAutoCalculateConf.put("formulaSchemes", formulaSchemes);
            if (formulaSchemes.isEmpty() || !(iterator = formulaSchemes.entrySet().iterator()).hasNext()) break block0;
            Map.Entry<String, List<String>> entry = iterator.next();
            actionConfig.put("needAutoCalculateFormSchemeKey", (Object)entry.getKey());
        }
        return needAutoCalculateConf;
    }

    private JSONObject getNeedAfterCalculateConf(TaskDefine taskDefine) throws Exception {
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        JSONObject needAfterCalculateConf = new JSONObject();
        Map<String, List<String>> formulaSchemes = this.formSchemeToFormulaSchemes(taskDefine, flowsSetting.getSubmitAfterFormulaValue());
        needAfterCalculateConf.put("formulaSchemes", formulaSchemes);
        return needAfterCalculateConf;
    }

    private JSONObject getNodeCheckConf(TaskDefine taskDefine) {
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        JSONObject nodeCheckConf = new JSONObject();
        HashMap<String, Object> currencyConf = new HashMap<String, Object>();
        ReportAuditType checkBZType = flowsSetting.getCheckBeforeReportingType();
        currencyConf.put("type", checkBZType.getValue());
        String checkBZValues = flowsSetting.getCheckBeforeReportingCustom();
        currencyConf.put("cusValue", checkBZValues);
        nodeCheckConf.put("currencyConf", currencyConf);
        return nodeCheckConf;
    }

    private Map<String, List<String>> formSchemeToFormulaSchemes(TaskDefine taskDefine, String formulaSchemes) throws Exception {
        LinkedHashMap<String, List<String>> map = new LinkedHashMap<String, List<String>>();
        String[] confFormulaSchemes = formulaSchemes.split(";");
        List formSchemeDefines = this.rtvController.listFormSchemeByTask(taskDefine.getKey());
        for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
            map.computeIfAbsent(formSchemeDefine.getKey(), k -> new ArrayList());
            List schemes = this.formulaRunTimeService.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
            for (FormulaSchemeDefine formulaScheme : schemes) {
                if (!Arrays.stream(confFormulaSchemes).anyMatch(formulaSchemeKey -> formulaSchemeKey.equals(formulaScheme.getKey()))) continue;
                ((List)map.get(formSchemeDefine.getKey())).add(formulaScheme.getKey());
            }
        }
        for (Map.Entry entry : map.entrySet()) {
            if (!((List)entry.getValue()).isEmpty()) continue;
            FormulaSchemeDefine defFormulaScheme = this.formulaRunTimeService.getDefaultFormulaSchemeInFormScheme((String)entry.getKey());
            ((List)entry.getValue()).add(defFormulaScheme.getKey());
        }
        return map;
    }
}

