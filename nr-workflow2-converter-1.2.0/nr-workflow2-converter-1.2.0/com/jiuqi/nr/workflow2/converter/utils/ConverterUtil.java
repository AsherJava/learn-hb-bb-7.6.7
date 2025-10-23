/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActor
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend
 *  com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.workflow2.converter.utils;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.engine.core.actor.IActor;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterUtil {
    @Autowired
    private INvwaSystemOptionService nvwaSystemOptionService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;

    public ActionParam buildActionParam(String actionCode, Map<String, Object> properties, String workflowNode, Workflow2EngineCompatibleExtend workflow2EngineCompatibleExtend, FormSchemeDefine formSchemeDefine) {
        ActionParam actionParam = new ActionParam();
        List<String> calculateFormulaSchemeList = workflow2EngineCompatibleExtend.getCalculateFormulaSchemeList(formSchemeDefine.getTaskKey(), workflowNode, actionCode);
        calculateFormulaSchemeList = this.filterFormulaSchemesInFormScheme(formSchemeDefine, calculateFormulaSchemeList);
        String calculateFormulaSchemeStr = String.join((CharSequence)";", calculateFormulaSchemeList);
        actionParam.setCalcuteFormulaValue(calculateFormulaSchemeStr);
        List<String> reviewFormulaSchemeList = workflow2EngineCompatibleExtend.getReviewFormulaSchemeList(formSchemeDefine.getTaskKey(), workflowNode, actionCode);
        reviewFormulaSchemeList = this.filterFormulaSchemesInFormScheme(formSchemeDefine, reviewFormulaSchemeList);
        String reviewFormulaSchemeStr = String.join((CharSequence)";", reviewFormulaSchemeList);
        actionParam.setCheckFormulaValue(reviewFormulaSchemeStr);
        ReportAuditType reviewCurrencyType = workflow2EngineCompatibleExtend.getReviewCurrencyType(formSchemeDefine.getTaskKey(), workflowNode, actionCode);
        actionParam.setCheckCurrencyType(reviewCurrencyType.getValue());
        List reviewCustomCurrencyValue = workflow2EngineCompatibleExtend.getReviewCustomCurrencyValue(formSchemeDefine.getTaskKey(), workflowNode, actionCode);
        String currencyStr = String.join((CharSequence)";", reviewCustomCurrencyValue);
        actionParam.setCheckCurrencyValue(currencyStr);
        List ignoreErrorStatus = workflow2EngineCompatibleExtend.getIgnoreErrorStatus(formSchemeDefine.getTaskKey(), workflowNode, actionCode);
        actionParam.setIgnoreErrorStatus(ignoreErrorStatus);
        List needCommentErrorStatus = workflow2EngineCompatibleExtend.getNeedCommentErrorStatus(formSchemeDefine.getTaskKey(), workflowNode, actionCode);
        actionParam.setNeedCommentErrorStatus(needCommentErrorStatus);
        actionParam.setSysMsgShow(false);
        boolean mailShow = workflow2EngineCompatibleExtend.getMailShow(formSchemeDefine.getTaskKey(), workflowNode, actionCode);
        actionParam.setMailShow(mailShow);
        boolean support_comment = properties.containsKey("SUPPORT_COMMENT") && (Boolean)properties.get("SUPPORT_COMMENT") != false;
        boolean returnExplain = actionCode.equals("act_return") && support_comment;
        boolean needOptDesc = (actionCode.equals("act_upload") || actionCode.equals("act_reject") || actionCode.equals("act_apply_reject")) && support_comment;
        boolean forceNeedOptDesc = properties.containsKey("COMMENT_NOTNULL") && (Boolean)properties.get("COMMENT_NOTNULL") != false;
        boolean returnTypeEnable = properties.containsKey("SUPPORT_RETURN_TYPE") && (Boolean)properties.get("SUPPORT_RETURN_TYPE") != false;
        boolean forceCommit = properties.containsKey("ENABLE_FORCE_REPORT") ? ((Boolean)properties.get("ENABLE_FORCE_REPORT")).booleanValue() : this.enableForceReport(workflow2EngineCompatibleExtend.getForceUpload(formSchemeDefine.getTaskKey()), actionCode);
        boolean isSingleFormReject = properties.containsKey(IProcessFormRejectAttrKeys.is_form_reject_button.attrKey) && (Boolean)properties.get(IProcessFormRejectAttrKeys.is_form_reject_button.attrKey) != false;
        actionParam.setReturnExplain(returnExplain);
        actionParam.setNeedOptDesc(needOptDesc);
        actionParam.setForceNeedOptDesc(forceNeedOptDesc);
        actionParam.setReturnTypeEnable(returnTypeEnable);
        actionParam.setForceCommit(forceCommit);
        actionParam.setSingleRejectAction(isSingleFormReject);
        boolean isShowBatchButton = !actionCode.equals("act_retrieve") && !actionCode.equals("act_cancel_confirm") && !actionCode.equals("act_apply_reject");
        actionParam.setBatchOpt(isShowBatchButton);
        return actionParam;
    }

    private boolean enableForceReport(String forceUploadValue, String actionCode) {
        if (forceUploadValue == null || forceUploadValue.isEmpty()) {
            return false;
        }
        if (!actionCode.equals("act_submit") && !actionCode.equals("act_upload")) {
            return false;
        }
        IActor actor = IActor.fromContext();
        if (ActorStrategyUtil.getInstance().actorIsSystemUser(actor)) {
            return true;
        }
        return ActorStrategyUtil.getInstance().actorIsGrantedToRole(actor, forceUploadValue);
    }

    public boolean isSystemTodoEnabled() {
        String optionValue = this.nvwaSystemOptionService.get("nr-flow-todo-id", "PROCESS_UPLOAD_CAN_SEND_MSG");
        return optionValue.contains("0");
    }

    public List<String> filterFormulaSchemesInFormScheme(FormSchemeDefine formSchemeDefine, List<String> oriFormulaSchemes) {
        if (oriFormulaSchemes != null) {
            ArrayList<String> filterFormulaSchemes = new ArrayList<String>();
            List allFormulaSchemeDefinesInFormScheme = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formSchemeDefine.getKey());
            List allFormulaSchemeKeysInFormScheme = allFormulaSchemeDefinesInFormScheme.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            for (String formulaSchemeKey : oriFormulaSchemes) {
                if (!allFormulaSchemeKeysInFormScheme.contains(formulaSchemeKey)) continue;
                filterFormulaSchemes.add(formulaSchemeKey);
            }
            return filterFormulaSchemes;
        }
        return null;
    }
}

