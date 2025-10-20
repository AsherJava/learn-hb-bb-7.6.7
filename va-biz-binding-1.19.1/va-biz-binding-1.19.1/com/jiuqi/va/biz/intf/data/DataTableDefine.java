/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTableInfo;
import com.jiuqi.va.biz.intf.value.NamedContainer;

public interface DataTableDefine
extends DataTableInfo {
    public NamedContainer<? extends DataFieldDefine> getFields();
}

