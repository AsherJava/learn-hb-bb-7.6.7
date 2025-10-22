/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.history;

import com.jiuqi.nr.bpm.movedata.NrHistoricVariableInstanceEntityImpl;
import java.util.List;

public interface IHistoricVariableInstanceEntityDao {
    public boolean batchInsert(List<NrHistoricVariableInstanceEntityImpl> var1);

    public void deleteByProcessId(String var1);

    public List<NrHistoricVariableInstanceEntityImpl> queryByProcessId(String var1);
}

