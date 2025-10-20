/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.value.NamedElement;

public interface DataField
extends NamedElement {
    public DataFieldDefine getDefine();

    public int getIndex();
}

