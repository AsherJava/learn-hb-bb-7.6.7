/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.excel.param;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CommonInitData
implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean isInit;
    private final Set<String> commonKeys = new HashSet<String>();

    public boolean isInit() {
        return this.isInit;
    }

    public void setInit(boolean init) {
        this.isInit = init;
    }

    public Set<String> getCommonKeys() {
        return this.commonKeys;
    }
}

