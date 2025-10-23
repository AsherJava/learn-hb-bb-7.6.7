/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.soulution;

import com.jiuqi.nr.summary.model.soulution.CommitState;
import com.jiuqi.nr.summary.model.soulution.DimensionData;
import com.jiuqi.nr.summary.model.soulution.SourceDimensionRange;
import com.jiuqi.nr.summary.model.soulution.TargetDimensionRange;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SummarySolutionModel
implements Serializable {
    private String key;
    private String name;
    private String title;
    private String group;
    private String mainTask;
    private List<String> relationTasks;
    private String targetDimension;
    private TargetDimensionRange targetDimensionRange;
    private List<String> targetDimensionValues;
    private String targetDimensionFilter;
    private SourceDimensionRange sourceDimensionRange;
    private List<String> sourceDimensionValues;
    private String sourceDimensionFilter;
    private CommitState commitState = CommitState.COMMIT;
    private List<DimensionData> dimDataRange;
    private String order;
    private Date modifyTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return this.group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getMainTask() {
        return this.mainTask;
    }

    public void setMainTask(String mainTask) {
        this.mainTask = mainTask;
    }

    public List<String> getRelationTasks() {
        return this.relationTasks;
    }

    public void setRelationTasks(List<String> relationTasks) {
        this.relationTasks = relationTasks;
    }

    public String getTargetDimension() {
        return this.targetDimension;
    }

    public void setTargetDimension(String targetDimension) {
        this.targetDimension = targetDimension;
    }

    public TargetDimensionRange getTargetDimensionRange() {
        return this.targetDimensionRange;
    }

    public void setTargetDimensionRange(TargetDimensionRange targetDimensionRange) {
        this.targetDimensionRange = targetDimensionRange;
    }

    public List<String> getTargetDimensionValues() {
        return this.targetDimensionValues;
    }

    public void setTargetDimensionValues(List<String> targetDimensionValues) {
        this.targetDimensionValues = targetDimensionValues;
    }

    public String getTargetDimensionFilter() {
        return this.targetDimensionFilter;
    }

    public void setTargetDimensionFilter(String targetDimensionFilter) {
        this.targetDimensionFilter = targetDimensionFilter;
    }

    public SourceDimensionRange getSourceDimensionRange() {
        return this.sourceDimensionRange;
    }

    public void setSourceDimensionRange(SourceDimensionRange sourceDimensionRange) {
        this.sourceDimensionRange = sourceDimensionRange;
    }

    public List<String> getSourceDimensionValues() {
        return this.sourceDimensionValues;
    }

    public void setSourceDimensionValues(List<String> sourceDimensionValues) {
        this.sourceDimensionValues = sourceDimensionValues;
    }

    public String getSourceDimensionFilter() {
        return this.sourceDimensionFilter;
    }

    public void setSourceDimensionFilter(String sourceDimensionFilter) {
        this.sourceDimensionFilter = sourceDimensionFilter;
    }

    public CommitState getCommitState() {
        return this.commitState;
    }

    public void setCommitState(CommitState commitState) {
        this.commitState = commitState;
    }

    public List<DimensionData> getDimDataRange() {
        return this.dimDataRange;
    }

    public void setDimDataRange(List<DimensionData> dimDataRange) {
        this.dimDataRange = dimDataRange;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}

