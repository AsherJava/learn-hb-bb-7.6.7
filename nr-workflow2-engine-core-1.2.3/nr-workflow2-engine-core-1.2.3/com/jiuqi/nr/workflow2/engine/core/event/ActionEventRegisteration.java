/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event;

import com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory;

public class ActionEventRegisteration {
    private String id;
    private String title;
    private String description;
    private IActionEventDefinition.ExecutionTiming executionTiming = IActionEventDefinition.ExecutionTiming.PRE_ACTION;
    private IActionEventDefinition.Level level = IActionEventDefinition.Level.USER;
    private short order;
    private IActionEventExecutorFactory executorFactory;

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public short getOrder() {
        return this.order;
    }

    public IActionEventDefinition.ExecutionTiming getExecutionTiming() {
        return this.executionTiming;
    }

    public IActionEventDefinition.Level getLevel() {
        return this.level;
    }

    public IActionEventExecutorFactory getExecutorFactory() {
        return this.executorFactory;
    }

    public ActionEventRegisteration(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public ActionEventRegisteration description(String description) {
        this.description = description;
        return this;
    }

    public ActionEventRegisteration order(short order) {
        this.order = order;
        return this;
    }

    public ActionEventRegisteration executionTiming(IActionEventDefinition.ExecutionTiming executionTiming) {
        this.executionTiming = executionTiming;
        return this;
    }

    public ActionEventRegisteration executorFactory(IActionEventExecutorFactory executorFactory) {
        this.executorFactory = executorFactory;
        return this;
    }

    public ActionEventRegisteration level(IActionEventDefinition.Level level) {
        this.level = level;
        return this;
    }
}

