/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine
 */
package com.jiuqi.nr.param.transfer.definition.dto.formscheme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.definition.facade.DesignSchemePeriodLinkDefine;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SchemePeriodLinkDTO {
    private String schemeKey;
    private String periodKey;

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getPeriodKey() {
        return this.periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public static SchemePeriodLinkDTO valueOf(DesignSchemePeriodLinkDefine periodLinkDefine) {
        if (periodLinkDefine == null) {
            return null;
        }
        SchemePeriodLinkDTO linkDTO = new SchemePeriodLinkDTO();
        linkDTO.setPeriodKey(periodLinkDefine.getPeriodKey());
        linkDTO.setSchemeKey(periodLinkDefine.getSchemeKey());
        return linkDTO;
    }

    public void value2Define(DesignSchemePeriodLinkDefine linkDefine) {
        linkDefine.setPeriodKey(this.getPeriodKey());
        linkDefine.setSchemeKey(this.getSchemeKey());
    }
}

