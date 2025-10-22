/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DataEngineRunType
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$DebugLogType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.reminder.bean;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.reminder.bean.DimensionCacheKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ReminderBatchConditionMonitor
extends AbstractMonitor {
    private Map<DimensionCacheKey, Set<String>> conditionResultList = new HashMap<DimensionCacheKey, Set<String>>();
    private Map<String, DimensionValue> dimensionValueMap;

    public ReminderBatchConditionMonitor() {
        super(DataEngineConsts.DataEngineRunType.JUDGE);
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

    public void error(CheckExpression expression, QueryContext context) throws SyntaxException, DataTypeException {
        DimensionValueSet rowKey = new DimensionValueSet(context.getRowKey());
        if (!rowKey.hasValue("DATATIME")) {
            rowKey.setValue("DATATIME", context.getDimensionValue("DATATIME"));
        }
        Map<String, DimensionValue> dimensionSet = ReminderBatchConditionMonitor.getDimensionSet(rowKey);
        Iterator<Map.Entry<String, DimensionValue>> iterators = dimensionSet.entrySet().iterator();
        while (iterators.hasNext()) {
            Map.Entry<String, DimensionValue> entry = iterators.next();
            if (this.dimensionValueMap.containsKey(entry.getKey())) continue;
            iterators.remove();
        }
        ArrayList<DimensionValue> lacks = new ArrayList<DimensionValue>();
        Set<Map.Entry<String, DimensionValue>> entrySet = this.dimensionValueMap.entrySet();
        for (Map.Entry<String, DimensionValue> entry : entrySet) {
            DimensionValue dimensionValue = dimensionSet.get(entry.getKey());
            if (null != dimensionValue) continue;
            lacks.add(entry.getValue());
        }
        if (!lacks.isEmpty()) {
            ArrayList<String> dimensionNames = new ArrayList<String>();
            ArrayList<List<String>> dimensionValues = new ArrayList<List<String>>();
            for (DimensionValue dimensionValue : lacks) {
                String value = dimensionValue.getValue();
                String[] values = value.split(";");
                List<String> valueIDList = Arrays.asList(values);
                dimensionNames.add(dimensionValue.getName());
                dimensionValues.add(valueIDList);
            }
            List<Map<String, DimensionValue>> dimensionValueList = this.getDimensionValueList(dimensionNames, dimensionValues, 0);
            for (Map<String, DimensionValue> map : dimensionValueList) {
                map.putAll(dimensionSet);
                DimensionCacheKey cacheKey = new DimensionCacheKey(map);
                Set<String> formKeys = this.conditionResultList.get(cacheKey);
                if (formKeys == null) {
                    formKeys = new HashSet<String>();
                    this.conditionResultList.put(cacheKey, formKeys);
                }
                formKeys.add(expression.getSource().getFormKey());
            }
        } else {
            DimensionCacheKey cacheKey = new DimensionCacheKey(dimensionSet);
            Set<String> formKeys = this.conditionResultList.get(cacheKey);
            if (formKeys == null) {
                formKeys = new HashSet<String>();
                this.conditionResultList.put(cacheKey, formKeys);
            }
            formKeys.add(expression.getSource().getFormKey());
        }
    }

    public Map<DimensionCacheKey, Set<String>> getConditionResultList() {
        return this.conditionResultList;
    }

    public void message(String msg, Object sender) {
    }

    public void onProgress(double progress) {
    }

    public void debug(String msg, DataEngineConsts.DebugLogType type) {
    }

    public Map<String, DimensionValue> getDimensionValueMap() {
        return this.dimensionValueMap;
    }

    public void setDimensionValueMap(Map<String, DimensionValue> dimensionValueMap) {
        this.dimensionValueMap = dimensionValueMap;
    }

    private List<Map<String, DimensionValue>> getDimensionValueList(List<String> dimensionNames, List<List<String>> dimensionValues, int layer) {
        ArrayList<Map<String, DimensionValue>> dimensionValueList;
        block5: {
            List<String> valueList;
            String dimensionName;
            block4: {
                dimensionValueList = new ArrayList<Map<String, DimensionValue>>();
                dimensionName = dimensionNames.get(layer);
                valueList = dimensionValues.get(layer);
                if (layer >= dimensionNames.size() - 1) break block4;
                List<Map<String, DimensionValue>> subDimensionValueList = this.getDimensionValueList(dimensionNames, dimensionValues, layer + 1);
                for (String dimensionValue : valueList) {
                    for (Map<String, DimensionValue> subDimensionValue : subDimensionValueList) {
                        LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
                        for (Map.Entry<String, DimensionValue> entry : subDimensionValue.entrySet()) {
                            DimensionValue value = new DimensionValue();
                            value.setName(entry.getValue().getName());
                            value.setValue(entry.getValue().getValue());
                            dimensionValueMap.put(entry.getKey(), value);
                        }
                        DimensionValue value = new DimensionValue();
                        value.setName(dimensionName);
                        value.setValue(dimensionValue);
                        dimensionValueMap.put(dimensionName, value);
                        dimensionValueList.add(dimensionValueMap);
                    }
                }
                break block5;
            }
            if (layer != dimensionNames.size() - 1) break block5;
            for (String dimensionValue : valueList) {
                LinkedHashMap<String, DimensionValue> dimensionValueMap = new LinkedHashMap<String, DimensionValue>();
                DimensionValue value = new DimensionValue();
                value.setName(dimensionName);
                value.setValue(dimensionValue);
                dimensionValueMap.put(dimensionName, value);
                dimensionValueList.add(dimensionValueMap);
            }
        }
        return dimensionValueList;
    }
}

