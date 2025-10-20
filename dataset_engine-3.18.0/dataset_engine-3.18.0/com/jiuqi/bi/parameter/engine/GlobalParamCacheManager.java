/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.parameter.engine;

import com.jiuqi.bi.parameter.engine.IGlobalCacheParamNameProvider;
import com.jiuqi.bi.parameter.engine.IParameterEnv;
import com.jiuqi.bi.util.StringUtils;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GlobalParamCacheManager {
    private Map<String, IParameterEnv> globalParamEnvs = new ConcurrentHashMap<String, IParameterEnv>();
    private IGlobalCacheParamNameProvider provider;

    public IParameterEnv getGlobalParameterEnv(String userGuid) {
        return StringUtils.isEmpty((String)userGuid) ? null : this.globalParamEnvs.get(userGuid);
    }

    public boolean setGlobalParameterEnv(String userGuid, IParameterEnv env) {
        this.globalParamEnvs.put(userGuid, env);
        return true;
    }

    public boolean removeGlobalParameterEnv(String userGuid) {
        if (!StringUtils.isEmpty((String)userGuid) && this.globalParamEnvs.containsKey(userGuid)) {
            this.globalParamEnvs.remove(userGuid);
            return true;
        }
        return false;
    }

    public void setGlobalCacheParamNameProvider(IGlobalCacheParamNameProvider provider) {
        this.provider = provider;
    }

    public Set<String> getGlobalCacheParamNames() {
        Set<String> set = new HashSet<String>();
        if (this.provider != null && this.provider.getCacheParamNames() != null) {
            set = this.provider.getCacheParamNames();
        }
        return set;
    }

    public static final GlobalParamCacheManager getInstance() {
        return GlobalParamCacheManagerImpl.INS;
    }

    private GlobalParamCacheManager() {
    }

    private static class GlobalParamCacheManagerImpl {
        private static GlobalParamCacheManager INS = new GlobalParamCacheManager();

        private GlobalParamCacheManagerImpl() {
        }
    }
}

