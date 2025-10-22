/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.message;

import com.jiuqi.nr.annotation.message.FormMappingMessage;
import com.jiuqi.nr.annotation.message.FormSchemeMappingMessage;
import com.jiuqi.nr.annotation.message.TaskMappingMessage;
import java.io.Serializable;
import java.util.List;

public class ExpAnnotationJsonData
implements Serializable {
    private static final long serialVersionUID = 1L;
    private TaskMappingMessage taskMappingMessage;
    private FormSchemeMappingMessage formSchemeMappingMessage;
    private List<FormMappingMessage> formMappingMessages;

    public ExpAnnotationJsonData() {
    }

    public ExpAnnotationJsonData(TaskMappingMessage taskMappingMessage, FormSchemeMappingMessage formSchemeMappingMessage, List<FormMappingMessage> formMappingMessages) {
        this.taskMappingMessage = taskMappingMessage;
        this.formSchemeMappingMessage = formSchemeMappingMessage;
        this.formMappingMessages = formMappingMessages;
    }

    public TaskMappingMessage getTaskMappingMessage() {
        return this.taskMappingMessage;
    }

    public void setTaskMappingMessage(TaskMappingMessage taskMappingMessage) {
        this.taskMappingMessage = taskMappingMessage;
    }

    public FormSchemeMappingMessage getFormSchemeMappingMessage() {
        return this.formSchemeMappingMessage;
    }

    public void setFormSchemeMappingMessage(FormSchemeMappingMessage formSchemeMappingMessage) {
        this.formSchemeMappingMessage = formSchemeMappingMessage;
    }

    public List<FormMappingMessage> getFormMappingMessges() {
        return this.formMappingMessages;
    }

    public void setFormMappingMessges(List<FormMappingMessage> formMappingMessages) {
        this.formMappingMessages = formMappingMessages;
    }
}

