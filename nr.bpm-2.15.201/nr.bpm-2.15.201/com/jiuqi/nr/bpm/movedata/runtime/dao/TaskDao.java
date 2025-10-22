/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao;

import com.jiuqi.nr.bpm.movedata.NrTaskEntityImpl;
import java.util.List;

public interface TaskDao {
    public boolean insert(NrTaskEntityImpl var1);

    public boolean batchInsert(List<NrTaskEntityImpl> var1);

    public List<NrTaskEntityImpl> queryByProcInstId(String var1);

    public boolean deleteByProcInstId(String var1);
}

