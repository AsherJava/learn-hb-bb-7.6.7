/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.facade.IMetaGroup;
import java.util.Date;

public interface TableGroupDefine
extends IMetaGroup {
    @Override
    public String getKey();

    @Override
    public String getOrder();

    @Override
    public String getVersion();

    @Override
    public String getOwnerLevelAndId();

    @Override
    public Date getUpdateTime();
}

