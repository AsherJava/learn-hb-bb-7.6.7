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
@Table(name="DC_lOG_MESSAGE")
public class TaskClobMessageDO
extends TenantDO {
    private static final long serialVersionUID = 1381923696918009749L;
    public static final String TABLENAME = "DC_lOG_MESSAGE";
    @Id
    @Column(name="ID")
    private String id;
    @Column(name="MESSAGE")
    private String message;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

