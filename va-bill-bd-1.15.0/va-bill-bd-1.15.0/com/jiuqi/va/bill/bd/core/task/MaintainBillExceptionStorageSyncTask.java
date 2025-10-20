/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.bill.bd.core.task;

import com.jiuqi.va.bill.bd.core.storage.MaintainBillExceptionStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import org.springframework.stereotype.Component;

@Component
public class MaintainBillExceptionStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        MaintainBillExceptionStorage.init(tenantName);
    }

    public String getVersion() {
        return "20210322-1410";
    }
}

