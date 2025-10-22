/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.NotBlank
 */
package com.jiuqi.nr.message.manager.pojo;

import java.time.Instant;
import java.util.List;
import javax.validation.constraints.NotBlank;

@Deprecated
public class MessageFormVO {
    @NotBlank
    private Integer type;
    private String createUser;
    @NotBlank
    private String title;
    @NotBlank
    private Integer participantType;
    @NotBlank
    private List<String> participants;
    @NotBlank
    private String content;
    private List<Integer> handleMode;
    private Instant validTime;
    private Instant invalidTime;
    private boolean isSticky;

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
}

