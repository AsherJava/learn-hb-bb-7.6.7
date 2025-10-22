/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.spi;

import com.jiuqi.nr.data.logic.facade.param.output.CheckErrorObj;

public interface ICheckErrHandler {
    public void start();

    public void handle(CheckErrorObj var1);

    public void finish();

    default public void canceled() {
    }
}

