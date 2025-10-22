/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.service;

import com.jiuqi.nr.configuration.event.bean.EventBO;
import org.springframework.lang.NonNull;

@Deprecated
public interface ISystemOptionListenerService {
    public void publish(@NonNull EventBO var1);

    public void publish(@NonNull String var1, Object var2);

    public void publish(@NonNull String var1, Object var2, Object var3);

    public void publish(@NonNull String var1, String var2, String var3, Object var4, Object var5);
}

