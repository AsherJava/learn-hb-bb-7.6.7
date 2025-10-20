/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import java.io.InputStream;

public class FastInputStream
extends InputStream {
    protected byte[] buffer;
    protected int position;
    protected int length;
    protected int mark;

    public FastInputStream(byte[] buffer) {
        this(buffer, 0, buffer.length);
    }

    public FastInputStream(byte[] buffer, int offset, int size) {
        this.buffer = buffer;
        this.position = offset;
        this.length = offset + size;
    }

    @Override
    public int available() {
        return this.length - this.position;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public void mark(int readlimit) {
        this.mark = this.position;
    }

    @Override
    public void reset() {
        this.position = this.mark;
    }

    @Override
    public long skip(long n) {
        int count = (int)n;
        if (count + this.position > this.length) {
            count = this.length - this.position;
        }
        this.skipFast(count);
        return count;
    }

    @Override
    public int read() {
        return this.readFast();
    }

    @Override
    public int read(byte[] b) {
        return this.readFast(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) {
        return this.readFast(b, off, len);
    }

    public final void skipFast(int count) {
        this.position += count;
    }

    public final int readFast() {
        return this.position < this.length ? this.buffer[this.position++] & 0xFF : -1;
    }

    public final int readFast(byte[] b) {
        return this.readFast(b, 0, b.length);
    }

    public final int readFast(byte[] b, int off, int len) {
        int available = this.length - this.position;
        if (available < 0) {
            return -1;
        }
        if (len > available) {
            len = available;
        }
        System.arraycopy(this.buffer, this.position, b, off, len);
        this.position += len;
        return len;
    }

    public final byte[] getBuffer() {
        return this.buffer;
    }

    public final int getPosition() {
        return this.position;
    }

    public final void setPosition(int position) throws IllegalArgumentException {
        if (position < 0 || position >= this.length) {
            throw new IllegalArgumentException("\u8bbe\u7f6e\u4f4d\u7f6e\u8d8a\u754c\uff1a" + position);
        }
        this.position = position;
    }

    public final int getLength() {
        return this.length;
    }

    public boolean eof() {
        return this.position >= this.length;
    }
}

