/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.spi;

import com.jiuqi.nr.datacrud.IQueryInfo;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.spi.IEnumFillNode;
import java.util.List;

public interface FillDataProvider {
    public List<List<String>> fillData(IQueryInfo var1, RegionRelation var2);

    public List<List<String>> fillData(IQueryInfo var1, RegionRelation var2, List<IEnumFillNode> var3);
}

