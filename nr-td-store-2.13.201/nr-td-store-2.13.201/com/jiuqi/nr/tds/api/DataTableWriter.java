/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tds.api;

import com.jiuqi.nr.tds.TdRowData;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.util.List;

public interface DataTableWriter
extends Closeable,
Flushable {
    public DataTableWriter appendRow(TdRowData var1);

    public DataTableWriter appendRow(List<Object> var1);

    public File getFile();

    public void destroy();

    @Override
    public void flush() throws IOException;

    @Override
    public void close() throws IOException;
}

