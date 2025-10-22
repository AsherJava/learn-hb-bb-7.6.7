/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionContext
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.MasterEntity;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkflowReportDimService;
import com.jiuqi.nr.bpm.instance.bean.CorporateData;
import com.jiuqi.nr.bpm.setting.utils.BpmQueryEntityData;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.util.StringUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IWorkFlowDimensionBuilder {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    public IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private WorkflowReportDimService workflowReportDimService;

    public DimensionCollection buildDimensionCollection(String taskKey, Map<String, DimensionValue> dimensionSet) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        List dataSchemeDimension = this.taskService.getDataDimension(taskKey);
        DimensionCollectionBuilder dimensionBuilder = new DimensionCollectionBuilder();
        DimensionContext dimensionContext = new DimensionContext(taskKey);
        dimensionBuilder.setContext(dimensionContext);
        for (DataDimension dimension : dataSchemeDimension) {
            DimensionValue dimensionValue;
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if ("ADJUST".equals(dimensionName)) {
                dimensionValue = dimensionSet.get(dimensionName);
                if (dimensionValue != null) {
                    dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{dimensionValue.getValue()});
                    continue;
                }
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{0});
                continue;
            }
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                dimensionValue = dimensionSet.get(dimensionName);
                String value = dimensionValue.getValue();
                String dimId = this.getContextMainDimId(taskDefine.getDw());
                String dimName = this.getDimensionName(dimension.getDimKey());
                DimensionProviderData providerData = new DimensionProviderData();
                if (StringUtils.isEmpty((String)value)) {
                    providerData.setFilter(taskDefine.getFilterExpression());
                    providerData.setDataSchemeKey(taskDefine.getDataScheme());
                    providerData.setAuthorityType(AuthorityType.Read);
                } else {
                    String[] split = value.split(";");
                    List entityKeys = Arrays.stream(split).filter(element -> element != null && !element.trim().isEmpty()).collect(Collectors.toList());
                    providerData.setChoosedValues(entityKeys);
                }
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", providerData);
                dimensionBuilder.addVariableDW(dimName, dimId, dimensionProvider);
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                dimensionValue = dimensionSet.get(dimensionName);
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{dimensionValue.getValue()});
                continue;
            }
            if (!this.isCorporate(taskDefine, dimension, dwEntityModel)) continue;
            DimensionProviderData providerData = new DimensionProviderData();
            providerData.setDataSchemeKey(taskDefine.getDataScheme());
            providerData.setAuthorityType(AuthorityType.Read);
            VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData);
            dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
        }
        return dimensionBuilder.getCollection();
    }

    public DimensionCollection buildDimensionCollection(String taskKey, Map<String, DimensionValue> dimensionSet, String corporateValue) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        List dataSchemeDimension = this.taskService.getDataDimension(taskKey);
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        DimensionCollectionBuilder dimensionBuilder = new DimensionCollectionBuilder();
        DimensionContext dimensionContext = new DimensionContext(taskKey);
        dimensionBuilder.setContext(dimensionContext);
        for (DataDimension dimension : dataSchemeDimension) {
            DimensionValue dimensionValue;
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if ("ADJUST".equals(dimensionName)) {
                dimensionValue = dimensionSet.get(dimensionName);
                if (dimensionValue != null) {
                    dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{dimensionValue.getValue()});
                    continue;
                }
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{0});
                continue;
            }
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                dimensionValue = dimensionSet.get(dimensionName);
                String value = dimensionValue.getValue();
                String dimName = this.getDimensionName(dimension.getDimKey());
                if (StringUtils.isEmpty((String)value)) {
                    DimensionProviderData providerData = new DimensionProviderData();
                    providerData.setDataSchemeKey(taskDefine.getDataScheme());
                    providerData.setAuthorityType(AuthorityType.Read);
                    VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", providerData);
                    dimensionBuilder.addVariableDW(dimName, corporateValue, dimensionProvider);
                    continue;
                }
                String[] split = value.split(";");
                List entityKeys = Arrays.stream(split).filter(element -> element != null && !element.trim().isEmpty()).collect(Collectors.toList());
                dimensionBuilder.setDWValue(dimName, corporateValue, new Object[]{entityKeys});
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                dimensionValue = dimensionSet.get(dimensionName);
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{dimensionValue.getValue()});
                continue;
            }
            if (!this.isCorporate(taskDefine, dimension, dwEntityModel)) continue;
            DimensionProviderData providerData = new DimensionProviderData();
            providerData.setDataSchemeKey(taskDefine.getDataScheme());
            providerData.setAuthorityType(AuthorityType.Read);
            VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData);
            dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
        }
        return dimensionBuilder.getCollection();
    }

    public DimensionCombination buildDimensionCombination(String taskKey, Map<String, DimensionValue> dimensionSet) {
        DimensionCollection dimensionValueSets = this.buildDimensionCollection(taskKey, dimensionSet);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        if (dimensionCombinations == null || dimensionCombinations.size() == 0) {
            throw new RuntimeException("\u7ef4\u5ea6\u503c\u4e0d\u5168\uff01\uff01\uff01");
        }
        DimensionCombination dimensionCombination = (DimensionCombination)dimensionCombinations.get(0);
        return dimensionCombination;
    }

    public DimensionCombination buildDimensionCombination(String taskKey, Map<String, DimensionValue> dimensionSet, String corporateDataValue) {
        DimensionCollection dimensionValueSets = this.buildDimensionCollection(taskKey, dimensionSet, corporateDataValue);
        List dimensionCombinations = dimensionValueSets.getDimensionCombinations();
        if (dimensionCombinations == null || dimensionCombinations.size() == 0) {
            throw new RuntimeException("\u7ef4\u5ea6\u503c\u4e0d\u5168\uff01\uff01\uff01");
        }
        DimensionCombination dimensionCombination = (DimensionCombination)dimensionCombinations.get(0);
        return dimensionCombination;
    }

    public DimensionCombination buildDimensionCombination(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        return this.buildDimensionCombination(formScheme.getTaskKey(), dimensionSet);
    }

    public String getDimensionName(String entityId) {
        if (this.periodEntityAdapter.isPeriodEntity(entityId)) {
            return this.periodEntityAdapter.getPeriodEntity(entityId).getDimensionName();
        }
        if ("ADJUST".equals(entityId)) {
            return "ADJUST";
        }
        return this.entityMetaService.queryEntity(entityId).getDimensionName();
    }

    public String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }

    public boolean isCorporate(TaskDefine taskDefine, DataDimension dimension, IEntityModel dwEntityModel) {
        String dimAttribute = dimension.getDimAttribute();
        List reportDimension = this.taskService.getReportDimension(taskDefine.getKey());
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
        DataDimension report = reportDimension.stream().filter(dataDimension -> dimension.getDimKey().equals(dataDimension.getDimKey())).findFirst().orElse(null);
        String dimReferAttr = report == null ? null : report.getDimAttribute();
        return DimensionType.DIMENSION == dimension.getDimensionType() && attribute != null && !attribute.isMultival() && StringUtils.isNotEmpty((String)dimReferAttr);
    }

    public boolean isCorporate(TaskDefine taskDefine) {
        List reportDimension = this.taskService.getReportDimension(taskDefine.getKey());
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        IEntityModel dwEntityModel = null;
        for (DataDimension dimension : dimensions) {
            String dimAttribute;
            Boolean reportDim = dimension.getReportDim();
            if (reportDim == null || !reportDim.booleanValue() || DimensionType.DIMENSION != dimension.getDimensionType() || (dimAttribute = dimension.getDimAttribute()) == null) continue;
            if (dwEntityModel == null) {
                dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
            }
            if (dwEntityModel == null) {
                return false;
            }
            IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
            if (attribute == null || attribute.isMultival()) continue;
            return true;
        }
        return false;
    }

    public boolean enableTwoTree(String taskKey) {
        List taskOrgLinkDefines = this.runTimeViewController.listTaskOrgLinkByTask(taskKey);
        return taskOrgLinkDefines != null && taskOrgLinkDefines.size() != 0 && taskOrgLinkDefines.size() != 1;
    }

    public String getCorporateValue(String taskKey, DimensionValueSet dimensionValueSet) {
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        String corporateEntityCode = this.getCorporateEntityCode(task);
        if (corporateEntityCode == null) {
            return null;
        }
        return dimensionValueSet.getValue(corporateEntityCode).toString();
    }

    public String getCorporateValue(String taskKey, BusinessKey businessKey) {
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        String corporateEntityCode = this.getCorporateEntityCode(task);
        if (corporateEntityCode == null) {
            return null;
        }
        MasterEntity masterEntity = businessKey.getMasterEntity();
        return masterEntity.getMasterEntityKey(corporateEntityCode);
    }

    public String getCorporateEntityId(TaskDefine taskDefine) {
        List<DataDimension> dataSchemeDimension = this.workflowReportDimService.getDataDimension(taskDefine.getKey());
        IEntityModel dwEntityModel = null;
        for (DataDimension dimension : dataSchemeDimension) {
            IEntityAttribute attribute;
            String dimAttribute;
            Boolean reportDim = dimension.getReportDim();
            if (reportDim == null || !reportDim.booleanValue() || DimensionType.DIMENSION != dimension.getDimensionType() || (dimAttribute = dimension.getDimAttribute()) == null) continue;
            if (dwEntityModel == null) {
                dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
            }
            if ((attribute = dwEntityModel.getAttribute(dimAttribute)) == null || attribute.isMultival()) continue;
            return attribute.getCode();
        }
        return null;
    }

    public boolean enableVirtualTree(String formSchemeKey, String period) {
        if (StringUtils.isEmpty((String)formSchemeKey) || StringUtils.isEmpty((String)period)) {
            return false;
        }
        BpmQueryEntityData bpmQueryEntityData = new BpmQueryEntityData();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        List<IEntityRow> rootData = bpmQueryEntityData.getRootData(dimensionValueSet, formSchemeKey);
        return rootData != null && rootData.size() > 0 && rootData.size() > 1;
    }

    public List<CorporateData> getCorporateList(String taskKey) {
        if (StringUtils.isEmpty((String)taskKey)) {
            return new ArrayList<CorporateData>();
        }
        ArrayList<CorporateData> list = new ArrayList<CorporateData>();
        TaskOrgLinkListStream taskOrgLinkListStream = this.runTimeViewController.listTaskOrgLinkStreamByTask(taskKey);
        List taskOrgLinkDefines = taskOrgLinkListStream.i18n().getList();
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkDefines) {
            CorporateData corporateData = new CorporateData();
            corporateData.setKey(taskOrgLinkDefine.getEntity());
            corporateData.setCode(taskOrgLinkDefine.getKey());
            String entityAlias = taskOrgLinkDefine.getEntityAlias();
            if (entityAlias != null) {
                corporateData.setTitle(entityAlias);
            } else {
                IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(taskOrgLinkDefine.getEntity());
                corporateData.setTitle(iEntityDefine.getTitle());
            }
            list.add(corporateData);
        }
        return list;
    }

    public List<CorporateData> getCorporateListOfDataScheme(String taskKey) {
        if (StringUtils.isEmpty((String)taskKey)) {
            return new ArrayList<CorporateData>();
        }
        ArrayList<CorporateData> list = new ArrayList<CorporateData>();
        TaskOrgLinkListStream taskOrgLinkListStream = this.runTimeViewController.listTaskOrgLinkStreamByTask(taskKey);
        List taskOrgLinkDefines = taskOrgLinkListStream.i18n().getList();
        for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkDefines) {
            CorporateData corporateData = new CorporateData();
            corporateData.setKey(taskOrgLinkDefine.getEntity());
            corporateData.setCode(taskOrgLinkDefine.getKey());
            String entityAlias = taskOrgLinkDefine.getEntityAlias();
            if (entityAlias != null) {
                corporateData.setTitle(entityAlias);
            } else {
                IEntityDefine iEntityDefine = this.entityMetaService.queryEntity(taskOrgLinkDefine.getEntity());
                corporateData.setTitle(iEntityDefine.getTitle());
            }
            list.add(corporateData);
        }
        return list;
    }

    public String getCorporateEntityCode(TaskDefine taskDefine) {
        String corporateEntityCode = null;
        if (taskDefine == null) {
            return null;
        }
        ContextExtension workflowCorporate = NpContextHolder.getContext().getExtension("WORKFLOW_CORPORATE");
        Object object = workflowCorporate.get(taskDefine.getKey());
        if (object != null && !"".equals(object)) {
            corporateEntityCode = object.toString();
            return corporateEntityCode;
        }
        List<DataDimension> dataSchemeDimension = this.workflowReportDimService.getDataDimension(taskDefine.getKey());
        IEntityModel dwEntityModel = null;
        for (DataDimension dimension : dataSchemeDimension) {
            IEntityAttribute attribute;
            String dimAttribute;
            Boolean reportDim = dimension.getReportDim();
            if (reportDim == null || !reportDim.booleanValue() || DimensionType.DIMENSION != dimension.getDimensionType() || (dimAttribute = dimension.getDimAttribute()) == null) continue;
            if (dwEntityModel == null) {
                dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
            }
            if ((attribute = dwEntityModel.getAttribute(dimAttribute)) == null || attribute.isMultival()) continue;
            String dimensionName = this.entityMetaService.getDimensionName(dimension.getDimKey());
            workflowCorporate.put(taskDefine.getKey(), (Serializable)((Object)dimensionName));
            return dimensionName;
        }
        return null;
    }
}

