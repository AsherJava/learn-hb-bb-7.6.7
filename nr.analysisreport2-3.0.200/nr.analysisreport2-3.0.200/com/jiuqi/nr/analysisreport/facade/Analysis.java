/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.analysisreport.facade;

import com.jiuqi.np.definition.facade.IMetaItem;
import java.util.Date;

public interface Analysis
extends IMetaItem {
    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setUpdateTime(Date var1);

    public String getDescription();
}

