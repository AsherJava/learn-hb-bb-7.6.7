/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.runtime.dto.FormGroupLinkDTO;
import java.util.ArrayList;
import java.util.List;

public class FormGroupDTO {
    private final FormGroupDefine formGroupDefine;
    private final List<FormGroupLinkDTO> formGroupLinks;

    public FormGroupDTO(FormGroupDefine formGroupDefine) {
        this.formGroupDefine = formGroupDefine;
        this.formGroupLinks = new ArrayList<FormGroupLinkDTO>();
    }

    public FormGroupDefine getFormGroupDefine() {
        return this.formGroupDefine;
    }

    public List<FormGroupLinkDTO> getFormGroupLinks() {
        return this.formGroupLinks;
    }
}

