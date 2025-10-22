/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 */
package com.jiuqi.nr.bpm.IO;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BusinessKeyObjectMapper
implements IDimensionObjectMapping {
    private int rowCount;
    private final Map<DimensionCombination, Set<String>> keyObjectMapper;

    public BusinessKeyObjectMapper(Map<DimensionCombination, Set<String>> keyObjectMapper) {
        this.keyObjectMapper = keyObjectMapper;
    }

    public BusinessKeyObjectMapper(Map<DimensionCombination, Set<String>> keyObjectMapper, int rowCount) {
        this(keyObjectMapper);
        this.rowCount = rowCount;
    }

    public Collection<String> getObject(DimensionCombination dimension) {
        return this.keyObjectMapper.get(dimension);
    }

    public void putObject(DimensionCombination dimension, String formOrGroupKey) {
        Set formOrGroupKeys = this.keyObjectMapper.computeIfAbsent(dimension, k -> new HashSet());
        formOrGroupKeys.add(formOrGroupKey);
    }

    public int getRowCount() {
        return this.rowCount;
    }
}

