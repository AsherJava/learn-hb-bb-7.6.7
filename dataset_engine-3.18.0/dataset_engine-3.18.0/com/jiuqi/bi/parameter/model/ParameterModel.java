/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.util.Guid
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterOwner
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.model;

import com.jiuqi.bi.dataset.DataType;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.parameter.DataSourceUtils;
import com.jiuqi.bi.parameter.extend.zb.ZbParameterDataSourceModel;
import com.jiuqi.bi.parameter.manager.DataSourceFactoryManager;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactory;
import com.jiuqi.bi.parameter.manager.DataSourceModelFactoryEx;
import com.jiuqi.bi.parameter.model.NoneDsDefValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterColumnInfo;
import com.jiuqi.bi.parameter.model.ParameterDefaultValueFilterMode;
import com.jiuqi.bi.parameter.model.ParameterHierarchyType;
import com.jiuqi.bi.parameter.model.ParameterSelectMode;
import com.jiuqi.bi.parameter.model.ParameterWidgetType;
import com.jiuqi.bi.parameter.model.SmartSelector;
import com.jiuqi.bi.parameter.model.datasource.DataSourceFilterMode;
import com.jiuqi.bi.parameter.model.datasource.DataSourceModel;
import com.jiuqi.bi.parameter.model.datasource.DataSourceValueModel;
import com.jiuqi.bi.util.Guid;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nvwa.framework.parameter.model.ParameterDependMember;
import com.jiuqi.nvwa.framework.parameter.model.ParameterOwner;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParameterModel
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String title;
    private DataSourceModel dataSourceModel;
    private DataSourceFilterMode dataSourceFilterMode = DataSourceFilterMode.ALL;
    private Object dataSourceValues;
    private String dataSourceFilter;
    private ParameterDefaultValueFilterMode defaultValueFilterMode = ParameterDefaultValueFilterMode.FIRST;
    private ParameterDefaultValueFilterMode defaultMaxValueFilterMode = ParameterDefaultValueFilterMode.FIRST;
    private String defaultValueFilterModeExtend;
    private String defaultMaxValueFilterModeExtend;
    private DataType dataType;
    private Object defaultValues;
    private String defaultValueFilter;
    private Object defaultMaxValues;
    private String defaultMaxValueFilter;
    private Object minValue;
    private Object maxValue;
    private NoneDsDefValueFilterMode noneDsDefValueFilterMode = NoneDsDefValueFilterMode.APPOINT;
    private NoneDsDefValueFilterMode noneDsDefMaxValueFilterMode = NoneDsDefValueFilterMode.APPOINT;
    private List<String> cascadeParameters = new ArrayList<String>();
    private List<ParameterDependMember> depends = new ArrayList<ParameterDependMember>();
    private ParameterSelectMode selectMode = ParameterSelectMode.SINGLE;
    private ParameterWidgetType widgetType = ParameterWidgetType.DEFAULT;
    private boolean onlyLeafSelectable;
    private boolean crossLeafEnable = true;
    private boolean titleVisible = true;
    private boolean hidden;
    private boolean orderReverse;
    private boolean showCode;
    private boolean switchShowCode;
    private boolean nullable = true;
    private boolean rangeParameter;
    private boolean showSearchWidget = true;
    private String tag;
    private boolean isPublic;
    private String guid;
    private boolean isRangeCloned;
    private String alias;
    private int width;
    private boolean needCascade = true;
    private String lastModifiedTime;
    private boolean cascadeAllChildren;
    private boolean cascadeDirectChildren;
    private List<String> choiceDimTreeNames = new ArrayList<String>();
    private String desc;
    private ParameterOwner owner;
    private static final String TAG_NAME = "name";
    private static final String TAG_TITLE = "title";
    private static final String TAG_MINVALUE = "minValue";
    private static final String TAG_MAXVALUE = "maxValue";
    private static final String TAG_DATATYPE = "dataType";
    private static final String TAG_GUID = "guid";
    private static final String TAG_ISRANGECLONED = "isRangeCloned";
    private static final String TAG_ISPUBLIC = "isPublic";
    private static final String TAG_ALIAS = "alias";
    private static final String TAG_LASTMODIFIEDTIME = "lastModifiedTime";
    private static final String TAG_DIMTREE = "dimTree";
    private static final String TAG_DESC = "desc";
    private static final String TAG_OWNER = "owner";
    private static final String TAG_DATASOURCE = "dataSource";
    private static final String TAG_DATASOURCE_FILTERMODE = "dataSourceFilterMode";
    private static final String TAG_DATASOURCE_FILTER = "dataSourceFilter";
    private static final String TAG_DATASOURCE_VALUES = "dataSourceValues";
    private static final String TAG_DEFAULTVALUE_FILTERMODE = "defaultValueFilterMode";
    private static final String TAG_DEFAULTMAXVALUE_FILTERMODE = "defaultMaxValueFilterMode";
    private static final String TAG_DEFAULTVALUE_FILTERMODE_EXTEND = "defaultValueFilterMode_extend";
    private static final String TAG_DEFAULTMAXVALUE_FILTERMODE_EXTEND = "defaultMaxValueFilterMode_extend";
    private static final String TAG_DEFAULTVALUE_FILTER = "defaultValueFilter";
    private static final String TAG_DEFAULTMAXVALUE_FILTER = "defaultMaxValueFilter";
    private static final String TAG_NONEDSDEFAULTVALUEFILTERMODE = "noneDsDefaulFilterMode";
    private static final String TAG_NONEDSDEFAULTMAXVALUE_FILTERMODE = "noneDsDefMaxFilterMode";
    private static final String TAG_DEFAULT_VALUES = "defaultValues";
    private static final String TAG_DEFAULT_MAX_VALUES = "defaultMaxValues";
    private static final String TAG_SELECTMODE = "selectMode";
    private static final String TAG_WIDGETTYPE = "widgetType";
    private static final String TAG_ONLYLEAFSELECTABLE = "onlyLeafSelectable";
    private static final String TAG_TITLE_VISIBLE = "titleVisible";
    private static final String TAG_HIDDEN = "hidden";
    private static final String TAG_ORDERREVERSE = "orderReverse";
    private static final String TAG_SHOWCODE = "showCode";
    private static final String TAG_SWITCHSHOWCODE = "switchShowCode";
    private static final String TAG_NULLABLE = "nullable";
    private static final String TAG_SHOWSEARCHWIDGET = "showSearchWidget";
    private static final String TAG_TAG = "tag";
    private static final String TAG_CASCADEP_ARAMETERS = "cascadeParameters";
    private static final String TAG_DEPENDS = "depends";
    private static final String TAG_DEPENDS_PARAMETER = "parameterName";
    private static final String TAG_DEPENDS_FIELD = "datasourceFieldName";
    private static final String TAG_RANGE = "range";
    private static final String TAG_WIDTH = "width";
    private static final String TAG_CASCADEALLCHILDREN = "cascadeAllChildren";
    private static final String TAG_CASCADEDIRECTCHILDREN = "cascadeDirectChildren";

    public String _getGuid() {
        return this.guid;
    }

    public void _setGuid(String guid) {
        this.guid = guid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public DataSourceModel getDataSourceModel() {
        return this.dataSourceModel;
    }

    public void setDataSourceModel(DataSourceModel dataSourceModel) {
        this.dataSourceModel = dataSourceModel;
    }

    public DataSourceFilterMode getDataSourceFilterMode() {
        return this.dataSourceFilterMode;
    }

    public void setDataSourceFilterMode(DataSourceFilterMode dataSourceFilterMode) {
        this.dataSourceFilterMode = dataSourceFilterMode;
    }

    public Object getDataSourceValues() {
        return this.dataSourceValues;
    }

    public void setDataSourceValues(Object dataSourceValues) {
        this.dataSourceValues = dataSourceValues;
    }

    public String getDataSourceFilter() {
        return this.dataSourceFilter;
    }

    public void setDataSourceFilter(String dataSourceFilter) {
        this.dataSourceFilter = dataSourceFilter;
    }

    public ParameterDefaultValueFilterMode getDefaultValueFilterMode() {
        return this.defaultValueFilterMode;
    }

    public void setDefaultValueFilterMode(ParameterDefaultValueFilterMode defaultValueFilterMode) {
        this.defaultValueFilterMode = defaultValueFilterMode;
    }

    public ParameterDefaultValueFilterMode getDefaultMaxValueFilterMode() {
        return this.defaultMaxValueFilterMode;
    }

    public void setDefaultMaxValueFilterMode(ParameterDefaultValueFilterMode defaultMaxValueFilterMode) {
        this.defaultMaxValueFilterMode = defaultMaxValueFilterMode;
    }

    public Object getDefaultValues() {
        return this.defaultValues;
    }

    public void setDefaultValues(Object defaultValues) {
        this.defaultValues = defaultValues;
    }

    public Object getDefaultMaxValues() {
        return this.defaultMaxValues;
    }

    public void setDefaultMaxValues(Object defaultMaxValues) {
        this.defaultMaxValues = defaultMaxValues;
    }

    public String getDefalutValueFilter() {
        return this.defaultValueFilter;
    }

    public void setDefalutValueFilter(String defalutValueFilter) {
        this.defaultValueFilter = defalutValueFilter;
    }

    public String getDefaultMaxValueFilter() {
        return this.defaultMaxValueFilter;
    }

    public void setDefaultMaxValueFilter(String defaultMaxValueFilter) {
        this.defaultMaxValueFilter = defaultMaxValueFilter;
    }

    public List<String> getCascadeParameters() {
        return this.cascadeParameters;
    }

    public void setCascadeParameters(List<String> cascadeParameters) {
        this.cascadeParameters = cascadeParameters;
    }

    public List<ParameterDependMember> getDepends() {
        return this.depends;
    }

    public Object getMinValue() {
        return this.minValue;
    }

    public void setMinValue(Object minValue) {
        this.minValue = minValue;
    }

    public Object getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(Object maxValue) {
        this.maxValue = maxValue;
    }

    public ParameterSelectMode getSelectMode() {
        return this.selectMode;
    }

    public void setSelectMode(ParameterSelectMode selectMode) {
        this.selectMode = selectMode;
    }

    public ParameterWidgetType getWidgetType() {
        return this.widgetType;
    }

    public void setWigetType(ParameterWidgetType widgetType) {
        this.widgetType = widgetType;
    }

    public boolean isOnlyLeafSelectable() {
        return this.onlyLeafSelectable;
    }

    public void setOnlyLeafSelectable(boolean onlyLeafSelectable) {
        this.onlyLeafSelectable = onlyLeafSelectable;
    }

    public boolean isRangeParameter() {
        return this.rangeParameter;
    }

    public void setRangeParameter(boolean rangeParameter) {
        this.rangeParameter = rangeParameter;
    }

    public boolean isShowSearchWidget() {
        return this.showSearchWidget;
    }

    public void setShowSearchWidget(boolean showSearchWidget) {
        this.showSearchWidget = showSearchWidget;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public boolean isOrderReverse() {
        return this.orderReverse;
    }

    public void setOrderReverse(boolean orderReverse) {
        this.orderReverse = orderReverse;
    }

    public boolean isShowCode() {
        return this.showCode;
    }

    public void setShowCode(boolean showCode) {
        this.showCode = showCode;
    }

    public boolean isSwitchShowCode() {
        return this.switchShowCode;
    }

    public void setSwitchShowCode(boolean switchShowCode) {
        this.switchShowCode = switchShowCode;
    }

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isZBParameter() {
        return this.dataSourceModel != null && this.dataSourceModel instanceof ZbParameterDataSourceModel;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isPublic() {
        return this.isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public NoneDsDefValueFilterMode getNoneDsDefValueFilterMode() {
        return this.noneDsDefValueFilterMode;
    }

    public void setNoneDsDefValueFilterMode(NoneDsDefValueFilterMode noneDsDefValueFilterMode) {
        this.noneDsDefValueFilterMode = noneDsDefValueFilterMode;
    }

    public NoneDsDefValueFilterMode getNoneDsDefMaxValueFilterMode() {
        return this.noneDsDefMaxValueFilterMode;
    }

    public void setNoneDsDefMaxValueFilterMode(NoneDsDefValueFilterMode noneDsDefMaxValueFilterMode) {
        this.noneDsDefMaxValueFilterMode = noneDsDefMaxValueFilterMode;
    }

    public boolean _isRangeCloned() {
        return this.isRangeCloned;
    }

    public void _setRangeCloned(boolean isRangeCloned) {
        this.isRangeCloned = isRangeCloned;
    }

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public void setCrossLeafEnable(boolean crossLeafEnable) {
        this.crossLeafEnable = crossLeafEnable;
    }

    public boolean isCrossLeafEnable() {
        return this.crossLeafEnable;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ParameterOwner getOwner() {
        return this.owner;
    }

    public void setOwner(ParameterOwner owner) {
        this.owner = owner;
    }

    public boolean isNormalHierParameter() {
        if (this.dataSourceModel == null) {
            return false;
        }
        return this.dataSourceModel.getHireachyType().equals((Object)ParameterHierarchyType.NORMAL);
    }

    public void save(JSONObject value) throws JSONException {
        if (value != null) {
            this.saveBasicInfo(value);
            this.saveDataSource(value);
            this.saveValues(value);
            this.saveAdvance(value);
        }
    }

    private void saveBasicInfo(JSONObject value) throws JSONException {
        if (StringUtils.isNotEmpty((String)this.guid)) {
            value.put(TAG_GUID, (Object)this.guid);
        }
        value.put(TAG_NAME, (Object)this.name);
        value.put(TAG_TITLE, (Object)this.title);
        if (this.minValue != null) {
            value.put(TAG_MINVALUE, this.minValue);
        }
        if (this.maxValue != null) {
            value.put(TAG_MAXVALUE, this.maxValue);
        }
        value.put(TAG_DATATYPE, this.dataType.value());
        value.put(TAG_ISRANGECLONED, this.isRangeCloned);
        value.put(TAG_ISPUBLIC, this.isPublic);
        if (StringUtils.isNotEmpty((String)this.alias)) {
            value.put(TAG_ALIAS, (Object)this.alias);
        }
        if (StringUtils.isNotEmpty((String)this.lastModifiedTime)) {
            value.put(TAG_LASTMODIFIEDTIME, (Object)this.lastModifiedTime);
        }
        if (StringUtils.isNotEmpty((String)this.desc)) {
            value.put(TAG_DESC, (Object)this.desc);
        }
    }

    private void saveDataSource(JSONObject value) throws JSONException {
        if (this.dataSourceModel != null) {
            JSONObject dataSourceModelJson = new JSONObject();
            this.dataSourceModel.save(dataSourceModelJson);
            value.put(TAG_DATASOURCE, (Object)dataSourceModelJson);
        }
    }

    private void saveValues(JSONObject value) throws JSONException {
        if (this.dataSourceFilterMode != null) {
            value.put(TAG_DATASOURCE_FILTERMODE, this.dataSourceFilterMode.value());
        }
        if (StringUtils.isNotEmpty((String)this.dataSourceFilter)) {
            value.put(TAG_DATASOURCE_FILTER, (Object)this.dataSourceFilter);
        }
        if (this.dataSourceValues != null) {
            this.saveDataSourceValues(value);
        }
        if (StringUtils.isNotEmpty((String)this.defaultValueFilterModeExtend)) {
            value.put(TAG_DEFAULTVALUE_FILTERMODE_EXTEND, (Object)this.defaultValueFilterModeExtend);
        } else if (this.defaultValueFilterMode != null) {
            value.put(TAG_DEFAULTVALUE_FILTERMODE, this.defaultValueFilterMode.value());
        }
        if (StringUtils.isNotEmpty((String)this.defaultMaxValueFilterModeExtend)) {
            value.put(TAG_DEFAULTMAXVALUE_FILTERMODE_EXTEND, (Object)this.defaultMaxValueFilterModeExtend);
        } else if (this.defaultMaxValueFilterMode != null) {
            value.put(TAG_DEFAULTMAXVALUE_FILTERMODE, this.defaultMaxValueFilterMode.value());
        }
        if (StringUtils.isNotEmpty((String)this.defaultValueFilter)) {
            value.put(TAG_DEFAULTVALUE_FILTER, (Object)this.defaultValueFilter);
        }
        if (StringUtils.isNotEmpty((String)this.defaultMaxValueFilter)) {
            value.put(TAG_DEFAULTMAXVALUE_FILTER, (Object)this.defaultMaxValueFilter);
        }
        value.put(TAG_NONEDSDEFAULTVALUEFILTERMODE, this.noneDsDefValueFilterMode.value());
        value.put(TAG_NONEDSDEFAULTMAXVALUE_FILTERMODE, this.noneDsDefMaxValueFilterMode.value());
        if (this.defaultValues != null) {
            this.saveDefaultValues(value);
        }
        if (this.rangeParameter && this.defaultMaxValues != null) {
            this.saveDefaulMaxtValues(value);
        }
        JSONArray dimTrees = new JSONArray();
        for (String s : this.choiceDimTreeNames) {
            dimTrees.put((Object)s);
        }
        value.put(TAG_DIMTREE, (Object)dimTrees);
    }

    private void saveDefaultValues(JSONObject value) throws JSONException {
        if (this.defaultValues instanceof List) {
            List valueModels = (List)this.defaultValues;
            JSONArray valueModelJsArray = DataSourceUtils.dataSourceValues2JsonArray(valueModels);
            value.put(TAG_DEFAULT_VALUES, (Object)valueModelJsArray);
        } else if (this.defaultValues instanceof MemoryDataSet) {
            MemoryDataSet dataSet = (MemoryDataSet)this.defaultValues;
            JSONObject dataSourceValueJsObj = DataSourceUtils.memoryDataSet2Json((MemoryDataSet<ParameterColumnInfo>)dataSet);
            value.put(TAG_DEFAULT_VALUES, (Object)dataSourceValueJsObj);
        } else if (this.getWidgetType() == ParameterWidgetType.SMARTSELECTOR) {
            if (this.defaultValues instanceof SmartSelector) {
                SmartSelector ss = (SmartSelector)this.defaultValues;
                value.put(TAG_DEFAULT_VALUES, (Object)ss.toJson());
            }
        } else {
            value.put(TAG_DEFAULT_VALUES, this.defaultValues);
        }
    }

    private void saveDefaulMaxtValues(JSONObject value) throws JSONException {
        if (this.defaultMaxValues instanceof List) {
            List valueModels = (List)this.defaultMaxValues;
            JSONArray valueModelJsArray = DataSourceUtils.dataSourceValues2JsonArray(valueModels);
            value.put(TAG_DEFAULT_MAX_VALUES, (Object)valueModelJsArray);
        } else if (this.defaultMaxValues instanceof MemoryDataSet) {
            MemoryDataSet dataSet = (MemoryDataSet)this.defaultMaxValues;
            JSONObject dataSourceValueJsObj = DataSourceUtils.memoryDataSet2Json((MemoryDataSet<ParameterColumnInfo>)dataSet);
            value.put(TAG_DEFAULT_MAX_VALUES, (Object)dataSourceValueJsObj);
        } else if (this.getWidgetType() == ParameterWidgetType.SMARTSELECTOR) {
            if (this.defaultMaxValues instanceof SmartSelector) {
                SmartSelector ss = (SmartSelector)this.defaultMaxValues;
                value.put(TAG_DEFAULT_MAX_VALUES, (Object)ss.toJson());
            }
        } else {
            value.put(TAG_DEFAULT_MAX_VALUES, this.defaultMaxValues);
        }
    }

    private void saveDataSourceValues(JSONObject value) throws JSONException {
        if (this.dataSourceValues instanceof List) {
            List valueModels = (List)this.dataSourceValues;
            JSONArray valueModelJsArray = DataSourceUtils.dataSourceValues2JsonArray(valueModels);
            value.put(TAG_DATASOURCE_VALUES, (Object)valueModelJsArray);
        } else if (this.dataSourceValues instanceof MemoryDataSet) {
            MemoryDataSet dataSet = (MemoryDataSet)this.dataSourceValues;
            JSONObject dataSourceValueJsObj = DataSourceUtils.memoryDataSet2Json((MemoryDataSet<ParameterColumnInfo>)dataSet);
            value.put(TAG_DATASOURCE_VALUES, (Object)dataSourceValueJsObj);
        } else {
            value.put(TAG_DATASOURCE_VALUES, this.dataSourceValues);
        }
    }

    private void saveAdvance(JSONObject value) throws JSONException {
        if (this.cascadeParameters != null && this.cascadeParameters.size() != 0) {
            JSONArray cascadeParameterArray = new JSONArray();
            for (String parameterName : this.cascadeParameters) {
                JSONObject parameterJson = new JSONObject();
                parameterJson.put(TAG_NAME, (Object)parameterName);
                cascadeParameterArray.put((Object)parameterJson);
            }
            value.put(TAG_CASCADEP_ARAMETERS, (Object)cascadeParameterArray);
        }
        if (this.depends != null && this.depends.size() != 0) {
            JSONArray dependsJsons = new JSONArray();
            for (ParameterDependMember depend : this.depends) {
                JSONObject dependJson = new JSONObject();
                dependJson.put(TAG_DEPENDS_PARAMETER, (Object)depend.getParameterName());
                dependJson.put(TAG_DEPENDS_FIELD, (Object)depend.getDatasourceFieldName());
                dependsJsons.put((Object)dependJson);
            }
            value.put(TAG_DEPENDS, (Object)dependsJsons);
        }
        if (this.selectMode != null) {
            value.put(TAG_SELECTMODE, this.selectMode.value());
        }
        if (this.widgetType != null) {
            value.put(TAG_WIDGETTYPE, this.widgetType.value());
        }
        value.put(TAG_ONLYLEAFSELECTABLE, this.onlyLeafSelectable);
        value.put("crossLeafEnable", this.crossLeafEnable);
        value.put(TAG_TITLE_VISIBLE, this.titleVisible);
        value.put(TAG_HIDDEN, this.hidden);
        value.put(TAG_ORDERREVERSE, this.orderReverse);
        value.put(TAG_SHOWCODE, this.showCode);
        value.put(TAG_SWITCHSHOWCODE, this.switchShowCode);
        value.put(TAG_NULLABLE, this.nullable);
        value.put(TAG_SHOWSEARCHWIDGET, this.showSearchWidget);
        value.put(TAG_RANGE, this.rangeParameter);
        value.put(TAG_TAG, (Object)this.tag);
        value.put(TAG_WIDTH, this.width);
        value.put(TAG_CASCADEALLCHILDREN, this.cascadeAllChildren);
        value.put(TAG_CASCADEDIRECTCHILDREN, this.cascadeDirectChildren);
        if (this.owner != null) {
            JSONObject json = new JSONObject();
            this.owner.toJson(json);
            value.put(TAG_OWNER, (Object)json);
        }
    }

    public void load(JSONObject value) throws JSONException {
        this.loadBasicInfo(value);
        this.loadDataSource(value);
        this.loadValues(value);
        this.loadAdvance(value);
        if (this.dataSourceModel == null && this.dataType == DataType.DATETIME) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy;mm;dd");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMDD");
            try {
                String val;
                if (this.minValue != null && StringUtils.isNotEmpty((String)(val = this.minValue.toString())) && val.contains(";")) {
                    Date min = sdf.parse(val);
                    this.minValue = sdf1.format(min);
                }
                if (this.maxValue != null && StringUtils.isNotEmpty((String)(val = this.maxValue.toString())) && val.contains(";")) {
                    Date max = sdf.parse(val);
                    this.maxValue = sdf1.format(max);
                }
            }
            catch (Exception e) {
                throw new JSONException((Throwable)e);
            }
        }
    }

    private void loadAdvance(JSONObject value) throws JSONException {
        if (value.has(TAG_DEPENDS) && !value.isNull(TAG_DEPENDS)) {
            JSONArray dependJsons = value.optJSONArray(TAG_DEPENDS);
            for (int i = 0; i < dependJsons.length(); ++i) {
                JSONObject dependJson = dependJsons.getJSONObject(i);
                String parameterName = dependJson.getString(TAG_DEPENDS_PARAMETER);
                String datasourceFieldName = dependJson.optString(TAG_DEPENDS_FIELD);
                this.depends.add(new ParameterDependMember(parameterName, datasourceFieldName));
                this.cascadeParameters.add(parameterName);
            }
        } else if (!value.isNull(TAG_CASCADEP_ARAMETERS)) {
            JSONArray cascadeParameterArray = value.getJSONArray(TAG_CASCADEP_ARAMETERS);
            for (int i = 0; i < cascadeParameterArray.length(); ++i) {
                JSONObject parameterJson = cascadeParameterArray.getJSONObject(i);
                String parameterName = parameterJson.getString(TAG_NAME);
                this.cascadeParameters.add(parameterName);
                this.depends.add(new ParameterDependMember(parameterName, null));
            }
        }
        if (!value.isNull(TAG_SELECTMODE)) {
            int selectModeValue = value.getInt(TAG_SELECTMODE);
            this.selectMode = ParameterSelectMode.valueOf(selectModeValue);
        }
        if (!value.isNull(TAG_WIDGETTYPE)) {
            int widgetTypeValue = value.getInt(TAG_WIDGETTYPE);
            this.widgetType = ParameterWidgetType.valueOf(widgetTypeValue);
        }
        this.onlyLeafSelectable = value.optBoolean(TAG_ONLYLEAFSELECTABLE, false);
        this.crossLeafEnable = value.optBoolean("crossLeafEnable", true);
        this.titleVisible = value.optBoolean(TAG_TITLE_VISIBLE, true);
        this.hidden = value.optBoolean(TAG_HIDDEN, false);
        this.orderReverse = value.optBoolean(TAG_ORDERREVERSE, false);
        this.showCode = value.optBoolean(TAG_SHOWCODE, false);
        this.switchShowCode = value.optBoolean(TAG_SWITCHSHOWCODE, false);
        this.nullable = value.optBoolean(TAG_NULLABLE, false);
        this.showSearchWidget = value.optBoolean(TAG_SHOWSEARCHWIDGET, false);
        if (!value.isNull(TAG_TAG)) {
            this.tag = value.getString(TAG_TAG);
        }
        this.rangeParameter = value.optBoolean(TAG_RANGE, false);
        if (!value.isNull(TAG_WIDTH)) {
            this.width = value.getInt(TAG_WIDTH);
        }
        this.cascadeAllChildren = value.optBoolean(TAG_CASCADEALLCHILDREN, false);
        this.cascadeDirectChildren = value.optBoolean(TAG_CASCADEDIRECTCHILDREN, false);
        if (!value.isNull(TAG_OWNER)) {
            JSONObject j = value.optJSONObject(TAG_OWNER);
            this.owner = new ParameterOwner();
            this.owner.fromJson(j);
        }
    }

    private void loadValues(JSONObject value) throws JSONException {
        if (!value.isNull(TAG_DATASOURCE_FILTERMODE)) {
            int dataSourceFilterModeValue = value.getInt(TAG_DATASOURCE_FILTERMODE);
            this.dataSourceFilterMode = DataSourceFilterMode.valueOf(dataSourceFilterModeValue);
        }
        if (!value.isNull(TAG_DATASOURCE_FILTER)) {
            this.dataSourceFilter = value.getString(TAG_DATASOURCE_FILTER);
        }
        if (!value.isNull(TAG_DATASOURCE_VALUES)) {
            this.loadDataSourceValues(value);
        }
        if (!value.isNull(TAG_DEFAULTVALUE_FILTERMODE)) {
            int defaultValueFilterModelValue = value.getInt(TAG_DEFAULTVALUE_FILTERMODE);
            this.defaultValueFilterMode = ParameterDefaultValueFilterMode.valueOf(defaultValueFilterModelValue);
        }
        if (!value.isNull(TAG_DEFAULTMAXVALUE_FILTERMODE)) {
            this.defaultMaxValueFilterMode = ParameterDefaultValueFilterMode.valueOf(value.getInt(TAG_DEFAULTMAXVALUE_FILTERMODE));
        }
        if (!value.isNull(TAG_DEFAULTVALUE_FILTERMODE_EXTEND)) {
            this.defaultValueFilterModeExtend = value.getString(TAG_DEFAULTVALUE_FILTERMODE_EXTEND);
        }
        if (!value.isNull(TAG_DEFAULTMAXVALUE_FILTERMODE_EXTEND)) {
            this.defaultMaxValueFilterModeExtend = value.getString(TAG_DEFAULTMAXVALUE_FILTERMODE_EXTEND);
        }
        if (!value.isNull(TAG_DEFAULTVALUE_FILTER)) {
            this.defaultValueFilter = value.getString(TAG_DEFAULTVALUE_FILTER);
        }
        if (!value.isNull(TAG_DEFAULTMAXVALUE_FILTER)) {
            this.defaultMaxValueFilter = value.getString(TAG_DEFAULTMAXVALUE_FILTER);
        }
        int noneDsDefValueFilterModeValue = value.optInt(TAG_NONEDSDEFAULTVALUEFILTERMODE, NoneDsDefValueFilterMode.APPOINT.value());
        this.noneDsDefValueFilterMode = NoneDsDefValueFilterMode.valueOf(noneDsDefValueFilterModeValue);
        this.noneDsDefMaxValueFilterMode = NoneDsDefValueFilterMode.valueOf(value.optInt(TAG_NONEDSDEFAULTMAXVALUE_FILTERMODE, NoneDsDefValueFilterMode.APPOINT.value()));
        this.loadDefaultValues(value);
        if (!value.isNull(TAG_DIMTREE)) {
            JSONArray array = value.getJSONArray(TAG_DIMTREE);
            for (int i = 0; i < array.length(); ++i) {
                this.choiceDimTreeNames.add(array.getString(i));
            }
        }
    }

    public String getDefaultValueFilterModeExtend() {
        return this.defaultValueFilterModeExtend;
    }

    public void setDefaultValueFilterModeExtend(String defaultValueFilterModeExtend) {
        this.defaultValueFilterModeExtend = defaultValueFilterModeExtend;
    }

    public String getDefaultMaxValueFilterModeExtend() {
        return this.defaultMaxValueFilterModeExtend;
    }

    public void setDefaultMaxValueFilterModeExtend(String defaultMaxValueFilterModeExtend) {
        this.defaultMaxValueFilterModeExtend = defaultMaxValueFilterModeExtend;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ParameterModel)) {
            return false;
        }
        ParameterModel parameterModel = (ParameterModel)obj;
        if (!parameterModel.getName().equals(this.getName())) {
            return false;
        }
        return StringUtils.isNotEmpty((String)parameterModel._getGuid()) && StringUtils.isNotEmpty((String)this.guid) && parameterModel._getGuid().equals(this.guid);
    }

    public int hashCode() {
        if (StringUtils.isEmpty((String)this.guid)) {
            return super.hashCode();
        }
        return this.guid.hashCode();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void loadDefaultValues(JSONObject value) throws JSONException {
        List<DataSourceValueModel> defaultValueModels;
        Object defalutValueJS;
        if (!value.isNull(TAG_DEFAULT_VALUES)) {
            if (this.dataSourceModel != null) {
                defalutValueJS = value.get(TAG_DEFAULT_VALUES);
                if (defalutValueJS instanceof JSONArray) {
                    this.defaultValues = defaultValueModels = DataSourceUtils.jsonArray2DataSourceValueModels(defalutValueJS);
                } else {
                    if (!(defalutValueJS instanceof JSONObject)) throw new JSONException("\u53c2\u6570\u9ed8\u8ba4\u503c\u683c\u5f0f\u9519\u8bef");
                    ParameterWidgetType widgetType = ParameterWidgetType.valueOf(value.getInt(TAG_WIDGETTYPE));
                    if (widgetType == ParameterWidgetType.SMARTSELECTOR) {
                        SmartSelector ss = new SmartSelector();
                        ss.load((JSONObject)defalutValueJS);
                        this.defaultValues = ss;
                    } else {
                        this.defaultValues = DataSourceUtils.json2MemoryDataSet((JSONObject)defalutValueJS);
                    }
                }
            } else if (this.dataType == DataType.BOOLEAN) {
                try {
                    this.defaultValues = value.optBoolean(TAG_DEFAULT_VALUES);
                }
                catch (Exception e) {
                    this.defaultValues = Boolean.FALSE;
                }
            } else {
                this.defaultValues = value.get(TAG_DEFAULT_VALUES);
            }
        }
        if (value.isNull(TAG_DEFAULT_MAX_VALUES)) return;
        if (this.dataSourceModel != null) {
            defalutValueJS = value.get(TAG_DEFAULT_MAX_VALUES);
            if (defalutValueJS instanceof JSONArray) {
                this.defaultMaxValues = defaultValueModels = DataSourceUtils.jsonArray2DataSourceValueModels(defalutValueJS);
                return;
            } else {
                if (!(defalutValueJS instanceof JSONObject)) throw new JSONException("\u53c2\u6570\u9ed8\u8ba4\u503c\u683c\u5f0f\u9519\u8bef");
                if (this.getWidgetType() == ParameterWidgetType.SMARTSELECTOR) {
                    SmartSelector ss = new SmartSelector();
                    ss.load((JSONObject)defalutValueJS);
                    this.defaultValues = ss;
                    return;
                } else {
                    this.defaultMaxValues = DataSourceUtils.json2MemoryDataSet((JSONObject)defalutValueJS);
                }
            }
            return;
        } else if (this.dataType == DataType.BOOLEAN) {
            try {
                this.defaultMaxValues = value.optBoolean(TAG_DEFAULT_MAX_VALUES);
                return;
            }
            catch (Exception e) {
                this.defaultMaxValues = Boolean.FALSE;
            }
            return;
        } else {
            this.defaultMaxValues = value.get(TAG_DEFAULT_MAX_VALUES);
        }
    }

    private void loadDataSourceValues(JSONObject value) throws JSONException {
        if (!value.isNull(TAG_DATASOURCE_VALUES)) {
            if (this.dataSourceModel != null) {
                Object dsJSValue = value.get(TAG_DATASOURCE_VALUES);
                if (dsJSValue instanceof JSONArray) {
                    List<DataSourceValueModel> dataSourceValueModels;
                    this.dataSourceValues = dataSourceValueModels = DataSourceUtils.jsonArray2DataSourceValueModels(dsJSValue);
                } else if (dsJSValue instanceof JSONObject) {
                    this.dataSourceValues = DataSourceUtils.json2MemoryDataSet((JSONObject)dsJSValue);
                }
            } else {
                this.dataSourceValues = value.get(TAG_DATASOURCE_VALUES);
            }
        }
    }

    private void loadDataSource(JSONObject value) throws JSONException {
        if (!value.isNull(TAG_DATASOURCE)) {
            DataSourceModel dataSourceModel;
            JSONObject dataSourceJsObj = value.getJSONObject(TAG_DATASOURCE);
            String dataSourceType = DataSourceModel.loadDataSourceType(dataSourceJsObj);
            DataSourceModelFactory dataSourceModelFactory = DataSourceFactoryManager.getDataSourceModelFactory(dataSourceType);
            if (dataSourceModelFactory == null) {
                return;
            }
            if (dataSourceModelFactory instanceof DataSourceModelFactoryEx) {
                DataSourceModelFactoryEx dataSourceModelFactoryEx = (DataSourceModelFactoryEx)dataSourceModelFactory;
                dataSourceModel = dataSourceModelFactoryEx.createDataSourceModel(dataSourceType);
            } else {
                dataSourceModel = dataSourceModelFactory.createDataSourceModel();
            }
            if (dataSourceModel != null) {
                dataSourceModel.load(dataSourceJsObj);
                this.setDataSourceModel(dataSourceModel);
            }
        }
    }

    private void loadBasicInfo(JSONObject value) throws JSONException {
        if (!value.isNull(TAG_TITLE)) {
            this.title = value.getString(TAG_TITLE);
        }
        if (!value.isNull(TAG_NAME)) {
            this.name = value.getString(TAG_NAME);
            if (StringUtils.isNotEmpty((String)this.name)) {
                this.name = this.name.toUpperCase();
            }
        }
        if (!value.isNull(TAG_GUID)) {
            this.guid = value.getString(TAG_GUID);
        }
        int dataTypeValue = value.optInt(TAG_DATATYPE);
        this.dataType = DataType.valueOf(dataTypeValue);
        if (!value.isNull(TAG_MAXVALUE)) {
            this.maxValue = value.get(TAG_MAXVALUE);
        }
        if (!value.isNull(TAG_MINVALUE)) {
            this.minValue = value.get(TAG_MINVALUE);
        }
        if (!value.isNull(TAG_ISRANGECLONED)) {
            this.isRangeCloned = value.getBoolean(TAG_ISRANGECLONED);
        }
        if (!value.isNull(TAG_ISPUBLIC)) {
            this.isPublic = value.getBoolean(TAG_ISPUBLIC);
        }
        if (!value.isNull(TAG_ALIAS)) {
            this.alias = value.getString(TAG_ALIAS);
        }
        if (!value.isNull(TAG_LASTMODIFIEDTIME)) {
            this.lastModifiedTime = value.getString(TAG_LASTMODIFIEDTIME);
        }
        if (!value.isNull(TAG_DESC)) {
            this.desc = value.getString(TAG_DESC);
        }
    }

    public ParameterModel clone() {
        try {
            ParameterModel parameterModel = (ParameterModel)super.clone();
            if (this.defaultValues != null) {
                parameterModel.setDefaultValues(this.cloneValues(this.defaultValues));
            }
            if (this.dataSourceValues != null) {
                parameterModel.setDataSourceValues(this.cloneValues(this.dataSourceValues));
            }
            if (this.dataSourceModel != null) {
                parameterModel.setDataSourceModel(this.dataSourceModel.clone());
            }
            parameterModel.cascadeParameters = new ArrayList<String>();
            parameterModel.cascadeParameters.addAll(this.cascadeParameters);
            parameterModel.choiceDimTreeNames = new ArrayList<String>();
            parameterModel.choiceDimTreeNames.addAll(this.choiceDimTreeNames);
            if (this.owner != null) {
                parameterModel.owner = this.owner.clone();
            }
            return parameterModel;
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    private Object cloneValues(Object value) {
        if (value != null) {
            if (value instanceof MemoryDataSet) {
                return ((MemoryDataSet)value).clone();
            }
            if (value instanceof List) {
                List values = (List)value;
                ArrayList<DataSourceValueModel> cloneValues = new ArrayList<DataSourceValueModel>();
                for (DataSourceValueModel dataSourceValueModel : DataSourceUtils.adapt2DataSourceValues(values)) {
                    cloneValues.add(dataSourceValueModel.clone());
                }
                return cloneValues;
            }
            return value;
        }
        return value;
    }

    public ParameterModel simpleClone() {
        ParameterModel parameterModel = new ParameterModel();
        parameterModel._setGuid(Guid.newGuid());
        parameterModel.setName(this.name);
        parameterModel.setTitle(this.title);
        parameterModel.setDataSourceFilterMode(this.dataSourceFilterMode);
        parameterModel.setDataSourceValues(this.dataSourceValues);
        parameterModel.setDataSourceFilter(this.dataSourceFilter);
        parameterModel.setDefaultValueFilterMode(this.defaultValueFilterMode);
        parameterModel.setDefaultMaxValueFilterMode(this.defaultMaxValueFilterMode);
        parameterModel.setDefaultValueFilterModeExtend(this.defaultValueFilterModeExtend);
        parameterModel.setDefaultMaxValueFilterModeExtend(this.defaultMaxValueFilterModeExtend);
        parameterModel.setDataType(this.dataType);
        parameterModel.setDefaultValues(this.defaultValues);
        parameterModel.setDefaultMaxValues(this.defaultMaxValues);
        parameterModel.setDefalutValueFilter(this.defaultValueFilter);
        parameterModel.setDefaultMaxValueFilter(this.defaultMaxValueFilter);
        parameterModel.setMinValue(this.minValue);
        parameterModel.setMaxValue(this.maxValue);
        parameterModel.setNoneDsDefValueFilterMode(this.noneDsDefValueFilterMode);
        parameterModel.setNoneDsDefMaxValueFilterMode(this.noneDsDefMaxValueFilterMode);
        parameterModel.setCascadeParameters(this.cascadeParameters);
        parameterModel.setSelectMode(this.selectMode);
        parameterModel.setWigetType(this.widgetType);
        parameterModel.setOnlyLeafSelectable(this.onlyLeafSelectable);
        parameterModel.setCrossLeafEnable(this.crossLeafEnable);
        parameterModel.setTitleVisible(this.titleVisible);
        parameterModel.setHidden(this.hidden);
        parameterModel.setOrderReverse(this.orderReverse);
        parameterModel.setShowCode(this.showCode);
        parameterModel.setNullable(this.nullable);
        parameterModel.setRangeParameter(this.rangeParameter);
        parameterModel.setShowSearchWidget(this.showSearchWidget);
        parameterModel.setTag(this.tag);
        parameterModel.setPublic(this.isPublic);
        parameterModel.setWidth(this.width);
        parameterModel.setLastModifiedTime(this.lastModifiedTime);
        parameterModel.setCascadeAllChildren(this.cascadeAllChildren);
        parameterModel.setCascadeDirectChildren(this.cascadeDirectChildren);
        parameterModel.getChoiceDimTreeNames().addAll(this.choiceDimTreeNames);
        return parameterModel;
    }

    public boolean isNeedCascade() {
        return this.needCascade;
    }

    public void setNeedCascade(boolean needCascade) {
        this.needCascade = needCascade;
    }

    public boolean isCascadeAllChildren() {
        return this.cascadeAllChildren;
    }

    public void setCascadeAllChildren(boolean cascadeAllChildren) {
        this.cascadeAllChildren = cascadeAllChildren;
    }

    public boolean isCascadeDirectChildren() {
        return this.cascadeDirectChildren;
    }

    public void setCascadeDirectChildren(boolean cascadeDirectChildren) {
        this.cascadeDirectChildren = cascadeDirectChildren;
    }

    public List<String> getChoiceDimTreeNames() {
        return this.choiceDimTreeNames;
    }

    public boolean isTitleVisible() {
        return this.titleVisible;
    }

    public void setTitleVisible(boolean titleVisible) {
        this.titleVisible = titleVisible;
    }
}

