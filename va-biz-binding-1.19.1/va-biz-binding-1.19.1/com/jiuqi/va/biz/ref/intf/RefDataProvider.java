/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ref.intf;

import com.jiuqi.va.biz.ref.intf.RefDataFilter;
import com.jiuqi.va.biz.ref.intf.RefDataObject;

public interface RefDataProvider {
    public int getRefDataType();

    public RefDataObject ref(RefDataFilter var1);

    public void flush();
}

