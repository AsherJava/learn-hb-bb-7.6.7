/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem
 */
package com.jiuqi.nr.unit.treestore.systemconfig;

import com.jiuqi.nvwa.systemoption.extend.ISystemOptionItem;

public class UnitTreeSystemOptionItem
implements ISystemOptionItem {
    private String id;
    private String title;
    private String defaultValue;

    public UnitTreeSystemOptionItem(String id, String title, String defaultValue) {
        this.id = id;
        this.title = title;
        this.defaultValue = defaultValue;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }
}

