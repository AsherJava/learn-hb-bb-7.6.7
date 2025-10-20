/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetch.impl.entity;

import com.jiuqi.gcreport.bde.fetch.impl.entity.BatchBdeFetchLog;
import java.util.List;
import java.util.Map;

public class BatchBdeFetchLogVO {
    Map<String, List<BatchBdeFetchLog>> batchBdeFetchLogsMap;
    String errorLog;
    Integer taskNum;
    Integer successNum;
    Integer failedNum;

    public Map<String, List<BatchBdeFetchLog>> getBatchBdeFetchLogsMap() {
        return this.batchBdeFetchLogsMap;
    }

    public void setBatchBdeFetchLogsMap(Map<String, List<BatchBdeFetchLog>> batchBdeFetchLogsMap) {
        this.batchBdeFetchLogsMap = batchBdeFetchLogsMap;
    }

    public Integer getTaskNum() {
        return this.taskNum;
    }

    public void setTaskNum(Integer taskNum) {
        this.taskNum = taskNum;
    }

    public Integer getSuccessNum() {
        return this.successNum;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public Integer getFailedNum() {
        return this.failedNum;
    }

    public void setFailedNum(Integer failedNum) {
        this.failedNum = failedNum;
    }

    public String getErrorLog() {
        return this.errorLog;
    }

    public void setErrorLog(String errorLog) {
        this.errorLog = errorLog;
    }
}

