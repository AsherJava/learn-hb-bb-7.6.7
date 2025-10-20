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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NullParameterEnv
implements IParameterEnv {
    private String userID;
    private List<ParameterModel> models;

    public NullParameterEnv(String userID) {
        this.userID = userID;
        this.models = Arrays.asList(new ParameterModel[0]);
    }

    @Override
    public Object getValue(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public List<?> getValueAsList(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public void setValue(String parameterName, Object value) throws ParameterException {
    }

    @Override
    public List<ParameterModel> getParameterModels() {
        return this.models;
    }

    @Override
    public ParameterModel getParameterModelByName(String parameterName) {
        return null;
    }

    @Override
    public DataSourceMetaInfo getDataSourceMetaInfo(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public String getUserGuid() {
        return this.userID;
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
    public void addParameterModels(List<ParameterModel> parameterModels) throws ParameterException {
    }

    @Override
    public boolean containsParameter(String parameterName) {
        return false;
    }

    @Override
    public void removeParameterModels(List<ParameterModel> parameterModels) throws ParameterException {
    }

    @Override
    public void restore(ParameterEnvSnapShot snapShot) throws ParameterException {
    }

    @Override
    public ParameterEnvSnapShot snapShot() throws ParameterException {
        return null;
    }

    @Override
    public List<?> getKeyValueAsList(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public List<String> getKeyValueAsString(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public List<String> getNameValueAsString(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public void setKeyValueAsString(String parameterName, List<String> value) throws ParameterException {
    }

    @Override
    public Map<String, Object> setKeyValueAsStringAndUpdateCascade(String parameterName, List<String> value) throws ParameterException {
        return new HashMap<String, Object>();
    }

    @Override
    public List<ParameterModel> getParameterModelsByName(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public List<String> getKeyAndNameCol(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public void addQueryScope(ParameterScopeType type, String id) {
    }

    @Override
    public boolean containsParameterWithAlias(String parameterAlias) {
        return false;
    }

    @Override
    public ParameterModel getParameterWithAlias(String paraAlias) {
        return null;
    }

    @Override
    public List<String> getCascadeParameters(String parameterName) {
        return null;
    }

    @Override
    public void initParameterValue(Map<String, Object> paramValueMap, boolean ignoreExternalParam) throws ParameterException {
    }

    @Override
    public void initGlobalParamCache() throws ParameterException {
    }

    @Override
    public String getDimTree(String parameterName) throws ParameterException {
        return null;
    }

    @Override
    public void setDimTree(String paraemterName, String dimTreeCode) throws ParameterException {
    }

    @Override
    public String getI18nLang() {
        return null;
    }

    @Override
    public void setI18nLang(String language) {
    }
}

