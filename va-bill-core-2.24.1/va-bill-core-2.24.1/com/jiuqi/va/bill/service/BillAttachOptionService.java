/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.option.OptionItemDTO
 *  com.jiuqi.va.domain.option.OptionItemVO
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.bill.domain.option.BillAttachOptionDO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.option.OptionItemDTO;
import com.jiuqi.va.domain.option.OptionItemVO;
import java.util.List;

public interface BillAttachOptionService {
    public List<OptionItemVO> list(OptionItemDTO var1);

    public R update(BillAttachOptionDO var1);
}

