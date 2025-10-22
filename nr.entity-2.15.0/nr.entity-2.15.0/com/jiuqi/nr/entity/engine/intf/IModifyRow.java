/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.intf;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;

public interface IModifyRow
extends IEntityRow {
    public void setValue(String var1, Object var2);

    public String buildRow();

    public void setNeedSync(boolean var1);

    public void setIgnoreCodeCheck(boolean var1);
}

