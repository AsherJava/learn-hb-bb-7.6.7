/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg
 */
package com.jiuqi.nr.param.transfer.definition.dto.formscheme;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.multcheck2.bean.MultcheckSchemeOrg;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MultCheckSchemeOrgDTO {
    private String key;
    private String scheme;
    private String org;

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

    public String getOrg() {
        return this.org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public static MultCheckSchemeOrgDTO valueOf(MultcheckSchemeOrg mcSchemeParam) {
        if (mcSchemeParam == null) {
            return null;
        }
        MultCheckSchemeOrgDTO multcheckSchemeOrgDTO = new MultCheckSchemeOrgDTO();
        multcheckSchemeOrgDTO.setKey(mcSchemeParam.getKey());
        multcheckSchemeOrgDTO.setScheme(mcSchemeParam.getScheme());
        multcheckSchemeOrgDTO.setOrg(mcSchemeParam.getOrg());
        return multcheckSchemeOrgDTO;
    }

    public void valueDefine(MultcheckSchemeOrg mcSchemeParam) {
        mcSchemeParam.setKey(this.getKey());
        mcSchemeParam.setScheme(this.getScheme());
        mcSchemeParam.setOrg(this.getOrg());
    }
}

