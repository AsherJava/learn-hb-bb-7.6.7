/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao;

import com.jiuqi.nr.bpm.movedata.NrTimerJobEntityImpl;
import java.util.List;

public interface TimerJobDao {
    public boolean insert(NrTimerJobEntityImpl var1);

    public boolean batchInsert(List<NrTimerJobEntityImpl> var1);

    public List<NrTimerJobEntityImpl> queryByProcInstId(String var1);

    public boolean deleteByProcInstId(String var1);
}

