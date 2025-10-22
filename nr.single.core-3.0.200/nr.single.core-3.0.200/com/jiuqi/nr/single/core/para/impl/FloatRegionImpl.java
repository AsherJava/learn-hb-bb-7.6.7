/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.para.impl;

import com.jiuqi.nr.single.core.para.consts.FloatRegionType;
import com.jiuqi.nr.single.core.para.consts.FormRegionType;

public class FloatRegionImpl {
    private String _title;
    private FormRegionType regionType = FormRegionType.forValue(0);
    private FloatRegionType floatRegionType = FloatRegionType.forValue(0);
    private int startCol;
    private int endCol;
    private int startRow;
    private int endRow;

    public final void setTitle(String title) {
        this._title = title;
    }

    public final void setType(FormRegionType type) {
        this.regionType = type;
    }

    public final void setFloatDirectory(FloatRegionType type) {
        this.floatRegionType = type;
    }

    public final FloatRegionType getFloatRegionType() {
        return this.floatRegionType;
    }

    public final void setFloatRangeStartColNo(int startCol) {
        this.startCol = startCol;
    }

    public final void setFloatRangeEndColNo(int endCol) {
        this.endCol = endCol;
    }

    public final void setFloatRangeStartNo(int startRow) {
        this.startRow = startRow;
    }

    public final int getFloatRangeStartNo() {
        if (this.floatRegionType == FloatRegionType.FLOAT_DIRECTION_ROW_FLOAT) {
            return this.startRow;
        }
        return this.startCol;
    }

    public final void setFloatRangeEndNo(int endRow) {
        this.endRow = endRow;
    }

    public final int getFloatRangeEndNo() {
        if (this.floatRegionType == FloatRegionType.FLOAT_DIRECTION_ROW_FLOAT) {
            return this.endRow;
        }
        return this.endCol;
    }

    public final String getTitle() {
        return this._title;
    }

    public final int reservedRowCount() {
        return this.endRow - this.startRow;
    }
}

