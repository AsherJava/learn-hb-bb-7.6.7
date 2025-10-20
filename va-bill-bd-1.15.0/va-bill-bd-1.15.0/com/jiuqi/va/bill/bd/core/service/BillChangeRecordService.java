/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 */
package com.jiuqi.va.bill.bd.core.service;

import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordDO;
import com.jiuqi.va.bill.bd.core.domain.BillChangeRecordVO;
import com.jiuqi.va.domain.common.R;
import java.util.List;

public interface BillChangeRecordService {
    public List<BillChangeRecordDO> list(BillChangeRecordDO var1);

    public R add(BillChangeRecordDO var1);

    public R batchAdd(List<BillChangeRecordDO> var1);

    public List<BillChangeRecordVO> listByBillCode(BillChangeRecordDO var1);
}

