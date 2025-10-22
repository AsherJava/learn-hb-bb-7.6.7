/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.runtime.dao;

import com.jiuqi.nr.bpm.movedata.NrIdentityLinkEntityImpl;
import java.util.List;

public interface IdentityLinkDao {
    public boolean insert(NrIdentityLinkEntityImpl var1);

    public boolean batchInsert(List<NrIdentityLinkEntityImpl> var1);

    public List<NrIdentityLinkEntityImpl> queryByProcInstId(String var1);

    public List<NrIdentityLinkEntityImpl> queryByTaskId(String var1);

    public boolean deleteByProcInstId(String var1);

    public boolean deleteByTaskId(String var1);
}

