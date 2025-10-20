/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.cache.intf.CacheEntity
 *  javax.validation.constraints.NotNull
 *  javax.validation.constraints.Size
 */
package com.jiuqi.dc.penetratebill.client.dto;

import com.jiuqi.dc.base.common.cache.intf.CacheEntity;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PenetrateSchemeDTO
implements Serializable,
CacheEntity {
    private static final long serialVersionUID = -5089768852070235362L;
    private String id;
    private Long ver;
    @NotNull(message="\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a")
    @Size(max=100, message="\u540d\u79f0\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e100")
    private @NotNull(message="\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a") @Size(max=100, message="\u540d\u79f0\u957f\u5ea6\u4e0d\u80fd\u5927\u4e8e100") String schemeName;
    @NotNull(message="\u8303\u56f4\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u8303\u56f4\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a") String scopeType;
    @NotNull(message="\u8303\u56f4\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u8303\u56f4\u4e0d\u80fd\u4e3a\u7a7a") String scopeValue;
    @NotNull(message="\u5904\u7406\u5668\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u5904\u7406\u5668\u4e0d\u80fd\u4e3a\u7a7a") String handlerCode;
    @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u53c2\u6570\u4e0d\u80fd\u4e3a\u7a7a") String customParam;
    private String createDate;
    private String billNoField;
    @NotNull(message="\u6253\u5f00\u65b9\u5f0f\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u6253\u5f00\u65b9\u5f0f\u4e0d\u80fd\u4e3a\u7a7a") String openWay;

    public PenetrateSchemeDTO() {
    }

    public PenetrateSchemeDTO(String id, Long ver, String schemeName, String scopeType, String scopeValue, String handlerCode, String customParam, String createDate, String billNoField, String openWay) {
        this.id = id;
        this.ver = ver;
        this.schemeName = schemeName;
        this.scopeType = scopeType;
        this.scopeValue = scopeValue;
        this.handlerCode = handlerCode;
        this.customParam = customParam;
        this.createDate = createDate;
        this.billNoField = billNoField;
        this.openWay = openWay;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getSchemeName() {
        return this.schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getScopeType() {
        return this.scopeType;
    }

    public void setScopeType(String scopeType) {
        this.scopeType = scopeType;
    }

    public String getScopeValue() {
        return this.scopeValue;
    }

    public void setScopeValue(String scopeValue) {
        this.scopeValue = scopeValue;
    }

    public String getHandlerCode() {
        return this.handlerCode;
    }

    public void setHandlerCode(String handlerCode) {
        this.handlerCode = handlerCode;
    }

    public String getCustomParam() {
        return this.customParam;
    }

    public void setCustomParam(String customParam) {
        this.customParam = customParam;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCacheKey() {
        return this.id;
    }

    public String getBillNoField() {
        return this.billNoField;
    }

    public void setBillNoField(String billNoField) {
        this.billNoField = billNoField;
    }

    public String getOpenWay() {
        return this.openWay;
    }

    public void setOpenWay(String openWay) {
        this.openWay = openWay;
    }
}

