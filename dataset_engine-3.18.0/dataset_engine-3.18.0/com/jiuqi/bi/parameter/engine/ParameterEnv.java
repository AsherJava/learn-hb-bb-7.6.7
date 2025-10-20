/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.parameter.engine;

import com.jiuqi.bi.parameter.ParameterEnvSnapShot;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.ParameterScopeType;
import com.jiuqi.bi.parameter.engine.GlobalParamCacheManager;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.engine.IUIParameterEnv;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.model.ParameterModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParameterEnv
implements IParameterEnv,
IUIParameterEnv {
    private ParameterEngineEnv engineEnv;

    public ParameterEnv(List<ParameterModel> parameterModels, String userGuid) throws ParameterException {
        this.engineEnv = new ParameterEngineEnv(parameterModels, userGuid);
        this.initGlobalParamCache();
    }

    public ParameterEnv(List<ParameterModel> parameterModels, String userGuid, boolean withOutCascadeRelation) throws ParameterException {
        this.engineEnv = new ParameterEngineEnv(parameterModels, userGuid, withOutCascadeRelation);
        this.initGlobalParamCache();
    }

    public ParameterEnv(ParameterEngineEnv env) {
        this.engineEnv = env;
    }

    @Override
    public Object getValue(String parameterName) throws ParameterException {
        return this.engineEnv.getValue(parameterName);
    }

    @Override
    public List<ParameterModel> getParameterModels() {
        return this.engineEnv.getParameterModels();
    }

    @Override
    public ParameterModel getParameterModelByName(String parameterName) {
        ParameterModel parameterModel = this.engineEnv.getParameterModel(parameterName);
        if (parameterModel != null) {
            return parameterModel;
        }
        ParameterModel minParameterModel = this.engineEnv.getParameterModel(parameterName + ".MIN");
        if (minParameterModel != null) {
            return minParameterModel;
        }
        return this.engineEnv.getParameterModel(parameterName + ".MAX");
    }

    @Override
    public DataSourceMetaInfo getDataSourceMetaInfo(String parameterName) throws ParameterException {
        return this.engineEnv.getDataSourceMetaInfo(parameterName);
    }

    public ParameterEngineEnv getParameterEngineEnv() {
        return this.engineEnv;
    }

    @Override
    public String getUserGuid() {
        return this.engineEnv.getUserGuid();
    }

    @Override
    public List<?> getValueAsList(String parameterName) throws ParameterException {
        return this.engineEnv.getValueAsList(parameterName);
    }

    @Override
    public List<?> getKeyValueAsList(String parameterName) throws ParameterException {
        return this.engineEnv.getKeyValueAsList(parameterName);
    }

    @Override
    public void setValue(String parameterName, Object value) throws ParameterException {
        this.engineEnv.setValue(parameterName, value);
    }

    @Override
    public IParameterEnv clone() {
        return new ParameterEnv(this.engineEnv.clone());
    }

    @Override
    public void addParameterModels(List<ParameterModel> parameterModels) throws ParameterException {
        this.engineEnv.addParameterModels(parameterModels);
    }

    @Override
    public boolean containsParameter(String parameterName) {
        return this.engineEnv.containsParameter(parameterName);
    }

    @Override
    public void removeParameterModels(List<ParameterModel> parameterModels) throws ParameterException {
        this.engineEnv.removeParameterModels(parameterModels);
    }

    @Override
    public void restore(ParameterEnvSnapShot snapShot) throws ParameterException {
        this.engineEnv.restore(snapShot);
    }

    @Override
    public ParameterEnvSnapShot snapShot() throws ParameterException {
        return this.engineEnv.snapShot();
    }

    @Override
    public List<String> getKeyValueAsString(String parameterName) throws ParameterException {
        return this.engineEnv.getKeyValueAsString(parameterName);
    }

    @Override
    public List<String> getNameValueAsString(String parameterName) throws ParameterException {
        return this.engineEnv.getNameValueAsString(parameterName);
    }

    @Override
    public void setKeyValueAsString(String parameterName, List<String> value) throws ParameterException {
        this.engineEnv.setValueAsString(parameterName, value);
    }

    @Override
    public Map<String, Object> setKeyValueAsStringAndUpdateCascade(String parameterName, List<String> value) throws ParameterException {
        return this.engineEnv.setKeyValueAsStringAndUpdateCascade(parameterName, value);
    }

    @Override
    public List<ParameterModel> getParameterModelsByName(String parameterName) throws ParameterException {
        return this.engineEnv.getParameterModelsByName(parameterName);
    }

    @Override
    public List<String> getKeyAndNameCol(String parameterName) throws ParameterException {
        return this.engineEnv.getKeyAndNameCol(parameterName);
    }

    @Override
    public void addQueryScope(ParameterScopeType type, String id) {
        this.engineEnv.addQueryScope(type, id);
    }

    @Override
    public boolean containsParameterWithAlias(String parameterAlias) {
        if (StringUtils.isEmpty((String)parameterAlias)) {
            return false;
        }
        for (ParameterModel model : this.engineEnv.getParameterModels()) {
            if (model.getAlias() == null || !model.getAlias().equalsIgnoreCase(parameterAlias)) continue;
            return true;
        }
        return false;
    }

    @Override
    public ParameterModel getParameterWithAlias(String paraAlias) {
        if (StringUtils.isEmpty((String)paraAlias)) {
            return null;
        }
        for (ParameterModel model : this.engineEnv.getParameterModels()) {
            if (model.getAlias() == null || !model.getAlias().equalsIgnoreCase(paraAlias)) continue;
            return model;
        }
        return null;
    }

    @Override
    public List<String> getCascadeParameters(String parameterName) {
        return this.engineEnv.getCascadeParameters(parameterName);
    }

    @Override
    public void initParameterValue(Map<String, Object> paramValueMap, boolean ignoreExternalParam) throws ParameterException {
        this.engineEnv.initParameterValue(paramValueMap, ignoreExternalParam);
    }

    @Override
    public Object getValueForUI(String parameterName) throws ParameterException {
        return this.engineEnv.getValueForUI(parameterName);
    }

    @Override
    public void initGlobalParamCache() throws ParameterException {
        IParameterEnv globalParamEnv;
        Set<String> globalCacheParamNames = GlobalParamCacheManager.getInstance().getGlobalCacheParamNames();
        if (globalCacheParamNames.isEmpty()) {
            return;
        }
        String userGuid = this.getUserGuid();
        if (userGuid != null && (globalParamEnv = GlobalParamCacheManager.getInstance().getGlobalParameterEnv(userGuid)) != null) {
            HashMap<String, Object> paramValueMap = new HashMap<String, Object>();
            for (ParameterModel param : globalParamEnv.getParameterModels()) {
                String paramName = param.getName();
                DataSourceModel dsm = param.getDataSourceModel();
                ParameterModel currModel = this.getParameterModelByName(paramName);
                if (currModel == null) continue;
                DataSourceModel currDsm = currModel.getDataSourceModel();
                if (currModel.isPublic() && currModel.getDataType() == param.getDataType() && (dsm == null && currDsm == null || dsm != null && dsm.getType().equals(currDsm.getType()))) {
                    paramValueMap.put(paramName, globalParamEnv.getValue(paramName));
                }
                this.engineEnv.submitValue(paramName, globalParamEnv.getValue(paramName));
            }
        }
    }

    @Override
    public String getDimTree(String parameterName) throws ParameterException {
        return this.engineEnv.getDimTree(parameterName);
    }

    @Override
    public void setDimTree(String paraemterName, String dimTreeCode) throws ParameterException {
        this.engineEnv.setDimTree(paraemterName, dimTreeCode);
    }

    @Override
    public String getI18nLang() {
        return this.engineEnv.getI18nLang();
    }

    @Override
    public void setI18nLang(String language) {
        this.engineEnv.setI18nLang(language);
    }

    @Override
    public boolean isOrderValues(String parameterName) throws ParameterException {
        return this.engineEnv.isOrderValues(parameterName);
    }

    @Override
    public void setOrderValues(String parameterName, boolean orderValues) throws ParameterException {
        this.engineEnv.setOrderValues(parameterName, orderValues);
    }

    @Override
    public boolean isOrderValuesEnable() {
        return this.engineEnv.isOrderValuesEnable();
    }

    @Override
    public void setOrderValuesEnable(boolean order) throws ParameterException {
        this.engineEnv.setOrderValuesEnable(order);
    }
}

