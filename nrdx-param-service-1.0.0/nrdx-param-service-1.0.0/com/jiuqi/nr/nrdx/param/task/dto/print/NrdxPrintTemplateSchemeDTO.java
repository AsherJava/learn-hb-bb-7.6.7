/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 *  com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO
 */
package com.jiuqi.nr.nrdx.param.task.dto.print;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.param.transfer.definition.dto.DesParamLanguageDTO;
import java.util.Date;

public class NrdxPrintTemplateSchemeDTO {
    private String formSchemeKey;
    private String taskKey;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String description;
    private byte[] commonAttribute;
    private byte[] gatherCoverData;
    private byte[] commonTemplate;
    private DesParamLanguageDTO desParamLanguageDTO;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
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

    public byte[] getCommonAttribute() {
        return this.commonAttribute;
    }

    public void setCommonAttribute(byte[] commonAttribute) {
        this.commonAttribute = commonAttribute;
    }

    public byte[] getGatherCoverData() {
        return this.gatherCoverData;
    }

    public void setGatherCoverData(byte[] gatherCoverData) {
        this.gatherCoverData = gatherCoverData;
    }

    public byte[] getCommonTemplate() {
        return this.commonTemplate;
    }

    public void setCommonTemplate(byte[] commonTemplate) {
        this.commonTemplate = commonTemplate;
    }

    public void valueOf(DesignPrintTemplateSchemeDefine define) {
        define.setKey(this.getKey());
        define.setOrder(this.getOrder());
        define.setVersion(this.getVersion());
        define.setTitle(this.getTitle());
        define.setUpdateTime(this.getUpdateTime());
        define.setDescription(this.getDescription());
        define.setTaskKey(this.getTaskKey());
        define.setFormSchemeKey(this.getFormSchemeKey());
        define.setCommonAttribute(this.getCommonAttribute());
        define.setGatherCoverData(this.getGatherCoverData());
        define.setOwnerLevelAndId(this.getOwnerLevelAndId());
    }

    public static NrdxPrintTemplateSchemeDTO valueOf(PrintTemplateSchemeDefine define) {
        if (define == null) {
            return null;
        }
        NrdxPrintTemplateSchemeDTO nrdxPrintTemplateSchemeDTO = new NrdxPrintTemplateSchemeDTO();
        nrdxPrintTemplateSchemeDTO.setKey(define.getKey());
        nrdxPrintTemplateSchemeDTO.setOrder(define.getOrder());
        nrdxPrintTemplateSchemeDTO.setVersion(define.getVersion());
        nrdxPrintTemplateSchemeDTO.setTitle(define.getTitle());
        nrdxPrintTemplateSchemeDTO.setUpdateTime(define.getUpdateTime());
        nrdxPrintTemplateSchemeDTO.setDescription(define.getDescription());
        nrdxPrintTemplateSchemeDTO.setTaskKey(define.getTaskKey());
        nrdxPrintTemplateSchemeDTO.setFormSchemeKey(define.getFormSchemeKey());
        nrdxPrintTemplateSchemeDTO.setCommonAttribute(define.getCommonAttribute());
        nrdxPrintTemplateSchemeDTO.setGatherCoverData(define.getGatherCoverData());
        nrdxPrintTemplateSchemeDTO.setOwnerLevelAndId(define.getOwnerLevelAndId());
        return nrdxPrintTemplateSchemeDTO;
    }

    public DesParamLanguageDTO getDesParamLanguageDTO() {
        return this.desParamLanguageDTO;
    }

    public void setDesParamLanguageDTO(DesParamLanguageDTO desParamLanguageDTO) {
        this.desParamLanguageDTO = desParamLanguageDTO;
    }
}

