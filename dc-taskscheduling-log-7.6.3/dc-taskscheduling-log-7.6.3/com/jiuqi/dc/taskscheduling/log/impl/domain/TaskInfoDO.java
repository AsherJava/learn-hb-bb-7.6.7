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
@Table(name="DC_LOG_TASKINFO")
public class TaskInfoDO
extends TenantDO {
    public static final String TABLENAME = "DC_LOG_TASKINFO";
    private static final long serialVersionUID = -5832689439208713452L;
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="TASKTYPE")
    private String taskType;
    @Column(name="CREATETIME")
    private Date createTime;
    @Column(name="MESSAGE")
    private String message;
    @Column(name="MSGSTORETYPE")
    private String msgStoreType;
    @Column(name="ENDTIME")
    private Date endTime;
    @Column(name="RESULTLOG")
    private String resultLog;
    @Column(name="SOURCETYPE")
    private String sourceType = "0";
    @Column(name="EXT_1")
    private String ext_1;
    @Column(name="EXT_2")
    private String ext_2;
    @Column(name="EXT_3")
    private String ext_3;
    @Column(name="EXT_4")
    private String ext_4;
    @Column(name="EXT_5")
    private String ext_5;

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

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getSourceType() {
        return this.sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getExt_1() {
        return this.ext_1;
    }

    public void setExt_1(String ext_1) {
        this.ext_1 = ext_1;
    }

    public String getExt_2() {
        return this.ext_2;
    }

    public void setExt_2(String ext_2) {
        this.ext_2 = ext_2;
    }

    public String getExt_3() {
        return this.ext_3;
    }

    public void setExt_3(String ext_3) {
        this.ext_3 = ext_3;
    }

    public String getExt_4() {
        return this.ext_4;
    }

    public void setExt_4(String ext_4) {
        this.ext_4 = ext_4;
    }

    public String getExt_5() {
        return this.ext_5;
    }

    public void setExt_5(String ext_5) {
        this.ext_5 = ext_5;
    }
}

