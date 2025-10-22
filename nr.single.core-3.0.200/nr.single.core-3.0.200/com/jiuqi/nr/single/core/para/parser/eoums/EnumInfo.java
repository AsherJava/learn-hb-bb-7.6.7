/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser.eoums;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.util.ReadUtil;
import java.io.IOException;

public class EnumInfo {
    private int hYColNum;
    private int hYRowNum;
    private String enumIdenty;
    private byte showValueType;
    private byte enumWidth;
    private String wReserved;

    public int Size() {
        return 40;
    }

    public void init(Stream mask0) throws IOException, StreamException {
        this.sethYColNum(ReadUtil.readSmallIntValue(mask0));
        this.sethYRowNum(ReadUtil.readSmallIntValue(mask0));
        this.setEnumIdenty(ReadUtil.readEnumStringValue(mask0, 32));
        this.setShowValueType(ReadUtil.readByteValue(mask0));
        this.setEnumWidth(ReadUtil.readByteValue(mask0));
        this.setwReserved(ReadUtil.readStringValue(mask0, 2));
    }

    public final int gethYColNum() {
        return this.hYColNum;
    }

    public final void sethYColNum(int hYColNum) {
        this.hYColNum = hYColNum;
    }

    public final int gethYRowNum() {
        return this.hYRowNum;
    }

    public final void sethYRowNum(int hYRowNum) {
        this.hYRowNum = hYRowNum;
    }

    public final String getEnumIdenty() {
        return this.enumIdenty;
    }

    public final void setEnumIdenty(String enumIdenty) {
        this.enumIdenty = enumIdenty;
    }

    public final byte getShowValueType() {
        return this.showValueType;
    }

    public final void setShowValueType(byte showValueType) {
        this.showValueType = showValueType;
    }

    public final byte getEnumWidth() {
        return this.enumWidth;
    }

    public final void setEnumWidth(byte enumWidth) {
        this.enumWidth = enumWidth;
    }

    public final String getwReserved() {
        return this.wReserved;
    }

    public final void setwReserved(String wReserved) {
        this.wReserved = wReserved;
    }
}

