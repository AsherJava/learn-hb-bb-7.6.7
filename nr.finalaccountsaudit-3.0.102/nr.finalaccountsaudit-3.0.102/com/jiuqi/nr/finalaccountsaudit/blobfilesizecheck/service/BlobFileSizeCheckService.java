/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckParam;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.bean.BlobFileSizeCheckReturnInfo;
import com.jiuqi.nr.finalaccountsaudit.blobfilesizecheck.service.BlobFileSizeCheck;
import org.springframework.stereotype.Service;

@Service
public class BlobFileSizeCheckService {
    public BlobFileSizeCheckReturnInfo blobFileSizeCheck(BlobFileSizeCheckParam param, AsyncTaskMonitor monitor) throws Exception {
        BlobFileSizeCheck blobCheck = new BlobFileSizeCheck();
        return blobCheck.blobFileSizeCheck(param, monitor);
    }
}

