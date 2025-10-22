/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.model;

import java.io.Serializable;

public interface IEntityCategory
extends Serializable {
    public String getId();

    public String getTitle();

    public int getOrder();

    public boolean enableSelect();
}

