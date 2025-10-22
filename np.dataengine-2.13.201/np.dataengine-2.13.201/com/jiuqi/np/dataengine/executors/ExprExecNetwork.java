/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.np.util.SearchList
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.np.dataengine.collector.FmlExecuteCollector;
import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.QueryFields;
import com.jiuqi.np.dataengine.exception.ExecuteException;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExprExecNetworkBase;
import com.jiuqi.np.dataengine.executors.ExprExecRegion;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.executors.FormulaExecRegion;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.util.SearchList;
import java.util.ArrayList;

public class ExprExecNetwork
extends ExprExecNetworkBase {
    protected SearchList scopeList = new SearchList();
    private ExprExecRegionCreator exprExecCreator;
    public DimensionSet masterDimensions;

    public ExprExecNetwork(QueryContext context, ExprExecRegionCreator exprExecCreator) {
        super(context);
        this.exprExecCreator = exprExecCreator;
    }

    @Override
    protected boolean doInitialization(Object initInfo) throws ExecuteException {
        if (this.context.outFMLPlan()) {
            this.context.getMonitor().message("\u6240\u6709\u516c\u5f0f\u5df2\u52a0\u8f7d\u5230\u6267\u884c\u57df\uff0c\u6267\u884c\u57df\u4e2a\u6570\uff1a" + this.size(), this);
        }
        boolean result = super.doInitialization(initInfo);
        for (ExecutorBase region : this.executors) {
            ((FormulaExecRegion)region).printExecRegion();
        }
        FmlExecuteCollector fmlExecuteCollector = this.context.getFmlExecuteCollector();
        if (fmlExecuteCollector != null) {
            fmlExecuteCollector.getGlobalInfo().addExecRegionCount(this.size());
        }
        return result;
    }

    @Override
    public int size() {
        return this.executors.size();
    }

    public DataRegion getScope(int index) {
        return (DataRegion)this.scopeList.getKey(index);
    }

    public ExprExecRegion getCenter(int index) {
        return (ExprExecRegion)this.scopeList.getValue(index);
    }

    public void combineExecCenters() {
        for (int li = this.scopeList.size() - 1; li >= 0; --li) {
            DataRegion largeScope = (DataRegion)this.scopeList.getKey(li);
            ExprExecRegion largeCenter = (ExprExecRegion)this.scopeList.getValue(li);
            for (int i = li - 1; i >= 0; --i) {
                DataRegion scope = (DataRegion)this.scopeList.getKey(i);
                if (largeScope.getDimensions().compareTo(scope.getDimensions()) != 0 || largeScope.getUseStatResult() != scope.getUseStatResult()) continue;
                ExprExecRegion center = (ExprExecRegion)this.scopeList.getValue(i);
                if (largeCenter.getUseStatResult() != center.getUseStatResult() || largeCenter.hasStatExecutor() != center.hasStatExecutor() || scope.getFuncCalcFlag() != largeScope.getFuncCalcFlag() || center.queryRegion.hasMultiFloat(this.context) || largeCenter.queryRegion.hasMultiFloat(this.context) || !largeScope.getQueryTables().getValues().containsAll(scope.getQueryTables().getValues()) || largeCenter.isSuccessorOf(center) || center.findStatExecutor() != null) continue;
                largeCenter.combine(center);
                for (int c = 0; c < this.executors.size(); ++c) {
                    ExecutorBase executor = (ExecutorBase)this.executors.get(c);
                    executor.replacePrecursor(center, largeCenter);
                }
                this.remove(center);
                this.scopeList.remove(i);
                --li;
            }
        }
    }

    @Override
    public ExprExecRegion setupExepression(QueryFields queryFields, ArrayList<ExprExecRegion> precursors, boolean hasStatExecutor) {
        ExprExecRegion result = this.getExecCenter(queryFields, precursors, hasStatExecutor);
        result.queryRegion.addQueryFields(queryFields);
        if (precursors != null && precursors.size() > 0) {
            result.setUseStatResult(true);
            for (int i = 0; i < precursors.size(); ++i) {
                ExprExecNetwork.createLink(precursors.get(i), result);
            }
        }
        return result;
    }

    public ExprExecRegion setupExepression(ASTNode node, ArrayList<ExprExecRegion> precursors, boolean hasStatExecutor) {
        QueryFields queryFields = ExpressionUtils.getQueryFields((IASTNode)node);
        return this.setupExepression(queryFields, precursors, hasStatExecutor);
    }

    protected ExprExecRegion getExecCenter(QueryFields queryFields, ArrayList<ExprExecRegion> precursors, boolean hasStatExecutor) {
        DataRegion scope;
        int i;
        DataRegion findRegion = queryFields.getDataRegion();
        ExprExecRegion result = null;
        int index = this.scopeList.findfirstkey((Object)findRegion);
        if (index >= 0) {
            result = (ExprExecRegion)this.scopeList.getValue(index);
            if (this.canAccept(result, precursors, hasStatExecutor)) {
                return result;
            }
        } else {
            index = -1 - index;
        }
        int replaceIndex = -1;
        DimensionSet dimensions = findRegion.getDimensions();
        for (i = index; i < this.scopeList.size(); ++i) {
            scope = (DataRegion)this.scopeList.getKey(i);
            if (scope.isFuncCalc()) continue;
            if (dimensions.compareTo(scope.getDimensions()) != 0) break;
            if (scope.getQueryTables().equals((Object)findRegion.getQueryTables()) && this.canAccept(result = (ExprExecRegion)this.scopeList.getValue(i), precursors, hasStatExecutor)) {
                return result;
            }
            if (!findRegion.getQueryTables().equals((Object)scope.getQueryTables()) || !this.canAccept(result = (ExprExecRegion)this.scopeList.getValue(i), precursors, hasStatExecutor)) continue;
            replaceIndex = i;
            break;
        }
        if (replaceIndex < 0) {
            for (i = index - 1; i >= 0; --i) {
                scope = (DataRegion)this.scopeList.getKey(i);
                if (scope.isFuncCalc()) continue;
                if (dimensions.compareTo(scope.getDimensions()) != 0) break;
                if (scope.getQueryTables().equals((Object)findRegion.getQueryTables()) && this.canAccept(result = (ExprExecRegion)this.scopeList.getValue(i), precursors, hasStatExecutor)) {
                    return result;
                }
                if (!findRegion.getQueryTables().equals((Object)scope.getQueryTables()) || !this.canAccept(result = (ExprExecRegion)this.scopeList.getValue(i), precursors, hasStatExecutor)) continue;
                replaceIndex = i;
                break;
            }
        }
        if (replaceIndex < 0) {
            ExprExecRegion execCenter = this.createExecCenter(findRegion);
            this.add(execCenter);
            this.scopeList.addlast((Object)findRegion, (Object)execCenter);
            return execCenter;
        }
        ExprExecRegion execCenter = (ExprExecRegion)this.scopeList.getValue(replaceIndex);
        this.scopeList.remove(replaceIndex);
        this.scopeList.addlast((Object)findRegion, (Object)execCenter);
        return execCenter;
    }

    protected ExprExecRegion createExecCenter(DataRegion dataRegion) {
        return this.exprExecCreator.createExecRegion(this.masterDimensions, dataRegion);
    }
}

