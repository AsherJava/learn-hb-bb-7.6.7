/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.bizmeta.domain.metaauth;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="AUTH_META")
public class MetaAuthDO
extends TenantDO {
    @Id
    private UUID id;
    @Column(name="BIZTYPE")
    private Integer biztype;
    @Column(name="BIZNAME")
    private String bizname;
    @Column(name="AUTHTYPE")
    private Integer authtype;
    @Column(name="UNIQUECODE")
    private String uniqueCode;
    @Column(name="METATYPE")
    private String metaType;
    @Column(name="GROUPFLAG")
    private Integer groupflag;
    @Column(name="AUTHFLAG")
    private Integer authflag;
    @Column(name="ATAUTHORIZE")
    private Integer atauthorize;

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

    public String getUniqueCode() {
        return this.uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public String getMetaType() {
        return this.metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public Integer getGroupflag() {
        return this.groupflag;
    }

    public void setGroupflag(Integer groupflag) {
        this.groupflag = groupflag;
    }

    public Integer getAuthflag() {
        return this.authflag;
    }

    public void setAuthflag(Integer authflag) {
        this.authflag = authflag;
    }

    public Integer getAtauthorize() {
        return this.atauthorize;
    }

    public void setAtauthorize(Integer atauthorize) {
        this.atauthorize = atauthorize;
    }
}

