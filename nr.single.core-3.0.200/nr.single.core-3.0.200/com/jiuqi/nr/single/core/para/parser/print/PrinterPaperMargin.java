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

public class PrinterPaperMargin {
    private short privateLeftMargin;
    private short privateTopMargin;
    private short privateRightMargin;
    private short privateBottomMargin;

    public final short getLeftMargin() {
        return this.privateLeftMargin;
    }

    public final void setLeftMargin(short value) {
        this.privateLeftMargin = value;
    }

    public final short getTopMargin() {
        return this.privateTopMargin;
    }

    public final void setTopMargin(short value) {
        this.privateTopMargin = value;
    }

    public final short getRightMargin() {
        return this.privateRightMargin;
    }

    public final void setRightMargin(short value) {
        this.privateRightMargin = value;
    }

    public final short getBottomMargin() {
        return this.privateBottomMargin;
    }

    public final void setBottomMargin(short value) {
        this.privateBottomMargin = value;
    }

    public final void loadFromStream(Stream stream) throws IOException, StreamException {
        this.setLeftMargin(ReadUtil.readShortValue(stream));
        this.setTopMargin(ReadUtil.readShortValue(stream));
        this.setRightMargin(ReadUtil.readShortValue(stream));
        this.setBottomMargin(ReadUtil.readShortValue(stream));
    }
}

