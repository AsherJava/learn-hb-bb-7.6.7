/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.task.dto;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;

public class FormSchemeBatchPublishResultDTO {
    private final FormSchemeDefine formSchemeDefine;
    private final boolean success;

    public FormSchemeBatchPublishResultDTO(FormSchemeDefine formSchemeDefine, boolean success) {
        this.formSchemeDefine = formSchemeDefine;
        this.success = success;
    }

    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public boolean isSuccess() {
        return this.success;
    }
}

