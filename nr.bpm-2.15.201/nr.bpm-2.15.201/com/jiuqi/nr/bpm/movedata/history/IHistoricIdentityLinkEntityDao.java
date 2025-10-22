/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.history;

import com.jiuqi.nr.bpm.movedata.NrHistoricIdentityLinkEntityImpl;
import java.util.List;

public interface IHistoricIdentityLinkEntityDao {
    public boolean batchInsert(List<NrHistoricIdentityLinkEntityImpl> var1);

    public void deleteByProcessId(String var1);

    public List<NrHistoricIdentityLinkEntityImpl> queryByProcInstId(String var1);
}

