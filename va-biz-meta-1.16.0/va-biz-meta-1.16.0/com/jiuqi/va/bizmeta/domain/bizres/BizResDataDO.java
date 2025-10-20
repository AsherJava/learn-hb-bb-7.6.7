/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Table
 */
package com.jiuqi.va.bizmeta.domain.bizres;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import javax.persistence.Table;

@Table(name="BIZ_RES_DATA")
public class BizResDataDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private byte[] resfile;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setResfile(byte[] resfile) {
        this.resfile = resfile;
    }

    public UUID getId() {
        return this.id;
    }

    public byte[] getResfile() {
        return this.resfile;
    }
}

