/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.IRWLockExecuter;

public interface IRWLockExecuterManager {
    public RWLockType getRWLockType();

    public IRWLockExecuter getRWLockExecuter(String var1);

    public static enum RWLockType {
        LOCAL,
        DATABASE;

    }
}

