/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import com.jiuqi.bi.office.excel.print.ZoomMode;
import java.io.Serializable;
import org.json.JSONObject;

public final class Zoom
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 1802432192448005084L;
    public static final String ID_NONE = "NONE";
    public static final String ID_FIT_PAGE = "FIT_PAGE";
    public static final String ID_FIT_COLS = "FIT_COLS";
    public static final String ID_FIT_ROWS = "FIT_ROWS";
    public static final String ID_CUSTOM = "CUSTOM";
    public static final Zoom NONE = new Zoom("NONE", 100);
    public static final Zoom FIT_PAGE = new Zoom("FIT_PAGE", 1, 1);
    public static final Zoom FIT_COLS = new Zoom("FIT_COLS", 0, 1);
    public static final Zoom FIT_ROWS = new Zoom("FIT_ROWS", 1, 0);
    private String id = "CUSTOM";
    private ZoomMode mode = ZoomMode.SCALE;
    private int scale = 100;
    private int fitHeight = 1;
    private int fitWeight = 1;

    public Zoom() {
    }

    public Zoom(int scale) {
        this(ID_CUSTOM, scale);
    }

    private Zoom(String id, int scale) {
        this.id = id;
        this.mode = ZoomMode.SCALE;
        this.scale = scale;
    }

    public Zoom(int fitHeight, int fitWeight) {
        this(ID_CUSTOM, fitHeight, fitWeight);
    }

    private Zoom(String id, int fitHeight, int fitWeight) {
        this.id = id;
        this.mode = ZoomMode.FIT;
        this.fitHeight = fitHeight;
        this.fitWeight = fitWeight;
    }

    public String getId() {
        return this.id;
    }

    public ZoomMode getMode() {
        return this.mode;
    }

    public int getScale() {
        return this.scale;
    }

    public int getFitHeight() {
        return this.fitHeight;
    }

    public int getFitWeight() {
        return this.fitWeight;
    }

    public JSONObject toJson() {
        JSONObject json_zoom = new JSONObject();
        json_zoom.put("id", (Object)this.id);
        json_zoom.put("mode", (Object)this.mode.name());
        json_zoom.put("scale", this.scale);
        json_zoom.put("fitHeight", this.fitHeight);
        json_zoom.put("fitWeight", this.fitWeight);
        return json_zoom;
    }

    public void fromJson(JSONObject json_zoom) {
        this.id = json_zoom.optString("id");
        this.mode = ZoomMode.valueOf(json_zoom.optString("mode"));
        this.scale = json_zoom.optInt("scale");
        this.fitHeight = json_zoom.optInt("fitHeight");
        this.fitWeight = json_zoom.optInt("fitWeight");
    }

    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

