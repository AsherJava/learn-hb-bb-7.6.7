/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.message.domain;

import com.jiuqi.nr.workflow2.settings.message.domain.MessageSampleDO;
import java.util.Calendar;

public class MessageSampleDOImpl
implements MessageSampleDO {
    private String id;
    private String type;
    private String actionCode;
    private String title;
    private String subject;
    private String content;
    private Calendar createTime;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public Calendar getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }
}

