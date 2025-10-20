/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.IMessageReceiver
 *  com.jiuqi.bi.core.messagequeue.MessageEngine
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager
 *  com.jiuqi.bi.core.nodekeeper.IConnectionProvider
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.certification.CertificationManager;
import com.jiuqi.bi.core.jobs.certification.Certifier;
import com.jiuqi.bi.core.jobs.core.bridge.AbstractJobBridge;
import com.jiuqi.bi.core.jobs.core.bridge.JobBridgeFactory;
import com.jiuqi.bi.core.jobs.core.bridge.UnsupportSchedulerBridge;
import com.jiuqi.bi.core.jobs.core.quartz.QuartzBridge;
import com.jiuqi.bi.core.jobs.defaultlog.LoggerConsumer;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.message.ConfigCacheRefreshReceiver;
import com.jiuqi.bi.core.jobs.message.JobCanceledMessageReceiver;
import com.jiuqi.bi.core.jobs.message.SchedulerRestartReceiver;
import com.jiuqi.bi.core.jobs.message.SchedulerShutdownReceiver;
import com.jiuqi.bi.core.jobs.message.SchedulerStartReceiver;
import com.jiuqi.bi.core.jobs.message.SubJobFinishedMessageReceiver;
import com.jiuqi.bi.core.jobs.message.usermessage.IUserMessageSender;
import com.jiuqi.bi.core.jobs.message.usermessage.UserMessageSenderFactory;
import com.jiuqi.bi.core.jobs.realtime.core.RealTimeJobRunnerFactory;
import com.jiuqi.bi.core.jobs.realtime.local.RealTimeLocalJobRunner;
import com.jiuqi.bi.core.jobs.realtime.quartz.RealTimeQuartzJobRunner;
import com.jiuqi.bi.core.messagequeue.IMessageReceiver;
import com.jiuqi.bi.core.messagequeue.MessageEngine;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.core.nodekeeper.GlobalConnectionProviderManager;
import com.jiuqi.bi.core.nodekeeper.IConnectionProvider;
import com.jiuqi.bi.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobLifeCycle {
    private static Logger logger = LoggerFactory.getLogger(JobLifeCycle.class);
    private static final String DEFAULT_OSS_BUCKET = "_JQ_JOBS";
    private static String bucket = "_JQ_JOBS";

    public static void ready(IConnectionProvider provider) {
        JobLifeCycle.ready(provider, null, true);
    }

    public static void ready(IConnectionProvider provider, boolean useQuartz) {
        JobLifeCycle.ready(provider, null, useQuartz);
    }

    public static void ready(IConnectionProvider provider, Certifier certifier, boolean useQuartz) {
        JobLifeCycle.ready(provider, certifier, useQuartz, false, null, null);
    }

    public static void ready(IConnectionProvider provider, Certifier certifier, boolean useQuartzForScheduleJob, boolean useQuartzForRealtimeJob) {
        JobLifeCycle.ready(provider, certifier, useQuartzForScheduleJob, useQuartzForRealtimeJob, null, null);
    }

    public static void ready(IConnectionProvider provider, Certifier certifier, boolean useQuartzForScheduleJob, boolean useQuartzForRealtimeJob, String ossBucket, IUserMessageSender messageSender) {
        JobLifeCycle.ready(provider, certifier, useQuartzForScheduleJob, useQuartzForRealtimeJob, ossBucket, messageSender, null);
    }

    public static void ready(IConnectionProvider provider, Certifier certifier, boolean useQuartzForScheduleJob, boolean useQuartzForRealtimeJob, String ossBucket, IUserMessageSender messageSender, String applicationName) {
        GlobalConnectionProviderManager.setConnectionProvider((IConnectionProvider)provider);
        CertificationManager.getInstance().setCertifier(certifier);
        MessageEngine.getInstance().registReceiver((IMessageReceiver)new JobCanceledMessageReceiver());
        MessageEngine.getInstance().registReceiver((IMessageReceiver)new SubJobFinishedMessageReceiver());
        MessageEngine.getInstance().registReceiver((IMessageReceiver)new ConfigCacheRefreshReceiver());
        MessageEngine.getInstance().registReceiver((IMessageReceiver)new SchedulerStartReceiver());
        MessageEngine.getInstance().registReceiver((IMessageReceiver)new SchedulerRestartReceiver());
        MessageEngine.getInstance().registReceiver((IMessageReceiver)new SchedulerShutdownReceiver());
        JobOperationManager manager = new JobOperationManager();
        try {
            manager.terminateAllUnfinishedInstance();
        }
        catch (Exception e) {
            logger.error("\u65e0\u6cd5\u7ec8\u6b62\u5f02\u5e38\u505c\u6b62\u7684\u4efb\u52a1", e);
        }
        JobBridgeFactory.getInstance().setBridge(useQuartzForScheduleJob ? new QuartzBridge() : new UnsupportSchedulerBridge());
        RealTimeJobRunnerFactory.getInstance().setRunner(useQuartzForRealtimeJob ? new RealTimeQuartzJobRunner() : new RealTimeLocalJobRunner());
        if (StringUtils.isNotEmpty((String)ossBucket)) {
            bucket = ossBucket;
        }
        if (applicationName == null) {
            applicationName = DistributionManager.getInstance().self().getName();
        }
        ConfigManager.getInstance().setApplicationName(applicationName);
        UserMessageSenderFactory.getInstance().setMessageSender(messageSender);
    }

    public static void start() throws Exception {
        logger.info("\u5f00\u59cb\u542f\u52a8\u5f02\u6b65\u65e5\u5fd7\u7ebf\u7a0b");
        LoggerConsumer.getInstance().start(4096);
        logger.info("\u5f02\u6b65\u65e5\u5fd7\u7ebf\u7a0b\u542f\u52a8\u5b8c\u6bd5");
        logger.info("\u5f00\u59cb\u521d\u59cb\u5316\u4efb\u52a1\u8c03\u5ea6\u5668");
        JobBridgeFactory.getInstance().getDefaultBridge().init();
        logger.info("\u4efb\u52a1\u8c03\u5ea6\u5668\u521d\u59cb\u5316\u5b8c\u6bd5");
    }

    public static void stop() throws Exception {
        AbstractJobBridge defaultBridge = JobBridgeFactory.getInstance().getDefaultBridge();
        if (defaultBridge != null) {
            logger.info("\u6b63\u5728\u505c\u6b62\u4efb\u52a1\u8c03\u5ea6\u5668");
            defaultBridge.shutdownAll();
            logger.info("\u5df2\u7ecf\u5c06\u4efb\u52a1\u8c03\u5ea6\u5668\u505c\u6b62");
        }
        logger.info("\u6b63\u5728\u505c\u6b62\u5f02\u6b65\u65e5\u5fd7\u7ebf\u7a0b");
        LoggerConsumer.getInstance().stop();
        logger.info("\u5df2\u7ecf\u505c\u6b62\u5f02\u6b65\u65e5\u5fd7\u7ebf\u7a0b");
    }

    public static String getBucket() {
        return bucket;
    }
}

