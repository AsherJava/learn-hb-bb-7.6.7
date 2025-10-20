/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import java.io.Serializable;
import java.util.Arrays;

public class BitSet
implements Cloneable,
Serializable {
    private static final long serialVersionUID = 6033326045597785131L;
    private byte[] bits;

    public BitSet() {
        this.bits = new byte[0];
    }

    public BitSet(int initSize) {
        int len = BitSet.getByteSize(initSize);
        this.bits = new byte[len];
    }

    public BitSet(byte[] bits) {
        this.bits = bits == null ? new byte[]{} : bits;
    }

    private void ensureSize(int pos) {
        int newSize = pos / 8 + 1;
        if (this.bits.length < newSize) {
            byte[] newBits = new byte[newSize];
            System.arraycopy(this.bits, 0, newBits, 0, this.bits.length);
            this.bits = newBits;
        }
    }

    public int size() {
        return this.bits.length * 8;
    }

    public boolean get(int index) {
        int pos = index / 8;
        if (pos >= this.bits.length) {
            return false;
        }
        return (this.bits[pos] & BitSet.bit(index)) != 0;
    }

    private static int bit(int index) {
        return 1 << index % 8;
    }

    public void set(int index, boolean value) {
        this.ensureSize(index);
        int pos = index / 8;
        if (value) {
            int n = pos;
            this.bits[n] = (byte)(this.bits[n] | BitSet.bit(index));
        } else {
            int n = pos;
            this.bits[n] = (byte)(this.bits[n] & ~BitSet.bit(index));
        }
    }

    public void set(int index) {
        this.set(index, true);
    }

    public void clear() {
        Arrays.fill(this.bits, (byte)0);
    }

    public byte[] getBytes() {
        return this.bits;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append('[');
        for (int i = 0; i < this.bits.length; ++i) {
            if (i > 0) {
                buffer.append(',');
            }
            for (int j = 0; j < 8; ++j) {
                buffer.append((1 << j & this.bits[i]) == 0 ? (char)'0' : '1');
            }
        }
        buffer.append(']');
        return buffer.toString();
    }

    public Object clone() {
        try {
            BitSet ret = (BitSet)super.clone();
            ret.bits = new byte[this.bits.length];
            System.arraycopy(this.bits, 0, ret.bits, 0, this.bits.length);
            return ret;
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    public static int getByteSize(int bitSize) {
        return (bitSize + 7) / 8;
    }
}

