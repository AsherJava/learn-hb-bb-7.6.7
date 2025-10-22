/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import java.io.Serializable;

public interface IBaseMetaItem
extends Serializable {
    public String getKey();

    public String getTitle();

    public String getOrder();

    public String getVersion();

    public String getOwnerLevelAndId();
}

