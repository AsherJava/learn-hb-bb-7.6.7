/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.cellbook.excel.print;

import com.jiuqi.nvwa.cellbook.excel.print.ZoomMode;
import java.io.Serializable;

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

    private Zoom(String id, int scale) {
        this.id = id;
        this.mode = ZoomMode.SCALE;
        this.scale = scale;
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

    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

