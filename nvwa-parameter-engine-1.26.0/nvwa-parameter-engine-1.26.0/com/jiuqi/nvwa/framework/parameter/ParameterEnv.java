/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter;

import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.SmartSelector;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.model.value.SmartSelectorParameterValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class ParameterEnv
implements IParameterEnv {
    private ParameterCalculator calculator;
    private static final String F_BINDING_TAG = "._BINDING_";

    private ParameterEnv() {
    }

    public ParameterEnv(String userId, List<ParameterModel> models) {
        this.calculator = new ParameterCalculator(userId, models);
    }

    @Override
    public void initValue(Map<String, List<Object>> valueMap) throws ParameterException {
        HashMap<String, AbstractParameterValue> vMap = new HashMap<String, AbstractParameterValue>();
        HashMap<String, List<Object>> bindings = new HashMap<String, List<Object>>();
        for (Map.Entry<String, List<Object>> entry : valueMap.entrySet()) {
            String name = entry.getKey();
            List<Object> v = entry.getValue();
            if (name.endsWith(F_BINDING_TAG)) {
                bindings.put(name.substring(0, name.length() - 10), v);
                continue;
            }
            ParameterModel model = this.calculator.getParameterModelByName(name);
            if (model == null) continue;
            if (this.isSmartSelectorParameter(model)) {
                vMap.put(name, this.parseAsSmartSelectorValue(v));
                continue;
            }
            vMap.put(name, new FixedMemberParameterValue(v));
        }
        if (bindings.size() > 0) {
            for (Map.Entry<String, List<Object>> entry : bindings.entrySet()) {
                AbstractParameterValue v = (AbstractParameterValue)vMap.get(entry.getKey());
                if (v == null || entry.getValue().size() <= 0) continue;
                String bind = (String)entry.getValue().get(0);
                v.setBindingData(bind);
            }
        }
        this.calculator.initValue(vMap);
    }

    @Override
    public AbstractParameterValue getOriginalValue(String parameterName) throws ParameterException {
        return this.calculator.getOriginalValue(parameterName);
    }

    @Override
    public void setValue(String parameterName, List<Object> value, boolean mustOrderValue) throws ParameterException {
        AbstractParameterValue apv;
        ParameterModel model = this.calculator.getParameterModelByName(parameterName);
        if (model == null) {
            return;
        }
        if (model.isRangeParameter()) {
            String v = parameterName.toLowerCase();
            if (v.endsWith(".min") || v.endsWith(".max")) {
                if (value.size() > 1) {
                    throw new ParameterException("\u53c2\u6570\u503c\u683c\u5f0f\u9519\u8bef\uff0c\u8303\u56f4\u53c2\u6570\u6700\u5927\u503c\u6216\u8005\u6700\u5c0f\u503c\u4e0d\u5141\u8bb8\u4e3a\u591a\u503c");
                }
            } else if (value.size() == 1) {
                value = new ArrayList<Object>(value);
                value.add(value.get(0));
            } else if (value.size() != 2) {
                throw new ParameterException("\u53c2\u6570\u503c\u683c\u5f0f\u9519\u8bef\uff0c\u8303\u56f4\u53c2\u6570\u957f\u5ea6\u5fc5\u987b\u4e3a2");
            }
        }
        if (this.isSmartSelectorParameter(model)) {
            apv = this.parseAsSmartSelectorValue(value);
        } else {
            IParameterValueFormat format = ParameterUtils.createValueFormat(model.getDatasource());
            ArrayList<Object> fomated = new ArrayList<Object>();
            for (Object v : value) {
                if (v instanceof String) {
                    fomated.add(format.parse((String)v));
                    continue;
                }
                fomated.add(v);
            }
            apv = new FixedMemberParameterValue(fomated);
        }
        apv.setOrdered(mustOrderValue);
        this.calculator.setValue(parameterName, apv);
    }

    @Override
    public void setValue(String parameterName, List<Object> value) throws ParameterException {
        this.setValue(parameterName, value, false);
    }

    private boolean isSmartSelectorParameter(ParameterModel model) {
        int widgetType = model.getWidgetType();
        if (widgetType == ParameterWidgetType.DEFAULT.value()) {
            IParameterRenderer render = ParameterDataSourceManager.getInstance().getFactory(model.getDatasource().getType()).createParameterRenderer();
            widgetType = render.getDefaultWidgetType(model.getDatasource(), model.getSelectMode());
        }
        return widgetType == ParameterWidgetType.SMARTSELECTOR.value();
    }

    private SmartSelectorParameterValue parseAsSmartSelectorValue(Object value) throws ParameterException {
        List list;
        if (value == null) {
            return null;
        }
        if (value instanceof List && (list = (List)value).size() == 1) {
            value = list.get(0);
        }
        if (value instanceof SmartSelector) {
            return new SmartSelectorParameterValue((SmartSelector)value);
        }
        SmartSelector ss = new SmartSelector();
        if (value instanceof String) {
            String val = (String)value;
            if (this.isJson(val)) {
                ss.fromJson(new JSONObject(val));
            } else {
                ss.setSelectMode("fixed");
                ss.getSelectedValues().add(val);
            }
        } else if (value instanceof List) {
            ss.setSelectMode("fixed");
            ss.getSelectedValues().addAll((List)value);
        } else {
            ss.setSelectMode("fixed");
            ss.getSelectedValues().add(value);
        }
        return new SmartSelectorParameterValue(ss);
    }

    private boolean isJson(String value) {
        return value != null && value.length() > 0 && value.charAt(0) == '{' && value.charAt(value.length() - 1) == '}';
    }

    @Override
    public ParameterResultset getValue(String parameterName) throws ParameterException {
        return this.calculator.getValue(parameterName);
    }

    @Override
    public List<ParameterModel> getParameterModels() {
        return this.calculator.getParameterModels();
    }

    @Override
    public ParameterModel getParameterModelByName(String parameterName) {
        return this.calculator.getParameterModelByName(parameterName);
    }

    @Override
    public boolean addParameterModel(ParameterModel model) throws ParameterException {
        return this.calculator.addParameterModel(model);
    }

    @Override
    public String getDimTree(String parameterName) throws ParameterException {
        return this.calculator.getDimTree(parameterName);
    }

    @Override
    public void setDimTree(String parameterName, String dimTreeCode) throws ParameterException {
        this.calculator.setDimTree(parameterName, dimTreeCode);
    }

    @Override
    public String getUserId() {
        return this.calculator.getUserId();
    }

    @Override
    public String getLanguage() {
        return this.calculator.getLanguage();
    }

    @Override
    public void setLanguage(String language) {
        this.calculator.setLanguage(language);
    }

    @Override
    public void setExtraValue(String key, String value) {
        this.calculator.setExtraValue(key, value);
    }

    @Override
    public String getExtraValue(String key) {
        return this.calculator.getExtraValue(key);
    }

    @Override
    public IParameterEnv clone() {
        ParameterEnv env = new ParameterEnv();
        env.calculator = this.calculator.clone();
        return env;
    }
}

