/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.dbf;

public class DbfHeader {
    public static final int HEADERSIZE = 32;
    public static final byte FOXPRO6_DB_SIGN = 48;
    public static final byte DEFAULT_LANG_DRIVER = 0;
    public static final short RESERVED_HEAD_LEN = 263;
    private byte version;
    private byte lastModifyYear;
    private byte lastModifyMonth;
    private byte lastModifyDay;
    private int recordCount;
    private short headerLength;
    private short recordLength;
    private byte[] reserved = new byte[16];
    private byte tableFlag;
    private byte codePageFlag;
    private byte[] reserved2 = new byte[2];

    public byte getVersion() {
        return this.version;
    }

    public void setVersion(byte value) {
        this.version = value;
    }

    public byte getLastModifyYear() {
        return this.lastModifyYear;
    }

    public void setLastModifyYear(byte value) {
        this.lastModifyYear = value;
    }

    public byte getLastModifyMonth() {
        return this.lastModifyMonth;
    }

    public void setLastModifyMonth(byte value) {
        this.lastModifyMonth = value;
    }

    public byte getLastModifyDay() {
        return this.lastModifyDay;
    }

    public void setLastModifyDay(byte value) {
        this.lastModifyDay = value;
    }

    public int getRecordCount() {
        return this.recordCount;
    }

    public void setRecordCount(int value) {
        this.recordCount = value;
    }

    public short getHeaderLength() {
        return this.headerLength;
    }

    public int getHeaderLength2() {
        return this.headerLength & 0xFFFF;
    }

    public void setHeaderLength(short value) {
        this.headerLength = value;
    }

    public short getRecordLength() {
        return this.recordLength;
    }

    public int getRecordLength2() {
        return this.recordLength & 0xFFFF;
    }

    public void setRecordLength(short value) {
        this.recordLength = value;
    }

    public byte[] getReserved() {
        return this.reserved;
    }

    public void setReserved(byte[] value) {
        this.reserved = value;
    }

    public byte getTableFlag() {
        return this.tableFlag;
    }

    public void setTableFlag(byte value) {
        this.tableFlag = value;
    }

    public byte getCodePageFlag() {
        return this.codePageFlag;
    }

    public void setCodePageFlag(byte value) {
        this.codePageFlag = value;
    }

    public byte[] getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(byte[] value) {
        this.reserved2 = value;
    }
}

