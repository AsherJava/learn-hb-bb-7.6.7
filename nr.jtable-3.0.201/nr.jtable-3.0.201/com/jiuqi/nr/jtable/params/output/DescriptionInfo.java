/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.jiuqi.nr.data.logic.facade.param.output.DesCheckState
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.data.logic.facade.param.output.DesCheckState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;

@ApiModel(value="DescriptionInfo", description="\u516c\u5f0f\u5ba1\u6838\u9519\u8bef\u8bf4\u660e")
public class DescriptionInfo
implements Serializable {
    private static final long serialVersionUID = -5717726151014963044L;
    @ApiModelProperty(value="\u7528\u6237ID", name="unitTitle")
    private String userId;
    @ApiModelProperty(value="\u4fee\u6539\u65f6\u95f4", name="unitTitle")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Instant updateTime;
    @ApiModelProperty(value="\u7528\u6237\u540d\u79f0", name="userName")
    private String userName;
    @ApiModelProperty(value="\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u6587\u672c", name="unitTitle")
    private String description;
    @ApiModelProperty(value="\u7528\u6237\u6807\u9898", name="userTitle")
    private String userTitle;
    @ApiModelProperty(value="\u51fa\u9519\u8bf4\u660e\u68c0\u67e5\u7ed3\u679c", name="state")
    private DesCheckState state;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserTitle() {
        return this.userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public DesCheckState getState() {
        return this.state;
    }

    public void setState(DesCheckState state) {
        this.state = state;
    }
}

