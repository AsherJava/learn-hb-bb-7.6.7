/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.basedata.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="BASEDATA_COMMONLY_USED")
public class BaseDataCommonlyUsedDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    private String userid;
    private String definename;
    private String objectcode;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDefinename() {
        return this.definename;
    }

    public void setDefinename(String definename) {
        this.definename = definename;
    }

    public String getObjectcode() {
        return this.objectcode;
    }

    public void setObjectcode(String objectcode) {
        this.objectcode = objectcode;
    }
}

