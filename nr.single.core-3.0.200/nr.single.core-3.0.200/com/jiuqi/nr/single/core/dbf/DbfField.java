/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.dbf;

import java.sql.Date;

public class DbfField {
    public static final int FIELDSIZE = 32;
    private byte[] nameBytes = new byte[11];
    private byte typeChar;
    private int offset;
    private int length;
    private int precision;
    private byte fieldSign = 0;
    private byte[] reserved = new byte[13];
    private byte dbaseivID;
    private byte[] reserved2 = new byte[10];
    private byte productionIndex;
    private byte[] dataBytes = null;
    private int dataOffset = -1;

    public byte getFieldSign() {
        return this.fieldSign;
    }

    public void setFieldSign(byte fieldSign) {
        this.fieldSign = fieldSign;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public byte[] getNameBytes() {
        return this.nameBytes;
    }

    public byte getTypeChar() {
        return this.typeChar;
    }

    public short getLength2() {
        if (this.length >= 0) {
            return (short)this.length;
        }
        return (short)(this.length + 256);
    }

    public int getLength() {
        return this.length;
    }

    public int getPrecision() {
        return this.precision;
    }

    public byte[] getReserved() {
        return this.reserved;
    }

    public byte getDbaseivID() {
        return this.dbaseivID;
    }

    public byte[] getReserved2() {
        return this.reserved2;
    }

    public byte getProductionIndex() {
        return this.productionIndex;
    }

    public void setNameBytes(byte[] value) {
        this.nameBytes = value;
    }

    public void setTypeChar(byte value) {
        this.typeChar = value;
    }

    public void setLength2(short value) {
        this.length = value > 127 ? (int)((byte)(value - 256)) : (int)((byte)value);
    }

    public void setLength(int value) {
        this.length = value;
    }

    public void setPrecision(int value) {
        this.precision = value;
    }

    public void setReserved(byte[] value) {
        this.reserved = value;
    }

    public void setDbaseivID(byte value) {
        this.dbaseivID = value;
    }

    public void setReserved2(byte[] value) {
        this.reserved2 = value;
    }

    public void setProductionIndex(byte value) {
        this.productionIndex = value;
    }

    public boolean getIsString() {
        return this.typeChar == 67;
    }

    public boolean getIsMoney() {
        return this.typeChar == 89;
    }

    public boolean getIsNumber() {
        return this.typeChar == 78;
    }

    public boolean getIsFloat() {
        return this.typeChar == 70;
    }

    public boolean getIsDate() {
        return this.typeChar == 68;
    }

    public boolean getIsTime() {
        return this.typeChar == 84;
    }

    public boolean getIsDouble() {
        return this.typeChar == 66;
    }

    public boolean getIsInt() {
        return this.typeChar == 73;
    }

    public boolean getIsLogic() {
        return this.typeChar == 76;
    }

    public boolean getIsMemo() {
        return this.typeChar == 77;
    }

    public boolean getIsGraph() {
        return this.typeChar == 80;
    }

    public boolean getIsBlob() {
        return this.typeChar == 71;
    }

    public boolean getIsFile() {
        return this.typeChar == 79;
    }

    public boolean getIsGeneral() {
        return this.typeChar == 67;
    }

    public Class<?> getFieldType() {
        if (this.getIsString()) {
            return String.class;
        }
        if (this.getIsMoney() || this.getIsNumber() || this.getIsFloat()) {
            return Float.class;
        }
        if (this.getIsDate() || this.getIsTime()) {
            return Date.class;
        }
        if (this.getIsDouble()) {
            return Double.class;
        }
        if (this.getIsInt()) {
            return Integer.TYPE;
        }
        if (this.getIsLogic()) {
            return Boolean.TYPE;
        }
        if (this.getIsMemo()) {
            return String.class;
        }
        if (this.getIsGraph()) {
            return String.class;
        }
        if (this.getIsBlob()) {
            return String.class;
        }
        if (this.getIsFile()) {
            return String.class;
        }
        return String.class;
    }

    public int getDataTypeInt() {
        if (this.getIsString()) {
            return 0;
        }
        if (this.getIsMoney() || this.getIsNumber() || this.getIsFloat()) {
            return 1;
        }
        if (this.getIsDate() || this.getIsTime()) {
            return 2;
        }
        if (this.getIsDouble()) {
            return 3;
        }
        if (this.getIsInt()) {
            return 4;
        }
        if (this.getIsLogic()) {
            return 5;
        }
        if (this.getIsMemo()) {
            return 6;
        }
        if (this.getIsGraph()) {
            return 7;
        }
        if (this.getIsBlob()) {
            return 8;
        }
        if (this.getIsFile()) {
            return 9;
        }
        return 0;
    }

    public String getFieldName() {
        return this.getFieldName("GB2312");
    }

    public String getFieldName(String encoding) {
        String fieldName = "";
        try {
            fieldName = new String(this.nameBytes, 0, this.nameBytes.length, encoding);
        }
        catch (Exception e) {
            fieldName = new String(this.nameBytes);
        }
        int i = fieldName.indexOf(0);
        if (i > 0) {
            return fieldName.substring(0, i).trim();
        }
        return fieldName.trim();
    }

    public void setFieldName(String fieldName) {
        this.setFieldName("GB2312", fieldName);
    }

    public void setFieldName(String encoding, String fieldName) {
        byte[] tmp = null;
        try {
            tmp = fieldName.trim().getBytes(encoding);
        }
        catch (Exception e) {
            tmp = fieldName.trim().getBytes();
        }
        System.arraycopy(tmp, 0, this.nameBytes, 0, tmp.length);
    }

    public void setDataType(char datatype) {
        this.typeChar = (byte)datatype;
    }

    public byte[] getDataBytes() {
        if (this.dataBytes == null) {
            // empty if block
        }
        return this.dataBytes;
    }

    public void setDataBytes(byte[] dataBytes) {
        this.dataBytes = dataBytes;
    }

    public int getDataOffset() {
        return this.dataOffset;
    }

    public void setDataOffset(int dataOffset) {
        this.dataOffset = dataOffset;
    }
}

