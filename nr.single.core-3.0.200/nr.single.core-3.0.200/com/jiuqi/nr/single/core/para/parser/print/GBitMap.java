/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.parser.print.GraphItem;
import java.io.IOException;

public class GBitMap
extends GraphItem {
    private int left;
    private int top;
    private int width;
    private int height;
    private byte[] bitMapData;

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte[] getBitMapData() {
        return this.bitMapData;
    }

    public void setBitMapData(byte[] bitMapData) {
        this.bitMapData = bitMapData;
    }

    @Override
    public void loadFromStream(Stream is) throws IOException, StreamException {
        super.loadFromStream(is);
    }
}

