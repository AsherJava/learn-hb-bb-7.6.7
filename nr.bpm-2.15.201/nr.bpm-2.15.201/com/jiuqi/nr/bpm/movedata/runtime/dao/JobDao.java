/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao;

import com.jiuqi.nr.bpm.movedata.NrJobEntityImpl;
import java.util.List;

public interface JobDao {
    public boolean insert(NrJobEntityImpl var1);

    public boolean batchInsert(List<NrJobEntityImpl> var1);

    public List<NrJobEntityImpl> queryByProcInstId(String var1);

    public boolean deleteByProcInstId(String var1);
}

