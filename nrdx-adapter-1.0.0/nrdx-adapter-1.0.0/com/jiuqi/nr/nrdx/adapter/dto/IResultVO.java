/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.io.tsd.dto.IResult
 */
package com.jiuqi.nr.nrdx.adapter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.io.tsd.dto.IResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown=true)
public class IResultVO {
    private final Map<String, DimensionValue> dimensionValueMap = new HashMap<String, DimensionValue>();
    private final List<String> forms = new ArrayList<String>();

    public IResultVO() {
    }

    public IResultVO(IResult importResult) {
        this.forms.addAll(importResult.getForms());
        this.unfoldDimensionCollection(importResult.getDimensionCollection());
    }

    public Map<String, DimensionValue> getDimensionValueMap() {
        return this.dimensionValueMap;
    }

    public List<String> getForms() {
        return this.forms;
    }

    private void unfoldDimensionCollection(DimensionCollection dimensionCollection) {
        if (dimensionCollection == null) {
            return;
        }
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        List setList = dimensionCombinations.stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        HashMap<String, Set> name2Values = new HashMap<String, Set>();
        for (DimensionValueSet dimensionValueSet : setList) {
            int size = dimensionValueSet.size();
            for (int i = 0; i < size; ++i) {
                String key = dimensionValueSet.getName(i);
                Set values = name2Values.computeIfAbsent(key, k -> new HashSet());
                Object value = dimensionValueSet.getValue(i);
                if (value instanceof List) {
                    values.addAll((List)value);
                    continue;
                }
                values.add(value.toString());
            }
        }
        for (Map.Entry entry : name2Values.entrySet()) {
            String key = (String)entry.getKey();
            Set values = (Set)entry.getValue();
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setName(key);
            dimensionValue.setValue(StringUtils.join(values.iterator(), (String)";"));
            this.dimensionValueMap.put(key, dimensionValue);
        }
    }
}

