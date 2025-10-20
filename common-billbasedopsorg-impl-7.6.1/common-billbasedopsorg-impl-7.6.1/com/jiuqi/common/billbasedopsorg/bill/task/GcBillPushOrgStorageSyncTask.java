/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.common.billbasedopsorg.bill.task;

import com.jiuqi.common.billbasedopsorg.bill.storage.GcBillPushOrgStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GcBillPushOrgStorageSyncTask
implements StorageSyncTask {
    private transient Logger logger = LoggerFactory.getLogger(GcBillPushOrgStorageSyncTask.class);

    public void execute() {
        try {
            String tenantName = ShiroUtil.getTenantName();
            GcBillPushOrgStorage.init(tenantName);
        }
        catch (Exception e) {
            this.logger.error("\u5355\u636e\u751f\u6210\u7ec4\u7ec7\u673a\u6784\u4e3b\u8868\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    public String getVersion() {
        return "20250713-2211";
    }
}

