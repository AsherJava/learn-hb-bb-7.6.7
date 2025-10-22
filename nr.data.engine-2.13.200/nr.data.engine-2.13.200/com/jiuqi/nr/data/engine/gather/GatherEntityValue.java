/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.data.engine.gather.NotGatherEntityValue;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class GatherEntityValue {
    private List<String> idValues;
    private List<String> midValues;
    private List<String> pidValues;
    private List<Double> isMinus;
    private List<Integer> levelValues;
    private List<Long> unitOrders;
    protected Map<String, List<String>> dimValues;
    protected Map<String, List<String>> dimValuesBySelf = new HashMap<String, List<String>>();
    private Map<String, Set<String>> notGatherIdMap = new HashMap<String, Set<String>>();

    public GatherEntityValue() {
        this.idValues = new ArrayList<String>();
        this.midValues = new ArrayList<String>();
        this.pidValues = new ArrayList<String>();
        this.isMinus = new ArrayList<Double>();
        this.levelValues = new ArrayList<Integer>();
        this.unitOrders = new ArrayList<Long>();
        this.dimValues = new LinkedHashMap<String, List<String>>();
    }

    public List<String> getIdValues() {
        return this.idValues;
    }

    public void setIdValues(List<String> idValues) {
        this.idValues = idValues;
    }

    public List<String> getMidValues() {
        return this.midValues;
    }

    public void setMidValues(List<String> midValues) {
        this.midValues = midValues;
    }

    public List<String> getPidValues() {
        return this.pidValues;
    }

    public void setPidValues(List<String> pidValues) {
        this.pidValues = pidValues;
    }

    public List<Double> getIsMinus() {
        return this.isMinus;
    }

    public void setIsMinus(List<Double> isMinus) {
        this.isMinus = isMinus;
    }

    public List<Integer> getLevelValues() {
        return this.levelValues;
    }

    public void setLevelValues(List<Integer> levelValues) {
        this.levelValues = levelValues;
    }

    public List<Long> getUnitOrders() {
        return this.unitOrders;
    }

    public void setUnitOrders(List<Long> unitOrders) {
        this.unitOrders = unitOrders;
    }

    public Map<String, List<String>> getDimValues() {
        return this.dimValues;
    }

    public void setDimValues(Map<String, List<String>> dimValues) {
        this.dimValues = dimValues;
    }

    public Map<String, List<String>> getDimValuesBySelf() {
        return this.dimValuesBySelf;
    }

    public void setDimValuesBySelf(Map<String, List<String>> dimValuesBySelf) {
        this.dimValuesBySelf = dimValuesBySelf;
    }

    public List<String> getAllTempColumns() {
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("GT_ID");
        columns.add("GT_PID");
        columns.add("GT_MINUSID");
        columns.add("GT_ISMINUS");
        columns.add("GT_LEVEL");
        columns.add("EXECUTION_ID");
        columns.add("TIME_");
        columns.add("GT_ORDER");
        return columns;
    }

    public Object[] getColumnValues(int index, boolean isUUID, String executionId) {
        Object[] values = new Object[13];
        values[0] = isUUID ? Convert.toUUID((String)this.idValues.get(index)) : this.idValues.get(index);
        Object object = values[1] = isUUID ? Convert.toUUID((String)this.pidValues.get(index)) : this.pidValues.get(index);
        values[2] = this.midValues == null || this.midValues.size() <= 0 ? null : (isUUID ? Convert.toUUID((String)this.midValues.get(index)) : this.midValues.get(index));
        values[3] = this.isMinus == null || this.isMinus.size() <= 0 ? null : this.isMinus.get(index);
        values[4] = this.levelValues == null || this.levelValues.size() <= 0 ? null : this.levelValues.get(index);
        values[5] = executionId;
        values[6] = new Timestamp(System.currentTimeMillis());
        values[7] = this.unitOrders == null || this.unitOrders.size() <= 0 ? (Number)0 : (Number)this.unitOrders.get(index);
        int dimIndex = 8;
        for (Map.Entry<String, List<String>> entry : this.dimValues.entrySet()) {
            values[dimIndex] = CollectionUtils.isEmpty(this.dimValues) ? Integer.valueOf(0) : this.dimValues.get(entry.getKey()).get(index);
            ++dimIndex;
        }
        int extra = 5 - this.dimValues.size();
        if (extra > 0) {
            for (int i = 0; i < extra; ++i) {
                values[dimIndex] = null;
                ++dimIndex;
            }
        }
        return values;
    }

    public Set<String> getChildrenIds(Set<String> parentIds) {
        HashSet<String> result = new HashSet<String>();
        for (String parentId : parentIds) {
            for (int i = 0; i < this.pidValues.size(); ++i) {
                if (!parentId.equals(this.pidValues.get(i))) continue;
                result.add(this.idValues.get(i));
            }
        }
        return result;
    }

    public List<String> getPidValuesByLevel(Integer level) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < this.levelValues.size(); ++i) {
            Integer integer = this.levelValues.get(i);
            if (!level.equals(integer)) continue;
            result.add(this.pidValues.get(i));
        }
        return result;
    }

    public List<String> getIdValuesByLevel(Integer level) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < this.levelValues.size(); ++i) {
            Integer integer = this.levelValues.get(i);
            if (!level.equals(integer)) continue;
            result.add(this.idValues.get(i));
        }
        return result;
    }

    public Map<String, Map<String, String>> getIdAndDimValue(Integer level) {
        HashMap<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        for (int i = 0; i < this.levelValues.size(); ++i) {
            Integer integer = this.levelValues.get(i);
            if (!level.equals(integer)) continue;
            HashMap<String, String> map = new HashMap<String, String>();
            for (Map.Entry<String, List<String>> entry : this.dimValuesBySelf.entrySet()) {
                map.put(entry.getKey(), this.dimValuesBySelf.get(entry.getKey()).get(i));
            }
            result.put(this.idValues.get(i), map);
        }
        return result;
    }

    public Map<String, Map<String, String>> getPIdAndDimValue(Integer level) {
        HashMap<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        for (int i = 0; i < this.levelValues.size(); ++i) {
            Integer integer = this.levelValues.get(i);
            if (!level.equals(integer)) continue;
            HashMap<String, String> map = new HashMap<String, String>();
            for (Map.Entry<String, List<String>> entry : this.dimValuesBySelf.entrySet()) {
                map.put(entry.getKey(), this.dimValuesBySelf.get(entry.getKey()).get(i));
            }
            result.put(this.pidValues.get(i), map);
        }
        return result;
    }

    public Map<String, String> getIdValuesAndPidValuesByLevel(Integer level) {
        HashMap<String, String> result = new HashMap<String, String>();
        for (int i = 0; i < this.levelValues.size(); ++i) {
            Integer integer = this.levelValues.get(i);
            if (!level.equals(integer)) continue;
            result.put(this.idValues.get(i), this.pidValues.get(i));
        }
        return result;
    }

    public Map<String, Double> getIdValuesAndMinusValuesByLevel(Integer level) {
        HashMap<String, Double> result = new HashMap<String, Double>();
        for (int i = 0; i < this.levelValues.size(); ++i) {
            Integer integer = this.levelValues.get(i);
            if (!level.equals(integer)) continue;
            result.put(this.idValues.get(i), this.isMinus.get(i));
        }
        return result;
    }

    public List<String> getIdValuesByLevelAndRemove(Integer level, NotGatherEntityValue notGatherEntityValue, String formKey) {
        List<String> idValuesByLevel = this.getIdValuesByLevel(level);
        if (notGatherEntityValue == null) {
            return idValuesByLevel;
        }
        Map<String, Set<String>> notGatherUnitByFormKey = notGatherEntityValue.getNotGatherUnitByFormKey();
        Set<Object> childrenIds = new HashSet();
        if (notGatherUnitByFormKey.containsKey(formKey)) {
            if (!this.notGatherIdMap.containsKey(formKey)) {
                this.notGatherIdMap.put(formKey, this.getChildrenIds(notGatherUnitByFormKey.get(formKey)));
            }
            childrenIds = this.notGatherIdMap.get(formKey);
        }
        idValuesByLevel.removeIf(childrenIds::contains);
        return idValuesByLevel;
    }

    public List<String> getPIdValuesByLevelAndRemove(Integer level, NotGatherEntityValue notGatherEntityValue, String formKey) {
        List<String> pidValuesByLevel = this.getPidValuesByLevel(level);
        if (notGatherEntityValue == null) {
            return pidValuesByLevel;
        }
        Map<String, Set<String>> notGatherUnitByFormKey = notGatherEntityValue.getNotGatherUnitByFormKey();
        Set<String> noGatherUnit = notGatherUnitByFormKey.get(formKey);
        HashSet<String> pid = new HashSet<String>(pidValuesByLevel);
        if (!CollectionUtils.isEmpty(noGatherUnit)) {
            pid.removeAll(noGatherUnit);
        }
        return new ArrayList<String>(pid);
    }

    public List<String> getAllPIds() {
        return new ArrayList<String>(new HashSet<String>(this.pidValues));
    }
}

