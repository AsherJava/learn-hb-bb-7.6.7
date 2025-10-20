/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.intf.impl;

import java.io.Serializable;

public interface PageDTO
extends Serializable {
    public Boolean isPagination();

    public Integer getOffset();

    public Integer getLimit();
}

