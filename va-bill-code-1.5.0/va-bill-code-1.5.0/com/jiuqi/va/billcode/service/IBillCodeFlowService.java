/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.billcode.service;

import com.jiuqi.va.billcode.domain.BillCodeFlowDTO;

public interface IBillCodeFlowService {
    public BillCodeFlowDTO getFlowNumberByDimensions(String var1);

    public long updateFlowNumberByDimensions(String var1, int var2);

    public BillCodeFlowDTO addFlowNumber(BillCodeFlowDTO var1);

    public long reUpdateFlowNumberByDimensions(String var1, int var2);
}

