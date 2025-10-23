/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.multcheck2.bean.MultcheckItem
 */
package com.jiuqi.nr.param.transfer.definition.dto.formscheme;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MultCheckItemDTO {
    private String key;
    private String scheme;
    private String title;
    private String type;
    private String config;
    private String order;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH-mm-ss.sss zzz")
    private Date updateTime;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfig() {
        return this.config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public static MultCheckItemDTO valueOf(MultcheckItem multcheckItem) {
        if (multcheckItem == null) {
            return null;
        }
        MultCheckItemDTO multcheckItemDTO = new MultCheckItemDTO();
        multcheckItemDTO.setKey(multcheckItem.getKey());
        multcheckItemDTO.setScheme(multcheckItem.getScheme());
        multcheckItemDTO.setTitle(multcheckItem.getTitle());
        multcheckItemDTO.setType(multcheckItem.getType());
        multcheckItemDTO.setConfig(multcheckItem.getConfig());
        multcheckItemDTO.setOrder(multcheckItem.getOrder());
        multcheckItemDTO.setUpdateTime(multcheckItem.getUpdateTime());
        return multcheckItemDTO;
    }

    public void valueDefine(MultcheckItem mcSchemeParam) {
        mcSchemeParam.setKey(this.getKey());
        mcSchemeParam.setScheme(this.getScheme());
        mcSchemeParam.setTitle(this.getTitle());
        mcSchemeParam.setType(this.getType());
        mcSchemeParam.setConfig(this.getConfig());
        mcSchemeParam.setOrder(this.getOrder());
        mcSchemeParam.setUpdateTime(this.getUpdateTime());
    }
}

