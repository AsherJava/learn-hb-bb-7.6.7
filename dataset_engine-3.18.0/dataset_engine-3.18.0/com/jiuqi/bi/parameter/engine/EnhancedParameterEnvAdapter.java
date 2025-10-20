/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultItem
 *  com.jiuqi.nvwa.framework.parameter.ParameterResultset
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.SmartSelector
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.SmartSelectorParameterValue
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.engine;

import com.jiuqi.bi.dataset.DSParamUtils;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.ParameterException;
import com.jiuqi.bi.parameter.ParameterScopeType;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.parameter.engine.ParameterEngineEnv;
import com.jiuqi.bi.parameter.engine.ParameterEnv;
import com.jiuqi.bi.parameter.manager.DataSourceAttrBean;
import com.jiuqi.bi.parameter.manager.DataSourceMetaInfo;
import com.jiuqi.bi.parameter.model.SmartSelector;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.nvwa.framework.parameter.ParameterResultItem;
import com.jiuqi.nvwa.framework.parameter.ParameterResultset;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.SmartSelector;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.SmartSelectorParameterValue;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class EnhancedParameterEnvAdapter
implements com.jiuqi.nvwa.framework.parameter.IParameterEnv {
    private IParameterEnv orgParameterEnv;

    public EnhancedParameterEnvAdapter(IParameterEnv parameterEnv) {
        if (parameterEnv == null) {
            try {
                parameterEnv = new ParameterEnv(new ArrayList<com.jiuqi.bi.parameter.model.ParameterModel>(), null);
            }
            catch (ParameterException e) {
                e.printStackTrace();
            }
        }
        this.orgParameterEnv = parameterEnv;
    }

    public IParameterEnv getOriginalParameterEnv() {
        return this.orgParameterEnv;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setValue(String parameterName, List<Object> value, boolean mustOrderValue) throws com.jiuqi.nvwa.framework.parameter.ParameterException {
        try {
            this.orgParameterEnv.setOrderValues(parameterName, mustOrderValue);
            com.jiuqi.bi.parameter.model.ParameterModel p = this.orgParameterEnv.getParameterModelByName(parameterName);
            if (p.isRangeParameter()) {
                if (p._isRangeCloned()) {
                    this.orgParameterEnv.setValue(parameterName, this.prepareRangeValue(p, value.isEmpty() ? null : value.get(0)));
                    return;
                } else {
                    if (value == null || value.size() != 2) throw new com.jiuqi.nvwa.framework.parameter.ParameterException("\u8303\u56f4\u53c2\u6570\u53d6\u503c\u957f\u5ea6\u5fc5\u987b\u4e3a2");
                    this.orgParameterEnv.setValue(parameterName + ".MIN", this.prepareRangeValue(p, value.get(0)));
                    this.orgParameterEnv.setValue(parameterName + ".MAX", this.prepareRangeValue(p, value.get(1)));
                }
                return;
            } else if (p.getDataSourceModel() == null) {
                Object valueObj = null;
                if (!value.isEmpty() && (valueObj = value.get(0)) instanceof SmartSelector) {
                    valueObj = this.prepareSmartSelector(value);
                }
                this.orgParameterEnv.setValue(parameterName, valueObj);
                return;
            } else {
                this.orgParameterEnv.setValue(parameterName, this.prepareSmartSelector(value));
            }
            return;
        }
        catch (ParameterException e) {
            throw new com.jiuqi.nvwa.framework.parameter.ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public void setValue(String parameterName, List<Object> value) throws com.jiuqi.nvwa.framework.parameter.ParameterException {
        super.setValue(parameterName, value, false);
    }

    public void initValue(Map<String, List<Object>> valueMap) throws com.jiuqi.nvwa.framework.parameter.ParameterException {
        try {
            HashMap<String, Object> vMap = new HashMap<String, Object>();
            for (Map.Entry<String, List<Object>> entry : valueMap.entrySet()) {
                String parameterName = entry.getKey();
                com.jiuqi.bi.parameter.model.ParameterModel p = this.orgParameterEnv.getParameterModelByName(parameterName);
                if (p.isRangeParameter()) {
                    List<Object> values = entry.getValue();
                    if (p._isRangeCloned()) {
                        vMap.put(parameterName, this.prepareRangeValue(p, values.isEmpty() ? null : values.get(0)));
                        continue;
                    }
                    if (values != null && values.size() == 2) {
                        vMap.put(parameterName + ".MIN", this.prepareRangeValue(p, values.get(0)));
                        vMap.put(parameterName + ".MAX", this.prepareRangeValue(p, values.get(1)));
                        continue;
                    }
                    throw new com.jiuqi.nvwa.framework.parameter.ParameterException("\u8303\u56f4\u53c2\u6570\u53d6\u503c\u957f\u5ea6\u5fc5\u987b\u4e3a2");
                }
                vMap.put(parameterName, this.prepareSmartSelector(entry.getValue()));
            }
            this.orgParameterEnv.initParameterValue(vMap, false);
        }
        catch (ParameterException e) {
            throw new com.jiuqi.nvwa.framework.parameter.ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public ParameterResultset getValue(String parameterName) throws com.jiuqi.nvwa.framework.parameter.ParameterException {
        try {
            com.jiuqi.bi.parameter.model.ParameterModel p = this.orgParameterEnv.getParameterModelByName(parameterName);
            if (p.isRangeParameter() && !p._isRangeCloned()) {
                Object valueMin = this.orgParameterEnv.getValue(parameterName + ".MIN");
                Object valueMax = this.orgParameterEnv.getValue(parameterName + ".MAX");
                ParameterResultset min = this.convertResult(parameterName, valueMin);
                ParameterResultset max = this.convertResult(parameterName, valueMax);
                ArrayList<ParameterResultItem> items = new ArrayList<ParameterResultItem>();
                items.add(min.isEmpty() ? new ParameterResultItem(null) : min.get(0));
                items.add(max.isEmpty() ? new ParameterResultItem(null) : max.get(0));
                return new ParameterResultset(items);
            }
            Object v = this.orgParameterEnv.getValue(parameterName);
            return this.convertResult(parameterName, v);
        }
        catch (ParameterException e) {
            throw new com.jiuqi.nvwa.framework.parameter.ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public AbstractParameterValue getOriginalValue(String parameterName) throws com.jiuqi.nvwa.framework.parameter.ParameterException {
        boolean cached;
        if (this.orgParameterEnv instanceof ParameterEnv && !(cached = ((ParameterEnv)this.orgParameterEnv).getParameterEngineEnv().valueCached(parameterName))) {
            return null;
        }
        try {
            Object value = this.orgParameterEnv.getValue(parameterName);
            if (value instanceof com.jiuqi.bi.parameter.model.SmartSelector) {
                SmartSelector smartSelector = this.convertSmartSelector((com.jiuqi.bi.parameter.model.SmartSelector)value);
                SmartSelectorParameterValue smartSelectorParameterValue = new SmartSelectorParameterValue(smartSelector);
                smartSelectorParameterValue.setOrdered(this.orgParameterEnv.isOrderValues(parameterName));
                return smartSelectorParameterValue;
            }
            com.jiuqi.bi.parameter.model.ParameterModel p = this.orgParameterEnv.getParameterModelByName(parameterName);
            FixedMemberParameterValue rs = new FixedMemberParameterValue();
            if (p.isRangeParameter() && !p._isRangeCloned()) {
                List<?> valueMin = this.orgParameterEnv.getKeyValueAsList(parameterName + ".MIN");
                List<?> valueMax = this.orgParameterEnv.getKeyValueAsList(parameterName + ".MAX");
                rs.getItems().add(valueMin == null || valueMin.isEmpty() ? null : (Object)valueMin.get(0));
                rs.getItems().add(valueMax == null || valueMax.isEmpty() ? null : (Object)valueMax.get(0));
            } else {
                List<?> v = this.orgParameterEnv.getKeyValueAsList(parameterName);
                rs.getItems().addAll(v);
            }
            rs.setOrdered(this.orgParameterEnv.isOrderValues(parameterName));
            return rs;
        }
        catch (ParameterException e) {
            throw new com.jiuqi.nvwa.framework.parameter.ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    private ParameterResultset convertResult(String parameterName, Object orgResult) throws ParameterException {
        if (orgResult == null) {
            return ParameterResultset.EMPTY_RESULTSET;
        }
        ParameterEngineEnv engineEnv = ((ParameterEnv)this.orgParameterEnv).getParameterEngineEnv();
        Object value = orgResult;
        if (orgResult instanceof com.jiuqi.bi.parameter.model.SmartSelector) {
            value = ((com.jiuqi.bi.parameter.model.SmartSelector)orgResult).getFilterValueInMemory(engineEnv, parameterName);
        }
        ArrayList<ParameterResultItem> items = new ArrayList<ParameterResultItem>();
        if (value instanceof MemoryDataSet) {
            value = engineEnv.tryGetValue(parameterName, (MemoryDataSet)value);
            MemoryDataSet dataSet = (MemoryDataSet)value;
            DataSourceMetaInfo dataSourceMetaInfo = engineEnv.getDataSourceMetaInfo(parameterName);
            DataSourceModel dataSourceModel = engineEnv.getParameterModel(parameterName).getDataSourceModel();
            int cols = dataSet.getMetadata().size();
            if (dataSourceMetaInfo == null || dataSourceModel == null) {
                for (DataRow row : dataSet) {
                    ParameterResultItem item = new ParameterResultItem(row.getValue(0), cols > 1 ? row.getString(1) : null);
                    items.add(item);
                }
            } else if (dataSourceMetaInfo.getAttrBeans().size() > 0) {
                DataSourceAttrBean bean = dataSourceMetaInfo.getAttrBeans().get(0);
                int keyIndex = dataSet.getMetadata().indexOf(bean.getKeyColName());
                if (keyIndex == -1) {
                    keyIndex = 0;
                }
                for (DataRow row : dataSet) {
                    Object key = this.formatResultValue(dataSourceModel, row.getValue(keyIndex));
                    ParameterResultItem item = new ParameterResultItem(key, cols > 1 ? row.getString(1) : null);
                    if (cols > 2) {
                        item.setParent(row.getString(2));
                    }
                    items.add(item);
                }
            }
        } else if (value.getClass().isArray()) {
            Object[] valueArray;
            for (Object object : valueArray = (Object[])value) {
                items.add(new ParameterResultItem(object));
            }
        } else if (orgResult instanceof com.jiuqi.bi.parameter.model.SmartSelector) {
            SmartSelector nss = this.convertSmartSelector((com.jiuqi.bi.parameter.model.SmartSelector)orgResult);
            items.add(new ParameterResultItem((Object)nss));
        } else {
            items.add(new ParameterResultItem(value));
        }
        return new ParameterResultset(items);
    }

    private Object formatResultValue(DataSourceModel dataSourceModel, Object value) {
        if (value == null) {
            return null;
        }
        if (dataSourceModel.getDataType().equals((Object)DataType.DATETIME) && value instanceof String) {
            String valueStr = value.toString();
            int year = Integer.valueOf(valueStr.substring(0, 4));
            int month = Integer.valueOf(valueStr.substring(5, 6)) - 1;
            int day = Integer.valueOf(valueStr.substring(6, 8));
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day, 0, 0, 0);
            return calendar;
        }
        return value;
    }

    public ParameterModel getParameterModelByName(String parameterName) {
        com.jiuqi.bi.parameter.model.ParameterModel orgModel = this.orgParameterEnv.getParameterModelByName(parameterName);
        try {
            return orgModel == null ? null : DSParamUtils.convertParameterModel(orgModel);
        }
        catch (com.jiuqi.nvwa.framework.parameter.ParameterException e) {
            return null;
        }
    }

    public boolean addParameterModel(ParameterModel model) throws com.jiuqi.nvwa.framework.parameter.ParameterException {
        com.jiuqi.bi.parameter.model.ParameterModel org = DSParamUtils.convertParameterModel(model);
        ArrayList<com.jiuqi.bi.parameter.model.ParameterModel> parameterModels = new ArrayList<com.jiuqi.bi.parameter.model.ParameterModel>();
        parameterModels.add(org);
        try {
            this.orgParameterEnv.addParameterModels(parameterModels);
        }
        catch (ParameterException e) {
            throw new com.jiuqi.nvwa.framework.parameter.ParameterException(e.getMessage(), (Throwable)e);
        }
        return true;
    }

    public String getDimTree(String parameterName) throws com.jiuqi.nvwa.framework.parameter.ParameterException {
        try {
            return this.orgParameterEnv.getDimTree(parameterName);
        }
        catch (ParameterException e) {
            throw new com.jiuqi.nvwa.framework.parameter.ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public void setDimTree(String parameterName, String dimTreeCode) throws com.jiuqi.nvwa.framework.parameter.ParameterException {
        try {
            this.orgParameterEnv.setDimTree(parameterName, dimTreeCode);
        }
        catch (ParameterException e) {
            throw new com.jiuqi.nvwa.framework.parameter.ParameterException(e.getMessage(), (Throwable)e);
        }
    }

    public String getUserId() {
        return this.orgParameterEnv.getUserGuid();
    }

    public String getLanguage() {
        return this.orgParameterEnv.getI18nLang();
    }

    public void setLanguage(String language) {
        this.orgParameterEnv.setI18nLang(language);
    }

    public List<ParameterModel> getParameterModels() {
        List<com.jiuqi.bi.parameter.model.ParameterModel> orgModels = this.orgParameterEnv.getParameterModels();
        ArrayList<ParameterModel> models = new ArrayList<ParameterModel>();
        for (com.jiuqi.bi.parameter.model.ParameterModel pm : orgModels) {
            try {
                models.add(DSParamUtils.convertParameterModel(pm));
            }
            catch (com.jiuqi.nvwa.framework.parameter.ParameterException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return models;
    }

    public com.jiuqi.nvwa.framework.parameter.IParameterEnv clone() {
        IParameterEnv env = this.orgParameterEnv.clone();
        return new EnhancedParameterEnvAdapter(env);
    }

    public String getExtraValue(String key) {
        throw new UnsupportedOperationException();
    }

    public void setExtraValue(String key, String value) {
        block3: {
            String[] zbs;
            block2: {
                String[] cubeNames;
                if (!key.equals("scope-cube")) break block2;
                for (String n : cubeNames = value.split(",")) {
                    this.orgParameterEnv.addQueryScope(ParameterScopeType.CUBE, n);
                }
                break block3;
            }
            if (!key.equals("scope-zb")) break block3;
            for (String n : zbs = value.split(",")) {
                this.orgParameterEnv.addQueryScope(ParameterScopeType.ZB, n);
            }
        }
    }

    private Object prepareRangeValue(com.jiuqi.bi.parameter.model.ParameterModel model, Object value) {
        if (model.getDataSourceModel() != null) {
            ArrayList<Object> values = new ArrayList<Object>();
            values.add(value);
            return values;
        }
        return value;
    }

    private Object prepareSmartSelector(List<Object> value) {
        Object values = value;
        if (value != null && value.size() == 1 && value.get(0) instanceof SmartSelector) {
            com.jiuqi.bi.parameter.model.SmartSelector smartSelector = this.convertSmartSelector((SmartSelector)value.get(0));
            values = smartSelector;
        }
        return values;
    }

    private SmartSelector convertSmartSelector(com.jiuqi.bi.parameter.model.SmartSelector model) {
        SmartSelector smartSelector = new SmartSelector();
        JSONObject json = model.toJson();
        json.put("selectedValues", JSONObject.NULL);
        smartSelector.fromJson(json);
        if ("1".equals(smartSelector.getSelectMode())) {
            smartSelector.setSelectMode("fixed");
        } else if ("2".equals(smartSelector.getSelectMode())) {
            smartSelector.setSelectMode("fuzzy");
        } else if ("3".equals(smartSelector.getSelectMode())) {
            smartSelector.setSelectMode("range");
        }
        for (SmartSelector.SelectedValue value : model.getSelectedValues()) {
            smartSelector.getSelectedValues().add(value.value);
        }
        return smartSelector;
    }

    private com.jiuqi.bi.parameter.model.SmartSelector convertSmartSelector(SmartSelector model) {
        com.jiuqi.bi.parameter.model.SmartSelector smartSelector = new com.jiuqi.bi.parameter.model.SmartSelector();
        JSONObject json = new JSONObject();
        model.toJson(json);
        json.put("selectedValues", JSONObject.NULL);
        smartSelector.load(json);
        if ("fixed".equals(model.getSelectMode())) {
            smartSelector.setType(SmartSelector.SelectType.FIXED);
        } else if ("fuzzy".equals(model.getSelectMode())) {
            smartSelector.setType(SmartSelector.SelectType.FUZZY);
        } else if ("range".equals(model.getSelectMode())) {
            smartSelector.setType(SmartSelector.SelectType.RANGE);
        }
        for (Object v : model.getSelectedValues()) {
            SmartSelector.SelectedValue value = new SmartSelector.SelectedValue();
            value.value = v;
            smartSelector.getSelectedValues().add(value);
        }
        return smartSelector;
    }
}

