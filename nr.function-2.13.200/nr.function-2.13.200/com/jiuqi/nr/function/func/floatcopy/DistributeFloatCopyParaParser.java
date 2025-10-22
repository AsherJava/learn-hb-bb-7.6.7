/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.function.func.floatcopy;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser;
import java.util.List;

public class DistributeFloatCopyParaParser
extends FloatCopyParaParser {
    private String subUnitColExp;

    public DistributeFloatCopyParaParser(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        super(qContext, parameters);
    }

    @Override
    protected void parse(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        this.parseFloatRowRelaParas(qContext, (String)parameters.get(0).evaluate((IContext)qContext), (String)parameters.get(1).evaluate((IContext)qContext), (String)parameters.get(3).evaluate((IContext)qContext));
        this.parseSubUnitColExp((String)parameters.get(2).evaluate((IContext)qContext));
        this.parseIsDestClear((Boolean)parameters.get(4).evaluate((IContext)qContext));
        this.parseDestCopyMode((String)parameters.get(5).evaluate((IContext)qContext));
    }

    private void parseSubUnitColExp(String subUnitColExp) {
        this.subUnitColExp = subUnitColExp;
    }

    public String getSubUnitColExp() {
        return this.subUnitColExp;
    }
}

