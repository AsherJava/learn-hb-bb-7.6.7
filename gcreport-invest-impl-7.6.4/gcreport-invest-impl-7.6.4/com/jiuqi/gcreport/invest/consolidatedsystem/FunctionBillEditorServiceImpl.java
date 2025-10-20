/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider
 *  com.jiuqi.gcreport.calculate.formula.functionEditor.service.FunctionEditorServiceImpl
 */
package com.jiuqi.gcreport.invest.consolidatedsystem;

import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.gcreport.billcore.formula.GcReportBillFunctionProvider;
import com.jiuqi.gcreport.calculate.formula.functionEditor.service.FunctionEditorServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class FunctionBillEditorServiceImpl
extends FunctionEditorServiceImpl {
    protected IFunctionProvider getFunctionProvider() {
        return new GcReportBillFunctionProvider();
    }
}

