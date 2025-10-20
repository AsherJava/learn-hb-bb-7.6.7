/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter.engine;

import com.jiuqi.bi.parameter.ParameterEnvSnapShot;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.ParameterScopeType;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import java.util.List;
import java.util.Map;

public interface IParameterEnv {
    public Object getValue(String var1) throws ParameterException;

    public List<?> getValueAsList(String var1) throws ParameterException;

    public List<?> getKeyValueAsList(String var1) throws ParameterException;

    public void setValue(String var1, Object var2) throws ParameterException;

    public ParameterEnvSnapShot snapShot() throws ParameterException;

    public void restore(ParameterEnvSnapShot var1) throws ParameterException;

    public List<ParameterModel> getParameterModels();

    public ParameterModel getParameterModelByName(String var1);

    public DataSourceMetaInfo getDataSourceMetaInfo(String var1) throws ParameterException;

    public void addParameterModels(List<ParameterModel> var1) throws ParameterException;

    public void removeParameterModels(List<ParameterModel> var1) throws ParameterException;

    public List<String> getKeyValueAsString(String var1) throws ParameterException;

    public List<String> getNameValueAsString(String var1) throws ParameterException;

    public void setKeyValueAsString(String var1, List<String> var2) throws ParameterException;

    public Map<String, Object> setKeyValueAsStringAndUpdateCascade(String var1, List<String> var2) throws ParameterException;

    public void initParameterValue(Map<String, Object> var1, boolean var2) throws ParameterException;

    public boolean containsParameter(String var1);

    public boolean containsParameterWithAlias(String var1);

    public List<String> getKeyAndNameCol(String var1) throws ParameterException;

    public List<ParameterModel> getParameterModelsByName(String var1) throws ParameterException;

    public String getUserGuid();

    public String getI18nLang();

    public void setI18nLang(String var1);

    public IParameterEnv clone();

    public void addQueryScope(ParameterScopeType var1, String var2);

    public ParameterModel getParameterWithAlias(String var1);

    public List<String> getCascadeParameters(String var1);

    public void initGlobalParamCache() throws ParameterException;

    public String getDimTree(String var1) throws ParameterException;

    public void setDimTree(String var1, String var2) throws ParameterException;

    default public boolean isOrderValues(String parameterName) throws ParameterException {
        return false;
    }

    default public void setOrderValues(String parameterName, boolean orderValues) throws ParameterException {
    }

    default public boolean isOrderValuesEnable() {
        return false;
    }

    default public void setOrderValuesEnable(boolean order) throws ParameterException {
    }
}

