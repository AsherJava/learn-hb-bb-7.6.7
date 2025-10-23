/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.nrdx.param.dto;

import com.jiuqi.nr.nrdx.param.dto.ParamDTO;
import java.util.List;

public class FormParamDTO
extends ParamDTO {
    private List<String> forms;

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }
}

