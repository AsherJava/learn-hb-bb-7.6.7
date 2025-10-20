/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.realtime.core;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.bean.RealTimeJobBean;
import com.jiuqi.bi.util.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealTimeJobFactory {
    private static Logger logger = LoggerFactory.getLogger(RealTimeJobFactory.class);
    private Map<String, Class<AbstractRealTimeJob>> realTimeJobGroupMap = new HashMap<String, Class<AbstractRealTimeJob>>();
    private Map<String, String> realTimeJobGroupTitleMap = new HashMap<String, String>();
    private Map<String, Boolean> realTimeJobHasProgressMap = new HashMap<String, Boolean>();
    private Map<String, String[]> realTimeJobTagMap = new HashMap<String, String[]>();
    private static RealTimeJobFactory instance = new RealTimeJobFactory();

    public static RealTimeJobFactory getInstance() {
        return instance;
    }

    private RealTimeJobFactory() {
    }

    @Deprecated
    public void register(String group, String groupTitle, Class clazz) throws JobsException {
        if (StringUtils.isEmpty((String)group)) {
            throw new JobsException("[" + clazz + "]\u7684group\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)group)) {
            throw new JobsException("[" + clazz + "]\u7684groupTitle\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (this.realTimeJobGroupMap.containsKey(group)) {
            logger.warn("[{}]\u7684group[{}]\u91cd\u590d\uff0c\u5df2\u5728{}\u5b58\u5728\u3002\u5ffd\u7565\u8be5\u4efb\u52a1", clazz, group, this.realTimeJobGroupMap.get(group));
            return;
        }
        Class clazz1 = clazz;
        this.realTimeJobGroupMap.put(group, clazz1);
        this.realTimeJobGroupTitleMap.put(group, groupTitle);
        try {
            RealTimeJob realTimeJob = clazz1.getAnnotation(RealTimeJob.class);
            this.realTimeJobHasProgressMap.put(group, realTimeJob.hasProgress());
            this.realTimeJobTagMap.put(group, realTimeJob.tags());
            logger.info("\u5373\u65f6\u4efb\u52a1[{}]\u6ce8\u518c\u5b8c\u6210[{}]", (Object)groupTitle, (Object)clazz);
        }
        catch (Exception e) {
            logger.error("\u6ce8\u518c\u5373\u65f6\u4efb\u52a1clazz\u5931\u8d25\uff0c" + clazz, e);
        }
    }

    public void register(Class<AbstractRealTimeJob> clazz) throws JobsException {
        try {
            RealTimeJob realTimeJob = clazz.getAnnotation(RealTimeJob.class);
            String group = realTimeJob.group();
            if (StringUtils.isEmpty((String)group)) {
                throw new JobsException("[" + clazz + "]\u7684group\u4e0d\u80fd\u4e3a\u7a7a");
            }
            if (this.realTimeJobGroupMap.containsKey(group)) {
                logger.warn("[{}]\u7684group[{}]\u91cd\u590d\uff0c\u5df2\u5728{}\u5b58\u5728\u3002\u5ffd\u7565\u8be5\u4efb\u52a1", clazz, group, this.realTimeJobGroupMap.get(group));
                return;
            }
            String groupTitle = realTimeJob.groupTitle();
            if (StringUtils.isEmpty((String)group)) {
                throw new JobsException("[" + clazz + "]\u7684groupTitle\u4e0d\u80fd\u4e3a\u7a7a");
            }
            this.realTimeJobGroupMap.put(group, clazz);
            this.realTimeJobGroupTitleMap.put(group, groupTitle);
            this.realTimeJobHasProgressMap.put(group, realTimeJob.hasProgress());
            this.realTimeJobTagMap.put(group, realTimeJob.tags());
            logger.info("\u5373\u65f6\u4efb\u52a1[{}]\u6ce8\u518c\u5b8c\u6210[{}]", (Object)groupTitle, (Object)clazz);
        }
        catch (Exception e) {
            logger.error("\u6ce8\u518c\u5373\u65f6\u4efb\u52a1clazz\u5931\u8d25\uff0c" + clazz, e);
        }
    }

    public List<RealTimeJobBean> getRealTimeJobList() {
        ArrayList<RealTimeJobBean> list = new ArrayList<RealTimeJobBean>();
        this.realTimeJobGroupMap.values().forEach(realTimeJobClass -> {
            RealTimeJob realTimeJob = realTimeJobClass.getAnnotation(RealTimeJob.class);
            RealTimeJobBean jobBean = new RealTimeJobBean();
            jobBean.setGroup(realTimeJob.group());
            jobBean.setGroupTitle(realTimeJob.groupTitle());
            jobBean.setCancellable(realTimeJob.cancellable());
            jobBean.setRollback(realTimeJob.rollback());
            list.add(jobBean);
        });
        return list;
    }

    public Class<AbstractRealTimeJob> getRealTimeJobClazzByGroup(String group) {
        Class<AbstractRealTimeJob> clazz = this.realTimeJobGroupMap.get(group);
        if (clazz == null) {
            logger.warn("RealTimeJob-group \u4e0d\u5b58\u5728");
        }
        return clazz;
    }

    public String getRealTimeJobGroupTitle(String group) {
        String title = this.realTimeJobGroupTitleMap.get(group);
        if (title == null) {
            logger.warn("RealTimeJob-group \u4e0d\u5b58\u5728");
        }
        return title;
    }

    public boolean getRealTimeJobHasProgress(String group) {
        Boolean hasProgress = this.realTimeJobHasProgressMap.get(group);
        if (hasProgress == null) {
            logger.warn("RealTimeJob-group\u4e0d\u5b58\u5728");
        }
        return hasProgress;
    }

    public String[] getRealTimeJobTags(String group) {
        return this.realTimeJobTagMap.get(group);
    }

    public Iterator<Map.Entry<String, String>> getGroupIterator() {
        return this.realTimeJobGroupTitleMap.entrySet().iterator();
    }

    public List<String> getGroupIds() {
        ArrayList<String> ids = new ArrayList<String>();
        for (Map.Entry<String, String> entry : this.realTimeJobGroupTitleMap.entrySet()) {
            ids.add(entry.getKey());
        }
        return ids;
    }

    public static String getRealTimeJobCategoryId(String group) {
        String id = "com.jiuqi.bi.jobs.realtime";
        if (StringUtils.isNotEmpty((String)group)) {
            id = id + "." + group;
        }
        return id;
    }

    public static String getRealTimeJobGroupByCategoryId(String categoryId) {
        if (StringUtils.isEmpty((String)categoryId)) {
            return "";
        }
        String group = categoryId;
        int i = categoryId.indexOf("com.jiuqi.bi.jobs.realtime.");
        if (i > -1) {
            group = categoryId.substring(i + "com.jiuqi.bi.jobs.realtime".length() + 1);
        }
        return group;
    }

    public String getRealTimeJobGroup(AbstractRealTimeJob job) {
        try {
            RealTimeJob realTimeJob = job.getClass().getAnnotation(RealTimeJob.class);
            return realTimeJob.group();
        }
        catch (Exception e) {
            return null;
        }
    }

    public boolean isIsolateJobGroup(AbstractRealTimeJob job) {
        try {
            RealTimeJob realTimeJob = job.getClass().getAnnotation(RealTimeJob.class);
            return realTimeJob.isolate();
        }
        catch (Exception e) {
            return false;
        }
    }
}

