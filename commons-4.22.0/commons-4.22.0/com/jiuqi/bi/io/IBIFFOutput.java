/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import com.jiuqi.bi.io.BIFFWriter;
import java.io.DataOutput;
import java.io.IOException;

public interface IBIFFOutput {
    public DataOutput data();

    public BIFFWriter toWriter() throws IOException;

    public void close() throws IOException;
}

