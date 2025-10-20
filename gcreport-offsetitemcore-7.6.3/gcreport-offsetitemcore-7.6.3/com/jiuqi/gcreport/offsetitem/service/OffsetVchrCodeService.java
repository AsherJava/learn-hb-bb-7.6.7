/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.service;

import com.jiuqi.gcreport.offsetitem.entity.GcOffsetVchrFlowEO;

public interface OffsetVchrCodeService {
    public GcOffsetVchrFlowEO getFlowNumberByDimensions(String var1);

    public long updateFlowNumberByDimensions(String var1, int var2);

    public long reUpdateFlowNumberByDimensions(String var1, int var2);
}

