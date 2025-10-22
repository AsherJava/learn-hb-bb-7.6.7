/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IUnitLeafFinder;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryStatLeafHelper {
    private Map<String, Set<String>> leafParents = new HashMap<String, Set<String>>();
    private String unitDimension;
    private Set<String> returnUnitKeys = new HashSet<String>();

    public QueryStatLeafHelper(String unitDimension) {
        this.unitDimension = unitDimension;
    }

    public void addLeafParent(String leafKey, String parentKey) {
        Set<String> parents = this.leafParents.get(leafKey);
        if (parents == null) {
            parents = new HashSet<String>();
            this.leafParents.put(leafKey, parents);
        }
        parents.add(parentKey);
    }

    public List<DimensionValueSet> getParentRowKeys(DimensionValueSet rowKey) {
        String leafKey = (String)rowKey.getValue(this.unitDimension);
        Set<String> parents = this.leafParents.get(leafKey);
        if (parents != null) {
            ArrayList<DimensionValueSet> parentRowKeys = new ArrayList<DimensionValueSet>();
            parents.forEach(parentKey -> {
                DimensionValueSet parentRowKey = new DimensionValueSet(rowKey);
                parentRowKey.setValue(this.unitDimension, parentKey);
                parentRowKeys.add(parentRowKey);
            });
            return parentRowKeys;
        }
        return null;
    }

    public Object processUnitLeafs(QueryContext qContext, IUnitLeafFinder unitLeafFinder, Object unitkeyValue) {
        String peirod;
        HashSet<String> unitKeySet = new HashSet<String>();
        PeriodWrapper periodWrapper = qContext.getPeriodWrapper();
        String string = peirod = periodWrapper == null ? null : periodWrapper.toString();
        if (unitkeyValue instanceof List) {
            List<String> unitKeys = ((List)unitkeyValue).stream().map(Object::toString).collect(Collectors.toList());
            this.returnUnitKeys.addAll(unitKeys);
            Map<String, List<String>> leafMap = unitLeafFinder.getAllSubLeafUnits(qContext.getExeContext(), peirod, null, unitKeys);
            for (String unitKey : unitKeys) {
                List<String> subLeafs = leafMap.get(unitKey);
                if (subLeafs != null && subLeafs.size() > 0) {
                    for (String subLeaf : subLeafs) {
                        this.addLeafParent(subLeaf, unitKey);
                        unitKeySet.add(subLeaf);
                    }
                    continue;
                }
                unitKeySet.add(unitKey);
            }
        } else {
            String unitKey = unitkeyValue.toString();
            this.returnUnitKeys.add(unitKey);
            List<String> subLeafUnits = unitLeafFinder.getSubLeafUnits(qContext.getExeContext(), peirod, null, unitKey);
            if (subLeafUnits != null && subLeafUnits.size() > 0) {
                for (String subLeaf : subLeafUnits) {
                    this.addLeafParent(subLeaf, unitKey);
                    unitKeySet.add(subLeaf);
                }
            } else {
                return unitkeyValue;
            }
        }
        return new ArrayList(unitKeySet);
    }

    public boolean needDataRow(DimensionValueSet rowKey) {
        String unitKey = (String)rowKey.getValue(this.unitDimension);
        return this.returnUnitKeys.contains(unitKey);
    }
}

