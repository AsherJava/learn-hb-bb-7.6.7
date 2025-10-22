/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 */
package com.jiuqi.nr.data.logic.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.logic.common.ExceptionEnum;
import com.jiuqi.nr.data.logic.exeception.LogicCheckedException;
import com.jiuqi.nr.data.logic.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

public class DimensionUtil {
    public static DimensionValueSet transFormDimensionValue(DimensionValueSet dimensionValueSet) {
        DimensionValueSet result = new DimensionValueSet();
        if (dimensionValueSet != null) {
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String name = dimensionValueSet.getName(i);
                Object value = dimensionValueSet.getValue(i);
                if (value instanceof Object[]) {
                    Object[] objects;
                    ArrayList<String> newValue = new ArrayList<String>();
                    for (Object object : objects = (Object[])value) {
                        newValue.add((String)object);
                    }
                    result.setValue(name, newValue);
                    continue;
                }
                result.setValue(name, value);
            }
        }
        return result;
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
        if (!dimensionNameList.isEmpty() && dimensionNameList.size() == dimensionValueList.size()) {
            dimensionValueSetList = DimensionUtil.getDimensionValueSetList(dimensionNameList, dimensionValueList, 0);
        }
        return dimensionValueSetList;
    }

    public static List<DimensionValueSet> getDimensionValueSetList(DimensionValueSet dimensionValueSet) {
        List<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>();
        if (dimensionValueSet == null) {
            return dimensionValueSetList;
        }
        ArrayList<String> dimensionNameList = new ArrayList<String>();
        ArrayList<List<Object>> dimensionValueList = new ArrayList<List<Object>>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            ArrayList<Object> values = new ArrayList<Object>();
            Object value = dimensionValueSet.getValue(i);
            if (value instanceof String) {
                values.add(value);
            } else if (value instanceof List) {
                values.addAll((List)value);
            }
            dimensionValueList.add(values);
            dimensionNameList.add(dimensionValueSet.getName(i));
        }
        if (!dimensionNameList.isEmpty() && dimensionNameList.size() == dimensionValueList.size()) {
            dimensionValueSetList = DimensionUtil.getDimensionValueSetList(dimensionNameList, dimensionValueList, 0);
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
                List<DimensionValueSet> subDimensionValueSetList = DimensionUtil.getDimensionValueSetList(dimensionNameList, dimensionValueList, layer + 1);
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

    public static List<DimensionValueSet> getDimensionValueSetList(Map<String, DimensionValue> dimensionValues, int batchSplitSize) {
        ArrayList<DimensionValueSet> dimensionValueSetList = new ArrayList<DimensionValueSet>();
        if (dimensionValues == null) {
            return dimensionValueSetList;
        }
        DimensionValueSet allDimValues = new DimensionValueSet();
        String splitDim = null;
        for (DimensionValue value : dimensionValues.values()) {
            String dimName = value.getName();
            String[] values = value.getValue().split(";");
            if (values.length == 1) {
                allDimValues.setValue(dimName, (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            allDimValues.setValue(dimName, valueList);
            if (valueList.size() <= batchSplitSize) continue;
            splitDim = dimName;
        }
        if (splitDim == null) {
            dimensionValueSetList.add(allDimValues);
        } else {
            List allValueList = (List)allDimValues.getValue(splitDim);
            int length = allValueList.size();
            int num = (length + batchSplitSize - 1) / batchSplitSize;
            for (int i = 0; i < num; ++i) {
                int fromIndex = i * batchSplitSize;
                int toIndex = Math.min((i + 1) * batchSplitSize, length);
                DimensionValueSet dimensionValueSet = new DimensionValueSet(allDimValues);
                dimensionValueSet.setValue(splitDim, allValueList.subList(fromIndex, toIndex));
                dimensionValueSetList.add(dimensionValueSet);
            }
        }
        return dimensionValueSetList;
    }

    public static DimensionValueSet getDimensionValueSet(Map<String, DimensionValue> dimensionSet) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            if (value.getValue() == null) continue;
            String[] values = value.getValue().split(";");
            if (values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            if (values.length == 1) {
                dimensionValueSet.setValue(value.getName(), (Object)values[0]);
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        return dimensionValueSet;
    }

    public static Map<String, DimensionValue> getDimensionSet(DimensionValueSet dimensionValueSet) {
        HashMap<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            DimensionValue value = new DimensionValue();
            value.setName(dimensionValueSet.getName(i));
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (dimensionValue == null) {
                value.setValue("");
            } else if (dimensionValue instanceof List) {
                value.setValue(StringUtils.join(((List)dimensionValue).iterator(), (String)";"));
            } else {
                value.setValue(dimensionValue.toString());
            }
            dimensionSet.put(value.getName(), value);
        }
        return dimensionSet;
    }

    public static List<DimensionValueSet> fillLostDim(DimensionValueSet dimensionValueSet, List<DimensionValueSet> lostDim) {
        ArrayList<DimensionValueSet> result = new ArrayList<DimensionValueSet>();
        for (DimensionValueSet valueSet : lostDim) {
            DimensionValueSet newDimension = new DimensionValueSet(dimensionValueSet);
            boolean change = false;
            for (int i = 0; i < valueSet.size(); ++i) {
                if (newDimension.hasValue(valueSet.getName(i))) continue;
                newDimension.setValue(valueSet.getName(i), valueSet.getValue(i));
                change = true;
            }
            if (!change) continue;
            result.add(newDimension);
        }
        if (result.isEmpty()) {
            result.add(new DimensionValueSet(dimensionValueSet));
        }
        return result;
    }

    public static Map<String, String> getDimensionTitle(DimensionValueSet dimensionValueSet, Map<String, IDimDataLoader> entityLoaderMap) {
        HashMap<String, String> result = new HashMap<String, String>();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String dimensionName = dimensionValueSet.getName(i);
            String dimensionValue = (String)dimensionValueSet.getValue(i);
            if (!entityLoaderMap.containsKey(dimensionName)) continue;
            IDimDataLoader dimDataLoader = entityLoaderMap.get(dimensionName);
            String title = dimDataLoader.getTitleByEntityDataKey(dimensionValue);
            result.put(dimensionName, title);
        }
        return result;
    }

    public static DimensionValueSet getDimFromCKREntity(Map<String, String> dimMap, Map<String, String> colDimNameMap) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry<String, String> entry : dimMap.entrySet()) {
            String colName = entry.getKey();
            String dimensionName = colDimNameMap.get(colName);
            dimensionValueSet.setValue(dimensionName == null ? colName : dimensionName, (Object)entry.getValue());
        }
        return dimensionValueSet;
    }

    public static List<DimensionValueSet> mergeDimensionByDw(DimensionCollection dimensionCollection, String dimensionName) {
        List allDimension = dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allDimension)) {
            return Collections.emptyList();
        }
        HashMap dimMap = new HashMap();
        for (DimensionValueSet dimensionValueSet : allDimension) {
            String dimGroupKey = DimensionUtil.getDimGroupKey(dimensionValueSet, dimensionName);
            if (dimMap.containsKey(dimGroupKey)) {
                ((List)dimMap.get(dimGroupKey)).add(dimensionValueSet);
                continue;
            }
            ArrayList<DimensionValueSet> list = new ArrayList<DimensionValueSet>();
            list.add(dimensionValueSet);
            dimMap.put(dimGroupKey, list);
        }
        ArrayList<DimensionValueSet> mergedDim = new ArrayList<DimensionValueSet>(dimMap.size());
        for (List dimList : dimMap.values()) {
            mergedDim.add(DimensionUtil.merge(dimList));
        }
        return mergedDim;
    }

    private static String getDimGroupKey(@NonNull DimensionValueSet dimensionValueSet, @NonNull String dimensionName) {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String name = dimensionValueSet.getName(i);
            Object value = dimensionValueSet.getValue(i);
            if (dimensionName.equals(name)) continue;
            key.append(value).append(";");
        }
        return key.toString();
    }

    public static List<DimensionValueSet> mergeDimensionWithDep(DimensionCollection dimensionCollection, List<String> depDimNames) throws LogicCheckedException {
        List<DimensionValueSet> allDimension = dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allDimension)) {
            throw new LogicCheckedException(ExceptionEnum.DIM_EXPAND_EXC.getCode());
        }
        if (allDimension.size() == 1) {
            return allDimension;
        }
        ArrayList<DimensionValueSet> result = new ArrayList<DimensionValueSet>();
        if (CollectionUtils.isEmpty(depDimNames)) {
            result.add(DimensionUtil.merge(allDimension));
        } else {
            Map<String, List<DimensionValueSet>> m = DimensionUtil.groupByDims(depDimNames, allDimension);
            m.values().forEach(o -> result.add(DimensionUtil.merge(o)));
        }
        return result;
    }

    @Nullable
    public static DimensionValueSet getMergeDimensionValueSet(DimensionCollection dimensionCollection) {
        List<DimensionValueSet> allDimension = dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allDimension)) {
            return null;
        }
        if (allDimension.size() == 1) {
            return allDimension.get(0);
        }
        return DimensionUtil.merge(allDimension);
    }

    public static Map<String, DimensionValue> getDimensionValues(DimensionCollection dimensionCollection) {
        List<DimensionValueSet> allDimension = dimensionCollection.getDimensionCombinations().stream().map(DimensionCombination::toDimensionValueSet).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(allDimension)) {
            return Collections.emptyMap();
        }
        return DimensionUtil.merge2(allDimension);
    }

    public static List<String> getDimKeySet(DimensionCollection dimensionCollection, String dimensionName) {
        HashSet dimKeySet = new HashSet();
        dimensionCollection.getDimensionCombinations().forEach(o -> {
            Object value = o.getValue(dimensionName);
            if (value instanceof String) {
                dimKeySet.add((String)value);
            } else if (value instanceof List) {
                dimKeySet.addAll((List)value);
            }
        });
        return new ArrayList<String>(dimKeySet);
    }

    public static DimensionValueSet merge(List<DimensionValueSet> allDimension) {
        LinkedHashMap dimensionMap = new LinkedHashMap();
        for (DimensionValueSet dimensionValueSet : allDimension) {
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String name = dimensionValueSet.getName(i);
                Object value = dimensionValueSet.getValue(i);
                if (dimensionMap.containsKey(name)) {
                    ((Set)dimensionMap.get(name)).add(value);
                    continue;
                }
                HashSet<Object> valueSet = new HashSet<Object>();
                valueSet.add(value);
                dimensionMap.put(name, valueSet);
            }
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry entry : dimensionMap.entrySet()) {
            String dimensionName = (String)entry.getKey();
            Set valueSet = (Set)entry.getValue();
            if (valueSet.size() == 1) {
                for (Object o : valueSet) {
                    dimensionValueSet.setValue(dimensionName, o);
                }
                continue;
            }
            dimensionValueSet.setValue(dimensionName, new ArrayList(valueSet));
        }
        return dimensionValueSet;
    }

    public static Map<String, DimensionValue> merge2(List<DimensionValueSet> allDimension) {
        LinkedHashMap dimensionMap = new LinkedHashMap();
        for (DimensionValueSet dimensionValueSet : allDimension) {
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                String name = dimensionValueSet.getName(i);
                Object value = dimensionValueSet.getValue(i);
                if (dimensionMap.containsKey(name)) {
                    ((Set)dimensionMap.get(name)).add(value.toString());
                    continue;
                }
                HashSet<String> valueSet = new HashSet<String>();
                valueSet.add(value.toString());
                dimensionMap.put(name, valueSet);
            }
        }
        HashMap<String, DimensionValue> result = new HashMap<String, DimensionValue>();
        for (Map.Entry entry : dimensionMap.entrySet()) {
            String dimensionName = (String)entry.getKey();
            Set valueSet = (Set)entry.getValue();
            DimensionValue dimensionValue = new DimensionValue();
            dimensionValue.setName(dimensionName);
            if (valueSet.size() == 1) {
                for (String o : valueSet) {
                    dimensionValue.setValue(o);
                }
            } else {
                dimensionValue.setValue(String.join((CharSequence)";", valueSet));
            }
            result.put(dimensionName, dimensionValue);
        }
        return result;
    }

    private static Map<String, List<DimensionValueSet>> groupByDims(List<String> depDimNames, List<DimensionValueSet> allDimension) {
        HashMap<String, List<DimensionValueSet>> m = new HashMap<String, List<DimensionValueSet>>();
        for (DimensionValueSet dimensionValueSet : allDimension) {
            StringBuilder s = new StringBuilder();
            for (String depDimName : depDimNames) {
                s.append((String)dimensionValueSet.getValue(depDimName)).append(";");
            }
            String str = s.toString();
            if (m.containsKey(str)) {
                ((List)m.get(str)).add(dimensionValueSet);
                continue;
            }
            ArrayList<DimensionValueSet> l = new ArrayList<DimensionValueSet>();
            l.add(dimensionValueSet);
            m.put(str, l);
        }
        return m;
    }

    public static boolean dimSingle(DimensionValueSet dimensionValueSet) {
        if (dimensionValueSet != null) {
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                List stringList;
                Object value = dimensionValueSet.getValue(i);
                if (!(value instanceof List) || (stringList = (List)value).size() <= 1) continue;
                return false;
            }
        }
        return true;
    }

    public static DimensionCollection combination2Collection(DimensionCombination dimensionCombination) {
        if (dimensionCombination == null) {
            return null;
        }
        DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
        FixedDimensionValue dwDimensionValue = dimensionCombination.getDWDimensionValue();
        String dwDimName = null;
        if (dwDimensionValue != null) {
            dwDimName = dwDimensionValue.getName();
            dimensionCollectionBuilder.setDWValue(dwDimName, dwDimensionValue.getEntityID(), new Object[]{dwDimensionValue.getValue()});
        }
        for (FixedDimensionValue fixedDimensionValue : dimensionCombination) {
            if (dwDimName != null && dwDimName.equals(fixedDimensionValue.getName())) continue;
            dimensionCollectionBuilder.setEntityValue(fixedDimensionValue.getName(), fixedDimensionValue.getEntityID(), new Object[]{fixedDimensionValue.getValue()});
        }
        return dimensionCollectionBuilder.getCollection();
    }
}

