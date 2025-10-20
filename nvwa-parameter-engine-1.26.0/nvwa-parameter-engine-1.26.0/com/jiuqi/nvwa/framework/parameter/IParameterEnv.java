/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter;

import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface IParameterEnv {
    public ParameterResultset getValue(String var1) throws ParameterException;

    public AbstractParameterValue getOriginalValue(String var1) throws ParameterException;

    default public List<Object> getValueAsList(String parameterName) throws ParameterException {
        ParameterResultset result = this.getValue(parameterName);
        if (result == null || result.isEmpty()) {
            return new ArrayList<Object>();
        }
        return result.getValueAsList();
    }

    public void initValue(Map<String, List<Object>> var1) throws ParameterException;

    public void setValue(String var1, List<Object> var2) throws ParameterException;

    default public void setValue(String parameterName, List<Object> value, boolean mustOrderValue) throws ParameterException {
        this.setValue(parameterName, value);
    }

    public ParameterModel getParameterModelByName(String var1);

    public List<ParameterModel> getParameterModels();

    public boolean addParameterModel(ParameterModel var1) throws ParameterException;

    public String getDimTree(String var1) throws ParameterException;

    public void setDimTree(String var1, String var2) throws ParameterException;

    public String getExtraValue(String var1);

    public void setExtraValue(String var1, String var2);

    public String getUserId();

    public String getLanguage();

    default public void setLanguage(String language) {
    }

    public IParameterEnv clone();
}

