/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.model.HyperlinkInfo;
import com.jiuqi.bi.quickreport.model.HyperlinkTargetMode;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
public class HyperlinkWindowInfo
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1L;
    public static final int MODE_WINDOW = 0;
    public static final int MODE_TAB = 1;
    public static final int MODE_DIV = 2;
    private int mode = 0;
    private int width = 600;
    private int height = 400;

    public HyperlinkWindowInfo() {
    }

    public HyperlinkWindowInfo(HyperlinkInfo info) {
        switch (info.getTargetMode()) {
            case MODEL_WINDOW: {
                this.mode = 2;
                break;
            }
            case BLANK: {
                this.mode = 0;
                break;
            }
            case NAVI_TAB: {
                this.mode = 1;
                break;
            }
            case SELF: {
                this.mode = 2;
            }
        }
        this.width = info.getTargetWidth();
        this.height = info.getTargetHeight();
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("mode", this.mode);
        json.put("width", this.width);
        json.put("height", this.height);
        return json;
    }

    public void fromJSON(JSONObject json) throws JSONException {
        this.mode = json.optInt("mode");
        this.width = json.optInt("width");
        this.height = json.optInt("height");
    }

    void into(HyperlinkInfo info) {
        switch (this.mode) {
            case 0: {
                info.setTargetMode(HyperlinkTargetMode.BLANK);
                break;
            }
            case 2: {
                info.setTargetMode(HyperlinkTargetMode.MODEL_WINDOW);
                break;
            }
            case 1: {
                info.setTargetMode(HyperlinkTargetMode.NAVI_TAB);
            }
        }
        info.setTargetWidth(this.width);
        info.setTargetHeight(this.height);
    }
}

