/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DeWorkflowBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.service.SingleFormRejectService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException
 *  com.jiuqi.nr.jtable.exception.NotFoundTaskException
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.readwrite.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.DeWorkflowBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.CommitFlowResult;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.bean.DWorkflowData;
import com.jiuqi.nr.dataentry.bean.DWorkflowUserAction;
import com.jiuqi.nr.dataentry.bean.DimensionCacheKey;
import com.jiuqi.nr.dataentry.bean.WorkflowParam;
import com.jiuqi.nr.dataentry.readwrite.IBatchDimensionReadWriteAccess;
import com.jiuqi.nr.dataentry.readwrite.IReadWriteStatusAccess;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.dataentry.readwrite.bean.EntityBatchAuthCache;
import com.jiuqi.nr.dataentry.readwrite.bean.FlowObjectBatchAuthCache;
import com.jiuqi.nr.dataentry.readwrite.bean.ReadWriteAccessCacheParams;
import com.jiuqi.nr.dataentry.readwrite.bean.WorkflowBatchAuthCache;
import com.jiuqi.nr.dataentry.readwrite.impl.ReadWriteAccessCacheManager;
import com.jiuqi.nr.dataentry.service.IWorkflowService;
import com.jiuqi.nr.dataentry.util.Consts;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.jtable.exception.NotFoundFormSchemeException;
import com.jiuqi.nr.jtable.exception.NotFoundTaskException;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=15)
@Component
@Deprecated
public class WorkFlowReadWriteAccessImpl
implements IReadWriteStatusAccess<CommitFlowResult>,
IBatchDimensionReadWriteAccess {
    private static final Logger logger = LoggerFactory.getLogger(WorkFlowReadWriteAccessImpl.class);
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private IDataentryFlowService dataentryFlowService;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private SingleFormRejectService singleFormRejectService;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private IWorkflow workflow;
    private final String KEY = "uploadEntitys";

    @Override
    public String getName() {
        return "upload";
    }

    @Override
    public CommitFlowResult getStatus(ReadWriteAccessItem item, JtableContext context) {
        CommitFlowResult result = new CommitFlowResult();
        WorkflowParam workflowParam = new WorkflowParam();
        workflowParam.setContext(context);
        Map map = (Map)item.getParams();
        workflowParam.setFormKeys((List)map.get("formKeys"));
        workflowParam.setGroupKeys((List)map.get("groupKeys"));
        try {
            DataEntryParam dataEntryParam = this.getDataEntryParam(workflowParam);
            DeWorkflowBean deWorkflow = this.dataentryFlowService.getDeWorkflow(dataEntryParam);
            result.setActions(this.getDWorkFlowDatas(deWorkflow.getWorkflowDataInfoList()));
            ReadOnlyBean workflowReadOnly = deWorkflow.getReadOnlyBean();
            result.setUploadState(deWorkflow.getActionState());
            result.setWriteable(workflowReadOnly.isReadOnly());
            result.setUnWriteMsg(workflowReadOnly.getMsg());
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException("\u540e\u53f0\u4e0a\u62a5\u6d41\u7a0b\u6743\u9650\u5224\u65ad\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        return result;
    }

    @Override
    public ReadWriteAccessDesc readable(ReadWriteAccessItem item, CommitFlowResult status, JtableContext context) {
        return new ReadWriteAccessDesc(true, "");
    }

    @Override
    public ReadWriteAccessDesc writeable(ReadWriteAccessItem item, CommitFlowResult status, JtableContext context) {
        return new ReadWriteAccessDesc(status.isWriteable(), status.getUnWriteMsg());
    }

    @Override
    public Object initCache(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        if (readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_WRITE || readWriteAccessCacheParams.getFormAccessLevel() == Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE) {
            JtableContext jtableContext = this.getJtableContext(readWriteAccessCacheParams);
            DimensionValueSet dimensionValueSetNew = DimensionValueSetUtil.getDimensionValueSet((JtableContext)jtableContext);
            jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSetNew));
            List<ReadOnlyBean> uploadStates = this.workflowService.batchWorkflowReadOnly(jtableContext);
            if (null != uploadStates) {
                ReadWriteAccessCacheManager accessCacheManager = new ReadWriteAccessCacheManager();
                HashMap<String, Object> res = new HashMap<String, Object>();
                Object multiplexingCacheObj = null;
                if (null != accessCacheManager) {
                    multiplexingCacheObj = accessCacheManager.getMultiplexingCacheObj(this.getName(), "uploadEntitys");
                }
                List<EntityViewData> uploadEntitys = this.getUploadEntitys(jtableContext.getFormSchemeKey(), accessCacheManager, multiplexingCacheObj);
                FormSchemeDefine formScheme = this.runtimeView.getFormScheme(readWriteAccessCacheParams.getJtableContext().getFormSchemeKey());
                if (formScheme.getFlowsSetting().getDesignFlowSettingDefine().isAllowFormBack()) {
                    WorkflowBatchAuthCache workflowBatchAuthCache = new WorkflowBatchAuthCache();
                    EntityBatchAuthCache entityBatchAuthCache = this.getEntityAuthCache(readWriteAccessCacheParams, jtableContext, uploadStates, accessCacheManager, res, uploadEntitys);
                    boolean index = false;
                    boolean adjustDimension = false;
                    HashMap<DimensionCacheKey, Set<String>> rejectEntityForms = new HashMap<DimensionCacheKey, Set<String>>();
                    for (ReadOnlyBean readOnlyBean : uploadStates) {
                        DimensionValueSet dimensionValueSet = readOnlyBean.getDim();
                        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
                        if (!index) {
                            adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(dimensionSet, uploadEntitys);
                        }
                        Map<String, DimensionValue> currentDimension = ReadWriteAccessCacheManager.getDimensionValue(adjustDimension, dimensionSet, uploadEntitys);
                        DimensionCacheKey cacheKey = new DimensionCacheKey(currentDimension);
                        Set formKeys = this.singleFormRejectService.getFormKeysByAction(dimensionValueSet, jtableContext.getFormSchemeKey(), "single_form_reject");
                        rejectEntityForms.put(cacheKey, formKeys);
                    }
                    workflowBatchAuthCache.setAllowFormRejecct(true);
                    workflowBatchAuthCache.setEntityBatchAuthCache(entityBatchAuthCache);
                    workflowBatchAuthCache.setRejectEntityForms(rejectEntityForms);
                    return workflowBatchAuthCache;
                }
                WorkflowBatchAuthCache workflowBatchAuthCache = new WorkflowBatchAuthCache();
                EntityBatchAuthCache entityBatchAuthCache = this.getEntityAuthCache(readWriteAccessCacheParams, jtableContext, uploadStates, accessCacheManager, res, uploadEntitys);
                workflowBatchAuthCache.setAllowFormRejecct(false);
                workflowBatchAuthCache.setEntityBatchAuthCache(entityBatchAuthCache);
                return workflowBatchAuthCache;
            }
        }
        return null;
    }

    private JtableContext getJtableContext(ReadWriteAccessCacheParams readWriteAccessCacheParams) {
        JtableContext jtableContext = new JtableContext(readWriteAccessCacheParams.getJtableContext());
        StringBuilder formKeyBuilder = new StringBuilder();
        for (String formKey : readWriteAccessCacheParams.getFormKeys()) {
            formKeyBuilder.append(formKey).append(";");
        }
        if (formKeyBuilder.length() > 1) {
            formKeyBuilder.setLength(formKeyBuilder.length() - 1);
            jtableContext.setFormKey(formKeyBuilder.toString());
        }
        return jtableContext;
    }

    private EntityBatchAuthCache getEntityAuthCache(ReadWriteAccessCacheParams readWriteAccessCacheParams, JtableContext jtableContext, List<ReadOnlyBean> uploadStates, ReadWriteAccessCacheManager accessCacheManager, Map<String, Object> res, List<EntityViewData> uploadEntitys) {
        int index = 0;
        boolean adjustDimension = false;
        FlowObjectBatchAuthCache flowObjectBatchAuthCache = new FlowObjectBatchAuthCache();
        String formSchemeKey = readWriteAccessCacheParams.getJtableContext().getFormSchemeKey();
        WorkFlowType workFlowType = this.workflow.queryStartType(formSchemeKey);
        flowObjectBatchAuthCache.setWorkFlowType(workFlowType);
        HashSet<DimensionCacheKey> cacheKeys = new HashSet<DimensionCacheKey>();
        HashMap<DimensionCacheKey, HashSet<String>> readOnlyForms = new HashMap<DimensionCacheKey, HashSet<String>>();
        ArrayList<String> dimKeys = new ArrayList();
        for (ReadOnlyBean readOnlyBean : uploadStates) {
            HashSet<String> formKeys;
            if (readOnlyBean.isReadOnly()) continue;
            DimensionValueSet dimensionValueSet = readOnlyBean.getDim();
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
            if (index == 0) {
                adjustDimension = ReadWriteAccessCacheManager.checkAdjustDimension(dimensionSet, uploadEntitys);
            }
            Map<String, DimensionValue> currentDimension = ReadWriteAccessCacheManager.getDimensionValue(adjustDimension, dimensionSet, uploadEntitys);
            if (index == 0) {
                dimKeys = new ArrayList<String>(currentDimension.keySet());
                flowObjectBatchAuthCache.setDimKeys(dimKeys);
            }
            DimensionCacheKey cacheKey = new DimensionCacheKey(currentDimension);
            cacheKeys.add(cacheKey);
            ++index;
            if (workFlowType == WorkFlowType.ENTITY) {
                flowObjectBatchAuthCache.setNotWriteEntitys(cacheKeys);
                continue;
            }
            if (workFlowType == WorkFlowType.GROUP) {
                formKeys = this.getReadOnlyForms(readOnlyForms, cacheKey);
                String groupKey = readOnlyBean.getGroupKey();
                List formDefines = null;
                try {
                    formDefines = this.runtimeView.getAllFormsInGroup(groupKey);
                    List keys = formDefines.stream().map(e -> e.getKey()).collect(Collectors.toList());
                    formKeys.addAll(keys);
                }
                catch (Exception e2) {
                    logger.error(e2.getMessage(), e2);
                }
                continue;
            }
            if (workFlowType != WorkFlowType.FORM) continue;
            formKeys = this.getReadOnlyForms(readOnlyForms, cacheKey);
            String formKey = readOnlyBean.getFormKey();
            formKeys.add(formKey);
        }
        flowObjectBatchAuthCache.setNotCanWriteFormKeys(readOnlyForms);
        return flowObjectBatchAuthCache;
    }

    private HashSet<String> getReadOnlyForms(Map<DimensionCacheKey, HashSet<String>> readOnlyForms, DimensionCacheKey cacheKey) {
        HashSet<String> formKeys = readOnlyForms.get(cacheKey);
        if (formKeys == null) {
            formKeys = new HashSet();
            readOnlyForms.put(cacheKey, formKeys);
        }
        return formKeys;
    }

    @Override
    public ReadWriteAccessDesc matchingAccessLevel(Object cacheObj, Consts.FormAccessLevel formAccessLevel, DimensionCacheKey cacheKey, String formKey, EntityViewData dwEntity) {
        if (Consts.FormAccessLevel.FORM_DATA_WRITE == formAccessLevel || Consts.FormAccessLevel.FORM_DATA_SYSTEM_WRITE == formAccessLevel) {
            WorkflowBatchAuthCache authCache = (WorkflowBatchAuthCache)cacheObj;
            EntityBatchAuthCache entityBatchAuthCache = authCache.getEntityBatchAuthCache();
            FlowObjectBatchAuthCache flowObjectBatchAuthCache = (FlowObjectBatchAuthCache)entityBatchAuthCache;
            WorkFlowType flowType = flowObjectBatchAuthCache.getWorkFlowType();
            DimensionCacheKey simpleKey = ReadWriteAccessCacheManager.getSimpleKey(cacheKey, flowObjectBatchAuthCache.getDimKeys());
            if (flowType == WorkFlowType.ENTITY) {
                boolean notCanWrite;
                HashSet<DimensionCacheKey> cacheKeys = flowObjectBatchAuthCache.getNotWriteEntitys();
                boolean bl = notCanWrite = cacheKeys != null && cacheKeys.contains(simpleKey);
                if (notCanWrite) {
                    if (authCache.isAllowFormRejecct()) {
                        Set<String> formKeys;
                        Map<DimensionCacheKey, Set<String>> rejectEntityForms = authCache.getRejectEntityForms();
                        Set<String> set = formKeys = rejectEntityForms == null ? null : rejectEntityForms.get(simpleKey);
                        if (formKeys == null || !formKeys.contains(formKey)) {
                            return new ReadWriteAccessDesc(false, "\u8be5\u5de5\u4f5c\u6d41\u8282\u70b9\u4e0d\u53ef\u7f16\u8f91");
                        }
                        return new ReadWriteAccessDesc(true, "");
                    }
                    return new ReadWriteAccessDesc(false, "\u8be5\u5de5\u4f5c\u6d41\u8282\u70b9\u4e0d\u53ef\u7f16\u8f91");
                }
                return new ReadWriteAccessDesc(true, "");
            }
            if (flowType == WorkFlowType.GROUP || flowType == WorkFlowType.FORM) {
                Map<DimensionCacheKey, HashSet<String>> dimensionFormKeys = flowObjectBatchAuthCache.getNotCanWriteFormKeys();
                if (dimensionFormKeys.containsKey(simpleKey)) {
                    HashSet<String> formKeys = dimensionFormKeys.get(simpleKey);
                    if (formKeys.contains(formKey)) {
                        return new ReadWriteAccessDesc(false, "\u8be5\u5de5\u4f5c\u6d41\u8282\u70b9\u4e0d\u53ef\u7f16\u8f91");
                    }
                    return new ReadWriteAccessDesc(true, "");
                }
                return new ReadWriteAccessDesc(true, "");
            }
        }
        return null;
    }

    @Override
    public String getCacheLevel() {
        return "FORM";
    }

    @Override
    public String getStatusFormKey(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager) {
        if (formAccessLevel == Consts.FormAccessLevel.FORM_DATA_WRITE) {
            List<EntityViewData> uploadEntitys;
            Object multiplexingCacheObj = null;
            if (null != accessCacheManager) {
                multiplexingCacheObj = accessCacheManager.getMultiplexingCacheObj(this.getName(), "uploadEntitys");
            }
            if (null != (uploadEntitys = this.getUploadEntitys(formSchemeKey, accessCacheManager, multiplexingCacheObj)) && uploadEntitys.size() > 0) {
                return ReadWriteAccessCacheManager.getStatusKey(dimensionSet, null, uploadEntitys);
            }
        }
        return null;
    }

    public String getStatusFormKeyCache(Map<String, DimensionValue> dimensionSet, List<EntityViewData> entityList, String formKey, Consts.FormAccessLevel formAccessLevel, String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager, List<EntityViewData> uploadEntitys) {
        if (formAccessLevel == Consts.FormAccessLevel.FORM_DATA_WRITE && null != uploadEntitys && uploadEntitys.size() > 0) {
            return ReadWriteAccessCacheManager.getStatusKey(dimensionSet, formKey, uploadEntitys);
        }
        return null;
    }

    private List<EntityViewData> getUploadEntitys(String formSchemeKey, ReadWriteAccessCacheManager accessCacheManager, Object multiplexingCacheObj) {
        List uploadEntitys = null;
        if (null == multiplexingCacheObj) {
            FormSchemeDefine formSchemeDefine = null;
            try {
                formSchemeDefine = this.runtimeView.getFormScheme(formSchemeKey);
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (null == formSchemeDefine) {
                throw new NotFoundFormSchemeException(new String[]{formSchemeKey + "\u62a5\u8868\u65b9\u6848\u672a\u627e\u5230"});
            }
            TaskFlowsDefine flowsSetting = formSchemeDefine.getFlowsSetting();
            String masterEntitiesKey = flowsSetting.getDesignTableDefines();
            if (StringUtils.isNotEmpty((String)masterEntitiesKey)) {
                try {
                    uploadEntitys = this.jtableParamService.getEntityList(masterEntitiesKey);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
            if (uploadEntitys == null || uploadEntitys.size() == 0) {
                TaskDefine taskDefine = null;
                try {
                    taskDefine = this.runtimeView.queryTaskDefine(formSchemeDefine.getTaskKey());
                }
                catch (Exception e) {
                    logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
                if (null == taskDefine) {
                    throw new NotFoundTaskException(new String[]{formSchemeDefine.getTaskKey() + "\u4efb\u52a1\u6ca1\u6709\u627e\u5230"});
                }
                flowsSetting = taskDefine.getFlowsSetting();
                masterEntitiesKey = flowsSetting.getDesignTableDefines();
                if (StringUtils.isNotEmpty((String)masterEntitiesKey)) {
                    uploadEntitys = this.jtableParamService.getEntityList(masterEntitiesKey);
                }
            }
            if (uploadEntitys == null) {
                uploadEntitys = new ArrayList();
            }
            if (null != accessCacheManager) {
                accessCacheManager.setMultiplexingCacheObj(this.getName(), "uploadEntitys", uploadEntitys);
            }
        } else {
            uploadEntitys = (ArrayList)multiplexingCacheObj;
        }
        return uploadEntitys;
    }

    private DataEntryParam getDataEntryParam(WorkflowParam workflowParam) {
        DataEntryParam dataEntryParam = new DataEntryParam();
        dataEntryParam.setFormKey(workflowParam.getContext().getFormKey());
        dataEntryParam.setFormSchemeKey(workflowParam.getContext().getFormSchemeKey());
        dataEntryParam.setGroupKey(workflowParam.getContext().getFormGroupKey());
        dataEntryParam.setGroupKeys(workflowParam.getGroupKeys());
        dataEntryParam.setFormKeys(workflowParam.getFormKeys());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)workflowParam.getContext());
        dataEntryParam.setDim(dimensionValueSet);
        return dataEntryParam;
    }

    private List<DWorkflowData> getDWorkFlowDatas(List<WorkflowDataInfo> workflowDataInfos) {
        ArrayList<DWorkflowData> dWorkflowDatas = new ArrayList<DWorkflowData>();
        for (WorkflowDataInfo wInfo : workflowDataInfos) {
            DWorkflowData dWorkflowData = new DWorkflowData();
            dWorkflowData.setTaskId(wInfo.getTaskId());
            dWorkflowData.setTaskCode(wInfo.getTaskCode());
            dWorkflowData.setDisabled(wInfo.isDisabled());
            ArrayList<DWorkflowUserAction> dWorkflowUserActions = new ArrayList<DWorkflowUserAction>();
            for (WorkflowAction workflowAction : wInfo.getActions()) {
                DWorkflowUserAction dWorkflowUserAction = new DWorkflowUserAction();
                dWorkflowUserAction.setCode(workflowAction.getCode());
                dWorkflowUserAction.setTitle(workflowAction.getTitle());
                dWorkflowUserAction.setIcon(workflowAction.getIcon());
                dWorkflowUserAction.setDesc(workflowAction.getDesc());
                DUserActionParam dUserActionParam = new DUserActionParam();
                dUserActionParam.setCheckFilter(workflowAction.getActionParam().getCheckFilter());
                dUserActionParam.setOpenForceCommit(workflowAction.getActionParam().isForceCommit());
                dUserActionParam.setNeedAutoCalculate(workflowAction.getActionParam().isNeedAutoCalculate());
                dUserActionParam.setNeedAutoCheck(workflowAction.getActionParam().isNeedAutoCheck());
                dUserActionParam.setNeedAutoNodeCheck(workflowAction.getActionParam().isNodeCheck());
                dUserActionParam.setNeedOptDesc(workflowAction.getActionParam().isNeedOptDesc());
                dUserActionParam.setNeedbuildVersion(workflowAction.getActionParam().isNeedbuildVersion());
                dUserActionParam.setStepByStepUpload(workflowAction.getActionParam().isStepByStep());
                dUserActionParam.setBatchOpt(workflowAction.getActionParam().isBatchOpt());
                dUserActionParam.setSysMsgShow(workflowAction.getActionParam().isSysMsgShow());
                dUserActionParam.setMailShow(workflowAction.getActionParam().isMailShow());
                dUserActionParam.setWorkFlowType(wInfo.getWorkFlowType());
                dUserActionParam.setSubmitAfterFormula(workflowAction.getActionParam().isSubmitAfterFormula());
                dUserActionParam.setSubmitAfterFormulaValue(workflowAction.getActionParam().getSubmitAfterFormulaValue());
                dUserActionParam.setCalculateFormulaValue(workflowAction.getActionParam().getCalcuteFormulaValue());
                dUserActionParam.setCheckFormulaValue(workflowAction.getActionParam().getCheckFormulaValue());
                dUserActionParam.setNeedAutoCheckAll(workflowAction.getActionParam().isNeedAutoAllCheck());
                dUserActionParam.setCheckCurrencyValue(workflowAction.getActionParam().getCheckCurrencyValue());
                dUserActionParam.setNodeCheckCurrencyValue(workflowAction.getActionParam().getNodeCheckCurrencyValue());
                dUserActionParam.setCheckCurrencyType(workflowAction.getActionParam().getCheckCurrencyType());
                dUserActionParam.setNodeCheckCurrencyType(workflowAction.getActionParam().getNodeCheckCurrencyType());
                dUserActionParam.setReturnExplain(workflowAction.getActionParam().isReturnExplain());
                dUserActionParam.setSignBootMode(workflowAction.getActionParam().isSignStartMode());
                dUserActionParam.setSingleRejectAction(workflowAction.getActionParam().isSingleRejectAction());
                ArrayList<Integer> formualTypes = new ArrayList<Integer>();
                try {
                    List auditTypes = this.auditTypeDefineService.queryAllAuditType();
                    if (auditTypes == null || auditTypes.size() == 0) {
                        throw new Exception();
                    }
                    for (AuditType auditType : auditTypes) {
                        formualTypes.add(auditType.getCode());
                    }
                }
                catch (Exception e) {
                    formualTypes.add(1);
                    formualTypes.add(2);
                    formualTypes.add(4);
                }
                ArrayList<Integer> erroStatus = new ArrayList<Integer>();
                for (int i = 0; i < formualTypes.size(); ++i) {
                    if (workflowAction.getActionParam().getIgnoreErrorStatus() != null && workflowAction.getActionParam().getIgnoreErrorStatus().contains(formualTypes.get(i))) continue;
                    erroStatus.add((Integer)formualTypes.get(i));
                }
                dUserActionParam.setErroStatus(erroStatus);
                dUserActionParam.setNeedCommentErrorStatus(workflowAction.getActionParam().getNeedCommentErrorStatus());
                dWorkflowUserAction.setUserActionParam(dUserActionParam);
                dWorkflowUserActions.add(dWorkflowUserAction);
            }
            dWorkflowData.setUserActions(dWorkflowUserActions);
            dWorkflowDatas.add(dWorkflowData);
        }
        return dWorkflowDatas;
    }
}

