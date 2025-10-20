/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.Stream
 *  com.jiuqi.bi.util.StreamException
 */
package com.jiuqi.np.grid;

import com.jiuqi.bi.util.Stream;
import com.jiuqi.bi.util.StreamException;
import java.io.Serializable;

public class BitList
implements Serializable {
    private static final long serialVersionUID = 2984241915738173835L;
    private byte[] bits = null;
    private int count = 0;
    private boolean defaultValue;

    public BitList(boolean dftValue) {
        this.defaultValue = dftValue;
    }

    private void setCount(int c) {
        if (c >= 0) {
            int l;
            int n = l = this.bits == null ? 0 : this.bits.length;
            if (l * 8 >= c) {
                this.count = c;
                return;
            }
            l = (c - 1) / 8 + 1;
            if (this.bits != null) {
                byte[] nb = new byte[l];
                System.arraycopy(this.bits, 0, nb, 0, this.bits.length);
                this.bits = nb;
            } else {
                this.bits = new byte[l];
            }
            this.count = c;
        }
    }

    public void add(boolean value) {
        this.setCount(this.count() + 1);
        this.set(this.count - 1, value);
    }

    public boolean get(int index) {
        if (index >= 0 && index < this.count) {
            int b = index / 8;
            int p = index % 8;
            return (this.bits[b] >> p & 1) > 0;
        }
        return false;
    }

    public void set(int index, boolean value) {
        if (this.count <= index) {
            this.setCount(index + 1);
        }
        int b = index / 8;
        int p = index % 8;
        this.bits[b] = value ? (byte)(this.bits[b] | 1 << p) : (byte)(this.bits[b] & ~(1 << p));
    }

    public void insert(int index, int insCount) {
        if (index >= 0 && index <= this.count) {
            int i;
            this.setCount(this.count + insCount);
            for (i = this.count - 1; i >= index + insCount; --i) {
                this.set(i, this.get(i - insCount));
            }
            for (i = index; i < index + insCount; ++i) {
                this.set(i, this.defaultValue);
            }
        }
    }

    public void insert(int index, int insCount, boolean value) {
        if (index >= 0 && index <= this.count) {
            int i;
            this.setCount(this.count + insCount);
            for (i = this.count - 1; i >= index + insCount; --i) {
                this.set(i, this.get(i - insCount));
            }
            for (i = index; i < index + insCount; ++i) {
                this.set(i, value);
            }
        }
    }

    public void delete(int index, int delCount) {
        if (index >= 0 && index < this.count) {
            if (index + delCount > this.count) {
                delCount = this.count - index;
            }
            for (int i = index; i < this.count - delCount; ++i) {
                this.set(i, this.get(i + delCount));
            }
            this.setCount(this.count - delCount);
        }
    }

    public int count() {
        return this.count;
    }

    public void saveToStream(Stream stream) throws StreamException {
        if (this.count() > 0) {
            stream.writeBuffer(this.bits, 0, (this.count() - 1) / 8 + 1);
        }
    }

    public void loadFromStream(Stream stream, int size) throws StreamException {
        if (size > 0) {
            this.bits = new byte[size];
            stream.readBuffer(this.bits, 0, size);
            this.count = size * 8;
        } else {
            this.bits = null;
        }
    }
}

