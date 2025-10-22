/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend;

import com.jiuqi.nr.data.logic.facade.extend.ICheckErrorFilter;
import com.jiuqi.nr.data.logic.facade.extend.param.ErrFilterParam;

public interface ICheckErrorFilterFactory {
    public ICheckErrorFilter getCheckErrorFilter(ErrFilterParam var1);
}

