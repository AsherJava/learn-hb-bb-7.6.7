/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tz.service;

import com.jiuqi.nr.io.tz.TzParams;

public interface IFlagStateDao {
    public void copySbId(TzParams var1);

    public void copyRptBizKeyOrder(TzParams var1);

    public int allFieldUpdateState(TzParams var1);

    public int notRecordChangeState(TzParams var1);

    public int addChangeState(TzParams var1);

    public int allRptFieldUpdateState(TzParams var1);

    public int countByState(TzParams var1, byte var2);

    public int countByRptState(TzParams var1, byte var2);
}

