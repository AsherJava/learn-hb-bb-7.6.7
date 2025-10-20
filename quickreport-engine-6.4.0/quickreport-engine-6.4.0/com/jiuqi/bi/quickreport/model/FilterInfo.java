/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public final class FilterInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private int position;
    private String formula;
    private static final String FILTER_POSITION = "position";
    private static final String FILTER_FORMULA = "formula";

    public FilterInfo() {
    }

    public FilterInfo(int position, String formula) {
        this.position = position;
        this.formula = formula;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.getMessage());
        }
    }

    public String toString() {
        return this.position + " : " + this.formula;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject info = new JSONObject();
        info.put(FILTER_POSITION, this.position);
        info.put(FILTER_FORMULA, (Object)this.formula);
        return info;
    }

    public void fromJSON(JSONObject info) throws JSONException {
        this.position = info.optInt(FILTER_POSITION);
        this.formula = info.optString(FILTER_FORMULA);
    }
}

