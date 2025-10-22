/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.setting.service.impl;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitToFormOrGroupMapUtils {
    @Resource
    private IRuntimeDataSchemeService dataSchemeService;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private DataPermissionEvaluatorFactory permissionEvaluatorFactory;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    public IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private WorkflowReportDimService workflowReportDimService;

    public Map<String, List<String>> mapToForms(BuildParam param) {
        HashMap<String, List<String>> toFormIds = new HashMap<String, List<String>>();
        Map<String, List<FormDefine>> toFormDefines = this.mapToFormDefines(param);
        for (Map.Entry<String, List<FormDefine>> entry : toFormDefines.entrySet()) {
            List formIds = entry.getValue().stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            Set<String> mergeIds = param.getMergeIds();
            if (mergeIds != null && !mergeIds.isEmpty()) {
                formIds.addAll(mergeIds);
            }
            toFormIds.put(entry.getKey(), formIds);
        }
        return toFormIds;
    }

    public Map<String, List<String>> mapToGroups(BuildParam param) throws Exception {
        HashMap<String, List<String>> toGroupIds = new HashMap<String, List<String>>();
        Map<String, List<FormGroupDefine>> toGroupDefines = this.mapToGroupDefines(param);
        for (Map.Entry<String, List<FormGroupDefine>> entry : toGroupDefines.entrySet()) {
            List groupIds = entry.getValue().stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            Set<String> mergeIds = param.getMergeIds();
            if (mergeIds != null && !mergeIds.isEmpty()) {
                groupIds.addAll(mergeIds);
            }
            toGroupIds.put(entry.getKey(), groupIds);
        }
        return toGroupIds;
    }

    public Map<String, List<FormDefine>> mapToFormDefines(BuildParam param) {
        HashMap<String, List<FormDefine>> toForms = new HashMap<String, List<FormDefine>>();
        Set<String> formIds = param.getFormIds();
        List formDefines = param.checkAllForm ? this.runTimeViewController.queryAllFormDefinesByFormScheme(param.getFormSchemeKey()) : formIds.stream().map(formId -> this.runTimeViewController.queryFormById(formId)).collect(Collectors.toList());
        DimensionCollection dimensionCollection = this.buildDimensionCollection(param);
        EvaluatorParam evaluatorParam = new EvaluatorParam();
        evaluatorParam.setFormSchemeId(param.formSchemeKey);
        DataPermissionEvaluator evaluator = this.permissionEvaluatorFactory.createEvaluator(evaluatorParam, dimensionCollection, (Collection)formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        for (DimensionCombination masterKeys : dimensionCombinations) {
            String unitId = masterKeys.getDWDimensionValue().getValue().toString();
            List forms = toForms.computeIfAbsent(unitId, k -> new ArrayList());
            for (FormDefine formDefine : formDefines) {
                if (!evaluator.haveAccess(masterKeys, formDefine.getKey(), AuthType.READABLE)) continue;
                forms.add(formDefine);
            }
        }
        return toForms;
    }

    public Map<String, List<FormGroupDefine>> mapToGroupDefines(BuildParam param) throws Exception {
        HashMap formToGroupMap = new HashMap();
        List groupsInFormScheme = param.isCheckAllForm() ? this.runTimeViewController.getAllFormGroupsInFormScheme(param.getFormSchemeKey()) : param.formIds.stream().map(groupId -> this.runTimeViewController.queryFormGroup(groupId)).collect(Collectors.toList());
        param.setCheckAllForm(false);
        param.formIds = new HashSet();
        for (FormGroupDefine groupDefine : groupsInFormScheme) {
            List allFormsInGroup = this.runTimeViewController.getAllFormsInGroup(groupDefine.getKey());
            allFormsInGroup.forEach(form -> {
                formToGroupMap.put(form.getKey(), groupDefine);
                param.formIds.add(form.getKey());
            });
        }
        HashMap<String, List<FormGroupDefine>> toGroupMap = new HashMap<String, List<FormGroupDefine>>();
        Map<String, List<FormDefine>> toFormMap = this.mapToFormDefines(param);
        for (Map.Entry<String, List<FormDefine>> entry : toFormMap.entrySet()) {
            String unitId = entry.getKey();
            List groups = toGroupMap.computeIfAbsent(unitId, k -> new ArrayList());
            List<FormDefine> forms = entry.getValue();
            for (FormDefine formDefine : forms) {
                FormGroupDefine groupDefine = (FormGroupDefine)formToGroupMap.get(formDefine.getKey());
                if (!groups.stream().noneMatch(group -> group.getKey().equals(groupDefine.getKey()))) continue;
                groups.add(groupDefine);
            }
        }
        return toGroupMap;
    }

    private DimensionCollection buildDimensionCollection(BuildParam buildParam) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(buildParam.getFormSchemeKey());
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        List<DataDimension> dataSchemeDimension = this.workflowReportDimService.getDataDimension(formScheme.getTaskKey());
        DimensionCollectionBuilder dimensionBuilder = new DimensionCollectionBuilder();
        for (DataDimension dimension : dataSchemeDimension) {
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if ("ADJUST".equals(dimensionName) && StringUtils.isNotEmpty((String)buildParam.adjustId)) {
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{buildParam.adjustId});
                continue;
            }
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                if (buildParam.checkAllUnit) {
                    DimensionProviderData providerData = new DimensionProviderData();
                    providerData.setDataSchemeKey(taskDefine.getDataScheme());
                    providerData.setAuthorityType(AuthorityType.Read);
                    VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", providerData);
                    dimensionBuilder.addVariableDW(dimensionName, dimension.getDimKey(), dimensionProvider);
                    continue;
                }
                dimensionBuilder.setDWValue(dimensionName, dimension.getDimKey(), new Object[]{new ArrayList(buildParam.unitIds)});
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{buildParam.period});
                continue;
            }
            if (DimensionType.DIMENSION != dimension.getDimensionType()) continue;
        }
        return dimensionBuilder.getCollection();
    }

    private String getDimensionName(String entityId) {
        if (this.periodEntityAdapter.isPeriodEntity(entityId)) {
            return this.periodEntityAdapter.getPeriodEntity(entityId).getDimensionName();
        }
        if ("ADJUST".equals(entityId)) {
            return "ADJUST";
        }
        return this.entityMetaService.queryEntity(entityId).getDimensionName();
    }

    private String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }

    public static final class BuildParam {
        private String period;
        private String adjustId;
        private String formSchemeKey;
        private boolean checkAllUnit;
        private Set<String> unitIds;
        private boolean checkAllForm;
        private Set<String> formIds;
        private Set<String> mergeIds;

        public String getPeriod() {
            return this.period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getFormSchemeKey() {
            return this.formSchemeKey;
        }

        public void setFormSchemeKey(String formSchemeKey) {
            this.formSchemeKey = formSchemeKey;
        }

        public Set<String> getUnitIds() {
            return this.unitIds;
        }

        public void setUnitIds(Set<String> unitIds) {
            this.unitIds = unitIds;
        }

        public Set<String> getFormIds() {
            return this.formIds;
        }

        public void setFormIds(Set<String> formIds) {
            this.formIds = formIds;
        }

        public String getAdjustId() {
            return this.adjustId;
        }

        public void setAdjustId(String adjustId) {
            this.adjustId = adjustId;
        }

        public Set<String> getMergeIds() {
            return this.mergeIds;
        }

        public void setMergeIds(Set<String> mergeIds) {
            this.mergeIds = mergeIds;
        }

        public boolean isCheckAllUnit() {
            return this.checkAllUnit;
        }

        public void setCheckAllUnit(boolean checkAllUnit) {
            this.checkAllUnit = checkAllUnit;
        }

        public boolean isCheckAllForm() {
            return this.checkAllForm;
        }

        public void setCheckAllForm(boolean checkAllForm) {
            this.checkAllForm = checkAllForm;
        }
    }
}

