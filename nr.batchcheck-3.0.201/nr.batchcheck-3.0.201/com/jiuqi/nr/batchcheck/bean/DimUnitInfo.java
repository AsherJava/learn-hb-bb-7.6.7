/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batchcheck.bean;

import java.io.Serializable;

public class DimUnitInfo
implements Serializable {
    private static final long serialVersionUID = 7333780387918945144L;
    private String title;
    private String dimensionName;
    private String[] units;
    private String key;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getUnits() {
        return this.units;
    }

    public void setUnits(String[] units) {
        this.units = units;
    }
}

