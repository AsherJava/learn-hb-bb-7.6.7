/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao;

import com.jiuqi.nr.bpm.movedata.NrExecutionEntityImpl;
import java.util.List;

public interface ExecutionDao {
    public boolean insert(NrExecutionEntityImpl var1);

    public boolean batchInsert(List<NrExecutionEntityImpl> var1);

    public List<NrExecutionEntityImpl> queryByProcInstId(String var1);

    public boolean deleteByProcInstId(String var1);
}

