/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata.history;

import com.jiuqi.nr.bpm.movedata.NrHistoricCommentImpl;
import java.util.List;

public interface IHistoricCommentEntityDao {
    public boolean batchInsert(List<NrHistoricCommentImpl> var1);

    public void deleteByProcessId(String var1);

    public List<NrHistoricCommentImpl> queryByProcessId(String var1);
}

