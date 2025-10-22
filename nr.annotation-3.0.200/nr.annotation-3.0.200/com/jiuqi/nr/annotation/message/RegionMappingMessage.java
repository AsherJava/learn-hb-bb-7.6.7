/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.message;

import java.io.Serializable;

public class RegionMappingMessage
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String regionKey;
    private int regionLeft;
    private int regionRight;
    private int regionTop;
    private int regionBottom;
    private int type;

    public RegionMappingMessage(String regionKey, int regionLeft, int regionRight, int regionTop, int regionBottom, int type) {
        this.regionKey = regionKey;
        this.regionLeft = regionLeft;
        this.regionRight = regionRight;
        this.regionTop = regionTop;
        this.regionBottom = regionBottom;
        this.type = type;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public int getRegionLeft() {
        return this.regionLeft;
    }

    public void setRegionLeft(int regionLeft) {
        this.regionLeft = regionLeft;
    }

    public int getRegionRight() {
        return this.regionRight;
    }

    public void setRegionRight(int regionRight) {
        this.regionRight = regionRight;
    }

    public int getRegionTop() {
        return this.regionTop;
    }

    public void setRegionTop(int regionTop) {
        this.regionTop = regionTop;
    }

    public int getRegionBottom() {
        return this.regionBottom;
    }

    public void setRegionBottom(int regionBottom) {
        this.regionBottom = regionBottom;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

