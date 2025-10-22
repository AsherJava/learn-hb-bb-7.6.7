/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.service;

import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.FormAccessResult;

public interface IFormAuthorityServive {
    public String getAccessCode();

    public FormAccessResult canWrite(JtableContext var1);

    default public FormAccessResult caRead(JtableContext context) {
        return FormAccessResult.formHaveAccess();
    }
}

