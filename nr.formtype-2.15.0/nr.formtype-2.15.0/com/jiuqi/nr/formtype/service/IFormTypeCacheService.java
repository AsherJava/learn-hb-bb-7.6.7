/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formtype.service;

import java.util.Set;

public interface IFormTypeCacheService {
    public Set<String> getAllFormTypeCodes();

    public boolean isFormType(String var1);

    public void cleanCache();
}

