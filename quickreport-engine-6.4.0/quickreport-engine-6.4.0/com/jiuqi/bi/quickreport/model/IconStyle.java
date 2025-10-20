/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.model.ConditionStyle;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public final class IconStyle
extends ConditionStyle
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private String iconName;
    private int position;
    private static final String IS_ICONNAME = "iconName";
    private static final String IS_POSITION = "position";
    public static final int POS_LEFT = 0;
    public static final int POS_RIGHT = 1;

    public String getIconName() {
        return this.iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = super.toJSON();
        json.put(IS_ICONNAME, (Object)this.iconName);
        json.put(IS_POSITION, this.position);
        return json;
    }

    @Override
    public void fromJSON(JSONObject json) throws JSONException {
        super.fromJSON(json);
        this.iconName = json.optString(IS_ICONNAME);
        this.position = json.optInt(IS_POSITION, 0);
    }
}

