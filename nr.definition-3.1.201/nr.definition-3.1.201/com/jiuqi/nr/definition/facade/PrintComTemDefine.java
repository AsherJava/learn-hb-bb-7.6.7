/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;

public interface PrintComTemDefine
extends IMetaItem {
    public String getCode();

    public String getOrder();

    public String getPrintSchemeKey();

    public String getDescription();

    public byte[] getTemplateData();
}

