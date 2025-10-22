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
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.IOException;

public class CustomGraph
extends GraphItem {
    private int privateLeft;
    private int privateTop;
    private int privateWidth;
    private int privateHeight;

    public final int getLeft() {
        return this.privateLeft;
    }

    public final void setLeft(int value) {
        this.privateLeft = value;
    }

    public final int getTop() {
        return this.privateTop;
    }

    public final void setTop(int value) {
        this.privateTop = value;
    }

    public final int getWidth() {
        return this.privateWidth;
    }

    public final void setWidth(int value) {
        this.privateWidth = value;
    }

    public final int getHeight() {
        return this.privateHeight;
    }

    public final void setHeight(int value) {
        this.privateHeight = value;
    }

    @Override
    public void loadFromStream(Stream stream) throws StreamException, IOException {
        super.loadFromStream(stream);
        this.setLeft(ReadUtil.readIntValue(stream));
        this.setTop(ReadUtil.readIntValue(stream));
        this.setWidth(ReadUtil.readIntValue(stream));
        this.setHeight(ReadUtil.readIntValue(stream));
    }
}

