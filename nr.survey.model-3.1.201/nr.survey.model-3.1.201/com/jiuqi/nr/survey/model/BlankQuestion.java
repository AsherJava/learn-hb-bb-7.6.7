/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model;

import com.jiuqi.nr.survey.model.Element;
import com.jiuqi.nr.survey.model.Item;
import java.util.List;

public class BlankQuestion
extends Element {
    private String blankText;
    private List<Item> items;
    private String defaultValue;

    public String getBlankText() {
        return this.blankText;
    }

    public void setBlankText(String blankText) {
        this.blankText = blankText;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}

