/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.event.StartReadyEvent
 *  com.jiuqi.nvwa.sf.Framework
 */
package com.jiuqi.dc.taskscheduling.api.listeners;

import com.jiuqi.dc.base.common.event.StartReadyEvent;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerClient;
import com.jiuqi.dc.taskscheduling.api.TaskHandlerFactory;
import com.jiuqi.nvwa.sf.Framework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component(value="dcTaskHandlerStartReadyEventListener")
public class TaskHandlerStartReadyEventListener
implements ApplicationListener<StartReadyEvent>,
ApplicationContextAware {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TaskHandlerFactory taskHandlerFactory;

    @Override
    @Async
    public void onApplicationEvent(StartReadyEvent event) {
        while (!Framework.getInstance().startSuccessful()) {
            try {
                Thread.sleep(10000L);
            }
            catch (Exception exception) {}
        }
        try {
            TaskHandlerClient taskHandlerClient = this.taskHandlerFactory.getMainTaskHandlerClient();
            taskHandlerClient.regist();
        }
        catch (Exception e) {
            this.logger.warn("\u670d\u52a1\u542f\u52a8\u6ce8\u518c\u52a8\u6001\u4efb\u52a1\u53d1\u751f\u9519\u8bef\uff1a" + e.getMessage());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }
}

