/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.idx;

public class IndexFieldDef {
    private String FieldName;
    private char DataType;
    private boolean Descending;
    private boolean CaseSensitive;
    private byte bReserved;
    private short FieldLen;
    private short wReserved;
    private int lReserved;
    private short dwReserved;

    public String getFieldName() {
        return this.FieldName;
    }

    public void setFieldName(String fieldName) {
        this.FieldName = fieldName;
    }

    public char getDataType() {
        return this.DataType;
    }

    public void setDataType(char dataType) {
        this.DataType = dataType;
    }

    public boolean isDescending() {
        return this.Descending;
    }

    public void setDescending(boolean descending) {
        this.Descending = descending;
    }

    public boolean isCaseSensitive() {
        return this.CaseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.CaseSensitive = caseSensitive;
    }

    public byte getbReserved() {
        return this.bReserved;
    }

    public void setbReserved(byte bReserved) {
        this.bReserved = bReserved;
    }

    public short getFieldLen() {
        return this.FieldLen;
    }

    public void setFieldLen(short fieldLen) {
        this.FieldLen = fieldLen;
    }

    public short getwReserved() {
        return this.wReserved;
    }

    public void setwReserved(short wReserved) {
        this.wReserved = wReserved;
    }

    public int getlReserved() {
        return this.lReserved;
    }

    public void setlReserved(int lReserved) {
        this.lReserved = lReserved;
    }

    public short getDwReserved() {
        return this.dwReserved;
    }

    public void setDwReserved(short dwReserved) {
        this.dwReserved = dwReserved;
    }
}

