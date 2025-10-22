/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.api.DataValueFormatter;
import com.jiuqi.nr.datacrud.spi.TypeFormatStrategy;

public interface DataValueFormatterBuilder {
    public DataValueFormatterBuilder registerFormatStrategy(int var1, TypeFormatStrategy var2);

    public DataValueFormatterBuilder installFormatStrategy();

    public DataValueFormatterBuilder unInstallFormatStrategy();

    public void setDefaultTypeStrategy(TypeFormatStrategy var1);

    public DataValueFormatter build();
}

