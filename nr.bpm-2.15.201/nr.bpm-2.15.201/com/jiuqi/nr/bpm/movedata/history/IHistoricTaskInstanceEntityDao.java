/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.history;

import com.jiuqi.nr.bpm.movedata.NrHistoricTaskInstanceEntityImpl;
import java.util.List;

public interface IHistoricTaskInstanceEntityDao {
    public boolean batchInsert(List<NrHistoricTaskInstanceEntityImpl> var1);

    public void deleteByProcessId(String var1);

    public List<NrHistoricTaskInstanceEntityImpl> queryByProcessId(String var1);
}

