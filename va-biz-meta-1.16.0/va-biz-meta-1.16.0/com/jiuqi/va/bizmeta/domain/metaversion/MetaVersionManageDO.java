/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bizmeta.domain.metaversion;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import java.util.UUID;

public class MetaVersionManageDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String username;
    private Date createtime;
    private String info;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

