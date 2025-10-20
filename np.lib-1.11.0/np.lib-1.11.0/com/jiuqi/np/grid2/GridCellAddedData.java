/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.np.grid2;

import com.jiuqi.np.grid2.LogUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class GridCellAddedData
extends JSONObject {
    private static final String EDITOR_KEY = "editor";
    private static final String FORMATTER_KEY = "formatter";
    private static final String CLIENTDATA_KEY = "clientData";
    private static final String EDITORFACE_KEY = "editorFace";
    private static final String DATAEX_KEY = "dataEx";

    public GridCellAddedData() {
    }

    public GridCellAddedData(String value) throws JSONException {
        super(value);
    }

    public int getEditorFace() {
        int editorFace = 0;
        if (!this.isNull(EDITORFACE_KEY)) {
            try {
                editorFace = this.getInt(EDITORFACE_KEY);
            }
            catch (JSONException jSONException) {
                // empty catch block
            }
        }
        return editorFace;
    }

    public String getEditor() {
        String editor = null;
        if (!this.isNull(EDITOR_KEY)) {
            try {
                editor = this.getString(EDITOR_KEY);
            }
            catch (JSONException jSONException) {
                // empty catch block
            }
        }
        return editor;
    }

    public JSONObject getClientData() {
        JSONObject clientData = null;
        if (!this.isNull(CLIENTDATA_KEY)) {
            try {
                clientData = this.getJSONObject(CLIENTDATA_KEY);
            }
            catch (JSONException jSONException) {
                // empty catch block
            }
        }
        return clientData;
    }

    public String getFormatter() {
        String formatter = null;
        if (!this.isNull(FORMATTER_KEY)) {
            try {
                formatter = this.getString(FORMATTER_KEY);
            }
            catch (JSONException jSONException) {
                // empty catch block
            }
        }
        return formatter;
    }

    public void setEditor(String value) {
        this.setValue(EDITOR_KEY, value);
    }

    public void setFormatter(String value) {
        this.setValue(FORMATTER_KEY, value);
    }

    public void setClientData(JSONObject value) {
        this.setValue(CLIENTDATA_KEY, value);
    }

    public void setEditorFace(int value) {
        this.setValue(EDITORFACE_KEY, value);
    }

    public void setDataEx(JSONObject obj) {
        this.setValue(DATAEX_KEY, obj);
    }

    public JSONObject getDataEx() {
        JSONObject obj = null;
        if (!this.isNull(DATAEX_KEY)) {
            try {
                obj = this.getJSONObject(DATAEX_KEY);
            }
            catch (JSONException jSONException) {
                // empty catch block
            }
        }
        return obj;
    }

    private void setValue(String key, Object value) {
        try {
            this.put(key, value);
        }
        catch (JSONException e) {
            LogUtil.log(e);
        }
    }
}

