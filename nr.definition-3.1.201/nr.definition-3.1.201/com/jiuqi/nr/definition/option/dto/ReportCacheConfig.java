/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option.dto;

import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.option.common.ReportCacheCycleType;
import com.jiuqi.nr.definition.option.common.ReportCacheOptionType;
import com.jiuqi.nr.definition.option.dto.ReportCacheOption;

public class ReportCacheConfig {
    private String task;
    private String taskTitle;
    private String taskCode;
    private ReportCacheCycleType cycleBeginType;
    private int cycleBeginDays;
    private ReportCacheCycleType cycleEndType;
    private int cycleEndDays;
    private ReportCacheOptionType currentOptionType;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public ReportCacheCycleType getCycleBeginType() {
        return this.cycleBeginType;
    }

    public void setCycleBeginType(ReportCacheCycleType cycleBeginType) {
        this.cycleBeginType = cycleBeginType;
    }

    public int getCycleBeginDays() {
        return this.cycleBeginDays;
    }

    public void setCycleBeginDays(int cycleBeginDays) {
        this.cycleBeginDays = cycleBeginDays;
    }

    public ReportCacheCycleType getCycleEndType() {
        return this.cycleEndType;
    }

    public void setCycleEndType(ReportCacheCycleType cycleEndType) {
        this.cycleEndType = cycleEndType;
    }

    public int getCycleEndDays() {
        return this.cycleEndDays;
    }

    public void setCycleEndDays(int cycleEndDays) {
        this.cycleEndDays = cycleEndDays;
    }

    public ReportCacheOptionType getCurrentOptionType() {
        return this.currentOptionType;
    }

    public void setCurrentOptionType(ReportCacheOptionType currentOptionType) {
        this.currentOptionType = currentOptionType;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public static ReportCacheConfig valueOf(ReportCacheOption option, TaskDefine taskDefine) {
        ReportCacheConfig cacheConfig = new ReportCacheConfig();
        cacheConfig.setTask(option.getTask());
        cacheConfig.setTaskTitle(taskDefine.getTitle());
        cacheConfig.setTaskCode(taskDefine.getTaskCode());
        if (option.getCycleBeginDays().contains("-")) {
            cacheConfig.setCycleBeginType(ReportCacheCycleType.SETTING_BEFORE_CYCLE);
        } else {
            cacheConfig.setCycleBeginType(ReportCacheCycleType.SETTING_AFTER_CYCLE);
        }
        cacheConfig.setCycleBeginDays(Math.abs(Integer.valueOf(option.getCycleBeginDays())));
        if (option.getCycleEndDays().contains("-")) {
            cacheConfig.setCycleEndType(ReportCacheCycleType.SETTING_BEFORE_CYCLE);
        } else {
            cacheConfig.setCycleEndType(ReportCacheCycleType.SETTING_AFTER_CYCLE);
        }
        cacheConfig.setCycleEndDays(Math.abs(Integer.valueOf(option.getCycleEndDays())));
        cacheConfig.setCurrentOptionType(ReportCacheOptionType.valueOf(option.getCurrentOptionType()));
        return cacheConfig;
    }

    public void toOptions(ReportCacheOption option) {
        option.setTask(this.getTask());
        option.setCurrentOptionType(this.getCurrentOptionType().getValue());
        String beginDays = String.valueOf(this.getCycleBeginDays());
        if (ReportCacheCycleType.SETTING_AFTER_CYCLE.equals((Object)this.cycleBeginType)) {
            option.setCycleBeginDays(beginDays);
        } else {
            option.setCycleBeginDays("-" + beginDays);
        }
        String endDays = String.valueOf(this.getCycleEndDays());
        if (ReportCacheCycleType.SETTING_AFTER_CYCLE.equals((Object)this.cycleEndType)) {
            option.setCycleEndDays(endDays);
        } else {
            option.setCycleEndDays("-" + endDays);
        }
    }
}

