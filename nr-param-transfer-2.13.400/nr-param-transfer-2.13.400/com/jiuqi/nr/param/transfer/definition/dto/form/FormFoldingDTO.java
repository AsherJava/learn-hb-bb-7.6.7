/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.common.FormFoldingDirEnum
 *  com.jiuqi.nr.definition.common.FormFoldingSpecialRegion
 *  com.jiuqi.nr.definition.facade.DesignFormFoldingDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.common.FormFoldingDirEnum;
import com.jiuqi.nr.definition.common.FormFoldingSpecialRegion;
import com.jiuqi.nr.definition.facade.DesignFormFoldingDefine;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class FormFoldingDTO {
    private String key;
    private String formKey;
    private Integer startIdx;
    private Integer endIdx;
    private List<FormFoldingSpecialRegion> hiddenRegion;
    private Integer direction;
    private boolean folding;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public Integer getStartIdx() {
        return this.startIdx;
    }

    public void setStartIdx(Integer startIdx) {
        this.startIdx = startIdx;
    }

    public Integer getEndIdx() {
        return this.endIdx;
    }

    public void setEndIdx(Integer endIdx) {
        this.endIdx = endIdx;
    }

    public List<FormFoldingSpecialRegion> getHiddenRegion() {
        return this.hiddenRegion;
    }

    public void setHiddenRegion(List<FormFoldingSpecialRegion> hiddenRegion) {
        this.hiddenRegion = hiddenRegion;
    }

    public Integer getDirection() {
        return this.direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public boolean isFolding() {
        return this.folding;
    }

    public void setFolding(boolean folding) {
        this.folding = folding;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static FormFoldingDTO valueOf(DesignFormFoldingDefine formFoldingDefine) {
        FormFoldingDTO formFoldingDTO = new FormFoldingDTO();
        formFoldingDTO.setKey(formFoldingDefine.getKey());
        formFoldingDTO.setFormKey(formFoldingDefine.getFormKey());
        formFoldingDTO.setStartIdx(formFoldingDefine.getStartIdx());
        formFoldingDTO.setEndIdx(formFoldingDefine.getEndIdx());
        formFoldingDTO.setHiddenRegion(formFoldingDefine.getHiddenRegion());
        formFoldingDTO.setDirection(formFoldingDefine.getDirection().getValue());
        formFoldingDTO.setFolding(formFoldingDefine.isFolding());
        formFoldingDTO.setUpdateTime(formFoldingDefine.getUpdateTime());
        return formFoldingDTO;
    }

    public void value2Define(DesignFormFoldingDefine formFoldingDefine) {
        formFoldingDefine.setKey(this.getKey());
        formFoldingDefine.setFormKey(this.getFormKey());
        formFoldingDefine.setStartIdx(this.getStartIdx());
        formFoldingDefine.setEndIdx(this.getEndIdx());
        formFoldingDefine.setHiddenRegion(this.getHiddenRegion());
        formFoldingDefine.setDirection(FormFoldingDirEnum.valueOf((int)this.getDirection()));
        formFoldingDefine.setFolding(this.isFolding());
        formFoldingDefine.setUpdateTime(this.getUpdateTime());
    }
}

