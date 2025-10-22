/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.monitor;

import java.util.Map;

public interface IStatusModifier {
    public void ready(String var1);

    public void start(String var1);

    public void pause(String var1);

    public void resume(String var1);

    public void end(String var1, boolean var2);

    public void cancel(String var1);

    public void error(String var1, String var2);

    public void modify(String var1, Map<String, Object> var2);
}

