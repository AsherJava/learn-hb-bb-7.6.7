/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;
import java.util.List;

public interface EnumLinkageSettingDefine
extends IMetaItem {
    public String getMasterLink();

    public List<String> getSlaveLinks();

    public List<String> getShowLinks();
}

