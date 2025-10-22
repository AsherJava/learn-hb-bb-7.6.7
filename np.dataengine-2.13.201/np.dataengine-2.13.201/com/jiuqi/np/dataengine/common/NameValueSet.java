/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.ENameSet;
import com.jiuqi.np.dataengine.common.IntegerValueFormat;
import com.jiuqi.np.dataengine.common.StringSerializer;
import com.jiuqi.np.dataengine.common.StringValueFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class NameValueSet {
    private ENameSet names = new ENameSet();
    private HashMap values = new HashMap(2);
    private static ArrayList serializerList = new ArrayList();

    public NameValueSet() {
    }

    public NameValueSet(String nameValues) {
        this.parseString(nameValues);
    }

    public NameValueSet(NameValueSet another) {
        this.assign(another);
    }

    public NameValueSet(DimensionSet dimensions, NameValueSet values) {
        int count = dimensions.size();
        for (int i = 0; i < count; ++i) {
            String dimension = dimensions.get(i);
            this.setValue(dimension, values.getValue(dimension));
        }
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
    }

    public void clearValue(String name) {
        int index = this.names.remove(name);
        if (index >= 0) {
            this.values.remove(name);
        }
    }

    public void clearAll() {
        this.names.clear();
        this.values.clear();
    }

    public boolean hasValue(String name) {
        return this.values.containsKey(name);
    }

    public void assign(NameValueSet source) {
        if (source == null || source == this) {
            return;
        }
        this.names.assign(source.names);
        this.values.putAll(source.values);
    }

    public void combine(NameValueSet another) {
        for (int i = 0; i < another.size(); ++i) {
            this.setValue(another.getName(i), another.getValue(i));
        }
    }

    public int compareTo(NameValueSet another) {
        if (another == null) {
            return 1;
        }
        int result = this.names.compareTo(another.names);
        if (result == 0) {
            int count = this.names.size();
            for (int i = 0; i < count; ++i) {
                String name = this.names.get(i);
                result = ((Comparable)this.values.get(name)).compareTo(another.values.get(name));
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
        if (!(obj instanceof NameValueSet)) {
            return false;
        }
        return this.compareTo((NameValueSet)obj) == 0;
    }

    public int hashCode() {
        int result = this.names.size();
        int count = this.names.size();
        for (int i = 0; i < count; ++i) {
            String name = this.names.get(i);
            result += this.values.get(name).hashCode();
        }
        return result;
    }

    public boolean isSubsetOf(NameValueSet another) {
        if (another == null) {
            return true;
        }
        int count = another.names.size();
        for (int i = 0; i < count; ++i) {
            String name = another.names.get(i);
            Object value = this.getValue(name);
            if (value == null) {
                return false;
            }
            if (value.equals(another.getValue(name))) continue;
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

    public void parseString(String strData) {
        this.clearAll();
        StringTokenizer st = new StringTokenizer(strData, ";,");
        while (st.hasMoreTokens()) {
            String data = st.nextToken();
            int p = data.indexOf(61);
            if (p <= 0) continue;
            String dimension = data.substring(0, p);
            Object value = NameValueSet.deserialize(data.substring(p + 1));
            this.setValue(dimension, value);
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        boolean needComma = false;
        int count = this.names.size();
        for (int i = 0; i < count; ++i) {
            if (needComma) {
                result.append(",");
            }
            result.append(this.names.get(i));
            result.append('=');
            NameValueSet.serialize(result, this.getValue(i));
            needComma = true;
        }
        return result.toString();
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
            StringSerializer serializer = (StringSerializer)serializerList.get(i);
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
            StringSerializer serializer = (StringSerializer)serializerList.get(i);
            Object result = serializer.deserialize(value);
            if (result == null) continue;
            return result;
        }
        return value;
    }

    static {
        NameValueSet.registerValueParser(new StringValueFormat());
        NameValueSet.registerValueParser(new IntegerValueFormat());
    }
}

