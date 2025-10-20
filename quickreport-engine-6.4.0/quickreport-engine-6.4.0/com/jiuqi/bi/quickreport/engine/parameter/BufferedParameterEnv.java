/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.collection.ArrayMap
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 */
package com.jiuqi.bi.quickreport.engine.parameter;

import com.jiuqi.bi.util.collection.ArrayMap;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BufferedParameterEnv
implements IParameterEnv {
    private final IParameterEnv env;
    private Map<String, Optional<List<Object>>> valueCache;

    public BufferedParameterEnv(IParameterEnv env) {
        this.env = env;
        this.valueCache = new ArrayMap((o1, o2) -> o1.compareToIgnoreCase((String)o2));
    }

    public IParameterEnv getEnv() {
        return this.env;
    }

    public ParameterResultset getValue(String parameterName) throws ParameterException {
        return this.env.getValue(parameterName);
    }

    public AbstractParameterValue getOriginalValue(String parameterName) throws ParameterException {
        return this.env.getOriginalValue(parameterName);
    }

    public void setValue(String parameterName, List<Object> value) throws ParameterException {
        this.env.setValue(parameterName, value);
    }

    public ParameterModel getParameterModelByName(String parameterName) {
        return this.env.getParameterModelByName(parameterName);
    }

    public boolean addParameterModel(ParameterModel model) throws ParameterException {
        return this.env.addParameterModel(model);
    }

    public String getDimTree(String parameterName) throws ParameterException {
        return this.env.getDimTree(parameterName);
    }

    public void setDimTree(String parameterName, String dimTreeCode) throws ParameterException {
        this.env.setDimTree(parameterName, dimTreeCode);
    }

    public String getUserId() {
        return this.env.getUserId();
    }

    public String getLanguage() {
        return this.env.getLanguage();
    }

    public void setLanguage(String language) {
        this.env.setLanguage(language);
    }

    public List<Object> getValueAsList(String parameterName) throws ParameterException {
        Optional<List<Object>> item = this.valueCache.get(parameterName);
        if (item != null) {
            return item.orElse(null);
        }
        List value = this.env.getValueAsList(parameterName);
        this.valueCache.put(parameterName, Optional.ofNullable(value));
        return value;
    }

    public List<ParameterModel> getParameterModels() {
        return this.env.getParameterModels();
    }

    public String getExtraValue(String key) {
        return this.env.getExtraValue(key);
    }

    public void setExtraValue(String key, String value) {
        this.env.setExtraValue(key, value);
    }

    public IParameterEnv clone() {
        IParameterEnv clonedEnv = this.env.clone();
        return new BufferedParameterEnv(clonedEnv);
    }

    public void initValue(Map<String, List<Object>> valueMap) throws ParameterException {
        this.env.initValue(valueMap);
    }
}

