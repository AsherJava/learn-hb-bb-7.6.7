/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.condition;

import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.condition.IConditionalExecute;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.util.StringUtils;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultConditionalExecute
implements IConditionalExecute {
    private Logger logger = LoggerFactory.getLogger(DefaultConditionalExecute.class);
    @Autowired
    private IConnectionProvider connectionProvider;
    @Autowired
    private CustomWorkFolwService customWorkFolwServiceImpl;
    @Autowired
    private IDataDefinitionRuntimeController dataRunTimeController;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private IRunTimeViewController controller;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    private WorkflowSettingService settingService;

    @Override
    public String getTitle() {
        return "\u9ed8\u8ba4\u6267\u884c\u5668";
    }

    @Override
    public String className() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean execute(Task task, String userId, String actionId, String businessKey, String workFlowLineId) {
        BusinessKey businessKeyInfo = BusinessKeyFormatter.parsingFromString(businessKey);
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKeyInfo.getFormSchemeKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwServiceImpl.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        WorkFlowLine workFlowLine = this.customWorkFolwServiceImpl.getWorkFlowLineByID(workFlowLineId, workFlowDefine.getLinkid());
        WorkFlowAction workflowAction = this.customWorkFolwServiceImpl.getWorkflowActionById(workFlowLine.getActionid(), workFlowDefine.getLinkid());
        boolean isAction = this.executeAction(workflowAction.getActionCode(), actionId);
        boolean isFormula = this.executeFormula(workFlowLine.getFormula(), businessKeyInfo);
        boolean isForm = this.executeForm(workFlowLine.getReport(), workFlowLine.isAllreport(), businessKeyInfo);
        return isAction && isFormula && isForm;
    }

    @Override
    public boolean execute(String businessKey, String workFlowLineId) {
        BusinessKey businessKeyInfo = BusinessKeyFormatter.parsingFromString(businessKey);
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKeyInfo.getFormSchemeKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwServiceImpl.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        WorkFlowLine workFlowLine = this.customWorkFolwServiceImpl.getWorkFlowLineByID(workFlowLineId, workFlowDefine.getLinkid());
        boolean isFormula = this.executeFormula(workFlowLine.getFormula(), businessKeyInfo);
        boolean isForm = this.executeForm(workFlowLine.getReport(), workFlowLine.isAllreport(), businessKeyInfo);
        return isFormula && isForm;
    }

    private boolean executeAction(String lineAction, String executeAction) {
        if (lineAction.isEmpty() || executeAction.isEmpty()) {
            return false;
        }
        return lineAction.equals(executeAction);
    }

    private boolean executeFormula(String formulaExpression, BusinessKey businessKeyInfo) {
        if (formulaExpression == null || formulaExpression.isEmpty()) {
            return true;
        }
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = this.executorContext(businessKeyInfo.getFormSchemeKey());
        ExpressionEvaluatorImpl expressionEvaluator = new ExpressionEvaluatorImpl(new QueryParam(this.connectionProvider, this.dataRunTimeController));
        DimensionValueSet masterKeys = this.nrParameterUtils.convertDimensionName(businessKeyInfo);
        try {
            AbstractData result = expressionEvaluator.eval(formulaExpression, (ExecutorContext)context, masterKeys);
            return result.getAsBool();
        }
        catch (ExpressionException e) {
            this.logger.error("\u6267\u884c\u516c\u5f0f\u51fa\u9519");
            return false;
        }
        catch (DataTypeException e) {
            this.logger.error("\u6267\u884c\u516c\u5f0f\u51fa\u9519");
            return false;
        }
    }

    private com.jiuqi.nr.entity.engine.executors.ExecutorContext executorContext(String formSchemeKey) {
        com.jiuqi.nr.entity.engine.executors.ExecutorContext context = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.dataRunTimeController);
        context.setVarDimensionValueSet(new DimensionValueSet());
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.controller, this.dataRunTimeController, this.entityViewRunTimeController, formSchemeKey));
        }
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        context.setOrgEntityId(this.getContextMainDimId(formScheme.getDw()));
        return context;
    }

    private String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }

    private boolean executeForm(String forms, boolean isAll, BusinessKey businessKeyInfo) {
        if (forms == null) {
            return true;
        }
        if (!isAll && forms.isEmpty()) {
            return true;
        }
        String curForm = businessKeyInfo.getFormKey();
        if (curForm != null) {
            return forms.contains(curForm);
        }
        return false;
    }
}

