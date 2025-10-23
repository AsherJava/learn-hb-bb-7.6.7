/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nr.zbquery.engine.grid;

import org.json.JSONObject;

public class TreeInfo {
    private int startRow = -1;
    private int endRow = -1;
    private boolean expand;
    private boolean leaf;
    private int colIndex;

    public int getStartRow() {
        return this.startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return this.endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public int getColIndex() {
        return this.colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("startRow", this.startRow);
        json.put("endRow", this.endRow);
        json.put("expand", this.expand);
        json.put("leaf", this.leaf);
        json.put("col", this.colIndex);
        json.put("type", (Object)"tree");
        return json;
    }
}

