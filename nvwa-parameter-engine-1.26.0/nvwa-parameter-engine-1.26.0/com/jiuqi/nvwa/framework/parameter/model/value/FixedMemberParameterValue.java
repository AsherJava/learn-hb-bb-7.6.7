/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.model.value;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FixedMemberParameterValue
extends AbstractParameterValue {
    private static final long serialVersionUID = 1L;
    private List<Object> items = new ArrayList<Object>();

    public FixedMemberParameterValue() {
    }

    public FixedMemberParameterValue(Object value) {
        this.items.add(value);
    }

    public FixedMemberParameterValue(Collection<?> values) {
        this.items.addAll(values);
    }

    @Override
    public Object getValue() {
        return this.items;
    }

    public List<Object> getItems() {
        return this.items;
    }

    @Override
    public List<Object> filterValue(List<?> candidateValue) throws ParameterException {
        ArrayList<Object> list = new ArrayList<Object>();
        for (Object item : this.items) {
            if (!candidateValue.contains(item)) continue;
            list.add(item);
        }
        return list;
    }

    @Override
    public void fromJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        JSONArray array = json.optJSONArray("value");
        if (array != null) {
            int type = datasource.getDataType();
            IParameterValueFormat format = ParameterUtils.createValueFormat(datasource);
            for (int i = 0; i < array.length(); ++i) {
                Object v = array.opt(i);
                if (v == JSONObject.NULL) {
                    this.items.add(null);
                    continue;
                }
                if (type != 6 && v instanceof String) {
                    try {
                        this.items.add(format.parse((String)v));
                        continue;
                    }
                    catch (ParameterException e) {
                        throw new JSONException("\u53c2\u6570\u53d6\u503c\u683c\u5f0f\u9519\u8bef\uff1a" + e.getMessage());
                    }
                }
                this.items.add(v);
            }
        }
        this.binding = json.optString("binding", null);
    }

    @Override
    public void toJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        json.put("valueMode", (Object)this.getValueMode());
        JSONArray array = new JSONArray();
        int type = datasource.getDataType();
        IParameterValueFormat format = ParameterUtils.createValueFormat(datasource);
        for (Object item : this.items) {
            if (item != null && item != JSONObject.NULL && type != 6 && !(item instanceof String)) {
                try {
                    array.put((Object)format.format(item));
                    continue;
                }
                catch (ParameterException e) {
                    throw new JSONException("\u53c2\u6570\u53d6\u503c\u683c\u5f0f\u9519\u8bef\uff1a" + e.getMessage());
                }
            }
            array.put(item);
        }
        json.put("value", (Object)array);
        if (this.binding != null) {
            json.put("binding", (Object)this.binding);
        }
    }

    @Override
    public String getValueMode() {
        return "fixed";
    }

    @Override
    public AbstractParameterValue clone() {
        return new FixedMemberParameterValue(this.items);
    }

    public String toString() {
        return this.items.toString();
    }
}

