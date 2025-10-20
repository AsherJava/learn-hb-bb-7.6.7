/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 */
package com.jiuqi.gcreport.temp.dto;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.gcreport.temp.dto.Message;

public class OnekeyProgressDataImpl
extends ProgressDataImpl<Message.ProgressResult> {
    protected static final long TASKLOG_EXPIRE_TIME_SECONDS = 1800L;
    protected static final String GROUP_NAME = "OnekeyMergeDataImpl";

    public OnekeyProgressDataImpl(String sn) {
        super(sn, (Object)new Message.ProgressResult(), 1800L, GROUP_NAME);
    }

    public OnekeyProgressDataImpl(String sn, Message.ProgressResult progressResult) {
        super(sn, (Object)progressResult, 1800L, GROUP_NAME);
    }
}

