/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataservice.core.dimension;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionTable;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class DimensionCollectionSoftImpl
implements DimensionCollection {
    private final transient DimensionTable dimensionTable = new DimensionTable();
    private final List<DimensionCombination> combinations = new ArrayList<DimensionCombination>();

    protected DimensionCollectionSoftImpl(List<DimensionCombination> dimensionCombinations) {
        if (dimensionCombinations != null && dimensionCombinations.size() > 0) {
            for (DimensionCombination combination : dimensionCombinations) {
                DimensionCombinationBuilder dimensionCombinationBuilder = new DimensionCombinationBuilder(this.dimensionTable);
                FixedDimensionValue dwDimensionValue = combination.getDWDimensionValue();
                dimensionCombinationBuilder.setDWValue(dwDimensionValue);
                Collection<String> names = combination.getNames();
                for (String name : names) {
                    if (name.equalsIgnoreCase(dwDimensionValue.getName())) continue;
                    dimensionCombinationBuilder.setValue(combination.getFixedDimensionValue(name));
                }
                this.combinations.add(dimensionCombinationBuilder.getCombination());
            }
        }
    }

    @Override
    public List<DimensionCombination> getDimensionCombinations() {
        return new ArrayList<DimensionCombination>(this.combinations);
    }

    @Override
    public Iterator<DimensionValueSet> iterator() {
        return new Iterator<DimensionValueSet>(){
            int nextIndex = 0;
            DimensionValueSet current = null;
            List<DimensionCombination> nodes = DimensionCollectionSoftImpl.this.getDimensionCombinations();

            @Override
            public boolean hasNext() {
                return this.nextIndex < this.nodes.size();
            }

            @Override
            public DimensionValueSet next() {
                DimensionValueSet result = this.current;
                this.current = this.nextIndex == this.nodes.size() ? null : this.nodes.get(this.nextIndex++).toDimensionValueSet();
                return result;
            }
        };
    }

    @Override
    public DimensionValueSet combineWithoutVarDim() {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (CollectionUtils.isEmpty(this.combinations)) {
            return dimensionValueSet;
        }
        HashMap valueMap = new HashMap();
        Collection<String> names = this.combinations.get(0).getNames();
        this.combinations.stream().forEach(d -> names.forEach(name -> {
            Set objects = valueMap.computeIfAbsent(name, k -> new HashSet());
            objects.add(d.getValue((String)name));
        }));
        valueMap.entrySet().stream().forEach(v -> {
            ArrayList value = new ArrayList((Collection)v.getValue());
            if (value.size() == 1) {
                dimensionValueSet.setValue((String)v.getKey(), value.get(0));
            } else {
                dimensionValueSet.setValue((String)v.getKey(), value);
            }
        });
        return dimensionValueSet;
    }

    @Override
    public DimensionValueSet combineDim() {
        return this.combineWithoutVarDim();
    }
}

