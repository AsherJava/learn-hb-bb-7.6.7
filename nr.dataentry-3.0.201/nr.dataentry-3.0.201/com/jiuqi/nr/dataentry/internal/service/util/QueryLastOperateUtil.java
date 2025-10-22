/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.bpm.common.UploadStateNew
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowLine
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet
 *  com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService
 *  com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine
 *  com.jiuqi.nr.bpm.setting.service.WorkflowSettingService
 *  com.jiuqi.nr.bpm.upload.UploadState
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.service.ICheckService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.UploadBeforeCheck
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.internal.service.util;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.monitor.WorkFlowCheckProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchWorkFlowInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.UploadBeforeCheck;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryLastOperateUtil {
    private static final Logger logger = LoggerFactory.getLogger(QueryLastOperateUtil.class);
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityViewRunTimeController viewAdapter;
    @Autowired
    private IEntityMetaService iEntityMetaService;
    @Autowired
    private IDataAccessFormService dataAccessFormService;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private ICheckService iCheckService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private IFormConditionService formConditionService;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;

    public List<String> getlastOperateFormKeys(JtableContext context, String acitonCode, String taskNodeCode) {
        ArrayList<String> lastformKeys = new ArrayList<String>();
        EntityViewData targetEntityInfo = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
        String mainDim = targetEntityInfo.getDimensionName();
        String mainKey = targetEntityInfo.getKey();
        WorkFlowType workflowType = this.workflow.queryStartType(context.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet());
        List<String> taskCodes = this.getTaskCode(context.getFormSchemeKey(), acitonCode, taskNodeCode);
        Map<String, Set<String>> queryState = this.queryState(context.getFormSchemeKey(), dimensionValueSet, mainDim, taskCodes);
        List dimensionSetList = DimensionValueSetUtil.getDimensionValueSetList((Map)context.getDimensionSet());
        Map<String, Set<String>> fliterForms = this.getFliterForms(dimensionValueSet, dimensionSetList, mainDim, workflowType, context.getFormSchemeKey());
        for (DimensionValueSet dimensionValue : dimensionSetList) {
            String unitKey = dimensionValue.getValue(mainDim).toString();
            Set<String> hasFliterFormOrGroupKeys = fliterForms.get(unitKey);
            Set<String> hasStateFormKeys = queryState.get(unitKey);
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                String selectFormKey = context.getFormKey();
                if (hasStateFormKeys != null && hasStateFormKeys.size() + 1 >= hasFliterFormOrGroupKeys.size()) {
                    lastformKeys = new ArrayList();
                    continue;
                }
                lastformKeys.add(selectFormKey);
                continue;
            }
            String selectFormGroupKey = context.getFormGroupKey();
            if (hasStateFormKeys != null && hasStateFormKeys.size() + 1 >= hasFliterFormOrGroupKeys.size()) {
                lastformKeys = new ArrayList();
                continue;
            }
            ArrayList<String> formKeys = new ArrayList<String>();
            try {
                List allForms = this.viewController.getAllFormsInGroup(selectFormGroupKey);
                for (FormDefine form : allForms) {
                    formKeys.add(form.getKey());
                }
            }
            catch (Exception e) {
                logger.error("\u62a5\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            lastformKeys.addAll(formKeys);
        }
        return lastformKeys;
    }

    public void batchCheckForSpecialCheck(BatchExecuteTaskParam param, List<BatchWorkFlowInfo> needCheckList, Map<String, List<String>> groupToForm, UploadBeforeCheck uploadBeforeCheck, WorkflowConfig workflowConfig, WorkFlowType startType, AsyncTaskMonitor asyncTaskMonitor, WorkflowAction workflowAction, List<WorkflowDataBean> filterForm, String mainDim, String mainKey, Map<String, DimensionValue> dimensionSet, String checkFilter, boolean enableCalc, String checkFormulaSchemeKey, String taskNodeCode) {
        Double progress = 0.0;
        Map<List<DimensionValueSet>, List<String>> dimToForms = this.getlastOperateDimToFormKeys(param, startType, mainDim, mainKey, param.getContext(), dimensionSet, taskNodeCode);
        double scale = 0.4 / (double)dimToForms.size();
        for (Map.Entry<List<DimensionValueSet>, List<String>> dimToForm : dimToForms.entrySet()) {
            List<DimensionValueSet> dims = dimToForm.getKey();
            List<String> formKeys = dimToForm.getValue();
            CheckParam checkParam = new CheckParam();
            checkParam.setActionId(asyncTaskMonitor.getTaskId());
            checkParam.setFilterCondition(checkFilter);
            DimensionValueSet dimensionValueSet = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.mergeDimensionValueSet(dims);
            DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, param.getContext().getFormSchemeKey());
            checkParam.setDimensionCollection(dimensionCollection);
            checkParam.setVariableMap(param.getContext().getVariableMap());
            checkParam.setMode(Mode.FORM);
            if (!WorkFlowType.ENTITY.equals((Object)startType)) {
                for (String string : formKeys) {
                }
                checkParam.setRangeKeys(formKeys);
            }
            if (enableCalc) {
                progress = 0.3;
            }
            WorkFlowCheckProgressMonitor workFlowCheckProgressMonitor = new WorkFlowCheckProgressMonitor(asyncTaskMonitor, scale, progress);
            String[] formulaSchemeKeys = checkFormulaSchemeKey.split(";");
            workFlowCheckProgressMonitor.setCount(formulaSchemeKeys.length);
            workFlowCheckProgressMonitor.setCoefficient(scale /= (double)formulaSchemeKeys.length);
            for (String formulaSchemeKey : formulaSchemeKeys) {
                workFlowCheckProgressMonitor.setProgress(progress);
                checkParam.setFormulaSchemeKey(formulaSchemeKey);
                this.iCheckService.batchCheck(checkParam, (IFmlMonitor)workFlowCheckProgressMonitor);
                progress = progress + scale;
            }
        }
    }

    private Map<List<DimensionValueSet>, List<String>> getlastOperateDimToFormKeys(BatchExecuteTaskParam param, WorkFlowType workflowType, String dwMainDim, String mainKey, JtableContext context, Map<String, DimensionValue> dimensionSet, String taskNodeCode) {
        HashMap<List<DimensionValueSet>, List<String>> dimToForms = new HashMap<List<DimensionValueSet>, List<String>>();
        ArrayList<DimensionValueSet> dimsForAllCheck = new ArrayList<DimensionValueSet>();
        ArrayList<DimensionValueSet> dimsForBatchCheck = new ArrayList<DimensionValueSet>();
        ArrayList<String> lastformKeysForBatchCheck = new ArrayList<String>();
        DimensionValueSet dimension = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
        List dimensionSetList = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionSetList((DimensionValueSet)dimension);
        List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition = this.getFormKeysCondition(context, workflowType);
        List<String> taskCode = this.getTaskCode(context.getFormSchemeKey(), param.getActionId(), taskNodeCode);
        Map<String, Set<String>> queryState = this.queryState(context.getFormSchemeKey(), dimension, dwMainDim, taskCode);
        Map<String, Set<String>> fliterForms = this.getFliterForms(dimension, dimensionSetList, dwMainDim, workflowType, context.getFormSchemeKey());
        Map<String, List<String>> selectFormOrGroupKeys = this.getDutyReadAuthDatas(param, formKeysCondition, workflowType, mainKey, dwMainDim);
        for (DimensionValueSet dimensionValue : dimensionSetList) {
            String unitKey = dimensionValue.getValue(dwMainDim).toString();
            Set<String> hasAuthFormOrGroupKeys = fliterForms.get(unitKey);
            Set<String> hasStateFormKeys = queryState.get(unitKey);
            List<String> hasSelectFormOrGroupKeys = selectFormOrGroupKeys.get(unitKey);
            if (hasStateFormKeys != null) {
                hasSelectFormOrGroupKeys.removeAll(hasStateFormKeys);
            }
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                if (hasStateFormKeys == null && hasSelectFormOrGroupKeys.size() >= hasAuthFormOrGroupKeys.size() || hasStateFormKeys != null && hasSelectFormOrGroupKeys != null && hasStateFormKeys.size() + hasSelectFormOrGroupKeys.size() >= hasAuthFormOrGroupKeys.size()) {
                    dimsForAllCheck.add(dimensionValue);
                    continue;
                }
                dimsForBatchCheck.add(dimensionValue);
                lastformKeysForBatchCheck.addAll(hasSelectFormOrGroupKeys);
                continue;
            }
            if (!WorkFlowType.GROUP.equals((Object)workflowType)) continue;
            if (hasStateFormKeys == null && hasSelectFormOrGroupKeys.size() >= hasAuthFormOrGroupKeys.size() || hasStateFormKeys != null && hasSelectFormOrGroupKeys != null && hasStateFormKeys.size() + hasSelectFormOrGroupKeys.size() >= hasAuthFormOrGroupKeys.size()) {
                dimsForAllCheck.add(dimensionValue);
                continue;
            }
            dimsForBatchCheck.add(dimensionValue);
            ArrayList<String> formKeys = new ArrayList<String>();
            for (String groupKey : hasSelectFormOrGroupKeys) {
                try {
                    List allForms = this.viewController.getAllFormsInGroup(groupKey);
                    for (FormDefine form : allForms) {
                        formKeys.add(form.getKey());
                    }
                }
                catch (Exception e) {
                    logger.info("\u62a5\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            lastformKeysForBatchCheck.addAll(formKeys);
        }
        if (dimsForAllCheck != null && dimsForAllCheck.size() > 0) {
            dimToForms.put(dimsForAllCheck, new ArrayList());
        }
        if (dimsForBatchCheck != null && dimsForBatchCheck.size() > 0) {
            dimToForms.put(dimsForBatchCheck, lastformKeysForBatchCheck);
        }
        return dimToForms;
    }

    private Map<String, Set<String>> getFliterForms(DimensionValueSet dimensionValueSet, List<DimensionValueSet> dimensionSetList, String dwDim, WorkFlowType workflowType, String formSchemeKey) {
        FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeKey);
        IConditionCache conditionCache = this.formConditionService.getConditionForms(dimensionValueSet, formSchemeKey);
        HashMap<String, Set<String>> hasAuthFormOrGroupKeys = new HashMap<String, Set<String>>();
        for (DimensionValueSet dimension : dimensionSetList) {
            Set<String> formOrGroupKeys = this.getReportKeyOrGroupKeys(formScheme, dimension, conditionCache, workflowType);
            String unitKey = dimension.getValue(dwDim).toString();
            hasAuthFormOrGroupKeys.put(unitKey, formOrGroupKeys);
        }
        return hasAuthFormOrGroupKeys;
    }

    private IConditionCache getConditionCache(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        IConditionCache conditionCache = this.formConditionService.getConditionForms(dimensionValueSet, formSchemeKey);
        return conditionCache;
    }

    private Set<String> getReportKeyOrGroupKeys(FormSchemeDefine fromScheme, DimensionValueSet dim, IConditionCache conditionCache, WorkFlowType workflowStartType) {
        HashSet<String> formOrGroupKeys = new HashSet<String>();
        DimensionValueSet defaultValue = this.setDefaultValue(fromScheme, dim);
        if (null == conditionCache) {
            conditionCache = this.formConditionService.getConditionForms(defaultValue, fromScheme.getKey());
        }
        formOrGroupKeys = WorkFlowType.FORM.equals((Object)workflowStartType) ? new HashSet(conditionCache.getSeeForms(defaultValue)) : new HashSet(conditionCache.getSeeFormGroups(defaultValue));
        return formOrGroupKeys;
    }

    private DimensionValueSet setDefaultValue(FormSchemeDefine formScheme, DimensionValueSet dimensionValueSet) {
        DimensionValueSet dim = new DimensionValueSet();
        DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            String dimTemp = dimensionSet.get(i);
            dim.setValue(dimTemp, dimensionValueSet.getValue(dimTemp));
        }
        String dims = formScheme.getDims();
        if (StringUtils.isNotEmpty((String)dims)) {
            String[] dimsArray;
            for (String dimKey : dimsArray = dims.split(";")) {
                String dimensionName = this.iEntityMetaService.getDimensionName(dimKey);
                if ("MD_CURRENCY".equals(dimensionName)) {
                    dim.setValue(dimensionName, (Object)"CNY");
                }
                if ("MD_GCADJTYPE".equals(dimensionName)) {
                    dim.setValue(dimensionName, (Object)"BEFOREADJ");
                }
                if (!"MD_GCORGTYPE".equals(dimensionName)) continue;
                dim.setValue(dimensionName, (Object)"MD_ORG_CORPORATE");
            }
        }
        return dim;
    }

    private Map<String, Set<String>> queryState(String formSchemeKey, DimensionValueSet dimensionValueSet, String unitDimName, List<String> nodeCodes) {
        HashMap<String, Set<String>> uploadUnit = new HashMap<String, Set<String>>();
        FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeKey);
        List uploadStates = this.batchQueryUploadStateService.queryUploadStateNew(formScheme, dimensionValueSet, null);
        HashSet<String> operateFormKeys = null;
        if (uploadStates != null && uploadStates.size() > 0) {
            for (UploadStateNew uploadStateNew : uploadStates) {
                DimensionValueSet entities = uploadStateNew.getEntities();
                String taskId = uploadStateNew.getTaskId();
                String formId = uploadStateNew.getFormId();
                ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
                if (actionStateBean == null) continue;
                String code = actionStateBean.getCode();
                if (UploadState.ORIGINAL.toString().equals(code) || UploadState.ORIGINAL_UPLOAD.toString().equals(code) || UploadState.ORIGINAL_SUBMIT.toString().equals(code) || UploadState.REJECTED.toString().equals(code) || UploadState.RETURNED.toString().equals(code) || UploadState.PART_START.toString().equals(code) || UploadState.PART_SUBMITED.toString().equals(code) || UploadState.PART_UPLOADED.toString().equals(code) || UploadState.PART_CONFIRMED.toString().equals(code) || !nodeCodes.contains(taskId)) continue;
                Object value = entities.getValue(unitDimName);
                Set formKeys = (Set)uploadUnit.get(value.toString());
                if (formKeys == null || formKeys.size() == 0) {
                    operateFormKeys = new HashSet<String>();
                    if (!"11111111-1111-1111-1111-111111111111".equals(formId)) {
                        operateFormKeys.add(formId);
                    }
                    uploadUnit.put(value.toString(), operateFormKeys);
                    continue;
                }
                Set set = (Set)uploadUnit.get(value.toString());
                if ("11111111-1111-1111-1111-111111111111".equals(formId)) continue;
                set.add(formId);
            }
        }
        return uploadUnit;
    }

    public Map<String, List<String>> getDutyReadAuthDatas(BatchExecuteTaskParam param, List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition, WorkFlowType workflowType, String mainKey, String mainDim) {
        List<String> selectFormKeys = param.getFormKeys();
        List<String> selectFormGroupKeys = param.getFormGroupKeys();
        Map<String, Set<String>> fliterFormKeyMap = this.unitToFormKeys(formKeysCondition, mainDim, workflowType);
        List<String> unitKeys = this.getAccessUnitKeys(formKeysCondition, mainDim);
        List<String> formKeys = this.getAccessFormKeys(formKeysCondition);
        HashMap<String, List<String>> hasSelectFromOrGroupKeys = new HashMap<String, List<String>>();
        Map selectFromOrGroupKeys = new HashMap();
        if (WorkFlowType.FORM.equals((Object)workflowType)) {
            if (selectFormKeys != null && selectFormKeys.size() > 0) {
                formKeys.retainAll(selectFormKeys);
            }
            selectFromOrGroupKeys = this.definitionAuthorityProvider.batchQueryCanReadFormWithDuty(formKeys, unitKeys, mainKey);
        }
        if (WorkFlowType.GROUP.equals((Object)workflowType)) {
            ArrayList<String> groupKeys = new ArrayList<String>();
            if (formKeys != null && formKeys.size() > 0) {
                for (String formKey : formKeys) {
                    List allFormGroupsByFormKey = this.viewController.getFormGroupsByFormKey(formKey);
                    for (FormGroupDefine groupDefine : allFormGroupsByFormKey) {
                        groupKeys.add(groupDefine.getKey());
                    }
                }
                if (selectFormGroupKeys != null && selectFormGroupKeys.size() > 0) {
                    groupKeys.retainAll(selectFormGroupKeys);
                }
            }
            selectFromOrGroupKeys = this.definitionAuthorityProvider.batchQueryCanReadFormGroupWithDuty(groupKeys, unitKeys, mainKey);
        }
        for (Map.Entry entry : selectFromOrGroupKeys.entrySet()) {
            String unitKey = (String)entry.getKey();
            Set<String> formKey = fliterFormKeyMap.get(unitKey);
            Map value = (Map)entry.getValue();
            ArrayList<String> formOrGroupKeys = new ArrayList<String>();
            for (Map.Entry entry2 : value.entrySet()) {
                String formOrGroupKey = (String)entry2.getKey();
                Boolean hasRead = (Boolean)entry2.getValue();
                if (!hasRead.booleanValue() || !formKey.contains(formOrGroupKey)) continue;
                formOrGroupKeys.add(formOrGroupKey);
            }
            hasSelectFromOrGroupKeys.put(unitKey, formOrGroupKeys);
        }
        return hasSelectFromOrGroupKeys;
    }

    public Map<String, Set<String>> unitToFormKeys(List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition, String mainDim, WorkFlowType workflowType) {
        LinkedHashMap<String, Set<String>> keys = new LinkedHashMap<String, Set<String>>();
        for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : formKeysCondition) {
            Map dimensions = accessFormInfo.getDimensions();
            List formKeys = accessFormInfo.getFormKeys();
            List dimensionValueSetList = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.getDimensionValueSetList((Map)dimensions);
            for (DimensionValueSet dimensionValueSet : dimensionValueSetList) {
                String unitKey = dimensionValueSet.getValue(mainDim).toString();
                LinkedHashSet formKeyTemps = (LinkedHashSet)keys.get(unitKey);
                if (formKeyTemps == null) {
                    formKeyTemps = new LinkedHashSet();
                    formKeyTemps.addAll(formKeys);
                    keys.put(unitKey, formKeyTemps);
                    continue;
                }
                ((Set)keys.get(unitKey)).addAll(formKeys);
            }
        }
        if (WorkFlowType.GROUP.equals((Object)workflowType)) {
            for (Map.Entry entry : keys.entrySet()) {
                String unit = (String)entry.getKey();
                Set value = (Set)entry.getValue();
                LinkedHashSet<String> groupKeyTemps = new LinkedHashSet<String>();
                for (String formKey : value) {
                    List allFormGroupsByFormKey = this.viewController.getFormGroupsByFormKey(formKey);
                    for (FormGroupDefine groupDefine : allFormGroupsByFormKey) {
                        groupKeyTemps.add(groupDefine.getKey());
                    }
                }
                keys.put(unit, groupKeyTemps);
            }
        }
        return keys;
    }

    public Map<String, Map<String, Boolean>> getDutyAuthDatas(List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition, WorkFlowType workflowType, String actionId, String mainKey, String mainDim) {
        List<String> unitKeys = this.getAccessUnitKeys(formKeysCondition, mainDim);
        List<String> formKeys = this.getAccessFormKeys(formKeysCondition);
        return this.getFormOrGroupKeysByDuty(unitKeys, formKeys, mainKey, actionId, workflowType);
    }

    private List<String> getAccessUnitKeys(List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition, String mainDim) {
        ArrayList<String> unitKeys = new ArrayList<String>();
        for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : formKeysCondition) {
            Map dimensions = accessFormInfo.getDimensions();
            if (dimensions == null) continue;
            for (DimensionValue value : dimensions.values()) {
                if (value.getValue() == null || !value.getName().equals(mainDim)) continue;
                String[] values = value.getValue().split(";");
                if (values.length == 1 || values.length == 0) {
                    unitKeys.add(value.getValue());
                    continue;
                }
                List<String> valueList = Arrays.asList(values);
                unitKeys.addAll(valueList);
            }
        }
        return unitKeys;
    }

    private List<String> getAccessFormKeys(List<DimensionAccessFormInfo.AccessFormInfo> formKeysCondition) {
        ArrayList<String> formKeys = new ArrayList<String>();
        for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : formKeysCondition) {
            List formKeys2 = accessFormInfo.getFormKeys();
            formKeys.addAll(formKeys2);
        }
        return formKeys;
    }

    private Map<String, Map<String, Boolean>> getFormOrGroupKeysByDuty(List<String> unitKeys, List<String> formKeys, String entityId, String actionCode, WorkFlowType workflowType) {
        if (WorkFlowType.FORM.equals((Object)workflowType)) {
            return this.getFormKeysByDuty(unitKeys, formKeys, entityId, actionCode, workflowType);
        }
        if (WorkFlowType.GROUP.equals((Object)workflowType)) {
            LinkedList<String> groupKeys = new LinkedList<String>();
            if (formKeys != null && formKeys.size() > 0) {
                for (String formKey : formKeys) {
                    List allFormGroupsByFormKey = this.viewController.getFormGroupsByFormKey(formKey);
                    for (FormGroupDefine groupDefine : allFormGroupsByFormKey) {
                        groupKeys.add(groupDefine.getKey());
                    }
                }
            }
            return this.getGroupKeysByDuty(unitKeys, groupKeys, entityId, actionCode, workflowType);
        }
        return new HashMap<String, Map<String, Boolean>>();
    }

    public Map<String, Map<String, Boolean>> getFormKeysByDuty(List<String> unitKeys, List<String> formKeys, String entityId, String actionCode, WorkFlowType workflowType) {
        if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode) || "act_return".equals(actionCode) || "act_apply_return".equals(actionCode) || "act_retrieve".equals(actionCode)) {
            Map sourceMap = this.definitionAuthorityProvider.batchQueryCanUploadFormWithDuty(formKeys, unitKeys, entityId);
            return this.convertToOrderMap(sourceMap, unitKeys, formKeys, formKeys, workflowType);
        }
        if ("act_submit".equals(actionCode) || "cus_submit".equals(actionCode) || "act_apply_return".equals(actionCode) || "act_retrieve".equals(actionCode)) {
            Map sourceMap = this.definitionAuthorityProvider.batchQueryCanSubmitFormWithDuty(formKeys, unitKeys, entityId);
            return this.convertToOrderMap(sourceMap, unitKeys, formKeys, formKeys, workflowType);
        }
        if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode) || "act_reject".equals(actionCode) || "cus_reject".equals(actionCode) || "act_cancel_confirm".equals(actionCode) || "act_apply_return".equals(actionCode) || "act_retrieve".equals(actionCode)) {
            Map sourceMap = this.definitionAuthorityProvider.batchQueryCanAuditFormWithDuty(formKeys, unitKeys, entityId);
            return this.convertToOrderMap(sourceMap, unitKeys, formKeys, formKeys, workflowType);
        }
        if ("cus_return".equals(actionCode)) {
            Map hasAuthUpload = this.definitionAuthorityProvider.batchQueryCanUploadFormWithDuty(formKeys, unitKeys, entityId);
            Map<String, Map<String, Boolean>> uplaodMap = this.convertToOrderMap(hasAuthUpload, unitKeys, formKeys, formKeys, workflowType);
            Map hasAuthSubmit = this.definitionAuthorityProvider.batchQueryCanSubmitFormWithDuty(formKeys, unitKeys, entityId);
            Map<String, Map<String, Boolean>> submitMap = this.convertToOrderMap(hasAuthSubmit, unitKeys, formKeys, formKeys, workflowType);
            Map hasAuthAudit = this.definitionAuthorityProvider.batchQueryCanAuditFormWithDuty(formKeys, unitKeys, entityId);
            Map<String, Map<String, Boolean>> auditMap = this.convertToOrderMap(hasAuthAudit, unitKeys, formKeys, formKeys, workflowType);
            return this.authGroup(uplaodMap, submitMap, auditMap);
        }
        return new HashMap<String, Map<String, Boolean>>();
    }

    public Map<String, Map<String, Boolean>> getGroupKeysByDuty(List<String> unitKeys, List<String> groupKeys, String entityId, String actionCode, WorkFlowType workflowType) {
        if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode) || "act_return".equals(actionCode)) {
            Map sourceMap = this.definitionAuthorityProvider.batchQueryCanUploadFormGroupWithDuty(groupKeys, unitKeys, entityId);
            return this.convertToOrderMap(sourceMap, unitKeys, groupKeys, groupKeys, workflowType);
        }
        if ("act_submit".equals(actionCode) || "cus_submit".equals(actionCode)) {
            Map sourceMap = this.definitionAuthorityProvider.batchQueryCanSubmitFormGroupWithDuty(groupKeys, unitKeys, entityId);
            return this.convertToOrderMap(sourceMap, unitKeys, groupKeys, groupKeys, workflowType);
        }
        if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode) || "act_reject".equals(actionCode) || "cus_reject".equals(actionCode) || "act_cancel_confirm".equals(actionCode)) {
            Map sourceMap = this.definitionAuthorityProvider.batchQueryCanAuditFormGroupWithDuty(groupKeys, unitKeys, entityId);
            return this.convertToOrderMap(sourceMap, unitKeys, groupKeys, groupKeys, workflowType);
        }
        if ("cus_return".equals(actionCode)) {
            Map hasAuthUpload = this.definitionAuthorityProvider.batchQueryCanUploadFormGroupWithDuty(groupKeys, unitKeys, entityId);
            Map<String, Map<String, Boolean>> uploadMap = this.convertToOrderMap(hasAuthUpload, unitKeys, groupKeys, groupKeys, workflowType);
            Map hasAuthSubmit = this.definitionAuthorityProvider.batchQueryCanSubmitFormGroupWithDuty(groupKeys, unitKeys, entityId);
            Map<String, Map<String, Boolean>> submitMap = this.convertToOrderMap(hasAuthSubmit, unitKeys, groupKeys, groupKeys, workflowType);
            Map hasAuthAudit = this.definitionAuthorityProvider.batchQueryCanAuditFormGroupWithDuty(groupKeys, unitKeys, entityId);
            Map<String, Map<String, Boolean>> auditMap = this.convertToOrderMap(hasAuthAudit, unitKeys, groupKeys, groupKeys, workflowType);
            return this.authGroup(uploadMap, submitMap, auditMap);
        }
        if ("act_apply_return".equals(actionCode) || "act_retrieve".equals(actionCode)) {
            Map hasAuthUpload = this.definitionAuthorityProvider.batchQueryCanUploadFormGroupWithDuty(groupKeys, unitKeys, entityId);
            Map<String, Map<String, Boolean>> uploadMap = this.convertToOrderMap(hasAuthUpload, unitKeys, groupKeys, groupKeys, workflowType);
            Map hasAuthSubmit = this.definitionAuthorityProvider.batchQueryCanSubmitFormGroupWithDuty(groupKeys, unitKeys, entityId);
            Map<String, Map<String, Boolean>> submitMap = this.convertToOrderMap(hasAuthSubmit, unitKeys, groupKeys, groupKeys, workflowType);
            Map hasAuthAudit = this.definitionAuthorityProvider.batchQueryCanAuditFormGroupWithDuty(groupKeys, unitKeys, entityId);
            Map<String, Map<String, Boolean>> auditMap = this.convertToOrderMap(hasAuthAudit, unitKeys, groupKeys, groupKeys, workflowType);
            return this.authGroup(uploadMap, submitMap, auditMap);
        }
        return new HashMap<String, Map<String, Boolean>>();
    }

    private Map<String, Map<String, Boolean>> convertToOrderMap(Map<String, Map<String, Boolean>> sourceMap, List<String> unitKeys, List<String> formKeys, List<String> groupKeys, WorkFlowType workflowType) {
        LinkedHashMap<String, Map<String, Boolean>> targetMap;
        block7: {
            block6: {
                targetMap = new LinkedHashMap<String, Map<String, Boolean>>();
                if (!WorkFlowType.FORM.equals((Object)workflowType)) break block6;
                LinkedHashMap<String, Boolean> formMap = new LinkedHashMap<String, Boolean>();
                for (String unitKey : unitKeys) {
                    for (String formKey : formKeys) {
                        if (targetMap.get(unitKey) == null) {
                            formMap = new LinkedHashMap();
                        }
                        formMap.put(formKey, sourceMap.get(unitKey).get(formKey));
                        targetMap.put(unitKey, formMap);
                    }
                }
                break block7;
            }
            if (!WorkFlowType.GROUP.equals((Object)workflowType)) break block7;
            LinkedHashMap<String, Boolean> groupMap = new LinkedHashMap<String, Boolean>();
            for (String unitKey : unitKeys) {
                for (String groupKey : groupKeys) {
                    if (targetMap.get(unitKey) == null) {
                        groupMap = new LinkedHashMap();
                    }
                    groupMap.put(groupKey, sourceMap.get(unitKey).get(groupKey));
                    targetMap.put(unitKey, groupMap);
                }
            }
        }
        return targetMap;
    }

    private Map<String, Map<String, Boolean>> authGroup(Map<String, Map<String, Boolean>> hasAuthUpload, Map<String, Map<String, Boolean>> hasAuthSubmit, Map<String, Map<String, Boolean>> hasAuthAudit) {
        LinkedHashMap<String, Map<String, Boolean>> temp = new LinkedHashMap<String, Map<String, Boolean>>();
        Set<String> unitKys = hasAuthUpload.keySet();
        for (String unitKey : unitKys) {
            Map<String, Boolean> hasUpload = hasAuthUpload.get(unitKey);
            Map<String, Boolean> hasSubmit = hasAuthSubmit.get(unitKey);
            Map<String, Boolean> hasAudit = hasAuthAudit.get(unitKey);
            Set<String> formKeysOrGroupKeys = hasUpload.keySet();
            for (String key : formKeysOrGroupKeys) {
                Boolean upload = hasUpload.get(key);
                Boolean submit = hasSubmit.get(key);
                Boolean audit = hasAudit.get(key);
                LinkedHashMap<String, Boolean> keys = (LinkedHashMap<String, Boolean>)temp.get(unitKey);
                if (keys == null) {
                    keys = new LinkedHashMap<String, Boolean>();
                    keys.put(key, upload);
                    temp.put(unitKey, keys);
                } else {
                    ((Map)temp.get(unitKey)).put(key, upload);
                }
                if (submit.booleanValue()) {
                    if (keys == null) {
                        keys = new LinkedHashMap();
                        keys.put(key, submit);
                        temp.put(unitKey, keys);
                    } else {
                        ((Map)temp.get(unitKey)).put(key, submit);
                    }
                }
                if (!audit.booleanValue()) continue;
                if (keys == null) {
                    keys = new LinkedHashMap();
                    keys.put(key, audit);
                    temp.put(unitKey, keys);
                    continue;
                }
                ((Map)temp.get(unitKey)).put(key, audit);
            }
        }
        return temp;
    }

    private List<DimensionAccessFormInfo.AccessFormInfo> getFormKeysCondition(JtableContext context, WorkFlowType startType) {
        ArrayList<String> formKeys = new ArrayList<String>();
        if (WorkFlowType.GROUP.equals((Object)startType)) {
            List<Object> formGroupKeys = new ArrayList();
            List allFormGroupsInFormScheme = this.viewController.getAllFormGroupsInFormScheme(context.getFormSchemeKey());
            formGroupKeys = allFormGroupsInFormScheme.stream().distinct().map(r -> r.getKey()).collect(Collectors.toList());
            for (String string : formGroupKeys) {
                List allFormsInGroup = null;
                try {
                    allFormsInGroup = this.viewController.getAllFormsInGroup(string);
                }
                catch (Exception e2) {
                    logger.error(e2.getMessage(), e2);
                }
                List<Object> keys = new ArrayList();
                if (allFormsInGroup != null) {
                    keys = allFormsInGroup.stream().map(e -> e.getKey()).collect(Collectors.toList());
                    keys.stream().distinct().collect(Collectors.toList());
                }
                formKeys.addAll(keys);
            }
        }
        return this.getAccessFormInfos(context, formKeys);
    }

    private List<DimensionAccessFormInfo.AccessFormInfo> getAccessFormInfos(JtableContext context, List<String> formKeys) {
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setTaskKey(context.getTaskKey());
        accessFormParam.setFormSchemeKey(context.getFormSchemeKey());
        if (formKeys == null) {
            accessFormParam.setFormKeys(new ArrayList());
        } else {
            accessFormParam.setFormKeys(formKeys);
        }
        Map dimensionSetMap = context.getDimensionSet();
        Map<String, DimensionValue> recombineDims = this.recombineDims(context.getFormSchemeKey(), dimensionSetMap);
        DimensionCollection dimensionCollection = com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection(recombineDims, (String)context.getFormSchemeKey());
        accessFormParam.setCollectionMasterKey(dimensionCollection);
        DimensionAccessFormInfo batchAccessForms = this.dataAccessFormService.getBatchAccessForms(accessFormParam);
        List accessForms = batchAccessForms.getAccessForms();
        return accessForms;
    }

    private Map<String, DimensionValue> recombineDims(String formSchemeKey, Map<String, DimensionValue> dimensionSetMap) {
        HashMap<String, DimensionValue> dimensionSetMapTemp = new HashMap<String, DimensionValue>();
        FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeKey);
        String dw = formScheme.getDw();
        IEntityDefine queryDimisionByView = this.queryDimisionByView(dw);
        if (queryDimisionByView != null) {
            String dimensionName = queryDimisionByView.getDimensionName();
            dimensionSetMapTemp.put(dimensionName, dimensionSetMap.get(dimensionName));
        }
        dimensionSetMapTemp.put("DATATIME", dimensionSetMap.get("DATATIME"));
        String dims = formScheme.getDims();
        String dimName = null;
        if (dims != null) {
            String[] dimKeys;
            for (String key : dimKeys = dims.split(";")) {
                if (!com.jiuqi.bi.util.StringUtils.isNotEmpty((String)key)) continue;
                IEntityDefine dimisionByView = this.queryDimisionByView(key);
                if (dimisionByView != null) {
                    dimName = dimisionByView.getDimensionName();
                }
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimName);
                dimensionValue.setValue("");
                dimensionSetMapTemp.put(dimName, dimensionValue);
            }
        }
        return dimensionSetMapTemp;
    }

    private IEntityDefine queryDimisionByView(String entityViewKey) {
        IEntityDefine entity = null;
        try {
            EntityViewDefine entityView = this.viewAdapter.buildEntityView(entityViewKey);
            if (entityView != null && entityView.getEntityId() != null) {
                entity = this.iEntityMetaService.queryEntity(entityView.getEntityId());
            }
        }
        catch (Exception e) {
            logger.error("\u67e5\u8be2\u7ef4\u5ea6\u51fa\u9519", e);
        }
        return entity;
    }

    public List<String> getTaskCode(String formSchemeKey, String actionCode, String taskNodeCode) {
        List<String> nodeCodes = new ArrayList<String>();
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        if (defaultWorkflow) {
            if ("act_submit".equals(actionCode)) {
                nodeCodes.add("tsk_upload");
                nodeCodes.add("tsk_audit");
                nodeCodes.add("tsk_audit_after_confirm");
                return nodeCodes;
            }
            if ("act_upload".equals(actionCode)) {
                nodeCodes.add("tsk_audit");
                nodeCodes.add("tsk_audit_after_confirm");
                return nodeCodes;
            }
        } else {
            List<List<String>> allNode = this.getAllNode(formSchemeKey);
            for (List<String> nodes : allNode) {
                if (!nodes.contains(taskNodeCode)) continue;
                int index = nodes.indexOf(taskNodeCode);
                nodeCodes = nodes.subList(index + 1, nodes.size());
            }
        }
        return nodeCodes;
    }

    private List<List<String>> getAllNode(String formSchemeKey) {
        ArrayList<List<String>> nodes = new ArrayList<List<String>>();
        WorkflowSettingDefine workflowSetting = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowSetting.getWorkflowId(), 1);
        List workflowNodes = this.customWorkFolwService.getWorkFlowNodeSets(workflowSetting.getWorkflowId(), 1);
        WorkFlowNodeSet startEvent = workflowNodes.stream().filter(e -> e.getId().contains("StartEvent")).findAny().get();
        List workflowLines = this.customWorkFolwService.getWorkflowLinesByPreTask(startEvent.getId(), workFlowDefine.getLinkid());
        Iterator iterator = workflowLines.iterator();
        while (iterator.hasNext()) {
            ArrayList<String> nodeSet = new ArrayList<String>();
            WorkFlowLine workflowLine = (WorkFlowLine)iterator.next();
            if (!nodeSet.contains(workflowLine.getAfterNodeID())) {
                nodeSet.add(workflowLine.getAfterNodeID());
                this.queryNextNode(nodeSet, workflowLine.getAfterNodeID(), workFlowDefine);
            }
            nodes.add(nodeSet);
        }
        return nodes;
    }

    private void queryNextNode(List<String> nodeSet, String currentNode, WorkFlowDefine workFlowDefine) {
        List workflowLines = this.customWorkFolwService.getWorkflowLinesByPreTask(currentNode, workFlowDefine.getLinkid());
        if (workflowLines != null && workflowLines.size() > 0) {
            for (WorkFlowLine workflowLine : workflowLines) {
                if (nodeSet.contains(workflowLine.getAfterNodeID())) continue;
                nodeSet.add(workflowLine.getAfterNodeID());
                this.queryNextNode(nodeSet, workflowLine.getAfterNodeID(), workFlowDefine);
            }
        }
    }
}

