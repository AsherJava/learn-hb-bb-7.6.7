/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.workflow.domain.detection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.mapper.domain.TenantDO;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="WORKFLOW_DETECTION_DATA")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class WorkflowDetectionDataDO
extends TenantDO {
    @Id
    private String id;
    private String detectiondata;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetectiondata() {
        return this.detectiondata;
    }

    public void setDetectiondata(String detectiondata) {
        this.detectiondata = detectiondata;
    }
}

