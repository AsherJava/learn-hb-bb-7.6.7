/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api;

import com.jiuqi.nr.data.logic.api.param.cksr.CheckStatusQueryPar;
import com.jiuqi.nr.data.logic.api.param.cksr.CheckStatusRecord;
import java.util.Collection;

public interface ICheckStatusService {
    public Collection<CheckStatusRecord> getCheckStatusRecords(CheckStatusQueryPar var1);
}

