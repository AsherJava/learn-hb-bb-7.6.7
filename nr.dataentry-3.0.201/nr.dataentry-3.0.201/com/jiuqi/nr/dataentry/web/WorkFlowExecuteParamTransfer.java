/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.web;

import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckResultGroupInfo;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class WorkFlowExecuteParamTransfer {
    @Resource
    private ActionMethod actionMethod;
    @Resource
    private IRunTimeViewController runtimeView;
    @Resource
    private AuditTypeDefineService auditTypeDefineService;
    @Resource
    private IDataentryFlowService dataentryFlowService;

    public void checkBatchExecuteTaskParam(BatchExecuteTaskParam param) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(param.getContext().getFormSchemeKey());
        if (formScheme != null && param.getUserActionParam() == null) {
            ActionParam actionParam = this.actionMethod.actionParam(formScheme.getKey(), param.getActionId());
            param.setUserActionParam(this.toDUserActionParam(formScheme, actionParam));
        }
    }

    public void checkBatchCheckResultGroupInfo(BatchCheckResultGroupInfo groupInfo) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(groupInfo.getContext().getFormSchemeKey());
        if (formScheme != null && groupInfo.getdUserActionParam() == null && groupInfo.isBatchUpload()) {
            String actionCode = this.actionMethod.isDefaultWorkflow(formScheme.getKey()) ? "act_upload" : "cus_upload";
            ActionParam actionParam = this.actionMethod.actionParam(formScheme.getKey(), actionCode);
            groupInfo.setdUserActionParam(this.toDUserActionParam(formScheme, actionParam));
        }
    }

    public void checkBatchCheckInfo(BatchCheckInfo batchCheckInfo) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(batchCheckInfo.getContext().getFormSchemeKey());
        if (formScheme != null && batchCheckInfo.getdUserActionParam() == null && batchCheckInfo.isWorkFlowCheck()) {
            String actionCode = this.actionMethod.isDefaultWorkflow(formScheme.getKey()) ? "act_upload" : "cus_upload";
            ActionParam actionParam = this.actionMethod.actionParam(formScheme.getKey(), actionCode);
            batchCheckInfo.setdUserActionParam(this.toDUserActionParam(formScheme, actionParam));
        }
    }

    public DUserActionParam toDUserActionParam(FormSchemeDefine formScheme, ActionParam actionParam) {
        DUserActionParam dUserActionParam = new DUserActionParam();
        if (actionParam != null) {
            WorkFlowType startType = this.dataentryFlowService.queryStartType(formScheme.getKey());
            dUserActionParam.setCheckFilter(actionParam.getCheckFilter());
            dUserActionParam.setOpenForceCommit(actionParam.isForceCommit());
            dUserActionParam.setNeedAutoCalculate(actionParam.isNeedAutoCalculate());
            dUserActionParam.setNeedAutoCheck(actionParam.isNeedAutoCheck());
            dUserActionParam.setNeedAutoNodeCheck(actionParam.isNodeCheck());
            dUserActionParam.setNeedOptDesc(actionParam.isNeedOptDesc());
            dUserActionParam.setNeedbuildVersion(actionParam.isNeedbuildVersion());
            dUserActionParam.setStepByStepUpload(actionParam.isStepByStep());
            dUserActionParam.setBatchOpt(actionParam.isBatchOpt());
            dUserActionParam.setSysMsgShow(actionParam.isSysMsgShow());
            dUserActionParam.setMailShow(actionParam.isMailShow());
            dUserActionParam.setWorkFlowType(startType);
            dUserActionParam.setSubmitAfterFormula(actionParam.isSubmitAfterFormula());
            dUserActionParam.setSubmitAfterFormulaValue(actionParam.getSubmitAfterFormulaValue());
            dUserActionParam.setCalculateFormulaValue(actionParam.getCalcuteFormulaValue());
            dUserActionParam.setCheckFormulaValue(actionParam.getCheckFormulaValue());
            dUserActionParam.setCheckCurrencyValue(actionParam.getCheckCurrencyValue());
            dUserActionParam.setNodeCheckCurrencyValue(actionParam.getNodeCheckCurrencyValue());
            dUserActionParam.setCheckCurrencyType(actionParam.getCheckCurrencyType());
            dUserActionParam.setNodeCheckCurrencyType(actionParam.getNodeCheckCurrencyType());
            dUserActionParam.setSingleRejectAction(actionParam.isSingleRejectAction());
            ArrayList<Integer> formualTypes = new ArrayList<Integer>();
            try {
                List auditTypes = this.auditTypeDefineService.queryAllAuditType();
                if (auditTypes == null || auditTypes.isEmpty()) {
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
                if (actionParam.getIgnoreErrorStatus() != null && actionParam.getIgnoreErrorStatus().contains(formualTypes.get(i))) continue;
                erroStatus.add((Integer)formualTypes.get(i));
            }
            dUserActionParam.setErroStatus(erroStatus);
            dUserActionParam.setNeedCommentErrorStatus(actionParam.getNeedCommentErrorStatus());
        }
        return dUserActionParam;
    }
}

