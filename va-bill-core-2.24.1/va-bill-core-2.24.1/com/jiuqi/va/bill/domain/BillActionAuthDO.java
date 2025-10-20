/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.bill.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="AUTH_BILL_ACTION")
public class BillActionAuthDO
extends TenantDO {
    @Id
    private UUID id;
    private Integer biztype;
    private String bizname;
    private Integer authtype;
    private String definename;
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

    public String getDefinename() {
        return this.definename;
    }

    public void setDefinename(String definename) {
        this.definename = definename;
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
        this.authflag = authflag == null ? Integer.valueOf(0) : authflag;
    }
}

