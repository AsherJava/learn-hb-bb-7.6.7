/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.nr.transmission.data.intf;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.nr.transmission.data.dto.SyncSchemeParamDTO;
import com.jiuqi.nr.transmission.data.intf.UserInfoParam;
import java.util.Date;

public class SyncFileParam {
    private SyncSchemeParamDTO syncSchemeParamDTO;
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private Date startTimes;
    private UserInfoParam userInfo;
    private String syncServiceName;

    public SyncSchemeParamDTO getSyncSchemeParamDTO() {
        return this.syncSchemeParamDTO;
    }

    public void setSyncSchemeParamDTO(SyncSchemeParamDTO syncSchemeParamDTO) {
        this.syncSchemeParamDTO = syncSchemeParamDTO;
    }

    public Date getStartTimes() {
        return this.startTimes;
    }

    public void setStartTimes(Date startTimes) {
        this.startTimes = startTimes;
    }

    public UserInfoParam getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(UserInfoParam userInfo) {
        this.userInfo = userInfo;
    }

    public String getSyncServiceName() {
        return this.syncServiceName;
    }

    public void setSyncServiceName(String syncServiceName) {
        this.syncServiceName = syncServiceName;
    }
}

