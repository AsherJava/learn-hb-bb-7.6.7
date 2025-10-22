/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.readwrite;

import com.jiuqi.nr.dataentry.readwrite.IReadWriteAccessBase;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessItem;
import com.jiuqi.nr.jtable.params.base.JtableContext;

public interface IReadWriteAccess
extends IReadWriteAccessBase {
    public ReadWriteAccessDesc readable(ReadWriteAccessItem var1, JtableContext var2) throws Exception;

    public ReadWriteAccessDesc writeable(ReadWriteAccessItem var1, JtableContext var2) throws Exception;
}

