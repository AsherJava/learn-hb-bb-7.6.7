/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.history;

import com.jiuqi.nr.bpm.movedata.NrHistoricAttachmentImpl;
import java.util.List;

public interface IHistoricAttachmentEntityDao {
    public boolean batchInsert(List<NrHistoricAttachmentImpl> var1);

    public void deleteByProcessId(String var1);

    public List<NrHistoricAttachmentImpl> queryByProcessId(String var1);
}

