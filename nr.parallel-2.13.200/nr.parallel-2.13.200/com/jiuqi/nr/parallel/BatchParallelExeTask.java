/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.parallel;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.Map;

public class BatchParallelExeTask
implements Serializable {
    private static final long serialVersionUID = -760309027875030975L;
    private String key;
    private String parentTaskID;
    private Map<String, DimensionValue> dimensionValues;
    private double weight;
    private Serializable taskInfo;
    private String type;
    private String mainDimName;
    private boolean finished;

    public BatchParallelExeTask() {
    }

    public BatchParallelExeTask(String parentTaskID, Map<String, DimensionValue> dimensionValues) {
        this.parentTaskID = parentTaskID;
        this.dimensionValues = dimensionValues;
    }

    public String getKey() {
        return this.key;
    }

    public String getParentTaskID() {
        return this.parentTaskID;
    }

    public double getWeight() {
        return this.weight;
    }

    public Serializable getTaskInfo() {
        return this.taskInfo;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setParentTaskID(String parentTaskID) {
        this.parentTaskID = parentTaskID;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setTaskInfo(Serializable taskInfo) {
        this.taskInfo = taskInfo;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Map<String, DimensionValue> getDimensionValues() {
        return this.dimensionValues;
    }

    public void setDimensionValues(Map<String, DimensionValue> dimensionValues) {
        this.dimensionValues = dimensionValues;
    }

    public String getMainDimName() {
        return this.mainDimName;
    }

    public void setMainDimName(String mainDimName) {
        this.mainDimName = mainDimName;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.key == null ? 0 : this.key.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        BatchParallelExeTask other = (BatchParallelExeTask)obj;
        return !(this.key == null ? other.key != null : !this.key.equals(other.key));
    }
}

