/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.io;

import com.jiuqi.nr.table.df.DataFrame;
import com.jiuqi.nr.table.io.Destination;
import com.jiuqi.nr.table.io.WriteOptions;
import java.io.IOException;

public interface DataWriter<O extends WriteOptions> {
    public void write(DataFrame<?> var1, Destination var2) throws IOException;

    public void write(DataFrame<?> var1, O var2) throws IOException;
}

