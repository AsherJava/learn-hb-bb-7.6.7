/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.facade.IMetaItem;
import java.util.Date;

public interface PrintTemplateDefine
extends IMetaItem {
    public String getDescription();

    public String getPrintSchemeKey();

    public String getFormKey();

    public boolean isAutoRefreshForm();

    public Date getFormUpdateTime();

    public byte[] getTemplateData();

    public byte[] getLabelData();

    public String getComTemCode();
}

