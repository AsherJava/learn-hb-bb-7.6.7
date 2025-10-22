/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.bpm.dataflow.serviceImpl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.UploadAllFormSumInfo;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.common.UploadSumNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.impl.ReportState.BatchUploadStateServiceImplDianxin;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.utils.ActionStateEnum;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryUploadStateServiceImpl
implements IQueryUploadStateService {
    private static final Logger logger = LoggerFactory.getLogger(QueryUploadStateServiceImpl.class);
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private IEntityMetaService entityMetaService;

    @Override
    public ActionStateBean queryActionState(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        return this.queryActionState(formSchemeKey, dimensionValueSet, null, null);
    }

    @Override
    public ActionStateBean queryActionState(String formSchemeKey, DimensionValueSet dimensionValueSet, String formKey) {
        return this.queryActionState(formSchemeKey, dimensionValueSet, formKey, null);
    }

    @Override
    public ActionStateBean queryActionState(String formSchemeKey, DimensionValueSet dimensionValueSet, String formKey, String formGroupKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet fliterDimension = this.fliterDimension(dimensionValueSet, formScheme);
        UploadStateNew uploadStateNew = this.batchQueryUploadStateService.queryUploadStateNew(fliterDimension, formKey, formGroupKey, formScheme);
        if (uploadStateNew != null && uploadStateNew.getTaskId() != null) {
            ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
            return actionStateBean;
        }
        WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
        String dbFormGroupKey = this.getDbFormGroupKey(startType, formKey, formGroupKey, formScheme);
        ActionStateBean actionStateByIsSubmit = this.getActionStateByIsSubmit(formSchemeKey, dimensionValueSet, dbFormGroupKey, startType);
        return actionStateByIsSubmit;
    }

    @Override
    public Map<DimensionValueSet, ActionStateBean> queryUploadStateMap(String formSchemeKey, DimensionValueSet dimensionValueSet, String formKey, String formGroupKey) {
        HashMap<DimensionValueSet, ActionStateBean> uploadStateMap = new HashMap<DimensionValueSet, ActionStateBean>();
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
            IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
            WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
            DimensionValueSet buildDimensionValueSet = this.buildDimensionValueSet(dimensionValueSet, formScheme);
            Map<Object, Object> uploadStates = new HashMap();
            boolean hasStatisticalNode = this.workflow.hasStatisticalNode(formSchemeKey);
            if (hasStatisticalNode) {
                BatchUploadStateServiceImplDianxin batchUploadStateServiceImplDianxin = new BatchUploadStateServiceImplDianxin();
                uploadStates = batchUploadStateServiceImplDianxin.queryUploadStates(buildDimensionValueSet, formKey, formGroupKey, formScheme);
            } else {
                uploadStates = this.batchQueryUploadStateService.queryUploadStates(buildDimensionValueSet, formKey, formGroupKey, formScheme);
            }
            List<DimensionValueSet> buildDimSets = this.dimensionUtil.buildDimensionValueSetsNofliter(buildDimensionValueSet, formSchemeKey);
            if (buildDimSets.size() > 0) {
                for (DimensionValueSet dimensionvalue : buildDimSets) {
                    ActionStateBean actionStateBean;
                    if (buildDimSets.size() <= 0) continue;
                    dimensionvalue.setValue("PROCESSKEY", (Object)this.getProcessKey(formScheme.getKey()));
                    if (WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) {
                        dimensionvalue.setValue("FORMID", (Object)"11111111-1111-1111-1111-111111111111");
                    }
                    if ((actionStateBean = (ActionStateBean)uploadStates.get(dimensionvalue)) != null && actionStateBean.getCode() != null) {
                        uploadStateMap.put(this.dimensionUtil.fliterProcessKey(dimensionvalue, formSchemeKey), actionStateBean);
                        continue;
                    }
                    String dbFormGroupKey = this.getDbFormGroupKey(startType, formKey, formGroupKey, formScheme);
                    ActionStateBean actionState = this.getActionStateByIsSubmit(formSchemeKey, dimensionValueSet, dbFormGroupKey, startType);
                    if (actionStateBean != null) {
                        uploadStateMap.put(this.dimensionUtil.fliterProcessKey(dimensionvalue, formSchemeKey, dwEntityModel), actionState);
                        continue;
                    }
                    uploadStateMap.put(this.dimensionUtil.fliterProcessKey(dimensionvalue, formSchemeKey, dwEntityModel), actionState);
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return uploadStateMap;
    }

    private DimensionValueSet buildDimensionValueSet(DimensionValueSet dimensionValueSet, FormSchemeDefine formScheme) {
        ArrayList<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>();
        DimensionValueSet fliterDimension = this.fliterDimension(dimensionValueSet, formScheme);
        Map dimensionValueMap = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)fliterDimension);
        DimensionCollection dimensionValueSets = this.workFlowDimensionBuilder.buildDimensionCollection(formScheme.getTaskKey(), dimensionValueMap);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            dimensionValueSetList.add(dimensionCombination.toDimensionValueSet());
        }
        return this.dimensionUtil.mergeDimensionValueSet(dimensionValueSetList, formScheme.getKey());
    }

    public ActionStateBean getActionStateByIsSubmit(String formSchemeKey, DimensionValueSet dims, String formKey, WorkFlowType startType) {
        ActionStateBean actionState = new ActionStateBean();
        try {
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
            if (formScheme != null) {
                boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
                if (defaultWorkflow) {
                    actionState = this.queryDefaultState(formSchemeKey, formKey, startType);
                } else {
                    WorkflowSettingDefine workflowDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                    if (workflowDefine != null && workflowDefine.getId() != null) {
                        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefine.getWorkflowId(), 1);
                        List<WorkFlowLine> workflowLinesByLinkid = this.customWorkFolwService.getWorkflowLinesByLinkid(workFlowDefine.getLinkid());
                        for (WorkFlowLine workFlowLine : workflowLinesByLinkid) {
                            Iterator<WorkFlowAction> iterator;
                            List<WorkFlowAction> workFlowAction;
                            String beforeNodeID = workFlowLine.getBeforeNodeID();
                            if (!beforeNodeID.startsWith("StartEvent_") || (workFlowAction = this.customWorkFolwService.getRunWorkFlowNodeAction(workFlowLine.getAfterNodeID(), workFlowDefine.getLinkid())) == null || !(iterator = workFlowAction.iterator()).hasNext()) continue;
                            WorkFlowAction action = iterator.next();
                            ActionStateBean actionStateBean = new ActionStateBean();
                            if ("cus_submit".equals(action.getActionCode())) {
                                actionStateBean.setCode(UploadState.ORIGINAL_SUBMIT.toString());
                            } else if ("cus_upload".equals(action.getActionCode())) {
                                actionStateBean.setCode(UploadState.ORIGINAL_UPLOAD.toString());
                            }
                            actionStateBean.setTitile(formSchemeKey, "\u672a" + action.getActionTitle());
                            return actionStateBean;
                        }
                    }
                }
            } else {
                actionState.setCode(UploadState.ORIGINAL.toString());
                actionState.setTitile(formSchemeKey, "\u672a\u77e5");
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionState;
    }

    public ActionStateBean queryDefaultState(String formSchemeKey, String fromKey, WorkFlowType startType) {
        ActionStateBean actionStateBean = new ActionStateBean();
        try {
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
            boolean unitSubmitForCensorship = formScheme.getFlowsSetting().isUnitSubmitForCensorship();
            if (unitSubmitForCensorship) {
                if ((WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) && "11111111-1111-1111-1111-111111111111".equals(fromKey)) {
                    actionStateBean.setCode(UploadState.PART_START.toString());
                    actionStateBean.setTitile(formSchemeKey, ActionStateEnum.PART_START.getStateName(startType));
                } else {
                    actionStateBean.setCode(UploadState.ORIGINAL_SUBMIT.toString());
                    actionStateBean.setTitile(formSchemeKey, ActionStateEnum.ORIGINAL_SUBMIT.getStateName());
                }
            } else {
                actionStateBean.setCode(UploadState.ORIGINAL_UPLOAD.toString());
                actionStateBean.setTitile(formSchemeKey, ActionStateEnum.ORIGINAL_UPLOAD.getStateName());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionStateBean;
    }

    public String getProcessKey(String formScheme) {
        WorkflowSettingDefine workflow;
        String result = "00000000-0000-0000-0000-000000000000";
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formScheme);
        if (!defaultWorkflow && (workflow = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formScheme)) != null) {
            result = workflow.getId();
        }
        return result;
    }

    private DimensionValueSet fliterDimension(DimensionValueSet dimensionValueSet, FormSchemeDefine formScheme) {
        return this.dimensionUtil.fliterDimensionValueSet(dimensionValueSet, formScheme);
    }

    @Override
    public UploadStateNew queryUploadState(String formSchemeKey, DimensionValueSet dimensionValueSet, String formKey, String formGroupKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet fliterDimension = this.fliterDimension(dimensionValueSet, formScheme);
        UploadStateNew uploadStateNew = new UploadStateNew();
        boolean hasStatisticalNode = this.workflow.hasStatisticalNode(formSchemeKey);
        if (hasStatisticalNode) {
            BatchUploadStateServiceImplDianxin batchUploadStateServiceImplDianxin = new BatchUploadStateServiceImplDianxin();
            uploadStateNew = batchUploadStateServiceImplDianxin.queryUploadStateNew(fliterDimension, formKey, formGroupKey, formScheme);
        } else {
            uploadStateNew = this.batchQueryUploadStateService.queryUploadStateNew(fliterDimension, formKey, formGroupKey, formScheme);
        }
        if (uploadStateNew != null && uploadStateNew.getTaskId() != null) {
            return uploadStateNew;
        }
        WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
        String dbFormGroupKey = this.getDbFormGroupKey(startType, formKey, formGroupKey, formScheme);
        uploadStateNew.setActionStateBean(this.getActionStateByIsSubmit(formSchemeKey, dimensionValueSet, dbFormGroupKey, startType));
        return uploadStateNew;
    }

    private String getDbFormGroupKey(WorkFlowType startType, String formKey, String groupKey, FormSchemeDefine formScheme) {
        if (WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) {
            return startType == WorkFlowType.FORM ? formKey : (startType == WorkFlowType.GROUP ? groupKey : this.nrParameterUtils.getDefaultFormId(formScheme.getKey()));
        }
        return this.nrParameterUtils.getDefaultFormId(formScheme.getKey());
    }

    @Override
    public UploadStateNew queryUploadState(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        return this.queryUploadState(formSchemeKey, dimensionValueSet, null, null);
    }

    @Override
    public UploadStateNew queryUploadState(String formSchemeKey, DimensionValueSet dimensionValueSet, String formKey) {
        return this.queryUploadState(formSchemeKey, dimensionValueSet, formKey, formKey);
    }

    @Override
    public List<UploadStateNew> queryUploadStates(String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> formGroupKeys) {
        ArrayList<UploadStateNew> uploadStates = new ArrayList<UploadStateNew>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        boolean corporate = this.workFlowDimensionBuilder.isCorporate(taskDefine);
        List<Object> dimensionValueSetList = new ArrayList();
        if (corporate) {
            dimensionValueSetList = this.mergeDimensionValueSet(dimensionValueSet, formScheme);
        } else {
            DimensionValueSet fliterDimension = this.fliterDimension(dimensionValueSet, formScheme);
            dimensionValueSetList.add(fliterDimension);
        }
        for (DimensionValueSet dimensionValueSet2 : dimensionValueSetList) {
            List<UploadStateNew> uploadStateNews = this.batchQueryUploadStateService.queryUploadStateNew(formScheme, dimensionValueSet2, formKeys, formGroupKeys);
            for (UploadStateNew uploadStateNew : uploadStateNews) {
                DimensionValueSet entities = uploadStateNew.getEntities();
                entities.clearValue("PROCESSKEY");
                uploadStateNew.setEntities(entities);
                uploadStates.add(uploadStateNew);
            }
        }
        return uploadStates;
    }

    private List<DimensionValueSet> mergeDimensionValueSet(DimensionValueSet dimensionValueSet, FormSchemeDefine formScheme) {
        ArrayList<DimensionValueSet> dimensionValueSets = new ArrayList<DimensionValueSet>();
        List<Object> periods = new ArrayList();
        Object value = dimensionValueSet.getValue("DATATIME");
        if (value != null) {
            if (value instanceof List) {
                periods = (List)value;
            } else if (value instanceof String) {
                String periodStr = value.toString();
                periods = Arrays.asList(periodStr.split(";"));
            }
            for (String period : periods) {
                dimensionValueSet.setValue("DATATIME", (Object)period);
                DimensionValueSet fliterDimension = this.fliterDimension(dimensionValueSet, formScheme);
                dimensionValueSets.add(fliterDimension);
            }
        }
        return dimensionValueSets;
    }

    @Override
    public void deleteUploadRecord(BusinessKey bussinessKey) {
        this.batchQueryUploadStateService.deleteUploadRecord(bussinessKey);
    }

    @Override
    public void deleteUploadState(BusinessKey bussinessKey) {
        this.batchQueryUploadStateService.deleteUploadState(bussinessKey);
    }

    @Override
    public List<UploadRecordNew> queryUploadHistoryStates(String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> formKeys, List<String> groupKeys) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet fliterDimension = this.fliterDimension(dimensionValueSet, formScheme);
        return this.batchQueryUploadStateService.queryHisUploadStates(formScheme, fliterDimension, formKeys, groupKeys, null);
    }

    @Override
    public List<UploadRecordNew> queryUploadHistoryStates(String formSchemeKey, DimensionValueSet dimensionValueSet, String formKey, String groupKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet fliterDimension = this.fliterDimension(dimensionValueSet, formScheme);
        return this.batchQueryUploadStateService.queryHisUploadStates(formScheme, fliterDimension, formKey, groupKey, null);
    }

    @Override
    public List<UploadRecordNew> queryUploadHistoryStates(String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> formKeys) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet fliterDimension = this.fliterDimension(dimensionValueSet, formScheme);
        return this.batchQueryUploadStateService.queryUploadHisState(formScheme, fliterDimension, formKeys);
    }

    @Override
    public List<UploadRecordNew> queryUploadHistoryStates(BusinessKey businessKey) {
        String formSchemeKey = businessKey.getFormSchemeKey();
        String formKey = businessKey.getFormKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
        return this.batchQueryUploadStateService.queryUploadActionsNew(buildDimension, formKey, formScheme);
    }

    @Override
    public List<UploadRecordNew> queryUploadHistoryStates(BusinessKey businessKey, List<String> formKeys, String nodeId) {
        String formSchemeKey = businessKey.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
        return this.batchQueryUploadStateService.queryHisUploadStates(formScheme, buildDimension, formKeys, formKeys, nodeId);
    }

    @Override
    public List<UploadStateNew> queryUploadStates(String formSchemeKey) {
        ArrayList<UploadStateNew> uploadStates = new ArrayList<UploadStateNew>();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        List<UploadStateNew> uploadStateNews = this.batchQueryUploadStateService.queryUploadStateNew(formScheme);
        for (UploadStateNew uploadStateNew : uploadStateNews) {
            DimensionValueSet entities = uploadStateNew.getEntities();
            entities.clearValue("PROCESSKEY");
            entities.clearValue("FORMID");
            uploadStateNew.setEntities(entities);
            uploadStates.add(uploadStateNew);
        }
        return uploadStates;
    }

    @Override
    public List<UploadStateNew> queryUploadStates(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        return this.queryUploadStates(formSchemeKey, dimensionValueSet, null, null);
    }

    @Override
    public ActionStateBean queryActionState(BusinessKey businessKey) {
        String formSchemeKey = businessKey.getFormSchemeKey();
        String formKey = businessKey.getFormKey();
        DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
        return this.queryActionState(formSchemeKey, buildDimension, formKey, formKey);
    }

    @Override
    public UploadRecordNew queryLatestUploadAction(String formSchemeKey, DimensionValueSet dimensionValueSet, String formKey, String groupKey, boolean getUser) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        return this.batchQueryUploadStateService.queryLatestUploadAction(dimensionValueSet, formKey, groupKey, formScheme, getUser);
    }

    @Override
    public UploadRecordNew queryLatestUploadAction(BusinessKey businessKey) {
        String formSchemeKey = businessKey.getFormSchemeKey();
        String formKey = businessKey.getFormKey();
        DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        return this.batchQueryUploadStateService.queryLatestUploadAction(buildDimension, formKey, formKey, formScheme);
    }

    @Override
    public UploadRecordNew queryLatestUploadAction(String formSchemeKey, DimensionValueSet dimensionValueSet, String formKey, String groupKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        return this.batchQueryUploadStateService.queryLatestUploadAction(dimensionValueSet, formKey, formKey, formScheme);
    }

    @Override
    public List<UploadAllFormSumInfo> queryAllFormState(DimensionValueSet dimensionValueSet, String formKeys, FormSchemeDefine formScheme, List<String> entityIds, WorkFlowType queryStartType, Map<String, UploadAllFormSumInfo> formToSum) {
        DimensionValueSet fliterDimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(dimensionValueSet, formScheme);
        return this.batchQueryUploadStateService.queryAllFormState(fliterDimensionValueSet, formKeys, formScheme, entityIds, queryStartType, formToSum);
    }

    @Override
    public List<UploadStateNew> queryUploadDelay(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet, String formKey, Calendar abortTime) {
        DimensionValueSet fliterDimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(dimensionValueSet, formScheme);
        return this.batchQueryUploadStateService.queryUploadDelay(formScheme, fliterDimensionValueSet, formKey, abortTime);
    }

    @Override
    public UploadSumNew queryUploadSumNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, boolean flowsType, String entitySelf, String mainDim, EntityViewDefine unitView, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, Calendar abortTime) throws Exception {
        DimensionValueSet fliterDimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(dimensionValueSet, formScheme);
        return this.batchQueryUploadStateService.queryUploadSumNew(fliterDimensionValueSet, formKey, formScheme, flowsType, entitySelf, mainDim, unitView, iEntityTable, leafEntity, filterDiffUnit, onlyDirectChild, abortTime);
    }

    @Override
    public UploadSumNew queryVirtualUploadSumNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, boolean flowsType, String entitySelf, String mainDim, EntityViewDefine unitView, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, Calendar abortTime) throws Exception {
        DimensionValueSet fliterDimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(dimensionValueSet, formScheme);
        return this.batchQueryUploadStateService.queryVirtualUploadSumNew(fliterDimensionValueSet, formKey, formScheme, flowsType, entitySelf, mainDim, unitView, iEntityTable, leafEntity, filterDiffUnit, onlyDirectChild, abortTime);
    }

    @Override
    public UploadSumNew queryUploadSum(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, boolean flowsType, String entitySelf, String mainDim, EntityViewDefine unitView, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, Calendar abortTime, Map<String, List<String>> statisticalStates) throws Exception {
        DimensionValueSet fliterDimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(dimensionValueSet, formScheme);
        return this.batchQueryUploadStateService.queryUploadSum(fliterDimensionValueSet, formKey, formScheme, flowsType, entitySelf, mainDim, unitView, iEntityTable, leafEntity, filterDiffUnit, onlyDirectChild, abortTime, statisticalStates);
    }

    @Override
    public UploadSumNew queryVirtualUploadSumNew(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme, boolean flowsType, String entitySelf, String mainDim, EntityViewDefine unitView, IEntityTable iEntityTable, boolean leafEntity, boolean filterDiffUnit, boolean onlyDirectChild, Calendar abortTime, Map<String, List<String>> statisticalStates) throws Exception {
        DimensionValueSet fliterDimensionValueSet = this.dimensionUtil.fliterDimensionValueSet(dimensionValueSet, formScheme);
        BatchUploadStateServiceImplDianxin batchUploadStateServiceImplDianxin = new BatchUploadStateServiceImplDianxin();
        return batchUploadStateServiceImplDianxin.queryVirtualUploadSumNew(fliterDimensionValueSet, formKey, formScheme, flowsType, entitySelf, mainDim, unitView, iEntityTable, leafEntity, filterDiffUnit, onlyDirectChild, abortTime, statisticalStates);
    }
}

