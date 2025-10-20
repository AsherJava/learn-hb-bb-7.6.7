/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.entity;

import com.jiuqi.gcreport.bde.fetchsetting.client.enums.BizTypeEnum;
import java.math.BigDecimal;

public class FetchSchemeEO {
    public static final String TABLENAME = "BDE_FETCHSCHEME";
    private static final long serialVersionUID = -6605464087591005422L;
    private String id;
    private String name;
    private String formSchemeId;
    private BigDecimal ordinal;
    private BizTypeEnum bizType;
    private Integer includeAdjustVchr;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public BizTypeEnum getBizType() {
        return this.bizType;
    }

    public void setBizType(BizTypeEnum bizType) {
        this.bizType = bizType;
    }

    public Integer getIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(Integer includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }
}

