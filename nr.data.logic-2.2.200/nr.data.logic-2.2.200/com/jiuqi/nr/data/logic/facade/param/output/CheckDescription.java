/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.data.logic.facade.param.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckState;
import java.io.Serializable;
import java.time.Instant;

public class CheckDescription
implements Serializable {
    private static final long serialVersionUID = 1843898171721431934L;
    private String userId;
    private String userNickName;
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Instant updateTime;
    private DesCheckState state;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return this.userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public DesCheckState getState() {
        return this.state;
    }

    public void setState(DesCheckState state) {
        this.state = state;
    }
}

