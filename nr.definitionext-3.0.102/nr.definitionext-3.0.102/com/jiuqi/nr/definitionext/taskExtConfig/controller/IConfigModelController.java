/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definitionext.taskExtConfig.controller;

import com.jiuqi.nr.definitionext.taskExtConfig.controller.IConfigModel;

public interface IConfigModelController {
    public boolean handled(String var1);

    public String initData(String var1, String var2);

    public IConfigModel getModel(String var1, String var2, String var3);

    public void updateOperation(String var1, String var2, Object var3);
}

