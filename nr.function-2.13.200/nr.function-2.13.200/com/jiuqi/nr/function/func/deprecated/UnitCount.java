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
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
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
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.function.func.cache.UnitTreeCacheInfo;
import com.jiuqi.nr.function.util.FuncExpressionParseUtil;
import java.util.HashMap;
import java.util.List;

public class UnitCount
extends Function
implements IReportFunction {
    private static final long serialVersionUID = 1392415366869628565L;

    public UnitCount() {
        this.parameters().add(new Parameter("unitCode", 6, "\u5355\u4f4d\u4e3b\u4ee3\u7801"));
        this.parameters().add(new Parameter("ChildrenLevel", 3, "\u7ea7\u6b21"));
    }

    public String name() {
        return "UnitCount";
    }

    public String title() {
        return "\u83b7\u53d6\u5f53\u524d\u5355\u4f4d\u6307\u5b9a\u4e0b\u7ea7\u7ea7\u6b21\u7684\u5b50\u8282\u70b9\u6570\u76ee";
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
        IASTNode p1 = parameters.get(1);
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
        IEntityTable dimTable = cacheInfo.getDimTable();
        IEntityRow currentEntityRow = dimTable.findByEntityKey(unitkey);
        if (currentEntityRow == null && !zdm.equals(unitkey)) {
            currentEntityRow = dimTable.findByEntityKey(zdm);
        }
        if (currentEntityRow == null) {
            return null;
        }
        IEntityRow root = cacheInfo.getRoot(currentEntityRow);
        HashMap<String, Integer> unitLevels = cacheInfo.getUnitCountLevels(root);
        int level = ((Number)p1.evaluate(context)).intValue();
        List allChilds = dimTable.getAllChildRows(unitkey);
        allChilds.add(currentEntityRow);
        if (null == allChilds || allChilds.size() <= 0 || level < 0) {
            return 0;
        }
        int levelUnits = 0;
        String bblxFieldCode = cacheInfo.getBblxFieldCode();
        if (level == 0) {
            for (IEntityRow node : allChilds) {
                String bblx = node.getAsString(bblxFieldCode);
                if (!this.judgeValidUnit(qContext, cacheInfo, bblx)) continue;
                ++levelUnits;
            }
        } else {
            for (IEntityRow node : allChilds) {
                String bblx = node.getAsString(bblxFieldCode);
                Integer childLevel = unitLevels.get(node.getEntityKeyData());
                if (childLevel == null || childLevel != level || !this.judgeValidUnit(qContext, cacheInfo, bblx)) continue;
                ++levelUnits;
            }
        }
        return levelUnits;
    }

    private boolean judgeValidUnit(QueryContext qContext, UnitTreeCacheInfo cacheInfo, String bblx) {
        return UnitTreeCacheInfo.VALID_BBLX.contains(bblx) || bblx.equals("1") && cacheInfo.getDwxz(qContext, bblx) == 0;
    }
}

