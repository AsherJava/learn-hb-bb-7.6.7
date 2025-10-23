/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

import com.jiuqi.nr.data.access.param.AccessItem;
import java.util.ArrayList;
import java.util.List;

public class AccessParam {
    private List<AccessItem> items = new ArrayList<AccessItem>();

    public List<AccessItem> getItems() {
        return this.items;
    }

    public void setItems(List<AccessItem> items) {
        this.items = items;
    }
}

