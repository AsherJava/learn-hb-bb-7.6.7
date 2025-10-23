/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event;

import com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventExecutorFactory;

public interface IActionEventFactory {
    public IActionEventDefinition queryActionEventDefinition(String var1);

    public short getActionEventOrder(String var1);

    public IActionEventExecutorFactory queryActionEventExecutorFactory(String var1);
}

