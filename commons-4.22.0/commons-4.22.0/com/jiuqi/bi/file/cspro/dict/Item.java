/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.file.cspro.dict;

public class Item {
    private String label;
    private String name;
    private int start;
    private int len;
    private int decimal;
    private String dataType;
    private String decimalChar;
    private boolean isIdItem;

    public Item(boolean isIdItem) {
        this.isIdItem = isIdItem;
    }

    public boolean isIdItem() {
        return this.isIdItem;
    }

    public String getDecimalChar() {
        return this.decimalChar;
    }

    public void setDecimalChar(String decimalChar) {
        this.decimalChar = decimalChar;
    }

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

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLen() {
        return this.len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public int getDecimal() {
        return this.decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}

