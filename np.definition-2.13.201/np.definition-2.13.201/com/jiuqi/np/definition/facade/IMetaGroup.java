/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;

public interface IMetaGroup
extends IMetaItem {
    @Override
    public String getTitle();

    public String getDescription();

    public String getParentKey();

    public String getCode();
}

