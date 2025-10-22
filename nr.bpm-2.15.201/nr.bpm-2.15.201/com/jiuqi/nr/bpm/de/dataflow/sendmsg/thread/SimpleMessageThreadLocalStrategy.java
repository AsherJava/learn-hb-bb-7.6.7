/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread;

import com.jiuqi.nr.bpm.de.dataflow.sendmsg.MessageEventListener;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.SimpleMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread.SimpleMessageManager;

public class SimpleMessageThreadLocalStrategy {
    private static final ThreadLocal<SimpleMessageManager> threadLocal = new ThreadLocal<SimpleMessageManager>(){

        @Override
        protected SimpleMessageManager initialValue() {
            return new SimpleMessageManager();
        }
    };

    private static SimpleMessageManager getManager() {
        SimpleMessageManager simpleMessageManager = threadLocal.get();
        if (simpleMessageManager == null) {
            simpleMessageManager = new SimpleMessageManager();
        }
        return simpleMessageManager;
    }

    public static void setUpperLimitValue(int num) {
        SimpleMessageManager manager = SimpleMessageThreadLocalStrategy.getManager();
        manager.setUpperLimitValue(num);
    }

    public static void setMessageEventListener(MessageEventListener messageEventListener) {
        SimpleMessageManager manager = SimpleMessageThreadLocalStrategy.getManager();
        manager.setMessageEventListener(messageEventListener);
    }

    public static void setSumTotal(int count) {
        SimpleMessageManager manager = SimpleMessageThreadLocalStrategy.getManager();
        manager.setSumTotal(count);
    }

    public static void addMessageBody(SimpleMessageEvent messageBody) {
        SimpleMessageManager simpleMessageManager = threadLocal.get();
        simpleMessageManager.addMessageObj(messageBody);
    }

    public SimpleMessageManager getMessageBody() {
        return threadLocal.get();
    }

    public static void clear() {
        SimpleMessageManager simpleMessageManager = threadLocal.get();
        simpleMessageManager.clear();
        threadLocal.remove();
    }
}

