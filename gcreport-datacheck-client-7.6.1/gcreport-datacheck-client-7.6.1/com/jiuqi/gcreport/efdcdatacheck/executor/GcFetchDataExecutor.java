/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.efdcdatacheck.executor;

import com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo;

public interface GcFetchDataExecutor {
    public GcFetchDataResultInfo execute(GcFetchDataEnvContext var1);

    public boolean isMatch(GcFetchDataEnvContext var1);

    public GcFetchDataInfo getFieldDefineList(GcFetchDataEnvContext var1);
}

