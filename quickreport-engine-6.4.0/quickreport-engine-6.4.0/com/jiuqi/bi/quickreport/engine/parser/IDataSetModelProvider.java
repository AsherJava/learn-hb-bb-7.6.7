/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.DSModel
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;

public interface IDataSetModelProvider {
    public DSModel findModel(String var1) throws ReportExpressionException;
}

