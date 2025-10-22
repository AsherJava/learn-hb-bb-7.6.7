/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.NedisCacheProvider
 */
package com.jiuqi.nr.designer.service.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.NedisCacheProvider;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeployManager
implements Serializable {
    private static final long serialVersionUID = -6022190595861358411L;
    private static final String CACHENAME = "deployManager";
    private NedisCacheManager cacheManager;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager("REMOTE_NP_DEFINITION");
    }

    public void addDeployTask(String taskID, String objID) {
        NedisCache cache = this.cacheManager.getCache(CACHENAME);
        DeployTaskImpl deployTaskImpl = (DeployTaskImpl)cache.get(taskID, DeployTaskImpl.class);
        if (deployTaskImpl != null) {
            return;
        }
        deployTaskImpl = new DeployTaskImpl();
        deployTaskImpl.setId(taskID);
        deployTaskImpl.setTargetId(objID);
        cache.put(taskID, (Object)deployTaskImpl);
    }

    public String getDelployObjID(String taskID) {
        NedisCache cache = this.cacheManager.getCache(CACHENAME);
        DeployTaskImpl deployTaskImpl = (DeployTaskImpl)cache.get(taskID, DeployTaskImpl.class);
        return deployTaskImpl == null ? null : deployTaskImpl.getTargetId();
    }

    public static class DeployTaskImpl
    implements Serializable {
        private static final long serialVersionUID = 6284367064064914766L;
        private String id;
        private String targetId;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTargetId() {
            return this.targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }
    }
}

