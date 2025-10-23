/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.summary.internal.dto;

import com.jiuqi.nr.summary.api.Ordered;
import com.jiuqi.nr.summary.api.SummarySolution;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;

public class SummarySolutionDTO
implements SummarySolution {
    private String key;
    private String name;
    private String title;
    private String group;
    private String mainTask;
    private String taskConfigData;
    private String targetDimension;
    private Instant modifyTime;
    private String order;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDesc() {
        return null;
    }

    @Override
    public Instant getModifyTime() {
        return this.modifyTime;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public String getMainTask() {
        return this.mainTask;
    }

    @Override
    public String getTaskConfigData() {
        return this.taskConfigData;
    }

    @Override
    public String getTargetDimension() {
        return this.targetDimension;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDesc(String desc) {
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setMainTask(String mainTask) {
        this.mainTask = mainTask;
    }

    public void setTaskConfigData(String taskConfigData) {
        this.taskConfigData = taskConfigData;
    }

    public void setTargetDimension(String targetDimension) {
        this.targetDimension = targetDimension;
    }

    @Override
    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public int compareTo(@NotNull Ordered o) {
        return 0;
    }
}

