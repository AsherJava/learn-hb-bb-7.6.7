/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.ref;

import com.jiuqi.va.biz.intf.ref.RefTableDataManager;
import com.jiuqi.va.biz.intf.ref.RefTableDataMap;
import com.jiuqi.va.biz.intf.ref.RefTableDataProvider;
import java.util.HashMap;
import java.util.Map;

public class RefDataBuffer {
    private Map<Integer, Map<String, Map<Map<String, Object>, RefTableDataMap>>> refMap = new HashMap<Integer, Map<String, Map<Map<String, Object>, RefTableDataMap>>>();

    public RefTableDataMap getRefTableMap(int refTableType, String refTableName, Map<String, Object> dimValues) {
        RefTableDataProvider provider;
        RefTableDataMap refDataMap;
        Map<Map<String, Object>, RefTableDataMap> refDimMap;
        Map<String, Map<Map<String, Object>, RefTableDataMap>> refTableMap = this.refMap.get(refTableType);
        if (refTableMap == null) {
            refTableMap = new HashMap<String, Map<Map<String, Object>, RefTableDataMap>>();
            this.refMap.put(refTableType, refTableMap);
        }
        if ((refDimMap = refTableMap.get(refTableName)) == null) {
            refDimMap = new HashMap<Map<String, Object>, RefTableDataMap>();
            refTableMap.put(refTableName, refDimMap);
        }
        if ((refDataMap = refDimMap.get(dimValues = (provider = RefTableDataManager.get(refTableType)).convertDimValues(refTableName, dimValues))) == null) {
            refDataMap = provider.getRefTableDataMap(refTableName, dimValues);
            refDimMap.put(dimValues, refDataMap);
        }
        return refDataMap;
    }
}

