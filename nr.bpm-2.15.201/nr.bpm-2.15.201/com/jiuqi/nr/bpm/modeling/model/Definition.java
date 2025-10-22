/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.jiuqi.nr.bpm.modeling.model.Process;
import com.jiuqi.nr.bpm.modeling.model.ProcessElement;
import org.springframework.util.Assert;

public class Definition
extends ProcessElement {
    public Definition() {
        super("definitions");
        this.addDefaultPropert();
    }

    private void addDefaultPropert() {
        super.setProperty("xmlns", "http://www.omg.org/spec/BPMN/20100524/MODEL");
        super.setProperty("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        super.setProperty("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
        super.setProperty("xmlns:activiti", "http://activiti.org/bpmn");
        super.setProperty("xmlns:bpmndi", "http://www.omg.org/spec/BPMN/20100524/DI");
        super.setProperty("xmlns:omgdc", "http://www.omg.org/spec/DD/20100524/DC");
        super.setProperty("xmlns:omgdi", "http://www.omg.org/spec/DD/20100524/DI");
        super.setProperty("typeLanguage", "http://www.w3.org/2001/XMLSchema");
        super.setProperty("expressionLanguage", "http://www.w3.org/1999/XPath");
        super.setProperty("targetNamespace", "http://www.activiti.org/processdef");
    }

    public void addProcess(Process process) {
        Assert.notNull((Object)process, "'process' must not be null.");
        super.appendChild(process);
    }

    @Override
    public void buildBPMNDocument(StringBuilder builder) {
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        super.buildBPMNDocument(builder);
    }
}

