/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow
 *  com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow
 *  com.jiuqi.nr.entity.engine.result.EntityResultSet
 */
package com.jiuqi.nr.batch.summary.service.ext.entityframe;

import com.jiuqi.nr.batch.summary.service.ext.entityframe.CorporateEntityData;
import com.jiuqi.nr.batch.summary.storage.condition.CustomConditionRow;
import com.jiuqi.nr.batch.summary.storage.entity.CustomCalibreRow;
import com.jiuqi.nr.entity.engine.result.EntityResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConditionGatherResultSet
extends EntityResultSet {
    private Map<String, String> columnMap;
    private List<CustomConditionRow> conditionRows;

    public ConditionGatherResultSet(List<CustomConditionRow> conditionRows, Map<String, CorporateEntityData> corporateColumn2Value) {
        super(conditionRows.size());
        this.conditionRows = conditionRows;
        this.initMap(corporateColumn2Value);
    }

    public List<String> getAllKeys() {
        return this.conditionRows.stream().map(CustomCalibreRow::getKey).collect(Collectors.toList());
    }

    protected Object getColumnObject(int index, String columnCode) {
        return this.columnMap.get(columnCode);
    }

    protected String getKey(int index) {
        return this.conditionRows.get(index).getKey();
    }

    protected String getCode(int index) {
        return this.conditionRows.get(index).getCode();
    }

    protected String getTitle(int index) {
        return this.conditionRows.get(index).getTitle();
    }

    protected String getParent(int index) {
        return this.conditionRows.get(index).getParentCode();
    }

    protected Object getOrder(int index) {
        return this.conditionRows.get(index).getOrdinal();
    }

    protected String[] getParents(int index) {
        return this.conditionRows.get(index).getPath();
    }

    public int append(EntityResultSet rs) {
        if (rs instanceof ConditionGatherResultSet) {
            ConditionGatherResultSet condiResultSet = (ConditionGatherResultSet)rs;
            if (this.conditionRows == null) {
                this.conditionRows = new ArrayList<CustomConditionRow>();
            }
            this.conditionRows.addAll(condiResultSet.conditionRows);
            return this.conditionRows.size();
        }
        return -1;
    }

    public boolean hasChildren(int index) {
        return this.conditionRows.get(index).getChildCount() > 0;
    }

    private void initMap(Map<String, CorporateEntityData> corporateColumn2Value) {
        this.columnMap = new HashMap<String, String>();
        corporateColumn2Value.keySet().forEach(k -> this.columnMap.put((String)k, ((CorporateEntityData)corporateColumn2Value.get(k)).getCode()));
    }
}

