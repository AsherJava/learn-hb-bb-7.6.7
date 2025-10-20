/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.va.filter.bill.formula;

import com.jiuqi.bi.syntax.parser.IContext;

public class EnumDataFormulaContext
implements IContext {
    private String val;
    private String title;

    public EnumDataFormulaContext() {
    }

    public EnumDataFormulaContext(String val, String title) {
        this.val = val;
        this.title = title;
    }

    public String getVal() {
        return this.val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

