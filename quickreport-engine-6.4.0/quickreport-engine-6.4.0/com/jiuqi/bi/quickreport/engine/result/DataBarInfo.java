/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.model.DataBarDispStyle;
import com.jiuqi.bi.quickreport.model.DataBarMode;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public final class DataBarInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private DataBarMode mode = DataBarMode.AUTO;
    private DataBarDispStyle dispStyle = DataBarDispStyle.BAND;
    private int foregroundColor;
    private int backgroundColor;
    private boolean gradual;
    private boolean dataBarOnly;
    private double minValue;
    private double maxValue;
    private static final String BAR_MODE = "mode";
    private static final String BAR_DISP = "dispStyle";
    private static final String BAR_FOREGROUND = "foreground";
    private static final String BAR_BACKGROUND = "background";
    @Deprecated
    private static final String BAR_COLOR = "color";
    private static final String BAR_GRADUAL = "gradual";
    private static final String BAR_BARONLY = "dataBarOnly";
    private static final String BAR_MIN = "min";
    private static final String BAR_MAX = "max";

    public DataBarMode getMode() {
        return this.mode;
    }

    public void setMode(DataBarMode mode) {
        this.mode = mode;
    }

    public DataBarDispStyle getDispStyle() {
        return this.dispStyle;
    }

    public void setDispStyle(DataBarDispStyle dispStyle) {
        this.dispStyle = dispStyle;
    }

    @Deprecated
    public int getColor() {
        return this.foregroundColor;
    }

    @Deprecated
    public void setColor(int color) {
        this.foregroundColor = color;
    }

    public int getForegroundColor() {
        return this.foregroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isGradual() {
        return this.gradual;
    }

    public void setGradual(boolean gradual) {
        this.gradual = gradual;
    }

    public boolean isDataBarOnly() {
        return this.dataBarOnly;
    }

    public void setDataBarOnly(boolean dataBarOnly) {
        this.dataBarOnly = dataBarOnly;
    }

    public double getMinValue() {
        return this.minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(BAR_MODE, (Object)this.mode.toString());
        json.put(BAR_DISP, (Object)this.dispStyle);
        json.put(BAR_FOREGROUND, this.foregroundColor);
        json.put(BAR_BACKGROUND, this.backgroundColor);
        json.put(BAR_GRADUAL, this.gradual);
        json.put(BAR_BARONLY, this.dataBarOnly);
        json.put(BAR_MIN, this.minValue);
        json.put(BAR_MAX, this.maxValue);
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.mode = DataBarMode.valueOf(json.optString(BAR_MODE));
        this.dispStyle = (DataBarDispStyle)json.optEnum(DataBarDispStyle.class, BAR_DISP, (Enum)DataBarDispStyle.BAND);
        this.foregroundColor = json.has(BAR_FOREGROUND) ? json.getInt(BAR_FOREGROUND) : json.getInt(BAR_COLOR);
        this.backgroundColor = json.optInt(BAR_BACKGROUND, 0x1FFFFFFF);
        this.gradual = json.optBoolean(BAR_GRADUAL);
        this.dataBarOnly = json.optBoolean(BAR_BARONLY);
        this.minValue = json.optDouble(BAR_MIN);
        this.maxValue = json.optDouble(BAR_MAX);
    }

    public DataBarInfo clone() {
        try {
            return (DataBarInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
    }

    public String toString() {
        return (Object)((Object)this.mode) + "@" + this.foregroundColor + "/" + this.backgroundColor + " [" + this.minValue + ", " + this.maxValue + "]";
    }
}

