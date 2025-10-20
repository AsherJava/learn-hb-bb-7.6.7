/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.message.usermessage;

import com.jiuqi.bi.core.jobs.message.usermessage.IUserMessageSender;

public class UserMessageSenderFactory {
    private static final UserMessageSenderFactory INSTANCE = new UserMessageSenderFactory();
    private IUserMessageSender messageSender;

    private UserMessageSenderFactory() {
    }

    public static UserMessageSenderFactory getInstance() {
        return INSTANCE;
    }

    public IUserMessageSender getMessageSender() {
        return this.messageSender;
    }

    public void setMessageSender(IUserMessageSender messageSender) {
        this.messageSender = messageSender;
    }
}

