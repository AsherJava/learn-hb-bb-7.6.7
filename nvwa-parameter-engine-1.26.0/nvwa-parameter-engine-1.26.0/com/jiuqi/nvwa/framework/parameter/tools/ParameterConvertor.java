/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.tools;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.ParameterCalculator;
import com.jiuqi.nvwa.framework.parameter.ParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterDataSourceProvider;
import com.jiuqi.nvwa.framework.parameter.datasource.IParameterRenderer;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceContext;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceRangeValues;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.owner.IParameterOwnerProvider;
import com.jiuqi.nvwa.framework.parameter.owner.ParameterOwnerManager;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterResourceIdentify;
import com.jiuqi.nvwa.framework.parameter.storage.ParameterStorageManager;
import com.jiuqi.nvwa.framework.parameter.tools.IEncryptUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class ParameterConvertor {
    private static final String DEFAULT_VALUE_TYPE_VAL = "VAL";
    private static final String DEFAULT_VALUE_TYPE_EXP = "EXP";
    private static final String DEFAULT_VALUE_TYPE_CUS = "CUS";
    private static IEncryptUtils encryptUtils = null;

    public static void setEncryptUtils(IEncryptUtils utils) {
        encryptUtils = utils;
    }

    public static JSONObject toJson(ParameterCalculator paramCalculator, ParameterModel param, boolean hasStorage) throws Exception {
        int widgetType = param.getWidgetType();
        JSONObject json = new JSONObject();
        json.put("name", (Object)param.getName());
        json.put("title", (Object)param.getTitle());
        json.put("titleVisible", param.isTitleVisible());
        json.put("defaultValues", (Object)ParameterConvertor.defaultValues2Json(param));
        json.put("dataType", (Object)ParameterConvertor.dataType2String(param.getDatasource().getDataType()));
        json.put("selectMode", (Object)ParameterConvertor.selectMode2String(param));
        json.put("widgetType", widgetType);
        json.put("isRange", param.isRangeParameter());
        json.put("required", !param.isNullable());
        if (param.getOwner() != null) {
            JSONObject ownerJson = new JSONObject();
            param.getOwner().toJson(ownerJson);
            json.put("owner", (Object)ownerJson);
        }
        List<ParameterDependMember> depends = null;
        if (paramCalculator != null) {
            depends = paramCalculator.getDependParameters(param.getName());
        } else {
            AbstractParameterValueConfig valueConfig = param.getValueConfig();
            depends = valueConfig.getDepends();
        }
        if (depends != null) {
            ArrayList<String> dependNames = new ArrayList<String>();
            for (ParameterDependMember depend : depends) {
                dependNames.add(depend.getParameterName());
            }
            json.put("dependencies", dependNames);
        }
        json.put("showSearchWidget", param.isShowSearchWidget());
        json.put("hidden", param.isHidden());
        json.put("showCode", param.isShowCode());
        json.put("switchShowCode", param.isSwitchShowCode());
        json.put("onlyLeafSelectable", param.isOnlyLeafSelectable());
        json.put("crossLeafEnable", param.isCrossLeafEnable());
        JSONObject style = new JSONObject();
        style.put("widgetWidth", param.getWidth() < 1 ? 150 : param.getWidth());
        if (widgetType == 7) {
            style.put("widthAuto", param.getWidth() < 1);
        }
        if (widgetType == 41 && param.getWidth() < 1) {
            style.put("widgetWidth", 260);
        }
        json.put("style", (Object)style);
        json.put("paramType", (Object)param.getDatasource().getType());
        AbstractParameterDataSourceModel datasource = param.getDatasource();
        String datasourceType = datasource.getType();
        AbstractParameterDataSourceFactory factory = ParameterDataSourceManager.getInstance().getFactory(datasourceType);
        if (factory != null) {
            String pluginName;
            IParameterRenderer renderer = factory.createParameterRenderer();
            if (widgetType == ParameterWidgetType.DEFAULT.value()) {
                int def = renderer.getDefaultWidgetType(datasource, param.getSelectMode());
                json.put("widgetType", def);
                widgetType = def;
            }
            json.put("pluginName", (Object)((pluginName = renderer.getRendererPluginName(widgetType)) != null ? pluginName : "BASE"));
            JSONObject renderExtData = renderer.getRendererExtData(datasource, widgetType);
            if (renderExtData != null) {
                json.put("renderExtData", (Object)renderExtData);
            }
            if (datasourceType.equals("com.jiuqi.publicparam.datasource.dimension") || datasourceType.equals("com.jiuqi.publicparam.datasource.date") || datasourceType.equals("com.jiuqi.publicparam.datasource.caliber")) {
                JSONObject datasourceJson = new JSONObject();
                datasource.toJson(datasourceJson);
                json.put("ext", (Object)datasourceJson);
                if (datasourceType.equals("com.jiuqi.publicparam.datasource.date")) {
                    json.put("periodStartEnd", (Object)datasourceJson.optString("periodStartEnd"));
                    json.put("periodType", (Object)datasourceJson.optString("periodType"));
                }
            } else if (!(widgetType != ParameterWidgetType.DATEPICKER.value() && widgetType != ParameterWidgetType.DATEPICKER_RANGE.value() || datasourceType.equals("com.jiuqi.bi.datasource.sql") || datasourceType.equals("com.jiuqi.bi.datasource.dimhier"))) {
                int periodType = 6;
                if (datasource.getTimegranularity() == 0) {
                    periodType = 1;
                } else if (datasource.getTimegranularity() == 1) {
                    periodType = 2;
                } else if (datasource.getTimegranularity() == 2) {
                    periodType = 3;
                } else if (datasource.getTimegranularity() == 3) {
                    periodType = 4;
                } else if (datasource.getTimegranularity() == 4) {
                    periodType = 5;
                } else if (datasource.getTimegranularity() == 6) {
                    periodType = 7;
                }
                json.put("periodType", periodType);
                IParameterDataSourceProvider parameterDataSourceProvider = factory.create(param.getDatasource());
                ParameterDataSourceRangeValues dataSourceCandidateRange = parameterDataSourceProvider.getDataSourceCandidateRange(new ParameterDataSourceContext(param, paramCalculator));
                String periodStartEnd = "";
                if (dataSourceCandidateRange != null) {
                    if (StringUtils.isNotEmpty((String)dataSourceCandidateRange.min)) {
                        periodStartEnd = periodStartEnd + dataSourceCandidateRange.min;
                    }
                    if (StringUtils.isNotEmpty((String)dataSourceCandidateRange.max)) {
                        periodStartEnd = periodStartEnd + "-" + dataSourceCandidateRange.max;
                    }
                }
                json.put("periodStartEnd", (Object)periodStartEnd);
            }
        }
        if (!hasStorage) {
            JSONObject json_model = new JSONObject();
            if (param.getDatasource() instanceof SQLDataSourceModel) {
                SQLDataSourceModel dataSourceModel = (SQLDataSourceModel)param.getDatasource();
                if (encryptUtils != null) {
                    if (StringUtils.isNotEmpty((String)dataSourceModel.getSql())) {
                        dataSourceModel.setSql(encryptUtils.encrypt(dataSourceModel.getSql()));
                    }
                } else {
                    dataSourceModel.setSql("");
                }
            }
            param.toJson(json_model);
            json.put("_model_", (Object)json_model);
        }
        return json;
    }

    public static ParameterModel fromJson(JSONObject json) throws Exception {
        ParameterModel param = null;
        if (json.has("_model_")) {
            JSONObject json_model = json.optJSONObject("_model_");
            param = new ParameterModel();
            param.fromJson(json_model);
            if (param.getDatasource() instanceof SQLDataSourceModel) {
                if (encryptUtils != null) {
                    SQLDataSourceModel dataSourceModel = (SQLDataSourceModel)param.getDatasource();
                    if (StringUtils.isNotEmpty((String)dataSourceModel.getSql())) {
                        dataSourceModel.setSql(encryptUtils.decrypt(dataSourceModel.getSql()));
                    }
                } else {
                    ParameterResourceIdentify paramIdentify = null;
                    if (param.getOwner() != null && StringUtils.isNotEmpty((String)param.getOwner().getType())) {
                        IParameterOwnerProvider ownerProvider = ParameterOwnerManager.getInstance().getOwnerProvider(param.getOwner().getType());
                        if (ownerProvider != null) {
                            ParameterModel model = ownerProvider.findModel(param.getName(), param.getOwner());
                            if (model != null) {
                                return model;
                            }
                            return param;
                        }
                        paramIdentify = new ParameterResourceIdentify(param.getName(), param.getOwner().getName(), param.getOwner().getType());
                    } else {
                        paramIdentify = new ParameterResourceIdentify(param.getName());
                    }
                    param = ParameterStorageManager.getInstance().findModel(paramIdentify, json.optString("storageType"));
                }
            }
        } else {
            ParameterResourceIdentify paramIdentify = new ParameterResourceIdentify(json.optString("name"));
            param = ParameterStorageManager.getInstance().findModel(paramIdentify, json.optString("storageType"));
        }
        return param;
    }

    private static JSONObject defaultValues2Json(ParameterModel p) throws Exception {
        JSONObject json_defaultValues = new JSONObject();
        json_defaultValues.put("type", (Object)ParameterConvertor.defaultValueType2String(p));
        json_defaultValues.put("data", JSONObject.NULL);
        if ("com.jiuqi.nvwa.parameter.ds.none".equals(p.getDatasource().getType()) && p.getValueConfig().getDefaultValueMode() != "none") {
            ArrayList<ParameterModel> pList = new ArrayList<ParameterModel>();
            pList.add(p);
            ParameterEnv env = new ParameterEnv(null, pList);
            List<Object> values = env.getValueAsList(p.getName());
            IParameterValueFormat format = ParameterUtils.createValueFormat(p.getDatasource());
            ArrayList<String> list = new ArrayList<String>(values.size());
            for (Object value : values) {
                list.add(format.format(value));
            }
            json_defaultValues.put("data", list);
            if (p.isRangeParameter() && !values.isEmpty()) {
                values.set(1, values.get(0));
            }
        }
        return json_defaultValues;
    }

    private static String defaultValueType2String(ParameterModel p) {
        if (p.getDatasource() == null) {
            return DEFAULT_VALUE_TYPE_VAL;
        }
        if (p.getValueConfig().getCandidateMode() == ParameterCandidateValueMode.EXPRESSION) {
            return DEFAULT_VALUE_TYPE_EXP;
        }
        return DEFAULT_VALUE_TYPE_CUS;
    }

    private static String dataType2String(int dataType) {
        switch (dataType) {
            case 1: {
                return "BOOLEAN";
            }
            case 2: {
                return "DATE";
            }
            case 3: 
            case 10: {
                return "FLOAT";
            }
            case 5: {
                return "INT";
            }
            case 6: {
                return "TEXT";
            }
        }
        return "UNKNOWN";
    }

    private static String selectMode2String(ParameterModel p) {
        if (p.isRangeParameter()) {
            return "RANGE";
        }
        ParameterSelectMode selectMode = p.getSelectMode();
        if (selectMode == null) {
            return null;
        }
        switch (selectMode) {
            case SINGLE: {
                return "SINGLE";
            }
            case MUTIPLE: {
                return "MULTI";
            }
        }
        return null;
    }
}

