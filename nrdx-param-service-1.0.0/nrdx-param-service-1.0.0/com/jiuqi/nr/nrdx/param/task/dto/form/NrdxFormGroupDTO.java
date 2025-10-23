/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.DesignFormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 */
package com.jiuqi.nr.nrdx.param.task.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.DesignFormGroupDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NrdxFormGroupDTO {
    private String formSchemeKey;
    private String parentKey;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String code;
    private String condition;
    private String measureUnit;
    private DesParamLanguageDTO desParamLanguageDTO;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
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

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public static NrdxFormGroupDTO valueOf(FormGroupDefine groupDefine) {
        if (groupDefine == null) {
            return null;
        }
        NrdxFormGroupDTO nrdxFormGroupDTO = new NrdxFormGroupDTO();
        nrdxFormGroupDTO.setFormSchemeKey(groupDefine.getFormSchemeKey());
        nrdxFormGroupDTO.setParentKey(groupDefine.getParentKey());
        nrdxFormGroupDTO.setKey(groupDefine.getKey());
        nrdxFormGroupDTO.setTitle(groupDefine.getTitle());
        nrdxFormGroupDTO.setOrder(groupDefine.getOrder());
        nrdxFormGroupDTO.setVersion(groupDefine.getVersion());
        nrdxFormGroupDTO.setOwnerLevelAndId(groupDefine.getOwnerLevelAndId());
        nrdxFormGroupDTO.setUpdateTime(groupDefine.getUpdateTime());
        nrdxFormGroupDTO.setCode(groupDefine.getCode());
        nrdxFormGroupDTO.setCondition(groupDefine.getCondition());
        nrdxFormGroupDTO.setMeasureUnit(groupDefine.getMeasureUnit());
        return nrdxFormGroupDTO;
    }

    public void value2Define(DesignFormGroupDefine formGroupDefine) {
        formGroupDefine.setFormSchemeKey(this.formSchemeKey);
        formGroupDefine.setParentKey(this.parentKey);
        formGroupDefine.setKey(this.key);
        formGroupDefine.setTitle(this.title);
        formGroupDefine.setOrder(this.order);
        formGroupDefine.setVersion(this.version);
        formGroupDefine.setOwnerLevelAndId(this.ownerLevelAndId);
        formGroupDefine.setUpdateTime(this.updateTime);
        formGroupDefine.setCode(this.code);
        formGroupDefine.setCondition(this.condition);
        formGroupDefine.setMeasureUnit(this.measureUnit);
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }
}

