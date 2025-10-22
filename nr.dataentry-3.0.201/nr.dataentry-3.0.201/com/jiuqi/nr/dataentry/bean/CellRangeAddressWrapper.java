/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.bean;

import org.apache.poi.ss.util.CellRangeAddress;

public class CellRangeAddressWrapper
implements Comparable<CellRangeAddressWrapper> {
    public CellRangeAddress range;

    public CellRangeAddressWrapper(CellRangeAddress theRange) {
        this.range = theRange;
    }

    @Override
    public int compareTo(CellRangeAddressWrapper craw) {
        if (this.range.getFirstColumn() < craw.range.getFirstColumn() && this.range.getFirstRow() < craw.range.getFirstRow()) {
            return -1;
        }
        if (this.range.getFirstColumn() == craw.range.getFirstColumn() && this.range.getFirstRow() == craw.range.getFirstRow()) {
            return 0;
        }
        return 1;
    }
}

