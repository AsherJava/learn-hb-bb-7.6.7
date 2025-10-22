/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.dynamic.DynamicNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.dynamic.DynamicNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.concurrent.atomic.LongAdder;

public class StatisticInfo
extends DynamicNode {
    private static final long serialVersionUID = 2817571509953920529L;
    public static final LongAdder nextUniqueNumber = new LongAdder();
    public int statKind;
    public IASTNode condNode = null;
    public DynamicDataNode valueNode = null;
    private long uniqueNum;

    public StatisticInfo(int statKind) {
        super(null);
        this.statKind = statKind;
        nextUniqueNumber.increment();
        this.uniqueNum = nextUniqueNumber.longValue();
    }

    public Object evaluate(QueryContext qContext) throws Exception {
        return qContext.readData(this.uniqueNum);
    }

    public int getType(IContext context) throws SyntaxException {
        if (this.statKind == 2) {
            return 3;
        }
        return this.valueNode.getType(context);
    }

    public Object evaluate(IContext context) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            return qContext.readData(this.uniqueNum);
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    public void toString(StringBuilder buffer) {
    }

    public long getUniqueNum() {
        return this.uniqueNum;
    }

    public QueryFields getQueryFields() {
        QueryFields queryFields = new QueryFields();
        queryFields.add(this.valueNode.getQueryField());
        if (this.condNode != null) {
            queryFields.addAll(ExpressionUtils.getQueryFields(this.condNode));
        }
        return queryFields;
    }

    public int getStatKind() {
        return this.statKind;
    }

    public IASTNode getCondNode() {
        return this.condNode;
    }

    public DynamicDataNode getValueNode() {
        return this.valueNode;
    }
}

