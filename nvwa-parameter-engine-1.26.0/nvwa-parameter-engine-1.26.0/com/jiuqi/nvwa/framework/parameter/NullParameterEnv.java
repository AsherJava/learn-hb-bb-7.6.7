/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter;

import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NullParameterEnv
implements IParameterEnv {
    private String userId;
    private List<ParameterModel> models;

    public NullParameterEnv(String userId) {
        this.userId = userId;
        this.models = new ArrayList<ParameterModel>();
    }

    @Override
    public ParameterResultset getValue(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public AbstractParameterValue getOriginalValue(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public void setValue(String parameterName, List<Object> value) throws ParameterException {
    }

    @Override
    public ParameterModel getParameterModelByName(String parameterName) {
        return null;
    }

    @Override
    public List<ParameterModel> getParameterModels() {
        return this.models;
    }

    @Override
    public boolean addParameterModel(ParameterModel model) throws ParameterException {
        return this.models.add(model);
    }

    @Override
    public String getDimTree(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public void setDimTree(String parameterName, String dimTreeCode) throws ParameterException {
    }

    @Override
    public String getExtraValue(String key) {
        return null;
    }

    @Override
    public void setExtraValue(String key, String value) {
    }

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public String getLanguage() {
        return null;
    }

    @Override
    public void setLanguage(String language) {
    }

    @Override
    public IParameterEnv clone() {
        try {
            return (IParameterEnv)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initValue(Map<String, List<Object>> valueMap) throws ParameterException {
    }
}

