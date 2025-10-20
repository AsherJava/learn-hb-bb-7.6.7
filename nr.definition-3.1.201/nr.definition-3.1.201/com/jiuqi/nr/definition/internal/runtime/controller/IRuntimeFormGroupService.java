/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.FormGroupDefine;
import java.util.List;

public interface IRuntimeFormGroupService {
    public FormGroupDefine queryFormGroup(String var1);

    public List<FormGroupDefine> getFormGroupsByForm(String var1);

    public List<FormGroupDefine> queryRootGroupsByFormScheme(String var1);

    public List<FormGroupDefine> getChildFormGroups(String var1, boolean var2);

    public List<FormGroupDefine> getAllFormGroupsInFormScheme(String var1);

    default public FormGroupDefine queryFormGroup(String formGroupKey, String formScheme) {
        return this.queryFormGroup(formGroupKey);
    }

    default public List<FormGroupDefine> getFormGroupsByForm(String formKey, String formScheme) {
        return this.getFormGroupsByForm(formKey);
    }
}

