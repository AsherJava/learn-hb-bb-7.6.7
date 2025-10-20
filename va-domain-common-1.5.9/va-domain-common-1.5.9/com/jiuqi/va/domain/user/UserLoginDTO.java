/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.va.domain.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.va.domain.user.UserDO;
import java.util.Date;
import java.util.Set;
import org.springframework.format.annotation.DateTimeFormat;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class UserLoginDTO
extends UserDO {
    private static final long serialVersionUID = 1L;
    private String loginUnit;
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date loginDate;
    private Set<String> perms;
    private String mgrFlag = "normal";

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

    public String getMgrFlag() {
        return this.mgrFlag;
    }

    public void setMgrFlag(String mgrFlag) {
        this.mgrFlag = mgrFlag;
    }
}

