/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser
 */
package com.jiuqi.gcreport.nr.impl.function.impl.floatcopyT;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser;
import java.util.List;

public class CrossTaskFloatCopyParaParser
extends FloatCopyParaParser {
    public CrossTaskFloatCopyParaParser(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        super(qContext, parameters);
    }

    protected void parse(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        this.parseFloatRowRelaParas(qContext, (String)parameters.get(0).evaluate((IContext)qContext), (String)parameters.get(1).evaluate((IContext)qContext), (String)parameters.get(3).evaluate((IContext)qContext));
        this.parseSrcFilter((String)parameters.get(2).evaluate((IContext)qContext));
        this.parseIsDestClear((Boolean)parameters.get(4).evaluate((IContext)qContext));
        this.getQueryCondition().setPeriodType((String)parameters.get(5).evaluate((IContext)qContext));
        this.getQueryCondition().setPeriodOffset(0L);
        this.getQueryCondition().setPeriodCount(1L);
        this.getCopyCondition().setCopyMode("0");
    }
}

