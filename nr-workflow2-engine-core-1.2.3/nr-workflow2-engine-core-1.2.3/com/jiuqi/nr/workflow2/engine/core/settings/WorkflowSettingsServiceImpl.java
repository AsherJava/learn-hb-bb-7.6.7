/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.engine.core.settings;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDao;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInDaysConfigImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInEndTimeConfigImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.FillInStartTimeConfigImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.ManualTerminationConfigImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettings;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowOtherSettingsImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.entity.WorkflowSelfControlImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.StartTimeStrategy;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.TimeControlType;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import java.time.LocalTime;
import org.json.JSONObject;

public class WorkflowSettingsServiceImpl
implements WorkflowSettingsService {
    public static final String CACHE_NAME = "WORKFLOW_SETTINGS_CACHE";
    private final WorkflowSettingsDao workflowSettingsDao;
    private final NedisCache cache;

    public WorkflowSettingsServiceImpl(WorkflowSettingsDao workflowSettingsDao, NedisCacheProvider cacheProvider) {
        this.workflowSettingsDao = workflowSettingsDao;
        this.cache = cacheProvider.getCacheManager().getCache(CACHE_NAME);
    }

    @Override
    public WorkflowSettingsDO queryWorkflowSettings(String taskId) {
        return this.getWorkflowSettingsDOWithCache(taskId);
    }

    @Override
    public boolean queryTaskWorkflowEnable(String taskId) {
        WorkflowSettingsDO workflowSettingsDO = this.getWorkflowSettingsDOWithCache(taskId);
        if (workflowSettingsDO == null) {
            return false;
        }
        return workflowSettingsDO.isWorkflowEnable();
    }

    @Override
    public boolean queryTaskTodoEnable(String taskId) {
        WorkflowSettingsDO workflowSettingsDO = this.getWorkflowSettingsDOWithCache(taskId);
        if (workflowSettingsDO == null) {
            return false;
        }
        return workflowSettingsDO.isTodoEnable();
    }

    @Override
    public String queryTaskWorkflowEngine(String taskId) {
        WorkflowSettingsDO workflowSettingsDO = this.getWorkflowSettingsDOWithCache(taskId);
        if (workflowSettingsDO == null) {
            return null;
        }
        return workflowSettingsDO.getWorkflowEngine();
    }

    @Override
    public String queryTaskWorkflowDefine(String taskId) {
        WorkflowSettingsDO workflowSettingsDO = this.getWorkflowSettingsDOWithCache(taskId);
        if (workflowSettingsDO == null) {
            return null;
        }
        return workflowSettingsDO.getWorkflowDefine();
    }

    @Override
    public WorkflowObjectType queryTaskWorkflowObjectType(String taskId) {
        WorkflowSettingsDO workflowSettingsDO = this.getWorkflowSettingsDOWithCache(taskId);
        if (workflowSettingsDO == null) {
            return null;
        }
        return workflowSettingsDO.getWorkflowObjectType();
    }

    @Override
    public WorkflowOtherSettings queryTaskWorkflowOtherSettings(String taskId) {
        WorkflowSettingsDO workflowSettingsDO = this.getWorkflowSettingsDOWithCache(taskId);
        JSONObject otherConfig = new JSONObject(workflowSettingsDO.getOtherConfig());
        return WorkflowSettingsServiceImpl.parseJSONObjectToWorkflowOtherSettings(otherConfig);
    }

    private WorkflowSettingsDO getWorkflowSettingsDOWithCache(String taskId) {
        WorkflowSettingsDO workflowSettingsDO = (WorkflowSettingsDO)this.cache.get(taskId, WorkflowSettingsDO.class);
        if (workflowSettingsDO == null) {
            workflowSettingsDO = this.workflowSettingsDao.queryWorkflowSettings(taskId);
            this.cache.put(taskId, (Object)workflowSettingsDO);
        }
        return workflowSettingsDO;
    }

    public static WorkflowOtherSettings parseJSONObjectToWorkflowOtherSettings(JSONObject otherConfig) {
        FillInDaysConfigImpl fillInDaysConfig;
        JSONObject customValue;
        JSONObject timeControl = otherConfig.getJSONObject("timeControl");
        WorkflowOtherSettingsImpl workflowOtherSettings = new WorkflowOtherSettingsImpl();
        FillInStartTimeConfigImpl fillInStartTimeConfig = new FillInStartTimeConfigImpl();
        FillInEndTimeConfigImpl fillInEndTimeConfig = new FillInEndTimeConfigImpl();
        ManualTerminationConfigImpl manualTerminationConfig = new ManualTerminationConfigImpl();
        WorkflowSelfControlImpl workflowSelfControlConfig = new WorkflowSelfControlImpl();
        boolean startTimeConfigEnable = !timeControl.isNull("startTimeConfig");
        boolean endTimeConfigEnable = !timeControl.isNull("endTimeConfig");
        boolean manualTerminationEnable = !timeControl.isNull("manualTermination");
        boolean workflowSelfControlEnable = !timeControl.isNull("workflowSelfControl");
        fillInStartTimeConfig.setEnable(startTimeConfigEnable);
        fillInEndTimeConfig.setEnable(endTimeConfigEnable);
        manualTerminationConfig.setEnable(manualTerminationEnable);
        workflowSelfControlConfig.setEnable(workflowSelfControlEnable);
        if (startTimeConfigEnable) {
            JSONObject startTimeConfig = timeControl.getJSONObject("startTimeConfig");
            fillInStartTimeConfig.setType(StartTimeStrategy.valueOf(startTimeConfig.getString("type")));
            customValue = startTimeConfig.getJSONObject("customValue");
            fillInDaysConfig = new FillInDaysConfigImpl();
            fillInDaysConfig.setType(TimeControlType.valueOf(customValue.getString("type")));
            fillInDaysConfig.setDayNum(customValue.getInt("dayNum"));
            fillInStartTimeConfig.setFillInDaysConfig(fillInDaysConfig);
        }
        if (endTimeConfigEnable) {
            JSONObject endTimeConfig = timeControl.getJSONObject("endTimeConfig");
            FillInDaysConfigImpl fillInDaysConfig2 = new FillInDaysConfigImpl();
            fillInDaysConfig2.setType(TimeControlType.valueOf(endTimeConfig.getString("type")));
            fillInDaysConfig2.setDayNum(endTimeConfig.getInt("dayNum"));
            fillInEndTimeConfig.setFillInDaysConfig(fillInDaysConfig2);
            fillInEndTimeConfig.setHierarchicalControl(endTimeConfig.getBoolean("hierarchicalControl"));
        }
        if (manualTerminationEnable) {
            JSONObject manualTermination = timeControl.getJSONObject("manualTermination");
            manualTerminationConfig.setRole(manualTermination.getString("role"));
        }
        if (workflowSelfControlEnable) {
            JSONObject workflowSelfControl = timeControl.getJSONObject("workflowSelfControl");
            workflowSelfControlConfig.setType(StartTimeStrategy.valueOf(workflowSelfControl.getString("type")));
            customValue = workflowSelfControl.getJSONObject("customValue");
            fillInDaysConfig = new FillInDaysConfigImpl();
            fillInDaysConfig.setType(TimeControlType.valueOf(customValue.getString("type")));
            fillInDaysConfig.setDayNum(customValue.getInt("dayNum"));
            workflowSelfControlConfig.setFillInDaysConfig(fillInDaysConfig);
            workflowSelfControlConfig.setBootTime(LocalTime.parse(workflowSelfControl.getString("bootTime")));
        }
        workflowOtherSettings.setFillInStartTimeConfig(fillInStartTimeConfig);
        workflowOtherSettings.setFillInEndTimeConfig(fillInEndTimeConfig);
        workflowOtherSettings.setManualTerminationConfig(manualTerminationConfig);
        workflowOtherSettings.setWorkflowSelfControl(workflowSelfControlConfig);
        return workflowOtherSettings;
    }
}

