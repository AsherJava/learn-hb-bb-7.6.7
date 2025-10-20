/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.elementtable.impl;

public class ElementExcelRangeVO {
    private ElementExcelSpanCellVO s;
    private ElementExcelSpanCellVO e;

    public ElementExcelRangeVO(int col_begin, int row_begin, int col_end, int row_end) {
        this.s = new ElementExcelSpanCellVO(col_begin, row_begin);
        this.e = new ElementExcelSpanCellVO(col_end, row_end);
    }

    public ElementExcelSpanCellVO getE() {
        return this.e;
    }

    public ElementExcelSpanCellVO getS() {
        return this.s;
    }

    public void setE(ElementExcelSpanCellVO e) {
        this.e = e;
    }

    public void setS(ElementExcelSpanCellVO s) {
        this.s = s;
    }

    class ElementExcelSpanCellVO {
        private int c;
        private int r;

        public ElementExcelSpanCellVO(int col, int row) {
            this.c = col;
            this.r = row;
        }

        public int getC() {
            return this.c;
        }

        public int getR() {
            return this.r;
        }

        public void setC(int c) {
            this.c = c;
        }

        public void setR(int r) {
            this.r = r;
        }
    }
}

