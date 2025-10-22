/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.impl;

import com.jiuqi.nr.data.logic.facade.param.input.CheckMax;
import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import com.jiuqi.nr.data.logic.spi.ICheckOptionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckOptionProvider
implements ICheckOptionProvider {
    @Autowired
    private SystemOptionUtil systemOptionUtil;

    @Override
    public int getBatchSplitCount() {
        return this.systemOptionUtil.getBatchSplitCount();
    }

    @Override
    public CheckMax getCheckMax() {
        return new CheckMax();
    }
}

