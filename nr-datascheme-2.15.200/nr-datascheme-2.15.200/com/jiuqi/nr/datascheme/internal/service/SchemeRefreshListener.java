/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.event.RefreshCache
 */
package com.jiuqi.nr.datascheme.internal.service;

import com.jiuqi.nr.datascheme.api.event.RefreshCache;

public interface SchemeRefreshListener {
    public void onClearCache();

    public void onClearCache(RefreshCache var1);
}

