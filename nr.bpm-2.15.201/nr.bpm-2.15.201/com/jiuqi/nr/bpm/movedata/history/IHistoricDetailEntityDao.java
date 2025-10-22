/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.history;

import com.jiuqi.nr.bpm.movedata.NrHistoricDetailImpl;
import java.util.List;

public interface IHistoricDetailEntityDao {
    public boolean batchInsert(List<NrHistoricDetailImpl> var1);

    public void deleteByProcessId(String var1);

    public List<NrHistoricDetailImpl> queryByProcessId(String var1);
}

