/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.syntax.function.IFunction
 */
package com.jiuqi.bi.quickreport.engine.context;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.syntax.function.IFunction;

public final class ParsingFunction {
    private final String funcName;
    private IFunction function;
    private DSModel dataSet;

    public ParsingFunction(String funcName) {
        this.funcName = funcName;
    }

    public String getFuncName() {
        return this.funcName;
    }

    public IFunction getFunction() {
        return this.function;
    }

    public void setFunction(IFunction function) {
        this.function = function;
    }

    public DSModel getDataSet() {
        return this.dataSet;
    }

    public void setDataSet(DSModel dataset) {
        this.dataSet = dataset;
    }
}

