/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend;

import com.jiuqi.nr.data.logic.facade.extend.ICheckDesValidator;
import com.jiuqi.nr.data.logic.facade.extend.param.CheckDesContext;
import java.util.List;

@Deprecated
public interface ICheckDesValidatorProvider {
    public List<ICheckDesValidator> getValidators(CheckDesContext var1);
}

