/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.JsonNode
 *  com.fasterxml.jackson.databind.node.ArrayNode
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.workflow.domain.detection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jiuqi.va.mapper.domain.TenantDO;

public class WorkflowDetectionElement
extends TenantDO {
    private String elementId;
    private String elementType;
    private String elementName;
    private ArrayNode outgoingList;
    private boolean submitNode;
    private String elementStencilType;
    private JsonNode conditionSequenceFlow;
    private String conditionView;
    private String multiInstanceType;
    private String multiInstanceCollection;
    private ArrayNode userTaskAssignment;
    private String selectNextApprovalNode;
    private String skipEmptyParticipant;
    private String autoAgree;
    private String pgwCode;
    private String subCode;
    private String subStartId;

    public String getSubCode() {
        return this.subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getSubStartId() {
        return this.subStartId;
    }

    public void setSubStartId(String subStartId) {
        this.subStartId = subStartId;
    }

    public String getPgwCode() {
        return this.pgwCode;
    }

    public void setPgwCode(String pgwCode) {
        this.pgwCode = pgwCode;
    }

    public boolean isSubmitNode() {
        return this.submitNode;
    }

    public void setSubmitNode(boolean submitNode) {
        this.submitNode = submitNode;
    }

    public String getElementId() {
        return this.elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public String getElementType() {
        return this.elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getElementName() {
        return this.elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public ArrayNode getOutgoingList() {
        return this.outgoingList;
    }

    public void setOutgoingList(ArrayNode outgoingList) {
        this.outgoingList = outgoingList;
    }

    public String getElementStencilType() {
        return this.elementStencilType;
    }

    public void setElementStencilType(String elementStencilType) {
        this.elementStencilType = elementStencilType;
    }

    public JsonNode getConditionSequenceFlow() {
        return this.conditionSequenceFlow;
    }

    public void setConditionSequenceFlow(JsonNode conditionSequenceFlow) {
        this.conditionSequenceFlow = conditionSequenceFlow;
    }

    public String getMultiInstanceType() {
        return this.multiInstanceType;
    }

    public void setMultiInstanceType(String multiInstanceType) {
        this.multiInstanceType = multiInstanceType;
    }

    public String getMultiInstanceCollection() {
        return this.multiInstanceCollection;
    }

    public void setMultiInstanceCollection(String multiInstanceCollection) {
        this.multiInstanceCollection = multiInstanceCollection;
    }

    public ArrayNode getUserTaskAssignment() {
        return this.userTaskAssignment;
    }

    public void setUserTaskAssignment(ArrayNode userTaskAssignment) {
        this.userTaskAssignment = userTaskAssignment;
    }

    public String getSelectNextApprovalNode() {
        return this.selectNextApprovalNode;
    }

    public void setSelectNextApprovalNode(String selectNextApprovalNode) {
        this.selectNextApprovalNode = selectNextApprovalNode;
    }

    public String getSkipEmptyParticipant() {
        return this.skipEmptyParticipant;
    }

    public void setSkipEmptyParticipant(String skipEmptyParticipant) {
        this.skipEmptyParticipant = skipEmptyParticipant;
    }

    public String getAutoAgree() {
        return this.autoAgree;
    }

    public void setAutoAgree(String autoAgree) {
        this.autoAgree = autoAgree;
    }

    public String getConditionView() {
        return this.conditionView;
    }

    public void setConditionView(String conditionView) {
        this.conditionView = conditionView;
    }
}

