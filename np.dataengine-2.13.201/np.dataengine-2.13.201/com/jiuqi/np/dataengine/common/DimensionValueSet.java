/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.collection.ArrayMap
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.bi.util.collection.ArrayMap;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.common.SpecialValue;
import com.jiuqi.np.dataengine.common.StringSerializer;
import com.jiuqi.np.dataengine.common.StringValueFormat;
import com.jiuqi.np.period.PeriodType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class DimensionValueSet {
    public static DimensionValueSet EMPTY = new DimensionValueSet();
    public static Object NoStrictDimValue = new SpecialValue("*");
    public static Object SumMergeDimValue = new SpecialValue("+");
    private ENameSet names = new ENameSet();
    private final Map<String, Object> values = new ArrayMap();
    private int hashCode = 0;
    private static ArrayList<StringSerializer> serializerList = new ArrayList();

    public DimensionValueSet() {
    }

    public DimensionValueSet(DimensionSet dimensions) {
        if (dimensions == null) {
            return;
        }
        this.names.assign(dimensions.getDimensions());
    }

    public DimensionValueSet(DimensionValueSet another) {
        this.assign(another);
    }

    public int size() {
        return this.names.size();
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
        if (value instanceof Date) {
            value = DataTypesConvert.dateToPeriod((Date)value).toString();
        } else if (value instanceof Calendar) {
            PeriodType pt = PeriodType.DAY;
            value = pt.fromCalendar((Calendar)value).toString();
        }
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

    public final boolean hasSumMerge() {
        for (String key : this.values.keySet()) {
            Object value = this.values.get(key);
            if (value != SumMergeDimValue) continue;
            return true;
        }
        return false;
    }

    public void assign(DimensionValueSet source) {
        if (source == null || source == this) {
            return;
        }
        this.names.assign(source.names);
        this.values.putAll(source.values);
        this.resetHashCode();
    }

    public void combine(DimensionValueSet another) {
        if (another == null || another == this) {
            return;
        }
        for (int i = 0; i < another.size(); ++i) {
            this.setValue(another.getName(i), another.getValue(i));
        }
        this.resetHashCode();
    }

    public int compareTo(DimensionValueSet another) {
        if (another == null) {
            return 1;
        }
        int result = this.names.compareTo(another.names);
        if (result == 0) {
            int count = this.names.size();
            for (int i = 0; i < count; ++i) {
                String name = this.names.get(i);
                Object value = this.values.get(name);
                Object anotherValue = another.values.get(name);
                if (value == null) {
                    if (anotherValue == null) continue;
                    return -1;
                }
                if (anotherValue == null) {
                    return 1;
                }
                result = ((Comparable)value).compareTo(anotherValue);
                if (result == 0) continue;
                return result;
            }
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DimensionValueSet)) {
            return false;
        }
        return this.compareTo((DimensionValueSet)obj) == 0;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            int prime = 31;
            int result = this.names.size();
            int count = this.names.size();
            for (int i = 0; i < count; ++i) {
                String name = this.names.get(i);
                Object value = this.values.get(name);
                if (value == null) continue;
                result = result * 31 + value.hashCode();
            }
            this.hashCode = result;
        }
        return this.hashCode;
    }

    public boolean isSubsetOf(DimensionValueSet another) {
        if (another == null) {
            return true;
        }
        int count = another.names.size();
        for (int i = 0; i < count; ++i) {
            String name = another.names.get(i);
            Object value = this.getValue(name);
            if (value == null) {
                return another.getValue(name) != null;
            }
            Object anotherValue = another.getValue(name);
            if (anotherValue != null && Objects.equals(value, anotherValue)) continue;
            return false;
        }
        return true;
    }

    public DimensionSet getDimensionSet() {
        DimensionSet result = new DimensionSet();
        int count = this.names.size();
        for (int i = 0; i < count; ++i) {
            result.addDimension(this.names.get(i));
        }
        result.setLocked();
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder(64);
        boolean needComma = false;
        int count = this.names.size();
        for (int i = 0; i < count; ++i) {
            if (needComma) {
                result.append(", ");
            }
            result.append(this.names.get(i));
            result.append('=');
            DimensionValueSet.serialize(result, this.getValue(i));
            needComma = true;
        }
        return result.toString();
    }

    public void parseString(String strData) {
        this.clearAll();
        StringTokenizer st = new StringTokenizer(strData, ";,");
        while (st.hasMoreTokens()) {
            String data = st.nextToken().trim();
            int p = data.indexOf(61);
            if (p <= 0) continue;
            String dimension = data.substring(0, p);
            Object value = DimensionValueSet.deserialize(data.substring(p + 1).trim());
            this.setValue(dimension, value);
        }
    }

    public static void registerValueParser(StringSerializer serializer) {
        if (serializerList.indexOf(serializer) < 0) {
            serializerList.add(serializer);
        }
    }

    public static void unregisterValueParser(StringSerializer serializer) {
        serializerList.remove(serializer);
    }

    public static void serialize(StringBuilder dest, Object value) {
        if (value == null) {
            return;
        }
        int count = serializerList.size();
        for (int i = 0; i < count; ++i) {
            StringSerializer serializer = serializerList.get(i);
            if (!serializer.serialize(dest, value)) continue;
            return;
        }
        dest.append(value.toString());
    }

    public static Object deserialize(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        int count = serializerList.size();
        for (int i = 0; i < count; ++i) {
            StringSerializer serializer = serializerList.get(i);
            Object result = serializer.deserialize(value);
            if (result == null) continue;
            return result;
        }
        return value;
    }

    private void resetHashCode() {
        this.hashCode = 0;
    }

    static {
        DimensionValueSet.registerValueParser(new StringValueFormat());
    }
}

