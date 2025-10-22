/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.custom.bean;

import java.io.Serializable;
import java.util.Date;

public class WorkFlowParticipant
implements Serializable {
    private static final long serialVersionUID = -6854719579047717585L;
    private String id;
    private String linkid;
    private String nodeid;
    private String strategyid;
    private String[] roleIds;
    private String[] userIds;
    private Date updatetime;
    private String param;
    private String description;

    public String getLinkid() {
        return this.linkid;
    }

    public void setLinkid(String linkid) {
        this.linkid = linkid;
    }

    public Date getUpdatetime() {
        return this.updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeid() {
        return this.nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public String getStrategyid() {
        return this.strategyid;
    }

    public void setStrategyid(String strategyid) {
        this.strategyid = strategyid;
    }

    public String[] getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(String[] roleIds) {
        this.roleIds = roleIds;
    }

    public String[] getUserIds() {
        return this.userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

