/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  org.quartz.JobDataMap
 *  org.quartz.JobExecutionContext
 *  org.quartz.JobKey
 *  org.quartz.Trigger
 *  org.quartz.Trigger$CompletedExecutionInstruction
 *  org.quartz.TriggerListener
 */
package com.jiuqi.bi.core.jobs.core.listener;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import java.sql.SQLException;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalTriggerListener
implements TriggerListener {
    private static final Logger logger = LoggerFactory.getLogger(GlobalTriggerListener.class);
    private String schedulerName;
    private final JobOperationManager operation = new JobOperationManager();

    public GlobalTriggerListener(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        logger.trace(this.schedulerName + ">>>trigger:\u3010" + trigger.getKey().getName() + "\u3011\u88ab\u89e6\u53d1;Job:\u3010" + context.getJobDetail().getKey().getName() + "\u3011\u7684execute\u65b9\u6cd5\u5373\u5c06\u88ab\u6267\u884c");
        try {
            ConfigManager.getInstance().deleteJobOnlyInNode(context.getJobDetail().getKey().getName());
        }
        catch (JobsException e) {
            logger.error("\u5220\u9664\u6267\u884c\u8282\u70b9\u914d\u7f6e\u3010deleteJobOnlyInNode\u3011\u5f02\u5e38", e);
        }
    }

    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        boolean access = false;
        logger.trace("trigger:\u3010" + trigger.getKey().getName() + "\u3011\u88ab\u89e6\u53d1;;;Job:\u3010" + context.getJobDetail().getKey().getName() + "\u3011\u7684execute\u65b9\u6cd5\u5373\u5c06\u88ab\u6267\u884c");
        logger.trace("--\u5e76\u6267\u884c\u5426\u51b3\u6743\uff1a\u3010" + access + "\u3011");
        return access;
    }

    public void triggerMisfired(Trigger trigger) {
        logger.trace("Trigger:\u3010" + trigger.getKey().getName() + "\u3011\u9519\u8fc7\u89e6\u53d1");
        if (trigger.mayFireAgain()) {
            return;
        }
        JobKey jobKey = trigger.getJobKey();
        String group = jobKey.getGroup();
        if (group.startsWith("com.jiuqi.bi.jobs.realtime")) {
            String instanceId = jobKey.getName();
            try {
                this.operation.jobTimeout(instanceId, "\u8d85\u65f6\u672a\u88ab\u89e6\u53d1\u6267\u884c\uff0c\u4efb\u52a1\u5f02\u5e38\u7ed3\u675f");
            }
            catch (Exception e) {
                logger.error("\u8bb0\u5f55\u4efb\u52a1\u5b9e\u4f8b\u72b6\u6001\u51fa\u9519:" + e.getMessage(), e);
            }
            return;
        }
        JobDataMap jobDataMap = trigger.getJobDataMap();
        if (jobDataMap.containsKey((Object)"__sys_instanceid")) {
            String instanceId = jobDataMap.getString("__sys_instanceid");
            try {
                this.operation.jobTimeout(instanceId, "\u8d85\u65f6\u672a\u88ab\u89e6\u53d1\u6267\u884c\uff0c\u4efb\u52a1\u5f02\u5e38\u7ed3\u675f");
            }
            catch (Exception e) {
                logger.error("\u8bb0\u5f55\u4efb\u52a1\u5b9e\u4f8b\u72b6\u6001\u51fa\u9519:" + e.getMessage(), e);
            }
        }
    }

    public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        String triggerName = trigger.getKey().getName();
        String jobName = context.getJobDetail().getKey().getName();
        logger.trace("Trigger\uff1a\u3010" + triggerName + "\u3011\u88ab\u89e6\u53d1;;;\u5e76\u4e14\u5df2\u5b8c\u6210Job\uff1a\u3010" + jobName + "\u3011 \u7684\u6267\u884c;;;CompletedExecutionInstruction\u503c\u4e3a\uff1a" + triggerInstructionCode);
        if (triggerInstructionCode == Trigger.CompletedExecutionInstruction.SET_TRIGGER_ERROR || triggerInstructionCode == Trigger.CompletedExecutionInstruction.SET_ALL_JOB_TRIGGERS_ERROR) {
            String msg = "\u4efb\u52a1\u89e6\u53d1\u5931\u8d25\uff0c\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\uff01\u96c6\u7fa4\u4e2d\u53ef\u80fd\u5b58\u5728\u4ee3\u7801\u4e0d\u4e00\u81f4\u8282\u70b9\uff0c\u5f53\u524d\u8282\u70b9\u4e3a\uff1a" + DistributionManager.getInstance().self();
            logger.error(msg);
            JobOperationManager jobOperationManager = new JobOperationManager();
            String instanceId = triggerName;
            JobInstanceBean instanceBean = null;
            try {
                instanceBean = jobOperationManager.getInstanceById(instanceId);
                if (instanceBean == null) {
                    instanceId = context.getJobDetail().getJobDataMap().getString("__sys_instanceid");
                    instanceBean = jobOperationManager.getInstanceById(instanceId);
                }
            }
            catch (JobsException e) {
                logger.error("\u67e5\u8be2\u4efb\u52a1\u5b9e\u4f8b\u5931\u8d25", e);
            }
            if (instanceBean != null) {
                try {
                    jobOperationManager.jobExcepted(instanceBean.getInstanceId(), msg);
                }
                catch (SQLException throwables) {
                    logger.error("\u8bbe\u7f6e\u4efb\u52a1\u5b9e\u4f8b\u72b6\u6001\u4e3a[\u5f02\u5e38]\u5931\u8d25", throwables);
                }
            }
        }
    }
}

