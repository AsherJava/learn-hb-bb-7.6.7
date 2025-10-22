/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.history;

import com.jiuqi.nr.bpm.movedata.NrHistoricProcessInstanceEntityImpl;
import java.util.List;

public interface IHistoricProcessInstanceEntityDao {
    public boolean batchInsert(List<NrHistoricProcessInstanceEntityImpl> var1);

    public void deleteByProcessId(String var1);

    public List<NrHistoricProcessInstanceEntityImpl> queryByProcessId(String var1);
}

