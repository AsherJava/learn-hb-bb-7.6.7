/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.lockDetail.service;

import com.jiuqi.nr.dataentry.lockDetail.param.LockDetailParamInfo;
import com.jiuqi.nr.dataentry.lockDetail.param.LockDetailReturnInfo;
import java.util.List;

public interface LockDetailTableService {
    public List<LockDetailReturnInfo> queryLockDetailList(LockDetailParamInfo var1);
}

