/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.service;

import com.jiuqi.nr.task.form.dto.CheckResult;
import com.jiuqi.nr.task.form.dto.FormDesignerDTO;

public interface IFormCheckService {
    public CheckResult doCheck(FormDesignerDTO var1);

    public CheckResult doCheck(String var1);
}

