/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.fmdm;

import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public interface IFMDMAttribute
extends ColumnModelDefine {
    @Deprecated
    public boolean isReferEntity();

    public String getReferEntityId();

    public String getFormSchemeKey();

    public String getEntityId();

    public String getZBKey();
}

