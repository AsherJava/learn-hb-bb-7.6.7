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
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Deprecated
@Table(name="DC_lOG_RESULTLOG")
public class TaskClobLogDO
extends TenantDO {
    private static final long serialVersionUID = 6519569348144848076L;
    public static final String TABLENAME = "DC_lOG_RESULTLOG";
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="RESULTLOG")
    private String resultLog;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultLog() {
        return this.resultLog;
    }

    public void setResultLog(String resultLog) {
        this.resultLog = resultLog;
    }
}

