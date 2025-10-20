/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class FastOutputStream
extends OutputStream {
    private byte[] buffer;
    private int count;
    private static final int DEFAULT_INIT_SIZE = 64;
    private static final int MAX_EXTEND_SIZE = 0x400000;

    public FastOutputStream() {
        this(64);
    }

    public FastOutputStream(int initSize) {
        this.buffer = new byte[initSize];
    }

    public FastOutputStream(byte[] buffer) {
        this.buffer = buffer;
    }

    public final int size() {
        return this.count;
    }

    protected final void setSize(int size) {
        this.count = size;
    }

    public final void reset() {
        this.count = 0;
    }

    @Override
    public void write(int b) {
        this.writeFast(b);
    }

    @Override
    public void write(byte[] b) {
        this.writeFast(b);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        this.writeFast(b, off, len);
    }

    public void writeTo(OutputStream out) throws IOException {
        out.write(this.buffer, 0, this.count);
    }

    public final void writeFast(int b) {
        if (this.count >= this.buffer.length) {
            this.extend(1);
        }
        this.buffer[this.count++] = (byte)b;
    }

    public final void writeFast(byte[] b) {
        int needed = this.count + b.length - this.buffer.length;
        if (needed > 0) {
            this.extend(needed);
        }
        System.arraycopy(b, 0, this.buffer, this.count, b.length);
        this.count += b.length;
    }

    public final void writeFast(byte[] b, int off, int len) {
        int needed = this.count + len - this.buffer.length;
        if (needed > 0) {
            this.extend(needed);
        }
        System.arraycopy(b, off, this.buffer, this.count, len);
        this.count += len;
    }

    public final void skip(int len) {
        int needed = this.count + len - this.buffer.length;
        if (needed > 0) {
            this.extend(needed);
        }
        this.count += len;
    }

    public final int seek(int pos) throws IndexOutOfBoundsException {
        if (pos < 0 || pos >= this.buffer.length) {
            throw new IndexOutOfBoundsException();
        }
        int raw = this.count;
        this.count = pos;
        return raw;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }

    public void requireSize(int needSize) {
        int needed = this.count + needSize - this.buffer.length;
        if (needed > 0) {
            this.extend(needed);
        }
    }

    private void extend(int needed) {
        int delta = Math.min(this.buffer.length, 0x400000);
        if (delta < needed) {
            delta = Math.max(needed, 64);
        }
        this.buffer = Arrays.copyOf(this.buffer, this.buffer.length + delta);
    }
}

