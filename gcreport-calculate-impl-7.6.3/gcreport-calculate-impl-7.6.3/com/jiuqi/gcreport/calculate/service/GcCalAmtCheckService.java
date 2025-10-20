/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO
 *  com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule
 */
package com.jiuqi.gcreport.calculate.service;

import com.jiuqi.gcreport.offsetitem.dto.GcOffSetVchrItemDTO;
import com.jiuqi.gcreport.unionrule.dto.AbstractUnionRule;
import java.math.BigDecimal;
import java.util.Collection;

public interface GcCalAmtCheckService {
    public BigDecimal customizeMatchOrient(AbstractUnionRule var1, Collection<GcOffSetVchrItemDTO> var2, BigDecimal var3, BigDecimal var4);
}

