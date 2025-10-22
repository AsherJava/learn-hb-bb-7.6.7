/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao;

import com.jiuqi.nr.bpm.movedata.NrDeadLetterJobEntityImpl;
import java.util.List;

public interface DeadLetterJobDao {
    public boolean insert(NrDeadLetterJobEntityImpl var1);

    public boolean batchInsert(List<NrDeadLetterJobEntityImpl> var1);

    public List<NrDeadLetterJobEntityImpl> queryByProcInstId(String var1);

    public boolean deleteByProcInstId(String var1);
}

