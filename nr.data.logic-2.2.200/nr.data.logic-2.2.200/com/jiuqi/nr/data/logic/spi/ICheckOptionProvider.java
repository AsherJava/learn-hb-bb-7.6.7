/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.spi;

import com.jiuqi.nr.data.logic.facade.param.input.CheckMax;

public interface ICheckOptionProvider {
    public int getBatchSplitCount();

    public CheckMax getCheckMax();
}

