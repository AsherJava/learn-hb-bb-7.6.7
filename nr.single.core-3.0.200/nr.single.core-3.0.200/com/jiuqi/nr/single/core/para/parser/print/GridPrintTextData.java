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
import com.jiuqi.nr.single.core.grid.SinglePrintUtil;
import com.jiuqi.nr.single.core.para.parser.print.FontDataClass;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.IOException;

public class GridPrintTextData {
    private String privateText;
    private String privateComments;
    private short privatePrintOption;
    private short privatePosition;
    private int privateRelativeTop;
    private FontDataClass privateFontData;

    public final String getText() {
        return this.privateText;
    }

    public final void setText(String value) {
        this.privateText = value;
    }

    public final String getComments() {
        return this.privateComments;
    }

    public final void setComments(String value) {
        this.privateComments = value;
    }

    public final short getPrintOption() {
        return this.privatePrintOption;
    }

    public final void setPrintOption(short value) {
        this.privatePrintOption = value;
    }

    public final short getPosition() {
        return this.privatePosition;
    }

    public final void setPosition(short value) {
        this.privatePosition = value;
    }

    public final int getRelativeTop() {
        return this.privateRelativeTop;
    }

    public final void setRelativeTop(int value) {
        this.privateRelativeTop = value;
    }

    public final FontDataClass getFontData() {
        return this.privateFontData;
    }

    public final void setFontData(FontDataClass value) {
        this.privateFontData = value;
    }

    public final void LoadFromStream(Stream stream) throws IOException, StreamException {
        this.setText(SinglePrintUtil.replaceAll(ReadUtil.readStreams(stream)));
        this.setComments(ReadUtil.readStreams(stream));
        this.setPrintOption(ReadUtil.readShortValue(stream));
        this.setPosition(ReadUtil.readShortValue(stream));
        this.setFontData(new FontDataClass());
        this.getFontData().loadFromStrea(stream);
    }
}

