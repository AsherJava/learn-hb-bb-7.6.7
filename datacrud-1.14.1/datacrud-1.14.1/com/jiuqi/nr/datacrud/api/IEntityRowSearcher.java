/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.datacrud.api;

import com.jiuqi.nr.datacrud.spi.entity.MatchSource;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public interface IEntityRowSearcher {
    public IEntityRow search(MatchSource var1, String var2);
}

