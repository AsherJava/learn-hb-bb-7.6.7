/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datapartnerapi.domain.FormDataQueryParam
 */
package com.jiuqi.nr.datapartnerapi.domain;

import com.jiuqi.nr.datapartnerapi.domain.FormDataQueryParam;

public class FormDataQueryDTO
extends FormDataQueryParam {
    public FormDataQueryDTO() {
    }

    public FormDataQueryDTO(FormDataQueryParam formDataQueryParam) {
        this.setTaskKey(formDataQueryParam.getTaskKey());
        this.getMdCode().addAll(formDataQueryParam.getMdCode());
        this.getDatatime().addAll(formDataQueryParam.getDatatime());
        this.getQueryForms().addAll(formDataQueryParam.getQueryForms());
        this.getQueryFields().addAll(formDataQueryParam.getQueryFields());
        this.getFilters().addAll(formDataQueryParam.getFilters());
        this.setEntityID(formDataQueryParam.getEntityID());
    }
}

