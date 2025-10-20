/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

class GuardedInputStream
extends InputStream {
    private InputStream in;
    private long current;
    private Stack<Long> guards;

    public GuardedInputStream(InputStream in) {
        this.in = in;
        this.current = 0L;
        this.guards = new Stack();
    }

    private int checkRange(int delta) throws IOException {
        this.checkRange((long)delta);
        return delta;
    }

    private long checkRange(long delta) throws IOException {
        if (delta == -1L) {
            return delta;
        }
        this.current += delta;
        if (this.guards.isEmpty()) {
            return delta;
        }
        if (this.current > this.guards.peek()) {
            throw new IOException("\u6570\u636e\u8bfb\u53d6\u8303\u56f4\u8d8a\u754c\uff01");
        }
        return delta;
    }

    public void pushGuard(long size) throws IOException {
        long target = this.current + size;
        if (!this.guards.isEmpty() && target > this.guards.peek()) {
            throw new IOException("\u6570\u636e\u8bfb\u53d6\u8303\u56f4\u8d8a\u754c\uff01");
        }
        this.guards.push(target);
    }

    public long popGuard() {
        return this.guards.isEmpty() ? -1L : this.guards.pop() - this.current;
    }

    @Override
    public int read() throws IOException {
        int ret = this.in.read();
        if (ret != -1) {
            this.checkRange(1);
        }
        return ret;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.checkRange(this.in.read(b));
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.checkRange(this.in.read(b, off, len));
    }

    @Override
    public long skip(long n) throws IOException {
        return this.checkRange(this.in.skip(n));
    }

    @Override
    public int available() throws IOException {
        return this.in.available();
    }

    @Override
    public void close() throws IOException {
        this.in.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        this.in.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        this.in.reset();
    }

    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }
}

