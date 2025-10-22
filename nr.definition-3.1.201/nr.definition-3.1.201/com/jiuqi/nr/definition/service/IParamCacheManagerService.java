/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.service;

import com.jiuqi.nr.definition.exception.NrParamSyncException;
import java.util.Collection;

public interface IParamCacheManagerService {
    public void init();

    public void reloadSetting();

    public void refreshCache(Collection<String> var1, Collection<String> var2) throws NrParamSyncException;

    default public void refreshCache(Collection<String> formSchemes, Collection<String> formulaSchemes, RefreshCallback callback) throws Exception {
        NrParamSyncException exception = null;
        try {
            this.refreshCache(formSchemes, formulaSchemes);
        }
        catch (NrParamSyncException e) {
            exception = e;
        }
        callback.run();
        if (null != exception) {
            throw exception;
        }
    }

    @FunctionalInterface
    public static interface RefreshCallback {
        public void run() throws Exception;
    }
}

