/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 */
package com.jiuqi.nr.dataservice.core.dimension.provider;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.DimensionEnvironment;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.CacheAbleProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderUtil;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class AllNodeDimensionProvider
extends CacheAbleProvider
implements VariableDimensionValueProvider {
    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory.getLogger(AllNodeDimensionProvider.class);
    protected DimensionProviderData dimensionProviderData;
    protected transient DimensionProviderUtil providerUtil;
    public static final String PROVIDER_ALLNODE = "PROVIDER_ALLNODE";

    public AllNodeDimensionProvider() {
        this.dimensionProviderData = new DimensionProviderData();
    }

    public AllNodeDimensionProvider(DimensionProviderData dimensionProviderData) {
        if (dimensionProviderData != null) {
            this.dimensionProviderData = dimensionProviderData;
        }
    }

    @Override
    public List<String> getValues(DimensionContext context, VariableDimensionValue variableDimensionValue, DimensionCombination dimensionCombination) {
        FixedDimensionValue periodDimension = dimensionCombination.getPeriodDimensionValue();
        String periodStr = periodDimension != null ? (String)periodDimension.getValue() : null;
        String cacheKey = this.getCacheKey(variableDimensionValue, null, periodStr);
        if (this.hasKey(cacheKey)) {
            return (List)this.valueCache.get(cacheKey);
        }
        IEntityQuery entityQuery = this.getEntityQuery(context, variableDimensionValue, dimensionCombination);
        try {
            IEntityTable entityTable = entityQuery.executeFullBuild((IContext)this.getProviderUtil().getExecutorContext());
            List<String> values = entityTable.getAllRows().stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
            values = this.filterValues(values);
            this.valueCache.put(cacheKey, values);
            return values;
        }
        catch (Exception e) {
            this.valueCache.put(cacheKey, Collections.emptyList());
            this.log.error("\u67e5\u8be2\u5b9e\u4f53\u6570\u636e\u51fa\u9519\u3002", e);
            return Collections.emptyList();
        }
    }

    protected IEntityQuery getEntityQuery(DimensionContext context, VariableDimensionValue variableDimensionValue, DimensionCombination dimensionCombination) {
        IEntityQuery entityQuery = DimensionEnvironment.getEntityDataService().newEntityQuery();
        EntityViewDefine entityViewDefine = this.getProviderUtil().getEntityViewDefine(context, variableDimensionValue, this.dimensionProviderData);
        entityQuery.setEntityView(entityViewDefine);
        FixedDimensionValue periodDimension = dimensionCombination.getFixedDimensionValue("DATATIME");
        if (periodDimension == null) {
            entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
        } else {
            IPeriodProvider periodProvider = DimensionEnvironment.getPeriodEntityAdapter().getPeriodProvider(periodDimension.getEntityID());
            try {
                entityQuery.setQueryVersionDate(periodProvider.getPeriodDateRegion((String)periodDimension.getValue())[1]);
            }
            catch (ParseException e) {
                entityQuery.setQueryVersionDate(Consts.DATE_VERSION_FOR_ALL);
                this.log.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25" + e.getMessage());
            }
        }
        entityQuery.setAuthorityOperations(this.dimensionProviderData.getAuthorityType());
        return entityQuery;
    }

    protected List<String> filterValues(List<String> values) {
        if (this.dimensionProviderData == null || CollectionUtils.isEmpty(values) || CollectionUtils.isEmpty(this.dimensionProviderData.getChoosedValues()) && CollectionUtils.isEmpty(this.dimensionProviderData.getExcludeValues())) {
            return values;
        }
        return values.stream().filter(v -> CollectionUtils.isEmpty(this.dimensionProviderData.getChoosedValues()) || this.dimensionProviderData.getChoosedValues().contains(v)).filter(v -> CollectionUtils.isEmpty(this.dimensionProviderData.getExcludeValues()) || !this.dimensionProviderData.getExcludeValues().contains(v)).collect(Collectors.toList());
    }

    protected DimensionProviderUtil getProviderUtil() {
        if (this.providerUtil == null) {
            this.providerUtil = (DimensionProviderUtil)SpringBeanUtils.getBean(DimensionProviderUtil.class);
        }
        return this.providerUtil;
    }

    @Override
    protected String getCacheKey(VariableDimensionValue variableDimensionValue, String dw, String period) {
        return variableDimensionValue.getName() + period;
    }

    @Override
    public String getID() {
        return PROVIDER_ALLNODE;
    }

    @Override
    public DimensionProviderData getDimensionProviderData() {
        return this.dimensionProviderData;
    }

    public String toString() {
        return "AllNodeDimensionProvider{dimensionProviderData=" + this.dimensionProviderData + '}';
    }
}

