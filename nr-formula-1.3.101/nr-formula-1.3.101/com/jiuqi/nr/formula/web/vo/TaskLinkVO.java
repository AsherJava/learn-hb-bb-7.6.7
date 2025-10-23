/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.PeriodMatchingType
 *  com.jiuqi.nr.definition.common.TaskLinkExpressionType
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 */
package com.jiuqi.nr.formula.web.vo;

import com.jiuqi.nr.definition.common.PeriodMatchingType;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.formula.dto.TaskLinkDTO;
import com.jiuqi.nr.formula.dto.TaskLinkDimMappingDTO;
import com.jiuqi.nr.formula.web.vo.TaskLinkDimMappingVO;
import com.jiuqi.nr.formula.web.vo.TaskLinkOrgMappingVO;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class TaskLinkVO
extends TaskLinkDTO {
    private String currentPeriodType;
    private String referFormSchemeTitle;
    private String referTaskTitle;
    private String referPeriodType;
    private String referTaskKey;
    private boolean current13Y;
    private boolean deleted = false;
    private List<TaskLinkOrgMappingVO> taskLinkOrgMapping;
    private List<TaskLinkDimMappingVO> taskLinkDimMapping;

    public String getCurrentPeriodType() {
        return this.currentPeriodType;
    }

    public void setCurrentPeriodType(String currentPeriodType) {
        this.currentPeriodType = currentPeriodType;
    }

    public String getReferFormSchemeTitle() {
        return this.referFormSchemeTitle;
    }

    public void setReferFormSchemeTitle(String referFormSchemeTitle) {
        this.referFormSchemeTitle = referFormSchemeTitle;
    }

    public String getReferTaskTitle() {
        return this.referTaskTitle;
    }

    public void setReferTaskTitle(String referTaskTitle) {
        this.referTaskTitle = referTaskTitle;
    }

    public String getReferPeriodType() {
        return this.referPeriodType;
    }

    public void setReferPeriodType(String referPeriodType) {
        this.referPeriodType = referPeriodType;
    }

    public String getReferTaskKey() {
        return this.referTaskKey;
    }

    public void setReferTaskKey(String referTaskKey) {
        this.referTaskKey = referTaskKey;
    }

    public boolean isCurrent13Y() {
        return this.current13Y;
    }

    public void setCurrent13Y(boolean current13Y) {
        this.current13Y = current13Y;
    }

    public static TaskLinkVO getInstance(TaskLinkDTO linkDTO) {
        TaskLinkVO taskLinkVO = new TaskLinkVO();
        BeanUtils.copyProperties(linkDTO, taskLinkVO);
        return taskLinkVO;
    }

    public List<TaskLinkOrgMappingVO> getTaskLinkOrgMapping() {
        return this.taskLinkOrgMapping;
    }

    public void setTaskLinkOrgMapping(List<TaskLinkOrgMappingVO> taskLinkOrgMapping) {
        this.taskLinkOrgMapping = taskLinkOrgMapping;
    }

    public List<TaskLinkDimMappingVO> getTaskLinkDimMapping() {
        return this.taskLinkDimMapping;
    }

    public void setTaskLinkDimMapping(List<TaskLinkDimMappingVO> taskLinkDimMapping) {
        this.taskLinkDimMapping = taskLinkDimMapping;
    }

    public static DesignTaskLinkDefine toDefine(TaskLinkVO taskLinkDTO, Supplier<DesignTaskLinkDefine> supplier) {
        DesignTaskLinkDefine taskLinkDefine = supplier.get();
        if (taskLinkDefine == null || taskLinkDTO == null) {
            return null;
        }
        taskLinkDefine.setKey(taskLinkDTO.getKey());
        taskLinkDefine.setOrder(taskLinkDTO.getOrder());
        taskLinkDefine.setLinkAlias(taskLinkDTO.getLinkAlias());
        taskLinkDefine.setCurrentFormSchemeKey(taskLinkDTO.getFormScheme());
        taskLinkDefine.setRelatedFormSchemeKey(taskLinkDTO.getRelatedFormScheme());
        taskLinkDefine.setPeriodOffset(taskLinkDTO.getPeriodOffset());
        taskLinkDefine.setCurrentTaskFormula(taskLinkDTO.getCurrentTaskFormula());
        taskLinkDefine.setRelatedTaskFormula(taskLinkDTO.getRelatedTaskFormula());
        taskLinkDefine.setMatching(taskLinkDTO.getMatching());
        if (taskLinkDTO.getConfiguration() != null) {
            taskLinkDefine.setConfiguration(PeriodMatchingType.forValue((int)taskLinkDTO.getConfiguration()));
        }
        taskLinkDefine.setSpecified(taskLinkDTO.getSpecified());
        taskLinkDefine.setTheoffset(taskLinkDTO.getSpecified());
        taskLinkDefine.setBeginTime(taskLinkDTO.getBeginTime());
        taskLinkDefine.setEndTime(taskLinkDTO.getEndTime());
        if (taskLinkDTO.getMatchingType() != null) {
            taskLinkDefine.setMatchingType(TaskLinkMatchingType.forValue((int)taskLinkDTO.getMatchingType()));
        }
        if (taskLinkDTO.getExpressionType() != null) {
            taskLinkDefine.setExpressionType(TaskLinkExpressionType.forValue((int)taskLinkDTO.getExpressionType()));
        }
        taskLinkDefine.setDescription(taskLinkDTO.getReferTaskTitle());
        taskLinkDefine.setTitle(taskLinkDTO.getReferFormSchemeTitle());
        taskLinkDefine.setIsHidden(1);
        if (!CollectionUtils.isEmpty(taskLinkDTO.getTaskLinkOrgMapping())) {
            taskLinkDefine.setOrgMappingRule(taskLinkDTO.getTaskLinkOrgMapping().stream().map(TaskLinkOrgMappingVO::toMappingRule).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(taskLinkDTO.getTaskLinkDimMapping())) {
            taskLinkDefine.setDimMappingRule(taskLinkDTO.revertRelatedDims(taskLinkDTO.getTaskLinkDimMapping()));
        }
        return taskLinkDefine;
    }

    private String revertRelatedDims(List<TaskLinkDimMappingVO> dimMapping) {
        StringBuffer str = new StringBuffer();
        for (TaskLinkDimMappingDTO taskLinkDimMappingDTO : dimMapping) {
            str.append(taskLinkDimMappingDTO.toString());
        }
        return str.toString();
    }

    public static DesignTaskLinkDefine[] toDefine(List<TaskLinkVO> taskLinks, Supplier<DesignTaskLinkDefine> supplier) {
        if (CollectionUtils.isEmpty(taskLinks)) {
            return null;
        }
        return (DesignTaskLinkDefine[])taskLinks.stream().map(taskLinkDTO -> TaskLinkVO.toDefine(taskLinkDTO, supplier)).toArray(DesignTaskLinkDefine[]::new);
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

