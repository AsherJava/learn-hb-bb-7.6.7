/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.domain.commrule;

import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import java.util.List;
import java.util.UUID;

public class BizRuleDTO {
    private UUID id;
    private String name;
    private UUID objectId;
    private String objectType;
    private String propertyType;
    private FormulaType formulaType;
    private String title;
    private String triggerType;
    private String expression;
    private String checkState;
    private String checkMessage;
    private String remark;
    private int masterLength;
    private List<Integer> masterIndex;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getObjectId() {
        return this.objectId;
    }

    public void setObjectId(UUID objectId) {
        this.objectId = objectId;
    }

    public FormulaType getFormulaType() {
        return this.formulaType;
    }

    public void setFormulaType(FormulaType formulaType) {
        this.formulaType = formulaType;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectType() {
        return this.objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getPropertyType() {
        return this.propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTriggerType() {
        return this.triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getCheckState() {
        return this.checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCheckMessage() {
        return this.checkMessage;
    }

    public void setCheckMessage(String checkMessage) {
        this.checkMessage = checkMessage;
    }

    public int getMasterLength() {
        return this.masterLength;
    }

    public void setMasterLength(int masterLength) {
        this.masterLength = masterLength;
    }

    public List<Integer> getMasterIndex() {
        return this.masterIndex;
    }

    public void setMasterIndex(List<Integer> masterIndex) {
        this.masterIndex = masterIndex;
    }
}

