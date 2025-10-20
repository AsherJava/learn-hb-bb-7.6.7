/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext
 *  com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo
 *  com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service;

import com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo;

public interface GcFetchDataService {
    public GcFetchDataResultInfo fetchData(GcFetchDataEnvContext var1);

    public GcFetchDataInfo getFieldDefineList(GcFetchDataEnvContext var1);
}

