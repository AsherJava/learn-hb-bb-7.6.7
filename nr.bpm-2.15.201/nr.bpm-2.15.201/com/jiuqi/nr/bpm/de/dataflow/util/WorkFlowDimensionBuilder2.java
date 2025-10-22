/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionContext
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.util.StringUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkFlowDimensionBuilder2 {
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

    public DimensionCollection buildDimensionCollection(String taskKey, Map<String, DimensionValue> dimensionSet) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        List dataSchemeDimension = this.taskService.getDataDimension(taskKey);
        DimensionCollectionBuilder dimensionBuilder = new DimensionCollectionBuilder();
        DimensionContext dimensionContext = new DimensionContext(taskKey);
        dimensionBuilder.setContext(dimensionContext);
        for (DataDimension dimension : dataSchemeDimension) {
            VariableDimensionValueProvider dimensionProvider;
            DimensionProviderData providerData;
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
                String dimName = this.getDimensionName(dimId);
                DimensionProviderData providerData2 = new DimensionProviderData();
                String[] split = value.split(";");
                List entityKeys = Arrays.stream(split).filter(element -> element != null && !element.trim().isEmpty()).collect(Collectors.toList());
                providerData2.setChoosedValues(entityKeys);
                VariableDimensionValueProvider dimensionProvider2 = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", providerData2);
                dimensionBuilder.addVariableDW(dimName, dimId, dimensionProvider2);
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                dimensionValue = dimensionSet.get(dimensionName);
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{dimensionValue.getValue()});
                continue;
            }
            if (this.isCorporate(taskDefine, dimension, dwEntityModel)) {
                providerData = new DimensionProviderData();
                providerData.setDataSchemeKey(taskDefine.getDataScheme());
                providerData.setAuthorityType(AuthorityType.Read);
                dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData);
                dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
                continue;
            }
            if (DimensionType.DIMENSION != dimension.getDimensionType()) continue;
            providerData = new DimensionProviderData();
            providerData.setDataSchemeKey(taskDefine.getDataScheme());
            providerData.setAuthorityType(AuthorityType.Read);
            dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_ALLNODE", providerData);
            dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
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

    private boolean isCorporate(TaskDefine taskDefine, DataDimension dimension, IEntityModel dwEntityModel) {
        String dimAttribute = dimension.getDimAttribute();
        List reportDimension = this.taskService.getReportDimension(taskDefine.getKey());
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
        DataDimension report = reportDimension.stream().filter(dataDimension -> dimension.getDimKey().equals(dataDimension.getDimKey())).findFirst().orElse(null);
        String dimReferAttr = report == null ? null : report.getDimAttribute();
        return DimensionType.DIMENSION == dimension.getDimensionType() && attribute != null && !attribute.isMultival() && StringUtils.isNotEmpty((String)dimReferAttr);
    }
}

