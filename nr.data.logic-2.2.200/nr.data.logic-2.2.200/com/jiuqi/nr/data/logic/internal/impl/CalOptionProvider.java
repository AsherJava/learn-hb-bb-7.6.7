/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.impl;

import com.jiuqi.nr.data.logic.internal.util.SystemOptionUtil;
import com.jiuqi.nr.data.logic.spi.ICalOptionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CalOptionProvider
implements ICalOptionProvider {
    @Autowired
    private SystemOptionUtil systemOptionUtil;

    @Override
    public int getBatchSplitCount() {
        return this.systemOptionUtil.getBatchSplitCount();
    }

    @Override
    public int getCalculateCount(String formulaSchemeKey) {
        return this.systemOptionUtil.getCalculateCount(formulaSchemeKey);
    }
}

