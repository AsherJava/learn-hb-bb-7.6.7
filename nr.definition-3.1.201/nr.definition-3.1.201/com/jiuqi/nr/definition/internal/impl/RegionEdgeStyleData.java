/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import java.io.Serializable;

public class RegionEdgeStyleData
implements Serializable,
RegionEdgeStyleDefine {
    private static final long serialVersionUID = 5421977346058162026L;
    private int startIndex;
    private int endIndex;
    private int edgeStyle;
    private int edgeLineColor;

    @Override
    public int getStartIndex() {
        return this.startIndex;
    }

    @Override
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @Override
    public int getEndIndex() {
        return this.endIndex;
    }

    @Override
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    @Override
    public int getEdgeLineStyle() {
        return this.edgeStyle;
    }

    @Override
    public void setEdgeLineStyle(int edgeStyle) {
        this.edgeStyle = edgeStyle;
    }

    @Override
    public int getEdgeLineColor() {
        return this.edgeLineColor;
    }

    @Override
    public void setEdgeLineColor(int edgeLineColor) {
        this.edgeLineColor = edgeLineColor;
    }
}

