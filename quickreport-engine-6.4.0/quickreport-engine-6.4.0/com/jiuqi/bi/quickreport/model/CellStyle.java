/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.model.ConditionStyle;
import com.jiuqi.bi.quickreport.model.ValueConvertMode;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public final class CellStyle
extends ConditionStyle
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private int foregroundColor = -1;
    private int backgroundColor = -1;
    private int backgroundAlpha = 100;
    private int fontBold;
    private boolean underline;
    private ValueConvertMode valueMode = ValueConvertMode.NONE;
    private static final String CS_FOREGROUNDCOLOR = "foregroundColor";
    private static final String CS_BACKGROUNDCOLOR = "backgroundColor";
    private static final String CS_BACKGROUNDALPHA = "backgroundAlpha";
    private static final String CS_BOLD = "bold";
    private static final String CS_FONTBOLD = "fontBold";
    private static final String CS_UNDERLINE = "underline";
    private static final String CS_VALUEMODE = "valueMode";
    public static final int COLOR_DEFAULT = -1;
    public static final int STATE_NONE = -1;
    public static final int STATE_FALSE = 0;
    public static final int STATE_TRUE = 1;

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

    public int getBackgroundAlpha() {
        return this.backgroundAlpha;
    }

    public void setBackgroundAlpha(int backgroundAlpha) {
        this.backgroundAlpha = backgroundAlpha;
    }

    @Deprecated
    public boolean isBold() {
        return this.fontBold > 0;
    }

    @Deprecated
    public void setBold(boolean bold) {
        this.fontBold = bold ? 1 : 0;
    }

    public int getFontBold() {
        return this.fontBold;
    }

    public void setFontBold(int fontBold) {
        this.fontBold = fontBold;
    }

    @Deprecated
    public boolean isUnderline() {
        return this.underline;
    }

    @Deprecated
    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public ValueConvertMode getValueMode() {
        return this.valueMode;
    }

    public void setValueMode(ValueConvertMode valueMode) {
        this.valueMode = valueMode;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = super.toJSON();
        json.put(CS_FOREGROUNDCOLOR, this.foregroundColor);
        json.put(CS_BACKGROUNDCOLOR, this.backgroundColor);
        json.put(CS_BACKGROUNDALPHA, this.backgroundAlpha);
        json.put(CS_FONTBOLD, this.fontBold);
        json.put(CS_UNDERLINE, this.underline);
        json.put(CS_VALUEMODE, (Object)this.valueMode);
        return json;
    }

    @Override
    public void fromJSON(JSONObject json) throws JSONException {
        super.fromJSON(json);
        this.foregroundColor = json.optInt(CS_FOREGROUNDCOLOR);
        this.backgroundColor = json.optInt(CS_BACKGROUNDCOLOR);
        this.backgroundAlpha = json.optInt(CS_BACKGROUNDALPHA, 100);
        this.fontBold = json.has(CS_FONTBOLD) ? json.getInt(CS_FONTBOLD) : (json.has(CS_BOLD) ? (json.getBoolean(CS_BOLD) ? 1 : 0) : -1);
        this.underline = json.optBoolean(CS_UNDERLINE);
        this.valueMode = (ValueConvertMode)json.optEnum(ValueConvertMode.class, CS_VALUEMODE, (Enum)ValueConvertMode.NONE);
    }
}

