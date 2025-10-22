/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.controller;

import com.jiuqi.nr.definition.facade.FormFieldInfoDefine;
import java.util.Set;
import java.util.stream.Collectors;

public interface IFormFieldInfoService {
    default public Set<String> getFormKeys(String dataFieldKey) {
        return this.getFormFieldInfos(dataFieldKey).stream().map(FormFieldInfoDefine::getFormKey).collect(Collectors.toSet());
    }

    public Set<FormFieldInfoDefine> getFormFieldInfos(String var1);
}

