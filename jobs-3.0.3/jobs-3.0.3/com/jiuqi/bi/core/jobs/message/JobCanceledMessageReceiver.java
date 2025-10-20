/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageReceiver
 *  com.jiuqi.bi.core.messagequeue.Message
 *  com.jiuqi.bi.core.messagequeue.MessageItem
 *  com.jiuqi.bi.util.StringUtils
 *  org.quartz.JobKey
 *  org.quartz.Scheduler
 */
package com.jiuqi.bi.core.jobs.message;

import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import com.jiuqi.bi.core.messagequeue.IMessageReceiver;
import com.jiuqi.bi.core.messagequeue.Message;
import com.jiuqi.bi.core.messagequeue.MessageItem;
import com.jiuqi.bi.util.StringUtils;
import java.util.List;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobCanceledMessageReceiver
implements IMessageReceiver {
    public static final String MSG_INSTANCE_TAG = "__instance__";
    public static final String ID = "com.jiuqi.bi.jobs.jobcancel";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private JobOperationManager operationManager = new JobOperationManager();

    public String getGroupId() {
        return ID;
    }

    public void receive(Message msg) {
        List items = msg.getItems();
        for (MessageItem item : items) {
            String category;
            String resource = item.getResourceId();
            String[] strings = resource.split("@");
            if (strings.length != 2) {
                this.logger.warn("\u4efb\u52a1\u53d6\u6d88\u6d88\u606f\u683c\u5f0f\u9519\u8bef[" + resource + "]\uff0c\u8be5\u6d88\u606f\u5c06\u88ab\u5ffd\u7565");
            }
            if (MSG_INSTANCE_TAG.equals(category = strings[1])) {
                String instanceId = strings[0];
                try {
                    JobInstanceBean instance = this.operationManager.getInstanceById(instanceId);
                    if (instance == null) {
                        this.logger.warn("\u5904\u7406\u4efb\u52a1\u53d6\u6d88\u6d88\u606f\u53d1\u751f\u9519\u8bef\uff1a\u65e0\u6cd5\u627e\u5230\u4efb\u52a1\u5b9e\u4f8b[" + instanceId + "]\uff0c\u8be5\u6d88\u606f\u5c06\u88ab\u5ffd\u7565");
                        return;
                    }
                    if (StringUtils.isEmpty((String)instance.getQuartzInstance())) {
                        this.logger.info("\u4efb\u52a1\u5b9e\u4f8b[" + instanceId + "]\u672a\u88ab\u8c03\u5ea6\u5668\u8c03\u5ea6\uff0c\u4e3a\u7b49\u5f85\u8c03\u5ea6\u72b6\u6001\u3002\u76f4\u63a5\u4fee\u6539instance\u72b6\u6001\u4e3a\u53d6\u6d88");
                        this.operationManager.jobCanceled(instanceId);
                        return;
                    }
                    int level = instance.getLevel();
                    Scheduler scheduler = SchedulerManager.getInstance().getScheduler(level);
                    scheduler.interrupt(instance.getQuartzInstance());
                }
                catch (Exception e) {
                    this.logger.error("\u5904\u7406\u4efb\u52a1\u53d6\u6d88\u6d88\u606f\u53d1\u751f\u9519\u8bef", e);
                }
                continue;
            }
            String jobId = strings[0];
            try {
                Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
                JobMonitorManager monitorManager = JobMonitorManager.getInstance(category);
                String instanceID = monitorManager.getJobRunningInstanceID(jobId);
                if (instanceID == null) {
                    this.logger.info("\u4efb\u52a1[" + jobId + "]\u5b9e\u4f8b\u5747\u5df2\u5b8c\u6210\uff0c\u5ffd\u7565\u6d88\u606f");
                    return;
                }
                JobInstanceBean instance = this.operationManager.getInstanceById(instanceID);
                if (StringUtils.isEmpty((String)instance.getQuartzInstance())) {
                    this.logger.info("\u4efb\u52a1\u5b9e\u4f8b[" + instanceID + "]\u672a\u88ab\u8c03\u5ea6\u5668\u8c03\u5ea6\uff0c\u4e3a\u7b49\u5f85\u8c03\u5ea6\u72b6\u6001\u3002\u76f4\u63a5\u4fee\u6539instance\u72b6\u6001\u4e3a\u53d6\u6d88");
                    this.operationManager.jobCanceled(instanceID);
                    return;
                }
                scheduler.interrupt(new JobKey(jobId, category));
            }
            catch (Exception e) {
                this.logger.error("\u5904\u7406\u4efb\u52a1\u53d6\u6d88\u6d88\u606f\u53d1\u751f\u9519\u8bef", e);
            }
        }
    }
}

