/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.IParseListener
 *  com.jiuqi.bi.syntax.parser.ParseException
 */
package com.jiuqi.bi.quickreport.engine.parser.function;

import com.jiuqi.bi.quickreport.engine.context.ParseContext;
import com.jiuqi.bi.quickreport.engine.context.ParsingFunction;
import com.jiuqi.bi.quickreport.engine.parser.ReportExpressionException;
import com.jiuqi.bi.quickreport.engine.parser.dataset.IDataSetNode;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunctionNode;
import com.jiuqi.bi.quickreport.engine.parser.function.DataSetFunctionProvider;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.IParseListener;
import com.jiuqi.bi.syntax.parser.ParseException;
import java.util.List;

public final class DataSetFunctionListener
implements IParseListener {
    public void startFunction(IContext context, String funcName) throws ParseException {
        if (DataSetFunctionProvider.DATASET_PROVIDER.exists(funcName)) {
            ((ParseContext)context).startFunction(funcName);
        }
    }

    public void finishParam(IContext context, IASTNode node) {
        ParsingFunction curFunc = ((ParseContext)context).getCurrentFunction();
        if (curFunc != null && node instanceof IDataSetNode) {
            curFunc.setDataSet(((IDataSetNode)node).getDataSetModel());
        }
    }

    public void finishFunction(IContext context, IASTNode funcNode) throws ParseException {
        if (funcNode instanceof DataSetFunctionNode) {
            try {
                ((ParseContext)context).finishFunction((DataSetFunctionNode)funcNode);
            }
            catch (ReportExpressionException e) {
                throw new ParseException((Throwable)e);
            }
        }
    }

    public void startRestrict(IContext context, List<String> objPathName) {
    }

    public void finishRestrictItem(IContext context, IASTNode item) {
    }

    public void finishRestrict(IContext context, IASTNode restrictNode) {
    }
}

