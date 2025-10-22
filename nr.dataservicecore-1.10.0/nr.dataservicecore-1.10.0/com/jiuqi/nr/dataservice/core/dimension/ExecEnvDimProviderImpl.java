/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.ExecEnvDimProvider
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DynamicDimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.CacheAbleProvider;
import com.jiuqi.nr.definition.facade.ExecEnvDimProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

public class ExecEnvDimProviderImpl
implements ExecEnvDimProvider {
    private final DimensionCollection collection;

    public ExecEnvDimProviderImpl(DimensionCollection collection) {
        this.collection = collection;
    }

    public Map<String, List<String>> getRelationDimValues(String dataSchemeKey, String dw, String period, List<String> dimNames) {
        if (this.collection instanceof DynamicDimensionCollection) {
            DynamicDimensionCollection collection = (DynamicDimensionCollection)this.collection;
            return collection.getVariableDimensionList().stream().filter(v -> dimNames.contains(v.getName())).filter(v -> v.getProvider() instanceof CacheAbleProvider).collect(Collectors.toMap(VariableDimensionValue::getName, v -> {
                VariableDimensionValueProvider provider = v.getProvider();
                CacheAbleProvider cacheAbleProvider = (CacheAbleProvider)((Object)provider);
                return cacheAbleProvider.getValues((VariableDimensionValue)v, dw, period);
            }));
        }
        List<DimensionCombination> dimensionCombinations = this.collection.getDimensionCombinations();
        if (CollectionUtils.isEmpty(dimensionCombinations)) {
            return new HashMap<String, List<String>>();
        }
        DimensionCombination combination1 = dimensionCombinations.get(0);
        FixedDimensionValue dwDimensionValue = combination1.getDWDimensionValue();
        FixedDimensionValue periodDimensionValue = combination1.getPeriodDimensionValue();
        if (dwDimensionValue == null || periodDimensionValue == null) {
            return new HashMap<String, List<String>>();
        }
        HashMap resultSet = new HashMap();
        dimensionCombinations.stream().filter(combination -> combination.getValue(dwDimensionValue.getName()).equals(dw) && combination.getValue(periodDimensionValue.getName()).equals(period)).forEach(combination -> {
            for (String dimName : dimNames) {
                Set set = resultSet.computeIfAbsent(dimName, k -> new HashSet());
                set.add(combination.getValue(dimName).toString());
            }
        });
        return resultSet.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> new ArrayList((Collection)e.getValue())));
    }
}

