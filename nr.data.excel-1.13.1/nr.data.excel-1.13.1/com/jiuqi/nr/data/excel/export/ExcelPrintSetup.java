/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.office.excel2.print.IExcelPrintSetup
 *  com.jiuqi.nr.definition.common.PageSize
 *  com.jiuqi.nr.definition.facade.PrintSettingDefine
 */
package com.jiuqi.nr.data.excel.export;

import com.jiuqi.np.office.excel2.print.IExcelPrintSetup;
import com.jiuqi.nr.definition.common.PageSize;
import com.jiuqi.nr.definition.facade.PrintSettingDefine;

public class ExcelPrintSetup
implements IExcelPrintSetup {
    private static final double PAPER_SIZE_COEFFICIENT = 0.393700787402;
    private boolean landscape;
    private short paperSize = (short)-1;
    private double topMargin = -1.0;
    private double bottomMargin = -1.0;
    private double leftMargin = -1.0;
    private double rightMargin = -1.0;
    private double topMarginCM = -1.0;
    private double bottomMarginCM = -1.0;
    private double leftMarginCM = -1.0;
    private double rightMarginCM = -1.0;
    private boolean horizontallyCenter;
    private boolean verticallyCenter;
    private short fitWidth = (short)-1;
    private short fitHeight = (short)-1;
    private int[] rowBreakIndex;
    private int[] columnBreakIndex;
    private boolean left2Right;

    public ExcelPrintSetup() {
    }

    public ExcelPrintSetup(PrintSettingDefine printSettingDefine) {
        this.init(printSettingDefine);
    }

    private void init(PrintSettingDefine printSettingDefine) {
        if (printSettingDefine == null) {
            return;
        }
        if (printSettingDefine.getLandscape() != null) {
            this.landscape = printSettingDefine.getLandscape();
        }
        this.paperSize = ExcelPrintSetup.getPaperSize(printSettingDefine.getPageSize());
        if (printSettingDefine.getTopMargin() != null) {
            this.topMargin = printSettingDefine.getTopMargin() * 0.393700787402;
            this.topMarginCM = printSettingDefine.getTopMargin();
        }
        if (printSettingDefine.getBottomMargin() != null) {
            this.bottomMargin = printSettingDefine.getBottomMargin() * 0.393700787402;
            this.bottomMarginCM = printSettingDefine.getBottomMargin();
        }
        if (printSettingDefine.getLeftMargin() != null) {
            this.leftMargin = printSettingDefine.getLeftMargin() * 0.393700787402;
            this.leftMarginCM = printSettingDefine.getLeftMargin();
        }
        if (printSettingDefine.getRightMargin() != null) {
            this.rightMargin = printSettingDefine.getRightMargin() * 0.393700787402;
            this.rightMarginCM = printSettingDefine.getRightMargin();
        }
        if (printSettingDefine.getHorizontallyCenter() != null) {
            this.horizontallyCenter = printSettingDefine.getHorizontallyCenter();
        }
        if (printSettingDefine.getVerticallyCenter() != null) {
            this.verticallyCenter = printSettingDefine.getVerticallyCenter();
        }
        if (printSettingDefine.getFitToWidth() != null) {
            this.fitWidth = printSettingDefine.getFitToWidth();
        }
        if (printSettingDefine.getFitToHeight() != null) {
            this.fitHeight = printSettingDefine.getFitToHeight();
        }
        if (printSettingDefine.getRowBreaks() != null) {
            this.rowBreakIndex = (int[])printSettingDefine.getRowBreaks().clone();
        }
        if (printSettingDefine.getColumnBreaks() != null) {
            this.columnBreakIndex = (int[])printSettingDefine.getColumnBreaks().clone();
        }
        if (printSettingDefine.getLeftToRight() != null) {
            this.left2Right = printSettingDefine.getLeftToRight();
        }
    }

    private static short getPaperSize(PageSize pageSize) {
        if (pageSize == null) {
            return 9;
        }
        switch (pageSize) {
            case A3_PAPER: {
                return 8;
            }
            case A5_PAPER: {
                return 11;
            }
            case B4_PAPER: {
                return 12;
            }
            case B5_PAPER: {
                return 13;
            }
            case EXECUTIVE_PAPER: {
                return 7;
            }
            case LEGAL_PAPER: {
                return 5;
            }
            case LETTER_PAPER: {
                return 1;
            }
            case STATEMENT_PAPER: {
                return 6;
            }
            case TABLOID_PAPER: {
                return 3;
            }
        }
        return 9;
    }

    public boolean isLandscape() {
        return this.landscape;
    }

    public short getPaperSize() {
        return this.paperSize;
    }

    public double getTopMargin() {
        return this.topMargin;
    }

    public double getLeftMargin() {
        return this.leftMargin;
    }

    public double getRightMargin() {
        return this.rightMargin;
    }

    public double getBottomMargin() {
        return this.bottomMargin;
    }

    public boolean isHorizontallyCenter() {
        return this.horizontallyCenter;
    }

    public boolean isVerticallyCenter() {
        return this.verticallyCenter;
    }

    public short getFitWidth() {
        return this.fitWidth;
    }

    public short getFitHeight() {
        return this.fitHeight;
    }

    public int[] getRowBreakIndex() {
        return this.rowBreakIndex;
    }

    public int[] getColumnBreakIndex() {
        return this.columnBreakIndex;
    }

    public boolean isLeft2Right() {
        return this.left2Right;
    }

    public void setBottomMargin(double bottomMargin) {
        this.bottomMargin = bottomMargin;
    }

    public void setFitHeight(short fitHeight) {
        this.fitHeight = fitHeight;
    }

    public void setFitWidth(short fitWidth) {
        this.fitWidth = fitWidth;
    }

    public void setHorizontallyCenter(boolean horizontallyCenter) {
        this.horizontallyCenter = horizontallyCenter;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    public void setLeftMargin(double leftMargin) {
        this.leftMargin = leftMargin;
    }

    public void setPaperSize(short paperSize) {
        this.paperSize = paperSize;
    }

    public void setRightMargin(double rightMargin) {
        this.rightMargin = rightMargin;
    }

    public void setTopMargin(double topMargin) {
        this.topMargin = topMargin;
    }

    public void setVerticallyCenter(boolean verticallyCenter) {
        this.verticallyCenter = verticallyCenter;
    }

    public void setColumnBreakIndex(int[] columnBreakIndex) {
        this.columnBreakIndex = columnBreakIndex;
    }

    public void setLeft2Right(boolean left2Right) {
        this.left2Right = left2Right;
    }

    public void setRowBreakIndex(int[] rowBreakIndex) {
        this.rowBreakIndex = rowBreakIndex;
    }

    public double getBottomMarginCM() {
        return this.bottomMarginCM;
    }

    public void setBottomMarginCM(double bottomMarginCM) {
        this.bottomMarginCM = bottomMarginCM;
    }

    public double getLeftMarginCM() {
        return this.leftMarginCM;
    }

    public void setLeftMarginCM(double leftMarginCM) {
        this.leftMarginCM = leftMarginCM;
    }

    public double getRightMarginCM() {
        return this.rightMarginCM;
    }

    public void setRightMarginCM(double rightMarginCM) {
        this.rightMarginCM = rightMarginCM;
    }

    public double getTopMarginCM() {
        return this.topMarginCM;
    }

    public void setTopMarginCM(double topMarginCM) {
        this.topMarginCM = topMarginCM;
    }
}

