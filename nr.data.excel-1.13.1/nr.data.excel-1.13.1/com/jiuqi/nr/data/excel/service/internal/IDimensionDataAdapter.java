/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.service.internal;

import com.jiuqi.nr.data.excel.obj.DimensionData;
import java.util.List;

public interface IDimensionDataAdapter {
    public DimensionData getDimensionData(String var1);

    public List<DimensionData> getByTitle(String var1);

    public String getCode(String var1);

    public String getTitle(String var1);
}

