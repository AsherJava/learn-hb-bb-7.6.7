/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.config.common;

import java.util.List;

public interface IMultCheckItemBase {
    public String getId();

    public String getType();

    public String getLabel();

    public String getGroupId();

    public List<IMultCheckItemBase> getChildren();

    public void setChildren(List<IMultCheckItemBase> var1);
}

