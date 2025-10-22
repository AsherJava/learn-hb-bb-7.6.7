/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.menu;

import com.jiuiqi.nr.unit.treebase.menu.IMenuItemType;
import java.util.List;
import java.util.Map;

public interface IMenuItemObject {
    public String getKey();

    public String getCode();

    public String getTitle();

    public String getIcon();

    public IMenuItemType getType();

    public Boolean getChecked();

    public Boolean getDisabled();

    public Boolean getDivider();

    public Map<String, Object> getData();

    public List<IMenuItemObject> getChildren();

    public int getOrder();
}

