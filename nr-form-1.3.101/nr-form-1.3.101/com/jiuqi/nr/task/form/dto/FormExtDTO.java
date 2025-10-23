/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.dto.ResourceExtDTO
 *  com.jiuqi.nr.task.api.face.IFormTypeExt
 */
package com.jiuqi.nr.task.form.dto;

import com.jiuqi.nr.task.api.dto.ResourceExtDTO;
import com.jiuqi.nr.task.api.face.IFormTypeExt;

public class FormExtDTO
extends ResourceExtDTO {
    private IFormTypeExt formType;
    private String extendInfo;

    public IFormTypeExt getFormType() {
        return this.formType;
    }

    public void setFormType(IFormTypeExt formType) {
        this.formType = formType;
    }

    public String getExtendInfo() {
        return this.extendInfo;
    }

    public void setExtendInfo(String extendInfo) {
        this.extendInfo = extendInfo;
    }
}

