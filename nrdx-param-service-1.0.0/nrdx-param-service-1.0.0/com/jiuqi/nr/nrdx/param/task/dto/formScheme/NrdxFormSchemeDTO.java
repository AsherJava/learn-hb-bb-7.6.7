/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.report.TransformReportDefine
 *  com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisSchemeDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormSchemeDTO
 *  com.jiuqi.nr.param.transfer.definition.dto.formscheme.SchemePeriodLinkDTO
 */
package com.jiuqi.nr.nrdx.param.task.dto.formScheme;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.report.TransformReportDefine;
import com.jiuqi.nr.definition.internal.impl.FillInAutomaticallyDue;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import com.jiuqi.nr.param.transfer.definition.dto.form.AnalysisSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.FormSchemeDTO;
import com.jiuqi.nr.param.transfer.definition.dto.formscheme.SchemePeriodLinkDTO;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NrdxFormSchemeDTO {
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
    private TransformReportDefine transformReportDefine;
    private List<SchemePeriodLinkDTO> periodLinks;
    private AnalysisSchemeDTO analysisSchemeDTO;
    private DesParamLanguageDTO desParamLanguageDTO;

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

    public static NrdxFormSchemeDTO valueOf(FormSchemeDefine formSchemeDefine) {
        if (formSchemeDefine == null) {
            return null;
        }
        NrdxFormSchemeDTO nrdxFormSchemeDTO = new NrdxFormSchemeDTO();
        nrdxFormSchemeDTO.setTaskKey(formSchemeDefine.getTaskKey());
        nrdxFormSchemeDTO.setFormSchemeCode(formSchemeDefine.getFormSchemeCode());
        nrdxFormSchemeDTO.setPeriodType(formSchemeDefine.getPeriodType());
        nrdxFormSchemeDTO.setKey(formSchemeDefine.getKey());
        nrdxFormSchemeDTO.setTitle(formSchemeDefine.getTitle());
        nrdxFormSchemeDTO.setOrder(formSchemeDefine.getOrder());
        nrdxFormSchemeDTO.setVersion(formSchemeDefine.getVersion());
        nrdxFormSchemeDTO.setOwnerLevelAndId(formSchemeDefine.getOwnerLevelAndId());
        nrdxFormSchemeDTO.setUpdateTime(formSchemeDefine.getUpdateTime());
        nrdxFormSchemeDTO.setDescription(formSchemeDefine.getDescription());
        nrdxFormSchemeDTO.setTaskPrefix(formSchemeDefine.getTaskPrefix());
        nrdxFormSchemeDTO.setFilePrefix(formSchemeDefine.getFilePrefix());
        nrdxFormSchemeDTO.setPeriodOffset(formSchemeDefine.getPeriodOffset());
        nrdxFormSchemeDTO.setMeasureUnit(formSchemeDefine.getMeasureUnit());
        nrdxFormSchemeDTO.setFillInAutomaticallyDue(formSchemeDefine.getFillInAutomaticallyDue());
        nrdxFormSchemeDTO.setDw(formSchemeDefine.getDw());
        nrdxFormSchemeDTO.setDatetime(formSchemeDefine.getDateTime());
        nrdxFormSchemeDTO.setDims(formSchemeDefine.getDims());
        nrdxFormSchemeDTO.setFilterExpression(formSchemeDefine.getFilterExpression());
        return nrdxFormSchemeDTO;
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

    public TransformReportDefine getTransformReportDefine() {
        return this.transformReportDefine;
    }

    public void setTransformReportDefine(TransformReportDefine transformReportDefine) {
        this.transformReportDefine = transformReportDefine;
    }

    public List<SchemePeriodLinkDTO> getPeriodLinks() {
        return this.periodLinks;
    }

    public void setPeriodLinks(List<SchemePeriodLinkDTO> periodLinks) {
        this.periodLinks = periodLinks;
    }

    public AnalysisSchemeDTO getAnalysisSchemeDTO() {
        return this.analysisSchemeDTO;
    }

    public void setAnalysisSchemeDTO(AnalysisSchemeDTO analysisSchemeDTO) {
        this.analysisSchemeDTO = analysisSchemeDTO;
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }

    public static FormSchemeDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (FormSchemeDTO)objectMapper.readValue(bytes, FormSchemeDTO.class);
    }
}

