/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.owner;

import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterOwner;

public interface IParameterOwnerProvider {
    public String getOwnerType();

    public ParameterModel findModel(String var1, ParameterOwner var2);
}

