/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.impl.AbstractSplitStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DefaultSplitStrategy
extends AbstractSplitStrategy {
    private int size = 500;

    public DefaultSplitStrategy(int size) {
        super("default", "\u9ed8\u8ba4", "\u6309\u6307\u5b9a\u7684\u4efb\u52a1\u5927\u5c0f\u5747\u5206");
        this.size = size;
    }

    public DefaultSplitStrategy() {
        super("default", "\u9ed8\u8ba4", "\u6309\u6307\u5b9a\u7684\u4efb\u52a1\u5927\u5c0f\u5747\u5206");
    }

    @Override
    public List<BatchParallelExeTask> doSplit(BatchParallelExeTask originalTask, int listSize) {
        Map<String, DimensionValue> dimensionValues = originalTask.getDimensionValues();
        DimensionValue dimValue = dimensionValues.get(originalTask.getMainDimName());
        ArrayList<BatchParallelExeTask> subtasks = new ArrayList<BatchParallelExeTask>();
        String valueStr = dimValue.getValue();
        String[] valueArray = valueStr.split(";");
        int valueSize = valueArray.length / listSize;
        if (valueSize < this.size) {
            valueSize = this.size;
        }
        if (valueArray.length > 1) {
            this.createSubTasks(originalTask, valueArray, subtasks, listSize, valueSize);
        } else {
            BatchParallelExeTask subTask = new BatchParallelExeTask(originalTask.getKey(), dimensionValues);
            subTask.setWeight(1.0);
            subTask.setKey(UUID.randomUUID().toString());
            subTask.setTaskInfo(originalTask.getTaskInfo());
            subTask.setType(originalTask.getType());
            subtasks.add(subTask);
        }
        return subtasks;
    }

    private void createSubTasks(BatchParallelExeTask originalTask, String[] dimValueArray, List<BatchParallelExeTask> subtasks, int listSize, int valueSize) {
        StringBuilder subBatchValues = new StringBuilder();
        int count = 0;
        for (String dimValue : dimValueArray) {
            subBatchValues.append(dimValue).append(";");
            if (++count != valueSize) continue;
            subBatchValues.setLength(subBatchValues.length() - 1);
            DimensionValue newDimValue = new DimensionValue();
            newDimValue.setName(originalTask.getMainDimName());
            newDimValue.setValue(subBatchValues.toString());
            this.addTask(originalTask, subtasks, newDimValue, (double)count / (double)dimValueArray.length);
            subBatchValues.setLength(0);
            count = 0;
        }
        if (count > 0) {
            if (subtasks.size() < listSize) {
                subBatchValues.setLength(subBatchValues.length() - 1);
                DimensionValue newDimValue = new DimensionValue();
                newDimValue.setName(originalTask.getMainDimName());
                newDimValue.setValue(subBatchValues.toString());
                this.addTask(originalTask, subtasks, newDimValue, (double)count / (double)dimValueArray.length);
            } else {
                BatchParallelExeTask lastTask = subtasks.get(subtasks.size() - 1);
                DimensionValue newDimValue = lastTask.getDimensionValues().get(originalTask.getMainDimName());
                newDimValue.setValue(subBatchValues.toString() + newDimValue.getValue());
            }
        }
    }

    private void addTask(BatchParallelExeTask originalTask, List<BatchParallelExeTask> subtasks, DimensionValue subBatchDimValues, double weight) {
        HashMap<String, DimensionValue> newDimensionValues = new HashMap<String, DimensionValue>(originalTask.getDimensionValues());
        newDimensionValues.put(originalTask.getMainDimName(), subBatchDimValues);
        BatchParallelExeTask subTask = new BatchParallelExeTask(originalTask.getKey(), newDimensionValues);
        subTask.setKey(UUID.randomUUID().toString());
        subTask.setWeight(weight);
        subTask.setTaskInfo(originalTask.getTaskInfo());
        subTask.setType(originalTask.getType());
        subtasks.add(subTask);
    }
}

