/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.print.common.other;

import java.io.IOException;
import java.io.OutputStream;
import org.slf4j.Logger;

public class Log4jPrintStream
extends OutputStream {
    private final Logger logger;
    private StringBuffer mem;
    private int lastb = -1;
    private byte[] buf = new byte[80];
    private int pos = 0;

    public Log4jPrintStream(Logger logger) {
        this.logger = logger;
        this.mem = new StringBuffer();
    }

    private void logBuf() {
        String msg = new String(this.buf, 0, this.pos);
        this.pos = 0;
        this.log(msg);
    }

    protected void log(String msg) {
        this.mem = new StringBuffer(msg);
        this.flush();
    }

    private void expandCapacity(int len) {
        while (this.pos + len > this.buf.length) {
            byte[] nb = new byte[this.buf.length * 2];
            System.arraycopy(this.buf, 0, nb, 0, this.pos);
            this.buf = nb;
        }
    }

    @Override
    public void write(int b) throws IOException {
        if (b == 13) {
            this.logBuf();
        } else if (b == 10) {
            if (this.lastb != 13) {
                this.logBuf();
            }
        } else {
            this.expandCapacity(1);
            this.buf[this.pos++] = (byte)b;
        }
        this.lastb = b;
    }

    @Override
    public void flush() {
        this.logger.info(this.mem.toString());
        this.mem = new StringBuffer();
    }
}

