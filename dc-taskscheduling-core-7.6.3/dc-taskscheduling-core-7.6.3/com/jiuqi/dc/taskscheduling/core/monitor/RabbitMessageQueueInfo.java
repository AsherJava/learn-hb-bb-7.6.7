/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.monitor;

import java.io.Serializable;

public class RabbitMessageQueueInfo
implements Serializable {
    private static final long serialVersionUID = 2311049269437738809L;
    private String queueName;
    private boolean isActive;
    private boolean isRunning;
    private int activeConsumer;
    private int consumer;
    private int messageCount;
    private String errorMsg;

    public RabbitMessageQueueInfo(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public int getActiveConsumer() {
        return this.activeConsumer;
    }

    public void setActiveConsumer(int activeConsumer) {
        this.activeConsumer = activeConsumer;
    }

    public int getConsumer() {
        return this.consumer;
    }

    public void setConsumer(int consumer) {
        this.consumer = consumer;
    }

    public int getMessageCount() {
        return this.messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\u961f\u5217\u540d\u79f0\uff1a").append(this.getQueueName()).append("\uff1b");
        sb.append("\u662f\u5426\u6d3b\u52a8\u72b6\u6001\uff1a").append(this.isActive() ? "\u662f\uff1b" : "\u5426\uff1b");
        sb.append("\u662f\u5426\u6b63\u5728\u8fd0\u884c\uff1a").append(this.isRunning() ? "\u662f\uff1b" : "\u5426\uff1b");
        sb.append("\u6d88\u8d39\u8005\u6570\u91cf\uff1a").append(this.getConsumer()).append("\uff1b");
        sb.append("\u6d3b\u52a8\u72b6\u6001\u7684\u6d88\u8d39\u8005\u6570\u91cf\uff1a").append(this.getActiveConsumer()).append("\uff1b");
        sb.append("\u5f85\u5904\u7406\u7684\u6d88\u606f\u603b\u6570\uff1a").append(this.getMessageCount()).append("\uff1b");
        sb.append("\u5f02\u5e38\u4fe1\u606f\uff1a").append(this.getErrorMsg()).append("\u3002");
        return sb.toString();
    }
}

