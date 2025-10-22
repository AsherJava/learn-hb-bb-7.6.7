/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.gcreport.billcore.transaction;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.billcore.transaction.IBillTransaction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BillTransactionProxy {
    private static BillTransactionProxy billTransactionProxy;

    public static BillTransactionProxy getInstance() {
        if (null == billTransactionProxy) {
            billTransactionProxy = (BillTransactionProxy)SpringContextUtils.getBean(BillTransactionProxy.class);
        }
        return billTransactionProxy;
    }

    @Transactional(rollbackFor={Exception.class})
    public void save(IBillTransaction billTransaction) {
        billTransaction.saveWithTrans();
    }
}

