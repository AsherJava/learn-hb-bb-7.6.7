/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.function.func.FetchOtherUnitDatas;
import com.jiuqi.nr.function.func.cache.UnitTreeCacheInfo;
import com.jiuqi.nr.function.util.FuncExpressionParseUtil;
import java.util.List;

public class Evaluate
extends FetchOtherUnitDatas
implements IReportFunction {
    private static final long serialVersionUID = -6943252075973792335L;

    public Evaluate() {
        this.parameters().clear();
        this.parameters().add(new Parameter("zdm", 6, "\u5355\u4f4d\u4e3b\u4ee3\u7801"));
        this.parameters().add(new Parameter("zbExp", 0, "\u53d6\u503c\u6307\u6807"));
    }

    @Override
    protected String getUnitCode(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        UnitTreeCacheInfo cacheInfo;
        IASTNode p0 = parameters.get(0);
        String cacheKey = "UnitTreeCacheInfo";
        String offSetPeriod = FuncExpressionParseUtil.getOffSetPeriod(qContext, p0);
        if (offSetPeriod != null) {
            cacheKey = cacheKey + "_" + offSetPeriod;
        }
        if ((cacheInfo = (UnitTreeCacheInfo)qContext.getCache().get(cacheKey)) == null) {
            cacheInfo = new UnitTreeCacheInfo();
            cacheInfo.doInit(qContext, p0, offSetPeriod, cacheKey, true);
        }
        String zdm = (String)p0.evaluate((IContext)qContext);
        return cacheInfo.getKeyByZdm(zdm);
    }

    @Override
    public String name() {
        return "Evaluate";
    }

    @Override
    public String title() {
        return "\u53d6\u6307\u5b9a\u5355\u4f4d\u7684\u6570\u636e";
    }

    public boolean isDeprecated() {
        return true;
    }
}

