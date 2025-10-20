/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.engine.result;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.model.HyperlinkTargetMode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CellLinkInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private JSONObject data;
    private HyperlinkTargetMode targetMode;
    private int targetWidth;
    private int targetHeight;
    private List<String> measures = new ArrayList<String>();
    private static final String LINK_DATA = "data";
    private static final String LINK_TARGET_MODE = "targetMode";
    private static final String LINK_TARGET_WIDTH = "targetWidth";
    private static final String LINK_TARGET_HEIGHT = "targetHeight";
    private static final String LINK_MEASURES = "measures";

    public JSONObject getData() {
        return this.data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public HyperlinkTargetMode getTargetMode() {
        return this.targetMode;
    }

    public void setTargetMode(HyperlinkTargetMode targetMode) {
        this.targetMode = targetMode;
    }

    public int getTargetWidth() {
        return this.targetWidth;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public int getTargetHeight() {
        return this.targetHeight;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    public List<String> getMeasures() {
        return this.measures;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(LINK_DATA, this.data == null ? JSONObject.NULL : this.data);
        json.put(LINK_TARGET_MODE, (Object)this.targetMode);
        if (this.targetMode == HyperlinkTargetMode.MODEL_WINDOW) {
            json.put(LINK_TARGET_WIDTH, this.targetWidth);
            json.put(LINK_TARGET_HEIGHT, this.targetHeight);
        }
        if (!this.measures.isEmpty()) {
            json.put(LINK_MEASURES, (Object)new JSONArray(this.measures));
        }
        return json;
    }

    public void fromJSON(JSONObject json) {
        this.data = json.optJSONObject(LINK_DATA);
        this.targetMode = (HyperlinkTargetMode)json.getEnum(HyperlinkTargetMode.class, LINK_TARGET_MODE);
        if (this.targetMode == HyperlinkTargetMode.MODEL_WINDOW) {
            this.targetWidth = json.getInt(LINK_TARGET_WIDTH);
            this.targetHeight = json.getInt(LINK_TARGET_HEIGHT);
        }
        this.measures.clear();
        JSONArray arr = json.optJSONArray(LINK_MEASURES);
        if (arr != null) {
            for (int i = 0; i < arr.length(); ++i) {
                this.measures.add(arr.getString(i));
            }
        }
    }

    public CellLinkInfo clone() {
        CellLinkInfo linkInfo;
        try {
            linkInfo = (CellLinkInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
        linkInfo.measures = new ArrayList<String>(this.measures);
        return linkInfo;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[data : ").append(this.data).append(", target : ").append((Object)this.targetMode);
        if (this.targetMode == HyperlinkTargetMode.MODEL_WINDOW) {
            buffer.append('(').append(this.targetWidth).append(", ").append(this.targetHeight).append(')');
        }
        if (!this.measures.isEmpty()) {
            buffer.append(", measures : ").append(this.measures);
        }
        buffer.append(']');
        return buffer.toString();
    }
}

