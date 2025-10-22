/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 */
package com.jiuqi.nr.dataservice.core.dimension.provider;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.DimensionEnvironment;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.AllNodeDimensionProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FilterDimByDw
extends AllNodeDimensionProvider {
    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory.getLogger(FilterDimByDw.class);
    protected transient DimensionProviderUtil providerUtil;
    public static final String PROVIDER_FILTERDIMBYDW = "PROVIDER_FILTERDIMBYDW";
    private final transient Map<String, IEntityTable> entityTableMap = new HashMap<String, IEntityTable>();

    public FilterDimByDw(DimensionProviderData dimensionProviderData) {
        super(dimensionProviderData);
    }

    @Override
    public List<String> getValues(DimensionContext context, VariableDimensionValue variableDimensionValue, DimensionCombination dimensionCombination) {
        List<String> result;
        FixedDimensionValue periodDimension;
        String periodStr;
        if (!StringUtils.hasText(this.dimensionProviderData.getDataSchemeKey())) {
            this.log.debug("\u65e0\u6570\u636e\u65b9\u6848\uff0c\u8fd4\u56de\u5df2\u9009\u503c\u5217\u8868");
            return this.dimensionProviderData.getChoosedValues();
        }
        String entityId = variableDimensionValue.getEntityID();
        if (!StringUtils.hasLength(entityId)) {
            this.log.debug("\u5f53\u524d\u5c55\u5f00\u7ef4\u5ea6\u65e0\u5b9e\u4f53ID\uff0c\u8fd4\u56de\u5df2\u9009\u503c\u5217\u8868");
            return this.dimensionProviderData.getChoosedValues();
        }
        List dimensions = DimensionEnvironment.getDataSchemeService().getDataSchemeDimension(this.dimensionProviderData.getDataSchemeKey());
        Optional<DataDimension> dimensionOptional = dimensions.stream().filter(d -> d.getDimKey().equalsIgnoreCase(entityId)).findFirst();
        Optional<DataDimension> dwOptional = dimensions.stream().filter(d -> d.getDimensionType() == DimensionType.UNIT).findFirst();
        if (!dimensionOptional.isPresent() || !dwOptional.isPresent()) {
            this.log.debug("\u6570\u636e\u65b9\u6848\u5185\u672a\u627e\u5230\u5355\u4f4d\u7ef4\u5ea6\uff0c\u8fd4\u56de\u5df2\u9009\u503c\u5217\u8868");
            return this.dimensionProviderData.getChoosedValues();
        }
        if (!StringUtils.hasLength(dimensionOptional.get().getDimAttribute())) {
            this.log.debug("\u4e3b\u7ef4\u5ea6\u4e0e\u60c5\u666f\u65e0\u5173\u8fd4\u56de\u6240\u6709\uff01");
            return this.choosedOrAllNode(variableDimensionValue, dimensionCombination);
        }
        FixedDimensionValue dwDimension = dimensionCombination.getDWDimensionValue();
        if (dwDimension == null) {
            String dwDimensionName = DimensionEnvironment.getEntityMetaService().getDimensionName(dwOptional.get().getDimKey());
            dwDimension = dimensionCombination.getFixedDimensionValue(dwDimensionName);
        }
        if (dwDimension == null) {
            this.log.debug("\u5f53\u524d\u65e0\u5355\u4f4d\u7ef4\u5ea6\uff0c\u8fd4\u56de\u5df2\u9009\u503c\u5217\u8868");
            return this.dimensionProviderData.getChoosedValues();
        }
        String dwValue = (String)dwDimension.getValue();
        String cacheKey = this.getCacheKey(variableDimensionValue, dwValue, periodStr = (periodDimension = dimensionCombination.getPeriodDimensionValue()) != null ? (String)periodDimension.getValue() : "NOPERIOD");
        if (this.hasKey(cacheKey)) {
            return (List)this.valueCache.get(cacheKey);
        }
        String dimAttribute = dimensionOptional.get().getDimAttribute();
        try {
            IEntityRow entityRow;
            IEntityTable entityTable = this.entityTableMap.get(periodStr);
            if (entityTable == null) {
                IEntityQuery dwEntityQuery = this.getDWEntityQuery(context, dwDimension, dimensionCombination);
                entityTable = dwEntityQuery.executeFullBuild((IContext)this.getProviderUtil().getExecutorContext());
                this.entityTableMap.put(periodStr, entityTable);
            }
            if ((entityRow = entityTable.findByEntityKey((String)dwDimension.getValue())) == null) {
                return Collections.emptyList();
            }
            String dimValue = entityRow.getAsString(dimAttribute);
            if (StringUtils.hasLength(dimValue)) {
                List<String> dims = Arrays.stream(dimValue.split(";")).collect(Collectors.toList());
                result = this.filterValues(dims);
            } else {
                result = super.getValues(context, variableDimensionValue, dimensionCombination);
            }
            if (result.isEmpty()) {
                List<String> choosedValues = this.dimensionProviderData.getChoosedValues();
                List<String> excludeValues = this.dimensionProviderData.getExcludeValues();
                this.log.info("\u672a\u7b5b\u9009\u51fa\u60c5\u666f\u503c\u3002dim:{},chooseValue:{},excludeValue:{}", dimValue, CollectionUtils.isEmpty(choosedValues) ? "" : String.join((CharSequence)",", choosedValues), CollectionUtils.isEmpty(excludeValues) ? "" : String.join((CharSequence)",", excludeValues));
            }
            this.valueCache.put(cacheKey, result);
        }
        catch (Exception e) {
            result = super.getValues(context, variableDimensionValue, dimensionCombination);
            this.valueCache.put(cacheKey, result);
            this.log.error("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u51fa\u9519\u3002", e);
        }
        return result;
    }

    private List<String> choosedOrAllNode(VariableDimensionValue variableDimensionValue, DimensionCombination dimensionCombination) {
        if (CollectionUtils.isEmpty(this.dimensionProviderData.getChoosedValues())) {
            VariableDimensionValueProvider dimensionProvider = DimensionEnvironment.getFactory().getDimensionProvider("PROVIDER_ALLNODE", this.dimensionProviderData);
            return dimensionProvider.getValues(variableDimensionValue, dimensionCombination);
        }
        return this.dimensionProviderData.getChoosedValues();
    }

    protected IEntityQuery getDWEntityQuery(DimensionContext context, FixedDimensionValue dwDimensionValue, DimensionCombination dimensionCombination) {
        IEntityQuery entityQuery = DimensionEnvironment.getEntityDataService().newEntityQuery();
        EntityViewDefine entityViewDefine = this.getProviderUtil().getEntityViewDefine(context, dwDimensionValue, this.dimensionProviderData);
        entityQuery.setEntityView(entityViewDefine);
        FixedDimensionValue periodDimension = dimensionCombination.getPeriodDimensionValue();
        if (periodDimension == null) {
            entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
        } else {
            try {
                IPeriodProvider periodProvider = DimensionEnvironment.getPeriodEntityAdapter().getPeriodProvider(periodDimension.getEntityID());
                entityQuery.setQueryVersionDate(periodProvider.getPeriodDateRegion((String)periodDimension.getValue())[1]);
            }
            catch (Exception e) {
                entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
                this.log.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25" + e.getMessage());
            }
        }
        entityQuery.setAuthorityOperations(AuthorityType.None);
        return entityQuery;
    }

    @Override
    public boolean isDynamicByDW(VariableDimensionValue variableDimensionValue) {
        if (!StringUtils.hasText(this.dimensionProviderData.getDataSchemeKey())) {
            return false;
        }
        return DimensionEnvironment.getEntityDataQueryAssist().buildReferRelation(this.dimensionProviderData.getDataSchemeKey(), variableDimensionValue.getEntityID()) != null;
    }

    @Override
    protected DimensionProviderUtil getProviderUtil() {
        if (this.providerUtil == null) {
            this.providerUtil = (DimensionProviderUtil)SpringBeanUtils.getBean(DimensionProviderUtil.class);
        }
        return this.providerUtil;
    }

    @Override
    protected String getCacheKey(VariableDimensionValue variableDimensionValue, String dw, String period) {
        return variableDimensionValue.getName() + dw + period;
    }

    @Override
    public String getID() {
        return PROVIDER_FILTERDIMBYDW;
    }

    @Override
    public Object getMergeValue() {
        if (CollectionUtils.isEmpty(this.dimensionProviderData.getChoosedValues())) {
            return this.getID();
        }
        return this.dimensionProviderData.getChoosedValues();
    }

    @Override
    public DimensionProviderData getDimensionProviderData() {
        return this.dimensionProviderData;
    }

    @Override
    public String toString() {
        return "FilterDimByDw{dimensionProviderData=" + this.dimensionProviderData + '}';
    }
}

