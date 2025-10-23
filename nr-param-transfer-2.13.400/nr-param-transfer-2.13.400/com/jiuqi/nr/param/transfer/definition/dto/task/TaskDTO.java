/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine
 *  com.jiuqi.nr.definition.option.core.TaskOption
 */
package com.jiuqi.nr.param.transfer.definition.dto.task;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskFlowsDefine;
import com.jiuqi.nr.definition.option.core.TaskOption;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.EntityViewDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.SchemePeriodLinkDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.DimensionFilterDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.FormulaConditionDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.TaskInfoDTO;
import com.jiuqi.nr.param.transfer.definition.dto.task.TaskOrgLinkDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TaskDTO
extends BaseDTO {
    private TaskInfoDTO taskInfo;
    private byte[] taskFlowsBigData;
    private List<DimensionFilterDTO> dimensionFilters;
    private List<TaskOption> taskOptions;
    private List<SchemePeriodLinkDTO> periodLinks;
    private List<EntityViewDTO> entityViews;
    private Map<String, byte[]> params = null;
    private List<FormulaConditionDTO> formulaConditions;
    private DesParamLanguageDTO desParamLanguageDTO;
    private List<TaskOrgLinkDTO> taskOrgLinkDTOs;

    public TaskInfoDTO getTaskInfo() {
        return this.taskInfo;
    }

    public void setTaskInfo(TaskInfoDTO taskInfo) {
        this.taskInfo = taskInfo;
    }

    @JsonIgnore
    public DesignTaskFlowsDefine getTaskFlowsDefine() {
        if (this.taskFlowsBigData == null) {
            return null;
        }
        return DesignTaskFlowsDefine.bytesToTaskFlowsData((byte[])this.taskFlowsBigData);
    }

    public void setTaskFlowsDefine(DesignTaskFlowsDefine taskFlowsDefine) {
        if (taskFlowsDefine != null) {
            this.taskFlowsBigData = DesignTaskFlowsDefine.designTaskFlowsDefineToBytes((TaskFlowsDefine)taskFlowsDefine);
        }
    }

    public byte[] getTaskFlowsBigData() {
        return this.taskFlowsBigData;
    }

    public void setTaskFlowsBigData(byte[] taskFlowsBigData) {
        this.taskFlowsBigData = taskFlowsBigData;
    }

    public List<TaskOption> getTaskOptions() {
        return this.taskOptions;
    }

    public void setTaskOptions(List<TaskOption> taskOptions) {
        this.taskOptions = taskOptions;
    }

    public Map<String, byte[]> getParams() {
        return this.params;
    }

    public void setParams(Map<String, byte[]> params) {
        this.params = params;
    }

    public List<SchemePeriodLinkDTO> getPeriodLinks() {
        return this.periodLinks;
    }

    public void setPeriodLinks(List<SchemePeriodLinkDTO> periodLinks) {
        this.periodLinks = periodLinks;
    }

    public static TaskDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (TaskDTO)objectMapper.readValue(bytes, TaskDTO.class);
    }

    public List<DimensionFilterDTO> getDimensionFilters() {
        return this.dimensionFilters;
    }

    public void setDimensionFilters(List<DimensionFilterDTO> dimensionFilters) {
        this.dimensionFilters = dimensionFilters;
    }

    public List<EntityViewDTO> getEntityViews() {
        return this.entityViews;
    }

    public void setEntityViews(List<EntityViewDTO> entityViews) {
        this.entityViews = entityViews;
    }

    public List<FormulaConditionDTO> getFormulaConditions() {
        if (this.formulaConditions == null) {
            this.formulaConditions = new ArrayList<FormulaConditionDTO>();
        }
        return this.formulaConditions;
    }

    public void setFormulaConditions(List<FormulaConditionDTO> formulaConditions) {
        this.formulaConditions = formulaConditions;
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }

    public List<TaskOrgLinkDTO> getTaskOrgLinkDTOs() {
        return this.taskOrgLinkDTOs;
    }

    public void setTaskOrgLinkDTOs(List<TaskOrgLinkDTO> taskOrgLinkDTOs) {
        this.taskOrgLinkDTOs = taskOrgLinkDTOs;
    }
}

