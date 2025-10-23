/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event;

public interface IActionEventDefinition {
    public String getId();

    public String getTitle();

    public String getDescription();

    public ExecutionTiming getExecutionTiming();

    public Level getLevel();

    public static enum Level {
        SYSTEM,
        USER;

    }

    public static enum ExecutionTiming {
        PRE_ACTION,
        POST_ACTION;

    }
}

