/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.model;

import com.jiuqi.va.biz.intf.model.DeclareHost;

public interface Declare<T, H extends DeclareHost<? super T>> {
    public H endDeclare();
}

