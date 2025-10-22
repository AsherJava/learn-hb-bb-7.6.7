/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.engine.condition.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.data.engine.condition.impl.FormGroupItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConditionCacheImpl
implements IConditionCache,
Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Set<String>> conditionMap;
    private FormGroupItem groupItem;
    private List<String> entityDimensions;

    public Map<DimensionValueSet, Set<String>> getConditionMap() {
        HashMap<DimensionValueSet, Set<String>> condition = new HashMap<DimensionValueSet, Set<String>>();
        if (this.conditionMap != null && this.conditionMap.size() > 0) {
            for (Map.Entry<String, Set<String>> map : this.conditionMap.entrySet()) {
                String dimensionStr = map.getKey();
                Set<String> value = map.getValue();
                DimensionValueSet dimension = new DimensionValueSet();
                dimension.parseString(dimensionStr);
                condition.put(dimension, value);
            }
        }
        return condition;
    }

    public void setConditionMap(Map<DimensionValueSet, Set<String>> conditionMap) {
        HashMap<String, Set<String>> condition = new HashMap<String, Set<String>>();
        if (conditionMap != null && conditionMap.size() > 0) {
            for (Map.Entry<DimensionValueSet, Set<String>> map : conditionMap.entrySet()) {
                DimensionValueSet dimension = map.getKey();
                Set<String> value = map.getValue();
                condition.put(dimension.toString(), value);
            }
        }
        this.conditionMap = condition;
    }

    public FormGroupItem getGroupItem() {
        return this.groupItem;
    }

    public void setGroupItem(FormGroupItem groupItem) {
        this.groupItem = groupItem;
    }

    public List<String> getEntityDimensions() {
        return this.entityDimensions;
    }

    public void setEntityDimensions(List<String> entityDimensions) {
        this.entityDimensions = entityDimensions;
    }

    @Override
    public boolean canSee(DimensionValueSet dimensionValueSet, String formKey) {
        if (this.conditionMap == null) {
            return true;
        }
        DimensionValueSet rowKey = this.getRowKey(dimensionValueSet);
        Set<String> formKeys = this.conditionMap.get(rowKey.toString());
        return formKeys == null || !formKeys.contains(formKey);
    }

    private DimensionValueSet getRowKey(DimensionValueSet dimensionValueSet) {
        if (this.entityDimensions == null || this.entityDimensions.size() <= 0) {
            return dimensionValueSet;
        }
        DimensionValueSet rowKey = new DimensionValueSet();
        for (String entityDimension : this.entityDimensions) {
            Object value = dimensionValueSet.getValue(entityDimension);
            if (null == value) continue;
            if (entityDimension.startsWith("MD_ORG")) {
                rowKey.setValue(entityDimension, value);
            }
            if (!entityDimension.equals("DATATIME")) continue;
            rowKey.setValue("DATATIME", value);
        }
        return rowKey;
    }

    @Override
    public List<String> getSeeForms(DimensionValueSet dimensionValueSet) {
        if (this.conditionMap == null) {
            return this.groupItem.getFormKeys();
        }
        DimensionValueSet rowKey = this.getRowKey(dimensionValueSet);
        Set<String> conditionValue = this.conditionMap.get(rowKey.toString());
        if (conditionValue != null && conditionValue.size() > 0) {
            if (this.groupItem.isGroupCondition()) {
                ArrayList<String> keys = new ArrayList<String>();
                List<String> formKeys = this.groupItem.getFormKeys();
                Set<String> nonFormsInGroup = this.getNonFormsInGroup(conditionValue);
                for (String formKey : formKeys) {
                    if (conditionValue.contains(formKey) || nonFormsInGroup.contains(formKey)) continue;
                    keys.add(formKey);
                }
                return keys;
            }
            ArrayList<String> keys = new ArrayList<String>();
            List<String> formKeys = this.groupItem.getFormKeys();
            for (String formKey : formKeys) {
                if (conditionValue.contains(formKey)) continue;
                keys.add(formKey);
            }
            return keys;
        }
        return this.groupItem.getFormKeys();
    }

    private Set<String> getNonFormsInGroup(Set<String> conditionValue) {
        HashSet<String> formKeys = new HashSet<String>();
        List<String> groupKeys = this.groupItem.getGroupKeys();
        for (String formGroup : groupKeys) {
            if (!conditionValue.contains(formGroup)) continue;
            formKeys.addAll((Collection<String>)this.groupItem.getFormsByGroup().get(formGroup));
        }
        return formKeys;
    }

    @Override
    public List<String> getSeeFormGroups(DimensionValueSet dimensionValueSet) {
        if (this.conditionMap == null || !this.groupItem.isGroupCondition()) {
            return this.groupItem.getGroupKeys();
        }
        DimensionValueSet rowKey = this.getRowKey(dimensionValueSet);
        Set<String> conditionValue = this.conditionMap.get(rowKey.toString());
        if (conditionValue != null && conditionValue.size() > 0) {
            ArrayList<String> groups = new ArrayList<String>();
            List<String> groupKeys = this.groupItem.getGroupKeys();
            for (String formGroup : groupKeys) {
                if (conditionValue.contains(formGroup)) continue;
                groups.add(formGroup);
            }
            return groups;
        }
        return this.groupItem.getGroupKeys();
    }
}

