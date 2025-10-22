/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DeWorkflowBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.SingleRejectFormActions
 *  com.jiuqi.nr.data.access.param.AccessItem
 *  com.jiuqi.nr.data.access.service.IDataAccessExtraResultService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.time.setting.de.DeSetTimeProvide
 *  javax.annotation.Nullable
 */
package com.jiuqi.nr.dataentry.readwrite.impl.access;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.DeWorkflowBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.SingleRejectFormActions;
import com.jiuqi.nr.data.access.param.AccessItem;
import com.jiuqi.nr.data.access.service.IDataAccessExtraResultService;
import com.jiuqi.nr.dataentry.bean.CommitFlowResult;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.bean.DWorkflowData;
import com.jiuqi.nr.dataentry.bean.DWorkflowUserAction;
import com.jiuqi.nr.dataentry.bean.WorkflowParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.time.setting.de.DeSetTimeProvide;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkFlowExtendAccessServiceImpl
implements IDataAccessExtraResultService<CommitFlowResult> {
    @Autowired
    private IDataentryFlowService dataentryFlowService;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    public DeSetTimeProvide deSetTimeProvide;
    @Autowired
    public SingleRejectFormActions singleRejectFormActions;
    private final Logger logger = LoggerFactory.getLogger(WorkFlowExtendAccessServiceImpl.class);

    public String name() {
        return "upload";
    }

    @Nullable
    public Optional<CommitFlowResult> getExtraResult(AccessItem param, String formSchemeKey, DimensionCombination masterKey, String formKey) {
        CommitFlowResult result = new CommitFlowResult();
        WorkflowParam workflowParam = new WorkflowParam();
        Map map = (Map)param.getParams();
        workflowParam.setFormKeys((List)map.get("formKeys"));
        workflowParam.setGroupKeys((List)map.get("groupKeys"));
        JtableContext context = (JtableContext)map.get("context");
        workflowParam.setContext(context);
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
            this.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            throw new RuntimeException("\u540e\u53f0\u4e0a\u62a5\u6d41\u7a0b\u6743\u9650\u5224\u65ad\u62a5\u9519\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458");
        }
        return Optional.of(result);
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
                dUserActionParam.setReturnTypeEnable(workflowAction.getActionParam().isReturnTypeEnable());
                dUserActionParam.setNeedOptDesc(workflowAction.getActionParam().isNeedOptDesc());
                dUserActionParam.setReturnExplain(workflowAction.getActionParam().isReturnExplain());
                dUserActionParam.setForceNeedOptDesc(workflowAction.getActionParam().isForceNeedOptDesc());
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
                dUserActionParam.setSingleRejectAction(workflowAction.getActionParam().isSingleRejectAction());
                ArrayList<Integer> erroStatus = new ArrayList<Integer>();
                for (int i = 0; i < formualTypes.size(); ++i) {
                    if (workflowAction.getActionParam().getIgnoreErrorStatus() != null && workflowAction.getActionParam().getIgnoreErrorStatus().contains(formualTypes.get(i))) continue;
                    erroStatus.add((Integer)formualTypes.get(i));
                }
                dUserActionParam.setErroStatus(erroStatus);
                dUserActionParam.setNeedCommentErrorStatus(workflowAction.getActionParam().getNeedCommentErrorStatus());
                dWorkflowUserAction.setUserActionParam(dUserActionParam);
                dUserActionParam.setSignBootMode(workflowAction.getActionParam().isSignStartMode());
                dWorkflowUserActions.add(dWorkflowUserAction);
            }
            dWorkflowData.setUserActions(dWorkflowUserActions);
            dWorkflowDatas.add(dWorkflowData);
        }
        return dWorkflowDatas;
    }
}

