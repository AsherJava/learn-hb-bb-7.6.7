/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.data.access.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.param.EntityDimData;
import com.jiuqi.nr.data.access.util.DataAccesslUtil;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DimCollectionBuildUtil {
    @Autowired
    private DataAccesslUtil dataAccesslUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormSchemeService formSchemeService;

    public DimensionCollection buildDimensionCollection(DimensionValueSet dimensionSet, String formSchemeKey) {
        Map<String, DimensionValue> dimMap = DimensionValueSetUtil.getDimensionSet(dimensionSet);
        return this.buildDimensionCollection(dimMap, formSchemeKey);
    }

    public DimensionCollection buildDimensionCollection(Map<String, DimensionValue> dimensionSet, String formSchemeKey) {
        EntityDimData dw = this.dataAccesslUtil.getDwEntityDimData(formSchemeKey);
        EntityDimData period = this.dataAccesslUtil.getPeriodEntityDimData(formSchemeKey);
        List<EntityDimData> dimEntities = this.dataAccesslUtil.getDimEntityDimData(formSchemeKey);
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        String dataScheme = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        this.buildPeriodDimension(builder, period, dimensionSet);
        this.buildDimension(dimensionSet, formSchemeKey, dataScheme, builder, dw, "PROVIDER_FILTERDWBYVERSION", true);
        for (EntityDimData dimEntity : dimEntities) {
            DimensionValue currenCy = dimensionSet.get("MD_CURRENCY");
            if ("MD_CURRENCY".equals(dimEntity.getDimensionName()) && currenCy != null && ("PROVIDER_BASECURRENCY".equals(currenCy.getValue()) || "PROVIDER_PBASECURRENCY".equals(currenCy.getValue()))) {
                this.buildCurrency(dataScheme, dimensionSet.get("MD_CURRENCY").getValue(), builder, dimEntity);
                continue;
            }
            if ("ADJUST".equals(dimEntity.getDimensionName())) {
                DimensionValue adjustDim = dimensionSet.get("ADJUST");
                if (Objects.isNull(adjustDim)) continue;
                String adjust = adjustDim.getValue();
                builder.setEntityValue(dimEntity.getDimensionName(), "ADJUST", new Object[]{adjust});
                continue;
            }
            boolean entityRefer = this.entityMetaService.estimateEntityRefer(dw.getEntityId(), dimEntity.getEntityId());
            if (entityRefer) {
                this.buildDimension(dimensionSet, formSchemeKey, dataScheme, builder, dimEntity, "PROVIDER_FILTERDIMBYDW", false);
                continue;
            }
            this.buildDimension(dimensionSet, formSchemeKey, dataScheme, builder, dimEntity, "PROVIDER_ALLNODE", false);
        }
        return builder.getCollection();
    }

    public DimensionCollection buildDimensionCollection(DimensionValueSet dimensionSet, String formSchemeKey, List<VariableDimensionValue> variableDimensionValues) {
        Map<String, DimensionValue> dimMap = DimensionValueSetUtil.getDimensionSet(dimensionSet);
        EntityDimData dw = this.dataAccesslUtil.getDwEntityDimData(formSchemeKey);
        EntityDimData period = this.dataAccesslUtil.getPeriodEntityDimData(formSchemeKey);
        FormSchemeDefine formScheme = this.dataAccesslUtil.queryFormScheme(formSchemeKey);
        String dataScheme = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey()).getDataScheme();
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        this.buildPeriodDimension(builder, period, dimMap);
        this.buildDimension(dimMap, formSchemeKey, dataScheme, builder, dw, "PROVIDER_FILTERDWBYVERSION", true);
        for (VariableDimensionValue vriableDimensionValue : variableDimensionValues) {
            builder.addVariableDimension(vriableDimensionValue.getName(), vriableDimensionValue.getEntityID(), vriableDimensionValue.getProvider());
        }
        return builder.getCollection();
    }

    public DimensionCombination buildDimensionCombination(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        DimensionCombinationBuilder dimensionCollectionBuilder = new DimensionCombinationBuilder();
        Map<String, String> entityMap = this.dataAccesslUtil.getAllEntityIdDimensionMap(formSchemeKey);
        for (int j = 0; j < dimensionValueSet.size(); ++j) {
            String name = dimensionValueSet.getName(j);
            String entityId = entityMap.get(name);
            Object value = dimensionValueSet.getValue(name);
            if (value == null) {
                dimensionCollectionBuilder.setValue(dimensionValueSet.getName(j), entityId, (Object)"");
                continue;
            }
            if (value instanceof List || value.toString().contains(";")) {
                throw new RuntimeException("\u56fa\u5b9a\u7ef4\u5ea6\u4e0d\u652f\u6301\u7ef4\u5ea6\u8303\u56f4\uff01");
            }
            dimensionCollectionBuilder.setValue(dimensionValueSet.getName(j), entityId, value);
        }
        DimensionCombination collection = dimensionCollectionBuilder.getCombination();
        return collection;
    }

    public DimensionCollection buildCollectionByCombination(DimensionCombination combination) {
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        FixedDimensionValue dwDimensionValue = combination.getDWDimensionValue();
        if (dwDimensionValue != null) {
            builder.setDWValue(dwDimensionValue.getName(), dwDimensionValue.getEntityID(), new Object[]{dwDimensionValue.getValue()});
            combination.getNames().stream().filter(e -> !e.equalsIgnoreCase(dwDimensionValue.getName())).forEach(name -> {
                FixedDimensionValue dimensionValue = combination.getFixedDimensionValue(name);
                builder.setEntityValue(dimensionValue.getName(), dimensionValue.getEntityID(), new Object[]{dimensionValue.getValue()});
            });
        } else {
            combination.getNames().stream().forEach(name -> {
                FixedDimensionValue dimensionValue = combination.getFixedDimensionValue(name);
                builder.setEntityValue(dimensionValue.getName(), dimensionValue.getEntityID(), new Object[]{dimensionValue.getValue()});
            });
        }
        return builder.getCollection();
    }

    private void buildPeriodDimension(DimensionCollectionBuilder builder, EntityDimData period, Map<String, DimensionValue> dimensionSet) {
        String value = dimensionSet.get(period.getDimensionName()).getValue();
        ArrayList<String> choosedValues = new ArrayList<String>();
        if (StringUtils.isNotEmpty((String)value) && value.contains(";")) {
            choosedValues.addAll(Arrays.asList(value.split(";")));
            builder.setEntityValue(period.getDimensionName(), period.getEntityId(), new Object[]{choosedValues});
        } else if (StringUtils.isNotEmpty((String)value)) {
            builder.setEntityValue(period.getDimensionName(), period.getEntityId(), new Object[]{value});
        }
    }

    private void buildDimension(Map<String, DimensionValue> dimensionSet, String formSchemeKey, String dataScheme, DimensionCollectionBuilder builder, EntityDimData entityData, String type, boolean dw) {
        String value;
        DimensionValue dimensionValue = dimensionSet.get(entityData.getDimensionName());
        List<Object> choosedValues = new ArrayList();
        DimensionProviderData dimensionProviderData = new DimensionProviderData(choosedValues, dataScheme);
        if (dimensionValue != null && dimensionValue.getValue() != null && StringUtils.isNotEmpty((String)(value = dimensionValue.getValue()))) {
            choosedValues = Arrays.asList(value.split(";"));
            dimensionProviderData.setChoosedValues(choosedValues);
        }
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
        if (dw) {
            String rowFilterExpression;
            EntityViewDefine view = this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
            if (view != null && StringUtils.isNotEmpty((String)(rowFilterExpression = view.getRowFilterExpression()))) {
                dimensionProviderData.setFilter(rowFilterExpression);
            }
            if (choosedValues.size() > 0) {
                if (choosedValues.size() == 1) {
                    builder.setDWValue(entityData.getDimensionName(), entityData.getEntityId(), new Object[]{choosedValues.get(0)});
                } else {
                    builder.setDWValue(entityData.getDimensionName(), entityData.getEntityId(), new Object[]{choosedValues});
                }
            } else {
                builder.addVariableDW(entityData.getDimensionName(), entityData.getEntityId(), dimensionProvider);
            }
        } else if ("PROVIDER_ALLNODE".equals(type) && !CollectionUtils.isEmpty(choosedValues)) {
            builder.setEntityValue(entityData.getDimensionName(), entityData.getEntityId(), choosedValues.toArray());
        } else {
            builder.addVariableDimension(entityData.getDimensionName(), entityData.getEntityId(), dimensionProvider);
        }
    }

    private void buildCurrency(String dataScheme, String type, DimensionCollectionBuilder builder, EntityDimData dimEntity) {
        DimensionProviderData dimensionProviderData = new DimensionProviderData(null, dataScheme);
        VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider(type, dimensionProviderData);
        builder.addVariableDimension(dimEntity.getDimensionName(), dimEntity.getEntityId(), dimensionProvider);
    }

    public DimensionCollection buildDimensionCollectionNoFilter(DimensionValueSet currDimensionValue, String formSchemeKey) {
        int size = currDimensionValue.size();
        boolean enableAdjust = this.formSchemeService.enableAdjustPeriod(formSchemeKey);
        if (enableAdjust) {
            --size;
        }
        EntityDimData dw = this.dataAccesslUtil.getDwEntityDimData(formSchemeKey);
        EntityDimData period = this.dataAccesslUtil.getPeriodEntityDimData(formSchemeKey);
        List<EntityDimData> dimEntities = this.dataAccesslUtil.getDimEntityDimData(formSchemeKey, false);
        ArrayList<EntityDimData> entityList = new ArrayList<EntityDimData>();
        entityList.add(dw);
        entityList.add(period);
        entityList.addAll(dimEntities);
        if (size != entityList.size()) {
            return null;
        }
        Map<String, DimensionValue> dimensionSetMap = DimensionValueSetUtil.getDimensionSet(currDimensionValue);
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        for (int i = 0; i < size; ++i) {
            EntityDimData entityViewData = (EntityDimData)entityList.get(i);
            String name = entityViewData.getDimensionName();
            DimensionValue dimensionValue = dimensionSetMap.get(name);
            if (dimensionValue == null || dimensionValue.getValue() == null || StringUtils.isEmpty((String)dimensionValue.getValue())) {
                return null;
            }
            String value = dimensionValue.getValue();
            String[] values = value.split(";");
            if (values.length > 1) {
                return null;
            }
            if (name.equalsIgnoreCase(dw.getDimensionName())) {
                builder.setDWValue(name, entityViewData.getEntityId(), new Object[]{value});
                continue;
            }
            if ("MD_CURRENCY".equals(name) && ("PROVIDER_BASECURRENCY".equals(value) || "PROVIDER_PBASECURRENCY".equals(value))) {
                return null;
            }
            builder.setEntityValue(name, entityViewData.getEntityId(), new Object[]{value});
        }
        if (enableAdjust) {
            DimensionValue adjustDim = dimensionSetMap.get("ADJUST");
            if (Objects.isNull(adjustDim)) {
                builder.setEntityValue("ADJUST", "ADJUST", new Object[]{0});
            } else {
                String adjust = adjustDim.getValue();
                builder.setEntityValue("ADJUST", "ADJUST", new Object[]{adjust});
            }
        }
        return builder.getCollection();
    }
}

