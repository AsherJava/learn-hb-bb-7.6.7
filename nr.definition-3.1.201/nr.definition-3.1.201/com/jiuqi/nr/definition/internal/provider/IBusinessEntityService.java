/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.definition.internal.provider;

import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;

public interface IBusinessEntityService {
    public TableModelDefine queryTableModelByEntityId(String var1);

    public String getDimensionNameByEntityId(String var1);

    public String getBusinessFieldCode(FieldDefine var1);
}

