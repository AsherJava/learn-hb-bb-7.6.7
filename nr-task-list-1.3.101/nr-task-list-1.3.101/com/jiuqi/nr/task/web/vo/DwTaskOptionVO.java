/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.web.vo.TaskDimensionVO;
import java.util.List;

public class DwTaskOptionVO {
    private String taskKey;
    private TaskDimensionVO dw;
    private List<TaskDimensionVO> dims;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public TaskDimensionVO getDw() {
        return this.dw;
    }

    public void setDw(TaskDimensionVO dw) {
        this.dw = dw;
    }

    public List<TaskDimensionVO> getDims() {
        return this.dims;
    }

    public void setDims(List<TaskDimensionVO> dims) {
        this.dims = dims;
    }
}

