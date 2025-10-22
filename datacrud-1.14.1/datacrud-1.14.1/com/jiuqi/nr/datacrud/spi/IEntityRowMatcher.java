/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.datacrud.spi;

import com.jiuqi.nr.datacrud.spi.entity.MatchSource;
import com.jiuqi.nr.datacrud.spi.entity.MatchValue;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public interface IEntityRowMatcher {
    public IEntityRow match(MatchSource var1, MatchValue var2);
}

