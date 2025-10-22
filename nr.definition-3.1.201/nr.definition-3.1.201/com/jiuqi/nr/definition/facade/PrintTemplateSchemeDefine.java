/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;

public interface PrintTemplateSchemeDefine
extends IMetaItem {
    public String getDescription();

    public String getTaskKey();

    public String getFormSchemeKey();

    public byte[] getCommonAttribute();

    public byte[] getGatherCoverData();
}

