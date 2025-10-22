/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.listener;

import com.jiuqi.nr.data.logic.facade.param.output.CheckRecord;

public interface ICheckRecordListener {
    public void processCheckRecord(CheckRecord var1);

    default public boolean isEnabled() {
        return true;
    }
}

