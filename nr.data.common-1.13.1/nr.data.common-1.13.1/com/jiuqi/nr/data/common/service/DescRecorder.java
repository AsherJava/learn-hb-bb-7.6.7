/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.service;

import java.io.Flushable;
import java.util.List;

public interface DescRecorder
extends Flushable {
    public void addDesc(List<String> var1, String var2);

    public void addDesc(String var1, String var2);

    public void addDesc(String var1, List<String> var2);
}

