/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Destination {
    protected final OutputStream stream;
    protected final Writer writer;

    public Destination(File file) throws IOException {
        this.stream = new FileOutputStream(file);
        this.writer = null;
    }

    public Destination(Writer writer) {
        this.stream = null;
        this.writer = writer;
    }

    public Destination(OutputStream stream) {
        this.stream = stream;
        this.writer = null;
    }

    public OutputStream stream() {
        return this.stream;
    }

    public Writer writer() {
        return this.writer;
    }

    public Writer createWriter() {
        return this.writer != null ? this.writer : new OutputStreamWriter(this.stream);
    }
}

