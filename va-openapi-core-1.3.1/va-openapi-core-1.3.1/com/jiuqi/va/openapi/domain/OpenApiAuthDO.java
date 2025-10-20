/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.domain.openapi.OpenApiRegisterDO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 *  javax.persistence.Transient
 */
package com.jiuqi.va.openapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.domain.openapi.OpenApiRegisterDO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="openapi_auth")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class OpenApiAuthDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private UUID id;
    private String clientid;
    private String clienttitle;
    private String randomcode;
    private String openid;
    private Integer stopflag;
    private BigDecimal expiretime;
    private String authdata;
    private String remark;
    @Transient
    private List<OpenApiRegisterDO> apiRegisterList;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getClientid() {
        return this.clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClienttitle() {
        return this.clienttitle;
    }

    public void setClienttitle(String clienttitle) {
        this.clienttitle = clienttitle;
    }

    public String getRandomcode() {
        return this.randomcode;
    }

    public void setRandomcode(String randomcode) {
        this.randomcode = randomcode;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getStopflag() {
        return this.stopflag;
    }

    public void setStopflag(Integer stopflag) {
        this.stopflag = stopflag;
    }

    public BigDecimal getExpiretime() {
        return this.expiretime;
    }

    public void setExpiretime(BigDecimal expiretime) {
        this.expiretime = expiretime;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuthdata() {
        return this.authdata;
    }

    public void setAuthdata(String authdata) {
        this.authdata = authdata;
    }

    public List<OpenApiRegisterDO> getApiRegisterList() {
        return this.apiRegisterList;
    }

    public void setApiRegisterList(List<OpenApiRegisterDO> apiRegisterList) {
        this.apiRegisterList = apiRegisterList;
    }
}

