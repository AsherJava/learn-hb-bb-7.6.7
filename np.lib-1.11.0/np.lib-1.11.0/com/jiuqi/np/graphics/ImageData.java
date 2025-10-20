/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.graphics;

import java.io.Serializable;

public class ImageData
implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte[] bytes;
    private int offset;
    private int length;

    public ImageData(byte[] bytes) {
        this.bytes = bytes;
        this.offset = 0;
        this.length = bytes.length;
    }

    public ImageData(byte[] bytes, int offset, int length) {
        this.bytes = bytes;
        this.offset = offset;
        this.length = length;
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLength() {
        return this.length;
    }

    public byte[] getRealBytes() {
        byte[] dest = new byte[this.length];
        System.arraycopy(this.bytes, this.offset, dest, 0, this.length);
        return dest;
    }

    public void setBytes(byte[] bytes, int offset, int length) {
        this.bytes = bytes;
        this.offset = offset;
        this.length = length;
    }

    public void setBytes(byte[] bytes) {
        this.setBytes(bytes, 0, bytes.length);
    }
}

