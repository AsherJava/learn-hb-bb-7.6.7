/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.PageVO
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.bill.domain.BillDTO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.Map;

public interface BillService {
    public PageVO<Map<String, List<Map<String, Object>>>> getBill(BillDTO var1);
}

