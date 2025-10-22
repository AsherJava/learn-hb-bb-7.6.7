/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.pojo;

import com.jiuqi.np.message.constants.HandleModeEnum;
import com.jiuqi.np.message.constants.MessageTypeEnum;
import com.jiuqi.np.message.constants.ParticipantTypeEnum;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MessageDTO {
    private String id;
    private Integer type;
    private String createUser;
    private String title;
    private Integer participantType;
    private List<String> participants;
    private String content;
    private Integer ccParticipantType;
    private List<String> ccParticipants;
    private List<Integer> handleMode;
    private Instant validTime;
    private Instant invalidTime;
    private boolean isSticky;
    private String appName;
    private Map<String, String> param;
    private List<String> tag;
    private String todoType;
    private boolean html;
    private String actionName;

    public static MessageDTO buildDefaultTodoMessage(String title, String appName, Map<String, String> param, ParticipantTypeEnum participantTypeEnum, List<String> participants) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTitle(title);
        messageDTO.setAppName(appName);
        messageDTO.setParam(param);
        messageDTO.setParticipantType(participantTypeEnum.getCode());
        messageDTO.setParticipants(participants);
        messageDTO.setId(UUID.randomUUID().toString());
        messageDTO.setType(MessageTypeEnum.TODO.getCode());
        messageDTO.setHandleMode(Arrays.asList(HandleModeEnum.SYSTEM.getCode()));
        messageDTO.setValidTime(Instant.now());
        messageDTO.setInvalidTime(Instant.parse("2037-12-30T00:00:00.00Z"));
        messageDTO.setSticky(true);
        messageDTO.setContent(title);
        return messageDTO;
    }

    public String getTodoType() {
        return this.todoType;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Map<String, String> getParam() {
        return this.param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCcParticipantType() {
        return this.ccParticipantType;
    }

    public void setCcParticipantType(Integer ccParticipantType) {
        this.ccParticipantType = ccParticipantType;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getParticipantType() {
        return this.participantType;
    }

    public void setParticipantType(Integer participantType) {
        this.participantType = participantType;
    }

    public List<String> getParticipants() {
        return this.participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getCcParticipants() {
        return this.ccParticipants;
    }

    public void setCcParticipants(List<String> ccParticipants) {
        this.ccParticipants = ccParticipants;
    }

    public List<Integer> getHandleMode() {
        return this.handleMode;
    }

    public void setHandleMode(List<Integer> handleMode) {
        this.handleMode = handleMode;
    }

    public Instant getValidTime() {
        return this.validTime;
    }

    public void setValidTime(Instant validTime) {
        this.validTime = validTime;
    }

    public Instant getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(Instant invalidTime) {
        this.invalidTime = invalidTime;
    }

    public boolean isSticky() {
        return this.isSticky;
    }

    public void setSticky(boolean sticky) {
        this.isSticky = sticky;
    }

    public List<String> getTag() {
        return this.tag;
    }

    public boolean isHtml() {
        return this.html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public String toString() {
        return "MessageDTO{id='" + this.id + '\'' + ", type=" + this.type + ", createUser='" + this.createUser + '\'' + ", title='" + this.title + '\'' + ", participantType=" + this.participantType + ", participants=" + this.participants + ", content='" + this.content + '\'' + ", handleMode=" + this.handleMode + ", validTime=" + this.validTime + ", invalidTime=" + this.invalidTime + ", isSticky=" + this.isSticky + ", appName='" + this.appName + '\'' + ", param=" + this.param + ", tag=" + this.tag + '}';
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }
}

