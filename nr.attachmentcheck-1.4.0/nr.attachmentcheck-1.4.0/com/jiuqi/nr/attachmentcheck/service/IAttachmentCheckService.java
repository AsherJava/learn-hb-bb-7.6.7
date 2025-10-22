/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.attachmentcheck.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckParam;
import com.jiuqi.nr.attachmentcheck.bean.AttachmentCheckReturnInfo;

public interface IAttachmentCheckService {
    public AttachmentCheckReturnInfo attachmentCheck(AttachmentCheckParam var1, AsyncTaskMonitor var2) throws Exception;
}

