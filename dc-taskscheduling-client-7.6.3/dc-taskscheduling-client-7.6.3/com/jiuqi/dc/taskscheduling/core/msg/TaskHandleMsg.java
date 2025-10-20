/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.utils.CompressUtil
 */
package com.jiuqi.dc.taskscheduling.core.msg;

import com.jiuqi.dc.base.common.utils.CompressUtil;
import java.io.Serializable;

public class TaskHandleMsg
implements Serializable {
    private static final long serialVersionUID = 5585574539851288882L;
    private String taskItemId;
    private String taskName;
    private String dimCode;
    private String instanceId;
    private String preNodeId;
    private Boolean paramCompress;
    private String param;
    private int level;
    private String runnerId;
    private String sqlRecordEnable;

    public TaskHandleMsg() {
    }

    public TaskHandleMsg(String taskName, String dimCode, String instanceId, String preNodeId, String param, int level, String runnerId) {
        this.taskName = taskName;
        this.dimCode = dimCode;
        this.instanceId = instanceId;
        this.preNodeId = preNodeId;
        this.paramCompress = CompressUtil.enableCompress((String)param);
        this.param = this.paramCompress != false ? CompressUtil.compress((String)param) : param;
        this.level = level;
        this.runnerId = runnerId;
    }

    public String getTaskItemId() {
        return this.taskItemId;
    }

    public void setTaskItemId(String taskItemId) {
        this.taskItemId = taskItemId;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public String getInstanceId() {
        return this.instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getPreNodeId() {
        return this.preNodeId;
    }

    public void setPreNodeId(String preNodeId) {
        this.preNodeId = preNodeId;
    }

    public Boolean getParamCompress() {
        return this.paramCompress;
    }

    public void setParamCompress(Boolean paramCompress) {
        this.paramCompress = paramCompress;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getSqlRecordEnable() {
        return this.sqlRecordEnable;
    }

    public void setSqlRecordEnable(String sqlRecordEnable) {
        this.sqlRecordEnable = sqlRecordEnable;
    }
}

