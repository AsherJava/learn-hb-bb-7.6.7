/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolConst {
    public static final ExecutorService SEND_MESSAGE_THREADPOOL = Executors.newFixedThreadPool(5);
    public static final ExecutorService AUTOTASK_THREADPOOL = Executors.newFixedThreadPool(10);
}

