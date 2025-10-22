/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.TaskState
 *  com.jiuqi.np.asynctask.impl.service.AsyncThreadExecutorImpl
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.step;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.TaskState;
import com.jiuqi.np.asynctask.impl.service.AsyncThreadExecutorImpl;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.nr.bpm.asynctask.BatchUploadAsyncTaskExecutor;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.MessageEventListener;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread.BatchMessageThreadLocalStrategy;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.step.StepUtil;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchUploadBean;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;
import com.jiuqi.nr.bpm.de.dataflow.step.provide.StepQueryState;
import com.jiuqi.nr.bpm.de.dataflow.systemoptions.WorkflowOptionsResult;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseUpload {
    private static final Logger log = LoggerFactory.getLogger(BaseUpload.class);
    @Autowired
    IDataentryFlowService dataentryFlowService;
    @Autowired
    DeSetTimeProvide deSetTimeProvide;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    DeEntityHelper deEntityHelper;
    @Autowired
    StepUtil stepUtil;
    @Autowired
    private IFormConditionService formConditionService;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    DimensionUtil dimensionUtil;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Value(value="${jiuqi.nr.workflow.batch-upload-async:true}")
    private boolean asyncBatchUpload;
    @Value(value="${jiuqi.nr.workflow.async-unit-num:200}")
    private int asyncUnitNum;
    @Value(value="${jiuqi.nr.workflow.todo-value:5000}")
    private int maxValue;
    @Autowired
    private MessageEventListener messageEventListener;
    @Autowired
    public IDataentryFlowService dataFlowService;
    @Resource
    public IEntityAuthorityService authService;
    @Resource
    public PeriodEngineService periodEngineService;

    public boolean isUpload(String actionCode) {
        return actionCode.equals("batch_act_upload") || actionCode.equals("act_upload") || actionCode.equals("cus_upload") || actionCode.equals("batch_act_submit") || actionCode.equals("act_submit") || actionCode.equals("cus_submit");
    }

    public boolean isBack(String actionCode) {
        return actionCode.equals("batch_act_reject") || actionCode.equals("act_reject") || actionCode.equals("cus_reject") || actionCode.equals("batch_act_return") || actionCode.equals("act_return") || actionCode.equals("cus_return");
    }

    public boolean isFilterFormOrGroup(String formSchemeKey, String unitId, String resourceId, BatchStepByStepParam stepByOptParam) {
        Map<String, LinkedHashSet<String>> formGroupToUnit;
        boolean is = false;
        WorkFlowType workflowType = this.commonUtil.workflowType(formSchemeKey);
        if (WorkFlowType.ENTITY.equals((Object)workflowType)) {
            is = true;
        } else if (WorkFlowType.FORM.equals((Object)workflowType)) {
            Map<String, LinkedHashSet<String>> formToUnit = this.formToUnit(stepByOptParam.getStepFormKeyMap());
            if (formToUnit.get(resourceId) != null && formToUnit.get(resourceId).contains(unitId)) {
                is = true;
            }
        } else if (WorkFlowType.GROUP.equals((Object)workflowType) && (formGroupToUnit = this.formGroupToUnit(stepByOptParam.getStepGroupKeyMap())).get(resourceId) != null && formGroupToUnit.get(resourceId).contains(unitId)) {
            is = true;
        }
        return is;
    }

    public Map<String, LinkedHashSet<String>> formToUnit(Map<String, LinkedHashSet<String>> unitToForm) {
        HashMap<String, LinkedHashSet<String>> formMap = new HashMap<String, LinkedHashSet<String>>();
        LinkedHashSet<String> units = null;
        if (unitToForm != null && unitToForm.size() > 0) {
            for (Map.Entry<String, LinkedHashSet<String>> to : unitToForm.entrySet()) {
                String unit = to.getKey();
                Set forms = to.getValue();
                for (String form : forms) {
                    if (formMap.get(form) == null) {
                        units = new LinkedHashSet<String>();
                        units.add(unit);
                        formMap.put(form, units);
                        continue;
                    }
                    LinkedHashSet set = (LinkedHashSet)formMap.get(form);
                    set.add(unit);
                    formMap.put(form, set);
                }
            }
        }
        return formMap;
    }

    public boolean isFliterConditions(String formSchemeKey, String period, String entityKey, List<IEntityRow> directChildRows, String resourceId, Map<String, LinkedHashSet<String>> stepFormKeyMap, Map<String, LinkedHashSet<String>> stepGroupKeyMap) {
        LinkedHashSet<String> groupKeyMap;
        boolean accord = false;
        DimensionValueSet dimension = new DimensionValueSet();
        dimension.setValue("DATATIME", (Object)period);
        dimension.setValue(this.dimensionUtil.getDwMainDimName(formSchemeKey), (Object)entityKey);
        boolean bindProcess = this.stepUtil.bindProcess(formSchemeKey, dimension, resourceId);
        List containUnit = directChildRows.stream().filter(e -> e.getEntityKeyData() == entityKey).collect(Collectors.toList());
        boolean containResource = true;
        WorkFlowType workflowType = this.commonUtil.workflowType(formSchemeKey);
        if (WorkFlowType.FORM.equals((Object)workflowType)) {
            LinkedHashSet<String> formKeyMap = stepFormKeyMap.get(entityKey);
            if (formKeyMap == null || !formKeyMap.contains(resourceId)) {
                containResource = false;
            }
        } else if (WorkFlowType.GROUP.equals((Object)workflowType) && ((groupKeyMap = stepGroupKeyMap.get(entityKey)) == null || !groupKeyMap.contains(resourceId))) {
            containResource = false;
        }
        if (containUnit != null && containUnit.size() > 0 && containResource) {
            accord = bindProcess;
        }
        return accord;
    }

    public boolean uploaded(String formSchemeKey) {
        String stepReportType;
        boolean uploaded = true;
        FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
        uploaded = this.commonUtil.isDefaultWorkflow(formSchemeKey) ? "1".equals(stepReportType = formScheme.getFlowsSetting().getStepReportType()) : true;
        return uploaded;
    }

    public void complete(StepByOptParam stepByOptParam, DimensionValueSet dimensionValueSet) {
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setActionId(stepByOptParam.getActionId());
        executeParam.setTaskId(stepByOptParam.getNodeId());
        executeParam.setFormSchemeKey(stepByOptParam.getFormSchemeKey());
        executeParam.setFormKey(stepByOptParam.getFormKey());
        executeParam.setGroupKey(stepByOptParam.getGroupKey());
        executeParam.setDimSet(dimensionValueSet);
        this.dataentryFlowService.executeTask(executeParam);
    }

    public CompleteMsg executeAction(BatchStepByStepParam stepByOptParam, String period, Set<String> unitIds, Map<String, LinkedHashSet<String>> formKeyMap, Map<String, LinkedHashSet<String>> groupKeys) {
        List<DimensionValueSet> stepUnit = stepByOptParam.getStepUnit();
        stepByOptParam.setFormKeys(formKeyMap);
        stepByOptParam.setGroupKeys(groupKeys);
        return this.complete(stepUnit, unitIds, stepByOptParam);
    }

    public CompleteMsg executeActionAll(BatchStepByStepParam stepByOptParam, String period, Set<String> unitIds, Map<String, LinkedHashSet<String>> formKeyMap, Map<String, LinkedHashSet<String>> groupKeys, List<DimensionValueSet> dims) {
        stepByOptParam.setFormKeys(formKeyMap);
        stepByOptParam.setGroupKeys(groupKeys);
        return this.completeAll(dims, unitIds, stepByOptParam);
    }

    public BatchStepByStepResult complete(BatchStepByStepParam stepByOptParam, DimensionValueSet buildDimensionSet) {
        BatchStepByStepResult batchStepByStepResult = new BatchStepByStepResult();
        HashMap<String, LinkedHashSet<String>> formkeyMap = new HashMap<String, LinkedHashSet<String>>();
        HashMap<String, LinkedHashSet<String>> groupKeyMap = new HashMap<String, LinkedHashSet<String>>();
        WorkFlowType startType = this.stepUtil.startType(stepByOptParam.getFormSchemeKey());
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
        List<DimensionValueSet> stepUnit = stepByOptParam.getStepUnit();
        if (stepUnit != null && stepUnit.size() > 0) {
            Set<String> collect = stepUnit.stream().map(e -> e.getValue(dwMainDimName).toString()).collect(Collectors.toSet());
            CompleteMsg completeMsg = this.complete(stepByOptParam.getStepUnit(), collect, stepByOptParam);
            batchStepByStepResult.setCompleteMsg(completeMsg);
            Object value = buildDimensionSet.getValue(this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey()));
            List keys = new ArrayList<String>();
            if (value instanceof String) {
                keys.add(value.toString());
            } else {
                keys = (List)value;
            }
            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                if (value != null && keys.size() > 0) {
                    batchStepByStepResult.setOperateUnits(new HashSet<String>(keys));
                }
            } else if (WorkFlowType.FORM.equals((Object)startType)) {
                formkeyMap = new HashMap();
                if (value != null && keys.size() > 0) {
                    for (String id : keys) {
                        formkeyMap.put(id, new LinkedHashSet(stepByOptParam.getFormKeys().get(id)));
                    }
                    batchStepByStepResult.setOperateUnitAndForms(formkeyMap);
                }
            } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                groupKeyMap = new HashMap();
                if (value != null && keys.size() > 0) {
                    for (String id : keys) {
                        groupKeyMap.put(id, new LinkedHashSet(stepByOptParam.getGroupKeys().get(id)));
                    }
                    batchStepByStepResult.setOperateUnitAndGroups(groupKeyMap);
                }
            }
        }
        return batchStepByStepResult;
    }

    private CompleteMsg complete(List<DimensionValueSet> uploadDim, Set<String> unitIds, BatchStepByStepParam stepByOptParam) {
        CompleteMsg completeMsg = new CompleteMsg();
        completeMsg = this.asyncBatchUpload ? this.completeTaskAsync(uploadDim, unitIds, stepByOptParam) : this.completeTask(uploadDim, unitIds, stepByOptParam);
        return completeMsg;
    }

    private CompleteMsg completeAll(List<DimensionValueSet> uploadDim, Set<String> unitIds, BatchStepByStepParam stepByOptParam) {
        CompleteMsg completeMsg = new CompleteMsg();
        completeMsg = this.asyncBatchUpload ? this.completeTaskAsyncAll(uploadDim, stepByOptParam) : this.completeTask(uploadDim, unitIds, stepByOptParam);
        return completeMsg;
    }

    private CompleteMsg completeTask(List<DimensionValueSet> uploadDim, Set<String> unitIds, BatchStepByStepParam stepByOptParam) {
        List<DimensionValueSet> stepUnit = uploadDim;
        ArrayList<CompleteMsg> msgList = new ArrayList<CompleteMsg>();
        CompleteMsg completeMsg = null;
        WorkFlowType startType = this.stepUtil.startType(stepByOptParam.getFormSchemeKey());
        BatchMessageThreadLocalStrategy.setMessageEventListener(this.messageEventListener);
        BatchMessageThreadLocalStrategy.setSumTotal(unitIds.size());
        BatchMessageThreadLocalStrategy.setUpperLimitValue(this.maxValue);
        if (stepUnit != null && stepUnit.size() > 0) {
            String formSchemeKey = stepByOptParam.getFormSchemeKey();
            List<DimensionValueSet> canUploadUnit = stepUnit.stream().filter(e -> !this.deSetTimeProvide.compareSetTime(formSchemeKey, e).isDisabled()).collect(Collectors.toList());
            Map<String, LinkedHashSet<String>> formKeyMap = stepByOptParam.getFormKeys();
            Map<String, LinkedHashSet<String>> groupKeyMap = stepByOptParam.getGroupKeys();
            for (DimensionValueSet dim : canUploadUnit) {
                String unitId = dim.getValue(this.dimensionUtil.getDwMainDimName(formSchemeKey)).toString();
                HashSet<String> formKeys = (HashSet<String>)formKeyMap.get(unitId);
                HashSet<String> groupKeys = (HashSet<String>)groupKeyMap.get(unitId);
                if (formKeys == null) {
                    formKeys = new HashSet<String>();
                    formKeys.add(this.stepUtil.defaultFormId(formSchemeKey));
                }
                if (groupKeys == null) {
                    groupKeys = new HashSet<String>();
                    groupKeys.add(this.stepUtil.defaultFormId(formSchemeKey));
                }
                BusinessKeySet businessKeySet = this.businessGenerator.buildBusinessKeySet(stepByOptParam.getFormSchemeKey(), new ArrayList<DimensionValueSet>(Arrays.asList(dim)), formKeys, groupKeys);
                BatchExecuteParam executeParam = new BatchExecuteParam();
                executeParam.setBusinessKeySet(businessKeySet);
                executeParam.setActionId(stepByOptParam.getActionId());
                executeParam.setUserId(NpContextHolder.getContext().getIdentityId());
                executeParam.setSendEmaill(stepByOptParam.isSendEmail());
                executeParam.setFormSchemeKey(stepByOptParam.getFormSchemeKey());
                executeParam.setForceUpload(stepByOptParam.isForceUpload());
                executeParam.setTaskId(stepByOptParam.getTaskId());
                executeParam.setNodeId(stepByOptParam.getTaskCode());
                executeParam.setDimSet(dim);
                executeParam.setComment(stepByOptParam.getContent());
                executeParam.setMessageIds(stepByOptParam.getMessageIds());
                executeParam.setFormKey(this.stepUtil.defaultFormId(formSchemeKey));
                executeParam.setCanUploadDimensionSets(canUploadUnit);
                executeParam.setTaskContext(stepByOptParam.getContext());
                if (WorkFlowType.ENTITY.equals((Object)startType)) {
                    completeMsg = this.dataentryFlowService.batchExecuteTask(executeParam);
                    msgList.add(completeMsg);
                    continue;
                }
                if ((groupKeys == null || groupKeys.size() <= 0) && (formKeys == null || formKeys.size() <= 0)) continue;
                completeMsg = this.dataentryFlowService.batchExecuteTask(executeParam);
                msgList.add(completeMsg);
            }
        }
        if (msgList != null && msgList.size() > 0) {
            List msgListTemp = msgList.stream().filter(e -> !e.isSucceed()).collect(Collectors.toList());
            if (msgListTemp != null && msgListTemp.size() > 0) {
                completeMsg = (CompleteMsg)msgListTemp.get(0);
            } else if (msgList != null && msgList.size() > 0) {
                completeMsg = (CompleteMsg)msgList.get(0);
            }
        }
        BatchMessageThreadLocalStrategy.clear();
        return completeMsg;
    }

    private CompleteMsg completeTaskAsync(List<DimensionValueSet> uploadDim, Set<String> unitIds, BatchStepByStepParam stepByOptParam) {
        List<DimensionValueSet> stepUnit = uploadDim;
        ArrayList<CompleteMsg> msgList = new ArrayList<CompleteMsg>();
        CompleteMsg completeMsg = null;
        WorkFlowType startType = this.stepUtil.startType(stepByOptParam.getFormSchemeKey());
        ArrayList<String> asynTaskIds = new ArrayList<String>();
        String formSchemeKey = stepByOptParam.getFormSchemeKey();
        String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        String defaultFormId = this.stepUtil.defaultFormId(formSchemeKey);
        List<DimensionValueSet> canUploadUnit = stepUnit.stream().filter(e -> !this.deSetTimeProvide.compareSetTime(formSchemeKey, e).isDisabled()).collect(Collectors.toList());
        AsyncThreadExecutor asyncThreadExecutor = (AsyncThreadExecutor)BeanUtils.getBean(AsyncThreadExecutorImpl.class);
        if (unitIds != null && unitIds.size() > 0) {
            canUploadUnit = canUploadUnit.stream().filter(e -> unitIds.contains(e.getValue(mainDimName).toString())).collect(Collectors.toList());
            DimensionValueSet mergeDimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(canUploadUnit);
            IConditionCache conditionCache = this.formConditionService.getConditionForms(mergeDimensionValueSet, stepByOptParam.getFormSchemeKey());
            List<List<DimensionValueSet>> splitList = this.calculateThreadNum(canUploadUnit);
            int i = 0;
            for (List<DimensionValueSet> list : splitList) {
                if (list == null || list.size() == 0) continue;
                BatchUploadBean batchUploadBean = new BatchUploadBean();
                batchUploadBean.setCanUploadUnit(list);
                batchUploadBean.setConditionCache(conditionCache);
                batchUploadBean.setDefaultFormKey(defaultFormId);
                batchUploadBean.setDimName(mainDimName);
                batchUploadBean.setWorkFlowType(startType);
                batchUploadBean.setStepByOptParam(stepByOptParam);
                batchUploadBean.setTaskContext(stepByOptParam.getContext());
                NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
                npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)batchUploadBean));
                npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchUploadAsyncTaskExecutor());
                String asynTaskID = asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
                asynTaskIds.add(asynTaskID);
                log.info("\u5f02\u6b65\u4efb\u52a1\u4e2a\u6570=" + i++);
            }
        }
        this.judgeSyncComplete(asynTaskIds, msgList);
        completeMsg = this.dealResult(msgList);
        return completeMsg;
    }

    private CompleteMsg completeTaskAsyncAll(List<DimensionValueSet> uploadDim, BatchStepByStepParam stepByOptParam) {
        List<DimensionValueSet> stepUnit = uploadDim;
        ArrayList<CompleteMsg> msgList = new ArrayList<CompleteMsg>();
        CompleteMsg completeMsg = null;
        WorkFlowType startType = this.stepUtil.startType(stepByOptParam.getFormSchemeKey());
        ArrayList<String> asynTaskIds = new ArrayList<String>();
        String formSchemeKey = stepByOptParam.getFormSchemeKey();
        String mainDimName = this.dimensionUtil.getDwMainDimName(formSchemeKey);
        String defaultFormId = this.stepUtil.defaultFormId(formSchemeKey);
        List<DimensionValueSet> canUploadUnit = stepUnit.stream().filter(e -> !this.deSetTimeProvide.compareSetTime(formSchemeKey, e).isDisabled()).collect(Collectors.toList());
        AsyncThreadExecutor asyncThreadExecutor = (AsyncThreadExecutor)BeanUtils.getBean(AsyncThreadExecutorImpl.class);
        DimensionValueSet mergeDimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(canUploadUnit);
        IConditionCache conditionCache = this.formConditionService.getConditionForms(mergeDimensionValueSet, stepByOptParam.getFormSchemeKey());
        List<List<DimensionValueSet>> splitList = this.calculateThreadNum(canUploadUnit);
        int i = 0;
        for (List<DimensionValueSet> list : splitList) {
            if (list == null || list.size() == 0) continue;
            BatchUploadBean batchUploadBean = new BatchUploadBean();
            batchUploadBean.setCanUploadUnit(list);
            batchUploadBean.setConditionCache(conditionCache);
            batchUploadBean.setDefaultFormKey(defaultFormId);
            batchUploadBean.setDimName(mainDimName);
            batchUploadBean.setWorkFlowType(startType);
            batchUploadBean.setStepByOptParam(stepByOptParam);
            batchUploadBean.setTaskContext(stepByOptParam.getContext());
            NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
            npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)batchUploadBean));
            npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchUploadAsyncTaskExecutor());
            String asynTaskID = asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
            asynTaskIds.add(asynTaskID);
            log.info("\u5f02\u6b65\u4efb\u52a1\u4e2a\u6570=" + i++);
        }
        this.judgeSyncComplete(asynTaskIds, msgList);
        completeMsg = this.dealResult(msgList);
        return completeMsg;
    }

    public List<List<DimensionValueSet>> calculateThreadNum(List<DimensionValueSet> canUploadUnit) {
        List<List<DimensionValueSet>> splitList = new ArrayList<List<DimensionValueSet>>();
        int asyncNum = WorkflowOptionsResult.asyncNum();
        List<List<DimensionValueSet>> splitListTemp = BaseUpload.fixedGrouping(canUploadUnit, this.asyncUnitNum);
        if (splitListTemp != null) {
            splitList = splitListTemp.size() < asyncNum ? splitListTemp : BaseUpload.averageAssign(canUploadUnit, asyncNum);
        }
        return splitList;
    }

    public Map<String, LinkedHashSet<String>> formGroupToUnit(Map<String, LinkedHashSet<String>> unitToFormGroup) {
        HashMap<String, LinkedHashSet<String>> formGroupMap = new HashMap<String, LinkedHashSet<String>>();
        LinkedHashSet<String> units = null;
        if (unitToFormGroup != null && unitToFormGroup.size() > 0) {
            for (Map.Entry<String, LinkedHashSet<String>> to : unitToFormGroup.entrySet()) {
                String unit = to.getKey();
                Set forms = to.getValue();
                for (String form : forms) {
                    if (formGroupMap.get(form) == null) {
                        units = new LinkedHashSet<String>();
                        units.add(unit);
                        formGroupMap.put(form, units);
                        continue;
                    }
                    LinkedHashSet set = (LinkedHashSet)formGroupMap.get(form);
                    set.add(unit);
                    formGroupMap.put(form, set);
                }
            }
        }
        return formGroupMap;
    }

    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int number = source.size() / n;
        int offset = 0;
        for (int i = 0; i < n; ++i) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                --remainder;
                ++offset;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {
        if (null == source || source.size() == 0 || n <= 0) {
            return null;
        }
        ArrayList<List<T>> result = new ArrayList<List<T>>();
        int remainder = source.size() % n;
        int size = source.size() / n;
        for (int i = 0; i < size; ++i) {
            List<T> subset = null;
            subset = source.subList(i * n, (i + 1) * n);
            result.add(subset);
        }
        if (remainder > 0) {
            List<T> subset = null;
            subset = source.subList(size * n, size * n + remainder);
            result.add(subset);
        }
        return result;
    }

    private void judgeSyncComplete(List<String> asynTaskIds, List<CompleteMsg> msgList) {
        block2: while (true) {
            try {
                while (true) {
                    Iterator<String> it = asynTaskIds.iterator();
                    while (it.hasNext()) {
                        String asynTaskId = it.next();
                        TaskState taskState = this.asyncTaskManager.queryTaskState(asynTaskId);
                        if (TaskState.FINISHED.equals((Object)taskState)) {
                            Object detail = this.asyncTaskManager.queryDetail(asynTaskId);
                            List<CompleteMsg> msgs = BaseUpload.castList(detail, CompleteMsg.class);
                            if (msgs != null) {
                                msgList.addAll(msgs);
                            }
                            it.remove();
                            continue;
                        }
                        if (TaskState.ERROR.equals((Object)taskState)) {
                            log.error("\u7ebf\u7a0bid\u4e3a" + asynTaskId + "\u7684\u4efb\u52a1\u6267\u884c\u51fa\u9519");
                            continue;
                        }
                        if (!TaskState.OVERTIME.equals((Object)taskState)) continue;
                        log.error("\u7ebf\u7a0bid\u4e3a" + asynTaskId + "\u7684\u4efb\u52a1\u7b49\u5f85\u8d85\u65f6");
                    }
                    if (asynTaskIds.size() == 0) break block2;
                    Thread.sleep(500L);
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            break;
        }
    }

    private CompleteMsg dealResult(List<CompleteMsg> msgList) {
        CompleteMsg completeMsg = new CompleteMsg();
        ArrayList<CompleteMsg> msgListTemp = new ArrayList<CompleteMsg>();
        if (msgList != null && msgList.size() > 0) {
            for (int i = 0; i < msgList.size(); ++i) {
                CompleteMsg cMsg = msgList.get(i);
                boolean succeed = cMsg.isSucceed();
                if (succeed) continue;
                msgListTemp.add(msgList.get(i));
            }
            if (msgListTemp != null && msgListTemp.size() > 0) {
                completeMsg = (CompleteMsg)msgListTemp.get(0);
            } else if (msgList != null && msgList.size() > 0) {
                completeMsg = msgList.get(0);
            }
        }
        return completeMsg;
    }

    public static <T> List<CompleteMsg> castList(Object obj, Class<CompleteMsg> clazz) {
        ArrayList<CompleteMsg> result = new ArrayList<CompleteMsg>();
        if (obj instanceof List) {
            for (Object o : (List)obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        if (obj instanceof String) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List readValue = (List)mapper.readValue(obj.toString(), List.class);
                for (Object cObj : readValue) {
                    CompleteMsg convertValue = (CompleteMsg)mapper.convertValue(cObj, CompleteMsg.class);
                    result.add(convertValue);
                }
                return result;
            }
            catch (JsonMappingException e) {
                e.printStackTrace();
            }
            catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Map<IEntityRow, Map<String, ActionStateBean>> getParentState(StepQueryState stepQueryState, List<IEntityRow> parentData, LinkedHashSet<String> selectResourceMap) {
        Map<IEntityRow, Map<String, ActionStateBean>> queryParentState = stepQueryState.queryParent(parentData, selectResourceMap);
        return queryParentState;
    }

    public Map<String, Object> getOtherDim(List<DimensionValueSet> stepUnit, String mainDimName) {
        HashMap<String, Object> dimNameToValue = new HashMap<String, Object>();
        DimensionValueSet dimensionValueSet = stepUnit.get(0);
        DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
        ENameSet dimensions = dimensionSet.getDimensions();
        for (int i = 0; i < dimensions.size(); ++i) {
            String dimName = dimensions.get(i);
            if ("DATATIME".equals(dimName) || mainDimName.equals(dimName)) continue;
            Object value = dimensionValueSet.getValue(dimName);
            dimNameToValue.put(dimName, value);
        }
        return dimNameToValue;
    }

    public boolean hasUnitAuditOperation(String entityId, String rowKey, String periodEntityId, String periodString) {
        try {
            if (this.authService.isEnableAuthority(entityId)) {
                Date[] dateRange = this.parseFromPeriod(periodString, periodEntityId);
                return this.authService.canAuditEntity(entityId, rowKey, dateRange[0], dateRange[1]);
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return true;
    }

    private Date[] parseFromPeriod(String periodString, String periodEntityId) throws ParseException {
        Date[] dateRegion = new Date[]{Consts.DATE_VERSION_INVALID_VALUE, Consts.DATE_VERSION_FOR_ALL};
        if (StringUtils.isEmpty((String)periodString) || StringUtils.isEmpty((String)periodEntityId)) {
            return dateRegion;
        }
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
        return periodProvider.getPeriodDateRegion(periodString);
    }
}

