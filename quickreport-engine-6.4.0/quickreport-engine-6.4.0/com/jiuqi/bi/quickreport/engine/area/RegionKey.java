/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.syntax.cell.Region;

public final class RegionKey {
    public final String sheetName;
    public final Region region;

    public RegionKey(String sheetName, Region region) {
        this.sheetName = sheetName;
        this.region = region;
    }

    public int hashCode() {
        return this.sheetName.hashCode() * 31 + this.region.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RegionKey)) {
            return false;
        }
        RegionKey key = (RegionKey)obj;
        return this.sheetName.equals(key.sheetName) && this.region.equals((Object)key.region);
    }
}

