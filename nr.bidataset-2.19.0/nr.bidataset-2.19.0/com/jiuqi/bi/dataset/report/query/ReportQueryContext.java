/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.bi.dataset.report.query;

import com.jiuqi.bi.dataset.report.model.ReportDSModel;
import com.jiuqi.bi.dataset.report.query.ReportEvalResultSet;
import com.jiuqi.bi.dataset.report.tree.SortedUnitTree;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.query.QueryContext;
import java.util.Set;

public class ReportQueryContext
extends QueryContext
implements IContext {
    private ReportEvalResultSet resultSet;
    private IExpression conditionExp;
    private Set<String> schemeDims;
    private SortedUnitTree sortedUnitTree;
    private String entityVeriosnPeriod;
    private ReportDSModel dsModel;

    public ReportQueryContext(ExecutorContext executorContext, QueryParam queryParam, ReportDSModel dsModel, IMonitor monitor) throws ParseException {
        super(executorContext, queryParam, monitor);
        this.dsModel = dsModel;
        this.setBatch(true);
    }

    public void rowEval() throws SyntaxException {
        try {
            this.resultSet.executeEval(this);
        }
        catch (SyntaxException e) {
            if (!e.isCanIgnore()) {
                this.getMonitor().exception((Exception)((Object)e));
            }
        }
        catch (Exception e) {
            this.getMonitor().exception(e);
        }
    }

    public ReportEvalResultSet getResultSet() {
        return this.resultSet;
    }

    public void setResultSet(ReportEvalResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public IExpression getConditionExp() {
        return this.conditionExp;
    }

    public void setConditionExp(IExpression conditionExp) {
        this.conditionExp = conditionExp;
    }

    public Set<String> getSchemeDims() {
        return this.schemeDims;
    }

    public void setSchemeDims(Set<String> schemeDims) {
        this.schemeDims = schemeDims;
    }

    public SortedUnitTree getSortedUnitTree() {
        return this.sortedUnitTree;
    }

    public void setSortedUnitTree(SortedUnitTree sortedUnitTree) {
        this.sortedUnitTree = sortedUnitTree;
    }

    public boolean needResetOrder() {
        return this.sortedUnitTree != null && !this.sortedUnitTree.isResetParent();
    }

    public boolean needResetParentAndOder() {
        return this.sortedUnitTree != null && this.sortedUnitTree.isResetParent();
    }

    public String getEntityVeriosnPeriod() {
        return this.entityVeriosnPeriod;
    }

    public void setEntityVeriosnPeriod(String entityVeriosnPeriod) {
        this.entityVeriosnPeriod = entityVeriosnPeriod;
    }

    public ReportDSModel getDsModel() {
        return this.dsModel;
    }
}

