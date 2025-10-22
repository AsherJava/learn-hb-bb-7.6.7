/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.dao;

import java.util.Set;

public interface GcCarryOverOffsetDao {
    public Set<String> queryJournalMrecidsByWhere(String[] var1, Object[] var2, String var3);

    public Set<String> listEffectLongAdjustOffset(String[] var1, Object[] var2, String var3);
}

