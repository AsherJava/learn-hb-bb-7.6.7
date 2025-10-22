/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 */
package com.jiuqi.nr.function.func.deprecated;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.function.func.cache.UnitTreeCacheInfo;
import com.jiuqi.nr.function.util.FuncExpressionParseUtil;
import java.util.List;

public class UnitExist
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -3046142346822138379L;

    public UnitExist() {
        this.parameters().add(new Parameter("unitCode", 6, "\u5355\u4f4d\u4ee3\u7801"));
    }

    public String name() {
        return "UnitExist";
    }

    public String title() {
        return "\u5224\u65ad\u5355\u4f4d\u662f\u5426\u5b58\u5728";
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        UnitTreeCacheInfo cacheInfo;
        if (!(context instanceof QueryContext)) {
            return null;
        }
        IASTNode p0 = parameters.get(0);
        QueryContext qContext = (QueryContext)context;
        String unitkey = (String)p0.evaluate(context);
        if (unitkey == null) {
            return false;
        }
        if (p0 instanceof DynamicDataNode) {
            return true;
        }
        String zdm = unitkey;
        String cacheKey = "UnitTreeCacheInfo";
        String offSetPeriod = FuncExpressionParseUtil.getOffSetPeriod(qContext, p0);
        if (offSetPeriod != null) {
            cacheKey = cacheKey + "_" + offSetPeriod;
        }
        if ((cacheInfo = (UnitTreeCacheInfo)qContext.getCache().get(cacheKey)) == null) {
            cacheInfo = new UnitTreeCacheInfo();
            cacheInfo.doInit(qContext, p0, offSetPeriod, cacheKey, true);
        }
        unitkey = cacheInfo.getKeyByZdm(zdm);
        IEntityTable dimTable = cacheInfo.getDimTable();
        IEntityRow currentEntityRow = dimTable.findByEntityKey(unitkey);
        if (currentEntityRow == null && !zdm.equals(unitkey)) {
            currentEntityRow = dimTable.findByEntityKey(zdm);
        }
        return currentEntityRow != null;
    }

    public boolean isDeprecated() {
        return true;
    }
}

