/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.message.domain.VaMessageChannelDTO
 */
package com.jiuqi.nr.reminder.plan;

import com.jiuqi.va.message.domain.VaMessageChannelDTO;
import java.util.List;

public class TaskInfo {
    private String taskId;
    private String taskTitle;
    private String formSchemeTitle;
    private String workFlowType;
    private String entityId;
    private String msgChannel;
    private List<VaMessageChannelDTO> msgChannels;
    private String currPeriod;
    private boolean showOrg;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(String workFlowType) {
        this.workFlowType = workFlowType;
    }

    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getMsgChannel() {
        return this.msgChannel;
    }

    public void setMsgChannel(String msgChannel) {
        this.msgChannel = msgChannel;
    }

    public String getCurrPeriod() {
        return this.currPeriod;
    }

    public List<VaMessageChannelDTO> getMsgChannels() {
        return this.msgChannels;
    }

    public void setMsgChannels(List<VaMessageChannelDTO> msgChannels) {
        this.msgChannels = msgChannels;
    }

    public List<VaMessageChannelDTO> getMsgChannelList() {
        return this.msgChannels;
    }

    public void setCurrPeriod(String currPeriod) {
        this.currPeriod = currPeriod;
    }

    public boolean isShowOrg() {
        return this.showOrg;
    }

    public void setShowOrg(boolean showOrg) {
        this.showOrg = showOrg;
    }
}

