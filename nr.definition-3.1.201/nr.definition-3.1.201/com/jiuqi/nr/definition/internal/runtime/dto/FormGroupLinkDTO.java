/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.nr.definition.internal.impl.RunTimeFormGroupLink;
import com.jiuqi.nr.definition.internal.runtime.dto.FormDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.FormGroupDTO;

public class FormGroupLinkDTO {
    private final FormGroupDTO formGroup;
    private final FormDTO form;
    private final String order;

    public FormGroupLinkDTO(FormGroupDTO formGroup, FormDTO form, String order) {
        this.formGroup = formGroup;
        this.form = form;
        this.order = order;
    }

    public FormGroupDTO getFormGroup() {
        return this.formGroup;
    }

    public FormDTO getForm() {
        return this.form;
    }

    public String getOrder() {
        return this.order;
    }

    public RunTimeFormGroupLink getRunTimeFormGroupLink() {
        RunTimeFormGroupLink runTimeFormGroupLink = new RunTimeFormGroupLink();
        runTimeFormGroupLink.setGroupKey(this.formGroup.getFormGroupDefine().getKey());
        runTimeFormGroupLink.setFormKey(this.form.getFormDefine().getKey());
        runTimeFormGroupLink.setFormOrder(this.order);
        return runTimeFormGroupLink;
    }
}

