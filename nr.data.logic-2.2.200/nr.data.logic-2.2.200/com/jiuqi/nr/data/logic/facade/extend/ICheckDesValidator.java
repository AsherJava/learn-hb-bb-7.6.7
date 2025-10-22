/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend;

import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.output.CKDValResult;

@Deprecated
public interface ICheckDesValidator {
    public CKDValResult validate(CheckDesObj var1);

    public String getUniqueCode();
}

