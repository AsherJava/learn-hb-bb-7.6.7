/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.common.DataEngineConsts
 *  com.jiuqi.np.dataengine.common.IQueryField
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.StatisticInfo
 *  com.jiuqi.np.dataengine.reader.QueryFieldInfo
 */
package com.jiuqi.nr.data.engine.analysis.parse;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.IQueryField;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.StatisticInfo;
import com.jiuqi.np.dataengine.reader.QueryFieldInfo;
import com.jiuqi.nr.data.engine.analysis.exe.AnalysisContext;

public class AnalysisDynamicDataNode
extends DynamicDataNode {
    private static final long serialVersionUID = 1747466940564872818L;
    private boolean isDest = true;
    private int updateColumnIndex;

    public AnalysisDynamicDataNode(QueryField queryField) {
        super(null, queryField);
    }

    public int setValue(IContext context, Object value) throws SyntaxException {
        if (context instanceof AnalysisContext) {
            AnalysisContext aContext = (AnalysisContext)context;
            try {
                Object resultValue = DataEngineConsts.formatData((IQueryField)this.queryField, (Object)value);
                aContext.setValue(this.updateColumnIndex, resultValue);
            }
            catch (Exception e) {
                throw new SyntaxException((Throwable)e);
            }
            return 1;
        }
        return super.setValue(context, value);
    }

    public void bindUpdateColumn(AnalysisContext aContext) throws ParseException {
        if (this.isDest) {
            int index = aContext.findIndex(this.queryField);
            if (index < 0) {
                QueryFieldInfo info = aContext.putIndex(aContext.getExeContext().getCache().getDataModelDefinitionsCache(), this.queryField);
                index = info.index;
            }
            this.updateColumnIndex = index;
        }
    }

    public void setStatistic(IASTNode conditionNode, int statKind) {
        this.statisticNode = new StatisticInfo(statKind);
        this.statisticNode.condNode = conditionNode;
        this.statisticNode.valueNode = new AnalysisDynamicDataNode(this.queryField);
    }

    public boolean isDest() {
        return this.isDest;
    }

    public void setDest(boolean isDest) {
        this.isDest = isDest;
    }
}

