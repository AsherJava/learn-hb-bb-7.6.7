/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.api;

import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IRowData;
import java.util.List;

public interface DataValueFormatter {
    public String format(IDataValue var1);

    public String format(String var1, Object var2);

    public List<String> format(IRowData var1);
}

