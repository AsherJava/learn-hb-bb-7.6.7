/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.core.DeployResult;

@FunctionalInterface
public interface IDeployCallback {
    public void run(DeployResult var1);
}

