/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.intf;

import java.util.ArrayList;
import java.util.List;

public class CalibreDimTable {
    private String tableCode;
    private List<String> fields = new ArrayList<String>();
    private int type;

    public CalibreDimTable(String tableCode, int type) {
        this.tableCode = tableCode;
        this.type = type;
    }

    public String getTableCode() {
        return this.tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public List<String> getFields() {
        return this.fields;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

