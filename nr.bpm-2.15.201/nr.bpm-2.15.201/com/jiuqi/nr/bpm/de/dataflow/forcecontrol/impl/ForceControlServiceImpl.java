/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 */
package com.jiuqi.nr.bpm.de.dataflow.forcecontrol.impl;

import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.IForceControlService;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean.BatchForceControlResult;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean.ForceControlInfo;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.bean.SingleForceControlResult;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.common.CalculateStateData;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.common.ForceControlContext;
import com.jiuqi.nr.bpm.de.dataflow.forcecontrol.common.InitCorporateRationData;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.step.BaseUpload;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchStepByStepResult;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckItem;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckResult;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.instance.bean.CorporateData;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ForceControlServiceImpl
extends BaseUpload
implements IForceControlService {
    @Autowired
    private IWorkFlowDimensionBuilder dimensionBuilder;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public StepByStepCheckResult reject(StepByOptParam stepByOptParam) {
        StepByStepCheckResult stepByStepCheckResult = new StepByStepCheckResult();
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
        List<CorporateData> corporateList = this.dimensionBuilder.getCorporateListOfDataScheme(stepByOptParam.getTaskKey());
        SingleForceControlResult singleForceControlResult = new SingleForceControlResult();
        ForceControlContext forceControlContext = new ForceControlContext(this.dimensionBuilder, stepByOptParam.getTaskKey());
        ForceControlInfo forceControlInfo = new ForceControlInfo();
        for (CorporateData corporateData : corporateList) {
            forceControlContext.initForceControlInfo(forceControlInfo, stepByOptParam, corporateData);
            InitCorporateRationData initCorporateRationData = new InitCorporateRationData(stepByOptParam.getPeriod(), corporateData.getKey(), stepByOptParam.getFormSchemeKey());
            initCorporateRationData.initData(forceControlInfo);
            CalculateStateData calculateStateData = new CalculateStateData(stepByOptParam.getFormSchemeKey(), dwMainDimName);
            calculateStateData.updateState(forceControlInfo, corporateData, singleForceControlResult);
        }
        List<StepByStepCheckItem> tips = singleForceControlResult.getTips();
        stepByStepCheckResult.setItems(tips);
        stepByStepCheckResult.setChild(false);
        stepByStepCheckResult.setActionState(UploadState.REJECTED.toString());
        stepByStepCheckResult.setActionStateTitle(this.driectActionState(stepByOptParam.getFormSchemeKey(), stepByOptParam.getActionId()));
        stepByStepCheckResult.setDirectActionStateTitle(this.driectActionState(stepByOptParam.getFormSchemeKey(), stepByOptParam.getActionId()));
        return stepByStepCheckResult;
    }

    @Override
    public BatchStepByStepResult batchReject(BatchStepByStepParam stepByOptParam) {
        BatchForceControlResult batchForceControlResult = new BatchForceControlResult();
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(stepByOptParam.getFormSchemeKey());
        WorkFlowType workFlowType = this.workflow.queryStartType(stepByOptParam.getFormSchemeKey());
        List<CorporateData> corporateList = this.dimensionBuilder.getCorporateListOfDataScheme(stepByOptParam.getTaskKey());
        ForceControlContext forceControlContext = new ForceControlContext(this.dimensionBuilder, stepByOptParam.getTaskKey());
        Map<String, ForceControlInfo> forceControlInfoMap = forceControlContext.batchInitForceControlInfo(stepByOptParam, workFlowType);
        for (CorporateData corporateData : corporateList) {
            List<ForceControlInfo> forceControlInfos = forceControlContext.initForceControlInfo(forceControlInfoMap, stepByOptParam, workFlowType, dwMainDimName, corporateData);
            InitCorporateRationData initCorporateRationData = new InitCorporateRationData(stepByOptParam.getPeriod(), corporateData.getKey(), stepByOptParam.getFormSchemeKey());
            initCorporateRationData.batchInitData(forceControlInfos);
            CalculateStateData calculateStateData = new CalculateStateData(stepByOptParam.getFormSchemeKey(), dwMainDimName);
            calculateStateData.batchUpdateState(forceControlInfos, corporateData, workFlowType, batchForceControlResult);
        }
        BatchStepByStepResult batchStepByStepResult = new BatchStepByStepResult();
        batchStepByStepResult.setOperateUnits(batchForceControlResult.getCanRejectUnitKeys());
        batchStepByStepResult.setOperateUnitAndForms(batchForceControlResult.getCanRejectUnitToForms());
        batchStepByStepResult.setOperateUnitAndGroups(batchForceControlResult.getCanRejectUnitToGroups());
        batchStepByStepResult.setNoOperateUnitMap(batchForceControlResult.getNoOperateUnitMap());
        batchStepByStepResult.setNoOperateGroupOrFormMap(batchForceControlResult.getNoOperateGroupOrFormMap());
        if (batchForceControlResult.getCanRejectUnitKeys() != null && batchForceControlResult.getCanRejectUnitKeys().size() > 0) {
            CompleteMsg completeMsg = this.executeAction(stepByOptParam, stepByOptParam.getPeriod(), batchForceControlResult.getCanRejectUnitKeys(), batchForceControlResult.getCanRejectUnitToForms(), batchForceControlResult.getCanRejectUnitToGroups());
            batchStepByStepResult.setCompleteMsg(completeMsg);
        }
        return batchStepByStepResult;
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
}

