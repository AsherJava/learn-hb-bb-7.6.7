/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.common.FormulaSyntaxStyle
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.common.TaskType
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FillDateType
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 */
package com.jiuqi.nr.param.transfer.definition.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.common.FormulaSyntaxStyle;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.common.TaskType;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FillDateType;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TaskInfoDTO {
    private String measureUnit;
    private String fromPeriod;
    private String toPeriod;
    private int taskPeriodOffset;
    private String taskCode;
    private String taskFilePrefix;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String description;
    private TaskGatherType taskGatherType;
    private FormulaSyntaxStyle formulaSyntaxStyle;
    private String viewsInEFDC;
    private TaskType taskType;
    private String createUserName;
    private String createTime;
    private boolean efdcSwitch;
    private FillInAutomaticallyDue fillInAutomaticallyDue;
    private String dataScheme;
    private String dw;
    private String datetime;
    private String dims;
    private String entityViewID;
    private String filterExpression;
    private int fillingDateType = 0;
    private int fillingDateDays = 0;

    public int getFillingDateType() {
        return this.fillingDateType;
    }

    public void setFillingDateType(int fillingDateType) {
        this.fillingDateType = fillingDateType;
    }

    public int getFillingDateDays() {
        return this.fillingDateDays;
    }

    public void setFillingDateDays(int fillingDateDays) {
        this.fillingDateDays = fillingDateDays;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getFromPeriod() {
        return this.fromPeriod;
    }

    public void setFromPeriod(String fromPeriod) {
        this.fromPeriod = fromPeriod;
    }

    public String getToPeriod() {
        return this.toPeriod;
    }

    public void setToPeriod(String toPeriod) {
        this.toPeriod = toPeriod;
    }

    public int getTaskPeriodOffset() {
        return this.taskPeriodOffset;
    }

    public void setTaskPeriodOffset(int taskPeriodOffset) {
        this.taskPeriodOffset = taskPeriodOffset;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskFilePrefix() {
        return this.taskFilePrefix;
    }

    public void setTaskFilePrefix(String taskFilePrefix) {
        this.taskFilePrefix = taskFilePrefix;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getOwnerLevelAndId() {
        return this.ownerLevelAndId;
    }

    public void setOwnerLevelAndId(String ownerLevelAndId) {
        this.ownerLevelAndId = ownerLevelAndId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskGatherType getTaskGatherType() {
        return this.taskGatherType;
    }

    public void setTaskGatherType(TaskGatherType taskGatherType) {
        this.taskGatherType = taskGatherType;
    }

    public FormulaSyntaxStyle getFormulaSyntaxStyle() {
        return this.formulaSyntaxStyle;
    }

    public void setFormulaSyntaxStyle(FormulaSyntaxStyle formulaSyntaxStyle) {
        this.formulaSyntaxStyle = formulaSyntaxStyle;
    }

    public String getViewsInEFDC() {
        return this.viewsInEFDC;
    }

    public void setViewsInEFDC(String viewsInEFDC) {
        this.viewsInEFDC = viewsInEFDC;
    }

    public TaskType getTaskType() {
        return this.taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getCreateUserName() {
        return this.createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public boolean isEfdcSwitch() {
        return this.efdcSwitch;
    }

    public void setEfdcSwitch(boolean efdcSwitch) {
        this.efdcSwitch = efdcSwitch;
    }

    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.fillInAutomaticallyDue;
    }

    public void setFillInAutomaticallyDue(FillInAutomaticallyDue fillInAutomaticallyDue) {
        this.fillInAutomaticallyDue = fillInAutomaticallyDue;
    }

    public String getDataScheme() {
        return this.dataScheme;
    }

    public void setDataScheme(String dataScheme) {
        this.dataScheme = dataScheme;
    }

    public String getDw() {
        return this.dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDims() {
        return this.dims;
    }

    public void setDims(String dims) {
        this.dims = dims;
    }

    public String getFilterExpression() {
        return this.filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        this.filterExpression = filterExpression;
    }

    public String getEntityViewID() {
        return this.entityViewID;
    }

    public void setEntityViewID(String entityViewID) {
        this.entityViewID = entityViewID;
    }

    public static TaskInfoDTO valueOf(TaskDefine taskDefine) {
        if (taskDefine == null) {
            return null;
        }
        TaskInfoDTO taskInfo = new TaskInfoDTO();
        taskInfo.setMeasureUnit(taskDefine.getMeasureUnit());
        taskInfo.setFromPeriod(taskDefine.getFromPeriod());
        taskInfo.setToPeriod(taskDefine.getToPeriod());
        taskInfo.setTaskPeriodOffset(taskDefine.getTaskPeriodOffset());
        taskInfo.setTaskCode(taskDefine.getTaskCode());
        taskInfo.setTaskFilePrefix(taskDefine.getTaskFilePrefix());
        taskInfo.setKey(taskDefine.getKey());
        taskInfo.setTitle(taskDefine.getTitle());
        taskInfo.setOrder(taskDefine.getOrder());
        taskInfo.setVersion(taskDefine.getVersion());
        taskInfo.setOwnerLevelAndId(taskDefine.getOwnerLevelAndId());
        taskInfo.setUpdateTime(taskDefine.getUpdateTime());
        taskInfo.setDescription(taskDefine.getDescription());
        taskInfo.setTaskGatherType(taskDefine.getTaskGatherType());
        taskInfo.setFormulaSyntaxStyle(taskDefine.getFormulaSyntaxStyle());
        taskInfo.setViewsInEFDC(taskDefine.getEntityViewsInEFDC());
        taskInfo.setTaskType(taskDefine.getTaskType());
        taskInfo.setCreateUserName(taskDefine.getCreateUserName());
        taskInfo.setCreateTime(taskDefine.getCreateTime());
        taskInfo.setEfdcSwitch(taskDefine.getEfdcSwitch());
        taskInfo.setFillInAutomaticallyDue(taskDefine.getFillInAutomaticallyDue());
        taskInfo.setDataScheme(taskDefine.getDataScheme());
        taskInfo.setDw(taskDefine.getDw());
        taskInfo.setDatetime(taskDefine.getDateTime());
        taskInfo.setDims(taskDefine.getDims());
        taskInfo.setEntityViewID(taskDefine.getFilterTemplate());
        taskInfo.setFilterExpression(taskDefine.getFilterExpression());
        taskInfo.setFillingDateType(taskDefine.getFillingDateType().getValue());
        taskInfo.setFillingDateDays(taskDefine.getFillingDateDays());
        return taskInfo;
    }

    public void value2Define(DesignTaskDefine taskDefine) {
        taskDefine.setMeasureUnit(this.measureUnit);
        taskDefine.setFromPeriod(this.fromPeriod);
        taskDefine.setToPeriod(this.toPeriod);
        taskDefine.setTaskPeriodOffset(this.taskPeriodOffset);
        taskDefine.setTaskCode(this.taskCode);
        taskDefine.setTaskFilePrefix(this.taskFilePrefix);
        taskDefine.setKey(this.key);
        taskDefine.setTitle(this.title);
        taskDefine.setOrder(this.order);
        taskDefine.setVersion(this.version);
        taskDefine.setOwnerLevelAndId(this.ownerLevelAndId);
        taskDefine.setUpdateTime(this.updateTime);
        taskDefine.setDescription(this.description);
        taskDefine.setTaskGatherType(this.taskGatherType);
        taskDefine.setFormulaSyntaxStyle(this.formulaSyntaxStyle);
        taskDefine.setEntityViewsInEFDC(this.viewsInEFDC);
        taskDefine.setTaskType(this.taskType);
        taskDefine.setCreateUserName(this.createUserName);
        taskDefine.setCreateTime(this.createTime);
        taskDefine.setEfdcSwitch(this.efdcSwitch);
        taskDefine.setFillInAutomaticallyDue(this.fillInAutomaticallyDue);
        taskDefine.setDataScheme(this.dataScheme);
        taskDefine.setDw(this.dw);
        taskDefine.setDateTime(this.datetime);
        taskDefine.setDims(this.dims);
        if (this.entityViewID != null) {
            taskDefine.setFilterTemplate(this.entityViewID);
            taskDefine.setFilterExpression(null);
        } else {
            taskDefine.setFilterExpression(this.filterExpression);
        }
        taskDefine.setFillingDateType(FillDateType.fromType((int)this.fillingDateType));
        taskDefine.setFillingDateDays(this.fillingDateDays);
    }
}

