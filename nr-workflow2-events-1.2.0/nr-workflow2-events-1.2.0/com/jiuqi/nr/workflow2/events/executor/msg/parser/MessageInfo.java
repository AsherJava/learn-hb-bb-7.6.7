/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.executor.msg.parser;

import com.jiuqi.nr.workflow2.events.executor.msg.parser.ReceiverItem;
import java.util.List;

public interface MessageInfo {
    public String getTitle();

    public String getSubject();

    public String getContent();

    public List<ReceiverItem> getReceiver();
}

