/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 */
package com.jiuqi.nr.formula.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.formula.dto.TaskLinkDimMappingDTO;
import com.jiuqi.nr.task.api.common.Constants;
import java.util.List;

public class TaskLinkDTO {
    private String key;
    private String formScheme;
    private String relatedFormScheme;
    private String currentTaskFormula;
    private String relatedTaskFormula;
    private String periodOffset;
    private String linkAlias;
    private String matching;
    private Integer configuration;
    private String specified;
    private String beginTime;
    private String endTime;
    private Integer matchingType;
    private String order;
    private Integer expressionType;
    private String level;
    @JsonIgnore
    private List<TaskLinkOrgMappingRule> taskLinkOrgRules;
    @JsonIgnore
    private List<TaskLinkDimMappingDTO> taskLinkDimRules;
    private Constants.DataStatus status = Constants.DataStatus.NONE;

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public String getRelatedFormScheme() {
        return this.relatedFormScheme;
    }

    public void setRelatedFormScheme(String relatedFormScheme) {
        this.relatedFormScheme = relatedFormScheme;
    }

    public String getCurrentTaskFormula() {
        return this.currentTaskFormula;
    }

    public void setCurrentTaskFormula(String currentTaskFormula) {
        this.currentTaskFormula = currentTaskFormula;
    }

    public String getRelatedTaskFormula() {
        return this.relatedTaskFormula;
    }

    public void setRelatedTaskFormula(String relatedTaskFormula) {
        this.relatedTaskFormula = relatedTaskFormula;
    }

    public String getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(String periodOffset) {
        this.periodOffset = periodOffset;
    }

    public String getLinkAlias() {
        return this.linkAlias;
    }

    public void setLinkAlias(String linkAlias) {
        this.linkAlias = linkAlias;
    }

    public String getMatching() {
        return this.matching;
    }

    public void setMatching(String matching) {
        this.matching = matching;
    }

    public Integer getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(Integer configuration) {
        this.configuration = configuration;
    }

    public String getSpecified() {
        return this.specified;
    }

    public void setSpecified(String specified) {
        this.specified = specified;
    }

    public String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public Integer getMatchingType() {
        return this.matchingType;
    }

    public void setMatchingType(Integer matchingType) {
        this.matchingType = matchingType;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getExpressionType() {
        return this.expressionType;
    }

    public void setExpressionType(Integer expressionType) {
        this.expressionType = expressionType;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Constants.DataStatus getStatus() {
        return this.status;
    }

    public void setStatus(Constants.DataStatus status) {
        this.status = status;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<TaskLinkOrgMappingRule> getTaskLinkOrgRules() {
        return this.taskLinkOrgRules;
    }

    public void setTaskLinkOrgRules(List<TaskLinkOrgMappingRule> taskLinkOrgRules) {
        this.taskLinkOrgRules = taskLinkOrgRules;
    }

    public List<TaskLinkDimMappingDTO> getTaskLinkDimRules() {
        return this.taskLinkDimRules;
    }

    public void setTaskLinkDimRules(List<TaskLinkDimMappingDTO> taskLinkDimRules) {
        this.taskLinkDimRules = taskLinkDimRules;
    }

    public static TaskLinkDTO defineToDto(TaskLinkDefine define) {
        if (define == null) {
            return null;
        }
        TaskLinkDTO taskLinkDTO = new TaskLinkDTO();
        taskLinkDTO.setKey(define.getKey());
        taskLinkDTO.setFormScheme(define.getCurrentFormSchemeKey());
        taskLinkDTO.setRelatedFormScheme(define.getRelatedFormSchemeKey());
        taskLinkDTO.setCurrentTaskFormula(define.getCurrentFormula());
        taskLinkDTO.setRelatedTaskFormula(define.getRelatedFormula());
        taskLinkDTO.setPeriodOffset(define.getPeriodOffset());
        taskLinkDTO.setLinkAlias(define.getLinkAlias());
        taskLinkDTO.setMatching(define.getMatching());
        taskLinkDTO.setConfiguration(define.getConfiguration().getValue());
        taskLinkDTO.setSpecified(define.getSpecified());
        taskLinkDTO.setBeginTime(define.getBeginTime());
        taskLinkDTO.setMatchingType(define.getMatchingType().getValue());
        taskLinkDTO.setOrder(define.getOrder());
        taskLinkDTO.setExpressionType(define.getExpressionType().getValue());
        taskLinkDTO.setEndTime(define.getEndTime());
        taskLinkDTO.setLevel(define.getOwnerLevelAndId());
        taskLinkDTO.setTaskLinkOrgRules(define.getOrgMappingRules());
        taskLinkDTO.setTaskLinkDimRules(TaskLinkDimMappingDTO.toMappings(define.getRelatedDims()));
        return taskLinkDTO;
    }
}

