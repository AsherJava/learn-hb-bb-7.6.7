/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.message;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.List;
import org.springframework.context.ApplicationEvent;

public class NrTaskDeleteEvent
extends ApplicationEvent {
    private String messageId;
    private String userId;
    private String taskKey;
    private TaskDefine taskdefine;
    private List<FormSchemeDefine> formSchemeDefines;

    public NrTaskDeleteEvent(Object source, String messageId, String userId) {
        super(source);
        this.messageId = messageId;
        this.userId = userId;
    }

    public NrTaskDeleteEvent(Object source) {
        super(source);
    }

    public TaskDefine getTaskdefine() {
        return this.taskdefine;
    }

    public void setTaskdefine(TaskDefine taskdefine) {
        this.taskdefine = taskdefine;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public List<FormSchemeDefine> getFormSchemeDefines() {
        return this.formSchemeDefines;
    }

    public void setFormSchemeDefines(List<FormSchemeDefine> formSchemeDefines) {
        this.formSchemeDefines = formSchemeDefines;
    }
}

