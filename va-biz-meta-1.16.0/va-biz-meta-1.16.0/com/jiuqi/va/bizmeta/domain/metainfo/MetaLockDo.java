/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.bizmeta.domain.metainfo;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="META_LOCK")
public class MetaLockDo
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    @Column(name="UNIQUECODE")
    private String uniqueCode;
    @Column(name="LOCKUSER")
    private String lockuser;
    @Column(name="LOCKTIME")
    private Date locktime;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getLockuser() {
        return this.lockuser;
    }

    public void setLockuser(String lockuser) {
        this.lockuser = lockuser;
    }

    public Date getLocktime() {
        return this.locktime;
    }

    public void setLocktime(Date locktime) {
        this.locktime = locktime;
    }
}

