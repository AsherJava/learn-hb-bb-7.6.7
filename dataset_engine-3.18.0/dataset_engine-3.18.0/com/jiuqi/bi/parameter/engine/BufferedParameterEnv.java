/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.engine;

import com.jiuqi.bi.parameter.ParameterEnvSnapShot;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.ParameterScopeType;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BufferedParameterEnv
implements IParameterEnv {
    private final IParameterEnv env;
    private Map<String, Object> values;
    private Map<String, List<?>> listValues;
    private Map<String, List<?>> keyListValues;

    public BufferedParameterEnv(IParameterEnv env) {
        this.env = env;
        this.values = new HashMap<String, Object>();
        this.listValues = new HashMap();
        this.keyListValues = new HashMap();
    }

    @Override
    public Object getValue(String parameterName) throws ParameterException {
        if (this.values.containsKey(parameterName = parameterName.toUpperCase())) {
            return this.values.get(parameterName);
        }
        Object v = this.env.getValue(parameterName);
        this.values.put(parameterName, v);
        return v;
    }

    @Override
    public List<?> getValueAsList(String parameterName) throws ParameterException {
        if (this.listValues.containsKey(parameterName = parameterName.toUpperCase())) {
            return this.listValues.get(parameterName);
        }
        List<?> v = this.env.getValueAsList(parameterName);
        this.listValues.put(parameterName, v);
        return v;
    }

    @Override
    public List<?> getKeyValueAsList(String parameterName) throws ParameterException {
        if (this.keyListValues.containsKey(parameterName = parameterName.toUpperCase())) {
            return this.keyListValues.get(parameterName);
        }
        List<?> v = this.env.getKeyValueAsList(parameterName);
        this.keyListValues.put(parameterName, v);
        return v;
    }

    @Override
    public void setValue(String parameterName, Object value) throws ParameterException {
        parameterName = parameterName.toUpperCase();
        this.env.setValue(parameterName, value);
        this.invalidate(parameterName);
    }

    private void invalidate(String parameterName) {
        this.values.remove(parameterName);
        this.listValues.remove(parameterName);
        this.keyListValues.remove(parameterName);
    }

    @Override
    public ParameterEnvSnapShot snapShot() throws ParameterException {
        return this.env.snapShot();
    }

    @Override
    public void restore(ParameterEnvSnapShot snapShot) throws ParameterException {
        this.env.restore(snapShot);
        this.invalidate();
    }

    private void invalidate() {
        this.values.clear();
        this.listValues.clear();
        this.keyListValues.clear();
    }

    @Override
    public List<ParameterModel> getParameterModels() {
        return this.env.getParameterModels();
    }

    @Override
    public ParameterModel getParameterModelByName(String parameterName) {
        return this.env.getParameterModelByName(parameterName);
    }

    @Override
    public DataSourceMetaInfo getDataSourceMetaInfo(String parameterName) throws ParameterException {
        return this.env.getDataSourceMetaInfo(parameterName);
    }

    @Override
    public void addParameterModels(List<ParameterModel> parameterModels) throws ParameterException {
        this.env.addParameterModels(parameterModels);
    }

    @Override
    public void removeParameterModels(List<ParameterModel> parameterModels) throws ParameterException {
        this.env.removeParameterModels(parameterModels);
        for (ParameterModel param : parameterModels) {
            this.invalidate(param.getName());
        }
    }

    @Override
    public List<String> getKeyValueAsString(String parameterName) throws ParameterException {
        return this.env.getKeyValueAsString(parameterName);
    }

    @Override
    public List<String> getNameValueAsString(String parameterName) throws ParameterException {
        return this.env.getNameValueAsString(parameterName);
    }

    @Override
    public void setKeyValueAsString(String parameterName, List<String> value) throws ParameterException {
        this.env.setKeyValueAsString(parameterName, value);
        this.invalidate(parameterName);
    }

    @Override
    public Map<String, Object> setKeyValueAsStringAndUpdateCascade(String parameterName, List<String> value) throws ParameterException {
        Map<String, Object> cascadeParams = this.env.setKeyValueAsStringAndUpdateCascade(parameterName, value);
        this.invalidate(parameterName);
        for (String key : cascadeParams.keySet()) {
            this.invalidate(key);
        }
        return cascadeParams;
    }

    @Override
    public boolean containsParameter(String parameterName) {
        return this.env.containsParameter(parameterName);
    }

    @Override
    public boolean containsParameterWithAlias(String parameterAlias) {
        return this.env.containsParameterWithAlias(parameterAlias);
    }

    @Override
    public List<String> getKeyAndNameCol(String parameterName) throws ParameterException {
        return this.env.getKeyAndNameCol(parameterName);
    }

    @Override
    public List<ParameterModel> getParameterModelsByName(String parameterName) throws ParameterException {
        return this.env.getParameterModelsByName(parameterName);
    }

    @Override
    public String getUserGuid() {
        return this.env.getUserGuid();
    }

    @Override
    public void addQueryScope(ParameterScopeType type, String id) {
        this.env.addQueryScope(type, id);
    }

    @Override
    public ParameterModel getParameterWithAlias(String paraAlias) {
        return this.env.getParameterWithAlias(paraAlias);
    }

    @Override
    public List<String> getCascadeParameters(String parameterName) {
        return this.env.getCascadeParameters(parameterName);
    }

    @Override
    public IParameterEnv clone() {
        return new BufferedParameterEnv(this.env.clone());
    }

    @Override
    public void initParameterValue(Map<String, Object> paramValueMap, boolean ignoreExternalParam) throws ParameterException {
        this.env.initParameterValue(paramValueMap, ignoreExternalParam);
    }

    @Override
    public void initGlobalParamCache() throws ParameterException {
    }

    @Override
    public String getDimTree(String parameterName) throws ParameterException {
        return this.env.getDimTree(parameterName);
    }

    @Override
    public void setDimTree(String paraemterName, String dimTreeCode) throws ParameterException {
        this.env.setDimTree(paraemterName, dimTreeCode);
    }

    @Override
    public String getI18nLang() {
        return this.env.getI18nLang();
    }

    @Override
    public void setI18nLang(String language) {
        this.env.setI18nLang(language);
    }
}

