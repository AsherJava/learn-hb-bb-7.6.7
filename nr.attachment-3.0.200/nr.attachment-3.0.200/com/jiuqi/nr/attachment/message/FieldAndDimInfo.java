/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 */
package com.jiuqi.nr.attachment.message;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FieldAndDimInfo {
    private Map<String, Set<String>> dimNameAndDimVals = new HashMap<String, Set<String>>();
    private Map<String, List<DimensionCombinationBuilder>> fieldDimMap = new HashMap<String, List<DimensionCombinationBuilder>>();

    public Map<String, Set<String>> getDimNameAndDimVals() {
        return this.dimNameAndDimVals;
    }

    public Map<String, List<DimensionCombinationBuilder>> getFieldDimMap() {
        return this.fieldDimMap;
    }
}

