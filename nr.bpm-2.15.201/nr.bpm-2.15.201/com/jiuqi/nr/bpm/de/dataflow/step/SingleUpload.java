/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.step;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.dataflow.service.IDataentryQueryStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.step.BaseUpload;
import com.jiuqi.nr.bpm.de.dataflow.step.BuildStepTree;
import com.jiuqi.nr.bpm.de.dataflow.step.StepUtil;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckItem;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckResult;
import com.jiuqi.nr.bpm.de.dataflow.step.provide.StepQueryState;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityHelper;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityQueryManager;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SingleUpload
extends BaseUpload {
    private static final Logger logger = LoggerFactory.getLogger(SingleUpload.class);
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    DeEntityHelper deEntityHelper;
    @Autowired
    StepUtil stepUtil;
    @Autowired
    IFormConditionService formConditionService;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    IDataentryQueryStateService dataentryQueryStateService;
    @Autowired
    private IDataentryFlowService dataFlowService;
    @Autowired
    private WorkflowReportDimService workflowReportDimService;
    @Autowired
    private SingleFormRejectService singleFormRejectService;
    @Autowired
    private DeEntityQueryManager entityManager;
    @Resource
    public IEntityAuthorityService authService;
    @Resource
    public PeriodEngineService periodEngineService;

    public StepQueryState stepQueryState(String formSchemeKey, DimensionValueSet sourceDim, String period, WorkFlowType startType) {
        return new StepQueryState(formSchemeKey, sourceDim, period, startType, this.commonUtil, this.dimensionUtil, this.formConditionService, this.dataentryQueryStateService, this.workflowReportDimService, this.singleFormRejectService, this.entityManager);
    }

    public StepByStepCheckResult stepByOpt(StepByOptParam stepByOptParam) {
        StepByStepCheckResult equal = new StepByStepCheckResult();
        try {
            boolean stepByStepReport = this.stepUtil.stepByStepUpload(stepByOptParam.getFormSchemeKey(), stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam);
            boolean stepByStepBack = this.stepUtil.stepByStepBack(stepByOptParam.getFormSchemeKey(), stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam);
            boolean stepByStepBackAll = this.stepUtil.stepByStepBackAll(stepByOptParam.getFormSchemeKey(), stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam);
            BuildStepTree buildStepTree = new BuildStepTree(this.stepUtil, this.commonUtil, this.dimensionUtil, this.deEntityHelper);
            LinkedHashSet<String> formKeys = new LinkedHashSet<String>();
            formKeys.add(stepByOptParam.getFormKey());
            LinkedHashSet<String> groupKeys = new LinkedHashSet<String>();
            groupKeys.add(stepByOptParam.getGroupKey());
            LinkedHashSet<String> formOrGroupKey = buildStepTree.formOrGroupKey(stepByOptParam.getFormSchemeKey(), formKeys, groupKeys);
            if (stepByStepReport && stepByStepBack) {
                if (this.isUpload(stepByOptParam.getActionId())) {
                    equal = this.singleStep(stepByOptParam, formOrGroupKey, true, stepByStepBackAll);
                } else if (this.isBack(stepByOptParam.getActionId())) {
                    equal = this.singleStep(stepByOptParam, formOrGroupKey, false, stepByStepBackAll);
                }
            } else if (stepByStepReport) {
                if (this.isUpload(stepByOptParam.getActionId())) {
                    equal = this.singleStep(stepByOptParam, formOrGroupKey, true, stepByStepBackAll);
                }
            } else if (stepByStepBack && this.isBack(stepByOptParam.getActionId())) {
                equal = this.singleStep(stepByOptParam, formOrGroupKey, false, stepByStepBackAll);
            }
            equal.setDirectActionStateTitle(this.driectActionState(stepByOptParam.getFormSchemeKey(), stepByOptParam.getActionId()));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return equal;
    }

    public StepByStepCheckResult singleStep(StepByOptParam stepByOptParam, LinkedHashSet<String> formOrGroupKey, boolean child, boolean stepByStepBackAll) {
        StepByStepCheckResult result = new StepByStepCheckResult();
        WorkFlowType startType = this.stepUtil.startType(stepByOptParam.getFormSchemeKey());
        StepQueryState stepQueryState = this.stepQueryState(stepByOptParam.getFormSchemeKey(), stepByOptParam.getDimensionValue(), stepByOptParam.getDimensionValue().getValue("DATATIME").toString(), startType);
        result = child ? this.stepByStepReport(stepByOptParam, stepQueryState, formOrGroupKey) : (stepByStepBackAll ? this.stepByStepBackAll(stepByOptParam, stepQueryState, formOrGroupKey) : this.stepByStepBack(stepByOptParam, stepQueryState, formOrGroupKey));
        return result;
    }

    public StepByStepCheckResult stepByStepReport(StepByOptParam stepByOptParam, StepQueryState stepQueryState, LinkedHashSet<String> formOrGroupKey) {
        StepByStepCheckResult result = new StepByStepCheckResult();
        ArrayList<StepByStepCheckItem> items = new ArrayList<StepByStepCheckItem>();
        try {
            FormSchemeDefine formSchemeDefine = this.stepUtil.getFormSchemeDefine(stepByOptParam.getFormSchemeKey());
            ActionStateBean stateByActionCode = this.stepUtil.getStateByActionCode(stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam.getFormSchemeKey());
            DimensionValueSet dimensionValue = stepByOptParam.getDimensionValue();
            String mainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
            String period = (String)dimensionValue.getValue("DATATIME");
            List<IEntityRow> childRows = this.deEntityHelper.getDirectChildrenData(dimensionValue, stepByOptParam.getFormSchemeKey());
            Map<IEntityRow, ActionStateBean> queryDirectState = stepQueryState.queryDirectState(childRows, formOrGroupKey);
            for (Map.Entry<IEntityRow, ActionStateBean> actionState : queryDirectState.entrySet()) {
                IEntityRow entityRow = actionState.getKey();
                ActionStateBean state = actionState.getValue();
                if (state == null || state.getCode() == null) continue;
                DimensionValueSet dimension = new DimensionValueSet();
                dimension.setValue("DATATIME", (Object)period);
                dimension.setValue(mainDimName, (Object)entityRow.getEntityKeyData());
                StepByStepCheckItem stepUpload = this.stepUpload(formSchemeDefine, dimension, state, entityRow, (String)formOrGroupKey.iterator().next());
                if (stepUpload == null) continue;
                items.add(stepUpload);
            }
            result.setItems(items);
            result.setChild(true);
            result.setActionState(stateByActionCode.getCode());
            result.setActionStateTitle(stateByActionCode.getTitile());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public StepByStepCheckResult stepByStepBack(StepByOptParam stepByOptParam, StepQueryState stepQueryState, LinkedHashSet<String> formOrGroupKey) {
        StepByStepCheckResult result = new StepByStepCheckResult();
        ArrayList<StepByStepCheckItem> items = new ArrayList<StepByStepCheckItem>();
        try {
            FormSchemeDefine formSchemeDefine = this.stepUtil.getFormSchemeDefine(stepByOptParam.getFormSchemeKey());
            ActionStateBean stateByActionCode = this.stepUtil.getStateByActionCode(stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam.getFormSchemeKey());
            DimensionValueSet dimensionValue = stepByOptParam.getDimensionValue();
            String mainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
            String period = (String)dimensionValue.getValue("DATATIME");
            String parentId = this.deEntityHelper.getParent(dimensionValue, formSchemeDefine, AuthorityType.None);
            if (parentId != null) {
                DimensionValueSet parentDim = new DimensionValueSet();
                parentDim.setValue("DATATIME", (Object)period);
                parentDim.setValue(mainDimName, (Object)parentId);
                List<IEntityRow> parent = this.deEntityHelper.getEntityRow(formSchemeDefine.getKey(), parentDim, AuthorityType.None);
                Map<IEntityRow, ActionStateBean> queryParentState = stepQueryState.queryParentState(parent, formOrGroupKey);
                for (Map.Entry<IEntityRow, ActionStateBean> actionState : queryParentState.entrySet()) {
                    IEntityRow entityRow = actionState.getKey();
                    ActionStateBean state = actionState.getValue();
                    if (state == null || state.getCode() == null) continue;
                    DimensionValueSet dimension = new DimensionValueSet();
                    dimension.setValue("DATATIME", (Object)period);
                    dimension.setValue(mainDimName, (Object)entityRow.getEntityKeyData());
                    StepByStepCheckItem stepUpload = this.stepUpload(formSchemeDefine, dimension, state, entityRow, (String)formOrGroupKey.iterator().next());
                    if (stepUpload == null) continue;
                    items.add(stepUpload);
                }
            }
            result.setItems(items);
            result.setChild(false);
            result.setActionState(stateByActionCode.getCode());
            result.setActionStateTitle(stateByActionCode.getTitile());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    public StepByStepCheckResult stepByStepBackAllaa(StepByOptParam stepByOptParam, StepQueryState stepQueryState, LinkedHashSet<String> formOrGroupKey) {
        StepByStepCheckResult result = new StepByStepCheckResult();
        ArrayList<StepByStepCheckItem> items = new ArrayList<StepByStepCheckItem>();
        try {
            FormSchemeDefine formSchemeDefine = this.stepUtil.getFormSchemeDefine(stepByOptParam.getFormSchemeKey());
            ActionStateBean stateByActionCode = this.stepUtil.getStateByActionCode(stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam.getFormSchemeKey());
            DimensionValueSet dimensionValue = stepByOptParam.getDimensionValue();
            String mainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
            String period = (String)dimensionValue.getValue("DATATIME");
            String[] parentId = this.deEntityHelper.getAllParent(dimensionValue, formSchemeDefine);
            if (parentId != null && parentId.length > 0) {
                ArrayList parentList = new ArrayList(parentId.length);
                Collections.addAll(parentList, parentId);
                DimensionValueSet parentDim = new DimensionValueSet();
                parentDim.setValue("DATATIME", (Object)period);
                parentDim.setValue(mainDimName, parentList);
                List<IEntityRow> parent = this.deEntityHelper.getEntityRow(formSchemeDefine.getKey(), parentDim, AuthorityType.None);
                Map<IEntityRow, ActionStateBean> queryParentState = stepQueryState.queryParentState(parent, formOrGroupKey);
                for (Map.Entry<IEntityRow, ActionStateBean> actionState : queryParentState.entrySet()) {
                    String formOrGroupKeyTemp;
                    ExecuteParam executeParam;
                    ActionStateBean state = actionState.getValue();
                    IEntityRow entityRow = actionState.getKey();
                    if (state == null || state.getCode() == null) continue;
                    DimensionValueSet dimension = new DimensionValueSet();
                    dimension.setValue("DATATIME", (Object)period);
                    dimension.setValue(mainDimName, (Object)entityRow.getEntityKeyData());
                    StepByStepCheckItem stepUpload = this.stepUpload(formSchemeDefine, dimension, state, entityRow, (String)formOrGroupKey.iterator().next());
                    if (stepUpload == null) continue;
                    if (UploadState.UPLOADED.toString().equals(state.getCode())) {
                        executeParam = new ExecuteParam();
                        executeParam.setFormSchemeKey(stepByOptParam.getFormSchemeKey());
                        executeParam.setDimSet(dimension);
                        formOrGroupKeyTemp = formOrGroupKey == null || formOrGroupKey.size() == 0 ? null : (String)formOrGroupKey.iterator().next();
                        executeParam.setFormKey(formOrGroupKeyTemp);
                        executeParam.setGroupKey(formOrGroupKeyTemp);
                        executeParam.setActionId(stepByOptParam.getActionId());
                        executeParam.setPeriod(period);
                        executeParam.setNodeId(state.getTaskKey());
                        executeParam.setTaskId(state.getTaskKey());
                        this.dataFlowService.executeTask(executeParam);
                    }
                    if (!UploadState.CONFIRMED.toString().equals(state.getCode())) continue;
                    executeParam = new ExecuteParam();
                    executeParam.setFormSchemeKey(stepByOptParam.getFormSchemeKey());
                    executeParam.setDimSet(dimension);
                    formOrGroupKeyTemp = formOrGroupKey == null || formOrGroupKey.size() == 0 ? null : (String)formOrGroupKey.iterator().next();
                    executeParam.setFormKey(formOrGroupKeyTemp);
                    executeParam.setGroupKey(formOrGroupKeyTemp);
                    executeParam.setActionId(stepByOptParam.getActionId());
                    executeParam.setPeriod(period);
                    executeParam.setNodeId(state.getTaskKey());
                    executeParam.setTaskId(state.getTaskKey());
                    this.dataFlowService.executeTask(executeParam);
                }
            }
            result.setItems(items);
            result.setChild(true);
            result.setActionState(stateByActionCode.getCode());
            result.setActionStateTitle(stateByActionCode.getTitile());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    private StepByStepCheckItem stepUpload(FormSchemeDefine formScheme, DimensionValueSet dimension, ActionStateBean actionStateBean, IEntityRow stepTree, String resourceId) {
        StepByStepCheckItem item = null;
        boolean bindProcess = this.stepUtil.bindProcess(formScheme.getKey(), dimension, resourceId);
        if (bindProcess) {
            item = new StepByStepCheckItem();
            item.setUnitId(stepTree.getEntityKeyData());
            item.setUnitCode(stepTree.getCode());
            item.setUnitTitle(stepTree.getTitle());
            item.setWorkflowState(actionStateBean == null ? "\u672a\u4e0a\u62a5" : actionStateBean.getTitile());
        }
        return item;
    }

    private String driectActionState(String formSchemeKey, String actionId) {
        String directActionStateTitle = "";
        FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
        if ("act_reject".equals(actionId) || "cus_reject".equals(actionId)) {
            directActionStateTitle = "\u9000\u56de";
        }
        if ("act_submit".equals(actionId) || "cus_submit".equals(actionId) || "act_upload".equals(actionId) || "cus_upload".equals(actionId) || "act_confirm".equals(actionId) || "cus_confirm".equals(actionId)) {
            String stepReportType;
            directActionStateTitle = this.commonUtil.isDefaultWorkflow(formSchemeKey) ? ("1".equals(stepReportType = formScheme.getFlowsSetting().getStepReportType()) ? "\u4e0a\u62a5" : "\u786e\u8ba4") : "\u4e0a\u62a5";
        }
        return directActionStateTitle;
    }

    public StepByStepCheckResult stepByStepBackAll(StepByOptParam stepByOptParam, StepQueryState stepQueryState, LinkedHashSet<String> formOrGroupKey) {
        StepByStepCheckResult result = new StepByStepCheckResult();
        ArrayList<StepByStepCheckItem> items = new ArrayList<StepByStepCheckItem>();
        try {
            EntityViewDefine dwEntityView = this.dimensionUtil.getDwEntityView(stepByOptParam.getFormSchemeKey());
            FormSchemeDefine formSchemeDefine = this.stepUtil.getFormSchemeDefine(stepByOptParam.getFormSchemeKey());
            ActionStateBean stateByActionCode = this.stepUtil.getStateByActionCode(stepByOptParam.getNodeId(), stepByOptParam.getActionId(), stepByOptParam.getFormSchemeKey());
            DimensionValueSet dimensionValue = stepByOptParam.getDimensionValue();
            String mainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
            String period = (String)dimensionValue.getValue("DATATIME");
            String[] parentId = this.deEntityHelper.getAllParent(dimensionValue, formSchemeDefine);
            if (parentId != null && parentId.length > 0) {
                ArrayList parentList = new ArrayList(parentId.length);
                Collections.addAll(parentList, parentId);
                DimensionValueSet parentDim = new DimensionValueSet();
                parentDim.setValue("DATATIME", (Object)period);
                parentDim.setValue(mainDimName, parentList);
                List<IEntityRow> parents = this.deEntityHelper.getEntityRow(formSchemeDefine.getKey(), parentDim, AuthorityType.None);
                Map<IEntityRow, ActionStateBean> queryParentState = stepQueryState.queryParentState(parents, formOrGroupKey);
                boolean canReturn = true;
                for (Map.Entry<IEntityRow, ActionStateBean> actionState : queryParentState.entrySet()) {
                    ActionStateBean state = actionState.getValue();
                    IEntityRow entityRow = actionState.getKey();
                    if (state == null || state.getCode() == null) continue;
                    DimensionValueSet dimension = new DimensionValueSet();
                    dimension.setValue("DATATIME", (Object)period);
                    dimension.setValue(mainDimName, (Object)entityRow.getEntityKeyData());
                    StepByStepCheckItem stepUpload = this.stepUpload(formSchemeDefine, dimension, state, entityRow, (String)formOrGroupKey.iterator().next());
                    boolean hasUnitAuditOperation = this.hasUnitAuditOperation(dwEntityView.getEntityId(), entityRow.getEntityKeyData(), formSchemeDefine.getDateTime(), period);
                    if (hasUnitAuditOperation && canReturn) {
                        String formOrGroupKeyTemp;
                        ExecuteParam executeParam;
                        if (stepUpload == null) continue;
                        if (UploadState.UPLOADED.toString().equals(state.getCode())) {
                            executeParam = new ExecuteParam();
                            executeParam.setFormSchemeKey(stepByOptParam.getFormSchemeKey());
                            executeParam.setDimSet(dimension);
                            formOrGroupKeyTemp = formOrGroupKey == null || formOrGroupKey.size() == 0 ? null : (String)formOrGroupKey.iterator().next();
                            executeParam.setFormKey(formOrGroupKeyTemp);
                            executeParam.setGroupKey(formOrGroupKeyTemp);
                            executeParam.setActionId(stepByOptParam.getActionId());
                            executeParam.setPeriod(period);
                            executeParam.setNodeId(state.getTaskKey());
                            executeParam.setTaskId(state.getTaskKey());
                            this.dataFlowService.executeTask(executeParam);
                        }
                        if (!UploadState.CONFIRMED.toString().equals(state.getCode())) continue;
                        executeParam = new ExecuteParam();
                        executeParam.setFormSchemeKey(stepByOptParam.getFormSchemeKey());
                        executeParam.setDimSet(dimension);
                        formOrGroupKeyTemp = formOrGroupKey == null || formOrGroupKey.size() == 0 ? null : (String)formOrGroupKey.iterator().next();
                        executeParam.setFormKey(formOrGroupKeyTemp);
                        executeParam.setGroupKey(formOrGroupKeyTemp);
                        executeParam.setActionId(stepByOptParam.getActionId());
                        executeParam.setPeriod(period);
                        executeParam.setNodeId(state.getTaskKey());
                        executeParam.setTaskId(state.getTaskKey());
                        this.dataFlowService.executeTask(executeParam);
                        continue;
                    }
                    if (stepUpload == null) continue;
                    items.add(stepUpload);
                    canReturn = false;
                }
            }
            result.setItems(items);
            result.setChild(false);
            result.setActionState(stateByActionCode.getCode());
            result.setActionStateTitle(stateByActionCode.getTitile());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
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

