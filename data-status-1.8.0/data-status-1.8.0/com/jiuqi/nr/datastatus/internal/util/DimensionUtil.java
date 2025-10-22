/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.datastatus.internal.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.datastatus.internal.util.entity.IDimDataLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (dimensionNameList.size() > 0 && dimensionNameList.size() == dimensionValueList.size()) {
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
            if (values.length == 1 || values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
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
}

