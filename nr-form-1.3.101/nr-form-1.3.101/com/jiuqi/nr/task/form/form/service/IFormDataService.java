/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.form.service;

import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.formstyle.dto.FormStyleDTO;

public interface IFormDataService {
    public CheckResult saveFormData(FormStyleDTO var1);

    public FormStyleDTO getFormData(String var1);
}

