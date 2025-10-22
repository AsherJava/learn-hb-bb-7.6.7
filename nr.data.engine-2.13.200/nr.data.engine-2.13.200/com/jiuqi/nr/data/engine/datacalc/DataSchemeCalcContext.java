/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.CalcExpression
 *  com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask
 */
package com.jiuqi.nr.data.engine.datacalc;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.CalcExpression;
import com.jiuqi.nr.data.engine.datacalc.DataSchemeCalcMonitor;
import com.jiuqi.nr.datascheme.api.service.DataSchemeCalcTask;
import java.util.ArrayList;
import java.util.List;

public class DataSchemeCalcContext
implements IContext {
    private String calcStartPeriod;
    private String calcEndPeriod;
    private DataSchemeCalcTask task;
    private ExecutorContext executorContext;
    private DataSchemeCalcMonitor monitor;
    private List<CalcExpression> calcExpressions = new ArrayList<CalcExpression>();
    private List<String> priorityCalcUnits = null;

    public DataSchemeCalcContext(DataSchemeCalcTask task, ExecutorContext eContext, AsyncTaskMonitor asysMonitor) {
        this.calcStartPeriod = task.getStartPeriod();
        this.calcEndPeriod = task.getEndPeriod();
        this.task = task;
        this.executorContext = eContext;
        this.monitor = new DataSchemeCalcMonitor(asysMonitor);
    }

    public String getCalcStartPeriod() {
        return this.calcStartPeriod;
    }

    public void setCalcStartPeriod(String calcStartPeriod) {
        if (this.task.getStartPeriod() == null && (this.calcStartPeriod == null || this.calcStartPeriod.compareTo(this.calcEndPeriod) > 0)) {
            this.calcStartPeriod = calcStartPeriod;
        }
    }

    public String getCalcEndPeriod() {
        return this.calcEndPeriod;
    }

    public void setCalcEndPeriod(String calcEndPeriod) {
        if (this.task.getEndPeriod() == null && (this.calcEndPeriod == null || this.calcEndPeriod.compareTo(calcEndPeriod) < 0)) {
            this.calcEndPeriod = calcEndPeriod;
        }
    }

    public DataSchemeCalcTask getTask() {
        return this.task;
    }

    public void setTask(DataSchemeCalcTask task) {
        this.task = task;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public DataSchemeCalcMonitor getMonitor() {
        return this.monitor;
    }

    public List<CalcExpression> getCalcExpressions() {
        return this.calcExpressions;
    }

    public void addPriorityCalcUnit(String unitKey) {
        if (this.priorityCalcUnits == null) {
            this.priorityCalcUnits = new ArrayList<String>();
        }
        this.priorityCalcUnits.add(unitKey);
    }

    public List<String> getPriorityCalcUnits() {
        return this.priorityCalcUnits;
    }
}

