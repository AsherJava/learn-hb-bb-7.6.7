/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper
 *  com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper
 *  com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv
 */
package com.jiuqi.nr.workflow2.events.helper;

import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.dimension.ProcessDimensionCollection;
import com.jiuqi.nr.workflow2.events.enumeration.CurrencyType;
import com.jiuqi.nr.workflow2.events.helper.CurrencyFilterCondition;
import com.jiuqi.nr.workflow2.events.helper.DimensionBuilderCondition;
import com.jiuqi.nr.workflow2.events.helper.StepBusinessObjectCollection;
import com.jiuqi.nr.workflow2.events.helper.UnitFilterCondition;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.helper.IProcessEntityQueryHelper;
import com.jiuqi.nr.workflow2.service.helper.IProcessRuntimeParamHelper;
import com.jiuqi.nr.workflow2.service.helper.IReportDimensionHelper;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecuteEnv;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventExecuteDimensionBuilder {
    @Autowired
    private WorkflowSettingsService settingService;
    @Autowired
    private IReportDimensionHelper reportDimensionHelper;
    @Autowired
    private IProcessRuntimeParamHelper runtimeParamHelper;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private IProcessEntityQueryHelper processEntityQueryHelper;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;

    public DimensionCollectionBuilder toDimensionCollectionBuilder(ProcessExecuteEnv envParam, IBusinessKey businessKey, DimensionBuilderCondition builderCondition) {
        TaskDefine taskDefine = this.runtimeParamHelper.getTaskDefine(envParam.getTaskKey());
        DimensionCollectionBuilder dimensionBuilder = new DimensionCollectionBuilder();
        DimensionCombination combination = businessKey.getBusinessObject().getDimensions();
        List allDataDimension = this.reportDimensionHelper.getAllDataDimension(taskDefine.getKey());
        for (DataDimension dimension : allDataDimension) {
            String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                FixedDimensionValue dwDimensionValue = combination.getDWDimensionValue();
                dimensionBuilder.setDWValue(dimensionName, dwDimensionValue.getEntityID(), new Object[]{dwDimensionValue.getValue()});
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                FixedDimensionValue periodDimensionValue = combination.getPeriodDimensionValue();
                dimensionBuilder.setEntityValue(periodDimensionValue.getName(), periodDimensionValue.getEntityID(), new Object[]{periodDimensionValue.getValue()});
                continue;
            }
            if (combination.hasValue(dimensionName)) {
                FixedDimensionValue fixedDimensionValue = combination.getFixedDimensionValue(dimensionName);
                dimensionBuilder.setEntityValue(fixedDimensionValue.getName(), fixedDimensionValue.getEntityID(), new Object[]{fixedDimensionValue.getValue()});
                continue;
            }
            if (this.reportDimensionHelper.isCorporateDimension(taskDefine, dimension)) {
                this.appendCorporateDimensionProvider(dimensionBuilder, envParam, dimension);
                continue;
            }
            if (this.reportDimensionHelper.isMdCurrencyDimension(taskDefine, dimension)) {
                this.appendCurrencyDimensionProvider(dimensionBuilder, envParam, dimension, builderCondition.getCurrencyFilterCondition());
                continue;
            }
            this.appendOtherDimensionProvider(dimensionBuilder, envParam, dimension);
        }
        return dimensionBuilder;
    }

    public DimensionCollectionBuilder toDimensionCollectionBuilder(ProcessExecuteEnv envParam, IBusinessKeyCollection businessKeyCollection, DimensionBuilderCondition builderCondition) throws Exception {
        TaskDefine taskDefine = this.runtimeParamHelper.getTaskDefine(envParam.getTaskKey());
        DimensionCollectionBuilder dimensionBuilder = new DimensionCollectionBuilder();
        Map<String, Set<String>> mergeDimensionCombinations = this.mergeDimensionCombinations(businessKeyCollection);
        List allDataDimension = this.reportDimensionHelper.getAllDataDimension(taskDefine.getKey());
        for (DataDimension dimension : allDataDimension) {
            String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                this.appendUnitDimensionProvider(dimensionBuilder, envParam, dimension, mergeDimensionCombinations, builderCondition.getUnitFilterCondition());
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                Set<String> periodSet = mergeDimensionCombinations.get(dimensionName);
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{periodSet.stream().findFirst().orElse(null)});
                continue;
            }
            if (mergeDimensionCombinations.containsKey(dimensionName)) {
                ArrayList scenesRangeKeys = new ArrayList(mergeDimensionCombinations.get(dimensionName));
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{scenesRangeKeys});
                continue;
            }
            if (this.reportDimensionHelper.isCorporateDimension(taskDefine, dimension)) {
                this.appendCorporateDimensionProvider(dimensionBuilder, envParam, dimension);
                continue;
            }
            if (this.reportDimensionHelper.isMdCurrencyDimension(taskDefine, dimension)) {
                this.appendCurrencyDimensionProvider(dimensionBuilder, envParam, dimension, builderCondition.getCurrencyFilterCondition());
                continue;
            }
            this.appendOtherDimensionProvider(dimensionBuilder, envParam, dimension);
        }
        return dimensionBuilder;
    }

    public List<String> getProcessRangeFormKeys(IBusinessKey businessKey) {
        ArrayList<String> formKeys = new ArrayList<String>();
        WorkflowObjectType flowObjectType = this.settingService.queryTaskWorkflowObjectType(businessKey.getTask());
        if (WorkflowObjectType.FORM_GROUP == flowObjectType) {
            IFormGroupObject formGroupObject = (IFormGroupObject)businessKey.getBusinessObject();
            Object periodValueObject = formGroupObject.getDimensions().getPeriodDimensionValue().getValue();
            FormSchemeDefine formScheme = this.runtimeParamHelper.getFormScheme(businessKey.getTask(), periodValueObject.toString());
            List formDefines = this.runTimeViewController.listFormByGroup(formGroupObject.getFormGroupKey(), formScheme.getKey());
            return formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
        }
        if (WorkflowObjectType.FORM == flowObjectType) {
            IFormObject formObject = (IFormObject)businessKey.getBusinessObject();
            formKeys.add(formObject.getFormKey());
        }
        return formKeys;
    }

    public List<String> getProcessRangeGroupKeys(IBusinessKeyCollection businessKeyCollection) {
        LinkedHashSet<String> groupKeys = new LinkedHashSet<String>();
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjects) {
            IFormGroupObject formGroupObject = (IFormGroupObject)businessObject;
            String formGroupKey = formGroupObject.getFormGroupKey();
            if (groupKeys.contains(formGroupKey)) continue;
            groupKeys.add(formGroupObject.getFormGroupKey());
        }
        return new ArrayList<String>(groupKeys);
    }

    public List<String> getProcessRangeFormKeys(IBusinessKeyCollection businessKeyCollection) {
        LinkedHashSet<String> formKeys;
        block4: {
            WorkflowObjectType flowObjectType;
            IBusinessObjectCollection businessObjects;
            block3: {
                formKeys = new LinkedHashSet<String>();
                businessObjects = businessKeyCollection.getBusinessObjects();
                flowObjectType = this.settingService.queryTaskWorkflowObjectType(businessKeyCollection.getTask());
                if (WorkflowObjectType.FORM_GROUP != flowObjectType) break block3;
                String periodValue = null;
                LinkedHashSet<String> groupKeys = new LinkedHashSet<String>();
                for (IBusinessObject businessObject : businessObjects) {
                    IFormGroupObject formGroupObject = (IFormGroupObject)businessObject;
                    String formGroupKey = formGroupObject.getFormGroupKey();
                    periodValue = formGroupObject.getDimensions().getPeriodDimensionValue().getValue().toString();
                    if (groupKeys.contains(formGroupKey)) continue;
                    groupKeys.add(formGroupObject.getFormGroupKey());
                }
                FormSchemeDefine formScheme = this.runtimeParamHelper.getFormScheme(businessKeyCollection.getTask(), periodValue);
                for (String groupKey : groupKeys) {
                    List formDefines = this.runTimeViewController.listFormByGroup(groupKey, formScheme.getKey());
                    formKeys.addAll(formDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList()));
                }
                break block4;
            }
            if (WorkflowObjectType.FORM != flowObjectType) break block4;
            for (IBusinessObject businessObject : businessObjects) {
                IFormObject formObject = (IFormObject)businessObject;
                formKeys.add(formObject.getFormKey());
            }
        }
        return new ArrayList<String>(formKeys);
    }

    public StepBusinessObjectCollection getCanOptParentBusinessObjectCollection(IProcessRunPara runEnvPara, IBusinessObject businessObject, WorkflowObjectType flowObjectType) throws Exception {
        FixedDimensionValue dwDimensionValue = businessObject.getDimensions().getDWDimensionValue();
        IEntityQuery entityQuery = this.processEntityQueryHelper.makeIEntityQuery(runEnvPara.getTaskKey(), runEnvPara.getPeriod());
        ExecutorContext executorContext = this.processEntityQueryHelper.makeExecuteContext(runEnvPara.getTaskKey(), runEnvPara.getPeriod());
        IEntityTable entityTable = this.processEntityQueryHelper.getIEntityTable(entityQuery, executorContext);
        IEntityRow entityRow = entityTable.findByEntityKey(dwDimensionValue.getValue().toString());
        String[] parentsEntityKeyDataPath = entityRow.getParentsEntityKeyDataPath();
        entityQuery.getMasterKeys().setValue(dwDimensionValue.getName(), Arrays.asList(parentsEntityKeyDataPath));
        entityTable = this.processEntityQueryHelper.getIEntityTable(entityQuery, executorContext);
        List pathParentRows = entityTable.getAllRows();
        return this.toStepBusinessObjectCollection(runEnvPara, businessObject, flowObjectType, pathParentRows);
    }

    public StepBusinessObjectCollection getCanOptChildBusinessObjectCollection(IProcessRunPara runEnvPara, IBusinessObject businessObject, WorkflowObjectType flowObjectType) throws Exception {
        FixedDimensionValue dwDimensionValue = businessObject.getDimensions().getDWDimensionValue();
        IEntityQuery entityQuery = this.processEntityQueryHelper.makeIEntityQuery(runEnvPara.getTaskKey(), runEnvPara.getPeriod());
        ExecutorContext executorContext = this.processEntityQueryHelper.makeExecuteContext(runEnvPara.getTaskKey(), runEnvPara.getPeriod());
        IEntityTable entityTable = this.processEntityQueryHelper.getIEntityTable(entityQuery, executorContext);
        List childRows = entityTable.getChildRows(dwDimensionValue.getValue().toString());
        return this.toStepBusinessObjectCollection(runEnvPara, businessObject, flowObjectType, childRows);
    }

    public StepBusinessObjectCollection getCanOptChildBusinessObjectCollection(IProcessRunPara runEnvPara, IBusinessObject businessObject, WorkflowObjectType flowObjectType, IEntityTable entityTable) throws Exception {
        FixedDimensionValue dwDimensionValue = businessObject.getDimensions().getDWDimensionValue();
        List childRows = entityTable.getChildRows(dwDimensionValue.getValue().toString());
        return this.toStepBusinessObjectCollection(runEnvPara, businessObject, flowObjectType, childRows);
    }

    private StepBusinessObjectCollection toStepBusinessObjectCollection(IProcessRunPara runEnvPara, IBusinessObject businessObject, WorkflowObjectType flowObjectType, List<IEntityRow> entityRows) {
        ArrayList<DimensionCombination> combinations = new ArrayList<DimensionCombination>();
        HashMap<DimensionCombination, IEntityRow> dimensionCombinationToEntityRowMap = new HashMap<DimensionCombination, IEntityRow>();
        for (IEntityRow entityRow : entityRows) {
            DimensionCombination combination = this.toDimensionCombination(businessObject, entityRow);
            dimensionCombinationToEntityRowMap.put(combination, entityRow);
            combinations.add(combination);
        }
        ArrayList<String> rangeGroupOrFormKeys = new ArrayList<String>();
        if (WorkflowObjectType.FORM_GROUP == flowObjectType) {
            IFormGroupObject formGroupObject = (IFormGroupObject)businessObject;
            rangeGroupOrFormKeys.add(formGroupObject.getFormGroupKey());
        } else if (WorkflowObjectType.FORM == flowObjectType) {
            IFormObject formObject = (IFormObject)businessObject;
            rangeGroupOrFormKeys.add(formObject.getFormKey());
        }
        ProcessDimensionCollection dimensionCollection = new ProcessDimensionCollection(combinations);
        IBusinessKeyCollection businessKeyCollection = this.toBusinessKeyCollection(runEnvPara, flowObjectType, (DimensionCollection)dimensionCollection, rangeGroupOrFormKeys);
        return new StepBusinessObjectCollection(businessKeyCollection, dimensionCombinationToEntityRowMap);
    }

    private IBusinessKeyCollection toBusinessKeyCollection(IProcessRunPara runEnvPara, WorkflowObjectType flowObjectType, DimensionCollection dimensionCollection, Collection<String> rangeGroupOrFormKeys) {
        if (WorkflowObjectType.FORM_GROUP == flowObjectType) {
            FormSchemeDefine formScheme = this.runtimeParamHelper.getFormScheme(runEnvPara.getTaskKey(), runEnvPara.getPeriod());
            IDimensionObjectMapping dimensionObjectMapping = this.processDimensionsBuilder.processDimToGroupDefinesMap(formScheme, dimensionCollection, rangeGroupOrFormKeys);
            return new BusinessKeyCollection(runEnvPara.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormGroupObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)dimensionObjectMapping));
        }
        if (WorkflowObjectType.FORM == flowObjectType) {
            FormSchemeDefine formScheme = this.runtimeParamHelper.getFormScheme(runEnvPara.getTaskKey(), runEnvPara.getPeriod());
            IDimensionObjectMapping dimensionObjectMapping = this.processDimensionsBuilder.processDimToFormDefinesMap(formScheme, dimensionCollection, rangeGroupOrFormKeys);
            return new BusinessKeyCollection(runEnvPara.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)dimensionObjectMapping));
        }
        return new BusinessKeyCollection(runEnvPara.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newDimensionObjectCollection((DimensionCollection)dimensionCollection));
    }

    public IBusinessObject toBusinessObject(IBusinessObject businessObject, IEntityRow entityRow, WorkflowObjectType flowObjectType) {
        DimensionCombination combination = this.toDimensionCombination(businessObject, entityRow);
        if (WorkflowObjectType.FORM_GROUP == flowObjectType) {
            IFormGroupObject formGroupObject = (IFormGroupObject)businessObject;
            return new FormGroupObject(combination, formGroupObject.getFormGroupKey());
        }
        if (WorkflowObjectType.FORM == flowObjectType) {
            IFormObject formObject = (IFormObject)businessObject;
            return new FormObject(combination, formObject.getFormKey());
        }
        return new DimensionObject(combination);
    }

    public DimensionCombination toDimensionCombination(IBusinessObject businessObject, IEntityRow entityRow) {
        DimensionCombination dimensions = businessObject.getDimensions();
        FixedDimensionValue dwDimensionValue = dimensions.getDWDimensionValue();
        DimensionCombinationImpl combination = new DimensionCombinationImpl();
        combination.setDWValue(dwDimensionValue.getName(), dwDimensionValue.getEntityID(), (Object)entityRow.getEntityKeyData());
        for (String dimensionName : dimensions.getNames()) {
            if (dimensionName.equals(dwDimensionValue.getName())) continue;
            FixedDimensionValue dimensionValue = dimensions.getFixedDimensionValue(dimensionName);
            combination.setValue(dimensionValue.getName(), dimensionValue.getEntityID(), dimensionValue.getValue());
        }
        return combination;
    }

    protected void appendUnitDimensionProvider(DimensionCollectionBuilder dimensionBuilder, ProcessExecuteEnv envParam, DataDimension dimension, Map<String, Set<String>> mergeDimensionCombinations, UnitFilterCondition filterCondition) throws Exception {
        String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
        ArrayList unitRangeKeys = new ArrayList(mergeDimensionCombinations.get(dimensionName));
        if (filterCondition != null) {
            UnitFilterCondition.FilterType filterType = filterCondition.getFilterType();
            if (UnitFilterCondition.FilterType.by_unit_range == filterType) {
                dimensionBuilder.setDWValue(dimensionName, dimension.getDimKey(), new Object[]{filterCondition.getUnitRangeKeys()});
            } else if (UnitFilterCondition.FilterType.by_leaf_node == filterType) {
                TaskDefine taskDefine = this.runtimeParamHelper.getTaskDefine(envParam.getTaskKey());
                DataDimension unitDimension = this.reportDimensionHelper.getReportUnitDimension(taskDefine.getKey());
                DimensionProviderData providerData = new DimensionProviderData();
                providerData.setDataSchemeKey(taskDefine.getDataScheme());
                providerData.setAuthorityType(AuthorityType.Read);
                providerData.setChoosedValues(unitRangeKeys);
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_LEAFNODE", providerData);
                dimensionBuilder.addVariableDW(dimensionName, unitDimension.getDimKey(), dimensionProvider);
            } else {
                dimensionBuilder.setDWValue(dimensionName, dimension.getDimKey(), new Object[]{unitRangeKeys});
            }
        } else {
            dimensionBuilder.setDWValue(dimensionName, dimension.getDimKey(), new Object[]{unitRangeKeys});
        }
    }

    protected void appendCorporateDimensionProvider(DimensionCollectionBuilder dimensionBuilder, ProcessExecuteEnv envParam, DataDimension dimension) {
        String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
        TaskDefine taskDefine = this.runtimeParamHelper.getTaskDefine(envParam.getTaskKey());
        DimensionProviderData providerData = this.getDimensionProviderData(envParam, taskDefine);
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData);
        dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
    }

    protected void appendOtherDimensionProvider(DimensionCollectionBuilder dimensionBuilder, ProcessExecuteEnv envParam, DataDimension dimension) {
        String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
        TaskDefine taskDefine = this.runtimeParamHelper.getTaskDefine(envParam.getTaskKey());
        DimensionProviderData providerData = this.getDimensionProviderData(envParam, taskDefine);
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_ALLNODE", providerData);
        dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
    }

    protected void appendCurrencyDimensionProvider(DimensionCollectionBuilder dimensionBuilder, ProcessExecuteEnv envParam, DataDimension dimension, CurrencyFilterCondition filterCondition) {
        if (filterCondition != null) {
            CurrencyType currencyType = filterCondition.getCurrencyType();
            String dimensionName = this.reportDimensionHelper.getDimensionName(dimension);
            TaskDefine taskDefine = this.runtimeParamHelper.getTaskDefine(envParam.getTaskKey());
            if (CurrencyType.ALL == currencyType || currencyType == null) {
                DimensionProviderData providerData = this.getDimensionProviderData(envParam, taskDefine);
                VariableDimensionValueProvider dimensionProvider = filterCondition.isMdCurrencyReferEntity() ? this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData) : this.dimensionProviderFactory.getDimensionProvider("PROVIDER_ALLNODE", providerData);
                dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
            } else if (CurrencyType.SELF == currencyType) {
                DimensionProviderData providerData = this.getDimensionProviderData(envParam, taskDefine);
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_BASECURRENCY", providerData);
                dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
            } else if (CurrencyType.SUPERIOR == currencyType) {
                DimensionProviderData providerData = this.getDimensionProviderData(envParam, taskDefine);
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_PBASECURRENCY", providerData);
                dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
            } else if (CurrencyType.CUSTOM == currencyType) {
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{filterCondition.getCustomCurrency()});
            }
        }
    }

    protected DimensionProviderData getDimensionProviderData(ProcessExecuteEnv envParam, TaskDefine taskDefine) {
        DimensionProviderData providerData = new DimensionProviderData();
        providerData.setDataSchemeKey(taskDefine.getDataScheme());
        providerData.setAuthorityType(AuthorityType.Read);
        return providerData;
    }

    public Map<String, Set<String>> mergeDimensionCombinations(IBusinessKeyCollection businessKeyCollection) {
        HashMap<String, Set<String>> dimensionCombinations = new HashMap<String, Set<String>>();
        IBusinessObjectCollection businessObjects = businessKeyCollection.getBusinessObjects();
        for (IBusinessObject businessObject : businessObjects) {
            DimensionCombination combination = businessObject.getDimensions();
            Collection names = combination.getNames();
            for (String name : names) {
                dimensionCombinations.computeIfAbsent(name, k -> new LinkedHashSet());
                ((Set)dimensionCombinations.get(name)).add(combination.getValue(name).toString());
            }
        }
        return dimensionCombinations;
    }
}

