/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.print;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PrintTemplateSchemeInfoDTO {
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

    public static PrintTemplateSchemeInfoDTO valueOf(PrintTemplateSchemeDefine define) {
        if (define == null) {
            return null;
        }
        PrintTemplateSchemeInfoDTO printTemplateScheme = new PrintTemplateSchemeInfoDTO();
        printTemplateScheme.setKey(define.getKey());
        printTemplateScheme.setOrder(define.getOrder());
        printTemplateScheme.setVersion(define.getVersion());
        printTemplateScheme.setTitle(define.getTitle());
        printTemplateScheme.setUpdateTime(define.getUpdateTime());
        printTemplateScheme.setDescription(define.getDescription());
        printTemplateScheme.setTaskKey(define.getTaskKey());
        printTemplateScheme.setFormSchemeKey(define.getFormSchemeKey());
        printTemplateScheme.setCommonAttribute(define.getCommonAttribute());
        printTemplateScheme.setGatherCoverData(define.getGatherCoverData());
        printTemplateScheme.setOwnerLevelAndId(define.getOwnerLevelAndId());
        return printTemplateScheme;
    }
}

