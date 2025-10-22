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
import com.jiuqi.nr.single.core.para.parser.print.PrintSchemeConsts;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.awt.Color;
import java.io.IOException;

public class GLineEndsGraph
extends GraphItem {
    private int y2;
    private int x1;
    private int y1;
    private int x2;
    private int width;
    private int lineColor;
    private int penMode;
    private int style;

    @Override
    public void loadFromStream(Stream is) throws IOException, StreamException {
        super.loadFromStream(is);
        int colorData = ReadUtil.readIntValue(is);
        Color color = new Color(colorData);
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        this.lineColor = (blue << 16) + (green << 8) + red;
        Byte b = ReadUtil.readByteValue(is);
        this.penMode = b.byteValue();
        b = ReadUtil.readByteValue(is);
        this.style = b >= 0 && b < PrintSchemeConsts.LineStyle.values().length ? PrintSchemeConsts.LineStyle.values()[b].getStyle() : PrintSchemeConsts.LineStyle.PSSOLID.getStyle();
        this.width = ReadUtil.readIntValue(is);
        long fontColor = ReadUtil.readIntValue(is);
        b = ReadUtil.readByteValue(is);
        this.x1 = ReadUtil.readIntValue(is);
        this.y1 = ReadUtil.readIntValue(is);
        this.x2 = ReadUtil.readIntValue(is);
        this.y2 = ReadUtil.readIntValue(is);
    }

    public int getY2() {
        return this.y2;
    }

    public int getX1() {
        return this.x1;
    }

    public int getY1() {
        return this.y1;
    }

    public int getX2() {
        return this.x2;
    }

    public int getWidth() {
        return this.width;
    }

    public int getLineColor() {
        return this.lineColor;
    }

    public int getStyle() {
        return this.style;
    }

    public int getPenMode() {
        return this.penMode;
    }
}

