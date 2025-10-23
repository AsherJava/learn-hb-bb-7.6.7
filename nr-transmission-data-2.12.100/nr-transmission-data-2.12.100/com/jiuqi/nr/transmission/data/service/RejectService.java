/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.transmission.data.reject.RejectParamDTO;
import com.jiuqi.va.message.domain.VaMessageOption;

public interface RejectService {
    public void sendRejectAction(RejectParamDTO var1);

    public void acceptRejectAction(RejectParamDTO var1, VaMessageOption.MsgChannel var2, String var3);
}

