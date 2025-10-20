/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.va.biz.task;

import com.jiuqi.va.biz.storage.BizCommonRuleInfoStorage;
import com.jiuqi.va.biz.storage.BizCommonRuleStorage;
import com.jiuqi.va.biz.storage.VaMonitorBillRuleStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import org.springframework.stereotype.Component;

@Component
public class VaBizAllStorageSyncTask
implements StorageSyncTask {
    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        BizCommonRuleInfoStorage.init(tenantName);
        BizCommonRuleStorage.init(tenantName);
        VaMonitorBillRuleStorage.init(tenantName);
    }

    public String getVersion() {
        return "20240315-1700";
    }
}

