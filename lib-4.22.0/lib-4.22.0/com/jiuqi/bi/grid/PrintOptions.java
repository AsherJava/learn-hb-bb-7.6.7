/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.MemStream
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.bi.grid;

import com.jiuqi.bi.grid.BIFF;
import com.jiuqi.bi.grid.GridData;
import com.jiuqi.bi.util.MemStream;
import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;

public class PrintOptions {
    private int zoom = -1;
    private int mode = -1;
    private int pageRows = -1;
    private int pageCols = -1;
    private int orientation = -1;
    private static final byte BIFF_Print_Zoom = 10;
    private static final byte BIFF_Print_Mode = 11;
    private static final byte BIFF_Print_PageRows = 12;
    private static final byte BIFF_Print_PageCols = 13;
    private static final byte BIFF_Print_Orientation = 14;
    public static final int PAGE_DEFAULT = 0;
    public static final int PAGE_PORTRAIT = 1;
    public static final int PAGE_LANDSCAPE = 2;

    public int getOrientation() {
        return this.orientation;
    }

    public void setOrientation(int value) {
        this.orientation = value;
    }

    public int getZoom() {
        return this.zoom;
    }

    public void setZoom(int value) {
        this.zoom = value;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int value) {
        this.mode = value;
    }

    public int getPageRows() {
        return this.pageRows;
    }

    public void setPageRows(int value) {
        this.pageRows = value;
    }

    public int getPageCols() {
        return this.pageCols;
    }

    public void setPageCols(int value) {
        this.pageCols = value;
    }

    public void saveToStream(Stream stream) throws StreamException {
        BIFF b = new BIFF();
        b.ident = 0;
        b.data().writeString("BIFF");
        b.data().writeInt(2);
        BIFF.writeBIFF(1, b, stream);
        b.reset();
        b.ident = (short)10;
        b.data().writeInt(this.zoom);
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)11;
        b.data().writeInt(this.mode);
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)12;
        b.data().writeInt(this.pageRows);
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)13;
        b.data().writeInt(this.pageCols);
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)14;
        b.data().writeInt(this.orientation);
        BIFF.writeBIFF(2, b, stream);
        b.reset();
        b.ident = (short)127;
        BIFF.writeBIFF(2, b, stream);
    }

    public void loadFromStream(Stream stream) {
    }

    public void apply(GridData grid) throws Exception {
        MemStream stream = new MemStream();
        this.saveToStream((Stream)stream);
        grid.getExtDatas().setData("print.options", stream.getBytes());
    }
}

