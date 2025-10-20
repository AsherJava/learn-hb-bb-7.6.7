/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.dc.taskscheduling.log.impl.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Deprecated
@Table(name="DC_LOG_TASKITEMINFO")
public class TaskItemInfoDO
extends TenantDO {
    public static final String TABLENAME = "DC_LOG_TASKITEMINFO";
    private static final long serialVersionUID = -4722137791821459100L;
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="TASKTYPE")
    private String taskType;
    @Column(name="INSTANCEID")
    private String instanceId;
    @Column(name="PRENODEID")
    private String preNodeId;
    @Column(name="DIMTYPE")
    private String dimType;
    @Column(name="DIMCODE")
    private String dimCode;
    @Column(name="MESSAGE")
    private String message;
    @Column(name="MSGSTORETYPE")
    private String msgStoreType;
    @Column(name="EXECUTESTATE")
    private Integer executeState;
    @Column(name="STARTTIME")
    private Date startTime;
    @Column(name="ENDTIME")
    private Date endTime;
    @Column(name="RESULTLOG")
    private String resultLog;
    @Column(name="STORETYPE")
    private Integer storeType = 1;
    @Column(name="RUNNERID")
    private String runnerId;
    @Column(name="QUEUENAME")
    private String queueName;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
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

    public String getDimType() {
        return this.dimType;
    }

    public void setDimType(String dimType) {
        this.dimType = dimType;
    }

    public String getDimCode() {
        return this.dimCode;
    }

    public void setDimCode(String dimCode) {
        this.dimCode = dimCode;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgStoreType() {
        return this.msgStoreType;
    }

    public void setMsgStoreType(String msgStoreType) {
        this.msgStoreType = msgStoreType;
    }

    public Integer getExecuteState() {
        return this.executeState;
    }

    public void setExecuteState(Integer executeState) {
        this.executeState = executeState;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getResultLog() {
        return this.resultLog;
    }

    public void setResultLog(String resultLog) {
        this.resultLog = resultLog;
    }

    public Integer getStoreType() {
        return this.storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public String getRunnerId() {
        return this.runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    public String getQueueName() {
        return this.queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}

