/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.DesignDataLinkMappingDefine;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DataLinkMappingDTO {
    private String id;
    private String formKey;
    private String leftDataLinkKey;
    private String rightDataLinkKey;
    private String key;
    private String title;
    private String order;
    private String version;
    private String ownerLevelAndId;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getLeftDataLinkKey() {
        return this.leftDataLinkKey;
    }

    public void setLeftDataLinkKey(String leftDataLinkKey) {
        this.leftDataLinkKey = leftDataLinkKey;
    }

    public String getRightDataLinkKey() {
        return this.rightDataLinkKey;
    }

    public void setRightDataLinkKey(String rightDataLinkKey) {
        this.rightDataLinkKey = rightDataLinkKey;
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

    public static DataLinkMappingDTO valueOf(DesignDataLinkMappingDefine linkMappingDefine) {
        DataLinkMappingDTO linkMappingDTO = new DataLinkMappingDTO();
        linkMappingDTO.setId(linkMappingDefine.getId());
        linkMappingDTO.setFormKey(linkMappingDefine.getFormKey());
        linkMappingDTO.setLeftDataLinkKey(linkMappingDefine.getLeftDataLinkKey());
        linkMappingDTO.setRightDataLinkKey(linkMappingDefine.getRightDataLinkKey());
        return linkMappingDTO;
    }

    public void value2Define(DesignDataLinkMappingDefine linkMappingDefine) {
        linkMappingDefine.setId(this.getId());
        linkMappingDefine.setFormKey(this.getFormKey());
        linkMappingDefine.setLeftDataLinkKey(this.getLeftDataLinkKey());
        linkMappingDefine.setRightDataLinkKey(this.getRightDataLinkKey());
    }
}

