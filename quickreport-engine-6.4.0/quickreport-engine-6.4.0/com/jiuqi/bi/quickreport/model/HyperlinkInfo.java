/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.QuickReportError;
import com.jiuqi.bi.quickreport.model.HyperlinkTargetMode;
import com.jiuqi.bi.quickreport.model.HyperlinkType;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public final class HyperlinkInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    private HyperlinkType type = HyperlinkType.NONE;
    private JSONObject data;
    private HyperlinkTargetMode targetMode = HyperlinkTargetMode.BLANK;
    private String targetTitle;
    private int targetWidth;
    private int targetHeight;
    private String filter;
    private static final String LINK_TYPE = "type";
    private static final String LINK_DATA = "data";
    private static final String LINK_TARGET_MODE = "targetMode";
    private static final String LINK_TARGET_TITLE = "targetTitle";
    private static final String LINK_TARGET_WIDTH = "targetWidth";
    private static final String LINK_TARGET_HEIGHT = "targetHeight";
    private static final String LINK_FILTER = "filter";
    public static final String PROTOCAL_URL = "qr://";
    public static final String LINK = "link";
    public static final String MESSAGE = "msg";
    public static final String PARAM_CELL_ID = "cell_id";
    public static final String PARAM_FILTER_ID = "filter_id";

    public HyperlinkType getType() {
        return this.type;
    }

    public void setType(HyperlinkType type) {
        this.type = type;
    }

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

    public String getTargetTitle() {
        return this.targetTitle;
    }

    public void setTargetTitle(String targetTitle) {
        this.targetTitle = targetTitle;
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

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void clear() {
        this.type = HyperlinkType.NONE;
        this.data = null;
        this.targetMode = HyperlinkTargetMode.BLANK;
        this.targetTitle = null;
        this.targetWidth = 0;
        this.targetHeight = 0;
        this.filter = null;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(LINK_TYPE, (Object)this.type);
        if (this.type != HyperlinkType.NONE) {
            if (this.data != null) {
                json.put(LINK_DATA, (Object)this.data);
            }
            json.put(LINK_TARGET_MODE, (Object)this.targetMode);
            if (this.targetTitle != null) {
                json.put(LINK_TARGET_TITLE, (Object)this.targetTitle);
            }
            if (this.targetMode == HyperlinkTargetMode.MODEL_WINDOW) {
                json.put(LINK_TARGET_WIDTH, this.targetWidth);
                json.put(LINK_TARGET_HEIGHT, this.targetHeight);
            }
            if (this.filter != null) {
                json.put(LINK_FILTER, (Object)this.filter);
            }
        }
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        String typeStr = json.getString(LINK_TYPE);
        if (HyperlinkType.NONE.name().equalsIgnoreCase(typeStr)) {
            this.clear();
            return;
        }
        this.data = json.optJSONObject(LINK_DATA);
        String linkType = this.data.optString("hyperLinkId");
        this.type = "com.jiuqi.bi.quickreport.hyperlink.message".equalsIgnoreCase(linkType) ? HyperlinkType.MESSAGE : HyperlinkType.NORMAL;
        this.targetMode = HyperlinkTargetMode.valueOf(json.getString(LINK_TARGET_MODE));
        if (this.targetMode == HyperlinkTargetMode.MODEL_WINDOW) {
            this.targetWidth = json.optInt(LINK_TARGET_WIDTH);
            this.targetHeight = json.optInt(LINK_TARGET_HEIGHT);
        }
        this.targetTitle = json.optString(LINK_TARGET_TITLE, null);
        this.filter = json.optString(LINK_FILTER);
    }

    public HyperlinkInfo clone() {
        HyperlinkInfo info;
        try {
            info = (HyperlinkInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new QuickReportError(e);
        }
        if (this.data != null) {
            info.data = new JSONObject(this.data, JSONObject.getNames((JSONObject)this.data));
        }
        return info;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('(').append((Object)this.type);
        if (this.type != HyperlinkType.NONE) {
            if (this.data != null) {
                buffer.append(", data : ").append(this.data);
            }
            buffer.append(", target : ").append((Object)this.targetMode);
            if (this.targetMode == HyperlinkTargetMode.MODEL_WINDOW) {
                buffer.append('[').append(this.targetWidth).append(", ").append(this.targetHeight).append(']');
            }
            if (this.filter != null) {
                buffer.append(", filter : ").append(this.filter);
            }
        }
        buffer.append(')');
        return buffer.toString();
    }
}

