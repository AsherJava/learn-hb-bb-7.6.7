/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.ParameterException
 *  com.jiuqi.nvwa.framework.parameter.ParameterUtils
 *  com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel$FixedMemberItem
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.DataBusinessType
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterLevelMemberCheckMode
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.SmartSelector
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterRangeValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig
 *  com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue
 *  com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat
 *  com.jiuqi.nvwa.framework.parameter.model.value.SmartSelectorParameterValue
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.dataset;

import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.bi.dataset.parameter.ParameterImportOption;
import com.jiuqi.bi.parameter.extend.customlist.CustomListDataSourceModel;
import com.jiuqi.bi.parameter.extend.sql.SQLDataSourceHierarchyType;
import com.jiuqi.bi.parameter.extend.zb.ZbParameterDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceFactoryManager;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.parameter.model.NoneDsDefValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterDefaultValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterDimType;
import com.jiuqi.bi.parameter.model.ParameterHierarchyType;
import com.jiuqi.bi.parameter.model.ParameterWidgetType;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceValueModel;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.ParameterUtils;
import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceHierarchyType;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.NonDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.datasource.extend.SQLDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.DataBusinessType;
import com.jiuqi.nvwa.framework.parameter.model.ParameterCandidateValueMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterLevelMemberCheckMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.SmartSelector;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterRangeValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.value.AbstractParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.ExpressionParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.FixedMemberParameterValue;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import com.jiuqi.nvwa.framework.parameter.model.value.SmartSelectorParameterValue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DSParamUtils {
    public static final String TAG_DSPARAMINFO = "publicParamInfo";
    public static final String TAG_DSNAME = "dsName";
    public static final String TAG_PUBLIC_PARAMETER = "publicParameter";
    public static final String TAG_OPTIONS = "options";
    public static final String TAG_PARAMETER_NAME = "parameterName";
    public static final String TAG_OPTION = "option";
    private static final List<String> defaultValueFilters = new ArrayList<String>();

    public static Map<String, ParameterImportOption> getParameterImportOpts(JSONObject jsonObject) throws JSONException {
        HashMap<String, ParameterImportOption> options = new HashMap<String, ParameterImportOption>();
        if (jsonObject.isNull(TAG_PUBLIC_PARAMETER)) {
            return options;
        }
        JSONObject publicParameter = jsonObject.getJSONObject(TAG_PUBLIC_PARAMETER);
        JSONArray optionJson = publicParameter.getJSONArray(TAG_OPTIONS);
        for (int i = 0; i < optionJson.length(); ++i) {
            JSONObject optObj = optionJson.getJSONObject(i);
            String pName = optObj.getString(TAG_PARAMETER_NAME);
            ParameterImportOption opt = new ParameterImportOption();
            JSONObject optStr = (JSONObject)optObj.get(TAG_OPTION);
            opt.fromJson(optStr);
            options.put(pName.toUpperCase(), opt);
        }
        return options;
    }

    public static void makeParameterImportOptsToJSON(Map<String, ParameterImportOption> opts, JSONObject jsonObj) throws JSONException {
        if (opts == null || opts.size() == 0) {
            return;
        }
        JSONObject publicParameter = new JSONObject();
        JSONArray optionJson = new JSONArray();
        for (Map.Entry<String, ParameterImportOption> entry : opts.entrySet()) {
            JSONObject optObj = new JSONObject();
            optObj.put(TAG_PARAMETER_NAME, (Object)entry.getKey());
            JSONObject opt = new JSONObject();
            entry.getValue().toJson(opt);
            optObj.put(TAG_OPTION, (Object)opt);
            optionJson.put((Object)optObj);
        }
        publicParameter.put(TAG_OPTIONS, (Object)optionJson);
        jsonObj.put(TAG_PUBLIC_PARAMETER, (Object)publicParameter);
    }

    public static ParameterModel parseOrginParameterJson(JSONObject json) throws ParameterException {
        com.jiuqi.bi.parameter.model.ParameterModel model = new com.jiuqi.bi.parameter.model.ParameterModel();
        try {
            model.load(json);
        }
        catch (Exception e) {
            throw new ParameterException("\u52a0\u8f7d\u53c2\u6570\u6a21\u578b\u6570\u636e\u51fa\u9519\uff1a" + e.getMessage(), (Throwable)e);
        }
        return DSParamUtils.convertParameterModel(model);
    }

    public static ParameterModel convertParameterModel(com.jiuqi.bi.parameter.model.ParameterModel model) throws ParameterException {
        String dataSourceFilter;
        IParameterValueFormat format;
        ParameterValueConfig valueCfg;
        CustomListDataSourceModel.FixedMemberItem item;
        NonDataSourceModel datasource;
        if (model == null) {
            return null;
        }
        ParameterModel target = new ParameterModel();
        target.setGuid(model._getGuid());
        target.setGlobal(model.isPublic());
        target.setMessageAlias(model.getAlias());
        if (model.isCascadeDirectChildren()) {
            target.setLevelCheckMode(ParameterLevelMemberCheckMode.DIRECT_LEVEL);
        }
        if (model.isCascadeAllChildren()) {
            target.setLevelCheckMode(ParameterLevelMemberCheckMode.ALL_SUB_LEVEL);
        }
        target.setCrossLeafEnable(model.isCrossLeafEnable());
        target.setTitleVisible(model.isTitleVisible());
        target.setHidden(model.isHidden());
        target.setName(model.getName());
        target.setNullable(model.isNullable());
        target.setOnlyLeafSelectable(model.isOnlyLeafSelectable());
        target.setOrderReverse(model.isOrderReverse());
        if (model.isRangeParameter()) {
            target.setSelectMode(ParameterSelectMode.RANGE);
        } else {
            target.setSelectMode(ParameterSelectMode.valueOf((int)model.getSelectMode().value()));
        }
        target.setShowCode(model.isShowCode());
        target.setSwitchShowCode(model.isSwitchShowCode());
        target.setShowSearchWidget(model.isShowSearchWidget());
        target.setWidth(model.getWidth());
        target.setTitle(model.getTitle());
        target.setWidgetType(model.getWidgetType().value());
        target.setDescription(model.getDesc());
        DataSourceModel orgDatasource = model.getDataSourceModel();
        if (orgDatasource == null) {
            datasource = new NonDataSourceModel(model.getDataType().value());
        } else if (orgDatasource instanceof ZbParameterDataSourceModel) {
            datasource = new com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel();
            ((com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel)datasource).setTreatAsZB(true);
            ((com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel)datasource).setDataType(orgDatasource.getDataType().value());
            ZbParameterDataSourceModel zbm = (ZbParameterDataSourceModel)orgDatasource;
            List<DataSourceValueModel> zbvs = zbm.getValues();
            for (DataSourceValueModel m : zbvs) {
                item = new CustomListDataSourceModel.FixedMemberItem(m.getCode(), m.getName());
                ((com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel)datasource).getItems().add(item);
            }
        } else if (orgDatasource instanceof CustomListDataSourceModel) {
            datasource = new com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel();
            ((com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel)datasource).setTreatAsZB(false);
            ((com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel)datasource).setDataType(orgDatasource.getDataType().value());
            CustomListDataSourceModel customListDataSourceModel = (CustomListDataSourceModel)orgDatasource;
            List<DataSourceValueModel> customList = customListDataSourceModel.getValues();
            for (DataSourceValueModel m : customList) {
                item = new CustomListDataSourceModel.FixedMemberItem(m.getCode(), m.getName());
                ((com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel)datasource).getItems().add(item);
            }
        } else if (orgDatasource instanceof com.jiuqi.bi.parameter.extend.sql.SQLDataSourceModel) {
            com.jiuqi.bi.parameter.extend.sql.SQLDataSourceModel org = (com.jiuqi.bi.parameter.extend.sql.SQLDataSourceModel)orgDatasource;
            datasource = new SQLDataSourceModel();
            ((SQLDataSourceModel)datasource).setDataType(org.getDataType().value());
            ((SQLDataSourceModel)datasource).setDatasourceId(org.getDataSouce());
            ((SQLDataSourceModel)datasource).setSql(org.getExpression());
            ((SQLDataSourceModel)datasource).setStructureCode(org.getStructureCode());
            ((SQLDataSourceModel)datasource).setTimekey(org.isTimekey());
            ((SQLDataSourceModel)datasource).setBusinessType(DataBusinessType.valueOf((int)org.getDimType().value()));
            if (org.getTimegranularity() != null) {
                ((SQLDataSourceModel)datasource).setTimegranularity(org.getTimegranularity().value());
            }
            ParameterDataSourceHierarchyType type = ParameterDataSourceHierarchyType.NONE;
            if (org.getHireachyType() == ParameterHierarchyType.PARENT_SON) {
                type = ParameterDataSourceHierarchyType.PARENTMODE;
            } else if (org.getHireachyType() == ParameterHierarchyType.STRUCTURECODE) {
                type = ParameterDataSourceHierarchyType.STRUCTURECODE;
            }
            ((SQLDataSourceModel)datasource).setHierarchyType(type);
        } else {
            String datasourceType = orgDatasource.getType();
            AbstractParameterDataSourceFactory datasourceFactory = ParameterDataSourceManager.getInstance().getFactory(datasourceType);
            if (datasourceFactory == null) {
                throw new ParameterException("\u7cfb\u7edf\u672a\u6ce8\u518c\u53c2\u6570\u6765\u6e90\u63d0\u4f9b\u5668\uff1a" + datasourceType);
            }
            datasource = datasourceFactory.newInstance();
            JSONObject json = new JSONObject();
            orgDatasource.save(json);
            datasource.fromJson(json);
            ParameterHierarchyType htype = orgDatasource.getHireachyType();
            if (htype == ParameterHierarchyType.NONE) {
                datasource.setHierarchyType(ParameterDataSourceHierarchyType.NONE);
            } else if (htype == ParameterHierarchyType.NORMAL) {
                datasource.setHierarchyType(ParameterDataSourceHierarchyType.NORMAL);
            } else if (htype == ParameterHierarchyType.PARENT_SON) {
                datasource.setHierarchyType(ParameterDataSourceHierarchyType.PARENTMODE);
            } else if (htype == ParameterHierarchyType.STRUCTURECODE) {
                datasource.setHierarchyType(ParameterDataSourceHierarchyType.STRUCTURECODE);
            }
        }
        if (orgDatasource != null) {
            datasource.setCanOrder(orgDatasource.isOrderEnable());
        }
        target.setDatasource((AbstractParameterDataSourceModel)datasource);
        if (model.isRangeParameter()) {
            ParameterRangeValueConfig cfg = new ParameterRangeValueConfig();
            valueCfg = cfg;
            cfg.setDefaultValue((AbstractParameterValue)new FixedMemberParameterValue(model.getDefaultValues()));
            cfg.setDefaultMaxValue((AbstractParameterValue)new FixedMemberParameterValue(model.getDefaultMaxValues()));
            if (model.getDataSourceModel() == null) {
                cfg.setDefaultValueMode(DSParamUtils.getValueMode(model.getNoneDsDefValueFilterMode()));
                cfg.setDefaultMaxValueMode(DSParamUtils.getValueMode(model.getNoneDsDefMaxValueFilterMode()));
            } else {
                cfg.setDefaultValueMode(DSParamUtils.getValueMode(model.getDefaultValueFilterMode(), model.getDefaultValueFilterModeExtend()));
                cfg.setDefaultMaxValueMode(DSParamUtils.getValueMode(model.getDefaultMaxValueFilterMode(), model.getDefaultMaxValueFilterModeExtend()));
            }
            format = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource);
            ExpressionParameterValue defaultMinValue = null;
            if (cfg.getDefaultValueMode().equals("expr")) {
                defaultMinValue = new ExpressionParameterValue(model.getDefalutValueFilter());
            } else if (valueCfg.getDefaultValueMode().equals("appoint")) {
                defaultMinValue = DSParamUtils.getAppointValue(model.getDefaultValues(), format);
            }
            cfg.setDefaultValue((AbstractParameterValue)defaultMinValue);
            ExpressionParameterValue defaultMaxValue = null;
            if (cfg.getDefaultMaxValueMode().equals("expr")) {
                defaultMaxValue = new ExpressionParameterValue(model.getDefaultMaxValueFilter());
            } else if (cfg.getDefaultMaxValueMode().equals("appoint")) {
                defaultMaxValue = DSParamUtils.getAppointValue(model.getDefaultMaxValues(), format);
            }
            cfg.setDefaultMaxValue((AbstractParameterValue)defaultMaxValue);
        } else {
            valueCfg = new ParameterValueConfig();
            if (model.getDataSourceModel() == null) {
                valueCfg.setDefaultValueMode(DSParamUtils.getValueMode(model.getNoneDsDefValueFilterMode()));
            } else {
                valueCfg.setDefaultValueMode(DSParamUtils.getValueMode(model.getDefaultValueFilterMode(), model.getDefaultValueFilterModeExtend()));
            }
            ExpressionParameterValue defaultValue = null;
            if ("expr".equals(valueCfg.getDefaultValueMode())) {
                defaultValue = new ExpressionParameterValue(model.getDefalutValueFilter());
            } else if ("appoint".equals(valueCfg.getDefaultValueMode())) {
                format = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource);
                defaultValue = DSParamUtils.getAppointValue(model.getDefaultValues(), format);
            }
            if (model.getWidgetType() == ParameterWidgetType.SMARTSELECTOR && model.getDefaultValues() instanceof com.jiuqi.bi.parameter.model.SmartSelector) {
                SmartSelector nss = new SmartSelector();
                nss.fromJson(((com.jiuqi.bi.parameter.model.SmartSelector)model.getDefaultValues()).toJson());
                defaultValue = new SmartSelectorParameterValue(nss);
            }
            valueCfg.setDefaultValue((AbstractParameterValue)defaultValue);
        }
        valueCfg.setAcceptMinValue(DSParamUtils.toString(model.getMinValue()));
        valueCfg.setAcceptMaxValue(DSParamUtils.toString(model.getMaxValue()));
        valueCfg.getCandidateDimTrees().addAll(model.getChoiceDimTreeNames());
        valueCfg.setCandidateMode(ParameterCandidateValueMode.valueOf((int)model.getDataSourceFilterMode().value()));
        if (model.getDataSourceFilterMode() == DataSourceFilterMode.APPOINT) {
            Object v = model.getDataSourceValues();
            if (v != null) {
                IParameterValueFormat valueFormat = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource);
                ArrayList<String> values = new ArrayList<String>();
                if (v instanceof List) {
                    List list = (List)v;
                    for (Object o : list) {
                        if (o instanceof DataSourceValueModel) {
                            values.add(((DataSourceValueModel)o).getCode());
                            continue;
                        }
                        values.add(DSParamUtils.toString(o));
                    }
                } else if (v instanceof MemoryDataSet) {
                    MemoryDataSet dataset = (MemoryDataSet)v;
                    Iterator itor = dataset.iterator();
                    while (itor.hasNext()) {
                        Object r = ((DataRow)itor.next()).getValue(0);
                        values.add(valueFormat.format(r));
                    }
                } else {
                    values.add(valueFormat.format(v));
                }
                valueCfg.getCandidateValue().addAll(values);
            }
        } else if (model.getDataSourceFilterMode() == DataSourceFilterMode.EXPRESSION && StringUtils.isNotEmpty((String)(dataSourceFilter = model.getDataSourceFilter()))) {
            valueCfg.getCandidateValue().add(dataSourceFilter);
        }
        List<ParameterDependMember> depends = model.getDepends();
        if (depends != null) {
            for (ParameterDependMember depend : depends) {
                valueCfg.getDepends().add(new ParameterDependMember(depend.getParameterName(), depend.getDatasourceFieldName()));
            }
        }
        target.setValueConfig((AbstractParameterValueConfig)valueCfg);
        if (model.getOwner() != null) {
            target.setOwner(model.getOwner().clone());
        }
        return target;
    }

    private static String toString(Object v) {
        if (v == null) {
            return null;
        }
        if (v instanceof Date) {
            SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return defaultDateFormat.format((Date)v);
        }
        if (v instanceof Calendar) {
            SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return defaultDateFormat.format(((Calendar)v).getTime());
        }
        return v.toString();
    }

    private static AbstractParameterValue getAppointValue(Object value, IParameterValueFormat format) throws ParameterException {
        FixedMemberParameterValue defaultValue = null;
        if (value != null) {
            ArrayList<Object> values = new ArrayList<Object>();
            if (value instanceof List) {
                List list = (List)value;
                for (Object o : list) {
                    if (o instanceof DataSourceValueModel) {
                        values.add(((DataSourceValueModel)o).getCode());
                        continue;
                    }
                    values.add(o);
                }
            } else {
                values.add(value);
            }
            defaultValue = new FixedMemberParameterValue(values);
        }
        return defaultValue;
    }

    private static String getValueMode(NoneDsDefValueFilterMode valueFilterMode) throws JSONException {
        if (valueFilterMode == NoneDsDefValueFilterMode.EXPRESSION) {
            return "expr";
        }
        return "appoint";
    }

    private static String getValueMode(ParameterDefaultValueFilterMode valueFilterMode, String valueFilterModeExt) throws JSONException {
        if (StringUtils.isNotEmpty((String)valueFilterModeExt)) {
            if (valueFilterModeExt.equals("currPeriod")) {
                return "currPeriod";
            }
            return valueFilterModeExt;
        }
        if (valueFilterMode == ParameterDefaultValueFilterMode.APPOINT) {
            return "appoint";
        }
        if (valueFilterMode == ParameterDefaultValueFilterMode.EXPRESSION) {
            return "expr";
        }
        if (valueFilterMode == ParameterDefaultValueFilterMode.FIRST) {
            return "first";
        }
        return "none";
    }

    public static com.jiuqi.bi.parameter.model.ParameterModel convertParameterModel(ParameterModel model) throws ParameterException {
        List<Object> valueList;
        Object dataSourceModel;
        if (model == null) {
            return null;
        }
        com.jiuqi.bi.parameter.model.ParameterModel target = new com.jiuqi.bi.parameter.model.ParameterModel();
        target._setGuid(model.getGuid());
        target.setAlias(model.getMessageAlias());
        target.setPublic(model.isGlobal());
        target.setCascadeAllChildren(model.getLevelCheckMode() == ParameterLevelMemberCheckMode.ALL_SUB_LEVEL);
        target.setCascadeDirectChildren(model.getLevelCheckMode() == ParameterLevelMemberCheckMode.DIRECT_LEVEL);
        target.setCrossLeafEnable(model.isCrossLeafEnable());
        target.setDataType(DataType.valueOf(model.getDataType()));
        target.setHidden(model.isHidden());
        target.setName(model.getName());
        target.setNullable(model.isNullable());
        target.setOnlyLeafSelectable(model.isOnlyLeafSelectable());
        target.setOrderReverse(model.isOrderReverse());
        target.setRangeParameter(model.isRangeParameter());
        target.setSelectMode(model.isRangeParameter() ? com.jiuqi.bi.parameter.model.ParameterSelectMode.SINGLE : com.jiuqi.bi.parameter.model.ParameterSelectMode.valueOf(model.getSelectMode().value()));
        target.setShowCode(model.isShowCode());
        target.setSwitchShowCode(model.isSwitchShowCode());
        target.setShowSearchWidget(model.isShowSearchWidget());
        target.setTitle(model.getTitle());
        target.setTitleVisible(model.isTitleVisible());
        target.setWidth(model.getWidth());
        target.setWigetType(ParameterWidgetType.valueOf(model.getWidgetType()));
        target.setDesc(model.getDescription());
        AbstractParameterValueConfig valueCfg = model.getValueConfig();
        List depends = valueCfg.getDepends();
        if (depends != null && !depends.isEmpty()) {
            ArrayList<String> cascadeParameters = new ArrayList<String>();
            for (ParameterDependMember depend : depends) {
                cascadeParameters.add(depend.getParameterName());
                target.getDepends().add(new ParameterDependMember(depend.getParameterName(), depend.getDatasourceFieldName()));
            }
            target.setCascadeParameters(cascadeParameters);
        }
        target.setDataSourceFilterMode(DataSourceFilterMode.valueOf(valueCfg.getCandidateMode().value()));
        if (valueCfg.getCandidateMode() == ParameterCandidateValueMode.EXPRESSION && !valueCfg.getCandidateValue().isEmpty()) {
            target.setDataSourceFilter((String)valueCfg.getCandidateValue().get(0));
        }
        target.getChoiceDimTreeNames().addAll(valueCfg.getCandidateDimTrees());
        AbstractParameterDataSourceModel datasource = model.getDatasource();
        String datasourceType = datasource.getType();
        if (datasource instanceof NonDataSourceModel) {
            List v = valueCfg.getCandidateValue();
            target.setDataSourceValues(v != null && !v.isEmpty() ? v.get(0) : null);
        } else if (datasource instanceof com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel) {
            DataSourceValueModel dsv;
            List items;
            com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel m = (com.jiuqi.nvwa.framework.parameter.datasource.extend.CustomListDataSourceModel)datasource;
            if (m.isTreatAsZB()) {
                dataSourceModel = new ZbParameterDataSourceModel();
                ((DataSourceModel)dataSourceModel).setDataType(DataType.valueOf(model.getDataType()));
                items = m.getItems();
                for (CustomListDataSourceModel.FixedMemberItem item : items) {
                    dsv = new DataSourceValueModel();
                    dsv.setCode(item.getCode());
                    dsv.setName(item.getTitle());
                    ((ZbParameterDataSourceModel)dataSourceModel).getValues().add(dsv);
                }
                target.setDataSourceModel((DataSourceModel)dataSourceModel);
            } else {
                dataSourceModel = new CustomListDataSourceModel();
                ((DataSourceModel)dataSourceModel).setDataType(DataType.valueOf(model.getDataType()));
                items = m.getItems();
                for (CustomListDataSourceModel.FixedMemberItem item : items) {
                    dsv = new DataSourceValueModel();
                    dsv.setCode(item.getCode());
                    dsv.setName(item.getTitle());
                    ((CustomListDataSourceModel)dataSourceModel).getValues().add(dsv);
                }
                target.setDataSourceModel((DataSourceModel)dataSourceModel);
            }
        } else {
            ParameterDataSourceHierarchyType htype;
            DataSourceModelFactory dataSourceModelFactory = DataSourceFactoryManager.getDataSourceModelFactory(datasourceType);
            if (dataSourceModelFactory == null) {
                throw new ParameterException("\u5f53\u524d\u73af\u5883\u4e0b\uff0c\u5df2\u4e0d\u5b58\u5728\u65e7\u7684\u53c2\u6570\u6765\u6e90\u63d0\u4f9b\u5668\uff1a" + datasourceType);
            }
            dataSourceModel = dataSourceModelFactory.createDataSourceModel();
            AbstractParameterDataSourceModel newDatasource = model.getDatasource();
            if (newDatasource instanceof SQLDataSourceModel) {
                com.jiuqi.bi.parameter.extend.sql.SQLDataSourceModel orgDatasource = (com.jiuqi.bi.parameter.extend.sql.SQLDataSourceModel)dataSourceModel;
                SQLDataSourceModel newSqlDatasource = (SQLDataSourceModel)newDatasource;
                orgDatasource.setExpression(newSqlDatasource.getSql());
                orgDatasource.setStructureCode(newSqlDatasource.getStructureCode());
                orgDatasource.setDataSouce(newSqlDatasource.getDatasourceId());
                SQLDataSourceHierarchyType htype2 = SQLDataSourceHierarchyType.NONE;
                if (newSqlDatasource.getHierarchyType() == ParameterDataSourceHierarchyType.PARENTMODE) {
                    htype2 = SQLDataSourceHierarchyType.PARENTMODE;
                } else if (newSqlDatasource.getHierarchyType() == ParameterDataSourceHierarchyType.STRUCTURECODE) {
                    htype2 = SQLDataSourceHierarchyType.STRUCTURECODE;
                }
                orgDatasource.setSQLHierarchyType(htype2);
            } else {
                JSONObject json = new JSONObject();
                model.getDatasource().toJson(json);
                ((DataSourceModel)dataSourceModel).load(json);
            }
            ((DataSourceModel)dataSourceModel).setType(datasourceType);
            ((DataSourceModel)dataSourceModel).setDataType(DataType.valueOf(model.getDataType()));
            ((DataSourceModel)dataSourceModel).setTimekey(model.getDatasource().isTimekey());
            ((DataSourceModel)dataSourceModel).setDimType(ParameterDimType.valueOf(model.getDatasource().getBusinessType().value()));
            int tg = model.getDatasource().getTimegranularity();
            if (tg != -1) {
                ((DataSourceModel)dataSourceModel).setTimegranularity(TimeGranularity.valueOf(tg));
            }
            if ((htype = model.getDatasource().getHierarchyType()) == ParameterDataSourceHierarchyType.NONE) {
                ((DataSourceModel)dataSourceModel).setHierarchyType(ParameterHierarchyType.NONE);
            } else if (htype == ParameterDataSourceHierarchyType.NORMAL) {
                ((DataSourceModel)dataSourceModel).setHierarchyType(ParameterHierarchyType.NORMAL);
            } else if (htype == ParameterDataSourceHierarchyType.PARENTMODE) {
                ((DataSourceModel)dataSourceModel).setHierarchyType(ParameterHierarchyType.PARENT_SON);
            }
            target.setDataSourceModel((DataSourceModel)dataSourceModel);
        }
        if (valueCfg.getCandidateMode() == ParameterCandidateValueMode.APPOINT) {
            ArrayList<DataSourceValueModel> datasourceVal = new ArrayList<DataSourceValueModel>();
            for (String s : valueCfg.getCandidateValue()) {
                DataSourceValueModel m = new DataSourceValueModel();
                m.setCode(s);
                datasourceVal.add(m);
            }
            target.setDataSourceValues(datasourceVal);
        }
        if (target.getDataSourceModel() != null) {
            target.getDataSourceModel().setOrderEnable(datasource.isCanOrder());
        }
        target.setMinValue(valueCfg.getAcceptMinValue());
        target.setMaxValue(valueCfg.getAcceptMaxValue());
        if (model.isRangeParameter()) {
            ParameterRangeValueConfig rvc = null;
            if (valueCfg instanceof ParameterRangeValueConfig) {
                rvc = (ParameterRangeValueConfig)valueCfg;
            } else {
                rvc = new ParameterRangeValueConfig();
                rvc.setCandidateMode(valueCfg.getCandidateMode());
                rvc.setDefaultValueMode(valueCfg.getDefaultValueMode());
                rvc.setDefaultValue(valueCfg.getDefaultValue());
                rvc.setDefaultMaxValueMode(valueCfg.getDefaultValueMode());
                rvc.setDefaultMaxValue(valueCfg.getDefaultValue());
            }
            if (datasource instanceof NonDataSourceModel) {
                target.setNoneDsDefValueFilterMode(DSParamUtils.getNoneValueFilterMode(rvc.getDefaultMinValueMode()));
                target.setNoneDsDefMaxValueFilterMode(DSParamUtils.getNoneValueFilterMode(rvc.getDefaultMaxValueMode()));
            } else {
                String minValueFilterModeExt = DSParamUtils.getValueFilterModeExt(rvc.getDefaultMinValueMode());
                if (StringUtils.isNotEmpty((String)minValueFilterModeExt)) {
                    target.setDefaultValueFilterModeExtend(minValueFilterModeExt);
                } else {
                    target.setDefaultValueFilterMode(DSParamUtils.getValueFilterMode(rvc.getDefaultMinValueMode()));
                }
                String maxValueFilterModeExt = DSParamUtils.getValueFilterModeExt(rvc.getDefaultMaxValueMode());
                if (StringUtils.isNotEmpty((String)maxValueFilterModeExt)) {
                    target.setDefaultMaxValueFilterModeExtend(maxValueFilterModeExt);
                } else {
                    target.setDefaultMaxValueFilterMode(DSParamUtils.getValueFilterMode(rvc.getDefaultMaxValueMode()));
                }
            }
            IParameterValueFormat format = ParameterUtils.createValueFormat((AbstractParameterDataSourceModel)datasource);
            if (rvc.getDefaultValueMode().equals("expr")) {
                target.setDefalutValueFilter((String)rvc.getDefaultValue().getValue());
            } else if (rvc.getDefaultValue() != null) {
                valueList = DSParamUtils.getValues(rvc.getDefaultValue());
                target.setDefaultValues(DSParamUtils.buildDefaultValues(datasource, valueList));
            }
            if (rvc.getDefaultMaxValueMode().equals("expr")) {
                target.setDefaultMaxValueFilter((String)rvc.getDefaultMaxValue().getValue());
            } else if (rvc.getDefaultMaxValue() != null) {
                valueList = DSParamUtils.getValues(rvc.getDefaultMaxValue());
                target.setDefaultMaxValues(DSParamUtils.buildDefaultValues(datasource, valueList));
            }
        } else {
            String defValMode = valueCfg.getDefaultValueMode();
            if (datasource instanceof NonDataSourceModel) {
                target.setNoneDsDefValueFilterMode(DSParamUtils.getNoneValueFilterMode(defValMode));
            } else {
                String valueFilterModeExt = DSParamUtils.getValueFilterModeExt(defValMode);
                if (StringUtils.isNotEmpty((String)valueFilterModeExt)) {
                    target.setDefaultValueFilterModeExtend(valueFilterModeExt);
                } else {
                    target.setDefaultValueFilterMode(DSParamUtils.getValueFilterMode(defValMode));
                }
            }
            AbstractParameterValue defValCfg = valueCfg.getDefaultValue();
            if (defValCfg != null) {
                if ("expr".equals(defValMode) && defValCfg.isFormulaValue()) {
                    target.setDefalutValueFilter((String)defValCfg.getValue());
                } else if (defValCfg.getValue() != null) {
                    valueList = DSParamUtils.getValues(defValCfg);
                    target.setDefaultValues(DSParamUtils.buildDefaultValues(datasource, valueList));
                }
            }
        }
        if (model.getOwner() != null) {
            target.setOwner(model.getOwner().clone());
        }
        return target;
    }

    private static List<Object> getValues(AbstractParameterValue defValCfg) {
        Object v = defValCfg.getValue();
        if (v instanceof List) {
            List data = (List)v;
            ArrayList<Object> rs = new ArrayList<Object>();
            for (Object obj : data) {
                rs.add(obj);
            }
            return rs;
        }
        ArrayList<Object> list = new ArrayList<Object>();
        list.add(v);
        return list;
    }

    private static NoneDsDefValueFilterMode getNoneValueFilterMode(String valueMode) {
        if ("expr".equals(valueMode)) {
            return NoneDsDefValueFilterMode.EXPRESSION;
        }
        return NoneDsDefValueFilterMode.APPOINT;
    }

    private static String getValueFilterModeExt(String valueMode) {
        if (defaultValueFilters.indexOf(valueMode) == -1) {
            if ("currPeriod".equals(valueMode)) {
                return "currPeriod";
            }
            return valueMode;
        }
        return null;
    }

    private static ParameterDefaultValueFilterMode getValueFilterMode(String valueMode) {
        if ("appoint".equals(valueMode)) {
            return ParameterDefaultValueFilterMode.APPOINT;
        }
        if ("expr".equals(valueMode)) {
            return ParameterDefaultValueFilterMode.EXPRESSION;
        }
        if ("first".equals(valueMode)) {
            return ParameterDefaultValueFilterMode.FIRST;
        }
        if ("firstChild".equals(valueMode)) {
            return ParameterDefaultValueFilterMode.FIRST_CHILD;
        }
        return ParameterDefaultValueFilterMode.NONE;
    }

    private static Object buildDefaultValues(AbstractParameterDataSourceModel datasource, List<Object> valueList) {
        if (datasource instanceof NonDataSourceModel) {
            Object value;
            Object object = value = valueList.size() > 0 ? valueList.get(0) : null;
            if (value instanceof Calendar) {
                return DSParamUtils.getDateValue((Calendar)value);
            }
            return value;
        }
        if (valueList.size() > 0 && valueList.get(0) instanceof SmartSelector) {
            JSONObject valueJson = new JSONObject();
            ((SmartSelector)valueList.get(0)).toJson(valueJson);
            com.jiuqi.bi.parameter.model.SmartSelector ss = new com.jiuqi.bi.parameter.model.SmartSelector();
            ss.load(valueJson);
            return ss;
        }
        List datasourceVal = valueList.stream().map(v -> {
            DataSourceValueModel m = new DataSourceValueModel();
            if (v != null) {
                if (v instanceof Calendar) {
                    m.setCode(DSParamUtils.getDateValue((Calendar)v));
                } else {
                    m.setCode(v.toString());
                }
            }
            return m;
        }).collect(Collectors.toList());
        return datasourceVal;
    }

    private static String getDateValue(Calendar value) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(value.getTime());
    }

    static {
        defaultValueFilters.add("none");
        defaultValueFilters.add("appoint");
        defaultValueFilters.add("expr");
        defaultValueFilters.add("first");
    }
}

