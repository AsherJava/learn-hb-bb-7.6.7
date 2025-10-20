/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageReceiver
 *  com.jiuqi.bi.core.messagequeue.Message
 *  com.jiuqi.bi.core.messagequeue.MessageItem
 */
package com.jiuqi.bi.core.jobs.message;

import com.jiuqi.bi.core.jobs.core.ISubJobFinishedNotifier;
import com.jiuqi.bi.core.jobs.message.SubJobFinishedNotifierFactory;
import com.jiuqi.bi.core.messagequeue.IMessageReceiver;
import com.jiuqi.bi.core.messagequeue.Message;
import com.jiuqi.bi.core.messagequeue.MessageItem;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubJobFinishedMessageReceiver
implements IMessageReceiver {
    public static final String ID = "com.jiuqi.bi.jobs.subjobfinished";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String getGroupId() {
        return ID;
    }

    public void receive(Message msg) {
        List items = msg.getItems();
        for (MessageItem item : items) {
            String resource = item.getResourceId();
            String[] strings = resource.split("@");
            if (strings.length != 2) {
                this.logger.warn("\u5b50\u4efb\u52a1\u7ed3\u675f\u6d88\u606f\u683c\u5f0f\u9519\u8bef[{}]\uff0c\u8be5\u6d88\u606f\u5c06\u88ab\u5ffd\u7565", (Object)resource);
            }
            String subInstanceId = strings[0];
            String parentInstanceId = strings[1];
            ISubJobFinishedNotifier subjobNotifier = SubJobFinishedNotifierFactory.getInstance().getSubjobNotifier(parentInstanceId);
            if (subjobNotifier == null) continue;
            subjobNotifier.notify(subInstanceId);
        }
    }
}

