/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.dict;

import com.jiuqi.bi.file.cspro.dict.Item;
import com.jiuqi.bi.file.cspro.dict.Record;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private String label;
    private String name;
    private List<Item> idItems = new ArrayList<Item>();
    private List<Record> records = new ArrayList<Record>();

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addIdItem(Item item) {
        this.idItems.add(item);
    }

    public Item getIdItem(int index) {
        return this.idItems.get(index);
    }

    public int getIdItemCount() {
        return this.idItems.size();
    }

    public void clearIdItems() {
        this.idItems.clear();
    }

    public void addRecord(Record record) {
        this.records.add(record);
    }

    public Record getRecord(int index) {
        return this.records.get(index);
    }

    public int getRecordCount() {
        return this.records.size();
    }

    public void clearRecordCount() {
        this.records.clear();
    }
}

