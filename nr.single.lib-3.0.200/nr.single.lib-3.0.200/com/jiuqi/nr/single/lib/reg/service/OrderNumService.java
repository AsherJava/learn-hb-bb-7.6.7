/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.lib.reg.service;

import java.util.List;

public interface OrderNumService {
    public String getNewAuthorizationCode(String var1);

    public String getNewAuthorizationCode(String var1, long var2);

    public List<String> getNewAuthorizationCodes(String var1, long var2, int var4);
}

