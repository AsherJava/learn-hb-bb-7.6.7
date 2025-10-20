/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.service;

import com.jiuqi.va.bill.domain.debug.BillFormulaDebugWhiteListDO;
import java.util.List;

public interface BillFormulaDebugWhiteListService {
    public int saveWhiteList(BillFormulaDebugWhiteListDO var1);

    public int deleteWhiteList(BillFormulaDebugWhiteListDO var1);

    public List<BillFormulaDebugWhiteListDO> whiteList();

    public boolean checkExist(BillFormulaDebugWhiteListDO var1);
}

