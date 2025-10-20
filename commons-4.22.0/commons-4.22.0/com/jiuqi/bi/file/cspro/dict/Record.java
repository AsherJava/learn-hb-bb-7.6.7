/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.dict;

import com.jiuqi.bi.file.cspro.dict.Item;
import java.util.ArrayList;
import java.util.List;

public class Record {
    private String label;
    private String name;
    private String recordTypeValue;
    private String required;
    private int maxRecords;
    private int recordLen;
    private List<Item> items = new ArrayList<Item>();

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecordTypeValue() {
        return this.recordTypeValue;
    }

    public void setRecordTypeValue(String recordTypeValue) {
        this.recordTypeValue = recordTypeValue;
    }

    public int getRecordLen() {
        return this.recordLen;
    }

    public void setRecordLen(int recordLen) {
        this.recordLen = recordLen;
    }

    public String getRequired() {
        return this.required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public int getMaxRecords() {
        return this.maxRecords;
    }

    public void setMaxRecords(int maxRecords) {
        this.maxRecords = maxRecords;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public int getItemCount() {
        return this.items.size();
    }

    public Item getItem(int index) {
        return this.items.get(index);
    }

    public void clearItems() {
        this.items.clear();
    }
}

