/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.definition.common.FormulaCheckType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.common.ReportAuditType
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.de.dataflow.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.common.FormulaCheckType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UserAction;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.CustomCheckFilter;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.complete.AutoComplete;
import com.jiuqi.nr.bpm.de.dataflow.service.IActionAlias;
import com.jiuqi.nr.bpm.de.dataflow.util.DataentryWorkflowUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.Actor.GrantedToEntityAndRoleParameter;
import com.jiuqi.nr.bpm.impl.Actor.SpecifiedAndGrantedUsersParameter;
import com.jiuqi.nr.bpm.impl.Actor.SpecifiedUsersParameter;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.bpm.upload.utils.ActionAndStateUtil;
import com.jiuqi.nr.definition.common.ReportAuditType;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.util.StringUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActionMethod {
    private static final Logger logger = LoggerFactory.getLogger(ActionMethod.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private RoleService roleService;
    @Autowired
    CustomWorkFolwService customWorkFolwService;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Autowired
    private IActionAlias actionAlias;
    @Autowired
    private ActionAndStateUtil actionAndStateUtil;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    private DataentryWorkflowUtil dataentryWorkflowUtil;

    public static ActorStrategyParameter getReceiverParam(String key, String partParam) throws Exception {
        Class<?> clazz = Class.forName(key);
        ObjectMapper mapper = new ObjectMapper();
        Map readValue = (Map)mapper.readValue(partParam, (TypeReference)new TypeReference<Map<String, Object>>(){});
        Object roleObj = readValue.get("roleIds");
        Object userObj = readValue.get("userIds");
        ArrayList<String> roleIds = new ArrayList<String>();
        if (roleObj != null) {
            if (roleObj instanceof ArrayList) {
                ArrayList list = (ArrayList)roleObj;
                roleIds.addAll(list);
            } else {
                String[] roleArray = (String[])roleObj;
                for (int i = 0; i < roleArray.length; ++i) {
                    String user = roleArray[i];
                    roleIds.add(user);
                }
            }
        }
        ArrayList userIds = new ArrayList();
        if (userObj != null) {
            if (userObj instanceof ArrayList) {
                userIds = (ArrayList)userObj;
            } else {
                String[] userArray = (String[])userObj;
                for (int i = 0; i < userArray.length; ++i) {
                    String user = userArray[i];
                    userIds.add(user);
                }
            }
        }
        ActorStrategy actorStrategyInstance = (ActorStrategy)clazz.newInstance();
        String parameterType = actorStrategyInstance.getParameterType().getSimpleName();
        HashSet<String> roleIdSet = new HashSet<String>();
        HashSet<String> userIdSet = new HashSet<String>();
        if (roleIds != null) {
            roleIdSet = new HashSet(roleIds);
        }
        if (userIds != null) {
            userIdSet = new HashSet(userIds);
        }
        ActorStrategyParameter strayeParam = ActionMethod.getActorStrategyParameterType(parameterType, roleIdSet, userIdSet);
        return strayeParam;
    }

    private static ActorStrategyParameter getActorStrategyParameterType(String actorStrategy, Set<String> roleIdSet, Set<String> userIdSet) {
        switch (actorStrategy) {
            case "SpecifiedAndGrantedUsersParameter": {
                SpecifiedAndGrantedUsersParameter param = new SpecifiedAndGrantedUsersParameter();
                param.setRoleIdSet(roleIdSet);
                param.setUserIdSet(userIdSet);
                return param;
            }
            case "SpecifiedUsersParameter": {
                SpecifiedUsersParameter param1 = new SpecifiedUsersParameter();
                param1.setUserIdSet(userIdSet);
                return param1;
            }
            case "GrantedToEntityAndRoleParameter": {
                GrantedToEntityAndRoleParameter param2 = new GrantedToEntityAndRoleParameter();
                param2.setRoleIdSet(roleIdSet);
                return param2;
            }
        }
        return null;
    }

    public ActionParam getWlrkflowParam(String formSchemeKey, String actionCode, boolean customCheckFliter, DimensionValueSet dimensionValueSet) {
        ActionParam actionParam = new ActionParam();
        FormSchemeDefine formScheme = null;
        try {
            formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            if ("act_upload".equals(actionCode) || "act_submit".equals(actionCode)) {
                String[] split;
                CustomCheckFilter customCheck;
                if ("act_upload".equals(actionCode)) {
                    boolean submitAfterFormula = flowsSetting.getSubmitAfterFormula();
                    String submitAfterFormulaValue = flowsSetting.getSubmitAfterFormulaValue();
                    actionParam.setSubmitAfterFormula(submitAfterFormula);
                    actionParam.setSubmitAfterFormulaValue(submitAfterFormulaValue);
                    actionParam.setNeedOptDesc(flowsSetting.isSubmitExplain());
                    actionParam.setForceNeedOptDesc(flowsSetting.isForceSubmitExplain());
                    boolean stepByStepReport = flowsSetting.getStepByStepReport();
                    boolean stepByStepBack = flowsSetting.getStepByStepBack();
                    actionParam.setStepByStepReport(flowsSetting.getStepByStepReport());
                    actionParam.setStepByStepBack(stepByStepBack);
                    actionParam.setStepByStep(stepByStepReport || stepByStepBack);
                }
                if (flowsSetting.getReportBeforeAuditValue() != null && !"".equals(flowsSetting.getReportBeforeAuditValue())) {
                    actionParam.setCheckFormulaValue(this.getCheckFormulaSchemeKey(formSchemeKey, flowsSetting.getReportBeforeAuditValue()));
                } else {
                    FormulaSchemeDefine defaultFormulaSchemeInFormScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
                    actionParam.setCheckFormulaValue(defaultFormulaSchemeInFormScheme.getKey());
                }
                if (flowsSetting.getReportBeforeOperationValue() != null && !"".equals(flowsSetting.getReportBeforeOperationValue())) {
                    actionParam.setCalcuteFormulaValue(this.getCheckFormulaSchemeKey(formSchemeKey, flowsSetting.getReportBeforeOperationValue()));
                } else {
                    FormulaSchemeDefine defaultFormulaSchemeInFormScheme = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
                    actionParam.setCalcuteFormulaValue(defaultFormulaSchemeInFormScheme.getKey());
                }
                if (flowsSetting.getFilterCondition() != null) {
                    actionParam.setCheckFilter(flowsSetting.getFilterCondition());
                } else {
                    actionParam.setCheckFilter("");
                }
                actionParam.setCheckCurrencyValue(this.getCheckConditions(formSchemeKey));
                actionParam.setCheckCurrencyType(flowsSetting.getReportBeforeAuditType().getValue());
                actionParam.setNodeCheckCurrencyValue(this.getNodecheckConditions(formSchemeKey));
                actionParam.setNodeCheckCurrencyType(flowsSetting.getCheckBeforeReportingType().getValue());
                actionParam.setNeedAutoCalculate(flowsSetting.getReportBeforeOperation());
                String selectedRoleForceKey = flowsSetting.getSelectedRoleForceKey();
                boolean unitSubmitForForce = flowsSetting.isUnitSubmitForForce();
                boolean forceSubimit = this.forceSubimit(selectedRoleForceKey, unitSubmitForForce, formSchemeKey, dimensionValueSet);
                actionParam.setForceCommit(forceSubimit);
                actionParam.setNeedAutoCheck(flowsSetting.getReportBeforeAudit());
                actionParam.setNodeCheck(flowsSetting.isCheckBeforeReporting());
                actionParam.setNeedAutoAllCheck(flowsSetting.getSpecialAudit());
                if (customCheckFliter) {
                    customCheck = this.getCustomCheckFliter(formSchemeKey);
                    actionParam.setNeedCommentErrorStatus(customCheck.getNeedCommentError());
                } else {
                    ArrayList<Integer> proptStatus = new ArrayList<Integer>();
                    if (!flowsSetting.getPromptStatus().isEmpty() && flowsSetting.getPromptStatus().contains(";")) {
                        for (String sp : split = flowsSetting.getPromptStatus().split(";")) {
                            if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)sp)) continue;
                            proptStatus.add(Integer.valueOf(sp));
                        }
                    }
                    actionParam.setNeedCommentErrorStatus(proptStatus);
                }
                if (customCheckFliter) {
                    customCheck = this.getCustomCheckFliter(formSchemeKey);
                    actionParam.setIgnoreErrorStatus(customCheck.getIgnorError());
                } else {
                    ArrayList<Integer> erroStatus = new ArrayList<Integer>();
                    if (!flowsSetting.getErroStatus().isEmpty() && flowsSetting.getErroStatus().contains(";")) {
                        for (String sp : split = flowsSetting.getErroStatus().split(";")) {
                            if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)sp)) continue;
                            erroStatus.add(Integer.valueOf(sp));
                        }
                    }
                    actionParam.setIgnoreErrorStatus(erroStatus);
                }
            } else if ("act_reject".equals(actionCode)) {
                boolean stepByStepReport = flowsSetting.getStepByStepReport();
                boolean stepByStepBack = flowsSetting.getStepByStepBack();
                actionParam.setStepByStepReport(flowsSetting.getStepByStepReport());
                actionParam.setStepByStepBack(stepByStepBack);
                actionParam.setStepByStepBackAll(flowsSetting.getGoBackAllSup());
                actionParam.setStepByStep(stepByStepReport || stepByStepBack);
                actionParam.setReturnTypeEnable(flowsSetting.isOpenBackType());
                actionParam.setNeedOptDesc(flowsSetting.isBackDescriptionNeedWrite());
                actionParam.setForceNeedOptDesc(true);
                actionParam.setNeedbuildVersion(flowsSetting.isReturnVersion());
                actionParam.setStepByStepReport(flowsSetting.getStepByStepReport());
                String sendMessageMail = flowsSetting.getSendMessageMail();
                if (StringUtils.isNotEmpty((String)sendMessageMail) && sendMessageMail.contains("act_reject_notice")) {
                    actionParam.setMailShow(true);
                    actionParam.setSysMsgShow(true);
                }
            } else if ("act_return".equals(actionCode)) {
                actionParam.setForceNeedOptDesc(true);
                actionParam.setIsReturnExplain(flowsSetting.isReturnExplain());
            } else if ("act_confirm".equals(actionCode)) {
                String sendMessageMail = flowsSetting.getSendMessageMail();
                if (StringUtils.isNotEmpty((String)sendMessageMail) && sendMessageMail.contains("act_confirm_notice")) {
                    actionParam.setMailShow(true);
                }
            } else if ("act_apply_return".equals(actionCode)) {
                actionParam.setNeedOptDesc(true);
                return actionParam;
            }
            actionParam.setBatchOpt(true);
            if ("act_cancel_confirm".equals(actionCode)) {
                actionParam.setBatchOpt(false);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionParam;
    }

    public ActionParam getCustomWlrkflowParam(String formSchemeKey, WorkFlowAction actionInfo, boolean customCheckFliter, String nodeId) {
        return this.getCustomWlrkflowParam(formSchemeKey, actionInfo, customCheckFliter, nodeId, new DimensionValueSet());
    }

    public ActionParam getCustomWlrkflowParam(String formSchemeKey, WorkFlowAction actionInfo, boolean customCheckFliter, String nodeId, DimensionValueSet dimensionValueSet) {
        ActionParam actionParam = new ActionParam();
        try {
            if (actionInfo != null && actionInfo.getLinkid() != null) {
                boolean sendEmail;
                WorkFlowDefine workFlowDefine;
                ObjectMapper objMapper = new ObjectMapper();
                String exset = actionInfo.getExset();
                Map object = (Map)objMapper.readValue(exset, (TypeReference)new TypeReference<Map<String, Object>>(){});
                if (object != null) {
                    Boolean nodeCheck;
                    Boolean stepBySteped;
                    ObjectMapper objectMapper;
                    CustomCheckFilter customCheck;
                    Boolean needAutoAllCheck;
                    Boolean needAutoCheck;
                    List list;
                    Map formulaSchemes;
                    Object o;
                    Boolean needAutoCalculate;
                    if (object.containsKey("needOptDesc")) {
                        Boolean needOptDesc = ActionMethod.convertBoolean(object.get("needOptDesc"));
                        actionParam.setNeedOptDesc(needOptDesc);
                        if (needOptDesc.booleanValue() && object.containsKey("forceNeedOptDesc")) {
                            actionParam.setForceNeedOptDesc(ActionMethod.convertBoolean(object.get("forceNeedOptDesc")));
                        }
                    }
                    if (object.containsKey("needbuildVersion")) {
                        actionParam.setNeedbuildVersion(ActionMethod.convertBoolean(object.get("needbuildVersion")));
                    }
                    if (object.containsKey("needAutoCalculate") && (needAutoCalculate = ActionMethod.convertBoolean(object.get("needAutoCalculate"))) != null) {
                        Map needAutoCalculateConfMap;
                        Object needAutoCalculateConf;
                        actionParam.setNeedAutoCalculate(needAutoCalculate);
                        if (object.containsKey("needAutoCalculateConf") && (needAutoCalculateConf = object.get("needAutoCalculateConf")) != null && needAutoCalculateConf instanceof HashMap && (o = (formulaSchemes = (Map)(needAutoCalculateConfMap = (Map)needAutoCalculateConf).get("formulaSchemes")).get(formSchemeKey)) != null && o instanceof List) {
                            list = (List)o;
                            actionParam.setCalcuteFormulaValue(String.join((CharSequence)";", list));
                        }
                    }
                    if (object.containsKey("needAutoCheck") && (needAutoCheck = ActionMethod.convertBoolean(object.get("needAutoCheck"))) != null) {
                        Object needAutoCheckConf;
                        actionParam.setNeedAutoCheck(needAutoCheck);
                        if (object.containsKey("needAutoCheckConf") && (needAutoCheckConf = object.get("needAutoCheckConf")) != null && needAutoCheckConf instanceof HashMap) {
                            Map needAutoCheckConfMap = (Map)needAutoCheckConf;
                            formulaSchemes = (Map)needAutoCheckConfMap.get("formulaSchemes");
                            o = formulaSchemes.get(formSchemeKey);
                            if (o != null && o instanceof List) {
                                list = (List)o;
                                actionParam.setCheckFormulaValue(String.join((CharSequence)";", list));
                            }
                            Map currencyConf = (Map)needAutoCheckConfMap.get("currencyConf");
                            Object cusValue = currencyConf.get("cusValue");
                            Object currencyType = currencyConf.get("type");
                            actionParam.setCheckCurrencyValue(this.getCurrencyValue(currencyType, cusValue));
                            actionParam.setCheckCurrencyType(Integer.valueOf(currencyType.toString()));
                            Object conditionFilter = needAutoCheckConfMap.get("dimFilterCondition");
                            if (conditionFilter != null) {
                                actionParam.setCheckFilter(conditionFilter.toString());
                            }
                        }
                    }
                    if (object.containsKey("needAutoAllCheck") && (needAutoAllCheck = ActionMethod.convertBoolean(object.get("needAutoAllCheck"))) != null) {
                        actionParam.setNeedAutoAllCheck(needAutoAllCheck);
                    }
                    if (object.containsKey("ignor")) {
                        if (customCheckFliter) {
                            customCheck = this.getCustomCheckFliter(formSchemeKey);
                            actionParam.setIgnoreErrorStatus(customCheck.getIgnorError());
                        } else {
                            Object ignor = object.get("ignor");
                            if (ignor != null) {
                                objectMapper = new ObjectMapper();
                                List erroStatus = (List)objectMapper.readValue(ignor.toString(), (TypeReference)new TypeReference<List<Integer>>(){});
                                actionParam.setIgnoreErrorStatus(erroStatus);
                            } else {
                                actionParam.setIgnoreErrorStatus(new ArrayList<Integer>());
                            }
                        }
                    }
                    if (object.containsKey("fill")) {
                        if (customCheckFliter) {
                            customCheck = this.getCustomCheckFliter(formSchemeKey);
                            actionParam.setNeedCommentErrorStatus(customCheck.getNeedCommentError());
                        } else {
                            Object fill = object.get("fill");
                            if (fill != null) {
                                objectMapper = new ObjectMapper();
                                List proptStatus = (List)objectMapper.readValue(fill.toString(), (TypeReference)new TypeReference<List<Integer>>(){});
                                actionParam.setNeedCommentErrorStatus(proptStatus);
                            } else {
                                actionParam.setNeedCommentErrorStatus(new ArrayList<Integer>());
                            }
                        }
                    }
                    if (("cus_upload".equals(actionInfo.getActionCode()) || "cus_submit".equals(actionInfo.getActionCode())) && object.containsKey("stepByStepReport") && (stepBySteped = ActionMethod.convertBoolean(object.get("stepByStepReport"))) != null) {
                        actionParam.setStepByStepReport(stepBySteped);
                        actionParam.setStepByStep(stepBySteped);
                    }
                    if ("cus_reject".equals(actionInfo.getActionCode())) {
                        Boolean stepByStepBackAll;
                        Boolean stepByStepBack;
                        if (object.containsKey("stepByStepReturn") && (stepByStepBack = ActionMethod.convertBoolean(object.get("stepByStepReturn"))) != null) {
                            actionParam.setStepByStepBack(stepByStepBack);
                            actionParam.setStepByStep(stepByStepBack);
                        }
                        if (object.containsKey("stepByStepReturnAll") && (stepByStepBackAll = ActionMethod.convertBoolean(object.get("stepByStepReturnAll"))) != null) {
                            actionParam.setStepByStepBackAll(stepByStepBackAll);
                        }
                    }
                    Boolean batchOpt = ActionMethod.convertBoolean(object.get("batchOpt"));
                    actionParam.setBatchOpt(batchOpt);
                    if (object.containsKey("nodeCheck") && (nodeCheck = ActionMethod.convertBoolean(object.get("nodeCheck"))) != null) {
                        Object nodeCheckConf;
                        actionParam.setNodeCheck(ActionMethod.convertBoolean(object.get("nodeCheck")));
                        if (object.containsKey("nodeCheckConf") && (nodeCheckConf = object.get("nodeCheckConf")) != null && nodeCheckConf instanceof Map) {
                            Map nodeCheckConfMap = (Map)nodeCheckConf;
                            Map currencyConf = (Map)nodeCheckConfMap.get("currencyConf");
                            Object cusValue = currencyConf.get("cusValue");
                            Object currencyType = currencyConf.get("type");
                            actionParam.setNodeCheckCurrencyValue(this.getCurrencyValue(currencyType, cusValue));
                            actionParam.setNodeCheckCurrencyType(Integer.valueOf(currencyType.toString()));
                        }
                    }
                    if (object.containsKey("forceUpload")) {
                        Boolean forceUpload = ActionMethod.convertBoolean(object.get("forceUpload"));
                        if (forceUpload == null) {
                            forceUpload = false;
                        }
                        forceUpload = this.dataentryWorkflowUtil.isEnable(formSchemeKey, dimensionValueSet, forceUpload);
                        actionParam.setForceCommit(forceUpload);
                        if (forceUpload.booleanValue()) {
                            String identityId = NpContextHolder.getContext().getIdentityId();
                            if (this.systemIdentityService.isAdmin()) {
                                actionParam.setForceCommit(true);
                            } else if (object.containsKey("forceRole")) {
                                String forceRole = object.get("forceRole").toString();
                                if (com.jiuqi.bi.util.StringUtils.isEmpty((String)forceRole)) {
                                    actionParam.setForceCommit(forceUpload);
                                } else {
                                    Set role = this.roleService.getIdByIdentity(identityId);
                                    actionParam.setForceCommit(role.contains(forceRole) && forceUpload != false);
                                }
                            }
                        }
                    }
                    if (object.containsKey("forceControl")) {
                        Boolean forceControl = ActionMethod.convertBoolean(object.get("forceControl"));
                        if (forceControl == null) {
                            forceControl = false;
                        }
                        actionParam.setForceControl(forceControl);
                    }
                }
                if ((workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByLinkID(actionInfo.getLinkid())) != null && workFlowDefine.getId() != null && (sendEmail = workFlowDefine.isSendEmail()) && "cus_reject".equals(actionInfo.getActionCode())) {
                    actionParam.setMailShow(true);
                    actionParam.setSysMsgShow(true);
                }
                if (nodeId != null && ("cus_upload".equals(actionInfo.getActionCode()) || "cus_submit".equals(actionInfo.getActionCode()) || "cus_confirm".equals(actionInfo.getActionCode()))) {
                    WorkFlowLine workFlowLine;
                    WorkFlowNodeSet nodeSetByID;
                    List<WorkFlowLine> workflowLinesByPreTask = this.customWorkFolwService.getWorkflowLinesByPreTask(nodeId, actionInfo.getId(), actionInfo.getLinkid());
                    boolean hasExecuted = this.hasExecuted(formSchemeKey, dimensionValueSet, nodeId);
                    if (workflowLinesByPreTask != null && workflowLinesByPreTask.size() > 0 && workflowLinesByPreTask.size() == 1 && (nodeSetByID = this.customWorkFolwService.getWorkFlowNodeSetByID((workFlowLine = workflowLinesByPreTask.get(0)).getAfterNodeID(), actionInfo.getLinkid())) != null && nodeSetByID.getId() != null && hasExecuted) {
                        boolean signStartMode = nodeSetByID.isSignStartMode();
                        actionParam.setSignStartMode(signStartMode);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return actionParam;
    }

    public ActionParam getWlrkflowParam(String formSchemeKey, String actionCode) {
        return this.getWlrkflowParam(formSchemeKey, actionCode, false, new DimensionValueSet());
    }

    public ActionParam getWlrkflowParam(String formSchemeKey, String actionCode, DimensionValueSet dimensionValueSet) {
        return this.getWlrkflowParam(formSchemeKey, actionCode, false, dimensionValueSet);
    }

    public ActionParam getCustomWlrkflowParam(String formSchemeKey, WorkFlowAction actionInfo, String nodeId) {
        return this.getCustomWlrkflowParam(formSchemeKey, actionInfo, false, nodeId);
    }

    public boolean forceSubimit(String selectedRoleForceKey, boolean unitSubmitForForce, String formSchemeKey, DimensionValueSet dimensionValueSet) {
        boolean forced = false;
        NpContext context = NpContextHolder.getContext();
        String identityId = context.getIdentityId();
        forced = this.dataentryWorkflowUtil.isEnable(formSchemeKey, dimensionValueSet, unitSubmitForForce);
        if (forced) {
            if (this.systemIdentityService.isAdmin()) {
                forced = true;
            } else {
                List byIdentity = this.roleService.getByIdentity(identityId);
                if (byIdentity != null && byIdentity.size() > 0) {
                    List userIds = byIdentity.stream().map(e -> e.getId()).collect(Collectors.toList());
                    forced = userIds.contains(selectedRoleForceKey);
                }
            }
        }
        return forced;
    }

    public String getIcon(String actionCode) {
        if (actionCode != null) {
            switch (actionCode) {
                case "act_upload": {
                    return "#icon-_GJHshangbao";
                }
                case "act_reject": {
                    return "#icon-_GJZtuihui";
                }
                case "act_confirm": {
                    return "#icon-_GJHqueren";
                }
                case "act_submit": {
                    return "#icon-_GJHsongshen";
                }
                case "act_return": {
                    return "#icon-_GJHtuishen";
                }
            }
        }
        return null;
    }

    public List<WorkflowAction> getActions(Task task, String formSchemeKey, String nodeId, DimensionValueSet dimensionValueSet, List<DimensionValueSet> unitKeys) {
        if (unitKeys.contains(dimensionValueSet)) {
            return this.getAction(task, formSchemeKey, nodeId, true, dimensionValueSet);
        }
        return this.getAction(task, formSchemeKey, nodeId, false, dimensionValueSet);
    }

    public List<WorkflowAction> getAction(Task task, String formSchemeKey, String nodeId, boolean customCheckFilter, DimensionValueSet dimensionValueSet) {
        ArrayList<WorkflowAction> worlflowActionList = new ArrayList<WorkflowAction>();
        Optional<ProcessEngine> processEngine = this.getProcessEngine(formSchemeKey);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        Optional<UserTask> userTask = deployService.getUserTask(task.getProcessDefinitionId(), task.getUserTaskId(), formSchemeKey);
        List userAction = userTask.map(usertask -> usertask.getActions()).orElse(null);
        WorkflowAction workflowAction = null;
        for (UserAction action : userAction) {
            workflowAction = new WorkflowAction();
            String actionCode = action.getId();
            workflowAction.setCode(actionCode);
            Map<String, String> actionCodeAndStateName = this.actionAlias.actionCodeAndActionName(formSchemeKey);
            if (actionCodeAndStateName != null && actionCodeAndStateName.size() > 0) {
                workflowAction.setTitle(actionCodeAndStateName.get(actionCode));
            } else {
                String actionTitle = action.getName();
                workflowAction.setTitle(actionTitle);
            }
            boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeKey);
            if (defaultWorkflow) {
                ActionParam actionParam = this.getWlrkflowParam(formSchemeKey, actionCode, customCheckFilter, dimensionValueSet);
                boolean forceUpdateNew = this.dataentryWorkflowUtil.isEnable(formSchemeKey, dimensionValueSet, actionParam.isForceCommit());
                actionParam.setForceCommit(forceUpdateNew);
                String icon = this.getIcon(actionCode);
                workflowAction.setIcon(icon);
                workflowAction.setActionParam(actionParam);
            } else {
                WorkflowSettingDefine workflowDefineByFormSchemeKey = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                WorkFlowDefine runWorkFlowDefineByID = this.customWorkFolwService.getRunWorkFlowDefineByID(workflowDefineByFormSchemeKey.getWorkflowId(), 1);
                WorkFlowAction actionInfo = this.customWorkFolwService.getWorkflowActionByCode(nodeId, actionCode, runWorkFlowDefineByID.getLinkid());
                if (null != actionInfo) {
                    workflowAction.setIcon(actionInfo.getImagePath());
                    workflowAction.setCode(actionInfo.getActionCode());
                    workflowAction.setTitle(actionInfo.getActionTitle());
                    workflowAction.setDesc(actionInfo.getActionDesc());
                }
                ActionParam actionParam = this.getCustomWlrkflowParam(formSchemeKey, actionInfo, customCheckFilter, nodeId, dimensionValueSet);
                workflowAction.setActionParam(actionParam);
            }
            String languageName = this.actionAndStateUtil.getLanguageName(workflowAction.getCode());
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)languageName)) {
                workflowAction.setTitle(languageName);
            }
            worlflowActionList.add(workflowAction);
        }
        return worlflowActionList;
    }

    public List<WorkflowAction> getAction(Task task, String formSchemeKey, String nodeId) {
        ArrayList<WorkflowAction> worlflowActionList = new ArrayList<WorkflowAction>();
        Optional<ProcessEngine> processEngine = this.getProcessEngine(formSchemeKey);
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        List<ProcessInstance> instances = runtimeService.queryInstanceByProcessDefinitionKey(task.getProcessDefinitionId());
        ProcessInstance processInstance = null;
        if (instances != null && instances.size() > 0) {
            processInstance = instances.get(0);
        }
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        Optional<UserTask> userTask = deployService.getUserTask(task.getProcessDefinitionId(), task.getUserTaskId(), formSchemeKey);
        List userAction = userTask.map(usertask -> usertask.getActions()).orElse(null);
        WorkflowAction workflowAction = null;
        for (UserAction action : userAction) {
            workflowAction = new WorkflowAction();
            String actionCode = action.getId();
            workflowAction.setCode(actionCode);
            Map<String, String> actionCodeAndStateName = this.actionAlias.actionCodeAndActionName(formSchemeKey);
            if (actionCodeAndStateName != null && actionCodeAndStateName.size() > 0) {
                workflowAction.setTitle(actionCodeAndStateName.get(actionCode));
            } else {
                String actionTitle = action.getName();
                workflowAction.setTitle(actionTitle);
            }
            boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeKey);
            if (defaultWorkflow) {
                ActionParam actionParam = new ActionParam();
                actionParam = this.getWlrkflowParam(formSchemeKey, actionCode);
                String icon = this.getIcon(actionCode);
                workflowAction.setIcon(icon);
                workflowAction.setActionParam(actionParam);
            } else {
                WorkflowSettingDefine workflowDefineByFormSchemeKey = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
                WorkFlowDefine workFlowDefineByID = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefineByFormSchemeKey.getWorkflowId(), 1);
                WorkFlowAction actionInfo = this.customWorkFolwService.getWorkflowActionByCode(nodeId, actionCode, workFlowDefineByID.getLinkid());
                if (null != actionInfo) {
                    workflowAction.setIcon(actionInfo.getImagePath());
                    workflowAction.setCode(actionInfo.getActionCode());
                    workflowAction.setTitle(actionInfo.getActionTitle());
                    workflowAction.setDesc(actionInfo.getActionDesc());
                }
                ActionParam actionParam = this.getCustomWlrkflowParam(formSchemeKey, actionInfo, nodeId);
                workflowAction.setActionParam(actionParam);
            }
            String languageName = this.actionAndStateUtil.getLanguageName(workflowAction.getCode());
            if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)languageName)) {
                workflowAction.setTitle(languageName);
            }
            worlflowActionList.add(workflowAction);
        }
        return worlflowActionList;
    }

    private static Boolean convertBoolean(Object obj) {
        String str;
        Boolean valueOf = false;
        if (obj != null && null != (str = obj.toString()) && !"".equals(str)) {
            valueOf = Boolean.valueOf(str);
            return valueOf;
        }
        return valueOf;
    }

    private CustomCheckFilter getCustomCheckFliter(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String taskKey = formScheme.getTaskKey();
        CustomCheckFilter customCheckFilter = new CustomCheckFilter();
        ITaskOptionController taskOptionController = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
        String filter = taskOptionController.getValue(taskKey, "REVIEW_CONDITION");
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)filter)) {
            String tip = taskOptionController.getValue(taskKey, "REVIEW_TIP");
            String warn = taskOptionController.getValue(taskKey, "REVIEW_WARN");
            String error = taskOptionController.getValue(taskKey, "REVIEW_ERROR");
            ArrayList<Integer> ignorError = new ArrayList<Integer>();
            ArrayList<Integer> needCommentError = new ArrayList<Integer>();
            if ("1".equals(tip)) {
                needCommentError.add(FormulaCheckType.FORMULA_CHECK_HINT.getValue());
            }
            if ("2".equals(tip)) {
                ignorError.add(FormulaCheckType.FORMULA_CHECK_HINT.getValue());
            }
            if ("1".equals(warn)) {
                needCommentError.add(FormulaCheckType.FORMULA_CHECK_WARNING.getValue());
            }
            if ("2".equals(warn)) {
                ignorError.add(FormulaCheckType.FORMULA_CHECK_WARNING.getValue());
            }
            if ("1".equals(error)) {
                needCommentError.add(FormulaCheckType.FORMULA_CHECK_ERROR.getValue());
            }
            if ("2".equals(error)) {
                ignorError.add(FormulaCheckType.FORMULA_CHECK_ERROR.getValue());
            }
            customCheckFilter.setIgnorError(ignorError);
            customCheckFilter.setNeedCommentError(needCommentError);
        }
        return customCheckFilter;
    }

    public List<DimensionValueSet> getCustomCheckFliterUnitKeys(String taskKey, DimensionValueSet dimensionValueSet, String dwEntityID) {
        ArrayList<DimensionValueSet> customDWs = new ArrayList<DimensionValueSet>();
        ITaskOptionController taskOptionController = (ITaskOptionController)BeanUtil.getBean(ITaskOptionController.class);
        String filter = taskOptionController.getValue(taskKey, "REVIEW_CONDITION");
        if (com.jiuqi.bi.util.StringUtils.isNotEmpty((String)filter)) {
            EntityViewDefine entityViewDefine = this.entityViewRunTimeController.buildEntityView(dwEntityID);
            IEntityQuery query = this.entityDataService.newEntityQuery();
            query.setEntityView(entityViewDefine);
            query.setMasterKeys(dimensionValueSet);
            query.setRowFilter(filter);
            ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
            String mainDimName = this.dimensionUtil.getDimensionName(entityViewDefine);
            try {
                IEntityTable resultSet = query.executeReader((IContext)context);
                List allRows = resultSet.getAllRows();
                if (allRows != null && !allRows.isEmpty()) {
                    for (IEntityRow allRow : allRows) {
                        String entityKeyData = allRow.getEntityKeyData();
                        DimensionValueSet dim = new DimensionValueSet();
                        dim.setValue("DATATIME", dimensionValueSet.getValue("DATATIME"));
                        dim.setValue(mainDimName, (Object)entityKeyData);
                        customDWs.add(dim);
                    }
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return customDWs;
    }

    public ActionParam actionParam(String formSchemeKey, String actionCode) {
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeKey);
        ArrayList<ActionParam> actionParams = new ArrayList<ActionParam>();
        ActionParam actionParam = new ActionParam();
        if (defaultWorkflow) {
            actionParam = "single_form_upload".equals(actionCode) ? this.getWlrkflowParam(formSchemeKey, "act_upload") : this.getWlrkflowParam(formSchemeKey, actionCode);
        } else {
            WorkflowSettingDefine workflowDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowDefine.getWorkflowId(), 1);
            List<WorkFlowAction> workflowActions = this.customWorkFolwService.getWorkflowActionsByLinkid(workFlowDefine.getLinkid());
            for (WorkFlowAction workFlowAction : workflowActions) {
                if (!actionCode.equals(workFlowAction.getActionCode())) continue;
                actionParam = this.getCustomWlrkflowParam(formSchemeKey, workFlowAction, null);
                actionParams.add(actionParam);
            }
            if (actionParams != null && actionParams.size() == 1) {
                return actionParam;
            }
            if (actionParams != null && actionParams.size() > 1) {
                ActionParam actionParamTemp = (ActionParam)actionParams.get(0);
                for (int i = 1; i < actionParams.size(); ++i) {
                    actionParamTemp = ActionMethod.combineSydwCore(actionParamTemp, (ActionParam)actionParams.get(i));
                }
                return actionParamTemp;
            }
        }
        return actionParam;
    }

    private static ActionParam combineSydwCore(ActionParam targetBean, ActionParam sourceBean) {
        Class<?> sourceBeanClass = sourceBean.getClass();
        Class<?> targetBeanClass = targetBean.getClass();
        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = targetBeanClass.getDeclaredFields();
        for (int i = 0; i < sourceFields.length; ++i) {
            Field targetField;
            Field sourceField = sourceFields[i];
            if (Modifier.isStatic(sourceField.getModifiers()) || Modifier.isStatic((targetField = targetFields[i]).getModifiers())) continue;
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
            try {
                boolean param;
                Object object;
                List<Integer> receiveUnionList;
                if (sourceField.get(sourceBean) != null && "ignoreErrorStatus".equals(sourceField.getName())) {
                    List sourceData = (List)sourceField.get(sourceBean);
                    List targetData = (List)targetField.get(targetBean);
                    receiveUnionList = AutoComplete.receiveUnionList(sourceData, targetData);
                    targetField.set(targetBean, receiveUnionList);
                    continue;
                }
                if (sourceField.get(sourceBean) != null && "needCommentErrorStatus".equals(sourceField.getName())) {
                    List sourceData = (List)sourceField.get(sourceBean);
                    List targetData = (List)targetField.get(targetBean);
                    receiveUnionList = AutoComplete.receiveUnionList(sourceData, targetData);
                    targetField.set(targetBean, receiveUnionList);
                    continue;
                }
                if (sourceField.get(sourceBean) != null && "forceCommit".equals(sourceField.getName())) {
                    boolean forceUpload = (Boolean)sourceField.get(sourceBean);
                    if (forceUpload) continue;
                    targetField.set(targetBean, sourceField.get(sourceBean));
                    continue;
                }
                if (sourceField.get(sourceBean) == null || (object = sourceField.get(sourceBean)) == null || !(object instanceof Boolean) || !(param = ((Boolean)object).booleanValue())) continue;
                targetField.set(targetBean, sourceField.get(sourceBean));
                continue;
            }
            catch (IllegalAccessException | IllegalArgumentException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return targetBean;
    }

    public Optional<ProcessEngine> getProcessEngine(String formSchemeKey) {
        boolean isDefaultFlow = this.isDefaultWorkflow(formSchemeKey);
        if (isDefaultFlow) {
            return this.processEngineProvider.getProcessEngine(ProcessType.DEFAULT);
        }
        return this.processEngineProvider.getProcessEngine();
    }

    public boolean isDefaultWorkflow(String formSchemeKey) {
        WorkflowStatus queryFlowType;
        boolean defaultWorkflow = false;
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        FlowsType flowsType = formSchemeDefine.getFlowsSetting().getFlowsType();
        if (FlowsType.DEFAULT.equals((Object)flowsType) && WorkflowStatus.DEFAULT.equals((Object)(queryFlowType = this.workflowSettingService.queryFlowType(formSchemeKey)))) {
            defaultWorkflow = true;
        }
        return defaultWorkflow;
    }

    public String getCheckFormulaSchemeKey(String formSchemeKey, String reportBeforeAuditValue) {
        List<Object> formulaByFormschemeKey = new ArrayList();
        List allRPTFormulaSchemeDefines = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
        if (allRPTFormulaSchemeDefines != null && allRPTFormulaSchemeDefines.size() > 0) {
            formulaByFormschemeKey = allRPTFormulaSchemeDefines.stream().map(e -> e.getKey()).collect(Collectors.toList());
        }
        ArrayList allFormula = new ArrayList();
        if (StringUtils.isNotEmpty((String)reportBeforeAuditValue)) {
            String[] split = reportBeforeAuditValue.split(";");
            Collections.addAll(allFormula, split);
        }
        allFormula.retainAll(formulaByFormschemeKey);
        return allFormula.stream().collect(Collectors.joining(";"));
    }

    public String getCheckConditions(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        ReportAuditType reportBeforeAuditType = flowsSetting.getReportBeforeAuditType();
        if (ReportAuditType.CUSTOM.equals((Object)reportBeforeAuditType)) {
            String reportBeforeAuditCustom = flowsSetting.getReportBeforeAuditCustom();
            return reportBeforeAuditCustom;
        }
        if (ReportAuditType.ESCALATION.equals((Object)reportBeforeAuditType)) {
            return "PROVIDER_BASECURRENCY";
        }
        if (ReportAuditType.CONVERSION.equals((Object)reportBeforeAuditType)) {
            return "PROVIDER_PBASECURRENCY";
        }
        return "";
    }

    public String getNodecheckConditions(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        ReportAuditType checkBeforeReportingType = flowsSetting.getCheckBeforeReportingType();
        if (ReportAuditType.CUSTOM.equals((Object)checkBeforeReportingType)) {
            String checkBeforeReportingCustom = flowsSetting.getCheckBeforeReportingCustom();
            return checkBeforeReportingCustom;
        }
        if (ReportAuditType.ESCALATION.equals((Object)checkBeforeReportingType)) {
            return "PROVIDER_BASECURRENCY";
        }
        if (ReportAuditType.CONVERSION.equals((Object)checkBeforeReportingType)) {
            return "PROVIDER_PBASECURRENCY";
        }
        return "";
    }

    private String getCurrencyValue(Object typeObj, Object cusValue) {
        int type = Integer.valueOf(typeObj.toString());
        if (ReportAuditType.CUSTOM.getValue() == type) {
            if (cusValue != null) {
                if (cusValue instanceof ArrayList) {
                    List cusArray = (List)cusValue;
                    return String.join((CharSequence)";", cusArray);
                }
                return "";
            }
            return "";
        }
        if (ReportAuditType.ESCALATION.getValue() == type) {
            return "PROVIDER_BASECURRENCY";
        }
        if (ReportAuditType.CONVERSION.getValue() == type) {
            return "PROVIDER_PBASECURRENCY";
        }
        return "";
    }

    public List<ActionParam> getCustomWorkflowAction(String formSchemeKey, String nodeId) {
        ArrayList<ActionParam> actionParams = new ArrayList<ActionParam>();
        WorkflowSettingDefine workflowDefine = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (workflowDefine != null && workflowDefine.getId() != null) {
            String workflowId = workflowDefine.getWorkflowId();
            WorkFlowDefine runWorkFlowDefineByID = this.customWorkFolwService.getRunWorkFlowDefineByID(workflowId, 1);
            List<WorkFlowNodeSet> workFlowNodeSets = this.customWorkFolwService.getWorkFlowNodeSets(runWorkFlowDefineByID.getLinkid());
            for (WorkFlowNodeSet workFlowNodeSet : workFlowNodeSets) {
                String[] actions;
                if (!workFlowNodeSet.getId().equals(nodeId) || (actions = workFlowNodeSet.getActions()) == null || actions.length <= 0) continue;
                for (String actionid : actions) {
                    WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(actionid, runWorkFlowDefineByID.getLinkid());
                    ActionParam customWlrkflowParam = this.getCustomWlrkflowParam(formSchemeKey, workflowAction, nodeId);
                    actionParams.add(customWlrkflowParam);
                }
            }
        }
        return actionParams;
    }

    private boolean hasExecuted(String formSchemeKey, DimensionValueSet dimensionValueSet, String nodeId) {
        if (dimensionValueSet == null) {
            return true;
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        List<UploadRecordNew> uploadRecordNews = this.batchQueryUploadStateService.queryHisUploadStates(formScheme, dimensionValueSet, "", "", nodeId);
        return uploadRecordNews != null && uploadRecordNews.size() > 0;
    }
}

