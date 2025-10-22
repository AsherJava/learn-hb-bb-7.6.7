/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.service;

import com.jiuqi.nr.data.logic.internal.obj.CKRRec;
import java.util.List;

public interface ICKRRecService {
    public int insert(CKRRec var1);

    public int deleteByBatchIds(List<String> var1);

    public int deleteBeforeStartTime(long var1);

    public List<String> getBatchIdsBeforeTime(long var1);
}

