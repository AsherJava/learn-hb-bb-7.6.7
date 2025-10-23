/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine
 *  com.jiuqi.nr.definition.facade.PrintTemplateDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.print;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateDefine;
import com.jiuqi.nr.definition.facade.PrintTemplateDefine;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import java.io.IOException;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PrintTemplateDTO
extends BaseDTO {
    private String printSchemeKey;
    private String formKey;
    private boolean autoRefreshForm;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date formUpdateTime;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String description;
    private byte[] templateData;
    private byte[] labelData;
    private String comTemCode;

    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
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

    public byte[] getTemplateData() {
        return this.templateData;
    }

    public void setTemplateData(byte[] templateData) {
        this.templateData = templateData;
    }

    public byte[] getLabelData() {
        return this.labelData;
    }

    public void setLabelData(byte[] labelData) {
        this.labelData = labelData;
    }

    public Date getFormUpdateTime() {
        return this.formUpdateTime;
    }

    public void setFormUpdateTime(Date formUpdateTime) {
        this.formUpdateTime = formUpdateTime;
    }

    public String getComTemCode() {
        return this.comTemCode;
    }

    public void setComTemCode(String comTemCode) {
        this.comTemCode = comTemCode;
    }

    public boolean isAutoRefreshForm() {
        return this.autoRefreshForm;
    }

    public void setAutoRefreshForm(boolean autoRefreshForm) {
        this.autoRefreshForm = autoRefreshForm;
    }

    public void value2Define(DesignPrintTemplateDefine define) {
        define.setKey(this.getKey());
        define.setOrder(this.getOrder());
        define.setVersion(this.getVersion());
        define.setTitle(this.getTitle());
        define.setUpdateTime(this.getUpdateTime());
        define.setFormKey(this.getFormKey());
        define.setDescription(this.getDescription());
        define.setPrintSchemeKey(this.getPrintSchemeKey());
        define.setTemplateData(this.getTemplateData());
        define.setLabelData(this.getLabelData());
        define.setOwnerLevelAndId(this.getOwnerLevelAndId());
        define.setAutoRefreshForm(this.isAutoRefreshForm());
        define.setFormUpdateTime(this.getFormUpdateTime());
        define.setComTemCode(this.getComTemCode());
    }

    public static PrintTemplateDTO valueOf(PrintTemplateDefine define) {
        if (define == null) {
            return null;
        }
        PrintTemplateDTO printTemplate = new PrintTemplateDTO();
        printTemplate.setKey(define.getKey());
        printTemplate.setOrder(define.getOrder());
        printTemplate.setVersion(define.getVersion());
        printTemplate.setTitle(define.getTitle());
        printTemplate.setUpdateTime(define.getUpdateTime());
        printTemplate.setFormKey(define.getFormKey());
        printTemplate.setDescription(define.getDescription());
        printTemplate.setPrintSchemeKey(define.getPrintSchemeKey());
        printTemplate.setTemplateData(define.getTemplateData());
        printTemplate.setLabelData(define.getLabelData());
        printTemplate.setOwnerLevelAndId(define.getOwnerLevelAndId());
        printTemplate.setAutoRefreshForm(define.isAutoRefreshForm());
        printTemplate.setFormUpdateTime(define.getFormUpdateTime());
        printTemplate.setComTemCode(define.getComTemCode());
        return printTemplate;
    }

    public static PrintTemplateDTO valueOf(byte[] bytes, ObjectMapper objectMapper) throws IOException {
        return (PrintTemplateDTO)objectMapper.readValue(bytes, PrintTemplateDTO.class);
    }
}

