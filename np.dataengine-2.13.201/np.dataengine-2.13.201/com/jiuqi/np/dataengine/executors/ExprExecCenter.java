/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.executors;

import com.jiuqi.np.dataengine.executors.CalcExecutor;
import com.jiuqi.np.dataengine.executors.CheckExecutor;
import com.jiuqi.np.dataengine.executors.ConditionExecutor;
import com.jiuqi.np.dataengine.executors.EvalExecutor;
import com.jiuqi.np.dataengine.executors.ExecutorBase;
import com.jiuqi.np.dataengine.executors.ExecutorCenter;
import com.jiuqi.np.dataengine.executors.ExprExecContext;
import com.jiuqi.np.dataengine.executors.StatExecutor;
import com.jiuqi.np.dataengine.query.QueryContext;

public class ExprExecCenter
extends ExecutorCenter {
    public ExprExecContext execContext;
    private CalcExecutor calcExecutor = null;
    private CheckExecutor checkExecutor = null;
    private EvalExecutor evalExecutor = null;
    private ConditionExecutor conditionExecutor = null;
    private StatExecutor statExecutor = null;

    public ExprExecCenter(QueryContext context) {
        super(context);
        this.execContext = new ExprExecContext(context);
    }

    public final CalcExecutor findCalcExecutor() {
        return this.calcExecutor;
    }

    public final CalcExecutor getCalcExecutor() {
        if (this.calcExecutor == null) {
            this.calcExecutor = new CalcExecutor(this.context);
            this.add(this.calcExecutor);
            this.calcExecutor.setPriority(160);
            if (this.evalExecutor != null) {
                ExecutorBase.createLink(this.calcExecutor, this.evalExecutor);
            }
        }
        return this.calcExecutor;
    }

    public final CheckExecutor findCheckExecutor() {
        return this.checkExecutor;
    }

    public CheckExecutor getCheckExecutor() {
        if (this.checkExecutor == null) {
            this.checkExecutor = new CheckExecutor(this.execContext);
            this.add(this.checkExecutor);
            this.checkExecutor.setPriority(120);
        }
        return this.checkExecutor;
    }

    public final EvalExecutor findEvalExecutor() {
        return this.evalExecutor;
    }

    public final EvalExecutor getEvalExecutor() {
        if (this.evalExecutor == null) {
            this.evalExecutor = new EvalExecutor(this.execContext);
            this.add(this.evalExecutor);
            this.evalExecutor.setPriority(120);
            if (this.calcExecutor != null) {
                ExecutorBase.createLink(this.calcExecutor, this.evalExecutor);
            }
            if (this.statExecutor != null) {
                ExecutorBase.createLink(this.evalExecutor, this.statExecutor);
            }
        }
        return this.evalExecutor;
    }

    public final ConditionExecutor findConditionExecutor() {
        return this.conditionExecutor;
    }

    public final ConditionExecutor getConditionExecutor() {
        if (this.conditionExecutor == null) {
            this.conditionExecutor = new ConditionExecutor(this.execContext);
            this.add(this.conditionExecutor);
            this.conditionExecutor.setPriority(120);
            if (this.calcExecutor != null) {
                ExecutorBase.createLink(this.conditionExecutor, this.calcExecutor);
            }
            if (this.checkExecutor != null) {
                ExecutorBase.createLink(this.conditionExecutor, this.checkExecutor);
            }
        }
        return this.conditionExecutor;
    }

    public final StatExecutor findStatExecutor() {
        return this.statExecutor;
    }

    public boolean hasStatExecutor() {
        return this.statExecutor != null;
    }

    public final StatExecutor getStatExecutor() {
        if (this.statExecutor == null) {
            this.statExecutor = new StatExecutor(this.execContext);
            this.add(this.statExecutor);
            this.statExecutor.setPriority(80);
            if (this.statExecutor != null) {
                ExecutorBase.createLink(this.evalExecutor, this.statExecutor);
            }
        }
        return this.statExecutor;
    }

    @Override
    public void prepare(Object taskInfo) throws Exception {
        boolean doReset = this.state == 2;
        super.prepare(taskInfo);
        if (doReset && this.state == 4 && this.statExecutor != null) {
            this.statExecutor.resetStatItems();
        }
    }
}

