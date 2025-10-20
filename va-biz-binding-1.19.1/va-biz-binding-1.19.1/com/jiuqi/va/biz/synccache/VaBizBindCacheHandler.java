/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.synccache;

import java.util.Map;

public interface VaBizBindCacheHandler {
    public boolean enableWhileIsCurrNode();

    public int getOrder();

    public void handle(Map<String, Object> var1, String var2);
}

