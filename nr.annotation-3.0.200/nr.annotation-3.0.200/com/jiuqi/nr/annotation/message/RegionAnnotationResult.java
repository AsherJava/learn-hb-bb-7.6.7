/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.message;

import com.jiuqi.nr.annotation.message.CellLocation;
import java.util.ArrayList;
import java.util.List;

public class RegionAnnotationResult {
    private String regionKey;
    private List<CellLocation> cells = new ArrayList<CellLocation>();

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public List<CellLocation> getCells() {
        return this.cells;
    }

    public void setCells(List<CellLocation> cells) {
        this.cells = cells;
    }
}

