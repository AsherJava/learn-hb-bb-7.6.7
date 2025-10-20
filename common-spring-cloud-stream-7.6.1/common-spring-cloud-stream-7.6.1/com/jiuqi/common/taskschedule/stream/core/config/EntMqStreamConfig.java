/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.taskschedule.stream.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EntMqStreamConfig {
    @Value(value="${spring.rabbitmq.listener.simple.concurrency:5}")
    private int concurrency;

    public int getConcurrency() {
        return this.concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }
}

