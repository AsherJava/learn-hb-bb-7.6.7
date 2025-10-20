/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Entity
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.dc.taskscheduling.lockmgr.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DC_TASKMANAGE")
public class TaskManageDO
extends TenantDO {
    public static final String TABLENAME = "DC_TASKMANAGE";
    private static final long serialVersionUID = -6853666563941870849L;
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="VER")
    private Long ver;
    @Column(name="TASKNAME")
    private String taskName;
    @Column(name="UNITCODE")
    private String unitCode;
    @Column(name="BEGINTIME")
    private Timestamp beginTime;
    @Column(name="BATCHNUM")
    private Integer batchNum;

    public String getTaskName() {
        return this.taskName;
    }

    public TaskManageDO() {
    }

    public TaskManageDO(String taskName, String unitCode, Timestamp beginTime, Integer batchNum) {
        this.taskName = taskName;
        this.unitCode = unitCode;
        this.beginTime = beginTime;
        this.batchNum = batchNum;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Timestamp getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getBatchNum() {
        return this.batchNum;
    }

    public void setBatchNum(Integer batchNum) {
        this.batchNum = batchNum;
    }
}

