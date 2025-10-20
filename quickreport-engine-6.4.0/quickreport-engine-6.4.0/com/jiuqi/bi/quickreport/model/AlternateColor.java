/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.QuickReportError;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AlternateColor
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private boolean enabled;
    private String range;
    private List<String> colors = new ArrayList<String>(2);
    private static final String AC_ENABLED = "enabled";
    private static final String AC_RANGE = "range";
    private static final String AC_COLORS = "colors";

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRange() {
        return this.range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<String> getColors() {
        return this.colors;
    }

    public AlternateColor clone() {
        AlternateColor result;
        try {
            result = (AlternateColor)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
        result.colors = new ArrayList<String>(this.colors);
        return result;
    }

    public String toString() {
        if (!this.enabled) {
            return "[disabled]";
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append("[range = ").append(this.range).append(", colors = ").append(String.join((CharSequence)",", this.colors)).append("]");
        return buffer.toString();
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(AC_ENABLED, this.enabled);
        json.put(AC_RANGE, (Object)this.range);
        json.put(AC_COLORS, this.colors);
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.enabled = json.optBoolean(AC_ENABLED);
        this.range = json.optString(AC_RANGE);
        JSONArray arr = json.optJSONArray(AC_COLORS);
        this.colors.clear();
        if (arr != null) {
            for (int i = 0; i < arr.length(); ++i) {
                this.colors.add(arr.getString(i));
            }
        }
    }
}

