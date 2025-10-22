/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.exeception.LogicCheckedException;
import com.jiuqi.nr.data.logic.facade.extend.SpecificDimBuilder;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.FixedDimBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DimensionCollectionUtil {
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private IFormSchemeService formSchemeService;

    public DimensionCollection getDimensionCollection(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        return this.getDimensionCollection(dimensionValueSet, formSchemeKey, null);
    }

    public DimensionCollection getDimensionCollection(DimensionValueSet dimensionValueSet, String formSchemeKey, SpecificDimBuilder specificDimBuilder) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        EntityData dw = this.entityUtil.getEntity(this.entityUtil.getContextMainDimId(formScheme.getDw()));
        EntityData period = this.entityUtil.getPeriodEntity(formScheme.getDateTime());
        List<EntityData> dimEntities = this.entityUtil.getDimEntities(formScheme);
        String dataScheme = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        String periodValue = (String)dimensionValueSet.getValue(period.getDimensionName());
        if (specificDimBuilder != null && specificDimBuilder.isUse(period.getKey())) {
            specificDimBuilder.setBuildInfo(builder, period.getKey(), period.getDimensionName());
        } else {
            builder.setEntityValue(period.getDimensionName(), period.getKey(), new Object[]{periodValue});
        }
        Date date = this.entityUtil.period2Date(period.getKey(), periodValue);
        DimColBuildContext dimColBuildContext = new DimColBuildContext();
        dimColBuildContext.setFormSchemeDefine(formScheme);
        dimColBuildContext.setDimensionValueSet(dimensionValueSet);
        dimColBuildContext.setPeriodDate(date);
        dimColBuildContext.setDataSchemeKey(dataScheme);
        if (specificDimBuilder != null && specificDimBuilder.isUse(dw.getKey())) {
            specificDimBuilder.setBuildInfo(builder, dw.getKey(), dw.getDimensionName());
        } else {
            this.buildDimension(dimColBuildContext, builder, dw, "PROVIDER_FILTERDWBYVERSION", true);
        }
        for (EntityData dimEntity : dimEntities) {
            if (specificDimBuilder != null && specificDimBuilder.isUse(dimEntity.getKey())) {
                specificDimBuilder.setBuildInfo(builder, dimEntity.getKey(), dimEntity.getDimensionName());
                continue;
            }
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
            List<Object> choosedValues = new ArrayList<String>();
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
                if (CollectionUtils.isEmpty(choosedValues)) {
                    choosedValues = this.entityUtil.getFSFilteredEntityRows(context.getFormSchemeDefine(), entityData.getKey(), context.getPeriodDate()).stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
                }
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
        DimensionValueSet dimensionValueSet = DimensionUtil.getDimensionValueSet(dimensionSet);
        return this.getDimensionCollection(dimensionValueSet, formSchemeKey, specificDimBuilder);
    }

    private void buildCurrency(String dataScheme, String type, DimensionCollectionBuilder builder, EntityData dimEntity) {
        DimensionProviderData dimensionProviderData = new DimensionProviderData(null, dataScheme);
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
        builder.addVariableDimension(dimEntity.getDimensionName(), dimEntity.getKey(), dimensionProvider);
    }

    @Nullable
    public DimensionValueSet getMergeDimensionValueSet(DimensionCollection dimensionCollection) {
        return DimensionUtil.getMergeDimensionValueSet(dimensionCollection);
    }

    public Map<String, DimensionValue> getDimensionValues(DimensionCollection dimensionCollection) {
        return DimensionUtil.getDimensionValues(dimensionCollection);
    }

    public List<DimensionValueSet> mergeDimensionWithDep(DimensionCollection dimensionCollection, List<String> depDimNames) throws LogicCheckedException {
        return DimensionUtil.mergeDimensionWithDep(dimensionCollection, depDimNames);
    }

    public List<String> getDimKeySet(DimensionCollection dimensionCollection, String dimensionName) {
        return DimensionUtil.getDimKeySet(dimensionCollection, dimensionName);
    }

    private boolean isVar(String value) {
        return "PROVIDER_ALLNODE".equals(value) || "PROVIDER_ALLCHILDREN".equals(value) || "PROVIDER_CHILDREN".equals(value) || "PROVIDER_FILTERDIMBYDW".equals(value) || "PROVIDER_FILTERDWBYVERSION".equals(value) || "PROVIDER_BASECURRENCY".equals(value) || "PROVIDER_PBASECURRENCY".equals(value);
    }

    public List<DimensionCollection> mergeDimensionByDw(DimensionCollection dimensionCollection, String dimensionName, FormSchemeDefine formScheme) {
        List<DimensionValueSet> dimensionValueSets = DimensionUtil.mergeDimensionByDw(dimensionCollection, dimensionName);
        if (CollectionUtils.isEmpty(dimensionValueSets)) {
            return Collections.emptyList();
        }
        ArrayList<DimensionCollection> result = new ArrayList<DimensionCollection>(dimensionValueSets.size());
        for (DimensionValueSet dimensionValueSet : dimensionValueSets) {
            FixedDimBuilder fixedDimBuilder = new FixedDimBuilder(this.entityUtil.getContextMainDimId(formScheme.getDw()), dimensionValueSet);
            DimensionCollection dim = this.getDimensionCollection(dimensionValueSet, formScheme.getKey(), (SpecificDimBuilder)fixedDimBuilder);
            result.add(dim);
        }
        return result;
    }

    static class DimColBuildContext {
        private FormSchemeDefine formSchemeDefine;
        private DimensionValueSet dimensionValueSet;
        private Date periodDate;
        private String dataSchemeKey;

        DimColBuildContext() {
        }

        public FormSchemeDefine getFormSchemeDefine() {
            return this.formSchemeDefine;
        }

        public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
            this.formSchemeDefine = formSchemeDefine;
        }

        public DimensionValueSet getDimensionValueSet() {
            return this.dimensionValueSet;
        }

        public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
            this.dimensionValueSet = dimensionValueSet;
        }

        public Date getPeriodDate() {
            return this.periodDate;
        }

        public void setPeriodDate(Date periodDate) {
            this.periodDate = periodDate;
        }

        public String getDataSchemeKey() {
            return this.dataSchemeKey;
        }

        public void setDataSchemeKey(String dataSchemeKey) {
            this.dataSchemeKey = dataSchemeKey;
        }
    }
}

