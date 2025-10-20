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
import com.jiuqi.nvwa.framework.parameter.model.SmartSelector;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class SmartSelectorParameterValue
extends AbstractParameterValue {
    private static final long serialVersionUID = 1L;
    private SmartSelector smartSelector;

    public SmartSelectorParameterValue() {
    }

    public SmartSelectorParameterValue(SmartSelector smartSelector) {
        this.smartSelector = smartSelector;
    }

    @Override
    public SmartSelector getValue() {
        return this.smartSelector;
    }

    @Override
    public List<Object> toValueList(IParameterValueFormat format) throws ParameterException {
        if (this.smartSelector.getSelectMode().equals("fixed")) {
            return new ArrayList<Object>(this.smartSelector.getSelectedValues());
        }
        throw new ParameterException("\u65e0\u6cd5\u8f6c\u5316\u4e3a\u503c\u5217\u8868\u5f62\u5f0f\uff1a" + this.smartSelector.getSelectMode());
    }

    @Override
    public List<Object> filterValue(List<?> candidateValue) throws ParameterException {
        ArrayList<Object> list = new ArrayList<Object>();
        for (Object obj : candidateValue) {
            if (!this.smartSelector.match(obj)) continue;
            list.add(obj);
        }
        return list;
    }

    @Override
    public String getValueMode() {
        return "smartSelector";
    }

    @Override
    public void toJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        json.put("valueMode", (Object)this.getValueMode());
        this.smartSelector.toJson(json);
    }

    @Override
    public void fromJson(JSONObject json, AbstractParameterDataSourceModel datasource) throws JSONException {
        this.smartSelector.fromJson(json);
    }

    @Override
    public AbstractParameterValue clone() {
        SmartSelector ss = new SmartSelector();
        ss.setSelectMode(this.smartSelector.getSelectMode());
        ss.getSelectedValues().addAll(this.smartSelector.getSelectedValues());
        ss.setExact(this.smartSelector.isExact());
        ss.setMatchTxt(this.smartSelector.getMatchTxt());
        ss.getRanges().addAll(this.smartSelector.getRanges());
        return new SmartSelectorParameterValue(ss);
    }
}

