/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.logic.facade.extend.ICKDQueryCKRFilter
 *  com.jiuqi.nr.data.logic.facade.param.input.BatchSaveCheckDesParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.output.CKDSaveResult
 *  com.jiuqi.nr.data.logic.facade.param.output.FormulaData
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.ParamUtil
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.params.base.FormulaData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.base.SearchFormulaData
 *  com.jiuqi.nr.jtable.params.input.BatchSaveFormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesCopyInfo
 *  com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo
 *  com.jiuqi.nr.jtable.params.input.FuzzyQueryFormulaParam
 *  com.jiuqi.nr.jtable.params.output.DescriptionInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 *  com.jiuqi.nr.jtable.params.output.ReturnInfo
 *  com.jiuqi.nr.jtable.service.IFormulaCheckDesService
 *  com.jiuqi.nr.jtable.util.CheckTransformUtil
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.facade.extend.ICKDQueryCKRFilter;
import com.jiuqi.nr.data.logic.facade.param.input.BatchSaveCheckDesParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.output.CKDSaveResult;
import com.jiuqi.nr.data.logic.facade.param.output.FormulaData;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.dataentry.bean.RefreshStatusParam;
import com.jiuqi.nr.dataentry.internal.service.BatchCheckResultFilter;
import com.jiuqi.nr.dataentry.internal.service.util.CheckDesTransformUtil;
import com.jiuqi.nr.dataentry.paramInfo.AllCheckInfo;
import com.jiuqi.nr.dataentry.paramInfo.BatchCheckResultGroupInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormulaCheckGroupReturnInfo;
import com.jiuqi.nr.dataentry.paramInfo.FormulaCheckResultGroupInfo;
import com.jiuqi.nr.dataentry.service.IAllCheckService;
import com.jiuqi.nr.dataentry.service.IBatchCheckResultService;
import com.jiuqi.nr.dataentry.service.IRefreshStatusService;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.SearchFormulaData;
import com.jiuqi.nr.jtable.params.input.BatchSaveFormulaCheckDesInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesBatchSaveInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesCopyInfo;
import com.jiuqi.nr.jtable.params.input.FormulaCheckDesQueryInfo;
import com.jiuqi.nr.jtable.params.input.FuzzyQueryFormulaParam;
import com.jiuqi.nr.jtable.params.output.DescriptionInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckDesInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckResultInfo;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.params.output.ReturnInfo;
import com.jiuqi.nr.jtable.service.IFormulaCheckDesService;
import com.jiuqi.nr.jtable.util.CheckTransformUtil;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormulaCheckDesServiceImplNew
implements IFormulaCheckDesService {
    private static final Logger logger = LoggerFactory.getLogger(IFormulaCheckDesService.class);
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired
    private CheckDesTransformUtil checkDesTransformUtil;
    @Autowired
    private CheckTransformUtil checkTransformUtil;
    @Autowired
    private ParamUtil paramUtil;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IAllCheckService allCheckService;
    @Autowired
    private IBatchCheckResultService batchCheckResultService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;

    public List<FormulaCheckDesInfo> queryFormulaCheckDes(FormulaCheckDesQueryInfo formulaCheckDesQueryInfo) {
        CheckDesQueryParam checkDesQueryParam = this.checkDesTransformUtil.getCheckDesQueryParam(formulaCheckDesQueryInfo);
        List checkDesObjs = this.checkErrorDescriptionService.queryFormulaCheckDes(checkDesQueryParam);
        return this.checkDesTransformUtil.getFormulaCheckDesInfoList(checkDesObjs);
    }

    public int batchSaveFormulaCheckDes(FormulaCheckDesBatchSaveInfo formulaCheckDesBatchSaveInfo) {
        CheckDesBatchSaveObj checkDesBatchSaveObj = this.checkDesTransformUtil.getCheckDesBatchSaveObj(formulaCheckDesBatchSaveInfo);
        this.checkErrorDescriptionService.batchSaveFormulaCheckDes(checkDesBatchSaveObj);
        return formulaCheckDesBatchSaveInfo.getDesInfos().size();
    }

    public FormulaCheckDesInfo saveFormulaCheckDes(FormulaCheckDesInfo descriptionInfo) {
        DescriptionInfo des = descriptionInfo.getDescriptionInfo();
        des.setUserTitle(this.paramUtil.getUserNickNameById(des.getUserId()));
        CheckDesObj checkDesObj = this.checkDesTransformUtil.getCheckDesObj(descriptionInfo);
        return this.checkDesTransformUtil.getFormulaCheckDesInfo(this.checkErrorDescriptionService.saveFormulaCheckDes(checkDesObj));
    }

    public ReturnInfo removeFormulaCheckDes(FormulaCheckDesInfo descriptionInfo) {
        boolean editEnable;
        ReturnInfo returnInfo = new ReturnInfo();
        if ("batchCheck_Result".equals(descriptionInfo.getCheckType()) && !(editEnable = this.checkEditEnable(descriptionInfo))) {
            returnInfo.setMessage("noAuth");
            return returnInfo;
        }
        CheckDesObj checkDesObj = this.checkDesTransformUtil.getCheckDesObj(descriptionInfo);
        this.checkErrorDescriptionService.removeFormulaCheckDes(checkDesObj);
        returnInfo.setMessage("success");
        return returnInfo;
    }

    public ReturnInfo reviseCheckDesKey() {
        ReturnInfo returnInfo = new ReturnInfo();
        try {
            this.checkErrorDescriptionService.reviseCheckDesKey();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            returnInfo.setMessage(e.getMessage());
        }
        returnInfo.setMessage("success");
        return returnInfo;
    }

    public ReturnInfo batchSaveFormulaCheckDes(BatchSaveFormulaCheckDesInfo checkDesInfo) {
        BatchSaveCheckDesParam batchSaveCheckDesParam;
        ReturnInfo returnInfo = new ReturnInfo();
        if (checkDesInfo.isUpload()) {
            try {
                FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(checkDesInfo.getContext().getFormSchemeKey());
                TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
                if (checkDesInfo.getContext().getDimensionSet().containsKey("MD_CURRENCY")) {
                    ReportAuditType reportAuditType = flowsSetting.getReportBeforeAuditType();
                    if (reportAuditType.equals((Object)ReportAuditType.CONVERSION)) {
                        ((DimensionValue)checkDesInfo.getContext().getDimensionSet().get("MD_CURRENCY")).setValue("PROVIDER_PBASECURRENCY");
                    }
                    if (reportAuditType.equals((Object)ReportAuditType.ESCALATION)) {
                        ((DimensionValue)checkDesInfo.getContext().getDimensionSet().get("MD_CURRENCY")).setValue("PROVIDER_BASECURRENCY");
                    }
                    if (reportAuditType.equals((Object)ReportAuditType.CUSTOM)) {
                        ((DimensionValue)checkDesInfo.getContext().getDimensionSet().get("MD_CURRENCY")).setValue(flowsSetting.getReportBeforeAuditCustom());
                    }
                }
                checkDesInfo.setFormulaSchemeKeys(flowsSetting.getReportBeforeAuditValue());
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        if ((batchSaveCheckDesParam = this.checkDesTransformUtil.getBatchSaveCheckDesParam(checkDesInfo)) == null) {
            returnInfo.setMessage("success");
            return returnInfo;
        }
        if (!checkDesInfo.isAllowAddErrorMsgAfterReport()) {
            batchSaveCheckDesParam.setFilter((ICKDQueryCKRFilter)new BatchCheckResultFilter(checkDesInfo.getContext(), this.runTimeViewController));
        }
        this.checkErrorDescriptionService.batchSaveFormulaCheckDes(batchSaveCheckDesParam);
        returnInfo.setMessage("success");
        return returnInfo;
    }

    public ReturnInfo copyCheckDesKey(FormulaCheckDesCopyInfo copyInfo) {
        return null;
    }

    public List<com.jiuqi.nr.jtable.params.base.FormulaData> queryCheckResultFormulaIds(BatchSaveFormulaCheckDesInfo checkDesInfo) {
        BatchSaveCheckDesParam batchSaveCheckDesParam = this.checkDesTransformUtil.getBatchSaveCheckDesParam(checkDesInfo);
        if (batchSaveCheckDesParam == null) {
            return new ArrayList<com.jiuqi.nr.jtable.params.base.FormulaData>();
        }
        ArrayList<com.jiuqi.nr.jtable.params.base.FormulaData> formulaData = new ArrayList<com.jiuqi.nr.jtable.params.base.FormulaData>();
        for (FormulaData formula : this.checkErrorDescriptionService.queryCheckResultFormulas(batchSaveCheckDesParam)) {
            formulaData.add(this.checkTransformUtil.transformFormulaData(formula));
        }
        return formulaData;
    }

    public List<SearchFormulaData> fuzzyQueryFormula(FuzzyQueryFormulaParam checkDesInfo) {
        ArrayList<SearchFormulaData> formulaData = new ArrayList<SearchFormulaData>();
        HashMap<String, Integer> codeMap = new HashMap<String, Integer>();
        if ("allcheck".equals(checkDesInfo.getActionName())) {
            AllCheckInfo allCheckInfo = new AllCheckInfo();
            allCheckInfo.setAsyncTaskKey(checkDesInfo.getAsyncTaskKey());
            allCheckInfo.setContext(checkDesInfo.getContext());
            allCheckInfo.setFormulas(checkDesInfo.getFormulas());
            allCheckInfo.setFormulaSchemeKeys(checkDesInfo.getFormulaSchemeKeys());
            allCheckInfo.setCheckTypes(checkDesInfo.getCheckTypes());
            allCheckInfo.setChooseTypes(checkDesInfo.getCheckTypes());
            allCheckInfo.setUploadCheckTypes(checkDesInfo.getUploadCheckTypes());
            allCheckInfo.setCheckDesNull(checkDesInfo.isCheckDesNull());
            FormulaCheckReturnInfo formulaCheckReturnInfo = this.allCheckService.allCheckResult(allCheckInfo);
            for (FormulaCheckResultInfo formulaCheckResultInfo : formulaCheckReturnInfo.getResults()) {
                com.jiuqi.nr.jtable.params.base.FormulaData formula = formulaCheckResultInfo.getFormula();
                if (!formula.getCode().toLowerCase().contains(checkDesInfo.getKeyWord().toLowerCase()) || codeMap.containsKey(formula.getCode())) continue;
                SearchFormulaData curNode = this.checkTransformUtil.transformSearchFormulaData(formula);
                int index = curNode.getCode().toLowerCase().indexOf(checkDesInfo.getKeyWord().toLowerCase());
                curNode.setBeforeText(curNode.getCode().substring(0, index));
                curNode.setHighlightText(curNode.getCode().substring(index, index + checkDesInfo.getKeyWord().length()));
                curNode.setAfterText(curNode.getCode().substring(index + checkDesInfo.getKeyWord().length()));
                formulaData.add(curNode);
                codeMap.put(formula.getCode(), 1);
            }
        } else {
            BatchCheckResultGroupInfo batchCheckResultGroupInfo = new BatchCheckResultGroupInfo();
            batchCheckResultGroupInfo.setAsyncTaskKey(checkDesInfo.getAsyncTaskKey());
            batchCheckResultGroupInfo.setContext(checkDesInfo.getContext());
            batchCheckResultGroupInfo.setFormulas(checkDesInfo.getFormulas());
            batchCheckResultGroupInfo.setFormulaSchemeKeys(checkDesInfo.getFormulaSchemeKeys());
            batchCheckResultGroupInfo.setCheckTypes(checkDesInfo.getCheckTypes());
            batchCheckResultGroupInfo.setUploadCheckTypes(checkDesInfo.getUploadCheckTypes());
            batchCheckResultGroupInfo.setCheckDesNull(checkDesInfo.isCheckDesNull());
            batchCheckResultGroupInfo.setOrderField("formula");
            FormulaCheckGroupReturnInfo formulaCheckGroupReturnInfo = this.batchCheckResultService.batchCheckResultGroup(batchCheckResultGroupInfo);
            for (FormulaCheckResultGroupInfo formulaCheckResultGroupInfo : formulaCheckGroupReturnInfo.getResults()) {
                if (!formulaCheckResultGroupInfo.getCode().toLowerCase().contains(checkDesInfo.getKeyWord().toLowerCase()) || codeMap.containsKey(formulaCheckResultGroupInfo.getCode())) continue;
                FormulaDefine formulaDefine = this.formulaRunTimeController.queryFormulaDefine(formulaCheckResultGroupInfo.getKey());
                SearchFormulaData curNode = new SearchFormulaData();
                curNode.setId(formulaCheckResultGroupInfo.getKey());
                curNode.setFormKey(formulaDefine.getFormKey());
                curNode.setCode(formulaCheckResultGroupInfo.getCode());
                int index = curNode.getCode().toLowerCase().indexOf(checkDesInfo.getKeyWord().toLowerCase());
                curNode.setBeforeText(curNode.getCode().substring(0, index));
                curNode.setHighlightText(curNode.getCode().substring(index, index + checkDesInfo.getKeyWord().length()));
                curNode.setAfterText(curNode.getCode().substring(index + checkDesInfo.getKeyWord().length()));
                curNode.setAfterDivideText(formulaDefine.getDescription());
                formulaData.add(curNode);
                codeMap.put(formulaCheckResultGroupInfo.getCode(), 1);
            }
        }
        return formulaData;
    }

    public ReturnInfo batchSaveCKD(BatchSaveFormulaCheckDesInfo checkDesInfo) {
        CKDSaveResult ckdSaveResult;
        BatchSaveCheckDesParam batchSaveCheckDesParam;
        ReturnInfo returnInfo = new ReturnInfo();
        if (checkDesInfo.isUpload()) {
            try {
                FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(checkDesInfo.getContext().getFormSchemeKey());
                TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
                if (checkDesInfo.getContext().getDimensionSet().containsKey("MD_CURRENCY")) {
                    ReportAuditType reportAuditType = flowsSetting.getReportBeforeAuditType();
                    if (reportAuditType.equals((Object)ReportAuditType.CONVERSION)) {
                        ((DimensionValue)checkDesInfo.getContext().getDimensionSet().get("MD_CURRENCY")).setValue("PROVIDER_PBASECURRENCY");
                    }
                    if (reportAuditType.equals((Object)ReportAuditType.ESCALATION)) {
                        ((DimensionValue)checkDesInfo.getContext().getDimensionSet().get("MD_CURRENCY")).setValue("PROVIDER_BASECURRENCY");
                    }
                    if (reportAuditType.equals((Object)ReportAuditType.CUSTOM)) {
                        ((DimensionValue)checkDesInfo.getContext().getDimensionSet().get("MD_CURRENCY")).setValue(flowsSetting.getReportBeforeAuditCustom());
                    }
                }
                checkDesInfo.setFormulaSchemeKeys(flowsSetting.getReportBeforeAuditValue());
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        if ((batchSaveCheckDesParam = this.checkDesTransformUtil.getBatchSaveCheckDesParam(checkDesInfo)) == null) {
            returnInfo.setMessage("success");
            return returnInfo;
        }
        if (!checkDesInfo.isAllowAddErrorMsgAfterReport()) {
            batchSaveCheckDesParam.setFilter((ICKDQueryCKRFilter)new BatchCheckResultFilter(checkDesInfo.getContext(), this.runTimeViewController));
        }
        if ((ckdSaveResult = this.checkErrorDescriptionService.batchSaveCKD(batchSaveCheckDesParam)).isSuccess()) {
            returnInfo.setMessage("success");
        } else {
            returnInfo.setMessage("error");
            StringBuffer sb = new StringBuffer();
            for (String msg : ckdSaveResult.getErrorMsgList()) {
                sb.append(msg).append(";");
            }
            returnInfo.setCommitError(sb.substring(0, sb.length() - 1));
        }
        return returnInfo;
    }

    public FormulaCheckDesInfo saveCKD(FormulaCheckDesInfo descriptionInfo) {
        boolean editEnable;
        if ("batchCheck_Result".equals(descriptionInfo.getCheckType()) && !(editEnable = this.checkEditEnable(descriptionInfo))) {
            FormulaCheckDesInfo formulaCheckDesInfo = new FormulaCheckDesInfo();
            formulaCheckDesInfo.setEditCheckDesEnable(false);
            formulaCheckDesInfo.setDesKey("saveCKDFail");
            return formulaCheckDesInfo;
        }
        DescriptionInfo des = descriptionInfo.getDescriptionInfo();
        des.setUserTitle(this.paramUtil.getUserNickNameById(des.getUserId()));
        CheckDesObj checkDesObj = this.checkDesTransformUtil.getCheckDesObj(descriptionInfo);
        CKDSaveResult ckdSaveResult = this.checkErrorDescriptionService.saveCKD(checkDesObj);
        FormulaCheckDesInfo formulaCheckDesInfo = this.checkDesTransformUtil.getFormulaCheckDesInfo(checkDesObj);
        if (!ckdSaveResult.isSuccess()) {
            StringBuffer sb = new StringBuffer();
            for (String msg : ckdSaveResult.getErrorMsgList()) {
                sb.append(msg).append(";");
            }
            formulaCheckDesInfo.setCommitError(sb.substring(0, sb.length() - 1));
        }
        return formulaCheckDesInfo;
    }

    private boolean checkEditEnable(FormulaCheckDesInfo descriptionInfo) {
        boolean writable = true;
        List uploadStateNews = new ArrayList();
        IWorkflow workflow = (IWorkflow)BeanUtil.getBean(IWorkflow.class);
        WorkFlowType workFlowType = workflow.queryStartType(descriptionInfo.getFormSchemeKey());
        IDataentryFlowService dataFlowService = (IDataentryFlowService)BeanUtil.getBean(IDataentryFlowService.class);
        boolean entityFlowType = workFlowType == WorkFlowType.ENTITY || workFlowType == WorkFlowType.GROUP || workFlowType == WorkFlowType.FORM;
        WorkflowConfig workflowConfig = dataFlowService.queryWorkflowConfig(descriptionInfo.getFormSchemeKey());
        if (entityFlowType && workflowConfig.isFlowStarted()) {
            IQueryUploadStateService queryUploadStateService = (IQueryUploadStateService)BeanUtil.getBean(IQueryUploadStateService.class);
            DimensionValueSet dim = DimensionValueSetUtil.getDimensionValueSet((Map)descriptionInfo.getDimensionSet());
            if (workFlowType == WorkFlowType.ENTITY) {
                uploadStateNews = queryUploadStateService.queryUploadStates(descriptionInfo.getFormSchemeKey(), dim, new ArrayList(), new ArrayList());
            } else if (workFlowType == WorkFlowType.FORM) {
                ArrayList<String> forms = new ArrayList<String>();
                forms.add(descriptionInfo.getFormKey());
                uploadStateNews = queryUploadStateService.queryUploadStates(descriptionInfo.getFormSchemeKey(), new DimensionValueSet(), forms, new ArrayList());
            } else {
                ArrayList<String> groupSet = new ArrayList<String>();
                List formGroups = this.runTimeViewController.getFormGroupsByFormKey(descriptionInfo.getFormKey());
                if (formGroups.size() > 0) {
                    groupSet.add(((FormGroupDefine)formGroups.get(0)).getKey());
                }
                uploadStateNews = queryUploadStateService.queryUploadStates(descriptionInfo.getFormSchemeKey(), new DimensionValueSet(), new ArrayList(), groupSet);
            }
        }
        JtableContext context = new JtableContext();
        context.setDimensionSet(descriptionInfo.getDimensionSet());
        context.setFormSchemeKey(descriptionInfo.getFormSchemeKey());
        context.setTaskKey(descriptionInfo.getTaskKey());
        RefreshStatusParam refreshStatusParam = new RefreshStatusParam();
        refreshStatusParam.setContext(context);
        refreshStatusParam.setForm(false);
        IRefreshStatusService refreshStatusService = (IRefreshStatusService)BeanUtil.getBean(IRefreshStatusService.class);
        Map<String, Object> status = refreshStatusService.getStatus(refreshStatusParam);
        UploadStateNew uploadStateNew = (UploadStateNew)uploadStateNews.get(0);
        ActionStateBean actionState = null;
        if (uploadStateNew != null) {
            actionState = uploadStateNew.getActionStateBean();
        }
        if (actionState != null && (UploadState.UPLOADED.name().equals(actionState.getCode()) || UploadState.CONFIRMED.name().equals(actionState.getCode()) || UploadState.SUBMITED.name().equals(actionState.getCode()))) {
            writable = false;
        }
        if (status.containsKey("formReject") && !((Set)status.get("formReject")).isEmpty()) {
            writable = ((Set)status.get("formReject")).contains(descriptionInfo.getFormKey());
        }
        if (status.containsKey("formReject-V2") && !((Map)status.get("formReject-V2")).isEmpty()) {
            writable = ((Map)status.get("formReject-V2")).containsKey(descriptionInfo.getFormKey());
        }
        return writable;
    }
}

