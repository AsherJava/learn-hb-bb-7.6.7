/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
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
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public class FilterDWByVersion
extends AllNodeDimensionProvider {
    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory.getLogger(FilterDWByVersion.class);
    public static final String PROVIDER_FILTERDWBYVERSION = "PROVIDER_FILTERDWBYVERSION";

    public FilterDWByVersion(DimensionProviderData unfoldData) {
        super(unfoldData);
    }

    @Override
    public List<String> getValues(DimensionContext context, VariableDimensionValue variableDimensionValue, DimensionCombination dimensionCombination) {
        if (CollectionUtils.isEmpty(this.dimensionProviderData.getChoosedValues())) {
            return super.getValues(context, variableDimensionValue, dimensionCombination);
        }
        FixedDimensionValue periodDimension = dimensionCombination.getPeriodDimensionValue();
        String periodStr = periodDimension != null ? (String)periodDimension.getValue() : null;
        String cacheKey = this.getCacheKey(variableDimensionValue, null, periodStr);
        if (this.hasKey(cacheKey)) {
            return (List)this.valueCache.get(cacheKey);
        }
        IEntityQuery entityQuery = this.getEntityQuery(context, variableDimensionValue, dimensionCombination);
        try {
            IEntityTable entityTable = entityQuery.executeReader((IContext)this.getProviderUtil().getExecutorContext());
            List<String> choosedValues = this.dimensionProviderData.getChoosedValues();
            if (CollectionUtils.isEmpty(choosedValues)) {
                ArrayList<String> values = new ArrayList<String>();
                this.valueCache.put(cacheKey, values);
                return values;
            }
            Map byEntityKeys = entityTable.findByEntityKeys(new HashSet<String>(choosedValues));
            List<String> values = choosedValues.stream().filter(d -> byEntityKeys.get(d) != null).collect(Collectors.toList());
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
    protected String getCacheKey(VariableDimensionValue variableDimensionValue, String dw, String period) {
        return variableDimensionValue.getName() + period;
    }

    @Override
    public String getID() {
        return PROVIDER_FILTERDWBYVERSION;
    }

    @Override
    public String toString() {
        return "FilterDWByVersion{ dimensionProviderData=" + this.dimensionProviderData + '}';
    }
}

