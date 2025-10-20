/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.logging;

import java.util.function.Consumer;

final class LogBuffer {
    private final StringBuilder buffer = new StringBuilder();
    private final Consumer<String> writer;

    public LogBuffer(Consumer<String> writer) {
        this.writer = writer;
    }

    public void append(char[] cbuf, int off, int len) {
        int p = 0;
        block4: while (p < len) {
            char ch = cbuf[p];
            ++p;
            switch (ch) {
                case '\r': {
                    if (p < len && cbuf[p] == '\n') {
                        this.write();
                        ++p;
                        continue block4;
                    }
                    this.buffer.append(ch);
                    continue block4;
                }
                case '\n': {
                    this.write();
                    continue block4;
                }
            }
            this.buffer.append(ch);
        }
    }

    public void flush() {
        if (this.buffer.length() > 0) {
            this.write();
        }
    }

    private void write() {
        this.writer.accept(this.buffer.toString());
        this.buffer.setLength(0);
    }
}

