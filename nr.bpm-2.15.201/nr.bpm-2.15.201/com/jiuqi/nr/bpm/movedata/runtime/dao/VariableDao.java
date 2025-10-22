/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao;

import com.jiuqi.nr.bpm.movedata.NrVariableInstanceEntityImpl;
import java.util.List;

public interface VariableDao {
    public boolean insert(NrVariableInstanceEntityImpl var1);

    public boolean batchInsert(List<NrVariableInstanceEntityImpl> var1);

    public List<NrVariableInstanceEntityImpl> queryByProcInstId(String var1);

    public boolean deleteByProcInstId(String var1);
}

