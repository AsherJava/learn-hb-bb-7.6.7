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
public class FormSaveWithFormulaManager
implements Serializable {
    private static final long serialVersionUID = -5127951341725944120L;
    private static final String CACHENAME = "formSaveWithFormulaManager";
    private NedisCacheManager cacheManager;

    @Autowired
    private void setCacheManager(NedisCacheProvider cacheProvider) {
        this.cacheManager = cacheProvider.getCacheManager("REMOTE_NP_DEFINITION");
    }

    public void addStepTask(String taskID, String objID) {
        NedisCache cache = this.cacheManager.getCache(CACHENAME);
        StepTaskImpl stepTask = (StepTaskImpl)cache.get(taskID, StepTaskImpl.class);
        if (stepTask != null) {
            return;
        }
        stepTask = new StepTaskImpl();
        stepTask.setId(taskID);
        stepTask.setTargetId(objID);
        cache.put(taskID, (Object)stepTask);
    }

    public String getStepObjID(String taskID) {
        NedisCache cache = this.cacheManager.getCache(CACHENAME);
        StepTaskImpl stepTask = (StepTaskImpl)cache.get(taskID, StepTaskImpl.class);
        return stepTask == null ? null : stepTask.getTargetId();
    }

    public static class StepTaskImpl
    implements Serializable {
        private static final long serialVersionUID = 8053519691918085129L;
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

