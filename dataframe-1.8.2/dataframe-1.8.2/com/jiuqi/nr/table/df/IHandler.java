/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import com.jiuqi.nr.table.data.DataType;
import com.jiuqi.nr.table.df.Options;
import java.text.Format;

public interface IHandler {
    public Format format(Options var1);

    public DataType type();
}

