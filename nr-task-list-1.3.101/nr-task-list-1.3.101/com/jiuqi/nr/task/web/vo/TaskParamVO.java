/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.web.vo;

import com.jiuqi.nr.task.dto.TaskDTO;
import com.jiuqi.nr.task.web.vo.TaskDimensionVO;
import com.jiuqi.nr.task.web.vo.TaskOrgListVO;
import java.util.List;

public class TaskParamVO
extends TaskDTO {
    private String dataScheme;
    private String dataSchemeTitle;
    private TaskDimensionVO dw;
    private List<TaskDimensionVO> orgDimScope;
    private List<TaskDimensionVO> dims;
    private String periodType;
    private Integer periodOffset;
    private String starterPeriod;
    private String endPeriod;
    private String dateTime;
    private List<TaskOrgListVO> orgList;

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getDataSchemeTitle() {
        return this.dataSchemeTitle;
    }

    public void setDataSchemeTitle(String dataSchemeTitle) {
        this.dataSchemeTitle = dataSchemeTitle;
    }

    public TaskDimensionVO getDw() {
        return this.dw;
    }

    public void setDw(TaskDimensionVO dw) {
        this.dw = dw;
    }

    public List<TaskDimensionVO> getOrgDimScope() {
        return this.orgDimScope;
    }

    public void setOrgDimScope(List<TaskDimensionVO> orgDimScope) {
        this.orgDimScope = orgDimScope;
    }

    public List<TaskDimensionVO> getDims() {
        return this.dims;
    }

    public void setDims(List<TaskDimensionVO> dims) {
        this.dims = dims;
    }

    public String getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(String periodType) {
        this.periodType = periodType;
    }

    public Integer getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(Integer periodOffset) {
        this.periodOffset = periodOffset;
    }

    public String getStarterPeriod() {
        return this.starterPeriod;
    }

    public void setStarterPeriod(String starterPeriod) {
        this.starterPeriod = starterPeriod;
    }

    public String getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public List<TaskOrgListVO> getOrgList() {
        return this.orgList;
    }

    public void setOrgList(List<TaskOrgListVO> orgList) {
        this.orgList = orgList;
    }
}

