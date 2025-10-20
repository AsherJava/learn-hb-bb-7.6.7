/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi
 */
package com.jiuqi.gcreport.billcore.offsetcheck.service;

import com.jiuqi.gcreport.datatrace.vo.GcDataTraceCondi;
import java.util.List;

public interface GcBillTracerService {
    public List<String> getAssociatedDataSrcGroupIds(GcDataTraceCondi var1);

    public void resetBillCode(GcDataTraceCondi var1);
}

