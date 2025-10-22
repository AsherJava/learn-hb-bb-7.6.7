/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.dataservice.core.dimension.build;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.adjustment.util.AdjustUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.build.DimColBuildContext;
import com.jiuqi.nr.dataservice.core.dimension.build.SpecificDimBuilder;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DimensionBuildUtil {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;

    public DimensionCollection getDimensionCollection(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        return this.getDimensionCollection(dimensionValueSet, formSchemeKey, null);
    }

    public DimensionCollection getDimensionCollection(DimensionValueSet dimensionValueSet, String formSchemeKey, SpecificDimBuilder specificDimBuilder) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String contextMainDimId = DimensionBuildUtil.getContextMainDimId(formScheme.getDw());
        EntityData dw = this.getEntity(contextMainDimId);
        EntityData period = this.getPeriodEntity(formScheme.getDateTime());
        List<EntityData> dimEntities = this.getDimEntities(formScheme);
        String dataScheme = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        builder.setContext(new DimensionContext(formScheme.getTaskKey()));
        DimColBuildContext dimColBuildContext = new DimColBuildContext();
        dimColBuildContext.setTaskDefine(this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()));
        dimColBuildContext.setFormSchemeDefine(formScheme);
        dimColBuildContext.setDimensionValueSet(dimensionValueSet);
        dimColBuildContext.setDataSchemeKey(dataScheme);
        dimColBuildContext.setDimensionCollectionBuilder(builder);
        dimColBuildContext.setDimensionProviderFactory(this.dimensionProviderFactory);
        dimColBuildContext.setMainDimId(contextMainDimId);
        String periodValue = (String)dimensionValueSet.getValue(period.getDimensionName());
        if (specificDimBuilder != null && specificDimBuilder.isUse(dimColBuildContext, period.getKey())) {
            specificDimBuilder.setBuildInfo(dimColBuildContext, period.getKey(), period.getDimensionName());
        } else {
            builder.setEntityValue(period.getDimensionName(), period.getKey(), periodValue);
        }
        if (specificDimBuilder != null && specificDimBuilder.isUse(dimColBuildContext, dw.getKey())) {
            specificDimBuilder.setBuildInfo(dimColBuildContext, dw.getKey(), dw.getDimensionName());
        } else {
            this.buildDimension(dimColBuildContext, builder, dw, "PROVIDER_FILTERDWBYVERSION", true);
        }
        for (EntityData dimEntity : dimEntities) {
            if (specificDimBuilder != null && specificDimBuilder.isUse(dimColBuildContext, dimEntity.getKey())) {
                specificDimBuilder.setBuildInfo(dimColBuildContext, dimEntity.getKey(), dimEntity.getDimensionName());
                continue;
            }
            if (!dimensionValueSet.hasValue(dimEntity.getDimensionName())) continue;
            if ("MD_CURRENCY".equals(dimEntity.getDimensionName()) && ("PROVIDER_BASECURRENCY".equals(dimensionValueSet.getValue("MD_CURRENCY").toString()) || "PROVIDER_PBASECURRENCY".equals(dimensionValueSet.getValue("MD_CURRENCY").toString()))) {
                this.buildCurrency(dataScheme, dimensionValueSet.getValue("MD_CURRENCY").toString(), builder, dimEntity);
                continue;
            }
            boolean entityRefer = StringUtils.isNotEmpty((String)this.formSchemeService.getDimAttributeByReportDim(formSchemeKey, dimEntity.getKey()));
            if (entityRefer) {
                this.buildDimension(dimColBuildContext, builder, dimEntity, "PROVIDER_FILTERDIMBYDW", false);
                continue;
            }
            this.buildDimension(dimColBuildContext, builder, dimEntity, "PROVIDER_ALLNODE", false);
        }
        return builder.getCollection();
    }

    private void buildDimension(DimColBuildContext context, DimensionCollectionBuilder builder, EntityData entityData, String type, boolean dw) {
        Object dimDimensionValue = context.getDimensionValueSet().getValue(entityData.getDimensionName());
        if (dimDimensionValue != null) {
            DimensionProviderData dimensionProviderData;
            ArrayList<String> choosedValues = new ArrayList<String>();
            if (dimDimensionValue instanceof String) {
                String str = (String)dimDimensionValue;
                if (StringUtils.isNotEmpty((String)str)) {
                    choosedValues.add(str);
                }
            } else if (dimDimensionValue instanceof List) {
                choosedValues.addAll((List)dimDimensionValue);
            }
            if (choosedValues.size() == 1 && this.isVar((String)choosedValues.get(0))) {
                dimensionProviderData = new DimensionProviderData(null, context.getDataSchemeKey());
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider((String)choosedValues.get(0), dimensionProviderData);
                if (dw) {
                    builder.addVariableDW(entityData.getDimensionName(), entityData.getKey(), dimensionProvider);
                } else {
                    builder.addVariableDimension(entityData.getDimensionName(), entityData.getKey(), dimensionProvider);
                }
            } else {
                dimensionProviderData = new DimensionProviderData(choosedValues, context.getDataSchemeKey());
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
                if (dw) {
                    builder.addVariableDW(entityData.getDimensionName(), entityData.getKey(), dimensionProvider);
                } else if ("PROVIDER_ALLNODE".equals(type) && !CollectionUtils.isEmpty(choosedValues)) {
                    builder.setEntityValue(entityData.getDimensionName(), entityData.getKey(), choosedValues.toArray());
                } else {
                    builder.addVariableDimension(entityData.getDimensionName(), entityData.getKey(), dimensionProvider);
                }
            }
        }
    }

    public DimensionCollection getDimensionCollection(Map<String, DimensionValue> dimensionSet, String formSchemeKey) {
        return this.getDimensionCollection(dimensionSet, formSchemeKey, null);
    }

    public DimensionCollection getDimensionCollection(Map<String, DimensionValue> dimensionSet, String formSchemeKey, SpecificDimBuilder specificDimBuilder) {
        DimensionValueSet dimensionValueSet = DimensionBuildUtil.getDimensionValueSet(dimensionSet);
        return this.getDimensionCollection(dimensionValueSet, formSchemeKey, specificDimBuilder);
    }

    private void buildCurrency(String dataScheme, String type, DimensionCollectionBuilder builder, EntityData dimEntity) {
        DimensionProviderData dimensionProviderData = new DimensionProviderData(null, dataScheme);
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
        builder.addVariableDimension(dimEntity.getDimensionName(), dimEntity.getKey(), dimensionProvider);
    }

    private boolean isVar(String value) {
        return "PROVIDER_ALLNODE".equals(value) || "PROVIDER_ALLCHILDREN".equals(value) || "PROVIDER_CHILDREN".equals(value) || "PROVIDER_FILTERDIMBYDW".equals(value) || "PROVIDER_FILTERDWBYVERSION".equals(value) || "PROVIDER_BASECURRENCY".equals(value) || "PROVIDER_PBASECURRENCY".equals(value) || "PROVIDER_LEAFNODE".equals(value);
    }

    private static DimensionValueSet getDimensionValueSet(Map<String, DimensionValue> dimensionSet) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            if (value.getValue() == null) continue;
            String[] values = value.getValue().split(";");
            if (values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            if (values.length == 1) {
                dimensionValueSet.setValue(value.getName(), (Object)values[0]);
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        return dimensionValueSet;
    }

    private static String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }

    private EntityData getPeriodEntity(String entityID) {
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(entityID);
        if (periodEntity != null) {
            return new EntityData(entityID, periodEntity.getDimensionName());
        }
        throw new IllegalArgumentException(entityID + " is not a period entity");
    }

    private List<EntityData> getDimEntities(FormSchemeDefine formSchemeDefine) {
        ArrayList<EntityData> result = new ArrayList<EntityData>();
        String dims = formSchemeDefine.getDims();
        List<Object> dimEntityIds = new ArrayList();
        if (StringUtils.isNotEmpty((String)dims)) {
            dimEntityIds = Arrays.asList(dims.split(";"));
            for (String string : dimEntityIds) {
                result.add(this.getEntity(string));
            }
        }
        if (!dimEntityIds.contains("ADJUST")) {
            this.appendAdjustIfExist(result, formSchemeDefine.getKey());
        }
        return result;
    }

    private void appendAdjustIfExist(List<EntityData> entityData, String formSchemeKey) {
        if (this.formSchemeService.enableAdjustPeriod(formSchemeKey)) {
            EntityData adjust = new EntityData("ADJUST", "ADJUST");
            entityData.add(adjust);
        }
    }

    private EntityData getEntity(String entityID) {
        if (AdjustUtils.isAdjust((String)entityID).booleanValue()) {
            return new EntityData("ADJUST", "ADJUST");
        }
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityID);
        if (entityDefine != null) {
            return new EntityData(entityID, entityDefine.getDimensionName());
        }
        throw new IllegalArgumentException("Entity " + entityID + " is not defined");
    }

    static class EntityData {
        private final String key;
        private final String dimensionName;

        public EntityData(String key, String dimensionName) {
            this.key = key;
            this.dimensionName = dimensionName;
        }

        public String getDimensionName() {
            return this.dimensionName;
        }

        public String getKey() {
            return this.key;
        }
    }
}

