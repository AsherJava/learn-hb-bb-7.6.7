/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDimensionRelationProvider
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.provider.DimensionRow
 *  com.jiuqi.np.definition.provider.DimensionTable
 */
package com.jiuqi.nr.bql.dataengine.impl;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDimensionRelationProvider;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.provider.DimensionRow;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.nr.bql.dataengine.query.DimQueryInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BqlDimensionRelationProvider
implements IDimensionRelationProvider {
    private Map<String, Map<String, List<String>>> dimRelationMap = new HashMap<String, Map<String, List<String>>>();

    public void doInit(QueryContext qContext, List<DimQueryInfo> dimQueryInfos) throws Exception {
        DimensionTable mainDimTble = null;
        for (DimQueryInfo info : dimQueryInfos) {
            if (mainDimTble == null) {
                mainDimTble = qContext.getDimTable(info.getRefTableName(), qContext.getPeriodWrapper());
            }
            String dimension = info.getDimension();
            HashMap<String, List<String>> refMap = new HashMap<String, List<String>>();
            this.dimRelationMap.put(dimension, refMap);
            for (int i = 0; i < mainDimTble.rowCount(); ++i) {
                DimensionRow row = mainDimTble.getRow(i);
                String mainDimKey = row.getKey();
                String refDimKey = (String)row.getValue(info.getRefFieldName());
                if (info.getValues() == null) {
                    refMap.put(mainDimKey, Collections.singletonList(refDimKey));
                    continue;
                }
                ArrayList<String> refList = new ArrayList<String>(info.getValues().stream().map(Object::toString).collect(Collectors.toList()));
                refList.add(refDimKey);
                refMap.put(mainDimKey, refList);
            }
        }
    }

    public List<String> getRelationValuesByDim(ExecutorContext context, String dimension, String mainDimValue, String period) {
        Map<String, List<String>> all = this.getAllRelationValuesByDim(context, dimension, null, period);
        if (all != null) {
            return all.get(mainDimValue);
        }
        return super.getRelationValuesByDim(context, dimension, mainDimValue, period);
    }

    public Map<String, List<String>> getAllRelationValuesByDim(ExecutorContext context, String dimension, List<String> mainDimValues, String period) {
        return this.dimRelationMap.get(dimension);
    }
}

