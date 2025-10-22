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
import com.jiuqi.nr.single.core.para.parser.print.PrinterPaperData;
import com.jiuqi.nr.single.core.para.parser.print.PrinterPaperMargin;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.IOException;

public class PrintPageData {
    private PrinterPaperData _paperData = new PrinterPaperData();
    private PrinterPaperMargin _paperMargin = new PrinterPaperMargin();
    private short privateGlobalProp;
    private boolean privateVertCenter;
    private boolean privateHorzCenter;
    private boolean privateFitInPage;
    private byte privateiReserved1;
    private short privateiReserved2;
    private short privatewReserved1;
    private short privatewReserved2;

    public final PrinterPaperData getPaperData() {
        return this._paperData;
    }

    public final PrinterPaperMargin getPaperMargin() {
        return this._paperMargin;
    }

    public final short getGlobalProp() {
        return this.privateGlobalProp;
    }

    public final void setGlobalProp(short value) {
        this.privateGlobalProp = value;
    }

    public final boolean getVertCenter() {
        return this.privateVertCenter;
    }

    public final void setVertCenter(boolean value) {
        this.privateVertCenter = value;
    }

    public final boolean getHorzCenter() {
        return this.privateHorzCenter;
    }

    public final void setHorzCenter(boolean value) {
        this.privateHorzCenter = value;
    }

    public final boolean getFitInPage() {
        return this.privateFitInPage;
    }

    public final void setFitInPage(boolean value) {
        this.privateFitInPage = value;
    }

    public final byte getiReserved1() {
        return this.privateiReserved1;
    }

    public final void setiReserved1(byte value) {
        this.privateiReserved1 = value;
    }

    public final short getiReserved2() {
        return this.privateiReserved2;
    }

    public final void setiReserved2(short value) {
        this.privateiReserved2 = value;
    }

    public final short getwReserved1() {
        return this.privatewReserved1;
    }

    public final void setwReserved1(short value) {
        this.privatewReserved1 = value;
    }

    public final short getwReserved2() {
        return this.privatewReserved2;
    }

    public final void setwReserved2(short value) {
        this.privatewReserved2 = value;
    }

    public final void loadFromStream(Stream stream) throws IOException, StreamException {
        this._paperData.loadFromStream(stream);
        this._paperMargin.loadFromStream(stream);
        this.setGlobalProp(ReadUtil.readShortValue(stream));
        this.setVertCenter(ReadUtil.readByteValue(stream) == 1);
        this.setHorzCenter(ReadUtil.readByteValue(stream) == 1);
        this.setFitInPage(ReadUtil.readByteValue(stream) == 1);
        this.setiReserved1(ReadUtil.readByteValue(stream));
        this.setiReserved2(ReadUtil.readShortValue(stream));
        this.setwReserved1(ReadUtil.readShortValue(stream));
        this.setwReserved2(ReadUtil.readShortValue(stream));
    }
}

