/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.common.observer;

import java.util.UUID;

public interface Observer {
    public boolean isAsyn();

    public void excute(UUID var1) throws Exception;
}

