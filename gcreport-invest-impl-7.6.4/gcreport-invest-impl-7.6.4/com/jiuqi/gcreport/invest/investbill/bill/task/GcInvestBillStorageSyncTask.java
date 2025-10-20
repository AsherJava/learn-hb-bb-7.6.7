/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.invest.investbill.bill.task;

import com.jiuqi.gcreport.invest.investbill.bill.storage.GcFairValueBillStorage;
import com.jiuqi.gcreport.invest.investbill.bill.storage.GcFairValueFixedItemBillStorage;
import com.jiuqi.gcreport.invest.investbill.bill.storage.GcFairValueOtherItemBillStorage;
import com.jiuqi.gcreport.invest.investbill.bill.storage.GcInvestBillItemStorage;
import com.jiuqi.gcreport.invest.investbill.bill.storage.GcInvestBillStorage;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcInvestBillStorageSyncTask
implements CustomClassExecutor {
    private transient Logger logger = LoggerFactory.getLogger(GcInvestBillStorageSyncTask.class);

    public void execute(DataSource dataSource) {
        try {
            GcInvestBillStorage.init("__default_tenant__");
            GcInvestBillItemStorage.init("__default_tenant__");
            GcFairValueBillStorage.init("__default_tenant__");
            GcFairValueFixedItemBillStorage.init("__default_tenant__");
            GcFairValueOtherItemBillStorage.init("__default_tenant__");
        }
        catch (Exception e) {
            this.logger.error("\u6295\u8d44\u548c\u516c\u5141\u53f0\u8d26\u5355\u636e\u521d\u59cb\u5316\u8868\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }
}

