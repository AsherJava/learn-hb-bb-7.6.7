/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.service;

import com.jiuqi.nr.transmission.data.monitor.TransmissionMonitorInfo;

public interface IProcessService {
    public TransmissionMonitorInfo queryProcess(String var1, int var2);

    public String getInstance(String var1);
}

