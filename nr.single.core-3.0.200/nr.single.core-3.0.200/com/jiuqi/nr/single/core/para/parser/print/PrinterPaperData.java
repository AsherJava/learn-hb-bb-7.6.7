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
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.IOException;

public class PrinterPaperData {
    private short privatePaperType;
    private short privateOrientation;
    private short privatePaperWidth;
    private short privatePaperHeight;

    public final short getPaperType() {
        return this.privatePaperType;
    }

    public final void setPaperType(short value) {
        this.privatePaperType = value;
    }

    public final short getOrientation() {
        return this.privateOrientation;
    }

    public final void setOrientation(short value) {
        this.privateOrientation = value;
    }

    public final short getPaperWidth() {
        return this.privatePaperWidth;
    }

    public final void setPaperWidth(short value) {
        this.privatePaperWidth = value;
    }

    public final short getPaperHeight() {
        return this.privatePaperHeight;
    }

    public final void setPaperHeight(short value) {
        this.privatePaperHeight = value;
    }

    public final void loadFromStream(Stream stream) throws IOException, StreamException {
        this.setPaperType(ReadUtil.readShortValue(stream));
        this.setOrientation(ReadUtil.readShortValue(stream));
        this.setPaperWidth(ReadUtil.readShortValue(stream));
        this.setPaperHeight(ReadUtil.readShortValue(stream));
    }
}

