/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy
 */
package com.jiuqi.nr.workflow2.converter.workflow;

import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.converter.utils.ConverterUtil;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ActionMethodConverter
extends ActionMethod {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private ConverterUtil converterUtil;
    @Autowired
    private Workflow2EngineCompatibleCollector workflow2EngineCompatibleCollector;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public boolean isDefaultWorkflow(String formSchemeKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.isDefaultWorkflow(formSchemeKey);
        }
        String taskKey = this.runTimeViewController.getFormScheme(formSchemeKey).getTaskKey();
        boolean workflowEnable = this.workflowSettingsService.queryTaskWorkflowEnable(taskKey);
        String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(taskKey);
        return workflowEnable && workflowEngine.equals("jiuqi.nr.default");
    }

    public ActionParam actionParam(String formSchemeKey, String actionCode) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(formSchemeDefine.getTaskKey())) {
            return super.actionParam(formSchemeKey, actionCode);
        }
        String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(formSchemeDefine.getTaskKey());
        Workflow2EngineCompatibleExtend workflow2EngineCompatibleExtend = this.workflow2EngineCompatibleCollector.getExtensionByEngine(workflowEngine);
        ActionParam actionParam = new ActionParam();
        List<String> calculateFormulaSchemeList = workflow2EngineCompatibleExtend.getCalculateFormulaSchemeList(formSchemeDefine.getTaskKey(), null, actionCode);
        calculateFormulaSchemeList = this.converterUtil.filterFormulaSchemesInFormScheme(formSchemeDefine, calculateFormulaSchemeList);
        String calculateFormulaSchemeStr = String.join((CharSequence)";", calculateFormulaSchemeList);
        actionParam.setCalcuteFormulaValue(calculateFormulaSchemeStr);
        List<String> reviewFormulaSchemeList = workflow2EngineCompatibleExtend.getReviewFormulaSchemeList(formSchemeDefine.getTaskKey(), null, actionCode);
        reviewFormulaSchemeList = this.converterUtil.filterFormulaSchemesInFormScheme(formSchemeDefine, reviewFormulaSchemeList);
        String reviewFormulaSchemeStr = String.join((CharSequence)";", reviewFormulaSchemeList);
        actionParam.setCheckFormulaValue(reviewFormulaSchemeStr);
        ReportAuditType reviewCurrencyType = workflow2EngineCompatibleExtend.getReviewCurrencyType(formSchemeDefine.getTaskKey(), null, actionCode);
        actionParam.setCheckCurrencyType(reviewCurrencyType.getValue());
        List reviewCustomCurrencyValue = workflow2EngineCompatibleExtend.getReviewCustomCurrencyValue(formSchemeDefine.getTaskKey(), null, actionCode);
        String currencyStr = String.join((CharSequence)";", reviewCustomCurrencyValue);
        actionParam.setCheckCurrencyValue(currencyStr);
        List ignoreErrorStatus = workflow2EngineCompatibleExtend.getIgnoreErrorStatus(formSchemeDefine.getTaskKey(), null, actionCode);
        actionParam.setIgnoreErrorStatus(ignoreErrorStatus);
        List needCommentErrorStatus = workflow2EngineCompatibleExtend.getNeedCommentErrorStatus(formSchemeDefine.getTaskKey(), null, actionCode);
        actionParam.setNeedCommentErrorStatus(needCommentErrorStatus);
        actionParam.setSysMsgShow(false);
        boolean mailShow = workflow2EngineCompatibleExtend.getMailShow(formSchemeDefine.getTaskKey(), null, actionCode);
        actionParam.setMailShow(mailShow);
        if (!actionCode.equals("act_submit")) {
            if (actionCode.equals("act_upload")) {
                String uploadLayerByLayerExtend = workflow2EngineCompatibleExtend.getUploadLayerByLayer(formSchemeDefine.getTaskKey());
                String uploadDescExtend = workflow2EngineCompatibleExtend.getUploadDesc(formSchemeDefine.getTaskKey());
                UploadLayerByLayerStrategy uploadLayerByLayer = uploadLayerByLayerExtend == null ? null : UploadLayerByLayerStrategy.valueOf((String)uploadLayerByLayerExtend);
                FillInDescStrategy uploadDesc = uploadDescExtend == null ? null : FillInDescStrategy.valueOf((String)uploadDescExtend);
                String forceUpload = workflow2EngineCompatibleExtend.getForceUpload(formSchemeDefine.getTaskKey());
                actionParam.setStepByStepReport(uploadLayerByLayer != null);
                actionParam.setStepByStep(uploadLayerByLayer != null);
                actionParam.setNeedOptDesc(uploadDesc != null);
                actionParam.setForceNeedOptDesc(uploadDesc != null && uploadDesc.equals((Object)FillInDescStrategy.REQUIRED));
                actionParam.setForceCommit(forceUpload != null && !forceUpload.isEmpty());
            } else if (actionCode.equals("act_return")) {
                String backDescExtend = workflow2EngineCompatibleExtend.getBackDesc(formSchemeDefine.getTaskKey());
                FillInDescStrategy backDesc = backDescExtend == null ? null : FillInDescStrategy.valueOf((String)backDescExtend);
                actionParam.setReturnExplain(backDesc != null);
                actionParam.setForceNeedOptDesc(backDesc != null && backDesc.equals((Object)FillInDescStrategy.REQUIRED));
            } else if (actionCode.equals("act_reject")) {
                String returnDescExtend = workflow2EngineCompatibleExtend.getReturnDesc(formSchemeDefine.getTaskKey());
                FillInDescStrategy returnDesc = returnDescExtend == null ? null : FillInDescStrategy.valueOf((String)returnDescExtend);
                String returnType = workflow2EngineCompatibleExtend.getReturnType(formSchemeDefine.getTaskKey());
                actionParam.setStepByStepBack(workflow2EngineCompatibleExtend.isReturnLayerByLayer(formSchemeDefine.getTaskKey()));
                actionParam.setStepByStep(workflow2EngineCompatibleExtend.isReturnLayerByLayer(formSchemeDefine.getTaskKey()));
                actionParam.setStepByStepBackAll(workflow2EngineCompatibleExtend.isReturnAllSuperior(formSchemeDefine.getTaskKey()));
                actionParam.setNeedOptDesc(returnDesc != null);
                actionParam.setForceNeedOptDesc(returnDesc != null && returnDesc.equals((Object)FillInDescStrategy.REQUIRED));
                actionParam.setReturnTypeEnable(returnType != null && !returnType.isEmpty());
            } else if (!actionCode.equals("act_confirm") && actionCode.equals("act_apply_reject")) {
                actionParam.setNeedOptDesc(true);
            }
        }
        return actionParam;
    }
}

