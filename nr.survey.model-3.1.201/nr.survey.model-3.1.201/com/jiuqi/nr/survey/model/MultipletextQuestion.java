/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.Item;
import java.util.List;

public class MultipletextQuestion
extends Element {
    private List<Item> items;

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

