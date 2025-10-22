/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 */
package com.jiuqi.nr.datastatus.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.datastatus.internal.obj.EntityData;
import com.jiuqi.nr.datastatus.internal.util.DimensionUtil;
import com.jiuqi.nr.datastatus.internal.util.EntityUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        EntityData dw = this.entityUtil.getEntity(this.entityUtil.getContextMainDimId(formScheme.getDw()));
        EntityData period = this.entityUtil.getPeriodEntity(formScheme.getDateTime());
        List<EntityData> dimEntities = this.entityUtil.getDimEntities(formScheme);
        String dataScheme = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        String periodValue = (String)dimensionValueSet.getValue(period.getDimensionName());
        builder.setEntityValue(period.getDimensionName(), period.getKey(), new Object[]{periodValue});
        Date date = this.entityUtil.period2Date(period.getKey(), periodValue);
        DimColBuildContext dimColBuildContext = new DimColBuildContext();
        dimColBuildContext.setFormSchemeDefine(formScheme);
        dimColBuildContext.setDimensionValueSet(dimensionValueSet);
        dimColBuildContext.setPeriodDate(date);
        dimColBuildContext.setDataSchemeKey(dataScheme);
        this.buildDimension(dimColBuildContext, builder, dw, "PROVIDER_FILTERDWBYVERSION", true);
        for (EntityData dimEntity : dimEntities) {
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
        DimensionValueSet dimensionValueSet = DimensionUtil.getDimensionValueSet(dimensionSet);
        return this.getDimensionCollection(dimensionValueSet, formSchemeKey);
    }

    private void buildCurrency(String dataScheme, String type, DimensionCollectionBuilder builder, EntityData dimEntity) {
        DimensionProviderData dimensionProviderData = new DimensionProviderData(null, dataScheme);
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
        builder.addVariableDimension(dimEntity.getDimensionName(), dimEntity.getKey(), dimensionProvider);
    }

    public DimensionValueSet getMergeDimensionValueSet(DimensionCollection dimensionCollection) {
        List<DimensionValueSet> allDimension = dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allDimension)) {
            return new DimensionValueSet();
        }
        if (allDimension.size() == 1) {
            return (DimensionValueSet)allDimension.get(0);
        }
        return this.merge(allDimension);
    }

    private DimensionValueSet merge(List<DimensionValueSet> allDimension) {
        LinkedHashMap dimensionMap = new LinkedHashMap();
        for (DimensionValueSet dimensionValueSet : allDimension) {
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String name = dimensionValueSet.getName(i);
                Object value = dimensionValueSet.getValue(i);
                if (dimensionMap.containsKey(name)) {
                    ((Set)dimensionMap.get(name)).add(value);
                    continue;
                }
                HashSet<Object> valueSet = new HashSet<Object>();
                valueSet.add(value);
                dimensionMap.put(name, valueSet);
            }
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry entry : dimensionMap.entrySet()) {
            String dimensionName = (String)entry.getKey();
            Set valueSet = (Set)entry.getValue();
            if (valueSet.size() == 1) {
                for (Object o : valueSet) {
                    dimensionValueSet.setValue(dimensionName, o);
                }
                continue;
            }
            dimensionValueSet.setValue(dimensionName, new ArrayList(valueSet));
        }
        return dimensionValueSet;
    }

    public List<DimensionValueSet> mergeDimensionWithDep(DimensionCollection dimensionCollection, List<String> depDimNames) throws RuntimeException {
        List<DimensionValueSet> allDimension = dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allDimension)) {
            throw new RuntimeException("\u7ef4\u5ea6\u4fe1\u606f\u5f02\u5e38");
        }
        if (allDimension.size() == 1) {
            return allDimension;
        }
        ArrayList<DimensionValueSet> result = new ArrayList<DimensionValueSet>();
        if (CollectionUtils.isEmpty(depDimNames)) {
            result.add(this.merge(allDimension));
        } else {
            Map<String, List<DimensionValueSet>> m = this.groupByDims(depDimNames, allDimension);
            m.values().forEach(o -> result.add(this.merge((List<DimensionValueSet>)o)));
        }
        return result;
    }

    private Map<String, List<DimensionValueSet>> groupByDims(List<String> depDimNames, List<DimensionValueSet> allDimension) {
        HashMap<String, List<DimensionValueSet>> m = new HashMap<String, List<DimensionValueSet>>();
        for (DimensionValueSet dimensionValueSet : allDimension) {
            StringBuilder s = new StringBuilder();
            for (String depDimName : depDimNames) {
                s.append((String)dimensionValueSet.getValue(depDimName)).append(";");
            }
            String str = s.toString();
            if (m.containsKey(str)) {
                ((List)m.get(str)).add(dimensionValueSet);
                continue;
            }
            ArrayList<DimensionValueSet> l = new ArrayList<DimensionValueSet>();
            l.add(dimensionValueSet);
            m.put(str, l);
        }
        return m;
    }

    public List<String> getDimKeySet(DimensionCollection dimensionCollection, String dimensionName) {
        HashSet dimKeySet = new HashSet();
        dimensionCollection.getDimensionCombinations().forEach(o -> {
            Object value = o.getValue(dimensionName);
            if (value instanceof String) {
                dimKeySet.add((String)value);
            } else if (value instanceof List) {
                dimKeySet.addAll((List)value);
            }
        });
        return new ArrayList<String>(dimKeySet);
    }

    private boolean isVar(String value) {
        return "PROVIDER_ALLNODE".equals(value) || "PROVIDER_ALLCHILDREN".equals(value) || "PROVIDER_CHILDREN".equals(value) || "PROVIDER_FILTERDIMBYDW".equals(value) || "PROVIDER_FILTERDWBYVERSION".equals(value) || "PROVIDER_BASECURRENCY".equals(value) || "PROVIDER_PBASECURRENCY".equals(value);
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

