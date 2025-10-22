/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao;

import com.jiuqi.nr.bpm.movedata.NrSuspendedJobEntityImpl;
import java.util.List;

public interface SuspendedJobDao {
    public boolean insert(NrSuspendedJobEntityImpl var1);

    public boolean batchInsert(List<NrSuspendedJobEntityImpl> var1);

    public List<NrSuspendedJobEntityImpl> queryByProcInstId(String var1);

    public boolean deleteByProcInstId(String var1);
}

