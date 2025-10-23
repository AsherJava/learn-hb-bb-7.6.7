/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tds.api;

import com.jiuqi.nr.tds.TdColumn;
import com.jiuqi.nr.tds.TdRowData;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public interface DataTableReader
extends Closeable,
Iterator<TdRowData> {
    public void open(File var1, String var2) throws IOException;

    public List<TdColumn> columns();

    @Override
    public boolean hasNext();

    @Override
    public TdRowData next();

    @Override
    public void close() throws IOException;

    public void destroy();
}

