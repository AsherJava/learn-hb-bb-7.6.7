/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.messaging.Message
 */
package com.jiuqi.common.taskschedule.stream.core.ack;

import org.springframework.messaging.Message;

public interface EntManualAck {
    public void ack(Message var1);
}

