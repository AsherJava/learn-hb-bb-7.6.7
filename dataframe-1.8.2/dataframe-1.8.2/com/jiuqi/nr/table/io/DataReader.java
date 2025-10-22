/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.io.ReadOptions;
import com.jiuqi.nr.table.io.Source;
import java.io.IOException;

public interface DataReader<O extends ReadOptions> {
    public DataFrame<?> read(Source var1) throws IOException;

    public DataFrame<?> read(O var1) throws IOException;
}

