/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.organization.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="AUTH_ORG_ACTION")
public class OrgActionAuthDO
extends TenantDO
implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    private Integer biztype;
    private String bizname;
    private Integer authtype;
    private String orgcategory;
    private String actname;
    private Integer authflag;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getBiztype() {
        return this.biztype;
    }

    public void setBiztype(Integer biztype) {
        this.biztype = biztype;
    }

    public String getBizname() {
        return this.bizname;
    }

    public void setBizname(String bizname) {
        this.bizname = bizname;
    }

    public Integer getAuthtype() {
        return this.authtype;
    }

    public void setAuthtype(Integer authtype) {
        this.authtype = authtype;
    }

    public String getOrgcategory() {
        return this.orgcategory;
    }

    public void setOrgcategory(String orgcategory) {
        this.orgcategory = orgcategory;
    }

    public String getActname() {
        return this.actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public Integer getAuthflag() {
        return this.authflag;
    }

    public void setAuthflag(Integer authflag) {
        this.authflag = authflag;
    }
}

