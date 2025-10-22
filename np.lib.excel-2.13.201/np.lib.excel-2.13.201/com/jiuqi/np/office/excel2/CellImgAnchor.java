/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2;

import java.io.Serializable;

public class CellImgAnchor
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int col1;
    private int row1;
    private int col2;
    private int row2;
    private int pictureType;
    private byte[] bytes;

    public int getCol1() {
        return this.col1;
    }

    public void setCol1(int col1) {
        this.col1 = col1;
    }

    public int getRow1() {
        return this.row1;
    }

    public void setRow1(int row1) {
        this.row1 = row1;
    }

    public int getCol2() {
        return this.col2;
    }

    public void setCol2(int col2) {
        this.col2 = col2;
    }

    public int getRow2() {
        return this.row2;
    }

    public void setRow2(int row2) {
        this.row2 = row2;
    }

    public int getPictureType() {
        return this.pictureType;
    }

    public void setPictureType(int pictureType) {
        this.pictureType = pictureType;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}

