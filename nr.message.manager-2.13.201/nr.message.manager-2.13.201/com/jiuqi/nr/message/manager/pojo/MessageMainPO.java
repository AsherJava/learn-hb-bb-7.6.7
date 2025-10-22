/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.annotation.JsonDeserialize
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 */
package com.jiuqi.nr.message.manager.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jiuqi.nr.message.manager.InstantJsonDeserializer;
import com.jiuqi.nr.message.manager.InstantJsonSerializer;
import java.io.Serializable;
import java.sql.Timestamp;

@Deprecated
public class MessageMainPO
implements Serializable {
    private static final long serialVersionUID = -1762754832212794416L;
    private String msgId;
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    private Timestamp createTime;
    private String title;
    private String createUser;
    private String appName;
    private String param;
    private int type;
    private boolean isSticky;
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    private Timestamp validTime;
    @JsonDeserialize(using=InstantJsonDeserializer.class)
    @JsonSerialize(using=InstantJsonSerializer.class)
    private Timestamp invalidTime;
    private int year;
    private String content;

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSticky() {
        return this.isSticky;
    }

    public void setSticky(boolean sticky) {
        this.isSticky = sticky;
    }

    public Timestamp getValidTime() {
        return this.validTime;
    }

    public void setValidTime(Timestamp validTime) {
        this.validTime = validTime;
    }

    public Timestamp getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(Timestamp invalidTime) {
        this.invalidTime = invalidTime;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

