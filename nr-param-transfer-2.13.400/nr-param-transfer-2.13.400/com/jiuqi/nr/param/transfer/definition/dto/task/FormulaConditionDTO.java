/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition
 */
package com.jiuqi.nr.param.transfer.definition.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaCondition;
import java.util.Date;

public class FormulaConditionDTO {
    private String key;
    private String code;
    private String title;
    private String formulaCondition;
    private String order;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String taskKey;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormulaCondition() {
        return this.formulaCondition;
    }

    public void setFormulaCondition(String formulaCondition) {
        this.formulaCondition = formulaCondition;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public static FormulaConditionDTO toDto(DesignFormulaCondition formulaCondition) {
        if (formulaCondition == null) {
            return null;
        }
        FormulaConditionDTO formulaConditionDTO = new FormulaConditionDTO();
        formulaConditionDTO.setKey(formulaCondition.getKey());
        formulaConditionDTO.setCode(formulaCondition.getCode());
        formulaConditionDTO.setTitle(formulaCondition.getTitle());
        formulaConditionDTO.setFormulaCondition(formulaCondition.getFormulaCondition());
        formulaConditionDTO.setOrder(formulaCondition.getOrder());
        formulaConditionDTO.setUpdateTime(formulaCondition.getUpdateTime());
        formulaConditionDTO.setTaskKey(formulaCondition.getTaskKey());
        return formulaConditionDTO;
    }

    public void toDefine(DesignFormulaCondition designFormulaCondition) {
        designFormulaCondition.setKey(this.key);
        designFormulaCondition.setCode(this.code);
        designFormulaCondition.setTitle(this.title);
        designFormulaCondition.setFormulaCondition(this.formulaCondition);
        designFormulaCondition.setOrder(this.order);
        designFormulaCondition.setUpdateTime(this.updateTime);
        designFormulaCondition.setTaskKey(this.taskKey);
    }
}

