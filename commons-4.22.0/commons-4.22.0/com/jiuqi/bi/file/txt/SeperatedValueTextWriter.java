/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.txt;

import java.io.BufferedWriter;
import java.io.IOException;

public class SeperatedValueTextWriter {
    private char separator;
    private boolean isConvertEscapeCharactor;
    private BufferedWriter writer;

    public char getSeparator() {
        return this.separator;
    }

    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public boolean isConvertEscapeCharactor() {
        return this.isConvertEscapeCharactor;
    }

    public void setConvertEscapeCharactor(boolean isConvertEscapeCharactor) {
        this.isConvertEscapeCharactor = isConvertEscapeCharactor;
    }

    public void setWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void append(String[] values) throws IOException {
        if (values == null || values.length == 0) {
            this.writer.append('\n');
            return;
        }
        String value = values[0];
        this.writeSingleField(value);
        for (int i = 1; i < values.length; ++i) {
            this.writer.append(this.separator);
            value = values[i];
            this.writeSingleField(value);
        }
        this.writer.append('\n');
    }

    public void close() throws IOException {
        this.writer.flush();
    }

    private void writeSingleField(String value) throws IOException {
        if (value == null) {
            return;
        }
        for (int i = 0; i < value.length(); ++i) {
            char c = value.charAt(i);
            if (this.isConvertEscapeCharactor) {
                if (c == ',') {
                    this.writer.append('\\');
                    this.writer.append(c);
                    continue;
                }
                if (c == '\n') {
                    this.writer.append("\\n");
                    continue;
                }
                if (c == '\r') {
                    this.writer.append("\\r");
                    continue;
                }
                this.writer.append(c);
                continue;
            }
            this.writer.append(c);
        }
    }
}

