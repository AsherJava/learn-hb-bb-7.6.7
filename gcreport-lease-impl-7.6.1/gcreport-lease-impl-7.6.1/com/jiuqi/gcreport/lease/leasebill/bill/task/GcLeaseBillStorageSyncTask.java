/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.lease.leasebill.bill.task;

import com.jiuqi.gcreport.lease.leasebill.bill.storage.GcLessorBillItemStorage;
import com.jiuqi.gcreport.lease.leasebill.bill.storage.GcLessorBillStorage;
import com.jiuqi.gcreport.lease.leasebill.bill.storage.GcTenantryBillItemStorage;
import com.jiuqi.gcreport.lease.leasebill.bill.storage.GcTenantryBillStorage;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcLeaseBillStorageSyncTask
implements CustomClassExecutor {
    private transient Logger logger = LoggerFactory.getLogger(GcLeaseBillStorageSyncTask.class);

    public void execute(DataSource dataSource) {
        try {
            GcLessorBillStorage.init("__default_tenant__");
            GcLessorBillItemStorage.init("__default_tenant__");
            GcTenantryBillStorage.init("__default_tenant__");
            GcTenantryBillItemStorage.init("__default_tenant__");
        }
        catch (Exception e) {
            this.logger.error("\u79df\u8d41\u53f0\u8d26\u5355\u636e\u521d\u59cb\u5316\u8868\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }
}

