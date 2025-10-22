/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.data.engine.condition.IFormConditionService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.bpm.de.dataflow.step.provide;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.dataflow.service.IDataentryQueryStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.step.inter.IQueryState;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DeEntityQueryManager;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.IFormConditionService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StepQueryState
implements IQueryState {
    private String formSchemeKey;
    private DimensionValueSet sourceDim;
    private String period;
    private CommonUtil commonUtil;
    private DimensionUtil dimensionUtil;
    private IFormConditionService formConditionService;
    private WorkFlowType startType;
    private IDataentryQueryStateService dataentryQueryStateService;
    private WorkflowReportDimService workflowReportDimService;
    private SingleFormRejectService singleFormRejectService;
    private String dwMainDimName;
    private DeEntityQueryManager entityManager;

    public StepQueryState() {
    }

    public StepQueryState(String formSchemeKey, DimensionValueSet sourceDim, String period, WorkFlowType startType, CommonUtil commonUtil, DimensionUtil dimensionUtil, IFormConditionService formConditionService, IDataentryQueryStateService dataentryQueryStateService, WorkflowReportDimService workflowReportDimService, SingleFormRejectService singleFormRejectService, DeEntityQueryManager entityManager) {
        this.formSchemeKey = formSchemeKey;
        this.sourceDim = sourceDim;
        this.period = period;
        this.startType = startType;
        this.commonUtil = commonUtil;
        this.dimensionUtil = dimensionUtil;
        this.formConditionService = formConditionService;
        this.dataentryQueryStateService = dataentryQueryStateService;
        this.workflowReportDimService = workflowReportDimService;
        this.singleFormRejectService = singleFormRejectService;
        this.entityManager = entityManager;
        this.dwMainDimName = dimensionUtil.getDwMainDimName(formSchemeKey);
    }

    @Override
    public ActionStateBean queryAllState(DimensionValueSet dimensionValue, List<String> formKeys, List<String> groupKeys) {
        DataEntryParam dataEntryParam = new DataEntryParam();
        this.setReportDimension(dimensionValue);
        dataEntryParam.setDim(dimensionValue);
        dataEntryParam.setFormSchemeKey(this.formSchemeKey);
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(this.commonUtil.formId(this.formSchemeKey));
        List<String> formOrGroupKey = WorkFlowType.FORM.equals((Object)this.startType) ? formKeys : (WorkFlowType.GROUP.equals((Object)this.startType) ? groupKeys : ids);
        dataEntryParam.setFormKeys(formOrGroupKey);
        dataEntryParam.setGroupKeys(formOrGroupKey);
        return this.dataentryQueryStateService.queryUnitState(dataEntryParam);
    }

    @Override
    public ActionStateBean queryOnlyState(DimensionValueSet dimensionValue, String formKey, String groupKey) {
        DataEntryParam dataEntryParam = new DataEntryParam();
        this.setReportDimension(dimensionValue);
        dataEntryParam.setDim(dimensionValue);
        dataEntryParam.setFormSchemeKey(this.formSchemeKey);
        String formOrGroupKey = WorkFlowType.FORM.equals((Object)this.startType) ? formKey : (WorkFlowType.GROUP.equals((Object)this.startType) ? groupKey : this.commonUtil.formId(this.formSchemeKey));
        dataEntryParam.setFormKey(formOrGroupKey);
        dataEntryParam.setGroupKey(formOrGroupKey);
        return this.dataentryQueryStateService.queryResourceState(dataEntryParam);
    }

    public Map<IEntityRow, ActionStateBean> queryDirectState(List<IEntityRow> childRows, Set<String> resourceIds) {
        HashMap<IEntityRow, ActionStateBean> actionMap;
        block5: {
            Map<String, Boolean> unitBooleanMap;
            block4: {
                actionMap = new HashMap<IEntityRow, ActionStateBean>();
                unitBooleanMap = this.queryFormRejectDatas(childRows);
                if (WorkFlowType.ENTITY.equals((Object)this.startType)) break block4;
                IConditionCache conditionCache = this.getConditionCache(childRows);
                if (childRows == null || childRows.size() <= 0) break block5;
                for (IEntityRow child : childRows) {
                    String id = child.getEntityKeyData();
                    DimensionValueSet childrenDim = new DimensionValueSet();
                    childrenDim.setValue("DATATIME", (Object)this.period);
                    childrenDim.setValue(this.dwMainDimName, (Object)id);
                    List<String> formOrGroupKeys = this.getFormOrGroupKeys(conditionCache, childrenDim);
                    for (String resourceid : resourceIds) {
                        ActionStateBean actionStateBean;
                        if (!formOrGroupKeys.contains(resourceid) || (actionStateBean = this.uploadState(childrenDim, resourceid, this.formSchemeKey, unitBooleanMap)) == null) continue;
                        actionMap.put(child, actionStateBean);
                    }
                }
                break block5;
            }
            if (childRows != null && childRows.size() > 0) {
                for (IEntityRow child : childRows) {
                    String id = child.getEntityKeyData();
                    DimensionValueSet childrenDim = new DimensionValueSet();
                    childrenDim.setValue("DATATIME", (Object)this.period);
                    childrenDim.setValue(this.dwMainDimName, (Object)id);
                    ActionStateBean actionStateBean = this.uploadState(childrenDim, null, this.formSchemeKey, unitBooleanMap);
                    if (actionStateBean == null) continue;
                    actionMap.put(child, actionStateBean);
                }
            }
        }
        return actionMap;
    }

    private IConditionCache getConditionCache(List<IEntityRow> childRows) {
        IConditionCache conditionCache = null;
        if (childRows != null && childRows.size() > 0) {
            List unitIds = childRows.stream().map(e -> e.getEntityKeyData()).collect(Collectors.toList());
            DimensionValueSet dim = new DimensionValueSet();
            dim.setValue(this.dwMainDimName, new ArrayList(unitIds));
            dim.setValue("DATATIME", (Object)this.period);
            DimensionValueSet defaultValue = this.setDefaultValue(dim);
            conditionCache = this.formConditionService.getConditionForms(defaultValue, this.formSchemeKey);
        }
        return conditionCache;
    }

    private List<String> getFormOrGroupKeys(IConditionCache conditionCache, DimensionValueSet dimensionValueSet) {
        ArrayList<String> fromOrGroupKeys;
        block3: {
            DimensionValueSet defaultValue;
            block2: {
                fromOrGroupKeys = new ArrayList<String>();
                defaultValue = this.setDefaultValue(dimensionValueSet);
                if (!WorkFlowType.FORM.equals((Object)this.startType)) break block2;
                List forms = conditionCache.getSeeForms(defaultValue);
                for (String formKey : forms) {
                    fromOrGroupKeys.add(formKey);
                }
                break block3;
            }
            if (!WorkFlowType.GROUP.equals((Object)this.startType)) break block3;
            List formGroups = conditionCache.getSeeFormGroups(defaultValue);
            for (String formGroupKey : formGroups) {
                fromOrGroupKeys.add(formGroupKey);
            }
        }
        return fromOrGroupKeys;
    }

    public Map<IEntityRow, Map<String, ActionStateBean>> queryState(List<IEntityRow> childRows, Set<String> resourceIds) {
        HashMap<IEntityRow, Map<String, ActionStateBean>> actionMap;
        block10: {
            Map<String, Boolean> unitBooleanMap;
            HashMap<String, ActionStateBean> action;
            block9: {
                actionMap = new HashMap<IEntityRow, Map<String, ActionStateBean>>();
                action = null;
                unitBooleanMap = this.queryFormRejectDatas(childRows);
                if (!WorkFlowType.ENTITY.equals((Object)this.startType)) break block9;
                if (childRows == null || childRows.size() <= 0) break block10;
                for (IEntityRow child : childRows) {
                    String id = child.getEntityKeyData();
                    DimensionValueSet childrenDim = new DimensionValueSet();
                    childrenDim.setValue("DATATIME", (Object)this.period);
                    childrenDim.setValue(this.dwMainDimName, (Object)id);
                    if (resourceIds != null && resourceIds.size() > 0) {
                        for (String resourceid : resourceIds) {
                            ActionStateBean actionStateBean = this.uploadState(childrenDim, resourceid, this.formSchemeKey, unitBooleanMap);
                            if (actionStateBean == null) continue;
                            if (actionMap.get(child) == null) {
                                action = new HashMap();
                                action.put(resourceid, actionStateBean);
                                actionMap.put(child, action);
                                continue;
                            }
                            ((Map)actionMap.get(child)).put(resourceid, actionStateBean);
                        }
                        continue;
                    }
                    ActionStateBean actionStateBean = this.uploadState(childrenDim, null, this.formSchemeKey, unitBooleanMap);
                    if (actionStateBean == null) continue;
                    if (actionMap.get(child) == null) {
                        action = new HashMap<String, ActionStateBean>();
                        action.put(child.getEntityKeyData(), actionStateBean);
                        actionMap.put(child, action);
                        continue;
                    }
                    ((Map)actionMap.get(child)).put(child.getEntityKeyData(), actionStateBean);
                }
                break block10;
            }
            if (childRows != null && childRows.size() > 0) {
                IConditionCache conditionCache = this.getConditionCache(childRows);
                for (IEntityRow child : childRows) {
                    String id = child.getEntityKeyData();
                    DimensionValueSet childrenDim = new DimensionValueSet();
                    childrenDim.setValue("DATATIME", (Object)this.period);
                    childrenDim.setValue(this.dwMainDimName, (Object)id);
                    List<String> formOrGroupKeys = this.getFormOrGroupKeys(conditionCache, childrenDim);
                    for (String resourceid : resourceIds) {
                        ActionStateBean actionStateBean;
                        if (!formOrGroupKeys.contains(resourceid) || (actionStateBean = this.uploadState(childrenDim, resourceid, this.formSchemeKey, unitBooleanMap)) == null) continue;
                        if (actionMap.get(child) == null) {
                            action = new HashMap();
                            action.put(resourceid, actionStateBean);
                            actionMap.put(child, action);
                            continue;
                        }
                        ((Map)actionMap.get(child)).put(resourceid, actionStateBean);
                    }
                }
            }
        }
        return actionMap;
    }

    public Map<IEntityRow, ActionStateBean> queryParentState(List<IEntityRow> childRows, Set<String> resourceIds) {
        LinkedHashMap<IEntityRow, ActionStateBean> actionMap;
        block6: {
            block5: {
                actionMap = new LinkedHashMap<IEntityRow, ActionStateBean>();
                if (!WorkFlowType.ENTITY.equals((Object)this.startType)) break block5;
                if (childRows == null || childRows.size() <= 0) break block6;
                for (IEntityRow child : childRows) {
                    String id = child.getEntityKeyData();
                    DimensionValueSet childrenDim = new DimensionValueSet();
                    childrenDim.setValue("DATATIME", (Object)this.period);
                    childrenDim.setValue(this.dwMainDimName, (Object)id);
                    for (String resourceid : resourceIds) {
                        ActionStateBean actionStateBean = this.backState(childrenDim, resourceid, this.formSchemeKey);
                        actionMap.put(child, actionStateBean);
                    }
                }
                break block6;
            }
            if (childRows != null && childRows.size() > 0) {
                IConditionCache conditionCache = this.getConditionCache(childRows);
                for (IEntityRow child : childRows) {
                    String id = child.getEntityKeyData();
                    DimensionValueSet childrenDim = new DimensionValueSet();
                    childrenDim.setValue("DATATIME", (Object)this.period);
                    childrenDim.setValue(this.dwMainDimName, (Object)id);
                    List<String> formOrGroupKeys = this.getFormOrGroupKeys(conditionCache, childrenDim);
                    for (String resourceid : resourceIds) {
                        if (!formOrGroupKeys.contains(resourceid)) continue;
                        ActionStateBean actionStateBean = this.backState(childrenDim, resourceid, this.formSchemeKey);
                        actionMap.put(child, actionStateBean);
                    }
                }
            }
        }
        return actionMap;
    }

    public Map<IEntityRow, Map<String, ActionStateBean>> queryParent(List<IEntityRow> parentData, Set<String> resourceIds) {
        LinkedHashMap<IEntityRow, Map<String, ActionStateBean>> actionMap;
        block10: {
            LinkedHashMap<String, ActionStateBean> resourceMap;
            block9: {
                actionMap = new LinkedHashMap<IEntityRow, Map<String, ActionStateBean>>();
                resourceMap = null;
                if (!WorkFlowType.ENTITY.equals((Object)this.startType)) break block9;
                if (parentData == null || parentData.size() <= 0) break block10;
                for (IEntityRow child : parentData) {
                    String id = child.getEntityKeyData();
                    DimensionValueSet childrenDim = new DimensionValueSet();
                    childrenDim.setValue("DATATIME", (Object)this.period);
                    childrenDim.setValue(this.dwMainDimName, (Object)id);
                    if (resourceIds != null && resourceIds.size() > 0) {
                        for (String resourceid : resourceIds) {
                            ActionStateBean actionStateBean = this.backState(childrenDim, resourceid, this.formSchemeKey);
                            if (actionStateBean == null) continue;
                            if (actionMap.get(child) == null) {
                                resourceMap = new LinkedHashMap();
                                resourceMap.put(resourceid, actionStateBean);
                                actionMap.put(child, resourceMap);
                                continue;
                            }
                            ((Map)actionMap.get(child)).put(resourceid, actionStateBean);
                        }
                        continue;
                    }
                    ActionStateBean actionStateBean = this.backState(childrenDim, null, this.formSchemeKey);
                    if (actionStateBean == null) continue;
                    if (actionMap.get(child) == null) {
                        resourceMap = new LinkedHashMap<String, ActionStateBean>();
                        resourceMap.put(child.getEntityKeyData(), actionStateBean);
                        actionMap.put(child, resourceMap);
                        continue;
                    }
                    ((Map)actionMap.get(child)).put(child.getEntityKeyData(), actionStateBean);
                }
                break block10;
            }
            if (parentData != null && parentData.size() > 0) {
                IConditionCache conditionCache = this.getConditionCache(parentData);
                for (IEntityRow child : parentData) {
                    String id = child.getEntityKeyData();
                    DimensionValueSet childrenDim = new DimensionValueSet();
                    childrenDim.setValue("DATATIME", (Object)this.period);
                    childrenDim.setValue(this.dwMainDimName, (Object)id);
                    List<String> formOrGroupKeys = this.getFormOrGroupKeys(conditionCache, childrenDim);
                    for (String resourceid : resourceIds) {
                        ActionStateBean actionStateBean;
                        if (!formOrGroupKeys.contains(resourceid) || (actionStateBean = this.backState(childrenDim, resourceid, this.formSchemeKey)) == null) continue;
                        if (actionMap.get(child) == null) {
                            resourceMap = new LinkedHashMap();
                            resourceMap.put(resourceid, actionStateBean);
                            actionMap.put(child, resourceMap);
                            continue;
                        }
                        ((Map)actionMap.get(child)).put(resourceid, actionStateBean);
                    }
                }
            }
        }
        return actionMap;
    }

    public ActionStateBean uploadState(DimensionValueSet dimensionValueSet, String resourceid, String formSchemeKey, Map<String, Boolean> unitBooleanMap) {
        FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        String unitKey = dimensionValueSet.getValue(this.dwMainDimName).toString();
        ActionStateBean state = this.queryOnlyState(dimensionValueSet, resourceid, resourceid);
        if (state != null && state.getCode() != null) {
            if (this.commonUtil.isDefaultWorkflow(formSchemeKey)) {
                String stepReportType = formScheme.getFlowsSetting().getStepReportType();
                if ("1".equals(stepReportType)) {
                    if (UploadState.UPLOADED.toString().equals(state.getCode()) || UploadState.CONFIRMED.toString().equals(state.getCode())) {
                        if (unitBooleanMap.size() > 0 && unitBooleanMap.containsKey(unitKey) && unitBooleanMap.get(unitKey).booleanValue() && flowsSetting.isAllowFormBack()) {
                            ActionStateBean actionStateBean = new ActionStateBean();
                            actionStateBean.setCode("single_form_reject");
                            actionStateBean.setTitile("\u5355\u8868\u9a73\u56de");
                            return actionStateBean;
                        }
                        return null;
                    }
                    return state;
                }
                if (UploadState.CONFIRMED.toString().equals(state.getCode())) {
                    if (unitBooleanMap.size() > 0 && unitBooleanMap.containsKey(unitKey) && unitBooleanMap.get(unitKey).booleanValue() && flowsSetting.isAllowFormBack()) {
                        ActionStateBean actionStateBean = new ActionStateBean();
                        actionStateBean.setCode("single_form_reject");
                        actionStateBean.setTitile("\u5355\u8868\u9a73\u56de");
                        return actionStateBean;
                    }
                    return null;
                }
                return state;
            }
            if (UploadState.UPLOADED.toString().equals(state.getCode()) || UploadState.CONFIRMED.toString().equals(state.getCode())) {
                if (unitBooleanMap.size() > 0 && unitBooleanMap.containsKey(unitKey) && unitBooleanMap.get(unitKey).booleanValue() && flowsSetting.isAllowFormBack()) {
                    ActionStateBean actionStateBean = new ActionStateBean();
                    actionStateBean.setCode("single_form_reject");
                    actionStateBean.setTitile("\u5355\u8868\u9a73\u56de");
                    return actionStateBean;
                }
                return null;
            }
            return state;
        }
        return state;
    }

    public ActionStateBean backState(DimensionValueSet dimensionValueSet, String resourceid, String formSchemeKey) {
        ActionStateBean state = this.queryOnlyState(dimensionValueSet, resourceid, resourceid);
        if (state != null && state.getCode() != null) {
            if (this.commonUtil.isDefaultWorkflow(formSchemeKey)) {
                if (UploadState.ORIGINAL_UPLOAD.toString().equals(state.getCode()) || UploadState.ORIGINAL_SUBMIT.toString().equals(state.getCode()) || UploadState.RETURNED.toString().equals(state.getCode()) || UploadState.SUBMITED.toString().equals(state.getCode()) || UploadState.REJECTED.toString().equals(state.getCode())) {
                    return null;
                }
                return state;
            }
            if (UploadState.ORIGINAL_UPLOAD.toString().equals(state.getCode()) || UploadState.ORIGINAL_SUBMIT.toString().equals(state.getCode()) || UploadState.RETURNED.toString().equals(state.getCode()) || UploadState.REJECTED.toString().equals(state.getCode())) {
                return null;
            }
            return state;
        }
        return state;
    }

    private DimensionValueSet setDefaultValue(DimensionValueSet dimensionValueSet) {
        DimensionValueSet dim = new DimensionValueSet();
        DimensionSet dimensionSet = dimensionValueSet.getDimensionSet();
        for (int i = 0; i < dimensionSet.size(); ++i) {
            String dimTemp = dimensionSet.get(i);
            dim.setValue(dimTemp, dimensionValueSet.getValue(dimTemp));
        }
        dim.setValue("MD_CURRENCY", (Object)"CNY");
        dim.setValue("MD_GCADJTYPE", (Object)"BEFOREADJ");
        dim.setValue("MD_GCORGTYPE", (Object)"MD_ORG_CORPORATE");
        return dim;
    }

    private void setReportDimension(DimensionValueSet targetDim) {
        IEntityMetaService entityMetaService = (IEntityMetaService)SpringBeanUtils.getBean(IEntityMetaService.class);
        FormSchemeDefine formScheme = this.commonUtil.getFormScheme(this.formSchemeKey);
        List<DataDimension> dataSchemeDimension = this.workflowReportDimService.getReportDimension(formScheme.getTaskKey());
        for (DataDimension dimension : dataSchemeDimension) {
            IEntityAttribute attribute;
            String code;
            String value;
            String dimKey = dimension.getDimKey();
            String dimensionName = this.dimensionUtil.getDimensionName(dimKey);
            if ("ADJUST".equals(dimensionName)) {
                Object value2 = this.sourceDim.getValue(dimensionName);
                if (value2 == null) continue;
                targetDim.setValue(dimensionName, value2);
                continue;
            }
            IEntityModel dwEntityModel = entityMetaService.getEntityModel(formScheme.getDw());
            String dimAttribute = dimension.getDimAttribute();
            if (dimAttribute == null || (value = this.getValue(this.formSchemeKey, targetDim, code = (attribute = dwEntityModel.getAttribute(dimAttribute)).getCode())) == null) continue;
            targetDim.setValue(dimensionName, (Object)value);
        }
    }

    private String getValue(String formSchemeKey, DimensionValueSet dimensionValue, String code) {
        String unitKey = dimensionValue.getValue(this.dwMainDimName).toString();
        EntityViewDefine dwEntityView = this.dimensionUtil.getDwEntityView(formSchemeKey);
        IEntityTable iEntityTable = this.entityManager.entityQuerySet(dwEntityView, dimensionValue, formSchemeKey, AuthorityType.None);
        IEntityRow entityRow = iEntityTable.findByEntityKey(unitKey);
        AbstractData value = entityRow.getValue(code);
        return value == null ? null : value.getAsString();
    }

    private Map<String, Boolean> queryFormRejectDatas(List<IEntityRow> datas) {
        HashSet<String> unitKeySet = new HashSet<String>();
        if (datas != null && datas.size() > 0) {
            for (IEntityRow row : datas) {
                String entityKeyData = row.getEntityKeyData();
                unitKeySet.add(entityKeyData);
            }
        }
        ArrayList<String> unitKeys = new ArrayList<String>();
        if (unitKeySet != null && unitKeySet.size() > 0) {
            unitKeys.addAll(unitKeySet);
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue(this.dwMainDimName, unitKeys);
        dimensionValueSet.setValue("DATATIME", (Object)this.period);
        return this.singleFormRejectService.queryRejectFromDatas(dimensionValueSet, this.formSchemeKey);
    }
}

