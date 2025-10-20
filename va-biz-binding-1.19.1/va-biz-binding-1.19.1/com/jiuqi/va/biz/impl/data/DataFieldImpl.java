/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.impl.data;

import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.intf.data.DataField;

public class DataFieldImpl
implements DataField {
    private final DataFieldDefineImpl define;
    private final int index;

    DataFieldImpl(DataFieldDefineImpl define, int index) {
        this.define = define;
        this.index = index;
    }

    @Override
    public String getName() {
        return this.define.getName();
    }

    @Override
    public DataFieldDefineImpl getDefine() {
        return this.define;
    }

    @Override
    public int getIndex() {
        return this.index;
    }
}

