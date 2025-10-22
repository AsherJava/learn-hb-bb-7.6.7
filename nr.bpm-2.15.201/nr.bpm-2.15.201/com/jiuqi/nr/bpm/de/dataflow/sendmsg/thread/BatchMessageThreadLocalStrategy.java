/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread;

import com.jiuqi.nr.bpm.de.dataflow.sendmsg.MessageEventListener;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.BatchMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread.BatchMessageManager;

public class BatchMessageThreadLocalStrategy {
    private static final ThreadLocal<BatchMessageManager> threadLocal = new ThreadLocal<BatchMessageManager>(){

        @Override
        protected BatchMessageManager initialValue() {
            return new BatchMessageManager();
        }
    };

    private static BatchMessageManager getManager() {
        BatchMessageManager batchMessageManager = threadLocal.get();
        if (batchMessageManager == null) {
            batchMessageManager = new BatchMessageManager();
        }
        return batchMessageManager;
    }

    public static void setMessageEventListener(MessageEventListener messageEventListener) {
        BatchMessageManager manager = BatchMessageThreadLocalStrategy.getManager();
        manager.setMessageEventListener(messageEventListener);
    }

    public static void setUpperLimitValue(int num) {
        BatchMessageManager manager = BatchMessageThreadLocalStrategy.getManager();
        manager.setUpperLimitValue(num);
    }

    public static void setSumTotal(int count) {
        BatchMessageManager manager = BatchMessageThreadLocalStrategy.getManager();
        manager.setSumTotal(count);
    }

    public static void addMessageBody(BatchMessageEvent messageBody) {
        BatchMessageManager batchMessageManager = threadLocal.get();
        batchMessageManager.addMessageObj(messageBody);
    }

    public static void clear() {
        BatchMessageManager batchMessageManager = threadLocal.get();
        batchMessageManager.clear();
        threadLocal.remove();
    }
}

