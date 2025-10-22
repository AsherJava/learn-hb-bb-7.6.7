/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.impl;

import com.jiuqi.np.office.excel2.steam.xlsx.impl.Supplier;

class StringSupplier
implements Supplier {
    private final String val;

    StringSupplier(String val) {
        this.val = val;
    }

    @Override
    public Object getContent() {
        return this.val;
    }
}

