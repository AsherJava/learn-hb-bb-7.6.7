/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.dataengine.common.DataRegion;
import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryRegion;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.exception.UnknownReadWriteException;
import com.jiuqi.np.dataengine.executors.CalcExecutor;
import com.jiuqi.np.dataengine.executors.CheckExecutor;
import com.jiuqi.np.dataengine.executors.ConditionExecutor;
import com.jiuqi.np.dataengine.executors.DNAFmlDataCommittor;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExprExecCenter;
import com.jiuqi.np.dataengine.executors.StatExecutor;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.dataengine.query.QuerySqlBuilder;
import java.util.HashSet;
import java.util.Set;

public class ExprExecRegion
extends ExprExecCenter {
    public final QueryRegion queryRegion;
    public Set<QueryField> regionReads;
    public Set<QueryField> regionWrites;
    public Set<QueryTable> regionTableReads;
    public Set<QueryTable> regionTableWrites;
    private boolean isFuncCalc;
    private String regionCode;
    private boolean useStatResult;

    public ExprExecRegion(QueryContext context, DataRegion scope) {
        super(context);
        this.queryRegion = this.createQueryRegion(context, scope);
        this.regionCode = OrderGenerator.newOrder();
    }

    public QueryRegion createQueryRegion(QueryContext context, DataRegion scope) {
        QuerySqlBuilder sqlBuilder = new QuerySqlBuilder();
        return new QueryRegion(scope.getDimensions(), sqlBuilder);
    }

    @Override
    public String getFeature() {
        return this.regionCode;
    }

    public final String getRegionCode() {
        return this.regionCode;
    }

    public final void setRegionCode(String value) {
        this.regionCode = value;
    }

    public final boolean getUseStatResult() {
        return this.useStatResult;
    }

    public final void setUseStatResult(boolean value) {
        this.useStatResult = value;
    }

    public DimensionSet getRegionDimensions() {
        return this.queryRegion.getDimensions();
    }

    public void combine(ExprExecRegion another) {
        int count = another.executors.size();
        for (int i = count - 1; i >= 0; --i) {
            ExecutorBase executor = (ExecutorBase)another.executors.get(i);
            if (executor instanceof CalcExecutor) {
                this.getCalcExecutor().combine((CalcExecutor)executor);
                continue;
            }
            if (executor instanceof CheckExecutor) {
                this.getCheckExecutor().combine((CheckExecutor)executor);
                continue;
            }
            if (executor instanceof EvalExecutor) {
                this.getEvalExecutor().combine((EvalExecutor)executor);
                continue;
            }
            if (executor instanceof ConditionExecutor) {
                this.getConditionExecutor().combine((ConditionExecutor)executor);
                continue;
            }
            if (executor instanceof StatExecutor) {
                this.getStatExecutor().combine((StatExecutor)executor);
                continue;
            }
            if (executor instanceof DNAFmlDataCommittor) continue;
            another.remove(executor);
            this.add(executor);
            executor.replacePrecursor(another.findCalcExecutor(), this.getCalcExecutor());
            executor.replacePrecursor(another.findCheckExecutor(), this.getCheckExecutor());
            executor.replacePrecursor(another.findEvalExecutor(), this.getEvalExecutor());
            executor.replacePrecursor(another.findConditionExecutor(), this.getConditionExecutor());
            executor.replacePrecursor(another.findStatExecutor(), this.getStatExecutor());
        }
        if (another.isFuncCalc) {
            this.setFuncCalc(true);
        }
        if (another.regionReads != null) {
            if (this.regionReads == null) {
                this.regionReads = new HashSet<QueryField>();
            }
            this.regionReads.addAll(another.regionReads);
        }
        if (another.regionWrites != null) {
            if (this.regionWrites == null) {
                this.regionWrites = new HashSet<QueryField>();
            }
            this.regionWrites.addAll(another.regionWrites);
        }
        if (another.regionTableReads != null) {
            if (this.regionTableReads == null) {
                this.regionTableReads = new HashSet<QueryTable>();
            }
            this.regionTableReads.addAll(another.regionTableReads);
        }
        if (another.regionTableWrites != null) {
            if (this.regionTableWrites == null) {
                this.regionTableWrites = new HashSet<QueryTable>();
            }
            this.regionTableWrites.addAll(another.regionTableWrites);
        }
        this.queryRegion.combine(another.queryRegion);
        this.setUseStatResult(this.getUseStatResult() || another.getUseStatResult());
        if (another.precursors != null) {
            for (Object p : another.precursors) {
                ExecutorBase precursor = (ExecutorBase)p;
                ExecutorBase.createLink(precursor, this);
            }
        }
    }

    public QueryTable getFuncWriteTable() throws UnknownReadWriteException {
        return ((CalcExpression)this.getCalcExecutor().get(0)).getWriteTable();
    }

    public boolean isFuncCalc() {
        return this.isFuncCalc;
    }

    public void setFuncCalc(boolean isFuncCalc) {
        this.isFuncCalc = isFuncCalc;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.regionCode == null ? 0 : this.regionCode.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        ExprExecRegion other = (ExprExecRegion)obj;
        return !(this.regionCode == null ? other.regionCode != null : !this.regionCode.equals(other.regionCode));
    }
}

