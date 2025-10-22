/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.WriteListener
 */
package com.jiuqi.gcreport.reportdatasync.util;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class DelegatingServletOutputStream
extends ServletOutputStream {
    private final OutputStream targetStream;

    public DelegatingServletOutputStream(OutputStream targetStream) {
        this.targetStream = targetStream;
    }

    public void write(int b) throws IOException {
        this.targetStream.write(b);
    }

    public void write(byte[] b) throws IOException {
        this.targetStream.write(b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        this.targetStream.write(b, off, len);
    }

    public void flush() throws IOException {
        this.targetStream.flush();
    }

    public void close() throws IOException {
        this.targetStream.close();
    }

    public boolean isReady() {
        return true;
    }

    public void setWriteListener(WriteListener writeListener) {
        throw new UnsupportedOperationException();
    }
}

