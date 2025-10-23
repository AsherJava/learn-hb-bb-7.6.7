/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event;

import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutor;

public interface IActionEventExecutorFactory {
    public String getActionEventDefinitionId();

    public IActionEventExecutor createActionEventExecutor(String var1);
}

