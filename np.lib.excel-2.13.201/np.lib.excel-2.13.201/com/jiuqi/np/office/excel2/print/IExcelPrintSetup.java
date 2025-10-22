/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.print;

public interface IExcelPrintSetup {
    public boolean isLandscape();

    public short getPaperSize();

    public double getTopMargin();

    public double getLeftMargin();

    public double getRightMargin();

    public double getBottomMargin();

    public boolean isHorizontallyCenter();

    public boolean isVerticallyCenter();

    public short getFitWidth();

    public short getFitHeight();

    public int[] getRowBreakIndex();

    public int[] getColumnBreakIndex();

    public boolean isLeft2Right();
}

