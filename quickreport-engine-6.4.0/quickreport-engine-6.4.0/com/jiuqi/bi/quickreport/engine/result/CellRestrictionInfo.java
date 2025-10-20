/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.model.JSONHelper;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.util.StringUtils;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CellRestrictionInfo
implements Externalizable,
Cloneable,
Comparable<CellRestrictionInfo> {
    private static final long serialVersionUID = 5249591758365795020L;
    private String dataSetName;
    private String fieldName;
    private String expression;
    private transient Object value;
    private static final String RESTRCITION_TABLENAME = "table";
    private static final String RESTRICTION_FIELDNAME = "field";
    private static final String RESTRICTION_VALUE = "value";
    private static final String RESTRICTION_VALUES = "values";
    private static final String RESTRICTION_EXPRESSION = "expr";

    public String getDataSetName() {
        return this.dataSetName;
    }

    public void setDataSetName(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(RESTRCITION_TABLENAME, (Object)this.dataSetName);
        json.put(RESTRICTION_FIELDNAME, (Object)this.fieldName);
        if (this.value instanceof List) {
            List values = (List)this.value;
            if (!values.isEmpty()) {
                JSONArray arr = new JSONArray();
                for (Object v : values) {
                    arr.put((Object)JSONHelper.toTypedValue(v));
                }
                json.put(RESTRICTION_VALUES, (Object)arr);
            }
        } else if (this.value != null) {
            json.put(RESTRICTION_VALUE, (Object)JSONHelper.toTypedValue(this.value));
        }
        json.put(RESTRICTION_EXPRESSION, (Object)this.expression);
        return json;
    }

    public void fromJSON(JSONObject obj) throws JSONException {
        this.dataSetName = obj.optString(RESTRCITION_TABLENAME);
        this.fieldName = obj.optString(RESTRICTION_FIELDNAME);
        if (obj.has(RESTRICTION_VALUE)) {
            JSONObject valObj = obj.optJSONObject(RESTRICTION_VALUE);
            this.value = JSONHelper.fromTypedValue(valObj);
        } else if (obj.has(RESTRICTION_VALUES)) {
            JSONArray arr = obj.optJSONArray(RESTRICTION_VALUES);
            ArrayList<Object> values = new ArrayList<Object>();
            for (int i = 0; i < arr.length(); ++i) {
                values.add(JSONHelper.fromTypedValue(arr.optJSONObject(i)));
            }
            this.value = values;
        }
        this.expression = obj.optString(RESTRICTION_EXPRESSION);
    }

    public CellRestrictionInfo clone() {
        CellRestrictionInfo info;
        try {
            info = (CellRestrictionInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
        if (this.value instanceof List) {
            info.value = new ArrayList((List)this.value);
        }
        return info;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        CellRestrictionInfo.writeValue(out, this.value);
    }

    static void writeValue(ObjectOutput out, Object value) throws IOException {
        if (value instanceof List) {
            List arr = (List)value;
            out.writeInt(arr.size());
            for (Object val : arr) {
                out.writeObject(val);
            }
        } else if (value != null) {
            out.writeInt(1);
            out.writeObject(value);
        } else {
            out.writeInt(0);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.value = CellRestrictionInfo.readValue(in);
    }

    static Object readValue(ObjectInput in) throws IOException, ClassNotFoundException {
        int len = in.readInt();
        if (len <= 0) {
            return null;
        }
        if (len == 1) {
            return in.readObject();
        }
        ArrayList<Object> arr = new ArrayList<Object>(len);
        for (int i = 0; i < len; ++i) {
            Object val = in.readObject();
            arr.add(val);
        }
        return arr;
    }

    public int hashCode() {
        int hash = this.dataSetName == null ? 0 : this.dataSetName.hashCode();
        hash = hash * 31 + (this.fieldName == null ? 0 : this.fieldName.hashCode());
        hash = hash * 31 + (this.expression == null ? 0 : this.expression.hashCode());
        hash = hash * 31 + (this.value == null ? 0 : this.value.hashCode());
        return hash;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CellRestrictionInfo)) {
            return false;
        }
        return this.compareTo((CellRestrictionInfo)obj) == 0;
    }

    @Override
    public int compareTo(CellRestrictionInfo o) {
        int cmp = StringUtils.compare((String)this.dataSetName, (String)o.dataSetName);
        if (cmp != 0) {
            return cmp;
        }
        cmp = StringUtils.compare((String)this.fieldName, (String)o.fieldName);
        if (cmp != 0) {
            return cmp;
        }
        cmp = StringUtils.compare((String)this.expression, (String)o.expression);
        if (cmp != 0) {
            return cmp;
        }
        if (this.value == o.value) {
            return 0;
        }
        if (this.value == null) {
            return -1;
        }
        if (o.value == null) {
            return 1;
        }
        List<?> thisValues = CellRestrictionInfo.valuesOf(this.value);
        List<?> thatValues = CellRestrictionInfo.valuesOf(o.value);
        return CellRestrictionInfo.compare(thisValues, thatValues);
    }

    private static List<?> valuesOf(Object value) {
        if (value instanceof List) {
            return (List)value;
        }
        return value == null ? Collections.emptyList() : Collections.singletonList(value);
    }

    private static int compare(List<?> list1, List<?> list2) {
        Iterator<?> itr1 = list1.iterator();
        Iterator<?> itr2 = list2.iterator();
        while (itr1.hasNext() && itr2.hasNext()) {
            Object v2;
            Object v1 = itr1.next();
            int cmp = DataType.compareObject(v1, v2 = itr2.next());
            if (cmp == 0) continue;
            return cmp;
        }
        if (itr1.hasNext()) {
            return 1;
        }
        if (itr2.hasNext()) {
            return -1;
        }
        return 0;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        if (this.dataSetName != null && this.fieldName != null) {
            buffer.append(this.dataSetName).append('.').append(this.fieldName).append(":");
        }
        if (this.value != null) {
            buffer.append(this.value);
        }
        if (this.expression != null) {
            if (this.value != null) {
                buffer.append(", ");
            }
            buffer.append(this.expression);
        }
        return buffer.toString();
    }
}

