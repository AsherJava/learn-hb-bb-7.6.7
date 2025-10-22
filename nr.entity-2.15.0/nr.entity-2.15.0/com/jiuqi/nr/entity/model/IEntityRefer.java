/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.model;

import java.io.Serializable;

public interface IEntityRefer
extends Serializable {
    public String getOwnField();

    public String getOwnEntityId();

    public String getReferEntityId();

    public String getReferEntityField();
}

