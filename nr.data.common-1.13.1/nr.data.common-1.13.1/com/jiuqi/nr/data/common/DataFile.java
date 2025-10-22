/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public interface DataFile
extends Closeable {
    public File getFile();

    public boolean isZip();

    @Override
    public void close() throws IOException;
}

