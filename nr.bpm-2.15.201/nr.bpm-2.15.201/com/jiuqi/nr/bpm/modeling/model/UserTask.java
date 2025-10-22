/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;
import com.jiuqi.nr.bpm.modeling.model.ExtensionElements;
import com.jiuqi.nr.bpm.modeling.model.FlowableProcessElement;
import com.jiuqi.nr.bpm.modeling.model.MultiInstanceLoop;
import com.jiuqi.nr.bpm.modeling.model.ProcessElement;
import org.springframework.util.Assert;

public class UserTask
extends FlowableProcessElement {
    private ExtensionElements extensionElements;
    private MultiInstanceLoop multiInstanceLoop;

    public UserTask(String id) {
        super("userTask", id);
    }

    public void setName(String name) {
        super.setProperty("name", name);
    }

    public void setCandidateUsers(String variable) {
        super.setProperty("activiti:candidateUsers", String.format("${%s}", variable));
    }

    public void setCandidateGroups(String variable) {
        super.setProperty("activiti:candidateGroups", String.format("${%s}", variable));
    }

    public void setExtensions(ExtensionElements extensionElements) {
        Assert.notNull((Object)extensionElements, "'extensionElements' must not be null.");
        super.appendChild(extensionElements);
        this.extensionElements = extensionElements;
    }

    public void addExtension(ActivitiExtension extension) {
        if (this.extensionElements == null) {
            this.setExtensions(new ExtensionElements());
        }
        this.extensionElements.addExtension(extension);
    }

    public void setMultiInstanceLoop(MultiInstanceLoop multiInstanceLoop) {
        this.multiInstanceLoop = multiInstanceLoop;
    }

    public void addMultiInstanceLoop(ProcessElement extension) {
        this.multiInstanceLoop.addExtension(extension);
        super.appendChild(this.multiInstanceLoop);
    }
}

