/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.jiuqi.nr.definition.facade.DesignPrintComTemDefine
 *  com.jiuqi.nr.definition.facade.PrintComTemDefine
 */
package com.jiuqi.nr.nrdx.param.task.dto.print;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jiuqi.nr.definition.facade.DesignPrintComTemDefine;
import com.jiuqi.nr.definition.facade.PrintComTemDefine;
import java.util.Date;

public class NrdxPrintComTemDTO {
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

    public static NrdxPrintComTemDTO valueOf(PrintComTemDefine define) {
        if (null == define) {
            return null;
        }
        NrdxPrintComTemDTO nrdxPrintComTemDTO = new NrdxPrintComTemDTO();
        nrdxPrintComTemDTO.setKey(define.getKey());
        nrdxPrintComTemDTO.setCode(define.getCode());
        nrdxPrintComTemDTO.setTitle(define.getTitle());
        nrdxPrintComTemDTO.setOrder(define.getOrder());
        nrdxPrintComTemDTO.setPrintSchemeKey(define.getPrintSchemeKey());
        nrdxPrintComTemDTO.setDescription(define.getDescription());
        nrdxPrintComTemDTO.setUpdateTime(define.getUpdateTime());
        nrdxPrintComTemDTO.setVersion(define.getVersion());
        nrdxPrintComTemDTO.setOwnerLevelAndId(define.getOwnerLevelAndId());
        nrdxPrintComTemDTO.setTemplateData(define.getTemplateData());
        return nrdxPrintComTemDTO;
    }
}

