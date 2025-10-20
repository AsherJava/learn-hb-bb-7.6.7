/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.grid.GridPallette
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.grid.GridPallette;
import com.jiuqi.bi.quickreport.model.ConditionStyle;
import java.io.Serializable;
import org.json.JSONObject;

public final class DataBarStyle
extends ConditionStyle
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    public static final int COLOR_TRANSPARENT = 0x1FFFFFFF;
    public static final int COLOR_DEFAULT = GridPallette.COLOR_PALLETTE_CELL[19];
    private int foregroundColor = COLOR_DEFAULT;
    private int backgroundColor = 0x1FFFFFFF;
    private static final String DBS_FOREGROUND = "foreground";
    private static final String DBS_BACKGROUND = "background";

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

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put(DBS_FOREGROUND, this.foregroundColor);
        json.put(DBS_BACKGROUND, this.backgroundColor);
        return json;
    }

    @Override
    public void fromJSON(JSONObject json) {
        super.fromJSON(json);
        this.foregroundColor = json.getInt(DBS_FOREGROUND);
        this.backgroundColor = json.optInt(DBS_BACKGROUND, 0x1FFFFFFF);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[').append(Integer.toHexString(this.foregroundColor)).append(", ").append(Integer.toHexString(this.backgroundColor));
        if (this.getCondition() != null) {
            buffer.append(", ").append(this.getCondition());
        }
        buffer.append(']');
        return buffer.toString();
    }
}

