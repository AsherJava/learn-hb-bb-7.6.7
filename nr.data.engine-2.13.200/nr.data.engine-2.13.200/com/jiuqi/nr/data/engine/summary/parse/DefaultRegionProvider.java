/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.cell.Region
 */
package com.jiuqi.nr.data.engine.summary.parse;

import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.nr.data.engine.summary.exe.ISheetRegionProvider;

public class DefaultRegionProvider
implements ISheetRegionProvider {
    private int maxRow;
    private int maxCol;

    public DefaultRegionProvider(int maxRow, int maxCol) {
        this.maxRow = maxRow;
        this.maxCol = maxCol;
    }

    @Override
    public Region getRegion(String sheetName) {
        Region region = new Region(1, 1, this.maxCol, this.maxRow);
        return region;
    }
}

