/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import com.jiuqi.bi.io.BIFFReader;
import com.jiuqi.bi.io.BIFFWriter;
import java.io.IOException;

public interface IBIFFSerializer {
    public void save(BIFFWriter var1) throws IOException;

    public void load(BIFFReader var1) throws IOException;
}

