/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid;

import java.io.Serializable;
import java.util.Arrays;

class CellBuffer
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 1L;
    private byte[] buffer;

    public CellBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public CellBuffer(int size) {
        this.buffer = new byte[size];
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public int length() {
        return this.buffer == null ? 0 : this.buffer.length;
    }

    public void fill(byte[] source) {
        System.arraycopy(source, 0, this.buffer, 0, Math.min(source.length, this.buffer.length));
    }

    public void fill(byte[] src, int srcPos, int destPos, int length) {
        System.arraycopy(src, srcPos, this.buffer, destPos, length);
    }

    public void fill(CellBuffer source) {
        this.fill(source.buffer);
    }

    public void fill(CellBuffer src, int srcPos, int destPos, int length) {
        this.fill(src.buffer, srcPos, destPos, length);
    }

    public boolean getBit(int bytePos, int bitPos) {
        return (this.buffer[bytePos] >> bitPos & 1) > 0;
    }

    public void setBit(int bytePos, int bitPos, boolean value) {
        int flag = 1 << bitPos;
        this.buffer[bytePos] = value ? (byte)(this.buffer[bytePos] | flag) : (byte)(this.buffer[bytePos] & ~flag);
    }

    public int getByte(int pos) {
        return this.buffer[pos] & 0xFF;
    }

    public void setByte(int pos, int value) {
        this.buffer[pos] = (byte)(value & 0xFF);
    }

    public int getUnsignedShort(int pos) {
        int low = this.buffer[pos] & 0xFF;
        int high = this.buffer[pos + 1] & 0xFF;
        return high << 8 | low;
    }

    public void setUnsighedShort(int pos, int value) {
        this.buffer[pos] = (byte)(value & 0xFF);
        this.buffer[pos + 1] = (byte)(value >> 8 & 0xFF);
    }

    public int getLow(int pos) {
        return this.buffer[pos] & 0xF;
    }

    public void setLow(int pos, int value) {
        this.buffer[pos] = (byte)(this.buffer[pos] & 0xF0 | value & 0xF);
    }

    public int getHigh(int pos) {
        return this.buffer[pos] >> 4 & 0xF;
    }

    public void setHigh(int pos, int value) {
        this.buffer[pos] = (byte)(this.buffer[pos] & 0xF | (value & 0xF) << 4);
    }

    public int hashCode() {
        return Arrays.hashCode(this.buffer);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CellBuffer)) {
            return false;
        }
        CellBuffer cellBuf = (CellBuffer)obj;
        if (this.buffer == cellBuf.buffer) {
            return true;
        }
        if (this.buffer == null || cellBuf.buffer == null || this.buffer.length != cellBuf.buffer.length) {
            return false;
        }
        for (int i = 0; i < this.buffer.length; ++i) {
            if (this.buffer[i] == cellBuf.buffer[i]) continue;
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder text = new StringBuilder();
        text.append('[');
        if (this.buffer != null) {
            for (int i = 0; i < this.buffer.length; ++i) {
                if (i > 0) {
                    text.append(", ");
                }
                String val = Integer.toHexString(this.buffer[i] & 0xFF).toUpperCase();
                if (this.buffer[i] != 0) {
                    text.append("(byte)");
                }
                text.append("0x");
                if (val.length() < 2) {
                    text.append('0');
                }
                text.append(val);
            }
        }
        text.append(']');
        return text.toString();
    }

    public CellBuffer clone() {
        CellBuffer buffer;
        try {
            buffer = (CellBuffer)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        buffer.buffer = this.buffer == null ? null : (byte[])this.buffer.clone();
        return buffer;
    }

    public CellBuffer copyOf(int len) {
        if (this.length() >= len) {
            return this.clone();
        }
        CellBuffer newBuf = new CellBuffer(len);
        newBuf.fill(this.buffer);
        return newBuf;
    }
}

