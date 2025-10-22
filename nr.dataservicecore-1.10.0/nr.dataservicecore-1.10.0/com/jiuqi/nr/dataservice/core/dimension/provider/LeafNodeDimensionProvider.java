/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.dataservice.core.dimension.provider;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.provider.AllNodeDimensionProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeafNodeDimensionProvider
extends AllNodeDimensionProvider {
    private static final long serialVersionUID = 3601628207154061505L;
    public static final String PROVIDER_LEAFNODE = "PROVIDER_LEAFNODE";
    private final Logger log = LoggerFactory.getLogger(LeafNodeDimensionProvider.class);

    public LeafNodeDimensionProvider() {
    }

    public LeafNodeDimensionProvider(DimensionProviderData dimensionProviderData) {
        super(dimensionProviderData);
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
            entityQuery.markLeaf();
            IEntityTable entityTable = entityQuery.executeFullBuild((IContext)this.getProviderUtil().getExecutorContext());
            List<String> values = entityTable.getAllRows().parallelStream().filter(IEntityRow::isLeaf).map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
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

    @Override
    public DimensionProviderData getDimensionProviderData() {
        return this.dimensionProviderData;
    }

    @Override
    public String getID() {
        return PROVIDER_LEAFNODE;
    }

    @Override
    public String toString() {
        return "LeafNodeDimensionProvider{dimensionProviderData=" + this.dimensionProviderData + '}';
    }
}

