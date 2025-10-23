/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.executor.msg.parser;

import com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInfo;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.ReceiverItem;
import java.util.List;

public class MessageInfoImpl
implements MessageInfo {
    private String title;
    private String subject;
    private String content;
    private List<ReceiverItem> receiver;

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public List<ReceiverItem> getReceiver() {
        return this.receiver;
    }

    public void setReceiver(List<ReceiverItem> receiver) {
        this.receiver = receiver;
    }
}

