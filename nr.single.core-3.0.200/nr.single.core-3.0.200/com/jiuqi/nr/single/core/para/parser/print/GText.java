/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 *  com.jiuqi.xg.draw2d.Font
 */
package com.jiuqi.nr.single.core.para.parser.print;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.grid.SinglePrintUtil;
import com.jiuqi.nr.single.core.para.parser.print.GraphItem;
import com.jiuqi.nr.single.core.para.parser.print.PrintReadUtil;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import com.jiuqi.xg.draw2d.Font;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GText
extends GraphItem {
    int tag;
    int lockPos;
    int left;
    double height;
    int top;
    Font font;
    String text;

    @Override
    public final void loadFromStream(Stream is) throws IOException, StreamException {
        super.loadFromStream(is);
        this.left = ReadUtil.readIntValue(is);
        this.top = ReadUtil.readIntValue(is);
        this.font = PrintReadUtil.readFont(is);
        this.height = this.font.getSize();
        double fontSize = SinglePrintUtil.getFontSizeByCE(this.font.getSize() * 72.0 / 254.0);
        this.font.setSize(fontSize);
        this.text = SinglePrintUtil.replaceAll(ReadUtil.readStreams(is));
    }

    public String replaceAll(String input, String regex, String replacement) {
        Pattern p = Pattern.compile(regex, 2);
        Matcher m = p.matcher(input);
        StringBuffer sb = new StringBuffer();
        boolean result = m.find();
        while (result) {
            m.appendReplacement(sb, replacement);
            result = m.find();
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public double getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public double getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public Font getFont() {
        return this.font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

