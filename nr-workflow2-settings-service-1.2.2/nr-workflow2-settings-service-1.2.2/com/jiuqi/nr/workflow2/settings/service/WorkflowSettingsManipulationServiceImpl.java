/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDao
 */
package com.jiuqi.nr.workflow2.settings.service;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDTO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDao;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationService;

public class WorkflowSettingsManipulationServiceImpl
implements WorkflowSettingsManipulationService {
    private final WorkflowSettingsDao workflowSettingsDao;
    private final NedisCache cache;

    public WorkflowSettingsManipulationServiceImpl(WorkflowSettingsDao workflowSettingsDao, NedisCacheProvider cacheProvider) {
        this.workflowSettingsDao = workflowSettingsDao;
        this.cache = cacheProvider.getCacheManager().getCache("WORKFLOW_SETTINGS_CACHE");
    }

    @Override
    public boolean addWorkflowSettings(WorkflowSettingsDTO workflowSettingsDTO) {
        WorkflowSettingsDO workflowSettingsDO = this.workflowSettingsDao.queryWorkflowSettings(workflowSettingsDTO.getTaskId());
        if (workflowSettingsDO != null) {
            return false;
        }
        String taskId = workflowSettingsDTO.getTaskId();
        if (taskId != null && !taskId.isEmpty()) {
            this.cache.evict(taskId);
        }
        return this.workflowSettingsDao.addWorkflowSettings(workflowSettingsDTO);
    }

    @Override
    public boolean deleteWorkflowSettings(String taskId) {
        if (taskId != null && !taskId.isEmpty()) {
            this.cache.evict(taskId);
        }
        return this.workflowSettingsDao.deleteWorkflowSettings(taskId);
    }

    @Override
    public boolean updateWorkflowSettings(WorkflowSettingsDTO workflowSettingsDTO) {
        WorkflowSettingsDO workflowSettingsDO = this.workflowSettingsDao.queryWorkflowSettings(workflowSettingsDTO.getTaskId());
        if (workflowSettingsDO == null) {
            return false;
        }
        String taskId = workflowSettingsDTO.getTaskId();
        if (taskId != null && !taskId.isEmpty()) {
            this.cache.evict(taskId);
        }
        return this.workflowSettingsDao.updateWorkflowSettings(workflowSettingsDTO);
    }
}

