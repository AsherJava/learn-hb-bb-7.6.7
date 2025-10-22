/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.nr.single.core.para.parser.query;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import com.jiuqi.nr.single.core.para.util.ReadUtil;

public class QueryScriptRec {
    private int version;
    private int maskFlag;
    private int reserved;
    private int maxResult;
    private int maxResultHigh;
    private short doSelect;
    private short doSumary;
    private short headRowStart;
    private short headRowEnd;
    private short rowNumColNum;
    private short queryExpRow;
    private int[] ReservedEx;

    public void LoadFromStream(Stream stream) throws StreamException {
        this.setVersion(ReadUtil.readIntValue(stream));
        this.setMaskFlag(ReadUtil.readIntValue(stream));
        this.setReserved(ReadUtil.readIntValue(stream));
        this.setMaxResult(ReadUtil.readIntValue(stream));
        this.setMaxResultHigh(ReadUtil.readIntValue(stream));
        this.setDoSelect(ReadUtil.readShortValue(stream));
        this.setDoSumary(ReadUtil.readShortValue(stream));
        this.setHeadRowStart(ReadUtil.readShortValue(stream));
        this.setHeadRowEnd(ReadUtil.readShortValue(stream));
        this.setRowNumColNum(ReadUtil.readShortValue(stream));
        this.setQueryExpRow(ReadUtil.readShortValue(stream));
        this.ReservedEx = new int[8];
        for (int i = 0; i < this.ReservedEx.length; ++i) {
            this.ReservedEx[i] = ReadUtil.readIntValue(stream);
        }
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getMaskFlag() {
        return this.maskFlag;
    }

    public void setMaskFlag(int maskFlag) {
        this.maskFlag = maskFlag;
    }

    public int getReserved() {
        return this.reserved;
    }

    public void setReserved(int reserved) {
        this.reserved = reserved;
    }

    public int getMaxResult() {
        return this.maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getMaxResultHigh() {
        return this.maxResultHigh;
    }

    public void setMaxResultHigh(int maxResultHigh) {
        this.maxResultHigh = maxResultHigh;
    }

    public short getDoSelect() {
        return this.doSelect;
    }

    public void setDoSelect(short doSelect) {
        this.doSelect = doSelect;
    }

    public short getDoSumary() {
        return this.doSumary;
    }

    public void setDoSumary(short doSumary) {
        this.doSumary = doSumary;
    }

    public short getHeadRowStart() {
        return this.headRowStart;
    }

    public void setHeadRowStart(short headRowStart) {
        this.headRowStart = headRowStart;
    }

    public short getHeadRowEnd() {
        return this.headRowEnd;
    }

    public void setHeadRowEnd(short headRowEnd) {
        this.headRowEnd = headRowEnd;
    }

    public short getRowNumColNum() {
        return this.rowNumColNum;
    }

    public void setRowNumColNum(short rowNumColNum) {
        this.rowNumColNum = rowNumColNum;
    }

    public short getQueryExpRow() {
        return this.queryExpRow;
    }

    public void setQueryExpRow(short queryExpRow) {
        this.queryExpRow = queryExpRow;
    }

    public int[] getReservedEx() {
        return this.ReservedEx;
    }

    public void setReservedEx(int[] reservedEx) {
        this.ReservedEx = reservedEx;
    }
}

