/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.format.IDataFormator
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.bi.dataset.function;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.expression.DSFormulaContext;
import com.jiuqi.bi.dataset.function.DSFunction;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.format.IDataFormator;
import com.jiuqi.bi.syntax.parser.IContext;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.List;

public class RowNum
extends DSFunction {
    private static final long serialVersionUID = 4029783893504070262L;

    @Override
    public Object evalute(IContext context, List<IASTNode> parameters, BIDataSet filterDs) throws SyntaxException {
        if (context == null) {
            throw new SyntaxException("\u6ca1\u6709\u6570\u636e\u96c6\u4e0a\u4e0b\u6587\u73af\u5883\uff0c\u65e0\u6cd5\u83b7\u53d6\u5f53\u524d\u884c");
        }
        DSFormulaContext dsFormulaContext = (DSFormulaContext)context;
        BIDataRow curRow = dsFormulaContext.getCurRow();
        if (curRow != null) {
            return curRow.getRowNum() + 1;
        }
        throw new SyntaxException("\u6570\u636e\u96c6\u4e0a\u4e0b\u6587\u73af\u5883\u4e2d\u4e0d\u5305\u542b\u5f53\u524d\u884c\u4fe1\u606f\uff0c\u65e0\u6cd5\u6267\u884c");
    }

    @Override
    public boolean isNeedOptimize() {
        return false;
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String name() {
        return "DS_ROWNUM";
    }

    public String title() {
        return "\u83b7\u53d6\u6570\u636e\u96c6\u5f53\u524d\u884c\u53f7";
    }

    @Override
    public IDataFormator getDataFormator(IContext context) {
        return new IDataFormator(){

            public Format getFormator(IContext context) {
                DecimalFormat format = new DecimalFormat("#0");
                ((NumberFormat)format).setGroupingUsed(true);
                return format;
            }
        };
    }
}

