/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ENameSet
 *  com.jiuqi.np.dataengine.common.SpecialValue
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.common.SpecialValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DimValSetSerializ
implements Serializable {
    private static final long serialVersionUID = 2952498103967653193L;
    public static DimensionValueSet EMPTY = new DimensionValueSet();
    public static Object NoStrictDimValue = new SpecialValue("*");
    public static Object SumMergeDimValue = new SpecialValue("+");
    private ENameSet names = new ENameSet();
    private Map<String, Object> values = new HashMap<String, Object>();
    private List<String> keys = new ArrayList<String>();
    private List<Object> vals = new ArrayList<Object>();
    private int hashCode = 0;

    public static DimensionValueSet getEMPTY() {
        return EMPTY;
    }

    public static void setEMPTY(DimensionValueSet EMPTY) {
        DimValSetSerializ.EMPTY = EMPTY;
    }

    public static Object getNoStrictDimValue() {
        return NoStrictDimValue;
    }

    public static void setNoStrictDimValue(Object noStrictDimValue) {
        NoStrictDimValue = noStrictDimValue;
    }

    public static Object getSumMergeDimValue() {
        return SumMergeDimValue;
    }

    public static void setSumMergeDimValue(Object sumMergeDimValue) {
        SumMergeDimValue = sumMergeDimValue;
    }

    public ENameSet getNames() {
        return this.names;
    }

    public void setNames(ENameSet names) {
        this.names = names;
    }

    public Map<String, Object> getValues() {
        return this.values;
    }

    public int getHashCode() {
        return this.hashCode;
    }

    public void setHashCode(int hashCode) {
        this.hashCode = hashCode;
    }

    public String getName(int index) {
        return this.names.get(index);
    }

    public Object getValue(int index) {
        return this.values.get(this.names.get(index));
    }

    public Object getValue(String name) {
        return this.values.get(name);
    }

    @Deprecated
    public void setValue(String name, Object value, int dataType) {
        this.setValue(name, value);
    }

    public void setValue(String name, Object value) {
        int index = this.names.add(name);
        if (index >= 0) {
            if (value != null) {
                this.values.put(name, value);
            }
        } else {
            index = -index;
            if (value != null) {
                this.values.put(name, value);
            } else {
                this.names.removeAt(index);
                this.values.remove(name);
            }
        }
        this.resetHashCode();
    }

    public void clearValue(String name) {
        int index = this.names.remove(name);
        if (index >= 0) {
            this.values.remove(name);
        }
        this.resetHashCode();
    }

    public void clearAll() {
        this.names.clear();
        this.values.clear();
        this.resetHashCode();
    }

    public boolean hasValue(String name) {
        return this.values.containsKey(name);
    }

    public boolean hasAnyNull() {
        for (String key : this.values.keySet()) {
            Object value = this.values.get(key);
            if (value != null) continue;
            return true;
        }
        return false;
    }

    public boolean isAllNull() {
        for (String key : this.values.keySet()) {
            Object value = this.values.get(key);
            if (value == null) continue;
            return false;
        }
        return true;
    }

    public final boolean noStrict() {
        for (String key : this.values.keySet()) {
            Object value = this.values.get(key);
            if (value != NoStrictDimValue) continue;
            return true;
        }
        return false;
    }

    private void resetHashCode() {
        this.hashCode = 0;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    public List<String> getKeys() {
        return this.keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<Object> getVals() {
        return this.vals;
    }

    public void setVals(List<Object> vals) {
        this.vals = vals;
    }
}

