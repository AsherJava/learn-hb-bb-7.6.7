/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nvwa.login.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nvwa.login.domain.NvwaContextIdentity;
import com.jiuqi.nvwa.login.domain.NvwaContextOrg;
import com.jiuqi.nvwa.login.domain.NvwaContextUser;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class NvwaContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tenantName;
    private String id;
    private String username;
    private String name;
    private String loginUnit;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date loginDate;
    private Set<String> perms;
    private Map<String, Object> extInfo = null;
    private String mgrFlag = "normal";
    private NvwaContextUser conetxtUser;
    private NvwaContextIdentity contextIdentity;
    private NvwaContextOrg contextOrg;

    public String getTenantName() {
        return this.tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginUnit() {
        return this.loginUnit;
    }

    public void setLoginUnit(String loginUnit) {
        this.loginUnit = loginUnit;
    }

    public Date getLoginDate() {
        return this.loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Set<String> getPerms() {
        return this.perms;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }

    public Map<String, Object> getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }

    public Object getExtInfo(String key) {
        if (this.extInfo == null) {
            return null;
        }
        return this.extInfo.get(key);
    }

    public void addExtInfo(String key, Object value) {
        if (this.extInfo == null) {
            this.extInfo = new HashMap<String, Object>();
        }
        this.extInfo.put(key, value);
    }

    public String getMgrFlag() {
        return this.mgrFlag;
    }

    public void setMgrFlag(String mgrFlag) {
        this.mgrFlag = mgrFlag;
    }

    public NvwaContextUser getConetxtUser() {
        return this.conetxtUser;
    }

    public void setConetxtUser(NvwaContextUser conetxtUser) {
        this.conetxtUser = conetxtUser;
    }

    public NvwaContextOrg getContextOrg() {
        return this.contextOrg;
    }

    public void setContextOrg(NvwaContextOrg contextOrg) {
        this.contextOrg = contextOrg;
    }

    public NvwaContextIdentity getContextIdentity() {
        return this.contextIdentity;
    }

    public void setContextIdentity(NvwaContextIdentity contextIdentity) {
        this.contextIdentity = contextIdentity;
    }
}

