/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.controller;

import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IDimensionFilter;

public interface IEntityViewRunTimeController {
    public EntityViewDefine buildEntityView(String var1);

    public EntityViewDefine buildEntityView(String var1, String var2);

    public EntityViewDefine buildEntityView(String var1, String var2, boolean var3);

    public EntityViewDefine buildEntityView(IDimensionFilter var1);

    public EntityViewDefine buildEntityView(IDimensionFilter var1, boolean var2);
}

