/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.access.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class DimensionValueSetUtil {
    public static Map<String, DimensionValue> getDimensionSet(DimensionValueSet dimensionValueSet) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            DimensionValue value = new DimensionValue();
            value.setName(dimensionValueSet.getName(i));
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (dimensionValue == null) continue;
            if (dimensionValue instanceof List) {
                value.setValue(StringUtils.join(((List)dimensionValue).iterator(), (String)";"));
            } else {
                value.setValue(dimensionValue.toString());
            }
            dimensionSet.put(value.getName(), value);
        }
        return dimensionSet;
    }

    public static Collection<DimensionValueSet> mergeDimensionValue(Collection<DimensionValueSet> dimensionValueSet, String dimensionName) {
        if (CollectionUtils.isEmpty(dimensionValueSet)) {
            return Collections.emptyList();
        }
        ArrayList<DimensionValueSet> merges = new ArrayList<DimensionValueSet>();
        HashMap<String, List> dimensionMap = new HashMap<String, List>();
        for (DimensionValueSet dimensionValueSet2 : dimensionValueSet) {
            DimensionValueSet copy = new DimensionValueSet(dimensionValueSet2);
            copy.clearValue(dimensionName);
            dimensionMap.computeIfAbsent(copy.toString(), r -> new ArrayList()).add(dimensionValueSet2);
        }
        for (Map.Entry entry : dimensionMap.entrySet()) {
            List valueSets = (List)entry.getValue();
            DimensionValueSet merged = DimensionValueSetUtil.mergeDimensionValueSet(valueSets);
            merges.add(merged);
        }
        return merges;
    }

    public static DimensionValueSet mergeDimensionValueSet(List<DimensionValueSet> dimensions) {
        DimensionValueSet dimensionSet = new DimensionValueSet();
        HashMap<String, Set> name2Values = new HashMap<String, Set>();
        for (DimensionValueSet dimensionValueSet : dimensions) {
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
            Set value = (Set)entry.getValue();
            if (value.size() == 1) {
                dimensionSet.setValue((String)entry.getKey(), value.iterator().next());
                continue;
            }
            dimensionSet.setValue((String)entry.getKey(), new ArrayList(value));
        }
        return dimensionSet;
    }

    public static Map<String, DimensionValue> mergeDimensionSetMap(List<Map<String, DimensionValue>> dimensions) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        HashMap<String, Set> valueMap = new HashMap<String, Set>();
        for (Map<String, DimensionValue> dimension : dimensions) {
            Set<String> keys = dimension.keySet();
            for (String key2 : keys) {
                String dimensionValue = dimension.get(key2).getValue();
                HashSet<String> valueSet = (HashSet<String>)valueMap.get(key2);
                if (valueSet == null) {
                    valueSet = new HashSet<String>();
                    valueMap.put(key2, valueSet);
                }
                if (dimensionValue == null) continue;
                if (dimensionValue instanceof List) {
                    valueSet.addAll((List)((Object)dimensionValue));
                    continue;
                }
                valueSet.add(dimensionValue);
            }
        }
        valueMap.forEach((key, val) -> {
            DimensionValue dimensionObj = (DimensionValue)dimensionSet.get(key);
            if (Objects.isNull(dimensionObj)) {
                dimensionObj = new DimensionValue();
                dimensionObj.setName(key);
            }
            dimensionObj.setValue(StringUtils.join(val.iterator(), (String)";"));
            dimensionSet.put((String)key, dimensionObj);
        });
        return dimensionSet;
    }

    public static DimensionValueSet getDimensionValueSet(Map<String, DimensionValue> dimensionSet) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            if (value.getValue() == null) continue;
            String[] values = value.getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        return dimensionValueSet;
    }

    public static DimensionValueSet filterDimensionValueSet(Map<String, DimensionValue> dimensionSet, String filterDim) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            if (value.getValue() == null || value.getName().equals(filterDim)) continue;
            String[] values = value.getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        return dimensionValueSet;
    }

    public static DimensionValueSet filterDimensionValueSet(DimensionValueSet dimensionValueSet, String filterDim) {
        DimensionValueSet newDimension = new DimensionValueSet();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            Object dimensionValue = dimensionValueSet.getValue(i);
            String name = dimensionValueSet.getName(i);
            if (dimensionValue == null || filterDim.equals(name)) continue;
            newDimension.setValue(name, dimensionValue);
        }
        return newDimension;
    }

    public static DimensionValueSet clearEmptyDimensionValue(DimensionValueSet dimensionValueSet) {
        ENameSet dimNameSet = dimensionValueSet.getDimensionSet().getDimensions();
        int size = dimNameSet.size();
        for (int index = 0; index < size; ++index) {
            String name = dimNameSet.get(index);
            Object value = dimensionValueSet.getValue(name);
            if (!ObjectUtils.isEmpty(value)) continue;
            dimensionValueSet.clearValue(name);
        }
        return dimensionValueSet;
    }

    public static List<Map<String, DimensionValue>> getDimensionSetList(Map<String, DimensionValue> dimensionSet) {
        ArrayList<Map<String, DimensionValue>> dimensionSetList = new ArrayList<Map<String, DimensionValue>>();
        List<DimensionValueSet> dimensionValueSetList = DimensionValueSetUtil.getDimensionValueSetList(dimensionSet);
        for (DimensionValueSet dimensionValueSet : dimensionValueSetList) {
            dimensionSetList.add(DimensionValueSetUtil.getDimensionSet(dimensionValueSet));
        }
        return dimensionSetList;
    }

    public static List<DimensionValueSet> getDimensionSetList(DimensionValueSet dimensionValueSet) {
        Map<String, DimensionValue> dimensionSet = DimensionValueSetUtil.getDimensionSet(dimensionValueSet);
        return DimensionValueSetUtil.getDimensionValueSetList(dimensionSet);
    }

    public static List<DimensionValueSet> getDimensionValueSetList(Map<String, DimensionValue> dimensionSet) {
        List<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>();
        if (dimensionSet == null) {
            return dimensionValueSetList;
        }
        ArrayList<String> dimensionNameList = new ArrayList<String>();
        ArrayList<List<Object>> dimensionValueList = new ArrayList<List<Object>>();
        for (DimensionValue value : dimensionSet.values()) {
            String[] values = value.getValue().split(";");
            List<String> valueList = Arrays.asList(values);
            dimensionValueList.add(valueList);
            dimensionNameList.add(value.getName());
        }
        if (dimensionNameList.size() > 0 && dimensionValueList.size() > 0 && dimensionNameList.size() == dimensionValueList.size()) {
            dimensionValueSetList = DimensionValueSetUtil.getDimensionValueSetList(dimensionNameList, dimensionValueList, 0);
        }
        return dimensionValueSetList;
    }

    private static List<DimensionValueSet> getDimensionValueSetList(List<String> dimensionNameList, List<List<Object>> dimensionValueList, int layer) {
        ArrayList<DimensionValueSet> dimensionValueSetList;
        block4: {
            block3: {
                dimensionValueSetList = new ArrayList<DimensionValueSet>();
                if (layer >= dimensionNameList.size() - 1) break block3;
                String dimensionName = dimensionNameList.get(layer);
                List<Object> valueList = dimensionValueList.get(layer);
                List<DimensionValueSet> subDimensionValueSetList = DimensionValueSetUtil.getDimensionValueSetList(dimensionNameList, dimensionValueList, layer + 1);
                for (Object dimensionValue : valueList) {
                    for (DimensionValueSet subDimensionValueSet : subDimensionValueSetList) {
                        DimensionValueSet dimensionValueSet = new DimensionValueSet(subDimensionValueSet);
                        dimensionValueSet.setValue(dimensionName, dimensionValue);
                        dimensionValueSetList.add(dimensionValueSet);
                    }
                }
                break block4;
            }
            if (layer != dimensionNameList.size() - 1) break block4;
            String dimensionName = dimensionNameList.get(layer);
            List<Object> valueList = dimensionValueList.get(layer);
            for (Object dimensionValue : valueList) {
                DimensionValueSet dimensionValueSet = new DimensionValueSet();
                dimensionValueSet.setValue(dimensionName, dimensionValue);
                dimensionValueSetList.add(dimensionValueSet);
            }
        }
        return dimensionValueSetList;
    }

    public static DimensionCombination buildDimensionCombination(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        DimCollectionBuildUtil dimCollectionBuildUtil = (DimCollectionBuildUtil)BeanUtil.getBean(DimCollectionBuildUtil.class);
        return dimCollectionBuildUtil.buildDimensionCombination(dimensionValueSet, formSchemeKey);
    }

    public static DimensionCollection buildDimensionCollection(DimensionValueSet dimensionValueSet, String formSchemeKey) {
        DimCollectionBuildUtil dimCollectionBuildUtil = (DimCollectionBuildUtil)BeanUtil.getBean(DimCollectionBuildUtil.class);
        return dimCollectionBuildUtil.buildDimensionCollection(dimensionValueSet, formSchemeKey);
    }

    public static DimensionCollection buildDimensionCollection(Map<String, DimensionValue> dimensionValueMap, String formSchemeKey) {
        DimCollectionBuildUtil dimCollectionBuildUtil = (DimCollectionBuildUtil)BeanUtil.getBean(DimCollectionBuildUtil.class);
        return dimCollectionBuildUtil.buildDimensionCollection(dimensionValueMap, formSchemeKey);
    }

    public static DimensionValueSet mergeDimensionValueSet(DimensionCollection collection) {
        List dimensionCombinations = collection.getDimensionCombinations();
        ArrayList<DimensionValueSet> setList = new ArrayList<DimensionValueSet>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            setList.add(dimensionValueSet);
        }
        return DimensionValueSetUtil.mergeDimensionValueSet(setList);
    }

    public static List<DimensionValueSet> toDimensionValueSetList(DimensionCollection collection) {
        List dimensionCombinations = collection.getDimensionCombinations();
        ArrayList<DimensionValueSet> setList = new ArrayList<DimensionValueSet>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            DimensionValueSet dimensionValueSet = dimensionCombination.toDimensionValueSet();
            setList.add(dimensionValueSet);
        }
        return setList;
    }
}

