/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 */
package com.jiuqi.bi.core.jobs.extension;

import com.jiuqi.bi.core.jobs.bean.JobConfigBean;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import java.util.ArrayList;
import java.util.List;

public class JobDispatchControlManager {
    private List<String> allow = new ArrayList<String>();
    private List<String> except = new ArrayList<String>();

    public void load(String jobMatchType, String jobDispatchType) {
        List<JobConfigBean> jobDispatchConfigValue;
        ConfigManager instance = ConfigManager.getInstance();
        if ("BY_APPLICATION_NAME".equalsIgnoreCase(jobMatchType) && "BY_TAG".equalsIgnoreCase(jobDispatchType)) {
            jobDispatchConfigValue = instance.getJobConfigValueByApplicationName("CATE_WHITELIST_TAG");
            for (JobConfigBean jobConfigBean : jobDispatchConfigValue) {
                this.allow.add(jobConfigBean.getValue());
            }
            jobDispatchConfigValue = instance.getJobConfigValueByApplicationName("CATE_BLACKLIST_TAG");
            for (JobConfigBean jobConfigBean : jobDispatchConfigValue) {
                this.except.add(jobConfigBean.getValue());
            }
        }
        if ("BY_APPLICATION_NAME".equalsIgnoreCase(jobMatchType) && "BY_TYPE".equalsIgnoreCase(jobDispatchType)) {
            jobDispatchConfigValue = instance.getJobConfigValueByApplicationName("CATE_WHITELIST_TYPE");
            for (JobConfigBean jobConfigBean : jobDispatchConfigValue) {
                this.allow.add(jobConfigBean.getValue());
            }
            jobDispatchConfigValue = instance.getJobConfigValueByApplicationName("CATE_BLACKLIST_TYPE");
            for (JobConfigBean jobConfigBean : jobDispatchConfigValue) {
                this.except.add(jobConfigBean.getValue());
            }
        }
        if ("BY_MACHINE_NAME".equalsIgnoreCase(jobMatchType) && "BY_TAG".equalsIgnoreCase(jobDispatchType)) {
            jobDispatchConfigValue = instance.getJobConfigValueByMachineName("TAG_EXECUTABLE");
            for (JobConfigBean jobConfigBean : jobDispatchConfigValue) {
                this.allow.add(jobConfigBean.getValue());
            }
            jobDispatchConfigValue = instance.getJobConfigValueByMachineName("TAG_UNEXECUTABLE");
            for (JobConfigBean jobConfigBean : jobDispatchConfigValue) {
                this.except.add(jobConfigBean.getValue());
            }
        }
        if ("BY_MACHINE_NAME".equalsIgnoreCase(jobMatchType) && "BY_TYPE".equalsIgnoreCase(jobDispatchType)) {
            jobDispatchConfigValue = instance.getJobConfigValueByMachineName("CATE_EXECUTABLE");
            for (JobConfigBean jobConfigBean : jobDispatchConfigValue) {
                this.allow.add(jobConfigBean.getValue());
            }
            jobDispatchConfigValue = instance.getJobConfigValueByMachineName("CATE_UNEXECUTABLE");
            for (JobConfigBean jobConfigBean : jobDispatchConfigValue) {
                this.except.add(jobConfigBean.getValue());
            }
        }
    }

    public boolean isAllowed(List<String> tags) {
        if (this.allow.isEmpty()) {
            if (this.except.contains("ALL")) {
                return false;
            }
            for (String forbiddenTaskType : this.except) {
                for (String tag : tags) {
                    if (!forbiddenTaskType.equalsIgnoreCase(tag)) continue;
                    return false;
                }
            }
            return true;
        }
        if (this.except.isEmpty()) {
            if (this.allow.contains("ALL")) {
                return true;
            }
            for (String allowedTaskType : this.allow) {
                for (String tag : tags) {
                    if (!allowedTaskType.equalsIgnoreCase(tag)) continue;
                    return true;
                }
            }
            return false;
        }
        throw new RuntimeException("\u670d\u52a1\u3010" + DistributionManager.getInstance().self().getApplicationName() + "\u3011\u540c\u65f6\u914d\u7f6e\u4e86\u9ed1\u540d\u5355\u548c\u767d\u540d\u5355\uff0c\u6682\u4e0d\u652f\u6301\u8be5\u6a21\u5f0f");
    }

    public boolean isAllowed(String checkGroup) {
        if (this.allow.isEmpty()) {
            if (this.except.contains("ALL")) {
                return false;
            }
            for (String forbiddenTaskType : this.except) {
                if (!checkGroup.startsWith(forbiddenTaskType)) continue;
                return false;
            }
            return true;
        }
        if (this.except.isEmpty()) {
            if (this.allow.contains("ALL")) {
                return true;
            }
            for (String allowedTaskType : this.allow) {
                if (!checkGroup.startsWith(allowedTaskType)) continue;
                return true;
            }
            return false;
        }
        throw new RuntimeException("\u670d\u52a1\u3010" + DistributionManager.getInstance().self().getApplicationName() + "\u3011\u540c\u65f6\u914d\u7f6e\u4e86\u9ed1\u540d\u5355\u548c\u767d\u540d\u5355\uff0c\u6682\u4e0d\u652f\u6301\u8be5\u6a21\u5f0f");
    }
}

