/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.vo;

import com.jiuqi.gcreport.bde.fetchsetting.client.vo.AdjustPeriodSettingVO;
import java.math.BigDecimal;
import java.util.List;

public class FetchSchemeVO {
    private String id;
    private String name;
    private String formSchemeId;
    private String bizType;
    private Integer includeAdjustVchr;
    private BigDecimal ordinal;
    private List<AdjustPeriodSettingVO> adjustPeriodSettingVOS;

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public List<AdjustPeriodSettingVO> getAdjustPeriodSettingVOs() {
        return this.adjustPeriodSettingVOS;
    }

    public void setAdjustPeriodSettingVOs(List<AdjustPeriodSettingVO> adjustPeriodSettingVOS) {
        this.adjustPeriodSettingVOS = adjustPeriodSettingVOS;
    }

    public Integer getIncludeAdjustVchr() {
        return this.includeAdjustVchr;
    }

    public void setIncludeAdjustVchr(Integer includeAdjustVchr) {
        this.includeAdjustVchr = includeAdjustVchr;
    }
}

