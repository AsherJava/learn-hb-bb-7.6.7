/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 */
package com.jiuqi.nr.param.transfer.definition.dto.formscheme;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormSchemeInfoDTO {
    private String taskKey;
    private String formSchemeCode;
    private PeriodType periodType;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String description;
    private String taskPrefix;
    private String filePrefix;
    private int periodOffset;
    private String measureUnit;
    private FillInAutomaticallyDue fillInAutomaticallyDue;
    private String dw;
    private String datetime;
    private String dims;
    private String filterExpression;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeCode() {
        return this.formSchemeCode;
    }

    public void setFormSchemeCode(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
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

    public String getTaskPrefix() {
        return this.taskPrefix;
    }

    public void setTaskPrefix(String taskPrefix) {
        this.taskPrefix = taskPrefix;
    }

    public String getFilePrefix() {
        return this.filePrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public int getPeriodOffset() {
        return this.periodOffset;
    }

    public void setPeriodOffset(int periodOffset) {
        this.periodOffset = periodOffset;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public FillInAutomaticallyDue getFillInAutomaticallyDue() {
        return this.fillInAutomaticallyDue;
    }

    public void setFillInAutomaticallyDue(FillInAutomaticallyDue fillInAutomaticallyDue) {
        this.fillInAutomaticallyDue = fillInAutomaticallyDue;
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

    public static FormSchemeInfoDTO valueOf(FormSchemeDefine formSchemeDefine) {
        if (formSchemeDefine == null) {
            return null;
        }
        FormSchemeInfoDTO taskInfo = new FormSchemeInfoDTO();
        taskInfo.setTaskKey(formSchemeDefine.getTaskKey());
        taskInfo.setFormSchemeCode(formSchemeDefine.getFormSchemeCode());
        taskInfo.setPeriodType(formSchemeDefine.getPeriodType());
        taskInfo.setKey(formSchemeDefine.getKey());
        taskInfo.setTitle(formSchemeDefine.getTitle());
        taskInfo.setOrder(formSchemeDefine.getOrder());
        taskInfo.setVersion(formSchemeDefine.getVersion());
        taskInfo.setOwnerLevelAndId(formSchemeDefine.getOwnerLevelAndId());
        taskInfo.setUpdateTime(formSchemeDefine.getUpdateTime());
        taskInfo.setDescription(formSchemeDefine.getDescription());
        taskInfo.setTaskPrefix(formSchemeDefine.getTaskPrefix());
        taskInfo.setFilePrefix(formSchemeDefine.getFilePrefix());
        taskInfo.setPeriodOffset(formSchemeDefine.getPeriodOffset());
        taskInfo.setMeasureUnit(formSchemeDefine.getMeasureUnit());
        taskInfo.setFillInAutomaticallyDue(formSchemeDefine.getFillInAutomaticallyDue());
        taskInfo.setDw(formSchemeDefine.getDw());
        taskInfo.setDatetime(formSchemeDefine.getDateTime());
        taskInfo.setDims(formSchemeDefine.getDims());
        taskInfo.setFilterExpression(formSchemeDefine.getFilterExpression());
        return taskInfo;
    }

    public void valueDefine(DesignFormSchemeDefine schemeDefine) {
        schemeDefine.setTaskKey(this.taskKey);
        schemeDefine.setFormSchemeCode(this.formSchemeCode);
        schemeDefine.setPeriodType(this.periodType);
        schemeDefine.setKey(this.key);
        schemeDefine.setTitle(this.title);
        schemeDefine.setOrder(this.order);
        schemeDefine.setVersion(this.version);
        schemeDefine.setOwnerLevelAndId(this.ownerLevelAndId);
        schemeDefine.setUpdateTime(this.updateTime);
        schemeDefine.setDescription(this.description);
        schemeDefine.setTaskPrefix(this.taskPrefix);
        schemeDefine.setFilePrefix(this.filePrefix);
        schemeDefine.setPeriodOffset(this.periodOffset);
        schemeDefine.setMeasureUnit(this.measureUnit);
        schemeDefine.setFillInAutomaticallyDue(this.fillInAutomaticallyDue);
        schemeDefine.setDw(this.dw);
        schemeDefine.setDateTime(this.datetime);
        schemeDefine.setDims(this.dims);
        schemeDefine.setFilterExpression(this.filterExpression);
    }
}

