/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.data.access.param.IBatchAccess
 *  com.jiuqi.nr.data.access.service.IFormConditionAccessService
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.access.AuthType
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.dataservice.core.access.EvaluatorParam
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.service.helper;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.data.access.param.IBatchAccess;
import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.access.AuthType;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluator;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.access.EvaluatorParam;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.service.dimension.BusinessKeyObjectMapper;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import com.jiuqi.nr.workflow2.service.para.ProcessOneExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessDimensionsBuilder
implements IProcessDimensionsBuilder {
    @Autowired
    private WorkflowSettingsService settingService;
    @Autowired
    private IRunTimeViewController modelDefineService;
    @Autowired
    private IReportDimensionHelper reportDimensionHelper;
    @Autowired
    private IProcessRuntimeParamHelper runtimeParamHelper;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private DataPermissionEvaluatorFactory permissionEvaluatorFactory;
    @Autowired
    private IProcessEntityQueryHelper processEntityQueryHelper;
    @Autowired
    private IFormConditionAccessService formConditionAccessService;

    @Override
    public IBusinessKey buildBusinessKey(ProcessOneRunPara runPara) {
        DimensionCombination combination = this.buildDimensionCombination(runPara);
        WorkflowObjectType flowObjectType = this.settingService.queryTaskWorkflowObjectType(runPara.getTaskKey());
        if (WorkflowObjectType.FORM_GROUP == flowObjectType) {
            ProcessOneDim dimensionValue = runPara.getOneReportDimension(EProcessDimensionName.PROCESS_GROUP.dimName);
            FormGroupObject formGroupObject = new FormGroupObject(combination, Objects.requireNonNull(dimensionValue).getDimensionValue());
            return new BusinessKey(runPara.getTaskKey(), (IBusinessObject)formGroupObject);
        }
        if (WorkflowObjectType.FORM == flowObjectType) {
            ProcessOneDim dimensionValue = runPara.getOneReportDimension(EProcessDimensionName.PROCESS_FORM.dimName);
            FormObject formObject = new FormObject(combination, Objects.requireNonNull(dimensionValue).getDimensionValue());
            return new BusinessKey(runPara.getTaskKey(), (IBusinessObject)formObject);
        }
        return new BusinessKey(runPara.getTaskKey(), (IBusinessObject)new DimensionObject(combination));
    }

    @Override
    public IBusinessKey buildBusinessKey(ProcessOneExecutePara runPara) {
        ProcessOneRunPara processOneRunPara = new ProcessOneRunPara();
        processOneRunPara.setTaskKey(runPara.getTaskKey());
        processOneRunPara.setPeriod(runPara.getPeriod());
        processOneRunPara.setReportDimensions(runPara.getReportDimensions());
        processOneRunPara.setEnvVariables(runPara.getEnvVariables());
        return this.buildBusinessKey(processOneRunPara);
    }

    @Override
    public IBusinessKeyCollection buildBusinessKeyCollection(ProcessBatchRunPara runPara) {
        FormSchemeDefine formScheme = this.runtimeParamHelper.getFormScheme(runPara.getTaskKey(), runPara.getPeriod());
        WorkflowObjectType flowObjectType = this.settingService.queryTaskWorkflowObjectType(runPara.getTaskKey());
        if (WorkflowObjectType.FORM_GROUP == flowObjectType) {
            return this.madeGroupDimensionCollection(runPara, formScheme);
        }
        if (WorkflowObjectType.FORM == flowObjectType) {
            return this.madeFormDimensionCollection(runPara, formScheme);
        }
        return this.madeUnitDimensionCollection(runPara);
    }

    @Override
    public IBusinessKeyCollection buildBusinessKeyCollection(ProcessBatchExecutePara runPara) {
        ProcessBatchRunPara processBatchRunPara = new ProcessBatchRunPara();
        processBatchRunPara.setTaskKey(runPara.getTaskKey());
        processBatchRunPara.setPeriod(runPara.getPeriod());
        processBatchRunPara.setReportDimensions(runPara.getReportDimensions());
        processBatchRunPara.setEnvVariables(runPara.getEnvVariables());
        return this.buildBusinessKeyCollection(processBatchRunPara);
    }

    @Override
    public IBusinessKeyCollection buildUnitDimensionCollection(ProcessBatchRunPara runPara) {
        return this.madeUnitDimensionCollection(runPara);
    }

    public IBusinessKeyCollection madeUnitDimensionCollection(ProcessBatchRunPara runPara) {
        DimensionCollection dimensionCollection = this.buildDimensionCollection(runPara);
        return new BusinessKeyCollection(runPara.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newDimensionObjectCollection((DimensionCollection)dimensionCollection));
    }

    private IBusinessKeyCollection madeGroupDimensionCollection(ProcessBatchRunPara runPara, FormSchemeDefine formScheme) {
        List<String> rangeGroupKeys = this.getRangeGroupKeys(runPara, formScheme);
        DimensionCollection dimensionCollection = this.buildDimensionCollection(runPara);
        IDimensionObjectMapping toGroupMapInfo = this.processDimToGroupDefinesMap(formScheme, dimensionCollection, rangeGroupKeys);
        return new BusinessKeyCollection(runPara.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormGroupObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)toGroupMapInfo));
    }

    private IBusinessKeyCollection madeFormDimensionCollection(ProcessBatchRunPara runPara, FormSchemeDefine formScheme) {
        List<String> rangeFormKeys = this.getRangeFormKeys(runPara, formScheme);
        DimensionCollection dimensionCollection = this.buildDimensionCollection(runPara);
        IDimensionObjectMapping toFormMapInfo = this.processDimToFormDefinesMap(formScheme, dimensionCollection, rangeFormKeys);
        return new BusinessKeyCollection(runPara.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)toFormMapInfo));
    }

    @Override
    public IDimensionObjectMapping processDimToGroupDefinesMap(FormSchemeDefine formScheme, DimensionCollection dimensionCollection, Collection<String> rangeGroupKeys) {
        HashMap<DimensionCombination, Set<String>> toGroupMap = new HashMap<DimensionCombination, Set<String>>();
        LinkedHashSet rangeFormKeys = new LinkedHashSet();
        HashMap formToGroupMap = new HashMap();
        for (String groupId : rangeGroupKeys) {
            List allFormsInGroup = this.modelDefineService.listFormByGroup(groupId, formScheme.getKey());
            allFormsInGroup.forEach(form -> {
                Set groupIds = formToGroupMap.computeIfAbsent(form.getKey(), k -> new LinkedHashSet());
                groupIds.add(groupId);
                rangeFormKeys.add(form.getKey());
            });
        }
        EvaluatorParam evaluatorParam = new EvaluatorParam();
        evaluatorParam.setFormSchemeId(formScheme.getKey());
        DataPermissionEvaluator evaluator = this.permissionEvaluatorFactory.createEvaluator(evaluatorParam, dimensionCollection, rangeFormKeys);
        List combinations = dimensionCollection.getDimensionCombinations();
        for (DimensionCombination masterKeys : combinations) {
            Set groupIds = toGroupMap.computeIfAbsent(masterKeys, k -> new LinkedHashSet());
            for (String formKey : rangeFormKeys) {
                if (!evaluator.haveAccess(masterKeys, formKey, AuthType.READABLE)) continue;
                groupIds.addAll((Collection)formToGroupMap.get(formKey));
            }
        }
        return new BusinessKeyObjectMapper(toGroupMap);
    }

    @Override
    public IDimensionObjectMapping processDimToGroupConditionMap(FormSchemeDefine formScheme, DimensionCollection dimensionCollection, Collection<String> rangeGroupKeys) {
        HashMap<DimensionCombination, Set<String>> toGroupMap = new HashMap<DimensionCombination, Set<String>>();
        LinkedHashSet rangeFormKeys = new LinkedHashSet();
        HashMap formToGroupMap = new HashMap();
        for (String groupId : rangeGroupKeys) {
            List allFormsInGroup = this.modelDefineService.listFormByGroup(groupId, formScheme.getKey());
            allFormsInGroup.forEach(form -> {
                Set groupIds = formToGroupMap.computeIfAbsent(form.getKey(), k -> new LinkedHashSet());
                groupIds.add(groupId);
                rangeFormKeys.add(form.getKey());
            });
        }
        IBatchAccess batchVisible = this.formConditionAccessService.getBatchVisible(formScheme.getKey(), dimensionCollection, new ArrayList(rangeFormKeys));
        List combinations = dimensionCollection.getDimensionCombinations();
        for (DimensionCombination masterKeys : combinations) {
            Set groupIds = toGroupMap.computeIfAbsent(masterKeys, k -> new LinkedHashSet());
            for (String formKey : rangeFormKeys) {
                if (!"1".equals(batchVisible.getAccessCode(masterKeys, formKey).getCode())) continue;
                groupIds.addAll((Collection)formToGroupMap.get(formKey));
            }
        }
        return new BusinessKeyObjectMapper(toGroupMap);
    }

    @Override
    public IDimensionObjectMapping processDimToFormDefinesMap(FormSchemeDefine formScheme, DimensionCollection dimensionCollection, Collection<String> rangeFormKeys) {
        HashMap<DimensionCombination, Set<String>> toFormMap = new HashMap<DimensionCombination, Set<String>>();
        EvaluatorParam evaluatorParam = new EvaluatorParam();
        evaluatorParam.setFormSchemeId(formScheme.getKey());
        DataPermissionEvaluator evaluator = this.permissionEvaluatorFactory.createEvaluator(evaluatorParam, dimensionCollection, rangeFormKeys);
        List combinations = dimensionCollection.getDimensionCombinations();
        for (DimensionCombination masterKeys : combinations) {
            Set forms = toFormMap.computeIfAbsent(masterKeys, k -> new LinkedHashSet());
            for (String formKey : rangeFormKeys) {
                if (!evaluator.haveAccess(masterKeys, formKey, AuthType.READABLE)) continue;
                forms.add(formKey);
            }
        }
        return new BusinessKeyObjectMapper(toFormMap);
    }

    @Override
    public IDimensionObjectMapping processDimToFormConditionMap(FormSchemeDefine formScheme, DimensionCollection dimensionCollection, Collection<String> rangeFormKeys) {
        HashMap<DimensionCombination, Set<String>> toFormMap = new HashMap<DimensionCombination, Set<String>>();
        IBatchAccess batchVisible = this.formConditionAccessService.getBatchVisible(formScheme.getKey(), dimensionCollection, new ArrayList<String>(rangeFormKeys));
        List combinations = dimensionCollection.getDimensionCombinations();
        for (DimensionCombination masterKeys : combinations) {
            Set forms = toFormMap.computeIfAbsent(masterKeys, k -> new LinkedHashSet());
            for (String formKey : rangeFormKeys) {
                if (!"1".equals(batchVisible.getAccessCode(masterKeys, formKey).getCode())) continue;
                forms.add(formKey);
            }
        }
        return new BusinessKeyObjectMapper(toFormMap);
    }

    private List<String> getRangeGroupKeys(ProcessBatchRunPara runPara, FormSchemeDefine formScheme) {
        List<String> groupKeys = new ArrayList<String>();
        Optional<ProcessRangeDims> optional = runPara.getReportDimensions().stream().filter(e -> EProcessDimensionName.PROCESS_GROUP.dimName.equals(e.getDimensionName())).findFirst();
        if (optional.isPresent()) {
            ProcessRangeDims groupDims = optional.get();
            if (EProcessRangeDimType.ALL == groupDims.getRangeType()) {
                List formGroups = this.modelDefineService.listFormGroupByFormScheme(formScheme.getKey());
                groupKeys = formGroups.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            } else if (EProcessRangeDimType.RANGE == groupDims.getRangeType()) {
                groupKeys = groupDims.getRangeDims();
            } else if (EProcessRangeDimType.ONE == groupDims.getRangeType()) {
                groupKeys = Collections.singletonList(groupDims.getDimensionValue());
            }
        }
        return groupKeys;
    }

    private List<String> getRangeFormKeys(ProcessBatchRunPara runPara, FormSchemeDefine formScheme) {
        List<String> formKeys = new ArrayList<String>();
        Optional<ProcessRangeDims> optional = runPara.getReportDimensions().stream().filter(e -> EProcessDimensionName.PROCESS_FORM.dimName.equals(e.getDimensionName())).findFirst();
        if (optional.isPresent()) {
            ProcessRangeDims formRangeDims = optional.get();
            if (EProcessRangeDimType.ALL == formRangeDims.getRangeType()) {
                List formDefines = this.modelDefineService.listFormByFormScheme(formScheme.getKey());
                formKeys = formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            } else if (EProcessRangeDimType.RANGE == formRangeDims.getRangeType()) {
                formKeys = formRangeDims.getRangeDims();
            } else if (EProcessRangeDimType.ONE == formRangeDims.getRangeType()) {
                String formKey = formRangeDims.getDimensionValue();
                formKeys = Collections.singletonList(formKey);
            }
        }
        return formKeys;
    }

    public DimensionCombination buildDimensionCombination(ProcessOneRunPara runPara) {
        TaskDefine taskDefine = this.modelDefineService.getTask(runPara.getTaskKey());
        DimensionCombinationImpl combination = new DimensionCombinationImpl();
        List<DataDimension> reportDimensions = this.reportDimensionHelper.getAllReportDimensions(runPara.getTaskKey());
        for (DataDimension dimension : reportDimensions) {
            IEntityDefine dwEntityDefine;
            String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
            ProcessOneDim dimensionValue = runPara.getOneReportDimension(dimensionName);
            assert (dimensionValue != null);
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                dwEntityDefine = this.runtimeParamHelper.getProcessEntityDefinition(runPara.getTaskKey());
                combination.setDWValue(dimensionName, dwEntityDefine.getId(), (Object)dimensionValue.getDimensionValue());
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                combination.setValue(dimensionName, dimension.getDimKey(), (Object)runPara.getPeriod());
                continue;
            }
            if (this.reportDimensionHelper.isCorporateDimension(taskDefine, dimension)) {
                dwEntityDefine = this.runtimeParamHelper.getProcessEntityDefinition(runPara.getTaskKey());
                String unitDimensionName = dwEntityDefine.getDimensionName();
                String unitCode = Objects.requireNonNull(runPara.getOneReportDimension(unitDimensionName)).getDimensionValue();
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue("DATATIME", (Object)runPara.getPeriod());
                dimensionValueSet.setValue(unitDimensionName, (Object)unitCode);
                try {
                    IEntityTable entityTable = this.processEntityQueryHelper.getIEntityTable(runPara.getTaskKey(), runPara.getPeriod(), dimensionValueSet);
                    IEntityRow targetRow = entityTable.findByEntityKey(unitCode);
                    combination.setValue(dimensionName, dimension.getDimKey(), (Object)targetRow.getAsString(dimension.getDimAttribute()));
                    continue;
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            combination.setValue(dimensionName, dimension.getDimKey(), (Object)dimensionValue.getDimensionValue());
        }
        return combination;
    }

    @Override
    public DimensionCollection buildDimensionCollection(ProcessBatchRunPara runPara) {
        TaskDefine taskDefine = this.modelDefineService.getTask(runPara.getTaskKey());
        List<DataDimension> reportDimensions = this.reportDimensionHelper.getAllReportDimensions(runPara.getTaskKey());
        DimensionCollectionBuilder dimensionBuilder = new DimensionCollectionBuilder();
        for (DataDimension dimension : reportDimensions) {
            String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                this.buildUnitDims(taskDefine, dimension, dimensionName, dimensionBuilder, runPara);
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{runPara.getPeriod()});
                continue;
            }
            if (this.reportDimensionHelper.isCorporateDimension(taskDefine, dimension)) {
                DimensionProviderData providerData = new DimensionProviderData();
                providerData.setDataSchemeKey(taskDefine.getDataScheme());
                providerData.setAuthorityType(AuthorityType.Read);
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData);
                dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
                continue;
            }
            this.buildDimensions(taskDefine, dimension, dimensionName, dimensionBuilder, runPara);
        }
        return dimensionBuilder.getCollection();
    }

    private void buildUnitDims(TaskDefine taskDefine, DataDimension dimension, String dimensionName, DimensionCollectionBuilder dimensionBuilder, ProcessBatchRunPara runPara) {
        Optional<ProcessRangeDims> optional = runPara.getReportDimensions().stream().filter(e -> dimensionName.equals(e.getDimensionName())).findFirst();
        if (optional.isPresent()) {
            IEntityDefine dwEntityDefine = this.runtimeParamHelper.getProcessEntityDefinition(runPara.getTaskKey());
            ProcessRangeDims unitRangeDim = optional.get();
            if (EProcessRangeDimType.ALL == unitRangeDim.getRangeType()) {
                DimensionProviderData providerData = new DimensionProviderData();
                providerData.setDataSchemeKey(taskDefine.getDataScheme());
                providerData.setAuthorityType(AuthorityType.Read);
                providerData.setFilter(StringUtils.isNotEmpty((String)taskDefine.getFilterExpression()) ? taskDefine.getFilterExpression() : null);
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", providerData);
                dimensionBuilder.addVariableDW(dimensionName, dwEntityDefine.getId(), dimensionProvider);
            } else if (EProcessRangeDimType.RANGE == unitRangeDim.getRangeType()) {
                List<String> unitRangeKeys = unitRangeDim.getRangeDims();
                DimensionProviderData providerData = new DimensionProviderData();
                providerData.setDataSchemeKey(taskDefine.getDataScheme());
                providerData.setAuthorityType(AuthorityType.Read);
                providerData.setChoosedValues(unitRangeKeys);
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", providerData);
                dimensionBuilder.addVariableDW(dimensionName, dwEntityDefine.getId(), dimensionProvider);
            } else if (EProcessRangeDimType.ONE == unitRangeDim.getRangeType()) {
                String unitValue = unitRangeDim.getDimensionValue();
                dimensionBuilder.setDWValue(dimensionName, dwEntityDefine.getId(), new Object[]{unitValue});
            }
        }
    }

    private void buildDimensions(TaskDefine taskDefine, DataDimension dimension, String dimensionName, DimensionCollectionBuilder dimensionBuilder, ProcessBatchRunPara runPara) {
        Optional<ProcessRangeDims> optional = runPara.getReportDimensions().stream().filter(e -> dimensionName.equals(e.getDimensionName())).findFirst();
        if (optional.isPresent()) {
            ProcessRangeDims scenesRangeDim = optional.get();
            if (EProcessRangeDimType.ALL == scenesRangeDim.getRangeType()) {
                DimensionProviderData providerData = new DimensionProviderData();
                providerData.setDataSchemeKey(taskDefine.getDataScheme());
                providerData.setAuthorityType(AuthorityType.Read);
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData);
                dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
            } else if (EProcessRangeDimType.RANGE == scenesRangeDim.getRangeType()) {
                List<String> scenesRangeKeys = scenesRangeDim.getRangeDims();
                DimensionProviderData providerData = new DimensionProviderData();
                providerData.setDataSchemeKey(taskDefine.getDataScheme());
                providerData.setAuthorityType(AuthorityType.Read);
                providerData.setChoosedValues(scenesRangeKeys);
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData);
                dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
            } else if (EProcessRangeDimType.ONE == scenesRangeDim.getRangeType()) {
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{scenesRangeDim.getDimensionValue()});
            }
        }
    }
}

