/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
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
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.Actor.ActorUtils;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class BatchQueryTaskUtil {
    @Autowired
    private IRunTimeViewController runtimeViewController;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private ActorStrategyProvider actorStrategyProvider;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    private IWorkflow workflow;

    public Map<String, List<Task>> getTasks(String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> groupKeys, WorkFlowType workFlowType, String dwMainDimName) {
        HashMap<String, List<Task>> map = new HashMap<String, List<Task>>();
        ArrayList<BusinessKey> businessKeys = new ArrayList<BusinessKey>();
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        Actor candicateActor = Actor.fromNpContext();
        Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(formSchemeKey);
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        FormSchemeDefine formScheme = this.runtimeViewController.getFormScheme(formSchemeKey);
        List dimensionSetList = DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)dimensionValueSet);
        for (DimensionValueSet dimensionSet : dimensionSetList) {
            DimensionValueSet filterDims = this.dimensionUtil.fliterDimensionValueSet(dimensionSet, formScheme);
            if (WorkFlowType.ENTITY.equals((Object)workFlowType)) {
                String defaultFormId = this.nrParameterUtils.getDefaultFormId(formSchemeKey);
                BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, filterDims, defaultFormId, defaultFormId);
                businessKeys.add(businessKey);
                continue;
            }
            if (!WorkFlowType.FORM.equals((Object)workFlowType) && !WorkFlowType.GROUP.equals((Object)workFlowType)) continue;
            ArrayList keys = WorkFlowType.FORM.equals((Object)workFlowType) ? formKeys : (WorkFlowType.GROUP.equals((Object)workFlowType) ? groupKeys : new ArrayList());
            for (String key : keys) {
                BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, filterDims, key, key);
                businessKeys.add(businessKey);
            }
        }
        if (businessKeys.size() > 0) {
            Map<String, List<BusinessKey>> businessKeyMap = this.buildBusinessKeyMap(businessKeys, formSchemeKey, dwMainDimName);
            block2: for (Map.Entry<String, List<BusinessKey>> businessKey : businessKeyMap.entrySet()) {
                List<BusinessKey> businessKeyList = businessKey.getValue();
                for (BusinessKey businessKey1 : businessKeyList) {
                    Map<String, List<Task>> listMap = this.buildMap(runtimeService, deployService, businessKeyList, candicateActor, defaultWorkflow, businessKey1);
                    if (listMap == null || listMap.size() <= 0) continue;
                    map.putAll(listMap);
                    continue block2;
                }
            }
        }
        return map;
    }

    public Map<String, List<Task>> getTasks(String formSchemeKey, List<WorkflowDataBean> workflowDatas, WorkFlowType workFlowType, String dwMainDimName) {
        HashMap<String, List<Task>> map = new HashMap<String, List<Task>>();
        ArrayList<BusinessKey> businessKeys = new ArrayList<BusinessKey>();
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        Actor candicateActor = Actor.fromNpContext();
        Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(formSchemeKey);
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
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
                LinkedHashMap childrenMap = new LinkedHashMap();
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
                block2: for (Map.Entry<String, List<BusinessKey>> businessKey : businessKeyMap.entrySet()) {
                    List<BusinessKey> businessKeyList = businessKey.getValue();
                    for (BusinessKey businessKey1 : businessKeyList) {
                        Map<String, List<Task>> listMap = this.buildMap(runtimeService, deployService, businessKeyList, candicateActor, defaultWorkflow, businessKey1);
                        if (listMap == null || listMap.size() <= 0) continue;
                        map.putAll(listMap);
                        continue block2;
                    }
                }
            }
        }
        return map;
    }

    private Map<String, List<Task>> buildMap(RunTimeService runtimeService, DeployService deployService, List<BusinessKey> businessKeys, Actor candicateActor, boolean defaultWorkflow, BusinessKey businessKey) {
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
}

