/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.framework.parameter.model;

import com.jiuqi.nvwa.framework.parameter.datasource.AbstractParameterDataSourceFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterLevelMemberCheckMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterOwner;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.ParameterWidgetType;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterRangeValueConfig;
import com.jiuqi.nvwa.framework.parameter.model.config.ParameterValueConfig;
import org.json.JSONException;
import org.json.JSONObject;

public class ParameterModel
implements Cloneable {
    private String guid;
    private String name;
    private String title;
    private String description;
    private AbstractParameterDataSourceModel datasource;
    private AbstractParameterValueConfig valueConfig;
    private ParameterSelectMode selectMode = ParameterSelectMode.SINGLE;
    private int widgetType = ParameterWidgetType.DEFAULT.value();
    private ParameterLevelMemberCheckMode levelCheckMode = ParameterLevelMemberCheckMode.SELF;
    private boolean onlyLeafSelectable;
    private boolean crossLeafEnable;
    private boolean showSearchWidget = true;
    private boolean hidden;
    private boolean titleVisible = true;
    private boolean orderReverse;
    private boolean showCode;
    private boolean switchShowCode;
    private boolean nullable = true;
    private String messageAlias;
    private int width;
    private boolean global = true;
    private ParameterOwner owner;

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AbstractParameterDataSourceModel getDatasource() {
        return this.datasource;
    }

    public void setDatasource(AbstractParameterDataSourceModel datasource) {
        this.datasource = datasource;
    }

    public AbstractParameterValueConfig getValueConfig() {
        return this.valueConfig;
    }

    public void setValueConfig(AbstractParameterValueConfig valueConfig) {
        this.valueConfig = valueConfig;
    }

    public ParameterSelectMode getSelectMode() {
        return this.selectMode;
    }

    public void setSelectMode(ParameterSelectMode selectMode) {
        this.selectMode = selectMode;
    }

    public int getWidgetType() {
        return this.widgetType;
    }

    public void setWidgetType(int widgetType) {
        this.widgetType = widgetType;
    }

    public boolean isOnlyLeafSelectable() {
        return this.onlyLeafSelectable;
    }

    public void setOnlyLeafSelectable(boolean onlyLeafSelectable) {
        this.onlyLeafSelectable = onlyLeafSelectable;
    }

    public boolean isCrossLeafEnable() {
        return this.crossLeafEnable;
    }

    public void setCrossLeafEnable(boolean crossLeafEnable) {
        this.crossLeafEnable = crossLeafEnable;
    }

    public void setLevelCheckMode(ParameterLevelMemberCheckMode levelCheckMode) {
        this.levelCheckMode = levelCheckMode;
    }

    public ParameterLevelMemberCheckMode getLevelCheckMode() {
        return this.levelCheckMode;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
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

    public boolean isTitleVisible() {
        return this.titleVisible;
    }

    public void setTitleVisible(boolean titleVisible) {
        this.titleVisible = titleVisible;
    }

    public String getMessageAlias() {
        return this.messageAlias;
    }

    public void setMessageAlias(String messageAlias) {
        this.messageAlias = messageAlias;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public boolean isGlobal() {
        return this.global;
    }

    public boolean isShowSearchWidget() {
        return this.showSearchWidget;
    }

    public void setShowSearchWidget(boolean showSearchWidget) {
        this.showSearchWidget = showSearchWidget;
    }

    public int getDataType() {
        return this.getDatasource().getDataType();
    }

    public boolean hasDatasource() {
        return this.datasource != null && !this.datasource.getType().equals("com.jiuqi.nvwa.parameter.ds.none");
    }

    public void setOwner(ParameterOwner owner) {
        this.owner = owner;
    }

    public ParameterOwner getOwner() {
        return this.owner;
    }

    public boolean isRangeParameter() {
        return this.selectMode == ParameterSelectMode.RANGE;
    }

    public void fromJson(JSONObject json) throws JSONException {
        this.guid = json.optString("guid");
        this.name = json.optString("name");
        this.title = json.optString("title");
        this.description = json.optString("description");
        this.global = json.optBoolean("global", true);
        JSONObject datasourceJson = json.optJSONObject("datasource");
        if (datasourceJson != null) {
            String datasourceType = datasourceJson.optString("type", null);
            if (datasourceType == null) {
                datasourceType = datasourceJson.optString("datasourceType");
            }
            ParameterDataSourceManager mgr = ParameterDataSourceManager.getInstance();
            AbstractParameterDataSourceFactory factory = mgr.getFactory(datasourceType);
            this.datasource = factory.newInstance();
            this.datasource.fromJson(datasourceJson);
        }
        this.selectMode = ParameterSelectMode.valueOf(json.optInt("selectMode"));
        this.valueConfig = this.selectMode == ParameterSelectMode.RANGE ? new ParameterRangeValueConfig() : new ParameterValueConfig();
        this.valueConfig.fromJson(json.optJSONObject("valueConfig"), this.datasource);
        this.widgetType = json.optInt("widgetType", 0);
        this.onlyLeafSelectable = json.optBoolean("onlyLeafSelectable");
        this.crossLeafEnable = json.optBoolean("crossLeafEnable");
        this.levelCheckMode = ParameterLevelMemberCheckMode.valueOf(json.optInt("levelCheckMode"));
        this.showSearchWidget = json.optBoolean("showSearchWidget");
        this.hidden = json.optBoolean("hidden");
        this.orderReverse = json.optBoolean("orderReverse");
        this.showCode = json.optBoolean("showCode");
        this.switchShowCode = json.optBoolean("switchShowCode");
        this.nullable = json.optBoolean("nullable");
        this.messageAlias = json.optString("messageAlias");
        this.width = json.optInt("width");
        this.titleVisible = json.optBoolean("titleVisible", true);
        if (!json.isNull("owner")) {
            JSONObject j = json.getJSONObject("owner");
            this.owner = new ParameterOwner();
            this.owner.fromJson(j);
        }
    }

    public void toJson(JSONObject json) throws JSONException {
        json.put("guid", (Object)this.guid);
        json.put("name", (Object)this.name);
        json.put("title", (Object)this.title);
        json.put("global", this.global);
        if (this.description != null) {
            json.put("description", (Object)this.description);
        }
        JSONObject datasourceJson = new JSONObject();
        this.datasource.toJson(datasourceJson);
        json.put("datasource", (Object)datasourceJson);
        json.put("selectMode", this.selectMode.value());
        JSONObject valueCfg = new JSONObject();
        this.valueConfig.toJson(valueCfg, this.datasource);
        json.put("valueConfig", (Object)valueCfg);
        json.put("widgetType", this.widgetType);
        json.put("onlyLeafSelectable", this.onlyLeafSelectable);
        json.put("crossLeafEnable", this.crossLeafEnable);
        json.put("levelCheckMode", this.levelCheckMode.value());
        json.put("showSearchWidget", this.showSearchWidget);
        json.put("hidden", this.hidden);
        json.put("orderReverse", this.orderReverse);
        json.put("showCode", this.showCode);
        json.put("switchShowCode", this.switchShowCode);
        json.put("nullable", this.nullable);
        if (this.messageAlias != null) {
            json.put("messageAlias", (Object)this.messageAlias);
        }
        json.put("width", this.width);
        json.put("titleVisible", this.titleVisible);
        if (this.owner != null) {
            JSONObject j = new JSONObject();
            this.owner.toJson(j);
            json.put("owner", (Object)j);
        }
    }

    public ParameterModel clone() {
        ParameterModel model;
        try {
            model = (ParameterModel)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        model.datasource = this.datasource.clone();
        model.valueConfig = this.valueConfig.clone();
        if (this.owner != null) {
            model.owner = this.owner.clone();
        }
        return model;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("{");
        buf.append("name:").append(this.name).append(", ");
        buf.append("title:").append(this.title).append(", ");
        buf.append("type:").append(this.datasource == null ? "null" : this.datasource.getType());
        buf.append("}");
        return buf.toString();
    }
}

