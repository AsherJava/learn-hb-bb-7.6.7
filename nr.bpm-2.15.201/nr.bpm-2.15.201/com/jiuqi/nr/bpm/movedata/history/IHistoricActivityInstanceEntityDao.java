/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.history;

import com.jiuqi.nr.bpm.movedata.NrHistoricActivityInstanceEntityImpl;
import java.util.List;

public interface IHistoricActivityInstanceEntityDao {
    public boolean batchInsert(List<NrHistoricActivityInstanceEntityImpl> var1);

    public void deleteByProcessId(String var1);

    public List<NrHistoricActivityInstanceEntityImpl> queryByProcessId(String var1);
}

