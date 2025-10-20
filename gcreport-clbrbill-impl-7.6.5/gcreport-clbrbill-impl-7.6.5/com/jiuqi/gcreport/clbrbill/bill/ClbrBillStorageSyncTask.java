/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.domain.task.StorageSyncTask
 */
package com.jiuqi.gcreport.clbrbill.bill;

import com.jiuqi.gcreport.clbrbill.bill.storage.AuthorityChangeBillStorage;
import com.jiuqi.gcreport.clbrbill.bill.storage.AuthorityItemBillStorage;
import com.jiuqi.gcreport.clbrbill.bill.storage.AuthorityItemChangedBillStorage;
import com.jiuqi.gcreport.clbrbill.bill.storage.AuthorityManageBillStorage;
import com.jiuqi.gcreport.clbrbill.bill.storage.ClbrBillSrcItemStorage;
import com.jiuqi.gcreport.clbrbill.bill.storage.ClbrBillStorage;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.domain.task.StorageSyncTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ClbrBillStorageSyncTask
implements StorageSyncTask {
    private final Logger logger = LoggerFactory.getLogger(ClbrBillStorageSyncTask.class);

    public void execute() {
        String tenantName = ShiroUtil.getTenantName();
        try {
            ClbrBillStorage.init(tenantName);
            ClbrBillSrcItemStorage.init(tenantName);
        }
        catch (Exception e) {
            this.logger.error("\u534f\u540c\u5355\u636e\u8868\u521d\u59cb\u5316\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
        try {
            AuthorityManageBillStorage.init(tenantName);
            AuthorityChangeBillStorage.init(tenantName);
            AuthorityItemBillStorage.init(tenantName);
            AuthorityItemChangedBillStorage.init(tenantName);
        }
        catch (Exception e) {
            this.logger.error("\u6743\u9650\u7ba1\u7406\u5355\u5355\u636e\u521d\u59cb\u5316\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }

    public int getSortNum() {
        return 10;
    }

    public String getVersion() {
        return "20250627-1333";
    }
}

