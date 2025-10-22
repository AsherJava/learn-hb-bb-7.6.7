/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.modeling;

import com.jiuqi.nr.bpm.modeling.model.ConditionExpression;
import com.jiuqi.nr.bpm.modeling.model.Definition;
import com.jiuqi.nr.bpm.modeling.model.EndEvent;
import com.jiuqi.nr.bpm.modeling.model.ExtensionElements;
import com.jiuqi.nr.bpm.modeling.model.FlowableProcessElement;
import com.jiuqi.nr.bpm.modeling.model.Process;
import com.jiuqi.nr.bpm.modeling.model.SequenceFlow;
import com.jiuqi.nr.bpm.modeling.model.StartEvent;
import com.jiuqi.nr.bpm.modeling.model.UserTask;
import org.springframework.util.Assert;

public abstract class ProcessBuilder {
    private Definition definition = new Definition();
    private Process process;
    private int nextSequenceFlowId;

    public ProcessBuilder(String processId) {
        this.process = new Process(processId);
        this.definition.addProcess(this.process);
    }

    public abstract ProcessBuilder build();

    public String getProcessId() {
        return this.process.getId();
    }

    public String getBpmnDocument() {
        return this.buildBpmnDocument();
    }

    protected void addStart(StartEvent startEvent) {
        this.process.addElement(startEvent);
    }

    protected void addEnd(EndEvent endEvent) {
        this.process.addElement(endEvent);
    }

    protected void addExtensionElements(ExtensionElements extensionElements) {
        this.process.addElement(extensionElements);
    }

    protected void addUserTask(UserTask userTask) {
        this.process.addElement(userTask);
    }

    protected void addSequenceFlow(SequenceFlow sequenceFlow) {
        this.process.addElement(sequenceFlow);
    }

    protected void addSequenceFlow(FlowableProcessElement sourceRef, FlowableProcessElement targetRef) {
        SequenceFlow sequenceFlow = new SequenceFlow(this.getNextFlowId(), sourceRef, targetRef);
        this.addSequenceFlow(sequenceFlow);
    }

    protected void addSequenceFlowDefault(FlowableProcessElement sourceRef, FlowableProcessElement targetRef) {
        SequenceFlow sequenceFlow = new SequenceFlow(this.getNextFlowId(), sourceRef, targetRef);
        this.addSequenceFlow(sequenceFlow);
        sourceRef.setDefaultFlow(sequenceFlow);
    }

    protected void addSequenceFlowConditional(FlowableProcessElement sourceRef, FlowableProcessElement targetRef, ConditionExpression condition) {
        SequenceFlow sequenceFlow = new SequenceFlow(this.getNextFlowId(), sourceRef, targetRef);
        sequenceFlow.setCondition(condition);
        this.addSequenceFlow(sequenceFlow);
    }

    protected String buildBpmnDocument() {
        StringBuilder documentBuilder = new StringBuilder();
        this.definition.buildBPMNDocument(documentBuilder);
        return documentBuilder.toString();
    }

    protected String getNextFlowId() {
        return "sf" + this.nextSequenceFlowId++;
    }

    protected String wrappingVariable(String varName) {
        Assert.notNull((Object)varName, "'varName' must not be null.");
        Assert.isTrue(!varName.contains("${") && !varName.contains("}"), "variable name can not contains ${ or }.");
        return String.format("%s%s%s", "${", varName, "}");
    }
}

