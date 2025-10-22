/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api.param;

import com.jiuqi.nr.data.logic.spi.IFmlExecInfoProvider;

public class CalEvent {
    private String formSchemeKey;
    private IFmlExecInfoProvider fmlExecInfoProvider;

    public CalEvent(String formSchemeKey, IFmlExecInfoProvider fmlExecInfoProvider) {
        this.formSchemeKey = formSchemeKey;
        this.fmlExecInfoProvider = fmlExecInfoProvider;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public IFmlExecInfoProvider getFmlExecInfoProvider() {
        return this.fmlExecInfoProvider;
    }

    public void setFmlExecInfoProvider(IFmlExecInfoProvider fmlExecInfoProvider) {
        this.fmlExecInfoProvider = fmlExecInfoProvider;
    }
}

