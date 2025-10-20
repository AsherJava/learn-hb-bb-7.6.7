/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.execute.intf;

import java.util.List;

public class UnitAndBookValue {
    private List<String> unitCodes;
    private List<String> bookCodes;

    public UnitAndBookValue(List<String> unitCodes, List<String> bookCodes) {
        this.unitCodes = unitCodes;
        this.bookCodes = bookCodes;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public List<String> getBookCodes() {
        return this.bookCodes;
    }

    public void setBookCodes(List<String> bookCodes) {
        this.bookCodes = bookCodes;
    }
}

