/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.PrintComTemDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.print;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import com.jiuqi.nr.param.transfer.definition.dto.BaseDTO;
import java.io.IOException;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PrintComTemDTO
extends BaseDTO {
    private String key;
    private String code;
    private String title;
    private String order;
    private String printSchemeKey;
    private String description;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;
    private String version;
    private String ownerLevelAndId;
    private byte[] templateData;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPrintSchemeKey() {
        return this.printSchemeKey;
    }

    public void setPrintSchemeKey(String printSchemeKey) {
        this.printSchemeKey = printSchemeKey;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public byte[] getTemplateData() {
        return this.templateData;
    }

    public void setTemplateData(byte[] templateData) {
        this.templateData = templateData;
    }

    public void value2Define(DesignPrintComTemDefine define) {
        if (null == define) {
            return;
        }
        define.setKey(this.key);
        define.setCode(this.code);
        define.setTitle(this.title);
        define.setOrder(this.order);
        define.setPrintSchemeKey(this.printSchemeKey);
        define.setDescription(this.description);
        define.setUpdateTime(this.updateTime);
        define.setVersion(this.version);
        define.setOwnerLevelAndId(this.ownerLevelAndId);
        define.setTemplateData(this.templateData);
    }

    public static PrintComTemDTO valueOf(PrintComTemDefine define) {
        if (null == define) {
            return null;
        }
        PrintComTemDTO dto = new PrintComTemDTO();
        dto.setKey(define.getKey());
        dto.setCode(define.getCode());
        dto.setTitle(define.getTitle());
        dto.setOrder(define.getOrder());
        dto.setPrintSchemeKey(define.getPrintSchemeKey());
        dto.setDescription(define.getDescription());
        dto.setUpdateTime(define.getUpdateTime());
        dto.setVersion(define.getVersion());
        dto.setOwnerLevelAndId(define.getOwnerLevelAndId());
        dto.setTemplateData(define.getTemplateData());
        return dto;
    }

    public static PrintComTemDTO valueOf(byte[] bytes, ObjectMapper mapper) throws IOException {
        return (PrintComTemDTO)mapper.readValue(bytes, PrintComTemDTO.class);
    }
}

