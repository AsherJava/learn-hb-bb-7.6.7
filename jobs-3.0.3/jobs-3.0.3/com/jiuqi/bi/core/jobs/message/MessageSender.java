/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.messagequeue.MessageEngine
 */
package com.jiuqi.bi.core.jobs.message;

import com.jiuqi.bi.core.messagequeue.MessageEngine;

public class MessageSender {
    private MessageSender() {
    }

    public static void sendJobCanceledMessage(String jobId, String category) throws Exception {
        MessageEngine.getInstance().sendMessage("com.jiuqi.bi.jobs.jobcancel", jobId + "@" + category);
    }

    public static void sendJobCanceledMessage(String instanceId) throws Exception {
        MessageEngine.getInstance().sendMessage("com.jiuqi.bi.jobs.jobcancel", instanceId + "@" + "__instance__");
    }

    public static void sendSubJobFinishedMessage(String subInstanceId, String parentInstanceId) throws Exception {
        MessageEngine.getInstance().sendMessage("com.jiuqi.bi.jobs.subjobfinished", subInstanceId + "@" + parentInstanceId);
    }

    public static void sendConfigCacheRefreshMessage(String cacheId) throws Exception {
        MessageEngine.getInstance().sendMessage("com.jiuqi.bi.jobs.configCacheRefresh", cacheId);
    }

    public static void sendSchedulerRestartMessage(int[] levels) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        if (levels != null) {
            for (int level : levels) {
                stringBuilder.append(level).append(",");
            }
        }
        MessageEngine.getInstance().sendMessage("com.jiuqi.bi.jobs.schedulerRestart", stringBuilder.toString());
    }

    public static void sendSchedulerStartMessage(String nodeName) throws Exception {
        MessageEngine.getInstance().sendMessage("com.jiuqi.bi.jobs.schedulerStart", nodeName);
    }

    public static void sendSchedulerShutdownMessage(String nodeName) throws Exception {
        MessageEngine.getInstance().sendMessage("com.jiuqi.bi.jobs.schedulerShutdown", nodeName);
    }
}

