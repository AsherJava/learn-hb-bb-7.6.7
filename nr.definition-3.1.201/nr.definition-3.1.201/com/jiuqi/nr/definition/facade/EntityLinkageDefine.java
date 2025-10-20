/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;

public interface EntityLinkageDefine
extends IMetaItem {
    public String getMasterEntityKey();

    public String getSlaveEntityKeys();

    public String getLinkageCondition();
}

