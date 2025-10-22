/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 */
package com.jiuqi.nr.reminder.internal;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Reminder {
    public static final byte TYPE_AUTO = 0;
    public static final byte TYPE_MANUAL = 1;
    public static final byte STATUS_UNDO = 0;
    public static final byte STATUS_ING = 1;
    public static final byte STATUS_OK = 2;
    public static final byte STATUS_ERROR = 3;
    public static final byte STATUS_WAIT = 4;
    private String id = Reminder.generateId();
    private String title;
    private byte type;
    private List<String> userIds;
    private String taskId;
    private String formSchemeId;
    private String period;
    private String unitId;
    private byte unitRange;
    private byte roleType;
    private List<Integer> handleMethod;
    private String content;
    private byte executeStatus;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime executeTime;
    private List<String> unitIds;
    private List<String> roleIds;
    private String originalContent;
    private List<String> ccInfos;
    private List<String> ccParticipants;
    private int sendTime;
    private int dayBeforeDeadline;
    private int dayAfterDeadline;
    private int repeatMode;
    private String showSendTime;
    private int frequencyMode;
    private String trigerCorn;
    private int executeNums;
    private String nextSendTime;
    private String validStartTime;
    private String validEndTime;
    private String regularTime;
    private String periodType;
    private boolean isEdit;
    private String groupRange;
    private String formRange;
    private String workFlowType;
    private List<String> formKeys;

    public static String generateId() {
        return UUID.randomUUID().toString();
    }

    public List<String> getUnitIds() {
        return this.unitIds;
    }

    public void setUnitIds(List<String> unitIds) {
        this.unitIds = unitIds;
    }

    public List<String> getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUserIds() {
        return this.userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public byte getType() {
        return this.type;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public byte getUnitRange() {
        return this.unitRange;
    }

    public void setUnitRange(byte unitRange) {
        this.unitRange = unitRange;
    }

    public byte getRoleType() {
        return this.roleType;
    }

    public void setRoleType(byte roleType) {
        this.roleType = roleType;
    }

    public List<Integer> getHandleMethod() {
        return this.handleMethod;
    }

    public void setHandleMethod(List<Integer> handleMethod) {
        this.handleMethod = handleMethod;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getExecuteStatus() {
        return this.executeStatus;
    }

    public void setExecuteStatus(byte executeStatus) {
        this.executeStatus = executeStatus;
    }

    public LocalDateTime getExecuteTime() {
        return this.executeTime;
    }

    public void setExecuteTime(LocalDateTime executeTime) {
        this.executeTime = executeTime;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public int getDayBeforeDeadline() {
        return this.dayBeforeDeadline;
    }

    public void setDayBeforeDeadline(int dayBeforeDeadline) {
        this.dayBeforeDeadline = dayBeforeDeadline;
    }

    public int getDayAfterDeadline() {
        return this.dayAfterDeadline;
    }

    public void setDayAfterDeadline(int dayAfterDeadline) {
        this.dayAfterDeadline = dayAfterDeadline;
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public List<String> getCcInfos() {
        return this.ccInfos;
    }

    public void setCcInfos(List<String> ccInfos) {
        this.ccInfos = ccInfos;
    }

    public List<String> getCcParticipants() {
        return this.ccParticipants;
    }

    public void setCcParticipants(List<String> ccParticipants) {
        this.ccParticipants = ccParticipants;
    }

    public String getShowSendTime() {
        return this.showSendTime;
    }

    public void setShowSendTime(String showSendTime) {
        this.showSendTime = showSendTime;
    }

    public int getFrequencyMode() {
        return this.frequencyMode;
    }

    public void setFrequencyMode(int frequencyMode) {
        this.frequencyMode = frequencyMode;
    }

    public String getTrigerCorn() {
        return this.trigerCorn;
    }

    public void setTrigerCorn(String trigerCorn) {
        this.trigerCorn = trigerCorn;
    }

    public int getExecuteNums() {
        return this.executeNums;
    }

    public void setExecuteNums(int executeNums) {
        this.executeNums = executeNums;
    }

    public String getNextSendTime() {
        return this.nextSendTime;
    }

    public void setNextSendTime(String nextSendTime) {
        this.nextSendTime = nextSendTime;
    }

    public String getValidStartTime() {
        return this.validStartTime;
    }

    public void setValidStartTime(String validStartTime) {
        this.validStartTime = validStartTime;
    }

    public String getValidEndTime() {
        return this.validEndTime;
    }

    public void setValidEndTime(String validEndTime) {
        this.validEndTime = validEndTime;
    }

    public String getRegularTime() {
        return this.regularTime;
    }

    public void setRegularTime(String regularTime) {
        this.regularTime = regularTime;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public boolean isEdit() {
        return this.isEdit;
    }

    public void setEdit(boolean edit) {
        this.isEdit = edit;
    }

    public String getGroupRange() {
        return this.groupRange;
    }

    public void setGroupRange(String groupRange) {
        this.groupRange = groupRange;
    }

    public String getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(String workFlowType) {
        this.workFlowType = workFlowType;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public String getFormRange() {
        return this.formRange;
    }

    public void setFormRange(String formRange) {
        this.formRange = formRange;
    }

    public String getOriginalContent() {
        return this.originalContent;
    }

    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

