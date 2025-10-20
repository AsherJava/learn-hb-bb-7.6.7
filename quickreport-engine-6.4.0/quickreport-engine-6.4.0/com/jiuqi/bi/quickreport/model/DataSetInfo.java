/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class DataSetInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1416075814338623329L;
    private static final String DATASET_ID = "id";
    private static final String DATASET_TYPE = "type";
    private static final String DATASET_TRACE = "traceFields";
    private String id;
    private String type;
    private List<String> traceFields = new ArrayList<String>();

    public DataSetInfo() {
    }

    public DataSetInfo(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTraceFields() {
        return this.traceFields;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
        result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        DataSetInfo other = (DataSetInfo)obj;
        if (this.id == null ? other.id != null : !this.id.equals(other.id)) {
            return false;
        }
        return !(this.type == null ? other.type != null : !this.type.equals(other.type));
    }

    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String toString() {
        return this.id;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(DATASET_ID, (Object)this.id);
        json.put(DATASET_TYPE, (Object)this.type);
        json.put(DATASET_TRACE, this.traceFields);
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.id = json.isNull(DATASET_ID) ? null : json.optString(DATASET_ID);
        this.type = json.isNull(DATASET_TYPE) ? null : json.optString(DATASET_TYPE);
        JSONArray traces = json.optJSONArray(DATASET_TRACE);
        if (traces != null) {
            for (int i = 0; i < traces.length(); ++i) {
                this.traceFields.add(traces.getString(i));
            }
        }
    }
}

