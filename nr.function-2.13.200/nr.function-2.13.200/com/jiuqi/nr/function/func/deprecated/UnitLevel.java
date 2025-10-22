/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.function.func.deprecated;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.function.func.cache.UnitTreeCacheInfo;
import com.jiuqi.nr.function.util.FuncExpressionParseUtil;
import java.util.List;

public class UnitLevel
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 1392415366869628565L;

    public UnitLevel() {
        this.parameters().add(new Parameter("unitCode", 6, "\u5355\u4f4d\u4e3b\u4ee3\u7801"));
    }

    public String name() {
        return "UnitLevel";
    }

    public String title() {
        return "\u83b7\u53d6\u5f53\u524d\u5355\u4f4d\u7684\u7ea7\u6b21";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 3;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public boolean isDeprecated() {
        return true;
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        UnitTreeCacheInfo cacheInfo;
        if (!(context instanceof QueryContext)) {
            return null;
        }
        IASTNode p0 = parameters.get(0);
        QueryContext qContext = (QueryContext)context;
        String cacheKey = "UnitTreeCacheInfo";
        String offSetPeriod = FuncExpressionParseUtil.getOffSetPeriod(qContext, p0);
        if (offSetPeriod != null) {
            cacheKey = cacheKey + "_" + offSetPeriod;
        }
        if ((cacheInfo = (UnitTreeCacheInfo)qContext.getCache().get(cacheKey)) == null) {
            cacheInfo = new UnitTreeCacheInfo();
            cacheInfo.doInit(qContext, p0, offSetPeriod, cacheKey, p0 instanceof DataNode || p0 instanceof VariableDataNode && p0.toString().contains("SYS_ZDM"));
        }
        String zdm = (String)p0.evaluate(context);
        String unitkey = cacheInfo.getKeyByZdm(zdm);
        IEntityRow row = cacheInfo.getDimTable().findByEntityKey(unitkey);
        if (row == null && !zdm.equals(unitkey) && (row = cacheInfo.getDimTable().findByEntityKey(zdm)) != null) {
            unitkey = zdm;
        }
        return cacheInfo.getUnitLevel(qContext, unitkey);
    }
}

