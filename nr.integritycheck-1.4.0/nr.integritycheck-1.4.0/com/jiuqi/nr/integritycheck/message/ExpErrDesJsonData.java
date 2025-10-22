/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.message;

import com.jiuqi.nr.integritycheck.message.FormMappingMessage;
import com.jiuqi.nr.integritycheck.message.FormSchemeMappingMessage;
import com.jiuqi.nr.integritycheck.message.TaskMappingMessage;
import java.io.Serializable;
import java.util.List;

public class ExpErrDesJsonData
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String version;
    private TaskMappingMessage taskMappingMessage;
    private FormSchemeMappingMessage formSchemeMappingMessage;
    private List<FormMappingMessage> formMappingMessages;

    public ExpErrDesJsonData() {
    }

    public ExpErrDesJsonData(String version, TaskMappingMessage taskMappingMessage, FormSchemeMappingMessage formSchemeMappingMessage, List<FormMappingMessage> formMappingMessages) {
        this.version = version;
        this.taskMappingMessage = taskMappingMessage;
        this.formSchemeMappingMessage = formSchemeMappingMessage;
        this.formMappingMessages = formMappingMessages;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
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

