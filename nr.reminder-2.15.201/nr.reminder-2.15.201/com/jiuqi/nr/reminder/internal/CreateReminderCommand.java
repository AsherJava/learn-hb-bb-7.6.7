/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.reminder.internal;

import com.jiuqi.nr.reminder.infer.ParticipantHelper;
import com.jiuqi.nr.reminder.untils.EntityHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CreateReminderCommand {
    public static final byte UNIT_ALL_SUBORDINATE = 0;
    public static final byte UNIT_DIRECT_SUBORDINATE = 1;
    public static final byte UNIT_ALL_SUBORDINATE_UNUPLOAD = 2;
    public static final byte UNIT_DIRECT_SUBORDINATE_UNUPLOAD = 3;
    public static final byte UNIT_CUSTOM = 8;
    public static final byte ROLE_ALL = 0;
    public static final byte ROLE_PARTICIPANT = 1;
    public static final byte ROLE_CUSTOM = 8;
    private String id;
    private String title;
    private byte type;
    private String taskId;
    private String viewKey;
    private String formSchemeId;
    private String period;
    private String unitId;
    private byte unitRange;
    private List<String> unitIds;
    private byte roleType;
    private List<String> roleIds;
    private int dayBeforeDeadline;
    private int dayAfterDeadline;
    private int repeatMode;
    private List<Integer> handleMethod;
    private String content;
    private int sendTime;
    private List<String> userIds;
    private String regularTime;
    private String showSendTime;
    private int frequencyMode;
    private String regularDay;
    private String regularWeek;
    private String regularMonth;
    private String validStartTime;
    private String validEndTime;
    private int executeNums;
    private String periodType;
    private boolean isEdit;
    private String groupRange;
    private String formRange;
    private String workFlowType;
    private List<String> formKeys;
    private Map<String, Map<String, ArrayList<String>>> map;
    private String originalContent;
    private List<String> ccInfos;
    private List<String> ccParticipants;

    public List<String> computeUnitId(EntityHelper entityHelper, ParticipantHelper participantHelper) throws Exception {
        List<String> unitIds;
        if (0 == this.unitRange) {
            unitIds = entityHelper.getAllSubordinate(this.viewKey, this.unitId, this.formSchemeId, this.period);
        } else if (1 == this.unitRange) {
            unitIds = entityHelper.getDirectSubordinate(this.viewKey, this.unitId, this.formSchemeId, this.period);
        } else if (8 == this.unitRange) {
            unitIds = this.unitIds;
        } else if (2 == this.unitRange) {
            unitIds = entityHelper.getDirectSubordinate(this.viewKey, this.unitId, this.formSchemeId, this.period);
        } else if (3 == this.unitRange) {
            unitIds = entityHelper.getAllSubordinate(this.viewKey, this.unitId, this.formSchemeId, this.period);
        } else {
            throw new Exception("\u4e0d\u652f\u6301\u7684\u5355\u4f4d\u8303\u56f4\u7c7b\u578b");
        }
        return unitIds;
    }

    public void buildTitle(String taskTitle) {
        StringBuilder builder = new StringBuilder(taskTitle).append("-");
        if (0 == this.unitRange) {
            builder.append("\u6240\u6709\u4e0b\u7ea7");
        } else if (1 == this.unitRange) {
            builder.append("\u76f4\u63a5\u4e0b\u7ea7");
        } else if (8 == this.unitRange) {
            int size = this.unitIds == null ? 0 : this.unitIds.size();
            builder.append(size).append("\u5bb6\u5355\u4f4d");
        } else if (2 == this.unitRange) {
            builder.append("\u76f4\u63a5\u4e0b\u7ea7");
        } else if (3 == this.unitRange) {
            builder.append("\u6240\u6709\u4e0b\u7ea7");
        }
        this.title = builder.toString();
    }

    public List<String> computeUnitId(EntityHelper entityHelper) throws Exception {
        List<String> unitIds;
        if (0 == this.unitRange) {
            unitIds = entityHelper.getAllSubordinate(this.viewKey, this.unitId, this.formSchemeId, this.period);
        } else if (1 == this.unitRange) {
            unitIds = entityHelper.getDirectSubordinate(this.viewKey, this.unitId, this.formSchemeId, this.period);
        } else if (8 == this.unitRange) {
            unitIds = this.unitIds;
        } else if (2 == this.unitRange) {
            unitIds = entityHelper.getAllSubordinate(this.viewKey, this.unitId, this.formSchemeId, this.period);
        } else if (3 == this.unitRange) {
            unitIds = entityHelper.getDirectSubordinate(this.viewKey, this.unitId, this.formSchemeId, this.period);
        } else {
            throw new Exception("\u4e0d\u652f\u6301\u7684\u5355\u4f4d\u8303\u56f4\u7c7b\u578b");
        }
        return unitIds;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getUnitId() {
        return this.unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
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

    public int getDayBeforeDeadline() {
        return this.dayBeforeDeadline;
    }

    public void setDayBeforeDeadline(int dayBeforeDeadline) {
        this.dayBeforeDeadline = dayBeforeDeadline;
    }

    public int getRepeatMode() {
        return this.repeatMode;
    }

    public void setRepeatMode(int repeatMode) {
        this.repeatMode = repeatMode;
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

    public int getDayAfterDeadline() {
        return this.dayAfterDeadline;
    }

    public void setDayAfterDeadline(int dayAfterDeadline) {
        this.dayAfterDeadline = dayAfterDeadline;
    }

    public int getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<String> getUserIds() {
        return this.userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getCcInfos() {
        return this.ccInfos;
    }

    public void setCcInfos(List<String> ccInfos) {
        this.ccInfos = ccInfos;
    }

    public String getRegularTime() {
        return this.regularTime;
    }

    public void setRegularTime(String regularTime) {
        this.regularTime = regularTime;
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

    public String getRegularDay() {
        return this.regularDay;
    }

    public void setRegularDay(String regularDay) {
        this.regularDay = regularDay;
    }

    public String getRegularWeek() {
        return this.regularWeek;
    }

    public void setRegularWeek(String regularWeek) {
        this.regularWeek = regularWeek;
    }

    public String getRegularMonth() {
        return this.regularMonth;
    }

    public void setRegularMonth(String regularMonth) {
        this.regularMonth = regularMonth;
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

    public int getExecuteNums() {
        return this.executeNums;
    }

    public void setExecuteNums(int executeNums) {
        this.executeNums = executeNums;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public List<String> getCcParticipants() {
        return this.ccParticipants;
    }

    public void setCcParticipants(List<String> ccParticipants) {
        this.ccParticipants = ccParticipants;
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

    public Map<String, Map<String, ArrayList<String>>> getMap() {
        return this.map;
    }

    public void setMap(Map<String, Map<String, ArrayList<String>>> map) {
        this.map = map;
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

