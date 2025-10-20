/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.parameter.model;

import com.jiuqi.bi.parameter.model.ParameterSelectMode;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class ParameterImportOption {
    private ParameterSelectMode selectMode = ParameterSelectMode.SINGLE;
    private boolean onlyLeafSelectable;
    private boolean hidden;
    private boolean orderReverse;
    private boolean showCode;
    private boolean nullable;
    private boolean showSearchWidget;
    private boolean rangeParameter;
    private boolean synPublicParameter = true;
    private boolean crossLeafEnable = true;
    private static final String TAG_SELECTMODE = "selectMode";
    private static final String TAG_ONLYLEAFSELECTABLE = "onlyLeafSelectable";
    private static final String TAG_HIDDEN = "hidden";
    private static final String TAG_ORDERREVERSE = "orderReverse";
    private static final String TAG_SHOWCODE = "showcode";
    private static final String TAG_NULLABLE = "nullable";
    private static final String TAG_SHOWSEARCHWIDGET = "showSearchWidget";
    private static final String TAG_RANGE = "range";
    private static final String TAG_SYNPUBLICPARA = "synPublicPara";
    private static final String TAG_CROSSLEAFENABLE = "crossLeafEnable";

    public boolean isRangeParameter() {
        return this.rangeParameter;
    }

    public void setRangeParameter(boolean rangeParameter) {
        this.rangeParameter = rangeParameter;
    }

    public boolean isSynPublicParameter() {
        return this.synPublicParameter;
    }

    public void setSynPublicParameter(boolean synPublicParameter) {
        this.synPublicParameter = synPublicParameter;
    }

    public ParameterSelectMode getSelectMode() {
        return this.selectMode;
    }

    public void setSelectMode(ParameterSelectMode selectMode) {
        this.selectMode = selectMode;
    }

    public boolean isOnlyLeafSelectable() {
        return this.onlyLeafSelectable;
    }

    public void setOnlyLeafSelectable(boolean onlyLeafSelectable) {
        this.onlyLeafSelectable = onlyLeafSelectable;
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

    public boolean isNullable() {
        return this.nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isShowSearchWidget() {
        return this.showSearchWidget;
    }

    public void setShowSearchWidget(boolean showSearchWidget) {
        this.showSearchWidget = showSearchWidget;
    }

    public boolean isCrossLeafEnable() {
        return this.crossLeafEnable;
    }

    public void setCrossLeafEnable(boolean crossLeafEnable) {
        this.crossLeafEnable = crossLeafEnable;
    }

    public void toJson(JSONObject value) throws JSONException {
        value.put(TAG_SELECTMODE, this.selectMode.value());
        value.put(TAG_ONLYLEAFSELECTABLE, this.onlyLeafSelectable);
        value.put(TAG_HIDDEN, this.hidden);
        value.put(TAG_ORDERREVERSE, this.orderReverse);
        value.put(TAG_SHOWCODE, this.showCode);
        value.put(TAG_NULLABLE, this.nullable);
        value.put(TAG_SHOWSEARCHWIDGET, this.showSearchWidget);
        value.put(TAG_RANGE, this.rangeParameter);
        value.put(TAG_SYNPUBLICPARA, this.synPublicParameter);
        value.put(TAG_CROSSLEAFENABLE, this.crossLeafEnable);
    }

    public void fromJson(JSONObject value) throws JSONException {
        this.selectMode = ParameterSelectMode.valueOf(value.getInt(TAG_SELECTMODE));
        this.onlyLeafSelectable = value.getBoolean(TAG_ONLYLEAFSELECTABLE);
        this.hidden = value.getBoolean(TAG_HIDDEN);
        this.orderReverse = value.getBoolean(TAG_ORDERREVERSE);
        this.showCode = value.getBoolean(TAG_SHOWCODE);
        this.nullable = value.getBoolean(TAG_NULLABLE);
        this.showSearchWidget = value.getBoolean(TAG_SHOWSEARCHWIDGET);
        if (!value.isNull(TAG_RANGE)) {
            this.rangeParameter = value.getBoolean(TAG_RANGE);
        }
        if (!value.isNull(TAG_SYNPUBLICPARA)) {
            this.synPublicParameter = value.getBoolean(TAG_SYNPUBLICPARA);
        }
        if (!value.isNull(TAG_CROSSLEAFENABLE)) {
            this.crossLeafEnable = value.getBoolean(TAG_CROSSLEAFENABLE);
        }
    }
}

