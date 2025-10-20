/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.remote.event.RemoteSeviceChangeEvent
 *  com.jiuqi.nvwa.sf.Framework
 */
package com.jiuqi.dc.taskscheduling.api.listeners;

import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.nvwa.remote.event.RemoteSeviceChangeEvent;
import com.jiuqi.nvwa.sf.Framework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component(value="dcRemoteServiceChangeEventListener")
public class RemoteServiceChangeEventListener
implements ApplicationListener<RemoteSeviceChangeEvent> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;

    @Override
    @Async
    public void onApplicationEvent(RemoteSeviceChangeEvent event) {
        while (!Framework.getInstance().startSuccessful()) {
            try {
                Thread.sleep(10000L);
            }
            catch (Exception exception) {}
        }
        try {
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            taskHandlerClient.refresh(event);
        }
        catch (Exception e) {
            this.logger.error("\u670d\u52a1\u542f\u52a8\u6ce8\u518c\u52a8\u6001\u4efb\u52a1\u53d1\u751f\u9519\u8bef", e);
        }
    }
}

