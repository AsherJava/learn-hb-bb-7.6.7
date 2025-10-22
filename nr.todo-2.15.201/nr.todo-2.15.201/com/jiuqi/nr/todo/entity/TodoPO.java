/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.todo.entity;

import java.sql.Timestamp;

public class TodoPO {
    private String msgId;
    private String Tag;
    private Timestamp completeTime;
    private String actionName;
    private String todoType;
    private String formSchemeKey;
    private String todoParam;
    private String langType;
    private String otherLangActionName;
    private String otherContent;
    private String otherTitle;
    private String otherParam;

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTag() {
        return this.Tag;
    }

    public void setTag(String tag) {
        this.Tag = tag;
    }

    public Timestamp getCompleteTime() {
        return this.completeTime;
    }

    public void setCompleteTime(Timestamp completeTime) {
        this.completeTime = completeTime;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getTodoType() {
        return this.todoType;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTodoParam() {
        return this.todoParam;
    }

    public void setTodoParam(String todoParam) {
        this.todoParam = todoParam;
    }

    public String getOtherLangActionName() {
        return this.otherLangActionName;
    }

    public void setOtherLangActionName(String otherLangActionName) {
        this.otherLangActionName = otherLangActionName;
    }

    public String getOtherContent() {
        return this.otherContent;
    }

    public void setOtherContent(String otherContent) {
        this.otherContent = otherContent;
    }

    public String getOtherTitle() {
        return this.otherTitle;
    }

    public void setOtherTitle(String otherTitle) {
        this.otherTitle = otherTitle;
    }

    public String getOtherParam() {
        return this.otherParam;
    }

    public void setOtherParam(String otherParam) {
        this.otherParam = otherParam;
    }

    public String getLangType() {
        return this.langType;
    }

    public void setLangType(String langType) {
        this.langType = langType;
    }
}

