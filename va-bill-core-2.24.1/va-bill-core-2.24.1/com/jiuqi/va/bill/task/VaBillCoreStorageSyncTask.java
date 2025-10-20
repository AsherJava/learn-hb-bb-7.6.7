/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.bill.task;

import com.jiuqi.va.bill.storage.AuthBillActionStorage;
import com.jiuqi.va.bill.storage.BillAttachOptionStorage;
import com.jiuqi.va.bill.storage.BillChangeRecordDataStorage;
import com.jiuqi.va.bill.storage.BillChangeRecordStorage;
import com.jiuqi.va.bill.storage.BillLockStorage;
import com.jiuqi.va.bill.storage.BillOptionStorage;
import com.jiuqi.va.bill.storage.BillRuleOptionItemStorage;
import com.jiuqi.va.bill.storage.BillRuleOptionStorage;
import com.jiuqi.va.bill.storage.EnumDataStorage;
import com.jiuqi.va.bill.storage.FormulaDebugContextStorage;
import com.jiuqi.va.bill.storage.FormulaDebugWhiteListStorage;
import com.jiuqi.va.bill.storage.MetaInfoExtendBillStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import org.springframework.stereotype.Component;

@Component
public class VaBillCoreStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        EnumDataStorage.init(tenantName);
        BillLockStorage.init(tenantName);
        BillOptionStorage.init(tenantName);
        BillAttachOptionStorage.init(tenantName);
        BillRuleOptionStorage.init(tenantName);
        BillRuleOptionItemStorage.init(tenantName);
        MetaInfoExtendBillStorage.init(tenantName);
        AuthBillActionStorage.init(tenantName);
        BillChangeRecordStorage.init(tenantName);
        BillChangeRecordDataStorage.init(tenantName);
        FormulaDebugContextStorage.init(tenantName);
        FormulaDebugWhiteListStorage.init(tenantName);
    }

    public String getVersion() {
        return "20240307-1700";
    }
}

