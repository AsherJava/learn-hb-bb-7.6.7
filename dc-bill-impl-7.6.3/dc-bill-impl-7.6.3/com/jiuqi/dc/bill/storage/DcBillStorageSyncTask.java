/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.event.StartReadyEvent
 */
package com.jiuqi.dc.bill.storage;

import com.jiuqi.dc.base.common.event.StartReadyEvent;
import com.jiuqi.dc.bill.storage.DcAgeUnclearedBillItemStorage;
import com.jiuqi.dc.bill.storage.DcAgeUnclearedBillStorage;
import com.jiuqi.dc.bill.storage.DcBillAcctPeriodEnumStorage;
import com.jiuqi.dc.bill.storage.DcVoucherBillItemStorage;
import com.jiuqi.dc.bill.storage.DcVoucherBillStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DcBillStorageSyncTask
implements ApplicationListener<StartReadyEvent> {
    private final Logger logger = LoggerFactory.getLogger(DcBillStorageSyncTask.class);

    @Override
    public void onApplicationEvent(StartReadyEvent event) {
        try {
            DcBillAcctPeriodEnumStorage.init("__default_tenant__");
            DcVoucherBillStorage.init("__default_tenant__");
            DcVoucherBillItemStorage.init("__default_tenant__");
            DcAgeUnclearedBillStorage.init("__default_tenant__");
            DcAgeUnclearedBillItemStorage.init("__default_tenant__");
        }
        catch (Exception e) {
            this.logger.error("\u4e00\u672c\u8d26\u5355\u636e\u8868\u521d\u59cb\u5316\u5931\u8d25\uff1a " + e.getMessage(), e);
        }
    }
}

