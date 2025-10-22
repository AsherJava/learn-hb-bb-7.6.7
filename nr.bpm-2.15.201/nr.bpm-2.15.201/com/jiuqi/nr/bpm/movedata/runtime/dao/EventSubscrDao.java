/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao;

import com.jiuqi.nr.bpm.movedata.NrEventSubscrEntityImpl;
import java.util.List;

public interface EventSubscrDao {
    public boolean insert(NrEventSubscrEntityImpl var1);

    public boolean batchInsert(List<NrEventSubscrEntityImpl> var1);

    public List<NrEventSubscrEntityImpl> queryByProcInstId(String var1);

    public boolean deleteByProcInstId(String var1);
}

