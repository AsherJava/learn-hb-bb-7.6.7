/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.dbf;

public class DbfCrcCode {
    public static final int CRCLENGTH = 16;
    public static final int OLD_CRCVERSION = 829530394;
    public static final int CURRRENT_CRCVERSION = 1147498740;
    private int version = 0;
    private int position = 0;
    private int crcCode = 0;
    private int reserved = 0;
    private int length = 0;
    private boolean hasCrcCode = true;

    public int getVersion() {
        return this.version;
    }

    public int getPosition() {
        return this.position;
    }

    public int getCrcCode() {
        return this.crcCode;
    }

    public int getReserved() {
        return this.reserved;
    }

    public int getLength() {
        return this.length;
    }

    public boolean getHasCrcCode() {
        return this.hasCrcCode;
    }

    public void setVersion(int value) {
        this.version = value;
    }

    public void setPosition(int value) {
        this.position = value;
    }

    public void setCrcCode(int value) {
        this.crcCode = value;
    }

    public void setReserved(int value) {
        this.reserved = value;
    }

    public void setLength(int value) {
        this.length = value;
    }

    public void setHasCrcCode(boolean value) {
        this.hasCrcCode = value;
    }
}

