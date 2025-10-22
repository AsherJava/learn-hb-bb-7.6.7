/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.message.manager.pojo;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Deprecated
public class MessageVO {
    private String id;
    private Integer type;
    private String createUser;
    private String title;
    private Integer participantType;
    private List<String> participants;
    private String content;
    private Integer handleMode;
    private Instant validTime;
    private Instant invalidTime;
    private boolean isSticky;
    private String appName;
    private Map<String, Object> param;
    private String tag;

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

    public Integer getHandleMode() {
        return this.handleMode;
    }

    public void setHandleMode(Integer handleMode) {
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

    public Map<String, Object> getParam() {
        return this.param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

