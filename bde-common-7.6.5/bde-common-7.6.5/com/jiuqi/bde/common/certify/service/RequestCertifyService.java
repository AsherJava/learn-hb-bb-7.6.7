/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.certify.service;

public interface RequestCertifyService {
    public String getNvwaUrl();

    public <T> T getFeignClient(Class<T> var1, String var2);

    public <T> T getFeignClient(Class<T> var1);
}

