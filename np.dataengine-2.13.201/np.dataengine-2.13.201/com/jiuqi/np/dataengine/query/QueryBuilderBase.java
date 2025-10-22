/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.ASTNodeType
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 */
package com.jiuqi.np.dataengine.query;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.ASTNodeType;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.LookupItem;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.intf.impl.ReadonlyTableImpl;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.StatisticInfo;
import com.jiuqi.np.dataengine.query.QueryFilterValueClassify;
import com.jiuqi.np.dataengine.query.QueryOpenMode;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilderBase
extends ASTNode {
    private static final long serialVersionUID = -3120791494952172539L;
    protected ReadonlyTableImpl table;
    protected final ArrayList<IASTNode> queryNodes = new ArrayList();
    protected IASTNode rowFilterNode = null;
    protected int orderByIndex = -1;
    protected int singleOrderByFieldIndex = -1;
    protected ArrayList<Boolean> orderByIsDescending;
    public QueryOpenMode queryOpenMode = QueryOpenMode.NORMAL_OPEN;
    public int ignoreFirstCubeRow = 0;
    public boolean filterRowByAuthority;
    protected QueryFilterValueClassify colValueFilters = new QueryFilterValueClassify();
    public boolean getRowNum = false;
    public DimensionValueSet rowKeys = null;
    private QueryFields queryFields;
    public List<StatisticInfo> statisticNodes = new ArrayList<StatisticInfo>();
    public List<LookupItem> lookupItems = new ArrayList<LookupItem>();
    protected List<String> recKeys;
    private String currentWorkPeriod;

    public List<String> getRecKeys() {
        return this.recKeys;
    }

    public void setRecKeys(List<String> recKeys) {
        this.recKeys = recKeys;
    }

    public QueryBuilderBase() {
        super(null);
    }

    public final String getCurrentWorkPeriod() {
        return this.currentWorkPeriod;
    }

    public final void setCurrentWorkPeriod(String value) {
        this.currentWorkPeriod = value;
    }

    public int childrenSize() {
        return this.queryNodes.size();
    }

    public IASTNode getChild(int index) {
        return this.queryNodes.get(index);
    }

    public QueryFields getQueryFields() {
        if (this.queryFields == null) {
            this.queryFields = ExpressionUtils.getQueryFields((IASTNode)this, this.statisticNodes, this.lookupItems);
        }
        return this.queryFields;
    }

    public ReadonlyTableImpl getResultTable() {
        return this.table;
    }

    public ASTNodeType getNodeType() {
        return null;
    }

    public int getType(IContext context) throws SyntaxException {
        return 0;
    }

    public ReadonlyTableImpl getTable() {
        return this.table;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return null;
    }

    public boolean isStatic(IContext context) {
        return false;
    }

    public void toString(StringBuilder buffer) {
    }
}

