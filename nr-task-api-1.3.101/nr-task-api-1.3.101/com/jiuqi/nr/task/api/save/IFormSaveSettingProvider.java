/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.save;

import com.jiuqi.nr.task.api.save.FormSaveContext;

public interface IFormSaveSettingProvider {
    public String getCode();

    public String getTitle();

    public Double getOrder();

    public Boolean support(FormSaveContext var1);

    default public Boolean needChange(FormSaveContext context) {
        return this.support(context);
    }

    public String executeSave(FormSaveContext var1);
}

