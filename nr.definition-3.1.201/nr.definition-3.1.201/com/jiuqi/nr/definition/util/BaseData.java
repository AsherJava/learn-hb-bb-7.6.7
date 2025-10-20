/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IEntityRow
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.np.dataengine.intf.IEntityRow;

public class BaseData {
    private String key;
    private String title;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static BaseData buildDicItem(IEntityRow entityRow) {
        BaseData baseData = new BaseData();
        baseData.setKey(entityRow.getRecKey());
        baseData.setTitle(entityRow.getTitle());
        return baseData;
    }
}

