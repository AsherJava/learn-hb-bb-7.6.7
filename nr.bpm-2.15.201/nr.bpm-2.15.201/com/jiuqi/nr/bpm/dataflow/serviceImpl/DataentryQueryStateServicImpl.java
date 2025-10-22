/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.time.setting.bean.MsgReturn
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 */
package com.jiuqi.nr.bpm.dataflow.serviceImpl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.dataflow.service.IReadOnlyService;
import com.jiuqi.nr.bpm.dataflow.serviceImpl.AbstractQueryState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.common.CustomDesignWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.SingleRejectFormActions;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchWorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.Actor.ActorUtils;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.time.setting.bean.MsgReturn;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DataentryQueryStateServicImpl
extends AbstractQueryState {
    private static final Logger logger = LoggerFactory.getLogger(DataentryQueryStateServicImpl.class);
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    private IQueryUploadStateService queryFormStateServicImpl;
    @Autowired
    private IReadOnlyService readOnlyService;
    @Autowired
    private WorkflowSettingService flowService;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired(required=false)
    Map<String, CustomDesignWorkflow> customDesignProvider;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private ActionMethod actionMethod;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private SingleRejectFormActions singleRejectFormActions;
    @Autowired
    private DeSetTimeProvide deSetTimeProvide;
    @Autowired
    private ActorStrategyProvider actorStrategyProvider;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;

    @Override
    public ActionStateBean queryResourceState(DataEntryParam dataEntryParam) {
        return this.queryFormStateServicImpl.queryActionState(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim(), dataEntryParam.getFormKey(), dataEntryParam.getGroupKey());
    }

    @Override
    public ActionStateBean queryUnitState(DataEntryParam dataEntryParam) {
        String formKey = "11111111-1111-1111-1111-111111111111";
        String groupKey = "11111111-1111-1111-1111-111111111111";
        return this.queryFormStateServicImpl.queryActionState(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim(), formKey, groupKey);
    }

    @Override
    public UploadStateNew queryUnitStateInfo(DataEntryParam dataEntryParam) {
        String formKey = "11111111-1111-1111-1111-111111111111";
        String groupKey = "11111111-1111-1111-1111-111111111111";
        return this.queryFormStateServicImpl.queryUploadState(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim(), formKey, groupKey);
    }

    @Override
    public ReadOnlyBean readOnly(DataEntryParam dataEntryParam) {
        return this.readOnlyService.readOnly(dataEntryParam);
    }

    @Override
    public ReadOnlyBean readOnly(DataEntryParam dataEntryParam, UploadStateNew uploadStateNew) {
        return this.readOnlyService.readOnly(dataEntryParam, uploadStateNew);
    }

    @Override
    public Map<DimensionValueSet, ActionStateBean> getWorkflowUploadState(DimensionValueSet dimSet, String formKey, String fromGroupKey, String formSchemeKey) {
        Map<DimensionValueSet, ActionStateBean> actionStateMap = new HashMap<DimensionValueSet, ActionStateBean>();
        try {
            CustomDesignWorkflow map;
            formKey = "11111111-1111-1111-1111-111111111111";
            WorkflowSettingDefine workflowSetting = this.flowService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
            actionStateMap = workflowSetting != null ? ((map = this.map(workflowSetting.getWorkflowId())) != null ? map.queryWorkflowTreeUploadState(dimSet, formKey, formSchemeKey, workflowSetting.getWorkflowId()) : this.queryFormStateServicImpl.queryUploadStateMap(formSchemeKey, dimSet, formKey, fromGroupKey)) : this.queryFormStateServicImpl.queryUploadStateMap(formSchemeKey, dimSet, formKey, fromGroupKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionStateMap;
    }

    private CustomDesignWorkflow map(String processDefineKey) {
        if (this.customDesignProvider != null && this.customDesignProvider.size() > 0) {
            for (Map.Entry<String, CustomDesignWorkflow> customDesign : this.customDesignProvider.entrySet()) {
                CustomDesignWorkflow value = customDesign.getValue();
                if (!value.isApply(processDefineKey)) continue;
                return value;
            }
        }
        return null;
    }

    @Override
    public Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> batchWorkflowDataInfo(BatchWorkflowDataBean batchWorkflowData) {
        Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> workflowMap = new HashMap<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>>();
        try {
            WorkflowDataInfo workflowDataInfo = null;
            String formSchemeKey = batchWorkflowData.getFormSchemeKey();
            FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(formSchemeKey);
            Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(formSchemeKey);
            RunTimeService runtimeService = processEngine.map((? super T engine) -> engine.getRunTimeService()).orElse(null);
            DeployService deployService = processEngine.map((? super T engine) -> engine.getDeployService()).orElse(null);
            WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
            List<WorkflowDataBean> workflowDatas = batchWorkflowData.getWorkflowDataList();
            String dwMainDimName = this.dimensionUtil.getDwMainDimName(batchWorkflowData.getFormSchemeKey());
            List dims = workflowDatas.stream().map((? super T e) -> e.getDimSet()).collect(Collectors.toList());
            DimensionValueSet dimensionSet = DimensionValueSetUtil.mergeDimensionValueSet(dims);
            List<DimensionValueSet> customCheckFliterUnitKeys = this.actionMethod.getCustomCheckFliterUnitKeys(formScheme.getTaskKey(), dimensionSet, formScheme.getDw());
            Map<String, List<Task>> taskMap = this.getTasks(formSchemeKey, workflowDatas, startType, dwMainDimName);
            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                for (WorkflowDataBean workData : workflowDatas) {
                    ArrayList<WorkflowDataInfo> workflowDataList = new ArrayList<WorkflowDataInfo>();
                    DimensionValueSet filterDims = this.dimensionUtil.fliterDimensionValueSet(workData.getDimSet(), formScheme);
                    BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, filterDims, workData.getFormKey(), workData.getFormGroupKey());
                    List<Task> tasks = taskMap.get(BusinessKeyFormatter.formatToString(businessKey));
                    if (tasks != null && tasks.size() > 0) {
                        for (Task task : tasks) {
                            workflowDataInfo = new WorkflowDataInfo();
                            workflowDataInfo.setWorkFlowType(this.workflow.queryStartType(workData.getFormSchemeKey()));
                            workflowDataInfo.setWorkFlowType(startType);
                            workflowDataInfo.setTaskId(task.getId());
                            workflowDataInfo.setTaskCode(task.getUserTaskId());
                            List<WorkflowAction> action = this.actionMethod.getActions(task, workData.getFormSchemeKey(), task.getUserTaskId(), filterDims, customCheckFliterUnitKeys);
                            List<WorkflowAction> otherWorkflowAction = this.otherWorkflowAction(tasks, businessKey);
                            action.addAll(otherWorkflowAction);
                            workflowDataInfo.setActions(action);
                            WorkflowDataInfo singleRejectAction = this.singleRejectFormActions.singleRejectAction(formScheme, businessKey, filterDims, workData.getFormKey(), runtimeService, deployService, startType);
                            if (singleRejectAction != null && singleRejectAction.getTaskId() != null) {
                                action.addAll(singleRejectAction.getActions());
                            }
                            MsgReturn compareSetTime = this.deSetTimeProvide.compareSetTime(workData.getFormSchemeKey(), workData.getDimSet());
                            workflowDataInfo.setDisabled(compareSetTime.isDisabled());
                            workflowDataList.add(workflowDataInfo);
                        }
                    } else {
                        List<WorkflowAction> otherWorkflowAction = this.otherWorkflowAction(tasks, businessKey);
                        workflowDataInfo = new WorkflowDataInfo();
                        WorkflowDataInfo singleRejectAction = this.singleRejectFormActions.singleRejectAction(formScheme, businessKey, filterDims, workData.getFormKey(), runtimeService, deployService, startType);
                        if (singleRejectAction != null && singleRejectAction.getTaskId() != null) {
                            otherWorkflowAction.addAll(singleRejectAction.getActions());
                        }
                        workflowDataInfo.setActions(otherWorkflowAction);
                        workflowDataInfo.setWorkFlowType(this.workflow.queryStartType(workData.getFormSchemeKey()));
                        workflowDataList.add(workflowDataInfo);
                    }
                    String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
                    LinkedHashMap<String, ArrayList<WorkflowDataInfo>> childrenMap = new LinkedHashMap<String, ArrayList<WorkflowDataInfo>>();
                    childrenMap.put(filterDims.getValue(mainDimName).toString(), workflowDataList);
                    workflowMap.put(filterDims, childrenMap);
                }
            } else if (WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) {
                workflowMap = this.queryWorkflowInfo(formSchemeKey, startType, workflowDatas, customCheckFliterUnitKeys, taskMap);
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return workflowMap;
    }

    private Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> queryWorkflowInfo(String formSchemeKey, WorkFlowType startType, List<WorkflowDataBean> workflowDatas, List<DimensionValueSet> customCheckFliterUnitKeys, Map<String, List<Task>> taskMap) {
        HashMap<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> workflowMap = new HashMap<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>>();
        try {
            FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(formSchemeKey);
            WorkflowDataInfo workflowDataInfo = null;
            for (WorkflowDataBean workData : workflowDatas) {
                DimensionValueSet filterDims = this.dimensionUtil.fliterDimensionValueSet(workData.getDimSet(), formScheme);
                LinkedHashMap childrenMap = new LinkedHashMap();
                ArrayList formKeys = workData.getFormKeys();
                List<String> formGroupKeys = workData.getFormGroupKeys();
                ArrayList keys = WorkFlowType.FORM.equals((Object)startType) ? formKeys : (WorkFlowType.GROUP.equals((Object)startType) ? formGroupKeys : new ArrayList());
                for (String key : keys) {
                    ArrayList<WorkflowDataInfo> workflowDataList = new ArrayList<WorkflowDataInfo>();
                    BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, filterDims, key, key);
                    List<Task> tasks = taskMap.get(BusinessKeyFormatter.formatToString(businessKey));
                    if (tasks != null && tasks.size() > 0) {
                        for (Task task : tasks) {
                            workflowDataInfo = new WorkflowDataInfo();
                            workflowDataInfo.setTaskId(task.getId());
                            workflowDataInfo.setTaskCode(task.getUserTaskId());
                            List<WorkflowAction> action = this.actionMethod.getActions(task, formSchemeKey, task.getUserTaskId(), filterDims, customCheckFliterUnitKeys);
                            List<WorkflowAction> otherWorkflowAction = this.otherWorkflowAction(tasks, businessKey);
                            action.addAll(otherWorkflowAction);
                            workflowDataInfo.setActions(action);
                            workflowDataInfo.setWorkFlowType(this.workflow.queryStartType(formSchemeKey));
                            workflowDataList.add(workflowDataInfo);
                        }
                    } else {
                        workflowDataInfo = new WorkflowDataInfo();
                        List<WorkflowAction> otherWorkflowAction = this.otherWorkflowAction(tasks, businessKey);
                        workflowDataInfo.setActions(otherWorkflowAction);
                        workflowDataInfo.setWorkFlowType(this.workflow.queryStartType(formSchemeKey));
                        workflowDataList.add(workflowDataInfo);
                    }
                    childrenMap.put(key, workflowDataList);
                }
                workflowMap.put(filterDims, childrenMap);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return workflowMap;
    }

    @Override
    public List<WorkflowDataInfo> queryWorkflowDataInfo(WorkflowDataBean workflowData) {
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(workflowData.getFormSchemeKey());
        WorkFlowType startType = this.workflow.queryStartType(workflowData.getFormSchemeKey());
        DimensionValueSet filterDims = this.dimensionUtil.fliterDimensionValueSet(workflowData.getDimSet(), formScheme);
        List<DimensionValueSet> customCheckFliterUnitKeys = this.actionMethod.getCustomCheckFliterUnitKeys(formScheme.getTaskKey(), filterDims, formScheme.getDw());
        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(workflowData.getFormSchemeKey(), filterDims, workflowData.getFormKey(), workflowData.getFormGroupKey());
        List<Task> tasks = this.workflow.queryTasks(workflowData.getFormSchemeKey(), workflowData.getFormKey(), workflowData.getFormGroupKey(), filterDims, businessKey, true, workflowData.getConditionCache());
        ArrayList<WorkflowDataInfo> workflowDataList = new ArrayList<WorkflowDataInfo>();
        WorkflowDataInfo workflowDataInfo = null;
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                workflowDataInfo = new WorkflowDataInfo();
                workflowDataInfo.setWorkFlowType(startType);
                workflowDataInfo.setTaskId(task.getId());
                workflowDataInfo.setTaskCode(task.getUserTaskId());
                List<WorkflowAction> action = this.actionMethod.getActions(task, workflowData.getFormSchemeKey(), task.getUserTaskId(), filterDims, customCheckFliterUnitKeys);
                List<WorkflowAction> otherWorkflowAction = this.otherWorkflowAction(tasks, businessKey);
                action.addAll(otherWorkflowAction);
                workflowDataInfo.setActions(action);
                MsgReturn compareSetTime = this.deSetTimeProvide.compareSetTime(workflowData.getFormSchemeKey(), workflowData.getDimSet());
                workflowDataInfo.setDisabled(compareSetTime.isDisabled());
                workflowDataList.add(workflowDataInfo);
            }
        } else {
            List<WorkflowAction> otherWorkflowAction = this.otherWorkflowAction(tasks, businessKey);
            workflowDataInfo = new WorkflowDataInfo();
            workflowDataInfo.setActions(otherWorkflowAction);
            workflowDataInfo.setWorkFlowType(startType);
            workflowDataList.add(workflowDataInfo);
        }
        this.setSingleRejectAction(workflowDataList, formScheme, businessKey, filterDims, workflowData.getFormKey(), startType);
        return workflowDataList;
    }

    private void setSingleRejectAction(List<WorkflowDataInfo> workflowDataList, FormSchemeDefine formScheme, BusinessKey businessKey, DimensionValueSet filterDims, String formKey, WorkFlowType startType) {
        DeployService deployService;
        Optional<ProcessEngine> processEngine;
        RunTimeService runtimeService;
        WorkflowDataInfo singleRejectAction;
        if (WorkFlowType.ENTITY.equals((Object)startType) && (singleRejectAction = this.singleRejectFormActions.singleRejectAction(formScheme, businessKey, filterDims, formKey, runtimeService = (RunTimeService)(processEngine = this.workflow.getProcessEngine(formScheme.getKey())).map((? super T engine) -> engine.getRunTimeService()).orElse(null), deployService = (DeployService)processEngine.map((? super T engine) -> engine.getDeployService()).orElse(null), startType)) != null) {
            workflowDataList.add(singleRejectAction);
        }
    }

    @Override
    public ActionStateBean queryUploadState(String fromSchemeKey, DimensionValueSet dimensionValueSet, String formKey, String formGroupKey) {
        return this.queryFormStateServicImpl.queryActionState(fromSchemeKey, dimensionValueSet, formKey, formGroupKey);
    }

    private Map<String, List<Task>> getTasks(String formSchemeKey, List<WorkflowDataBean> workflowDatas, WorkFlowType workFlowType, String dwMainDimName) {
        HashMap<String, List<Task>> map = new HashMap<String, List<Task>>();
        ArrayList<BusinessKey> businessKeys = new ArrayList<BusinessKey>();
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        Actor candicateActor = Actor.fromNpContext();
        Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(formSchemeKey);
        RunTimeService runtimeService = processEngine.map((? super T engine) -> engine.getRunTimeService()).orElse(null);
        DeployService deployService = processEngine.map((? super T engine) -> engine.getDeployService()).orElse(null);
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(formSchemeKey);
        if (workflowDatas != null && workflowDatas.size() > 0) {
            for (WorkflowDataBean workflowDataBean : workflowDatas) {
                DimensionValueSet filterDims = this.dimensionUtil.fliterDimensionValueSet(workflowDataBean.getDimSet(), formScheme);
                if (WorkFlowType.ENTITY.equals((Object)workFlowType)) {
                    BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, filterDims, workflowDataBean.getFormKey(), workflowDataBean.getFormGroupKey());
                    businessKeys.add(businessKey);
                    continue;
                }
                if (!WorkFlowType.FORM.equals((Object)workFlowType) && !WorkFlowType.GROUP.equals((Object)workFlowType)) continue;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                ArrayList formKeys = workflowDataBean.getFormKeys();
                List<String> formGroupKeys = workflowDataBean.getFormGroupKeys();
                ArrayList keys = WorkFlowType.FORM.equals((Object)workFlowType) ? formKeys : (WorkFlowType.GROUP.equals((Object)workFlowType) ? formGroupKeys : new ArrayList());
                for (String key : keys) {
                    BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, filterDims, key, key);
                    businessKeys.add(businessKey);
                }
            }
            if (businessKeys.size() > 0) {
                Map<String, List<BusinessKey>> businessKeyMap = this.buildBusinessKeyMap(businessKeys, formSchemeKey, dwMainDimName);
                Map<String, Map<String, Boolean>> workflowStop = this.isWorkflowStop(formScheme.getKey(), businessKeys, dwMainDimName);
                block2: for (Map.Entry<String, List<BusinessKey>> entry : businessKeyMap.entrySet()) {
                    List<BusinessKey> businessKeyList = entry.getValue();
                    for (BusinessKey businessKey1 : businessKeyList) {
                        Map<String, List<Task>> listMap = this.buildMap(runtimeService, deployService, businessKeyList, candicateActor, defaultWorkflow, businessKey1, workflowStop, workFlowType, dwMainDimName);
                        if (listMap == null || listMap.size() <= 0) continue;
                        map.putAll(listMap);
                        continue block2;
                    }
                }
            }
        }
        return map;
    }

    private Map<String, List<Task>> buildMap(RunTimeService runtimeService, DeployService deployService, List<BusinessKey> businessKeys, Actor candicateActor, boolean defaultWorkflow, BusinessKey businessKey, Map<String, Map<String, Boolean>> workflowStop, WorkFlowType workFlowType, String dwDimName) {
        HashMap<String, List<Task>> map = new HashMap<String, List<Task>>(businessKeys.size());
        try {
            List<Object> tasks = new ArrayList();
            tasks = defaultWorkflow ? runtimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), true) : runtimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
            if (tasks != null && tasks.size() > 0) {
                Optional<UserTask> userTask = deployService.getUserTask(((Task)tasks.get(0)).getProcessDefinitionId(), ((Task)tasks.get(0)).getUserTaskId(), businessKey.getFormSchemeKey());
                Map<String, Boolean> batchTaskActor = ActorUtils.isBatchTaskActor(userTask.get(), businessKeys, candicateActor, this.actorStrategyProvider, (Task)tasks.get(0), defaultWorkflow);
                for (BusinessKey businessKey2 : businessKeys) {
                    boolean hasAuth;
                    String busniessStr = BusinessKeyFormatter.formatToString(businessKey2);
                    if (!batchTaskActor.containsKey(busniessStr) || !(hasAuth = batchTaskActor.get(busniessStr).booleanValue())) continue;
                    DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(businessKey2);
                    String currentUnitkey = dimensionValueSet.getValue(dwDimName).toString();
                    String formKey = businessKey2.getFormKey();
                    if (workflowStop != null && workflowStop.size() > 0) {
                        Map<String, Boolean> booleanMap = workflowStop.get(currentUnitkey);
                        if (booleanMap != null) {
                            Boolean b;
                            if (WorkFlowType.ENTITY.equals((Object)workFlowType)) {
                                b = booleanMap.values().stream().findAny().get();
                                if (b.booleanValue()) continue;
                                map.put(busniessStr, tasks);
                                continue;
                            }
                            if (!WorkFlowType.FORM.equals((Object)workFlowType) && !WorkFlowType.GROUP.equals((Object)workFlowType) || (b = booleanMap.get(formKey)).booleanValue()) continue;
                            map.put(busniessStr, tasks);
                            continue;
                        }
                        map.put(busniessStr, tasks);
                        continue;
                    }
                    map.put(busniessStr, tasks);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return map;
    }

    private Map<String, List<BusinessKey>> buildBusinessKeyMap(List<BusinessKey> businessKeys, String formSchemeKey, String dwMainDimName) {
        HashMap<String, List<BusinessKey>> map = new HashMap<String, List<BusinessKey>>();
        if (businessKeys == null && businessKeys.size() == 0) {
            return new HashMap<String, List<BusinessKey>>();
        }
        ArrayList<String> businessKeyList = new ArrayList<String>();
        for (BusinessKey businessKey : businessKeys) {
            businessKeyList.add(BusinessKeyFormatter.formatToString(businessKey));
        }
        Map<String, List<String>> businessKeyMap = this.getBusinessKeyStrMap(businessKeys, formSchemeKey, dwMainDimName);
        ArrayList<String> businessTemp = new ArrayList<String>();
        if (businessKeyMap != null && businessKeyMap.size() > 0) {
            Set<String> actionCodes = businessKeyMap.keySet();
            Collection<List<String>> values = businessKeyMap.values();
            for (List<String> list : values) {
                businessTemp.addAll(list);
            }
            businessKeyList.removeAll(businessTemp);
            if (businessKeyList != null && businessKeyList.size() > 0) {
                Optional<String> startCode = actionCodes.stream().filter(ac -> ac.startsWith("start")).findAny();
                if (startCode.isPresent()) {
                    List<String> list = businessKeyMap.get(startCode.get());
                    if (list == null) {
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.addAll(businessKeyList);
                    } else {
                        businessKeyMap.get(startCode.get()).addAll(businessKeyList);
                    }
                } else {
                    businessKeyMap.put("start", businessKeyList);
                }
            }
            for (Map.Entry entry : businessKeyMap.entrySet()) {
                String key = (String)entry.getKey();
                List value = (List)entry.getValue();
                ArrayList<BusinessKey> bs = new ArrayList<BusinessKey>();
                for (String businessKey : value) {
                    bs.add(BusinessKeyFormatter.parsingFromString(businessKey));
                }
                map.put(key, bs);
            }
        } else {
            map.put("start", businessKeys);
        }
        return map;
    }

    private Map<String, List<String>> getBusinessKeyStrMap(List<BusinessKey> businessKeys, String formSchemeKey, String dwMainDimName) {
        List<UploadStateNew> states;
        HashMap<String, List<String>> businessStrMap = new HashMap<String, List<String>>();
        ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
        HashSet<String> formKeys = new HashSet<String>();
        ArrayList<String> unitKeys = new ArrayList<String>();
        for (BusinessKey businessKey : businessKeys) {
            DimensionValueSet dimensionValueSet;
            String unitKey;
            if (StringUtils.hasText(businessKey.getFormKey())) {
                formKeys.add(businessKey.getFormKey());
            }
            if (unitKeys.contains(unitKey = (dimensionValueSet = this.dimensionUtil.buildDimension(businessKey)).getValue(dwMainDimName).toString())) continue;
            unitKeys.add(unitKey);
            dims.add(dimensionValueSet);
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(dims);
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(formSchemeKey);
        ArrayList<String> formOrGroupKeys = new ArrayList<String>();
        if (formKeys != null && formKeys.size() > 0) {
            formOrGroupKeys.addAll(formKeys);
        }
        if ((states = this.batchQueryUploadStateService.queryUploadStateNew(formScheme, dimensionValueSet, formOrGroupKeys)) != null && states.size() > 0) {
            for (UploadStateNew uploadStateNew : states) {
                DimensionValueSet entities = uploadStateNew.getEntities();
                String formId = uploadStateNew.getFormId();
                String preEvent = uploadStateNew.getPreEvent();
                String taskId = uploadStateNew.getTaskId();
                String uniqueKey = preEvent + "@" + taskId;
                BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, entities, formId, formId);
                String businessStr = BusinessKeyFormatter.formatToString(businessKey);
                ArrayList<String> businessKeyList = (ArrayList<String>)businessStrMap.get(uniqueKey);
                if (businessKeyList == null) {
                    businessKeyList = new ArrayList<String>();
                    businessKeyList.add(businessStr);
                    businessStrMap.put(uniqueKey, businessKeyList);
                    continue;
                }
                ((List)businessStrMap.get(uniqueKey)).add(businessStr);
            }
        }
        return businessStrMap;
    }

    @Override
    public UploadStateNew queryUploadStateList(String fromSchemeKey, DimensionValueSet dimensionValueSet, String formKey, String formGroupKey) {
        return this.queryFormStateServicImpl.queryUploadState(fromSchemeKey, dimensionValueSet, formKey, formGroupKey);
    }

    private Map<String, Map<String, Boolean>> isWorkflowStop(String formSchemeKey, List<BusinessKey> businessKeys, String dwMainDimName) {
        ArrayList<String> unitKeys = new ArrayList<String>();
        ArrayList<String> formOrGroupKeys = new ArrayList<String>();
        String period = null;
        for (BusinessKey businessKey : businessKeys) {
            DimensionValueSet dimensionValueSet = this.dimensionUtil.buildDimension(businessKey);
            String unitKey = dimensionValueSet.getValue(dwMainDimName).toString();
            unitKeys.add(unitKey);
            String formKey = businessKey.getFormKey();
            formOrGroupKeys.add(formKey);
            period = businessKey.getPeriod();
        }
        Map<String, Map<String, Boolean>> processStop = this.flowService.isProcessStop(formSchemeKey, period, unitKeys, formOrGroupKeys);
        return processStop;
    }
}

