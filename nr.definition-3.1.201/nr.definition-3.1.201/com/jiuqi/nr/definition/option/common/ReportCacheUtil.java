/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 */
package com.jiuqi.nr.definition.option.common;

import com.jiuqi.nr.definition.option.common.ReportCacheCycleType;
import com.jiuqi.nr.definition.option.common.ReportCacheOptionType;
import com.jiuqi.nr.definition.option.dto.ReportCacheConfig;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class ReportCacheUtil {
    public static List<ReportCacheConfig> configRevert(String configStr) {
        ArrayList<ReportCacheConfig> optionConfigs = new ArrayList<ReportCacheConfig>();
        JSONArray jsonArray = new JSONArray(configStr);
        for (int i = 0; i < jsonArray.length(); ++i) {
            ReportCacheConfig cacheConfig = new ReportCacheConfig();
            cacheConfig.setTask(jsonArray.getJSONObject(i).getString("task"));
            cacheConfig.setTaskTitle(jsonArray.getJSONObject(i).getString("taskTitle"));
            cacheConfig.setTaskCode(jsonArray.getJSONObject(i).getString("taskCode"));
            cacheConfig.setCycleBeginType(ReportCacheCycleType.valueOf(jsonArray.getJSONObject(i).getInt("cycleBeginType")));
            cacheConfig.setCycleBeginDays(jsonArray.getJSONObject(i).getInt("cycleBeginDays"));
            cacheConfig.setCycleEndType(ReportCacheCycleType.valueOf(jsonArray.getJSONObject(i).getInt("cycleEndType")));
            cacheConfig.setCycleEndDays(jsonArray.getJSONObject(i).getInt("cycleEndDays"));
            cacheConfig.setCurrentOptionType(ReportCacheOptionType.valueOf(jsonArray.getJSONObject(i).getInt("currentOptionType")));
            optionConfigs.add(cacheConfig);
        }
        return optionConfigs;
    }
}

