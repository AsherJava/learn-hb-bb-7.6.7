/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.mail.MessagingException
 */
package com.jiuqi.np.message.internal;

import javax.mail.MessagingException;

@Deprecated
public class MessageException
extends MessagingException {
    public MessageException() {
    }

    public MessageException(String s) {
        super(s);
    }

    public MessageException(String s, Exception e) {
        super(s, e);
    }
}

