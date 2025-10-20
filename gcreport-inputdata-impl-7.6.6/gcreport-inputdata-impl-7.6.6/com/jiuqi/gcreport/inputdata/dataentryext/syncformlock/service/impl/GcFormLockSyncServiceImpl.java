/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataentry.bean.FormLockParam
 *  com.jiuqi.nr.dataentry.service.IFormLockSyncService
 */
package com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.service.impl;

import com.jiuqi.gcreport.inputdata.dataentryext.syncformlock.service.GcFormLockService;
import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.service.IFormLockSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcFormLockSyncServiceImpl
implements IFormLockSyncService {
    @Autowired
    private GcFormLockService gcFormLockService;

    public void syncLockForm(FormLockParam param) {
        this.gcFormLockService.lockForm(param);
    }

    public void syncBatchLockForm(FormLockParam param) {
    }
}

