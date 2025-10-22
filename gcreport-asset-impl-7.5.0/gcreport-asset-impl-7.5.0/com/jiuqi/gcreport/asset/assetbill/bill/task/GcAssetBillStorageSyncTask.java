/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.sql.CustomClassExecutor
 */
package com.jiuqi.gcreport.asset.assetbill.bill.task;

import com.jiuqi.gcreport.asset.assetbill.bill.storage.GcCombinedAssetBillItemStorage;
import com.jiuqi.gcreport.asset.assetbill.bill.storage.GcCombinedAssetBillStorage;
import com.jiuqi.gcreport.asset.assetbill.bill.storage.GcCommonAssetBillItemStorage;
import com.jiuqi.gcreport.asset.assetbill.bill.storage.GcCommonAssetBillStorage;
import com.jiuqi.np.sql.CustomClassExecutor;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcAssetBillStorageSyncTask
implements CustomClassExecutor {
    private transient Logger logger = LoggerFactory.getLogger(GcAssetBillStorageSyncTask.class);

    public void execute(DataSource dataSource) {
        try {
            GcCombinedAssetBillStorage.init("__default_tenant__");
            GcCombinedAssetBillItemStorage.init("__default_tenant__");
            GcCommonAssetBillStorage.init("__default_tenant__");
            GcCommonAssetBillItemStorage.init("__default_tenant__");
        }
        catch (Exception e) {
            this.logger.error("\u8d44\u4ea7\u53f0\u8d26\u5355\u636e\u8868\u521d\u59cb\u5316\u5931\u8d25\uff1a" + e.getMessage(), e);
        }
    }
}

