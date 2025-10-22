/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskGroupDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.params.base.FormData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.util;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.nr.dataentry.bean.DWorkflowConfig;
import com.jiuqi.nr.dataentry.gather.IDataentryFormFilter;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.internal.service.util.DateTimeUtil;
import com.jiuqi.nr.dataentry.paramInfo.FormGroupData;
import com.jiuqi.nr.dataentry.paramInfo.FormSchemeData;
import com.jiuqi.nr.dataentry.paramInfo.TaskData;
import com.jiuqi.nr.dataentry.paramInfo.TaskGroupParam;
import com.jiuqi.nr.dataentry.service.ITemplateConfigService;
import com.jiuqi.nr.dataentry.service.IWorkflowService;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AnalysisSchemeParamDefine;
import com.jiuqi.nr.definition.facade.DesignTaskGroupDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.service.DesignTaskGroupDefineService;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataEntryParamProvider {
    private static final Logger logger = LoggerFactory.getLogger(DataEntryParamProvider.class);
    private IRunTimeViewController runtimeView = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private IFormulaRunTimeController formulaRunTimeController = (IFormulaRunTimeController)BeanUtil.getBean(IFormulaRunTimeController.class);
    private IDataAccessProvider dataAccessProvider = (IDataAccessProvider)BeanUtil.getBean(IDataAccessProvider.class);
    private FormGroupProvider formGroupProvider = (FormGroupProvider)BeanUtil.getBean(FormGroupProvider.class);
    private IWorkflowService workflowService = (IWorkflowService)BeanUtil.getBean(IWorkflowService.class);
    private RunTimeAuthViewController runTimeAuthViewController = (RunTimeAuthViewController)BeanUtil.getBean(RunTimeAuthViewController.class);
    private DesignTaskGroupDefineService designTaskGroupDefineService = (DesignTaskGroupDefineService)BeanUtil.getBean(DesignTaskGroupDefineService.class);
    private Map<String, IDataentryFormFilter> dataentryFormFilterMap;
    private IPeriodEntityAdapter periodEntityAdapter = (IPeriodEntityAdapter)BeanUtil.getBean(IPeriodEntityAdapter.class);
    private ITemplateConfigService templateConfigService;
    private PeriodEngineService periodEngineService;

    public DataEntryParamProvider() {
        this.dataentryFormFilterMap = SpringBeanUtils.getApplicationContext().getBeansOfType(IDataentryFormFilter.class);
        this.templateConfigService = (ITemplateConfigService)BeanUtil.getBean(ITemplateConfigService.class);
        this.periodEngineService = (PeriodEngineService)BeanUtil.getBean(PeriodEngineService.class);
    }

    public List<FormGroupData> getRuntimeFMDM(JtableContext context) {
        ArrayList<FormGroupData> groups = new ArrayList<FormGroupData>();
        List formDefines = this.runtimeView.queryAllFormDefinesByFormScheme(context.getFormSchemeKey());
        for (FormDefine formDefine : formDefines) {
            if (formDefine.getFormType() != FormType.FORM_TYPE_NEWFMDM) continue;
            List formGroupsByFormKey = this.runtimeView.getFormGroupsByFormKey(formDefine.getKey());
            FormGroupData formGroupData = new FormGroupData();
            formGroupData.setKey(((FormGroupDefine)formGroupsByFormKey.get(0)).getKey());
            formGroupData.setCode(((FormGroupDefine)formGroupsByFormKey.get(0)).getCode());
            formGroupData.setTitle(((FormGroupDefine)formGroupsByFormKey.get(0)).getTitle());
            FormData formData = new FormData();
            formData.init(formDefine);
            formGroupData.addForm(formData);
            groups.add(formGroupData);
            return groups;
        }
        return groups;
    }

    public List<FormGroupData> getRuntimeFormList(JtableContext jtableContext) {
        List<FormGroupData> formGroupList = this.formGroupProvider.getFormGroupList(jtableContext, false);
        return formGroupList;
    }

    public List<TaskData> getRuntimeTaskByGroupKey(TaskGroupParam taskGroupParam) {
        if (StringUtils.isEmpty((String)taskGroupParam.getGroupKey())) {
            return this.getRuntimeTaskList();
        }
        return this.getTaskListByGroupKey(taskGroupParam.getGroupKey());
    }

    public List<TaskData> getRuntimeTaskList() {
        ArrayList<TaskData> taskList = new ArrayList<TaskData>();
        List allTaskDefines = this.runTimeAuthViewController.getAllReportTaskDefines();
        Date date = DateTimeUtil.getDay();
        for (TaskDefine taskDefine : allTaskDefines) {
            TaskData taskData = new TaskData();
            taskData.initWithoutFlowSetting(taskDefine);
            try {
                List designTaskGroupDefines = this.designTaskGroupDefineService.getGroupByTask(taskDefine.getKey());
                ArrayList loopDefines = new ArrayList(designTaskGroupDefines);
                List groupKeys = designTaskGroupDefines.stream().map(item -> item.getKey()).collect(Collectors.toList());
                StringBuffer taskGroupKeyS = new StringBuffer();
                if (designTaskGroupDefines.size() > 0) {
                    for (DesignTaskGroupDefine designTaskGroupDefine : designTaskGroupDefines) {
                        taskGroupKeyS.append(designTaskGroupDefine.getKey()).append(";");
                    }
                    Iterator iterator = loopDefines.iterator();
                    while (iterator.hasNext()) {
                        DesignTaskGroupDefine designTaskGroupDefine;
                        DesignTaskGroupDefine loopdefine = designTaskGroupDefine = (DesignTaskGroupDefine)iterator.next();
                        while (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)loopdefine.getParentKey())) {
                            if (groupKeys.contains((loopdefine = (DesignTaskGroupDefine)this.runtimeView.queryTaskGroupDefine(loopdefine.getParentKey())).getKey())) continue;
                            groupKeys.add(loopdefine.getKey());
                            designTaskGroupDefines.add(loopdefine);
                        }
                    }
                    taskData.setTaskGroupKeys(taskGroupKeyS.toString());
                    taskData.setDesignTaskGroupDefines(designTaskGroupDefines);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            taskList.add(taskData);
        }
        return taskList;
    }

    public TaskData getRuntimeTaskByKey(String taskKey) {
        TaskDefine taskDefine = null;
        try {
            this.runtimeView.initTask(taskKey);
            taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (taskDefine != null) {
            TaskData task = new TaskData();
            task.init(taskDefine);
            return task;
        }
        return null;
    }

    public List<FormSchemeData> getRuntimeFormSchemeList(String taskKey) throws Exception {
        ArrayList<FormSchemeData> list = new ArrayList<FormSchemeData>();
        List queryAllFormSchemeDefines = null;
        TaskDefine taskDefine = null;
        if (taskKey != null) {
            try {
                this.runtimeView.initTask(taskKey);
                taskDefine = this.runtimeView.queryTaskDefine(taskKey);
                queryAllFormSchemeDefines = this.runTimeAuthViewController.queryFormSchemeByTask(taskKey);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw e;
            }
        } else {
            queryAllFormSchemeDefines = new ArrayList();
        }
        if (queryAllFormSchemeDefines == null || queryAllFormSchemeDefines.isEmpty()) {
            throw new NotFoundFormSchemeException(null);
        }
        for (FormSchemeDefine formSchemeDefine : queryAllFormSchemeDefines) {
            FormSchemeData formSchemeData = new FormSchemeData();
            formSchemeData.init(this.formulaRunTimeController, this.dataAccessProvider, formSchemeDefine, taskDefine);
            DWorkflowConfig workflowConfig = null;
            workflowConfig = this.workflowService.getWorkflowConfig(formSchemeData.getKey());
            formSchemeData.setWorkflowConfig(workflowConfig);
            try {
                AnalysisSchemeParamDefine queryAnalysisSchemeParamDefine = this.runtimeView.queryAnalysisSchemeParamDefine(formSchemeData.getKey());
                formSchemeData.setAnalysisParamDefine(queryAnalysisSchemeParamDefine);
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                throw e;
            }
            list.add(formSchemeData);
        }
        return list;
    }

    public List<TaskData> getTaskListByGroupKey(String groupKey) {
        ArrayList<TaskData> taskList = new ArrayList<TaskData>();
        List taskDefinesFromGroup = this.runTimeAuthViewController.getTaskDefinesFromGroup(groupKey);
        Date date = DateTimeUtil.getDay();
        for (TaskDefine taskDefine : taskDefinesFromGroup) {
            TaskData taskData = new TaskData();
            taskData.initWithoutFlowSetting(taskDefine);
            try {
                List designTaskGroupDefines = this.designTaskGroupDefineService.getGroupByTask(taskDefine.getKey());
                ArrayList loopDefines = new ArrayList(designTaskGroupDefines);
                List groupKeys = designTaskGroupDefines.stream().map(item -> item.getKey()).collect(Collectors.toList());
                StringBuffer taskGroupKeyS = new StringBuffer();
                if (designTaskGroupDefines.size() > 0) {
                    for (DesignTaskGroupDefine designTaskGroupDefine : designTaskGroupDefines) {
                        taskGroupKeyS.append(designTaskGroupDefine.getKey()).append(";");
                    }
                    Iterator iterator = loopDefines.iterator();
                    while (iterator.hasNext()) {
                        DesignTaskGroupDefine designTaskGroupDefine;
                        DesignTaskGroupDefine loopdefine = designTaskGroupDefine = (DesignTaskGroupDefine)iterator.next();
                        while (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)loopdefine.getParentKey())) {
                            if (groupKeys.contains((loopdefine = (DesignTaskGroupDefine)this.runtimeView.queryTaskGroupDefine(loopdefine.getParentKey())).getKey())) continue;
                            groupKeys.add(loopdefine.getKey());
                            designTaskGroupDefines.add(loopdefine);
                        }
                    }
                    taskData.setTaskGroupKeys(taskGroupKeyS.toString());
                    taskData.setDesignTaskGroupDefines(designTaskGroupDefines);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            taskList.add(taskData);
        }
        return taskList;
    }
}

