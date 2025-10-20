/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.model.value;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.model.value.SmartSelectorParameterValue;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class AbstractParameterValue
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    protected String binding;
    protected boolean ordered;

    public abstract Object getValue();

    public final String getBindingData() {
        return this.binding;
    }

    public final void setBindingData(String extra) {
        this.binding = extra;
    }

    public final void setOrdered(boolean ordered) {
        this.ordered = ordered;
    }

    public final boolean isOrdered() {
        return this.ordered;
    }

    public abstract String getValueMode();

    public List<Object> toValueList(IParameterValueFormat format) throws ParameterException {
        Object v = this.getValue();
        if (v instanceof List) {
            List data = (List)v;
            ArrayList<Object> rs = new ArrayList<Object>();
            for (Object obj : data) {
                if (obj instanceof String) {
                    rs.add(format.parse((String)obj));
                    continue;
                }
                rs.add(obj);
            }
            return rs;
        }
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(v);
        return list;
    }

    public abstract List<Object> filterValue(List<?> var1) throws ParameterException;

    public final List<String> getKeysAsString(List<Object> candidateValue, IParameterValueFormat format) throws ParameterException {
        List<Object> v = candidateValue == null ? this.toValueList(format) : this.filterValue(candidateValue);
        ArrayList<String> list = new ArrayList<String>();
        for (Object obj : v) {
            list.add(format.format(obj));
        }
        return list;
    }

    @Deprecated
    public final List<String> getKeysAsString(IParameterValueFormat format) throws ParameterException {
        return this.getKeysAsString(null, format);
    }

    public boolean isFormulaValue() {
        return false;
    }

    public boolean isEmpty() {
        Object v = this.getValue();
        if (v == null) {
            return true;
        }
        if (v instanceof List) {
            return ((List)v).isEmpty();
        }
        return false;
    }

    public AbstractParameterValue clone() {
        try {
            return (AbstractParameterValue)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void fromJson(JSONObject var1, AbstractParameterDataSourceModel var2) throws JSONException;

    public void toJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        json.put("valueMode", (Object)this.getValueMode());
        json.put("value", this.getValue());
        if (this.binding != null) {
            json.put("binding", (Object)this.binding);
        }
    }

    public static final AbstractParameterValue loadFromJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        String type = json.optString("valueMode");
        AbstractParameterValue value = null;
        if (type == null || type.equals("fixed")) {
            value = new FixedMemberParameterValue();
        } else if (type.equals("expr")) {
            value = new ExpressionParameterValue(json.optString("value"));
        } else if (type.equals("smartSelector")) {
            value = new SmartSelectorParameterValue();
        }
        ((AbstractParameterValue)value).fromJson(json, datasource);
        return value;
    }
}

