/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONObject
 *  org.quartz.JobDataMap
 *  org.quartz.JobKey
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.Trigger
 *  org.quartz.TriggerBuilder
 *  org.quartz.spi.OperableTrigger
 */
package com.jiuqi.bi.core.jobs.combination;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.bean.JobInstanceBean;
import com.jiuqi.bi.core.jobs.combination.CombinationExtendStageConfig;
import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.bi.core.jobs.model.JobModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.spi.OperableTrigger;

public class CombinationJobTools {
    private static final Random random = new Random();

    private CombinationJobTools() {
    }

    public static boolean isCombinationType(String categoryIdStr) {
        return categoryIdStr.startsWith("combination_job");
    }

    public static List<CombinationExtendStageConfig> getStageConfigs(String config) {
        ArrayList<CombinationExtendStageConfig> configs = new ArrayList<CombinationExtendStageConfig>();
        JSONObject object = new JSONObject(config);
        JSONArray array = object.optJSONArray("stages");
        if (array == null) {
            return configs;
        }
        for (int i = 0; i < array.length(); ++i) {
            configs.add(new CombinationExtendStageConfig().fromJson(array.getJSONObject(i)));
        }
        return configs;
    }

    public static void buildStageTrigger(JobModel job, JobInstanceBean instanceBean, JobDataMap jobDataMap) throws JobsException {
        Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
        JobKey jobKey = new JobKey(job.getGuid(), job.getCategory());
        JobDataMap map = new JobDataMap();
        if (jobDataMap != null) {
            map.putAll((Map)jobDataMap);
        }
        map.put("__sys_instanceid", instanceBean.getInstanceId());
        map.put("__sys_rootinstanceid", instanceBean.getInstanceId());
        map.put("__sys_jobid", job.getGuid());
        map.put("__sys_jobtitle", job.getTitle());
        String triggerId = CombinationJobTools.newTriggerId() + "_" + instanceBean.getInstanceId();
        OperableTrigger trig = (OperableTrigger)TriggerBuilder.newTrigger().withIdentity(triggerId, job.getCategory() + triggerId).forJob(jobKey).build();
        trig.computeFirstFireTime(null);
        trig.setJobDataMap(map);
        try {
            scheduler.scheduleJob((Trigger)trig);
        }
        catch (SchedulerException e) {
            throw new JobsException("\u7ec4\u5408\u4efb\u52a1\u9636\u6bb5\u6267\u884c\u89e6\u53d1\u5931\u8d25", e);
        }
    }

    private static String newTriggerId() {
        long r = random.nextLong();
        if (r < 0L) {
            r = -r;
        }
        return "MT_" + Long.toString(r, 30 + (int)(System.currentTimeMillis() % 7L));
    }
}

