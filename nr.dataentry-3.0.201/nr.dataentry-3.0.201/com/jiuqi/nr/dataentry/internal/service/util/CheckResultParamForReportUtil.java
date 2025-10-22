/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.definition.option.AuditSchemeOptionService
 *  com.jiuqi.nr.definition.option.dto.AuditSchemeDTO
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.service.IEntityAssist
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 */
package com.jiuqi.nr.dataentry.internal.service.util;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.definition.option.AuditSchemeOptionService;
import com.jiuqi.nr.definition.option.dto.AuditSchemeDTO;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.service.IEntityAssist;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckResultParamForReportUtil {
    @Autowired
    private IDataentryFlowService dataentryFlowService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityViewRunTimeController viewAdapter;
    @Autowired
    private IEntityAssist iEntityAssist;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private AuditSchemeOptionService auditSchemeOptionService;
    public static final String CANIGNORE = "CANIGNORE";
    public static final String NEEDEXPLAIN = "NEEDEXPLAIN";
    public static final String AFFECT = "AFFECT";
    private static final Logger logger = LoggerFactory.getLogger(CheckResultParamForReportUtil.class);

    public List<String> getFormListForAllcheck(JtableContext context) {
        WorkflowConfig workflowConfig = this.dataentryFlowService.queryWorkflowConfig(context.getFormSchemeKey());
        ArrayList<String> formsList = new ArrayList<String>();
        if (workflowConfig.getWorkFlowType().equals((Object)WorkFlowType.FORM)) {
            formsList.add(context.getFormKey());
            return formsList;
        }
        if (workflowConfig.getWorkFlowType().equals((Object)WorkFlowType.GROUP)) {
            String groupKey = context.getFormGroupKey();
            try {
                List formDefines = this.viewController.getAllFormsInGroup(groupKey, true);
                for (FormDefine formDefine : formDefines) {
                    formsList.add(formDefine.getKey());
                }
            }
            catch (Exception e) {
                return null;
            }
            return formsList;
        }
        return formsList;
    }

    public Map<Integer, Boolean> getCheckTypesMapForReport(JtableContext context, List<Integer> chooseTypes, Boolean checkDesNull, boolean isAffectCommit, DUserActionParam userActionParam) {
        HashedMap<Integer, Boolean> checkTypeMap = new HashedMap<Integer, Boolean>();
        if (chooseTypes == null || chooseTypes.size() == 0) {
            return checkTypeMap;
        }
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
        String dwDim = dwEntity.getDimensionName();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet());
        String dwkey = (String)dimensionValueSet.getValue(dwDim);
        boolean enable = this.enableCustomConfig(dwEntity, dwkey, context.getTaskKey(), context.getFormSchemeKey());
        Map<Object, Object> formulaTypeMap = new HashedMap();
        formulaTypeMap = enable ? this.getCustomFormulaTypeMap(context.getTaskKey()) : this.getFlowFormulaTypeMap(context, userActionParam);
        List needExplain = (List)formulaTypeMap.get(NEEDEXPLAIN);
        List affect = (List)formulaTypeMap.get(AFFECT);
        if (affect == null || affect.size() == 0) {
            return checkTypeMap;
        }
        if (isAffectCommit) {
            ArrayList errorType = new ArrayList();
            chooseTypes.stream().forEach(e -> {
                if (affect.contains(e)) {
                    errorType.add(e);
                }
            });
            for (Integer type : errorType) {
                if (checkDesNull == null) {
                    if (needExplain.contains(type)) {
                        checkTypeMap.put(type, false);
                        continue;
                    }
                    checkTypeMap.put(type, null);
                    continue;
                }
                if (needExplain.contains(type)) {
                    if (!checkDesNull.booleanValue()) continue;
                    checkTypeMap.put(type, false);
                    continue;
                }
                checkTypeMap.put(type, checkDesNull == false);
            }
        } else {
            for (Integer type : chooseTypes) {
                if (checkDesNull == null) {
                    checkTypeMap.put(type, null);
                    continue;
                }
                if (checkDesNull.booleanValue()) {
                    checkTypeMap.put(type, false);
                    continue;
                }
                checkTypeMap.put(type, true);
            }
        }
        return checkTypeMap;
    }

    public Map<Boolean, Set<String>> enableCustomConfigs(EntityViewData dwEntity, List<String> unitKeys, String taskKey, String formSchemeKey) {
        Map<Boolean, Set<String>> unitCustomConfigMap = new HashedMap<Boolean, Set<String>>();
        String customCondition = this.auditSchemeOptionService.getConditionValueInScheme(taskKey);
        if (StringUtils.isNotEmpty((String)customCondition) && !unitKeys.isEmpty()) {
            if (dwEntity == null) {
                dwEntity = this.jtableParamService.getDwEntity(formSchemeKey);
            }
            FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeKey);
            String dateTime = formScheme.getDateTime();
            EntityViewDefine entityView = this.viewAdapter.buildEntityView(dwEntity.getKey(), customCondition);
            ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
            executorContext.setPeriodView(dateTime);
            HashSet<String> unitSet = new HashSet<String>(unitKeys);
            unitCustomConfigMap = this.iEntityAssist.filterExpression((IContext)executorContext, entityView, unitSet);
        }
        return unitCustomConfigMap;
    }

    public boolean enableCustomConfig(EntityViewData dwEntity, String unitKey, String taskKey, String formSchemeKey) {
        boolean enable = false;
        String customCondition = this.auditSchemeOptionService.getConditionValueInScheme(taskKey);
        if (StringUtils.isNotEmpty((String)customCondition) && StringUtils.isNotEmpty((String)unitKey)) {
            if (dwEntity == null) {
                dwEntity = this.jtableParamService.getDwEntity(formSchemeKey);
            }
            FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeKey);
            String dateTime = formScheme.getDateTime();
            EntityViewDefine entityView = this.viewAdapter.buildEntityView(dwEntity.getKey(), customCondition);
            ExecutorContext executorContext = new ExecutorContext(this.iDataDefinitionRuntimeController);
            executorContext.setPeriodView(dateTime);
            enable = this.iEntityAssist.judgementExpression((IContext)executorContext, entityView, unitKey);
        }
        return enable;
    }

    public Map<String, List<Integer>> getCustomFormulaTypeMap(String taskKey) {
        HashedMap<String, List<Integer>> formulaTypeMap = new HashedMap<String, List<Integer>>();
        ArrayList<Integer> needExplain = new ArrayList<Integer>();
        ArrayList<Integer> canIgnore = new ArrayList<Integer>();
        ArrayList<Integer> affect = new ArrayList<Integer>();
        List allAuditTypesInSchemes = this.auditSchemeOptionService.getAllAuditTypesInScheme(taskKey);
        for (AuditSchemeDTO allAuditTypesInScheme : allAuditTypesInSchemes) {
            String formulaCode = allAuditTypesInScheme.getCode();
            String formulaValue = allAuditTypesInScheme.getValue();
            if (!StringUtils.isNotEmpty((String)formulaValue) || !StringUtils.isNotEmpty((String)formulaCode)) continue;
            if (formulaValue.equals("2")) {
                canIgnore.add(Integer.valueOf(formulaCode));
                continue;
            }
            if (formulaValue.equals("1")) {
                needExplain.add(Integer.valueOf(formulaCode));
                affect.add(Integer.valueOf(formulaCode));
                continue;
            }
            affect.add(Integer.valueOf(formulaCode));
        }
        formulaTypeMap.put(CANIGNORE, canIgnore);
        formulaTypeMap.put(NEEDEXPLAIN, needExplain);
        formulaTypeMap.put(AFFECT, affect);
        return formulaTypeMap;
    }

    public Map<String, List<Integer>> getFlowFormulaTypeMap(String formSchemeKey, List<Integer> needExplain, List<Integer> canIgnore) {
        HashedMap<String, List<Integer>> formulaTypeMap = new HashedMap<String, List<Integer>>();
        List<Object> formualTypes = new ArrayList();
        try {
            formualTypes = this.auditTypeDefineService.queryAllAuditType().stream().map(AuditType::getCode).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error("\u62a5\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ArrayList<Object> affect = new ArrayList<Object>();
        for (int i = 0; i < formualTypes.size(); ++i) {
            if (canIgnore != null && canIgnore.contains(formualTypes.get(i))) continue;
            affect.add(formualTypes.get(i));
        }
        formulaTypeMap.put(CANIGNORE, canIgnore);
        formulaTypeMap.put(NEEDEXPLAIN, needExplain);
        formulaTypeMap.put(AFFECT, affect);
        return formulaTypeMap;
    }

    public Map<String, List<Integer>> getFlowFormulaTypeMap(String formSchemeKey) {
        String[] split;
        HashedMap<String, List<Integer>> formulaTypeMap = new HashedMap<String, List<Integer>>();
        List<Object> formualTypes = new ArrayList();
        try {
            formualTypes = this.auditTypeDefineService.queryAllAuditType().stream().map(AuditType::getCode).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error("\u62a5\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        ArrayList<Integer> needExplain = new ArrayList<Integer>();
        ArrayList<Integer> canIgnore = new ArrayList<Integer>();
        ArrayList<Object> affect = new ArrayList<Object>();
        FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeKey);
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        if (!flowsSetting.getPromptStatus().isEmpty() && flowsSetting.getPromptStatus().contains(";")) {
            for (String sp : split = flowsSetting.getPromptStatus().split(";")) {
                if (!StringUtils.isNotEmpty((String)sp)) continue;
                needExplain.add(Integer.valueOf(sp));
            }
        }
        if (!flowsSetting.getErroStatus().isEmpty() && flowsSetting.getErroStatus().contains(";")) {
            for (String sp : split = flowsSetting.getErroStatus().split(";")) {
                if (!StringUtils.isNotEmpty((String)sp)) continue;
                canIgnore.add(Integer.valueOf(sp));
            }
        }
        for (int i = 0; i < formualTypes.size(); ++i) {
            if (canIgnore != null && canIgnore.contains(formualTypes.get(i))) continue;
            affect.add(formualTypes.get(i));
        }
        formulaTypeMap.put(CANIGNORE, canIgnore);
        formulaTypeMap.put(NEEDEXPLAIN, needExplain);
        formulaTypeMap.put(AFFECT, affect);
        return formulaTypeMap;
    }

    public Map<String, List<Integer>> getFlowFormulaTypeMap(JtableContext context, DUserActionParam userActionParam) {
        HashedMap<String, List<Integer>> formulaTypeMap = new HashedMap<String, List<Integer>>();
        List<Object> formualTypes = new ArrayList();
        try {
            formualTypes = this.auditTypeDefineService.queryAllAuditType().stream().map(AuditType::getCode).collect(Collectors.toList());
        }
        catch (Exception e) {
            logger.error("\u62a5\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        List<Object> needExplain = new ArrayList();
        List canIgnore = new ArrayList();
        ArrayList<Object> affect = new ArrayList<Object>();
        WorkflowDataBean workflowData = new WorkflowDataBean();
        workflowData.setFormSchemeKey(context.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet());
        workflowData.setDimSet(dimensionValueSet);
        workflowData.setFormKey(context.getFormKey());
        workflowData.setFormGroupKey(context.getFormGroupKey());
        if (userActionParam != null) {
            List<Integer> erroStatus = userActionParam.getErroStatus();
            List<Integer> needCommentErrorStatus = userActionParam.getNeedCommentErrorStatus();
            needExplain = needCommentErrorStatus;
            formulaTypeMap.put(CANIGNORE, canIgnore);
            formulaTypeMap.put(NEEDEXPLAIN, needExplain);
            formulaTypeMap.put(AFFECT, erroStatus);
        } else {
            List queryWorkflowDataInfo = this.dataentryFlowService.queryWorkflowDataInfo(workflowData);
            if (queryWorkflowDataInfo != null && queryWorkflowDataInfo.size() > 0) {
                WorkflowDataInfo workflowDataInfo = (WorkflowDataInfo)queryWorkflowDataInfo.get(0);
                List actions = workflowDataInfo.getActions();
                for (WorkflowAction action : actions) {
                    if (!action.getCode().equals("act_upload") && !action.getCode().equals("cus_upload") && !action.getCode().equals("act_submit") && !action.getCode().equals("cus_submit")) continue;
                    ActionParam actionParam = action.getActionParam();
                    List ignoreErrorStatus = actionParam.getIgnoreErrorStatus();
                    List needCommentErrorStatus = actionParam.getNeedCommentErrorStatus();
                    needExplain = needCommentErrorStatus;
                    canIgnore = ignoreErrorStatus;
                    for (int i = 0; i < formualTypes.size(); ++i) {
                        if (canIgnore != null && canIgnore.contains(formualTypes.get(i))) continue;
                        affect.add(formualTypes.get(i));
                    }
                    formulaTypeMap.put(CANIGNORE, canIgnore);
                    formulaTypeMap.put(NEEDEXPLAIN, needExplain);
                    formulaTypeMap.put(AFFECT, affect);
                }
            }
        }
        return formulaTypeMap;
    }

    public String getFilterCondition(String formSchemeKey) {
        FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeKey);
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        return flowsSetting.getFilterCondition();
    }
}

