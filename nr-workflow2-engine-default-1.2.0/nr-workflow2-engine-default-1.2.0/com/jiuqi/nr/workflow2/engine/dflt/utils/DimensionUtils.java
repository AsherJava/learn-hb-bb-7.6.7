/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 */
package com.jiuqi.nr.workflow2.engine.dflt.utils;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class DimensionUtils {
    public static DimensionCollection combineDimensions(Collection<DimensionCombination> dimensions) {
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        if (!dimensions.isEmpty()) {
            HashMap<String, String> entityIds = new HashMap<String, String>();
            Iterator<DimensionCombination> iterator = dimensions.iterator();
            if (iterator.hasNext()) {
                DimensionCombination dimension = iterator.next();
                for (FixedDimensionValue dimvalue : dimension.getDimensionValues()) {
                    entityIds.put(dimvalue.getName(), dimvalue.getEntityID());
                }
            }
            HashMap<String, HashSet<Object>> entityValues = new HashMap<String, HashSet<Object>>();
            for (DimensionCombination dimensionCombination : dimensions) {
                for (FixedDimensionValue dimvalue : dimensionCombination.getDimensionValues()) {
                    HashSet<Object> values = (HashSet<Object>)entityValues.get(dimvalue.getName());
                    if (values == null) {
                        values = new HashSet<Object>();
                        entityValues.put(dimvalue.getName(), values);
                    }
                    values.add(dimvalue.getValue());
                }
            }
            for (Map.Entry entry : entityValues.entrySet()) {
                builder.setEntityValue((String)entry.getKey(), (String)entityIds.get(entry.getKey()), new Object[]{new ArrayList((Collection)entry.getValue())});
            }
        }
        return builder.getCollection();
    }
}

