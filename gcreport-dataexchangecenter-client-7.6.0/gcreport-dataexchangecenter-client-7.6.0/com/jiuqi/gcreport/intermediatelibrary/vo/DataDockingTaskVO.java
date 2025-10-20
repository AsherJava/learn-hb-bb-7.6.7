/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.vo;

import com.jiuqi.gcreport.intermediatelibrary.vo.DataDockingTaskDataVO;
import java.util.List;

public class DataDockingTaskVO {
    private String taskCode;
    private List<DataDockingTaskDataVO> taskData;

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public List<DataDockingTaskDataVO> getTaskData() {
        return this.taskData;
    }

    public void setTaskData(List<DataDockingTaskDataVO> taskData) {
        this.taskData = taskData;
    }
}

