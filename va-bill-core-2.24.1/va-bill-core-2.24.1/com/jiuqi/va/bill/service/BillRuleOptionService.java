/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.bill.domain.option.BillRuleOptionVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import java.util.List;

public interface BillRuleOptionService {
    public List<BillRuleOptionVO> list(OptionItemDTO var1);

    public R update(BillRuleOptionVO var1);
}

