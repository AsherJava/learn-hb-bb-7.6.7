/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.log;

import com.jiuqi.nr.entity.log.EntityLogger;

@FunctionalInterface
public interface LoggerAction {
    public void accept(EntityLogger var1);
}

