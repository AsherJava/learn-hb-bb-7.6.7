/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.domain.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Date;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class LogDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String sessionid;
    private String optuser;
    private String orgcode;
    private String orgtitle;
    private String optip;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss.SSS")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Date opttime;
    private String funcname;
    private String actname;
    private String biztype;
    private String bizid;
    private String properties;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSessionid() {
        return this.sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getOptuser() {
        return this.optuser;
    }

    public void setOptuser(String optuser) {
        this.optuser = optuser;
    }

    public String getOrgcode() {
        return this.orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getOrgtitle() {
        return this.orgtitle;
    }

    public void setOrgtitle(String orgtitle) {
        this.orgtitle = orgtitle;
    }

    public String getOptip() {
        return this.optip;
    }

    public void setOptip(String optip) {
        this.optip = optip;
    }

    public Date getOpttime() {
        return this.opttime;
    }

    public void setOpttime(Date opttime) {
        this.opttime = opttime;
    }

    public String getFuncname() {
        return this.funcname;
    }

    public void setFuncname(String funcname) {
        this.funcname = funcname;
    }

    public String getActname() {
        return this.actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public String getBiztype() {
        return this.biztype;
    }

    public void setBiztype(String biztype) {
        this.biztype = biztype;
    }

    public String getBizid() {
        return this.bizid;
    }

    public void setBizid(String bizid) {
        this.bizid = bizid;
    }

    public String getProperties() {
        return this.properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}

