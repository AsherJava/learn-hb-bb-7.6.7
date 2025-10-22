/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.FormAuthorityFilterParam
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FormAuthorityFilterParam;

public class FormAuthorityFilterDTO
extends FormAuthorityFilterParam {
    public FormAuthorityFilterDTO(FormAuthorityFilterParam formAuthorityFilterParam) {
        this.setTaskKey(formAuthorityFilterParam.getTaskKey());
        this.setFormSchemeKey(formAuthorityFilterParam.getFormSchemeKey());
        this.setMdCode(formAuthorityFilterParam.getMdCode());
        this.setDatatime(formAuthorityFilterParam.getDatatime());
        this.setEntityID(formAuthorityFilterParam.getEntityID());
    }
}

