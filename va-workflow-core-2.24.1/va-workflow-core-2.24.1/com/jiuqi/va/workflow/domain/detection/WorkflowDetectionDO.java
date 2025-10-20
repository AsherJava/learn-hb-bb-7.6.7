/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.workflow.domain.detection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="WORKFLOW_DETECTION")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class WorkflowDetectionDO
extends TenantDO {
    private static final long serialVersionUID = 123456789L;
    @Id
    private String id;
    private String workflowdefinekey;
    private String bizdefine;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date operatetime;
    private String operator;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkflowdefinekey() {
        return this.workflowdefinekey;
    }

    public void setWorkflowdefinekey(String workflowdefinekey) {
        this.workflowdefinekey = workflowdefinekey;
    }

    public String getBizdefine() {
        return this.bizdefine;
    }

    public void setBizdefine(String bizdefine) {
        this.bizdefine = bizdefine;
    }

    public Date getOperatetime() {
        return this.operatetime;
    }

    public void setOperatetime(Date operatetime) {
        this.operatetime = operatetime;
    }

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}

