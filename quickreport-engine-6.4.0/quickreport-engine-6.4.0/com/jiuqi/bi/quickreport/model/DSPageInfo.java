/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.QuickReportError;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class DSPageInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private String datasetName;
    private String groupField;
    private static final String PI_DSNAME = "datasetName";
    private static final String PI_GROUPFIELD = "groupField";

    public DSPageInfo() {
    }

    public DSPageInfo(String datasetName, String groupField) {
        this.datasetName = datasetName;
        this.groupField = groupField;
    }

    public String getDatasetName() {
        return this.datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getGroupField() {
        return this.groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }

    public String toString() {
        return this.groupField == null || this.groupField.isEmpty() ? this.datasetName : this.datasetName + "(" + this.groupField + ")";
    }

    public DSPageInfo clone() {
        try {
            return (DSPageInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(PI_DSNAME, (Object)this.datasetName);
        if (this.groupField != null) {
            json.put(PI_GROUPFIELD, (Object)this.groupField);
        }
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.datasetName = json.optString(PI_DSNAME);
        this.groupField = json.optString(PI_GROUPFIELD);
    }
}

