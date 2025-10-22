/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.listener;

import com.jiuqi.nr.integritycheck.listener.param.CheckEvent;

public interface IntegrityCheckListener {
    public void beforeCheck(CheckEvent var1);

    public void afterCheck(CheckEvent var1);
}

