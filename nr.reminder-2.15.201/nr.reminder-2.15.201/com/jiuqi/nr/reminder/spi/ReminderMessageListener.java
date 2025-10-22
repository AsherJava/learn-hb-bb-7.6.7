/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.message.pojo.MessageDTO
 */
package com.jiuqi.nr.reminder.spi;

import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.nr.reminder.internal.Reminder;

public interface ReminderMessageListener {
    public void sendMessageBefore(Reminder var1, MessageDTO var2);
}

