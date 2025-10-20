/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCancelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.impl;

import com.jiuqi.gcreport.financialcheckImpl.offset.adjust.service.impl.FinancialCheckOffsetServiceImpl;
import com.jiuqi.gcreport.offsetitem.service.GcOffSetItemAdjustCancelService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GcOffSetItemAdjustCancelServiceImpl
implements GcOffSetItemAdjustCancelService {
    @Autowired
    private FinancialCheckOffsetServiceImpl financialCheckOffsetService;

    @Transactional(rollbackFor={Exception.class})
    public void cancelOffsetByOffsetGroupId(Collection<String> srcOffsetGroupIds) {
        this.financialCheckOffsetService.batchDeleteByOffsetGroupId(srcOffsetGroupIds, null);
    }
}

